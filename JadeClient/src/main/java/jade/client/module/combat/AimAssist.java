// Jade recovery: module: Aim Assist (combat); original class: jade.deps.eLz.PlRXHC
package jade.client.module.combat;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.RotationHandler;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.event.RotationEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.aimassist.AimAssistMode;
import jade.client.module.combat.shared.BreakableBlockWhitelist;
import jade.client.module.other.AntiBot;
import jade.client.module.player.BedNuker;
import jade.client.module.player.BridgeNuker;
import jade.client.setting.BlockListSetting;
import jade.client.setting.BooleanSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.SliderSetting;

import jade.deps.loader107.AdditiveMixConstantCipherThree;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

@ModuleInfo
public class AimAssist extends Module {
   private SliderSetting mode;
   private SliderSetting speed;
   private SliderSetting multipoint;
   private SliderSetting randomization;
   private SliderSetting fov;
   private SliderSetting distance;
   private SliderSetting sort;
   private BooleanSetting aimInvis;
   private BooleanSetting requireMouse;
   private BooleanSetting teammates;
   private BooleanSetting ignoreBehindWalls;
   private BooleanSetting targetBehindFriendly;
   private BooleanSetting targetBehindEnemy;
   private BooleanSetting stopWhenBreaking;
   private BooleanSetting breakingBlocksWhitelist;
   private BlockListSetting whitelistedBreakingBlocks;
   private BooleanSetting weaponOnly;
   private long kaY = AdditiveMixConstantCipherThree.decodeLong(-7082539507774140965L, -1996504331);
   private int IQRw = -1;
   private int aii = -2147483648;
   private float WNk;
   private float lastAppliedPitch;
   private static final long BREAK_STOP_DELAY_MS = 50L;
   private static final String[] uGubL = new String[]{"Health", "FOV", "Hurttime", "Distance"};

   public AimAssist() {
      super("Aim Assist", Category.combat);
      this.registerSetting(this.mode = new SliderSetting("Mode", AimAssistMode.NORMAL.ordinal(), AimAssistMode.labels()));
      this.registerSetting(this.speed = new SliderSetting("Speed", 10.0, 1.0, 30.0, 1.0));
      this.registerSetting(
         this.multipoint = new SliderSetting(
            (GroupSetting)null,
            "Multipoint",
            "%",
            0.0,
            0.0,
            100.0,
            1.0,
            new String[]{"Multipoint horizontal", "Multipoint vertical"}
         )
      );
      this.registerSetting(
         this.randomization = new SliderSetting(
            "Randomization", "%", 50.0, 0.0, 100.0, 1.0
         )
      );
      this.registerSetting(this.fov = new SliderSetting("FOV", 90.0, 15.0, 360.0, 1.0));
      this.registerSetting(this.distance = new SliderSetting("Distance", 4.5, 0.0, 5.0, 0.1, new String[]{"Range"}));
      this.registerSetting(
         this.sort = new SliderSetting(
            "Sort", 1, uGubL
         )
      );
      this.registerSetting(
         this.ignoreBehindWalls = new BooleanSetting(
            "Ignore behind walls",
            false
         )
      );
      this.registerSetting(
         this.targetBehindFriendly = new BooleanSetting(
            "Target behind Friendly",
            true
         )
      );
      this.registerSetting(this.targetBehindEnemy = new BooleanSetting("Target behind Enemy", true));
      this.registerSetting(
         this.aimInvis = new BooleanSetting(
            "Aim invis", false
         )
      );
      this.registerSetting(
         this.requireMouse = new BooleanSetting(
            "Require mouse", true
         )
      );
      this.registerSetting(this.teammates = new BooleanSetting("Teammates", false));
      this.registerSetting(this.stopWhenBreaking = new BooleanSetting("Stop when breaking", false));
      this.registerSetting(
         this.breakingBlocksWhitelist = new BooleanSetting(
            "Breaking Blocks Whitelist",
            false
         )
      );
      this.registerSetting(this.whitelistedBreakingBlocks = new BlockListSetting("Whitelisted Breaking Blocks"));
      BreakableBlockWhitelist.TdpPo7(this.whitelistedBreakingBlocks);
      this.breakingBlocksWhitelist.visible = false;
      this.whitelistedBreakingBlocks.visible = false;
      this.registerSetting(
         this.weaponOnly = new BooleanSetting(
            "Weapon only", false
         )
      );
   }

   @Override
   public String getInfo() {
      return this.KVfE96().getLabel();
   }

   public boolean hasTargetWhileMouseHeld() {
      return this.isEnabled() && ClientUtils.isInWorld() && this.canAimWithMouseHeld() && this.findTarget(this.isSilentMode()) != null;
   }

   public EntityPlayer GHPA() {
      if (this.isEnabled() && ClientUtils.isInWorld() && this.canAimWithMouseHeld()) {
         Entity var1 = this.findTarget(this.isSilentMode());
         return var1 instanceof EntityPlayer ? (EntityPlayer)var1 : null;
      } else {
         return null;
      }
   }

   public boolean hasTarget() {
      return this.isEnabled() && ClientUtils.isInWorld() && this.canAim(false) && this.findTarget(this.isSilentMode()) != null;
   }

   public EntityPlayer getTargetPlayer() {
      if (this.isEnabled() && ClientUtils.isInWorld() && this.canAim(false)) {
         Entity var1 = this.findTarget(this.isSilentMode());
         return var1 instanceof EntityPlayer ? (EntityPlayer)var1 : null;
      } else {
         return null;
      }
   }

   @Override
   public void guiUpdate() {
      boolean var1 = this.isLockOnMode();
      this.speed.setVisible(!var1, this);
      this.multipoint.setVisible(!var1, this);
      this.randomization.setVisible(!var1, this);
      this.breakingBlocksWhitelist.setVisible(this.stopWhenBreaking.isToggled(), this);
      this.whitelistedBreakingBlocks.setVisible(this.stopWhenBreaking.isToggled() && this.breakingBlocksWhitelist.isToggled(), this);
   }

   @Override
   public void onEnable() {
      this.LgCxk7();
   }

   @Override
   public void onDisable() {
      this.kaY = -1L;
      this.Kgnne();
      this.LgCxk7();
   }

   private void LgCxk7() {
      if (Jade.getModuleManager().getModule(AutoClicker.class) != null) {
         Jade.getModuleManager().getModule(AutoClicker.class).guiUpdate();
      }
   }

   @Subscribe(priority = EventPriority.NORMAL)
   public void onRotation(RotationEvent var1) {
      if (Jade.getModuleManager().getModule(BedNuker.class) == null || !Jade.getModuleManager().getModule(BedNuker.class).shouldOverridePointedObject()) {
         if (Jade.getModuleManager().getModule(BridgeNuker.class) == null || !Jade.getModuleManager().getModule(BridgeNuker.class).shouldOverridePointedObject()) {
            if (this.isSilentMode() && this.canAimWithMouseHeld()) {
               Entity var2 = this.findTarget(true);
               if (var2 == null) {
                  this.Kgnne();
               } else {
                  float[] var3 = this.computeSilentRotation(var2, var1);
                  if (var3 != null) {
                     this.rememberTargetRotation(var2, var3);
                     RotationHandler.getInstance().ljYma8(false);
                     var1.setRotation(var3[0], var3[1], 10);
                  }
               }
            }
         }
      }
   }

   @Override
   public void onUpdate() {
      if (!this.isSilentMode() && this.canAimWithMouseHeld()) {
         Entity var1 = this.findTarget(false);
         if (var1 == null) {
            this.Kgnne();
         } else {
            float[] var2 = this.PCzIr(var1);
            if (var2 != null) {
               var2 = RotationUtils.NSsr(var2[0], var2[1], mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
               this.rememberTargetRotation(var1, var2);
               mc.thePlayer.rotationYaw = var2[0];
               mc.thePlayer.rotationPitch = var2[1];
            }
         }
      }
   }

   public Float getLastAppliedPitch(Entity var1) {
      if (var1 == null || mc.thePlayer == null || var1.getEntityId() != this.IQRw) {
         return null;
      } else {
         return mc.thePlayer.ticksExisted - this.aii > 1 ? null : this.lastAppliedPitch;
      }
   }

   public Float getLastAppliedYaw(Entity var1) {
      if (var1 == null || mc.thePlayer == null || var1.getEntityId() != this.IQRw) {
         return null;
      } else {
         return mc.thePlayer.ticksExisted - this.aii > 1 ? null : this.WNk;
      }
   }

   private boolean isSilentMode() {
      return this.KVfE96() == AimAssistMode.SILENT;
   }

   private boolean isLockOnMode() {
      return this.KVfE96() == AimAssistMode.LOCK_ON;
   }

   private AimAssistMode KVfE96() {
      return AimAssistMode.fromSetting(this.mode.getInput());
   }

   private float[] computeSilentRotation(Entity var1, RotationEvent var2) {
      double var3 = this.getEffectiveMultipoint();
      boolean var5 = this.ebdGdj();
      return RotationHandler.getInstance()
         .getEventEntityRotationsFull(var1, var2, this.getEffectiveSpeed(), var3, var3, this.getEffectiveRandomization(), var5, this.distance.getInput(), !this.ignoreBehindWalls.isToggled(), this.targetBehindFriendly.isToggled(), this.targetBehindEnemy.isToggled());
   }

   private float[] PCzIr(Entity var1) {
      double var2 = this.getEffectiveMultipoint();
      boolean var4 = this.ebdGdj();
      return RotationHandler.getInstance()
         .getEntityRotationsFull(var1, this.getEffectiveSpeed(), var2, var2, this.getEffectiveRandomization(), var4, this.distance.getInput(), !this.ignoreBehindWalls.isToggled(), this.targetBehindFriendly.isToggled(), this.targetBehindEnemy.isToggled());
   }

   private int getEffectiveSpeed() {
      return this.isLockOnMode() ? 30 : (int)this.speed.getInput();
   }

   private double getEffectiveMultipoint() {
      return this.isLockOnMode() ? 0.0 : this.multipoint.getInput();
   }

   private float getEffectiveRandomization() {
      return this.isLockOnMode() ? 0.0F : (float)this.randomization.getInput();
   }

   private Entity findTarget(boolean var1) {
      int var2 = (int)this.fov.getInput();
      double var3 = this.distance.getInput();
      double var5 = var3 * var3;
      boolean var7 = this.ebdGdj();
      boolean var8 = this.teammates.isToggled();
      boolean var9 = this.aimInvis.isToggled();
      int var10 = (int)this.sort.getInput();
      float var11 = mc.thePlayer.rotationYaw;
      if (var1) {
         Float var12 = RotationHandler.getInstance().getTargetYaw();
         if (var12 != null) {
            var11 = var12;
         }
      }

      AutoFeed var22 = Jade.getModuleManager().getModule(AutoFeed.class);
      ArrayList<AimAssist$1> var13 = var7 ? new ArrayList<AimAssist$1>() : null;
      AimAssist$1 var14 = null;

      for (EntityPlayer var16 : mc.theWorld.playerEntities) {
         if (var16 != mc.thePlayer
            && var16.deathTime == 0
            && !ClientUtils.isFriend(var16)
            && (var8 || !ClientUtils.isTeammate(var16))
            && (var9 || !var16.isInvisible())
            && (var22 == null || !var22.wouldCancelAttack(var16))) {
            double var17 = RotationUtils.getDistanceSqToEntity(var16);
            if (!(var17 > var5) && !AntiBot.shouldHideEntity(var16)) {
               if (var2 != 360) {
                  float var19 = RotationUtils.ilaZ(var16.posX, var16.posZ);
                  if (!ClientUtils.Bhr95(var11, var2, var19)) {
                     continue;
                  }
               }

               double var25 = mc.thePlayer.getDistanceSqToEntity(var16);
               AimAssist$1 var21 = new AimAssist$1(var16, computeSortMetric(var16, var10, var25), var25);
               if (var7) {
                  var13.add(var21);
               } else if (var14 == null || compareTargets(var21, var14) < 0) {
                  var14 = var21;
               }
            }
         }
      }

      if (!var7) {
         return var14 == null ? null : AimAssist$1.getPlayer(var14);
      } else if (var13.isEmpty()) {
         return null;
      } else {
         var13.sort(AimAssist::compareTargets);
         double var23 = this.isLockOnMode() ? 0.0 : this.multipoint.getInput();
         boolean var24 = !this.ignoreBehindWalls.isToggled();
         boolean var18 = this.targetBehindFriendly.isToggled();
         boolean var26 = this.targetBehindEnemy.isToggled();

         for (AimAssist$1 var27 : (java.lang.Iterable<AimAssist$1>) (java.lang.Iterable<?>) (var13)) {
            if (RotationUtils.XKXgx5(AimAssist$1.getPlayer(var27), var23, var23, var3, var24, var18, var26)) {
               return AimAssist$1.getPlayer(var27);
            }
         }

         return null;
      }
   }

   private boolean ebdGdj() {
      return this.ignoreBehindWalls.isToggled() || !this.targetBehindFriendly.isToggled() || !this.targetBehindEnemy.isToggled();
   }

   private void rememberTargetRotation(Entity var1, float[] var2) {
      if (var1 != null && var2 != null && mc.thePlayer != null) {
         this.IQRw = var1.getEntityId();
         this.aii = mc.thePlayer.ticksExisted;
         this.WNk = var2[0];
         this.lastAppliedPitch = var2[1];
      } else {
         this.Kgnne();
      }
   }

   private void Kgnne() {
      this.IQRw = -1;
      this.aii = Integer.MIN_VALUE;
      this.WNk = 0.0F;
      this.lastAppliedPitch = 0.0F;
   }

   private static double computeSortMetric(EntityPlayer var0, int var1, double var2) {
      switch (var1) {
         case 0:
            return var0.getHealth() + var0.getAbsorptionAmount();
         case 1:
            return Math.abs(ClientUtils.getYawDifferenceToEntity(var0, false)) + Math.abs(ClientUtils.coPw(var0, false));
         case 2:
            return var0.hurtTime;
         case 3:
            return var2;
         default:
            return Math.abs(ClientUtils.getYawDifferenceToEntity(var0, false)) + Math.abs(ClientUtils.coPw(var0, false));
      }
   }

   private static int compareTargets(AimAssist$1 var0, AimAssist$1 var1) {
      int var2 = Double.compare(AimAssist$1.Dxq6(var0), AimAssist$1.Dxq6(var1));
      return var2 != 0 ? var2 : Double.compare(AimAssist$1.getDistanceSquared(var0), AimAssist$1.getDistanceSquared(var1));
   }

   private boolean canAimWithMouseHeld() {
      return this.canAim(true);
   }

   private boolean canAim(boolean var1) {
      if (mc.currentScreen != null || !mc.inGameHasFocus) {
         return false;
      } else if (this.weaponOnly.isToggled() && !ClientUtils.isHoldingWeapon()) {
         return false;
      } else if (var1 && this.requireMouse.isToggled() && !Mouse.isButtonDown(0)) {
         return false;
      } else {
         if (this.stopWhenBreaking.isToggled() && ClientUtils.isMiningBlock() && this.isTargetedBlockWhitelisted()) {
            if (this.kaY == -1L) {
               this.kaY = System.currentTimeMillis();
            }

            long var2 = System.currentTimeMillis() - this.kaY;
            if (var2 >= 50L) {
               return false;
            }
         } else {
            this.kaY = -1L;
         }

         return true;
      }
   }

   private boolean isTargetedBlockWhitelisted() {
      if (!this.breakingBlocksWhitelist.isToggled()) {
         return true;
      } else {
         MovingObjectPosition var1 = RotationUtils.traceBlockHit(mc.playerController.getBlockReachDistance(), mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
         BlockPos var2 = var1 != null && var1.typeOfHit == MovingObjectType.BLOCK ? var1.getBlockPos() : null;
         return BreakableBlockWhitelist.isBlockWhitelisted(mc, var2, this.whitelistedBreakingBlocks);
      }
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Target",
            "Sort",
            new String[]{"teammates", "ignore behind walls", "target behind friendly", "target behind enemy", "aim invis"},
            new String[]{"Teammates", "Target behind wall", "Target behind Friendly", "Target behind Enemy", "Target invisible"}
         ),
         buildSettingAlias(
            "Conditions",
            "Target",
            new String[]{"require mouse", "stop when breaking", "weapon only"},
            new String[]{"Mouse held", "Not whilst breaking", "Holding weapon"}
         )
      );
   }
}
