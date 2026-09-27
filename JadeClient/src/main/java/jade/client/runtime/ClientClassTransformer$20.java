// Jade recovery: original class: jade.deps.eLz.BHs3TBy$20
package jade.client.runtime;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$20 extends ClassVisitor {
   private final boolean forgeHudPass;

   public ClientClassTransformer$20(ClassVisitor var1, boolean var2) {
      super(589824, var1);
      this.forgeHudPass = var2;
   }

   @Override
   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodVisitor var6 = super.visitMethod(var1, var2, var3, var4, var5);
      if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "renderGameOverlay",
         "func_175180_a",
         "(F)V"
      )) {
         return (MethodVisitor)(this.forgeHudPass
            ? var6
            : new ClientClassTransformer$17(var6, 1, "onRenderHud"));
      } else {
         return var6;
      }
   }
}
