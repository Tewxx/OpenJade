// Jade recovery: original class: jade.deps.eLz.BHs3TBy$7
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$7 extends MethodVisitor {
   public ClientClassTransformer$7(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 1);
      super.visitVarInsn(25, 2);
      ClientClassTransformer.emitHookCall(this, "onAttackEntity", "(Ljava/lang/Object;Ljava/lang/Object;)Z");
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
