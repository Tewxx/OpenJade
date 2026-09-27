// Jade recovery: original class: jade.deps.eLz.RfbQbXB6P
package jade.client.gui;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import jade.client.common.Account;
import jade.client.common.AccountType;
import jade.client.common.LoginMethod;
import jade.client.common.SessionAccessor;
import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import jade.deps.gson.JsonPrimitive;
import java.awt.Desktop.Action;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.minecraft.util.Session;

public class MicrosoftLogin {
   private static final String Orqb = "42a60a84-599d-44b2-a7c6-b00cdef1d6a2";
   private static final String MINECRAFT_CLIENT_ID = "00000000402b5328";
   private static final String LIVE_DESKTOP_REDIRECT_URI = "https://login.live.com/oauth20_desktop.srf";
   private static final String XOfwQ = "service::user.auth.xboxlive.com::MBI_SSL";
   private static final String ADXjZ = "6a3728d6-27a3-4180-99bb-479895b8f88e";
   private static final String MICROSOFT_V2_CLIENT_SECRET = "dR.50SWwVez4-PQOF2-e_2GHmC~4Xl-p4p";
   private static final String MICROSOFT_V2_TOKEN_URL = "https://login.microsoftonline.com/consumers/oauth2/v2.0/token";
   private static final int CALLBACK_PORT = 25575;
   private static final String dVviUb = "http://localhost:25575/callback";
   private static volatile HttpServer httpServer;
   private static volatile CountDownLatch countDownLatch;

   public static boolean openMicrosoftLoginPage() {
      return openLoginPageForState(UUID.randomUUID().toString());
   }

   private static boolean openLoginPageForState(String var0) {
      try {
         if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
            Desktop.getDesktop().browse(buildAuthorizeUri(var0));
            return true;
         }
      } catch (Exception var2) {
      }

      return false;
   }

   private static URI buildAuthorizeUri(String var0) throws UnsupportedEncodingException, URISyntaxException {
      String var1 = "client_id="
         + urlEncode("42a60a84-599d-44b2-a7c6-b00cdef1d6a2")
         + "&response_type=code&redirect_uri="
         + urlEncode("http://localhost:25575/callback")
         + "&scope="
         + urlEncode("XboxLive.signin XboxLive.offline_access")
         + "&state="
         + urlEncode(var0)
         + "&prompt=select_account";
      return new URI("https://login.live.com/oauth20_authorize.srf?" + var1);
   }

   public static String extractAuthCode(String var0) {
      var0 = var0.trim();
      int var1 = var0.indexOf("code=");
      if (var1 >= 0) {
         String var2 = var0.substring(var1 + 5);
         int var3 = var2.indexOf(38);
         return var3 >= 0 ? var2.substring(0, var3) : var2;
      } else {
         return var0;
      }
   }

   public static String extractAccessToken(String var0) {
      var0 = var0.trim();
      if (var0.startsWith("Bearer ")) {
         var0 = var0.substring("Bearer ".length()).trim();
      }

      try {
         if (var0.startsWith("{")) {
            JsonObject var1 = new JsonParser().parse(var0).getAsJsonObject();
            if (var1.has("access_token")) {
               return var1.get("access_token").getAsString();
            }
         }
      } catch (Exception var2) {
      }

      String var4 = XBabKd3(var0, "access_token");
      return var4 != null ? var4 : var0;
   }

   public static String extractRefreshToken(String var0) {
      var0 = var0.trim();

      try {
         if (var0.startsWith("{")) {
            JsonObject var1 = new JsonParser().parse(var0).getAsJsonObject();
            if (var1.has("refresh_token")) {
               return var1.get("refresh_token").getAsString();
            }
         }
      } catch (Exception var2) {
      }

      String var4 = XBabKd3(var0, "refresh_token");
      return var4 != null ? var4 : var0;
   }

   public static boolean isMsaArtifactToken(String var0) {
      String var1 = extractRefreshToken(var0);
      return var1.startsWith("M.")
         && var1.indexOf(".MsaArtifacts.") >= 0;
   }

   public static boolean kNp3(String var0) {
      String var1 = extractRefreshToken(var0);
      return var1.length() == 0 ? false : var1.startsWith("M.");
   }

   public static CompletableFuture<Account> startMicrosoftTokenLogin(String var0, Executor var1, AtomicReference<String> var2) {
      return loginWithMicrosoftToken(var0, var1, var2);
   }

   public static CompletableFuture<Account> loginWithMicrosoftToken(final String var0, Executor var1, final AtomicReference<String> var2) {
      return CompletableFuture.supplyAsync(new Supplier<Account>() {
         public Account get() {
            try {
               String var1x = MicrosoftLogin.extractAccessToken(var0);
               if (var1x.isEmpty()) {
                  var2.set("No access token found");
                  return null;
               } else {
                  var2.set("Checking Minecraft token...");
                  return MicrosoftLogin.loginWithMinecraftToken(var1x, var2);
               }
            } catch (Exception var2x) {
               var2.set("Error: " + var2x.getMessage());
               return null;
            }
         }
      }, var1);
   }

   public static CompletableFuture<Account> startSavedTokenValidation(final String var0, Executor var1, final AtomicReference<String> var2) {
      return CompletableFuture.supplyAsync(new Supplier<Account>() {
         public Account get() {
            try {
               String var1x = MicrosoftLogin.extractAccessToken(var0);
               if (var1x.isEmpty()) {
                  var2.set("No access token found");
                  return null;
               } else {
                  var2.set("Validating saved token...");
                  return MicrosoftLogin.validateTokenAndGetProfile(var1x, var2, false);
               }
            } catch (Exception var2x) {
               var2.set("Error: " + var2x.getMessage());
               return null;
            }
         }
      }, var1);
   }

   public static CompletableFuture<Account> startMsaArtifactLogin(final String var0, Executor var1, final AtomicReference<String> var2) {
      return CompletableFuture.supplyAsync(new Supplier<Account>() {
         public Account get() {
            try {
               final String var1x = MicrosoftLogin.extractRefreshToken(var0);
               if (var1x.isEmpty()) {
                  var2.set("No MSA artifact token found");
                  return null;
               } else {
                  String[][] var2x = MicrosoftLogin.IYvq(var1x);
                  StringBuilder var3 = new StringBuilder();
                  String[] var4 = new String[]{"consumers-v2 secret refresh", "consumers-v2 secret scoped refresh", "consumers-v2 public refresh", "consumers-v2 public scoped refresh"};
                  boolean[] var5 = new boolean[]{true, true, false, false};
                  boolean[] var6 = new boolean[]{false, true, false, true};

                  for (int var7 = 0; var7 < var4.length; var7++) {
                     String var8 = var4[var7];
                     final boolean var9 = var5[var7];
                     final boolean var10 = var6[var7];
                     Account var11 = MicrosoftLogin.PYMcS(var8, var3, var2, new MicrosoftLogin$11() {
                        @Override
                        public Account IxQia69() throws Exception {
                           return MicrosoftLogin.refreshMicrosoftV2Token(var1x, var9, var10, LoginMethod.MICROSOFT_V2, var2, true);
                        }
                     });
                     if (var11 != null) {
                        return var11;
                     }
                  }

                  for (int var13 = 0; var13 < var2x.length; var13++) {
                     String var14 = var2x[var13][0];
                     final String var15 = var2x[var13][1];
                     Account var16 = MicrosoftLogin.PYMcS(var14, var3, var2, new MicrosoftLogin$11() {
                        @Override
                        public Account IxQia69() throws Exception {
                           return MicrosoftLogin.loginWithTokenPrefix(var15, var1x, LoginMethod.MSA_ARTIFACT, var2, true);
                        }
                     });
                     if (var16 != null) {
                        return var16;
                     }
                  }

                  var2.set("MSA token failed: " + MicrosoftLogin.bygRd0(var3.toString()));
                  return null;
               }
            } catch (Exception var12) {
               var2.set("Error: " + MicrosoftLogin.formatThrowable(var12));
               return null;
            }
         }
      }, var1);
   }

   public static CompletableFuture<Account> sawbe(final String var0, Executor var1, final AtomicReference<String> var2) {
      return CompletableFuture.supplyAsync(
         new Supplier<Account>() {
            public Account get() {
               try {
                  var2.set("Refreshing Microsoft v2 session...");
                  String[] var1x = new String[]{"consumers-v2 secret refresh", "consumers-v2 secret scoped refresh", "consumers-v2 public refresh", "consumers-v2 public scoped refresh"};
                  boolean[] var2x = new boolean[]{true, true, false, false};
                  boolean[] var3 = new boolean[]{false, true, false, true};
                  StringBuilder var4 = new StringBuilder();

                  for (int var5 = 0; var5 < var1x.length; var5++) {
                     String var6 = var1x[var5];
                     var2.set("Trying Microsoft refresh: " + var6);

                     try {
                        Account var7 = MicrosoftLogin.refreshMicrosoftV2Token(var0, var2x[var5], var3[var5], LoginMethod.MICROSOFT_V2, var2, false);
                        var2.set("Microsoft refresh worked: " + var6);
                        return var7;
                     } catch (Exception var9) {
                        String var8 = MicrosoftLogin.formatThrowable(var9);
                        if (var4.length() > 0) {
                           var4.append("; ");
                        }

                        var4.append(var6).append(": ").append(var8);
                        var2.set(
                           "Microsoft refresh failed: "
                              + var6
                              + " ("
                              + var8
                              + ")"
                        );
                     }
                  }

                  var2.set("Microsoft refresh failed: " + MicrosoftLogin.bygRd0(var4.toString()));
                  return null;
               } catch (Exception var10) {
                  var2.set("Error: " + MicrosoftLogin.formatThrowable(var10));
                  return null;
               }
            }
         },
         var1
      );
   }

   public static CompletableFuture<Account> startPastedRefreshTokenLogin(final String var0, Executor var1, final AtomicReference<String> var2) {
      return CompletableFuture.supplyAsync(new Supplier<Account>() {
         public Account get() {
            try {
               String var1x = MicrosoftLogin.extractRefreshToken(var0);
               if (var1x.isEmpty()) {
                  var2.set("No refresh token found");
                  return null;
               } else {
                  return MicrosoftLogin.refreshLiveToken(var1x, var2, true);
               }
            } catch (Exception var2x) {
               var2.set("Error: " + MicrosoftLogin.formatThrowable(var2x));
               return null;
            }
         }
      }, var1);
   }

   public static CompletableFuture<Account> loginWithSavedRefreshToken(final String var0, Executor var1, final AtomicReference<String> var2) {
      return CompletableFuture.supplyAsync(new Supplier<Account>() {
         public Account get() {
            try {
               if (var0 != null && var0.length() != 0) {
                  return MicrosoftLogin.refreshLiveToken(var0, var2, false);
               } else {
                  var2.set("No refresh token saved");
                  return null;
               }
            } catch (Exception var2x) {
               var2.set("Error: " + MicrosoftLogin.formatThrowable(var2x));
               return null;
            }
         }
      }, var1);
   }

   public static CompletableFuture<Account> loginWithBrowser(Executor var0, final AtomicReference<String> var1) {
      return CompletableFuture.supplyAsync(new Supplier<Account>() {
         public Account get() {
            HttpServer var1x = null;

            Account var7;
            try {
               MicrosoftLogin.shutdownCallbackServer();
               String var2 = UUID.randomUUID().toString();
               CountDownLatch var13 = new CountDownLatch(1);
               AtomicReference var4 = new AtomicReference();
               AtomicReference var5 = new AtomicReference();
               var1x = HttpServer.create(new InetSocketAddress(25575), 0);
               MicrosoftLogin.Wvoo(var1x);
               MicrosoftLogin.Eeln(var13);
               var1x.createContext("/callback", var6x -> MicrosoftLogin.handleOAuthCallback(var6x, var2, var4, var5, var13));
               var1x.start();
               var1.set("Opening Microsoft login...");
               if (!MicrosoftLogin.IqBe4(var2)) {
                  var1.set("Open this URL in browser:\n" + MicrosoftLogin.getAuthorizeUri(var2).toString());
               } else {
                  var1.set("Complete login in browser...");
               }

               var13.await();
               if (var5.get() != null) {
                  var1.set((String)var5.get());
                  return null;
               }

               String var6 = (String)var4.get();
               if (var6 == null || var6.isEmpty()) {
                  var1.set("No OAuth code received");
                  return null;
               }

               var7 = MicrosoftLogin.loginWithAuthCode(var6, var1);
            } catch (Exception var11) {
               var1.set("Error: " + var11.getMessage());
               return null;
            } finally {
               if (var1x != null) {
                  var1x.stop(0);
               }

               if (MicrosoftLogin.getCallbackServer() == var1x) {
                  MicrosoftLogin.Wvoo(null);
                  MicrosoftLogin.Eeln(null);
               }
            }

            return var7;
         }
      }, var0);
   }

   public static void shutdownCallbackServer() {
      HttpServer var0 = httpServer;
      CountDownLatch var1 = countDownLatch;
      if (var0 != null) {
         var0.stop(0);
      }

      if (var1 != null) {
         var1.countDown();
      }

      httpServer = null;
      countDownLatch = null;
   }

   public static CompletableFuture<Account> loginWithPastedRedirect(final String var0, Executor var1, final AtomicReference<String> var2) {
      return CompletableFuture.supplyAsync(new Supplier<Account>() {
         public Account get() {
            try {
               String var1x = MicrosoftLogin.extractAuthCode(var0);
               if (var1x.isEmpty()) {
                  var2.set("No code found");
                  return null;
               } else {
                  return MicrosoftLogin.loginWithAuthCode(var1x, var2);
               }
            } catch (Exception var2x) {
               var2.set("Error: " + var2x.getMessage());
               return null;
            }
         }
      }, var1);
   }

   public static CompletableFuture<Account> refreshLiveSession(final String var0, Executor var1, final AtomicReference<String> var2) {
      return CompletableFuture.supplyAsync(
         new Supplier<Account>() {
            public Account get() {
               try {
                  var2.set("Refreshing session...");
                  String var1x = MicrosoftLogin.postForm(
                     "https://login.live.com/oauth20_token.srf",
                     "client_id=42a60a84-599d-44b2-a7c6-b00cdef1d6a2&refresh_token="
                        + URLEncoder.encode(var0, "UTF-8")
                        + "&grant_type=refresh_token&redirect_uri="
                        + URLEncoder.encode(
                           "http://localhost:25575/callback",
                           "UTF-8"
                        ),
                     "application/x-www-form-urlencoded",
                     null
                  );
                  JsonObject var2x = new JsonParser().parse(var1x).getAsJsonObject();
                  if (!var2x.has("access_token")) {
                     String var6 = var2x.has("error_description")
                        ? var2x.get("error_description").getAsString()
                        : var1x;
                     var2.set("Refresh failed: " + var6);
                     return null;
                  } else {
                     String var3 = var2x.get("access_token").getAsString();
                     String var4 = var2x.has("refresh_token")
                        ? var2x.get("refresh_token").getAsString()
                        : var0;
                     return MicrosoftLogin.loginWithRefreshedTokens(var3, var4, var2, false);
                  }
               } catch (Exception var5) {
                  var2.set("Error: " + var5.getMessage());
                  return null;
               }
            }
         },
         var1
      );
   }

   private static Account QKwpiNe(String var0, AtomicReference<String> var1) throws Exception {
      var1.set("Exchanging Microsoft code...");
      String var2 = httpPost(
         "https://login.live.com/oauth20_token.srf",
         "client_id=42a60a84-599d-44b2-a7c6-b00cdef1d6a2&code="
            + urlEncode(var0)
            + "&grant_type=authorization_code&redirect_uri="
            + urlEncode("http://localhost:25575/callback"),
         "application/x-www-form-urlencoded",
         null
      );
      JsonObject var3 = new JsonParser().parse(var2).getAsJsonObject();
      if (!var3.has("access_token")) {
         String var6 = var3.has("error_description")
            ? var3.get("error_description").getAsString()
            : var2;
         var1.set("Code exchange failed: " + var6);
         return null;
      } else {
         String var4 = var3.get("access_token").getAsString();
         String var5 = var3.has("refresh_token")
            ? var3.get("refresh_token").getAsString()
            : null;
         return Bn490(var4, var5, var1);
      }
   }

   public static Account Bn490(String var0, String var1, AtomicReference<String> var2) throws Exception {
      return loginWithMicrosoftTokens(var0, var1, var2, true);
   }

   private static Account loginWithMicrosoftTokens(String var0, String var1, AtomicReference<String> var2, boolean var3) throws Exception {
      return loginWithTokenAndMethod(var0, var1, LoginMethod.MICROSOFT, var2, var3);
   }

   private static Account loginWithTokenAndMethod(String var0, String var1, LoginMethod var2, AtomicReference<String> var3, boolean var4) throws Exception {
      return TQYxs("d=" + var0, var1, var2, var3, var4);
   }

   private static Account TQYxs(String var0, String var1, LoginMethod var2, AtomicReference<String> var3, boolean var4) throws Exception {
      var3.set("Authenticating with Xbox...");
      JsonObject var5 = new JsonObject();
      var5.addProperty("AuthMethod", "RPS");
      var5.addProperty(
         "SiteName", "user.auth.xboxlive.com"
      );
      var5.addProperty("RpsTicket", var0);
      JsonObject var6 = new JsonObject();
      var6.add("Properties", var5);
      var6.addProperty(
         "RelyingParty", "http://auth.xboxlive.com"
      );
      var6.addProperty("TokenType", "JWT");
      String var7 = httpPost(
         "https://user.auth.xboxlive.com/user/authenticate",
         var6.toString(),
         "application/json",
         null
      );
      JsonObject var8 = new JsonParser().parse(var7).getAsJsonObject();
      requireTokenField(var8, "Xbox auth failed");
      String var9 = var8.get("Token").getAsString();
      if (var8.has("DisplayClaims")
         && var8.getAsJsonObject("DisplayClaims").has("xui")
         && var8.getAsJsonObject("DisplayClaims")
               .getAsJsonArray("xui")
               .size()
            != 0
         && var8.getAsJsonObject("DisplayClaims")
            .getAsJsonArray("xui")
            .get(0)
            .getAsJsonObject()
            .has("uhs")) {
         String var10 = var8.get("DisplayClaims")
            .getAsJsonObject()
            .get("xui")
            .getAsJsonArray()
            .get(0)
            .getAsJsonObject()
            .get("uhs")
            .getAsString();
         var3.set("Getting XSTS token...");
         JsonArray var11 = new JsonArray();
         var11.add(new JsonPrimitive(var9));
         JsonObject var12 = new JsonObject();
         var12.addProperty("SandboxId", "RETAIL");
         var12.add("UserTokens", var11);
         JsonObject var13 = new JsonObject();
         var13.add("Properties", var12);
         var13.addProperty(
            "RelyingParty",
            "rp://api.minecraftservices.com/"
         );
         var13.addProperty("TokenType", "JWT");
         String var14 = httpPost(
            "https://xsts.auth.xboxlive.com/xsts/authorize",
            var13.toString(),
            "application/json",
            null
         );
         JsonObject var15 = new JsonParser().parse(var14).getAsJsonObject();
         requireTokenField(var15, "XSTS auth failed");
         String var16 = var15.get("Token").getAsString();
         var3.set("Logging in to Minecraft...");
         JsonObject var17 = new JsonObject();
         var17.addProperty(
            "identityToken",
            "XBL3.0 x=" + var10 + ";" + var16
         );
         String var18 = httpPost(
            "https://api.minecraftservices.com/authentication/login_with_xbox",
            var17.toString(),
            "application/json",
            null
         );
         JsonObject var19 = new JsonParser().parse(var18).getAsJsonObject();
         if (!var19.has("access_token")) {
            throw new IOException(vvGm(var19, "Minecraft login failed"));
         } else {
            String var20 = var19.get("access_token").getAsString();
            var3.set("Getting profile...");
            String var21 = httpGet("https://api.minecraftservices.com/minecraft/profile", var20);
            JsonObject var22 = new JsonParser().parse(var21).getAsJsonObject();
            if (var22.has("name") && var22.has("id")) {
               String var23 = var22.get("name").getAsString();
               String var24 = var22.get("id").getAsString();
               if (var4) {
                  SessionAccessor.setSession(new Session(var23, var24, var20, "mojang"));
               }

               var3.set("Logged in as " + var23);
               Account var25 = new Account(var23, var24, var20, var1, AccountType.MICROSOFT);
               var25.setLoginMethod(var2);
               return var25;
            } else {
               throw new IOException(vvGm(var22, "No Minecraft profile found"));
            }
         }
      } else {
         throw new IOException(vvGm(var8, "Xbox auth missing user hash"));
      }
   }

   private static Account createAccountFromToken(String var0, AtomicReference<String> var1) throws Exception {
      return fetchProfileAndCreateAccount(var0, var1, true);
   }

   private static Account fetchProfileAndCreateAccount(String var0, AtomicReference<String> var1, boolean var2) throws Exception {
      var1.set("Getting profile...");
      String var3 = httpGet("https://api.minecraftservices.com/minecraft/profile", var0);
      JsonObject var4 = new JsonParser().parse(var3).getAsJsonObject();
      if (var4.has("name") && var4.has("id")) {
         String var5 = var4.get("name").getAsString();
         String var6 = var4.get("id").getAsString();
         if (var2) {
            SessionAccessor.setSession(new Session(var5, var6, var0, "mojang"));
         }

         var1.set("Logged in as " + var5);
         Account var7 = new Account(var5, var6, var0, null, AccountType.MICROSOFT);
         var7.setLoginMethod(LoginMethod.TOKEN);
         return var7;
      } else {
         throw new IOException(vvGm(var4, "Invalid Minecraft token"));
      }
   }

   private static void cbYnoE(HttpExchange var0, String var1, AtomicReference<String> var2, AtomicReference<String> var3, CountDownLatch var4) throws IOException {
      try {
         String var5 = var0.getRequestURI().getRawQuery();
         String var6 = xnbej(var5, "state");
         if (!var1.equals(var6)) {
            var3.set("State mismatch during OAuth login");
         } else {
            String var7 = xnbej(var5, "code");
            if (var7 != null && !var7.isEmpty()) {
               var2.set(var7);
            } else {
               String var8 = xnbej(var5, "error");
               String var9 = xnbej(var5, "error_description");
               var3.set(
                  var8 != null
                     ? var8 + ": " + var9
                     : "No OAuth code received"
               );
            }
         }

         byte[] var13 = getCallbackResponseHtml().getBytes("UTF-8");
         var0.getResponseHeaders()
            .add("Content-Type", "text/html; charset=UTF-8");
         var0.sendResponseHeaders(200, var13.length);
         OutputStream var14 = var0.getResponseBody();
         var14.write(var13);
         var14.close();
      } finally {
         var4.countDown();
      }
   }

   private static String getCallbackResponseHtml() {
      return "<!DOCTYPE html><html><head><title>Return to Minecraft</title><meta charset=\"UTF-8\"></head><body style=\"font-family:sans-serif;text-align:center;margin-top:20vh;\"><h2>Close this window and return to Minecraft.</h2></body></html>";
   }

   private static String XBabKd3(String var0, String var1) {
      int var2 = var0.indexOf(var1 + "=");
      if (var2 < 0) {
         return null;
      } else {
         String var3 = var0.substring(var2 + var1.length() + 1);
         int var4 = var3.indexOf(38);
         if (var4 >= 0) {
            var3 = var3.substring(0, var4);
         }

         try {
            return URLDecoder.decode(var3, "UTF-8");
         } catch (UnsupportedEncodingException var6) {
            return var3;
         }
      }
   }

   private static String xnbej(String var0, String var1) {
      if (var0 == null) {
         return null;
      } else {
         String[] var2 = var0.split("&");

         for (String var6 : var2) {
            int var7 = var6.indexOf(61);
            String var8 = var7 >= 0 ? var6.substring(0, var7) : var6;
            if (var1.equals(urlDecode(var8))) {
               return var7 >= 0 ? urlDecode(var6.substring(var7 + 1)) : "";
            }
         }

         return null;
      }
   }

   private static String urlEncode(String var0) throws UnsupportedEncodingException {
      return URLEncoder.encode(var0, "UTF-8");
   }

   private static String urlDecode(String var0) {
      try {
         return URLDecoder.decode(var0, "UTF-8");
      } catch (UnsupportedEncodingException var2) {
         return var0;
      }
   }

   private static void requireTokenField(JsonObject var0, String var1) throws IOException {
      if (!var0.has("Token")) {
         throw new IOException(vvGm(var0, var1));
      }
   }

   private static Account tryLoginStep(String var0, StringBuilder var1, AtomicReference<String> var2, MicrosoftLogin$11 var3) {
      var2.set("Trying MSA token: " + var0);

      try {
         Account var4 = var3.IxQia69();
         var2.set("MSA token worked: " + var0);
         return var4;
      } catch (Exception var6) {
         String var5 = describeThrowable(var6);
         if (var1.length() > 0) {
            var1.append("; ");
         }

         var1.append(var0).append(": ").append(var5);
         var2.set(
            "MSA token failed: "
               + var0
               + " ("
               + var5
               + ")"
         );
         return null;
      }
   }

   private static Account JELVPwr(String var0, boolean var1, boolean var2, LoginMethod var3, AtomicReference<String> var4, boolean var5) throws Exception {
      String var6 = "client_id="
         + urlEncode("6a3728d6-27a3-4180-99bb-479895b8f88e")
         + "&refresh_token="
         + urlEncode(var0)
         + "&grant_type=refresh_token";
      if (var1) {
         var6 = var6
            + "&client_secret="
            + urlEncode("dR.50SWwVez4-PQOF2-e_2GHmC~4Xl-p4p");
      }

      if (var2) {
         var6 = var6
            + "&scope="
            + urlEncode("XboxLive.signin offline_access");
      }

      String var7 = httpPost(
         "https://login.microsoftonline.com/consumers/oauth2/v2.0/token",
         var6,
         "application/x-www-form-urlencoded",
         null
      );
      JsonObject var8 = parseJsonObject(var7);
      if (!var8.has("access_token")) {
         throw new IOException(vvGm(var8, "Microsoft v2 refresh failed"));
      } else {
         String var9 = var8.get("access_token").getAsString();
         String var10 = var8.has("refresh_token")
            ? var8.get("refresh_token").getAsString()
            : var0;
         return loginWithTokenAndMethod(var9, var10, var3, var4, var5);
      }
   }

   private static Account UiosN(String var0, AtomicReference<String> var1, boolean var2) throws Exception {
      var1.set("Refreshing Microsoft token...");
      String var3 = "client_id="
         + urlEncode("00000000402b5328")
         + "&grant_type=refresh_token&redirect_uri="
         + urlEncode("https://login.live.com/oauth20_desktop.srf")
         + "&refresh_token="
         + urlEncode(var0)
         + "&scope="
         + urlEncode("service::user.auth.xboxlive.com::MBI_SSL");
      String var4 = httpPost(
         "https://login.live.com/oauth20_token.srf",
         var3,
         "application/x-www-form-urlencoded",
         null
      );
      JsonObject var5 = parseJsonObject(var4);
      if (!var5.has("access_token")) {
         throw new IOException(vvGm(var5, "Refresh token failed"));
      } else {
         String var6 = var5.get("access_token").getAsString();
         String var7 = var5.has("refresh_token")
            ? var5.get("refresh_token").getAsString()
            : var0;
         return TQYxs("t=" + var6, var7, LoginMethod.MICROSOFT_REFRESH, var1, var2);
      }
   }

   private static String[][] buildTokenVariants(String var0) {
      return !var0.startsWith("d=") && !var0.startsWith("t=")
         ? new String[][]{
            {"raw", var0},
            {"d=raw", "d=" + var0},
            {"t=raw", "t=" + var0}
         }
         : new String[][]{
            {"as pasted", var0},
            {"without prefix", var0.substring(2)}
         };
   }

   private static String describeThrowable(Throwable var0) {
      if (var0 == null) {
         return "unknown error";
      } else {
         String var1 = var0.getMessage();
         if (var1 == null || var1.trim().length() == 0) {
            var1 = var0.getClass().getSimpleName();
         }

         return var1 != null && var1.trim().length() != 0 ? truncateMessage(var1.trim()) : "unknown error";
      }
   }

   private static String truncateMessage(String var0) {
      if (var0 == null) {
         return "";
      } else {
         return var0.length() > 220 ? var0.substring(0, 217) + "..." : var0;
      }
   }

   private static String vvGm(JsonObject var0, String var1) {
      if (var0.has("error_description")) {
         return var0.get("error_description").getAsString();
      } else if (var0.has("errorMessage")) {
         return var0.get("errorMessage").getAsString();
      } else if (var0.has("message")) {
         return var0.get("message").getAsString();
      } else if (var0.has("XErr")) {
         return var1
            + " (XErr "
            + var0.get("XErr").getAsString()
            + ")";
      } else {
         return var0.has("error")
            ? var1 + ": " + var0.get("error").getAsString()
            : var1;
      }
   }

   public static Account HJJh(Account var0, String var1, String var2, AtomicReference<String> var3) throws Exception {
      if (var0 != null && var0.qRa8673() != null && var0.qRa8673().length() != 0) {
         String var4 = var0.qRa8673();
         if (var1 != null && var1.trim().length() > 0 && !var1.trim().equalsIgnoreCase(var0.zYgb())) {
            var3.set("Changing name...");
            String var5 = httpPut(
               "https://api.minecraftservices.com/minecraft/profile/name/"
                  + URLEncoder.encode(var1.trim(), "UTF-8"),
               "",
               "application/json",
               var4
            );
            JsonObject var6 = parseJsonObject(var5);
            if (var6.has("error")
               || var6.has("message")
               || var6.has("errorMessage")) {
               throw new IOException(vvGm(var6, "Name change failed"));
            }
         }

         if (var2 != null && var2.trim().length() > 0) {
            var3.set("Finding skin...");
            String var9 = findSkinUrl(var2.trim());
            if (var9 == null || var9.length() == 0) {
               throw new IOException("Could not find a skin for " + var2.trim());
            }

            var3.set("Changing skin...");
            String var11 = "{\"variant\":\"classic\",\"url\":\""
               + Gylx(var9)
               + "\"}";
            String var7 = httpPost(
               "https://api.minecraftservices.com/minecraft/profile/skins",
               var11,
               "application/json",
               var4
            );
            JsonObject var8 = parseJsonObject(var7);
            if (var8.has("error")
               || var8.has("message")
               || var8.has("errorMessage")) {
               throw new IOException(vvGm(var8, "Skin change failed"));
            }
         }

         var3.set("Refreshing profile...");
         String var10 = httpGet("https://api.minecraftservices.com/minecraft/profile", var4);
         JsonObject var12 = parseJsonObject(var10);
         if (var12.has("name") && var12.has("id")) {
            var0.setUsername(var12.get("name").getAsString());
            var0.setUuid(var12.get("id").getAsString());
            return var0;
         } else {
            throw new IOException(vvGm(var12, "Could not refresh profile"));
         }
      } else {
         throw new IOException("No access token saved for this account");
      }
   }

   private static String findSkinUrl(String var0) throws Exception {
      String var1 = httpGet(
         "https://api.mojang.com/users/profiles/minecraft/"
            + URLEncoder.encode(var0, "UTF-8"),
         null
      );
      JsonObject var2 = parseJsonObject(var1);
      if (!var2.has("id")) {
         return "";
      } else {
         String var3 = httpGet(
            "https://sessionserver.mojang.com/session/minecraft/profile/"
               + var2.get("id").getAsString(),
            null
         );
         JsonObject var4 = parseJsonObject(var3);
         if (!var4.has("properties")) {
            return "";
         } else {
            JsonArray var5 = var4.getAsJsonArray("properties");

            for (int var6 = 0; var6 < var5.size(); var6++) {
               JsonObject var7 = var5.get(var6).getAsJsonObject();
               if (var7.has("name")
                  && "textures"
                     .equals(var7.get("name").getAsString())
                  && var7.has("value")) {
                  String var8 = new String(
                     Base64.getDecoder().decode(var7.get("value").getAsString()),
                     "UTF-8"
                  );
                  JsonObject var9 = parseJsonObject(var8);
                  if (var9.has("textures")
                     && var9.getAsJsonObject("textures")
                        .has("SKIN")) {
                     JsonObject var10 = var9.getAsJsonObject("textures")
                        .getAsJsonObject("SKIN");
                     if (var10.has("url")) {
                        return var10.get("url").getAsString();
                     }
                  }
               }
            }

            return "";
         }
      }
   }

   private static JsonObject parseJsonObject(String var0) {
      return var0 != null && var0.trim().length() != 0 ? new JsonParser().parse(var0).getAsJsonObject() : new JsonObject();
   }

   private static String Gylx(String var0) {
      return var0.replace("\\", "\\\\")
         .replace("\"", "\\\"");
   }

   private static String httpPost(String var0, String var1, String var2, String var3) throws Exception {
      HttpURLConnection var4 = (HttpURLConnection)new URL(var0).openConnection();
      var4.setRequestMethod("POST");
      var4.setRequestProperty("Content-Type", var2);
      var4.setRequestProperty("Accept", "application/json");
      if (var0.indexOf("xboxlive.com") >= 0) {
         var4.setRequestProperty("x-xbl-contract-version", "1");
      }

      if (var3 != null) {
         var4.setRequestProperty(
            "Authorization", "Bearer " + var3
         );
      }

      var4.setDoOutput(true);
      var4.setConnectTimeout(15000);
      var4.setReadTimeout(15000);

      try (OutputStream var5 = var4.getOutputStream()) {
         var5.write(var1.getBytes("UTF-8"));
      }

      InputStream var36;
      try {
         var36 = var4.getInputStream();
      } catch (IOException var31) {
         var36 = var4.getErrorStream();
      }

      StringBuilder var37 = new StringBuilder();

      String var9;
      try (BufferedReader var7 = new BufferedReader(new InputStreamReader(var36, "UTF-8"))) {
         while ((var9 = var7.readLine()) != null) {
            var37.append(var9);
         }
      }

      return var37.toString();
   }

   private static String httpPut(String var0, String var1, String var2, String var3) throws Exception {
      HttpURLConnection var4 = (HttpURLConnection)new URL(var0).openConnection();
      var4.setRequestMethod("PUT");
      var4.setRequestProperty("Content-Type", var2);
      var4.setRequestProperty("Accept", "application/json");
      if (var3 != null) {
         var4.setRequestProperty(
            "Authorization", "Bearer " + var3
         );
      }

      var4.setConnectTimeout(15000);
      var4.setReadTimeout(15000);
      if (var1 != null && var1.length() > 0) {
         var4.setDoOutput(true);

         try (OutputStream var5 = var4.getOutputStream()) {
            var5.write(var1.getBytes("UTF-8"));
         }
      }

      InputStream var36;
      try {
         var36 = var4.getInputStream();
      } catch (IOException var31) {
         var36 = var4.getErrorStream();
      }

      StringBuilder var37 = new StringBuilder();

      String var9;
      try (BufferedReader var7 = new BufferedReader(new InputStreamReader(var36, "UTF-8"))) {
         while ((var9 = var7.readLine()) != null) {
            var37.append(var9);
         }
      }

      return var37.toString();
   }

   private static String httpGet(String var0, String var1) throws Exception {
      HttpURLConnection var2 = (HttpURLConnection)new URL(var0).openConnection();
      var2.setRequestMethod("GET");
      if (var1 != null) {
         var2.setRequestProperty(
            "Authorization", "Bearer " + var1
         );
      }

      var2.setRequestProperty("Accept", "application/json");
      var2.setConnectTimeout(15000);
      var2.setReadTimeout(15000);

      InputStream var3;
      try {
         var3 = var2.getInputStream();
      } catch (IOException var16) {
         var3 = var2.getErrorStream();
      }

      StringBuilder var4 = new StringBuilder();

      String var7;
      try (BufferedReader var5 = new BufferedReader(new InputStreamReader(var3, "UTF-8"))) {
         while ((var7 = var5.readLine()) != null) {
            var4.append(var7);
         }
      }

      return var4.toString();
   }

   public static Account loginWithMinecraftToken(String var0, AtomicReference var1) throws Exception {
      return createAccountFromToken(var0, var1);
   }

   public static Account validateTokenAndGetProfile(String var0, AtomicReference var1, boolean var2) throws Exception {
      return fetchProfileAndCreateAccount(var0, var1, var2);
   }

   public static String[][] IYvq(String var0) {
      return buildTokenVariants(var0);
   }

   public static Account refreshMicrosoftV2Token(String var0, boolean var1, boolean var2, LoginMethod var3, AtomicReference var4, boolean var5) throws Exception {
      return JELVPwr(var0, var1, var2, var3, var4, var5);
   }

   public static Account PYMcS(String var0, StringBuilder var1, AtomicReference var2, MicrosoftLogin$11 var3) {
      return tryLoginStep(var0, var1, var2, var3);
   }

   public static Account loginWithTokenPrefix(String var0, String var1, LoginMethod var2, AtomicReference var3, boolean var4) throws Exception {
      return TQYxs(var0, var1, var2, var3, var4);
   }

   public static String bygRd0(String var0) {
      return truncateMessage(var0);
   }

   public static String formatThrowable(Throwable var0) {
      return describeThrowable(var0);
   }

   public static Account refreshLiveToken(String var0, AtomicReference var1, boolean var2) throws Exception {
      return UiosN(var0, var1, var2);
   }

   public static HttpServer Wvoo(HttpServer var0) {
      httpServer = var0;
      return var0;
   }

   public static CountDownLatch Eeln(CountDownLatch var0) {
      countDownLatch = var0;
      return var0;
   }

   public static boolean IqBe4(String var0) {
      return openLoginPageForState(var0);
   }

   public static URI getAuthorizeUri(String var0) throws UnsupportedEncodingException, URISyntaxException {
      return buildAuthorizeUri(var0);
   }

   public static Account loginWithAuthCode(String var0, AtomicReference var1) throws Exception {
      return QKwpiNe(var0, var1);
   }

   public static HttpServer getCallbackServer() {
      return httpServer;
   }

   public static void handleOAuthCallback(HttpExchange var0, String var1, AtomicReference var2, AtomicReference var3, CountDownLatch var4) throws IOException {
      cbYnoE(var0, var1, var2, var3, var4);
   }

   public static String postForm(String var0, String var1, String var2, String var3) throws Exception {
      return httpPost(var0, var1, var2, var3);
   }

   public static Account loginWithRefreshedTokens(String var0, String var1, AtomicReference var2, boolean var3) throws Exception {
      return loginWithMicrosoftTokens(var0, var1, var2, var3);
   }
}
