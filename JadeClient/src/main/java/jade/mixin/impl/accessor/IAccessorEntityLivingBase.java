// Jade recovery: recovered class name: IAccessorEntityLivingBase; mixin target: net.minecraft.entity.EntityLivingBase
package jade.mixin.impl.accessor;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityLivingBase.class)
public interface IAccessorEntityLivingBase {
   @Accessor("jumpTicks")
   int getJumpTicks();

   @Accessor("jumpTicks")
   void setJumpTicks(int var1);
}
