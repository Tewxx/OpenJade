"""Compile the recovered Java sources. Never falls back to recovered client bytecode."""
from pathlib import Path
import json,os,subprocess,sys,zipfile,re,shutil

CLIENT=Path(__file__).resolve().parents[1]
ROOT=CLIENT.parent
BUILD=CLIENT/'build'
LIBS=CLIENT/'lib'          # jars that ship with the repository (see .gitignore)
MC=Path(os.environ.get('JADE_MINECRAFT_HOME') or (Path(os.environ.get('APPDATA',''))/'.minecraft'))

# ---------------------------------------------------------------- JDK discovery
# This client targets Java 8 (Minecraft 1.8.9), and the injector needs the Attach API from
# <jdk>/lib/tools.jar, which only exists in JDK 8. So: prefer an explicit override, then a
# JAVA_HOME that is really 8, then the usual vendor install folders, and fail loudly otherwise.

def _jdk_bin(home):
    return Path(home)/'bin'/('javac.exe' if os.name=='nt' else 'javac')

def _jdk_release(home):
    try:
        text=(Path(home)/'release').read_text(encoding='utf-8',errors='replace')
    except OSError:
        return None
    m=re.search(r'JAVA_VERSION="([^"]+)"',text)
    return m.group(1) if m else None

def _is_jdk(home):
    return bool(home) and _jdk_bin(home).exists()

def _is_java8(home):
    version=_jdk_release(home)
    if version:
        return version.startswith(('1.8','8.0','8u')) or version in ('8','8.0')
    try:
        out=subprocess.run([str(_jdk_bin(home)),'-version'],capture_output=True,text=True,errors='replace')
    except OSError:
        return False
    return '1.8' in (out.stdout+out.stderr)

# vendor folders, newest-style names first; the version filter below picks the Java 8 one
_VENDOR_GLOBS=('Eclipse Adoptium/jdk*','Eclipse Foundation/jdk*','AdoptOpenJDK/jdk*',
               'Java/jdk1.8*','Java/jdk-8*','Java/jdk*','Amazon Corretto/jdk1.8*','Amazon Corretto/jdk8*',
               'Zulu/zulu-8*','Zulu/zulu1.8*','Microsoft/jdk-8*','Microsoft/jdk1.8*',
               'BellSoft/LibericaJDK-8*','Semeru/jdk-8*','scoop/apps/*/current')

def _jdk_roots():
    home=Path.home()
    return [os.environ.get('ProgramFiles'),os.environ.get('ProgramFiles(x86)'),os.environ.get('ProgramW6432'),
            '/usr/lib/jvm','/Library/Java/JavaVirtualMachines','/usr/java',
            str(home/'.jdks'),str(home/'sdkman/candidates/java')]

def find_jdk():
    explicit=os.environ.get('JADE_JAVA_HOME')
    if explicit:
        if not _is_jdk(explicit):
            raise SystemExit('JADE_JAVA_HOME=%s is not a JDK (no bin/javac there)'%explicit)
        if not _is_java8(explicit):
            print('warning: JADE_JAVA_HOME points at Java %s, but this project targets Java 8 '
                  '(Minecraft 1.8.9); the built classes may not run on Java 8.'
                  %(_jdk_release(explicit) or '?'), file=sys.stderr)
        return Path(explicit)
    seen=[]
    def add(p):
        if p and _is_jdk(p) and Path(p).resolve() not in {Path(x).resolve() for x in seen}:
            seen.append(Path(p))
    add(os.environ.get('JAVA_HOME'))
    for root in _jdk_roots():
        if not root:
            continue
        for pattern in _VENDOR_GLOBS:
            for candidate in sorted(Path(root).glob(pattern)):
                add(str(candidate))
    eights=[p for p in seen if _is_java8(p)]
    if eights:
        return eights[0]
    found=', '.join(str(p) for p in seen[:4]) or 'none'
    raise SystemExit(
        'No JDK 8 found (searched JAVA_HOME and the usual install folders; saw: %s).\n'
        'This project targets Java 8 and needs a JDK 8 (the injector also needs its lib/tools.jar).\n'
        'Install one, or point JADE_JAVA_HOME at it, e.g.\n'
        '  set JADE_JAVA_HOME=C:/Program Files/Eclipse Adoptium/jdk8u472-b08'%found)

JDK=find_jdk()
LIB_PREFIXES=('jade/deps/asm/','jade/deps/gson/','jade/deps/slf4j/','jade/deps/websocket/','jade/deps/eddsa/','org/spongepowered/')

def classpath():
    jars=list((BUILD/'dependencies').glob('*.jar'))+list(LIBS.glob('*.jar'))
    # A local Minecraft install is optional: JadeClient/lib ships everything the sources compile against.
    # When one is present its libraries are added too, so a build stays consistent with the installed game.
    version=MC/'versions/1.8.9/1.8.9.json'
    if version.exists():
        for lib in json.loads(version.read_text())['libraries']:
            parts=lib['name'].split(':')
            path=lib.get('downloads',{}).get('artifact',{}).get('path') or f"{parts[0].replace('.','/')}/{parts[1]}/{parts[2]}/{parts[1]}-{parts[2]}.jar"
            file=MC/'libraries'/path
            if file.exists(): jars.append(file)
        extras=['net/minecraft/launchwrapper/1.12/launchwrapper-1.12.jar','org/ow2/asm/asm-all/5.0.3/asm-all-5.0.3.jar']
        for rel in extras:
            p=MC/'libraries'/rel
            if p.exists():jars.append(p)
    libraries=LIBS/'recovered-libraries.jar'
    if not libraries.exists():
        LIBS.mkdir(parents=True,exist_ok=True)
        recovered=CLIENT/'bytecode/jade-recovered.jar'
        if not recovered.exists():
            raise SystemExit(
                'Missing %s, and %s is not here to rebuild it from.\n'
                'recovered-libraries.jar is the one binary the repository ships (JadeClient/lib) - restore it before building.' % (libraries,recovered))
        with zipfile.ZipFile(recovered) as src,zipfile.ZipFile(libraries,'w',zipfile.ZIP_DEFLATED) as dest:
            for n in src.namelist():
                if n.endswith('.class') and n.startswith(LIB_PREFIXES):dest.writestr(n,src.read(n))
    jars.append(libraries)
    return sorted(set(p.resolve() for p in jars))

def main():
    BUILD.mkdir(exist_ok=True)
    (BUILD/'classes').mkdir(exist_ok=True)
    java_root=CLIENT/'src/main/java'
    files=[p for p in java_root.rglob('*.java') if not p.relative_to(java_root).as_posix().startswith(LIB_PREFIXES+('jade/client/obfuscator/',))]
    jars=classpath()
    args=['-source','8','-target','8','-encoding','UTF-8','-proc:none','-Xmaxerrs','10000','-Xdiags:verbose','-cp',os.pathsep.join(str(p) for p in jars),'-d',str(BUILD/'classes')]+[str(p) for p in sorted(files)]
    argfile=BUILD/'javac.args'
    argfile.write_text('\n'.join('"'+s.replace('\\','/')+'"' for s in args),encoding='utf-8')
    with (BUILD/'compile-errors.txt').open('w',encoding='utf-8') as log:
        result=subprocess.run([str(JDK/'bin/javac.exe'),'-J-Xmx4g','-J-Dfile.encoding=UTF-8','@'+str(argfile)],stdout=log,stderr=subprocess.STDOUT)
    text=(BUILD/'compile-errors.txt').read_text(encoding='utf-8',errors='replace')
    diagnostics=re.findall(r'^(.+?\.java):(\d+): error: (.+)$',text,re.M)
    from collections import Counter
    report={'exit_code':result.returncode,'source_files':len(files),'error_count':len(diagnostics),'error_categories':dict(Counter(x[2] for x in diagnostics)),'files':dict(Counter(str(Path(x[0]).relative_to(CLIENT)) for x in diagnostics)),'classpath':[str(p) for p in jars]}
    (BUILD/'compile-report.json').write_text(json.dumps(report,indent=2))
    print('Source files:',len(files),'Errors:',len(diagnostics),'Exit:',result.returncode)
    print(Counter(x[2] for x in diagnostics).most_common(12))
    raise SystemExit(result.returncode)

if __name__=='__main__':main()
