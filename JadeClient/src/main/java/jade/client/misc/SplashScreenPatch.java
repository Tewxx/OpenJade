// Jade recovery: original class: jade.deps.eLz.rcsJkQN2rJ
package jade.client.misc;

import jade.deps.loader107.CoreResourceIndex;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class SplashScreenPatch {
   private static int splashTextureId = -1;
   private static boolean initialized = false;

   private static void VAZy() {
      try {
         Class var0 = Class.forName("net.minecraftforge.fml.client.SplashProgress");
         Field var1 = var0.getDeclaredField("fontColor");
         var1.setAccessible(true);
         var1.set(null, 16777215);
         Field var2 = var0.getDeclaredField("barBackgroundColor");
         var2.setAccessible(true);
         var2.set(null, 0);
         Field var3 = var0.getDeclaredField("barBorderColor");
         var3.setAccessible(true);
         var3.set(null, 0);
      } catch (Exception var4) {
         System.err.println("[Jade] Could not patch SplashProgress colors: " + var4.getMessage());
      }
   }

   public static void renderSplashImage() {
      if (!initialized) {
         initialized = true;
         VAZy();
         splashTextureId = loadSplashTexture();
      }

      if (splashTextureId != -1) {
         int var0 = Display.getWidth();
         int var1 = Display.getHeight();
         float var2 = 320.0F - var0 / 2.0F;
         float var3 = 320.0F + var0 / 2.0F;
         float var4 = 240.0F - var1 / 2.0F;
         float var5 = 240.0F + var1 / 2.0F;
         GL11.glEnable(3553);
         GL11.glBindTexture(3553, splashTextureId);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glBegin(7);
         GL11.glTexCoord2f(0.0F, 0.0F);
         GL11.glVertex2f(var2, var4);
         GL11.glTexCoord2f(1.0F, 0.0F);
         GL11.glVertex2f(var3, var4);
         GL11.glTexCoord2f(1.0F, 1.0F);
         GL11.glVertex2f(var3, var5);
         GL11.glTexCoord2f(0.0F, 1.0F);
         GL11.glVertex2f(var2, var5);
         GL11.glEnd();
         GL11.glDisable(3553);
      }
   }

   private static int loadSplashTexture() {
      try {
         InputStream var0 = CoreResourceIndex.openResource("/assets/jade/textures/gui/splash.png");
         if (var0 == null) {
            System.err.println("[Jade] splash.png not found");
            return -1;
         } else {
            BufferedImage var1 = ImageIO.read(var0);
            var0.close();
            if (var1 == null) {
               return -1;
            } else {
               int var2 = var1.getWidth();
               int var3 = var1.getHeight();
               int[] var4 = new int[var2 * var3];
               var1.getRGB(0, 0, var2, var3, var4, 0, var2);
               ByteBuffer var5 = BufferUtils.createByteBuffer(var2 * var3 * 4);

               for (int var9 : var4) {
                  var5.put((byte)(var9 >> 16 & 0xFF));
                  var5.put((byte)(var9 >> 8 & 0xFF));
                  var5.put((byte)(var9 & 0xFF));
                  var5.put((byte)(var9 >> 24 & 0xFF));
               }

               ((Buffer)var5).flip();
               int var11 = GL11.glGenTextures();
               GL11.glBindTexture(3553, var11);
               GL11.glTexParameteri(3553, 10241, 9729);
               GL11.glTexParameteri(3553, 10240, 9729);
               GL11.glTexImage2D(3553, 0, 6408, var2, var3, 0, 6408, 5121, var5);
               return var11;
            }
         }
      } catch (Exception var10) {
         System.err.println("[Jade] Failed to create loading texture: " + var10.getMessage());
         return -1;
      }
   }
}
