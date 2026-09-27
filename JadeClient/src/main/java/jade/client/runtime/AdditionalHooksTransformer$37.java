// Jade recovery: original class: jade.deps.eLz.InnKfaj$37
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$37 extends MethodVisitor {
   public AdditionalHooksTransformer$37(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(
         this, "onRenderEntityPre", "(Ljava/lang/Object;Ljava/lang/Object;)V"
      );
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 172) {
         super.visitVarInsn(54, 999);
         super.visitVarInsn(25, 0);
         super.visitVarInsn(25, 1);
         ClientClassTransformer.emitHookCall(
            this, "onRenderEntityPost", "(Ljava/lang/Object;Ljava/lang/Object;)V"
         );
         super.visitVarInsn(21, 999);
      }

      super.visitInsn(var1);
   }
}
