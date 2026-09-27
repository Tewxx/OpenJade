// Jade recovery: module: Auto Clicker (combat); original class: jade.deps.eLz.OV0wQSJ
package jade.client.module.combat;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.InputHookManager;
import jade.client.common.Subscribe;
import jade.client.event.PrePlayerInteractEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.shared.BreakableBlockWhitelist;
import jade.client.module.minigames.Opsec;
import jade.client.module.player.InventoryManager;
import jade.client.setting.BlockListSetting;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.SliderSetting;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;

@ModuleInfo
public class AutoClicker extends Module {
   public SliderSetting cps;
   public BooleanSetting notUsingItem;
   public BooleanSetting breakBlocks;
   public BooleanSetting breakingBlocksWhitelist;
   public BlockListSetting whitelistedBreakingBlocks;
   public BooleanSetting weaponOnly;
   public BooleanSetting disableInCreative;
   public BooleanSetting holdingLeft;
   public BooleanSetting whilstTargetting;
   public BooleanSetting whilstShiftingInInventory;
   public SliderSetting startDelay;
   private long nextClickTime;
   private long mzN;
   private boolean TTiB;
   private boolean attackKeyHeld;
   private boolean attackKeyState;
   private GuiContainer guiContainer;
   private Slot slot;
   private Random rrL;

   public AutoClicker() {
      super(
         "Auto Clicker",
         Category.combat,
         0
      );
      this.registerSetting(new DescriptionSetting("Best with delay remover."));
      this.registerSetting(this.cps = new SliderSetting("CPS", 10.0, 1.0, 20.0, 0.5, new String[]{"Target CPS"}));
      this.registerSetting(this.notUsingItem = new BooleanSetting("Not using item", false));
      this.registerSetting(this.breakBlocks = new BooleanSetting("Break blocks", false));
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
      this.registerSetting(this.disableInCreative = new BooleanSetting("Disable in creative", false));
      this.registerSetting(
         this.holdingLeft = new BooleanSetting(
            "Holding Left", true
         )
      );
      this.registerSetting(this.whilstTargetting = new BooleanSetting("Whilst Targetting", false, new String[]{"Within Aim-Assist Range"}));
      this.whilstTargetting.visible = false;
      this.registerSetting(
         this.whilstShiftingInInventory = new BooleanSetting(
            "Whilst Shifting in Inventory",
            false,
            new String[]{"In Inventory", "Inventory"}
         )
      );
      this.registerSetting(
         this.startDelay = new SliderSetting(
            "Start delay", "ms", 100.0, 0.0, 250.0, 10.0
         )
      );
      this.initialized = true;
   }

   @Override
   public String getInfo() {
      double var1 = this.cps.getInput();
      return var1 == Math.rint(var1) ? Integer.toString((int)var1) : Double.toString(ClientUtils.WXYd(var1, 1));
   }

   @Override
   public void guiUpdate() {
      this.whilstTargetting.setVisible(Jade.getModuleManager().getModule(AimAssist.class) != null && Jade.getModuleManager().getModule(AimAssist.class).isEnabled(), this);
      this.startDelay.setVisible(this.whilstShiftingInInventory.isToggled(), this);
      this.breakingBlocksWhitelist.setVisible(this.breakBlocks.isToggled(), this);
      this.whitelistedBreakingBlocks.setVisible(this.breakBlocks.isToggled() && this.breakingBlocksWhitelist.isToggled(), this);
   }

   @Override
   public void onEnable() {
      this.rrL = new Random();
      this.nextClickTime = 0L;
      this.mzN = 0L;
      this.TTiB = false;
      this.clearInventoryTarget();
      this.TXebJt();
   }

   @Override
   public void onDisable() {
      this.mzN = 0L;
      this.clearInventoryTarget();
      this.TXebJt();
      if (mc != null && mc.gameSettings != null && mc.gameSettings.keyBindAttack != null) {
         this.releaseAttackKey(mc.gameSettings.keyBindAttack.getKeyCode());
      } else {
         this.nextClickTime = 0L;
         this.TTiB = false;
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onTickEnd(TickEndEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.mzN = 0L;
         this.clearInventoryTarget();
      } else if (var1.eventPhase == EventPhase.START) {
         this.updateForcedAttackKey();
         this.processInventoryClick();
      } else if (var1.eventPhase == EventPhase.END) {
         if (!this.isInventoryClickActive()) {
            this.mzN = 0L;
         }
      }
   }

   public void setInventoryTarget(GuiContainer var1, Slot var2) {
      this.guiContainer = var1;
      this.slot = var2;
   }

   private void processInventoryClick() {
      GuiContainer var1 = this.guiContainer;
      Slot var2 = this.slot;
      if (this.isEnabled() && var1 == mc.currentScreen && this.isInventoryClickActive()) {
         long var3 = System.currentTimeMillis();
         if (this.mzN == 0L) {
            this.mzN = var3 + (long)this.startDelay.getInput();
         }

         if (this.mzN <= var3) {
            if (var2 != null
               && var2.slotNumber >= 0
               && var2.slotNumber < var1.inventorySlots.inventorySlots.size()
               && var1.inventorySlots.getSlot(var2.slotNumber) == var2
               && var2.getHasStack()
               && var2.canTakeStack(mc.thePlayer)) {
               if (mc.playerController != null && mc.thePlayer != null) {
                  mc.playerController.windowClick(var1.inventorySlots.windowId, var2.slotNumber, 0, 1, mc.thePlayer);
               }
            }
         }
      } else {
         this.mzN = 0L;
      }
   }

   private boolean isInventoryClickActive() {
      if (this.whilstShiftingInInventory.isToggled() && ClientUtils.isInWorld() && mc.currentScreen instanceof GuiContainer && this.isSupportedInventoryContainer((GuiContainer)mc.currentScreen)) {
         GuiContainer var1 = (GuiContainer)mc.currentScreen;
         return var1.inventorySlots == mc.thePlayer.openContainer
            && GuiScreen.isShiftKeyDown()
            && Mouse.isButtonDown(0)
            && !this.attackKeyHeld
            && mc.thePlayer.inventory.getItemStack() == null
            && (Jade.getModuleManager().getModule(InventoryManager.class) == null || !Jade.getModuleManager().getModule(InventoryManager.class).isExecutingPlan())
            && (Jade.getModuleManager().getModule(Opsec.class) == null || !Jade.getModuleManager().getModule(Opsec.class).isQuickBuySetupActive());
      } else {
         return false;
      }
   }

   private boolean isSupportedInventoryContainer(GuiContainer var1) {
      if (mc.thePlayer.openContainer instanceof ContainerPlayer && mc.thePlayer.openContainer == mc.thePlayer.inventoryContainer) {
         return var1.inventorySlots == mc.thePlayer.inventoryContainer && var1.inventorySlots.windowId == 0;
      } else if (mc.thePlayer.openContainer instanceof ContainerChest && var1.inventorySlots == mc.thePlayer.openContainer) {
         IInventory var2 = ((ContainerChest)mc.thePlayer.openContainer).getLowerChestInventory();
         if (var2 != null && var2.getDisplayName() != null) {
            int var3 = var2.getSizeInventory();
            String var4 = var2.getDisplayName().getUnformattedText();
            if (var4 != null && var4.trim().isEmpty()) {
               return var3 == 27 || var3 == 54;
            } else {
               return var3 == 27
                  ? matchesContainerTitle(var4, "container.chest", "Chest") || matchesContainerTitle(var4, "container.enderchest", "Ender Chest")
                  : var3 == 54 && matchesContainerTitle(var4, "container.chestDouble", "Large Chest");
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean matchesContainerTitle(String var0, String var1, String var2) {
      if (var0 == null) {
         return false;
      } else {
         String var3 = var0.trim();
         return var3.equals(I18n.format(var1, new Object[0]).trim()) || var3.equals(var2);
      }
   }

   private void clearInventoryTarget() {
      this.guiContainer = null;
      this.slot = null;
   }

   @Subscribe
   public void onPrePlayerInteract(PrePlayerInteractEvent var1) {
      if (ClientUtils.isInWorld()) {
         int var2 = mc.gameSettings.keyBindAttack.getKeyCode();
         boolean var3 = Mouse.isButtonDown(0) && !this.attackKeyHeld;
         if (this.passesClickConditions()) {
            long var4 = System.currentTimeMillis();
            if (this.nextClickTime == 0L) {
               this.nextClickTime = var4 + this.computeClickInterval();
            }

            int var6;
            for (var6 = 0; this.nextClickTime <= var4; this.nextClickTime = this.nextClickTime + this.computeClickInterval()) {
               var6++;
            }

            if (this.breakBlocks.isToggled()) {
               if (!var3) {
                  if (this.TTiB) {
                     KeyBinding.setKeyBindState(var2, false);
                     this.setAttackKeyState(false);
                     this.TTiB = false;
                  }
               } else if (!mc.thePlayer.capabilities.allowEdit) {
                  if (this.TTiB) {
                     KeyBinding.setKeyBindState(var2, false);
                     this.setAttackKeyState(false);
                     this.TTiB = false;
                  }
               } else if (mc.objectMouseOver != null) {
                  BlockPos var7 = mc.objectMouseOver.getBlockPos();
                  if (var7 != null) {
                     Block var8 = mc.theWorld.getBlockState(var7).getBlock();
                     if (this.isBlockBreakable(var7, var8)) {
                        if (!this.TTiB) {
                           KeyBinding.setKeyBindState(var2, true);
                           this.setAttackKeyState(true);
                           this.TTiB = true;
                        }

                        return;
                     }

                     if (this.TTiB) {
                        KeyBinding.setKeyBindState(var2, false);
                        this.setAttackKeyState(false);
                        this.TTiB = false;
                        return;
                     }
                  } else {
                     this.TTiB = false;
                  }
               }
            }

            if (!this.isAimAssistConditionMet()) {
               this.releaseAttackKey(var2);
               return;
            }

            for (int var9 = 0; var9 < var6; var9++) {
               KeyBinding.onTick(var2);
               this.setAttackKeyState(true);
            }
         } else {
            this.releaseAttackKey(var2);
         }
      }
   }

   private boolean passesClickConditions() {
      if (mc.currentScreen != null || !mc.inGameHasFocus) {
         return false;
      } else if (this.holdingLeft.isToggled() && !Mouse.isButtonDown(0)) {
         return false;
      } else if (this.notUsingItem.isToggled() && mc.thePlayer.isUsingItem()) {
         return false;
      } else {
         return this.disableInCreative.isToggled() && mc.thePlayer.capabilities.isCreativeMode ? false : !this.weaponOnly.isToggled() || ClientUtils.isHoldingWeapon() || this.isBlockBreakingWeapon();
      }
   }

   private boolean isBlockBreakingWeapon() {
      return this.breakBlocks.isToggled() && this.isBreakableBlockTargeted();
   }

   private boolean isAimAssistConditionMet() {
      return !this.whilstTargetting.isToggled()
         || Jade.getModuleManager().getModule(AimAssist.class) == null
         || !Jade.getModuleManager().getModule(AimAssist.class).isEnabled()
         || Jade.getModuleManager().getModule(AimAssist.class).hasTarget();
   }

   private void updateForcedAttackKey() {
      if (this.shouldForceAttackKey()) {
         InputHookManager.setMouseButtonState(0, true);
         this.attackKeyHeld = true;
      } else {
         this.TXebJt();
      }
   }

   private boolean shouldForceAttackKey() {
      return !this.holdingLeft.isToggled()
         && this.whilstTargetting.isToggled()
         && Jade.getModuleManager().getModule(AimAssist.class) != null
         && Jade.getModuleManager().getModule(AimAssist.class).hasTarget()
         && this.passesClickConditions()
         && !this.isBreakingWhitelistedBlock();
   }

   private boolean isBreakingWhitelistedBlock() {
      return this.breakBlocks.isToggled() && this.isBreakableBlockTargeted();
   }

   private boolean isBreakableBlockTargeted() {
      if (mc.objectMouseOver == null) {
         return false;
      } else {
         BlockPos var1 = mc.objectMouseOver.getBlockPos();
         if (var1 != null && mc.theWorld != null) {
            Block var2 = mc.theWorld.getBlockState(var1).getBlock();
            return this.isBlockBreakable(var1, var2);
         } else {
            return false;
         }
      }
   }

   private boolean isBlockBreakable(BlockPos var1, Block var2) {
      return var2 != Blocks.air && !(var2 instanceof BlockLiquid) && (!this.breakingBlocksWhitelist.isToggled() || BreakableBlockWhitelist.isBlockWhitelisted(mc, var1, this.whitelistedBreakingBlocks));
   }

   private void TXebJt() {
      if (this.attackKeyHeld) {
         InputHookManager.setMouseButtonState(0, false);
         this.attackKeyHeld = false;
      }
   }

   private void setAttackKeyState(boolean var1) {
      if (var1 || this.attackKeyState) {
         if (this.whilstTargetting.isToggled()) {
            InputHookManager.setMouseButtonState(0, var1);
         } else {
            InputHookManager.simulateMouseButton(0, var1);
         }

         this.attackKeyState = var1;
      }
   }

   private void releaseAttackKey(int var1) {
      this.TXebJt();
      this.nextClickTime = 0L;
      this.TTiB = false;
      KeyBinding.setKeyBindState(var1, false);
      this.setAttackKeyState(false);
   }

   private long computeClickInterval() {
      int var1 = Math.max(1, (int)this.cps.getInput());
      int var2 = 1000 / var1;
      int var3 = var2 + (this.rrL.nextInt(21) - 10);
      return Math.max(33, Math.min(180, var3));
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Conditions",
            "CPS",
            new String[]{"not using item", "break blocks", "weapon only", "disable in creative", "holding left", "whilst targetting"},
            new String[]{"Not using item", "Breaking blocks", "Holding weapon", "Not in creative", "Holding Left", "Whilst Targetting"}
         )
      );
   }
}
