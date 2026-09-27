// Jade recovery: original class: jade.deps.eLz.InnKfaj$44
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$44 extends MethodVisitor {
   public AdditionalHooksTransformer$44(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(21, 1);
      super.visitVarInsn(25, 2);
      super.visitVarInsn(25, 3);
      ClientClassTransformer.emitHookCall(this, "onTabRender", "(ILjava/lang/Object;Ljava/lang/Object;)Z");
      Label var1 = new Label();
      super.visitJumpInsn(153, var1);
      super.visitInsn(177);
      super.visitLabel(var1);
   }
}
