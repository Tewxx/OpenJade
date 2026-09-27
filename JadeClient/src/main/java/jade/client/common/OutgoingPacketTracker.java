// Jade recovery: original class: jade.deps.eLz.Wv6LdEfyq
package jade.client.common;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public final class OutgoingPacketTracker {
   private final PacketThrottleState RpHgb = new PacketThrottleState();
   private final RepeatCounter directionRepeatCounter = new RepeatCounter();

   public OutgoingPacketTracker$1 analyzePacket(Packet<?> var1, Minecraft var2, boolean var3) {
      boolean var4 = var3;
      if (var1 instanceof C08PacketPlayerBlockPlacement && ClientUtils.CqWuiK() && !BlockUtils.vctG(var2.objectMouseOver) && !var3) {
         var4 = true;
      } else if (var1 instanceof C07PacketPlayerDigging && var3 && ((C07PacketPlayerDigging)var1).getStatus() == Action.RELEASE_USE_ITEM) {
         var4 = false;
      } else if (var1 instanceof C09PacketHeldItemChange && var3) {
         var4 = false;
      }

      boolean var5 = var1 instanceof C02PacketUseEntity;
      if (var5) {
         this.RpHgb.aiof3();
      }

      return new OutgoingPacketTracker$1(var4, var5);
   }

   public OutgoingPacketTracker$2 FOPa(Packet<?> var1, Minecraft var2, int var3) {
      boolean var4 = var1 instanceof C07PacketPlayerDigging;
      boolean var5 = false;
      boolean var6 = false;
      if (var1 instanceof C08PacketPlayerBlockPlacement && ClientUtils.isHoldingFireball() && ClientUtils.xusXfhC(var2.gameSettings.keyBindUseItem)) {
         this.RpHgb.setPlaceTime(System.currentTimeMillis());
         var5 = true;
         var6 = var2.thePlayer.rotationPitch > 50.0F;
      }

      int var7 = var3;
      if (var1 instanceof C08PacketPlayerBlockPlacement && ClientUtils.isSideCorrectionEnabled(false)) {
         int var8 = ((C08PacketPlayerBlockPlacement)var1).getPlacedBlockDirection();
         if (var8 != 1) {
            var7 = this.directionRepeatCounter.nextRepeatCount(var8, var3);
         }
      }

      return new OutgoingPacketTracker$2(var4, var5, var6, var7);
   }

   public OutgoingPacketTracker$3 computeTickState(boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6, long var7, long var9) {
      PacketThrottleState$1 var11 = this.RpHgb.throttleEveryEighthCall(var1, var2);
      PacketThrottleState$2 var12 = this.RpHgb.RPUpBh(var7, var9, var5, var6);
      return new OutgoingPacketTracker$3(var11.isUsingItem(), var11.Ssii2(), this.RpHgb.consumeAttackPulse(var3), this.RpHgb.consumeAlternateTick(var4), var12.isPlacementFlagSet(), var12.JfI3());
   }
}
