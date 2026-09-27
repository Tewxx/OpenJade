// Jade recovery: original class: jade.deps.eLz.BHs3TBy$43
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$43 extends MethodVisitor {
   private final String ryVnx5;

   public ClientClassTransformer$43(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.ryVnx5 = var2;
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(this, this.ryVnx5, "(Ljava/lang/Object;)V");
      }

      super.visitInsn(var1);
   }
}
