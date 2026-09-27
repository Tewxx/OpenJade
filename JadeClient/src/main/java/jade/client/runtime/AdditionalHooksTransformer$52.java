// Jade recovery: original class: jade.deps.eLz.InnKfaj$52
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$52 extends MethodVisitor {
   public AdditionalHooksTransformer$52(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(23, 1);
      super.visitVarInsn(23, 2);
      ClientClassTransformer.emitHookCall(this, "onUpdateDistance", "(Ljava/lang/Object;FF)F");
      super.visitInsn(174);
   }
}
