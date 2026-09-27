// Jade recovery: original class: jade.deps.eLz.BHs3TBy$38
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$38 extends MethodVisitor {
   private final String XHoqB;
   private final String fiE;
   private final boolean nmF;

   public ClientClassTransformer$38(MethodVisitor var1, String var2, String var3, boolean var4) {
      super(589824, var1);
      this.XHoqB = var2;
      this.fiE = var3;
      this.nmF = var4;
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 >= 172 && var1 <= 177) {
         if (this.nmF) {
            super.visitVarInsn(23, 1);
         }

         ClientClassTransformer.emitHookCall(this, this.XHoqB, this.fiE);
      }

      super.visitInsn(var1);
   }
}
