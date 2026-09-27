// Jade recovery: original class: jade.deps.eLz.InnKfaj$22
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$22 extends MethodVisitor {
   private final String hookName;

   public AdditionalHooksTransformer$22(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.hookName = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(this, this.hookName, "(Ljava/lang/Object;)Z");
      Label var1 = new Label();
      super.visitJumpInsn(153, var1);
      super.visitInsn(3);
      super.visitInsn(172);
      super.visitLabel(var1);
   }
}
