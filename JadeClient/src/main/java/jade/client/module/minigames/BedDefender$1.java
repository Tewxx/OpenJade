// Jade recovery: original class: jade.deps.eLz.FV47yBX$1
package jade.client.module.minigames;

import net.minecraft.util.BlockPos;

public class BedDefender$1 {
   private final int ZXDjPq;
   private final BlockPos blockPos;
   private final int hotbarSlot;
   private final float yaw;
   private final float pitch;
   private final double jeH;

   BedDefender$1(int var1, BlockPos var2, int var3, float var4, float var5, double var6) {
      this.ZXDjPq = var1;
      this.blockPos = var2;
      this.hotbarSlot = var3;
      this.yaw = var4;
      this.pitch = var5;
      this.jeH = var6;
   }

   public static BlockPos getBedPos(BedDefender$1 var0) {
      return var0.blockPos;
   }

   public static float getYaw(BedDefender$1 var0) {
      return var0.yaw;
   }

   public static float getPitch(BedDefender$1 var0) {
      return var0.pitch;
   }

   public static int getSlotIndex(BedDefender$1 var0) {
      return var0.ZXDjPq;
   }

   public static int getHotbarSlot(BedDefender$1 var0) {
      return var0.hotbarSlot;
   }

   public static double getScore(BedDefender$1 var0) {
      return var0.jeH;
   }
}
