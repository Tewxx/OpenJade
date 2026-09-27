// Jade recovery: recovered class name: MixinRendererLivingEntity; mixin target: net.minecraft.client.renderer.entity.RendererLivingEntity; original class: jade.mixin.impl.render.M2536f6c06571014fc7c5c5242a518bb4
package jade.mixin.impl.render;

import jade.client.Jade;
import jade.client.common.EventBus;
import jade.client.common.FakePlayerRenderer;
import jade.client.event.RenderLivingPostEvent;
import jade.client.event.RenderLivingPreEvent;
import jade.client.module.render.AntiInvis;
import jade.client.module.render.ESP;
import jade.client.module.render.Nametags;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {
   protected MixinRendererLivingEntity(RenderManager renderManager) {
      super(renderManager);
   }

   @Redirect(method = "renderModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isInvisible()Z"))
   private boolean jade$revealInvisiblePlayer(EntityLivingBase entity) {
      boolean invisible = entity.isInvisible();
      AntiInvis antiInvis = Jade.getModuleManager().getModule(AntiInvis.class);
      return invisible && antiInvis != null && antiInvis.isForceVisibleTarget(entity) ? false : invisible;
   }

   @Redirect(
      method = "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isInvisibleToPlayer(Lnet/minecraft/entity/player/EntityPlayer;)Z")
   )
   private boolean jade$showRevealedPlayerName(EntityLivingBase entity, EntityPlayer viewer) {
      AntiInvis antiInvis = Jade.getModuleManager().getModule(AntiInvis.class);
      return antiInvis != null && antiInvis.isForceVisibleTarget(entity) ? false : entity.isInvisibleToPlayer(viewer);
   }

   @Inject(method = "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z", at = @At("HEAD"), cancellable = true)
   private void suppressNameDuringOutlinePass(T entity, CallbackInfoReturnable<Boolean> cir) {
      if (ESP.overlayPassActive) {
         cir.setReturnValue(false);
      } else if (FakePlayerRenderer.isRendering()) {
         cir.setReturnValue(false);
      } else {
         if (entity instanceof EntityPlayer && this.jade$shouldHideVanillaNametag((EntityPlayer)entity)) {
            cir.setReturnValue(false);
         }
      }
   }

   @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At("HEAD"))
   private void renderLiving$pre(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
      if (!FakePlayerRenderer.isRendering()) {
         if (EventBus.hasListeners(RenderLivingPreEvent.class)) {
            EventBus.post(new RenderLivingPreEvent(entity, x, y, z, partialTicks));
         }
      }
   }

   @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At("RETURN"))
   private void renderLiving$post(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
      if (!FakePlayerRenderer.isRendering()) {
         if (EventBus.hasListeners(RenderLivingPostEvent.class)) {
            EventBus.post(new RenderLivingPostEvent(entity, x, y, z, partialTicks));
         }
      }
   }

   @Inject(method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V", at = @At("HEAD"), cancellable = true)
   private void nametags$hideVanillaName(T entity, double x, double y, double z, CallbackInfo ci) {
      if (FakePlayerRenderer.isRendering()) {
         ci.cancel();
      } else {
         if (entity instanceof EntityPlayer && this.jade$shouldHideVanillaNametag((EntityPlayer)entity)) {
            ci.cancel();
         }
      }
   }

   @Unique
   private boolean jade$shouldHideVanillaNametag(EntityPlayer player) {
      Nametags nametags = Jade.getModuleManager().getModule(Nametags.class);
      return nametags != null && nametags.shouldHideVanillaNametag(player);
   }
}
