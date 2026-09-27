// Jade recovery: original class: jade.deps.eLz.hnW2i9i7
package jade.client.gui;

import jade.client.common.RenderUtils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D.Float;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public final class AltManagerIcons {
   private static final int Jax = 64;
   private static ResourceLocation proxyIcon;
   private static ResourceLocation deleteIcon;
   private static ResourceLocation YxV;
   private static ResourceLocation plusIcon;
   private static ResourceLocation pz3;
   private static ResourceLocation ujAisv;
   private static ResourceLocation JhAlo;
   private static ResourceLocation folderIcon;
   private static ResourceLocation containerLogoIcon;
   private static ResourceLocation uuVhxQ;
   private static ResourceLocation xMarkIcon;

   private AltManagerIcons() {
   }

   public static void drawProxyIcon(int var0, int var1, int var2, int var3) {
      RenderUtils.drawIconTexture(getProxyIcon(), var0, var1, var2, var3);
   }

   public static void drawDeleteIcon(int var0, int var1, int var2, int var3) {
      RenderUtils.drawIconTexture(Axrs(), var0, var1, var2, var3);
   }

   public static void drawMoreIcon(int var0, int var1, int var2, int var3) {
      RenderUtils.drawIconTexture(getMoreIcon(), var0, var1, var2, var3);
   }

   public static void drawPlusIcon(int var0, int var1, int var2, int var3) {
      RenderUtils.drawIconTexture(getPlusIcon(), var0, var1, var2, var3);
   }

   public static void drawTokenIcon(int var0, int var1, int var2, int var3) {
      RenderUtils.drawIconTexture(getTokenIcon(), var0, var1, var2, var3);
   }

   public static void drawCookieIcon(int var0, int var1, int var2, int var3) {
      RenderUtils.drawIconTexture(GgkPb(), var0, var1, var2, var3);
   }

   public static void ZEce(int var0, int var1, int var2, int var3) {
      RenderUtils.drawIconTexture(getMicrosoftIcon(), var0, var1, var2, var3);
   }

   public static void drawFolderIcon(int var0, int var1, int var2, int var3) {
      RenderUtils.drawIconTexture(getFolderIcon(), var0, var1, var2, var3);
   }

   public static void drawContainerLogoIcon(int var0, int var1, int var2, int var3) {
      RenderUtils.drawIconTexture(getContainerLogoIcon(), var0, var1, var2, var3);
   }

   public static void drawCheckIcon(int var0, int var1, int var2, int var3) {
      RenderUtils.drawIconTexture(getCheckIcon(), var0, var1, var2, var3);
   }

   public static void drawXMarkIcon(int var0, int var1, int var2, int var3) {
      RenderUtils.drawIconTexture(getXMarkIcon(), var0, var1, var2, var3);
   }

   private static ResourceLocation getProxyIcon() {
      if (proxyIcon == null) {
         proxyIcon = HEwdOfe(
            "jade_alt_proxy",
            new AltManagerIcons$6(
               3.0F,
               4.0F,
               18.0F,
               16.0F,
               new AltManagerIcons$7(
                  "M18 7H18.01M15 7H15.01M18 17H18.01M15 17H15.01M6 10H18C18.9319 10 19.3978 10 19.7654 9.84776C20.2554 9.64477 20.6448 9.25542 20.8478 8.76537C21 8.39782 21 7.93188 21 7C21 6.06812 21 5.60218 20.8478 5.23463C20.6448 4.74458 20.2554 4.35523 19.7654 4.15224C19.3978 4 18.9319 4 18 4H6C5.06812 4 4.60218 4 4.23463 4.15224C3.74458 4.35523 3.35523 4.74458 3.15224 5.23463C3 5.60218 3 6.06812 3 7C3 7.93188 3 8.39782 3.15224 8.76537C3.35523 9.25542 3.74458 9.64477 4.23463 9.84776C4.60218 10 5.06812 10 6 10ZM6 20H18C18.9319 20 19.3978 20 19.7654 19.8478C20.2554 19.6448 20.6448 19.2554 20.8478 18.7654C21 18.3978 21 17.9319 21 17C21 16.0681 21 15.6022 20.8478 15.2346C20.6448 14.7446 20.2554 14.3552 19.7654 14.1522C19.3978 14 18.9319 14 18 14H6C5.06812 14 4.60218 14 4.23463 14.1522C3.74458 14.3552 3.35523 14.7446 3.15224 15.2346C3 15.6022 3 16.0681 3 17C3 17.9319 3 18.3978 3.15224 18.7654C3.35523 19.2554 3.74458 19.6448 4.23463 19.8478C4.60218 20 5.06812 20 6 20Z",
                  2.0F
               )
            )
         );
      }

      return proxyIcon;
   }

   private static ResourceLocation Axrs() {
      if (deleteIcon == null) {
         deleteIcon = HEwdOfe(
            "jade_alt_delete",
            new AltManagerIcons$6(
               4.0F,
               3.0F,
               16.0F,
               18.0F,
               new AltManagerIcons$7(
                  "M10 12L14 16M14 12L10 16M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M18 6V16.2C18 17.8802 18 18.7202 17.673 19.362C17.3854 19.9265 16.9265 20.3854 16.362 20.673C15.7202 21 14.8802 21 13.2 21H10.8C9.11984 21 8.27976 21 7.63803 20.673C7.07354 20.3854 6.6146 19.9265 6.32698 19.362C6 18.7202 6 17.8802 6 16.2V6",
                  2.0F
               )
            )
         );
      }

      return deleteIcon;
   }

   private static ResourceLocation getMoreIcon() {
      if (YxV == null) {
         YxV = createCustomIcon("jade_alt_more", new Consumer<Graphics2D>() {
            public void accept(Graphics2D var1) {
               var1.setColor(Color.WHITE);
               var1.fillOval(27, 10, 10, 10);
               var1.fillOval(27, 27, 10, 10);
               var1.fillOval(27, 44, 10, 10);
            }
         });
      }

      return YxV;
   }

   private static ResourceLocation getPlusIcon() {
      if (plusIcon == null) {
         plusIcon = createCustomIcon("jade_alt_plus", new Consumer<Graphics2D>() {
            public void accept(Graphics2D var1) {
               var1.setColor(Color.WHITE);
               var1.setStroke(new BasicStroke(8.0F, 1, 1));
               var1.drawLine(32, 14, 32, 50);
               var1.drawLine(14, 32, 50, 32);
            }
         });
      }

      return plusIcon;
   }

   private static ResourceLocation getTokenIcon() {
      if (pz3 == null) {
         pz3 = HEwdOfe(
            "jade_alt_token",
            new AltManagerIcons$6(
               3.0F,
               5.0F,
               18.0F,
               14.0F,
               new AltManagerIcons$7(
                  "M21 9.5C21 11.9853 16.9706 14 12 14M21 9.5C21 7.01472 16.9706 5 12 5C7.02944 5 3 7.01472 3 9.5M21 9.5V15C21 17.2091 16.9706 19 12 19M12 14C7.02944 14 3 11.9853 3 9.5M12 14V19M3 9.5V15C3 17.2091 7.02944 19 12 19M7 18.3264V13.2422M17 18.3264V13.2422M16 9.5C16 10.3284 14.2091 11 12 11C9.79086 11 8 10.3284 8 9.5C8 8.67157 9.79086 8 12 8C14.2091 8 16 8.67157 16 9.5Z",
                  2.0F
               )
            )
         );
      }

      return pz3;
   }

   private static ResourceLocation GgkPb() {
      if (ujAisv == null) {
         ujAisv = HEwdOfe(
            "jade_alt_cookie",
            new AltManagerIcons$6(
               3.0F,
               3.0F,
               18.0F,
               18.0F,
               new AltManagerIcons$7(
                  "M9 16H9.01M12 11H12.01M7 10H7.01M15 16H15.01M21 12C21 16.9706 16.9706 21 12 21C7.02944 21 3 16.9706 3 12C3 7.02944 7.02944 3 12 3C12 5.76142 13.7909 8 16 8C16 10.2091 18.2386 12 21 12Z",
                  2.0F
               )
            )
         );
      }

      return ujAisv;
   }

   private static ResourceLocation getMicrosoftIcon() {
      if (JhAlo == null) {
         JhAlo = createCustomIcon("jade_alt_microsoft", new Consumer<Graphics2D>() {
            public void accept(Graphics2D var1) {
               var1.setColor(Color.WHITE);
               var1.fillRect(8, 8, 22, 22);
               var1.fillRect(34, 8, 22, 22);
               var1.fillRect(8, 34, 22, 22);
               var1.fillRect(34, 34, 22, 22);
            }
         });
      }

      return JhAlo;
   }

   private static ResourceLocation getFolderIcon() {
      if (folderIcon == null) {
         folderIcon = HEwdOfe(
            "jade_alt_folder",
            new AltManagerIcons$6(
               3.0F,
               5.0F,
               18.0F,
               14.0F,
               new AltManagerIcons$7(
                  "M16 19C15.6218 17.2883 13.9747 16 12 16C10.0253 16 8.37818 17.2883 8 19M12 12H12.01M12.0627 6.06274L11.9373 5.93726C11.5914 5.59135 11.4184 5.4184 11.2166 5.29472C11.0376 5.18506 10.8425 5.10425 10.6385 5.05526C10.4083 5 10.1637 5 9.67452 5H6.2C5.0799 5 4.51984 5 4.09202 5.21799C3.71569 5.40973 3.40973 5.71569 3.21799 6.09202C3 6.51984 3 7.07989 3 8.2V15.8C3 16.9201 3 17.4802 3.21799 17.908C3.40973 18.2843 3.71569 18.5903 4.09202 18.782C4.51984 19 5.07989 19 6.2 19H17.8C18.9201 19 19.4802 19 19.908 18.782C20.2843 18.5903 20.5903 18.2843 20.782 17.908C21 17.4802 21 16.9201 21 15.8V10.2C21 9.0799 21 8.51984 20.782 8.09202C20.5903 7.71569 20.2843 7.40973 19.908 7.21799C19.4802 7 18.9201 7 17.8 7H14.3255C13.8363 7 13.5917 7 13.3615 6.94474C13.1575 6.89575 12.9624 6.81494 12.7834 6.70528C12.5816 6.5816 12.4086 6.40865 12.0627 6.06274ZM13 12C13 12.5523 12.5523 13 12 13C11.4477 13 11 12.5523 11 12C11 11.4477 11.4477 11 12 11C12.5523 11 13 11.4477 13 12Z",
                  2.0F
               )
            )
         );
      }

      return folderIcon;
   }

   private static ResourceLocation getContainerLogoIcon() {
      if (containerLogoIcon == null) {
         containerLogoIcon = createCustomIcon("jade_container_logo", new Consumer<Graphics2D>() {
            public void accept(Graphics2D var1) {
               AffineTransform var2 = AffineTransform.getScaleInstance(1.0, 1.0);
               Shape var3 = var2.createTransformedShape(AltManagerIcons.parseSvgToPath("M32 6 L54 28 L32 58 L10 28 Z"));
               Shape var4 = var2.createTransformedShape(AltManagerIcons.parseSvgToPath("M32 6 L18 38 L46 38 Z"));
               var1.setColor(new Color(-1929379841, true));
               var1.fill(var3);
               var1.setColor(new Color(1090519039, true));
               var1.fill(var4);
               var1.setStroke(new BasicStroke(2.2F, 1, 1));
               var1.setColor(Color.WHITE);
               var1.draw(var3);
               var1.draw(var4);
            }
         });
      }

      return containerLogoIcon;
   }

   private static ResourceLocation getCheckIcon() {
      if (uuVhxQ == null) {
         uuVhxQ = createCustomIcon("jade_alt_check", new Consumer<Graphics2D>() {
            public void accept(Graphics2D var1) {
               var1.setColor(Color.WHITE);
               var1.setStroke(new BasicStroke(7.0F, 1, 1));
               var1.drawLine(15, 34, 27, 46);
               var1.drawLine(27, 46, 50, 19);
            }
         });
      }

      return uuVhxQ;
   }

   private static ResourceLocation getXMarkIcon() {
      if (xMarkIcon == null) {
         xMarkIcon = createCustomIcon("jade_alt_x", new Consumer<Graphics2D>() {
            public void accept(Graphics2D var1) {
               var1.setColor(Color.WHITE);
               var1.setStroke(new BasicStroke(7.0F, 1, 1));
               var1.drawLine(18, 18, 46, 46);
               var1.drawLine(46, 18, 18, 46);
            }
         });
      }

      return xMarkIcon;
   }

   private static ResourceLocation createCustomIcon(String var0, Consumer<Graphics2D> var1) {
      BufferedImage var2 = new BufferedImage(64, 64, 2);
      Graphics2D var3 = var2.createGraphics();
      var3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var3.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      var3.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
      var1.accept(var3);
      var3.dispose();
      return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(var0, new DynamicTexture(var2));
   }

   private static ResourceLocation HEwdOfe(String var0, AltManagerIcons$6 var1) {
      BufferedImage var2 = new BufferedImage(64, 64, 2);
      Graphics2D var3 = var2.createGraphics();
      var3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var3.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      var3.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
      float var4 = 4.0F;
      float var5 = Math.min((64.0F - var4 * 2.0F) / var1.mcmG, (64.0F - var4 * 2.0F) / var1.rhWx);
      AffineTransform var6 = new AffineTransform();
      var6.translate((64.0F - var1.mcmG * var5) / 2.0F - var1.kiXd * var5, (64.0F - var1.rhWx * var5) / 2.0F - var1.SUHqLe * var5);
      var6.scale(var5, var5);
      var3.setColor(Color.WHITE);

      for (AltManagerIcons$7 var10 : var1.strokePaths) {
         Shape var11 = var6.createTransformedShape(parseSvgPath(var10.pathData));
         var3.setStroke(new BasicStroke(var10.Czc * var5, 1, 1));
         var3.draw(var11);
      }

      var3.dispose();
      return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(var0, new DynamicTexture(var2));
   }

   private static Float parseSvgPath(String var0) {
      List var1 = tokenizeSvgPath(var0);
      Float var2 = new Float(0);
      char var3 = ' ';
      float var4 = 0.0F;
      float var5 = 0.0F;
      float var6 = 0.0F;
      float var7 = 0.0F;
      int var8 = 0;

      while (var8 < var1.size()) {
         String var9 = (String)var1.get(var8);
         if (isPathCommand(var9)) {
            var3 = var9.charAt(0);
            var8++;
            if (var3 == 'Z' || var3 == 'z') {
               var2.closePath();
               var4 = var6;
               var5 = var7;
            }
         } else if (var3 == 'M') {
            var4 = parseCoordinate(var1, var8);
            var5 = parseCoordinate(var1, var8 + 1);
            var2.moveTo(var4, var5);
            var6 = var4;
            var7 = var5;
            var8 += 2;
            var3 = 'L';
         } else if (var3 == 'L') {
            var4 = parseCoordinate(var1, var8);
            var5 = parseCoordinate(var1, var8 + 1);
            var2.lineTo(var4, var5);
            var8 += 2;
         } else if (var3 == 'H') {
            var4 = parseCoordinate(var1, var8);
            var2.lineTo(var4, var5);
            var8++;
         } else if (var3 == 'V') {
            var5 = parseCoordinate(var1, var8);
            var2.lineTo(var4, var5);
            var8++;
         } else if (var3 == 'C') {
            float var10 = parseCoordinate(var1, var8);
            float var11 = parseCoordinate(var1, var8 + 1);
            float var12 = parseCoordinate(var1, var8 + 2);
            float var13 = parseCoordinate(var1, var8 + 3);
            var4 = parseCoordinate(var1, var8 + 4);
            var5 = parseCoordinate(var1, var8 + 5);
            var2.curveTo(var10, var11, var12, var13, var4, var5);
            var8 += 6;
         } else {
            var8++;
         }
      }

      return var2;
   }

   private static List<String> tokenizeSvgPath(String var0) {
      ArrayList var1 = new ArrayList();
      StringBuilder var2 = new StringBuilder();

      for (int var3 = 0; var3 < var0.length(); var3++) {
         char var4 = var0.charAt(var3);
         if ((var4 < 'A' || var4 > 'Z') && (var4 < 'a' || var4 > 'z')) {
            if (var4 == '-' || var4 == '.' || var4 >= '0' && var4 <= '9') {
               if (var4 == '-' && var2.length() > 0 && var2.charAt(var2.length() - 1) != 'e' && var2.charAt(var2.length() - 1) != 'E') {
                  flushToken(var2, var1);
               }

               var2.append(var4);
            } else {
               flushToken(var2, var1);
            }
         } else {
            flushToken(var2, var1);
            var1.add(String.valueOf(var4));
         }
      }

      flushToken(var2, var1);
      return var1;
   }

   private static void flushToken(StringBuilder var0, List<String> var1) {
      if (var0.length() > 0) {
         var1.add(var0.toString());
         var0.setLength(0);
      }
   }

   private static boolean isPathCommand(String var0) {
      return var0.length() == 1 && Character.isLetter(var0.charAt(0));
   }

   private static float parseCoordinate(List<String> var0, int var1) {
      return java.lang.Float.parseFloat((String)var0.get(var1));
   }

   public static Float parseSvgToPath(String var0) {
      return parseSvgPath(var0);
   }
}
