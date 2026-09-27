// Jade recovery: original class: jade.deps.eLz.qiYbVQBhM
package jade.client.hook;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassVisitor;
import jade.deps.asm.ClassWriter;
import jade.deps.asm.MethodVisitor;

import java.lang.instrument.ClassFileTransformer;
import java.nio.charset.StandardCharsets;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.Set;

public class AccessorConflictPatcher implements ClassFileTransformer {
   private final Set<String> targetClassNames;
   private final Map<String, String> accessorRenameMap;

   public AccessorConflictPatcher(Set<String> var1, Map<String, String> var2) {
      this.targetClassNames = var1;
      this.accessorRenameMap = var2;
   }

   @Override
   public byte[] transform(ClassLoader var1, String var2, Class<?> var3, ProtectionDomain var4, byte[] var5) {
      if (var2 == null) {
         return null;
      } else if (this.accessorRenameMap.isEmpty()) {
         return null;
      } else if (!this.targetClassNames.contains(var2)) {
         return null;
      } else {
         boolean var6 = false;

         for (String var8 : this.accessorRenameMap.keySet()) {
            if (var8.startsWith(var2 + "\n")) {
               var6 = true;
               break;
            }
         }

         if (!var6) {
            for (String var12 : this.accessorRenameMap.keySet()) {
               String var9 = var12.split("\n")[1];
               if (containsUtf8Bytes(var5, var9)) {
                  var6 = true;
                  break;
               }
            }
         }

         if (!var6) {
            return null;
         } else {
            byte[] var11 = applyAccessorRenames(var5, this.accessorRenameMap);
            System.out.println("[Mod-Agent] AccessorConflictPatcher applied renames in: " + var2);
            return var11;
         }
      }
   }

   public static byte[] applyAccessorRenames(byte[] var0, final Map<String, String> var1) {
      ClassReader var2 = new ClassReader(var0);
      ClassWriter var3 = new ClassWriter(var2, 0);
      var2.accept(
         new ClassVisitor(327680, var3) {
            private String className;

            @Override
            public void visit(int var1x, int var2x, String var3x, String var4, String var5, String[] var6) {
               this.className = var3x;
               super.visit(var1x, var2x, var3x, var4, var5, var6);
            }

            @Override
            public MethodVisitor visitMethod(int var1x, String var2x, String var3x, String var4, String[] var5) {
               String var6 = this.className
                  + "\n"
                  + var2x
                  + "\n"
                  + var3x;
               String var7 = (String)var1.get(var6);
               if (var7 != null) {
                  System.out
                     .println(
                        "[Mod-Agent] Renaming accessor declaration: "
                           + var2x
                           + " \u2192 "
                           + var7
                           + " in "
                           + this.className
                     );
                  return super.visitMethod(var1x, var7, var3x, var4, var5);
               } else {
                  return new MethodVisitor(327680, super.visitMethod(var1x, var2x, var3x, var4, var5)) {
                     @Override
                     public void visitMethodInsn(int var1x, String var2x, String var3x, String var4x, boolean var5x) {
                        String var6x = var2x
                           + "\n"
                           + var3x
                           + "\n"
                           + var4x;
                        String var7x = (String)var1.get(var6x);
                        if (var7x != null) {
                           System.out
                              .println(
                                 "[Mod-Agent] Renaming accessor call site: "
                                    + var3x
                                    + " \u2192 "
                                    + var7x
                                    + " (owner: "
                                    + var2x
                                    + ")"
                              );
                           super.visitMethodInsn(var1x, var2x, var7x, var4x, var5x);
                        } else {
                           super.visitMethodInsn(var1x, var2x, var3x, var4x, var5x);
                        }
                     }
                  };
               }
            }
         },
         0
      );
      return var3.toByteArray();
   }

   private static boolean containsUtf8Bytes(byte[] var0, String var1) {
      byte[] var2 = var1.getBytes(StandardCharsets.UTF_8);

      label24:
      for (int var3 = 0; var3 <= var0.length - var2.length; var3++) {
         for (int var4 = 0; var4 < var2.length; var4++) {
            if (var0[var3 + var4] != var2[var4]) {
               continue label24;
            }
         }

         return true;
      }

      return false;
   }
}
