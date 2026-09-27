// Jade recovery: recovered class name: MixinRenderManager; mixin target: net.minecraft.client.renderer.entity.RenderManager; original class: jade.mixin.impl.render.M9c3e500167dd9edd97173499e7d86da7
package jade.mixin.impl.render;

import jade.client.hook.FreeLookHook;
import jade.client.hook.PlayerPitchOverride;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderManager.class)
public class MixinRenderManager {
   @Shadow
   private float field_78732_j;
   @Shadow
   private float field_78735_i;
   @Unique
   private final PlayerPitchOverride jade$pitchOverride = new PlayerPitchOverride();

   @Inject(method = "renderEntityStatic", at = @At("HEAD"))
   public void renderEntityStaticPre(Entity entity, float n, boolean b, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      this.jade$pitchOverride.applySilentPitch(entity);
   }

   @Inject(method = "renderEntityStatic", at = @At("RETURN"))
   public void renderEntityStaticPost(Entity entity, float n, boolean b, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
      this.jade$pitchOverride.restoreOriginalPitch(entity);
   }

   @Inject(method = "cacheActiveRenderInfo", at = @At("RETURN"))
   private void jade$applyFreelookViewAngles(CallbackInfo ci) {
      if (FreeLookHook.QNLy()) {
         this.field_78732_j = FreeLookHook.getFreeLookPitch();
         this.field_78735_i = FreeLookHook.wusrdWx();
      }
   }
}
