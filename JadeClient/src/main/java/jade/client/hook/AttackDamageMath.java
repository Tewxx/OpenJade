// Jade recovery: original class: jade.deps.eLz.ebh24G
package jade.client.hook;

public final class AttackDamageMath {
   private static final float[] rvtraO = new float[65536];

   private AttackDamageMath() {
   }

   public static boolean isCriticalHit(float var0, boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      return var0 > 0.0F && !var1 && !var2 && !var3 && !var4 && !var5 && var6;
   }

   public static float applyCriticalDamageBonus(float var0, float var1, boolean var2) {
      float var3 = var2 && var0 > 0.0F ? var0 * 1.5F : var0;
      return var3 + var1;
   }

   public static boolean shouldIgniteTarget(boolean var0, int var1, boolean var2) {
      return var0 && var1 > 0 && !var2;
   }

   public static AttackDamageMath$1 computeKnockbackVector(float var0, int var1) {
      float var2 = var0 * (float) Math.PI / 180.0F;
      return new AttackDamageMath$1(-fastSin(var2) * var1 * 0.5F, fastCos(var2) * var1 * 0.5F);
   }

   public static boolean isOverkillDamage(float var0) {
      return var0 >= 18.0F;
   }

   public static int toDamageStatAmount(float var0) {
      return Math.round(var0 * 10.0F);
   }

   public static int Azpa(int var0) {
      return var0 * 4;
   }

   private static float fastSin(float var0) {
      return rvtraO[(int)(var0 * 10430.378F) & 65535];
   }

   private static float fastCos(float var0) {
      return rvtraO[(int)(var0 * 10430.378F + 16384.0F) & 65535];
   }

   static {
      for (int var0 = 0; var0 < rvtraO.length; var0++) {
         rvtraO[var0] = (float)Math.sin(var0 * Math.PI * 2.0 / 65536.0);
      }
   }
}
