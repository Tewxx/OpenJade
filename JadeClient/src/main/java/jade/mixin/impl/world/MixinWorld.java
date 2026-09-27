// Jade recovery: recovered class name: MixinWorld; mixin target: net.minecraft.world.World; original class: jade.mixin.impl.world.M03e123d10fccb36d78c99817c23267b3
package jade.mixin.impl.world;

import jade.client.common.EventBus;
import jade.client.event.EntityJoinWorldEvent;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class MixinWorld {
   @Shadow
   @Final
   public boolean field_72995_K;

   @Inject(method = "spawnEntityInWorld", at = @At("RETURN"))
   private void onSpawnEntityInWorld(Entity entity, CallbackInfoReturnable<Boolean> cir) {
      World world = (World)(Object)this;
      if (world.isRemote) {
         EventBus.post(new EntityJoinWorldEvent(entity, world));
      }
   }
}
