// Jade recovery: original class: jade.deps.eLz.ZKPOvKFbMr
package jade.client.module.player.blockin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;

public final class KeybindSync {
   private KeybindSync() {
   }

   public static void releaseMouseKeybinds(Minecraft var0) {
      applyKeybindState(var0.gameSettings.keyBindAttack, false);
      applyKeybindState(var0.gameSettings.keyBindUseItem, false);
   }

   public static void restoreMouseKeybinds(Minecraft var0) {
      applyKeybindState(var0.gameSettings.keyBindAttack, Mouse.isButtonDown(0));
      applyKeybindState(var0.gameSettings.keyBindUseItem, Mouse.isButtonDown(1));
   }

   private static void applyKeybindState(KeyBinding var0, boolean var1) {
      KeyBinding.setKeyBindState(var0.getKeyCode(), var1);
   }
}
