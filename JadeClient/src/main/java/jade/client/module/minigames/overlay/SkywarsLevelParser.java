// Jade recovery: original class: jade.deps.eLz.FtFmRV
package jade.client.module.minigames.overlay;

public final class SkywarsLevelParser {
   public static final String LEVEL_BEGIN_MARKER = "__JADE_SW_LEVEL_BEGIN__";
   public static final String LEVEL_END_MARKER = "__JADE_SW_LEVEL_END__";

   private SkywarsLevelParser() {
   }

   public static String Sg45(String var0) {
      if (var0 == null) {
         return "";
      } else {
         int var1 = var0.indexOf("__JADE_SW_LEVEL_BEGIN__");
         if (var1 < 0) {
            return "";
         } else {
            var1 += "__JADE_SW_LEVEL_BEGIN__".length();
            int var2 = var0.indexOf("__JADE_SW_LEVEL_END__", var1);
            if (var2 < var1) {
               return "";
            } else {
               String var3 = var0.substring(var1, var2);
               int var4 = var3.length();

               while (var4 > 0 && Character.isWhitespace(var3.charAt(var4 - 1))) {
                  var4--;
               }

               if (var4 >= 2 && var3.charAt(var4 - 2) == 167 && Character.toLowerCase(var3.charAt(var4 - 1)) == 'r') {
                  var4 -= 2;
               }

               return var3.substring(0, var4);
            }
         }
      }
   }
}
