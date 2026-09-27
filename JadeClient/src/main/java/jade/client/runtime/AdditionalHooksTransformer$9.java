// Jade recovery: original class: jade.deps.eLz.InnKfaj$9
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$9 extends MethodVisitor {
   public AdditionalHooksTransformer$9(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 172) {
         super.visitVarInsn(54, 999);
         super.visitVarInsn(25, 0);
         super.visitVarInsn(25, 1);
         super.visitVarInsn(21, 999);
         ClientClassTransformer.emitHookCall(
            this, "onCapeWearState", "(Ljava/lang/Object;Ljava/lang/Object;Z)Z"
         );
      }

      super.visitInsn(var1);
   }
}
