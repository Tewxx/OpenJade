// Jade recovery: module: MLG (player); original class: jade.deps.eLz.cvwW4NpZR
package jade.client.module.player;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.Subscribe;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.RotationEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.mlg.MlgState;
import jade.client.module.player.mlg.MlgUtils;
import jade.client.setting.BooleanSetting;

import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo
public class MLG extends Module {
   public BooleanSetting pickUp;
   public BooleanSetting swapSlot;
   private final MlgState mlgState = new MlgState();

   public MLG() {
      super("MLG", Category.player);
      this.registerSetting(this.pickUp = new BooleanSetting("Pick Up", true, new String[]{"Pickup water"}));
      this.registerSetting(
         this.swapSlot = new BooleanSetting(
            "Swap Slot",
            true,
            new String[]{"Switch to item"}
         )
      );
   }

   @Override
   public void onDisable() {
      this.restorePreviousSlot();
      this.mlgState.reset();
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (ClientUtils.isInWorld() && !mc.isGamePaused() && !mc.thePlayer.capabilities.isFlying && !mc.thePlayer.capabilities.isCreativeMode) {
         if (this.LvPq()) {
            MovingObjectPosition var2 = ClientUtils.rayTraceBlocksWithRotations(mc.playerController.getBlockReachDistance(), mc.thePlayer.rotationYaw, 90.0F);
            if (var2 != null && var2.typeOfHit == MovingObjectType.BLOCK && var2.sideHit == EnumFacing.UP) {
               long var3 = System.currentTimeMillis();
               if (this.mlgState.isReadyToRefill(var3)) {
                  if (!MlgUtils.EReKjl(mc.thePlayer.getHeldItem(), Items.water_bucket) && this.swapSlot.isToggled()) {
                     this.swapToWaterBucket();
                  }

                  if (MlgUtils.EReKjl(mc.thePlayer.getHeldItem(), Items.water_bucket)) {
                     this.mlgState.onWaterPlaced(var3, this.pickUp.isToggled());
                     this.sendItemUsePacket();
                     if (!this.pickUp.isToggled()) {
                        this.restorePreviousSlot();
                     }

                     if (Jade.profilingEnabled) {
                        ClientUtils.sendModuleMessage(
                           this,
                           "&7Placed with motionY &d"
                              + ClientUtils.WXYd(mc.thePlayer.motionY, 2)
                              + " &7and fall distance &d"
                              + ClientUtils.WXYd(mc.thePlayer.fallDistance, 2)
                        );
                     }
                  }
               }
            }
         }
      }
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      if (!mc.isGamePaused()) {
         if (this.mlgState.shouldPickUpWater(System.currentTimeMillis()) && MlgUtils.EReKjl(mc.thePlayer.getHeldItem(), Items.bucket)) {
            this.mlgState.TcFaeva();
            this.sendItemUsePacket();
            this.restorePreviousSlot();
         }
      }
   }

   @Subscribe
   public void onRotation(RotationEvent var1) {
      if (Jade.getModuleManager().getModule(BedNuker.class) == null || !Jade.getModuleManager().getModule(BedNuker.class).shouldOverridePointedObject()) {
         if (Jade.getModuleManager().getModule(BridgeNuker.class) == null || !Jade.getModuleManager().getModule(BridgeNuker.class).shouldOverridePointedObject()) {
            if ((this.LvPq() || this.mlgState.isRecentlyPlaced(System.currentTimeMillis())) && this.findWaterBucketSlot() != -1) {
               var1.setRotation(mc.thePlayer.rotationYaw, 90.0F, 45);
            }
         }
      }
   }

   private void swapToWaterBucket() {
      int var1 = this.findWaterBucketSlot();
      if (var1 != -1) {
         this.mlgState.NOMpoo(mc.thePlayer.inventory.currentItem);
         ClientUtils.setHeldSlot(var1, true);
      }
   }

   private void restorePreviousSlot() {
      int var1 = this.mlgState.consumeSavedSlot();
      if (var1 != -1 && mc.thePlayer != null) {
         ClientUtils.setHeldSlot(var1, true);
      }
   }

   private int findWaterBucketSlot() {
      return MlgUtils.KMqC(mc.thePlayer.inventory, Items.water_bucket);
   }

   private void sendItemUsePacket() {
      mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
   }

   private boolean LvPq() {
      return MlgUtils.isFallingDangerously(mc.thePlayer);
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(buildSettingAlias("Action", "Range", new String[]{"pick up", "swap slot"}, new String[]{"Pick Up", "Swap Slot"}));
   }
}
