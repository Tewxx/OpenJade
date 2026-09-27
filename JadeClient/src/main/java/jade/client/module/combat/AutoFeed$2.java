// Jade recovery: original class: jade.deps.eLz.r7rEnm8$2
package jade.client.module.combat;

public final class AutoFeed$2 {
   private float previousHealthTotal;
   private float YDutN;
   private long ESm5;
   private int sampleArmorPoints;

   AutoFeed$2(float var1) {
      this.previousHealthTotal = var1;
   }

   public static float getObservedDamageRatio(AutoFeed$2 var0) {
      return var0.YDutN;
   }

   public static long getLastDamageObservationTime(AutoFeed$2 var0) {
      return var0.ESm5;
   }

   public static int getSampleArmorPoints(AutoFeed$2 var0) {
      return var0.sampleArmorPoints;
   }

   public static float TBJf18(AutoFeed$2 var0) {
      return var0.previousHealthTotal;
   }

   public static float setObservedDamageRatio(AutoFeed$2 var0, float var1) {
      return var0.YDutN = var1;
   }

   public static long setLastDamageObservationTime(AutoFeed$2 var0, long var1) {
      return var0.ESm5 = var1;
   }

   public static int kcJs(AutoFeed$2 var0, int var1) {
      return var0.sampleArmorPoints = var1;
   }

   public static float setPreviousHealthTotal(AutoFeed$2 var0, float var1) {
      return var0.previousHealthTotal = var1;
   }
}
