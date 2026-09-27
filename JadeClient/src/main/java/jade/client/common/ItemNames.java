// Jade recovery: original class: jade.deps.eLz.N2zvoes9
package jade.client.common;

import java.util.Locale;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class ItemNames {
   private static final String EXTRA_ATTRIBUTES_TAG = "ExtraAttributes";
   private static final String Cr87 = "databaseName";

   private ItemNames() {
   }

   public static String getSkyblockId(ItemStack var0) {
      if (var0 != null && var0.hasTagCompound()) {
         NBTTagCompound var1 = var0.getSubCompound("ExtraAttributes", false);
         return var1 != null && var1.hasKey("databaseName", 8) ? QuickBuyLayout.normalizeItemName(var1.getString("databaseName")) : "";
      } else {
         return "";
      }
   }

   public static boolean tooltipContains(ItemStack var0, String var1) {
      if (var0 != null && var1 != null && !var1.isEmpty()) {
         String var2 = var1.toLowerCase(Locale.ROOT);

         try {
            for (String var5 : var0.getTooltip(ClientUtils.mc.thePlayer, false)) {
               if (ClientUtils.AOAtn(var5).toLowerCase(Locale.ROOT).contains(var2)) {
                  return true;
               }
            }
         } catch (Exception var6) {
         }

         return false;
      } else {
         return false;
      }
   }

   public static String getCleanDisplayName(ItemStack var0) {
      return var0 != null && var0.hasDisplayName() ? ClientUtils.AOAtn(var0.getDisplayName()).trim() : "";
   }

   public static String formatSkyblockName(String var0) {
      String var1 = QuickBuyLayout.normalizeItemName(var0);
      if ("wool".equals(var1)) {
         return "Wool";
      } else if ("hardened_clay".equals(var1)) {
         return "Hardened Clay";
      } else if ("blast-proof_glass".equals(var1)) {
         return "Blast-Proof Glass";
      } else if ("end_stone".equals(var1)) {
         return "End Stone";
      } else if ("ladder".equals(var1)) {
         return "Ladder";
      } else if ("wood".equals(var1)) {
         return "Oak Wood Planks";
      } else if ("obsidian".equals(var1)) {
         return "Obsidian";
      } else if ("packed_ice".equals(var1)) {
         return "Packed Ice";
      } else if ("sponge".equals(var1)) {
         return "Sponge";
      } else if ("compact_pop-up_tower".equals(var1)) {
         return "Compact Pop-up Tower";
      } else if ("tnt".equals(var1)) {
         return "TNT";
      } else if ("stone_sword".equals(var1)) {
         return "Stone Sword";
      } else if ("iron_sword".equals(var1)) {
         return "Iron Sword";
      } else if ("diamond_sword".equals(var1)) {
         return "Diamond Sword";
      } else if ("chainmail_boots".equals(var1)) {
         return "Permanent Chainmail Armor";
      } else if ("iron_boots".equals(var1)) {
         return "Permanent Iron Armor";
      } else if ("diamond_boots".equals(var1)) {
         return "Permanent Diamond Armor";
      } else if ("shears".equals(var1)) {
         return "Permanent Shears";
      } else if ("arrow".equals(var1)) {
         return "Arrow";
      } else if ("bow".equals(var1)) {
         return "Bow";
      } else if ("bow_(power_i__punch_i)".equals(var1)) {
         return "Bow (Power I, Punch I)";
      } else if ("golden_apple".equals(var1)) {
         return "Golden Apple";
      } else if ("bedbug".equals(var1)) {
         return "Bed Bug";
      } else if ("dream_defender".equals(var1)) {
         return "Dream Defender";
      } else if ("fireball".equals(var1)) {
         return "Fireball";
      } else if ("ender_pearl".equals(var1)) {
         return "Ender Pearl";
      } else if ("water_bucket".equals(var1)) {
         return "Water Bucket";
      } else if ("bridge_egg".equals(var1)) {
         return "Bridge Egg";
      } else if ("magic_milk".equals(var1)) {
         return "Magic Milk";
      } else if ("wooden_pickaxe".equals(var1)) {
         return "Wooden Pickaxe (Efficiency I)";
      } else if ("wooden_axe".equals(var1)) {
         return "Wooden Axe (Efficiency I)";
      } else if ("stick_(knockback_i)".equals(var1)) {
         return "Knockback Stick";
      } else if ("speed_ii_potion_(45_seconds)".equals(var1)) {
         return "Speed II Potion (45 seconds)";
      } else if ("jump_v_potion_(45_seconds)".equals(var1)) {
         return "Jump V Potion (45 seconds)";
      } else if ("invisibility_potion_(30_seconds)".equals(var1)) {
         return "Invisibility Potion (30 seconds)";
      } else {
         String var2 = var1.replace('_', ' ');
         StringBuilder var3 = new StringBuilder(var2.length());
         boolean var4 = true;

         for (int var5 = 0; var5 < var2.length(); var5++) {
            char var6 = var2.charAt(var5);
            var3.append(var4 && Character.isLetter(var6) ? Character.toUpperCase(var6) : var6);
            var4 = var6 == ' ' || var6 == '(';
         }

         return var3.toString();
      }
   }

   public static boolean matchesToolName(ItemStack var0, String var1) {
      return var0 != null && ToolNames.matchesToolName(ClientUtils.AOAtn(var0.getDisplayName()), var1);
   }
}
