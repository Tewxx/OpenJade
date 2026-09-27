// Jade recovery: recovered class name: IAccessorEntityArrow; mixin target: net.minecraft.entity.projectile.EntityArrow
package jade.mixin.impl.accessor;

import net.minecraft.entity.projectile.EntityArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityArrow.class)
public interface IAccessorEntityArrow {
   @Accessor("inGround")
   boolean getInGround();
}
