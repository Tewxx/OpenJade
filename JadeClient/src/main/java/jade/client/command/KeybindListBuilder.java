// Jade recovery: original class: jade.deps.eLz.ZpANaX3
package jade.client.command;

import jade.client.Jade;
import jade.client.common.ConfigEntry;
import jade.client.module.Module;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class KeybindListBuilder {
   public Map<String, List<String>> buildKeybindMap(int var1) {
      HashMap var2 = new HashMap();

      for (Module var4 : Jade.getModuleManager().getModules()) {
         this.HOrk(var2, var4, var1);
      }

      for (ConfigEntry var6 : Jade.configManager.profiles) {
         this.HOrk(var2, var6.getProfile(), var1);
      }

      return var2;
   }

   public int yMmw(Map<String, List<String>> var1) {
      int var2 = 0;

      for (List var4 : var1.values()) {
         var2 += var4.size();
      }

      return var2;
   }

   private void HOrk(Map<String, List<String>> var1, Module var2, int var3) {
      int var4 = var2.getKeycode();
      if (var4 != 0 && (var3 == 0 || var3 == var4)) {
         String var5 = KeyNames.getKeyName(var4);
         List<String> var6 = var1.get(var5);
         if (var6 == null) {
            var6 = new ArrayList();
            var1.put(var5, var6);
         }

         var6.add(var2.getName());
      }
   }
}
