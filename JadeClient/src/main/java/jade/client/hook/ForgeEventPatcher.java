// Jade recovery: original class: jade.deps.eLz.zWWk9hiXdH
package jade.client.hook;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassVisitor;
import jade.deps.asm.ClassWriter;
import jade.deps.asm.MethodVisitor;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Set;

public class ForgeEventPatcher implements ClassFileTransformer {
   private static final String FORGE_EVENT_SUPERCLASS = "net/minecraftforge/fml/common/eventhandler/Event";
   private final Set<String> QWf;

   public ForgeEventPatcher(Set<String> var1) {
      this.QWf = var1;
   }

   @Override
   public byte[] transform(ClassLoader var1, String var2, Class<?> var3, ProtectionDomain var4, byte[] var5) {
      if (var2 != null && this.QWf.contains(var2)) {
         ClassReader var6 = new ClassReader(var5);
         if (!this.isForgeEventClass(var6)) {
            return null;
         } else if (this.hasDefaultConstructor(var6)) {
            return null;
         } else {
            ClassWriter var7 = new ClassWriter(var6, 2) {
               @Override
               protected String getCommonSuperClass(String var1, String var2x) {
                  try {
                     return super.getCommonSuperClass(var1, var2x);
                  } catch (Throwable var4x) {
                     return "java/lang/Object";
                  }
               }
            };
            var6.accept(new ForgeEventPatcher$2(var7, var6.getSuperName()), 0);
            byte[] var8 = var7.toByteArray();
            System.out.println("[Mod-Agent] Injected no-args constructor into event: " + var2);
            return var8;
         }
      } else {
         return null;
      }
   }

   private boolean isForgeEventClass(ClassReader var1) {
      String var2 = var1.getSuperName();
      if (var2 == null) {
         return false;
      } else {
         return var2.equals("net/minecraftforge/fml/common/eventhandler/Event")
            ? true
            : var2.startsWith("net/minecraftforge/");
      }
   }

   private boolean hasDefaultConstructor(ClassReader var1) {
      final boolean[] var2 = new boolean[]{false};
      var1.accept(new ClassVisitor(327680) {
         @Override
         public MethodVisitor visitMethod(int var1, String var2x, String var3, String var4, String[] var5) {
            if ("<init>".equals(var2x) && "()V".equals(var3)) {
               var2[0] = true;
            }

            return null;
         }
      }, 5);
      return var2[0];
   }
}
