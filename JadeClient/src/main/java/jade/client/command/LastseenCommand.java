// Jade recovery: original class: jade.deps.eLz.XDZwkx
package jade.client.command;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.CommandInput;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LastseenCommand extends Command {
   private static final String Wma05 = "https://redacted/api/seen";

   public LastseenCommand() {
      super("lastseen", "seen", "ls");
   }

   @Override
   public void execute(CommandInput var1) {
      if (var1.getArgumentCount() != 1) {
         this.ahGlioN();
      } else {
         final String var2 = var1.getArgument(0);
         this.sendChatMessage("&7checking when &f" + var2 + " &7was last seen...");
         Jade.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
               LastseenCommand.queryLastSeen(LastseenCommand.this, var2);
            }
         });
      }
   }

   @Override
   public void ahGlioN() {
      this.sendChatMessage("&7Last seen: &b" + this.withCommandPrefix("lastseen") + " <username>");
   }

   private void egoH(String var1) {
      HttpURLConnection var2 = null;

      try {
         JsonObject var3 = new JsonObject();
         var3.addProperty("username", var1);
         byte[] var4 = var3.toString().getBytes(StandardCharsets.UTF_8);
         var2 = (HttpURLConnection)new URL("https://redacted/api/seen").openConnection();
         var2.setRequestMethod("POST");
         var2.setDoOutput(true);
         var2.setConnectTimeout(7000);
         var2.setReadTimeout(10000);
         var2.setRequestProperty("Content-Type", "application/json; charset=utf-8");
         var2.setRequestProperty("Accept", "application/json");
         var2.setRequestProperty("Accept-Encoding", "identity");
         var2.setFixedLengthStreamingMode(var4.length);
         OutputStream var5 = var2.getOutputStream();

         try {
            var5.write(var4);
         } finally {
            var5.close();
         }

         int var6 = var2.getResponseCode();
         String var7 = readResponseBody(var2, var6);
         JsonObject var8 = new JsonParser().parse(var7 != null && !var7.isEmpty() ? var7 : "{}").getAsJsonObject();
         if (!var8.has("success") || var8.get("success").getAsBoolean()) {
            if (var6 != 200) {
               replyToPlayer("&7last seen API returned HTTP &c" + var6 + "&7.");
               return;
            }

            long var27 = extractTimestamp(var8);
            if (var27 <= 0L) {
               replyToPlayer("&7no last seen data for &f" + var1 + "&7.");
               return;
            }

            String var11 = getStringField(var8, "username");
            if (var11.isEmpty()) {
               var11 = var1;
            }

            long var12 = Math.max(0L, System.currentTimeMillis() - var27);
            replyToPlayer("&f" + var11 + " &7was last seen &b" + BBtA(var12) + " &7ago!");
            return;
         }

         String var9 = getStringField(var8, "reason");
         replyToPlayer(var9.isEmpty() ? "&7no last seen data for &f" + var1 + "&7." : "&7last seen failed: &c" + var9 + "&7.");
      } catch (Exception var25) {
         replyToPlayer("&7could not load last seen data for &f" + var1 + "&7.");
         return;
      } finally {
         if (var2 != null) {
            var2.disconnect();
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

   private static long extractTimestamp(JsonObject var0) {
      long var1 = WnY0(var0, "timestamp");
      if (var1 <= 0L) {
         var1 = WnY0(var0, "last_seen");
      }

      if (var1 <= 0L) {
         var1 = WnY0(var0, "lastLobbyAt");
      }

      if (var1 > 0L && var1 < 100000000000L) {
         var1 *= 1000L;
      }

      return var1;
   }

   private static long WnY0(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && var0.get(var1).isJsonPrimitive()) {
         try {
            return var0.get(var1).getAsLong();
         } catch (Exception var3) {
         }
      }

      return 0L;
   }

   private static String getStringField(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && var0.get(var1).isJsonPrimitive()) {
         try {
            return var0.get(var1).getAsString();
         } catch (Exception var3) {
         }
      }

      return "";
   }

   private static String BBtA(long var0) {
      long var2 = var0 / 1000L;
      long var4 = var2 / 86400L;
      long var6 = var2 % 86400L / 3600L;
      long var8 = var2 % 3600L / 60L;
      long var10 = var2 % 60L;
      if (var4 > 0L) {
         return var4 + "d " + var6 + "h";
      } else if (var6 > 0L) {
         return var6 + "h " + SWsp(var8) + "m";
      } else {
         return var8 > 0L ? var8 + "m " + SWsp(var10) + "s" : var10 + "s";
      }
   }

   private static String SWsp(long var0) {
      return var0 < 10L ? "0" + var0 : String.valueOf(var0);
   }

   private static void replyToPlayer(final String var0) {
      ClientUtils.mc.addScheduledTask(new Runnable() {
         @Override
         public void run() {
            ClientUtils.sendJadeMessage("Jade", var0);
         }
      });
   }

   public static void queryLastSeen(LastseenCommand var0, String var1) {
      var0.egoH(var1);
   }
}
