// Jade recovery: original class: jade.deps.eLz.rcdsHdYLKo
package jade.client.core;

import jade.client.common.Subscribe;
import jade.client.common.OverlayConfig;
import jade.client.event.RenderTickEvent;
import jade.client.gui.JadeScreen;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class KeystrokesHud {
   private static final int[] COLOR_OPTIONS = new int[]{16777215, 16711680, 65280, 255, 16776960, 11141290};
   private Minecraft mc = Minecraft.getMinecraft();
   private KeystrokesDisplay[] aYu = new KeystrokesDisplay[4];
   private CpsDisplay[] cpsDisplays = new CpsDisplay[2];

   public KeystrokesHud() {
      this.aYu[0] = new KeystrokesDisplay(this.mc.gameSettings.keyBindForward, 26, 2);
      this.aYu[1] = new KeystrokesDisplay(this.mc.gameSettings.keyBindBack, 26, 26);
      this.aYu[2] = new KeystrokesDisplay(this.mc.gameSettings.keyBindLeft, 2, 26);
      this.aYu[3] = new KeystrokesDisplay(this.mc.gameSettings.keyBindRight, 50, 26);
      this.cpsDisplays[0] = new CpsDisplay(0, 2, 50);
      this.cpsDisplays[1] = new CpsDisplay(1, 38, 50);
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (this.mc.currentScreen != null) {
         if (this.mc.currentScreen instanceof JadeScreen) {
            try {
               this.mc.currentScreen.handleInput();
            } catch (IOException var3) {
            }
         }
      } else if (this.mc.inGameHasFocus && !this.mc.gameSettings.showDebugInfo) {
         this.renderHud();
      }
   }

   public void renderHud() {
      if (OverlayConfig.overlayEnabled) {
         int var1 = OverlayConfig.WRg;
         int var2 = OverlayConfig.overlayY;
         int var3 = this.resolveColor(OverlayConfig.textColorIndex);
         boolean var4 = OverlayConfig.showMouseButtons;
         ScaledResolution var5 = new ScaledResolution(this.mc);
         byte var6 = 74;
         int var7 = var4 ? 74 : 50;
         if (var1 < 0) {
            OverlayConfig.WRg = 0;
            var1 = OverlayConfig.WRg;
         } else if (var1 > var5.getScaledWidth() - var6) {
            OverlayConfig.WRg = var5.getScaledWidth() - var6;
            var1 = OverlayConfig.WRg;
         }

         if (var2 < 0) {
            OverlayConfig.overlayY = 0;
            var2 = OverlayConfig.overlayY;
         } else if (var2 > var5.getScaledHeight() - var7) {
            OverlayConfig.overlayY = var5.getScaledHeight() - var7;
            var2 = OverlayConfig.overlayY;
         }

         this.renderKeystrokes(var1, var2, var3);
         if (var4) {
            this.renderCps(var1, var2, var3);
         }
      }
   }

   private int resolveColor(int var1) {
      return var1 == 6 ? Color.getHSBColor((float)(System.currentTimeMillis() % 3750L) / 3750.0F, 1.0F, 1.0F).getRGB() : COLOR_OPTIONS[var1];
   }

   private void renderKeystrokes(int var1, int var2, int var3) {
      for (KeystrokesDisplay var7 : this.aYu) {
         var7.drawKey(var1, var2, var3);
      }
   }

   private void renderCps(int var1, int var2, int var3) {
      for (CpsDisplay var7 : this.cpsDisplays) {
         var7.drawCps(var1, var2, var3);
      }
   }
}
