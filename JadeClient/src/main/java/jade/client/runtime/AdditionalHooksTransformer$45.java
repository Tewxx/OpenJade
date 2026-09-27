// Jade recovery: original class: jade.deps.eLz.InnKfaj$45
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$45 extends MethodVisitor {
   private final String sdP;

   public AdditionalHooksTransformer$45(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.sdP = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(this, this.sdP, "(Ljava/lang/Object;Ljava/lang/Object;)V");
   }
}
