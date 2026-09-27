// Jade recovery: recovered class name: PlayerWalkingUpdate; original class: jade.mixin.feature.player.Mf30e36b84e4c092994e45762af2516b8
package jade.mixin.feature.player;

import jade.client.common.EventBus;
import jade.client.common.RotationUtils;
import jade.client.event.UpdateWalkingPlayerEvent;
import jade.client.event.PostWalkingUpdateEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public final class PlayerWalkingUpdate {
   private static final double POSITION_EPSILON_SQUARED = 9.0E-4;
   private static final int FORCED_POSITION_INTERVAL = 20;

   private PlayerWalkingUpdate() {
   }

   public static PlayerWalkingUpdate$0 transmit(
      EntityPlayerSP player, NetHandlerPlayClient network, boolean cameraOwnsPlayer, PlayerWalkingUpdate$0 previous
   ) {
      resetRotationRequests();
      UpdateWalkingPlayerEvent motion = new UpdateWalkingPlayerEvent(
         player.posX,
         player.getEntityBoundingBox().minY,
         player.posZ,
         player.rotationYaw,
         player.rotationPitch,
         player.onGround,
         player.isSprinting(),
         player.isSneaking()
      );
      EventBus.post(motion);
      RotationUtils.lastSentRotation = new float[]{motion.getYaw(), motion.getPitch()};
      boolean sprinting = motion.isSprinting();
      if (sprinting != previous.sprinting) {
         send(network, new C0BPacketEntityAction(player, sprinting ? Action.START_SPRINTING : Action.STOP_SPRINTING));
      }

      boolean sneaking = motion.isSneaking();
      if (sneaking != previous.sneaking) {
         send(network, new C0BPacketEntityAction(player, sneaking ? Action.START_SNEAKING : Action.STOP_SNEAKING));
      }

      double sentX = previous.x;
      double sentY = previous.y;
      double sentZ = previous.z;
      float sentYaw = previous.yaw;
      float sentPitch = previous.pitch;
      int idleTicks = previous.idleTicks;
      if (cameraOwnsPlayer) {
         updateRenderedRotation(motion);
         double dx = motion.getX() - sentX;
         double dy = motion.getY() - sentY;
         double dz = motion.getZ() - sentZ;
         boolean moved = dx * dx + dy * dy + dz * dz > 9.0E-4 || idleTicks >= 20;
         boolean turned = motion.getYaw() != sentYaw || motion.getPitch() != sentPitch;
         if (player.ridingEntity == null) {
            send(network, ordinaryPacket(motion, moved, turned));
         } else {
            send(network, new C06PacketPlayerPosLook(player.motionX, -999.0, player.motionZ, motion.getYaw(), motion.getPitch(), motion.isOnGround()));
            moved = false;
         }

         idleTicks++;
         if (moved) {
            sentX = motion.getX();
            sentY = motion.getY();
            sentZ = motion.getZ();
            idleTicks = 0;
         }

         if (turned) {
            sentYaw = motion.getYaw();
            sentPitch = motion.getPitch();
         }
      }

      EventBus.post(new PostWalkingUpdateEvent());
      return new PlayerWalkingUpdate$0(sprinting, sneaking, sentX, sentY, sentZ, sentYaw, sentPitch, idleTicks);
   }

   private static void resetRotationRequests() {
      UpdateWalkingPlayerEvent.QhtJiq = false;
      UpdateWalkingPlayerEvent.setYawOverrideRequested(false);
      RotationUtils.hasPendingRotationOverride = false;
   }

   private static void updateRenderedRotation(UpdateWalkingPlayerEvent motion) {
      if (UpdateWalkingPlayerEvent.isYawOverrideRequested()) {
         RotationUtils.applyRenderedYaw(motion.getYaw());
      }

      RotationUtils.pN0 = motion.getPitch();
      RotationUtils.outgoingYaw = motion.getYaw();
      if (RotationUtils.hasPendingRotationOverride) {
         RotationUtils.pN0 = RotationUtils.gib[1];
         RotationUtils.outgoingYaw = RotationUtils.gib[0];
         RotationUtils.applyRenderedYaw(RotationUtils.outgoingYaw);
      }

      RotationUtils.hasPendingRotationOverride = false;
   }

   private static Packet ordinaryPacket(UpdateWalkingPlayerEvent motion, boolean moved, boolean turned) {
      if (moved && turned) {
         return new C06PacketPlayerPosLook(motion.getX(), motion.getY(), motion.getZ(), motion.getYaw(), motion.getPitch(), motion.isOnGround());
      } else if (moved) {
         return new C04PacketPlayerPosition(motion.getX(), motion.getY(), motion.getZ(), motion.isOnGround());
      } else {
         return (Packet)(turned ? new C05PacketPlayerLook(motion.getYaw(), motion.getPitch(), motion.isOnGround()) : new C03PacketPlayer(motion.isOnGround()));
      }
   }

   private static void send(NetHandlerPlayClient network, Packet packet) {
      network.addToSendQueue(packet);
   }
}
