// Jade recovery: module: Inventory Walk (movement); original class: jade.deps.eLz.MneaEV6z
package jade.client.module.movement;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.PacketUtils;
import jade.client.common.Subscribe;
import jade.client.event.PacketSendEvent;
import jade.client.event.PreUpdateEvent;
import jade.client.event.TickStartEvent;
import jade.client.event.UpdateWalkingPlayerEvent;
import jade.client.gui.ClickGui;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.movement.inventorywalk.InventoryWalkMode;
import jade.client.module.player.Stealer;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.SliderSetting;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import org.lwjgl.input.Mouse;

@ModuleInfo(aliases = {"InvMove", "Inv Walk"})
public class InventoryWalk extends Module {
   private static final String[] oJka = new String[]{"Full", "Semi", "No"};
   private static final int STOP_MODE_FULL = 0;
   private static final int STOP_MODE_SEMI = 1;
   private static final int STOP_MODE_NONE = 2;
   private static final int SLOWDOWN_STOP_TICKS = 8;
   private final SliderSetting mode;
   private final SliderSetting stopOnInteract;
   private final SliderSetting stopDuration;
   private final SliderSetting alwaysStop;
   private final BooleanSetting customContainer;
   private final BooleanSetting Ujxdk;
   private final BooleanSetting JFPJx2;
   private final BooleanSetting clickGUI;
   private final BooleanSetting rotateWhilstStealing;
   private final MultiSelectSetting multiSelectSetting;
   private final Queue<C0EPacketClickWindow> SamR = new ConcurrentLinkedQueue<>();
   private boolean RbVq;
   private boolean MpegKx;
   private boolean DeO;
   private boolean FeNk;
   private int UbkA;
   private int alwaysStopTicks;

   public InventoryWalk() {
      super("Inventory Walk", Category.movement);
      this.customContainer = this.createHiddenSetting("Custom Container", true);
      this.Ujxdk = this.createHiddenSetting(
         "Inventory", true
      );
      this.JFPJx2 = this.createHiddenSetting("Chest", true);
      this.registerSetting(new DescriptionSetting("Allows moving in inventory."));
      this.registerSetting(new DescriptionSetting("Main"));
      this.registerSetting(this.mode = new SliderSetting("Mode", InventoryWalkMode.SLOW_DOWN.ordinal(), InventoryWalkMode.labels()));
      String var10004 = "Containers";
      BooleanSetting[] var10005 = new BooleanSetting[3];
      var10005[0] = this.customContainer;
      var10005[1] = this.Ujxdk;
      var10005[2] = this.JFPJx2;
      this.registerSetting(this.multiSelectSetting = new MultiSelectSetting(var10004, var10005));
      this.registerSetting(this.clickGUI = new BooleanSetting("ClickGUI", true));
      this.registerSetting(
         this.rotateWhilstStealing = new BooleanSetting(
            "Rotate whilst stealing",
            false
         )
      );
      this.registerSetting(
         this.stopOnInteract = new SliderSetting(
            "Stop on interact", 0, oJka
         )
      );
      this.registerSetting(
         this.stopDuration = new SliderSetting(
            "Stop duration", "ticks", 8.0, 0.0, 20.0, 1.0
         )
      );
      this.registerSetting(new DescriptionSetting("Other containers"));
      this.registerSetting(
         this.alwaysStop = new SliderSetting(
            "Always stop", 0, oJka
         )
      );
      this.registerSetting(this.customContainer);
      this.registerSetting(this.Ujxdk);
      this.registerSetting(this.JFPJx2);
      this.mpSm(false);
   }

   @Override
   public void onDisable() {
      this.resetState();
      if (mc.currentScreen != null) {
         this.releaseMovementKeys();
      }
   }

   @Override
   public String getInfo() {
      return this.getInventoryWalkMode().getLabel();
   }

   @Override
   public void guiUpdate() {
      this.mpSm(true);
   }

   @Override
   public void guiSliderChanged(SliderSetting var1) {
      this.mpSm(true);
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onTickStart(TickStartEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.resetState();
      } else {
         if (this.UbkA > 0) {
            this.UbkA--;
         }

         if (this.alwaysStopTicks > 0) {
            this.alwaysStopTicks--;
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.resetState();
      } else if (mc.currentScreen instanceof ClickGui) {
         this.SamR.clear();
      } else if (this.isSlowDownMode()) {
         this.flushQueuedClicks();
      } else {
         this.SamR.clear();
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPreUpdate(PreUpdateEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.resetState();
      } else {
         this.updateMouseGrab();
         if (this.isSlowDownMode() && mc.currentScreen != null && !(mc.currentScreen instanceof ClickGui)) {
            this.stopSprinting();
         }

         if (mc.currentScreen instanceof ClickGui) {
            if (this.clickGUI.isToggled() && !((ClickGui)mc.currentScreen).isTypingInTextInput()) {
               this.EHKssP(false);
            } else if (this.RbVq) {
               this.releaseMovementKeys();
            }
         } else if (this.isInventoryScreenOpen()) {
            if (!this.isSlowDownMode() || this.UbkA <= 0 && this.SamR.isEmpty()) {
               if (this.UbkA > 0) {
                  this.applyStopMode((int)this.stopOnInteract.getInput(), true);
               } else {
                  this.EHKssP(false);
               }
            } else {
               this.applyStopMode(0, true);
            }
         } else if (!this.IKcj()) {
            if (this.RbVq) {
               this.qahZ();
               this.RbVq = false;
            }
         } else {
            if (!this.isSlowDownMode() || this.UbkA <= 0 && this.SamR.isEmpty()) {
               if (this.alwaysStopTicks > 0) {
                  this.applyStopMode((int)this.alwaysStop.getInput(), true);
               } else {
                  this.EHKssP(false);
               }
            } else {
               this.applyStopMode(0, true);
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPacketSend(PacketSendEvent var1) {
      if (ClientUtils.isInWorld()) {
         if (var1.ys98() instanceof C0EPacketClickWindow) {
            C0EPacketClickWindow var2 = (C0EPacketClickWindow)var1.ys98();
            if (this.shouldInterceptClickPacket(var2)) {
               var1.setCanceled(true);
               if (this.isSlowDownMode() && this.isClickOutsideWindow(var2)) {
                  return;
               }

               if (this.isSlowDownMode()) {
                  this.releaseMovementKeys();
                  this.stopSprinting();
                  this.RbVq = true;
                  this.UbkA = this.fjnS();
               }

               this.SamR.offer(var2);
            } else if (this.IKcj()) {
               this.applyAlwaysStop();
            }
         } else {
            if (var1.ys98() instanceof C0DPacketCloseWindow) {
               if (this.isHypixelMode() && !this.SamR.isEmpty()) {
                  var1.setCanceled(true);
                  this.flushQueuedClicks();
                  PacketUtils.sendSilently(var1.ys98());
               } else {
                  this.SamR.clear();
               }
            }
         }
      }
   }

   private void applyAlwaysStop() {
      int var1 = (int)this.alwaysStop.getInput();
      if (var1 != 2) {
         this.alwaysStopTicks = this.fjnS();
         this.applyStopMode(var1, true);
      }
   }

   private void applyStopMode(int var1, boolean var2) {
      if (var1 == 2) {
         this.EHKssP(false);
      } else if (var1 == 1) {
         this.VmayD(mc.gameSettings.keyBindForward, ClientUtils.xusXfhC(mc.gameSettings.keyBindForward));
         this.VmayD(mc.gameSettings.keyBindBack, ClientUtils.xusXfhC(mc.gameSettings.keyBindBack));
         this.VmayD(mc.gameSettings.keyBindLeft, ClientUtils.xusXfhC(mc.gameSettings.keyBindLeft));
         this.VmayD(mc.gameSettings.keyBindRight, ClientUtils.xusXfhC(mc.gameSettings.keyBindRight));
         this.VmayD(mc.gameSettings.keyBindJump, false);
         this.VmayD(mc.gameSettings.keyBindSneak, false);
         if (var2) {
            this.stopSprinting();
         }

         this.RbVq = true;
      } else {
         this.releaseMovementKeys();
         this.stopSprinting();
         this.RbVq = true;
      }
   }

   private void releaseMovementKeys() {
      this.VmayD(mc.gameSettings.keyBindForward, false);
      this.VmayD(mc.gameSettings.keyBindBack, false);
      this.VmayD(mc.gameSettings.keyBindLeft, false);
      this.VmayD(mc.gameSettings.keyBindRight, false);
      this.VmayD(mc.gameSettings.keyBindJump, false);
      this.VmayD(mc.gameSettings.keyBindSneak, false);
   }

   private void EHKssP(boolean var1) {
      this.VmayD(mc.gameSettings.keyBindForward, ClientUtils.xusXfhC(mc.gameSettings.keyBindForward));
      this.VmayD(mc.gameSettings.keyBindBack, ClientUtils.xusXfhC(mc.gameSettings.keyBindBack));
      this.VmayD(mc.gameSettings.keyBindLeft, ClientUtils.xusXfhC(mc.gameSettings.keyBindLeft));
      this.VmayD(mc.gameSettings.keyBindRight, ClientUtils.xusXfhC(mc.gameSettings.keyBindRight));
      this.VmayD(mc.gameSettings.keyBindJump, ClientUtils.isJumpKeyDown());
      this.VmayD(mc.gameSettings.keyBindSneak, var1 && ClientUtils.xusXfhC(mc.gameSettings.keyBindSneak));
      this.RbVq = true;
   }

   private void qahZ() {
      this.EHKssP(true);
      this.RbVq = false;
   }

   private void stopSprinting() {
      this.VmayD(mc.gameSettings.keyBindSprint, false);
      if (mc.thePlayer != null) {
         mc.thePlayer.setSprinting(false);
      }
   }

   private void VmayD(KeyBinding var1, boolean var2) {
      int var3 = var1.getKeyCode();
      if (var3 != 0) {
         KeyBinding.setKeyBindState(var3, var2);
      }
   }

   private boolean isInventoryScreenOpen() {
      return this.getInventoryWalkMode() != InventoryWalkMode.NONE && this.Ujxdk.isToggled() && mc.currentScreen instanceof GuiInventory;
   }

   private boolean isHypixelMode() {
      return this.getInventoryWalkMode().isHypixel();
   }

   private boolean isSlowDownMode() {
      return this.getInventoryWalkMode() == InventoryWalkMode.SLOW_DOWN;
   }

   private boolean shouldInterceptClickPacket(C0EPacketClickWindow var1) {
      if (!this.isHypixelMode()) {
         return false;
      } else {
         return !(mc.currentScreen instanceof GuiInventory) ? this.IKcj() : this.Ujxdk.isToggled() && var1.getWindowId() == 0;
      }
   }

   private boolean IKcj() {
      if (this.getInventoryWalkMode() == InventoryWalkMode.NONE
         || !(mc.currentScreen instanceof GuiContainer)
         || mc.currentScreen instanceof GuiContainerCreative
         || mc.currentScreen instanceof GuiInventory
         || mc.currentScreen instanceof ClickGui
         || mc.currentScreen instanceof GuiChat) {
         return false;
      } else if (mc.currentScreen instanceof GuiChest) {
         ContainerChest var1 = mc.thePlayer.openContainer instanceof ContainerChest ? (ContainerChest)mc.thePlayer.openContainer : null;
         String var2 = var1 != null && var1.getLowerChestInventory().getDisplayName() != null
            ? var1.getLowerChestInventory().getDisplayName().getUnformattedText()
            : "";
         boolean var3 = var2 != null && !var2.trim().isEmpty();
         return var3 ? this.customContainer.isToggled() : this.JFPJx2.isToggled();
      } else {
         return this.customContainer.isToggled();
      }
   }

   private InventoryWalkMode getInventoryWalkMode() {
      return InventoryWalkMode.fromSetting(this.mode.getInput());
   }

   private int fjnS() {
      return this.isSlowDownMode() ? 8 : Math.max(0, (int)Math.round(this.stopDuration.getInput()));
   }

   private void updateMouseGrab() {
      boolean var1 = this.rotateWhilstStealing.isToggled() && this.IKcj() && Jade.getModuleManager().getModule(Stealer.class) != null && Jade.getModuleManager().getModule(Stealer.class).isStealingActive();
      if (var1) {
         this.grabMouseForStealing();
      } else {
         this.releaseMouseGrab();
      }
   }

   private void grabMouseForStealing() {
      if (!this.MpegKx) {
         this.DeO = mc.inGameHasFocus;
         this.FeNk = Mouse.isGrabbed();
         mc.mouseHelper.grabMouseCursor();
         mc.inGameHasFocus = true;
         this.MpegKx = true;
      }
   }

   private void releaseMouseGrab() {
      if (this.MpegKx) {
         if (mc.currentScreen == null && ClientUtils.isInWorld()) {
            mc.inGameHasFocus = true;
            if (!Mouse.isGrabbed()) {
               mc.mouseHelper.grabMouseCursor();
            }
         } else {
            mc.inGameHasFocus = this.DeO;
            if (this.FeNk && !Mouse.isGrabbed()) {
               mc.mouseHelper.grabMouseCursor();
            } else if (!this.FeNk && Mouse.isGrabbed()) {
               mc.mouseHelper.ungrabMouseCursor();
            }
         }

         this.MpegKx = false;
      }
   }

   private void mpSm(boolean var1) {
      boolean var2 = this.isSlowDownMode();
      boolean var3 = this.isHypixelMode();
      if (var2) {
         this.stopOnInteract.setValueClamped(0.0);
         this.stopDuration.setValueClamped(8.0);
         this.alwaysStop.setValueClamped(0.0);
      }

      if (var1) {
         this.stopOnInteract.setVisible(!var3, this);
         this.stopDuration.setVisible(!var3, this);
         this.alwaysStop.setVisible(!var3, this);
      } else {
         this.stopOnInteract.visible = !var3;
         this.stopDuration.visible = !var3;
         this.alwaysStop.visible = !var3;
      }
   }

   private boolean isClickOutsideWindow(C0EPacketClickWindow var1) {
      return var1.getSlotId() == -999 && (var1.getMode() == 3 || var1.getMode() == 4);
   }

   private void flushQueuedClicks() {
      while (!this.SamR.isEmpty()) {
         PacketUtils.sendSilently((Packet)this.SamR.poll());
      }
   }

   private void resetState() {
      this.releaseMouseGrab();
      this.RbVq = false;
      this.UbkA = 0;
      this.alwaysStopTicks = 0;
      this.SamR.clear();
   }

   private BooleanSetting createHiddenSetting(String var1, boolean var2) {
      BooleanSetting var3 = new BooleanSetting(var1, var2);
      var3.visible = false;
      return var3;
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Allow", "Mode", new String[]{"allow jumping", "allow rotating", "allow sprinting"}, new String[]{"Jumping", "Rotating", "Sprinting"}),
         buildSettingAlias(
            "Motion", "Allow", new String[]{"modify motion after click", "slow motion when necessary"}, new String[]{"Modify after click", "Slow when needed"}
         )
      );
   }
}
