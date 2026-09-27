// Jade recovery: original class: jade.deps.eLz.wpmQGvzwn$19
package jade.client.module.other;

import java.util.Collections;
import java.util.Map;

public final class Denick$19 {
   private final String name;
   private final String killMessage;
   private final int finals;
   private final int lPi;
   private final Map<String, String> oqO2;
   private final long GAg;
   private final long iqf2;
   private final double score;

   Denick$19(String var1, String var2, int var3, int var4, Map<String, String> var5, long var6, long var8, double var10) {
      this.name = var1;
      this.killMessage = var2;
      this.finals = var3;
      this.lPi = var4;
      this.oqO2 = var5 == null ? Collections.emptyMap() : var5;
      this.GAg = var6;
      this.iqf2 = var8;
      this.score = var10;
   }

   public static String getName(Denick$19 var0) {
      return var0.name;
   }

   public static double YJeT(Denick$19 var0) {
      return var0.score;
   }

   public static String getKillMessage(Denick$19 var0) {
      return var0.killMessage;
   }

   public static long UhoHj(Denick$19 var0) {
      return var0.iqf2;
   }

   public static long getLastUpdated(Denick$19 var0) {
      return var0.GAg;
   }

   public static int YQYDHV(Denick$19 var0) {
      return var0.finals;
   }

   public static int getBeds(Denick$19 var0) {
      return var0.lPi;
   }

   public static Map getCosmeticSelections(Denick$19 var0) {
      return var0.oqO2;
   }
}
