// Jade recovery: original class: jade.deps.eLz.L5EcMpJh
package jade.client.command;

import jade.client.common.ClientUtils;
import jade.client.common.IMinecraft;

public final class PlayerNameClipboardCopier implements IMinecraft {
   public String copyPlayerName() {
      if (!ClientUtils.isInWorld()) {
         return null;
      } else {
         String var1 = mc.thePlayer.getName();
         ClipboardUtils.copyToClipboard(var1);
         return var1;
      }
   }
}
