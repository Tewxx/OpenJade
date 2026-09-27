// Jade recovery: recovered class name: MixinEntityRenderer; mixin target: net.minecraft.client.renderer.EntityRenderer; original class: jade.mixin.impl.render.Ma8fc36cfdde3c345129c3a0ef1ca0585
package jade.mixin.impl.render;

import jade.client.common.EventBus;
import jade.client.event.MouseOverEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.hook.FreeLookHook;
import jade.client.hook.RenderTickMessagePump;
import jade.client.hook.MouseOverRotationBridge;
import jade.client.hook.EntityRotationOverride;
import jade.client.hook.EntityRendererHooks;
import jade.client.module.player.Freecam;
import jade.client.module.render.FreeLook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
   @Unique
   private final EntityRotationOverride jade$cameraSession = new EntityRotationOverride();

   @Inject(method = "orientCamera", at = @At("HEAD"))
   private void jade$beginFreelookCamera(float partialTicks, CallbackInfo ci) {
      this.jade$cameraSession.nZugr();
      if (FreeLookHook.QNLy()) {
         this.jade$cameraSession.applyRotation(Minecraft.getMinecraft().getRenderViewEntity(), FreeLookHook.wusrdWx(), FreeLookHook.getFreeLookPitch());
      }
   }

   @Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setAngles(FF)V"), require = 0)
   private void jade$directMouseLookToFreecam(EntityPlayerSP player, float yawDelta, float pitchDelta) {
      if (!Freecam.applyCameraAngles(yawDelta, pitchDelta)) {
         player.setAngles(yawDelta, pitchDelta);
      }
   }

   @Inject(method = "orientCamera", at = @At("RETURN"))
   private void jade$endFreelookCamera(float partialTicks, CallbackInfo ci) {
      this.jade$cameraSession.restoreRotation();
   }

   @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D"))
   public double injectNoCameraClip(Vec3 raytrace, Vec3 original) {
      return EntityRendererHooks.getCameraRayTraceDistance(raytrace, original);
   }

   @Redirect(method = "updateCameraAndRender", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;inGameHasFocus:Z"))
   private boolean freelookOverrideMouse(Minecraft mc) {
      return FreeLook.updateFreeLookCamera(mc);
   }

   @Redirect(method = "setupFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z"))
   private boolean redirectSetupFog(EntityLivingBase entity, Potion potion) {
      return EntityRendererHooks.SHst(entity, potion, true);
   }

   @Redirect(
      method = "updateFogColor",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z")
   )
   private boolean redirectFogColor(EntityLivingBase entity, Potion potion) {
      return EntityRendererHooks.SHst(entity, potion, true);
   }

   @Redirect(
      method = "setupCameraTransform",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isPotionActive(Lnet/minecraft/potion/Potion;)Z")
   )
   private boolean redirectSetupCameraTransform(EntityPlayerSP entity, Potion potion) {
      return EntityRendererHooks.SHst(entity, potion, false);
   }

   @Inject(
      method = "updateCameraAndRender",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V", shift = At.Shift.AFTER),
      require = 0
   )
   private void jade$afterGameOverlay(float partialTicks, long nanoTime, CallbackInfo ci) {
      RenderTickMessagePump.onRenderTickEnd(partialTicks);
   }

   @Inject(
      method = "renderWorld",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;getMouseOver(F)V", shift = At.Shift.AFTER)
   )
   private void onRenderWorld(float partialTicks, long finishTimeNano, CallbackInfo ci) {
      if (EventBus.hasListeners(MouseOverEvent.class)) {
         EventBus.post(new MouseOverEvent());
      }
   }

   @Inject(
      method = "renderWorldPass",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;clear(I)V", ordinal = 1, shift = At.Shift.BEFORE)
   )
   private void onRenderWorldLast(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
      if (EventBus.hasListeners(RenderWorldLastEvent.class)) {
         EventBus.post(new RenderWorldLastEvent(partialTicks));
      }
   }

   @Inject(method = "getMouseOver", at = @At("HEAD"))
   private void onGetMouseOverHead(float partialTicks, CallbackInfo ci) {
      MouseOverRotationBridge.beginSilentRotation();
   }

   @Inject(method = "getMouseOver", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endSection()V", shift = At.Shift.BEFORE))
   private void onGetMouseOverBeforeEndSection(float partialTicks, CallbackInfo ci) {
      MouseOverRotationBridge.updateRotationTargets(partialTicks);
   }

   @Inject(method = "getMouseOver", at = @At("RETURN"))
   private void onGetMouseOverReturn(float partialTicks, CallbackInfo ci) {
      MouseOverRotationBridge.endSilentRotation();
   }
}
