// Jade recovery: original class: jade.deps.eLz.RfHNFoUd
package jade.client.command;

import jade.client.common.ConfigEntry;
import java.util.List;

public final class CommandInputMatch {
   public final String Nq7;
   public final String QWa;

   private CommandInputMatch(String var1, String var2) {
      this.Nq7 = var1;
      this.QWa = var2;
   }

   public static CommandInputMatch matchLongestName(String var0, List<ConfigEntry> var1) {
      String var2 = var0 == null ? "" : var0.trim();
      if (var2.isEmpty()) {
         return null;
      } else {
         String var3 = null;

         for (ConfigEntry var5 : var1) {
            String var6 = var5.getName();
            if (var2.length() > var6.length()
               && var2.regionMatches(true, 0, var6, 0, var6.length())
               && Character.isWhitespace(var2.charAt(var6.length()))
               && (var3 == null || var6.length() > var3.length())) {
               var3 = var6;
            }
         }

         if (var3 == null) {
            return null;
         } else {
            String var7 = var2.substring(var3.length()).trim();
            return var7.isEmpty() ? null : new CommandInputMatch(var3, var7);
         }
      }
   }
}
