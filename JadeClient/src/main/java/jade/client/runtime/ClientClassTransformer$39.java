// Jade recovery: original class: jade.deps.eLz.BHs3TBy$39
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$39 extends MethodVisitor {
   private final String Cq3;
   private final String postHookName;

   public ClientClassTransformer$39(MethodVisitor var1, String var2, String var3) {
      super(589824, var1);
      this.Cq3 = var2;
      this.postHookName = var3;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(this, this.Cq3, "(Ljava/lang/Object;)V");
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 >= 172 && var1 <= 177) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(this, this.postHookName, "(Ljava/lang/Object;)V");
      }

      super.visitInsn(var1);
   }
}
