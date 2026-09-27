// Jade recovery: original class: jade.deps.eLz.InnKfaj$30
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$30 extends MethodVisitor {
   public AdditionalHooksTransformer$30(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(this, "onSuppressLivingName", "(Ljava/lang/Object;)Z");
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
