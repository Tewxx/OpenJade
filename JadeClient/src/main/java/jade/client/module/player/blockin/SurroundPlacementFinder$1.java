// Jade recovery: original class: jade.deps.eLz.v6k1WOrmL$1
package jade.client.module.player.blockin;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class SurroundPlacementFinder$1 {
   private final double score;
   private final float yaw;
   private final float pitch;
   private final BlockPos QJzi;
   private final EnumFacing KRmV8;
   private final BlockPos dRk;

   SurroundPlacementFinder$1(double var1, float var3, float var4, BlockPos var5, EnumFacing var6, BlockPos var7) {
      this.score = var1;
      this.yaw = var3;
      this.pitch = var4;
      this.QJzi = var5;
      this.KRmV8 = var6;
      this.dRk = var7;
   }

   public static float getYaw(SurroundPlacementFinder$1 var0) {
      return var0.yaw;
   }

   public static float getPitch(SurroundPlacementFinder$1 var0) {
      return var0.pitch;
   }

   public static BlockPos getClickedPos(SurroundPlacementFinder$1 var0) {
      return var0.QJzi;
   }

   public static EnumFacing YvqqU(SurroundPlacementFinder$1 var0) {
      return var0.KRmV8;
   }

   public static BlockPos getPlacementPos(SurroundPlacementFinder$1 var0) {
      return var0.dRk;
   }

   public static double getScore(SurroundPlacementFinder$1 var0) {
      return var0.score;
   }
}
