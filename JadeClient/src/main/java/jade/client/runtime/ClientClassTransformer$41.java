// Jade recovery: original class: jade.deps.eLz.BHs3TBy$41
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$41 extends MethodVisitor {
   private final String cancelHookName;

   public ClientClassTransformer$41(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.cancelHookName = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(this, this.cancelHookName, "(Ljava/lang/Object;)Z");
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
