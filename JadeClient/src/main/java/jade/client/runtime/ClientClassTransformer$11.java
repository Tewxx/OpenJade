// Jade recovery: original class: jade.deps.eLz.BHs3TBy$11
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$11 extends MethodVisitor {
   public ClientClassTransformer$11(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 2);
      ClientClassTransformer.emitHookCall(this, "onNetworkReceive", "(Ljava/lang/Object;)Z");
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
