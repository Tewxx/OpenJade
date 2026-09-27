// Jade recovery: original class: jade.deps.eLz.WRPbpeNtpl
package jade.client.command;

import java.util.Arrays;

public final class CommandAliases {
   private final String primaryName;
   private final String[] aliasNames;

   public CommandAliases(String var1, String[] var2) {
      this.primaryName = var1;
      this.aliasNames = var2 != null && var2.length > 0 ? var2 : new String[]{var1};
   }

   public String Ktja() {
      return this.primaryName;
   }

   public String[] getAliasNames() {
      return (String[])this.aliasNames.clone();
   }

   public boolean matches(String var1) {
      if (var1 == null) {
         return false;
      } else {
         return this.primaryName.equalsIgnoreCase(var1) ? true : Arrays.stream(this.aliasNames).anyMatch((recoveredArg0) -> CommandAliases.aliasEqualsIgnoreCase(var1, (java.lang.String) recoveredArg0));
      }
   }

   private static boolean aliasEqualsIgnoreCase(String var0, String var1) {
      return var1.equalsIgnoreCase(var0);
   }
}
