// Jade recovery: recovered class name: IAccessorItemFood; mixin target: net.minecraft.item.ItemFood
package jade.mixin.impl.accessor;

import net.minecraft.item.ItemFood;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemFood.class)
public interface IAccessorItemFood {
   @Accessor("alwaysEdible")
   boolean getAlwaysEdible();
}
