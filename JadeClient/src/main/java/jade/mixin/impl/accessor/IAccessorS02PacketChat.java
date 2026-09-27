// Jade recovery: recovered class name: IAccessorS02PacketChat; mixin target: net.minecraft.network.play.server.S02PacketChat
package jade.mixin.impl.accessor;

import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(S02PacketChat.class)
public interface IAccessorS02PacketChat {
   @Accessor("chatComponent")
   IChatComponent getChatComponent();

   @Accessor("chatComponent")
   void setChatComponent(IChatComponent var1);
}
