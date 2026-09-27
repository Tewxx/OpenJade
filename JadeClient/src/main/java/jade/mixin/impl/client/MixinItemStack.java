// Jade recovery: recovered class name: MixinItemStack; mixin target: net.minecraft.item.ItemStack; original class: jade.mixin.impl.client.M2a1ce5c9e0a3367ef2a233f4029987a6
package jade.mixin.impl.client;

import jade.client.hook.GlintRenderPolicy;
import jade.client.module.render.ESP;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
   @Inject(method = "hasEffect", at = @At("HEAD"), cancellable = true)
   private void jade$applyOutlineGlintPolicy(CallbackInfoReturnable<Boolean> result) {
      if (GlintRenderPolicy.shouldSuppressGlint(ESP.overlayPassActive)) {
         result.setReturnValue(Boolean.FALSE);
      }
   }
}
