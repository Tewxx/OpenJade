// Jade recovery: original class: jade.deps.eLz.TxtBJj
package jade.client.command;

import java.util.Locale;
import org.lwjgl.input.Keyboard;

public final class KeyNames {
   public static final int SGkOb = -1;

   private KeyNames() {
   }

   public static int parseKeyCode(String var0) {
      if (var0 != null && !var0.trim().isEmpty()) {
         String var1 = var0.trim().toUpperCase(Locale.ROOT);
         if (!var1.equals("NONE") && !var1.equals("UNBOUND") && !var1.equals("KEY_NONE") && !var1.equals("0")) {
            int var2 = Keyboard.getKeyIndex(var1);
            return var2 == 0 ? -1 : var2;
         } else {
            return 0;
         }
      } else {
         return -1;
      }
   }

   public static String getKeyName(int var0) {
      if (var0 >= 1000) {
         return "M" + (var0 - 1000);
      } else {
         return var0 == 0 ? "NONE" : Keyboard.getKeyName(var0);
      }
   }
}
