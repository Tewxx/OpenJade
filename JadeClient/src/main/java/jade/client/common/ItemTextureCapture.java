// Jade recovery: original class: jade.deps.eLz.YqEfcojyL
package jade.client.common;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.item.ItemStack;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public final class ItemTextureCapture {
   private ItemTextureCapture() {
   }

   public static byte[] UAlhg(ItemStack var0, TextureAtlasSprite var1) {
      if (var0 == null) {
         return PrFz(var1);
      } else {
         Minecraft var2 = Minecraft.getMinecraft();
         if (!OpenGlHelper.isFramebufferEnabled()) {
            return PrFz(var2.getRenderItem().getItemModelMesher().getItemModel(var0).getParticleTexture());
         } else {
            byte[] var26;
            try (GlStateSnapshot var3 = new GlStateSnapshot()) {
               GL20.glUseProgram(0);
               GL11.glDisable(3089);
               GL11.glDisable(2960);
               Framebuffer var5 = new Framebuffer(64, 64, true);

               try {
                  var5.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
                  var5.framebufferClear();
                  var5.bindFramebuffer(true);
                  GL11.glDisable(3089);
                  GL11.glDisable(2960);
                  GL11.glColorMask(true, true, true, true);
                  GL11.glMatrixMode(5889);
                  GL11.glLoadIdentity();
                  GL11.glOrtho(0.0, 16.0, 16.0, 0.0, -1000.0, 1000.0);
                  GL11.glMatrixMode(5888);
                  GL11.glLoadIdentity();
                  GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
                  GlStateManager.enableTexture2D();
                  GlStateManager.enableDepth();
                  GlStateManager.depthMask(true);
                  GlStateManager.depthFunc(515);
                  GlStateManager.enableAlpha();
                  GlStateManager.alphaFunc(516, 0.01F);
                  GlStateManager.enableBlend();
                  GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                  RenderHelper.enableGUIStandardItemLighting();
                  var2.getRenderItem().renderItemIntoGUI(var0, 0, 0);
                  ByteBuffer var6 = BufferUtils.createByteBuffer(16384);
                  GL11.glReadPixels(0, 0, 64, 64, 32993, 5121, var6);
                  byte[] var7 = new byte[16384];

                  for (int var8 = 0; var8 < 64; var8++) {
                     ((Buffer)var6).position((63 - var8) * 64 * 4);
                     var6.get(var7, var8 * 64 * 4, 256);
                  }

                  var26 = var7;
               } finally {
                  var5.deleteFramebuffer();
               }
            }

            return var26;
         }
      }
   }

   private static byte[] PrFz(TextureAtlasSprite var0) {
      if (var0 != null && var0.getFrameCount() != 0) {
         int[][] var1 = var0.getFrameTextureData(0);
         int var2 = var0.getIconWidth();
         int var3 = var0.getIconHeight();
         if (var1 != null && var1.length != 0 && var1[0] != null && var2 > 0 && var3 > 0 && var1[0].length >= var2 * var3) {
            byte[] var4 = new byte[16384];

            for (int var5 = 0; var5 < 64; var5++) {
               for (int var6 = 0; var6 < 64; var6++) {
                  int var7 = var1[0][var5 * var3 / 64 * var2 + var6 * var2 / 64];
                  int var8 = var7 >>> 24;
                  int var9 = (var5 * 64 + var6) * 4;
                  var4[var9] = (byte)((var7 & 0xFF) * var8 / 255);
                  var4[var9 + 1] = (byte)((var7 >> 8 & 0xFF) * var8 / 255);
                  var4[var9 + 2] = (byte)((var7 >> 16 & 0xFF) * var8 / 255);
                  var4[var9 + 3] = (byte)var8;
               }
            }

            return var4;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }
}
