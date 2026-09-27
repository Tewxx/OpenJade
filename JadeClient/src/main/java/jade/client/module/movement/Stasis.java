// Jade recovery: module: Stasis (movement); original class: jade.deps.eLz.FK6uC9m
package jade.client.module.movement;

import jade.client.common.BlockUtils;
import jade.client.common.ClientUtils;
import jade.client.common.EventBus;
import jade.client.common.EventPriority;
import jade.client.common.PacketUtils;
import jade.client.common.Subscribe;
import jade.client.event.LoadWorldEvent;
import jade.client.event.MoveInputEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.PostUpdateEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RightClickEvent;
import jade.client.event.TickStartEvent;
import jade.client.event.VelocityEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.shared.ProgressBarSource;
import jade.client.setting.BooleanSetting;
import jade.deps.gson.JsonObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

@ModuleInfo
public class Stasis extends Module implements ProgressBarSource {
   private static final int MAX_HOLD_TICKS = 280;
   private static final long MAX_HOLD_MS = 14000L;
   private static final double EPSILON = 1.0E-6;
   private final BooleanSetting hypixelMode;
   private final BooleanSetting shiftPlaceThroughWalls;
   private final StasisInputLock inputLock;
   private final Deque<Packet<?>> heldPackets = new ArrayDeque<>();
   private boolean active;
   private boolean frozenOnGround;
   private boolean resyncPosition;
   private boolean knockbackPending;
   private boolean holdingPackets;
   private boolean packetsWereHeld;
   private boolean releasingPackets;
   private int knockbackTicks;
   private int heldTicks;
   private int ticksSincePositionSync;
   private int lastPlaceTick;
   private long holdStartMs;
   private double frozenX;
   private double frozenY;
   private double frozenZ;
   private double savedMotionX;
   private double savedMotionY;
   private double savedMotionZ;
   private boolean motionSaved;

   public Stasis() {
      super("Stasis", Category.movement);
      this.inputLock = new StasisInputLock();
      EventBus.register(this.inputLock);
      this.registerSetting(
         this.hypixelMode = new BooleanSetting(
            "Hypixel Mode", false
         ) {
            @Override
            public void loadConfig(JsonObject json) {
               super.loadConfig(json);
               if (json.has("Delay Knockback") && json.get("Delay Knockback").isJsonPrimitive()) {
                  try {
                     if (json.getAsJsonPrimitive("Delay Knockback").getAsBoolean()) {
                        this.enable();
                     }
                  } catch (RuntimeException ignored) {
                  }
               }
            }
         }
      );
      this.registerSetting(
         this.shiftPlaceThroughWalls = new BooleanSetting(
            "Shift place through walls",
            false
         )
      );
      this.shiftPlaceThroughWalls.visible = false;
   }

   @Override
   public void guiUpdate() {
      this.shiftPlaceThroughWalls.setVisible(this.hypixelMode.isToggled(), this);
   }

   @Override
   public void onEnable() {
      StasisInputLock.reset(this.inputLock);
      if (!ClientUtils.isInWorld()) {
         this.active = false;
      } else {
         this.saveMotion();
         this.frozenOnGround = mc.thePlayer.onGround;
         this.captureFrozenPosition();
         this.resyncPosition = false;
         this.knockbackPending = false;
         this.holdingPackets = false;
         this.packetsWereHeld = false;
         this.releasingPackets = false;
         this.knockbackTicks = 0;
         this.heldTicks = 0;
         this.ticksSincePositionSync = 0;
         this.lastPlaceTick = -1;
         this.holdStartMs = 0L;
         this.active = true;
      }
   }

   @Override
   public void onDisable() {
      boolean lockInputAfter = this.active && ClientUtils.isInWorld();
      boolean restoreMotionAfter = ClientUtils.isInWorld() && this.motionSaved && !this.packetsWereHeld;
      this.releaseHeldPackets();
      if (restoreMotionAfter) {
         this.restoreMotion();
      }

      this.active = false;
      this.frozenOnGround = false;
      this.resyncPosition = false;
      this.knockbackPending = false;
      this.holdingPackets = false;
      this.packetsWereHeld = false;
      this.releasingPackets = false;
      this.knockbackTicks = 0;
      this.heldTicks = 0;
      this.ticksSincePositionSync = 0;
      this.lastPlaceTick = -1;
      this.holdStartMs = 0L;
      this.motionSaved = false;
      if (lockInputAfter) {
         StasisInputLock.lockInput(this.inputLock);
      }
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent event) {
      this.discardHeldPackets();
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent event) {
      if (this.active && ClientUtils.isInWorld()) {
         if (!this.resyncPosition && !this.knockbackPending) {
            this.freezePlayer();
         }
      }
   }

   @Subscribe
   public void onPostUpdate(PostUpdateEvent event) {
      if (this.active && ClientUtils.isInWorld()) {
         if (this.resyncPosition) {
            this.captureFrozenPosition();
            this.resyncPosition = false;
         } else if (this.knockbackPending) {
            if (++this.knockbackTicks >= 2) {
               this.captureFrozenPosition();
               this.knockbackPending = false;
               this.knockbackTicks = 0;
               this.freezePlayer();
            }
         } else {
            this.freezePlayer();
         }
      }
   }

   @Subscribe
   public void onVelocity(VelocityEvent event) {
      if (this.active && ClientUtils.isInWorld()) {
         if (!this.hypixelMode.isToggled() || this.releasingPackets) {
            if (event.s12PacketEntityVelocity.getEntityID() == mc.thePlayer.getEntityId()) {
               this.knockbackPending = true;
               this.knockbackTicks = 0;
            }
         }
      }
   }

   @Subscribe
   public void onMoveInput(MoveInputEvent event) {
      if (this.active) {
         event.setMoveForward(0.0F);
         event.setMoveStrafe(0.0F);
         event.setJumping(false);
         event.setSneaking(this.isShiftPlacing());
      }
   }

   @Subscribe
   public void onRightClick(RightClickEvent event) {
      if (this.isShiftPlacing()) {
         if (this.lastPlaceTick == mc.thePlayer.ticksExisted) {
            event.setCanceled(true);
         } else {
            if (this.tryPlaceThroughWall()) {
               this.lastPlaceTick = mc.thePlayer.ticksExisted;
               event.setCanceled(true);
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onPacketReceive(PacketReceiveEvent event) {
      if (this.active && !event.isCanceled()) {
         Packet packet = event.ys98();
         if (packet != null) {
            if (this.isWorldChangePacket(packet)) {
               this.discardHeldPackets();
            } else if (!ClientUtils.isInWorld()) {
               this.discardHeldPackets();
            } else if (packet instanceof S08PacketPlayerPosLook) {
               if (this.hypixelMode.isToggled() && this.holdingPackets) {
                  this.holdPacket(packet);
                  event.setCanceled(true);
               } else {
                  this.resyncPosition = true;
               }
            } else if (this.hypixelMode.isToggled()) {
               if (!this.holdingPackets) {
                  if (this.isSelfKnockback(packet)) {
                     this.startHolding();
                     this.holdPacket(packet);
                     event.setCanceled(true);
                  } else {
                     if (this.isExplosionKnockback(packet)) {
                        this.startHolding();
                        this.holdPacket(packet);
                        event.setCanceled(true);
                     }
                  }
               } else if (!this.isConnectionPacket(packet)) {
                  if (this.isPassthroughPacket(packet)) {
                     PacketUtils.processInboundPacket(packet);
                     event.setCanceled(true);
                  } else {
                     this.holdPacket(packet);
                     event.setCanceled(true);
                  }
               }
            }
         }
      }
   }

   @Subscribe
   public void onTickStart(TickStartEvent event) {
      if (this.active && this.hypixelMode.isToggled() && this.holdingPackets) {
         if (!ClientUtils.isInWorld() || mc.thePlayer.isDead) {
            this.discardHeldPackets();
         } else if (this.heldPackets.isEmpty()) {
            this.discardHeldPackets();
         } else {
            this.heldTicks++;
            if (this.holdExpired()) {
               this.releaseHeldPackets();
            }
         }
      }
   }

   @Subscribe
   public void onPacketSend(PacketSendEvent event) {
      if (this.active && ClientUtils.isInWorld()) {
         if (!this.resyncPosition && !this.knockbackPending) {
            if (event.ys98() instanceof C03PacketPlayer) {
               C03PacketPlayer packet = (C03PacketPlayer)event.ys98();
               if (this.ticksSincePositionSync >= 20) {
                  event.setCancelled(true);
                  this.sendFrozenPosition(packet);
                  this.ticksSincePositionSync = 0;
               } else if (!(packet instanceof C04PacketPlayerPosition) && !(packet instanceof C06PacketPlayerPosLook)) {
                  this.ticksSincePositionSync++;
               } else {
                  event.setCancelled(true);
                  this.sendStationaryPacket(packet);
                  this.ticksSincePositionSync++;
               }
            }
         }
      }
   }

   private void freezePlayer() {
      if (!ClientUtils.isInWorld()) {
         this.active = false;
      } else {
         mc.thePlayer.motionX = 0.0;
         mc.thePlayer.motionY = 0.0;
         mc.thePlayer.motionZ = 0.0;
         mc.thePlayer.fallDistance = 0.0F;
         mc.thePlayer.setPosition(this.frozenX, this.frozenY, this.frozenZ);
         mc.thePlayer.onGround = this.frozenOnGround;
      }
   }

   private void captureFrozenPosition() {
      if (!ClientUtils.isInWorld()) {
         this.active = false;
      } else {
         this.frozenX = mc.thePlayer.posX;
         this.frozenY = mc.thePlayer.getEntityBoundingBox().minY;
         this.frozenZ = mc.thePlayer.posZ;
         this.frozenOnGround = mc.thePlayer.onGround;
      }
   }

   private void saveMotion() {
      this.savedMotionX = mc.thePlayer.motionX;
      this.savedMotionY = mc.thePlayer.motionY;
      this.savedMotionZ = mc.thePlayer.motionZ;
      this.motionSaved = true;
   }

   private void restoreMotion() {
      mc.thePlayer.motionX = this.savedMotionX;
      mc.thePlayer.motionY = this.savedMotionY;
      mc.thePlayer.motionZ = this.savedMotionZ;
   }

   private void sendFrozenPosition(C03PacketPlayer packet) {
      if (packet.getRotating()) {
         PacketUtils.sendSilently(new C06PacketPlayerPosLook(this.frozenX, this.frozenY, this.frozenZ, packet.getYaw(), packet.getPitch(), this.frozenOnGround));
      } else {
         PacketUtils.sendSilently(new C04PacketPlayerPosition(this.frozenX, this.frozenY, this.frozenZ, this.frozenOnGround));
      }
   }

   private void sendStationaryPacket(C03PacketPlayer packet) {
      if (packet.getRotating()) {
         PacketUtils.sendSilently(new C05PacketPlayerLook(packet.getYaw(), packet.getPitch(), this.frozenOnGround));
      } else {
         PacketUtils.sendSilently(new C03PacketPlayer(this.frozenOnGround));
      }
   }

   private boolean isShiftPlacing() {
      return this.active
         && this.hypixelMode.isToggled()
         && this.shiftPlaceThroughWalls.isToggled()
         && ClientUtils.isInWorld()
         && mc.currentScreen == null
         && ClientUtils.xusXfhC(mc.gameSettings.keyBindSneak);
   }

   private boolean tryPlaceThroughWall() {
      ItemStack held = mc.thePlayer.getHeldItem();
      if (held != null && held.getItem() instanceof ItemBlock) {
         double reach = mc.playerController.getBlockReachDistance();
         Vec3 eyes = mc.thePlayer.getPositionEyes(1.0F);
         Vec3 look = mc.thePlayer.getLook(1.0F);
         Vec3 rayEnd = eyes.addVector(look.xCoord * reach, look.yCoord * reach, look.zCoord * reach);
         MovingObjectPosition hit = mc.theWorld.rayTraceBlocks(eyes, rayEnd, false, false, false);
         if (hit != null && hit.typeOfHit == MovingObjectType.BLOCK && hit.getBlockPos() != null) {
            BlockPos hitPos = hit.getBlockPos();
            StasisPlaceTarget target = this.findRayExit(hitPos, hit.hitVec, look);
            if (target == null) {
               return false;
            } else {
               BlockPos placePos = hitPos.offset(StasisPlaceTarget.face(target));
               if (BlockUtils.isReplaceableAt(hitPos) || !BlockUtils.isReplaceableAt(placePos)) {
                  return false;
               } else if (!BlockUtils.canPlaceItemOnSide(held, hitPos, StasisPlaceTarget.face(target))) {
                  return false;
               } else if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, held, hitPos, StasisPlaceTarget.face(target), StasisPlaceTarget.hitVec(target))) {
                  mc.thePlayer.swingItem();
                  return true;
               } else {
                  return false;
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private StasisPlaceTarget findRayExit(BlockPos pos, Vec3 hitVec, Vec3 look) {
      if (hitVec != null && look != null) {
         double nearest = Double.MAX_VALUE;
         EnumFacing exitFace = null;
         double localX = hitVec.xCoord - pos.getX();
         double localY = hitVec.yCoord - pos.getY();
         double localZ = hitVec.zCoord - pos.getZ();
         if (look.xCoord > EPSILON) {
            double distX = (1.0 - localX) / look.xCoord;
            if (distX > EPSILON && distX < nearest) {
               nearest = distX;
               exitFace = EnumFacing.EAST;
            }
         } else if (look.xCoord < -EPSILON) {
            double distX = (0.0 - localX) / look.xCoord;
            if (distX > EPSILON && distX < nearest) {
               nearest = distX;
               exitFace = EnumFacing.WEST;
            }
         }

         if (look.yCoord > EPSILON) {
            double distY = (1.0 - localY) / look.yCoord;
            if (distY > EPSILON && distY < nearest) {
               nearest = distY;
               exitFace = EnumFacing.UP;
            }
         } else if (look.yCoord < -EPSILON) {
            double distY = (0.0 - localY) / look.yCoord;
            if (distY > EPSILON && distY < nearest) {
               nearest = distY;
               exitFace = EnumFacing.DOWN;
            }
         }

         if (look.zCoord > EPSILON) {
            double distZ = (1.0 - localZ) / look.zCoord;
            if (distZ > EPSILON && distZ < nearest) {
               nearest = distZ;
               exitFace = EnumFacing.SOUTH;
            }
         } else if (look.zCoord < -EPSILON) {
            double distZ = (0.0 - localZ) / look.zCoord;
            if (distZ > EPSILON && distZ < nearest) {
               nearest = distZ;
               exitFace = EnumFacing.NORTH;
            }
         }

         return exitFace == null ? null : new StasisPlaceTarget(exitFace, hitVec.addVector(look.xCoord * nearest, look.yCoord * nearest, look.zCoord * nearest));
      } else {
         return null;
      }
   }

   private void holdPacket(Packet<?> packet) {
      synchronized (this.heldPackets) {
         this.heldPackets.addLast(packet);
      }
   }

   private void startHolding() {
      this.holdingPackets = true;
      this.packetsWereHeld = true;
      this.heldTicks = 0;
      this.holdStartMs = System.currentTimeMillis();
   }

   private void releaseHeldPackets() {
      ArrayList packets = new ArrayList();
      synchronized (this.heldPackets) {
         while (!this.heldPackets.isEmpty()) {
            packets.add(this.heldPackets.removeFirst());
         }
      }

      this.heldTicks = 0;
      this.holdStartMs = 0L;
      this.releasingPackets = true;

      try {
         for (Packet packet : (java.lang.Iterable<Packet>) (java.lang.Iterable<?>) (packets)) {
            PacketUtils.processInboundPacket(packet);
         }
      } finally {
         this.releasingPackets = false;
      }

      this.holdingPackets = false;
   }

   private void discardHeldPackets() {
      synchronized (this.heldPackets) {
         this.heldPackets.clear();
      }

      this.holdingPackets = false;
      this.heldTicks = 0;
      this.holdStartMs = 0L;
   }

   private boolean holdExpired() {
      return this.heldTicks >= MAX_HOLD_TICKS || this.holdStartMs > 0L && System.currentTimeMillis() - this.holdStartMs >= MAX_HOLD_MS;
   }

   @Override
   public boolean isProgressActive() {
      return this.isEnabled() && this.hypixelMode.isToggled() && this.holdingPackets;
   }

   public boolean isHypixelModeActive() {
      return this.isEnabled() && this.hypixelMode.isToggled();
   }

   public boolean isHoldingPackets() {
      return this.isEnabled() && this.hypixelMode.isToggled() && this.holdingPackets;
   }

   @Override
   public float getProgressFraction() {
      return Math.max(0.0F, Math.min(1.0F, this.heldTicks / (float)MAX_HOLD_TICKS));
   }

   @Override
   public String getProgressLabel() {
      return "Stasis";
   }

   private boolean isWorldChangePacket(Packet<?> packet) {
      return packet instanceof S01PacketJoinGame || packet instanceof S07PacketRespawn || packet instanceof S40PacketDisconnect;
   }

   private boolean isConnectionPacket(Packet<?> packet) {
      return packet instanceof S00PacketKeepAlive || packet instanceof S48PacketResourcePackSend;
   }

   private boolean isPassthroughPacket(Packet<?> packet) {
      return packet instanceof S02PacketChat
         || packet instanceof S45PacketTitle
         || this.isWorldUpdatePacket(packet)
         || this.isPlayerStatusPacket(packet)
         || this.isEntityPacket(packet)
         || this.isScoreboardPacket(packet)
         || this.isSoundPacket(packet);
   }

   private boolean isWorldUpdatePacket(Packet<?> packet) {
      return packet instanceof S21PacketChunkData
         || packet instanceof S22PacketMultiBlockChange
         || packet instanceof S23PacketBlockChange
         || packet instanceof S24PacketBlockAction
         || packet instanceof S25PacketBlockBreakAnim
         || packet instanceof S26PacketMapChunkBulk
         || packet instanceof S28PacketEffect
         || packet instanceof S35PacketUpdateTileEntity
         || packet instanceof S33PacketUpdateSign;
   }

   private boolean isEntityPacket(Packet<?> packet) {
      return packet instanceof S0CPacketSpawnPlayer
         || packet instanceof S0FPacketSpawnMob
         || packet instanceof S0EPacketSpawnObject
         || packet instanceof S10PacketSpawnPainting
         || packet instanceof S11PacketSpawnExperienceOrb
         || packet instanceof S2CPacketSpawnGlobalEntity
         || this.isOtherEntityVelocity(packet)
         || packet instanceof S13PacketDestroyEntities
         || packet instanceof S14PacketEntity
         || packet instanceof S18PacketEntityTeleport
         || packet instanceof S19PacketEntityHeadLook
         || packet instanceof S0BPacketAnimation
         || packet instanceof S0APacketUseBed
         || packet instanceof S1BPacketEntityAttach
         || packet instanceof S1CPacketEntityMetadata
         || packet instanceof S04PacketEntityEquipment
         || packet instanceof S1DPacketEntityEffect
         || packet instanceof S1EPacketRemoveEntityEffect
         || packet instanceof S20PacketEntityProperties
         || packet instanceof S19PacketEntityStatus
         || packet instanceof S0DPacketCollectItem;
   }

   private boolean isOtherEntityVelocity(Packet<?> packet) {
      if (!(packet instanceof S12PacketEntityVelocity)) {
         return false;
      } else {
         S12PacketEntityVelocity velocity = (S12PacketEntityVelocity)packet;
         return mc.thePlayer == null || velocity.getEntityID() != mc.thePlayer.getEntityId();
      }
   }

   private boolean isPlayerStatusPacket(Packet<?> packet) {
      return packet instanceof S06PacketUpdateHealth
         || packet instanceof S1CPacketEntityMetadata
         || packet instanceof S1DPacketEntityEffect
         || packet instanceof S1EPacketRemoveEntityEffect
         || packet instanceof S20PacketEntityProperties
         || packet instanceof S19PacketEntityStatus;
   }

   private boolean isScoreboardPacket(Packet<?> packet) {
      return packet instanceof S3BPacketScoreboardObjective
         || packet instanceof S3CPacketUpdateScore
         || packet instanceof S3DPacketDisplayScoreboard
         || packet instanceof S3EPacketTeams
         || packet instanceof S38PacketPlayerListItem
         || packet instanceof S47PacketPlayerListHeaderFooter;
   }

   private boolean isSoundPacket(Packet<?> packet) {
      return packet instanceof S29PacketSoundEffect || packet instanceof S28PacketEffect;
   }

   private boolean isSelfKnockback(Packet<?> packet) {
      if (packet instanceof S12PacketEntityVelocity && mc.thePlayer != null) {
         S12PacketEntityVelocity velocity = (S12PacketEntityVelocity)packet;
         return velocity.getEntityID() == mc.thePlayer.getEntityId() && (velocity.getMotionX() != 0 || velocity.getMotionY() != 0 || velocity.getMotionZ() != 0);
      } else {
         return false;
      }
   }

   private boolean isExplosionKnockback(Packet<?> packet) {
      if (!(packet instanceof S27PacketExplosion)) {
         return false;
      } else {
         S27PacketExplosion explosion = (S27PacketExplosion)packet;
         double motionX = explosion.func_149149_c();
         double motionY = explosion.func_149144_d();
         double motionZ = explosion.func_149147_e();
         return motionX * motionX + motionY * motionY + motionZ * motionZ > EPSILON;
      }
   }
}
