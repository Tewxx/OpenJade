// Jade recovery: module: Fireball Boost (player); original class: jade.deps.eLz.DEVZ8Q
package jade.client.module.player;

import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.Subscribe;
import jade.client.event.RotationEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.SliderSetting;

import jade.deps.loader107.MurmurFinalizerConstantCipherNine;
import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;

@ModuleInfo
public class FireballBoost extends Module {
   private SliderSetting boostDelay;
   private int fireballSlot = -1;
   private int snowballSlot = -1;
   private int previousHotbarSlot = -1;
   private int mUk = 0;
   private long boostStartTime = MurmurFinalizerConstantCipherNine.decodeLong(-2045335841308193648L, 1379009959);
   private float startPitch = 0.0F;
   private float Ztx = 0.0F;
   private boolean restoringPitch = false;
   private int pitchRestoreTicks = 0;

   public FireballBoost() {
      super("Fireball Boost", Category.player);
      this.registerSetting(
         this.boostDelay = new SliderSetting(
            "Boost Delay", " ms", 550.0, 450.0, 650.0, 10.0
         )
      );
   }

   @Override
   public void onEnable() {
      if (!ClientUtils.isInWorld()) {
         this.disable();
      } else {
         this.fireballSlot = this.xpup(ItemFireball.class);
         this.snowballSlot = this.findSnowballSlot();
         if (this.fireballSlot != -1 && this.snowballSlot != -1) {
            this.previousHotbarSlot = mc.thePlayer.inventory.currentItem;
            this.mUk = 0;
            this.boostStartTime = 0L;
            this.restoringPitch = false;
            this.pitchRestoreTicks = 0;
            this.Ztx = mc.thePlayer.rotationYaw;
         } else {
            this.disable();
         }
      }
   }

   @Override
   public void onDisable() {
      if (ClientUtils.isInWorld() && this.previousHotbarSlot != -1 && this.previousHotbarSlot != mc.thePlayer.inventory.currentItem) {
         this.switchToHotbarSlot(this.previousHotbarSlot);
      }

      this.mUk = 0;
      this.restoringPitch = false;
      this.pitchRestoreTicks = 0;
      this.previousHotbarSlot = -1;
      this.fireballSlot = -1;
      this.snowballSlot = -1;
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onRotation(RotationEvent var1) {
      if (ClientUtils.isInWorld()) {
         if (this.mUk >= 4 && this.mUk < 7) {
            var1.setRotation(this.Ztx, -3.0F, 45);
         } else if (this.restoringPitch && this.mUk == 7) {
            float var2 = Math.min(this.pitchRestoreTicks / 2.0F, 1.0F);
            float var3 = -3.0F + (this.startPitch - -3.0F) * var2;
            var1.setRotation(this.Ztx, var3, 45);
         }
      }
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.START) {
         if (ClientUtils.isInWorld()) {
            this.runBoostSequence();
         }
      }
   }

   private void runBoostSequence() {
      switch (this.mUk) {
         case 0:
            this.switchToHotbarSlot(this.fireballSlot);
            this.mUk = 1;
            return;
         case 1:
            this.yTh6();
            this.boostStartTime = System.currentTimeMillis();
            this.mUk = 2;
            return;
         case 2:
            this.switchToHotbarSlot(this.previousHotbarSlot);
            this.mUk = 3;
            return;
         case 3:
            if (System.currentTimeMillis() - this.boostStartTime >= (long)this.boostDelay.getInput()) {
               this.Ztx = mc.thePlayer.rotationYaw;
               this.startPitch = mc.thePlayer.rotationPitch;
               this.mUk = 4;
            }

            return;
         case 4:
            this.switchToHotbarSlot(this.snowballSlot);
            this.yTh6();
            this.mUk = 5;
            return;
         case 5:
            this.switchToHotbarSlot(this.fireballSlot);
            this.yTh6();
            this.mUk = 6;
            return;
         case 6:
            this.switchToHotbarSlot(this.previousHotbarSlot);
            this.restoringPitch = true;
            this.mUk = 7;
            return;
         case 7:
            this.pitchRestoreTicks++;
            if (this.pitchRestoreTicks >= 3) {
               this.disable();
            }

            return;
      }
   }

   private void yTh6() {
      if (mc.thePlayer != null && mc.thePlayer.sendQueue != null) {
         ItemStack var1 = mc.thePlayer.getHeldItem();
         if (var1 != null) {
            BlockPos var2 = new BlockPos(-1, -1, -1);
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(var2, 255, var1, 0.0F, 0.0F, 0.0F));
            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            if (!mc.thePlayer.capabilities.isCreativeMode && var1.getItem() instanceof ItemFireball && --var1.stackSize <= 0) {
               mc.thePlayer.inventory.mainInventory[mc.thePlayer.inventory.currentItem] = null;
            }
         }
      }
   }

   private void switchToHotbarSlot(int var1) {
      if (var1 >= 0 && var1 <= 8) {
         if (var1 != mc.thePlayer.inventory.currentItem) {
            mc.thePlayer.inventory.currentItem = var1;
            ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
         }
      }
   }

   private int xpup(Class<?> var1) {
      for (int var2 = 0; var2 < 9; var2++) {
         ItemStack var3 = mc.thePlayer.inventory.getStackInSlot(var2);
         if (var3 != null && var1.isInstance(var3.getItem())) {
            return var2;
         }
      }

      return -1;
   }

   private int findSnowballSlot() {
      for (int var1 = 0; var1 < 9; var1++) {
         ItemStack var2 = mc.thePlayer.inventory.getStackInSlot(var1);
         if (var2 != null) {
            String var3 = var2.getUnlocalizedName() == null ? "" : var2.getUnlocalizedName().toLowerCase();
            if (var3.contains("snowball")) {
               return var1;
            }
         }
      }

      return -1;
   }
}
