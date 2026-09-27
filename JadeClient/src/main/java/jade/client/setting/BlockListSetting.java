// Jade recovery: original class: jade.deps.eLz.uHaOFe3
package jade.client.setting;

import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonPrimitive;
import java.util.List;

public class BlockListSetting extends Setting {
   public GroupSetting groupSetting;
   private final ItemPatternSet entries = new ItemPatternSet();
   private final String[] defaultEntries;

   public BlockListSetting(String var1) {
      this(null, var1);
   }

   public BlockListSetting(String var1, String... var2) {
      this(null, var1, var2);
   }

   public BlockListSetting(GroupSetting var1, String var2) {
      this(var1, var2, new String[0]);
   }

   public BlockListSetting(GroupSetting var1, String var2, String... var3) {
      super(var2);
      this.groupSetting = var1;
      this.defaultEntries = var3 == null ? new String[0] : var3;
   }

   public void addEntry(String var1) {
      this.entries.addPattern(var1);
   }

   public void removeEntry(String var1) {
      this.entries.getPatterns().remove(var1);
   }

   public List<String> getEntries() {
      return this.entries.getPatterns();
   }

   public boolean containsEntry(String var1) {
      return this.entries.matchesPattern(var1);
   }

   protected String[] buqyQhh() {
      return this.defaultEntries;
   }

   @Override
   public String getPath() {
      return this.groupSetting == null ? this.getName() : this.groupSetting.getName() + "." + this.getName();
   }

   @Override
   public void loadConfig(JsonObject var1) {
      JsonElement var2 = JsonConfigHelper.mZr0(var1, this.getPath(), this.getName(), this.defaultEntries);
      if (var2 != null) {
         List var3 = this.entries.getPatterns();
         var3.clear();
         if (var2.isJsonArray()) {
            var2.getAsJsonArray().forEach((recoveredArg0) -> BlockListSetting.addEntryFromJson(var3, (jade.deps.gson.JsonElement) recoveredArg0));
         }
      }
   }

   public JsonArray toJsonArray() {
      JsonArray var1 = new JsonArray();
      this.entries.getPatterns().forEach((recoveredArg0) -> BlockListSetting.MZsg(var1, (java.lang.String) recoveredArg0));
      return var1;
   }

   private static void MZsg(JsonArray var0, String var1) {
      var0.add(new JsonPrimitive(var1));
   }

   private static void addEntryFromJson(List var0, JsonElement var1) {
      var0.add(var1.getAsString());
   }
}
