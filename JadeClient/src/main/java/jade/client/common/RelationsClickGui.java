// Jade recovery: original class: jade.deps.eLz.wBQZcIT
package jade.client.common;

import jade.client.Jade;
import jade.client.command.PlayerNameCompleter;
import jade.client.gui.AnimatedFloat;
import jade.client.gui.ClickGui;
import jade.client.module.Category;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;

public abstract class RelationsClickGui extends ClickGui {
   private static final int PAD = 8;
   private static final int MAX_VISIBLE_ENTRIES = 15;
   private static final int MANAGER_WIDTH = 160;
   private static final int BG_WINDOW = -15066598;
   private static final int BG_PILL = -15066598;
   private static final int BG_PILL_HOVER = -14671840;
   private static final int BG_INPUT = -15658735;
   private static final int TXT = -986896;
   private static final int TXT_DIM = -6645094;
   private static final int TXT_MUTE = -9803158;
   protected final AnimatedFloat usTe3 = new AnimatedFloat(160L);
   protected final Map<RelationEntryKey, GuiRect> relationEntryRemoveRects = new LinkedHashMap<>();
   protected String friendInputText = "";
   protected String enemyInputText = "";
   protected RelationManager$1 IAq;
   protected RelationManager$1 OVUf;
   protected String uay = "";
   protected boolean searchModalOpen;
   protected boolean ssh;
   protected boolean showAllEnemies;
   protected GuiRect DMpe;
   protected GuiRect enemyInputRect;
   protected GuiRect GTnX94;
   protected GuiRect zj9;
   protected GuiRect friendShowMoreRect;
   protected GuiRect lB7;
   protected GuiRect sTwoz;
   protected GuiRect NHv;
   protected GuiRect ZRE;
   protected GuiRect middleClickToggleRect;
   private final Object toggleLock = new Object();
   private List<String> TWz = new ArrayList<>();
   private int Vca9 = -1;

   protected final boolean isTypingInRelationshipInput() {
      return this.IAq != null;
   }

   protected final boolean isRelationshipInputAt(int var1, int var2) {
      return this.DMpe != null && this.DMpe.contains(var1, var2) || this.enemyInputRect != null && this.enemyInputRect.contains(var1, var2);
   }

   protected final void drawRelationshipSearchModal(GuiRect var1, int var2, int var3) {
      RenderUtils.XNRNki(0.0, 0.0, this.width, this.height, -2046820352);
      this.relationEntryRemoveRects.clear();
      short var4 = 250;
      short var5 = 212;
      int var6 = var1.x + (var1.busF - var4) / 2;
      int var7 = var1.ufe + (var1.HfS - var5) / 2;
      this.sTwoz = new GuiRect(var6, var7, var4, var5);
      this.round(var6 + 2, var7 + 3, var4, var5, 10.0F, 1426063360);
      this.round(var6, var7, var4, var5, 9.0F, -15066598);
      this.edgeOutline(var6, var7, var4, var5, 9.0F);
      String var8 = this.OVUf == RelationManager$1.ENEMY ? "SEARCH ENEMIES" : "SEARCH FRIENDS";
      this.drawHeader(var8, var6 + 14, var7 + 13, -986896);
      this.ZRE = new GuiRect(var6 + var4 - 30, var7 + 10, 18, 18);
      this.round(this.ZRE.x, this.ZRE.ufe, this.ZRE.busF, this.ZRE.HfS, 5.0F, this.ZRE.contains(var2, var3) ? -14079703 : -15658735);
      this.drawCenteredText("x", this.ZRE.getCenterX(), this.ZRE.ufe + 4, -6645094, this.settingFont());
      this.NHv = new GuiRect(var6 + 14, var7 + 42, var4 - 28, 26);
      this.drawConfigTextInput(this.NHv, this.uay, "Search...", true);
      List var9 = this.getFilteredRelations(this.OVUf, this.uay);
      int var10 = var7 + 76;
      int var11 = Math.min(5, var9.size());

      for (int var12 = 0; var12 < var11; var12++) {
         this.XAoF(this.OVUf, (RelationManager$0)var9.get(var12), var6 + 14, var10, var4 - 28, var2, var3);
         var10 += 24;
      }

      if (var9.isEmpty()) {
         this.drawSmall("No matches", var6 + 18, var10 + 5, -9803158);
      }
   }

   protected final int dropdownRelationshipsHeight() {
      int var1 = Math.max(this.getFilteredRelations(RelationManager$1.FRIEND, "").size(), this.getFilteredRelations(RelationManager$1.ENEMY, "").size());
      return 104 + Math.max(2, Math.min(10, var1)) * 24;
   }

   protected final void drawDropdownRelationshipsManager(GuiRect var1, int var2, int var3) {
      int var4 = var1.x + 8;
      int var5 = var1.ufe + 8;
      int var6 = var1.busF - 16;
      this.middleClickToggleRect = new GuiRect(var4, var5, var6, 21);
      boolean var7 = Jade.relationManager != null && Jade.relationManager.isMiddleClickFriends();
      RenderUtils.XNRNki(var4, var5, var4 + var6, var5 + 21, var7 ? GuiTheme.withAlpha(60) : -1509357303);
      if (this.middleClickToggleRect.contains(var2, var3)) {
         RenderUtils.XNRNki(var4, var5, var4 + var6, var5 + 21, 822083583);
      }

      this.drawSmall("Middle-click friends", var4 + 4, this.centeredTextY(var5, 21.0F, this.settingFont()), -1182988);
      this.relationEntryRemoveRects.clear();
      this.friendShowMoreRect = null;
      this.lB7 = null;
      int var8 = (var6 - 10) / 2;
      int var9 = var5 + 76;
      int var10 = Math.max(24, var1.HfS - 96);
      int var11 = Math.max(this.getFilteredRelations(RelationManager$1.FRIEND, "").size(), this.getFilteredRelations(RelationManager$1.ENEMY, "").size());
      this.clampScroll(this.usTe3, var11 * 24, var10);
      this.drawCompactCategoryPanel(RelationManager$1.FRIEND, var4, var5 + 31, var8, var9, var10, var2, var3);
      this.drawCompactCategoryPanel(RelationManager$1.ENEMY, var4 + var8 + 10, var5 + 31, var8, var9, var10, var2, var3);
   }

   private void drawCompactCategoryPanel(RelationManager$1 var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      boolean var9 = var1 == RelationManager$1.FRIEND;
      List var10 = this.getFilteredRelations(var1, "");
      this.drawSmall((var9 ? "Friends" : "Enemies") + "  " + var10.size(), var2 + 4, var3, -1182988);
      GuiRect var11 = new GuiRect(var2, var3 + 17, var4 - 25, 22);
      GuiRect var12 = new GuiRect(var2 + var4 - 22, var3 + 17, 22, 22);
      if (var9) {
         this.DMpe = var11;
         this.GTnX94 = var12;
      } else {
         this.enemyInputRect = var11;
         this.zj9 = var12;
      }

      this.drawConfigTextInput(var11, var9 ? this.friendInputText : this.enemyInputText, "Username", this.IAq == var1);
      GuiIcons.drawIconButton(this, var12, "search", null, var7, var8);
      RenderUtils.pushScissorRect(var2, var5, var4, var6);
      int var13 = var5 - Math.round(this.usTe3.ORMWO());

      for (RelationManager$0 var15 : (java.lang.Iterable<RelationManager$0>) (java.lang.Iterable<?>) (var10)) {
         if (var13 + 24 > var5 && var13 < var5 + var6) {
            GuiRect var16 = new GuiRect(var2, var13, var4, 24);
            GuiRect var17 = new GuiRect(var2 + var4 - 22, var13, 22, 24);
            RenderUtils.XNRNki(var2, var13, var2 + var4, var13 + 24, -1945499126);
            if (var16.contains(var7, var8)) {
               RenderUtils.XNRNki(var2, var13, var2 + var4, var13 + 24, 822083583);
            }

            this.drawSmall(
               this.trimToWidth(var15.ZNKxZ(), var4 - 30, this.settingFont()), var2 + 4, this.centeredTextY(var13, 24.0F, this.settingFont()), -1182988
            );
            GuiIcons.drawIconButton(this, var17, "x", null, var7, var8);
            RenderUtils.XNRNki(var2, var13 + 23, var2 + var4, var13 + 24, 613258909);
            if (var8 >= var5 && var8 < var5 + var6) {
               this.relationEntryRemoveRects.put(new RelationEntryKey(var1, var15.ZNKxZ()), var17);
            }
         }

         var13 += 24;
      }

      RenderUtils.restoreScissorState();
      GuiIcons.drawRectBorder(var2, var5, var4, var6, 613258909);
   }

   protected final void drawRelationshipsManager(GuiRect var1, int var2, int var3) {
      int var4 = var1.x + this.sidebarW();
      int var5 = var4 + 8;
      int var6 = var1.ufe + 8;
      int var7 = var1.busF - this.sidebarW() - 16;
      int var8 = var1.HfS - 16;
      byte var9 = 8;
      int var10 = (var7 - var9) / 2;
      byte var11 = 28;
      int var12 = Math.max(0, this.computeCategoryPanelHeight(RelationManager$1.FRIEND, var10, this.ssh));
      var12 = Math.max(var12, this.computeCategoryPanelHeight(RelationManager$1.ENEMY, var10, this.showAllEnemies));
      var12 += var11 + var9;
      this.clampScroll(this.usTe3, var12, var8);
      int var13 = Math.round(this.usTe3.ORMWO());
      RenderUtils.pushScissorRect(var5 - 1, var6 - 1, var7 + 2, var8 + 2);
      this.SWFc(var5, var6 - var13, var7, var2, var3);
      int var14 = var6 + var11 + var9 - var13;
      this.drawCategoryPanel(RelationManager$1.FRIEND, var5, var14, var10, var2, var3, this.ssh);
      this.drawCategoryPanel(RelationManager$1.ENEMY, var5 + var10 + var9, var14, var10, var2, var3, this.showAllEnemies);
      RenderUtils.restoreScissorState();
      this.drawScrollFade(Category.friends, var5, var6, var7, var8, var12, var13);
   }

   protected final boolean handleRelationshipsClick(int var1, int var2, int var3) {
      if (this.selectedCategory() == Category.friends && (this.normalizedSearch().isEmpty() || this.searchCategoryScoped())) {
         GuiRect var4 = this.windowRect();
         int var5 = var4.x + this.sidebarW();
         if (var1 >= var5 && var1 < var4.x + var4.busF && var2 >= var4.ufe && var2 < var4.ufe + var4.HfS) {
            if (var3 != 0) {
               return true;
            } else if (this.middleClickToggleRect != null && this.middleClickToggleRect.contains(var1, var2)) {
               if (Jade.relationManager != null) {
                  Jade.relationManager.ADaZ8(!Jade.relationManager.isMiddleClickFriends());
               }

               return true;
            } else if (this.GTnX94 != null && this.GTnX94.contains(var1, var2)) {
               this.VzuG(RelationManager$1.FRIEND);
               return true;
            } else if (this.zj9 != null && this.zj9.contains(var1, var2)) {
               this.VzuG(RelationManager$1.ENEMY);
               return true;
            } else if (this.DMpe != null && this.DMpe.contains(var1, var2)) {
               this.IAq = RelationManager$1.FRIEND;
               this.resetNameSuggestions();
               return true;
            } else if (this.enemyInputRect != null && this.enemyInputRect.contains(var1, var2)) {
               this.IAq = RelationManager$1.ENEMY;
               this.resetNameSuggestions();
               return true;
            } else if (this.friendShowMoreRect != null && this.friendShowMoreRect.contains(var1, var2)) {
               this.ssh = !this.ssh;
               return true;
            } else if (this.lB7 != null && this.lB7.contains(var1, var2)) {
               this.showAllEnemies = !this.showAllEnemies;
               return true;
            } else {
               for (Entry var7 : this.relationEntryRemoveRects.entrySet()) {
                  if (((GuiRect)var7.getValue()).contains(var1, var2)) {
                     this.removeRelationEntry((RelationEntryKey)var7.getKey());
                     return true;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected final void handleRelationshipSearchModalClick(int var1, int var2, int var3) {
      if (var3 == 0) {
         if (this.sTwoz != null && this.sTwoz.contains(var1, var2)) {
            if (this.ZRE != null && this.ZRE.contains(var1, var2)) {
               this.closeRelationshipSearch();
            } else {
               for (Entry var5 : this.relationEntryRemoveRects.entrySet()) {
                  if (((GuiRect)var5.getValue()).contains(var1, var2)) {
                     this.removeRelationEntry((RelationEntryKey)var5.getKey());
                     return;
                  }
               }
            }
         } else {
            this.closeRelationshipSearch();
         }
      }
   }

   protected final void handleRelationshipSearchModalKey(char var1, int var2) {
      if (var2 == 1) {
         this.closeRelationshipSearch();
      } else {
         this.uay = this.editConfigText(this.uay, var1, var2);
      }
   }

   protected final void handleRelationshipInputKey(char var1, int var2) {
      if (var2 == 15) {
         this.cycleNameSuggestion();
      } else {
         this.resetNameSuggestions();
         if (var2 == 1) {
            this.IAq = null;
         } else if (var2 != 28 && var2 != 156) {
            if (this.IAq == RelationManager$1.FRIEND) {
               this.friendInputText = this.editConfigText(this.friendInputText, var1, var2);
            } else if (this.IAq == RelationManager$1.ENEMY) {
               this.enemyInputText = this.editConfigText(this.enemyInputText, var1, var2);
            }
         } else {
            this.submitRelationshipInput(this.IAq);
         }
      }
   }

   private void cycleNameSuggestion() {
      String var1 = this.IAq == RelationManager$1.FRIEND ? this.friendInputText : this.enemyInputText;
      if (!this.TWz.isEmpty() && this.ISYw(this.TWz, var1)) {
         this.Vca9 = (this.Vca9 + 1) % this.TWz.size();
      } else {
         this.TWz = PlayerNameCompleter.completePlayerNames(var1);
         this.Vca9 = 0;
      }

      if (this.TWz.isEmpty()) {
         this.Vca9 = -1;
      } else {
         String var2 = this.TWz.get(this.Vca9);
         if (this.IAq == RelationManager$1.FRIEND) {
            this.friendInputText = var2;
         } else {
            this.enemyInputText = var2;
         }
      }
   }

   private boolean ISYw(List<String> var1, String var2) {
      for (String var4 : var1) {
         if (var4.equalsIgnoreCase(var2)) {
            return true;
         }
      }

      return false;
   }

   private void resetNameSuggestions() {
      this.TWz = new ArrayList<>();
      this.Vca9 = -1;
   }

   protected final void closeRelationshipSearch() {
      this.searchModalOpen = false;
      this.uay = "";
      this.OVUf = null;
   }

   private void drawCategoryPanel(RelationManager$1 var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      boolean var8 = var1 == RelationManager$1.FRIEND;
      this.drawHeader(var8 ? "Friends" : "Enemies", var2 + 3, var3 + 3, -6645094);
      GuiRect var9 = new GuiRect(var2, var3 + 24, var4, 26);
      GuiRect var10 = new GuiRect(var9.x + var9.busF - 25, var9.ufe + 4, 18, 18);
      if (var8) {
         this.DMpe = var9;
         this.GTnX94 = var10;
      } else {
         this.enemyInputRect = var9;
         this.zj9 = var10;
      }

      String var11 = var8 ? this.friendInputText : this.enemyInputText;
      this.drawRelationshipInput(var9, var10, var11, this.IAq == var1, var5, var6);
      List var12 = this.getFilteredRelations(var1, "");
      int var13 = var7 ? var12.size() : Math.min(15, var12.size());
      int var14 = var3 + 57;

      for (int var15 = 0; var15 < var13; var15++) {
         this.XAoF(var1, (RelationManager$0)var12.get(var15), var2, var14, var4, var5, var6);
         var14 += 24;
      }

      if (var12.size() > 15) {
         GuiRect var17 = new GuiRect(var2, var14 + 3, var4, 23);
         if (var8) {
            this.friendShowMoreRect = var17;
         } else {
            this.lB7 = var17;
         }

         boolean var16 = var17.contains(var5, var6);
         this.round(var17.x, var17.ufe, var17.busF, var17.HfS, 6.0F, var16 ? -14671840 : -15066598);
         this.drawCenteredText(
            var7 ? "show less" : "show more", var17.getCenterX(), this.centeredTextY(var17.ufe, var17.HfS, this.settingFont()), -6645094, this.settingFont()
         );
      }
   }

   private void SWFc(int var1, int var2, int var3, int var4, int var5) {
      this.middleClickToggleRect = new GuiRect(var1, var2, var3, 28);
      boolean var6 = Jade.relationManager != null && Jade.relationManager.isMiddleClickFriends();
      boolean var7 = this.middleClickToggleRect.contains(var4, var5);
      this.round(var1, var2, var3, 28.0F, 6.0F, var7 ? -14671840 : -15066598);
      this.drawSmall("Middle-click friends", var1 + 9, this.centeredTextY(var2, 28.0F, this.settingFont()), var6 ? -986896 : -6645094);
      byte var8 = 22;
      byte var9 = 12;
      int var10 = var1 + var3 - var8 - 8;
      int var11 = var2 + (28 - var9) / 2;
      this.drawRelationshipToggle(var10, var11, var6, var8, var9, this.toggleLock);
   }

   private void drawRelationshipInput(GuiRect var1, GuiRect var2, String var3, boolean var4, int var5, int var6) {
      this.round(var1.x, var1.ufe, var1.busF, var1.HfS, 7.0F, var4 ? -15395563 : -15658735);
      if (var4) {
         RoundedRect.drawRoundedOutline(var1.x, var1.ufe, var1.busF, var1.HfS, 7.0F, 0.12F, new Color(0, 0, 0, 0), new Color(-1761607681, true));
      }

      String var7 = var3 == null ? "" : var3;
      if (!var7.isEmpty() || !var4) {
         String var8 = var7.isEmpty() ? "username" : var7;
         int var9 = var7.isEmpty() ? -11184811 : -986896;
         this.drawText(
            this.trimToWidth(var8, var1.busF - 38, this.settingFont()),
            var1.x + 8,
            this.centeredTextY(var1.ufe, var1.HfS, this.settingFont()),
            var9,
            this.settingFont()
         );
      }

      if (var4 && System.currentTimeMillis() / 500L % 2L == 0L) {
         int var10 = var1.x
            + 8
            + Math.min(var1.busF - 40, this.textWidth(this.trimToWidth(var3 == null ? "" : var3, var1.busF - 40, this.settingFont()), this.settingFont()))
            + 2;
         RenderUtils.XNRNki(var10, var1.ufe + 7, var10 + 1, var1.ufe + var1.HfS - 7, -6645094);
      }

      boolean var11 = var2.contains(var5, var6);
      this.round(var2.x, var2.ufe, var2.busF, var2.HfS, 5.0F, var11 ? -14408668 : -15395563);
      this.drawSearchIcon(var2.x + 3, var2.ufe + 3, 12, -9803158);
   }

   private void XAoF(RelationManager$1 var1, RelationManager$0 var2, int var3, int var4, int var5, int var6, int var7) {
      GuiRect var8 = new GuiRect(var3, var4, var5, 20);
      boolean var9 = var8.contains(var6, var7);
      this.round(var8.x, var8.ufe, var8.busF, var8.HfS, 5.0F, var9 ? -14671840 : -15066598);
      this.drawSmall(
         this.trimToWidth(var2.ZNKxZ(), var5 - 34, this.settingFont()), var8.x + 8, this.centeredTextY(var8.ufe, var8.HfS, this.settingFont()), -6645094
      );
      GuiRect var10 = new GuiRect(var8.x + var8.busF - 21, var8.ufe + 3, 14, 14);
      this.relationEntryRemoveRects.put(new RelationEntryKey(var1, var2.ZNKxZ()), var10);
      this.drawCenteredText("x", var10.getCenterX(), var10.ufe + 3, var9 ? -6645094 : -9803158, this.settingFont());
   }

   private int computeCategoryPanelHeight(RelationManager$1 var1, int var2, boolean var3) {
      List var4 = this.getFilteredRelations(var1, "");
      int var5 = var3 ? var4.size() : Math.min(15, var4.size());
      int var6 = 57 + var5 * 24;
      if (var4.size() > 15) {
         var6 += 29;
      }

      return var6;
   }

   private List<RelationManager$0> getFilteredRelations(RelationManager$1 var1, String var2) {
      ArrayList var3 = new ArrayList();
      if (Jade.relationManager == null) {
         return var3;
      } else {
         String var4 = this.normalizeSearchToken(var2);

         for (RelationManager$0 var6 : Jade.relationManager.Ayorz(var1)) {
            if (var4.isEmpty() || this.matchesText(var6.ZNKxZ(), var4) || this.matchesText(var6.getPlayerName(), var4)) {
               var3.add(var6);
            }
         }

         return var3;
      }
   }

   private void VzuG(RelationManager$1 var1) {
      this.OVUf = var1;
      this.uay = "";
      this.searchModalOpen = true;
      this.IAq = null;
      this.clearSearchFocus();
   }

   private void submitRelationshipInput(RelationManager$1 var1) {
      if (Jade.relationManager != null && var1 != null) {
         if (var1 == RelationManager$1.FRIEND) {
            if (Jade.relationManager.ZUCSul(var1, this.friendInputText)) {
               this.friendInputText = "";
            }
         } else if (Jade.relationManager.ZUCSul(var1, this.enemyInputText)) {
            this.enemyInputText = "";
         }
      }
   }

   private void removeRelationEntry(RelationEntryKey var1) {
      if (Jade.relationManager != null && var1 != null) {
         Jade.relationManager.removeRelation(var1.relationType, var1.IRi);
      }
   }

   protected abstract int sidebarW();

   protected abstract Category selectedCategory();

   protected abstract boolean searchCategoryScoped();

   protected abstract String normalizedSearch();

   protected abstract GuiRect windowRect();

   protected abstract void clearSearchFocus();

   protected abstract void round(float var1, float var2, float var3, float var4, float var5, int var6);

   protected abstract void edgeOutline(float var1, float var2, float var3, float var4, float var5);

   protected abstract void drawHeader(String var1, float var2, float var3, int var4);

   protected abstract void drawCenteredText(String var1, float var2, float var3, int var4, IFont var5);

   protected abstract IFont settingFont();

   protected abstract void drawConfigTextInput(GuiRect var1, String var2, String var3, boolean var4);

   protected abstract void drawSmall(String var1, float var2, float var3, int var4);

   protected abstract String trimToWidth(String var1, int var2, IFont var3);

   protected abstract float centeredTextY(float var1, float var2, IFont var3);

   protected abstract int textWidth(String var1, IFont var2);

   protected abstract void drawText(String var1, float var2, float var3, int var4, IFont var5);

   protected abstract void drawSearchIcon(int var1, int var2, int var3, int var4);

   protected abstract void drawRelationshipToggle(int var1, int var2, boolean var3, int var4, int var5, Object var6);

   protected abstract String normalizeSearchToken(String var1);

   protected abstract boolean matchesText(String var1, String var2);

   protected abstract void clampScroll(AnimatedFloat var1, int var2, int var3);

   protected abstract void drawScrollFade(Category var1, int var2, int var3, int var4, int var5, int var6, int var7);

   protected abstract String editConfigText(String var1, char var2, int var3);
}
