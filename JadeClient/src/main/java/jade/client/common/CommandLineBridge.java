// Jade recovery: original class: jade.deps.eLz.wxLoj3JH11
package jade.client.common;

import java.util.List;
import net.minecraft.client.gui.FontRenderer;

public final class CommandLineBridge {
   public static List<String> consoleLines = FMMBeTEkVt.getChatLines();

   private CommandLineBridge() {
   }

   public static void executeCommand(String var0) {
      ChatCommandHandler.handleChatCommand(var0);
   }

   public static void appendConsoleLine(String var0, int var1) {
      FMMBeTEkVt.appendChatLine(var0, var1);
   }

   public static void renderConsole(FontRenderer var0, int var1, int var2, double var3) {
      ChatHudRenderer.qzhR8(var0, var1, var2, var3);
   }

   public static void pickConsoleBackgroundColor() {
      ChatHudRenderer.pickBackgroundColor();
   }

   public static void resetPingCheck() {
      PingChecker.cancelPingCheck(false);
   }
}
