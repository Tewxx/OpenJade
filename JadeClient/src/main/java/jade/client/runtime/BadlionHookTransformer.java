// Jade recovery: recovered class name: BadlionHookTransformer; original class: jade.deps.eLz.xvex97kT
package jade.client.runtime;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public final class BadlionHookTransformer implements ClassFileTransformer {
   private final VanillaHookTransformer delegate;

   public BadlionHookTransformer(RuntimeMappings mappings) {
      if (mappings == null) {
         throw new IllegalArgumentException(
            "Badlion requires Notch runtime mappings"
         );
      } else {
         this.delegate = new VanillaHookTransformer(mappings, true, false);
      }
   }

   @Override
   public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
      return this.delegate.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
   }
}
