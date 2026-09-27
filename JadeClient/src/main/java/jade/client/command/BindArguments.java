// Jade recovery: original class: jade.deps.eLz.nIjDf8
package jade.client.command;

import jade.client.common.CommandInput;
import java.util.Arrays;

public final class BindArguments {
   private final CommandInput Ydk5;

   public BindArguments(CommandInput var1) {
      this.Ydk5 = var1;
   }

   public boolean hasNoArguments() {
      return this.Ydk5.getArgumentCount() == 0;
   }

   public boolean isListRequest() {
      return !this.hasNoArguments() && "list".equalsIgnoreCase(this.Ydk5.getArgument(0));
   }

   public boolean hasMultipleArguments() {
      return this.Ydk5.getArgumentCount() >= 2;
   }

   public String getLastArgument() {
      return this.Ydk5.getArgument(this.Ydk5.getArgumentCount() - 1);
   }

   public String joinLeadingArguments() {
      StringBuilder var1 = new StringBuilder();

      for (int var2 = 0; var2 < this.Ydk5.getArgumentCount() - 1; var2++) {
         if (var1.length() != 0) {
            var1.append(' ');
         }

         var1.append(this.Ydk5.getArgument(var2));
      }

      return var1.toString();
   }

   public CommandInput toBindsArguments() {
      String[] var1 = this.Ydk5.yeqp();
      String[] var2 = var1.length < 2 ? new String[0] : Arrays.copyOfRange(var1, 1, var1.length);
      return new CommandInput(this.Ydk5.getRawInput(), "binds", var2);
   }
}
