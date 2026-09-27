// Jade recovery: original class: jade.deps.eLz.T2kmiz
package jade.client.core;

import jade.client.common.OverlayConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeystrokesDisplay {
   private static final int SIZE = 22;
   private final Minecraft mc = Minecraft.getMinecraft();
   private final KeyBinding keyBinding;
   private final int offsetX;
   private final int offsetY;
   private final PressAnimation TUIlq = new PressAnimation();

   public KeystrokesDisplay(KeyBinding var1, int var2, int var3) {
      this.keyBinding = var1;
      this.offsetX = var2;
      this.offsetY = var3;
   }

   public void drawKey(int var1, int var2, int var3) {
      boolean var4 = this.keyBinding.isKeyDown();
      if (this.TUIlq.hasStateChanged(var4)) {
         this.TUIlq.updateState(var4, System.currentTimeMillis());
      }

      int var5 = this.TUIlq.getAlpha(var4, System.currentTimeMillis());
      double var6 = this.TUIlq.JCj0(var4, System.currentTimeMillis());
      int var8 = var1 + this.offsetX;
      int var9 = var2 + this.offsetY;
      Gui.drawRect(var8, var9, var8 + 22, var9 + 22, ColorUtils.translucentGray(var5));
      if (OverlayConfig.outlineEnabled) {
         drawBorder(var8, var9, ColorUtils.withFullAlpha(var3));
      }

      String var10 = Keyboard.getKeyName(this.keyBinding.getKeyCode());
      this.mc.fontRendererObj.drawString(var10, var8 + 8, var9 + 8, ColorUtils.scaleRgb(var3, var6));
   }

   private static void drawBorder(int var0, int var1, int var2) {
      Gui.drawRect(var0, var1, var0 + 22, var1 + 1, var2);
      Gui.drawRect(var0, var1 + 22 - 1, var0 + 22, var1 + 22, var2);
      Gui.drawRect(var0, var1, var0 + 1, var1 + 22, var2);
      Gui.drawRect(var0 + 22 - 1, var1, var0 + 22, var1 + 22, var2);
   }
}
