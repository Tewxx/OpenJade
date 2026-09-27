// Jade recovery: recovered class name: IAccessorGuiNewChat; mixin target: net.minecraft.client.gui.GuiNewChat
package jade.mixin.impl.accessor;

import java.util.List;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiNewChat.class)
public interface IAccessorGuiNewChat {
   @Accessor("drawnChatLines")
   List<ChatLine> getDrawnChatLines();

   @Accessor("scrollPos")
   int getScrollPos();
}
