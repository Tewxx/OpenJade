// Jade recovery: original class: jade.deps.eLz.n9b6lzOX
package jade.client.module.render.arraylist;

import jade.client.common.ClientUtils;
import jade.client.module.client.Settings;
import java.awt.Color;

public enum ColorTheme {
   Rainbow(),
   Cherry(255, 200, 200, 243, 58, 106),
   Cotton_candy(99, 249, 255, 255, 104, 204),
   Flare(231, 39, 24, 245, 173, 49),
   Flower(215, 166, 231, 211, 90, 232),
   Gold(255, 215, 0, 240, 159, 0),
   Grayscale(240, 240, 240, 110, 110, 110),
   Royal(125, 204, 241, 30, 71, 170),
   Sky(160, 230, 225, 15, 190, 220),
   Vine(17, 192, 45, 201, 234, 198);

   private final int[] endpoints;
   public static Color[] descriptor = new Color[]{new Color(95, 235, 255), new Color(68, 102, 250)};
   public static Color[] hiddenBind = new Color[]{new Color(245, 33, 33), new Color(229, 21, 98)};
   public static String[] themes = new String[]{"Rainbow", "Cherry", "Cotton candy", "Flare", "Flower", "Gold", "Grayscale", "Royal", "Sky", "Vine"};

   private ColorTheme(int... var3) {
      this.endpoints = var3;
   }

   public static int getGradient(int var0, double var1) {
      if (var0 > 0) {
         ColorTheme var3 = values()[var0];
         double var4 = GradientColors.waveFraction(System.currentTimeMillis(), Settings.timeMultiplier.getInput(), var1, Settings.offset.getInput());
         return GradientColors.blend(var3.first(), var3.second(), var4).getRGB();
      } else {
         return var0 == 0 ? getChromaOffset(2L, (long)var1) : -1;
      }
   }

   public static int getChromaOffset(long var0, long var2) {
      return GradientColors.ZZZb(System.currentTimeMillis(), var0, var2, Settings.offset.getInput()).getRGB();
   }

   public static int getGradient(Color var0, Color var1, double var2) {
      double var4 = GradientColors.waveFraction(System.currentTimeMillis(), 0.5, var2, 0.55F);
      return GradientColors.blend(var0, var1, var4).getRGB();
   }

   public static Color convert(Color var0, Color var1, double var2) {
      return GradientColors.blend(var0, var1, var2);
   }

   public static int[] getGradients(int var0) {
      ColorTheme[] var1 = values();
      if (var0 >= 0 && var0 < var1.length) {
         ColorTheme var2 = var1[var0];
         return !var2.hasFixedPalette() ? new int[]{ClientUtils.AIowEv(2L, 0L), ClientUtils.AIowEv(2L, 0L)} : new int[]{var2.first().getRGB(), var2.second().getRGB()};
      } else {
         return new int[]{0, 0};
      }
   }

   private boolean hasFixedPalette() {
      return this.endpoints.length == 6;
   }

   private Color first() {
      return new Color(this.endpoints[0], this.endpoints[1], this.endpoints[2]);
   }

   private Color second() {
      return new Color(this.endpoints[3], this.endpoints[4], this.endpoints[5]);
   }
}
