// Jade recovery: original class: jade.deps.eLz.SFZ0u00
package jade.client.module.player.autodeflect;

public final class DeflectConditions {
   private DeflectConditions() {
   }

   public static boolean canAimAtFireball(boolean var0, boolean var1, boolean var2, boolean var3, boolean var4) {
      return var0 && var1 && (!var2 || var3) && var4;
   }

   public static boolean shouldHoldSneak(boolean var0, boolean var1, boolean var2, boolean var3) {
      return var0 && var1 && !var2 && !var3;
   }

   public static boolean isTrackableEntity(boolean var0, boolean var1, double var2) {
      return !var0 && var1 && var2 > 16.0;
   }

   public static boolean isValidFireballTarget(boolean var0, boolean var1, double var2, double var4, boolean var6) {
      return var0 && var1 && var2 <= var4 && var6;
   }
}
