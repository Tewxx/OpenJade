// Jade recovery: original class: jade.deps.eLz.InnKfaj$10
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$10 extends MethodVisitor {
   public AdditionalHooksTransformer$10(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, 0);
         super.visitVarInsn(25, 1);
         super.visitVarInsn(21, 2);
         super.visitVarInsn(21, 3);
         super.visitVarInsn(21, 4);
         ClientClassTransformer.emitHookCall(
            this, "onChatLineSet", "(Ljava/lang/Object;Ljava/lang/Object;IIZ)V"
         );
      }

      super.visitInsn(var1);
   }
}
