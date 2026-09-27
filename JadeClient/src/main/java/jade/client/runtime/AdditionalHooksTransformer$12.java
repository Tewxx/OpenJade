// Jade recovery: original class: jade.deps.eLz.InnKfaj$12
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$12 extends MethodVisitor {
   public AdditionalHooksTransformer$12(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      super.visitVarInsn(21, 2);
      ClientClassTransformer.emitHookCall(this, "onGuiChatSend", "(Ljava/lang/Object;Ljava/lang/Object;Z)Z");
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
