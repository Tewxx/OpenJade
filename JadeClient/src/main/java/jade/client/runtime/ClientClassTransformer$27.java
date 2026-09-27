// Jade recovery: original class: jade.deps.eLz.BHs3TBy$27
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$27 extends MethodVisitor {
   private final String PubuyS;

   public ClientClassTransformer$27(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.PubuyS = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(this, this.PubuyS, "(Ljava/lang/Object;)V");
   }
}
