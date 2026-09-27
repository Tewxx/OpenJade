// Jade recovery: original class: jade.deps.eLz.InnKfaj$50
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$50 extends MethodVisitor {
   private final String z62;

   public AdditionalHooksTransformer$50(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.z62 = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(this, this.z62, "(Ljava/lang/Object;)V");
      super.visitInsn(177);
   }
}
