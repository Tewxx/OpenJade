// Jade recovery: original class: jade.deps.eLz.InnKfaj$24
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$24 extends MethodVisitor {
   public AdditionalHooksTransformer$24(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(21, 2);
      ClientClassTransformer.emitHookCall(this, "onGuiChatKeyPre", "(Ljava/lang/Object;I)V");
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(this, "onGuiChatKeyPost", "(Ljava/lang/Object;)V");
      }

      super.visitInsn(var1);
   }
}
