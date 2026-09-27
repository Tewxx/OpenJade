// Jade recovery: original class: jade.deps.eLz.qsXn9y$28
package jade.client.gui;

import jade.client.module.Module;
import jade.client.module.minigames.BedwarsUtils;
import jade.client.module.minigames.Overlay;
import jade.client.module.other.LagDetect;
import jade.client.module.player.BridgeAssist;
import jade.client.module.player.HideWindow;
import jade.client.module.render.Arraylist;
import jade.client.module.render.KeyBinds;
import jade.client.module.render.Notifications;
import jade.client.module.render.ProgressBar;
import jade.client.module.render.TargetHUD;
import jade.client.module.render.Watermark;
import jade.client.setting.SliderSetting;

public class HudEditorScreen$28 {
   public final String elementId;
   public final Module module;
   public final int configuredX;
   public final int configuredY;
   public final HudEditorScreen$30 sizeProvider;
   public final HudEditorScreen$29 renderHandler;
   public final SliderSetting sliderSetting;
   public float[] bounds;

   public HudEditorScreen$28(String var1, Module var2, int var3, int var4, HudEditorScreen$30 var5, HudEditorScreen$29 var6) {
      float[] var10001 = new float[4];
      var10001[0] = 0.0F;
      var10001[1] = 0.0F;
      var10001[2] = 1.0F;
      var10001[3] = 1.0F;
      this.bounds = var10001;
      this.elementId = var1;
      this.module = var2;
      this.configuredX = var3;
      this.configuredY = var4;
      this.sizeProvider = var5;
      this.renderHandler = var6;
      this.sliderSetting = findScaleSetting(var1, var2);
   }

   public float getBoundsWidth() {
      return Math.max(1.0F, this.bounds[2] - this.bounds[0]);
   }

   public float getBoundsHeight() {
      return Math.max(1.0F, this.bounds[3] - this.bounds[1]);
   }

   public boolean isMouseOver(int var1, int var2) {
      return var1 >= this.bounds[0] - 4.0F && var1 <= this.bounds[2] + 4.0F && var2 >= this.bounds[1] - 4.0F && var2 <= this.bounds[3] + 4.0F;
   }

   public int pibfy(int var1, int var2) {
      float var3 = this.bounds[0] - 3.0F;
      float var4 = this.bounds[1] - 3.0F;
      float var5 = this.bounds[2] + 3.0F;
      float var6 = this.bounds[3] + 3.0F;
      if (ZeUm(var1, var2, var3, var4)) {
         return 1;
      } else if (ZeUm(var1, var2, var5, var4)) {
         return 2;
      } else if (ZeUm(var1, var2, var5, var6)) {
         return 3;
      } else {
         return ZeUm(var1, var2, var3, var6) ? 4 : 0;
      }
   }

   private static boolean ZeUm(int var0, int var1, float var2, float var3) {
      float var4 = 5.0F;
      return var0 >= var2 - var4 && var0 <= var2 + var4 && var1 >= var3 - var4 && var1 <= var3 + var4;
   }

   private static SliderSetting findScaleSetting(String var0, Module var1) {
      if (var1 instanceof Arraylist) {
         return ((Arraylist)var1).getScale();
      } else if (var1 instanceof KeyBinds) {
         return ((KeyBinds)var1).getScale();
      } else if (var1 instanceof TargetHUD) {
         return ((TargetHUD)var1).getFontScale();
      } else if (var1 instanceof Watermark) {
         return ((Watermark)var1).getScale();
      } else if (var1 instanceof ProgressBar) {
         return ((ProgressBar)var1).getFontScale();
      } else if (var1 instanceof Overlay) {
         return ((Overlay)var1).getFontSize();
      } else if (var1 instanceof LagDetect) {
         return ((LagDetect)var1).getFontScale();
      } else if (var1 instanceof Notifications) {
         return ((Notifications)var1).getScale();
      } else if (var1 instanceof HideWindow) {
         return ((HideWindow)var1).getIconScale();
      } else if (var1 instanceof BridgeAssist) {
         return ((BridgeAssist)var1).getScale();
      } else {
         if (var1 instanceof BedwarsUtils) {
            BedwarsUtils var2 = (BedwarsUtils)var1;
            if ("bedwarsfinalkills".equals(var0)) {
               return var2.getFinalKillHudScale();
            }

            if ("bedwarsdragons".equals(var0)) {
               return var2.getDragonHudScale();
            }

            if ("bedwarsbuildlimit".equals(var0)) {
               return var2.getBuildLimitHudScale();
            }
         }

         return null;
      }
   }

   public double getElementScale() {
      return "overlay".equals(this.elementId) ? 0.75 : 1.0;
   }
}
