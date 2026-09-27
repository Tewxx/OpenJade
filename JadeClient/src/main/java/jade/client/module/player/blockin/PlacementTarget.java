// Jade recovery: original class: jade.deps.eLz.UqDy4vbaD
package jade.client.module.player.blockin;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class PlacementTarget {
   private final BlockPos blockPos;
   private final EnumFacing enumFacing;
   private final float yaw;
   private final float ZtsJ;

   public PlacementTarget(BlockPos var1, EnumFacing var2, float var3, float var4) {
      this.blockPos = var1;
      this.enumFacing = var2;
      this.yaw = var3;
      this.ZtsJ = var4;
   }

   public BlockPos getClickedPos() {
      return this.blockPos;
   }

   public EnumFacing GjveS() {
      return this.enumFacing;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.ZtsJ;
   }

   public BlockPos getPlacementPos() {
      return this.blockPos.offset(this.enumFacing);
   }
}
