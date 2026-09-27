// Jade recovery: module: Block In (player); original class: jade.deps.eLz.WXYLOmhdFK
package jade.client.module.player;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.Subscribe;
import jade.client.event.MouseEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RotationEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.blockin.SelfPlacementUtils;
import jade.client.module.player.blockin.KeybindSync;
import jade.client.module.player.blockin.PlacementTarget;
import jade.client.module.player.blockin.PlacementHit;
import jade.client.module.player.blockin.OneShotValue;
import jade.client.module.player.blockin.BlockSlotSelector;
import jade.client.module.player.blockin.NearbyPlacementFinder;
import jade.client.module.player.blockin.SurroundSpaceUtils;
import jade.client.module.player.blockin.SlotSelectionSession;
import jade.client.module.player.blockin.SurroundPlacementFinder;
import jade.client.module.player.blockin.SmoothedProgress;
import jade.client.module.player.blockin.PlacementRotation;
import jade.client.module.shared.ProgressBarSource;
import jade.client.setting.KeySetting;
import jade.client.setting.SliderSetting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo
public class BlockIn extends Module implements ProgressBarSource {
   private static final double REACH_DISTANCE = 4.5;
   private final SliderSetting speed;
   private final SliderSetting randomization;
   private final KeySetting selectKeybind;
   private final SlotSelectionSession rl2 = new SlotSelectionSession();
   private final OneShotValue<PlacementHit> queuedPlacement = new OneShotValue<>();
   private BlockPos blockPos;
   private EnumFacing enumFacing;
   private float GzL;
   private float targetPitch;
   private final SmoothedProgress QcQ9 = new SmoothedProgress();
   private boolean targetIsAdjacent;

   public BlockIn() {
      super("Block In", Category.player);
      this.registerSetting(this.speed = new SliderSetting("Speed", 10.0, 1.0, 30.0, 1.0));
      this.registerSetting(
         this.randomization = new SliderSetting(
            "Randomization", "%", 10.0, 0.0, 100.0, 1.0
         )
      );
      this.registerSetting(this.selectKeybind = new KeySetting("Select Keybind", 0));
      this.initialized = true;
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
      this.cancelSelection();
      this.queuedPlacement.clear();
      this.QcQ9.reset();
   }

   @Subscribe
   public void onRotation(RotationEvent var1) {
      if (ClientUtils.isInWorld()) {
         if (Jade.getModuleManager().getModule(BedNuker.class) == null || !Jade.getModuleManager().getModule(BedNuker.class).shouldOverridePointedObject()) {
            if (Jade.getModuleManager().getModule(BridgeNuker.class) == null || !Jade.getModuleManager().getModule(BridgeNuker.class).shouldOverridePointedObject()) {
               this.updateKeybindSelection();
               if (mc.currentScreen != null) {
                  this.cancelSelection();
               }

               if (this.rl2.isActive() && this.blockPos != null) {
                  PlacementRotation var2 = PlacementRotation.RYgmVv9(
                     var1.MGzP2, var1.pitch, this.GzL, this.targetPitch, (int)this.speed.getInput(), (float)this.randomization.getInput(), 4.5, this.blockPos, this.enumFacing
                  );
                  if (var2.getPlacementHit() != null) {
                     this.queuedPlacement.gzgY0(var2.getPlacementHit());
                  }

                  var1.setRotation(var2.getYaw(), var2.getPitch(), 45);
               }
            }
         }
      }
   }

   private void updateKeybindSelection() {
      this.HZUWZ();
      if (this.selectKeybind.isHeldDown() && mc.currentScreen == null) {
         int var1 = this.fTb5(true);
         int var2 = this.fTb5(false);
         if (var1 == -1 && var2 == -1) {
            this.cancelSelection();
         } else {
            this.rl2.setSelectedSlot(var1 != -1 ? var1 : var2);
            if (!this.resolvePlacementTarget()) {
               this.cancelSelection();
            } else {
               if (this.targetIsAdjacent) {
                  this.rl2.setSelectedSlot(var1 != -1 ? var1 : var2);
               } else {
                  this.rl2.setSelectedSlot(var2 != -1 ? var2 : var1);
               }

               if (!this.rl2.isActive()) {
                  this.rZiw();
               }

               if (mc.gameSettings.keyBindAttack.isKeyDown() || mc.gameSettings.keyBindUseItem.isKeyDown()) {
                  this.HZUWZ();
               }

               KeybindSync.releaseMouseKeybinds(mc);
               this.syncSelectedHotbarSlot();
            }
         }
      } else {
         this.cancelSelection();
         this.QcQ9.cilm();
      }
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      if (ClientUtils.isInWorld()) {
         this.queuedPlacement.consumeIfPresent(BlockIn::placeBlock);
         this.QcQ9.resetTarget();
         if (this.selectKeybind.isHeldDown() && mc.currentScreen == null) {
            this.QcQ9.updateTarget(SurroundSpaceUtils.FLOOA(mc.thePlayer), System.currentTimeMillis());
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onMouse(MouseEvent var1) {
      if (this.rl2.isActive() && var1.button > -1) {
         var1.setCanceled(true);
      }
   }

   public boolean isSlotSelectionActive() {
      return this.rl2.isActive();
   }

   @Override
   public boolean isProgressActive() {
      return this.isEnabled() && (this.rl2.isActive() || this.QcQ9.hasTarget());
   }

   @Override
   public float getProgressFraction() {
      return this.QcQ9.getSmoothedValue(System.currentTimeMillis());
   }

   @Override
   public String getProgressLabel() {
      return "Block In";
   }

   private void rZiw() {
      this.rl2.OvuplMq(BlockIn::getSelectedHotbarSlot);
   }

   private void cancelSelection() {
      if (this.rl2.pKf97(BlockIn::readCurrentHotbarSlot, BlockIn::restoreHotbarSlot)) {
         if (mc.currentScreen == null) {
            KeybindSync.restoreMouseKeybinds(mc);
         }
      }
   }

   private void HZUWZ() {
      this.blockPos = null;
      this.enumFacing = null;
   }

   private void syncSelectedHotbarSlot() {
      this.rl2.dkgyua(BlockIn::getCurrentHotbarSlot, BlockIn::applyHotbarSlot);
   }

   private int fTb5(boolean var1) {
      return BlockSlotSelector.findPlaceableSlot(mc.thePlayer, var1);
   }

   private boolean resolvePlacementTarget() {
      if (this.rl2.getSelectedSlot() >= 0 && this.rl2.getSelectedSlot() <= 8) {
         ItemStack var1 = mc.thePlayer.inventory.mainInventory[this.rl2.getSelectedSlot()];
         PlacementTarget var2 = NearbyPlacementFinder.findNearbyPlacement(mc.thePlayer, var1, 4.5);
         if (var2 == null) {
            var2 = SurroundPlacementFinder.findSurroundPlacement(mc.thePlayer, var1, ClientUtils.LHYz(100.0), 4.5);
         }

         if (var2 == null) {
            return false;
         } else {
            this.targetIsAdjacent = this.isBlockDirectlyAdjacent(var2.getPlacementPos());
            this.blockPos = var2.getClickedPos();
            this.enumFacing = var2.GjveS();
            this.GzL = var2.getYaw();
            this.targetPitch = var2.getPitch();
            return true;
         }
      } else {
         return false;
      }
   }

   private boolean isBlockDirectlyAdjacent(BlockPos var1) {
      return SelfPlacementUtils.isSelfSurroundPosition(mc.thePlayer, var1);
   }

   private static void applyHotbarSlot(int var0) {
      mc.thePlayer.inventory.currentItem = var0;
   }

   private static int getCurrentHotbarSlot() {
      return mc.thePlayer.inventory.currentItem;
   }

   private static void restoreHotbarSlot(int var0) {
      mc.thePlayer.inventory.currentItem = var0;
   }

   private static int readCurrentHotbarSlot() {
      return mc.thePlayer.inventory.currentItem;
   }

   private static int getSelectedHotbarSlot() {
      return mc.thePlayer.inventory.currentItem;
   }

   private static void placeBlock(PlacementHit var0) {
      if (var0.blockPos != null
         && var0.enumFacing != null
         && var0.vec3 != null
         && mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), var0.blockPos, var0.enumFacing, var0.vec3)) {
         mc.thePlayer.swingItem();
      }
   }
}
