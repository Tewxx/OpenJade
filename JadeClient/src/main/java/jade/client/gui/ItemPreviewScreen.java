// Jade recovery: original class: jade.deps.eLz.agotcbK2L
package jade.client.gui;

import jade.client.common.IFont;
import jade.client.common.ItemNames;
import jade.client.common.QuickBuyLayout;
import jade.client.common.RoundedRect;
import jade.client.module.client.Gui;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public abstract class ItemPreviewScreen extends GuiScreen {
   protected static final int WINDOW_WIDTH = 336;
   protected static final int HEADER_HEIGHT = 24;
   protected static final int BUTTON_HEIGHT = 20;
   protected static final int SLOT_SIZE = 28;
   protected static final int SLOT_CORNER_RADIUS = 4;
   protected static final int YGqZpx = -15066598;
   protected static final int bemz = -15658735;
   protected static final int BUTTON_HOVER_START_COLOR = -14803162;
   protected static final int BUTTON_HOVER_END_COLOR = -14408404;
   protected static final int oxw = -15461356;
   protected static final int HOVER_FILL_COLOR = -14408404;
   protected static final int WINDOW_OUTLINE_COLOR = 1711276032;
   protected static final int VhduoO = 352321535;
   protected static final int PRIMARY_TEXT_COLOR = -986896;
   protected static final int SECONDARY_TEXT_COLOR = -6645094;
   protected static final int OQWgV = -10197916;
   protected static final int OqD = -34953;
   private long dDk;
   private long lastFrameTimeMillis;
   protected float tjyJ = 1.0F;
   protected final float[] slotHoverAmounts = new float[21];
   protected String hoveredItemName = "";

   protected final void beginFlowAnimation() {
      this.dDk = System.currentTimeMillis();
      this.lastFrameTimeMillis = this.dDk;
   }

   protected final void updateFlowAnimation() {
      long var1 = System.currentTimeMillis();
      float var3 = Math.min(0.05F, Math.max(0.001F, (float)(var1 - this.lastFrameTimeMillis) / 1000.0F));
      this.lastFrameTimeMillis = var1;
      this.tjyJ = 1.0F - (float)Math.exp(-13.0F * var3);
   }

   protected final float openScale() {
      float var1 = clamp((float)(System.currentTimeMillis() - this.dDk) / 230.0F, 0.0F, 1.0F);
      return 0.94F + easeOutCubic(var1) * 0.06F;
   }

   protected final float approach(float var1, float var2) {
      return var1 + (var2 - var1) * this.tjyJ;
   }

   protected final void drawWindow(int var1, int var2, int var3, int var4) {
      this.round(var1, var2, var3, var4, 9.0F, -15066598);
      this.outline(var1, var2, var3, var4, 9.0F);
   }

   protected final void drawButton(String var1, ItemPreviewScreen$0 var2, float var3, boolean var4, boolean var5) {
      if (var2 != ItemPreviewScreen$0.EMPTY) {
         int var6 = var4 ? (var5 ? lerpColor(-15577801, -15172779, var3) : lerpColor(-14803162, -14408404, var3)) : -15395301;
         this.round(var2.CONop, var2.YHn95, var2.NhA, var2.crXyrt, 7.0F, var6);
         IFont var7 = this.headerFont();
         int var8 = var4 ? lerpColor(-6645094, -986896, var3) : -10197916;
         var7.drawString(
            var1, var2.CONop + (var2.NhA - var7.getStringWidth(var1)) / 2.0F, var2.YHn95 + (var2.crXyrt - var7.getFontHeight()) / 2.0F + 1.0F, var8, false
         );
      }
   }

   protected final void drawPreview(List<String> var1, List<ItemStack> var2, int var3, int var4, int var5, int var6, int var7, float var8) {
      this.hoveredItemName = "";
      if (QuickBuyLayout.isValidLayout(var1)) {
         short var9 = 220;
         int var10 = var3 + (var5 - var9) / 2;

         for (int var11 = 0; var11 < 21; var11++) {
            int var12 = var10 + var11 % 7 * 32;
            int var13 = var4 + var11 / 7 * 32;
            ItemPreviewScreen$0 var14 = new ItemPreviewScreen$0(var12, var13, 28, 28);
            boolean var15 = var14.contains(var6, var7) && var8 > 0.92F;
            this.slotHoverAmounts[var11] = this.approach(this.slotHoverAmounts[var11], var15 ? 1.0F : 0.0F);
            float var16 = clamp(var8 * 1.3F - var11 * 0.015F, 0.0F, 1.0F);
            float var17 = easeOutCubic(var16);
            int var18 = lerpColor(-15461356, -14408404, this.slotHoverAmounts[var11]);
            this.round(var12, var13, 28.0F, 28.0F, 4.0F, withAlpha(var18, Math.round(255.0F * var17)));
            RoundedRect.drawRoundedOutline(var12, var13, 28.0F, 28.0F, 4.0F, 0.18F, new Color(0, 0, 0, 0), new Color(withAlpha(352321535, Math.round(40.0F * var17)), true));
            if (var11 < var2.size() && var16 > 0.08F) {
               float var19 = 0.78F + 0.22F * var17;
               float var20 = var12 + 14.0F;
               float var21 = var13 + 14.0F;
               GlStateManager.pushMatrix();
               GlStateManager.translate(var20, var21, 0.0F);
               GlStateManager.scale(var19, var19, 1.0F);
               GlStateManager.translate(-var20, -var21, 0.0F);
               this.drawItem((ItemStack)var2.get(var11), var12 + 6, var13 + 6);
               GlStateManager.popMatrix();
            }

            if (var15) {
               this.hoveredItemName = ItemNames.formatSkyblockName((String)var1.get(var11));
            }
         }
      }
   }

   protected final void drawItem(ItemStack var1, int var2, int var3) {
      if (var1 != null && this.itemRender != null) {
         GlStateManager.enableDepth();
         RenderHelper.enableGUIStandardItemLighting();
         this.itemRender.renderItemAndEffectIntoGUI(var1, var2, var3);
         this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, var1, var2, var3, null);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableLighting();
      }
   }

   protected final void drawTooltip(String var1, int var2, int var3) {
      if (var1 != null && !var1.isEmpty()) {
         IFont var4 = this.settingFont();
         int var5 = var4.getStringWidth(var1) + 12;
         int var6 = var4.getFontHeight() + 8;
         int var7 = Math.min(this.width - var5 - 4, var2 + 9);
         int var8 = Math.min(this.height - var6 - 4, var3 + 6);
         this.round(var7, var8, var5, var6, 5.0F, -233170406);
         this.outline(var7, var8, var5, var6, 5.0F);
         var4.drawString(var1, var7 + 6, var8 + 4, -986896, false);
      }
   }

   protected static List<ItemStack> resolveItemStacks(List<String> var0) {
      if (var0 == null) {
         return Collections.emptyList();
      } else {
         ArrayList var1 = new ArrayList(var0.size());

         for (String var3 : var0) {
            var1.add(QuickBuyItems.createItemStack(var3));
         }

         return Collections.unmodifiableList(var1);
      }
   }

   protected final IFont headerFont() {
      return Gui.getHeaderFont();
   }

   protected final IFont settingFont() {
      return Gui.getSettingFont();
   }

   protected final int accent() {
      return Gui.accent == null ? -15030151 : Gui.accent.getArgb() | 0xFF000000;
   }

   protected final void round(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (!(var3 <= 0.0F) && !(var4 <= 0.0F)) {
         RoundedRect.drawRoundedRect(var1, var2, var3, var4, var5, new Color(var6, true));
      }
   }

   protected final void outline(float var1, float var2, float var3, float var4, float var5) {
      RoundedRect.drawRoundedOutline(var1, var2, var3, var4, var5, 0.22F, new Color(0, 0, 0, 0), new Color(1711276032, true));
      RoundedRect.drawRoundedOutline(var1 + 0.5F, var2 + 0.5F, var3 - 1.0F, var4 - 1.0F, Math.max(0.0F, var5 - 0.5F), 0.22F, new Color(0, 0, 0, 0), new Color(352321535, true));
   }

   protected static int withAlpha(int var0, int var1) {
      return Math.max(0, Math.min(255, var1)) << 24 | var0 & 16777215;
   }

   protected static int lerpColor(int var0, int var1, float var2) {
      float var3 = clamp(var2, 0.0F, 1.0F);
      int var4 = Math.round((var0 >>> 24 & 0xFF) + ((var1 >>> 24 & 0xFF) - (var0 >>> 24 & 0xFF)) * var3);
      int var5 = Math.round((var0 >>> 16 & 0xFF) + ((var1 >>> 16 & 0xFF) - (var0 >>> 16 & 0xFF)) * var3);
      int var6 = Math.round((var0 >>> 8 & 0xFF) + ((var1 >>> 8 & 0xFF) - (var0 >>> 8 & 0xFF)) * var3);
      int var7 = Math.round((var0 & 0xFF) + ((var1 & 0xFF) - (var0 & 0xFF)) * var3);
      return var4 << 24 | var5 << 16 | var6 << 8 | var7;
   }

   protected static float easeOutCubic(float var0) {
      float var1 = 1.0F - clamp(var0, 0.0F, 1.0F);
      return 1.0F - var1 * var1 * var1;
   }

   protected static float clamp(float var0, float var1, float var2) {
      return Math.max(var1, Math.min(var2, var0));
   }
}
