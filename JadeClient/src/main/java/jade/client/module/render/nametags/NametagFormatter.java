// Jade recovery: original class: jade.deps.eLz.PtEjQGyisY
package jade.client.module.render.nametags;

public final class NametagFormatter {
   private NametagFormatter() {
   }

   public static int getVisibleLength(String var0) {
      int var1 = 0;

      for (int var2 = 0; var2 < var0.length(); var2++) {
         char var3 = var0.charAt(var2);
         if (var3 == 167 && var2 + 1 < var0.length()) {
            var2++;
         } else if (var3 != '\n') {
            var1++;
         }
      }

      return var1;
   }

   public static String formatDistance(float var0) {
      int var1 = (int)var0;
      String var2 = var1 <= 8 ? "§c" : (var1 <= 15 ? "§6" : (var1 <= 25 ? "§e" : "§7"));
      return "§7[" + var2 + var1 + "m§7]§r";
   }

   public static float scaleByDistance(float var0, float var1, boolean var2, float var3) {
      if (!var2) {
         return var0;
      } else {
         float var4 = var0 * (Math.max(1.0F, var1) / var3);
         return Math.max(var0, var4);
      }
   }

   public static int getTotalWidth(int var0, int var1, int... var2) {
      int var3 = var0;

      for (int var7 : var2) {
         if (var7 > 0) {
            if (var3 > 0) {
               var3 += var1;
            }

            var3 += var7;
         }
      }

      return Math.max(var3, 1);
   }

   public static int getHealthColor(float var0) {
      if (var0 < 0.3F) {
         return -43691;
      } else if (var0 < 0.5F) {
         return -22016;
      } else {
         return var0 < 0.7F ? -171 : -11141291;
      }
   }

   public static int[] findVisibleTextRange(String var0, String var1) {
      String var2 = stripColorCodes(var0);
      int var3 = var2.indexOf(var1);
      return var3 < 0 ? new int[]{-1, -1} : new int[]{var3, var3 + var1.length()};
   }

   public static String formatHealthText(float var0, float var1, float var2, boolean var3, boolean var4) {
      float var5 = Math.max(0.0F, var0);
      float var6 = var1 <= 0.0F ? 20.0F : var1;
      float var7 = var5 / var6;
      String var8 = var7 < 0.3F ? "§c" : (var7 < 0.5F ? "§6" : (var7 < 0.7F ? "§e" : "§a"));
      String var9 = var3 && var4 ? " ❤" : "";
      String var10 = var8 + formatNumber(var3 ? var5 * 0.5F : var5) + var9;
      if (var2 > 0.0F) {
         var10 = var10 + " §6+" + formatNumber(var3 ? var2 * 0.5F : var2) + var9;
      }

      return var10 + "§r";
   }

   private static String formatNumber(float var0) {
      int var1 = (int)var0;
      if (var0 == var1) {
         return String.valueOf(var1);
      } else {
         int var2 = Math.round(var0 * 10.0F);
         return var2 / 10 + "." + Math.abs(var2 % 10);
      }
   }

   private static String stripColorCodes(String var0) {
      StringBuilder var1 = new StringBuilder(var0.length());

      for (int var2 = 0; var2 < var0.length(); var2++) {
         char var3 = var0.charAt(var2);
         if (var3 == 167 && var2 + 1 < var0.length()) {
            var2++;
         } else {
            var1.append(var3);
         }
      }

      return var1.toString();
   }
}
