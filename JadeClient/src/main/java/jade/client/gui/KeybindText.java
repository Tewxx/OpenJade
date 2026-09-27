// Jade recovery: original class: jade.deps.eLz.Ozd4G6ZOnT
package jade.client.gui;

import jade.client.module.Module;
import jade.client.setting.KeySetting;
import org.lwjgl.input.Keyboard;

public final class KeybindText {
   private KeybindText() {
   }

   public static String getBindDisplayText(Module var0, KeySetting var1, boolean var2) {
      if (var1 != null) {
         return var2 ? "Press a key..." : var1.getName() + ": '§e" + var1.ukYzs() + "§r'";
      } else if (!var0.canBeEnabled()) {
         return "Module cannot be bound.";
      } else {
         return var2 ? "Press a key..." : "Current bind: '§e" + getKeyName(var0.getKeycode()) + "§r'";
      }
   }

   public static String getKeyName(int var0) {
      if (var0 < 1000) {
         return Keyboard.getKeyName(var0);
      } else if (var0 == 1069) {
         return "MScrollUp";
      } else {
         return var0 == 1070 ? "MScrollDown" : "M" + (var0 - 1000);
      }
   }
}
