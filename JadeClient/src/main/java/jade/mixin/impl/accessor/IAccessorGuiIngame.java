// Jade recovery: recovered class name: IAccessorGuiIngame; mixin target: net.minecraft.client.gui.GuiIngame
package jade.mixin.impl.accessor;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiIngame.class)
public interface IAccessorGuiIngame {
   @Accessor("remainingHighlightTicks")
   void setRemainingHighlightTicks(int var1);

   @Accessor("highlightingItemStack")
   void setHighlightingItemStack(ItemStack var1);

   @Accessor("displayedSubTitle")
   String getDisplayedSubTitle();

   @Accessor("displayedTitle")
   String getDisplayedTitle();

   @Accessor("recordPlaying")
   String getRecordPlaying();
}
