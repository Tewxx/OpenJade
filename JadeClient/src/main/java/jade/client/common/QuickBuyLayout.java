// Jade recovery: original class: jade.deps.eLz.RDLlOYGZz
package jade.client.common;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public final class QuickBuyLayout {
   public static final int SLOT_COUNT = 21;
   public static final List<String> XouT = Collections.unmodifiableList(
      ahnDbkE(
         "wool,stone_sword,wooden_pickaxe,wooden_axe,stick_(knockback_i),bridge_egg,ender_pearl,end_stone,iron_sword,fireball,chainmail_boots,shears,jump_v_potion_(45_seconds),water_bucket,golden_apple,ladder,bedbug,iron_boots,wood,speed_ii_potion_(45_seconds),magic_milk"
      )
   );

   private QuickBuyLayout() {
   }

   public static List<String> ahnDbkE(String var0) {
      ArrayList var1 = new ArrayList();
      if (var0 != null && !var0.trim().isEmpty()) {
         String[] var2 = var0.split(",");

         for (String var6 : var2) {
            String var7 = normalizeItemName(var6);
            if (!var7.isEmpty()) {
               var1.add(var7);
            }
         }

         return var1;
      } else {
         return var1;
      }
   }

   public static String normalizeItemName(String var0) {
      return var0 == null ? "" : var0.trim().toLowerCase(Locale.ROOT);
   }

   public static List<String> buildLayout(List<String> var0, List<String> var1, Collection<String> var2) {
      HashSet var3 = var2 == null ? null : new HashSet(var2);
      ArrayList var4 = new ArrayList(21);
      HashSet var5 = new HashSet();

      for (int var6 = 0; var6 < 21; var6++) {
         String var7 = selectAllowedItem(getItemAt(var0, var6), var5, var3);
         if (var7.isEmpty()) {
            var7 = selectAllowedItem(getItemAt(var1, var6), var5, var3);
         }

         if (var7.isEmpty()) {
            for (String var9 : XouT) {
               var7 = selectAllowedItem(var9, var5, var3);
               if (!var7.isEmpty()) {
                  break;
               }
            }
         }

         if (var7.isEmpty()) {
            break;
         }

         var4.add(var7);
         var5.add(var7);
      }

      return var4;
   }

   public static List<String> shuffleLayout(List<String> var0) {
      return AFIIiUl(var0, new SecureRandom());
   }

   public static List<String> AFIIiUl(List<String> var0, Random var1) {
      if (!isValidLayout(var0)) {
         throw new IllegalArgumentException("Quick Buy layout must contain 21 unique items");
      } else {
         ArrayList var2 = new ArrayList(var0);

         do {
            Collections.shuffle(var2, var1);
         } while (!isDerangement(var0, var2));

         return var2;
      }
   }

   public static boolean isValidLayout(List<String> var0) {
      return var0 != null && var0.size() == 21 && new HashSet(var0).size() == 21 && !var0.contains("");
   }

   public static boolean isDerangement(List<String> var0, List<String> var1) {
      if (!ywZww(var0, var1)) {
         return false;
      } else {
         for (int var2 = 0; var2 < 21; var2++) {
            if (((String)var0.get(var2)).equals(var1.get(var2))) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean ywZww(List<String> var0, List<String> var1) {
      return isValidLayout(var0) && isValidLayout(var1) && new HashSet(var0).equals(new HashSet(var1));
   }

   public static int indexOfItem(List<String> var0, String var1) {
      return var0 == null ? -1 : var0.indexOf(normalizeItemName(var1));
   }

   private static String getItemAt(List<String> var0, int var1) {
      return var0 != null && var1 >= 0 && var1 < var0.size() ? normalizeItemName((String)var0.get(var1)) : "";
   }

   private static String selectAllowedItem(String var0, Set<String> var1, Set<String> var2) {
      String var3 = normalizeItemName(var0);
      return !var3.isEmpty() && !var1.contains(var3) && (var2 == null || var2.contains(var3)) ? var3 : "";
   }
}
