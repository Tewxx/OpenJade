// Jade recovery: original class: jade.deps.eLz.uR5CTiqN
package jade.client.core;

import jade.client.common.CommandInput;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommandLineParser {
   private static final Pattern pattern = Pattern.compile("\\P{javaWhitespace}+");

   private CommandLineParser() {
   }

   public static CommandInput parse(String var0, String var1, boolean var2) {
      String var3 = var0.substring(var1.length());
      Matcher var4 = pattern.matcher(var3);
      if (!var4.find()) {
         return null;
      } else {
         String var5 = var4.group();
         ArrayList var6 = new ArrayList();

         while (var4.find()) {
            var6.add(var4.group());
         }

         if (var2 && endsWithWhitespace(var3)) {
            var6.add("");
         }

         return new CommandInput(var0, var5, (String[]) var6.toArray(new String[0]));
      }
   }

   public static boolean endsWithWhitespace(String var0) {
      return var0.length() != 0 && Character.isWhitespace(var0.charAt(var0.length() - 1));
   }
}
