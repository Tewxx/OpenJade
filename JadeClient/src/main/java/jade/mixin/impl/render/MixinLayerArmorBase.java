// Jade recovery: recovered class name: MixinLayerArmorBase; mixin target: net.minecraft.client.renderer.entity.layers.LayerArmorBase; original class: jade.mixin.impl.render.M8b9648e81aaae70260a56935e317eec8
package jade.mixin.impl.render;

import jade.client.common.FakePlayerRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LayerArmorBase.class)
public class MixinLayerArmorBase {
   @Redirect(method = "renderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"), require = 0)
   private void jade$applyGhostArmorOpacity(float red, float green, float blue, float alpha) {
      if (FakePlayerRenderer.isRendering()) {
         GlStateManager.color(red, green, blue, alpha * FakePlayerRenderer.xu60());
      } else {
         GlStateManager.color(red, green, blue, alpha);
      }
   }
}
