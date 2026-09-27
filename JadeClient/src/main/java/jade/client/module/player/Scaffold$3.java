// Jade recovery: original class: jade.deps.eLz.mEMfiB5g40$3
package jade.client.module.player;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public final class Scaffold$3 {
   private final BlockPos Lkyovn;
   private final EnumFacing dkwe;
   private final boolean cgsaE;
   private Vec3 Vi4;
   private Scaffold$5 placement;

   Scaffold$3(BlockPos var1, EnumFacing var2) {
      this(var1, var2, false);
   }

   Scaffold$3(BlockPos var1, EnumFacing var2, boolean var3) {
      this.Lkyovn = var1;
      this.dkwe = var2;
      this.cgsaE = var3;
   }

   public static Scaffold$5 getPlacement(Scaffold$3 var0) {
      return var0.placement;
   }

   public static BlockPos getTargetPos(Scaffold$3 var0) {
      return var0.Lkyovn;
   }

   public static EnumFacing getFacing(Scaffold$3 var0) {
      return var0.dkwe;
   }

   public static Vec3 setHitVector(Scaffold$3 var0, Vec3 var1) {
      return var0.Vi4 = var1;
   }

   public static boolean isMultiFace(Scaffold$3 var0) {
      return var0.cgsaE;
   }

   public static Vec3 Dtxaj(Scaffold$3 var0) {
      return var0.Vi4;
   }

   public static Scaffold$5 setPlacement(Scaffold$3 var0, Scaffold$5 var1) {
      return var0.placement = var1;
   }
}
