// Jade recovery: recovered class name: IAccessorS30PacketWindowItems; mixin target: net.minecraft.network.play.server.S30PacketWindowItems
package jade.mixin.impl.accessor;

import net.minecraft.network.play.server.S30PacketWindowItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(S30PacketWindowItems.class)
public interface IAccessorS30PacketWindowItems {
   @Accessor("windowId")
   int getWindowId();
}
