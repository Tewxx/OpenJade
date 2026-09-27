// Jade recovery: original class: jade.deps.eLz.InnKfaj$51
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$51 extends MethodVisitor {
   private final String postHookName;

   public AdditionalHooksTransformer$51(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.postHookName = var2;
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(this, this.postHookName, "(Ljava/lang/Object;)V");
      }

      super.visitInsn(var1);
   }
}
