// Jade recovery: original class: jade.deps.eLz.Y7qGqF
package jade.client.setting;

public final class ItemListSettingHelper {
   private ItemListSettingHelper() {
   }

   public static boolean isVisible(ItemListSetting var0) {
      return var0.visible;
   }

   public static String BxyBj0(ItemListSetting var0) {
      return var0.groupSetting == null ? "" : var0.groupSetting.getName();
   }

   public static void addEntry(ItemListSetting var0, String var1) {
      var0.removeItem(var1);
   }
}
