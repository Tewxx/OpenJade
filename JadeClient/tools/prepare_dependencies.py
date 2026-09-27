from pathlib import Path
import os,subprocess,shutil
import compile_source as c

def prepare():
    deps=c.BUILD/'dependencies';deps.mkdir(parents=True,exist_ok=True)
    # JadeClient/lib already carries the remapped Minecraft/Forge jars, so there is nothing to do:
    # no local Minecraft install and no Maven artifacts are needed for a normal build.
    if (c.LIBS/'minecraft-1.8.9-mcp.jar').exists() and (c.LIBS/'forge-1.8.9-mcp.jar').exists() \
       and (c.LIBS/'annotations-java5-24.1.0.jar').exists():
        print('Using the remapped Minecraft/Forge jars in JadeClient/lib - nothing to prepare.')
        return
    home=Path.home();m2=home/'.m2/repository';asm=m2/'org/ow2/asm/asm/9.8/asm-9.8.jar';commons=m2/'org/ow2/asm/asm-commons/9.8/asm-commons-9.8.jar'
    ann=m2/'org/jetbrains/annotations-java5/24.1.0/annotations-java5-24.1.0.jar'
    missing=[p for p in (asm,commons,ann) if not p.exists()]
    if missing:
        raise SystemExit(
            'Missing Maven artifacts in %s:\n  %s\n'
            'Fetch them once with:\n'
            '  mvn dependency:get -Dartifact=org.ow2.asm:asm:9.8\n'
            '  mvn dependency:get -Dartifact=org.ow2.asm:asm-commons:9.8\n'
            '  mvn dependency:get -Dartifact=org.jetbrains:annotations-java5:24.1.0\n'
            '(or put the jars in %s yourself)' % (m2, '\n  '.join(str(p) for p in missing), deps))
    if not (deps/'annotations-java5-24.1.0.jar').exists():shutil.copy2(ann,deps/ann.name)
    tool=c.CLIENT/'tools/RemapCompileDependencies.java';out=c.BUILD/'tools';out.mkdir(exist_ok=True)
    cp=os.pathsep.join(map(str,[asm,commons]))
    subprocess.run([str(c.JDK/'bin/javac.exe'),'-cp',cp,'-d',str(out),str(tool)],check=True)
    tasks=[('mcp-notch.srg',c.MC/'versions/1.8.9/1.8.9.jar','minecraft-1.8.9-mcp.jar'),('mcp-srg.srg',c.MC/'libraries/net/minecraftforge/forge/1.8.9-11.15.1.2318-1.8.9/forge-1.8.9-11.15.1.2318-1.8.9.jar','forge-1.8.9-mcp.jar')]
    for mapping,original,name in tasks:
        target=deps/name
        if not target.exists() or tool.stat().st_mtime>target.stat().st_mtime:
            subprocess.run([str(c.JDK/'bin/java.exe'),'-cp',str(out)+os.pathsep+cp,'RemapCompileDependencies',str(c.CLIENT/'src/main/resources/jade/inject/mappings'/mapping),str(original),str(target)],check=True)
if __name__=='__main__':prepare()
