// Jade recovery: original class: jade.deps.eLz.KLpAkY
package jade.client.setting;

import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CategoryListSetting extends Setting {
   public static final int SLOT_COUNT = 9;
   public static final int RgnL6 = 2;
   public static final String CATEGORY_SWORD = "@category:sword";
   public static final String CATEGORY_BLOCK = "@category:block";
   public static final String nNm = "@category:ladder";
   public static final String OdM6 = "@category:stick";
   public static final String CATEGORY_GAPPLE = "@category:gapple";
   public static final String CATEGORY_FIREBALL = "@category:fireball";
   public static final String CATEGORY_BOW = "@category:bow";
   public static final String CATEGORY_FISHING_ROD = "@category:fishing_rod";
   public static final String GPIE = "@category:fish";
   public static final String CATEGORY_SNOWBALL = "@category:snowball";
   public static final String aHjlu = "@category:egg";
   public static final String CATEGORY_CLOCK = "@category:clock";
   public static final String CATEGORY_ENDER_PEARL = "@category:enderpearl";
   public static final String CATEGORY_WATER = "@category:water";
   public static final String DdXb = "@category:lava";
   public static final String CATEGORY_AXE = "@category:axe";
   public static final String CATEGORY_PICKAXE = "@category:pickaxe";
   public static final String CATEGORY_SHEARS = "@category:shears";
   public static final String[] keM = new String[]{
         "@category:sword",
         "@category:block",
         "@category:stick",
         "@category:gapple",
         "@category:fireball",
         "@category:bow",
         "@category:fishing_rod",
         "@category:fish",
         "@category:snowball",
         "@category:egg",
         "@category:clock",
         "@category:enderpearl",
         "@category:water",
         "@category:lava",
         "@category:axe",
         "@category:pickaxe",
         "@category:shears",
         "@category:ladder"
      };
   public static final String[] JQZs = new String[]{
         "Sword",
         "Blocks",
         "Stick",
         "Golden apple",
         "Fireball",
         "Bow",
         "Fishing rod",
         "Fish",
         "Snowballs",
         "Eggs",
         "Clock",
         "Ender pearl",
         "Water",
         "Lava",
         "Axe",
         "Pickaxe",
         "Shears",
         "Ladders"
      };
   private final List<List<String>> WQOs = new ArrayList<>(9);

   public CategoryListSetting(String var1) {
      super(var1);

      for (int var2 = 0; var2 < 9; var2++) {
         this.WQOs.add(new ArrayList<>());
      }
   }

   public List<String> getCategoriesForSlot(int var1) {
      return var1 >= 0 && var1 < 9 ? Collections.unmodifiableList(this.WQOs.get(var1)) : Collections.emptyList();
   }

   public boolean addCategoryToSlot(int var1, String var2) {
      if (var1 >= 0 && var1 < 9 && var2 != null && !var2.isEmpty()) {
         List var3 = this.WQOs.get(var1);
         if (!var3.contains(var2) && var3.size() < 2) {
            var3.add(var2);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean MCweS(int var1) {
      return var1 >= 0 && var1 < 9 ? this.WQOs.get(var1).size() >= 2 : false;
   }

   public void removeCategoryFromSlot(int var1, String var2) {
      if (var1 >= 0 && var1 < 9) {
         this.WQOs.get(var1).remove(var2);
      }
   }

   public void removeLastCategory(int var1) {
      if (var1 >= 0 && var1 < 9) {
         List var2 = this.WQOs.get(var1);
         if (!var2.isEmpty()) {
            var2.remove(var2.size() - 1);
         }
      }
   }

   public void clearSlot(int var1) {
      if (var1 >= 0 && var1 < 9) {
         this.WQOs.get(var1).clear();
      }
   }

   public boolean isSlotEmpty(int var1) {
      return var1 < 0 || var1 >= 9 || this.WQOs.get(var1).isEmpty();
   }

   public String scXepg(int var1) {
      if (var1 >= 0 && var1 < 9) {
         List var2 = this.WQOs.get(var1);
         if (var2.isEmpty()) {
            return "None";
         } else {
            StringBuilder var3 = new StringBuilder();

            for (int var4 = 0; var4 < var2.size(); var4++) {
               if (var4 > 0) {
                  var3.append(", ");
               }

               var3.append(getCategoryDisplayName((String)var2.get(var4)));
            }

            return var3.toString();
         }
      } else {
         return "None";
      }
   }

   public static String getCategoryDisplayName(String var0) {
      for (int var1 = 0; var1 < keM.length; var1++) {
         if (keM[var1].equals(var0)) {
            return JQZs[var1];
         }
      }

      return var0;
   }

   @Override
   public void loadConfig(JsonObject var1) {
      for (int var2 = 0; var2 < 9; var2++) {
         this.WQOs.get(var2).clear();
      }

      String var11 = var1.has(this.getPath()) ? this.getPath() : (var1.has(this.getName()) ? this.getName() : null);
      if (var11 != null) {
         JsonElement var3 = var1.get(var11);
         if (var3.isJsonObject()) {
            JsonObject var4 = var3.getAsJsonObject();

            for (int var5 = 0; var5 < 9; var5++) {
               String var6 = "slot" + (var5 + 1);
               if (var4.has(var6) && var4.get(var6).isJsonArray()) {
                  for (JsonElement var9 : var4.getAsJsonArray(var6)) {
                     if (var9 != null && var9.isJsonPrimitive()) {
                        String var10 = var9.getAsString();
                        if (var10 != null && !var10.isEmpty() && keFacl(var10) && !this.WQOs.get(var5).contains(var10) && this.WQOs.get(var5).size() < 2) {
                           this.WQOs.get(var5).add(var10);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public JsonObject OIXIqfO() {
      JsonObject var1 = new JsonObject();

      for (int var2 = 0; var2 < 9; var2++) {
         JsonArray var3 = new JsonArray();

         for (String var5 : this.WQOs.get(var2)) {
            var3.add(new JsonPrimitive(var5));
         }

         var1.add("slot" + (var2 + 1), var3);
      }

      return var1;
   }

   private static boolean keFacl(String var0) {
      return Arrays.asList(keM).contains(var0);
   }
}
