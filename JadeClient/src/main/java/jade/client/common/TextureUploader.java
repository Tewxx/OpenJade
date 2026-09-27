// Jade recovery: original class: jade.deps.eLz.ry05Fgg
package jade.client.common;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class TextureUploader {
   public int upload(BufferedImage var1) {
      int var2 = var1.getWidth();
      int var3 = var1.getHeight();
      int[] var4 = var1.getRGB(0, 0, var2, var3, new int[var2 * var3], 0, var2);
      ByteBuffer var5 = BufferUtils.createByteBuffer(var2 * var3 * 4);

      for (int var9 : var4) {
         int var10 = var9 >>> 24 & 0xFF;
         var5.put((byte)(var10 == 0 ? 255 : var9 >>> 16 & 0xFF));
         var5.put((byte)(var10 == 0 ? 255 : var9 >>> 8 & 0xFF));
         var5.put((byte)(var10 == 0 ? 255 : var9 & 0xFF));
         var5.put((byte)var10);
      }

      ((Buffer)var5).flip();
      int var11 = GL11.glGenTextures();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.enableTexture2D();
      GlStateManager.bindTexture(var11);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexParameteri(3553, 10242, 33071);
      GL11.glTexParameteri(3553, 10243, 33071);
      GL11.glTexImage2D(3553, 0, 6408, var2, var3, 0, 6408, 5121, var5);
      return var11;
   }
}
