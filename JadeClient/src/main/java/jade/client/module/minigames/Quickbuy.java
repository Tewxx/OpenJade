// Jade recovery: module: Quickbuy (minigames); original class: jade.deps.eLz.xM032mTxLl
package jade.client.module.minigames;

import jade.client.common.ClientUtils;
import jade.client.common.Subscribe;
import jade.client.event.PreUpdateEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.GroupSetting;
import jade.client.setting.KeySetting;
import jade.client.setting.SliderSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

@ModuleInfo
public class Quickbuy extends Module {
   private static final String[] QUICKSLOT_LABELS = new String[]{
         "None",
         "1",
         "2",
         "3",
         "4",
         "5",
         "6",
         "7",
         "8",
         "9"
      };
   private static final String[][] QUICKBUY_ENTRIES;
   private final SliderSetting purchaseDelay;
   private final GroupSetting[] pUs;
   private final SliderSetting[] quickslotSettings;
   private final KeySetting[] ThXe;
   private final Map<String, Integer> HzrIp = new HashMap<>();
   private final List<int[]> pendingClicks = new ArrayList<>();
   private final Map<String, Long> lastPurchaseTimes = new HashMap<>();
   private final Map<String, Boolean> keyPressConsumed = new HashMap<>();
   private int yanwS = 0;

   private static boolean ezwqyi(ItemStack var0, String var1) {
      if (var0 == null) {
         return false;
      } else {
         Item var2 = var0.getItem();
         switch (var1) {
            case "wool":
               return var2 == Item.getItemFromBlock(Blocks.wool);
            case "stone_sword":
               return var2 == Items.stone_sword;
            case "iron_sword":
               return var2 == Items.iron_sword;
            case "golden_apple":
               return var2 == Items.golden_apple;
            case "fire_charge":
               return var2 instanceof ItemFireball;
            case "tnt":
               return var2 == Item.getItemFromBlock(Blocks.tnt);
            case "ender_pearl":
               return var2 == Items.ender_pearl;
            case "pickaxe":
               return var2 instanceof ItemPickaxe;
            case "axe":
               return var2 instanceof ItemAxe;
            case "shears":
               return var2 == Items.shears;
            case "chainmail_boots":
               return var2 == Items.chainmail_boots;
            case "iron_boots":
               return var2 == Items.iron_boots;
            case "diamond_sword":
               return var2 == Items.diamond_sword;
            case "stick":
               return var2 == Items.stick;
            case "arrow":
               return var2 == Items.arrow;
            case "diamond_boots":
               return var2 == Items.diamond_boots;
            case "upg_iron_sword":
               return var2 == Items.iron_sword;
            case "upg_iron_chest":
               return var2 == Items.iron_chestplate;
            case "upg_iron_pick":
               return var2 == Items.iron_pickaxe;
            case "upg_gold_pick":
               return var2 == Items.golden_pickaxe;
            case "upg_diamond_boots":
               return var2 == Items.diamond_boots;
            default:
               return false;
         }
      }
   }

   public Quickbuy() {
      super("Quickbuy", Category.minigames);
      this.registerSetting(
         this.purchaseDelay = new SliderSetting(
            "Purchase Delay",
            "ms",
            100.0,
            50.0,
            500.0,
            50.0
         )
      );
      int var1 = QUICKBUY_ENTRIES.length;
      this.pUs = new GroupSetting[var1];
      this.quickslotSettings = new SliderSetting[var1];
      this.ThXe = new KeySetting[var1];

      for (int var2 = 0; var2 < var1; var2++) {
         String var3 = QUICKBUY_ENTRIES[var2][1];
         boolean var4 = "true".equals(QUICKBUY_ENTRIES[var2][2]);
         GroupSetting var5 = new GroupSetting(var3);
         this.registerSetting(this.pUs[var2] = var5);
         if (!var4) {
            this.registerSetting(
               this.quickslotSettings[var2] = new SliderSetting(
                  var5, "Quickslot", 0, QUICKSLOT_LABELS
               )
            );
         }

         this.registerSetting(
            this.ThXe[var2] = new KeySetting(
               var5, var3 + " Keybind", 0
            )
         );
      }

      this.initialized = true;
   }

   @Override
   public void onEnable() {
      this.HzrIp.clear();
      this.pendingClicks.clear();
      this.lastPurchaseTimes.clear();
      this.keyPressConsumed.clear();
      this.yanwS = 0;
   }

   @Override
   public void onDisable() {
      this.HzrIp.clear();
      this.pendingClicks.clear();
   }

   @Subscribe
   public void onPreUpdate(PreUpdateEvent var1) {
      if (ClientUtils.isInWorld() && mc.currentScreen instanceof GuiChest) {
         ContainerChest var2 = (ContainerChest)mc.thePlayer.openContainer;
         String var3 = var2.getLowerChestInventory().getDisplayName().getUnformattedText();
         boolean var4 = var3.equals("Quick Buy");
         boolean var5 = var3.contains("Upgrades");
         if (!var4 && !var5) {
            this.HzrIp.clear();
         } else {
            int var6 = var2.getLowerChestInventory().getSizeInventory();
            this.HzrIp.clear();

            for (int var7 = 0; var7 < QUICKBUY_ENTRIES.length; var7++) {
               String var8 = QUICKBUY_ENTRIES[var7][0];
               boolean var9 = "true".equals(QUICKBUY_ENTRIES[var7][2]);
               if ((!var4 || !var9) && (!var5 || var9)) {
                  int var10 = var4 ? 18 : 9;
                  int var11 = var4 ? var6 - 9 : 27;

                  for (int var12 = var10; var12 < var11; var12++) {
                     ItemStack var13 = var2.getLowerChestInventory().getStackInSlot(var12);
                     if (ezwqyi(var13, var8)) {
                        this.HzrIp.put(var8, var12);
                        break;
                     }
                  }
               }
            }

            long var19 = System.currentTimeMillis();

            for (int var20 = 0; var20 < QUICKBUY_ENTRIES.length; var20++) {
               String var22 = QUICKBUY_ENTRIES[var20][0];
               boolean var24 = "true".equals(QUICKBUY_ENTRIES[var20][2]);
               if ((!var4 || !var24) && (!var5 || var24)) {
                  boolean var26 = this.ThXe[var20].isHeldDown();
                  boolean var27 = this.keyPressConsumed.getOrDefault(var22, false);
                  if (!var26) {
                     this.keyPressConsumed.put(var22, false);
                  } else {
                     int var14 = !var24 && this.quickslotSettings[var20] != null ? (int)this.quickslotSettings[var20].getInput() - 1 : -1;
                     long var15 = var24 ? 300L : 90L;
                     long var17 = this.lastPurchaseTimes.getOrDefault(var22, 0L);
                     if ((!var24 || !var27) && var19 - var17 >= var15 && this.HzrIp.containsKey(var22)) {
                        this.lastPurchaseTimes.put(var22, var19);
                        this.keyPressConsumed.put(var22, true);
                        this.pendingClicks.add(new int[]{this.HzrIp.get(var22), var14});
                     }
                  }
               }
            }

            this.yanwS++;
            int var21 = Math.max(1, (int)(this.purchaseDelay.getInput() / 50.0));
            if (this.yanwS % var21 == 0 && !this.pendingClicks.isEmpty()) {
               int[] var23 = this.pendingClicks.remove(0);
               int var25 = mc.thePlayer.openContainer.windowId;
               if (var23[1] >= 0) {
                  mc.playerController.windowClick(var25, var23[0], var23[1], 2, mc.thePlayer);
               } else {
                  mc.playerController.windowClick(var25, var23[0], 2, 3, mc.thePlayer);
               }
            }
         }
      } else {
         this.HzrIp.clear();
         this.pendingClicks.clear();
      }
   }

   static {
      String[][] var10000 = new String[21][];
      var10000[0] = new String[]{"wool", "Wool", "false"};
      var10000[1] = new String[]{"stone_sword", "Stone Sword", "false"};
      var10000[2] = new String[]{"iron_sword", "Iron Sword", "false"};
      var10000[3] = new String[]{"golden_apple", "Golden Apple", "false"};
      var10000[4] = new String[]{"fire_charge", "Fireball", "false"};
      var10000[5] = new String[]{"tnt", "TNT", "false"};
      var10000[6] = new String[]{"ender_pearl", "Ender Pearl", "false"};
      var10000[7] = new String[]{"pickaxe", "Pickaxe", "false"};
      var10000[8] = new String[]{"axe", "Axe", "false"};
      var10000[9] = new String[]{"shears", "Shears", "false"};
      var10000[10] = new String[]{"chainmail_boots", "Chainmail Armor", "false"};
      var10000[11] = new String[]{"iron_boots", "Iron Armor", "false"};
      var10000[12] = new String[]{"diamond_sword", "Diamond Sword", "false"};
      var10000[13] = new String[]{"stick", "Knockback Stick", "false"};
      var10000[14] = new String[]{"arrow", "Arrows", "false"};
      var10000[15] = new String[]{"diamond_boots", "Diamond Armor", "false"};
      var10000[16] = new String[]{"upg_iron_sword", "Sharpness", "true"};
      var10000[17] = new String[]{"upg_iron_chest", "Protection", "true"};
      var10000[18] = new String[]{"upg_iron_pick", "Mining Fatigue", "true"};
      var10000[19] = new String[]{"upg_gold_pick", "Haste", "true"};
      var10000[20] = new String[]{"upg_diamond_boots", "Feather Falling", "true"};
      QUICKBUY_ENTRIES = var10000;
   }
}
