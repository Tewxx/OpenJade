// Jade recovery: original class: jade.deps.eLz.xoz7RIcRO
package jade.client.common;

import jade.client.event.ChatReceivedEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;

public class WhisperShortcuts {
   private static final long REPLY_TIMEOUT_MS = 5000L;
   private static final List<WhisperShortcuts$1> pendingReplies = new LinkedList<>();

   public static void vQwg3(String var0) {
      String var1 = WLzVqoy(var0);
      if (!var1.isEmpty()) {
         sendWhisperCommand("/w +" + var1, "+" + var1);
      }
   }

   public static void removeWhisperShortcut(String var0) {
      String var1 = WLzVqoy(var0);
      if (!var1.isEmpty()) {
         sendWhisperCommand("/w -" + var1, "-" + var1);
      }
   }

   public static void clearWhisperShortcuts() {
      sendWhisperCommand("/w --", "--");
   }

   @Subscribe
   public void onChatReceived(ChatReceivedEvent var1) {
      if (var1 != null && var1.messageType != 2 && var1.iChatComponent != null) {
         String var2 = ClientUtils.AOAtn(var1.iChatComponent.getUnformattedText());
         if (var2 != null && !var2.isEmpty()) {
            if (mfbD2(var2)) {
               var1.setCanceled(true);
               ClientUtils.logger.info("[CHAT] " + var2);
            }
         }
      }
   }

   private static void sendWhisperCommand(String var0, String var1) {
      Minecraft var2 = Minecraft.getMinecraft();
      if (var2 != null && var2.thePlayer != null && var0 != null && !var0.isEmpty()) {
         synchronized (pendingReplies) {
            pendingReplies.add(new WhisperShortcuts$1(var1, System.currentTimeMillis() + 5000L));
         }

         var2.thePlayer.sendChatMessage(var0);
      }
   }

   private static boolean mfbD2(String var0) {
      String var1 = var0.toLowerCase(Locale.ROOT);
      long var2 = System.currentTimeMillis();
      synchronized (pendingReplies) {
         Iterator var5 = pendingReplies.iterator();

         while (var5.hasNext()) {
            WhisperShortcuts$1 var6 = (WhisperShortcuts$1)var5.next();
            if (WhisperShortcuts$1.dmlTh(var6) < var2) {
               var5.remove();
            } else if (var1.contains(WhisperShortcuts$1.getTriggerText(var6))) {
               var5.remove();
               return true;
            }
         }

         return false;
      }
   }

   private static String WLzVqoy(String var0) {
      if (var0 == null) {
         return "";
      } else {
         String var1 = ClientUtils.AOAtn(var0).trim();
         int var2 = var1.indexOf(32);
         if (var2 >= 0) {
            var1 = var1.substring(0, var2);
         }

         if (var1.length() >= 3 && var1.length() <= 16) {
            for (int var3 = 0; var3 < var1.length(); var3++) {
               char var4 = var1.charAt(var3);
               if (!Character.isLetterOrDigit(var4) && var4 != '_') {
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
