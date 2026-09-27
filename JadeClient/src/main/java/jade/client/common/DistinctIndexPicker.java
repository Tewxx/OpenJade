// Jade recovery: original class: jade.deps.eLz.tay5C6ujr
package jade.client.common;

public final class DistinctIndexPicker {
   private DistinctIndexPicker() {
   }

   public static int pickDistinctIndex(int var0, int var1, int var2) {
      if (var0 != var1) {
         return var0;
      } else {
         return var0 == var2 - 1 ? 0 : var0 + 1;
      }
   }
}
