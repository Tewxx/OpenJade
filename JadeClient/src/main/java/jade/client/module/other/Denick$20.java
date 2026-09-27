// Jade recovery: original class: jade.deps.eLz.wpmQGvzwn$20
package jade.client.module.other;

public final class Denick$20 {
   private final String name;
   private final String mVy;
   private final boolean nicked;
   private final long observedAt;

   Denick$20(String var1, String var2, boolean var3, long var4) {
      this.name = var1;
      this.mVy = var2 == null ? "" : var2;
      this.nicked = var3;
      this.observedAt = var4;
   }

   public static String getName(Denick$20 var0) {
      return var0.name;
   }

   public static boolean isNicked(Denick$20 var0) {
      return var0.nicked;
   }

   public static long getObservedAt(Denick$20 var0) {
      return var0.observedAt;
   }

   public static String getDenickedName(Denick$20 var0) {
      return var0.mVy;
   }
}
