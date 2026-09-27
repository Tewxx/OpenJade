// Jade recovery: module: Buffer (player); original class: jade.deps.eLz.reDAL01jZS
package jade.client.module.player;

import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.PacketUtils;
import jade.client.common.Subscribe;
import jade.client.event.LeftClickEvent;
import jade.client.event.LoadWorldEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.RenderTickEvent;
import jade.client.event.RightClickEvent;
import jade.client.event.TickStartEvent;
import jade.client.event.UseItemEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.render.Arraylist;
import jade.client.module.shared.ProgressBarSource;
import jade.client.setting.BooleanSetting;
import jade.client.setting.GroupSetting;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
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
import org.lwjgl.opengl.GL11;

@ModuleInfo
public class Buffer extends Module implements ProgressBarSource {
   private static final int PKBdr = 300;
   private static final double MOTION_EPSILON = 1.0E-6;
   private static final double ARROW_SCALE_FACTOR = 0.78;
   private final BooleanSetting disableOnInteract;
   private final BooleanSetting showProgressBar;
   private final BooleanSetting showKnockbackDirection;
   private final GroupSetting updateGroup;
   private final BooleanSetting chatMessages;
   private final BooleanSetting blockChanges;
   private final BooleanSetting playerHealth;
   private final BooleanSetting playerPositions;
   private final BooleanSetting scoreboard;
   private final BooleanSetting soundEffects;
   private final Deque<Packet<?>> PzU = new ArrayDeque<>();
   private boolean LxX9;
   private boolean ignoreNextKnockback;
   private int bufferedTicks;
   private double knockbackMotionX;
   private double knockbackMotionY;
   private double knockbackMotionZ;

   public Buffer() {
      super("Buffer", Category.player);
      this.registerSetting(
         this.disableOnInteract = new BooleanSetting(
            "Disable on interact",
            false
         )
      );
      this.registerSetting(
         this.showProgressBar = new BooleanSetting(
            "Show progress bar", true
         )
      );
      this.registerSetting(
         this.showKnockbackDirection = new BooleanSetting(
            "Show knockback direction",
            true
         )
      );
      this.registerSetting(this.updateGroup = new GroupSetting("Update"));
      this.registerSetting(
         this.chatMessages = new BooleanSetting(
            this.updateGroup,
            "Chat messages",
            true,
            new String[]{"Update chat messages"}
         )
      );
      this.registerSetting(
         this.blockChanges = new BooleanSetting(
            this.updateGroup,
            "Block changes",
            true,
            new String[]{"Update block changes"}
         )
      );
      this.registerSetting(
         this.playerHealth = new BooleanSetting(
            this.updateGroup,
            "Player health",
            true,
            new String[]{"Update player health"}
         )
      );
      this.registerSetting(
         this.playerPositions = new BooleanSetting(
            this.updateGroup,
            "Player positions",
            true,
            new String[]{"Update player positions"}
         )
      );
      this.registerSetting(this.scoreboard = new BooleanSetting(this.updateGroup, "Scoreboard", true, new String[]{"Update scoreboard"}));
      this.registerSetting(this.soundEffects = new BooleanSetting(this.updateGroup, "Sound effects", true, new String[]{"Update sound effects"}));
   }

   @Override
   public void onDisable() {
      this.flushQueuedPackets();
   }

   @Override
   public String getInfo() {
      return "";
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent var1) {
      this.clearQueue();
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (!var1.isCanceled()) {
         Packet var2 = var1.ys98();
         if (var2 != null) {
            if (this.QCzor(var2)) {
               this.clearQueue();
            } else if (!ClientUtils.isInWorld()) {
               this.clearQueue();
            } else if (!this.LxX9) {
               if (var2 instanceof S08PacketPlayerPosLook) {
                  this.ignoreNextKnockback = true;
               } else if (this.isSelfKnockbackPacket(var2)) {
                  if (this.ignoreNextKnockback) {
                     this.ignoreNextKnockback = false;
                  } else {
                     this.beginBuffering();
                     this.captureKnockbackMotion(var2);
                     this.enqueuePacket(var2);
                     var1.setCanceled(true);
                  }
               } else if (this.isSignificantExplosion(var2)) {
                  this.beginBuffering();
                  this.captureKnockbackMotion(var2);
                  this.enqueuePacket(var2);
                  var1.setCanceled(true);
               }
            } else if (!this.isPassthroughPacket(var2)) {
               if (this.isTrackedPacket(var2)) {
                  PacketUtils.processInboundPacket(var2);
                  var1.setCanceled(true);
               } else {
                  this.captureKnockbackMotion(var2);
                  this.enqueuePacket(var2);
                  var1.setCanceled(true);
               }
            }
         }
      }
   }

   @Subscribe
   public void onTickStart(TickStartEvent var1) {
      this.ignoreNextKnockback = false;
      if (this.LxX9) {
         if (ClientUtils.isInWorld() && !mc.thePlayer.isDead) {
            this.bufferedTicks++;
            if (this.bufferedTicks >= 300) {
               this.flushQueuedPackets();
               ClientUtils.sendModuleMessage(this, "&ctimed out.");
            }
         } else {
            this.flushQueuedPackets();
         }
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         if (this.LxX9 && ClientUtils.isInWorld() && mc.currentScreen == null && !mc.gameSettings.showDebugInfo) {
            ScaledResolution var2 = new ScaledResolution(mc);
            if (this.showKnockbackDirection.isToggled()) {
               this.iJuc(var2);
            }
         }
      }
   }

   @Subscribe
   public void onLeftClick(LeftClickEvent var1) {
      this.jyvz5();
   }

   @Subscribe
   public void onRightClick(RightClickEvent var1) {
      this.jyvz5();
   }

   @Subscribe
   public void onUseItem(UseItemEvent var1) {
      this.jyvz5();
   }

   private void jyvz5() {
      if (this.LxX9 && this.disableOnInteract.isToggled()) {
         this.disable();
      }
   }

   private void beginBuffering() {
      this.LxX9 = true;
      this.bufferedTicks = 0;
      this.knockbackMotionX = 0.0;
      this.knockbackMotionY = 0.0;
      this.knockbackMotionZ = 0.0;
   }

   private void enqueuePacket(Packet<?> var1) {
      synchronized (this.PzU) {
         this.PzU.addLast(var1);
      }
   }

   private void flushQueuedPackets() {
      ArrayList var1 = new ArrayList();
      synchronized (this.PzU) {
         while (!this.PzU.isEmpty()) {
            var1.add(this.PzU.removeFirst());
         }
      }

      for (Packet var3 : (java.lang.Iterable<Packet>) (java.lang.Iterable<?>) (var1)) {
         PacketUtils.processInboundPacket(var3);
      }

      this.resetBufferingState();
   }

   private void clearQueue() {
      synchronized (this.PzU) {
         this.PzU.clear();
      }

      this.resetBufferingState();
   }

   private void resetBufferingState() {
      this.LxX9 = false;
      this.ignoreNextKnockback = false;
      this.bufferedTicks = 0;
      this.knockbackMotionX = 0.0;
      this.knockbackMotionY = 0.0;
      this.knockbackMotionZ = 0.0;
   }

   @Override
   public boolean isProgressActive() {
      return this.isEnabled() && this.LxX9 && this.showProgressBar.isToggled();
   }

   public boolean isBufferEnabled() {
      return this.isEnabled();
   }

   public boolean isBufferingActive() {
      return this.isEnabled() && this.LxX9;
   }

   @Override
   public float getProgressFraction() {
      return Math.max(0.0F, Math.min(1.0F, this.bufferedTicks / 300.0F));
   }

   @Override
   public String getProgressLabel() {
      return "Buffer " + this.bufferedTicks + "t";
   }

   private boolean isSelfKnockbackPacket(Packet<?> var1) {
      if (var1 instanceof S12PacketEntityVelocity && mc.thePlayer != null) {
         S12PacketEntityVelocity var2 = (S12PacketEntityVelocity)var1;
         return var2.getEntityID() == mc.thePlayer.getEntityId() && (var2.getMotionX() != 0 || var2.getMotionY() != 0 || var2.getMotionZ() != 0);
      } else {
         return false;
      }
   }

   private boolean isSignificantExplosion(Packet<?> var1) {
      if (!(var1 instanceof S27PacketExplosion)) {
         return false;
      } else {
         S27PacketExplosion var2 = (S27PacketExplosion)var1;
         double var3 = var2.func_149149_c();
         double var5 = var2.func_149144_d();
         double var7 = var2.func_149147_e();
         return var3 * var3 + var5 * var5 + var7 * var7 > 1.0E-6;
      }
   }

   private void captureKnockbackMotion(Packet<?> var1) {
      if (var1 instanceof S12PacketEntityVelocity && mc.thePlayer != null) {
         S12PacketEntityVelocity var3 = (S12PacketEntityVelocity)var1;
         if (var3.getEntityID() == mc.thePlayer.getEntityId()) {
            this.knockbackMotionX = var3.getMotionX() / 8000.0;
            this.knockbackMotionY = var3.getMotionY() / 8000.0;
            this.knockbackMotionZ = var3.getMotionZ() / 8000.0;
         }
      } else if (var1 instanceof S27PacketExplosion) {
         S27PacketExplosion var2 = (S27PacketExplosion)var1;
         this.knockbackMotionX = mc.thePlayer.motionX + var2.func_149149_c();
         this.knockbackMotionY = mc.thePlayer.motionY + var2.func_149144_d();
         this.knockbackMotionZ = mc.thePlayer.motionZ + var2.func_149147_e();
      }
   }

   private boolean QCzor(Packet<?> var1) {
      return var1 instanceof S01PacketJoinGame || var1 instanceof S07PacketRespawn || var1 instanceof S40PacketDisconnect;
   }

   private boolean isPassthroughPacket(Packet<?> var1) {
      return var1 instanceof S00PacketKeepAlive || var1 instanceof S48PacketResourcePackSend;
   }

   private boolean isTrackedPacket(Packet<?> var1) {
      if (!this.chatMessages.isToggled() || !(var1 instanceof S02PacketChat) && !(var1 instanceof S45PacketTitle)) {
         if (this.blockChanges.isToggled() && this.isBlockChangePacket(var1)) {
            return true;
         } else if (this.playerHealth.isToggled() && this.isHealthPacket(var1)) {
            return true;
         } else if (this.playerPositions.isToggled() && this.isEntityPacket(var1)) {
            return true;
         } else {
            return this.scoreboard.isToggled() && this.isScoreboardPacket(var1) ? true : this.soundEffects.isToggled() && this.isSoundPacket(var1);
         }
      } else {
         return true;
      }
   }

   private boolean isBlockChangePacket(Packet<?> var1) {
      return var1 instanceof S21PacketChunkData
         || var1 instanceof S22PacketMultiBlockChange
         || var1 instanceof S23PacketBlockChange
         || var1 instanceof S24PacketBlockAction
         || var1 instanceof S25PacketBlockBreakAnim
         || var1 instanceof S26PacketMapChunkBulk
         || var1 instanceof S28PacketEffect
         || var1 instanceof S35PacketUpdateTileEntity
         || var1 instanceof S33PacketUpdateSign;
   }

   private boolean isEntityPacket(Packet<?> var1) {
      return var1 instanceof S0CPacketSpawnPlayer
         || var1 instanceof S0FPacketSpawnMob
         || var1 instanceof S0EPacketSpawnObject
         || var1 instanceof S10PacketSpawnPainting
         || var1 instanceof S11PacketSpawnExperienceOrb
         || var1 instanceof S2CPacketSpawnGlobalEntity
         || this.isOtherEntityVelocity(var1)
         || var1 instanceof S13PacketDestroyEntities
         || var1 instanceof S14PacketEntity
         || var1 instanceof S18PacketEntityTeleport
         || var1 instanceof S19PacketEntityHeadLook
         || var1 instanceof S0BPacketAnimation
         || var1 instanceof S0APacketUseBed
         || var1 instanceof S1BPacketEntityAttach
         || var1 instanceof S1CPacketEntityMetadata
         || var1 instanceof S04PacketEntityEquipment
         || var1 instanceof S1DPacketEntityEffect
         || var1 instanceof S1EPacketRemoveEntityEffect
         || var1 instanceof S20PacketEntityProperties
         || var1 instanceof S19PacketEntityStatus
         || var1 instanceof S0DPacketCollectItem;
   }

   private boolean isOtherEntityVelocity(Packet<?> var1) {
      if (!(var1 instanceof S12PacketEntityVelocity)) {
         return false;
      } else {
         S12PacketEntityVelocity var2 = (S12PacketEntityVelocity)var1;
         return mc.thePlayer == null || var2.getEntityID() != mc.thePlayer.getEntityId();
      }
   }

   private boolean isHealthPacket(Packet<?> var1) {
      return var1 instanceof S06PacketUpdateHealth
         || var1 instanceof S1CPacketEntityMetadata
         || var1 instanceof S1DPacketEntityEffect
         || var1 instanceof S1EPacketRemoveEntityEffect
         || var1 instanceof S20PacketEntityProperties
         || var1 instanceof S19PacketEntityStatus;
   }

   private boolean isScoreboardPacket(Packet<?> var1) {
      return var1 instanceof S3BPacketScoreboardObjective
         || var1 instanceof S3CPacketUpdateScore
         || var1 instanceof S3DPacketDisplayScoreboard
         || var1 instanceof S3EPacketTeams
         || var1 instanceof S38PacketPlayerListItem
         || var1 instanceof S47PacketPlayerListHeaderFooter;
   }

   private boolean isSoundPacket(Packet<?> var1) {
      return var1 instanceof S29PacketSoundEffect || var1 instanceof S28PacketEffect;
   }

   private void iJuc(ScaledResolution var1) {
      double var2 = this.knockbackMotionX * this.knockbackMotionX + this.knockbackMotionZ * this.knockbackMotionZ;
      if (!(var2 <= 1.0E-6)) {
         double var4 = Math.sqrt(var2);
         double var6 = this.knockbackMotionX / var4;
         double var8 = this.knockbackMotionZ / var4;
         double var10 = Math.toRadians(this.MPOs());
         double var12 = -Math.sin(var10);
         double var14 = Math.cos(var10);
         double var16 = -var14;
         double var20 = var6 * var16 + var8 * var12;
         double var22 = -(var6 * var12 + var8 * var14);
         double var24 = Math.sqrt(var20 * var20 + var22 * var22);
         if (!(var24 <= 1.0E-6)) {
            var20 /= var24;
            var22 /= var24;
            double var26 = var1.getScaledWidth() / 2.0;
            double var28 = var1.getScaledHeight() / 2.0;
            double var30 = 36.0;
            double var32 = var26 + var20 * var30;
            double var34 = var28 + var22 * var30;
            double var36 = Math.atan2(var22, var20) * (float) (180.0 / Math.PI) + 90.0;
            this.VUszsez(var32, var34, var36, this.Jyl7(var32));
         }
      }
   }

   private int Jyl7(double var1) {
      return ClientUtils.YVVZ(Arraylist.xQec0(var1 * 0.35), 230);
   }

   private float MPOs() {
      Entity var1 = mc.getRenderViewEntity();
      return var1 == null ? mc.thePlayer.rotationYaw : var1.rotationYaw;
   }

   private void VUszsez(double var1, double var3, double var5, int var7) {
      float var8 = (var7 >> 24 & 0xFF) / 255.0F;
      float var9 = (var7 >> 16 & 0xFF) / 255.0F;
      float var10 = (var7 >> 8 & 0xFF) / 255.0F;
      float var11 = (var7 & 0xFF) / 255.0F;
      GL11.glPushAttrib(1048575);
      GlStateManager.pushMatrix();

      try {
         GlStateManager.translate(var1, var3, 0.0);
         GlStateManager.rotate((float)var5, 0.0F, 0.0F, 1.0F);
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDisable(2884);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(2848);
         ArrayList var12 = this.buildArrowOutline();
         float var13 = this.SguR(var9);
         float var14 = this.SguR(var10);
         float var15 = this.SguR(var11);
         GL11.glBegin(6);
         GL11.glColor4f(var13, var14, var15, var8);
         GL11.glVertex2d(0.0, 1.2);
         GL11.glColor4f(var9, var10, var11, var8);

         for (double[] var17 : (java.lang.Iterable<double[]>) (java.lang.Iterable<?>) (var12)) {
            GL11.glVertex2d(var17[0], var17[1]);
         }

         double[] var22 = (double[])var12.get(0);
         GL11.glVertex2d(var22[0], var22[1]);
         GL11.glEnd();
         GL11.glLineWidth(1.6F);
         GL11.glColor4f(this.SysD(var9), this.SysD(var10), this.SysD(var11), Math.min(1.0F, var8 + 0.1F));
         GL11.glBegin(2);

         for (double[] var18 : (java.lang.Iterable<double[]>) (java.lang.Iterable<?>) (var12)) {
            GL11.glVertex2d(var18[0], var18[1]);
         }

         GL11.glEnd();
         GL11.glLineWidth(2.2F);
         GL11.glColor4f(var9, var10, var11, var8);
         GL11.glBegin(1);
         GL11.glVertex2d(0.0, 5.226);
         GL11.glVertex2d(0.0, 17.16);
         GL11.glEnd();
      } finally {
         GlStateManager.popMatrix();
         GL11.glPopAttrib();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private float SguR(float var1) {
      return Math.min(1.0F, var1 + (1.0F - var1) * 0.42F);
   }

   private float SysD(float var1) {
      return Math.max(0.0F, var1 * 0.42F);
   }

   private ArrayList<double[]> buildArrowOutline() {
      double[][] var1 = new double[][]{{0.0, -7.0200000000000005}, {5.46, 5.07}, {-5.46, 5.07}};
      ArrayList var2 = new ArrayList();
      double var3 = 0.18;
      byte var5 = 5;

      for (int var6 = 0; var6 < var1.length; var6++) {
         double[] var7 = var1[(var6 + var1.length - 1) % var1.length];
         double[] var8 = var1[var6];
         double[] var9 = var1[(var6 + 1) % var1.length];
         double var10 = var8[0] + (var7[0] - var8[0]) * var3;
         double var12 = var8[1] + (var7[1] - var8[1]) * var3;
         double var14 = var8[0] + (var9[0] - var8[0]) * var3;
         double var16 = var8[1] + (var9[1] - var8[1]) * var3;

         for (int var18 = 0; var18 <= var5; var18++) {
            double var19 = (double)var18 / var5;
            double var21 = 1.0 - var19;
            double var23 = var21 * var21 * var10 + 2.0 * var21 * var19 * var8[0] + var19 * var19 * var14;
            double var25 = var21 * var21 * var12 + 2.0 * var21 * var19 * var8[1] + var19 * var19 * var16;
            var2.add(new double[]{var23, var25});
         }
      }

      return var2;
   }
}
