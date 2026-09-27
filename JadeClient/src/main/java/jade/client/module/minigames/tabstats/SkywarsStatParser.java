// Jade recovery: original class: jade.deps.eLz.KwepFo9ige
package jade.client.module.minigames.tabstats;

public final class SkywarsStatParser {
   public static final String KILLS_BEGIN_MARKER = "__JADE_SW_KILLS_BEGIN__";
   public static final String aDo = "__JADE_SW_KILLS_END__";
   public static final String WINS_BEGIN_MARKER = "__JADE_SW_WINS_BEGIN__";
   public static final String WINS_END_MARKER = "__JADE_SW_WINS_END__";
   private static final int oaNkw = 32;

   private SkywarsStatParser() {
   }

   public static double oZnyDae(String var0) {
      return parseNumberBetween(var0, "__JADE_SW_KILLS_BEGIN__", "__JADE_SW_KILLS_END__");
   }

   public static double parseWins(String var0) {
      return parseNumberBetween(var0, "__JADE_SW_WINS_BEGIN__", "__JADE_SW_WINS_END__");
   }

   private static double parseNumberBetween(String var0, String var1, String var2) {
      if (var0 == null) {
         return Double.NaN;
      } else {
         int var3 = var0.indexOf(var1);
         if (var3 < 0) {
            return Double.NaN;
         } else {
            var3 += var1.length();
            int var4 = var0.indexOf(var2, var3);
            if (var4 >= var3 && var4 - var3 <= 32) {
               String var5 = var0.substring(var3, var4);
               if (!var5.isEmpty() && var5.equals(var5.trim())) {
                  try {
                     double var6 = Double.parseDouble(var5);
                     return !Double.isNaN(var6) && !Double.isInfinite(var6) && var6 >= 0.0 ? var6 : Double.NaN;
                  } catch (NumberFormatException var8) {
                     return Double.NaN;
                  }
               } else {
                  return Double.NaN;
               }
            } else {
               return Double.NaN;
            }
         }
      }
   }
}
