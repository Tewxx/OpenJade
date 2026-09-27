// Jade recovery: original class: jade.deps.eLz.jOTEEUATI
package jade.client.command;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.PlayerApi$2;
import jade.client.common.PlayerApi;
import jade.client.common.CommandInput;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.UUID;

public class WinstreakCommand extends Command {
   private static final String Jyzw = "https://redacted/api/winstreak";

   public WinstreakCommand() {
      super("winstreak", "ws");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() != 1) {
         this.ahGlioN();
      } else {
         final String var2 = var1.getArgument(0);
         this.sendChatMessage("&7checking winstreak for &f" + var2 + "&7...");
         Jade.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
               PlayerApi$2 var1x = PlayerApi.lookupProfileByName(var2);
               if (var1x != null && var1x.getUuid() != null) {
                  String var2x = var1x.getName().isEmpty() ? var2 : var1x.getName();
                  WinstreakCommand.requestWinstreak(WinstreakCommand.this, var2x, WinstreakCommand.toPlayerId(var1x.getUuid()));
               } else {
                  WinstreakCommand.c666("&7could not find player &f" + var2 + "&7.");
               }
            }
         });
      }
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Winstreak: &b" + this.withCommandPrefix("winstreak") + " <username>");
   }

   private void fetchWinstreak(String var1, String var2) {
      HttpURLConnection var3 = null;

      try {
         JsonObject var4 = new JsonObject();
         var4.addProperty("uuid", var2);
         byte[] var5 = var4.toString().getBytes(StandardCharsets.UTF_8);
         var3 = (HttpURLConnection)new URL("https://redacted/api/winstreak").openConnection();
         var3.setRequestMethod("POST");
         var3.setDoOutput(true);
         var3.setConnectTimeout(7000);
         var3.setReadTimeout(10000);
         var3.setRequestProperty("Content-Type", "application/json; charset=utf-8");
         var3.setRequestProperty("Accept", "application/json");
         var3.setRequestProperty("Accept-Encoding", "identity");
         var3.setFixedLengthStreamingMode(var5.length);
         OutputStream var6 = var3.getOutputStream();

         try {
            var6.write(var5);
         } finally {
            var6.close();
         }

         int var7 = var3.getResponseCode();
         String var8 = readResponseBody(var3, var7);
         JsonObject var9 = new JsonParser().parse(var8 != null && !var8.isEmpty() ? var8 : "{}").getAsJsonObject();
         if (!var9.has("success") || !var9.get("success").isJsonPrimitive() || var9.get("success").getAsBoolean()) {
            if (var7 != 200) {
               queueChatMessage("&7winstreak API returned HTTP &c" + var7 + "&7.");
               return;
            }

            JsonObject var28 = XPant(var9, "data");
            if (var28 == null) {
               queueChatMessage("&7winstreak API returned malformed data.");
               return;
            }

            JsonElement var11 = var28.get("accurate_winstreak");
            boolean var12 = var11 == null || var11.isJsonNull();
            int var13 = var12 ? getIntProperty(var28, "winstreak") : getIntProperty(var28, "accurate_winstreak");
            String var14 = var12 ? "~" : "";
            queueChatMessage("&f" + var1 + " &7has a " + getWinstreakColor(var13) + var14 + var13 + " &7winstreak!");
            return;
         }

         String var10 = getStringProperty(var9, "reason");
         queueChatMessage(var10.isEmpty() ? "&7could not load winstreak for &f" + var1 + "&7." : "&7winstreak failed: &c" + var10 + "&7.");
      } catch (Exception var26) {
         queueChatMessage("&7could not load winstreak for &f" + var1 + "&7.");
         return;
      } finally {
         if (var3 != null) {
            var3.disconnect();
         }
      }
   }

   private static String readResponseBody(HttpURLConnection var0, int var1) throws Exception {
      InputStream var2 = var1 >= 200 && var1 < 400 ? var0.getInputStream() : var0.getErrorStream();
      if (var2 == null) {
         return "";
      } else {
         BufferedReader var3 = new BufferedReader(new InputStreamReader(var2, StandardCharsets.UTF_8));

         String var6;
         try {
            StringBuilder var4 = new StringBuilder();

            String var5;
            while ((var5 = var3.readLine()) != null) {
               var4.append(var5);
            }

            var6 = var4.toString();
         } finally {
            var3.close();
         }

         return var6;
      }
   }

   private static JsonObject XPant(JsonObject var0, String var1) {
      return var0 != null && var0.has(var1) && var0.get(var1).isJsonObject() ? var0.getAsJsonObject(var1) : null;
   }

   private static int getIntProperty(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && var0.get(var1).isJsonPrimitive()) {
         try {
            return var0.get(var1).getAsInt();
         } catch (Exception var3) {
         }
      }

      return 0;
   }

   private static String getStringProperty(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && var0.get(var1).isJsonPrimitive()) {
         try {
            return var0.get(var1).getAsString();
         } catch (Exception var3) {
         }
      }

      return "";
   }

   private static String getWinstreakColor(int var0) {
      if (var0 >= 500) {
         return "§5";
      } else if (var0 >= 250) {
         return "§d";
      } else if (var0 >= 100) {
         return "§4";
      } else if (var0 >= 75) {
         return "§c";
      } else if (var0 >= 50) {
         return "§6";
      } else if (var0 >= 40) {
         return "§e";
      } else if (var0 >= 25) {
         return "§2";
      } else if (var0 >= 15) {
         return "§a";
      } else {
         return var0 >= 5 ? "§f" : "§7";
      }
   }

   private static String formatUuid(UUID var0) {
      return var0.toString().replace("-", "").toLowerCase(Locale.ROOT);
   }

   private static void queueChatMessage(final String var0) {
      ClientUtils.mc.addScheduledTask(new Runnable() {
         @Override
         public void run() {
            ClientUtils.sendJadeMessage("Jade", var0);
         }
      });
   }

   public static void c666(String var0) {
      queueChatMessage(var0);
   }

   public static String toPlayerId(UUID var0) {
      return formatUuid(var0);
   }

   public static void requestWinstreak(WinstreakCommand var0, String var1, String var2) {
      var0.fetchWinstreak(var1, var2);
   }
}
