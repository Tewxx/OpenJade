// Jade recovery: original class: jade.deps.eLz.MUnWJsTR
package jade.client.common;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class ExternalSkinTextures {
   private static final Map<String, ExternalSkinTextures$1> TEXTURES_BY_LOCATION = new LinkedHashMap<>(32, 0.75F, true);
   private static final Map<Integer, ExternalSkinTextures$1> TEXTURES_BY_ID = new HashMap<>();
   private static int nextTextureId = 1610612736;

   private ExternalSkinTextures() {
   }

   public static void drawPlayerHead(ExternalRenderBuffer var0, AbstractClientPlayer var1, double var2, double var4, float var6, float var7) {
      if (var0 != null && var1 != null && !(var7 <= 0.0F)) {
         ResourceLocation var8 = var1.getLocationSkin();
         if (var8 != null) {
            Minecraft var9 = Minecraft.getMinecraft();
            ITextureObject var10 = var9.getTextureManager().getTexture(var8);
            if (var10 == null) {
               int var11 = GL11.glGetInteger(32873);

               try {
                  var9.getTextureManager().bindTexture(var8);
                  var10 = var9.getTextureManager().getTexture(var8);
               } finally {
                  GL11.glBindTexture(3553, var11);
               }
            }

            if (var10 != null) {
               int var17 = var10.getGlTextureId();
               String var12 = var8.toString();
               ExternalSkinTextures$1 var13 = TEXTURES_BY_LOCATION.get(var12);
               if (var13 == null || ExternalSkinTextures$1.getGlTextureId(var13) != var17) {
                  byte[] var14 = FxrG(var17);
                  if (var14 == null) {
                     return;
                  }

                  if (var13 != null) {
                     TEXTURES_BY_ID.remove(ExternalSkinTextures$1.rhYo(var13));
                  }

                  var13 = new ExternalSkinTextures$1(var17, var14);
                  cacheSkinTexture(var12, var13);
               }

               var0.drawTextureQuad(ExternalSkinTextures$1.rhYo(var13), var2, var4, var6, Math.max(0.0F, Math.min(1.0F, var7)), 0.0F, true);
            }
         }
      }
   }

   private static void cacheSkinTexture(String var0, ExternalSkinTextures$1 var1) {
      if (!TEXTURES_BY_LOCATION.containsKey(var0) && TEXTURES_BY_LOCATION.size() >= 64) {
         Iterator var2 = TEXTURES_BY_LOCATION.values().iterator();
         ExternalSkinTextures$1 var3 = (ExternalSkinTextures$1)var2.next();
         var2.remove();
         TEXTURES_BY_ID.remove(ExternalSkinTextures$1.rhYo(var3));
      }

      TEXTURES_BY_LOCATION.put(var0, var1);
      TEXTURES_BY_ID.put(ExternalSkinTextures$1.rhYo(var1), var1);
   }

   private static byte[] FxrG(int var0) {
      int var1 = GL11.glGetInteger(32873);

      byte[] var15;
      try {
         GL11.glBindTexture(3553, var0);
         int var2 = GL11.glGetTexLevelParameteri(3553, 0, 4096);
         int var3 = GL11.glGetTexLevelParameteri(3553, 0, 4097);
         if (var2 < 48 || var3 < 16 || var2 > 2048 || var3 > 2048) {
            return null;
         }

         ByteBuffer var4 = BufferUtils.createByteBuffer(var2 * var3 * 4);
         GL11.glGetTexImage(3553, 0, 32993, 5121, var4);
         byte[] var5 = new byte[256];

         for (int var6 = 0; var6 < 8; var6++) {
            for (int var7 = 0; var7 < 8; var7++) {
               int var8 = ((8 + var6) * var2 + 8 + var7) * 4;
               int var9 = ((8 + var6) * var2 + 40 + var7) * 4;
               int var10 = (var6 * 8 + var7) * 4;
               blendOverlayPixel(var4, var8, var9, var5, var10);
            }
         }

         var15 = var5;
      } finally {
         GL11.glBindTexture(3553, var1);
      }

      return var15;
   }

   private static void blendOverlayPixel(ByteBuffer var0, int var1, int var2, byte[] var3, int var4) {
      int var5 = var0.get(var1 + 3) & 255;
      int var6 = var0.get(var2 + 3) & 255;
      int var7 = 255 - var6;
      int var8 = var6 + var5 * var7 / 255;

      for (int var9 = 0; var9 < 3; var9++) {
         int var10 = var0.get(var1 + var9) & 255;
         int var11 = var0.get(var2 + var9) & 255;
         var3[var4 + var9] = (byte)((var11 * var6 + var10 * var5 * var7 / 255) / 255);
      }

      var3[var4 + 3] = (byte)var8;
   }

   public static void uploadPendingTextures(ExternalRenderBuffer var0, ExternalOverlaySession var1) {
      for (int var2 = 0; var2 < var0.referencedTextureCount; var2++) {
         ExternalSkinTextures$1 var3 = TEXTURES_BY_ID.get(var0.referencedTextureIds[var2]);
         if (var3 != null && !var1.hasTexture(ExternalSkinTextures$1.rhYo(var3))) {
            var0.qqPw(ExternalSkinTextures$1.rhYo(var3), 8, 8, ExternalSkinTextures$1.HJLiu(var3));
         }
      }
   }

   public static void clearCaches() {
      TEXTURES_BY_LOCATION.clear();
      TEXTURES_BY_ID.clear();
   }

   public static int allocateTextureId() {
      return nextTextureId++;
   }
}
