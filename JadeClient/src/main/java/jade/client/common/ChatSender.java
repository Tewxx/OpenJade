// Jade recovery: original class: jade.deps.eLz.tGsXzqv
package jade.client.common;

import jade.client.module.render.Arraylist;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public final class ChatSender {
   private ChatSender() {
   }

   public static String[] getGradientColors() {
      List var0 = Arraylist.getGradientColors();
      if (var0.isEmpty()) {
         return new String[]{"#43E886", "#11804B"};
      } else {
         String[] var1 = new String[Math.max(2, var0.size())];

         for (int var2 = 0; var2 < var1.length; var2++) {
            var1[var2] = String.format("#%06X", (Integer)var0.get(Math.min(var2, var0.size() - 1)) & 16777215);
         }

         return var1;
      }
   }

   public static String formatGradientTitle(String var0) {
      return ChatUtils.applyFlowGradient("§l" + var0, getGradientColors());
   }

   public static void HARd(Minecraft var0, String var1, boolean var2) {
      if (Mmb4(var0)) {
         deliverMessage(var0, TextUtils.QJdK(var1), var2, false);
      }
   }

   public static void daPdo(Minecraft var0, String var1, String var2) {
      if (Mmb4(var0)) {
         deliverMessage(var0, formatGradientTitle(var1) + TextUtils.QJdK(var2), true, true);
      }
   }

   public static void sendDebugMessage(Minecraft var0, String var1) {
      if (Mmb4(var0)) {
         deliverMessage(var0, TextUtils.QJdK("&8[debug]&r ") + var1, false, false);
      }
   }

   private static boolean Mmb4(Minecraft var0) {
      return var0.thePlayer != null && var0.theWorld != null;
   }

   private static void deliverMessage(Minecraft var0, String var1, boolean var2, boolean var3) {
      ConditionalChatFormatter.emitFormattedMessage(var1, var2, var3, ExternalChatOverlay::WBGA, ChatUtils::hasFlowTag, ChatUtils::resolveFlowText, (recoveredArg0) -> ChatSender.fzvR(var0, (java.lang.String) recoveredArg0));
   }

   private static void fzvR(Minecraft var0, String var1) {
      var0.thePlayer.addChatMessage(new ChatComponentText(var1));
   }
}
