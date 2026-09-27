// Jade recovery: original class: jade.deps.eLz.QhUsfUP6
package jade.client.hook;

import jade.client.common.ClientUtils;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.IMinecraft;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.module.client.Gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public final class UpdateNotification implements IMinecraft {
   private static final long JHamu = 180L;
   private static final long FADE_STEP_DURATION = 100L;
   private static final long TEXT_SWAP_DURATION = 260L;
   private static final long TITLE_HOLD_DURATION = 1800L;
   private static final long LINE_FADE_DURATION = 200L;
   private static final long SUBTITLE_HOLD_DURATION = 1800L;
   private static final long qwnH = 260L;
   private static final long cajO = 180L;
   private static final long TOTAL_DURATION = 4780L;
   private static final float gdm = 32.0F;
   private static final float ICON_TEXT_GAP = 12.0F;
   private static final float MIN_PILL_WIDTH = 56.0F;
   private static final float XEpx = 32.0F;
   private static final int ICON_BACKGROUND_COLOR = -14671066;
   private static final int KrtJ = -15131874;
   private static final int ono = -1;
   private static ResourceLocation resourceLocation;

   private static long Zd55(String var0) {
      long var1 = 2340L;
      return var0 != null && !var0.isEmpty() ? var1 + 200L + 1800L : var1;
   }

   private static long getNotificationDuration(String var0) {
      return Zd55(var0) + 260L + 180L;
   }

   private UpdateNotification() {
   }

   public static long HUOiM() {
      return 4780L;
   }

   public static void Ia86(long var0) {
      LkDv(var0, "New Update |Available!|", "Relaunch now to install!");
   }

   public static void LkDv(long var0, String var2, String var3) {
      if (mc != null && !mc.gameSettings.showDebugInfo) {
         long var4 = System.currentTimeMillis() - var0;
         if (var4 >= 0L && var4 <= getNotificationDuration(var3)) {
            ScaledResolution var6 = new ScaledResolution(mc);
            float var7 = var6.getScaledWidth() / 2.0F;
            float var8 = Math.max(8.0F, var6.getScaledHeight() * 0.035F);
            KikN7(var7, var8, var4, var2, var3);
         }
      }
   }

   public static void renderDefaultNotification(float var0, float var1, long var2) {
      KikN7(var0, var1, var2, "New Update |Available!|", "Relaunch now to install!");
   }

   public static void KikN7(float var0, float var1, long var2, String var4, String var5) {
      var4 = SmlSwl(var4, "New Update |Available!|");
      var5 = SmlSwl(var5, null);
      long var6 = Zd55(var5);
      float var8 = IglqB0(var2, 0L, 180L);
      float var9 = IglqB0(var2, 280L, 260L);
      float var10 = IglqB0(var2, var6, 260L);
      float var11 = IglqB0(var2, var6 + 260L, 180L);
      float var12 = easeOutCubic(var9) * (1.0F - easeInCubic(var10));
      float var13 = easeOutBack(var8);
      if (var11 > 0.0F) {
         var13 *= 1.0F - easeInCubic(var11);
      }

      if (!(var13 <= 0.01F)) {
         float var14 = getPillWidth(var4, var5);
         float var15 = var0 - 16.0F;
         float var16 = var0 - var14 / 2.0F;
         float var17 = var15 + (var16 - var15) * easeOutCubic(var9);
         var17 += (var15 - var16) * easeInCubic(var10);
         float var18 = var17 + 16.0F;
         float var19 = 32.0F * var13;
         float var20 = var18 - var19 / 2.0F;
         float var21 = var1 + (32.0F - var19) / 2.0F;
         if (var12 > 0.001F) {
            Qwy8(var17, var1, var12, var2, var14, var4, var5);
         }

         RoundedRect.drawRoundedRectArgb(var20, var21, var19, var19, var19 / 2.0F, -14671066);
         float var22 = var19 * 0.58F;
         RenderUtils.drawIconTexture(AOJo(), var18 - var22 / 2.0F, var1 + 16.0F - var22 / 2.0F, Math.round(var22), getAccentColor());
      }
   }

   private static void Qwy8(float var0, float var1, float var2, long var3, float var5, String var6, String var7) {
      float var8 = ZJPoU(var2, var5);
      RoundedRect.drawRoundedRectArgb(var0, var1, var8, 32.0F, 16.0F, -15131874);
      IFont var9 = getRegularFont();
      float var10 = var0 + 32.0F + 12.0F;
      float var11 = centerTextVertically(var1, 32.0F, var9);
      float var12 = Math.max(0.0F, var8 - 32.0F - 24.0F);
      if (!(var12 <= 1.0F)) {
         RenderUtils.pushScissorRect(var10, var1, var12, 32.0);
         float var13 = IglqB0(var3, 2340L, 200L);
         float var14 = var11 - easeInCubic(var13) * 32.0F;
         float var15 = var11 + 32.0F - easeOutCubic(var13) * 32.0F;
         if (var7 == null) {
            drawAccentText(centerTextHorizontally(var10, var12, var6), var11, var6);
         } else {
            if (var13 < 1.0F) {
               drawAccentText(centerTextHorizontally(var10, var12, var6), var14, var6);
            }

            if (var13 > 0.0F) {
               drawAccentText(centerTextHorizontally(var10, var12, var7), var15, var7);
            }
         }

         RenderUtils.restoreScissorState();
      }
   }

   private static float centerTextHorizontally(float var0, float var1, String var2) {
      return var0 + Math.max(0.0F, var1 - LeqW(var2)) / 2.0F;
   }

   private static void drawAccentText(float var0, float var1, String var2) {
      IFont var3 = getRegularFont();
      IFont var4 = getBoldFont();
      int var5 = getAccentColor();
      int var6 = var2.indexOf(124);
      int var7 = var6 < 0 ? -1 : var2.indexOf(124, var6 + 1);
      if (var6 >= 0 && var7 > var6) {
         String var8 = var2.substring(0, var6);
         String var9 = var2.substring(var6 + 1, var7);
         String var10 = var2.substring(var7 + 1);
         var3.drawString(var8, var0, var1, -1, true);
         float var11 = var0 + var3.getStringWidth(var8);
         var4.drawString(var9, var11, var1, ClientUtils.YVVZ(var5, 255), true);
         var3.drawString(var10, var11 + var4.getStringWidth(var9), var1, -1, true);
      } else {
         var3.drawString(var2, var0, var1, -1, true);
      }
   }

   private static float ZJPoU(float var0, float var1) {
      return 32.0F + (var1 - 32.0F) * var0;
   }

   private static float getPillWidth(String var0, String var1) {
      return Math.max(56.0F, 56.0F + getMaxLineWidth(var0, var1));
   }

   private static float getMaxLineWidth(String var0, String var1) {
      float var2 = LeqW(var0);
      float var3 = var1 == null ? 0.0F : LeqW(var1);
      return Math.max(var2, var3);
   }

   private static float LeqW(String var0) {
      IFont var1 = getRegularFont();
      IFont var2 = getBoldFont();
      int var3 = var0.indexOf(124);
      int var4 = var3 < 0 ? -1 : var0.indexOf(124, var3 + 1);
      return var3 >= 0 && var4 > var3
         ? var1.getStringWidth(var0.substring(0, var3)) + var2.getStringWidth(var0.substring(var3 + 1, var4)) + var1.getStringWidth(var0.substring(var4 + 1))
         : var1.getStringWidth(var0);
   }

   private static String SmlSwl(String var0, String var1) {
      return var0 != null && !var0.trim().isEmpty() ? var0 : var1;
   }

   private static float centerTextVertically(float var0, float var1, IFont var2) {
      int var3 = var2.getTextTopOffset();
      int var4 = var2.getTextBottomOffset();
      return var0 + (var1 - (var4 - var3)) / 2.0F - var3;
   }

   private static IFont getRegularFont() {
      return FontManager.getHudRenderer("Modern", 0.96F);
   }

   private static IFont getBoldFont() {
      return FontManager.getHudRenderer("Bold", 0.96F);
   }

   private static int getAccentColor() {
      return Gui.accent == null ? -15030151 : Gui.accent.getArgb();
   }

   private static ResourceLocation AOJo() {
      if (resourceLocation != null) {
         return resourceLocation;
      } else {
         byte var0 = 96;
         BufferedImage var1 = new BufferedImage(var0, var0, 2);
         Graphics2D var2 = var1.createGraphics();
         var2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         var2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
         var2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
         float var3 = var0 / 64.0F;
         Polygon var4 = createScaledPolygon(var3, 32, 6, 54, 28, 32, 58, 10, 28);
         Polygon var5 = createScaledPolygon(var3, 32, 6, 18, 38, 46, 38);
         var2.setColor(new Color(-1929379841, true));
         var2.fill(var4);
         var2.setColor(new Color(1090519039, true));
         var2.fill(var5);
         var2.setStroke(new BasicStroke(2.2F * var3, 1, 1));
         var2.setColor(Color.WHITE);
         var2.draw(var4);
         var2.draw(var5);
         var2.dispose();
         resourceLocation = mc.getTextureManager().getDynamicTextureLocation("jade_announcements_logo", new DynamicTexture(var1));
         return resourceLocation;
      }
   }

   private static Polygon createScaledPolygon(float var0, int... var1) {
      Polygon var2 = new Polygon();

      for (byte var3 = 0; var3 + 1 < var1.length; var3 += 2) {
         var2.addPoint(Math.round(var1[var3] * var0), Math.round(var1[var3 + 1] * var0));
      }

      return var2;
   }

   private static float IglqB0(long var0, long var2, long var4) {
      return clampProgress((float)(var0 - var2) / (float)var4);
   }

   private static float clampProgress(float var0) {
      return Math.max(0.0F, Math.min(1.0F, var0));
   }

   private static float easeOutCubic(float var0) {
      float var1 = 1.0F - clampProgress(var0);
      return 1.0F - var1 * var1 * var1;
   }

   private static float easeInCubic(float var0) {
      float var1 = clampProgress(var0);
      return var1 * var1 * var1;
   }

   private static float easeOutBack(float var0) {
      float var1 = clampProgress(var0) - 1.0F;
      float var2 = 1.70158F;
      return 1.0F + var1 * var1 * ((var2 + 1.0F) * var1 + var2);
   }
}
