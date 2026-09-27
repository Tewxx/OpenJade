from pathlib import Path
import subprocess,zipfile,json,hashlib,shutil,io,sys
import compile_source as c
R=c.CLIENT;B=c.BUILD

def clear_output(path):
    path=path.resolve();assert path.parent==B.resolve() and path.name in ('classes','bootstrap-classes','injector-classes')
    if path.exists():shutil.rmtree(path)
    path.mkdir(parents=True)

def java_compile(files,out,extra=()):
    subprocess.run([str(c.JDK/'bin/javac.exe'),'-encoding','UTF-8','-source','8','-target','8',*extra,'-d',str(out),*[str(p) for p in files]],check=True)

def writejar(path,entries):
    with zipfile.ZipFile(path,'w',zipfile.ZIP_DEFLATED) as z:
        for name,data in sorted(entries.items()):
            info=zipfile.ZipInfo(name,(2026,1,1,0,0,0));info.compress_type=zipfile.ZIP_DEFLATED;z.writestr(info,data)
    with zipfile.ZipFile(path) as z:assert z.testzip() is None

def bootstrap_requires_context(context_source):
    """True when the build still demands the commercial loader's signed envelope.

    The recovered client originally resolved an envelope/startup key/hardware identity
    through jade.deps.loader107 and verified them (HMAC-SHA256, plus a licence file). A
    locally built client has nothing to present, so that path was removed from
    InjectionBootstrapContext; report whether it came back.
    """
    text=context_source.read_text(encoding='utf-8')
    # these were obfuscated class names before the rename (Cd0a... style); use the readable ones now
    markers=('LicenceSessionManager','StartupKeyStore','HardwareIdentity',
             'JadeInjector-','initializeFromNativePayload')
    return any(marker in text for marker in markers)

def main():
    clear_output(B/'classes')
    subprocess.run([sys.executable,str(R/'tools/compile_source.py')],check=True)
    clear_output(B/'bootstrap-classes');java_compile(list((R/'src/bootstrap/java').rglob('*.java')),B/'bootstrap-classes')
    bridge={p.relative_to(B/'bootstrap-classes').as_posix():p.read_bytes() for p in (B/'bootstrap-classes').rglob('*.class')}
    stream=io.BytesIO()
    with zipfile.ZipFile(stream,'w',zipfile.ZIP_DEFLATED) as z:
        for n,d in sorted(bridge.items()):z.writestr(n,d)
    entries={p.relative_to(B/'classes').as_posix():p.read_bytes() for p in (B/'classes').rglob('*.class')}
    class_count=len(entries)
    with zipfile.ZipFile(R/'lib/recovered-libraries.jar') as z:
        for n in z.namelist():
            assert n.startswith(c.LIB_PREFIXES);assert n not in entries;entries[n]=z.read(n)
    for p in (R/'src/main/resources').rglob('*'):
        if p.is_file():entries[p.relative_to(R/'src/main/resources').as_posix()]=p.read_bytes()
    entries['jade/inject/bootstrap-bridge.bin']=stream.getvalue()
    identity=hashlib.sha256(b''.join(n.encode()+hashlib.sha256(d).digest() for n,d in sorted(entries.items()) if n.endswith('.class'))).hexdigest()
    entries['META-INF/jade-build-id']=identity.encode()
    entries['META-INF/MANIFEST.MF']=b'Manifest-Version: 1.0\r\nAgent-Class: jade.build.CheckedAgent\r\nPremain-Class: jade.build.CheckedAgent\r\nCan-Retransform-Classes: true\r\nCan-Redefine-Classes: true\r\nJade-Offline-Status: 1\r\n\r\n'
    dist=R/'dist';dist.mkdir(exist_ok=True);dest=dist/'jade-source-built.jar';writejar(dest,entries)
    clear_output(B/'injector-classes');java_compile(list((R/'injector').glob('*.java')),B/'injector-classes',('-cp',str(c.JDK/'lib/tools.jar')))
    inj={p.relative_to(B/'injector-classes').as_posix():p.read_bytes() for p in (B/'injector-classes').rglob('*.class')};inj['META-INF/MANIFEST.MF']=b'Manifest-Version: 1.0\r\nMain-Class: JadeInjector\r\n\r\n';writejar(dist/'jade-injector.jar',inj)
    missing=[]
    mix=json.loads(entries['mixins.jade.json'])
    for n in mix.get('mixins',[])+mix.get('client',[]):
        rel=(mix['package']+'.'+n).replace('.','/')+'.class'
        if rel not in entries:missing.append(rel)
    assert not missing,missing
    report={'source_compilation':True,'source_classes':class_count,'total_classes':sum(n.endswith('.class') for n in entries),'mixins':len(mix.get('mixins',[])+mix.get('client',[])),'sha256':hashlib.sha256(dest.read_bytes()).hexdigest(),'runtime_tested':False,'bootstrap_requires_original_context':bootstrap_requires_context(R/'src/main/java/jade/inject/InjectionBootstrapContext.java')}
    (R/'reports').mkdir(exist_ok=True)
    (R/'reports/source-build.json').write_text(json.dumps(report,indent=2));print(json.dumps(report,indent=2))
if __name__=='__main__':main()
