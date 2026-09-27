// Jade recovery: original class: jade.deps.eLz.InnKfaj$33
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$33 extends MethodVisitor {
   private final int hookArgumentVarIndex;
   private final String hookName;

   public AdditionalHooksTransformer$33(MethodVisitor var1, int var2, String var3) {
      super(589824, var1);
      this.hookArgumentVarIndex = var2;
      this.hookName = var3;
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, this.hookArgumentVarIndex);
         ClientClassTransformer.emitHookCall(this, this.hookName, "(Ljava/lang/Object;)V");
      }

      super.visitInsn(var1);
   }
}
