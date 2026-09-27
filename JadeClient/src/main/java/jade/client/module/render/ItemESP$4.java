// Jade recovery: original class: jade.deps.eLz.SqO4CW$4
package jade.client.module.render;

public final class ItemESP$4 {
   private final String displayName;
   private final ItemESP$1 colorPair;
   private final String jz7;
   private int totalCount;

   ItemESP$4(String var1, ItemESP$1 var2, String var3) {
      this.displayName = var1 != null ? var1 : "";
      this.colorPair = var2;
      this.jz7 = var3;
   }

   public static ItemESP$1 getColorPair(ItemESP$4 var0) {
      return var0.colorPair;
   }

   public static String getGeneratorTag(ItemESP$4 var0) {
      return var0.jz7;
   }

   public static String getDisplayName(ItemESP$4 var0) {
      return var0.displayName;
   }

   public static int getTotalCount(ItemESP$4 var0) {
      return var0.totalCount;
   }

   public static int setTotalCount(ItemESP$4 var0, int var1) {
      return var0.totalCount = var1;
   }
}
