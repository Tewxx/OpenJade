// Jade recovery: original class: jade.deps.eLz.rodtRZVgT
package jade.client.misc;

import jade.client.common.ChatUtils;
import net.minecraft.util.ChatComponentText;

public final class rodtRZVgT {
   private rodtRZVgT() {
   }

   public static ChatComponentText buildChatComponent(String var0, String... var1) {
      return new ChatComponentText(ChatUtils.resolveFlowText(ChatUtils.applyFlowGradient(var0, var1)));
   }
}
