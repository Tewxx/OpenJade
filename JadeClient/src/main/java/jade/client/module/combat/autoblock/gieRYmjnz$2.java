// Jade recovery: original class: jade.deps.eLz.gieRYmjnz$2
package jade.client.module.combat.autoblock;

public final class gieRYmjnz$2 {
   private final long baY6;
   private boolean VgW;

   gieRYmjnz$2(long var1) {
      this.baY6 = var1;
   }

   public static boolean isInvalidated(gieRYmjnz$2 var0) {
      return var0.VgW;
   }

   public static boolean PITAi(gieRYmjnz$2 var0, boolean var1) {
      return var0.VgW = var1;
   }

   public static long getRecordedTime(gieRYmjnz$2 var0) {
      return var0.baY6;
   }
}
