// Jade recovery: original class: jade.deps.eLz.xoz7RIcRO$1
package jade.client.common;

import java.util.Locale;

public final class WhisperShortcuts$1 {
   private final String triggerText;
   private final long expiresAtMillis;

   WhisperShortcuts$1(String var1, long var2) {
      this.triggerText = var1 == null ? "" : var1.toLowerCase(Locale.ROOT);
      this.expiresAtMillis = var2;
   }

   public static long dmlTh(WhisperShortcuts$1 var0) {
      return var0.expiresAtMillis;
   }

   public static String getTriggerText(WhisperShortcuts$1 var0) {
      return var0.triggerText;
   }
}
