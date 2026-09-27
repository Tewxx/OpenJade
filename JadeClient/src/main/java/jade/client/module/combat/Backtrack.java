// Jade recovery: module: Backtrack (combat); original class: jade.deps.eLz.RRPEugveA
package jade.client.module.combat;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.PacketUtils;
import jade.client.common.RenderUtils;
import jade.client.common.ScreenProjector;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.AttackEntityEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.TickStartEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.Blink;
import jade.client.module.shared.TargetFinder;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorS14PacketEntity;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@ModuleInfo
public class Backtrack extends Module implements ExternalRenderableModule {
   private static final long SLAk = 100L;
   private static final double POSITION_EPSILON = 1.0E-6;
   private static final float INDICATOR_HEIGHT_MARGIN = 0.15F;
   private final SliderSetting minDistance;
   private final SliderSetting maxDistance;
   private final SliderSetting maximumDelay;
   private final SliderSetting maxHurtTime;
   private final SliderSetting cooldown;
   private final BooleanSetting disableOnHit;
   private final BooleanSetting requireLeftMouse;
   private final BooleanSetting requireWeapon;
   private final BooleanSetting realPositionIndicator;
   private final ColorSetting indicatorColor;
   private final SliderSetting lineWidth;
   private final BooleanSetting filled;
   private final BooleanSetting applyHeadRotation;
   private volatile EntityPlayer entityPlayer;
   private volatile boolean ZSVpEk;
   private volatile boolean LAt;
   private double kybYo;
   private double KRCvrS;
   private double laggedZ;
   private long cooldownUntil;
   private int previousHurtTime;
   private volatile boolean DfVxse;
   private int targetPreviousHurtTime;
   private int lastAttackTick;
   private Vec3 fgY;
   private Vec3 previousIndicatorPosition;
   private long indicatorLastUpdateMillis;
   private final Deque<Backtrack$0> heldPackets = new ArrayDeque<>();
   private final Object packetQueueLock = new Object();

   public Backtrack() {
      super("Backtrack", Category.combat);
      this.registerSetting(new DescriptionSetting("Distance range"));
      this.registerSetting(
         this.minDistance = new SliderSetting(
            "Min distance", " blocks", 0.0, 0.0, 4.0, 0.1
         )
      );
      this.registerSetting(
         this.maxDistance = new SliderSetting(
            "Max distance", " blocks", 6.0, 1.0, 10.0, 0.1
         )
      );
      this.registerSetting(new DescriptionSetting("Distances at which Backtrack may activate."));
      this.registerSetting(new DescriptionSetting("Players closer than min or further than max"));
      this.registerSetting(new DescriptionSetting("are ignored."));
      this.registerSetting(
         this.maximumDelay = new SliderSetting(
            "Maximum delay", "ms", 200.0, 50.0, 600.0, 10.0
         )
      );
      this.registerSetting(new DescriptionSetting("Max time the module may block packets for."));
      this.registerSetting(new DescriptionSetting("Higher values keep players closer for longer,"));
      this.registerSetting(new DescriptionSetting("giving more time to hit them."));
      this.registerSetting(
         this.maxHurtTime = new SliderSetting(
            "Max hurt time", "ms", 500.0, 0.0, 500.0, 10.0
         )
      );
      this.registerSetting(new DescriptionSetting("Limits how high the target's hurt time can"));
      this.registerSetting(new DescriptionSetting("be before Backtrack activates."));
      this.registerSetting(
         this.cooldown = new SliderSetting(
            "Cooldown", "ms", 0.0, 0.0, 2000.0, 50.0
         )
      );
      this.registerSetting(new DescriptionSetting("Delay after Backtrack deactivates before it"));
      this.registerSetting(new DescriptionSetting("can start lagging a player again."));
      this.registerSetting(
         this.disableOnHit = new BooleanSetting(
            "Disable on hit", true
         )
      );
      this.registerSetting(new DescriptionSetting("Immediately stops lagging if you take knockback."));
      this.registerSetting(new DescriptionSetting("Conditions"));
      this.registerSetting(
         this.requireWeapon = new BooleanSetting(
            "Require weapon", false
         )
      );
      this.registerSetting(new DescriptionSetting("Only activate while holding a weapon."));
      this.registerSetting(this.requireLeftMouse = new BooleanSetting("Require Left mouse", true));
      this.registerSetting(new DescriptionSetting("Only activate while holding left click."));
      this.registerSetting(new DescriptionSetting("Real position indicator"));
      this.registerSetting(
         this.realPositionIndicator = new BooleanSetting(
            "Real position indicator",
            true
         )
      );
      this.registerSetting(new DescriptionSetting("Renders a box where the player would be"));
      this.registerSetting(new DescriptionSetting("standing if there was no lag."));
      this.registerSetting(
         this.indicatorColor = new ColorSetting(
            "Indicator color",
            255,
            50,
            50,
            150
         )
      );
      this.registerSetting(this.lineWidth = new SliderSetting("Line width", 2.0, 1.0, 5.0, 0.5));
      this.registerSetting(this.filled = new BooleanSetting("Filled", true));
      this.registerSetting(
         this.applyHeadRotation = new BooleanSetting(
            "Apply head rotation",
            true
         )
      );
      this.initialized = true;
   }

   @Override
   public void guiUpdate() {
      boolean var1 = this.realPositionIndicator.isToggled();
      this.indicatorColor.setVisible(var1, this);
      this.lineWidth.setVisible(var1, this);
      this.filled.setVisible(var1, this);
      this.applyHeadRotation.setVisible(var1, this);
   }

   @Override
   public void onEnable() {
      if (isBlinkConflict()) {
         ClientUtils.sendColoredMessage("&cBacktrack conflicts with Blink inbound. Disable Blink or use outbound-only.");
         this.disable();
      } else {
         this.resetLagState();
      }
   }

   @Override
   public void onDisable() {
      this.stopBacktracking(false);
   }

   @Override
   public String getInfo() {
      return (int)this.maximumDelay.getInput() + "ms";
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (this.isEnabled() && !var1.isCanceled() && ClientUtils.isInWorld()) {
         Packet var2 = var1.ys98();
         if (!(var2 instanceof S08PacketPlayerPosLook) && !(var2 instanceof S40PacketDisconnect)) {
            EntityPlayer var3 = this.entityPlayer;
            if (this.ZSVpEk && var3 != null) {
               int var4 = var3.getEntityId();
               if (var2 instanceof S13PacketDestroyEntities) {
                  for (int var8 : ((S13PacketDestroyEntities)var2).getEntityIDs()) {
                     if (var8 == var4) {
                        this.LAt = true;
                        return;
                     }
                  }
               } else {
                  if (var2 instanceof S14PacketEntity) {
                     if (((IAccessorS14PacketEntity)var2).getEntityId() != var4) {
                        return;
                     }

                     S14PacketEntity var5 = (S14PacketEntity)var2;
                     synchronized (this.packetQueueLock) {
                        this.kybYo = this.kybYo + var5.func_149062_c() / 32.0;
                        this.KRCvrS = this.KRCvrS + var5.func_149061_d() / 32.0;
                        this.laggedZ = this.laggedZ + var5.func_149064_e() / 32.0;
                        this.heldPackets.addLast(new Backtrack$0(var2));
                     }

                     var1.setCanceled(true);
                  } else if (var2 instanceof S18PacketEntityTeleport) {
                     S18PacketEntityTeleport var13 = (S18PacketEntityTeleport)var2;
                     if (var13.getEntityId() != var4) {
                        return;
                     }

                     synchronized (this.packetQueueLock) {
                        this.kybYo = var13.getX() / 32.0;
                        this.KRCvrS = var13.getY() / 32.0;
                        this.laggedZ = var13.getZ() / 32.0;
                        this.heldPackets.addLast(new Backtrack$0(var2));
                     }

                     var1.setCanceled(true);
                  }
               }
            }
         } else {
            if (this.ZSVpEk) {
               this.LAt = true;
            }
         }
      }
   }

   @Subscribe
   public void onAttackEntity(AttackEntityEvent var1) {
      if (this.isEnabled() && ClientUtils.isInWorld() && var1.entityPlayer == mc.thePlayer) {
         if (!this.isOnCooldown()) {
            if (this.isLeftMouseHeld()) {
               if (!this.requireWeapon.isToggled() || this.isHoldingWeapon()) {
                  EntityPlayer var2 = TargetFinder.TDBVqmH(var1.entity, this.maxDistance.getInput() * this.maxDistance.getInput());
                  if (var2 != null && this.hUoh(var2)) {
                     if (this.entityPlayer == var2) {
                        this.lastAttackTick = mc.thePlayer.ticksExisted;
                     } else {
                        if (this.ZSVpEk || this.DfVxse) {
                           this.stopBacktracking(false);
                        }

                        this.lastAttackTick = mc.thePlayer.ticksExisted;
                        this.armBacktrack(var2);
                     }
                  }
               }
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onTickStart(TickStartEvent var1) {
      if (!this.isEnabled() || !ClientUtils.isInWorld() || mc.thePlayer.isDead) {
         this.stopBacktracking(true);
      } else if (this.LAt) {
         this.LAt = false;
         this.stopBacktracking(true);
      } else {
         if (this.disableOnHit.isToggled() && this.ZSVpEk) {
            int var2 = mc.thePlayer.hurtTime;
            if (var2 > this.previousHurtTime) {
               this.stopBacktracking(true);
               this.cooldownUntil = Math.max(this.cooldownUntil, System.currentTimeMillis() + var2 * 50L);
               this.previousHurtTime = var2;
               return;
            }

            this.previousHurtTime = var2;
         }

         if (!this.isOnCooldown()) {
            if (!this.ZSVpEk && !this.DfVxse) {
               this.tryActivateBacktrack();
            } else if (this.entityPlayer == null || !this.NpD1(this.entityPlayer)) {
               this.stopBacktracking(true);
            } else if (this.DfVxse) {
               this.beginLagOnHurtIncrease();
            } else if (!this.isLagStillUseful()) {
               this.flushAllQueuedPackets();
            } else {
               this.releaseExpiredPackets();
            }
         } else {
            if (this.ZSVpEk || this.DfVxse) {
               this.stopBacktracking(true);
            }
         }
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (this.isEnabled() && this.ZSVpEk && this.realPositionIndicator.isToggled() && this.entityPlayer != null && ClientUtils.isInWorld()) {
         EntityPlayer var2 = this.entityPlayer;
         if (var2 != null && this.NpD1(var2)) {
            RenderManager var3 = mc.getRenderManager();
            Vec3 var4;
            synchronized (this.packetQueueLock) {
               var4 = new Vec3(this.kybYo, this.KRCvrS, this.laggedZ);
            }

            Vec3 var21 = this.smoothIndicatorPosition(var4);
            if (this.VIuQ()) {
               this.renderIndicatorExternal(var2, var21);
            } else {
               double var6 = var21.xCoord - var3.viewerPosX;
               double var8 = var21.yCoord - var3.viewerPosY;
               double var10 = var21.zCoord - var3.viewerPosZ;
               int var12 = this.indicatorColor.getArgb();
               float var13 = (var12 >> 16 & 0xFF) / 255.0F;
               float var14 = (var12 >> 8 & 0xFF) / 255.0F;
               float var15 = (var12 & 0xFF) / 255.0F;
               float var16 = (var12 >> 24 & 0xFF) / 255.0F;
               float var17 = var2.width / 2.0F;
               float var18 = var2.height + 0.15F;
               AxisAlignedBB var19 = new AxisAlignedBB(-var17, 0.0, -var17, var17, var18, var17);
               GlStateManager.pushMatrix();
               GlStateManager.translate(var6, var8, var10);
               if (this.applyHeadRotation.isToggled()) {
                  GlStateManager.rotate(-var2.rotationYawHead, 0.0F, 1.0F, 0.0F);
               }

               GlStateManager.disableTexture2D();
               GlStateManager.enableBlend();
               GlStateManager.disableDepth();
               GlStateManager.depthMask(false);
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               GlStateManager.disableLighting();
               if (this.filled.isToggled()) {
                  RenderUtils.drawFilledAabb(var19, var13, var14, var15, var16);
               }

               GL11.glLineWidth((float)this.lineWidth.getInput());
               GL11.glColor4f(var13, var14, var15, var16);
               RenderUtils.drawBoxOutline(var19);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.enableDepth();
               GlStateManager.depthMask(true);
               GlStateManager.disableBlend();
               GlStateManager.enableTexture2D();
               GlStateManager.enableLighting();
               GlStateManager.popMatrix();
            }
         } else {
            this.resetIndicators();
         }
      }
   }

   private void renderIndicatorExternal(EntityPlayer var1, Vec3 var2) {
      ExternalRenderBuffer var3 = ExternalRenderer.getActiveRenderBuffer();
      ScreenProjector var4 = ExternalRenderer.getActiveScreenProjector();
      if (var3 != null && var4 != null) {
         RenderManager var5 = mc.getRenderManager();
         float var6 = var1.width / 2.0F;
         float var7 = var1.height + 0.15F;
         double var8 = var2.xCoord - var5.viewerPosX;
         double var10 = var2.yCoord - var5.viewerPosY;
         double var12 = var2.zCoord - var5.viewerPosZ;
         int var14 = this.indicatorColor.getArgb();
         boolean var15 = var4.drawProjectedBox(var8 - var6, var10, var12 - var6, var8 + var6, var10 + var7, var12 + var6, var3, var14, 0.0F, false);
         if (var15 && this.filled.isToggled()) {
            var4.HIQRn(var3, var14);
         }

         if (var15) {
            var4.drawProjectedBox(var8 - var6, var10, var12 - var6, var8 + var6, var10 + var7, var12 + var6, var3, var14, (float)this.lineWidth.getInput(), true);
         }
      }
   }

   private void beginLagging(EntityPlayer var1) {
      synchronized (this.packetQueueLock) {
         this.heldPackets.clear();
         this.kybYo = var1.posX;
         this.KRCvrS = var1.posY;
         this.laggedZ = var1.posZ;
      }

      this.resetIndicators();
      this.LAt = false;
      this.DfVxse = false;
      this.entityPlayer = var1;
      this.ZSVpEk = true;
   }

   private void tryActivateBacktrack() {
      if (this.isLeftMouseHeld()) {
         if (!this.requireWeapon.isToggled() || this.isHoldingWeapon()) {
            EntityPlayer var1 = TargetFinder.findTarget(this.maxDistance.getInput() * this.maxDistance.getInput());
            if (var1 != null && this.hUoh(var1)) {
               this.armBacktrack(var1);
            }
         }
      }
   }

   private void armBacktrack(EntityPlayer var1) {
      synchronized (this.packetQueueLock) {
         this.heldPackets.clear();
         this.kybYo = var1.posX;
         this.KRCvrS = var1.posY;
         this.laggedZ = var1.posZ;
      }

      this.resetIndicators();
      this.LAt = false;
      this.targetPreviousHurtTime = var1.hurtTime;
      this.entityPlayer = var1;
      this.ZSVpEk = false;
      this.DfVxse = true;
   }

   private void beginLagOnHurtIncrease() {
      EntityPlayer var1 = this.entityPlayer;
      if (var1 != null) {
         int var2 = var1.hurtTime;
         boolean var3 = mc.thePlayer.ticksExisted - this.lastAttackTick <= 10;
         boolean var4 = var2 > this.targetPreviousHurtTime && var3;
         this.targetPreviousHurtTime = var2;
         if (var4) {
            this.beginLagging(var1);
         }
      }
   }

   private void releaseExpiredPackets() {
      long var1 = (long)this.maximumDelay.getInput();
      long var3 = System.currentTimeMillis();
      ArrayList var5 = null;
      synchronized (this.packetQueueLock) {
         for (; !this.heldPackets.isEmpty() && var3 - this.heldPackets.peekFirst().MLho >= var1; var5.add(this.heldPackets.pollFirst().packet)) {
            if (var5 == null) {
               var5 = new ArrayList();
            }
         }
      }

      this.processHeldPackets(var5);
   }

   private void flushAllQueuedPackets() {
      ArrayList var1 = null;
      synchronized (this.packetQueueLock) {
         if (!this.heldPackets.isEmpty()) {
            var1 = new ArrayList(this.heldPackets.size());

            while (!this.heldPackets.isEmpty()) {
               var1.add(this.heldPackets.pollFirst().packet);
            }
         }
      }

      this.processHeldPackets(var1);
   }

   private void processHeldPackets(List<Packet<?>> var1) {
      if (var1 != null) {
         for (Packet var3 : var1) {
            PacketUtils.processInboundPacket(var3);
         }
      }
   }

   private void stopBacktracking(boolean var1) {
      this.flushAllQueuedPackets();
      if (this.ZSVpEk && var1) {
         this.cooldownUntil = System.currentTimeMillis() + (long)this.cooldown.getInput();
      }

      this.ZSVpEk = false;
      this.DfVxse = false;
      this.entityPlayer = null;
      this.LAt = false;
      this.resetIndicators();
   }

   private void resetLagState() {
      synchronized (this.packetQueueLock) {
         this.heldPackets.clear();
         this.kybYo = this.KRCvrS = this.laggedZ = 0.0;
      }

      this.ZSVpEk = false;
      this.DfVxse = false;
      this.entityPlayer = null;
      this.LAt = false;
      this.cooldownUntil = 0L;
      this.previousHurtTime = 0;
      this.targetPreviousHurtTime = 0;
      this.resetIndicators();
   }

   private boolean isOnCooldown() {
      return this.cooldown.getInput() > 0.0 && System.currentTimeMillis() < this.cooldownUntil;
   }

   public boolean isLagging() {
      return this.isEnabled() && this.ZSVpEk;
   }

   private boolean isHoldingWeapon() {
      if (mc.thePlayer.getHeldItem() == null) {
         return false;
      } else {
         Item var1 = mc.thePlayer.getHeldItem().getItem();
         return var1 instanceof ItemSword || var1 instanceof ItemAxe;
      }
   }

   private boolean NpD1(EntityPlayer var1) {
      if (!TargetFinder.isValidTarget(var1)) {
         return false;
      } else if (!this.isLeftMouseHeld()) {
         return false;
      } else {
         return this.requireWeapon.isToggled() && !this.isHoldingWeapon() ? false : this.hUoh(var1);
      }
   }

   private boolean isLeftMouseHeld() {
      return !this.requireLeftMouse.isToggled() || mc.gameSettings.keyBindAttack.isKeyDown() || Mouse.isButtonDown(0);
   }

   private boolean hUoh(EntityPlayer var1) {
      double var2 = mc.thePlayer.getDistanceToEntity(var1);
      return !(var2 < this.minDistance.getInput()) && !(var2 > this.maxDistance.getInput()) ? this.maxHurtTime.getInput() >= 500.0 || var1.hurtTime * 50 <= (int)this.maxHurtTime.getInput() : false;
   }

   private boolean isLagStillUseful() {
      Vec3 var1;
      boolean var2;
      synchronized (this.packetQueueLock) {
         var2 = this.heldPackets.isEmpty();
         var1 = new Vec3(this.kybYo, this.KRCvrS, this.laggedZ);
      }

      if (var2) {
         return true;
      } else {
         Vec3 var9 = mc.thePlayer.getPositionVector();
         double var4 = this.entityPlayer.getPositionVector().distanceTo(var9);
         double var6 = var1.distanceTo(var9);
         return var6 + 1.0E-4 >= var4;
      }
   }

   private Vec3 smoothIndicatorPosition(Vec3 var1) {
      long var2 = System.currentTimeMillis();
      if (this.previousIndicatorPosition == null) {
         this.fgY = var1;
         this.previousIndicatorPosition = var1;
         this.indicatorLastUpdateMillis = var2;
      } else if (this.hasPositionChanged(var1, this.previousIndicatorPosition)) {
         double var4 = this.Nibw4(var2);
         this.fgY = this.UnhfdN(this.fgY, this.previousIndicatorPosition, var4);
         this.previousIndicatorPosition = var1;
         this.indicatorLastUpdateMillis = var2;
      }

      return this.UnhfdN(this.fgY, this.previousIndicatorPosition, this.Nibw4(var2));
   }

   private double Nibw4(long var1) {
      return Math.min(1.0, (var1 - this.indicatorLastUpdateMillis) / 100.0);
   }

   private boolean hasPositionChanged(Vec3 var1, Vec3 var2) {
      return Math.abs(var1.xCoord - var2.xCoord) > 1.0E-6 || Math.abs(var1.yCoord - var2.yCoord) > 1.0E-6 || Math.abs(var1.zCoord - var2.zCoord) > 1.0E-6;
   }

   private void resetIndicators() {
      this.fgY = null;
      this.previousIndicatorPosition = null;
      this.indicatorLastUpdateMillis = 0L;
   }

   private Vec3 UnhfdN(Vec3 var1, Vec3 var2, double var3) {
      return new Vec3(
         var1.xCoord + (var2.xCoord - var1.xCoord) * var3, var1.yCoord + (var2.yCoord - var1.yCoord) * var3, var1.zCoord + (var2.zCoord - var1.zCoord) * var3
      );
   }

   private static boolean isBlinkConflict() {
      Blink var0 = Jade.getModuleManager().getModule(Blink.class);
      return var0 != null && var0.isEnabled() && var0.isInboundMode();
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Conditions",
            "Cooldown",
            new String[]{"disable on hit", "require left mouse", "require weapon"},
            new String[]{"Not after hit", "Left mouse held", "Holding weapon"}
         )
      );
   }
}
