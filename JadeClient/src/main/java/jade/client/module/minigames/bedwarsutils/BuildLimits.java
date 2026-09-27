// Jade recovery: original class: jade.deps.eLz.x0eniEcMi
package jade.client.module.minigames.bedwarsutils;

import jade.deps.loader107.CoreResourceIndex;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class BuildLimits {
   private static final String MSEg = "/assets/jade/data/bedwars_build_limits.tsv";
   private static final Map<String, BuildLimits$0> buildLimitsByMapKey = KoyvGr();

   private BuildLimits() {
   }

   public static BuildLimits$0 getForMapName(String var0) {
      return var0 == null ? null : buildLimitsByMapKey.get(toLookupKey(var0));
   }

   public static String normalizeMapName(String var0) {
      if (var0 == null) {
         return "";
      } else {
         String var1 = var0.trim();
         if (var1.regionMatches(true, 0, "NEW ", 0, 4)) {
            var1 = var1.substring(4).trim();
         }

         int var2 = var1.indexOf(40);
         if (var2 > 0) {
            var1 = var1.substring(0, var2).trim();
         }

         return var1;
      }
   }

   private static Map<String, BuildLimits$0> KoyvGr() {
      InputStream var0 = CoreResourceIndex.openResource("/assets/jade/data/bedwars_build_limits.tsv");
      if (var0 == null) {
         return Collections.emptyMap();
      } else {
         HashMap var1 = new HashMap();
         BufferedReader var2 = new BufferedReader(new InputStreamReader(var0, StandardCharsets.UTF_8));

         Map var4;
         try {
            String var3;
            while ((var3 = var2.readLine()) != null) {
               if (var3.length() != 0 && var3.charAt(0) != '#') {
                  String[] var19 = var3.split("\\t");
                  if (var19.length >= 3) {
                     try {
                        int var5 = Integer.parseInt(var19[1].trim());
                        int var6 = Integer.parseInt(var19[2].trim());
                        if (var5 < var6) {
                           putWithAliases(var1, var19[0], new BuildLimits$0(var5, var6));
                        }
                     } catch (NumberFormatException var16) {
                     }
                  }
               }
            }

            return Collections.unmodifiableMap(var1);
         } catch (IOException var17) {
            var4 = Collections.emptyMap();
         } finally {
            try {
               var2.close();
            } catch (IOException var15) {
            }
         }

         return var4;
      }
   }

   private static void putWithAliases(Map<String, BuildLimits$0> var0, String var1, BuildLimits$0 var2) {
      String var3 = normalizeMapName(var1);
      String var4 = toLookupKey(var3);
      if (var4.length() > 0) {
         var0.put(var4, var2);
      }

      String var5 = toLookupKey(var1);
      if (var5.length() > 0) {
         var0.put(var5, var2);
      }
   }

   private static String toLookupKey(String var0) {
      String var1 = var0.toLowerCase(Locale.ROOT).trim();
      StringBuilder var2 = new StringBuilder(var1.length());

      for (int var3 = 0; var3 < var1.length(); var3++) {
         char var4 = var1.charAt(var3);
         if (Character.isLetterOrDigit(var4)) {
            var2.append(var4);
         }
      }

      return var2.toString();
   }
}
