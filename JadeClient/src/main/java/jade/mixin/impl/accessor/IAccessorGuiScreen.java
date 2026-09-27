// Jade recovery: recovered class name: IAccessorGuiScreen; mixin target: net.minecraft.client.gui.GuiScreen
package jade.mixin.impl.accessor;

import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiScreen.class)
public interface IAccessorGuiScreen {
   @Invoker("mouseClicked")
   void callMouseClicked(int var1, int var2, int var3);
}
