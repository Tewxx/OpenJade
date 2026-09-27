// Jade recovery: original class: jade.deps.eLz.FMMBeTEkVt
package jade.client.common;

import java.util.ArrayList;
import java.util.List;

public final class FMMBeTEkVt {
   private static final List<String> chatLines = new ArrayList<>();

   private FMMBeTEkVt() {
   }

   public static List<String> getChatLines() {
      return chatLines;
   }

   public static void appendChatLine(String var0, int var1) {
      if (var1 == 1 || var1 == 2) {
         chatLines.add("");
      }

      chatLines.add(var0);
      if (var1 == 2 || var1 == 3) {
         chatLines.add("");
      }
   }

   public static void clearChatLines() {
      chatLines.clear();
   }
}
