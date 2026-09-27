import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import java.io.File;
import java.util.*;
import java.util.jar.*;

/** Uses the supported JVM Attach API; does not patch native process memory. */
public final class JadeInjector {
    public static void main(String[] args) {
        try { run(args); } catch(Exception e) {
            System.err.println("Attachment failed: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            System.exit(1);
        }
    }
    private static void run(String[] args) throws Exception {
        if(args.length==0 || Arrays.asList(args).contains("--help")) {
            System.out.println("JadeInjector --list\nJadeInjector --pid <PID> --jar <agent.jar> [--args <agent arguments>]\nJade's recovered agent requires its original bootstrap session nonce and context.");
            return;
        }
        if(args.length==1 && args[0].equals("--list")) {
            for(VirtualMachineDescriptor vm: VirtualMachine.list()) System.out.println(vm.id()+"  "+vm.displayName());
            return;
        }
        Map<String,String> opts=new HashMap<>();
        for(int i=0;i<args.length;i+=2) {
            if(i+1>=args.length || !Arrays.asList("--pid","--jar","--args").contains(args[i]))
                throw new IllegalArgumentException("Expected --pid, --jar, or --args followed by a value");
            if(opts.put(args[i],args[i+1])!=null)throw new IllegalArgumentException("Duplicate option: "+args[i]);
        }
        String pid=opts.get("--pid"), path=opts.get("--jar");
        if(pid==null || !pid.matches("[1-9][0-9]*") || path==null)throw new IllegalArgumentException("A positive PID and agent JAR path are required");
        File jar=new File(path).getCanonicalFile();
        String argument=opts.containsKey("--args")?opts.get("--args"):"";
        try(JarFile z=new JarFile(jar,true)) {
            Manifest manifest=z.getManifest();
            if(manifest==null || manifest.getMainAttributes().getValue("Agent-Class")==null)throw new IllegalArgumentException("JAR has no Agent-Class manifest entry");
            String agent=manifest.getMainAttributes().getValue("Agent-Class");
            if(z.getJarEntry(agent.replace('.','/')+".class")==null)throw new IllegalArgumentException("Agent entry class is missing");
            if(z.getJarEntry("jade/inject/InjectionBootstrapContext.class")!=null && !argument.matches("[0-9a-f]{32}"))
                throw new IllegalArgumentException("This Jade build needs the original bootstrap's 32-digit nonce and startup context. A plain attach cannot create that context.");
        }
        VirtualMachine vm=VirtualMachine.attach(pid);
        try {vm.loadAgent(jar.getPath(),argument);} finally {vm.detach();}
        System.out.println("JVM accepted the agent. Check the agent's startup status for client readiness.");
    }
}
