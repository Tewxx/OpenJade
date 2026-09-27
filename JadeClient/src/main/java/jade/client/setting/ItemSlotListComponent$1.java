// Jade recovery: original class: jade.deps.eLz.APmfhBo$1
package jade.client.setting;

public final class ItemSlotListComponent$1 extends ItemListComponent$0 {
   private final String itemKey;
   private final String displayLabel;
   private final Integer slotIndex;

   ItemSlotListComponent$1(ItemSlotListResolver$1 var1) {
      super(var1.pkPjxC(), var1.getDisplayName(), var1.aks79(), var1.Rwywl());
      this.itemKey = var1.pkPjxC();
      this.displayLabel = var1.getDisplayName();
      this.slotIndex = var1.getSlotIndex();
   }

   public static String getItemKey(ItemSlotListComponent$1 var0) {
      return var0.itemKey;
   }

   public static String getDisplayName(ItemSlotListComponent$1 var0) {
      return var0.displayLabel;
   }

   public static Integer getSlotIndex(ItemSlotListComponent$1 var0) {
      return var0.slotIndex;
   }
}
