// Jade recovery: original class: jade.deps.eLz.GWw9rGE5h
package jade.client.common;

import jade.client.command.PlayerNameCompleter;
import jade.client.module.render.arraylist.ColorTheme;
import jade.client.setting.SettingComponent$1;
import jade.client.setting.SettingComponent;
import jade.client.setting.RelationListSetting;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class PlayerListComponent extends SettingComponent {
   private static final int iTe = 7;
   private static final float ENTRY_LIST_TOP_OFFSET = 4.0F;
   private static final float ENTRY_ROW_HEIGHT = 11.0F;
   private static final float ROW_LABEL_FONT_SCALE = 0.56F;
   private static final int HEAD_ICON_SIZE = 8;
   private static final int azS = 6;
   private static final float CLOSE_ICON_RIGHT_OFFSET = 3.0F;
   private static final String CLOSE_ICON_TEXTURE = "/assets/jade/textures/gui/close.png";
   private final RelationListSetting playerListSetting;
   private final ListScrollController WXh = new ListScrollController(7, 12.0F);
   private final SuggestionCycler nameCompleter = new SuggestionCycler();

   public PlayerListComponent(RelationListSetting var1, ModuleComponent var2, float var3) {
      super(var2, var3, var1.getInputHint(), var1.getMaxNameLength());
      this.playerListSetting = var1;
   }

   @Override
   public void renderComponent() {
      SettingComponent$1 var1 = this.computeSettingBounds(false);
      this.drawLabelText(var1, this.playerListSetting.getName());
      this.drawTextInputBackground(var1);
      this.renderEntryRows(var1);
   }

   @Override
   public void updateLayout(int var1, int var2) {
      super.updateLayout(var1, var2);
      this.WXh.BtQv(var1, var2);
      this.WXh.updateScrollRange(this.playerListSetting.getRelations().size());
   }

   @Override
   public boolean mouseClicked(int var1, int var2, int var3) {
      if (!this.isExpandedAndVisible()) {
         return false;
      } else {
         SettingComponent$1 var4 = this.computeSettingBounds(true);
         if (var3 == 0 && this.isMouseOverTextInput(var1, var2, var4)) {
            this.setTextInputFocused(true);
            return true;
         } else if (var3 == 0 && this.handleRowDeleteClick(var1, var2, var4)) {
            return true;
         } else {
            this.cancelEditing();
            return false;
         }
      }
   }

   @Override
   public void keyTyped(char var1, int var2) {
      if (this.moduleComponent.Nqe && this.isTextInputFocused()) {
         if (var2 == 1) {
            this.cancelEditing();
         } else if (var2 == 28 || var2 == 156) {
            this.submitEntry();
            this.setTextInputFocused(false);
         } else if (var2 == 15) {
            this.applyNameSuggestion();
         } else {
            this.nameCompleter.reset();
            this.getSearchTextField().keyTyped(var1, var2);
         }
      }
   }

   @Override
   public void keyPressed(int var1) {
      if (this.isExpandedAndVisible()) {
         this.WXh.onScroll(var1, this.playerListSetting.getRelations().size(), this.isScrollListHovered(this.WXh.DUpzmA(), this.WXh.getDragStartY()));
      }
   }

   @Override
   public void resetEditingState() {
      super.resetEditingState();
      this.getSearchTextField().setText("");
      this.nameCompleter.reset();
      this.WXh.resetScroll();
   }

   @Override
   public float getHeight() {
      int var1 = this.playerListSetting.getRelations().size();
      float var2 = var1 == 0 ? 0.0F : 4.0F + LayoutMath.clampScaled(var1, 7, 12.0F);
      return 24.0F + var2;
   }

   @Override
   public boolean ejbAn() {
      return this.playerListSetting.visible;
   }

   @Override
   public String getGroupName() {
      return this.playerListSetting.groupSetting == null ? "" : this.playerListSetting.groupSetting.getName();
   }

   public boolean isScrollListHovered(float var1, float var2) {
      return this.playerListSetting.getRelations().size() > 7 && this.isOverEntryList(var1, var2);
   }

   @Override
   public boolean isMouseOver(int var1, int var2) {
      SettingComponent$1 var3 = this.computeSettingBounds(true);
      return this.isMouseOverTextInput(var1, var2, var3) || this.isOverEntryList(var1, var2);
   }

   public void refreshScrollState() {
      this.WXh.updateScrollRange(this.playerListSetting.getRelations().size());
   }

   private boolean isExpandedAndVisible() {
      return this.moduleComponent.Nqe && this.moduleComponent.isComponentVisible(this);
   }

   private void cancelEditing() {
      if (this.isTextInputFocused()) {
         this.getSearchTextField().setText("");
         this.setTextInputFocused(false);
      }
   }

   private void submitEntry() {
      String var1 = this.getSearchTextField().getText();
      if (var1 != null && !var1.trim().isEmpty()) {
         if (this.playerListSetting.addRelation(var1)) {
            this.getSearchTextField().setText("");
         }

         this.moduleComponent.invalidateCategoryLayout();
         this.WXh.updateScrollRange(this.playerListSetting.getRelations().size());
      }
   }

   private void applyNameSuggestion() {
      String var1 = this.getSearchTextField().getText();
      String var2 = this.nameCompleter.nextSuggestion(var1, new SuggestionCycler$0() {
         @Override
         public List<String> getSuggestions(String var1) {
            return PlayerNameCompleter.completePlayerNames(var1);
         }
      });
      if (var2 != null) {
         this.getSearchTextField().setText(var2);
      }
   }

   private void renderEntryRows(SettingComponent$1 var1) {
      List var2 = this.playerListSetting.getRelations();
      if (!var2.isEmpty()) {
         float var3 = this.FCpMetY(var1);
         float var4 = LayoutMath.clampScaled(var2.size(), 7, 12.0F);
         float var5 = this.moduleComponent.categoryComponent.WHmq() - var1.boundsY;
         RenderUtils.pushScissorRect(var1.contentLeftX, var3 + var5, var1.contentRightX - var1.contentLeftX, var4);
         float var6 = this.WXh.lGva();
         int var7 = LayoutMath.floorDivideToInt(var6, 12.0F);
         int var8 = LayoutMath.clampEndIndex(var7, var2.size(), 7);
         Map var9 = PlayerInfoIndex.HXMtc();

         for (int var10 = var7; var10 < var8; var10++) {
            RelationManager$0 var11 = (RelationManager$0)var2.get(var10);
            this.renderEntryRow(var11, (NetworkPlayerInfo)var9.get(var11.getPlayerName()), var1.contentLeftX, var1.contentRightX, LayoutMath.KDlh(var3, var6, var10, 12.0F), (var10 & 1) == 0);
         }

         RenderUtils.restoreScissorState();
      }
   }

   private void renderEntryRow(RelationManager$0 var1, NetworkPlayerInfo var2, float var3, float var4, float var5, boolean var6) {
      RenderUtils.XNRNki(var3, var5, var4, var5 + 11.0F, var6 ? -15066582 : -14803410);
      this.renderPlayerHead(var1, var2, var3 + 2.0F, var5 + 1.5F);
      drawScaledSettingText(var1.ZNKxZ(), var3 + 13.0F, computeCenteredTextYAtScale(var5, 11.0F, 0.56F) + 0.56F, -3355444, 0.56F);
      ResourceLocation var7 = RenderUtils.getIconTexture("/assets/jade/textures/gui/close.png");
      if (var7 != null) {
         float var8 = var4 - 6.0F - 3.0F;
         float var9 = var5 + 2.5F;
         RenderUtils.drawIconTexture(var7, var8, var9, 6, ColorTheme.getGradient(ColorTheme.hiddenBind[0], ColorTheme.hiddenBind[1], 0.0));
      }
   }

   private void renderPlayerHead(RelationManager$0 var1, NetworkPlayerInfo var2, float var3, float var4) {
      ResourceLocation var5 = SkinCache.getPlayerSkin(var1.ZNKxZ(), var2);
      if (var5 != null) {
         RenderUtils.finishItemRenderState();
         Minecraft.getMinecraft().getTextureManager().bindTexture(var5);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         Gui.drawScaledCustomSizeModalRect((int)var3, (int)var4, 8.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
         Gui.drawScaledCustomSizeModalRect((int)var3, (int)var4, 40.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
      }
   }

   private boolean handleRowDeleteClick(float var1, float var2, SettingComponent$1 var3) {
      List var4 = this.playerListSetting.getRelations();
      float var5 = this.FCpMetY(var3);
      float var6 = this.WXh.lGva();

      for (int var7 = 0; var7 < var4.size(); var7++) {
         float var8 = LayoutMath.KDlh(var5, var6, var7, 12.0F);
         float var9 = var3.contentRightX - 6.0F - 3.0F;
         float var10 = var8 + 2.5F;
         if (LayoutMath.isInsideBoundsInclusive(var1, var2, var9, var9 + 6.0F, var10, 6.0F)) {
            this.playerListSetting.removeRelation(((RelationManager$0)var4.get(var7)).getPlayerName());
            this.moduleComponent.invalidateCategoryLayout();
            this.WXh.updateScrollRange(this.playerListSetting.getRelations().size());
            return true;
         }
      }

      return false;
   }

   private boolean isOverEntryList(float var1, float var2) {
      int var3 = this.playerListSetting.getRelations().size();
      if (var3 == 0) {
         return false;
      } else {
         SettingComponent$1 var4 = this.computeSettingBounds(true);
         return LayoutMath.isInsideBoundsExclusive(var1, var2, var4.contentLeftX, var4.contentRightX, this.FCpMetY(var4), LayoutMath.clampScaled(var3, 7, 12.0F));
      }
   }

   private float FCpMetY(SettingComponent$1 var1) {
      return var1.listTopY + 4.0F;
   }
}
