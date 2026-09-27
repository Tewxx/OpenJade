// Jade recovery: original class: jade.deps.eLz.dd8H7bUpF
package jade.client.common;

import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import java.util.UUID;

public class PlayerApi {
   private static final int nTm = 5000;
   private static final int READ_TIMEOUT_MILLIS = 7000;

   public static String EFq4(String var0) {
      String var1 = HttpUtils.httpRequest("https://api.mojang.com/users/profiles/minecraft/" + var0, false, false);
      return extractProfileId(var1);
   }

   public static PlayerApi$2 lookupProfileByName(String var0) {
      String var1 = HttpUtils.httpRequestWithTimeouts("https://api.minecraftservices.com/minecraft/profile/lookup/name/" + var0, false, false, 5000, 7000);
      PlayerApi$2 var2 = parseProfile(var1);
      if (var2 == null) {
         var1 = HttpUtils.httpRequestWithTimeouts("https://api.mojang.com/users/profiles/minecraft/" + var0, false, false, 5000, 7000);
         var2 = parseProfile(var1);
      }

      return var2;
   }

   public static UUID mpEw(String var0) {
      PlayerApi$2 var1 = lookupProfileByName(var0);
      return var1 == null ? null : var1.getUuid();
   }

   private static String extractProfileId(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         try {
            JsonObject var1 = parseJsonObject(var0);
            return var1.has("id") ? var1.get("id").getAsString() : "";
         } catch (Exception var2) {
            return "";
         }
      } else {
         return "";
      }
   }

   private static PlayerApi$2 parseProfile(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         try {
            JsonObject var1 = parseJsonObject(var0);
            String var2 = var1.has("id") ? var1.get("id").getAsString() : "";
            String var3 = var1.has("name") ? var1.get("name").getAsString() : "";
            UUID var4 = parseUndashedUuid(var2);
            return var4 == null ? null : new PlayerApi$2(var4, var3);
         } catch (Exception var5) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static UUID parseUndashedUuid(String var0) {
      if (var0 != null && var0.length() == 32) {
         try {
            return UUID.fromString(
               var0.substring(0, 8) + "-" + var0.substring(8, 12) + "-" + var0.substring(12, 16) + "-" + var0.substring(16, 20) + "-" + var0.substring(20)
            );
         } catch (IllegalArgumentException var2) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static int[] fetchDuelsStats(String var0, PlayerApi$1 var1) {
      int[] var2 = new int[]{0, 0, 0};
      String var3 = EFq4(var0);
      if (var3.isEmpty()) {
         var2[0] = -1;
         return var2;
      } else {
         String var4 = HttpUtils.httpRequest("https://api.hypixel.net/player?key=" + HttpUtils.yBgdIx + "&uuid=" + var3, false, false);
         if (var4.isEmpty()) {
            return null;
         } else if (var4.equals("{\"success\":true,\"player\":null}")) {
            var2[0] = -1;
            return var2;
         } else {
            return DuelsStatsParser.parseDuelsStats(parseJsonObject(var4), var1);
         }
      }
   }

   public static JsonObject parseJsonObject(String var0) {
      return new JsonParser().parse(var0).getAsJsonObject();
   }
}
