// Jade recovery: original class: jade.deps.eLz.ttBZ4vk
package jade.client.common;

import java.util.Arrays;
import java.util.regex.Pattern;

public final class FuzzyNameMatcher {
   private static final Pattern pattern = Pattern.compile("\\s+");
   public final boolean iqpvb;
   private final String o83;

   public FuzzyNameMatcher(String var1) {
      this.iqpvb = var1 != null && !var1.trim().isEmpty();
      this.o83 = fvmKg(var1);
   }

   public int score(String var1, String var2) {
      return Math.max(similarityScore(this.o83, var1), similarityScore(this.o83, var2));
   }

   public static String fvmKg(String var0) {
      if (var0 == null) {
         return "";
      } else {
         String var1 = var0.toLowerCase().trim();
         String var2 = var1.startsWith("minecraft:") ? var1.substring(10) : var1;
         return pattern.matcher(var2.replace('_', ' ').replace('-', ' ').replace(':', ' ')).replaceAll(" ").trim();
      }
   }

   public static int similarityScore(String var0, String var1) {
      String var2 = fvmKg(var1);
      if (!var0.isEmpty() && !var2.isEmpty()) {
         if (var0.equals(var2)) {
            return 1000;
         } else if (var2.startsWith(var0)) {
            return 850;
         } else {
            String[] var3 = var2.split(" ");
            boolean var4 = Arrays.stream(var0.split(" ")).allMatch((recoveredArg0) -> FuzzyNameMatcher.ktuca(var3, (java.lang.String) recoveredArg0));
            if (var4) {
               return 780;
            } else if (var2.contains(var0)) {
               return 650;
            } else {
               String var5 = var2.replace(" ", "");
               String var6 = var0.replace(" ", "");
               if (var5.equals(var6)) {
                  return 900;
               } else if (var5.startsWith(var6)) {
                  return 720;
               } else if (var5.contains(var6)) {
                  return 560;
               } else if (var6.length() < 2) {
                  return 0;
               } else {
                  int var9 = 0;

                  for (int var8 = 0; var8 < var6.length(); var8++) {
                     var9 = var5.indexOf(var6.charAt(var8), var9);
                     if (var9 < 0) {
                        return 0;
                     }

                     var9++;
                  }

                  return 360;
               }
            }
         }
      } else {
         return 0;
      }
   }

   private static boolean ktuca(String[] var0, String var1) {
      return !var1.isEmpty() && Arrays.stream(var0).anyMatch((recoveredArg0) -> FuzzyNameMatcher.EVOp(var1, (java.lang.String) recoveredArg0));
   }

   private static boolean EVOp(String var0, String var1) {
      return var1.startsWith(var0);
   }
}
