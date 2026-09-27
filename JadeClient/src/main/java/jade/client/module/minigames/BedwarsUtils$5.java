// Jade recovery: original class: jade.deps.eLz.t1ZZKD$5
package jade.client.module.minigames;

import jade.client.setting.BooleanSetting;
import net.minecraft.item.ItemStack;

public class BedwarsUtils$5 {
   private final BedwarsUtils$1 WEcQy;
   private final String qLm;
   private final String JZnq;
   private final String displayName;
   private final BooleanSetting booleanSetting;
   private final boolean matchesDisplayName;

   BedwarsUtils$5(BedwarsUtils$1 var1, String var2, String var3, BooleanSetting var4, boolean var5) {
      this.WEcQy = var1;
      this.qLm = (var5 ? "display:" : "item:") + var2;
      this.JZnq = var2;
      this.displayName = var3;
      this.booleanSetting = var4;
      this.matchesDisplayName = var5;
   }

   private static BedwarsUtils$5 createItemEntry(BedwarsUtils$1 var0, String var1, String var2, BooleanSetting var3) {
      return new BedwarsUtils$5(var0, var1, var2, var3, false);
   }

   private static BedwarsUtils$5 createDisplayEntry(BedwarsUtils$1 var0, String var1, String var2, BooleanSetting var3) {
      return new BedwarsUtils$5(var0, var1, var2, var3, true);
   }

   private boolean RproyO(ItemStack var1) {
      return this.matchesDisplayName ? this.JZnq.equals(var1.getDisplayName()) : this.JZnq.equals(BedwarsUtils.getItemRegistryKey(var1));
   }

   public static BooleanSetting uiTdt(BedwarsUtils$5 var0) {
      return var0.booleanSetting;
   }

   public static String getTimerKey(BedwarsUtils$5 var0) {
      return var0.qLm;
   }

   public static String getDisplayName(BedwarsUtils$5 var0) {
      return var0.displayName;
   }

   public static BedwarsUtils$1 getCategory(BedwarsUtils$5 var0) {
      return var0.WEcQy;
   }

   public static boolean isDisplayNameMatch(BedwarsUtils$5 var0) {
      return var0.matchesDisplayName;
   }

   public static boolean matchesItemStack(BedwarsUtils$5 var0, ItemStack var1) {
      return var0.RproyO(var1);
   }

   public static BedwarsUtils$5 qnPb(BedwarsUtils$1 var0, String var1, String var2, BooleanSetting var3) {
      return createItemEntry(var0, var1, var2, var3);
   }

   public static BedwarsUtils$5 saFbc(BedwarsUtils$1 var0, String var1, String var2, BooleanSetting var3) {
      return createDisplayEntry(var0, var1, var2, var3);
   }
}
