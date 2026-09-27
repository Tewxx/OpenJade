// Jade recovery: original class: jade.deps.eLz.qsXn9y
package jade.client.gui;

import jade.client.Jade;
import jade.client.common.ExternalChatOverlay;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.JadeClickGui;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.module.Module;
import jade.client.module.client.Gui;
import jade.client.module.client.Rendering;
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
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class HudEditorScreen extends GuiScreen {
   private static final int BACKGROUND_TOP_COLOR = -671088640;
   private static final int jvt = 1979711488;
   private static final int cQkd3 = -870704614;
   private static final int pUuye = -584834012;
   private static final int BUTTON_TEXT_HOVER_COLOR = -1184275;
   private static final int nfja3 = -6645094;
   private static final int SELECTION_OUTLINE_COLOR = -1;
   private static final int EDITOR_DIM_COLOR = -1207959552;
   private static final int aJo = -855310;
   private static final int HANDLE_OUTLINE_COLOR = -15263977;
   private static final int BOUNDS_PADDING = 32;
   private static final int NXYVc = 0;
   private static final int HANDLE_TOP_LEFT = 1;
   private static final int Fdw = 2;
   private static final int HANDLE_BOTTOM_RIGHT = 3;
   private static final int HANDLE_BOTTOM_LEFT = 4;
   private static final int SNAP_GRID_SIZE = 10;
   private final GuiScreen parentScreen;
   private final List<HudEditorScreen$28> managedElements = new ArrayList<>();
   private final Map<String, HudEditorScreen$32> elementPositionCache = new HashMap<>();
   private HudEditorScreen$28 oV6;
   private String nkG9;
   private int YRbRzo;
   private int ggK;
   private int dragOriginX;
   private int UKs;
   private HudEditorScreen$28 HneJs;
   private String activeSliderElementId;
   private String csZ;
   private int Ugwz;
   private float Fg9;
   private float sliderElementWidth;
   private float sliderElementHeight;
   private float UVbx;
   private float sliderAnchorY;

   public HudEditorScreen(GuiScreen var1) {
      this.parentScreen = var1;
   }

   public void initGui() {
      this.buttonList.clear();
   }

   public void drawScreen(int var1, int var2, float var3) {
      if (this.oV6 != null) {
         ScaledResolution var4 = new ScaledResolution(this.mc);
         int var5 = Math.max(1, Math.round(this.oV6.getBoundsWidth()));
         int var6 = Math.max(1, Math.round(this.oV6.getBoundsHeight()));
         int var7 = Math.round(this.oV6.bounds[0]) - this.oV6.configuredX;
         int var8 = Math.round(this.oV6.bounds[1]) - this.oV6.configuredY;
         int var9 = -var7 - 32;
         int var10 = -var8 - 32;
         int var11 = Math.max(var9, var4.getScaledWidth() - var5 - var7 + 32);
         int var12 = Math.max(var10, var4.getScaledHeight() - var6 - var8 + 32);
         int var13 = ECFaW(this.dragOriginX + var1 - this.YRbRzo, var9, var11);
         int var14 = ECFaW(this.UKs + var2 - this.ggK, var10, var12);
         this.oV6.renderHandler.VxRc(var13, var14, var4);
      }

      this.updateSliderDrag(var1, var2);
      if (Gui.JzraV3()) {
         RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, -788001777);
      } else {
         RenderUtils.drawVerticalGradient(0.0F, 0.0F, this.width, this.height, -671088640, 1979711488);
      }

      this.refreshElements();
      RenderUtils.setAlphaThreshold(0.0F);

      for (HudEditorScreen$28 var16 : this.managedElements) {
         HudEditorScreen$32 var17 = this.KAdzq(var16);
         var16.bounds = var16.sizeProvider.HBTYxF8(var17.oHgtI, var17.Zu5);
         if (this.activeSliderElementId != null && this.activeSliderElementId.equals(var16.elementId)) {
            this.updateSliderElementPosition(var16);
         }

         GlStateManager.enableBlend();
         GL11.glBlendFunc(770, 771);
         boolean var18 = var16.elementId.equals(this.csZ) || var16.elementId.equals(this.nkG9) || var16.elementId.equals(this.activeSliderElementId);
         boolean var19 = var16.isMouseOver(var1, var2);
         if (var19 || var18) {
            drawSelectionOutline(var16, var18);
         }
      }

      RenderUtils.setAlphaThreshold(10.0F);
      RenderUtils.lTbf();
      this.DZVbH10(this.OhHap(), "Reset all", var1, var2);
      this.DZVbH10(this.QJKaBj3(), "Done", var1, var2);
   }

   private void refreshElements() {
      this.managedElements.clear();
      ScaledResolution var1 = new ScaledResolution(this.mc);
      final Rendering var2 = Jade.getModuleManager().getModule(Rendering.class);
      if (var2 != null && Rendering.isExternalOutput()) {
         this.managedElements
            .add(
               new HudEditorScreen$28(
                  "externalchat", var2, Math.round(var2.NGLeqy() * var1.getScaledWidth()), Math.round(var2.getExternalChatY() * var1.getScaledHeight()), new HudEditorScreen$30() {
                     @Override
                     public float[] HBTYxF8(float var1, float var2x) {
                        return ExternalChatOverlay.getPreviewBounds(var1, var2x);
                     }
                  }, new HudEditorScreen$29() {
                     @Override
                     public void VxRc(int var1, int var2x, ScaledResolution var3) {
                        var2.setExternalChatPosition((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2x / Math.max(1, var3.getScaledHeight()));
                     }

                     @Override
                     public void resetPosition(ScaledResolution var1) {
                        var2.diViwfX();
                     }
                  }
               )
            );
      }

      if (Jade.getModuleManager().getModule(Arraylist.class) != null && Jade.getModuleManager().getModule(Arraylist.class).isEnabled()) {
         this.managedElements
            .add(
               new HudEditorScreen$28(
                  "hud",
                  Jade.getModuleManager().getModule(Arraylist.class),
                  Math.round(Arraylist.getAnchorXRatio() * var1.getScaledWidth()),
                  Math.round(Arraylist.IRZw() * var1.getScaledHeight()),
                  new HudEditorScreen$30() {
                     @Override
                     public float[] HBTYxF8(float var1, float var2) {
                        return Arraylist.renderPreviewBounds(var1, var2);
                     }
                  },
                  new HudEditorScreen$29() {
                     @Override
                     public void VxRc(int var1, int var2, ScaledResolution var3) {
                        Arraylist.setAnchorRatios((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2 / Math.max(1, var3.getScaledHeight()));
                     }

                     @Override
                     public void resetPosition(ScaledResolution var1) {
                        Arraylist.resetPosition();
                     }
                  }
               )
            );
      }

      final KeyBinds var3 = Jade.getModuleManager().getModule(KeyBinds.class);
      if (var3 != null && var3.isEnabled()) {
         this.managedElements.add(new HudEditorScreen$28("hotkey", var3, Math.round(var3.getResolvedX()), Math.round(var3.getResolvedY()), new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var3.getPanelBounds(var1, var2);
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3x) {
               var3.setNormalizedPosition((float)var1 / Math.max(1, var3x.getScaledWidth()), (float)var2 / Math.max(1, var3x.getScaledHeight()));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               var3.resetPosition();
            }
         }));
      }

      if (Jade.getModuleManager().getModule(TargetHUD.class) != null && Jade.getModuleManager().getModule(TargetHUD.class).isEnabled()) {
         final TargetHUD var4 = Jade.getModuleManager().getModule(TargetHUD.class);
         float var5 = var4.getHudWidth();
         int var6 = Math.round(var1.getScaledWidth() / 2.0F - var5 / 2.0F + var4.Vilxx);
         int var7 = Math.round(var1.getScaledHeight() / 2.0F + 15.0F + var4.offsetY);
         this.managedElements.add(new HudEditorScreen$28("targethud", var4, var6, var7, new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var4.getEditorBounds(Math.round(var1), Math.round(var2));
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3) {
               var4.Vilxx = Math.round(var1 - (var3.getScaledWidth() / 2.0F - var4.getHudWidth() / 2.0F));
               var4.offsetY = Math.round(var2 - (var3.getScaledHeight() / 2.0F + 15.0F));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               var4.Vilxx = 70;
               var4.offsetY = 30;
            }
         }));
      }

      if (Jade.getModuleManager().getModule(Watermark.class) != null && Jade.getModuleManager().getModule(Watermark.class).isEnabled()) {
         final Watermark var10 = Jade.getModuleManager().getModule(Watermark.class);
         this.managedElements.add(new HudEditorScreen$28("watermark", var10, Math.round(var10.getRenderX()), Math.round(var10.getRenderY()), new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var10.getBoundsAt(var1, var2);
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3) {
               var10.XkC0((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2 / Math.max(1, var3.getScaledHeight()));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               var10.resetToDefaultOffset();
            }
         }));
      }

      if (Jade.getModuleManager().getModule(ProgressBar.class) != null && Jade.getModuleManager().getModule(ProgressBar.class).isEnabled()) {
         final ProgressBar var11 = Jade.getModuleManager().getModule(ProgressBar.class);
         this.managedElements.add(new HudEditorScreen$28("progressbar", var11, Math.round(var11.getScreenX()), Math.round(var11.getScreenY()), new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var11.getBoundsAt(var1, var2);
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3) {
               var11.setNormalizedPosition((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2 / Math.max(1, var3.getScaledHeight()));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               var11.resetPosition();
            }
         }));
      }

      final BedwarsUtils var12 = Jade.getModuleManager().getModule(BedwarsUtils.class);
      if (var12 != null && var12.isEnabled() && var12.isFinalKillHudActive()) {
         this.managedElements.add(new HudEditorScreen$28("bedwarsfinalkills", var12, Math.round(var12.getFinalKillHudX()), Math.round(var12.iskppX4()), new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var12.renderFinalKillHud(var1, var2);
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3) {
               var12.applyFinalKillHudDrag((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2 / Math.max(1, var3.getScaledHeight()));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               var12.resetFinalKillHudPosition();
            }
         }));
      }

      if (var12 != null && var12.isEnabled() && var12.woFa()) {
         this.managedElements.add(new HudEditorScreen$28("bedwarsdragons", var12, Math.round(var12.getDragonHudX()), Math.round(var12.getDragonHudY()), new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var12.renderDragonHud(var1, var2);
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3) {
               var12.applyDragonHudDrag((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2 / Math.max(1, var3.getScaledHeight()));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               var12.resetDragonHudPosition();
            }
         }));
      }

      if (var12 != null && var12.isEnabled() && var12.isBuildLimitHudActive()) {
         this.managedElements.add(new HudEditorScreen$28("bedwarsbuildlimit", var12, Math.round(var12.getBuildLimitHudX()), Math.round(var12.getBuildLimitHudY()), new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var12.renderBuildLimitHud(var1, var2);
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3) {
               var12.applyBuildLimitHudDrag((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2 / Math.max(1, var3.getScaledHeight()));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               var12.resetBuildLimitHudPosition();
            }
         }));
      }

      final Overlay var13 = Jade.getModuleManager().getModule(Overlay.class);
      if (var13 != null && var13.isEnabled()) {
         this.managedElements.add(new HudEditorScreen$28("overlay", var13, Math.round(var13.getOverlayPosX()), Math.round(var13.getOverlayPosY()), new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var13.getOverlayPreviewBounds(var1, var2);
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3) {
               var13.setOverlayPosition((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2 / Math.max(1, var3.getScaledHeight()));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               var13.resetOverlayPosition();
            }
         }));
      }

      final LagDetect var14 = Jade.getModuleManager().getModule(LagDetect.class);
      if (var14 != null && var14.isEnabled()) {
         this.managedElements.add(new HudEditorScreen$28("lagdetect", var14, Math.round(LagDetect.getAlertX()), Math.round(LagDetect.unh25()), new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var14.drawAlertAt(var1, var2);
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3) {
               LagDetect.setPositionFractions((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2 / Math.max(1, var3.getScaledHeight()));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               LagDetect.resetPosition();
            }
         }));
      }

      final Notifications var15 = Jade.getModuleManager().getModule(Notifications.class);
      if (var15 != null && var15.isEnabled()) {
         this.managedElements.add(new HudEditorScreen$28("notifications", var15, Math.round(Notifications.getScreenAnchorX()), Math.round(Notifications.getScreenAnchorY()), new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var15.VWNVw(var1, var2);
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3) {
               Notifications.setAnchorRatio((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2 / Math.max(1, var3.getScaledHeight()));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               Notifications.resetAnchorPosition();
            }
         }));
      }

      final HideWindow var8 = Jade.getModuleManager().getModule(HideWindow.class);
      if (var8 != null && var8.isEnabled()) {
         this.managedElements.add(new HudEditorScreen$28("hidewindow", var8, Math.round(var8.getWindowPixelX()), Math.round(var8.getWindowPixelY()), new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var8.renderIconAndGetBounds(var1, var2);
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3) {
               var8.YYiq8((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2 / Math.max(1, var3.getScaledHeight()));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               var8.fdma();
            }
         }));
      }

      final BridgeAssist var9 = Jade.getModuleManager().getModule(BridgeAssist.class);
      if (var9 != null && var9.isEnabled() && var9.lrJygt()) {
         this.managedElements.add(new HudEditorScreen$28("bridgeassistblocks", var9, Math.round(var9.getHudPixelX()), Math.round(var9.uRaud()), new HudEditorScreen$30() {
            @Override
            public float[] HBTYxF8(float var1, float var2) {
               return var9.TDIk(var1, var2);
            }
         }, new HudEditorScreen$29() {
            @Override
            public void VxRc(int var1, int var2, ScaledResolution var3) {
               var9.setHudFraction((float)var1 / Math.max(1, var3.getScaledWidth()), (float)var2 / Math.max(1, var3.getScaledHeight()));
            }

            @Override
            public void resetPosition(ScaledResolution var1) {
               var9.resetHudPosition();
            }
         }));
      }
   }

   private HudEditorScreen$32 KAdzq(HudEditorScreen$28 var1) {
      HudEditorScreen$32 var2 = this.elementPositionCache.get(var1.elementId);
      if (var2 == null) {
         var2 = new HudEditorScreen$32(var1.configuredX, var1.configuredY);
         this.elementPositionCache.put(var1.elementId, var2);
      }

      boolean var3 = this.nkG9 != null && this.nkG9.equals(var1.elementId) || this.activeSliderElementId != null && this.activeSliderElementId.equals(var1.elementId);
      if (var3) {
         var2.oHgtI = var1.configuredX;
         var2.Zu5 = var1.configuredY;
      } else {
         var2.oHgtI = var2.oHgtI + (var1.configuredX - var2.oHgtI) * 0.34F;
         var2.Zu5 = var2.Zu5 + (var1.configuredY - var2.Zu5) * 0.34F;
         if (Math.abs(var1.configuredX - var2.oHgtI) < 0.05F) {
            var2.oHgtI = var1.configuredX;
         }

         if (Math.abs(var1.configuredY - var2.Zu5) < 0.05F) {
            var2.Zu5 = var1.configuredY;
         }
      }

      return var2;
   }

   private void DZVbH10(HudEditorScreen$31 var1, String var2, int var3, int var4) {
      boolean var5 = var1.contains(var3, var4);
      int var6 = var5 ? -584834012 : -870704614;
      if (Gui.JzraV3()) {
         RenderUtils.XNRNki(var1.left, var1.top, var1.left + var1.urr, var1.top + var1.eFnoRh, var6);
         if (var5) {
            RenderUtils.XNRNki(var1.left, var1.top, var1.left + var1.urr, var1.top + var1.eFnoRh, 436207615);
         }

         drawBorder(var1.left, var1.top, var1.left + var1.urr, var1.top + var1.eFnoRh, 613258909);
      } else {
         RoundedRect.drawRoundedRectArgb(var1.left, var1.top, var1.urr, var1.eFnoRh, 6.0F, var6);
         RoundedRect.drawRoundedOutline(var1.left, var1.top, var1.urr, var1.eFnoRh, 6.0F, 0.35F, new Color(0, 0, 0, 0), new Color(var5 ? 620756991 : 352321535, true));
      }

      IFont var7 = FontManager.getClickGuiSettingRenderer(Gui.JzraV3() ? Gui.ISjhxoi() : "Modern");
      var7.drawString(var2, var1.left + (var1.urr - var7.getStringWidth(var2)) / 2.0F, var1.top + 7, var5 ? -1184275 : -6645094, false);
   }

   private static void drawBorder(float var0, float var1, float var2, float var3, int var4) {
      RenderUtils.XNRNki(var0, var1, var2, var1 + 1.0F, var4);
      RenderUtils.XNRNki(var0, var3 - 1.0F, var2, var3, var4);
      RenderUtils.XNRNki(var0, var1, var0 + 1.0F, var3, var4);
      RenderUtils.XNRNki(var2 - 1.0F, var1, var2, var3, var4);
   }

   private static void drawSelectionOutline(HudEditorScreen$28 var0, boolean var1) {
      float var2 = var0.bounds[0] - 3.0F;
      float var3 = var0.bounds[1] - 3.0F;
      float var4 = var0.bounds[2] + 3.0F;
      float var5 = var0.bounds[3] + 3.0F;
      drawBorder(var2, var3, var4, var5, -1);
      if (var1 || var0.sliderSetting != null) {
         drawResizeHandle(var2, var3);
         drawResizeHandle(var4, var3);
         drawResizeHandle(var4, var5);
         drawResizeHandle(var2, var5);
      }
   }

   private static void drawResizeHandle(float var0, float var1) {
      RoundedRect.drawRoundedRectArgb(var0 - 3.0F, var1 - 3.0F, 6.0F, 6.0F, Gui.JzraV3() ? 0.0F : 2.0F, -855310);
      RoundedRect.drawRoundedOutline(var0 - 3.0F, var1 - 3.0F, 6.0F, 6.0F, Gui.JzraV3() ? 0.0F : 2.0F, 0.16F, new Color(0, 0, 0, 0), new Color(-15263977, true));
   }

   private HudEditorScreen$31 OhHap() {
      return new HudEditorScreen$31(this.width - 188, this.height - 30, 86, 22);
   }

   private HudEditorScreen$31 QJKaBj3() {
      return new HudEditorScreen$31(this.width - 94, this.height - 30, 78, 22);
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 1) {
         for (int var4 = this.managedElements.size() - 1; var4 >= 0; var4--) {
            HudEditorScreen$28 var5 = this.managedElements.get(var4);
            if (var5.isMouseOver(var1, var2)) {
               this.openModuleConfig(var5.module);
               return;
            }
         }
      }

      if (var3 == 0) {
         if (this.OhHap().contains(var1, var2)) {
            this.HgrYv();
            return;
         }

         if (this.QJKaBj3().contains(var1, var2)) {
            this.mc.displayGuiScreen(this.parentScreen);
            return;
         }

         for (int var7 = this.managedElements.size() - 1; var7 >= 0; var7--) {
            HudEditorScreen$28 var8 = this.managedElements.get(var7);
            int var6 = var8.pibfy(var1, var2);
            if (var6 != 0 && var8.sliderSetting != null) {
               this.beginSliderDrag(var8, var6);
               return;
            }

            if (var8.isMouseOver(var1, var2)) {
               this.oV6 = var8;
               this.nkG9 = var8.elementId;
               this.csZ = var8.elementId;
               this.YRbRzo = var1;
               this.ggK = var2;
               this.dragOriginX = var8.configuredX;
               this.UKs = var8.configuredY;
               return;
            }
         }
      }

      super.mouseClicked(var1, var2, var3);
   }

   private void openModuleConfig(Module var1) {
      if (var1 != null && this.parentScreen instanceof JadeClickGui) {
         JadeClickGui var2 = (JadeClickGui)this.parentScreen;
         var2.openModuleConfig(var1);
         this.mc.displayGuiScreen(var2);
      }
   }

   protected void mouseClickMove(int var1, int var2, int var3, long var4) {
      super.mouseClickMove(var1, var2, var3, var4);
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      super.mouseReleased(var1, var2, var3);
      if (var3 == 0) {
         boolean var4 = this.oV6 != null || this.HneJs != null;
         this.oV6 = null;
         this.nkG9 = null;
         if (var4) {
            markConfigUnsaved();
         }

         this.HneJs = null;
         this.activeSliderElementId = null;
         this.Ugwz = 0;
      }
   }

   private void HgrYv() {
      ScaledResolution var1 = new ScaledResolution(this.mc);
      this.refreshElements();

      for (HudEditorScreen$28 var3 : this.managedElements) {
         var3.renderHandler.resetPosition(var1);
         if (var3.sliderSetting != null) {
            var3.sliderSetting.setValueClamped(var3.getElementScale());
         }
      }

      markConfigUnsaved();
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         this.mc.displayGuiScreen(this.parentScreen);
      } else {
         super.keyTyped(var1, var2);
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   private static int ECFaW(int var0, int var1, int var2) {
      return Math.max(var1, Math.min(var2, var0));
   }

   private void beginSliderDrag(HudEditorScreen$28 var1, int var2) {
      this.HneJs = var1;
      this.activeSliderElementId = var1.elementId;
      this.csZ = var1.elementId;
      this.Ugwz = var2;
      this.Fg9 = (float)var1.sliderSetting.getInput();
      this.sliderElementWidth = var1.getBoundsWidth();
      this.sliderElementHeight = var1.getBoundsHeight();
      this.UVbx = tlqE(var2) ? var1.bounds[2] : var1.bounds[0];
      this.sliderAnchorY = isTopHandle(var2) ? var1.bounds[3] : var1.bounds[1];
   }

   private void updateSliderDrag(int var1, int var2) {
      if (this.HneJs != null && this.HneJs.sliderSetting != null && this.Ugwz != 0) {
         float var3 = tlqE(this.Ugwz) ? -this.sliderElementWidth : this.sliderElementWidth;
         float var4 = isTopHandle(this.Ugwz) ? -this.sliderElementHeight : this.sliderElementHeight;
         float var5 = var1 - this.UVbx;
         float var6 = var2 - this.sliderAnchorY;
         float var7 = var3 * var3 + var4 * var4;
         if (!(var7 <= 0.001F)) {
            float var8 = (var5 * var3 + var6 * var4) / var7;
            this.HneJs.sliderSetting.setValueClamped(this.Fg9 * Math.max(0.05F, var8));
         }
      }
   }

   private void updateSliderElementPosition(HudEditorScreen$28 var1) {
      float var2 = tlqE(this.Ugwz) ? this.UVbx - var1.getBoundsWidth() : this.UVbx;
      float var3 = isTopHandle(this.Ugwz) ? this.sliderAnchorY - var1.getBoundsHeight() : this.sliderAnchorY;
      float var4 = var1.bounds[0] - var1.configuredX;
      float var5 = var1.bounds[1] - var1.configuredY;
      var1.renderHandler.VxRc(Math.round(var2 - var4), Math.round(var3 - var5), new ScaledResolution(this.mc));
   }

   private static boolean tlqE(int var0) {
      return var0 == 1 || var0 == 4;
   }

   private static boolean isTopHandle(int var0) {
      return var0 == 1 || var0 == 2;
   }

   private static void markConfigUnsaved() {
      if (Jade.Grq != null && Jade.Grq.getProfile() != null) {
         Jade.Grq.getProfile().unmodified = false;
      }
   }
}
