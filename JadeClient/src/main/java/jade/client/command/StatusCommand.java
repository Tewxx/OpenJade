// Jade recovery: original class: jade.deps.eLz.EjTaZDS1W
package jade.client.command;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.PlayerApi$2;
import jade.client.common.PlayerApi;
import jade.client.common.CommandInput;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StatusCommand extends Command {
   private static final String TLy = "https://api.hypixel.net/v2/status?uuid=%s";
   private static final String RECENT_GAMES_URL = "https://api.hypixel.net/v2/recentgames?uuid=%s";
   private final Map<String, StatusCommand$7> Qmg = new ConcurrentHashMap<>();
   private volatile String hypixelApiKey = "";

   public StatusCommand() {
      super("status");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() >= 1 && var1.getArgumentCount() <= 2) {
         final String var2 = var1.getArgument(0);
         if (var1.getArgumentCount() == 2) {
            this.hypixelApiKey = var1.getArgument(1);
         }

         if (this.hypixelApiKey != null && !this.hypixelApiKey.trim().isEmpty()) {
            this.sendChatMessage("&7checking status for &f" + var2 + "&7...");
            Jade.getExecutor().execute(new Runnable() {
               @Override
               public void run() {
                  final PlayerApi$2 var1x = PlayerApi.lookupProfileByName(var2);
                  ClientUtils.mc.addScheduledTask(new Runnable() {
                     @Override
                     public void run() {
                        if (var1x != null && var1x.getUuid() != null) {
                           StatusCommand.togglePolling(StatusCommand.this, var1x, var2);
                        } else {
                           ClientUtils.sendJadeMessage("Jade", "&7could not find player &f" + var2 + "&7.");
                        }
                     }
                  });
               }
            });
         } else {
            this.sendChatMessage("&7Status needs a Hypixel API key first: &b" + this.withCommandPrefix("status") + " <username> <apikey>");
         }
      } else {
         this.ahGlioN();
      }
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Status: &b" + this.withCommandPrefix("status") + " <username> <apikey> &8(first use)");
      this.vsuIw("&7Then use &b" + this.withCommandPrefix("status") + " <username> &7for more players this session.");
   }

   private void toggleStatusPolling(PlayerApi$2 var1, String var2) {
      String var3 = wxCg5(var1.getUuid());
      StatusCommand$7 var4 = this.Qmg.remove(var3);
      String var5 = var1.getName().isEmpty() ? var2 : var1.getName();
      if (var4 != null) {
         StatusCommand$7.cancelPolling(var4);
         ClientUtils.sendJadeMessage("Jade", "&7stopped status polling for &f" + StatusCommand$7.FLoRyw(var4) + "&7.");
      } else {
         StatusCommand$7 var6 = new StatusCommand$7(this, var3, var5);
         this.Qmg.put(var3, var6);
         ClientUtils.sendJadeMessage("Jade", "&7status polling &f" + var5 + "&7.");
         StatusCommand$7.bmt2(var6);
      }
   }

   private static String wxCg5(UUID var0) {
      return var0.toString().replace("-", "").toLowerCase(Locale.ROOT);
   }

   private static JsonObject executeApiGet(String var0, String var1) throws Exception {
      HttpURLConnection var2 = null;

      JsonObject var5;
      try {
         var2 = (HttpURLConnection)new URL(var0).openConnection();
         var2.setRequestMethod("GET");
         var2.setConnectTimeout(5000);
         var2.setReadTimeout(7000);
         var2.setRequestProperty("Accept", "application/json");
         var2.setRequestProperty("Accept-Encoding", "identity");
         var2.setRequestProperty("API-Key", var1);
         int var3 = var2.getResponseCode();
         if (var3 != 200) {
            throw new StatusCommand$2(Wprybhr(var2, var3));
         }

         JsonObject var4 = readJsonFromStream(var2.getInputStream());
         if (!readJsonBoolean(var4, "success")) {
            String var9 = readJsonString(var4, "cause");
            throw new StatusCommand$2(var9.isEmpty() ? "API request failed" : var9);
         }

         var5 = var4;
      } finally {
         if (var2 != null) {
            var2.disconnect();
         }
      }

      return var5;
   }

   private static JsonObject readJsonFromStream(InputStream var0) throws Exception {
      BufferedReader var1 = new BufferedReader(new InputStreamReader(var0, StandardCharsets.UTF_8));

      JsonObject var4;
      try {
         StringBuilder var2 = new StringBuilder();

         String var3;
         while ((var3 = var1.readLine()) != null) {
            var2.append(var3);
         }

         var4 = new JsonParser().parse(var2.toString()).getAsJsonObject();
      } finally {
         var1.close();
      }

      return var4;
   }

   private static String Wprybhr(HttpURLConnection var0, int var1) {
      InputStream var2 = var0.getErrorStream();
      if (var2 != null) {
         try {
            JsonObject var3 = readJsonFromStream(var2);
            String var4 = readJsonString(var3, "cause");
            if (!var4.isEmpty()) {
               return var4;
            }
         } catch (Exception var5) {
         }
      }

      return "HTTP " + var1;
   }

   private static String readJsonString(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && var0.get(var1).isJsonPrimitive()) {
         try {
            return var0.get(var1).getAsString();
         } catch (Exception var3) {
         }
      }

      return "";
   }

   private static long readJsonLong(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && var0.get(var1).isJsonPrimitive()) {
         try {
            return var0.get(var1).getAsLong();
         } catch (Exception var3) {
         }
      }

      return 0L;
   }

   private static boolean readJsonBoolean(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && var0.get(var1).isJsonPrimitive()) {
         try {
            return var0.get(var1).getAsBoolean();
         } catch (Exception var3) {
         }
      }

      return false;
   }

   private static JsonObject readJsonObject(JsonObject var0, String var1) {
      return var0 != null && var0.has(var1) && var0.get(var1).isJsonObject() ? var0.getAsJsonObject(var1) : null;
   }

   public static void togglePolling(StatusCommand var0, PlayerApi$2 var1, String var2) {
      var0.toggleStatusPolling(var1, var2);
   }

   public static Map getActivePollers(StatusCommand var0) {
      return var0.Qmg;
   }

   public static String ewEe(StatusCommand var0) {
      return var0.hypixelApiKey;
   }

   public static JsonObject fetchApiJson(String var0, String var1) throws Exception {
      return executeApiGet(var0, var1);
   }

   public static JsonObject getJsonObject(JsonObject var0, String var1) {
      return readJsonObject(var0, var1);
   }

   public static boolean QRFfWw(JsonObject var0, String var1) {
      return readJsonBoolean(var0, var1);
   }

   public static String vLs7(JsonObject var0, String var1) {
      return readJsonString(var0, var1);
   }

   public static long getJsonLong(JsonObject var0, String var1) {
      return readJsonLong(var0, var1);
   }
}
