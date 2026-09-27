// Jade recovery: original class: jade.deps.eLz.InnKfaj$32
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$32 extends MethodVisitor {
   public AdditionalHooksTransformer$32(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(23, 1);
      super.visitVarInsn(23, 2);
      super.visitVarInsn(23, 3);
      ClientClassTransformer.emitHookCall(this, "onMoveFlying", "(Ljava/lang/Object;FFF)V");
      super.visitInsn(177);
   }
}
