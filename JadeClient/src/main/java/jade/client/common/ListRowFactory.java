// Jade recovery: original class: jade.deps.eLz.weWtWP
package jade.client.common;

public final class ListRowFactory {
   private static final int ROW_COLOR = -15066582;
   private static final int BDgp59 = -14803410;
   private static final int XJh = -14013892;

   private ListRowFactory() {
   }

   public static PotionListRow createSearchResultRow(SettingEntryListModel var0, int var1, int var2, float var3, float var4, float var5) {
      PotionLookup$0 var6 = var0.Laqjix(var1);
      int var7 = var1 == var2 ? -14013892 : getRowColor(var1);
      return new PotionListRow(var6.displayName, PotionLookup.GhAk(var6.registryName), var3 + var1 * var5 - var4, var7, false);
   }

   public static PotionListRow createListRow(SettingEntryListModel var0, int var1, float var2, float var3, float var4) {
      String var5 = var0.getEntryName(var1);
      return new PotionListRow(PotionLookup.getPotionDisplayName(var5), PotionLookup.GhAk(var5), var2 + var1 * var4 - var3, getRowColor(var1), true);
   }

   public static int findRowIndexAt(float var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      if (!(var0 <= var2) && !(var0 >= var3)) {
         int var8 = (int)((var1 - var4 + var5) / var6);
         if (var8 >= 0 && var8 < var7) {
            float var9 = var4 + var8 * var6 - var5;
            return var1 >= var9 && var1 < var9 + var6 ? var8 : -1;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   private static int getRowColor(int var0) {
      return (var0 & 1) == 0 ? -15066582 : -14803410;
   }
}
