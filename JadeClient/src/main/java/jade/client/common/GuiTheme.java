// Jade recovery: original class: jade.deps.eLz.ACEI48h
package jade.client.common;

import jade.client.module.client.Gui;

public final class GuiTheme {
   public static final int PANEL_WIDTH = 185;
   public static final int KlA = 21;
   public static final int DROPDOWN_HEADER_HEIGHT = 23;
   public static final int SMALL_SPACING = 4;
   public static final int MEDIUM_SPACING = 7;
   public static final int LARGE_SPACING = 9;
   public static final int buVe = 900;
   public static final int ncmo = 220;
   public static final int PANEL_HEIGHT = 260;
   public static final int PANEL_BACKGROUND_COLOR = -1945499126;
   public static final int ROW_BACKGROUND_COLOR = -1509357303;
   public static final int SOLID_BACKGROUND_COLOR = -435154928;
   public static final int INACTIVE_ELEMENT_COLOR = 613258909;
   public static final int SLc = 950974140;
   public static final int UPLK = 822083583;
   public static final int OVERLAY_ALPHA = 124;
   public static final float YWD = 1.35F;
   public static final int vAtrRs = -1182988;
   public static final int MUTED_TEXT_COLOR = -4208434;

   private GuiTheme() {
   }

   public static int xGoxa() {
      return Gui.getAccentColor();
   }

   public static int withAlpha(int var0) {
      return xGoxa() & 16777215 | var0 << 24;
   }

   public static int getShadedAccentColor() {
      int var0 = xGoxa();
      return 0xFF000000 | Math.round((var0 >> 16 & 0xFF) * 0.59F) << 16 | Math.round((var0 >> 8 & 0xFF) * 0.59F) << 8 | Math.round((var0 & 0xFF) * 0.59F);
   }

   public static float ANLBFU(float var0) {
      float var1 = Math.max(0.0F, Math.min(1.0F, var0));
      float var2 = 0.0F;
      float var3 = 1.0F;
      float var4 = var1;

      for (int var5 = 0; var5 < 14; var5++) {
         var4 = (var2 + var3) * 0.5F;
         float var6 = 1.0F - var4;
         float var7 = 3.0F * var6 * var6 * var4 * 0.22F + 3.0F * var6 * var4 * var4 * 0.36F + var4 * var4 * var4;
         if (var7 < var1) {
            var2 = var4;
         } else {
            var3 = var4;
         }
      }

      return var1 != 0.0F && var1 != 1.0F ? 1.0F - (float)Math.pow(1.0F - var4, 3.0) : var1;
   }
}
