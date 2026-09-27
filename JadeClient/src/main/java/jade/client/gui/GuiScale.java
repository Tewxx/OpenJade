// Jade recovery: original class: jade.deps.eLz.LP5GlTgP
package jade.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;

public final class GuiScale {
   private ScaledResolution scaledResolution;
   private int scaledWidth;
   private int scaledHeight;
   private int KiB;
   private int logicalHeight;

   public void aZyv(Minecraft var1, double var2) {
      this.scaledResolution = new ScaledResolution(var1);
      this.scaledWidth = this.scaledResolution.getScaledWidth();
      this.scaledHeight = this.scaledResolution.getScaledHeight();
      double var4 = Math.max(1.0, Math.min((double)computeMaxGuiScale(var1), var2 * 2.0));
      this.KiB = Math.max(1, MathHelper.ceiling_double_int(var1.displayWidth / var4));
      this.logicalHeight = Math.max(1, MathHelper.ceiling_double_int(var1.displayHeight / var4));
   }

   public int toLogicalCoordinate(int var1) {
      return (int)Math.floor(var1 / this.getLogicalScaleRatio());
   }

   public double getLogicalScaleRatio() {
      return this.scaledWidth > 0 && this.KiB > 0 ? (double)this.scaledWidth / this.KiB : 1.0;
   }

   public int getScaledWidth() {
      return this.scaledWidth;
   }

   public int getScaledHeight() {
      return this.scaledHeight;
   }

   public int getLogicalWidth() {
      return this.KiB;
   }

   public int getLogicalHeight() {
      return this.logicalHeight;
   }

   public int getScaleFactor() {
      return this.scaledResolution.getScaleFactor();
   }

   private static int computeMaxGuiScale(Minecraft var0) {
      int var1 = 1;

      while (var0.displayWidth / (var1 + 1) >= 320 && var0.displayHeight / (var1 + 1) >= 240) {
         var1++;
      }

      if (var0.isUnicode() && (var1 & 1) != 0 && var1 != 1) {
         var1--;
      }

      return var1;
   }
}
