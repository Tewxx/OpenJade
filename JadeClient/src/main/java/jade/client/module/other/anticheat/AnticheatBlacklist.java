// Jade recovery: original class: jade.deps.eLz.PQ215i6wc
package jade.client.module.other.anticheat;

import jade.deps.gson.GsonBuilder;
import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import jade.deps.loader107.InjectionPaths;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;

public final class AnticheatBlacklist {
   private static final String FILE_NAME = "anticheat_blacklist.json";
   private static final Map<UUID, AnticheatBlacklist$1> flaggedPlayers = new LinkedHashMap<>();
   private static boolean loaded;

   private AnticheatBlacklist() {
   }

   public static synchronized boolean recordFlag(UUID var0, String var1, String var2, int var3) {
      if (var0 != null && ugc5(var2) != Integer.MAX_VALUE && var3 >= ugc5(var2)) {
         loadFromDisk();
         AnticheatBlacklist$1 var4 = flaggedPlayers.get(var0);
         if (var4 == null) {
            var4 = new AnticheatBlacklist$1(var0, sanitizeName(var1));
            flaggedPlayers.put(var0, var4);
         } else if (!sanitizeName(var1).isEmpty()) {
            AnticheatBlacklist$1.setLastKnownName(var4, sanitizeName(var1));
         }

         boolean var5 = AnticheatBlacklist$1.getFlags(var4).add(canonicalizeCheckName(var2));
         if (var5) {
            saveToDisk();
         }

         return var5;
      } else {
         return false;
      }
   }

   public static synchronized boolean isBlacklisted(UUID var0, String var1) {
      return findEntry(var0, var1) != null;
   }

   public static synchronized List<String> getFlags(UUID var0, String var1) {
      AnticheatBlacklist$1 var2 = findEntry(var0, var1);
      return var2 == null ? new ArrayList<>() : new ArrayList<>(AnticheatBlacklist$1.getFlags(var2));
   }

   private static AnticheatBlacklist$1 findEntry(UUID var0, String var1) {
      loadFromDisk();
      AnticheatBlacklist$1 var2 = var0 == null ? null : flaggedPlayers.get(var0);
      if (var2 != null) {
         return var2;
      } else {
         String var3 = sanitizeName(var1).toLowerCase(Locale.ROOT);
         if (var3.isEmpty()) {
            return null;
         } else {
            for (AnticheatBlacklist$1 var5 : flaggedPlayers.values()) {
               if (AnticheatBlacklist$1.getLastKnownName(var5).toLowerCase(Locale.ROOT).equals(var3)) {
                  return var5;
               }
            }

            return null;
         }
      }
   }

   private static void loadFromDisk() {
      if (!loaded) {
         loaded = true;
         File var0 = getBlacklistFile();
         if (var0.exists()) {
            try (FileReader var1 = new FileReader(var0)) {
               JsonObject var3 = new JsonParser().parse(var1).getAsJsonObject();

               for (JsonElement var6 : var3.has("players") && var3.get("players").isJsonArray() ? var3.getAsJsonArray("players") : new JsonArray()) {
                  if (var6.isJsonObject()) {
                     JsonObject var7 = var6.getAsJsonObject();
                     UUID var8 = UUID.fromString(var7.get("uuid").getAsString());
                     AnticheatBlacklist$1 var9 = new AnticheatBlacklist$1(var8, var7.has("name") ? var7.get("name").getAsString() : "");
                     if (var7.has("flags") && var7.get("flags").isJsonArray()) {
                        for (JsonElement var11 : var7.getAsJsonArray("flags")) {
                           String var12 = canonicalizeCheckName(var11.getAsString());
                           if (!var12.isEmpty()) {
                              AnticheatBlacklist$1.getFlags(var9).add(var12);
                           }
                        }
                     }

                     if (!AnticheatBlacklist$1.getFlags(var9).isEmpty()) {
                        flaggedPlayers.put(var8, var9);
                     }
                  }
               }
            } catch (Exception var23) {
               flaggedPlayers.clear();
            }
         }
      }
   }

   private static void saveToDisk() {
      File var0 = getBlacklistFile();
      File var1 = var0.getParentFile();
      if (var1.exists() || var1.mkdirs()) {
         JsonObject var2 = new JsonObject();
         JsonArray var3 = new JsonArray();

         for (AnticheatBlacklist$1 var5 : flaggedPlayers.values()) {
            JsonObject var6 = new JsonObject();
            var6.addProperty("uuid", AnticheatBlacklist$1.getPlayerUuid(var5).toString());
            var6.addProperty("name", AnticheatBlacklist$1.getLastKnownName(var5));
            JsonArray var7 = new JsonArray();

            for (String var9 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (AnticheatBlacklist$1.getFlags(var5))) {
               var7.add(var9);
            }

            var6.add("flags", var7);
            var3.add(var6);
         }

         var2.add("players", var3);

         try (FileWriter var21 = new FileWriter(var0)) {
            new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)var2, var21);
         } catch (Exception var20) {
         }
      }
   }

   private static File getBlacklistFile() {
      Minecraft var0 = Minecraft.getMinecraft();
      File var1 = var0 != null && var0.mcDataDir != null ? var0.mcDataDir : new File(".");
      return new File(InjectionPaths.dataDirectory(var1), "anticheat_blacklist.json");
   }

   private static int ugc5(String var0) {
      String var1 = canonicalizeCheckName(var0);
      if ("Autoblock".equals(var1)) {
         return 3;
      } else if ("Scaffold".equals(var1)) {
         return 5;
      } else {
         return "Bridge Assist".equals(var1) ? 5 : Integer.MAX_VALUE;
      }
   }

   private static String canonicalizeCheckName(String var0) {
      String var1 = var0 == null ? "" : var0.trim();
      if (var1.equalsIgnoreCase("autoblock")) {
         return "Autoblock";
      } else if (var1.equalsIgnoreCase("scaffold")) {
         return "Scaffold";
      } else {
         return var1.equalsIgnoreCase("bridge assist") ? "Bridge Assist" : "";
      }
   }

   private static String sanitizeName(String var0) {
      return var0 == null ? "" : var0.trim();
   }

   public static String sJmx(String var0) {
      return sanitizeName(var0);
   }
}
