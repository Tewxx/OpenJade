// Jade recovery: original class: jade.deps.eLz.InnKfaj$25
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$25 extends MethodVisitor {
   public AdditionalHooksTransformer$25(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(
         this, "onGuiChatRequest", "(Ljava/lang/Object;Ljava/lang/Object;)Z"
      );
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
