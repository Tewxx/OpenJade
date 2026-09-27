// Jade recovery: original class: jade.deps.eLz.BHs3TBy$19
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$19 extends MethodVisitor {
   public ClientClassTransformer$19(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      ClientClassTransformer.emitHookCall(this, "onGetMouseOverPre", "()V");
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 >= 172 && var1 <= 177) {
         super.visitVarInsn(23, 1);
         ClientClassTransformer.emitHookCall(this, "onGetMouseOverPost", "(F)V");
      }

      super.visitInsn(var1);
   }
}
