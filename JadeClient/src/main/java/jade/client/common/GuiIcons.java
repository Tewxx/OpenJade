// Jade recovery: original class: jade.deps.eLz.dJXPxHOSH
package jade.client.common;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public final class GuiIcons {
   private GuiIcons() {
   }

   public static void fillRect(float var0, float var1, float var2, float var3, int var4) {
      if (!(var2 <= 0.0F) && !(var3 <= 0.0F) && var4 >>> 24 != 0) {
         boolean var5 = GL11.glIsEnabled(3008);
         GlStateManager.disableAlpha();

         try {
            RenderUtils.XNRNki(var0, var1, var0 + var2, var1 + var3, var4);
         } finally {
            if (var5) {
               GlStateManager.enableAlpha();
            }
         }
      }
   }

   public static void drawRectBorder(float var0, float var1, float var2, float var3, int var4) {
      if (!(var2 <= 0.0F) && !(var3 <= 0.0F)) {
         fillRect(var0, var1, var2, Math.min(1.0F, var3), var4);
         if (var3 > 1.0F) {
            fillRect(var0, var1 + Math.max(1.0F, var3 - 1.0F), var2, Math.min(1.0F, var3 - 1.0F), var4);
         }

         if (var3 > 2.0F) {
            fillRect(var0, var1 + 1.0F, Math.min(1.0F, var2), var3 - 2.0F, var4);
            if (var2 > 1.0F) {
               fillRect(var0 + Math.max(1.0F, var2 - 1.0F), var1 + 1.0F, Math.min(1.0F, var2 - 1.0F), var3 - 2.0F, var4);
            }
         }
      }
   }

   public static void drawHoverableButton(RelationsClickGui var0, GuiRect var1, String var2, int var3, int var4, boolean var5) {
      drawButtonWithHover(var0, var1, var2, var5, var1.contains(var3, var4) ? 1.0F : 0.0F);
   }

   public static void drawButtonWithHover(RelationsClickGui var0, GuiRect var1, String var2, boolean var3, float var4) {
      fillRect(var1.x, var1.ufe, var1.busF, var1.HfS, var3 ? GuiTheme.withAlpha(70) : 603979776);
      fillRect(var1.x, var1.ufe, var1.busF, var1.HfS, Math.round(48.0F * var4) << 24 | 16777215);
      drawRectBorder(var1.x, var1.ufe, var1.busF, var1.HfS, 613258909);
      var0.drawCenteredText(var2, var1.getCenterX(), var0.centeredTextY(var1.ufe, var1.HfS, var0.settingFont()), -1182988, var0.settingFont());
   }

   public static void drawIconButton(RelationsClickGui var0, GuiRect var1, String var2, String var3, int var4, int var5) {
      if (var1.contains(var4, var5)) {
         fillRect(var1.x, var1.ufe, var1.busF, var1.HfS, 822083583);
      }

      int var6 = var1.getCenterX();
      int var7 = var1.getCenterY();
      if (var2.equals("search")) {
         var0.drawSearchIcon(var6 - 5, var7 - 5, 10, -1182988);
      } else if (var2.equals("edit")) {
         RenderUtils.drawIconTexture(RenderUtils.getIconTexture("/assets/jade/textures/gui/edit.png"), var6 - 5, var7 - 5, 10, -1182988);
      } else if (var2.equals("save")) {
         VectorIconPainter.drawSaveIcon(var6, var7, -1182988);
      } else if (var2.equals("refresh")) {
         VectorIconPainter.drawRefreshIcon(var6, var7, -1182988);
      } else if (var2.equals("folder")) {
         drawRectBorder(var6 - 6, var7 - 3, 12.0F, 8.0F, -1182988);
         RenderUtils.XNRNki(var6 - 6, var7 - 5, var6 - 1, var7 - 3, -1182988);
      } else {
         var0.drawCenteredText(var2, var6, var0.centeredTextY(var1.ufe, var1.HfS, var0.settingFont()), -1182988, var0.settingFont());
      }

      if (var1.contains(var4, var5) && var3 != null) {
         var0.drawSmall(var3, var1.x + var1.busF - var0.textWidth(var3, var0.settingFont()), var1.ufe - 13, -1182988);
      }
   }

   public static void drawPowerButton(JadeClickGui var0, GuiRect var1, boolean var2, int var3, int var4) {
      boolean var5 = var1.contains(var3, var4);
      if (var5) {
         fillRect(var1.x, var1.ufe, var1.busF, var1.HfS, 822083583);
      }

      int var6 = var5 ? -1182988 : GuiTheme.xGoxa();
      int var7 = var1.getCenterX();
      int var8 = var1.getCenterY();
      VectorIconPainter.drawPowerOrPanelIcon(var7, var8, var2, var6);
      if (var5) {
         String var9 = var2 ? "Disconnect" : "Edit HUD";
         var0.drawSmall(var9, var1.x + var1.busF - var0.textWidth(var9, var0.settingFont()), var1.ufe - 15, -1182988);
      }
   }
}
