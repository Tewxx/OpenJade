// Jade recovery: original class: jade.deps.eLz.JuqbEq2Jgo
package jade.client.core;

import jade.client.common.MiddleClickFriend;
import jade.client.common.OverlayConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class CpsDisplay {
   private static final String[] SPb = new String[]{"LMB", "RMB"};
   private static final int WIDTH = 34;
   private static final int fku = 22;
   private final Minecraft mc = Minecraft.getMinecraft();
   private final int mouseButton;
   private final int Bif5;
   private final int offsetY;
   private final PressAnimation pressAnimation = new PressAnimation();

   public CpsDisplay(int var1, int var2, int var3) {
      this.mouseButton = var1;
      this.Bif5 = var2;
      this.offsetY = var3;
   }

   public void drawCps(int var1, int var2, int var3) {
      boolean var4 = Mouse.isButtonDown(this.mouseButton);
      if (this.pressAnimation.hasStateChanged(var4)) {
         this.pressAnimation.updateState(var4, System.currentTimeMillis());
      }

      int var5 = this.pressAnimation.getAlpha(var4, System.currentTimeMillis());
      double var6 = this.pressAnimation.JCj0(var4, System.currentTimeMillis());
      int var8 = var1 + this.Bif5;
      int var9 = var2 + this.offsetY;
      Gui.drawRect(var8, var9, var8 + 34, var9 + 22, ColorUtils.translucentGray(var5));
      if (OverlayConfig.outlineEnabled) {
         drawBorder(var8, var9, ColorUtils.withFullAlpha(var3));
      }

      this.mc.fontRendererObj.drawString(SPb[this.mouseButton], var8 + 8, var9 + 4, ColorUtils.scaleRgb(var3, var6));
      String var10 = MiddleClickFriend.getLeftCps() + " CPS";
      String var11 = MiddleClickFriend.getRightCps() + " CPS";
      boolean var12 = this.mouseButton == 0;
      String var13 = var12 ? var10 : var11;
      int var14 = this.mc.fontRendererObj.getStringWidth(var13);
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.mc.fontRendererObj.drawString(var13, (var8 + 17) * 2 - var14 / 2, (var9 + 14) * 2, ColorUtils.grayFromFraction(var6));
      GL11.glScalef(2.0F, 2.0F, 2.0F);
   }

   private static void drawBorder(int var0, int var1, int var2) {
      Gui.drawRect(var0, var1, var0 + 34, var1 + 1, var2);
      Gui.drawRect(var0, var1 + 22 - 1, var0 + 34, var1 + 22, var2);
      Gui.drawRect(var0, var1, var0 + 1, var1 + 22, var2);
      Gui.drawRect(var0 + 34 - 1, var1, var0 + 34, var1 + 22, var2);
   }
}
