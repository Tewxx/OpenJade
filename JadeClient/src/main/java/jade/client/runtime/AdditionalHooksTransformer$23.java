// Jade recovery: original class: jade.deps.eLz.InnKfaj$23
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$23 extends MethodVisitor {
   public AdditionalHooksTransformer$23(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(23, 1);
      super.visitVarInsn(23, 2);
      ClientClassTransformer.emitHookCall(this, "onFreecamEntitySetAngles", "(Ljava/lang/Object;FF)Z");
      Label var1 = new Label();
      super.visitJumpInsn(153, var1);
      super.visitInsn(177);
      super.visitLabel(var1);
   }
}
