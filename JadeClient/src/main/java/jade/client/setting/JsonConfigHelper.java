// Jade recovery: original class: jade.deps.eLz.dUrYUKyt0l
package jade.client.setting;

import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonPrimitive;

public final class JsonConfigHelper {
   private JsonConfigHelper() {
   }

   public static JsonPrimitive MCzK(JsonObject var0, String var1, String var2, String[] var3) {
      JsonElement var4 = mZr0(var0, var1, var2, var3);
      return var4 != null && var4.isJsonPrimitive() ? var4.getAsJsonPrimitive() : null;
   }

   public static JsonElement mZr0(JsonObject var0, String var1, String var2, String[] var3) {
      String[] var4 = new String[var3.length + 2];
      var4[0] = var1;
      var4[1] = var2;
      System.arraycopy(var3, 0, var4, 2, var3.length);

      for (String var8 : var4) {
         if (var0.has(var8)) {
            return var0.get(var8);
         }
      }

      return null;
   }
}
