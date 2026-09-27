// Jade recovery: original class: jade.deps.eLz.InnKfaj$13
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$13 extends MethodVisitor {
   public AdditionalHooksTransformer$13(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      super.visitVarInsn(25, 2);
      super.visitVarInsn(25, 3);
      super.visitVarInsn(25, 4);
      super.visitVarInsn(25, 5);
      super.visitVarInsn(25, 6);
      ClientClassTransformer.emitHookCall(
         this,
         "onAddCollisionBoxes",
         "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z"
      );
      Label vanilla = new Label();
      super.visitJumpInsn(153, vanilla);
      super.visitInsn(177);
      super.visitLabel(vanilla);
   }
}
