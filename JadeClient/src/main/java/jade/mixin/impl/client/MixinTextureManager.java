// Jade recovery: recovered class name: MixinTextureManager; mixin target: net.minecraft.client.renderer.texture.TextureManager; original class: jade.mixin.impl.client.M79f793f81b5f24517eca4a0c7dc99d78
package jade.mixin.impl.client;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextureManager.class)
public class MixinTextureManager {
   @Shadow
   private Map<ResourceLocation, ITextureObject> field_110585_a;
   @Unique
   private Set<ResourceLocation> jade$pendingSimpleTextureReloads;

   @Redirect(
      method = "onResourceManagerReload",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/texture/TextureManager;loadTexture(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/renderer/texture/ITextureObject;)Z"
      )
   )
   private boolean jade$preserveDownloadedTexture(TextureManager textureManager, ResourceLocation location, ITextureObject texture) {
      if (texture instanceof ThreadDownloadImageData || texture instanceof DynamicTexture) {
         return true;
      } else if (texture instanceof SimpleTexture) {
         if (this.jade$pendingSimpleTextureReloads == null) {
            this.jade$pendingSimpleTextureReloads = new HashSet<>();
         }

         this.jade$pendingSimpleTextureReloads.add(location);
         return true;
      } else {
         return textureManager.loadTexture(location, texture);
      }
   }

   @Inject(method = "bindTexture", at = @At("HEAD"))
   private void jade$reloadSimpleTextureOnDemand(ResourceLocation location, CallbackInfo ci) {
      if (this.jade$pendingSimpleTextureReloads != null && this.jade$pendingSimpleTextureReloads.remove(location)) {
         ITextureObject texture = this.field_110585_a.get(location);
         if (texture instanceof SimpleTexture && !(texture instanceof ThreadDownloadImageData)) {
            ((TextureManager)(Object)this).loadTexture(location, texture);
         }
      }
   }

   @Inject(method = "loadTexture", at = @At("HEAD"))
   private void jade$cancelPendingReload(ResourceLocation location, ITextureObject texture, CallbackInfoReturnable<Boolean> cir) {
      if (this.jade$pendingSimpleTextureReloads != null) {
         this.jade$pendingSimpleTextureReloads.remove(location);
      }
   }
}
