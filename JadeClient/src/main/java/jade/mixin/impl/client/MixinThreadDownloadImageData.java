// Jade recovery: recovered class name: MixinThreadDownloadImageData; mixin target: net.minecraft.client.renderer.ThreadDownloadImageData; original class: jade.mixin.impl.client.M99312c2d5ef6f619e67171b258507938
package jade.mixin.impl.client;

import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.IResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ThreadDownloadImageData.class)
public class MixinThreadDownloadImageData {
   @Redirect(
      method = "loadTexture",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/texture/SimpleTexture;loadTexture(Lnet/minecraft/client/resources/IResourceManager;)V"
      )
   )
   private void jade$skipGeneratedSkinPackLookup(SimpleTexture texture, IResourceManager resourceManager) {
   }
}
