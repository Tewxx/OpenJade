// Jade recovery: original class: jade.deps.eLz.mMEIOuvT8C
package jade.client.common;

import jade.client.Jade;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.deps.gson.JsonObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import org.lwjgl.input.Keyboard;

public final class DropdownPanelManager {
   private final List<CategoryPanel> categoryPanels = new ArrayList<>();
   private final Map<String, GuiRect> menuButtonBounds = new LinkedHashMap<>();
   private final Map<String, SmoothedFloat> menuButtonHoverFade = new LinkedHashMap<>();
   private final BlurFramebufferCapture blurRenderer = new BlurFramebufferCapture();
   private CategoryPanel draggedPanel;
   private int dragOffsetX;
   private int dragOffsetY;
   private int panOffsetX;
   private int screenWidth;
   private int Exm;
   private int panelTopMargin = 48;
   private int panelWidth = 600;
   private int claK = 300;
   private GuiRect Nnr;
   private GuiRect openCategoryBounds;
   private GuiRect fin = new GuiRect(14, 12, 200, 24);
   private Category openCategory;
   private Category pendingCategory;
   private boolean closingCategory;
   private SmoothedFloat sgO = new SmoothedFloat(1.0F);
   private float whI1 = 1.0F;

   public DropdownPanelManager() {
      Category[] var1 = new Category[]{
         Category.combat, Category.player, Category.movement, Category.render, Category.minigames, Category.other, Category.client
      };

      for (int var2 = 0; var2 < var1.length; var2++) {
         this.categoryPanels.add(new CategoryPanel(var1[var2], 14 + var2 * 194, this.panelTopMargin));
      }
   }

   public GuiRect getSearchFieldBounds() {
      return this.fin;
   }

   public boolean hasOpenCategory() {
      return this.openCategory != null;
   }

   public Category getOpenCategory() {
      return this.openCategory;
   }

   public GuiRect JjDy() {
      int var1 = Math.min(this.panelWidth, Math.max(200, this.screenWidth - 28));
      int var2 = Math.min(this.claK, Math.max(120, this.Exm - this.panelTopMargin - 70));
      return new GuiRect((this.screenWidth - var1) / 2, Math.max(this.panelTopMargin + 23, (this.Exm - var2) / 2), var1, var2);
   }

   public void VJvs2(JadeClickGui var1, int var2, int var3, int var4, int var5) {
      this.screenWidth = var2;
      this.Exm = var3;
      this.whI1 = this.sgO.smoothTowards(this.closingCategory ? 0.0F : 1.0F);
      if (this.closingCategory && this.whI1 <= 0.001F) {
         this.openCategory = this.pendingCategory;
         this.closingCategory = false;
         if (this.openCategory != null) {
            var1.showCategoryAfterProfileLoad(this.openCategory);
            if (this.openCategory == Category.themes) {
               var1.beginDropdownThemes();
               if (Jade.themeManager != null) {
                  Jade.themeManager.reloadCustomThemes();
               }
            }
         }

         this.sgO = new SmoothedFloat(0.0F);
         this.whI1 = this.sgO.smoothTowards(1.0F);
         var1.clearDropdownGeometry();
      }

      this.CPMlM(var1, var2, var4, var5);
      if (this.closingCategory || this.whI1 < 0.99F) {
         var4 = -10000;
         var5 = -10000;
      }

      this.blurRenderer.beginBlurCapture(this.whI1);

      try {
         this.renderPanelLayer(var1, var2, var3, var4, var5);
      } finally {
         var1.endDropdownFade(this.blurRenderer, this.whI1);
      }
   }

   private void renderPanelLayer(JadeClickGui var1, int var2, int var3, int var4, int var5) {
      if (this.openCategory != null) {
         int[] var9 = var1.dropdownManagerSize(this.openCategory);
         this.panelWidth = var9[0];
         this.claK = var9[1];
         GuiRect var11 = this.JjDy();
         this.openCategoryBounds = var11;
         RenderUtils.pushScissorRect(var11.x, var11.ufe - 23, var11.busF, var11.HfS + 23);
         RenderUtils.XNRNki(var11.x, var11.ufe, var11.x + var11.busF, var11.ufe + var11.HfS, -233828336);
         RenderUtils.drawVerticalGradient(var11.x, var11.ufe - 23, var11.x + var11.busF, var11.ufe, GuiTheme.xGoxa(), GuiTheme.getShadedAccentColor());
         String var13 = CategoryNames.getDisplayName(this.openCategory);
         var1.drawDropdownTitle(
            var13, var11.getCenterX() - var1.textWidth(var13, var1.settingFont()) / 2.0F, var1.centeredTextY(var11.ufe - 23, 23.0F, var1.settingFont()), -1182988
         );
         var1.drawDropdownManager(this.openCategory, var11, var4, var5);
         GuiIcons.drawRectBorder(var11.x, var11.ufe, var11.busF, var11.HfS, 613258909);
         RenderUtils.restoreScissorState();
      } else {
         if (this.draggedPanel != null) {
            this.draggedPanel.panelX = Math.max(this.panOffsetX + 4, Math.min(this.panOffsetX + var2 - 185 - 4, var4 + this.panOffsetX - this.dragOffsetX));
            this.draggedPanel.panelY = Math.max(this.panelTopMargin, Math.min(var3 - 65, var5 - this.dragOffsetY));
         }

         this.panOffsetX = Math.max(0, Math.min(this.getMaxPanOffset(), this.panOffsetX));
         CategoryPanel var6 = this.findPanelAt(var4, var5);

         for (CategoryPanel var8 : this.categoryPanels) {
            if (var8 != var6) {
               this.renderCategoryPanel(var1, var8, var4, var5, false);
            }
         }

         var1.clearDropdownGeometry();
         this.Nnr = null;
         if (var6 != null) {
            this.renderCategoryPanel(var1, var6, var4, var5, true);
         }

         if (this.getMaxPanOffset() > 0) {
            int var10 = Math.max(40, (var2 - 28) * var2 / (var2 + this.getMaxPanOffset()));
            int var12 = 14 + (var2 - 28 - var10) * this.panOffsetX / Math.max(1, this.getMaxPanOffset());
            RenderUtils.XNRNki(14.0, var3 - 47, var2 - 14, var3 - 45, 687865855);
            RenderUtils.XNRNki(var12, var3 - 47, var12 + var10, var3 - 45, GuiTheme.withAlpha(170));
         }
      }
   }

   private void CPMlM(JadeClickGui var1, int var2, int var3, int var4) {
      this.menuButtonBounds.clear();
      String[] var5 = this.hasOpenCategory() ? new String[]{"Friends", "Configs", "Themes", "Back"} : new String[]{"Friends", "Configs", "Themes"};
      byte var6 = 34;
      int var7 = Math.min(220, Math.max(110, var2 / 5));
      var7 = Math.max(72, Math.min(var7, var2 - 28 - var5.length * var6));
      int var8 = var7 + var5.length * var6;
      int var9 = Math.max(14, (var2 - var8) / 2);
      byte var10 = 10;
      GuiRect var11 = new GuiRect(var9, var10, var8, 26);
      GuiIcons.fillRect(var11.x, var11.ufe, var11.busF, var11.HfS, -1945499126);
      GuiIcons.drawRectBorder(var11.x, var11.ufe, var11.busF, var11.HfS, 613258909);
      this.fin = new GuiRect(var9, var10, var7, var11.HfS);
      if (this.fin.contains(var3, var4)) {
         GuiIcons.fillRect(this.fin.x + 1, this.fin.ufe + 1, this.fin.busF - 1, this.fin.HfS - 2, 822083583);
      }

      var1.drawDropdownSearch(this.fin);
      var9 += var7;

      for (String var15 : var5) {
         GuiRect var16 = new GuiRect(var9, var10, var6, var11.HfS);
         this.menuButtonBounds.put(var15, var16);
         SmoothedFloat var17 = this.menuButtonHoverFade.get(var15);
         if (var17 == null) {
            var17 = new SmoothedFloat(0.0F);
            this.menuButtonHoverFade.put(var15, var17);
         }

         float var18 = var17.smoothTowards(var16.contains(var3, var4) ? 1.0F : 0.0F);
         Category var19 = this.categoryForMenuLabel(var15);
         boolean var20 = var19 != null && var19 == this.openCategory;
         if (var20) {
            GuiIcons.fillRect(var16.x, var16.ufe, var16.busF, var16.HfS, GuiTheme.withAlpha(64));
         }

         if (var18 > 0.001F) {
            GuiIcons.fillRect(var16.x, var16.ufe, var16.busF, var16.HfS, Math.round(48.0F * var18) << 24 | 16777215);
         }

         GuiIcons.fillRect(var16.x, var16.ufe + 5, 1.0F, var16.HfS - 10, 950974140);
         int var21 = !var20 && !var16.contains(var3, var4) ? -4208434 : -1182988;
         if (var19 != null) {
            var1.drawDropdownCategoryIcon(var19, var16.getCenterX() - 7, var16.getCenterY() - 7, 14, var21);
         } else {
            VectorIconPainter.drawArrowIcon(var16.getCenterX(), var16.getCenterY(), var21);
         }

         if (var16.contains(var3, var4)) {
            int var22 = var1.textWidth(var15, var1.settingFont());
            var1.drawSmall(var15, var16.getCenterX() - var22 / 2.0F, var16.ufe + var16.HfS + 5, -1182988);
         }

         var9 += var6;
      }

      GuiRect var25 = new GuiRect(var2 - 78, this.Exm - 33, 27, 25);
      GuiRect var26 = new GuiRect(var2 - 43, this.Exm - 33, 27, 25);
      this.menuButtonBounds.put("Edit HUD", var25);
      this.menuButtonBounds.put("Disconnect", var26);
      GuiIcons.drawPowerButton(var1, var25, false, var3, var4);
      GuiIcons.drawPowerButton(var1, var26, true, var3, var4);
      int var27 = this.panelTopMargin;
      this.panelTopMargin = var10 + 40;
      if (this.panelTopMargin != var27) {
         for (CategoryPanel var29 : this.categoryPanels) {
            var29.panelY = Math.max(this.panelTopMargin, var29.panelY + this.panelTopMargin - var27);
         }
      }
   }

   private Category categoryForMenuLabel(String var1) {
      if (var1.equals("Friends")) {
         return Category.friends;
      } else if (var1.equals("Configs")) {
         return Category.profiles;
      } else {
         return var1.equals("Themes") ? Category.themes : null;
      }
   }

   private void renderCategoryPanel(JadeClickGui var1, CategoryPanel var2, int var3, int var4, boolean var5) {
      int var6 = var2.panelX - this.panOffsetX;
      int var7 = Math.max(this.panelTopMargin, Math.min(this.Exm - 65, var2.panelY));
      if (var6 + 185 >= 0 && var6 <= this.screenWidth) {
         byte var8 = 4;
         int var9 = var6 + var8;
         int var10 = 185 - var8 * 2;
         List var11 = var1.dropdownModules(var2.category);
         boolean var12 = var2.category == Category.client;
         LinkedHashMap var13 = new LinkedHashMap();
         long var14 = System.currentTimeMillis();
         int var16 = 0;
         boolean var17 = false;

         for (Module var19 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var11)) {
            boolean var20 = var1.dropdownMatchesSearch(var19);
            var17 |= var20;
            SmoothedFloat var21 = var2.moduleAnimations.get(var19);
            if (var21 == null) {
               var21 = new SmoothedFloat(var20 ? 1.0F : 0.0F);
               var2.moduleAnimations.put(var19, var21);
            }

            int var22 = Math.round(var1.dropdownModuleHeight(var19, var10) * var21.smoothTowardsAt(var20 ? 1.0F : 0.0F, var14));
            var13.put(var19, var22);
            var16 += var22;
         }

         int var33 = Math.round(28.0F * var2.expandAnimation.smoothTowardsAt(var17 ? 0.0F : 1.0F, var14));
         var16 += var33;
         if (var12) {
            var16 = var1.dropdownClientHeight(var10);
         }

         var16 += var8 * 2;
         int var34 = Math.max(0, this.Exm - var7 - 23 - 48);
         int var35 = Math.round(Math.min(var34, var16) * var2.hoverAnimation.smoothTowards(var2.tFj ? 0.0F : 1.0F));
         var2.MFruB = new GuiRect(var6, var7, 185, 23 + var35);
         RenderUtils.pushScissorRect(var6, var7, 185.0, var2.MFruB.HfS);
         var1.clampScroll(var2.animatedFloat, var16, var34);
         int var36 = var7 + 23;
         RenderUtils.XNRNki(var6, var36, var6 + 185, var36 + var35, -1945499126);
         if (var35 > 0) {
            int var37 = var36 + var8;
            int var23 = var36 + var35 - var8;
            var1.gvH.MeQu(new GuiRect(var9, var37, var10, Math.max(0, var23 - var37)), !var2.tFj && !this.closingCategory);
            RenderUtils.pushScissorRect(var9, var37, var10, Math.max(0, var23 - var37));
            int var24 = var36 + 4 - Math.round(var2.animatedFloat.ORMWO());
            if (var12) {
               if (var5) {
                  this.Nnr = new GuiRect(var9, var37, var10, Math.max(0, var23 - var37));
               }

               var1.drawDropdownClient(var9, var24, var10, var5 ? var3 : -10000, var5 ? var4 : -10000);
            } else {
               Module var25 = null;
               int var26 = 0;
               int var27 = 0;

               for (Module var29 : (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (var11)) {
                  int var30 = (Integer)var13.get(var29);
                  if (var30 > 0 && var24 + var30 >= var37 && var24 < var23) {
                     if (var5
                        && var1.dropdownMatchesSearch(var29)
                        && var30 >= 21
                        && var3 >= var9
                        && var3 < var9 + var10
                        && var4 >= Math.max(var37, var24)
                        && var4 < Math.min(var23, var24 + var30)) {
                        var25 = var29;
                        var26 = var24;
                        var27 = var30;
                     } else {
                        RenderUtils.pushScissorRect(var9, var24, var10, var30);
                        var1.drawDropdownModule(var29, var9, var24, var10, var30, -10000, -10000);
                        RenderUtils.restoreScissorState();
                     }
                  }

                  var24 += var30;
               }

               if (var5) {
                  var1.clearDropdownGeometry();
                  if (var25 != null) {
                     int var41 = Math.max(var37, var26);
                     this.Nnr = new GuiRect(var9, var41, var10, Math.min(var23, var26 + var27) - var41);
                     var1.drawDropdownModule(var25, var9, var26, var10, var27, var3, var4);
                  }
               }

               if (var33 > 0) {
                  RenderUtils.pushScissorRect(var9, var24, var10, var33);
                  var1.drawSmall("No matching modules", var6 + 8, var24 + 9, -4208434);
                  RenderUtils.restoreScissorState();
               }
            }

            RenderUtils.restoreScissorState();
            var1.gvH.s172();
            GuiIcons.drawRectBorder(var6, var36, 185.0F, var35, 613258909);
            if (var16 > var34 && var35 > 0) {
               int var39 = Math.min(var35, Math.max(12, var35 * var35 / var16));
               int var40 = Math.round((var35 - var39) * var2.animatedFloat.ORMWO() / Math.max(1, var16 - var35));
               RenderUtils.XNRNki(var6 + 185 - 2, var36 + var40, var6 + 185 - 1, var36 + var40 + var39, GuiTheme.withAlpha(130));
            }
         }

         RenderUtils.drawVerticalGradient(var6, var7, var6 + 185, var36, GuiTheme.xGoxa(), GuiTheme.getShadedAccentColor());
         RenderUtils.XNRNki(var6, var7, var6 + 185, var7 + 1, 654311423);
         String var38 = var2.category == Category.movement ? "Movement" : CategoryNames.getDisplayName(var2.category);
         var1.drawDropdownTitle(
            var38, var6 + (185 - var1.textWidth(var38, var1.settingFont())) / 2.0F, var1.centeredTextY(var7, 23.0F, var1.settingFont()), -1182988
         );
         var1.drawSmall(var2.tFj ? "+" : "-", var6 + 185 - 15, var1.centeredTextY(var7, 23.0F, var1.settingFont()), -1182988);
         RenderUtils.restoreScissorState();
      }
   }

   public boolean handleMouseClick(JadeClickGui var1, int var2, int var3, int var4) {
      if (var4 == 0 && this.fin.contains(var2, var3)) {
         var1.focusDropdownSearch();
         return true;
      } else {
         for (Entry var6 : this.menuButtonBounds.entrySet()) {
            if (var4 == 0 && ((GuiRect)var6.getValue()).contains(var2, var3)) {
               String var7 = (String)var6.getKey();
               if (var7.equals("Friends")) {
                  this.toggleCategory(var1, Category.friends);
               } else if (var7.equals("Configs")) {
                  this.toggleCategory(var1, Category.profiles);
               } else if (var7.equals("Themes")) {
                  this.toggleCategory(var1, Category.themes);
               } else if (var7.equals("Back")) {
                  this.beginCategorySwitch(null);
               } else if (var7.equals("Edit HUD")) {
                  var1.openDropdownHud();
               } else if (var7.equals("Disconnect")) {
                  var1.openDropdownDisconnect();
               }

               return true;
            }
         }

         if (this.closingCategory || this.whI1 < 0.99F) {
            return true;
         } else if (this.hasOpenCategory()) {
            return false;
         } else {
            CategoryPanel var8 = this.findPanelAt(var2, var3);
            if (var8 == null) {
               return false;
            } else if (var3 >= var8.MFruB.ufe + 23) {
               return false;
            } else {
               if (var4 == 1 || var4 == 0 && var2 >= var8.MFruB.x + 185 - 24) {
                  var8.tFj = !var8.tFj;
               } else if (var4 == 0) {
                  this.draggedPanel = var8;
                  this.dragOffsetX = var2 + this.panOffsetX - var8.panelX;
                  this.dragOffsetY = var3 - var8.panelY;
                  this.categoryPanels.remove(var8);
                  this.categoryPanels.add(var8);
               }

               return true;
            }
         }
      }
   }

   public boolean isMouseOverOpenContent(int var1, int var2) {
      return !this.closingCategory
         && this.whI1 >= 0.99F
         && (this.hasOpenCategory() ? this.openCategoryBounds != null && this.openCategoryBounds.contains(var1, var2) : this.Nnr != null && this.Nnr.contains(var1, var2));
   }

   public void endPanelDrag() {
      this.draggedPanel = null;
   }

   public boolean closeOpenCategory() {
      if (this.openCategory == null && !this.closingCategory) {
         return false;
      } else {
         this.beginCategorySwitch(null);
         return true;
      }
   }

   public void resetState() {
      this.draggedPanel = null;
      this.openCategory = this.pendingCategory = null;
      this.closingCategory = false;
      this.sgO = new SmoothedFloat(1.0F);
      this.whI1 = 1.0F;
      this.blurRenderer.jbgV();
      this.menuButtonHoverFade.clear();

      for (CategoryPanel var2 : this.categoryPanels) {
         var2.moduleAnimations.clear();
      }
   }

   private void beginCategorySwitch(Category var1) {
      this.pendingCategory = var1;
      this.closingCategory = true;
      this.draggedPanel = null;
      this.sgO.smoothTowards(0.0F);
   }

   public void toggleCategory(JadeClickGui var1, Category var2) {
      this.beginCategorySwitch((this.closingCategory ? this.pendingCategory : this.openCategory) == var2 ? null : var2);
   }

   public void handleMouseScroll(int var1, int var2, int var3) {
      if (!this.closingCategory && !(this.whI1 < 0.99F)) {
         if (!Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54) && var2 <= this.Exm - 51) {
            CategoryPanel var4 = this.findPanelAt(var1, var2);
            if (var4 != null && !var4.tFj) {
               var4.animatedFloat.addToTarget(var3 > 0 ? -42.0F : 42.0F);
            }
         } else {
            this.panOffsetX = Math.max(0, Math.min(this.getMaxPanOffset(), this.panOffsetX + (var3 > 0 ? -65 : 65)));
         }
      }
   }

   public void focusCategoryPanel(Category var1) {
      for (CategoryPanel var3 : this.categoryPanels) {
         if (var3.category == var1) {
            var3.tFj = false;
            var3.animatedFloat.snapTo(0.0F);
            this.panOffsetX = Math.max(0, Math.min(this.getMaxPanOffset(), var3.panelX - 14));
         }
      }
   }

   private CategoryPanel findPanelAt(int var1, int var2) {
      for (int var3 = this.categoryPanels.size() - 1; var3 >= 0; var3--) {
         if (this.categoryPanels.get(var3).MFruB.contains(var1, var2)) {
            return this.categoryPanels.get(var3);
         }
      }

      return null;
   }

   private int getMaxPanOffset() {
      int var1 = 0;

      for (CategoryPanel var3 : this.categoryPanels) {
         var1 = Math.max(var1, var3.panelX + 185 + 14);
      }

      return Math.max(0, var1 - this.screenWidth);
   }

   private void resetPanelPositions() {
      int var1 = 0;

      for (Category var5 : new Category[]{
         Category.combat, Category.player, Category.movement, Category.render, Category.minigames, Category.other, Category.client
      }) {
         for (CategoryPanel var7 : this.categoryPanels) {
            if (var7.category == var5) {
               var7.panelX = 14 + var1++ * 194;
               var7.panelY = this.panelTopMargin;
               var7.tFj = false;
               var7.animatedFloat.snapTo(0.0F);
            }
         }
      }

      this.panOffsetX = 0;
   }

   public JsonObject ZZYR() {
      JsonObject var1 = new JsonObject();
      var1.addProperty("pan", this.panOffsetX);

      for (CategoryPanel var3 : this.categoryPanels) {
         JsonObject var4 = new JsonObject();
         var4.addProperty("x", var3.panelX);
         var4.addProperty("y", var3.panelY);
         var4.addProperty("collapsed", var3.tFj);
         var1.add(var3.category.name(), var4);
      }

      return var1;
   }

   public void WoR0(JsonObject var1) {
      for (CategoryPanel var3 : this.categoryPanels) {
         if (var1.has(var3.category.name()) && var1.get(var3.category.name()).isJsonObject()) {
            JsonObject var4 = var1.getAsJsonObject(var3.category.name());
            if (var4.has("x")) {
               var3.panelX = Math.max(4, Math.min(10000, var4.get("x").getAsInt()));
            }

            if (var4.has("y")) {
               var3.panelY = Math.max(this.panelTopMargin, Math.min(10000, var4.get("y").getAsInt()));
            }

            if (var4.has("collapsed")) {
               var3.tFj = var4.get("collapsed").getAsBoolean();
            }
         }
      }

      if (var1.has("pan")) {
         this.panOffsetX = Math.max(0, var1.get("pan").getAsInt());
      }
   }
}
