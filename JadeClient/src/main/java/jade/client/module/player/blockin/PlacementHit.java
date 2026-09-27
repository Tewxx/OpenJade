// Jade recovery: original class: jade.deps.eLz.a8qYhqE
package jade.client.module.player.blockin;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public final class PlacementHit {
   public final BlockPos blockPos;
   public final EnumFacing enumFacing;
   public final Vec3 vec3;

   public PlacementHit(BlockPos var1, EnumFacing var2, Vec3 var3) {
      this.blockPos = var1;
      this.enumFacing = var2;
      this.vec3 = var3;
   }
}
