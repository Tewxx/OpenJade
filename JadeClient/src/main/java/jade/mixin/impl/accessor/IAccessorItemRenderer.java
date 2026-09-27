// Jade recovery: recovered class name: IAccessorItemRenderer; mixin target: net.minecraft.client.renderer.ItemRenderer
package jade.mixin.impl.accessor;

import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderer.class)
public interface IAccessorItemRenderer {
   @Accessor("equippedProgress")
   float getEquippedProgress();
}
