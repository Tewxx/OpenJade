// Jade recovery: original class: jade.deps.eLz.BMK6I1Py
package jade.client.module.minigames.overlay;

import jade.client.Jade;
import jade.deps.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class StatsFetcher {
   private static final long CACHE_TTL_MILLIS = 900000L;
   private static final long vad = 12000L;
   private static final int CONNECT_TIMEOUT_MILLIS = 4000;
   private static final int READ_TIMEOUT_MILLIS = 6000;
   private static final StatsFetcher statsFetcher = new StatsFetcher();
   private final Map<String, StatsFetcher$1> statsCache = new ConcurrentHashMap<>();
   private final Map<String, Long> lastRequestMillis = new ConcurrentHashMap<>();
   private final Set<String> inFlightRequests = Collections.newSetFromMap(new ConcurrentHashMap<>());

   private StatsFetcher() {
   }

   public static StatsFetcher getInstance() {
      byte var0 = 0;

      while (true) {
         switch (var0) {
            case 0:

               var0 = 1;
               break;
            case 1:
               var0 = 2;
               break;
            default:
               return statsFetcher;
         }
      }
   }

   public StatsFetcher$2 findAnyCachedStats(UUID var1) {
      byte var8 = 0;

      while (true) {
         switch (var8) {
            case 0:

               var8 = 1;
               break;
            case 1:
               var8 = 2;
               break;
            default:
               if (var1 == null) {
                  return null;
               } else {
                  String var2 = var1.toString() + "|";
                  long var3 = System.currentTimeMillis();

                  for (Entry var6 : this.statsCache.entrySet()) {
                     StatsFetcher$1 var7 = (StatsFetcher$1)var6.getValue();
                     if (((String)var6.getKey()).startsWith(var2) && StatsFetcher$1.getCachedAtMillis(var7) > var3) {
                        return StatsFetcher$1.JIPpa(var7);
                     }
                  }

                  return null;
               }
         }
      }
   }

   public StatsFetcher$2 getCachedStats(UUID var1, String var2, String var3, String var4) {
      byte var7 = 0;

      while (true) {
         switch (var7) {
            case 0:

               var7 = 1;
               break;
            case 1:
               var7 = 2;
               break;
            default:
               if (var1 != null && var4 != null && !var4.trim().isEmpty()) {
                  String var5 = buildVariantKey(var2, var3, var4);
                  StatsFetcher$1 var6 = this.statsCache.get(buildCacheKey(var1, var5));
                  return var6 != null && StatsFetcher$1.getCachedAtMillis(var6) > System.currentTimeMillis() ? StatsFetcher$1.JIPpa(var6) : null;
               } else {
                  return null;
               }
         }
      }
   }

   public void requestStats(final UUID var1, final String var2, final String var3, final String var4) {
      byte var11 = 0;

      while (true) {
         switch (var11) {
            case 0:

               var11 = 1;
               break;
            case 1:
               var11 = 2;
               break;
            default:
               if (var1 != null && var4 != null && !var4.trim().isEmpty()) {
                  String var5 = buildVariantKey(var2, var3, var4);
                  final String var6 = buildCacheKey(var1, var5);
                  long var7 = System.currentTimeMillis();
                  StatsFetcher$1 var9 = this.statsCache.get(var6);
                  if (var9 != null && StatsFetcher$1.getCachedAtMillis(var9) > var7) {
                     return;
                  } else {
                     Long var10 = this.lastRequestMillis.get(var6);
                     if (var10 != null && var7 - var10 < 12000L) {
                        return;
                     } else if (!this.inFlightRequests.add(var6)) {
                        return;
                     } else {
                        this.lastRequestMillis.put(var6, var7);
                        Jade.getExecutor().execute(new Runnable() {
                           @Override
                           public void run() {

                              try {
                                 String var1x = StatsFetcher.buildRequestUrl(var4, var1, var2, var3);
                                 String var2x = StatsFetcher.fetchUrl(var1x);
                                 StatsFetcher$2 var3x = StatsFetcher$2.parseStatsJson(var2x);
                                 StatsFetcher.getStatsCache(StatsFetcher.this).put(var6, new StatsFetcher$1(var3x, System.currentTimeMillis() + 900000L));
                              } catch (Exception var7x) {
                              } finally {
                                 StatsFetcher.QqkiZu(StatsFetcher.this).remove(var6);
                              }
                           }
                        });
                        return;
                     }
                  }
               } else {
                  return;
               }
         }
      }
   }

   public void clearRequestState() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               this.lastRequestMillis.clear();
               this.inFlightRequests.clear();
               return;
         }
      }
   }

   private static String buildCacheKey(UUID var0, String var1) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               return var0.toString() + "|" + var1;
         }
      }
   }

   private static String httpGet(String var0) throws Exception {
      HttpURLConnection var1 = null;

      String var6;
      try {
         URL var2 = new URL(var0);
         var1 = (HttpURLConnection)var2.openConnection();
         var1.setRequestMethod("GET");
         var1.setConnectTimeout(4000);
         var1.setReadTimeout(6000);
         var1.setRequestProperty("Accept", "application/json");
         var1.setRequestProperty("Accept-Encoding", "identity");
         BufferedReader var3 = new BufferedReader(
            new InputStreamReader(
               var1.getResponseCode() >= 200 && var1.getResponseCode() < 400 ? var1.getInputStream() : var1.getErrorStream(), StandardCharsets.UTF_8
            )
         );

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
      } finally {
         if (var1 != null) {
            var1.disconnect();
         }
      }

      return var6;
   }

   private static String resolveUrlTemplate(String var0, UUID var1, String var2, String var3) throws Exception {
      byte var8 = 0;

      while (true) {
         switch (var8) {
            case 0:

               var8 = 1;
               break;
            case 1:
               var8 = 2;
               break;
            default:
               String var4 = var1.toString();
               String var5 = var2 == null ? "" : var2;
               String var6 = var3 != null && !var3.isEmpty() ? var3 : "GAME";
               boolean var7 = var0.contains("{{id}}")
                  || var0.contains("{{name}}")
                  || var0.contains("{{sources}}");
               return var7
                  ? var0.replace("{{id}}", urlEncode(var4))
                     .replace("{{name}}", urlEncode(var5))
                     .replace("{{sources}}", urlEncode(var6))
                  : setQueryParam(
                     setQueryParam(var0, "id", var4), "name", var5
                  );
         }
      }
   }

   private static String setQueryParam(String var0, String var1, String var2) throws Exception {
      byte var12 = 0;

      while (true) {
         switch (var12) {
            case 0:

               var12 = 1;
               break;
            case 1:
               var12 = 2;
               break;
            default:
               String var3 = var1 + "=" + urlEncode(var2);
               int var4 = var0.indexOf(63);
               if (var4 < 0) {
                  return var0 + "?" + var3;
               } else {
                  String var5 = var0.substring(0, var4 + 1);
                  String[] var6 = var0.substring(var4 + 1).split("&");
                  StringBuilder var7 = new StringBuilder();
                  boolean var8 = false;

                  for (int var9 = 0; var9 < var6.length; var9++) {
                     if (!var6[var9].isEmpty()) {
                        String var10 = var6[var9];
                        int var11 = var10.indexOf(61);
                        if (var11 >= 0) {
                           var10 = var10.substring(0, var11);
                        }

                        if (var1.equals(var10)) {
                           appendQueryParam(var7, var3);
                           var8 = true;
                        } else {
                           appendQueryParam(var7, var6[var9]);
                        }
                     }
                  }

                  if (!var8) {
                     appendQueryParam(var7, var3);
                  }

                  return var5 + var7.toString();
               }
         }
      }
   }

   private static void appendQueryParam(StringBuilder var0, String var1) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               if (var0.length() > 0) {
                  var0.append('&');
               }

               var0.append(var1);
               return;
         }
      }
   }

   private static String urlEncode(String var0) throws Exception {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return URLEncoder.encode(var0 == null ? "" : var0, "UTF-8")
                  .replace("+", "%20");
         }
      }
   }

   private static String buildVariantKey(String var0, String var1, String var2) {
      byte var3 = 0;

      while (true) {
         switch (var3) {
            case 0:

               var3 = 1;
               break;
            case 1:
               var3 = 2;
               break;
            default:
               return (var0 == null ? "" : var0)
                  + "|"
                  + (var1 == null ? "" : var1)
                  + "|"
                  + var2.trim();
         }
      }
   }

   private static int LOkw0(JsonObject var0, String var1, int var2) {
      if (var0 != null && var0.has(var1) && var0.get(var1).isJsonPrimitive()) {
         try {
            return 0xFF000000 | var0.get(var1).getAsInt() & 16777215;
         } catch (Exception var4) {
         }
      }

      return var2;
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

   private static double dEg4(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && var0.get(var1).isJsonPrimitive()) {
         try {
            return var0.get(var1).getAsDouble();
         } catch (Exception var3) {
         }
      }

      return Double.NaN;
   }

   public static String buildRequestUrl(String var0, UUID var1, String var2, String var3) throws Exception {
      byte var4 = 0;

      while (true) {
         switch (var4) {
            case 0:

               var4 = 1;
               break;
            case 1:
               var4 = 2;
               break;
            default:
               return resolveUrlTemplate(var0, var1, var2, var3);
         }
      }
   }

   public static String fetchUrl(String var0) throws Exception {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return httpGet(var0);
         }
      }
   }

   public static Map getStatsCache(StatsFetcher var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return var0.statsCache;
         }
      }
   }

   public static Set QqkiZu(StatsFetcher var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return var0.inFlightRequests;
         }
      }
   }

   public static double getJsonDouble(JsonObject var0, String var1) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               return dEg4(var0, var1);
         }
      }
   }

   public static String getJsonString(JsonObject var0, String var1) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               return readJsonString(var0, var1);
         }
      }
   }

   public static int getJsonColor(JsonObject var0, String var1, int var2) {
      byte var3 = 0;

      while (true) {
         switch (var3) {
            case 0:

               var3 = 1;
               break;
            case 1:
               var3 = 2;
               break;
            default:
               return LOkw0(var0, var1, var2);
         }
      }
   }

   static {
   }
}
