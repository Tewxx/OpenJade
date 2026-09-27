// Jade recovery: original class: jade.deps.eLz.InnKfaj$41
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$41 extends MethodVisitor {
   public AdditionalHooksTransformer$41(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 1);
      super.visitVarInsn(25, 2);
      super.visitVarInsn(23, 4);
      ClientClassTransformer.emitHookCall(
         this, "onDrawSelectionBox", "(Ljava/lang/Object;Ljava/lang/Object;F)Z"
      );
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
