// Jade recovery: original class: jade.deps.eLz.stQPFGx
package jade.client.module.other.anticheat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;

public class AnticheatPlayerState {
   public double horizontalSpeed;
   public int lastVerticalMoveTick;
   public int GefiE;
   public int autoblockTicks;
   public int lastMoveTick;
   public int AASf;
   public double deltaZ;
   public int scaffoldFlagCount;
   public int sprintUseItemTicks;
   public double KIU;
   public boolean GVTx;
   public double QSdcI;
   public double packetPosX;
   public double utZbz;
   public double packetPosZ;

   public void updateFromPlayer(EntityPlayer var1) {
      this.updateMovementDelta(var1);
      int var2 = var1.ticksExisted;
      if (AnticheatMath.BfnuQ(this.horizontalSpeed)) {
         this.GefiE = AnticheatMath.QgxOo(this.GefiE);
         this.lastMoveTick = var2;
      } else {
         this.GefiE = 0;
      }

      if (AnticheatMath.hasVerticalMovement(this.KIU)) {
         this.lastVerticalMoveTick = var2;
      }

      boolean var3 = var1.isSneaking();
      if (var3) {
         this.AASf = var2;
      }

      this.autoblockTicks = AnticheatMath.nextStreakCount(this.autoblockTicks, var1.isSwingInProgress && var1.isBlocking());
      this.sprintUseItemTicks = AnticheatMath.nextStreakCount(this.sprintUseItemTicks, var1.isSprinting() && var1.isUsingItem());
      boolean var4 = var1.getHeldItem() != null && var1.getHeldItem().getItem() instanceof ItemBlock;
      this.scaffoldFlagCount = AnticheatMath.pFoo(this.scaffoldFlagCount, var1.rotationPitch, var4, var1.swingProgressInt, this.GVTx, var3);
   }

   private void updateMovementDelta(EntityPlayer var1) {
      this.QSdcI = var1.posX - var1.lastTickPosX;
      this.KIU = var1.posY - var1.lastTickPosY;
      this.deltaZ = var1.posZ - var1.lastTickPosZ;
      this.horizontalSpeed = AnticheatMath.horizontalSpeed(this.QSdcI, this.deltaZ);
   }

   public void recordSneakingState(EntityPlayer var1) {
      this.GVTx = var1.isSneaking();
   }

   public void updateServerPosition(EntityPlayer var1) {
      this.packetPosX = AnticheatMath.AERFxL(var1.serverPosX);
      this.utZbz = AnticheatMath.AERFxL(var1.serverPosY);
      this.packetPosZ = AnticheatMath.AERFxL(var1.serverPosZ);
   }
}
