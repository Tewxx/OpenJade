// Jade recovery: original class: jade.deps.eLz.o0pBqA
package jade.client.common;

import jade.deps.gson.Gson;
import jade.deps.gson.GsonBuilder;
import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Map;

public final class RelationsJson {
   private RelationsJson() {
   }

   public static RelationsSettings load(File var0, RelationStore var1) throws IOException {
      FileReader var2 = new FileReader(var0);

      RelationsSettings var4;
      try {
         JsonObject var3 = new JsonParser().parse(var2).getAsJsonObject();
         if (var3 != null) {
            jJaxqcG(var3.get("friends"), var1, RelationManager$1.FRIEND);
            jJaxqcG(var3.get("enemies"), var1, RelationManager$1.ENEMY);
            boolean var10 = !var3.has("active") || var3.get("active").getAsBoolean();
            boolean var5 = var3.has("middleClickFriends") && var3.get("middleClickFriends").getAsBoolean();
            return new RelationsSettings(var10, var5);
         }

         var4 = new RelationsSettings(true, false);
      } finally {
         var2.close();
      }

      return var4;
   }

   public static void TNiu(File var0, RelationStore var1, RelationsSettings var2) throws IOException {
      JsonObject var3 = new JsonObject();
      var3.addProperty("active", var2.isActive());
      var3.addProperty("middleClickFriends", var2.isMiddleClickFriends());
      var3.add("friends", toJsonArray(var1.getEntryMap(RelationManager$1.FRIEND)));
      var3.add("enemies", toJsonArray(var1.getEntryMap(RelationManager$1.ENEMY)));
      FileWriter var4 = new FileWriter(var0);

      try {
         Gson var5 = new GsonBuilder().setPrettyPrinting().create();
         var5.toJson((JsonElement)var3, var4);
      } finally {
         var4.close();
      }
   }

   private static void jJaxqcG(JsonElement var0, RelationStore var1, RelationManager$1 var2) {
      if (var0 != null && var0.isJsonArray()) {
         for (JsonElement var4 : var0.getAsJsonArray()) {
            if (var4 != null && !var4.isJsonNull()) {
               if (var4.isJsonPrimitive()) {
                  String var5 = var4.getAsString();
                  var1.setEntry(var2, var5, var5);
               } else if (var4.isJsonObject()) {
                  JsonObject var8 = var4.getAsJsonObject();
                  String var6 = var8.has("key") ? var8.get("key").getAsString() : "";
                  String var7 = var8.has("displayName") ? var8.get("displayName").getAsString() : var6;
                  var1.setEntry(var2, var6, var7);
               }
            }
         }
      }
   }

   private static JsonArray toJsonArray(Map<String, String> var0) {
      JsonArray var1 = new JsonArray();

      for (Entry var3 : var0.entrySet()) {
         JsonObject var4 = new JsonObject();
         var4.addProperty("key", (String)var3.getKey());
         var4.addProperty("displayName", (String)var3.getValue());
         var1.add(var4);
      }

      return var1;
   }
}
