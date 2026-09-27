// Jade recovery: original class: jade.deps.eLz.InnKfaj$54
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$54 extends MethodVisitor {
   public AdditionalHooksTransformer$54(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, 0);
         super.visitVarInsn(25, 2);
         ClientClassTransformer.emitHookCall(
            this, "onEntityJoinWorld", "(Ljava/lang/Object;Ljava/lang/Object;)V"
         );
      }

      super.visitInsn(var1);
   }
}
