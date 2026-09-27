// Jade recovery: original class: jade.deps.eLz.xQrHhjQ5L
package jade.client.common;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLadder;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class MovementChecks {
   private MovementChecks() {
   }

   public static boolean isOnLadder(Minecraft var0, Entity var1) {
      BlockPos var2 = new BlockPos(MathHelper.floor_double(var1.posX), MathHelper.floor_double(var1.posY - 0.2F), MathHelper.floor_double(var1.posZ));
      boolean var3 = var0.theWorld.getBlockState(var2).getBlock() instanceof BlockLadder;
      return var3 && !var1.onGround;
   }

   public static boolean hasBlockAboveHead(Entity var0) {
      BlockPos var1 = new BlockPos(var0.posX, var0.posY + 2.0, var0.posZ);
      return !(BlockUtils.iepjdt(var1) instanceof BlockAir);
   }

   public static boolean isOverVoid(Minecraft var0, Entity var1) {
      return var0.theWorld.getCollidingBoundingBoxes(var1, var1.getEntityBoundingBox().offset(var1.motionX / 3.0, -1.0, var1.motionZ / 3.0)).isEmpty();
   }

   public static boolean isAirAtFeet(Minecraft var0, Entity var1) {
      double var2 = var1.posY;
      if (var2 % 1.0 == 0.0) {
         var2--;
      }

      return var0.theWorld.isAirBlock(new BlockPos(var1.posX, var2, var1.posZ));
   }
}
