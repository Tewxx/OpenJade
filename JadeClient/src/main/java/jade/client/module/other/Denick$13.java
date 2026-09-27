// Jade recovery: original class: jade.deps.eLz.wpmQGvzwn$13
package jade.client.module.other;

public final class Denick$13 {
   private static final int EmM = 1500;
   private int finals = -1;
   private int gyO = -1;

   Denick$13(Denick$14 var1, int var2) {
      if (var1 == Denick$14.BEDS) {
         this.gyO = var2;
      } else if (var1 == Denick$14.FINALS) {
         this.finals = var2;
      }
   }

   private void vBonFd(Denick$13 var1) {
      if (var1 != null) {
         if (var1.finals >= 0) {
            this.finals = var1.finals;
         }

         if (var1.gyO >= 0) {
            this.gyO = var1.gyO;
         }
      }
   }

   private boolean isEntryWithinTolerance(Denick$19 var1) {
      if (this.finals < 0 || Denick$19.YQYDHV(var1) >= 0 && Math.abs(Denick$19.YQYDHV(var1) - this.finals) <= 1500) {
         return this.gyO < 0 || Denick$19.getBeds(var1) >= 0 && Math.abs(Denick$19.getBeds(var1) - this.gyO) <= 1500
            ? this.finals >= 0 || this.gyO >= 0
            : false;
      } else {
         return false;
      }
   }

   private int getEntryDistance(Denick$19 var1) {
      long var2 = 0L;
      if (this.finals >= 0) {
         if (Denick$19.YQYDHV(var1) < 0) {
            return Integer.MAX_VALUE;
         }

         var2 += Math.abs(Denick$19.YQYDHV(var1) - this.finals);
      }

      if (this.gyO >= 0) {
         if (Denick$19.getBeds(var1) < 0) {
            return Integer.MAX_VALUE;
         }

         var2 += Math.abs(Denick$19.getBeds(var1) - this.gyO);
      }

      return var2 > 2147483647L ? Integer.MAX_VALUE : (int)var2;
   }

   private String getStatsLabel() {
      if (this.finals >= 0 && this.gyO >= 0) {
         return "finals " + this.finals + ", beds " + this.gyO;
      } else {
         return this.gyO >= 0 ? "beds " + this.gyO : "finals " + this.finals;
      }
   }

   private String TofDr() {
      return "finals:" + this.finals + "|beds:" + this.gyO;
   }

   public static void copyStats(Denick$13 var0, Denick$13 var1) {
      var0.vBonFd(var1);
   }

   public static String getStatsLabelFor(Denick$13 var0) {
      return var0.getStatsLabel();
   }

   public static int kBqhi(Denick$13 var0) {
      return var0.finals;
   }

   public static int Dceyqa(Denick$13 var0) {
      return var0.gyO;
   }

   public static int setFinals(Denick$13 var0, int var1) {
      return var0.finals = var1;
   }

   public static int setBeds(Denick$13 var0, int var1) {
      return var0.gyO = var1;
   }

   public static int computeEntryDistance(Denick$13 var0, Denick$19 var1) {
      return var0.getEntryDistance(var1);
   }

   public static boolean UYRBXj(Denick$13 var0, Denick$19 var1) {
      return var0.isEntryWithinTolerance(var1);
   }

   public static String getStatsKey(Denick$13 var0) {
      return var0.TofDr();
   }
}
