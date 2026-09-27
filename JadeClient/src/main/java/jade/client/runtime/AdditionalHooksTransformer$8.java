// Jade recovery: original class: jade.deps.eLz.InnKfaj$8
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$8 extends MethodVisitor {
   public AdditionalHooksTransformer$8(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 176) {
         super.visitVarInsn(58, 999);
         super.visitVarInsn(25, 0);
         super.visitVarInsn(25, 999);
         ClientClassTransformer.emitHookCall(
            this,
            "onCapeLocation",
            "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
         );
         Label var2 = new Label();
         super.visitInsn(89);
         super.visitJumpInsn(199, var2);
         super.visitInsn(87);
         super.visitVarInsn(25, 999);
         super.visitLabel(var2);
         super.visitTypeInsn(192, "net/minecraft/util/ResourceLocation");
      }

      super.visitInsn(var1);
   }
}
