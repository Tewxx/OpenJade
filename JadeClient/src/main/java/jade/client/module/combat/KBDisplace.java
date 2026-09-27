// Jade recovery: module: KB Displace (combat); original class: jade.deps.eLz.vj1kHa9
package jade.client.module.combat;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.event.ClickMouseEvent;
import jade.client.event.MouseEvent;
import jade.client.event.MouseOverEvent;
import jade.client.event.MoveStateUpdateEvent;
import jade.client.event.RotationEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.shared.TargetFinder;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.ItemListSetting;
import jade.client.setting.SliderSetting;

import jade.deps.loader107.DoubleMultiplyConstantCipher;

import jade.mixin.impl.accessor.IAccessorMinecraft;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;

@ModuleInfo(aliases = {"Displace", "KB Displace", "Displacer"})
public class KBDisplace extends Module {
   private static final int CACHE_MAX_AGE_TICKS = 4;
   private static final double uqR = 0.12249999999999998;
   private final SliderSetting mode;
   private final SliderSetting blatantDelay;
   private final Map<Integer, Integer> blatantClickTicks = new HashMap<>();
   private static final int BLATANT_CLICK_WINDOW_TICKS = 10;
   private boolean previousBlatantMode;
   private final SliderSetting delay;
   private final SliderSetting direction;
   private final BooleanSetting stopWhenBreaking;
   private final BooleanSetting onlyOnTap;
   private final BooleanSetting hasKnockback;
   private final BooleanSetting itemWhitelist;
   private final ItemListSetting whitelistedItems;
   private boolean clickingThisTick = false;
   private boolean displacing = false;
   private boolean holdsKnockbackWeapon = false;
   private boolean strafeQueued = false;
   private boolean displaceLeft = false;
   private float JMaS = 0.0F;
   private boolean voidDirectionAvailable = false;
   private boolean em1 = false;
   private int TlO;
   private int attackPressTick = -1;
   private long tI5 = DoubleMultiplyConstantCipher.decodeLong(7335988364458598613L, -125916041);
   private long cdNa;
   private int PTYlB = -1;
   private boolean blatantClickQueued;
   private boolean zmpQ;
   private boolean simulatingClick;
   private int rtF = -1;
   private int GbE = -1;
   private int cachedTick = -4;
   private double kKunU;
   private double acr;
   private double YTDz;
   private boolean HZDOJ;
   private boolean cachedScanSucceeded;
   private boolean cachedDisplaceLeft;
   private boolean cachedDirectionAvailable;
   private float cachedVoidAngle;
   private static final int LEFT_DIRECTION_INDEX = 0;
   private static final int VOID_DIRECTION_INDEX = 2;
   private static final float oaI = 90.0F;
   private static final String[] DIRECTION_NAMES = new String[]{"Left", "Right", "Void"};

   public KBDisplace() {
      super("KB Displace", Category.combat);
      this.registerSetting(
         this.mode = new SliderSetting(
            "Mode", 0, new String[]{"Normal", "Blatant"}
         )
      );
      this.registerSetting(
         this.blatantDelay = new SliderSetting(
            "Blatant delay", "ms", 0.0, 0.0, 500.0, 50.0
         )
      );
      this.registerSetting(
         this.delay = new SliderSetting(
            "Delay", "ms", 100.0, 100.0, 2000.0, 50.0
         )
      );
      this.registerSetting(
         this.direction = new SliderSetting(
            "Direction", 0, DIRECTION_NAMES
         )
      );
      this.registerSetting(this.stopWhenBreaking = new BooleanSetting("Stop when breaking", false));
      this.registerSetting(
         this.onlyOnTap = new BooleanSetting(
            "Only on tap", false
         )
      );
      this.registerSetting(new DescriptionSetting("Item conditions"));
      this.registerSetting(this.hasKnockback = new BooleanSetting("Has knockback", false));
      this.registerSetting(
         this.itemWhitelist = new BooleanSetting(
            "Item whitelist", false
         )
      );
      this.registerSetting(this.whitelistedItems = new ItemListSetting("Whitelisted items"));
   }

   @Override
   public void guiUpdate() {
      this.delay.setVisible(!this.isBlatantMode(), this);
      this.blatantDelay.setVisible(this.isBlatantMode(), this);
      this.whitelistedItems.setVisible(this.itemWhitelist.isToggled(), this);
   }

   @Override
   public String getInfo() {
      int var1 = (int)Math.round(this.isBlatantMode() ? this.blatantDelay.getInput() : this.delay.getInput());
      return var1 + "ms";
   }

   @Override
   public void onEnable() {
      this.clickingThisTick = false;
      this.displacing = false;
      this.holdsKnockbackWeapon = false;
      this.strafeQueued = false;
      this.em1 = false;
      this.TlO = 0;
      this.attackPressTick = -1;
      this.clearPendingClick();
      this.cdNa = 0L;
      this.rtF = -1;
      this.blatantClickTicks.clear();
      this.previousBlatantMode = this.isBlatantMode();
      this.QWCJIKj();
   }

   @Override
   public void onDisable() {
      this.displacing = false;
      this.clickingThisTick = false;
      this.strafeQueued = false;
      this.em1 = false;
      this.attackPressTick = -1;
      this.clearPendingClick();
      this.cdNa = 0L;
      this.rtF = -1;
      this.blatantClickTicks.clear();
      this.previousBlatantMode = this.isBlatantMode();
      this.QWCJIKj();
   }

   private boolean isMovementKeyHeld() {
      return mc.gameSettings.keyBindForward.isKeyDown()
         || mc.gameSettings.keyBindBack.isKeyDown()
         || mc.gameSettings.keyBindLeft.isKeyDown()
         || mc.gameSettings.keyBindRight.isKeyDown();
   }

   @Subscribe
   public void onMouse(MouseEvent var1) {
      if (!this.simulatingClick && var1.button == 0 && var1.raW) {
         this.attackPressTick = this.TlO;
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onClickMouse(ClickMouseEvent var1) {
      if (!this.isBlatantMode() && !this.simulatingClick) {
         if (this.rtF != this.TlO && !this.isBlatantClickScheduled()) {
            long var2 = System.nanoTime();
            if (var2 >= this.cdNa) {
               EntityPlayer var4 = this.findNearbyTarget();
               if (this.canStartDisplacing(var4)) {
                  this.PTYlB = var4.getEntityId();
                  this.tI5 = var2;
                  this.blatantClickQueued = false;
                  this.zmpQ = false;
                  var1.setCanceled(true);
               }
            }
         } else {
            var1.setCanceled(true);
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onMouseOver(MouseOverEvent var1) {
      if (!this.isBlatantMode() && this.zmpQ) {
         this.zmpQ = false;
         this.tI5 = -1L;
         this.PTYlB = -1;
         this.blatantClickQueued = false;
         this.rtF = this.TlO;
         if (ClientUtils.isInWorld() && mc.currentScreen == null) {
            this.simulatingClick = true;

            try {
               ((IAccessorMinecraft)mc).callClickMouse();
            } finally {
               this.simulatingClick = false;
            }
         }
      }
   }

   private boolean isBlatantClickScheduled() {
      return this.tI5 >= 0L;
   }

   private void clearPendingClick() {
      this.tI5 = -1L;
      this.PTYlB = -1;
      this.blatantClickQueued = false;
      this.zmpQ = false;
      this.simulatingClick = false;
   }

   private EntityPlayer findNearbyTarget() {
      return TargetFinder.findNearestTarget(9.0);
   }

   private EntityPlayer resolvePendingTarget() {
      if (this.isBlatantClickScheduled() && mc.theWorld != null) {
         Entity var1 = mc.theWorld.getEntityByID(this.PTYlB);
         return var1 instanceof EntityPlayer && TargetFinder.isValidTarget((EntityPlayer)var1) ? (EntityPlayer)var1 : null;
      } else {
         return null;
      }
   }

   private boolean areItemConditionsMet() {
      if (!this.hasKnockback.isToggled() && !this.itemWhitelist.isToggled()) {
         return true;
      } else {
         boolean var1 = !this.hasKnockback.isToggled() || this.CnTv();
         boolean var2 = !this.itemWhitelist.isToggled() || this.whitelistedItems.EMuhC6(mc.thePlayer.getHeldItem());
         return var1 || var2;
      }
   }

   private boolean canStartDisplacing(EntityPlayer var1) {
      if (!ClientUtils.isInWorld() || var1 == null || mc.currentScreen != null) {
         return false;
      } else if (!this.areItemConditionsMet() || this.shouldStopWhileBreaking()) {
         return false;
      } else if (this.isVoidDirection() && this.YJSUvq(var1)) {
         return false;
      } else {
         Fisherman var2 = Jade.getModuleManager().getModule(Fisherman.class);
         return (var2 == null || !var2.isEnabled()) && (this.CnTv() || this.isMovementKeyHeld());
      }
   }

   private boolean YJSUvq(EntityPlayer var1) {
      double var2 = this.isHoldingKnockbackStick() ? 4.0 : 2.0;
      return this.countVoidSamplesAlongAngle(var1, this.getPerpendicularYawRadians(), var2) > 0;
   }

   private boolean isHoldingKnockbackStick() {
      return mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() == Items.stick && this.CnTv();
   }

   private double getPerpendicularYawRadians() {
      return Math.toRadians(RotationUtils.lastSentRotation[0]) + (Math.PI / 2);
   }

   private int VrvBn(EntityPlayer var1, double var2) {
      return this.countVoidSamplesAlongAngle(var1, var2, 12.0);
   }

   private int countVoidSamplesAlongAngle(EntityPlayer var1, double var2, double var4) {
      double var6 = var1.posX;
      double var8 = var1.posZ;
      double var10 = var1.posY + var1.getEyeHeight();
      double var12 = var1.posY + 1.25;
      double var14 = Math.cos(var2);
      double var16 = Math.sin(var2);
      double var18 = Math.min(12.0, var4);
      double var20 = var18;
      MovingObjectPosition var22 = mc.theWorld.rayTraceBlocks(new Vec3(var6, var10, var8), new Vec3(var6 + var14 * var18, var10, var8 + var16 * var18));
      if (var22 != null) {
         double var23 = var22.hitVec.xCoord - var6;
         double var25 = var22.hitVec.zCoord - var8;
         var20 = Math.sqrt(var23 * var23 + var25 * var25);
      }

      MovingObjectPosition var32 = mc.theWorld.rayTraceBlocks(new Vec3(var6, var12, var8), new Vec3(var6 + var14 * var18, var12, var8 + var16 * var18));
      if (var32 != null) {
         double var24 = var32.hitVec.xCoord - var6;
         double var26 = var32.hitVec.zCoord - var8;
         var20 = Math.min(var20, Math.sqrt(var24 * var24 + var26 * var26));
      }

      int var33 = 0;

      for (int var34 = 1; var34 <= 24; var34++) {
         double var35 = var34 * 0.5;
         if (var35 > var20 || var35 > var4) {
            break;
         }

         double var28 = var6 + var14 * var35;
         double var30 = var8 + var16 * var35;
         if (mc.theWorld.rayTraceBlocks(new Vec3(var28, var10, var30), new Vec3(var28, var10 - 20.0, var30)) == null) {
            var33++;
         }
      }

      return var33;
   }

   private boolean isAttackTapConfirmed() {
      return this.onlyOnTap.isToggled() ? this.attackPressTick >= this.TlO - 1 : this.isAttackKeyHeld() || this.attackPressTick >= this.TlO - 1;
   }

   private boolean isAttackKeyHeld() {
      int var1 = mc.gameSettings.keyBindAttack.getKeyCode();
      return var1 < 0 ? Mouse.isButtonDown(var1 + 100) : mc.gameSettings.keyBindAttack.isKeyDown();
   }

   private boolean CnTv() {
      return mc.thePlayer.getHeldItem() != null && EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, mc.thePlayer.getHeldItem()) > 0;
   }

   private boolean isBreakingBlock() {
      return ClientUtils.isMiningBlock();
   }

   private boolean shouldStopWhileBreaking() {
      return this.stopWhenBreaking.isToggled() && this.isBreakingBlock();
   }

   private boolean kmzP(EntityPlayer var1) {
      double var2 = var1.posY + var1.getEyeHeight();
      double var4 = var1.posX;
      double var6 = var1.posZ;
      if (mc.theWorld.rayTraceBlocks(new Vec3(var4, var2, var6), new Vec3(var4, var2 - 20.0, var6)) != null) {
         return false;
      } else {
         boolean var8 = true;
         int var9 = 0;
         int var10 = -1;

         for (int var11 = 0; var11 < 360; var11 += 10) {
            double var12 = Math.toRadians(var11);
            double var14 = Math.cos(var12);
            double var16 = Math.sin(var12);
            int var18 = 0;

            for (int var19 = 1; var19 <= 4; var19++) {
               double var20 = var19 * 0.5;
               double var22 = var4 + var14 * var20;
               double var24 = var6 + var16 * var20;
               if (mc.theWorld.rayTraceBlocks(new Vec3(var22, var2, var24), new Vec3(var22, var2 - 20.0, var24)) == null) {
                  var18++;
               } else {
                  var8 = false;
               }
            }

            if (var18 > var9) {
               var9 = var18;
               var10 = var11;
            }
         }

         if (var9 > 0 && var10 >= 0) {
            double var26 = Math.toRadians(var10);
            double var13 = Math.cos(var26);
            double var15 = Math.sin(var26);
            if (mc.theWorld.rayTraceBlocks(new Vec3(var4, var2, var6), new Vec3(var4 + var13 * 2.0, var2, var6 + var15 * 2.0)) == null) {
               this.JMaS = (float)(Math.atan2(-var13, var15) * 180.0 / Math.PI);
               this.voidDirectionAvailable = true;
               double var17 = var1.posX - mc.thePlayer.posX;
               double var27 = var1.posZ - mc.thePlayer.posZ;
               double var21 = var17 * var15 - var27 * var13;
               this.displaceLeft = var21 > 0.0;
            }
         }

         return var8;
      }
   }

   private boolean findBestVoidAngle(EntityPlayer var1) {
      double var2 = var1.posX;
      double var4 = var1.posZ;
      double var6 = var1.posY + var1.getEyeHeight();
      double var8 = var1.posY + 1.25;
      int var10 = 0;
      int var11 = -1;
      double var12 = Double.MAX_VALUE;
      int var14 = -1;
      boolean var15 = false;
      double[] var16 = new double[36];

      for (int var17 = 0; var17 < 360; var17 += 10) {
         double var18 = Math.toRadians(var17);
         double var20 = Math.cos(var18);
         double var22 = Math.sin(var18);
         double var24 = 12.0;
         MovingObjectPosition var26 = mc.theWorld.rayTraceBlocks(new Vec3(var2, var6, var4), new Vec3(var2 + var20 * var24, var6, var4 + var22 * var24));
         double var27 = var24;
         if (var26 != null) {
            double var29 = var26.hitVec.xCoord - var2;
            double var31 = var26.hitVec.zCoord - var4;
            var27 = Math.sqrt(var29 * var29 + var31 * var31);
         }

         MovingObjectPosition var54 = mc.theWorld.rayTraceBlocks(new Vec3(var2, var8, var4), new Vec3(var2 + var20 * var24, var8, var4 + var22 * var24));
         if (var54 != null) {
            double var30 = var54.hitVec.xCoord - var2;
            double var32 = var54.hitVec.zCoord - var4;
            double var34 = Math.sqrt(var30 * var30 + var32 * var32);
            if (var34 < var27) {
               var27 = var34;
            }
         }

         var16[var17 / 10] = var27;
         int var56 = 0;
         double var57 = -1.0;

         for (int var33 = 1; var33 <= 24; var33++) {
            double var60 = var33 * 0.5;
            double var36 = var2 + var20 * var60;
            double var38 = var4 + var22 * var60;
            if (mc.theWorld.rayTraceBlocks(new Vec3(var36, var6, var38), new Vec3(var36, var6 - 20.0, var38)) == null) {
               if (var60 <= var27) {
                  var56++;
               }

               if (var57 < 0.0) {
                  var57 = var60;
               }
            }
         }

         if (var57 > 0.0) {
            var15 = true;
            if (var57 < var12) {
               var12 = var57;
               var14 = var17;
            }

            if (var56 > var10) {
               var10 = var56;
               var11 = var17;
            }
         }
      }

      if (!var15) {
         return false;
      } else {
         int var48;
         if (var10 > 0) {
            var48 = var11;
         } else {
            int var49 = 0;
            int var19 = -1;

            for (int var51 = 0; var51 < 360; var51 += 10) {
               double var21 = var16[var51 / 10];
               if (!(var21 < 1.0)) {
                  double var23 = Math.toRadians(var51);
                  double var25 = Math.cos(var23);
                  double var53 = Math.sin(var23);
                  double var55 = Math.min(var21, 4.0);
                  double var58 = var2 + var25 * var55;
                  double var59 = var4 + var53 * var55;
                  int var35 = 0;

                  for (int var61 = 0; var61 < 360; var61 += 45) {
                     double var37 = Math.toRadians(var61);
                     double var39 = Math.cos(var37);
                     double var41 = Math.sin(var37);

                     for (int var43 = 1; var43 <= 4; var43++) {
                        double var44 = var58 + var39 * var43;
                        double var46 = var59 + var41 * var43;
                        if (mc.theWorld.rayTraceBlocks(new Vec3(var44, var6, var46), new Vec3(var44, var6 - 20.0, var46)) == null) {
                           var35++;
                        }
                     }
                  }

                  if (var35 > var49) {
                     var49 = var35;
                     var19 = var51;
                  }
               }
            }

            if (var49 > 0) {
               var48 = var19;
            } else {
               var48 = var14;
            }
         }

         double var50 = Math.toRadians(var48);
         if (var10 > 0) {
            double var52 = this.findBestPerpendicularAngle(var1);
            if (!Double.isNaN(var52)) {
               var50 = var52;
            }
         }

         this.oPmfA(var1, var50);
         return true;
      }
   }

   private double findBestPerpendicularAngle(EntityPlayer var1) {
      double var2 = this.getPerpendicularYawRadians();

      for (int var4 = 0; var4 <= 5; var4++) {
         double var5 = Double.NaN;
         int var7 = 0;
         int[] var8 = var4 == 0 ? new int[]{0} : new int[]{-var4, var4};

         for (int var12 : var8) {
            double var13 = Math.toRadians(90.0 + var12);
            double var15 = var2 - var13;
            double var17 = var2 + var13;
            int var19 = this.VrvBn(var1, var15);
            int var20 = this.VrvBn(var1, var17);
            if (var19 > 0 && var20 > 0) {
               int var21 = Math.max(var19, var20);
               if (var21 > var7) {
                  var7 = var21;
                  var5 = var19 >= var20 ? var15 : var17;
               }
            }
         }

         if (!Double.isNaN(var5)) {
            return var5;
         }
      }

      return Double.NaN;
   }

   private void oPmfA(EntityPlayer var1, double var2) {
      double var4 = Math.cos(var2);
      double var6 = Math.sin(var2);
      this.JMaS = (float)(Math.atan2(-var4, var6) * 180.0 / Math.PI);
      this.voidDirectionAvailable = true;
      double var8 = var1.posX - mc.thePlayer.posX;
      double var10 = var1.posZ - mc.thePlayer.posZ;
      double var12 = var8 * var6 - var10 * var4;
      this.displaceLeft = var12 > 0.0;
   }

   private boolean shouldAbortVoidDisplacement(EntityPlayer var1, int var2) {
      if (this.canReuseVoidScan(var1, var2)) {
         this.displaceLeft = this.cachedDisplaceLeft;
         this.voidDirectionAvailable = this.cachedDirectionAvailable;
         this.JMaS = this.cachedVoidAngle;
         if (!this.cachedScanSucceeded && !this.HZDOJ) {
            this.displaceLeft = true;
         }

         return this.HZDOJ;
      } else {
         boolean var3 = this.kmzP(var1);
         boolean var4 = false;
         if (!var3) {
            var4 = this.findBestVoidAngle(var1);
            if (!var4) {
               this.displaceLeft = true;
            }
         }

         this.GbE = var1.getEntityId();
         this.cachedTick = var2;
         this.kKunU = var1.posX;
         this.acr = var1.posY;
         this.YTDz = var1.posZ;
         this.HZDOJ = var3;
         this.cachedScanSucceeded = var4;
         this.cachedDisplaceLeft = this.displaceLeft;
         this.cachedDirectionAvailable = this.voidDirectionAvailable;
         this.cachedVoidAngle = this.JMaS;
         return var3;
      }
   }

   private boolean canReuseVoidScan(EntityPlayer var1, int var2) {
      if (var1 == null || var1.getEntityId() != this.GbE) {
         return false;
      } else if (var2 - this.cachedTick > 4) {
         return false;
      } else {
         double var3 = var1.posX - this.kKunU;
         double var5 = var1.posY - this.acr;
         double var7 = var1.posZ - this.YTDz;
         return var3 * var3 + var5 * var5 + var7 * var7 <= 0.12249999999999998;
      }
   }

   private void QWCJIKj() {
      this.GbE = -1;
      this.cachedTick = -4;
   }

   private boolean isBlatantMode() {
      return this.mode.getInput() == 1.0;
   }

   private boolean isVoidDirection() {
      return (int)this.direction.getInput() == 2;
   }

   private void XKIVa() {
      if (mc.theWorld == null) {
         this.blatantClickTicks.clear();
      } else {
         Iterator var1 = this.blatantClickTicks.entrySet().iterator();

         while (var1.hasNext()) {
            Entry var2 = (Entry)var1.next();
            Entity var3 = mc.theWorld.getEntityByID((Integer)var2.getKey());
            if (!(var3 instanceof EntityPlayer) || var3.isDead || ((EntityPlayer)var3).deathTime != 0) {
               var1.remove();
            }
         }
      }
   }

   private boolean EhhY(EntityPlayer var1, int var2) {
      if (var1 == null) {
         return true;
      } else {
         int var3 = var1.getEntityId();
         Integer var4 = this.blatantClickTicks.get(var3);
         if (var4 != null && var2 - var4 < 10) {
            int var5 = (int)Math.ceil(this.blatantDelay.getInput() / 50.0);
            if (var5 <= 0) {
               return true;
            } else {
               int var6 = var2 - var4;
               return var6 >= var5;
            }
         } else {
            this.blatantClickTicks.put(var3, var2);
            return true;
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onMoveStateUpdate(MoveStateUpdateEvent var1) {
      if (!this.displacing) {
         this.strafeQueued = false;
      } else if (this.strafeQueued && !this.clickingThisTick) {
         this.strafeQueued = false;
         if (this.displaceLeft) {
            mc.thePlayer.movementInput.moveStrafe = -1.0F;
         } else {
            mc.thePlayer.movementInput.moveStrafe = 1.0F;
         }
      } else if (this.clickingThisTick && !this.holdsKnockbackWeapon) {
         if (this.isMovementKeyHeld()) {
            mc.thePlayer.movementInput.moveForward = 1.0F;
            this.strafeQueued = true;
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onRotation(RotationEvent var1) {
      if (Jade.getModuleManager().getModule(Fisherman.class) != null && Jade.getModuleManager().getModule(Fisherman.class).isEnabled()) {
         this.displacing = false;
         this.clickingThisTick = false;
         this.strafeQueued = false;
         this.em1 = false;
         this.clearPendingClick();
      } else if (!ClientUtils.isInWorld()) {
         this.displacing = false;
         this.clickingThisTick = false;
         this.strafeQueued = false;
         this.em1 = false;
         this.clearPendingClick();
      } else {
         if (this.previousBlatantMode != this.isBlatantMode()) {
            this.onEnable();
         }

         this.TlO++;
         int var2 = this.TlO;
         if (this.isBlatantClickScheduled() && this.blatantClickQueued) {
            this.displacing = false;
            this.clickingThisTick = false;
            this.strafeQueued = false;
            this.em1 = false;
            this.zmpQ = true;
         } else if (this.isBlatantClickScheduled() && System.nanoTime() < this.tI5) {
            this.displacing = false;
            this.clickingThisTick = false;
            this.strafeQueued = false;
            this.em1 = false;
         } else if (!this.isBlatantClickScheduled() && !this.areItemConditionsMet()) {
            this.displacing = false;
            this.clickingThisTick = false;
            this.strafeQueued = false;
            this.em1 = false;
            this.clearPendingClick();
         } else if (!this.isBlatantClickScheduled() && this.shouldStopWhileBreaking()) {
            this.displacing = false;
            this.clickingThisTick = false;
            this.strafeQueued = false;
            this.em1 = false;
            this.clearPendingClick();
         } else {
            EntityPlayer var3 = this.isBlatantMode() ? (mc.currentScreen == null && this.isAttackTapConfirmed() ? this.findNearbyTarget() : null) : this.resolvePendingTarget();
            boolean var4 = this.CnTv();
            this.displacing = this.isBlatantClickScheduled() ? var3 != null : var3 != null && (var4 || this.isMovementKeyHeld());
            if (!this.displacing) {
               this.clickingThisTick = false;
               this.strafeQueued = false;
               this.em1 = false;
               if (this.isBlatantClickScheduled()) {
                  this.clearPendingClick();
               }
            } else {
               this.voidDirectionAvailable = false;
               if (this.isVoidDirection()) {
                  if (this.shouldAbortVoidDisplacement(var3, var2)) {
                     this.displacing = false;
                     this.clickingThisTick = false;
                     this.strafeQueued = false;
                     this.em1 = false;
                     this.clearPendingClick();
                     return;
                  }
               } else {
                  this.displaceLeft = (int)this.direction.getInput() == 0;
               }

               this.holdsKnockbackWeapon = var4;
               if (this.isBlatantMode()) {
                  this.XKIVa();
                  this.clickingThisTick = !this.clickingThisTick;
                  if (this.clickingThisTick && !this.EhhY(var3, var2)) {
                     this.clickingThisTick = false;
                     this.strafeQueued = false;
                     this.em1 = false;
                     return;
                  }

                  if (!this.clickingThisTick && this.em1) {
                     int var5 = mc.gameSettings.keyBindAttack.getKeyCode();
                     if (var5 != 0) {
                        KeyBinding.onTick(var5);
                     }
                  }
               } else {
                  this.clickingThisTick = true;
               }

               this.em1 = this.clickingThisTick;
               if (this.clickingThisTick) {
                  if (this.isBlatantClickScheduled()) {
                     this.blatantClickQueued = true;
                     this.cdNa = System.nanoTime() + (long)(this.delay.getInput() * 1000000.0);
                  }

                  float var7;
                  if (this.isVoidDirection() && this.voidDirectionAvailable) {
                     var7 = this.JMaS + (this.isBlatantMode() && !this.holdsKnockbackWeapon ? 180.0F : 0.0F);
                  } else {
                     float var6 = RotationUtils.lastSentRotation[0];
                     if (this.displaceLeft) {
                        var6 -= 90.0F;
                     } else {
                        var6 += 90.0F;
                     }

                     var7 = var6;
                  }

                  var1.PrQmu(this.isBlatantMode() ? var7 : this.applyAngleJitter(var7), 20);
               }
            }
         }
      }
   }

   private float applyAngleJitter(float var1) {
      return var1 + (float)ClientUtils.randomDouble(-10.0, 10.0);
   }

   public boolean isDisplacing() {
      return this.isEnabled() && this.displacing;
   }

   public boolean isSimulatingClick() {
      return this.isEnabled() && this.simulatingClick;
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Conditions",
            "Action",
            new String[]{"stop when breaking", "only on tap", "has knockback", "item whitelist"},
            new String[]{"Not whilst breaking", "Only on tap", "Holding knockback item", "Whitelisted item"}
         )
      );
   }
}
