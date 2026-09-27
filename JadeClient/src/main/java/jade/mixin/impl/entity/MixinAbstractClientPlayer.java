// Jade recovery: recovered class name: MixinAbstractClientPlayer; mixin target: net.minecraft.client.entity.AbstractClientPlayer; original class: jade.mixin.impl.entity.M928e70fa8ed39a820336d9335ac82890
package jade.mixin.impl.entity;

import jade.client.module.render.Cape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public class MixinAbstractClientPlayer {
   @Inject(method = "getLocationCape", at = @At("RETURN"), cancellable = true)
   private void jade$overrideCapeTexture(CallbackInfoReturnable<ResourceLocation> cir) {
      if ((Object)this == Minecraft.getMinecraft().thePlayer && Cape.isCapeEnabled()) {
         ResourceLocation tex = Cape.FOsFcam();
         if (tex != null) {
            cir.setReturnValue(tex);
         }
      }
   }
}
