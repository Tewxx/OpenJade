// Jade recovery: original class: jade.deps.eLz.InnKfaj$47
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$47 extends MethodVisitor {
   private final String enterHookName;
   private final String exitHookName;

   public AdditionalHooksTransformer$47(MethodVisitor var1, String var2, String var3) {
      super(589824, var1);
      this.enterHookName = var2;
      this.exitHookName = var3;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(this, this.enterHookName, "(Ljava/lang/Object;)V");
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(this, this.exitHookName, "(Ljava/lang/Object;)V");
      }

      super.visitInsn(var1);
   }
}
