// Jade recovery: original class: jade.deps.eLz.InnKfaj$6
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$6 extends MethodVisitor {
   private final String jGkx;

   public AdditionalHooksTransformer$6(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.jGkx = var2;
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 172) {
         super.visitVarInsn(54, 999);
         super.visitVarInsn(25, 0);
         super.visitVarInsn(21, 999);
         ClientClassTransformer.emitHookCall(this, this.jGkx, "(Ljava/lang/Object;Z)Z");
      }

      super.visitInsn(var1);
   }
}
