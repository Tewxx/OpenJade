// Jade recovery: recovered class name: MixinRenderGlobal; mixin target: net.minecraft.client.renderer.RenderGlobal; original class: jade.mixin.impl.render.M40ceef5d9cfdb0d1ef885acbaf8718aa
package jade.mixin.impl.render;

import jade.client.common.EventBus;
import jade.client.event.DrawSelectionBoxEvent;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {
   @Inject(method = "drawSelectionBox", at = @At("HEAD"), cancellable = true)
   private void onDrawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int execute, float partialTicks, CallbackInfo ci) {
      if (EventBus.hasListeners(DrawSelectionBoxEvent.class)) {
         DrawSelectionBoxEvent event = new DrawSelectionBoxEvent(player, movingObjectPositionIn, partialTicks);
         EventBus.post(event);
         if (event.isCanceled()) {
            ci.cancel();
         }
      }
   }
}
