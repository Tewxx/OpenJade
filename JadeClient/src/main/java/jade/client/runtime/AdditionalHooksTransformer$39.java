// Jade recovery: original class: jade.deps.eLz.InnKfaj$39
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$39 extends MethodVisitor {
   public AdditionalHooksTransformer$39(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(
         this,
         "onResourceInputHead",
         "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
      );
      Label var1 = new Label();
      super.visitInsn(89);
      super.visitJumpInsn(198, var1);
      super.visitTypeInsn(192, "java/io/InputStream");
      super.visitInsn(176);
      super.visitLabel(var1);
      super.visitInsn(87);
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
            "onResourceInputReturn",
            "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
         );
         super.visitTypeInsn(192, "java/io/InputStream");
      }

      super.visitInsn(var1);
   }
}
