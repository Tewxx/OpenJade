// Jade recovery: original class: jade.deps.eLz.JdxgPRdIcK
package jade.client.common;

import net.minecraft.util.MathHelper;

public final class FovUtils {
   private FovUtils() {
   }

   public static boolean isAngleWithinFov(float var0, float var1, float var2) {
      double var3 = MathHelper.wrapAngleTo180_double((var0 - var2) % 360.0F);
      double var5 = var1 * 0.5;
      return var3 > 0.0 ? var3 < var5 : var3 > -var5;
   }
}
