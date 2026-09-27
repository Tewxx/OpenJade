// Jade recovery: original class: jade.deps.eLz.t6TRQxXFf
package jade.client.common;

import jade.client.module.client.Gui;
import jade.client.setting.BooleanSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.Setting;
import java.util.IdentityHashMap;
import java.util.Map.Entry;
import java.util.Map;

public final class DropdownLayoutManager {
   private final SettingsPanelBuilder panelBuilder = new SettingsPanelBuilder();
   private final Map<SettingGroup, GuiRect> titleBounds = new IdentityHashMap<>();
   private final Map<SettingGroup, GuiRect> expandButtonBounds = new IdentityHashMap<>();
   private final Map<SettingGroup, GuiRect> bindLabelBounds = new IdentityHashMap<>();
   private final Map<Object, SmoothedFloat> NUq = new IdentityHashMap<>();
   private final Map<Object, Integer> animatedHeights = new IdentityHashMap<>();

   public void resetRowAnimations() {
      this.NUq.clear();
      this.animatedHeights.clear();
   }

   private int animateRowHeight(Object var1, int var2, boolean var3, long var4) {
      SmoothedFloat var6 = this.NUq.get(var1);
      if (var6 == null) {
         var6 = new SmoothedFloat(var3 ? 1.0F : 0.0F);
         this.NUq.put(var1, var6);
      }

      int var7 = Math.round(var2 * var6.smoothTowardsAt(var3 ? 1.0F : 0.0F, var4));
      this.animatedHeights.put(var1, var7);
      return var7;
   }

   public void XRfL() {
      this.titleBounds.clear();
      this.expandButtonBounds.clear();
      this.bindLabelBounds.clear();
   }

   private boolean matchesSettingSearch(JadeClickGui var1, Setting var2) {
      if (!this.isSettingVisible(var2)) {
         return false;
      } else {
         String var3 = var1.normalizedSearch();
         if (!var3.isEmpty() && !var1.matchesText(var2.getName(), var3)) {
            if (var2 instanceof MultiSelectSetting) {
               for (BooleanSetting var7 : ((MultiSelectSetting)var2).awwHd()) {
                  if (var7 != null && var1.matchesText(var7.getName(), var3)) {
                     return true;
                  }
               }
            }

            return false;
         } else {
            return true;
         }
      }
   }

   private boolean matchesModuleSearch(JadeClickGui var1, SettingGroup var2) {
      String var3 = var1.normalizedSearch();
      if (!var3.isEmpty() && !var1.matchesText(var2.title, var3)) {
         for (Setting var5 : var2.settings) {
            if (this.matchesSettingSearch(var1, var5)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private boolean isDropdownExpanded(JadeClickGui var1, SettingGroup var2) {
      return var2.expanded || !var1.normalizedSearch().isEmpty() && this.matchesModuleSearch(var1, var2);
   }

   private int NGrm(JadeClickGui var1, SettingGroup var2, int var3) {
      int var4 = 8;

      for (Setting var6 : var2.settings) {
         if (this.isSettingVisible(var6)) {
            var4 += var1.settingHeight(var6, var3) + 2;
         }
      }

      return 21 + Math.round(var4 * var1.dropdownTransition(var2, this.isDropdownExpanded(var1, var2) ? 1.0F : 0.0F));
   }

   private boolean isSettingVisible(Setting var1) {
      return var1.visible && (var1 != Gui.dropdownAccent || Gui.YPPBEUf());
   }

   public int measureColumnHeight(JadeClickGui var1, int var2) {
      int var3 = 0;
      long var4 = System.currentTimeMillis();

      for (SettingGroup var7 : this.panelBuilder.RpwI) {
         var3 += this.animateRowHeight(var7, this.NGrm(var1, var7, var2), this.matchesModuleSearch(var1, var7), var4);
      }

      for (Setting var9 : this.panelBuilder.IAS) {
         var3 += this.animateRowHeight(var9, var1.settingHeight(var9, var2) + 2, this.matchesSettingSearch(var1, var9), var4);
      }

      return var3;
   }

   public void drawDropdownColumn(JadeClickGui var1, int var2, int var3, int var4, int var5, int var6) {
      SettingGroup var7 = null;
      Setting var8 = null;
      int var9 = 0;
      int var10 = 0;
      int var11 = var3;

      for (SettingGroup var13 : this.panelBuilder.RpwI) {
         int var14 = this.animatedHeights.get(var13);
         if (var14 > 0) {
            if (this.matchesModuleSearch(var1, var13) && var14 >= 21 && new GuiRect(var2, var11, var4, var14).contains(var5, var6)) {
               var7 = var13;
               var9 = var11;
            } else {
               this.drawDropdownPanel(var1, var13, var2, var11, var4, var14, -10000, -10000);
            }

            var11 += var14;
         }
      }

      int var16 = 0;

      for (Setting var19 : this.panelBuilder.IAS) {
         var16 += this.animatedHeights.get(var19);
      }

      RenderUtils.XNRNki(var2, var11, var2 + var4, var11 + var16, -1509357303);

      for (Setting var20 : this.panelBuilder.IAS) {
         int var15 = this.animatedHeights.get(var20);
         if (var15 > 0) {
            if (this.matchesSettingSearch(var1, var20) && new GuiRect(var2, var11, var4, var15).contains(var5, var6)) {
               var8 = var20;
               var10 = var11;
            } else {
               var1.gvH.MeQu(new GuiRect(var2, var11, var4, var15), this.matchesSettingSearch(var1, var20));
               RenderUtils.pushScissorRect(var2, var11, var4, var15);
               var1.drawDropdownSettingRow(this.panelBuilder.module, var20, var2, var11, var4, -10000, -10000);
               RenderUtils.restoreScissorState();
               var1.gvH.s172();
            }

            var11 += var15;
         }
      }

      var1.clearDropdownGeometry();
      if (var7 != null) {
         this.drawDropdownPanel(var1, var7, var2, var9, var4, this.animatedHeights.get(var7), var5, var6);
      } else if (var8 != null) {
         var1.gvH.MeQu(new GuiRect(var2, var10, var4, this.animatedHeights.get(var8)), true);
         RenderUtils.pushScissorRect(var2, var10, var4, this.animatedHeights.get(var8).intValue());
         var1.drawDropdownSettingRow(this.panelBuilder.module, var8, var2, var10, var4, var5, var6);
         RenderUtils.restoreScissorState();
         var1.gvH.s172();
      }
   }

   private void drawDropdownPanel(JadeClickGui var1, SettingGroup var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      var1.gvH.MeQu(new GuiRect(var3, var4, var5, var6), this.isDropdownExpanded(var1, var2) && this.matchesModuleSearch(var1, var2));
      RenderUtils.pushScissorRect(var3, var4, var5, var6);

      try {
         this.drawModuleDropdown(var1, var2, var3, var4, var5, var6, var7, var8);
      } finally {
         RenderUtils.restoreScissorState();
         var1.gvH.s172();
      }
   }

   private void drawModuleDropdown(JadeClickGui var1, SettingGroup var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      byte var9 = 21;
      GuiRect var10 = new GuiRect(var3, var4, var5, var9);
      GuiRect var11 = new GuiRect(var3 + var5 - 25, var4, 25, var9);
      this.titleBounds.put(var2, var10);
      this.expandButtonBounds.put(var2, var11);
      if (var2.booleanSetting != null && var2.booleanSetting.isToggled()) {
         RenderUtils.XNRNki(var3, var4, var3 + var5, var4 + var9, GuiTheme.withAlpha(124));
      }

      if (var10.contains(var7, var8)) {
         RenderUtils.XNRNki(var3, var4, var3 + var5, var4 + var9, 822083583);
      }

      var1.drawDropdownTitle(var2.title, var3 + 4, var1.centeredTextY(var4, var9, var1.settingFont()), -1182988);
      if (var2.module instanceof Gui) {
         String var12 = var1.dropdownBindLabel(var2.module);
         int var13 = var12.isEmpty() ? 0 : var1.dropdownBindWidth(var12) + 6;
         GuiRect var14 = new GuiRect(var11.x - var13, var4, var13, var9);
         if (!var12.isEmpty()) {
            this.bindLabelBounds.put(var2, var14);
            var1.drawDropdownBind(var12, var14);
         }
      }

      var1.drawDropdownExpand(var11, this.isDropdownExpanded(var1, var2));
      ZIdxbY.drawGlowLine(var3 + 3, var4 + var9 - 0.65F, var5 - 6, 950974140);
      if (var6 > var9) {
         RenderUtils.pushScissorRect(var3, var4 + var9, var5, var6 - var9);
         RenderUtils.XNRNki(var3, var4 + var9, var3 + var5, var4 + var6, -1509357303);
         int var15 = var4 + var9 + 4;

         for (Setting var17 : var2.settings) {
            if (this.isSettingVisible(var17)) {
               var15 += var1.drawDropdownSettingRow(var2.module, var17, var3, var15, var5, var7, var8);
            }
         }

         RenderUtils.restoreScissorState();
      }
   }

   public boolean handleDropdownClick(JadeClickGui var1, int var2, int var3, int var4) {
      for (Entry var6 : this.titleBounds.entrySet()) {
         if (((GuiRect)var6.getValue()).contains(var2, var3)) {
            SettingGroup var7 = (SettingGroup)var6.getKey();
            if (!(var7.module instanceof Gui) || var4 != 2 && (var4 != 0 || !this.bindLabelBounds.containsKey(var7) || !this.bindLabelBounds.get(var7).contains(var2, var3))) {
               if (var4 == 1 || var4 == 0 && (var7.booleanSetting == null || this.expandButtonBounds.get(var7).contains(var2, var3))) {
                  var7.expanded = !var7.expanded;
               } else if (var4 == 0 && var7.booleanSetting != null) {
                  var7.booleanSetting.toggle();
                  var7.module.guiButtonToggled(var7.booleanSetting);
                  var1.markUnsaved(var7.module);
               }
            } else {
               var1.bindDropdownGui(var7.module);
            }

            return true;
         }
      }

      return false;
   }
}
