// Jade recovery: original class: jade.deps.eLz.SaacHQv3i
package jade.client.common;

import java.util.Map;
import net.minecraft.client.settings.KeyBinding;

public final class KeybindRegistry {
   private KeybindRegistry() {
   }

   public static void registerKeybindings(KeyBinding[] var0, Map<String, KeyBinding> var1) {
      for (KeyBinding var5 : var0) {
         var1.put(stripKeyPrefix(var5.getKeyDescription()), var5);
      }
   }

   private static String stripKeyPrefix(String var0) {
      return var0.replaceFirst("key\\.", "");
   }
}
