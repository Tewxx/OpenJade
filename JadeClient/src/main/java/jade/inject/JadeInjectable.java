// Jade recovery: recovered class name: JadeInjectable
package jade.inject;

import java.io.File;

public final class JadeInjectable {
   private static volatile boolean started;

   private JadeInjectable() {
   }

   public static synchronized void start(String jarPath, String dllPath) {
      if (!started) {
         started = true;
         File jar = new File(jarPath).getAbsoluteFile();
         if (!jar.isFile()) {
            System.err.println("[Jade-Inject] jade.jar does not exist: " + jar);
         } else {
            try {
               System.load(new File(dllPath).getAbsolutePath());
            } catch (UnsatisfiedLinkError var8) {
               String message = var8.getMessage();
               if (message == null || message.indexOf("already loaded") < 0) {
                  System.err.println("[Jade-Inject] JNI registration failed: " + var8);
                  return;
               }
            }

            try {
               String dllName = new File(dllPath).getName();
               int prefix = dllName.indexOf("payload-");
               int suffix = dllName.lastIndexOf(".dll");
               String nonce = prefix == 0 && suffix > 8 ? dllName.substring(8, suffix) : "";
               if (!NativeBridge.attachAgent(jar.getAbsolutePath(), nonce)) {
                  System.err.println("[Jade-Inject] Agent_OnAttach returned failure");
               }
            } catch (Throwable var7) {
               System.err.println("[Jade-Inject] Agent_OnAttach failed: " + var7);
            }
         }
      }
   }
}
