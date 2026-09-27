// Jade recovery: original class: jade.deps.eLz.Jk9SHIq
package jade.client.common;

import jade.deps.loader107.SubscriptionState;
import java.time.Instant;

public final class SubscriptionStatus {
   private SubscriptionStatus() {
   }

   public static String kkxPtl4() {
      String var0 = SubscriptionState.getExpiresAt();
      if (var0 == null) {
         return SubscriptionState.getStartupId() > 0L ? "Lifetime" : "Unavailable";
      } else {
         try {
            String var1 = var0.contains("T") ? var0 : var0.replace(' ', 'T');
            if (!var1.endsWith("Z") && var1.indexOf(43, 10) < 0 && var1.indexOf(45, 10) < 0) {
               var1 = var1 + "Z";
            }

            long var2 = (Instant.parse(var1).toEpochMilli() - System.currentTimeMillis()) / 1000L;
            if (var2 <= 0L) {
               return "Expired";
            } else {
               long var4 = var2 / 86400L;
               long var6 = var2 / 3600L % 24L;
               long var8 = var2 / 60L % 60L;
               if (var4 > 0L) {
                  return var4 + "d " + var6 + "h remaining";
               } else {
                  return var6 > 0L ? var6 + "h " + var8 + "m remaining" : Math.max(1L, var8) + "m remaining";
               }
            }
         } catch (RuntimeException var10) {
            return "Unavailable";
         }
      }
   }

   public static void drawStartupStatus(JadeClickGui var0, int var1) {
      IFont var2 = var0.settingFont();
      String var3 = "Startup  ";
      var0.drawDropdownGreeting(14, var1 - 37);
      var0.drawText(var3, 14.0F, var1 - 22, -5523263, var2);
      var0.drawText(kkxPtl4(), 14 + var0.textWidth(var3, var2), var1 - 22, GuiTheme.xGoxa(), var2);
   }
}
