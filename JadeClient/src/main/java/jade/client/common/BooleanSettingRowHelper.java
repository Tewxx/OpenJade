// Jade recovery: original class: jade.deps.eLz.TIC0GX
package jade.client.common;

public final class BooleanSettingRowHelper {
   public static final int TOGGLED_COLOR = -15401216;
   public static final int DISABLED_COLOR = -1;

   private BooleanSettingRowHelper() {
   }

   public static String formatSettingLabel(String var0, boolean var1, boolean var2) {
      String var3 = var1 ? "[=]  " : (var2 ? "[+]  " : "[-]  ");
      return var3 + var0;
   }

   public static int getToggleColor(boolean var0) {
      return var0 ? -15401216 : -1;
   }

   public static boolean isRowHovered(int var0, int var1, int var2, float var3, float var4, float var5, boolean var6, boolean var7) {
      return var2 == 0 && var6 && var7 && var0 > var3 && var0 < var3 + var5 && var1 > var4 && var1 < var4 + 11.0F;
   }
}
