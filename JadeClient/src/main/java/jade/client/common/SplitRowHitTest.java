// Jade recovery: original class: jade.deps.eLz.LbjqV885J
package jade.client.common;

public final class SplitRowHitTest {
   private SplitRowHitTest() {
   }

   public static boolean FBin(int var0, int var1, float var2, float var3, float var4) {
      return isYWithinRow(var1, var3) && var0 > var2 && var0 < var2 + var4 * 0.5F + 1.0F;
   }

   public static boolean BCwl(int var0, int var1, float var2, float var3, float var4) {
      return isYWithinRow(var1, var3) && var0 > var2 + var4 * 0.5F && var0 < var2 + var4;
   }

   private static boolean isYWithinRow(int var0, float var1) {
      return var0 > var1 && var0 < var1 + 16.0F;
   }
}
