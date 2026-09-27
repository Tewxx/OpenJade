// Jade recovery: original class: jade.deps.eLz.InnKfaj$21
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$21 extends MethodVisitor {
   public AdditionalHooksTransformer$21(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      super.visitVarInsn(23, 2);
      super.visitVarInsn(23, 3);
      super.visitVarInsn(21, 4);
      ClientClassTransformer.emitHookCall(
         this,
         "onChatDrawString",
         "(Ljava/lang/Object;Ljava/lang/Object;FFI)Ljava/lang/Object;"
      );
      Label var1 = new Label();
      super.visitInsn(89);
      super.visitJumpInsn(198, var1);
      super.visitTypeInsn(192, "java/lang/Number");
      super.visitMethodInsn(182, "java/lang/Number", "intValue", "()I", false);
      super.visitInsn(172);
      super.visitLabel(var1);
      super.visitInsn(87);
   }
}
