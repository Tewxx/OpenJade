// Jade recovery: original class: jade.deps.eLz.l89YH0S
package jade.client.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class CommandInput {
   private final String rawInput;
   private final String FoQ;
   private final List<String> arguments;

   public CommandInput(String var1, String var2, String[] var3) {
      this.rawInput = var1 == null ? "" : var1;
      this.FoQ = var2 == null ? "" : var2;
      this.arguments = (List<String>)(var3 == null ? Collections.emptyList() : new ArrayList<>(Arrays.asList(var3)));
   }

   public String getRawInput() {
      return this.rawInput;
   }

   public String getCommandName() {
      return this.FoQ;
   }

   public int getArgumentCount() {
      return this.arguments.size();
   }

   public String[] yeqp() {
      return (String[]) this.arguments.toArray(new String[0]);
   }

   public String getArgument(int var1) {
      return var1 >= 0 && var1 < this.arguments.size() ? this.arguments.get(var1) : null;
   }

   public String joinArgumentsFrom(int var1) {
      if (var1 >= 0 && var1 < this.arguments.size()) {
         int var2 = var1;

         while (var2 < this.arguments.size() && "".equals(this.arguments.get(var2))) {
            var2++;
         }

         return String.join(" ", this.arguments.subList(var2, this.arguments.size()));
      } else {
         return "";
      }
   }
}
