// Jade recovery: original class: jade.deps.eLz.InnKfaj$17
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$17 extends MethodVisitor {
   public AdditionalHooksTransformer$17(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 != 183
         || !"net/minecraft/client/renderer/texture/SimpleTexture".equals(var2)
         || !"loadTexture".equals(var3)
            && !"func_110551_a".equals(var3)) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         super.visitInsn(88);
      }
   }
}
