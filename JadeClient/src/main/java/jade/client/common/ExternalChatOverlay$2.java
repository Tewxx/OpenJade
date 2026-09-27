// Jade recovery: original class: jade.deps.eLz.bj3B9yb6$2
package jade.client.common;

public final class ExternalChatOverlay$2 {
   private final String YmcN;
   private final long timestampMillis;

   ExternalChatOverlay$2(String var1, long var2) {
      this.YmcN = var1;
      this.timestampMillis = var2;
   }

   public static long getTimestampMillis(ExternalChatOverlay$2 var0) {
      return var0.timestampMillis;
   }

   public static String getText(ExternalChatOverlay$2 var0) {
      return var0.YmcN;
   }
}
