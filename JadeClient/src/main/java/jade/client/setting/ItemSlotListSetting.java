// Jade recovery: original class: jade.deps.eLz.VFw7pGne5
package jade.client.setting;

import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonPrimitive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSlotListSetting extends ItemListSetting {
   private final Map<String, Integer> itemSlots = new HashMap<>();

   public ItemSlotListSetting(String var1) {
      this(null, var1);
   }

   public ItemSlotListSetting(String var1, String... var2) {
      this(null, var1, var2);
   }

   public ItemSlotListSetting(GroupSetting var1, String var2) {
      this(var1, var2, new String[0]);
   }

   public ItemSlotListSetting(GroupSetting var1, String var2, String... var3) {
      super(var1, var2, var3);
   }

   private boolean fiGah(String var1) {
      return var1 != null && var1.length() != 0 && !this.containsItem(var1);
   }

   private static int clampSlot(Integer var0) {
      return var0 != null && var0 >= 1 && var0 <= 9 ? var0 : 1;
   }

   @Override
   public void addItem(String var1) {
      if (this.fiGah(var1)) {
         super.addItem(var1);
         this.itemSlots.put(var1, 1);
      }
   }

   @Override
   public void removeItem(String var1) {
      super.removeItem(var1);
      this.itemSlots.remove(var1);
   }

   public Integer getSlotForItem(String var1) {
      if (var1 != null && this.getItems().contains(var1)) {
         Integer var2 = this.itemSlots.get(var1);
         return var2 == null ? 1 : var2;
      } else {
         return null;
      }
   }

   public void setSlotForItem(String var1, Integer var2) {
      if (var1 != null && this.getItems().contains(var1)) {
         this.itemSlots.put(var1, clampSlot(var2));
      }
   }

   public void moveItemToIndex(String var1, int var2) {
      List var3 = this.getItems();
      int var4 = var3.indexOf(var1);
      if (var4 >= 0) {
         int var5 = Math.min(var3.size() - 1, Math.max(0, var2));
         if (var4 != var5) {
            var3.remove(var4);
            var3.add(var5, var1);
         }
      }
   }

   @Override
   public void loadConfig(JsonObject var1) {
      JsonElement var2 = JsonConfigHelper.mZr0(var1, this.getPath(), this.getName(), this.buqyQhh());
      if (var2 != null) {
         this.getItems().clear();
         this.itemSlots.clear();
         if (var2.isJsonArray()) {
            for (JsonElement var4 : var2.getAsJsonArray()) {
               this.loadEntryFromJson(var4);
            }
         }
      }
   }

   private void loadEntryFromJson(JsonElement var1) {
      if (var1 != null && !var1.isJsonNull()) {
         if (var1.isJsonPrimitive()) {
            String var6 = var1.getAsString();
            if (this.fiGah(var6)) {
               super.addItem(var6);
               this.itemSlots.put(var6, 1);
            }
         } else if (var1.isJsonObject()) {
            JsonObject var2 = var1.getAsJsonObject();
            if (var2.has("id")) {
               String var3 = var2.get("id").getAsString();
               if (this.fiGah(var3)) {
                  super.addItem(var3);
                  JsonElement var4 = var2.get("slot");
                  int var5 = var4 != null && var4.isJsonPrimitive() ? clampSlot(var4.getAsInt()) : 1;
                  this.itemSlots.put(var3, var5);
               }
            }
         }
      }
   }

   @Override
   public JsonArray toJsonArray() {
      JsonArray var1 = new JsonArray();
      this.getItems().forEach((recoveredArg0) -> this.writeEntryToJson(var1, recoveredArg0));
      return var1;
   }

   private void writeEntryToJson(JsonArray var1, String var2) {
      JsonObject var3 = new JsonObject();
      var3.addProperty("id", var2);
      var3.add("slot", new JsonPrimitive(this.getSlotForItem(var2)));
      var1.add(var3);
   }
}
