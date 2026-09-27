// Jade recovery: original class: jade.deps.eLz.InnKfaj$40
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$40 extends MethodVisitor {
   public AdditionalHooksTransformer$40(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(
         this, "onResourceStreamHead", "(Ljava/lang/Object;Ljava/lang/Object;)Z"
      );
      Label var1 = new Label();
      super.visitJumpInsn(153, var1);
      super.visitInsn(1);
      super.visitInsn(176);
      super.visitLabel(var1);
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 176) {
         super.visitVarInsn(58, 999);
         super.visitVarInsn(25, 0);
         super.visitVarInsn(25, 1);
         super.visitVarInsn(25, 999);
         ClientClassTransformer.emitHookCall(
            this,
            "onResourceStreamReturn",
            "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
         );
         super.visitTypeInsn(192, "java/io/InputStream");
      }

      super.visitInsn(var1);
   }
}
