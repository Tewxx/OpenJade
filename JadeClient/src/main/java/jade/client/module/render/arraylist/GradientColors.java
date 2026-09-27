// Jade recovery: original class: jade.deps.eLz.gtqtesfeXk
package jade.client.module.render.arraylist;

import java.awt.Color;

public final class GradientColors {
   private static final double NANOS_PER_SECOND = 1.0E8;
   private static final double WAVE_SPEED_SCALE = 400000.0;
   private static final long GRADIENT_CYCLE_MILLIS = 15000L;

   private GradientColors() {
   }

   public static double waveFraction(long var0, double var2, double var4, double var6) {
      double var8 = var0 / 1.0E8 * var2 * 400000.0 + var4 * var6;
      return (Math.sin(var8) + 1.0) * 0.5;
   }

   public static Color blend(Color var0, Color var1, double var2) {
      double var4 = 1.0 - var2;
      int var6 = (int)(var0.getRed() * var2 + var1.getRed() * var4);
      int var7 = (int)(var0.getGreen() * var2 + var1.getGreen() * var4);
      int var8 = (int)(var0.getBlue() * var2 + var1.getBlue() * var4);
      return new Color(var6, var7, var8);
   }

   public static Color ZZZb(long var0, long var2, long var4, double var6) {
      long var8 = var0 + (long)(var6 * var4);
      float var10 = (float)(var8 % (15000L / var2)) / (15000.0F / (float)var2);
      return Color.getHSBColor(var10, 1.0F, 1.0F);
   }
}
