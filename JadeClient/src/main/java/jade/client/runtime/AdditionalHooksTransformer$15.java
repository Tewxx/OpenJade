// Jade recovery: original class: jade.deps.eLz.InnKfaj$15
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$15 extends MethodVisitor {
   public AdditionalHooksTransformer$15(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, 0);
         super.visitVarInsn(21, 1);
         super.visitVarInsn(21, 2);
         ClientClassTransformer.emitHookCall(this, "onContainerDrawScreen", "(Ljava/lang/Object;II)V");
      }

      super.visitInsn(var1);
   }
}
