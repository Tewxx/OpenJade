// Jade recovery: module: Freecam (player); original class: jade.deps.eLz.kzVasTc7N
package jade.client.module.player;

import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.Subscribe;
import jade.client.event.ChangeCurrentItemEvent;
import jade.client.event.LoadWorldEvent;
import jade.client.event.MouseEvent;
import jade.client.event.MoveInputEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.SetCurrentItemEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.other.AntiBot;
import jade.client.setting.BooleanSetting;

import jade.mixin.impl.accessor.IAccessorGuiIngame;
import jade.mixin.impl.accessor.IAccessorS30PacketWindowItems;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

@ModuleInfo
public class Freecam extends Module {
   private static final double[] SPEED_MULTIPLIERS;
   private static final double BASE_MOVE_SPEED = 0.215;
   private static final double EQHgN9 = 180.0;
   private static final long CLICK_COOLDOWN_MS = 250L;
   public static EntityOtherPlayerMP cameraEntity;
   private BooleanSetting disableOnDamage;
   private final ItemStack[] savedMainInventory = new ItemStack[9];
   private final List<EntityPlayer> entityPlayers = new ArrayList<>();
   private boolean inventorySpoofed;
   private boolean inventoryRefreshPending;
   private int plvR;
   private int speedIndex = 3;
   private int selectedPlayerIndex = -1;
   private String selectedPlayerName;
   private ItemStack speedItemStack;
   private ItemStack teleportItemStack;
   private ItemStack UdA;
   private long ygd;

   public Freecam() {
      super("Freecam", Category.player);
      this.registerSetting(
         this.disableOnDamage = new BooleanSetting(
            "Disable on damage", true
         )
      );
   }

   @Override
   public String getInfo() {
      return this.JqLix9() + "x";
   }

   @Override
   public void onEnable() {
      if (!ClientUtils.isInWorld()) {
         this.disable();
      } else {
         this.backupInventory();
         this.freezePlayerInput();
         if (mc.playerController != null) {
            mc.playerController.resetBlockRemoving();
         }

         cameraEntity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
         cameraEntity.copyLocationAndAnglesFrom(mc.thePlayer);
         cameraEntity.prevPosX = cameraEntity.lastTickPosX = cameraEntity.posX;
         cameraEntity.prevPosY = cameraEntity.lastTickPosY = cameraEntity.posY;
         cameraEntity.prevPosZ = cameraEntity.lastTickPosZ = cameraEntity.posZ;
         cameraEntity.prevRotationYaw = cameraEntity.rotationYaw;
         cameraEntity.prevRotationPitch = cameraEntity.rotationPitch;
         cameraEntity.setInvisible(true);
         mc.setRenderViewEntity(cameraEntity);
         this.QmdAc(true);
         this.selectedPlayerIndex = -1;
         this.selectedPlayerName = null;
         this.setSelectedPlayerName(null);
      }
   }

   @Override
   public void onDisable() {
      if (mc.getRenderViewEntity() == cameraEntity && mc.thePlayer != null) {
         mc.setRenderViewEntity(mc.thePlayer);
      }

      cameraEntity = null;
      this.inventoryRefreshPending = false;
      this.restoreInventory();
      this.entityPlayers.clear();
   }

   @Override
   public void onUpdate() {
      if (ClientUtils.isInWorld() && cameraEntity != null) {
         if (this.inventoryRefreshPending) {
            this.QmdAc(false);
            this.inventoryRefreshPending = false;
         }

         this.ensureFakeHotbar();
         cameraEntity.inventory.currentItem = mc.thePlayer.inventory.currentItem;
         if (mc.getRenderViewEntity() != cameraEntity) {
            mc.setRenderViewEntity(cameraEntity);
         }

         this.freezePlayerInput();
         if (mc.playerController != null) {
            mc.playerController.resetBlockRemoving();
         }

         if (this.disableOnDamage.isToggled() && mc.thePlayer.hurtTime > 0) {
            this.disable();
         } else {
            this.updateCameraMovement();
         }
      } else {
         this.disable();
      }
   }

   @Subscribe
   public void onMoveInput(MoveInputEvent var1) {
      var1.setMoveForward(0.0F);
      var1.setMoveStrafe(0.0F);
      var1.setJumping(false);
      var1.setSneaking(false);
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onChangeCurrentItem(ChangeCurrentItemEvent var1) {
      if (ClientUtils.isInWorld() && this.inventorySpoofed) {
         int var2 = Integer.compare(var1.scrollDirection, 0);
         if (var2 != 0) {
            int var3 = Math.floorMod(mc.thePlayer.inventory.currentItem - var2, 9);
            mc.thePlayer.inventory.currentItem = var3;
            if (cameraEntity != null && cameraEntity.inventory != null) {
               cameraEntity.inventory.currentItem = var3;
            }
         }

         var1.setCanceled(true);
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onSetCurrentItem(SetCurrentItemEvent var1) {
      if (ClientUtils.isInWorld() && this.inventorySpoofed) {
         int var2 = Math.max(0, Math.min(8, var1.slot));
         mc.thePlayer.inventory.currentItem = var2;
         if (cameraEntity != null && cameraEntity.inventory != null) {
            cameraEntity.inventory.currentItem = var2;
         }

         var1.setCanceled(true);
      }
   }

   @Subscribe
   public void onMouse(MouseEvent var1) {
      if (ClientUtils.isInWorld()) {
         if ((var1.button == 0 || var1.button == 1) && var1.raW) {
            ItemStack var2 = mc.thePlayer.getHeldItem();
            if (var1.button == 0 && this.isPlayerSelectItem(var2)) {
               if (this.isClickReady()) {
                  this.cycleSelectedPlayer(false);
                  this.muCf(this.UdA);
               }
            } else if (var1.button == 1) {
               if (this.isSpeedItem(var2) && this.isClickReady()) {
                  this.cycleFreecamSpeed();
                  this.muCf(this.speedItemStack);
               } else if (this.isTeleportItem(var2) && this.isClickReady()) {
                  this.teleportToLookedAtBlock();
                  this.muCf(this.teleportItemStack);
               } else if (this.isPlayerSelectItem(var2) && this.isClickReady()) {
                  this.cycleSelectedPlayer(true);
                  this.muCf(this.UdA);
               }
            }
         }

         if (var1.button == 0 || var1.button == 1) {
            var1.setCanceled(true);
         }
      }
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (ClientUtils.isInWorld() && this.inventorySpoofed) {
         if (!(var1.ys98() instanceof S30PacketWindowItems)) {
            if (var1.ys98() instanceof S2FPacketSetSlot) {
               S2FPacketSetSlot var6 = (S2FPacketSetSlot)var1.ys98();
               if (var6.func_149175_c() == 0) {
                  int var7 = var6.func_149173_d() - 36;
                  if (var7 >= 0 && var7 < 9) {
                     this.savedMainInventory[var7] = this.copyStack(var6.func_149174_e());
                     this.QmdAc(false);
                     this.inventoryRefreshPending = false;
                     var1.setCanceled(true);
                  }
               }
            }
         } else {
            S30PacketWindowItems var2 = (S30PacketWindowItems)var1.ys98();
            if (((IAccessorS30PacketWindowItems)var2).getWindowId() == 0) {
               ItemStack[] var3 = var2.getItemStacks();
               if (var3 != null) {
                  for (int var4 = 0; var4 < 9; var4++) {
                     int var5 = 36 + var4;
                     if (var5 < var3.length) {
                        this.savedMainInventory[var4] = this.copyStack(var3[var5]);
                     }
                  }

                  this.QmdAc(false);
                  this.applyFakeItemsToWindow(var3);
               }

               this.inventoryRefreshPending = false;
            }
         }
      }
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent var1) {
      this.disable();
   }

   private void backupInventory() {
      ItemStack[] var1 = mc.thePlayer.inventory.mainInventory;

      for (int var2 = 0; var2 < 9; var2++) {
         this.savedMainInventory[var2] = this.copyStack(var1[var2]);
      }

      this.plvR = mc.thePlayer.inventory.currentItem;
      this.inventorySpoofed = true;
   }

   private void freezePlayerInput() {
      if (mc.thePlayer != null) {
         mc.thePlayer.moveForward = 0.0F;
         mc.thePlayer.moveStrafing = 0.0F;
         if (mc.thePlayer.movementInput != null) {
            mc.thePlayer.movementInput.moveForward = 0.0F;
            mc.thePlayer.movementInput.moveStrafe = 0.0F;
            mc.thePlayer.movementInput.jump = false;
            mc.thePlayer.movementInput.sneak = false;
         }
      }
   }

   private void restoreInventory() {
      if (this.inventorySpoofed && mc.thePlayer != null) {
         ItemStack[] var1 = mc.thePlayer.inventory.mainInventory;

         for (int var2 = 0; var2 < 9; var2++) {
            var1[var2] = this.savedMainInventory[var2];
            this.savedMainInventory[var2] = null;
         }

         mc.thePlayer.inventory.currentItem = Math.max(0, Math.min(8, this.plvR));
         mc.thePlayer.inventory.markDirty();
         this.inventorySpoofed = false;
         this.speedItemStack = null;
         this.teleportItemStack = null;
         this.UdA = null;
         this.selectedPlayerName = null;
         this.ygd = 0L;
      }
   }

   private void QmdAc(boolean var1) {
      if (ClientUtils.isInWorld()) {
         this.speedItemStack = this.createNamedItem(Items.sugar, this.getSpeedItemName());
         this.teleportItemStack = this.createNamedItem(Items.blaze_rod, EnumChatFormatting.GOLD + "Teleport");
         this.UdA = this.createNamedItem(Items.compass, this.zYuao());
         this.applyFakeHotbar(mc.thePlayer.inventory.mainInventory);
         if (cameraEntity != null && cameraEntity.inventory != null) {
            this.applyFakeHotbar(cameraEntity.inventory.mainInventory);
         }

         if (var1) {
            mc.thePlayer.inventory.currentItem = 0;
            if (cameraEntity != null && cameraEntity.inventory != null) {
               cameraEntity.inventory.currentItem = 0;
            }
         }

         mc.thePlayer.inventory.markDirty();
         this.muCf(mc.thePlayer.getHeldItem());
      }
   }

   private void ensureFakeHotbar() {
      if (ClientUtils.isInWorld() && this.inventorySpoofed) {
         ItemStack[] var1 = mc.thePlayer.inventory.mainInventory;
         if (this.speedItemStack == null || this.teleportItemStack == null || this.UdA == null || var1[0] != this.speedItemStack || var1[1] != this.teleportItemStack || var1[2] != this.UdA) {
            this.QmdAc(false);
         }
      }
   }

   private void applyFakeHotbar(ItemStack[] var1) {
      var1[0] = this.speedItemStack;
      var1[1] = this.teleportItemStack;
      var1[2] = this.UdA;

      for (int var2 = 3; var2 < 9; var2++) {
         var1[var2] = null;
      }
   }

   private void applyFakeItemsToWindow(ItemStack[] var1) {
      if (var1.length > 36) {
         var1[36] = this.speedItemStack;
      }

      if (var1.length > 37) {
         var1[37] = this.teleportItemStack;
      }

      if (var1.length > 38) {
         var1[38] = this.UdA;
      }

      for (int var2 = 39; var2 < 45 && var2 < var1.length; var2++) {
         var1[var2] = null;
      }
   }

   private ItemStack createNamedItem(Item var1, String var2) {
      ItemStack var3 = new ItemStack(var1);
      var3.setStackDisplayName(EnumChatFormatting.RESET + var2);
      return var3;
   }

   private ItemStack copyStack(ItemStack var1) {
      return var1 == null ? null : var1.copy();
   }

   private void muCf(ItemStack var1) {
      if (var1 != null && mc.ingameGUI != null) {
         IAccessorGuiIngame var2 = (IAccessorGuiIngame)mc.ingameGUI;
         var2.setHighlightingItemStack(var1);
         var2.setRemainingHighlightTicks(40);
      }
   }

   private boolean isClickReady() {
      long var1 = System.currentTimeMillis();
      if (var1 < this.ygd) {
         return false;
      } else {
         this.ygd = var1 + 250L;
         return true;
      }
   }

   private boolean isSpeedItem(ItemStack var1) {
      return var1 != null && var1 == this.speedItemStack;
   }

   private boolean isTeleportItem(ItemStack var1) {
      return var1 != null && var1 == this.teleportItemStack;
   }

   private boolean isPlayerSelectItem(ItemStack var1) {
      return var1 != null && var1 == this.UdA;
   }

   private void cycleFreecamSpeed() {
      this.speedIndex = (this.speedIndex + 1) % SPEED_MULTIPLIERS.length;
      if (this.speedItemStack != null) {
         this.speedItemStack.setStackDisplayName(EnumChatFormatting.RESET + this.getSpeedItemName());
      }

      mc.thePlayer.inventory.markDirty();
   }

   private double kzGu() {
      return SPEED_MULTIPLIERS[Math.max(0, Math.min(SPEED_MULTIPLIERS.length - 1, this.speedIndex))];
   }

   private String getSpeedItemName() {
      return EnumChatFormatting.WHITE + "Speed: " + EnumChatFormatting.AQUA + this.JqLix9() + "x";
   }

   private String JqLix9() {
      double var1 = this.kzGu();
      return var1 == (int)var1 ? Integer.toString((int)var1) : String.format(Locale.ROOT, "%.1f", var1);
   }

   private void teleportToLookedAtBlock() {
      if (cameraEntity != null) {
         MovingObjectPosition var1 = cameraEntity.rayTrace(180.0, 1.0F);
         if (var1 != null && var1.typeOfHit == MovingObjectType.BLOCK && var1.sideHit != null) {
            BlockPos var2 = var1.getBlockPos();
            EnumFacing var3 = var1.sideHit;
            double var4 = var2.getX() + 0.5 + var3.getFrontOffsetX() * 0.501;
            double var6 = var2.getZ() + 0.5 + var3.getFrontOffsetZ() * 0.501;
            double var8 = var3 == EnumFacing.UP ? var2.getY() + 1.02 : (var3 == EnumFacing.DOWN ? var2.getY() - 0.02 : var1.hitVec.yCoord);
            this.moveCameraTo(new Vec3(var4, var8, var6));
         }
      }
   }

   private void cycleSelectedPlayer(boolean var1) {
      this.refreshPlayerList();
      if (this.entityPlayers.isEmpty()) {
         this.selectedPlayerIndex = -1;
         this.setSelectedPlayerName(null);
      } else {
         if (this.selectedPlayerIndex >= 0 && this.selectedPlayerIndex < this.entityPlayers.size()) {
            this.selectedPlayerIndex = Math.floorMod(this.selectedPlayerIndex + (var1 ? 1 : -1), this.entityPlayers.size());
         } else {
            this.selectedPlayerIndex = var1 ? 0 : this.entityPlayers.size() - 1;
         }

         EntityPlayer var2 = this.entityPlayers.get(this.selectedPlayerIndex);
         this.setSelectedPlayerName(var2.getGameProfile().getName());
         this.moveCameraTo(new Vec3(var2.posX, var2.posY + var2.height * 0.5, var2.posZ));
      }
   }

   private void refreshPlayerList() {
      this.entityPlayers.clear();

      for (Object var2 : mc.theWorld.playerEntities) {
         if (var2 instanceof EntityPlayer) {
            EntityPlayer var3 = (EntityPlayer)var2;
            if (var3 != mc.thePlayer && var3 != cameraEntity && !AntiBot.shouldHideEntity(var3)) {
               this.entityPlayers.add(var3);
            }
         }
      }

      this.entityPlayers.sort(new Comparator<EntityPlayer>() {
         public int compare(EntityPlayer var1, EntityPlayer var2x) {
            return var1.getGameProfile().getName().toLowerCase(Locale.ROOT).compareTo(var2x.getGameProfile().getName().toLowerCase(Locale.ROOT));
         }
      });
   }

   private void setSelectedPlayerName(String var1) {
      this.selectedPlayerName = var1;
      if (this.UdA != null) {
         this.UdA.setStackDisplayName(EnumChatFormatting.RESET + this.zYuao());
         mc.thePlayer.inventory.markDirty();
      }
   }

   private String zYuao() {
      return EnumChatFormatting.WHITE + "Player: " + EnumChatFormatting.GRAY + (this.selectedPlayerName == null ? "None" : this.selectedPlayerName);
   }

   private void moveCameraTo(Vec3 var1) {
      if (cameraEntity != null && var1 != null) {
         cameraEntity.prevPosX = cameraEntity.lastTickPosX = cameraEntity.posX;
         cameraEntity.prevPosY = cameraEntity.lastTickPosY = cameraEntity.posY;
         cameraEntity.prevPosZ = cameraEntity.lastTickPosZ = cameraEntity.posZ;
         cameraEntity.setPosition(var1.xCoord, var1.yCoord, var1.zCoord);
      }
   }

   private void updateCameraMovement() {
      double var1 = cameraEntity.posX;
      double var3 = cameraEntity.posY;
      double var5 = cameraEntity.posZ;
      double var7 = 0.215 * this.kzGu();
      double var9 = Math.toRadians(cameraEntity.rotationYaw);
      double var11 = 0.0;
      double var13 = 0.0;
      double var15 = 0.0;
      if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
         var11 -= Math.sin(var9) * var7;
         var15 += Math.cos(var9) * var7;
      }

      if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
         var11 += Math.sin(var9) * var7;
         var15 -= Math.cos(var9) * var7;
      }

      if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
         var11 += Math.cos(var9) * var7;
         var15 += Math.sin(var9) * var7;
      }

      if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
         var11 -= Math.cos(var9) * var7;
         var15 -= Math.sin(var9) * var7;
      }

      if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
         var13 += var7;
      }

      if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
         var13 -= var7;
      }

      cameraEntity.prevPosX = cameraEntity.lastTickPosX = var1;
      cameraEntity.prevPosY = cameraEntity.lastTickPosY = var3;
      cameraEntity.prevPosZ = cameraEntity.lastTickPosZ = var5;
      cameraEntity.setPosition(var1 + var11, var3 + var13, var5 + var15);
   }

   public static boolean applyCameraAngles(float var0, float var1) {
      Minecraft var2 = Minecraft.getMinecraft();
      if (cameraEntity != null && var2 != null && var2.thePlayer != null) {
         Entity var3 = var2.getRenderViewEntity();
         if (var3 == null || var3 == var2.thePlayer) {
            var3 = cameraEntity;
            var2.setRenderViewEntity((Entity)var3);
         }

         var3.setAngles(var0, var1);
         if (var3 instanceof EntityLivingBase) {
            ((EntityLivingBase)var3).rotationYawHead = ((Entity)var3).rotationYaw;
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean pqos() {
      return cameraEntity != null;
   }

   public static boolean isHiddenLocalPlayer(AbstractClientPlayer var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      return pqos() && var1 != null && var0 == var1.thePlayer;
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(buildSettingAlias("Conditions", null, new String[]{"disable on damage"}, new String[]{"Disable on damage"}));
   }

   static {
      double[] var10000 = new double[6];
      var10000[0] = 0.5;
      var10000[1] = 1.0;
      var10000[2] = 1.5;
      var10000[3] = 2.5;
      var10000[4] = 5.0;
      var10000[5] = 10.0;
      SPEED_MULTIPLIERS = var10000;
   }
}
