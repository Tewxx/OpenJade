// Jade recovery: original class: jade.deps.eLz.klxVRZP
package jade.client.command;

import jade.client.common.ClientUtils;

public final class ClipboardUtils {
   private ClipboardUtils() {
   }

   public static String generateRequestId(String var0) {
      try {
         return TimeTokenGenerator.HXFm(System.currentTimeMillis());
      } catch (Exception var2) {
         var2.printStackTrace();
         return "";
      }
   }

   public static void copyToClipboard(String var0) {
      try {
         Clipboard.copyToClipboard(var0);
      } catch (Exception var2) {
         ClientUtils.sendColoredMessage("&cFailed to copy &b" + var0);
      }
   }
}
