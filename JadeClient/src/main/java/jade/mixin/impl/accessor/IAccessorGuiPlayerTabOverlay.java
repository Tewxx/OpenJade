// Jade recovery: recovered class name: IAccessorGuiPlayerTabOverlay; mixin target: net.minecraft.client.gui.GuiPlayerTabOverlay
package jade.mixin.impl.accessor;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiPlayerTabOverlay.class)
public interface IAccessorGuiPlayerTabOverlay {
   @Accessor("footer")
   IChatComponent getFooter();

   @Accessor("header")
   IChatComponent getHeader();
}
