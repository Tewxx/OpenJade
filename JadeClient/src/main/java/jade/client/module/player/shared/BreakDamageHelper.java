// Jade recovery: original class: jade.deps.eLz.IcT7Vah0Fh
package jade.client.module.player.shared;

public final class BreakDamageHelper {
   private BreakDamageHelper() {
   }

   public static float GeX06(float var0) {
      return var0 > 1.0F ? (float)(1.0 - 1.0 / var0) : 0.0F;
   }

   public static float oxOn(float var0, float var1) {
      return var0 > 0.0F && var0 < var1 ? var1 : var0;
   }
}
