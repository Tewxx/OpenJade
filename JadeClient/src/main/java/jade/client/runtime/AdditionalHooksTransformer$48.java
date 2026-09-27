// Jade recovery: original class: jade.deps.eLz.InnKfaj$48
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$48 extends MethodVisitor {
   private final String cancelHookName;

   public AdditionalHooksTransformer$48(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.cancelHookName = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(this, this.cancelHookName, "(Ljava/lang/Object;)Z");
      Label var1 = new Label();
      super.visitJumpInsn(153, var1);
      super.visitInsn(177);
      super.visitLabel(var1);
   }
}
