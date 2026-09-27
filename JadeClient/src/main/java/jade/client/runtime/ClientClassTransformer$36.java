// Jade recovery: original class: jade.deps.eLz.BHs3TBy$36
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$36 extends MethodVisitor {
   private final String cancelHookName;

   public ClientClassTransformer$36(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.cancelHookName = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      ClientClassTransformer.emitHookCall(this, this.cancelHookName, "()Z");
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
