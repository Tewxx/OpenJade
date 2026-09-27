// Jade recovery: original class: jade.deps.eLz.lKOs0ysV
package jade.client.common;

import jade.client.module.client.Settings;
import jade.client.setting.BooleanSetting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;

public final class WeaponCheck {
   private WeaponCheck() {
   }

   public static boolean isHoldingEnabledWeapon(EntityLivingBase var0) {
      if (var0.getHeldItem() == null) {
         return isSettingEnabled(Settings.fist);
      } else {
         Item var1 = var0.getHeldItem().getItem();

         for (int var2 = 0; var2 < 7; var2++) {
            if (isSettingEnabled(getWeaponSettingForIndex(var2)) && matchesWeaponItem(var1, var2)) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean isSettingEnabled(BooleanSetting var0) {
      return var0 != null && var0.isToggled();
   }

   private static BooleanSetting getWeaponSettingForIndex(int var0) {
      switch (var0) {
         case 0:
            return Settings.sword;
         case 1:
            return Settings.axe;
         case 2:
            return Settings.bow;
         case 3:
            return Settings.rod;
         case 4:
            return Settings.stick;
         case 5:
            return Settings.hoe;
         default:
            return Settings.shovel;
      }
   }

   private static boolean matchesWeaponItem(Item var0, int var1) {
      switch (var1) {
         case 0:
            return var0 instanceof ItemSword;
         case 1:
            return var0 instanceof ItemAxe;
         case 2:
            return var0 instanceof ItemBow;
         case 3:
            return var0 instanceof ItemFishingRod;
         case 4:
            return var0 == Items.stick;
         case 5:
            return var0 instanceof ItemHoe;
         default:
            return var0 instanceof ItemSpade;
      }
   }
}
