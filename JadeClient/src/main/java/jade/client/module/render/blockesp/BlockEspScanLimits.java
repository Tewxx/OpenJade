// Jade recovery: original class: jade.deps.eLz.BvvQjKl
package jade.client.module.render.blockesp;

public final class BlockEspScanLimits {
   private BlockEspScanLimits() {
   }

   public static int resolveBlocksPerTick(boolean var0, boolean var1, boolean var2, double var3) {
      return var0 && var1 && var2 ? Math.max(1, (int)var3) : 0;
   }

   public static int resolveMaxPerChunk(double var0) {
      int var2 = (int)var0;
      return var2 >= 50 ? 0 : var2;
   }

   public static boolean YjzcSl(BlockEspScanLimits$0 var0, BlockEspScanLimits$1 var1, double var2) {
      double var4 = var0.blockX + 0.5 - var1.HSA;
      double var6 = var0.PHONm + 0.5 - var1.posY;
      double var8 = var0.blockZ + 0.5 - var1.posZ;
      return var4 * var4 + var6 * var6 + var8 * var8 <= var2;
   }
}
