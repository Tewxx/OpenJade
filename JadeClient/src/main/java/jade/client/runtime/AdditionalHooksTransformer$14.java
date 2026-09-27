// Jade recovery: original class: jade.deps.eLz.InnKfaj$14
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$14 extends MethodVisitor {
   public AdditionalHooksTransformer$14(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      super.visitVarInsn(21, 2);
      super.visitVarInsn(21, 3);
      super.visitVarInsn(21, 4);
      ClientClassTransformer.emitHookCall(
         this, "onContainerClick", "(Ljava/lang/Object;Ljava/lang/Object;III)Z"
      );
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
