// Jade recovery: original class: jade.deps.eLz.InnKfaj$18
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$18 extends MethodVisitor {
   public AdditionalHooksTransformer$18(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 172) {
         super.visitInsn(89);
         super.visitVarInsn(25, 0);
         super.visitVarInsn(25, 1);
         ClientClassTransformer.emitHookCall(
            this, "onEntityJoinWorld", "(Ljava/lang/Object;Ljava/lang/Object;)V"
         );
         super.visitInsn(87);
      }

      super.visitInsn(var1);
   }
}
