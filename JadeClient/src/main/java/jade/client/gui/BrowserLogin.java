// Jade recovery: original class: jade.deps.eLz.qa6FLieN
package jade.client.gui;

import jade.client.common.Account;
import jade.client.common.LoginMethod;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.HttpsURLConnection;

public class BrowserLogin {
   private static final String qAr = "https://login.live.com/oauth20_authorize.srf?redirect_uri=https://sisu.xboxlive.com/connect/oauth/XboxLive&response_type=token&client_id=000000004420578E&scope=XboxLive.Signin%20XboxLive.offline_access";

   public static CompletableFuture<Account> loginWithCookieFileAsync(File var0, Executor var1, AtomicReference<String> var2) {
      return CompletableFuture.supplyAsync(() -> BrowserLogin.loginWithCookies(var0, var2), var1);
   }

   private static Map<String, String> readCookieFile(File var0) throws Exception {
      BufferedReader var1 = new BufferedReader(new InputStreamReader(new FileInputStream(var0), "UTF-8"));

      Map var2;
      try {
         var2 = parseCookieLines(var1);
      } finally {
         var1.close();
      }

      return var2;
   }

   private static Map<String, String> parseCookieLines(BufferedReader var0) throws Exception {
      LinkedHashMap var1 = new LinkedHashMap();

      String var2;
      while ((var2 = var0.readLine()) != null) {
         var2 = var2.trim();
         if (!var2.isEmpty()
            && !var2.startsWith("#")
            && var2.contains("\t")) {
            String[] var3 = var2.split("\t");
            if (var3.length >= 7) {
               String var4 = var3[0].toLowerCase();
               if (var4.contains("login.live.com")
                  || ".live.com".equals(var4)
                  || "login.live.com".equals(var4)
                  || ".account.microsoft.com".equals(var4)) {
                  String var5 = var3[5].trim();
                  String var6 = var3[6].trim();
                  if (var6.isEmpty()) {
                     var6 = "Disabled";
                  }

                  if (!var1.containsKey(var5)) {
                     var1.put(var5, var6);
                  }
               }
            }
         }
      }

      return var1;
   }

   private static String requestAccessTokenWithCookies(Map<String, String> var0, boolean var1) throws Exception {
      HttpsURLConnection var2 = (HttpsURLConnection)new URL(
            "https://login.live.com/oauth20_authorize.srf?redirect_uri=https://sisu.xboxlive.com/connect/oauth/XboxLive&response_type=token&client_id=000000004420578E&scope=XboxLive.Signin%20XboxLive.offline_access"
         )
         .openConnection();
      var2.setRequestMethod("GET");
      var2.setRequestProperty("Host", "login.live.com");
      var2.setRequestProperty(
         "User-Agent",
         "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome Safari/537.36"
      );
      var2.setRequestProperty("Cookie", buildCookieHeader(var0, var1));
      var2.setRequestProperty("Accept", "*/*");
      var2.setRequestProperty(
         "Accept-Language", "en-US,en;q=0.9"
      );
      var2.setInstanceFollowRedirects(false);
      var2.setConnectTimeout(15000);
      var2.setReadTimeout(15000);
      int var3 = var2.getResponseCode();
      String var4 = var2.getHeaderField("Location");
      var2.disconnect();
      if (var3 == 302 && var4 != null && var4.indexOf("#access_token=") >= 0) {
         int var5 = var4.indexOf(35);
         if (var5 >= 0 && var5 + 1 < var4.length()) {
            String var6 = var4.substring(var5 + 1);
            String[] var7 = var6.split("&");

            for (String var11 : var7) {
               int var12 = var11.indexOf(61);
               if (var12 > 0 && "access_token".equals(var11.substring(0, var12))) {
                  return URLDecoder.decode(var11.substring(var12 + 1), "UTF-8");
               }
            }

            return null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static String buildCookieHeader(Map<String, String> var0, boolean var1) {
      ArrayList var2 = new ArrayList<>(
         Arrays.asList(
            var1 ? "JSH" : "JSHP",
            "MSPAuth",
            "MSPBack",
            "MSPProf",
            "MSPRequ",
            "MSPSoftVis",
            "NAP",
            "OParams",
            "PPLState",
            "WLSSC"
         )
      );

      for (String var4 : var0.keySet()) {
         if ("__Host-MSAAUTH".equals(var4)
            || var4.startsWith("__Host-")
            || "uaid".equals(var4)) {
            var2.add(var4);
         }
      }

      StringBuilder var6 = new StringBuilder();

      for (String var5 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var2)) {
         if (var0.containsKey(var5)) {
            if (var6.length() > 0) {
               var6.append(';');
            }

            var6.append(var5).append('=').append((String)var0.get(var5));
         }
      }

      return var6.toString();
   }

   private static Account loginWithCookies(File var0, AtomicReference var1) {
      try {
         if (var0 != null && var0.exists() && var0.isFile()) {
            var1.set("Reading cookie file...");
            Map var2 = readCookieFile(var0);
            if (var2.isEmpty()) {
               var1.set("No valid Microsoft cookies found");
               return null;
            } else {
               String var3 = null;
               if (var2.containsKey("JSHP")) {
                  var1.set("Trying cookie login...");

                  try {
                     var3 = requestAccessTokenWithCookies(var2, false);
                  } catch (Exception var6) {
                  }
               }

               if ((var3 == null || var3.isEmpty()) && var2.containsKey("JSH")) {
                  var1.set("Trying alternate cookie login...");

                  try {
                     var3 = requestAccessTokenWithCookies(var2, true);
                  } catch (Exception var5) {
                  }
               }

               if (var3 != null && !var3.isEmpty()) {
                  Account var4 = MicrosoftLogin.Bn490(var3, null, var1);
                  if (var4 != null) {
                     var4.setLoginMethod(LoginMethod.COOKIE);
                  }

                  return var4;
               } else {
                  var1.set("Failed to get cookie access token");
                  return null;
               }
            }
         } else {
            var1.set("Cookie file not found");
            return null;
         }
      } catch (Exception var7) {
         var1.set("Cookie error: " + var7.getMessage());
         return null;
      }
   }
}
