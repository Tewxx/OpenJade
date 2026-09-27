// Jade recovery: recovered class name: IAccessorEntity; mixin target: net.minecraft.entity.Entity
package jade.mixin.impl.accessor;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface IAccessorEntity {
   @Accessor("isInWeb")
   boolean getIsInWeb();

   @Accessor("nextStepDistance")
   int getNextStepDistance();

   @Accessor("fire")
   int getFire();
}
