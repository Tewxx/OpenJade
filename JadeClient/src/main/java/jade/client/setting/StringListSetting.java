// Jade recovery: original class: jade.deps.eLz.mWT7Do
package jade.client.setting;

import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class StringListSetting extends Setting {
   public GroupSetting groupSetting;
   private final String entryHint;
   private final int maxEntryLength;
   private final List<String> entries = new ArrayList<>();

   public StringListSetting(String var1, String var2, int var3) {
      this(null, var1, var2, var3);
   }

   public StringListSetting(GroupSetting var1, String var2, String var3, int var4) {
      super(var2);
      this.groupSetting = var1;
      this.entryHint = var3 == null ? "" : var3;
      this.maxEntryLength = var4 > 0 ? var4 : 1;
   }

   public String getInputHint() {
      return this.entryHint;
   }

   public int getMaxEntryLength() {
      return this.maxEntryLength;
   }

   public List<String> getEntries() {
      return Collections.unmodifiableList(this.entries);
   }

   public void clearEntries() {
      this.entries.clear();
   }

   public boolean addEntry(String var1) {
      String var2 = normalizeEntry(var1);
      if (var2 == null) {
         return false;
      } else {
         return this.entries.stream().anyMatch(var2::equalsIgnoreCase) ? false : this.entries.add(var2);
      }
   }

   public boolean removeEntry(String var1) {
      boolean var2 = false;
      Iterator var3 = this.entries.iterator();

      while (var3.hasNext()) {
         if (((String)var3.next()).equalsIgnoreCase(var1)) {
            var3.remove();
            var2 = true;
         }
      }

      return var2;
   }

   private static String normalizeEntry(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = var0.trim();
         return var1.length() == 0 ? null : var1;
      }
   }

   public JsonArray toJsonArray() {
      JsonArray var1 = new JsonArray();
      this.entries.forEach((recoveredArg0) -> StringListSetting.appendJsonEntry(var1, (java.lang.String) recoveredArg0));
      return var1;
   }

   public void loadEntries(JsonArray var1) {
      this.entries.clear();
      if (var1 != null) {
         for (JsonElement var3 : var1) {
            if (var3.isJsonPrimitive()) {
               String var4 = normalizeEntry(var3.getAsString());
               if (var4 != null) {
                  this.entries.add(var4);
               }
            }
         }
      }
   }

   @Override
   public String getPath() {
      return this.groupSetting == null ? this.getName() : this.groupSetting.getName() + "." + this.getName();
   }

   @Override
   public void loadConfig(JsonObject var1) {
      if (var1 != null) {
         JsonElement var2 = var1.get(this.getPath());
         if (var2 != null && var2.isJsonArray()) {
            this.loadEntries(var2.getAsJsonArray());
         }
      }
   }

   private static void appendJsonEntry(JsonArray var0, String var1) {
      var0.add(new JsonPrimitive(var1));
   }
}
