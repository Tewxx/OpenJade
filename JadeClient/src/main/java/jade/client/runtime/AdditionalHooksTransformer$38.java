// Jade recovery: original class: jade.deps.eLz.InnKfaj$38
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$38 extends MethodVisitor {
   public AdditionalHooksTransformer$38(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(
         this,
         "onResourceExistsHead",
         "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
      );
      Label var1 = new Label();
      super.visitInsn(89);
      super.visitJumpInsn(198, var1);
      super.visitTypeInsn(192, "java/lang/Boolean");
      super.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z", false);
      super.visitInsn(172);
      super.visitLabel(var1);
      super.visitInsn(87);
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 172) {
         super.visitVarInsn(54, 999);
         super.visitVarInsn(25, 0);
         super.visitVarInsn(25, 1);
         super.visitVarInsn(21, 999);
         ClientClassTransformer.emitHookCall(
            this, "onResourceExists", "(Ljava/lang/Object;Ljava/lang/Object;Z)Z"
         );
      }

      super.visitInsn(var1);
   }
}
