// Jade recovery: recovered class name: MixinSoundHandler; mixin target: net.minecraft.client.audio.SoundHandler; original class: jade.mixin.impl.client.Mcd7fd5d92e72602e738dc2c60cbf8f8d
package jade.mixin.impl.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.CRC32;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundHandler.class)
public class MixinSoundHandler {
   @Unique
   private String jade$lastSoundFingerprint;
   @Unique
   private boolean jade$restartSoundSystem = true;

   @Inject(method = "onResourceManagerReload", at = @At("HEAD"))
   private void jade$detectSoundChanges(IResourceManager resourceManager, CallbackInfo ci) {
      String fingerprint = this.jade$soundFingerprint(resourceManager);
      this.jade$restartSoundSystem = fingerprint == null || this.jade$lastSoundFingerprint == null || !fingerprint.equals(this.jade$lastSoundFingerprint);
      this.jade$lastSoundFingerprint = fingerprint;
   }

   @Redirect(method = "onResourceManagerReload", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundManager;reloadSoundSystem()V"))
   private void jade$restartSoundSystemOnlyWhenNeeded(SoundManager soundManager) {
      if (this.jade$restartSoundSystem) {
         soundManager.reloadSoundSystem();
      }
   }

   @Unique
   private String jade$soundFingerprint(IResourceManager resourceManager) {
      CRC32 crc = new CRC32();
      int resourceCount = 0;

      try {
         List<String> domains = new ArrayList<>(resourceManager.getResourceDomains());
         Collections.sort(domains);
         byte[] buffer = new byte[4096];

         for (String domain : domains) {
            this.jade$update(crc, domain);

            List<IResource> resources;
            try {
               resources = resourceManager.getAllResources(new ResourceLocation(domain, "sounds.json"));
            } catch (IOException var17) {
               continue;
            }

            for (IResource resource : resources) {
               resourceCount++;
               this.jade$update(crc, resource.getResourcePackName());
               InputStream input = resource.getInputStream();

               int read;
               try {
                  while ((read = input.read(buffer)) != -1) {
                     crc.update(buffer, 0, read);
                  }
               } finally {
                  input.close();
               }
            }
         }

         return resourceCount + ":" + Long.toHexString(crc.getValue());
      } catch (Throwable var19) {
         return null;
      }
   }

   @Unique
   private void jade$update(CRC32 crc, String value) {
      byte[] bytes = (value == null ? "" : value).getBytes(StandardCharsets.UTF_8);
      crc.update(bytes, 0, bytes.length);
      crc.update(0);
   }
}
