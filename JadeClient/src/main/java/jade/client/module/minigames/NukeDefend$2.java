// Jade recovery: original class: jade.deps.eLz.Fb8OTJ$2
package jade.client.module.minigames;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public final class NukeDefend$2 {
   private final BlockPos bedPos;
   private final BlockPos hJvim3;
   private final EnumFacing enumFacing;
   private final Vec3 vec3;
   private final double distanceScore;

   NukeDefend$2(BlockPos var1, BlockPos var2, EnumFacing var3, Vec3 var4, double var5) {
      this.bedPos = var1;
      this.hJvim3 = var2;
      this.enumFacing = var3;
      this.vec3 = var4;
      this.distanceScore = var5;
   }

   public static BlockPos ZKNliC(NukeDefend$2 var0) {
      return var0.bedPos;
   }

   public static Vec3 getHitVec(NukeDefend$2 var0) {
      return var0.vec3;
   }

   public static BlockPos getClickedPos(NukeDefend$2 var0) {
      return var0.hJvim3;
   }

   public static EnumFacing getClickedSide(NukeDefend$2 var0) {
      return var0.enumFacing;
   }

   public static double getDistanceScore(NukeDefend$2 var0) {
      return var0.distanceScore;
   }
}
