// Jade recovery: recovered class name: MixinGuiIngame; mixin target: net.minecraft.client.gui.GuiIngame; original class: jade.mixin.impl.render.Mba215db76d33cd942e57325fdee1344d
package jade.mixin.impl.render;

import jade.client.hook.RenderTickMessagePump;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {
   @Inject(method = "renderGameOverlay", at = @At("RETURN"))
   private void jade$renderOverlay(float partialTicks, CallbackInfo ci) {
      RenderTickMessagePump.onRenderTickEnd(partialTicks);
   }
}
