// Jade recovery: original class: jade.deps.eLz.i61yN2RE
package jade.client.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ConfigSubcommand {
   private static final List<String> CAp = Collections.unmodifiableList(
      Arrays.asList("list", "save", "s", "load", "l", "delete", "remove", "r", "rename", "startup", "defaultconfig")
   );

   private ConfigSubcommand() {
   }

   public static ConfigSubcommand$0 parseSubcommand(String var0) {
      if (var0 == null) {
         return ConfigSubcommand$0.UNKNOWN;
      } else {
         String var1 = var0.toLowerCase();
         if (var1.equals("list") || var1.equals("ls")) {
            return ConfigSubcommand$0.LIST;
         } else if (var1.equals("save") || var1.equals("s")) {
            return ConfigSubcommand$0.SAVE;
         } else if (var1.equals("load") || var1.equals("l")) {
            return ConfigSubcommand$0.LOAD;
         } else if (var1.equals("delete") || var1.equals("remove") || var1.equals("r")) {
            return ConfigSubcommand$0.DELETE;
         } else if (var1.equals("rename")) {
            return ConfigSubcommand$0.RENAME;
         } else {
            return !var1.equals("startup") && !var1.equals("defaultconfig") ? ConfigSubcommand$0.UNKNOWN : ConfigSubcommand$0.STARTUP;
         }
      }
   }

   public static List<String> getSubcommandNames() {
      return CAp;
   }

   public static boolean acceptsConfigName(ConfigSubcommand$0 var0, int var1) {
      return var0 != ConfigSubcommand$0.LOAD && var0 != ConfigSubcommand$0.DELETE ? var1 == 2 && (var0 == ConfigSubcommand$0.RENAME || var0 == ConfigSubcommand$0.STARTUP) : true;
   }

   public static boolean takesConfigNameArgument(ConfigSubcommand$0 var0) {
      return var0 == ConfigSubcommand$0.LOAD || var0 == ConfigSubcommand$0.DELETE || var0 == ConfigSubcommand$0.RENAME || var0 == ConfigSubcommand$0.STARTUP;
   }
}
