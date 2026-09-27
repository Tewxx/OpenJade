// Jade recovery: original class: jade.deps.eLz.v6k1WOrmL$2
package jade.client.module.player.blockin;

import net.minecraft.util.EnumFacing;

public final class SurroundPlacementFinder$2 {
   private final int offsetX;
   private final int offsetY;
   private final int kbyQm;
   private final EnumFacing enumFacing;

   SurroundPlacementFinder$2(int var1, int var2, int var3, EnumFacing var4) {
      this.offsetX = var1;
      this.offsetY = var2;
      this.kbyQm = var3;
      this.enumFacing = var4;
   }

   public static int getOffsetX(SurroundPlacementFinder$2 var0) {
      return var0.offsetX;
   }

   public static int getOffsetY(SurroundPlacementFinder$2 var0) {
      return var0.offsetY;
   }

   public static int getOffsetZ(SurroundPlacementFinder$2 var0) {
      return var0.kbyQm;
   }

   public static EnumFacing getClickSide(SurroundPlacementFinder$2 var0) {
      return var0.enumFacing;
   }
}
