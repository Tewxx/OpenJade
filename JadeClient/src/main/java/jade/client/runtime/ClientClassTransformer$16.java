// Jade recovery: original class: jade.deps.eLz.BHs3TBy$16
package jade.client.runtime;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$16 extends ClassVisitor {
   private final boolean FjT70;
   private final boolean CWvqK;

   public ClientClassTransformer$16(ClassVisitor var1, boolean var2, boolean var3) {
      super(589824, var1);
      this.FjT70 = var2;
      this.CWvqK = var3;
   }

   @Override
   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodVisitor var6 = super.visitMethod(var1, var2, var3, var4, var5);
      if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "orientCamera",
         "func_78467_g",
         "(F)V"
      )) {
         return new ClientClassTransformer$25(var6);
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "renderWorldPass",
         "func_175068_a",
         "(IFJ)V"
      )) {
         return new ClientClassTransformer$31(var6, this.FjT70);
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "getMouseOver",
         "func_78473_a",
         "(F)V"
      )) {
         return new ClientClassTransformer$19(var6);
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "updateCameraAndRender",
         "func_181560_a",
         "(FJ)V"
      )) {
         ClientClassTransformer$10 var7 = new ClientClassTransformer$10(var6);
         return (MethodVisitor)(this.CWvqK
            ? new ClientClassTransformer$17(var7, 1, "onRenderHud")
            : var7);
      } else if (ClientClassTransformer.OSIs(
            var2,
            var3,
            "setupFog",
            "func_78468_a",
            "(IF)V"
         )
         || ClientClassTransformer.OSIs(
            var2,
            var3,
            "updateFogColor",
            "func_78466_h",
            "(F)V"
         )) {
         return new ClientClassTransformer$30(var6, "onBlindnessPotionCheck");
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "setupCameraTransform",
         "func_78479_a",
         "(FI)V"
      )) {
         return new ClientClassTransformer$30(var6, "onNauseaPotionCheck");
      } else {
         return (MethodVisitor)(ClientClassTransformer.OSIs(
               var2,
               var3,
               "renderWorld",
               "func_78471_a",
               "(FJ)V"
            )
            ? new ClientClassTransformer$32(var6)
            : var6);
      }
   }
}
