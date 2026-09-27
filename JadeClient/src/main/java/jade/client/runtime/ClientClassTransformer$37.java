// Jade recovery: original class: jade.deps.eLz.BHs3TBy$37
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$37 extends MethodVisitor {
   private final String hookName;

   public ClientClassTransformer$37(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.hookName = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      ClientClassTransformer.emitHookCall(this, this.hookName, "()V");
   }
}
