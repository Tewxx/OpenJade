// Jade recovery: recovered class name: IAccessorEntityPlayer; mixin target: net.minecraft.entity.player.EntityPlayer
package jade.mixin.impl.accessor;

import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityPlayer.class)
public interface IAccessorEntityPlayer {
   @Accessor("itemInUseCount")
   int getItemInUseCountField();

   @Accessor("itemInUseCount")
   void setItemInUseCount(int var1);
}
