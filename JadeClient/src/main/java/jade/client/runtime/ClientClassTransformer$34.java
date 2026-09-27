// Jade recovery: original class: jade.deps.eLz.BHs3TBy$34
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$34 extends MethodVisitor {
   public ClientClassTransformer$34(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 3);
      ClientClassTransformer.emitHookCall(this, "onSendUseItem", "(Ljava/lang/Object;)Z");
      Label var1 = new Label();
      super.visitJumpInsn(153, var1);
      super.visitInsn(3);
      super.visitInsn(172);
      super.visitLabel(var1);
   }
}
