// Jade recovery: module: Attribute Swap (combat); original class: jade.deps.eLz.pPT4QTvkN
package jade.client.module.combat;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.Subscribe;
import jade.client.event.GameLoopEvent;
import jade.client.event.LeftClickEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.TickStartEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.attributeswap.ItemScorer;
import jade.client.setting.BooleanSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.SliderSetting;

import jade.deps.loader107.AdditiveMixConstantCipherThree;

import jade.mixin.impl.accessor.IAccessorMinecraft;
import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

@ModuleInfo(aliases = "AttributeSwap")
public class AttributeSwap extends Module {
   private final MultiSelectSetting multiSelectSetting;
   private final BooleanSetting onlyWhilstDisplacing;
   private final BooleanSetting rightMouseHeld;
   private final SliderSetting swapDelay;
   private int previousSlot = -1;
   private long restoreAtNanos = AdditiveMixConstantCipherThree.decodeLong(2883742705851724877L, 603303623);
   private boolean attackPending;

   public AttributeSwap() {
      super("Attribute Swap", Category.combat);
      this.onlyWhilstDisplacing = new BooleanSetting(
         "Only whilst displacing",
         false
      );
      this.rightMouseHeld = new BooleanSetting(
         "Right mouse held", false
      );
      String var10004 = "Conditionals";
      BooleanSetting[] var10005 = new BooleanSetting[2];
      var10005[0] = this.onlyWhilstDisplacing;
      var10005[1] = this.rightMouseHeld;
      this.registerSetting(this.multiSelectSetting = new MultiSelectSetting(var10004, var10005));
      this.onlyWhilstDisplacing.visible = false;
      this.rightMouseHeld.visible = false;
      this.registerSetting(this.onlyWhilstDisplacing);
      this.registerSetting(this.rightMouseHeld);
      this.registerSetting(
         this.swapDelay = new SliderSetting(
            "Swap delay", "ms", 50.0, 0.0, 250.0, 50.0
         )
      );
   }

   @Override
   public void onEnable() {
      this.previousSlot = -1;
      this.restoreAtNanos = -1L;
      this.attackPending = false;
   }

   @Override
   public void onDisable() {
      this.restorePreviousSlot();
      this.attackPending = false;
   }

   @Subscribe
   public void onTickStart(TickStartEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.previousSlot = -1;
         this.restoreAtNanos = -1L;
         this.attackPending = false;
      } else if (this.attackPending && !this.bjFs()) {
         this.attackPending = false;
      }
   }

   @Subscribe
   public void onGameLoop(GameLoopEvent var1) {
      if (this.previousSlot >= 0 && System.nanoTime() >= this.restoreAtNanos) {
         this.restorePreviousSlot();
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPacketSend(PacketSendEvent var1) {
      if (!var1.isCanceled()
         && var1.ys98() instanceof C02PacketUseEntity
         && ((C02PacketUseEntity)var1.ys98()).getAction() == Action.ATTACK
         && ClientUtils.isInWorld()
         && this.bjFs()) {
         this.attackPending = true;
      }
   }

   @Subscribe
   public void onLeftClick(LeftClickEvent var1) {
      if (this.attackPending
         && ClientUtils.isInWorld()
         && ((IAccessorMinecraft)mc).getLeftClickCounter() <= 0
         && mc.objectMouseOver != null
         && mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY
         && this.bjFs()
         && (!this.onlyWhilstDisplacing.isToggled() || this.isDisplacing())
         && (!this.rightMouseHeld.isToggled() || ClientUtils.xusXfhC(mc.gameSettings.keyBindUseItem))) {
         int var2 = this.findBestSwordSlot();
         if (var2 >= 0 && var2 != mc.thePlayer.inventory.currentItem) {
            this.attackPending = false;
            this.previousSlot = mc.thePlayer.inventory.currentItem;
            this.restoreAtNanos = System.nanoTime() + (long)(this.swapDelay.getInput() * 1000000.0);
            mc.thePlayer.inventory.currentItem = var2;
            ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
         }
      }
   }

   private void restorePreviousSlot() {
      int var1 = this.previousSlot;
      this.previousSlot = -1;
      this.restoreAtNanos = -1L;
      if (var1 >= 0 && var1 < 9 && ClientUtils.isInWorld() && mc.playerController != null) {
         if (mc.thePlayer.inventory.currentItem != var1) {
            mc.thePlayer.inventory.currentItem = var1;
            ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
         }
      }
   }

   private boolean bjFs() {
      ItemStack var1 = mc.thePlayer.getHeldItem();
      return var1 != null && (var1.getItem() == Items.golden_pickaxe || EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, var1) > 0);
   }

   private boolean isDisplacing() {
      KBDisplace var1 = Jade.getModuleManager().getModule(KBDisplace.class);
      return var1 != null && (var1.isDisplacing() || var1.isSimulatingClick());
   }

   private int findBestSwordSlot() {
      int var1 = -1;
      double var2 = Double.NEGATIVE_INFINITY;

      for (int var4 = 0; var4 < 9; var4++) {
         ItemStack var5 = mc.thePlayer.inventory.getStackInSlot(var4);
         if (var5 != null && var5.getItem() instanceof ItemSword) {
            double var6 = ItemScorer.getTotalMeleeDamage(var5);
            if (var6 > var2) {
               var2 = var6;
               var1 = var4;
            }
         }
      }

      return var1;
   }
}
