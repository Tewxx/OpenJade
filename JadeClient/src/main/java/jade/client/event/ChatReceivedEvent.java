// Jade recovery: original class: jade.deps.eLz.cSxG1O
package jade.client.event;

import net.minecraft.util.IChatComponent;

public class ChatReceivedEvent extends Event {
   public final byte messageType;
   public final IChatComponent iChatComponent;

   public ChatReceivedEvent(byte var1, IChatComponent var2) {
      this.messageType = var1;
      this.iChatComponent = var2;
   }
}
