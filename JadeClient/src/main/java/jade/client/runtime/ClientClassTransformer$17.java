// Jade recovery: original class: jade.deps.eLz.BHs3TBy$17
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$17 extends MethodVisitor {
   private final int trLpmA;
   private final String returnHookName;

   public ClientClassTransformer$17(MethodVisitor var1, int var2, String var3) {
      super(589824, var1);
      this.trLpmA = var2;
      this.returnHookName = var3;
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 >= 172 && var1 <= 177) {
         super.visitVarInsn(23, this.trLpmA);
         ClientClassTransformer.emitHookCall(this, this.returnHookName, "(F)V");
      }

      super.visitInsn(var1);
   }
}
