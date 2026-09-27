// Jade recovery: original class: jade.deps.eLz.G1K2PF
package jade.client.common;

import jade.client.module.Category;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D.Float;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class VectorIcons {
   private static final int ICON_TEXTURE_SIZE = 64;
   private final Map<Category, ResourceLocation> Jnn = new EnumMap<>(Category.class);
   private final Map<Category, ResourceLocation> vde = new EnumMap<>(Category.class);
   private final Map<IconType, ResourceLocation> lYilZ = new EnumMap<>(IconType.class);
   private ResourceLocation logoTexture;
   private ResourceLocation searchIconTexture;
   private ResourceLocation CcJ;
   private ResourceLocation Cj7;

   public void drawCategoryIcon(Category var1, int var2, int var3, int var4, int var5) {
      RenderUtils.drawIconTexture(this.CvEmh(var1), var2, var3, var4, var5);
   }

   public void drawCategoryToolbarIcon(Category var1, int var2, int var3, int var4, int var5) {
      ResourceLocation var6 = this.vde.get(var1);
      if (var6 == null) {
         var6 = this.createIconTexture("jade_dropdown_toolbar_" + var1.name(), this.xbrE1(var1));
         this.vde.put(var1, var6);
      }

      RenderUtils.drawIconTexture(var6, var2, var3, var4, var5);
   }

   public void HViKr(int var1, int var2, int var3, int var4) {
      RenderUtils.drawIconTexture(this.getSearchIconTexture(), var1, var2, var3, var4);
   }

   public ResourceLocation GOXpq() {
      if (this.CcJ == null) {
         this.CcJ = this.createIconTexture(
            "jade_container_eye",
            new VectorIconDefinition(
               1.0F,
               5.0F,
               22.0F,
               14.0F,
               new VectorPath(
                  "M11.9944 15.5 C13.9274 15.5 15.4944 13.933 15.4944 12 C15.4944 10.067 13.9274 8.5 11.9944 8.5 C10.0614 8.5 8.49439 10.067 8.49439 12 C8.49439 13.933 10.0614 15.5 11.9944 15.5 Z M11.9944 13.4944 C11.1691 13.4944 10.5 12.8253 10.5 12 C10.5 11.1747 11.1691 10.5056 11.9944 10.5056 C12.8197 10.5056 13.4888 11.1747 13.4888 12 C13.4888 12.8253 12.8197 13.4944 11.9944 13.4944 Z",
                  true,
                  0.0F
               ),
               new VectorPath(
                  "M12 5 C7.18879 5 3.9167 7.60905 2.1893 9.47978 C0.857392 10.9222 0.857393 13.0778 2.1893 14.5202 C3.9167 16.391 7.18879 19 12 19 C16.8112 19 20.0833 16.391 21.8107 14.5202 C23.1426 13.0778 23.1426 10.9222 21.8107 9.47978 C20.0833 7.60905 16.8112 5 12 5 Z M3.65868 10.8366 C5.18832 9.18002 7.9669 7 12 7 C16.0331 7 18.8117 9.18002 20.3413 10.8366 C20.9657 11.5128 20.9657 12.4872 20.3413 13.1634 C18.8117 14.82 16.0331 17 12 17 C7.9669 17 5.18832 14.82 3.65868 13.1634 C3.03426 12.4872 3.03426 11.5128 3.65868 10.8366 Z",
                  true,
                  0.0F
               )
            )
         );
      }

      return this.CcJ;
   }

   public ResourceLocation esvk() {
      if (this.Cj7 == null) {
         this.Cj7 = this.createIconTexture(
            "jade_container_eye_off",
            new VectorIconDefinition(
               3.3F,
               7.6F,
               17.4F,
               9.4F,
               new VectorPath(
                  "M11.254 13.9749 V17 H12.754 V13.9672 C14.2271 13.846 15.6039 13.3931 16.8015 12.6851 L18.3545 15.375 L19.6535 14.625 L18.0318 11.816 C19.1746 10.8606 20.1061 9.61996 20.6917 8.2749 L19.3164 7.67617 C18.7623 8.94881 17.8318 10.1208 16.7002 10.9556 C16.673 10.9757 16.6456 10.9957 16.618 11.0155 C15.3326 11.9381 13.7363 12.4901 12.0009 12.4999 C12.0006 12.4999 12.0002 12.4999 11.9999 12.4999 L11.9542 12.5 C11.9538 12.5 11.9535 12.5 11.9532 12.5 C10.229 12.4998 8.64029 11.9643 7.35483 11.0613 C6.16376 10.2246 5.23552 9.07427 4.69969 7.74421 L3.30835 8.30472 C3.87054 9.70022 4.7854 10.922 5.94395 11.872 L4.3545 14.625 L5.65354 15.375 L7.18138 12.7287 C8.38861 13.4274 9.77393 13.8685 11.254 13.9749 Z",
                  true,
                  0.0F
               )
            )
         );
      }

      return this.Cj7;
   }

   public void drawIcon(IconType var1, int var2, int var3, int var4, int var5) {
      RenderUtils.drawIconTexture(this.getIconTexture(var1), var2, var3, var4, var5);
   }

   public void drawLogo(float var1, float var2, float var3, int var4) {
      RenderUtils.drawIconTexture(this.getLogoTexture(), var1, var2, Math.round(var3), var4);
   }

   private ResourceLocation getIconTexture(IconType var1) {
      ResourceLocation var2 = this.lYilZ.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         ResourceLocation var3 = this.createIconTexture("jade_container_config_" + var1.name().toLowerCase(), this.createIconDefinition(var1));
         this.lYilZ.put(var1, var3);
         return var3;
      }
   }

   private ResourceLocation createIconTexture(String var1, VectorIconDefinition var2) {
      BufferedImage var3 = new BufferedImage(64, 64, 2);
      Graphics2D var4 = var3.createGraphics();
      var4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var4.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      var4.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
      float var5 = 4.0F;
      float var6 = Math.min((64.0F - var5 * 2.0F) / var2.OHuy, (64.0F - var5 * 2.0F) / var2.cZq);
      AffineTransform var7 = new AffineTransform();
      var7.translate((64.0F - var2.OHuy * var6) / 2.0F - var2.viewBoxMinX * var6, (64.0F - var2.cZq * var6) / 2.0F - var2.QQua * var6);
      var7.scale(var6, var6);
      var4.setColor(Color.WHITE);

      for (VectorPath var11 : var2.dlhyL) {
         Shape var12 = var7.createTransformedShape(this.psR7(var11.pathData));
         if (var11.filled) {
            var4.fill(var12);
         }

         if (var11.strokeWidth > 0.0F) {
            var4.setStroke(new BasicStroke(var11.strokeWidth * var6, 1, 1));
            var4.draw(var12);
         }
      }

      var4.dispose();
      return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(var1, new DynamicTexture(var3));
   }

   private VectorIconDefinition createIconDefinition(IconType var1) {
      switch (var1) {
         case CREATE:
            return new VectorIconDefinition(
               1.0F,
               1.0F,
               22.0F,
               22.0F,
               new VectorPath(
                  "M13,3 L5,3 L5,21 L15,21 L15,23 L5,23 C3.8954305,23 3,22.1045695 3,21 L3,3 C3,1.8954305 3.8954305,1 5,1 L15.4142136,1 L21,6.58578644 L21,14 L19,14 L19,9 L15,9 C13.8954305,9 13,8.1045695 13,7 L13,3 Z M19,19 L19,17 L21,17 L21,19 L23,19 L23,21 L21,21 L21,23 L19,23 L19,21 L17,21 L17,19 L19,19 Z M18.5857864,7 L15,3.41421356 L15,7 L18.5857864,7 Z",
                  true,
                  0.0F
               )
            );
         case REFRESH:
            return new VectorIconDefinition(
               4.0F,
               4.0F,
               16.0F,
               16.0F,
               new VectorPath(
                  "M4.06189 13C4.02104 12.6724 4 12.3387 4 12C4 7.58172 7.58172 4 12 4C14.5006 4 16.7332 5.14727 18.2002 6.94416M19.9381 11C19.979 11.3276 20 11.6613 20 12C20 16.4183 16.4183 20 12 20C9.61061 20 7.46589 18.9525 6 17.2916M9 17H6V17.2916M18.2002 4V6.94416M18.2002 6.94416V6.99993L15.2002 7M6 20V17.2916",
                  false,
                  2.0F
               )
            );
         case FOLDER:
            return new VectorIconDefinition(
               3.0F,
               5.0F,
               18.0F,
               14.0F,
               new VectorPath(
                  "M9 13H15M15 13L13 11M15 13L13 15M12.0627 6.06274L11.9373 5.93726C11.5914 5.59135 11.4184 5.4184 11.2166 5.29472C11.0376 5.18506 10.8425 5.10425 10.6385 5.05526C10.4083 5 10.1637 5 9.67452 5H6.2C5.0799 5 4.51984 5 4.09202 5.21799C3.71569 5.40973 3.40973 5.71569 3.21799 6.09202C3 6.51984 3 7.07989 3 8.2V15.8C3 16.9201 3 17.4802 3.21799 17.908C3.40973 18.2843 3.71569 18.5903 4.09202 18.782C4.51984 19 5.07989 19 6.2 19H17.8C18.9201 19 19.4802 19 19.908 18.782C20.2843 18.5903 20.5903 18.2843 20.782 17.908C21 17.4802 21 16.9201 21 15.8V10.2C21 9.0799 21 8.51984 20.782 8.09202C20.5903 7.71569 20.2843 7.40973 19.908 7.21799C19.4802 7 18.9201 7 17.8 7H14.3255C13.8363 7 13.5917 7 13.3615 6.94474C13.1575 6.89575 12.9624 6.81494 12.7834 6.70528C12.5816 6.5816 12.4086 6.40865 12.0627 6.06274Z",
                  false,
                  2.0F
               )
            );
         case SAVE:
            return new VectorIconDefinition(
               1.0F,
               1.0F,
               22.0F,
               22.0F,
               new VectorPath(
                  "M18.1716 1C18.702 1 19.2107 1.21071 19.5858 1.58579L22.4142 4.41421C22.7893 4.78929 23 5.29799 23 5.82843V20C23 21.6569 21.6569 23 20 23H4C2.34315 23 1 21.6569 1 20V4C1 2.34315 2.34315 1 4 1H18.1716ZM4 3C3.44772 3 3 3.44772 3 4V20C3 20.5523 3.44772 21 4 21L5 21L5 15C5 13.3431 6.34315 12 8 12L16 12C17.6569 12 19 13.3431 19 15V21H20C20.5523 21 21 20.5523 21 20V6.82843C21 6.29799 20.7893 5.78929 20.4142 5.41421L18.5858 3.58579C18.2107 3.21071 17.702 3 17.1716 3H17V5C17 6.65685 15.6569 8 14 8H10C8.34315 8 7 6.65685 7 5V3H4ZM17 21V15C17 14.4477 16.5523 14 16 14L8 14C7.44772 14 7 14.4477 7 15L7 21L17 21ZM9 3H15V5C15 5.55228 14.5523 6 14 6H10C9.44772 6 9 5.55228 9 5V3Z",
                  true,
                  0.0F
               )
            );
         case DELETE:
            return new VectorIconDefinition(
               4.0F,
               3.0F,
               16.0F,
               18.0F,
               new VectorPath("M10 11V17", false, 2.0F),
               new VectorPath("M14 11V17", false, 2.0F),
               new VectorPath("M4 7H20", false, 2.0F),
               new VectorPath("M6 7H12H18V18C18 19.6569 16.6569 21 15 21H9C7.34315 21 6 19.6569 6 18V7Z", false, 2.0F),
               new VectorPath("M9 5C9 3.89543 9.89543 3 11 3H13C14.1046 3 15 3.89543 15 5V7H9V5Z", false, 2.0F)
            );
         case BIND:
            return new VectorIconDefinition(
               2.0F,
               6.0F,
               20.0F,
               12.0F,
               new VectorPath(
                  "M4 6H20C21.1046 6 22 6.89543 22 8V16C22 17.1046 21.1046 18 20 18H4C2.89543 18 2 17.1046 2 16V8C2 6.89543 2.89543 6 4 6Z", false, 2.0F
               ),
               new VectorPath("M8 15H16", false, 2.0F)
            );
         case TICK:
            return new VectorIconDefinition(2.75F, 4.75F, 10.5F, 7.5F, new VectorPath("M2.75 8.75 L6.25 12.25 L13.25 4.75", false, 1.5F));
         case POWER:
            return new VectorIconDefinition(
               3.0F,
               3.0F,
               18.0F,
               18.0F,
               new VectorPath("M12 3 V12", false, 2.0F),
               new VectorPath(
                  "M18.3611 5.64001 C19.6195 6.8988 20.4764 8.50246 20.8234 10.2482 C21.1704 11.994 20.992 13.8034 20.3107 15.4478 C19.6295 17.0921 18.4759 18.4976 16.9959 19.4864 C15.5159 20.4752 13.776 21.0029 11.9961 21.0029 C10.2162 21.0029 8.47625 20.4752 6.99627 19.4864 C5.51629 18.4976 4.36274 17.0921 3.68146 15.4478 C3.00019 13.8034 2.82179 11.994 3.16882 10.2482 C3.51584 8.50246 4.37272 6.8988 5.6311 5.64001",
                  false,
                  2.0F
               )
            );
         case HUD:
         default:
            return new VectorIconDefinition(
               0.0F,
               2.994F,
               32.0F,
               26.011F,
               new VectorPath(
                  "M30 2.994 H2 C0.901 2.994 0 3.894 0 4.994 V22 C0 23.099 0.9 23.999 2 23.999 H15 V27.005 H10 C9.448 27.005 9 27.453 9 28.005 S9.448 29.005 10 29.005 H22 C22.552 29.005 23 28.557 23 28.005 S22.552 27.005 22 27.005 H17 V23.999 H30 C31.099 23.999 32 23.099 32 22 V4.994 C32 3.894 31.099 2.994 30 2.994 Z M30 22 H2 V4.994 H30 V22 Z",
                  true,
                  0.0F
               )
            );
      }
   }

   private VectorIconDefinition xbrE1(Category var1) {
      if (var1 == Category.friends) {
         return new VectorIconDefinition(
            2.0F,
            3.0F,
            20.0F,
            18.0F,
            new VectorPath("M9 11C11.2091 11 13 9.20914 13 7C13 4.79086 11.2091 3 9 3C6.79086 3 5 4.79086 5 7C5 9.20914 6.79086 11 9 11Z", false, 2.0F),
            new VectorPath("M2 21C2 16.5817 5.13401 13 9 13C12.866 13 16 16.5817 16 21", false, 2.0F),
            new VectorPath("M16 11C17.6569 11 19 9.65685 19 8C19 6.34315 17.6569 5 16 5", false, 2.0F),
            new VectorPath("M17 14C19.7614 14 22 16.6863 22 20", false, 2.0F)
         );
      } else {
         return var1 == Category.profiles
            ? new VectorIconDefinition(
               2.0F,
               2.0F,
               20.0F,
               20.0F,
               new VectorPath(
                  "M9.7 2H14.3L15.1 5.1C15.7 5.35 16.25 5.67 16.76 6.06L19.85 5.15L22.15 9.15L19.85 11.35C19.95 11.99 19.95 12.66 19.85 13.3L22.15 15.5L19.85 19.5L16.76 18.59C16.25 18.98 15.7 19.3 15.1 19.55L14.3 22.65H9.7L8.9 19.55C8.3 19.3 7.75 18.98 7.24 18.59L4.15 19.5L1.85 15.5L4.15 13.3C4.05 12.66 4.05 11.99 4.15 11.35L1.85 9.15L4.15 5.15L7.24 6.06C7.75 5.67 8.3 5.35 8.9 5.1L9.7 2Z",
                  false,
                  1.8F
               ),
               new VectorPath(
                  "M12 15.5C13.933 15.5 15.5 13.933 15.5 12C15.5 10.067 13.933 8.5 12 8.5C10.067 8.5 8.5 10.067 8.5 12C8.5 13.933 10.067 15.5 12 15.5Z",
                  false,
                  1.8F
               )
            )
            : this.edmX1(var1);
      }
   }

   private ResourceLocation getSearchIconTexture() {
      if (this.searchIconTexture != null) {
         return this.searchIconTexture;
      } else {
         byte var1 = 64;
         BufferedImage var2 = new BufferedImage(var1, var1, 2);
         Graphics2D var3 = var2.createGraphics();
         var3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var3.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
         var3.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
         float var4 = var1 / 24.0F;
         var3.setColor(Color.WHITE);
         var3.setStroke(new BasicStroke(2.0F * var4, 1, 1));
         int var5 = Math.round(3.0F * var4);
         int var6 = Math.round(3.0F * var4);
         int var7 = Math.round(14.0F * var4);
         var3.drawOval(var5, var6, var7, var7);
         var3.drawLine(Math.round(15.0F * var4), Math.round(15.0F * var4), Math.round(21.0F * var4), Math.round(21.0F * var4));
         var3.dispose();
         this.searchIconTexture = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("jade_container_search", new DynamicTexture(var2));
         return this.searchIconTexture;
      }
   }

   private ResourceLocation getLogoTexture() {
      if (this.logoTexture != null) {
         return this.logoTexture;
      } else {
         short var1 = 128;
         BufferedImage var2 = new BufferedImage(var1, var1, 2);
         Graphics2D var3 = var2.createGraphics();
         var3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var3.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
         var3.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
         float var4 = var1 / 64.0F;
         AffineTransform var5 = AffineTransform.getScaleInstance(var4, var4);
         Shape var6 = var5.createTransformedShape(this.psR7("M32 6 L54 28 L32 58 L10 28 Z"));
         Shape var7 = var5.createTransformedShape(this.psR7("M32 6 L18 38 L46 38 Z"));
         var3.setColor(new Color(-1929379841, true));
         var3.fill(var6);
         var3.setColor(new Color(1090519039, true));
         var3.fill(var7);
         var3.setStroke(new BasicStroke(2.2F * var4, 1, 1));
         var3.setColor(Color.WHITE);
         var3.draw(var6);
         var3.draw(var7);
         var3.dispose();
         this.logoTexture = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("jade_container_logo", new DynamicTexture(var2));
         return this.logoTexture;
      }
   }

   private void drawGlLine(float var1, float var2, float var3, float var4, float var5, int var6) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      this.applyGlColor(var6);
      GL11.glLineWidth(var5);
      GL11.glBegin(1);
      GL11.glVertex2f(var1, var2);
      GL11.glVertex2f(var3, var4);
      GL11.glEnd();
      GL11.glLineWidth(1.0F);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   private void trlAs(float var1, float var2, float var3, float var4, int var5) {
      RenderUtils.ZHgAz(var1, var2, var3, 28, var4, (var5 >> 16 & 0xFF) / 255.0F, (var5 >> 8 & 0xFF) / 255.0F, (var5 & 0xFF) / 255.0F, (var5 >> 24 & 0xFF) / 255.0F);
   }

   private void fillCircle(float var1, float var2, float var3, int var4) {
      this.fillRoundedRect(var1 - var3, var2 - var3, var3 * 2.0F, var3 * 2.0F, var3, var4);
   }

   private void TbeJ7(float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      RoundedRect.drawRoundedOutline(var1, var2, var3, var4, var5, var6, new Color(0, 0, 0, 0), new Color(var7, true));
   }

   private void drawBulletListIcon(float var1, float var2, float var3, int var4) {
      this.drawGlLine(var1 + 3.0F * var3, var2 + 5.0F * var3, var1 + 15.0F * var3, var2 + 5.0F * var3, 1.5F, var4);
      this.drawGlLine(var1 + 3.0F * var3, var2 + 9.0F * var3, var1 + 15.0F * var3, var2 + 9.0F * var3, 1.5F, var4);
      this.drawGlLine(var1 + 3.0F * var3, var2 + 13.0F * var3, var1 + 15.0F * var3, var2 + 13.0F * var3, 1.5F, var4);
      this.fillCircle(var1 + 7.0F * var3, var2 + 5.0F * var3, 2.2F * var3, var4);
      this.fillCircle(var1 + 12.0F * var3, var2 + 9.0F * var3, 2.2F * var3, var4);
      this.fillCircle(var1 + 6.0F * var3, var2 + 13.0F * var3, 2.2F * var3, var4);
   }

   private void drawCircleOutline(float var1, float var2, float var3, float var4, int var5) {
      for (int var6 = 0; var6 < 8; var6++) {
         double var7 = (Math.PI * 2) * var6 / 8.0;
         this.drawGlLine(
            var1 + (float)Math.cos(var7) * (var3 - 1.5F),
            var2 + (float)Math.sin(var7) * (var3 - 1.5F),
            var1 + (float)Math.cos(var7) * var3,
            var2 + (float)Math.sin(var7) * var3,
            1.6F,
            var5
         );
      }

      this.trlAs(var1, var2, var3 - 2.0F, 1.8F, var5);
      this.fillCircle(var1, var2, var4, var5);
   }

   private void eaqQ(int var1, float... var2) {
      if (var2 != null && var2.length >= 6) {
         GL11.glPushMatrix();
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glDisable(3553);
         GL11.glEnable(2881);
         this.applyGlColor(var1);
         GL11.glBegin(6);

         for (byte var3 = 0; var3 + 1 < var2.length; var3 += 2) {
            GL11.glVertex2f(var2[var3], var2[var3 + 1]);
         }

         GL11.glEnd();
         GL11.glDisable(2881);
         GL11.glEnable(3553);
         GL11.glDisable(3042);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }
   }

   private void applyGlColor(int var1) {
      GL11.glColor4f((var1 >> 16 & 0xFF) / 255.0F, (var1 >> 8 & 0xFF) / 255.0F, (var1 & 0xFF) / 255.0F, (var1 >> 24 & 0xFF) / 255.0F);
   }

   private ResourceLocation CvEmh(Category var1) {
      ResourceLocation var2 = this.Jnn.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         VectorIconDefinition var3 = this.edmX1(var1);
         BufferedImage var4 = new BufferedImage(64, 64, 2);
         Graphics2D var5 = var4.createGraphics();
         var5.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var5.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
         var5.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
         float var6 = 4.0F;
         float var7 = Math.min((64.0F - var6 * 2.0F) / var3.OHuy, (64.0F - var6 * 2.0F) / var3.cZq);
         AffineTransform var8 = new AffineTransform();
         var8.translate((64.0F - var3.OHuy * var7) / 2.0F - var3.viewBoxMinX * var7, (64.0F - var3.cZq * var7) / 2.0F - var3.QQua * var7);
         var8.scale(var7, var7);
         var5.setColor(Color.WHITE);

         for (VectorPath var12 : var3.dlhyL) {
            Shape var13 = var8.createTransformedShape(this.psR7(var12.pathData));
            if (var12.filled) {
               var5.fill(var13);
            }

            if (var12.strokeWidth > 0.0F) {
               var5.setStroke(new BasicStroke(var12.strokeWidth * var7, 1, 1));
               var5.draw(var13);
            }
         }

         if (var1 == Category.client) {
            float var14 = 2.3F * var7;
            var5.setComposite(AlphaComposite.Clear);
            var5.fillOval(Math.round(32.0F - var14), Math.round(32.0F - var14), Math.round(var14 * 2.0F), Math.round(var14 * 2.0F));
            var5.setComposite(AlphaComposite.SrcOver);
         }

         var5.dispose();
         ResourceLocation var15 = Minecraft.getMinecraft()
            .getTextureManager()
            .getDynamicTextureLocation("jade_container_svg_" + var1.name().toLowerCase(), new DynamicTexture(var4));
         this.Jnn.put(var1, var15);
         return var15;
      }
   }

   private VectorIconDefinition edmX1(Category var1) {
      switch (var1) {
         case player:
            return new VectorIconDefinition(
               0.0F,
               0.0F,
               512.0F,
               512.0F,
               new VectorPath(
                  "M256,265.308c73.252,0,132.644-59.391,132.644-132.654C388.644,59.412,329.252,0,256,0c-73.262,0-132.643,59.412-132.643,132.654C123.357,205.917,182.738,265.308,256,265.308z",
                  true,
                  0.0F
               ),
               new VectorPath(
                  "M425.874,393.104c-5.922-35.474-36-84.509-57.552-107.465c-5.829-6.212-15.948-3.628-19.504-1.427c-27.04,16.672-58.782,26.399-92.819,26.399c-34.036,0-65.778-9.727-92.818-26.399c-3.555-2.201-13.675-4.785-19.505,1.427c-21.55,22.956-51.628,71.991-57.551,107.465C71.573,480.444,164.877,512,256,512C347.123,512,440.427,480.444,425.874,393.104z",
                  true,
                  0.0F
               )
            );
         case movement:
            return new VectorIconDefinition(
               0.0F,
               0.0F,
               24.0F,
               24.0F,
               new VectorPath(
                  "M15.6205 5.53997C15.6205 6.35551 14.9593 7.01665 14.1438 7.01665C13.3282 7.01665 12.6671 6.35551 12.6671 5.53997C12.6671 4.72442 13.3282 4.06328 14.1438 4.06328C14.9593 4.06328 15.6205 4.72442 15.6205 5.53997Z",
                  true,
                  1.26573F
               ),
               new VectorPath(
                  "M12.0933 9.23584C12.8705 9.23584 13.862 10.0475 13.862 10.0475C12.8953 12.1133 12.0933 12.8251 12.0933 14.4691H9.94097C11.6266 10.9973 11.1466 11.1017 12.0933 9.23584Z",
                  true,
                  0.0F
               ),
               new VectorPath(
                  "M9.94097 14.4691C8.64019 16.7679 5.86951 20.0585 5.86951 20.0585M9.94097 14.4691C10.6347 14.4691 11.3996 14.4691 12.0933 14.4691M9.94097 14.4691C11.6266 10.9973 11.1466 11.1017 12.0933 9.23584C12.8705 9.23584 13.862 10.0475 13.862 10.0475C12.8953 12.1133 12.0933 12.8251 12.0933 14.4691M12.0933 14.4691C13.4374 16.7679 11.4221 20.5695 11.4221 20.5695",
                  false,
                  2.21502F
               ),
               new VectorPath("M14.0381 10.0635L14.9763 13.9662L18.1305 11.9881", false, 1.91969F),
               new VectorPath("M12.0342 9.05542L9.18264 9.02772L7.54512 12.0129", false, 1.91969F)
            );
         case render:
            return new VectorIconDefinition(
               0.0F,
               0.0F,
               32.0F,
               32.0F,
               new VectorPath(
                  "M0 16q0.064 0.128 0.16 0.352t0.48 0.928 0.832 1.344 1.248 1.536 1.664 1.696 2.144 1.568 2.624 1.344 3.136 0.896 3.712 0.352 3.712-0.352 3.168-0.928 2.592-1.312 2.144-1.6 1.664-1.632 1.248-1.6 0.832-1.312 0.48-0.928l0.16-0.352q-0.032-0.128-0.16-0.352t-0.48-0.896-0.832-1.344-1.248-1.568-1.664-1.664-2.144-1.568-2.624-1.344-3.136-0.896-3.712-0.352-3.712 0.352-3.168 0.896-2.592 1.344-2.144 1.568-1.664 1.664-1.248 1.568-0.832 1.344-0.48 0.928zM10.016 16q0-2.464 1.728-4.224t4.256-1.76 4.256 1.76 1.76 4.224-1.76 4.256-4.256 1.76-4.256-1.76-1.728-4.256zM12 16q0 1.664 1.184 2.848t2.816 1.152 2.816-1.152 1.184-2.848-1.184-2.816-2.816-1.184-2.816 1.184l2.816 2.816h-4z",
                  true,
                  0.0F
               )
            );
         case minigames:
            return new VectorIconDefinition(
               0.0F,
               0.0F,
               24.0F,
               24.0F,
               new VectorPath(
                  "M7.99999 8.5C7.99999 7.94772 7.55227 7.5 6.99999 7.5C6.4477 7.5 5.99999 7.94772 5.99999 8.5V9H5.49999C4.9477 9 4.49999 9.44771 4.49999 10C4.49999 10.5523 4.9477 11 5.49999 11H5.99999V11.5C5.99999 12.0523 6.4477 12.5 6.99999 12.5C7.55227 12.5 7.99999 12.0523 7.99999 11.5V11H8.49999C9.05227 11 9.49999 10.5523 9.49999 10C9.49999 9.44771 9.05227 9 8.49999 9H7.99999V8.5Z",
                  true,
                  0.0F
               ),
               new VectorPath("M18 8C18 8.55229 17.5523 9 17 9C16.4477 9 16 8.55229 16 8C16 7.44772 16.4477 7 17 7C17.5523 7 18 7.44772 18 8Z", true, 0.0F),
               new VectorPath(
                  "M17 13C17.5523 13 18 12.5523 18 12C18 11.4477 17.5523 11 17 11C16.4477 11 16 11.4477 16 12C16 12.5523 16.4477 13 17 13Z", true, 0.0F
               ),
               new VectorPath(
                  "M16 10C16 10.5523 15.5523 11 15 11C14.4477 11 14 10.5523 14 10C14 9.44771 14.4477 9 15 9C15.5523 9 16 9.44771 16 10Z", true, 0.0F
               ),
               new VectorPath(
                  "M19 11C19.5523 11 20 10.5523 20 10C20 9.44771 19.5523 9 19 9C18.4477 9 18 9.44771 18 10C18 10.5523 18.4477 11 19 11Z", true, 0.0F
               ),
               new VectorPath(
                  "M12 3C10.1879 3 7.96237 3.25817 6.21782 3.5093C3.94305 3.83676 2.09096 5.51696 1.60993 7.7883C1.34074 9.05935 1.07694 10.5622 1.01649 11.8204C0.973146 12.7225 0.877981 13.9831 0.777155 15.1923C0.672256 16.4504 1.09148 17.7464 1.86079 18.6681C2.64583 19.6087 3.88915 20.2427 5.32365 19.8413C6.24214 19.5842 6.97608 18.9387 7.5205 18.3026C8.07701 17.6525 8.51992 16.9124 8.83535 16.3103C9.07821 15.8467 9.50933 15.5855 9.91539 15.5855H14.0846C14.4906 15.5855 14.9218 15.8467 15.1646 16.3103C15.4801 16.9124 15.923 17.6525 16.4795 18.3026C17.0239 18.9387 17.7578 19.5842 18.6763 19.8413C20.1108 20.2427 21.3541 19.6087 22.1392 18.6681C22.9085 17.7464 23.3277 16.4504 23.2228 15.1923C23.122 13.9831 23.0268 12.7225 22.9835 11.8204C22.923 10.5622 22.6592 9.05935 22.39 7.7883C21.909 5.51696 20.0569 3.83676 17.7821 3.5093C16.0376 3.25817 13.8121 3 12 3ZM6.50279 5.48889C8.22744 5.24063 10.3368 5 12 5C13.6632 5 15.7725 5.24063 17.4972 5.4889C18.965 5.70019 20.1311 6.77489 20.4334 8.20267C20.6967 9.44565 20.9332 10.8223 20.9858 11.9164C21.0309 12.856 21.1287 14.1463 21.2297 15.3585C21.2912 16.0956 21.0342 16.8708 20.6037 17.3866C20.1889 17.8836 19.7089 18.0534 19.2153 17.9153C18.8497 17.8129 18.4327 17.509 17.9989 17.0021C17.5771 16.5094 17.2144 15.9131 16.9362 15.3822C16.4043 14.3667 15.3482 13.5855 14.0846 13.5855H9.91539C8.65178 13.5855 7.59571 14.3667 7.06374 15.3822C6.78558 15.9131 6.42285 16.5094 6.00109 17.0021C5.56723 17.509 5.15027 17.8129 4.78463 17.9153C4.29109 18.0534 3.81102 17.8836 3.39625 17.3866C2.96576 16.8708 2.70878 16.0956 2.77024 15.3585C2.87131 14.1463 2.96904 12.856 3.01418 11.9164C3.06675 10.8223 3.30329 9.44565 3.56653 8.20267C3.86891 6.77489 5.03497 5.70019 6.50279 5.48889Z",
                  true,
                  0.0F
               )
            );
         case other:
            return new VectorIconDefinition(
               0.0F,
               0.0F,
               16.0F,
               16.0F,
               new VectorPath("M4 8C4 9.10457 3.10457 10 2 10C0.895431 10 0 9.10457 0 8C0 6.89543 0.895431 6 2 6C3.10457 6 4 6.89543 4 8Z", true, 0.0F),
               new VectorPath("M10 8C10 9.10457 9.10457 10 8 10C6.89543 10 6 9.10457 6 8C6 6.89543 6.89543 6 8 6C9.10457 6 10 6.89543 10 8Z", true, 0.0F),
               new VectorPath("M14 10C15.1046 10 16 9.10457 16 8C16 6.89543 15.1046 6 14 6C12.8954 6 12 6.89543 12 8C12 9.10457 12.8954 10 14 10Z", true, 0.0F)
            );
         case friends:
            return new VectorIconDefinition(
               0.0F,
               0.0F,
               45.902F,
               45.902F,
               new VectorPath(
                  "M43.162,26.681c-1.564-1.578-3.631-2.539-5.825-2.742c1.894-1.704,3.089-4.164,3.089-6.912c0-5.141-4.166-9.307-9.308-9.307c-4.911,0-8.932,3.804-9.281,8.625c4.369,1.89,7.435,6.244,7.435,11.299c0,1.846-0.42,3.65-1.201,5.287c1.125,0.588,2.162,1.348,3.066,2.26c2.318,2.334,3.635,5.561,3.61,8.851l-0.002,0.067l-0.002,0.057l-0.082,1.557h11.149l0.092-12.33C45.921,30.878,44.936,28.466,43.162,26.681z",
                  true,
                  0.0F
               ),
               new VectorPath(
                  "M23.184,34.558c1.893-1.703,3.092-4.164,3.092-6.912c0-5.142-4.168-9.309-9.309-9.309c-5.142,0-9.309,4.167-9.309,9.309c0,2.743,1.194,5.202,3.084,6.906c-4.84,0.375-8.663,4.383-8.698,9.318l-0.092,1.853h14.153h15.553l0.092-1.714c0.018-2.514-0.968-4.926-2.741-6.711C27.443,35.719,25.377,34.761,23.184,34.558z",
                  true,
                  0.0F
               ),
               new VectorPath(
                  "M6.004,11.374v3.458c0,1.432,1.164,2.595,2.597,2.595c1.435,0,2.597-1.163,2.597-2.595v-3.458h3.454c1.433,0,2.596-1.164,2.596-2.597c0-1.432-1.163-2.596-2.596-2.596h-3.454V2.774c0-1.433-1.162-2.595-2.597-2.595c-1.433,0-2.597,1.162-2.597,2.595V6.18H2.596C1.161,6.18,0,7.344,0,8.776c0,1.433,1.161,2.597,2.596,2.597H6.004z",
                  true,
                  0.0F
               )
            );
         case profiles:
            return new VectorIconDefinition(
               0.0F,
               0.0F,
               24.0F,
               24.0F,
               new VectorPath(
                  "M12 5.5C12 6.88071 10.8807 8 9.5 8C8.11929 8 7 6.88071 7 5.5M12 5.5C12 4.11929 10.8807 3 9.5 3C8.11929 3 7 4.11929 7 5.5M12 5.5H21M7 5.5H3M19 12C19 13.3807 17.8807 14.5 16.5 14.5C15.1193 14.5 14 13.3807 14 12M19 12C19 10.6193 17.8807 9.5 16.5 9.5C15.1193 9.5 14 10.6193 14 12M19 12H21M14 12H3M10 18.5C10 19.8807 8.88071 21 7.5 21C6.11929 21 5 19.8807 5 18.5M10 18.5C10 17.1193 8.88071 16 7.5 16C6.11929 16 5 17.1193 5 18.5M10 18.5H21M5 18.5H3",
                  false,
                  2.0F
               )
            );
         case themes:
            return new VectorIconDefinition(
               3.0F,
               3.0F,
               18.0F,
               18.0F,
               new VectorPath(
                  "M15.5 8.5H15.51M10.5 7.5H10.51M7.5 11.5H7.51M12 21C7.02944 21 3 16.9706 3 12C3 7.02944 7.02944 3 12 3C16.9706 3 21 7.02944 21 12C21 13.6569 19.6569 15 18 15H17.4C17.0284 15 16.8426 15 16.6871 15.0246C15.8313 15.1602 15.1602 15.8313 15.0246 16.6871C15 16.8426 15 17.0284 15 17.4V18C15 19.6569 13.6569 21 12 21ZM16 8.5C16 8.77614 15.7761 9 15.5 9C15.2239 9 15 8.77614 15 8.5C15 8.22386 15.2239 8 15.5 8C15.7761 8 16 8.22386 16 8.5ZM11 7.5C11 7.77614 10.7761 8 10.5 8C10.2239 8 10 7.77614 10 7.5C10 7.22386 10.2239 7 10.5 7C10.7761 7 11 7.22386 11 7.5ZM8 11.5C8 11.7761 7.77614 12 7.5 12C7.22386 12 7 11.7761 7 11.5C7 11.2239 7.22386 11 7.5 11C7.77614 11 8 11.2239 8 11.5Z",
                  false,
                  2.0F
               )
            );
         case client:
            return new VectorIconDefinition(
               0.0F,
               0.0F,
               24.0F,
               24.0F,
               new VectorPath(
                  "M12 8.25C9.92894 8.25 8.25 9.92893 8.25 12C8.25 14.0711 9.92894 15.75 12 15.75C14.0711 15.75 15.75 14.0711 15.75 12C15.75 9.92893 14.0711 8.25 12 8.25ZM9.75 12C9.75 10.7574 10.7574 9.75 12 9.75C13.2426 9.75 14.25 10.7574 14.25 12C14.25 13.2426 13.2426 14.25 12 14.25C10.7574 14.25 9.75 13.2426 9.75 12Z",
                  true,
                  0.0F
               ),
               new VectorPath(
                  "M11.9747 1.25C11.5303 1.24999 11.1592 1.24999 10.8546 1.27077C10.5375 1.29241 10.238 1.33905 9.94761 1.45933C9.27379 1.73844 8.73843 2.27379 8.45932 2.94762C8.31402 3.29842 8.27467 3.66812 8.25964 4.06996C8.24756 4.39299 8.08454 4.66251 7.84395 4.80141C7.60337 4.94031 7.28845 4.94673 7.00266 4.79568C6.64714 4.60777 6.30729 4.45699 5.93083 4.40743C5.20773 4.31223 4.47642 4.50819 3.89779 4.95219C3.64843 5.14353 3.45827 5.3796 3.28099 5.6434C3.11068 5.89681 2.92517 6.21815 2.70294 6.60307L2.67769 6.64681C2.45545 7.03172 2.26993 7.35304 2.13562 7.62723C1.99581 7.91267 1.88644 8.19539 1.84541 8.50701C1.75021 9.23012 1.94617 9.96142 2.39016 10.5401C2.62128 10.8412 2.92173 11.0602 3.26217 11.2741C3.53595 11.4461 3.68788 11.7221 3.68786 12C3.68785 12.2778 3.53592 12.5538 3.26217 12.7258C2.92169 12.9397 2.62121 13.1587 2.39007 13.4599C1.94607 14.0385 1.75012 14.7698 1.84531 15.4929C1.88634 15.8045 1.99571 16.0873 2.13552 16.3727C2.26983 16.6469 2.45535 16.9682 2.67758 17.3531L2.70284 17.3969C2.92507 17.7818 3.11058 18.1031 3.28089 18.3565C3.45817 18.6203 3.64833 18.8564 3.89769 19.0477C4.47632 19.4917 5.20763 19.6877 5.93073 19.5925C6.30717 19.5429 6.647 19.3922 7.0025 19.2043C7.28833 19.0532 7.60329 19.0596 7.8439 19.1986C8.08452 19.3375 8.24756 19.607 8.25964 19.9301C8.27467 20.3319 8.31403 20.7016 8.45932 21.0524C8.73843 21.7262 9.27379 22.2616 9.94761 22.5407C10.238 22.661 10.5375 22.7076 10.8546 22.7292C11.1592 22.75 11.5303 22.75 11.9747 22.75H12.0252C12.4697 22.75 12.8407 22.75 13.1454 22.7292C13.4625 22.7076 13.762 22.661 14.0524 22.5407C14.7262 22.2616 15.2616 21.7262 15.5407 21.0524C15.686 20.7016 15.7253 20.3319 15.7403 19.93C15.7524 19.607 15.9154 19.3375 16.156 19.1985C16.3966 19.0596 16.7116 19.0532 16.9974 19.2042C17.3529 19.3921 17.6927 19.5429 18.0692 19.5924C18.7923 19.6876 19.5236 19.4917 20.1022 19.0477C20.3516 18.8563 20.5417 18.6203 20.719 18.3565C20.8893 18.1031 21.0748 17.7818 21.297 17.3969L21.3223 17.3531C21.5445 16.9682 21.7301 16.6468 21.8644 16.3726C22.0042 16.0872 22.1135 15.8045 22.1546 15.4929C22.2498 14.7697 22.0538 14.0384 21.6098 13.4598C21.3787 13.1586 21.0782 12.9397 20.7378 12.7258C20.464 12.5538 20.3121 12.2778 20.3121 11.9999C20.3121 11.7221 20.464 11.4462 20.7377 11.2742C21.0783 11.0603 21.3788 10.8414 21.6099 10.5401C22.0539 9.96149 22.2499 9.23019 22.1547 8.50708C22.1136 8.19546 22.0043 7.91274 21.8645 7.6273C21.7302 7.35313 21.5447 7.03183 21.3224 6.64695L21.2972 6.60318C21.0749 6.21825 20.8894 5.89688 20.7191 5.64347C20.5418 5.37967 20.3517 5.1436 20.1023 4.95225C19.5237 4.50826 18.7924 4.3123 18.0692 4.4075C17.6928 4.45706 17.353 4.60782 16.9975 4.79572C16.7117 4.94679 16.3967 4.94036 16.1561 4.80144C15.9155 4.66253 15.7524 4.39297 15.7403 4.06991C15.7253 3.66808 15.686 3.2984 15.5407 2.94762C15.2616 2.27379 14.7262 1.73844 14.0524 1.45933C13.762 1.33905 13.4625 1.29241 13.1454 1.27077C12.8407 1.24999 12.4697 1.24999 12.0252 1.25H11.9747Z",
                  true,
                  0.0F
               )
            );
         case combat:
         default:
            return new VectorIconDefinition(
               0.0F,
               0.0F,
               290.226F,
               290.226F,
               new VectorPath(
                  "M63.951,243.575c-1.945-3.578-4.401-6.907-7.363-9.869c-3.106-3.102-6.626-5.633-10.4-7.63c-4.51-2.387-0.945-7.5-0.945-7.5c4.616-7.023,8.825-14.079,12.305-20.226l-23.363-23.344H11.504c-4.362,0-7.898-3.539-7.898-7.902c0-4.361,3.536-7.9,7.898-7.9h25.947c2.1,0,4.107,0.832,5.588,2.312l85.379,85.291c1.483,1.483,2.315,3.495,2.315,5.589v26.073c0,4.365-3.537,7.897-7.9,7.897c-4.367,0-7.904-3.531-7.904-7.897v-22.798l-23.27-23.24c-6.281,3.707-13.582,8.252-20.816,13.25C70.842,245.679,66.698,248.629,63.951,243.575z",
                  true,
                  0.0F
               ),
               new VectorPath(
                  "M26.61,237.102c-7.106,0-13.784,2.764-18.812,7.784c-5.019,5.015-7.782,11.686-7.782,18.778c0,7.097,2.764,13.762,7.782,18.776c5.027,5.016,11.706,7.783,18.812,7.785c7.102,0,13.781-2.77,18.804-7.785c5.023-5.015,7.79-11.682,7.79-18.776c0-7.093-2.768-13.764-7.79-18.778C40.392,239.866,33.712,237.102,26.61,237.102z",
                  true,
                  0.0F
               ),
               new VectorPath(
                  "M100.985,182.318c-3.502,3.499-9.232,3.499-12.734,0.001l-8.81-8.801c-3.502-3.498-3.502-9.223,0-12.721L229.832,10.564c3.502-3.498,10.401-6.727,15.33-7.175l36.862-3.352c4.93-0.448,8.596,3.218,8.148,8.148l-3.346,36.791c-0.448,4.93-3.68,11.825-7.182,15.324l-150.4,150.251c-3.502,3.498-9.232,3.498-12.734,0l-8.822-8.813c-3.502-3.498-3.502-9.223,0-12.722L233.608,63.213c1.854-1.848,1.856-4.852,0.003-6.702c-1.848-1.853-4.853-1.853-6.709-0.002L100.985,182.318z",
                  true,
                  0.0F
               )
            );
      }
   }

   private Float psR7(String var1) {
      ArrayList var2 = new ArrayList();
      Matcher var3 = Pattern.compile("[A-Za-z]|[-+]?(?:\\d*\\.\\d+|\\d+\\.?)(?:[eE][-+]?\\d+)?").matcher(var1);

      while (var3.find()) {
         var2.add(var3.group());
      }

      Float var4 = new Float(0);
      int var5 = 0;
      char var6 = ' ';
      char var7 = ' ';
      float var8 = 0.0F;
      float var9 = 0.0F;
      float var10 = 0.0F;
      float var11 = 0.0F;
      float var12 = 0.0F;
      float var13 = 0.0F;
      float var14 = 0.0F;
      float var15 = 0.0F;

      while (var5 < var2.size()) {
         String var16 = (String)var2.get(var5);
         if (this.PIBgM(var16)) {
            var6 = var16.charAt(0);
            var5++;
         }

         boolean var17 = Character.isLowerCase(var6);
         char var18 = Character.toUpperCase(var6);
         if (var18 == 'Z') {
            var4.closePath();
            var8 = var10;
            var9 = var11;
            var7 = var18;
         } else if (var18 == 'M') {
            boolean var45 = true;

            while (var5 + 1 < var2.size() && !this.PIBgM((String)var2.get(var5))) {
               float var50 = this.yRwtQ(var2, var5++);
               float var54 = this.yRwtQ(var2, var5++);
               if (var17) {
                  var50 += var8;
                  var54 += var9;
               }

               if (var45) {
                  var4.moveTo(var50, var54);
                  var10 = var50;
                  var11 = var54;
                  var45 = false;
               } else {
                  var4.lineTo(var50, var54);
               }

               var8 = var50;
               var9 = var54;
            }

            var7 = var18;
         } else {
            for (; var5 < var2.size() && !this.PIBgM((String)var2.get(var5)); var7 = var18) {
               if (var18 == 'L') {
                  float var19 = this.yRwtQ(var2, var5++);
                  float var20 = this.yRwtQ(var2, var5++);
                  if (var17) {
                     var19 += var8;
                     var20 += var9;
                  }

                  var4.lineTo(var19, var20);
                  var8 = var19;
                  var9 = var20;
               } else if (var18 == 'H') {
                  float var39 = this.yRwtQ(var2, var5++);
                  if (var17) {
                     var39 += var8;
                  }

                  var4.lineTo(var39, var9);
                  var8 = var39;
               } else if (var18 == 'V') {
                  float var40 = this.yRwtQ(var2, var5++);
                  if (var17) {
                     var40 += var9;
                  }

                  var4.lineTo(var8, var40);
                  var9 = var40;
               } else if (var18 == 'C') {
                  float var41 = this.yRwtQ(var2, var5++);
                  float var46 = this.yRwtQ(var2, var5++);
                  float var21 = this.yRwtQ(var2, var5++);
                  float var22 = this.yRwtQ(var2, var5++);
                  float var23 = this.yRwtQ(var2, var5++);
                  float var24 = this.yRwtQ(var2, var5++);
                  if (var17) {
                     var41 += var8;
                     var46 += var9;
                     var21 += var8;
                     var22 += var9;
                     var23 += var8;
                     var24 += var9;
                  }

                  var4.curveTo(var41, var46, var21, var22, var23, var24);
                  var12 = var21;
                  var13 = var22;
                  var8 = var23;
                  var9 = var24;
               } else if (var18 == 'S') {
                  float var42 = var7 != 'C' && var7 != 'S' ? var8 : var8 * 2.0F - var12;
                  float var47 = var7 != 'C' && var7 != 'S' ? var9 : var9 * 2.0F - var13;
                  float var51 = this.yRwtQ(var2, var5++);
                  float var55 = this.yRwtQ(var2, var5++);
                  float var58 = this.yRwtQ(var2, var5++);
                  float var59 = this.yRwtQ(var2, var5++);
                  if (var17) {
                     var51 += var8;
                     var55 += var9;
                     var58 += var8;
                     var59 += var9;
                  }

                  var4.curveTo(var42, var47, var51, var55, var58, var59);
                  var12 = var51;
                  var13 = var55;
                  var8 = var58;
                  var9 = var59;
               } else if (var18 == 'Q') {
                  float var43 = this.yRwtQ(var2, var5++);
                  float var48 = this.yRwtQ(var2, var5++);
                  float var52 = this.yRwtQ(var2, var5++);
                  float var56 = this.yRwtQ(var2, var5++);
                  if (var17) {
                     var43 += var8;
                     var48 += var9;
                     var52 += var8;
                     var56 += var9;
                  }

                  var4.quadTo(var43, var48, var52, var56);
                  var14 = var43;
                  var15 = var48;
                  var8 = var52;
                  var9 = var56;
               } else {
                  if (var18 != 'T') {
                     break;
                  }

                  float var44 = var7 != 'Q' && var7 != 'T' ? var8 : var8 * 2.0F - var14;
                  float var49 = var7 != 'Q' && var7 != 'T' ? var9 : var9 * 2.0F - var15;
                  float var53 = this.yRwtQ(var2, var5++);
                  float var57 = this.yRwtQ(var2, var5++);
                  if (var17) {
                     var53 += var8;
                     var57 += var9;
                  }

                  var4.quadTo(var44, var49, var53, var57);
                  var14 = var44;
                  var15 = var49;
                  var8 = var53;
                  var9 = var57;
               }
            }
         }
      }

      return var4;
   }

   private boolean PIBgM(String var1) {
      return var1.length() == 1 && Character.isLetter(var1.charAt(0));
   }

   private float yRwtQ(List<String> var1, int var2) {
      return java.lang.Float.parseFloat((String)var1.get(var2));
   }

   private void fillRoundedRect(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (!(var3 <= 0.0F) && !(var4 <= 0.0F)) {
         RoundedRect.drawRoundedRectArgb(var1, var2, var3, var4, Math.max(0.0F, var5), var6);
      }
   }
}
