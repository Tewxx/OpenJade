// Jade recovery: original class: jade.deps.eLz.BHs3TBy$14
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$14 extends MethodVisitor {
   public ClientClassTransformer$14(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(
         this, "onDisplayGuiScreen", "(Ljava/lang/Object;Ljava/lang/Object;)Z"
      );
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
