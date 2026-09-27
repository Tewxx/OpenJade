// Jade recovery: original class: jade.deps.eLz.r7rEnm8$1
package jade.client.module.combat;

public final class AutoFeed$1 {
   private final float raN;
   private final long attackTime;

   AutoFeed$1(float var1, long var2) {
      this.raN = var1;
      this.attackTime = var2;
   }

   public static long getAttackTime(AutoFeed$1 var0) {
      return var0.attackTime;
   }

   public static float getExpectedDamage(AutoFeed$1 var0) {
      return var0.raN;
   }
}
