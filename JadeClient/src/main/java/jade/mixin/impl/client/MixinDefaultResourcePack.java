// Jade recovery: recovered class name: MixinDefaultResourcePack; mixin target: net.minecraft.client.resources.DefaultResourcePack; original class: jade.mixin.impl.client.M78e9041f793f232b0313e5e2937d1bbd
package jade.mixin.impl.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultResourcePack.class)
public class MixinDefaultResourcePack {
   @Unique
   private volatile Map<ResourceLocation, Boolean> jade$resourceExistence;
   @Unique
   private volatile Map<ResourceLocation, byte[]> jade$smallResourceContents;
   @Unique
   private volatile Set<ResourceLocation> jade$missingClasspathResources;

   @Inject(method = "getResourceStream", at = @At("HEAD"), cancellable = true)
   private void jade$skipKnownMissingResource(ResourceLocation location, CallbackInfoReturnable<InputStream> cir) {
      this.jade$ensureCaches();
      if (jade$isGeneratedSkin(location) || this.jade$missingClasspathResources.contains(location)) {
         cir.setReturnValue(null);
      }
   }

   @Inject(method = "getResourceStream", at = @At("RETURN"))
   private void jade$rememberMissingClasspathResource(ResourceLocation location, CallbackInfoReturnable<InputStream> cir) {
      this.jade$ensureCaches();
      if (location != null && cir.getReturnValue() == null) {
         this.jade$missingClasspathResources.add(location);
      }
   }

   @Inject(method = "getInputStream", at = @At("HEAD"), cancellable = true)
   private void jade$useCachedSmallResource(ResourceLocation location, CallbackInfoReturnable<InputStream> cir) {
      this.jade$ensureCaches();
      if (location != null) {
         byte[] cached = this.jade$smallResourceContents.get(location);
         if (cached != null) {
            cir.setReturnValue(new ByteArrayInputStream(cached));
         }
      }
   }

   @Inject(method = "getInputStream", at = @At("RETURN"), cancellable = true)
   private void jade$cacheSmallResource(ResourceLocation location, CallbackInfoReturnable<InputStream> cir) throws IOException {
      this.jade$ensureCaches();
      InputStream stream = cir.getReturnValue();
      if (jade$isSmallImmutableResource(location) && stream != null) {
         byte[] contents = jade$readAndClose(stream);
         byte[] existing = this.jade$smallResourceContents.putIfAbsent(location, contents);
         cir.setReturnValue(new ByteArrayInputStream(existing == null ? contents : existing));
      }
   }

   @Inject(method = "resourceExists", at = @At("HEAD"), cancellable = true)
   private void jade$useCachedResourceExistence(ResourceLocation location, CallbackInfoReturnable<Boolean> cir) {
      this.jade$ensureCaches();
      if (location != null) {
         Boolean cached = this.jade$resourceExistence.get(location);
         if (cached != null) {
            cir.setReturnValue(cached);
         }
      }
   }

   @Inject(method = "resourceExists", at = @At("RETURN"))
   private void jade$rememberResourceExistence(ResourceLocation location, CallbackInfoReturnable<Boolean> cir) {
      this.jade$ensureCaches();
      if (location != null) {
         this.jade$resourceExistence.put(location, cir.getReturnValue());
      }
   }

   @Unique
   private static boolean jade$isGeneratedSkin(ResourceLocation location) {
      return location != null && "minecraft".equals(location.getResourceDomain()) && location.getResourcePath().startsWith("skins/");
   }

   @Unique
   private void jade$ensureCaches() {
      if (this.jade$resourceExistence == null || this.jade$smallResourceContents == null || this.jade$missingClasspathResources == null) {
         synchronized (this) {
            if (this.jade$resourceExistence == null) {
               this.jade$resourceExistence = new ConcurrentHashMap<>();
            }

            if (this.jade$smallResourceContents == null) {
               this.jade$smallResourceContents = new ConcurrentHashMap<>();
            }

            if (this.jade$missingClasspathResources == null) {
               this.jade$missingClasspathResources = Collections.newSetFromMap(new ConcurrentHashMap<>());
            }
         }
      }
   }

   @Unique
   private static boolean jade$isSmallImmutableResource(ResourceLocation location) {
      return location != null && location.getResourcePath().endsWith(".json");
   }

   @Unique
   private static byte[] jade$readAndClose(InputStream stream) throws IOException {
      byte[] var4;
      try {
         ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
         byte[] buffer = new byte[4096];

         int read;
         while ((read = stream.read(buffer)) != -1) {
            output.write(buffer, 0, read);
         }

         var4 = output.toByteArray();
      } finally {
         stream.close();
      }

      return var4;
   }
}
