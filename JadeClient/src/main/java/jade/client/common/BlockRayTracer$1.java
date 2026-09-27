// Jade recovery: original class: jade.deps.eLz.kFrsgwxL$1
package jade.client.common;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class BlockRayTracer$1 {
   private int blockX;
   private int blockY;
   private int rmOh;
   private Vec3 vec3;

   BlockRayTracer$1(Vec3 var1) {
      this.vec3 = var1;
      this.blockX = MathHelper.floor_double(var1.xCoord);
      this.blockY = MathHelper.floor_double(var1.yCoord);
      this.rmOh = MathHelper.floor_double(var1.zCoord);
   }

   private boolean matchesBlockPosition(int var1, int var2, int var3) {
      return this.blockX == var1 && this.blockY == var2 && this.rmOh == var3;
   }

   private void advanceToNextBlock(Vec3 var1, int var2, int var3, int var4) {
      boolean var5 = var2 != this.blockX;
      boolean var6 = var3 != this.blockY;
      boolean var7 = var4 != this.rmOh;
      double var8 = var5 ? (var2 > this.blockX ? this.blockX + 1.0 : this.blockX) : 999.0;
      double var10 = var6 ? (var3 > this.blockY ? this.blockY + 1.0 : this.blockY) : 999.0;
      double var12 = var7 ? (var4 > this.rmOh ? this.rmOh + 1.0 : this.rmOh) : 999.0;
      double var14 = var1.xCoord - this.vec3.xCoord;
      double var16 = var1.yCoord - this.vec3.yCoord;
      double var18 = var1.zCoord - this.vec3.zCoord;
      double var20 = var5 ? (var8 - this.vec3.xCoord) / var14 : 999.0;
      double var22 = var6 ? (var10 - this.vec3.yCoord) / var16 : 999.0;
      double var24 = var7 ? (var12 - this.vec3.zCoord) / var18 : 999.0;
      if (var20 == -0.0) {
         var20 = -1.0E-4;
      }

      if (var22 == -0.0) {
         var22 = -1.0E-4;
      }

      if (var24 == -0.0) {
         var24 = -1.0E-4;
      }

      EnumFacing var26;
      if (var20 < var22 && var20 < var24) {
         var26 = var2 > this.blockX ? EnumFacing.WEST : EnumFacing.EAST;
         this.vec3 = new Vec3(var8, this.vec3.yCoord + var16 * var20, this.vec3.zCoord + var18 * var20);
      } else if (var22 < var24) {
         var26 = var3 > this.blockY ? EnumFacing.DOWN : EnumFacing.UP;
         this.vec3 = new Vec3(this.vec3.xCoord + var14 * var22, var10, this.vec3.zCoord + var18 * var22);
      } else {
         var26 = var4 > this.rmOh ? EnumFacing.NORTH : EnumFacing.SOUTH;
         this.vec3 = new Vec3(this.vec3.xCoord + var14 * var24, this.vec3.yCoord + var16 * var24, var12);
      }

      this.blockX = MathHelper.floor_double(this.vec3.xCoord) - (var26 == EnumFacing.EAST ? 1 : 0);
      this.blockY = MathHelper.floor_double(this.vec3.yCoord) - (var26 == EnumFacing.UP ? 1 : 0);
      this.rmOh = MathHelper.floor_double(this.vec3.zCoord) - (var26 == EnumFacing.SOUTH ? 1 : 0);
   }

   public static Vec3 getRayPosition(BlockRayTracer$1 var0) {
      return var0.vec3;
   }

   public static boolean isAtBlockPosition(BlockRayTracer$1 var0, int var1, int var2, int var3) {
      return var0.matchesBlockPosition(var1, var2, var3);
   }

   public static void advanceRay(BlockRayTracer$1 var0, Vec3 var1, int var2, int var3, int var4) {
      var0.advanceToNextBlock(var1, var2, var3, var4);
   }

   public static int getBlockX(BlockRayTracer$1 var0) {
      return var0.blockX;
   }

   public static int fhB1(BlockRayTracer$1 var0) {
      return var0.blockY;
   }

   public static int HNvN(BlockRayTracer$1 var0) {
      return var0.rmOh;
   }
}
