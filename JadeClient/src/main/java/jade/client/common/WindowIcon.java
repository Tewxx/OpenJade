// Jade recovery: original class: jade.deps.eLz.L9kPWmo
package jade.client.common;

import jade.deps.loader107.CoreResourceIndex;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;

public class WindowIcon {
   public static void restoreDefaultIcon() {
      try {
         BufferedImage var0 = loadIconResource("/assets/minecraft/icons/icon_16x16.png");
         BufferedImage var1 = loadIconResource("/assets/minecraft/icons/icon_32x32.png");
         if (var0 == null || var1 == null) {
            return;
         }

         Class var2 = Class.forName("org.lwjgl.opengl.Display");
         Method var3 = var2.getDeclaredMethod("setIcon", ByteBuffer[].class);
         var3.setAccessible(true);
         var3.invoke(null, (Object) new ByteBuffer[]{toRgbaByteBuffer(resizeIcon(var0, 16)), toRgbaByteBuffer(resizeIcon(var1, 32))});
      } catch (Exception var4) {
         System.err.println("[Jade] Failed to reset window icon: " + var4.getMessage());
      }
   }

   private static BufferedImage loadIconResource(String var0) {
      try {
         InputStream var1 = CoreResourceIndex.openResource(var0);
         if (var1 == null) {
            var1 = Minecraft.class.getResourceAsStream(var0);
         }

         if (var1 == null) {
            return null;
         } else {
            BufferedImage var2 = ImageIO.read(var1);
            var1.close();
            return var2;
         }
      } catch (Exception var3) {
         return null;
      }
   }

   public static void applyCustomIcon() {
      try {
         BufferedImage var0 = loadCustomIconImage();
         if (var0 != null) {
            applyIconToDisplay(var0);
         }
      } catch (Exception var1) {
         System.err.println("[Jade] Failed to apply custom window icon: " + var1.getMessage());
      }
   }

   private static BufferedImage loadCustomIconImage() {
      BufferedImage var0 = loadIconResource("/assets/jade/textures/gui/window_icon.png");
      if (var0 == null) {
         System.err.println("[Jade] window_icon.png not found in assets");
      }

      return var0;
   }

   private static void applyIconToDisplay(BufferedImage var0) throws Exception {
      ByteBuffer var1 = toRgbaByteBuffer(resizeIcon(var0, 16));
      ByteBuffer var2 = toRgbaByteBuffer(resizeIcon(var0, 32));
      Class var3 = Class.forName("org.lwjgl.opengl.Display");
      Method var4 = var3.getDeclaredMethod("setIcon", ByteBuffer[].class);
      var4.setAccessible(true);
      var4.invoke(null, (Object) new ByteBuffer[]{var1, var2});
   }

   private static BufferedImage resizeIcon(BufferedImage var0, int var1) {
      if (var0.getWidth() == var1 && var0.getHeight() == var1 && var0.getType() == 2) {
         return var0;
      } else {
         BufferedImage var2 = new BufferedImage(var1, var1, 2);
         Graphics2D var3 = var2.createGraphics();
         var3.drawImage(var0, 0, 0, var1, var1, null);
         var3.dispose();
         return var2;
      }
   }

   private static ByteBuffer toRgbaByteBuffer(BufferedImage var0) {
      int var1 = var0.getWidth();
      int var2 = var0.getHeight();
      int[] var3 = new int[var1 * var2];
      var0.getRGB(0, 0, var1, var2, var3, 0, var1);
      ByteBuffer var4 = ByteBuffer.allocateDirect(var1 * var2 * 4);

      for (int var8 : var3) {
         var4.put((byte)(var8 >> 16 & 0xFF));
         var4.put((byte)(var8 >> 8 & 0xFF));
         var4.put((byte)(var8 & 0xFF));
         var4.put((byte)(var8 >> 24 & 0xFF));
      }

      ((Buffer)var4).flip();
      return var4;
   }
}
