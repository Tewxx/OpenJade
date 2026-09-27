// Jade recovery: original class: jade.deps.eLz.hFekCFn$1
package jade.client.module.player;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class ClutchAssist$1 {
   public final BlockPos vj4;
   public final EnumFacing enumFacing;
   public final Vec3 vec3;
   public final float usiU;
   public final float targetPitch;
   public final boolean stillAimedAt;
   public final boolean ladderClutch;

   public ClutchAssist$1(BlockPos var1, EnumFacing var2, Vec3 var3, float var4, float var5, boolean var6, boolean var7) {
      this.vj4 = var1;
      this.enumFacing = var2;
      this.vec3 = var3;
      this.usiU = var4;
      this.targetPitch = var5;
      this.stillAimedAt = var6;
      this.ladderClutch = var7;
   }
}
