// Jade recovery: original class: jade.deps.eLz.ixYwpvRzur
package jade.client.command;

import jade.client.Jade;
import jade.client.module.Module;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class ModuleNameCompleter {
   private ModuleNameCompleter() {
   }

   public static Module findModule(String var0) {
      return Jade.getModuleManager().getModuleByName(var0);
   }

   public static List<String> getMatchingModuleNames(String var0) {
      String var1 = normalizeModuleName(var0);
      ArrayList var2 = new ArrayList();

      for (Module var4 : Jade.getModuleManager().getModules()) {
         if (normalizeModuleName(var4.getName()).startsWith(var1)) {
            var2.add(var4.getName());
         }
      }

      return var2;
   }

   private static String normalizeModuleName(String var0) {
      return var0 == null ? "" : var0.replace(" ", "").toLowerCase(Locale.ROOT);
   }
}
