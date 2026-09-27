// Jade recovery: original class: jade.deps.eLz.MUnWJsTR$1
package jade.client.common;

public final class ExternalSkinTextures$1 {
   private final int NvgB7 = ExternalSkinTextures.allocateTextureId();
   private final int glTextureId;
   private final byte[] skinPixelData;

   ExternalSkinTextures$1(int var1, byte[] var2) {
      this.glTextureId = var1;
      this.skinPixelData = var2;
   }

   public static int getGlTextureId(ExternalSkinTextures$1 var0) {
      return var0.glTextureId;
   }

   public static int rhYo(ExternalSkinTextures$1 var0) {
      return var0.NvgB7;
   }

   public static byte[] HJLiu(ExternalSkinTextures$1 var0) {
      return var0.skinPixelData;
   }
}
