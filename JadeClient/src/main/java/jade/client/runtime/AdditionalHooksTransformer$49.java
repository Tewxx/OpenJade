// Jade recovery: original class: jade.deps.eLz.InnKfaj$49
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$49 extends MethodVisitor {
   private final String hookName;

   public AdditionalHooksTransformer$49(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.hookName = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(this, this.hookName, "(Ljava/lang/Object;)V");
   }
}
