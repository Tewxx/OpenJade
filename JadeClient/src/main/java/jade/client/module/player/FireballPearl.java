// Jade recovery: module: Fireball Pearl (player); original class: jade.deps.eLz.GUb9NIQW
package jade.client.module.player;

import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.event.PreUpdateEvent;
import jade.client.event.RotationEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;

import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;

@ModuleInfo
public class FireballPearl extends Module {
   private final BooleanSetting realRotations;
   private int pearlSlot = -1;
   private int Ndu = -1;
   private int previousHotbarSlot = -1;
   private int sequenceStep = 0;
   private float startYaw = 0.0F;

   public FireballPearl() {
      super("Fireball Pearl", Category.player);
      this.registerSetting(
         this.realRotations = new BooleanSetting(
            "Real Rotations", false
         )
      );
   }

   @Override
   public void onEnable() {
      if (!ClientUtils.isInWorld()) {
         this.disable();
      } else {
         this.pearlSlot = this.findHotbarSlotForItemClass(ItemEnderPearl.class);
         this.Ndu = this.findHotbarSlotForItemClass(ItemFireball.class);
         if (this.pearlSlot == -1) {
            this.disable();
         } else {
            this.previousHotbarSlot = mc.thePlayer.inventory.currentItem;
            this.startYaw = mc.thePlayer.rotationYaw;
            this.sequenceStep = 0;
         }
      }
   }

   @Override
   public void onDisable() {
      if (ClientUtils.isInWorld() && this.previousHotbarSlot != -1 && this.previousHotbarSlot != mc.thePlayer.inventory.currentItem) {
         this.switchToHotbarSlot(this.previousHotbarSlot);
      }

      this.sequenceStep = 0;
      this.previousHotbarSlot = -1;
      this.pearlSlot = -1;
      this.Ndu = -1;
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onRotation(RotationEvent var1) {
      if (ClientUtils.isInWorld() && !this.realRotations.isToggled()) {
         if (this.sequenceStep <= 3) {
            var1.setRotation(this.startYaw, -90.0F, 45);
         }
      }
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      if (ClientUtils.isInWorld() && this.realRotations.isToggled()) {
         if (this.sequenceStep > 0 && this.sequenceStep <= 3) {
            float[] var2 = RotationUtils.NSsr(this.startYaw, -90.0F, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
            mc.thePlayer.rotationYaw = var2[0];
            mc.thePlayer.rotationPitch = var2[1];
         }
      }
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.START) {
         if (ClientUtils.isInWorld()) {
            switch (this.sequenceStep) {
               case 0:
                  this.switchToHotbarSlot(this.pearlSlot);
                  this.sequenceStep = 1;
                  return;
               case 1:
                  this.sendUseItemPacket();
                  this.sequenceStep = 2;
                  return;
               case 2:
                  if (this.Ndu != -1) {
                     this.switchToHotbarSlot(this.Ndu);
                     this.sendUseItemPacket();
                  }

                  this.sequenceStep = 3;
                  return;
               case 3:
                  this.switchToHotbarSlot(this.previousHotbarSlot);
                  this.sequenceStep = 4;
                  return;
               case 4:
                  this.disable();
                  return;
            }
         }
      }
   }

   private void sendUseItemPacket() {
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

   private int findHotbarSlotForItemClass(Class<?> var1) {
      for (int var2 = 0; var2 < 9; var2++) {
         ItemStack var3 = mc.thePlayer.inventory.getStackInSlot(var2);
         if (var3 != null && var1.isInstance(var3.getItem())) {
            return var2;
         }
      }

      return -1;
   }
}
