// Jade recovery: original class: jade.deps.eLz.mEMfiB5g40$2
package jade.client.module.player;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

public final class Scaffold$2 {
   private final AxisAlignedBB axisAlignedBB;
   private final EnumFacing enumFacing;
   private final int fillColor;
   private final int outlineColor;

   Scaffold$2(AxisAlignedBB var1, EnumFacing var2, int var3, int var4) {
      this.axisAlignedBB = var1;
      this.enumFacing = var2;
      this.fillColor = var3;
      this.outlineColor = var4;
   }

   public static AxisAlignedBB getBoundingBox(Scaffold$2 var0) {
      return var0.axisAlignedBB;
   }

   public static EnumFacing getFacing(Scaffold$2 var0) {
      return var0.enumFacing;
   }

   public static int getFillColor(Scaffold$2 var0) {
      return var0.fillColor;
   }

   public static int getOutlineColor(Scaffold$2 var0) {
      return var0.outlineColor;
   }
}
