// Jade recovery: original class: jade.deps.eLz.BHs3TBy$13
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$13 extends MethodVisitor {
   public ClientClassTransformer$13(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(this, "onPlayerDamageBlockHead", "(Ljava/lang/Object;)V");
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 172) {
         super.visitVarInsn(54, 999);
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(
            this, "onPlayerDamageBlockReturn", "(Ljava/lang/Object;)V"
         );
         super.visitVarInsn(21, 999);
      }

      super.visitInsn(var1);
   }
}
