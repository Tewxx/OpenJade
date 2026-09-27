// Jade recovery: original class: jade.deps.eLz.BHs3TBy$26
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$26 extends MethodVisitor {
   private final String BiP;

   public ClientClassTransformer$26(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.BiP = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(this, this.BiP, "(Ljava/lang/Object;)Z");
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
