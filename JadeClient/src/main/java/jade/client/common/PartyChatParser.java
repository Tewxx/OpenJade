// Jade recovery: original class: jade.deps.eLz.uc4i6nIZ
package jade.client.common;

public final class PartyChatParser {
   private static final String hFw = "Party >";

   private PartyChatParser() {
   }

   public static PartyChatParser$0 parsePartyMessage(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = var0.trim();
         int var2 = var1.indexOf("Party >");
         if (var2 < 0) {
            return null;
         } else {
            int var3 = var1.lastIndexOf(32);
            if (var3 >= 0 && var3 + 1 < var1.length()) {
               String var4 = var1.substring(var3 + 1).trim();
               if (!JengaPartyCodec.isEncodedPacket(var4)) {
                  return null;
               } else {
                  int var5 = var1.lastIndexOf(58, var3);
                  if (var5 < var2) {
                     return null;
                  } else {
                     String var6 = var1.substring(var2 + "Party >".length(), var5).trim();
                     int var7 = var6.lastIndexOf(32);
                     String var8 = sanitizePlayerName(var7 >= 0 ? var6.substring(var7 + 1) : var6);
                     return var8.isEmpty() ? null : new PartyChatParser$0(var8, var4);
                  }
               }
            } else {
               return null;
            }
         }
      }
   }

   private static String sanitizePlayerName(String var0) {
      if (var0 == null) {
         return "";
      } else {
         String var1 = var0.trim();
         if (var1.length() >= 1 && var1.length() <= 16) {
            for (int var2 = 0; var2 < var1.length(); var2++) {
               char var3 = var1.charAt(var2);
               if (!Character.isLetterOrDigit(var3) && var3 != '_') {
                  return "";
               }
            }

            return var1;
         } else {
            return "";
         }
      }
   }
}
