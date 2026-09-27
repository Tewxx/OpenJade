// Jade recovery: module: No Slow (movement); original class: jade.deps.eLz.zjO8q5u
package jade.client.module.movement;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.AutoBlock;
import jade.client.module.movement.noslow.NoSlowMode;
import jade.client.setting.SliderSetting;

import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

@ModuleInfo(aliases = {"NoSlow", "No Slow"})
public class NoSlow extends Module {
   public static SliderSetting swordMode;
   public static SliderSetting foodMode;
   public static SliderSetting bowMode;
   public static SliderSetting slow;

   public NoSlow() {
      super("No Slow", Category.movement, 0);
      this.registerSetting(swordMode = new SliderSetting("Sword Mode", NoSlowMode.NONE.ordinal(), NoSlowMode.labels()));
      this.registerSetting(foodMode = new SliderSetting("Food Mode", NoSlowMode.NONE.ordinal(), NoSlowMode.labels()));
      this.registerSetting(bowMode = new SliderSetting("Bow Mode", NoSlowMode.NONE.ordinal(), NoSlowMode.labels()));
      this.registerSetting(slow = new SliderSetting("Slow %", 80.0, 0.0, 80.0, 1.0));
      slow.visible = this.MFejozs();
   }

   @Override
   public void guiUpdate() {
      slow.setVisible(this.MFejozs(), this);
   }

   public static boolean isVanillaModeActive() {
      return !hAugQ() && getActiveMode() == NoSlowMode.VANILLA;
   }

   public static float getMovementSpeedMultiplier() {
      NoSlowMode var0 = getActiveMode();
      return var0 == NoSlowMode.VANILLA ? (100.0F - (float)slow.getInput()) / 100.0F : 1.0F;
   }

   public static boolean CjyFd(boolean var0) {
      return Jade.getModuleManager().getModule(NoSlow.class) != null && Jade.getModuleManager().getModule(NoSlow.class).isEnabled() && var0
         ? getActiveMode() == NoSlowMode.SPRINT
         : false;
   }

   private static boolean hAugQ() {
      return Jade.getModuleManager().getModule(AutoBlock.class) != null && Jade.getModuleManager().getModule(AutoBlock.class).isSwapBlocking();
   }

   private static NoSlowMode getActiveMode() {
      if (ClientUtils.isInWorld() && Jade.getModuleManager().getModule(NoSlow.class) != null && Jade.getModuleManager().getModule(NoSlow.class).isEnabled()) {
         ItemStack var0 = mc.thePlayer.getHeldItem();
         if (var0 == null || !mc.thePlayer.isUsingItem()) {
            return NoSlowMode.NONE;
         } else if (var0.getItem() instanceof ItemSword) {
            return FPZSd(swordMode);
         } else if (isConsumableItem(var0)) {
            return FPZSd(foodMode);
         } else {
            return var0.getItem() instanceof ItemBow ? FPZSd(bowMode) : NoSlowMode.NONE;
         }
      } else {
         return NoSlowMode.NONE;
      }
   }

   private static NoSlowMode FPZSd(SliderSetting var0) {
      return NoSlowMode.fromSetting(var0.getInput());
   }

   private static boolean isConsumableItem(ItemStack var0) {
      return var0.getItem() instanceof ItemFood || var0.getItem() instanceof ItemPotion && !ItemPotion.isSplash(var0.getItemDamage());
   }

   private boolean MFejozs() {
      return FPZSd(swordMode) == NoSlowMode.VANILLA || FPZSd(foodMode) == NoSlowMode.VANILLA || FPZSd(bowMode) == NoSlowMode.VANILLA;
   }

   @Override
   public String getInfo() {
      return FPZSd(swordMode).getLabel();
   }
}
