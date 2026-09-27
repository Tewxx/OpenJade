// Jade recovery: recovered class name: IAccessorGuiScreenBook; mixin target: net.minecraft.client.gui.GuiScreenBook
package jade.mixin.impl.accessor;

import java.util.List;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiScreenBook.class)
public interface IAccessorGuiScreenBook {
   @Accessor("field_175386_A")
   List<IChatComponent> getBookContents();
}
