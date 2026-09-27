// Jade recovery: original class: jade.deps.eLz.toaI9s7nhS
package jade.client.common;

import jade.client.event.PacketReceiveEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.SilentPacketSendEvent;
import jade.client.event.UpdateWalkingPlayerEvent;
import jade.client.event.PostWalkingUpdateEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S27PacketExplosion;

public class PlayerPacketStateTracker implements IMinecraft {
   public static boolean xpj;
   public static boolean Wok;
   public static boolean placedLookingDown;
   public static long explosionDistanceSq = 10L;
   private long placeTimeoutMs = 500L;
   public static int rqmyul;
   public static int groundTicks;
   public static int idleTicks;
   public static int sprintJumpTicks;
   public static int sameDirectionPlaceCount;
   public static double OZURaj = 2.01E-11;
   public static boolean IKRhL9;
   public static int HAH = -1;
   public static boolean wasPreviouslyOnGround;
   public static boolean EBM;
   public static boolean attackRepeatMirror;
   public static boolean placingBlock;
   public static boolean attackRepeatActive;
   private static boolean hasSpeedEffect;
   public static boolean ScT;
   public static boolean airborneAfterSprintJump;
   public static boolean airFrictionApplied;
   private final OutgoingPacketTracker packetTracker = new OutgoingPacketTracker();
   private final KnockbackMotionController joI = new KnockbackMotionController();

   @Subscribe
   public void onSilentPacketSend(SilentPacketSendEvent var1) {
      this.handleOutgoingPacket(var1.ys98());
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onPacketSend(PacketSendEvent var1) {
      this.handleOutgoingPacket(var1.ys98());
      OutgoingPacketTracker$2 var2 = this.packetTracker.FOPa(var1.ys98(), mc, sameDirectionPlaceCount);
      if (var2.DWaag) {
         xpj = true;
      }

      if (var2.placedFireball) {
         Wok = true;
      }

      if (var2.placedLookingDown) {
         placedLookingDown = true;
      }

      sameDirectionPlaceCount = var2.oas;
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (ClientUtils.isInWorld() && !var1.isCanceled()) {
         if (var1.ys98() instanceof S27PacketExplosion) {
            S27PacketExplosion var2 = (S27PacketExplosion)var1.ys98();
            if (Wok && mc.thePlayer.getPosition().distanceSq(var2.getX(), var2.getY(), var2.getZ()) <= explosionDistanceSq) {
               Wok = false;
               var1.setCanceled(false);
            }
         }
      }
   }

   private void handleOutgoingPacket(Packet<?> var1) {
      if (ClientUtils.isInWorld()) {
         OutgoingPacketTracker$1 var2 = this.packetTracker.analyzePacket(var1, mc, placingBlock);
         placingBlock = var2.placingBlock;
         if (var2.attackPacket) {
            IKRhL9 = true;
         }
      }
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      HAH++;
      OutgoingPacketTracker$3 var2 = this.packetTracker.computeTickState(attackRepeatActive, attackRepeatMirror, IKRhL9, xpj, Wok, placedLookingDown, System.currentTimeMillis(), this.placeTimeoutMs);
      attackRepeatActive = var2.pulseFlag;
      attackRepeatMirror = var2.pulseFlagCompanion;
      IKRhL9 = var2.KZo;
      xpj = var2.digging;
      Wok = var2.usingItem;
      placedLookingDown = var2.FHUJw;
   }

   @Subscribe
   public void onPostWalkingUpdate(PostWalkingUpdateEvent var1) {
   }

   public static double getSpeedMultiplier() {
      int var0 = ClientUtils.getSpeedAmplifier();
      return var0 > 1 && hasSpeedEffect ? 1.0 : 1.0;
   }

   @Subscribe
   public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent var1) {
      KnockbackMotionController$1 var2 = this.joI.updateKnockbackMotion(mc.thePlayer, rqmyul, groundTicks, idleTicks, ScT, airborneAfterSprintJump, airFrictionApplied);
      wasPreviouslyOnGround = var2.NSTr.wasPreviouslyOnGround();
      EBM = var2.NSTr.wasPreviouslyOnBlockGrid();
      rqmyul = var2.NSTr.getAirborneTicks();
      groundTicks = var2.NSTr.KynY0();
      idleTicks = var2.NSTr.getIdleTicks();
      ScT = var2.Rx7;
      airborneAfterSprintJump = var2.airborneActive;
      airFrictionApplied = var2.fMkw;
   }

   public static void resetAirborneState() {
      airborneAfterSprintJump = false;
      ScT = true;
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
   }
}
