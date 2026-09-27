// Jade recovery: original class: jade.deps.eLz.Ky4N7R9$1
package jade.client.module.minigames.bedwarsutils;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

public final class BuildLimitRenderer$1 {
   private final AxisAlignedBB axisAlignedBB;
   private final EnumFacing enumFacing;
   private final int argbColor;

   BuildLimitRenderer$1(AxisAlignedBB var1, EnumFacing var2, int var3) {
      this.axisAlignedBB = var1;
      this.enumFacing = var2;
      this.argbColor = var3;
   }

   public static AxisAlignedBB yfx0(BuildLimitRenderer$1 var0) {
      return var0.axisAlignedBB;
   }

   public static EnumFacing VEflEja(BuildLimitRenderer$1 var0) {
      return var0.enumFacing;
   }

   public static int getArgbColor(BuildLimitRenderer$1 var0) {
      return var0.argbColor;
   }
}
