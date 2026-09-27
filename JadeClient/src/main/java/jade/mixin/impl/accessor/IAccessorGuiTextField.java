// Jade recovery: recovered class name: IAccessorGuiTextField; mixin target: net.minecraft.client.gui.GuiTextField
package jade.mixin.impl.accessor;

import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiTextField.class)
public interface IAccessorGuiTextField {
   @Accessor("width")
   int getWidth();

   @Accessor("height")
   int getHeight();

   @Accessor("enableBackgroundDrawing")
   boolean isEnableBackgroundDrawing();

   @Accessor("lineScrollOffset")
   int getLineScrollOffset();

   @Accessor("cursorPosition")
   int getCursorPosition();

   @Accessor("selectionEnd")
   int getSelectionEnd();
}
