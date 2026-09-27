// Jade recovery: recovered class name: IAccessorMouseHelper; mixin target: net.minecraft.util.MouseHelper
package jade.mixin.impl.accessor;

import net.minecraft.util.MouseHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MouseHelper.class)
public interface IAccessorMouseHelper {
   @Accessor("deltaY")
   int getDeltaY();

   @Accessor("deltaX")
   int getDeltaX();
}
