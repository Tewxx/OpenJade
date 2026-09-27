// Jade recovery: original class: jade.deps.eLz.BHs3TBy$35
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public class ClientClassTransformer$35 extends MethodVisitor {
   private final String preHookName;
   private final String OpFy;

   public ClientClassTransformer$35(MethodVisitor var1, String var2, String var3) {
      super(589824, var1);
      this.preHookName = var2;
      this.OpFy = var3;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      ClientClassTransformer.emitHookCall(this, this.preHookName, "()V");
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 >= 172 && var1 <= 177) {
         ClientClassTransformer.emitHookCall(this, this.OpFy, "()V");
      }

      super.visitInsn(var1);
   }
}
