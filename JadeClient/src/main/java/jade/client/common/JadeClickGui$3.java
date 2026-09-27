// Jade recovery: original class: jade.deps.eLz.qQ8P7JMy3$3
package jade.client.common;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public final class JadeClickGui$3 {
   private final DynamicTexture dynamicTexture;
   private final ResourceLocation resourceLocation;
   private int GRWs;
   private boolean textureUploaded;

   JadeClickGui$3(DynamicTexture var1, ResourceLocation var2) {
      this.dynamicTexture = var1;
      this.resourceLocation = var2;
   }

   public static boolean isTextureUploaded(JadeClickGui$3 var0) {
      return var0.textureUploaded;
   }

   public static int getCachedColor(JadeClickGui$3 var0) {
      return var0.GRWs;
   }

   public static DynamicTexture GNUb(JadeClickGui$3 var0) {
      return var0.dynamicTexture;
   }

   public static int setCachedColor(JadeClickGui$3 var0, int var1) {
      return var0.GRWs = var1;
   }

   public static boolean setTextureUploaded(JadeClickGui$3 var0, boolean var1) {
      return var0.textureUploaded = var1;
   }

   public static ResourceLocation getResourceLocation(JadeClickGui$3 var0) {
      return var0.resourceLocation;
   }
}
