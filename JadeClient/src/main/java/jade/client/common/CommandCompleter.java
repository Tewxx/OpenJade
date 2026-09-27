// Jade recovery: original class: jade.deps.eLz.xf0bH3f3rG
package jade.client.common;

import jade.client.command.Command;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public final class CommandCompleter {
   private CommandCompleter() {
   }

   public static String[] completeCommandNames(List<Command> var0, String var1, Supplier<String> var2) {
      String var3 = var1 == null ? "" : var1.toLowerCase();
      LinkedHashSet var4 = new LinkedHashSet();

      for (Command var6 : var0) {
         addMatchingName(var4, var6.getName(), var3, var2);

         for (String var10 : var6.getAliases()) {
            addMatchingName(var4, var10, var3, var2);
         }
      }

      return (String[]) var4.toArray(new String[0]);
   }

   private static void addMatchingName(Set<String> var0, String var1, String var2, Supplier<String> var3) {
      if (var1.toLowerCase().startsWith(var2)) {
         var0.add((String)var3.get() + var1);
      }
   }

   public static String[] buildCommandCompletions(Command var0, CommandInput var1, List<String> var2, Supplier<String> var3) {
      if (var2 != null && !var2.isEmpty()) {
         int var4 = Math.max(0, Math.min(var1.getArgumentCount(), var0.getArgumentOffset(var1)));
         LinkedHashSet var5 = new LinkedHashSet();

         for (String var7 : var2) {
            if (var7 != null && var7.length() != 0) {
               StringBuilder var8 = new StringBuilder((String)var3.get()).append(var1.getCommandName());

               for (int var9 = 0; var9 < var4; var9++) {
                  var8.append(' ').append(var1.getArgument(var9));
               }

               if (var8.length() != 0) {
                  var8.append(' ');
               }

               var5.add(var8.append(var7).toString());
            }
         }

         return (String[]) var5.toArray(new String[0]);
      } else {
         return new String[0];
      }
   }

   public static List<String> filterCompletions(CommandInput var0, List<String> var1) {
      String var2 = var0.getArgumentCount() == 0 ? "" : var0.getArgument(var0.getArgumentCount() - 1);
      ArrayList var3 = new ArrayList();

      for (String var5 : var1) {
         if (var2 == null || var2.length() == 0 || var5.toLowerCase().startsWith(var2.toLowerCase())) {
            var3.add(var5);
         }
      }

      return var3;
   }
}
