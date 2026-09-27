// Jade recovery: original class: jade.deps.eLz.l77Qwz
package jade.client.module.player.bednuker;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public final class BreakTarget {
   private final BlockPos blockPos;
   private final Vec3 vec3;
   private final EnumFacing enumFacing;

   public BreakTarget(BlockPos var1, Vec3 var2, EnumFacing var3) {
      this.blockPos = var1;
      this.vec3 = var2;
      this.enumFacing = var3;
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }

   public Vec3 getHitVec() {
      return this.vec3;
   }

   public EnumFacing getSide() {
      return this.enumFacing;
   }
}
