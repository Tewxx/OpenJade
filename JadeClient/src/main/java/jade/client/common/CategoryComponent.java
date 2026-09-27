// Jade recovery: original class: jade.deps.eLz.PMoqHTj
package jade.client.common;

import jade.client.Jade;
import jade.client.gui.AnimatedFloat;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.client.Gui;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.gui.FontRenderer;

public class CategoryComponent {
   private static long AWgP;
   private static final CategoryIcons categoryIcons = new CategoryIcons();
   public List<ModuleComponent> moduleComponents = new CopyOnWriteArrayList<>();
   public Category category;
   public boolean expanded;
   public float EHA;
   public float TFmJ1;
   public float panelX;
   public float headerHeight;
   public boolean dragging;
   public float dragOffsetX;
   public float dragOffsetY;
   public boolean hovered = false;
   public boolean Exg = false;
   public Animation animation;
   public float contentHeight;
   public float animatedPanelY;
   private float lastY;
   private float lastX;
   private final DualAnimation jy1;
   private final AnimatedFloat animatedFloat = new AnimatedFloat(200L);
   public long Cs2 = 0L;

   public CategoryComponent(Category var1) {
      this.category = var1;
      this.EHA = 92.0F;
      this.panelX = 5.0F;
      this.animatedPanelY = this.TFmJ1 = 5.0F;
      this.headerHeight = 13.0F;
      float var2 = this.headerHeight + 3.0F;
      this.animatedFloat.snapTo(this.animatedPanelY);
      this.jy1 = new DualAnimation(this.TFmJ1 + this.headerHeight + 4.0F);

      for (Module var4 : Jade.getModuleManager().getModulesInCategory(this.category)) {
         ModuleComponent var5 = new ModuleComponent(var4, this, var2);
         this.moduleComponents.add(var5);
         var2 += 16.0F;
      }
   }

   public List<ModuleComponent> getModuleComponents() {
      return this.moduleComponents;
   }

   public String getDisplayName() {
      return this.category == Category.profiles ? "configs" : this.category.name();
   }

   public void open() {
      CategoryModuleRefresher.refreshCategoryModules(this, true, false);
      this.updateAnimationState();
   }

   public void close(boolean var1) {
      if (CategoryModuleRefresher.refreshCategoryModules(this, false, var1)) {
         this.updateAnimationState();
      }
   }

   private void updateAnimationState() {
      TooltipLayout$1 var1 = this.measureModuleLayout(this.expanded || this.animation != null);
      float var2 = var1.adjustedX;
      float var3 = this.TFmJ1;
      float var4 = Math.max(var2, Math.min(var3, this.animatedFloat.getTargetValue()));
      this.animatedPanelY = var4;
      this.animatedFloat.snapTo(var4);
      if (this.expanded && !this.moduleComponents.isEmpty()) {
         this.contentHeight = var1.contentHeight;
         this.jy1.WUjb(var1.rightEdge);
      } else {
         if (!this.expanded && this.animation == null) {
            this.contentHeight = 0.0F;
         }

         this.jy1.WUjb(this.TFmJ1 + this.headerHeight + 4.0F);
      }
   }

   public void setPanelX(float var1, boolean var2) {
      if (var2) {
         var1 = HoverUtils.nrje(var1, this.lastX, this.EHA);
      }

      this.panelX = var1;
   }

   public void setPanelY(float var1, boolean var2) {
      if (var2) {
         var1 = HoverUtils.clampVerticalPosition(var1, this.lastY, this.headerHeight, false);
      }

      float var3 = this.animatedFloat.getTargetValue() - this.TFmJ1;
      this.TFmJ1 = var1;
      float var4 = var1 + var3;
      this.animatedPanelY = var4;
      this.animatedFloat.snapTo(var4);
   }

   public void setDragging(boolean var1) {
      this.dragging = var1;
   }

   public boolean isExpanded() {
      return this.expanded;
   }

   public void bGdbO() {
      this.Cs2 = ++AWgP;
   }

   public void zDmaqhA(boolean var1) {
      float var2 = this.BQOR();
      float var3 = this.getTitleY();
      this.expanded = var1;
      this.animation = this.jy1.retarget(var2, var3);
   }

   public void jhXm(int var1) {
      this.onMouseScroll(var1, Float.NaN, Float.NaN);
   }

   public void onMouseScroll(int var1, float var2, float var3) {
      for (ModuleComponent var5 : this.moduleComponents) {
         var5.keyPressed(var1);
      }

      if (CategoryScrollHelper.shouldHandleScroll(this.Exg, this.expanded, var2, var3, () -> this.hasModuleAt(var2, var3))) {
         this.bGdbO();
         float var7 = this.getMinWidth();
         float var8 = this.TFmJ1;
         float var6 = CategoryScrollHelper.getScrollAmount(var1);
         if (var6 != 0.0F) {
            this.animatedFloat.addToTarget(var6);
         }

         this.animatedFloat.clampTarget(var7, var8);
      }
   }

   private float getMinWidth() {
      return this.measureModuleLayout(false).adjustedX;
   }

   public void render(FontRenderer var1) {
      this.EHA = 92.0F;
      IFont var2 = Gui.getHeaderFont();
      this.animation = DualAnimation.expireIfStale(this.animation, System.currentTimeMillis());
      this.jy1.tickAnimations(System.currentTimeMillis());

      for (ModuleComponent var4 : this.moduleComponents) {
         var4.updateHeightAnimation();
      }

      TooltipLayout$1 var9 = this.measureModuleLayout(this.expanded || this.animation != null);
      this.contentHeight = !this.expanded && this.animation == null ? 0.0F : var9.contentHeight;
      float var10 = this.TFmJ1;
      float var5 = var9.adjustedX;
      this.animatedFloat.clampTarget(var5, var10);
      this.animatedPanelY = this.animatedFloat.ORMWO();
      this.animatedPanelY = Math.max(var5, Math.min(var10, this.animatedPanelY));
      String var6 = this.getDisplayName();
      float var7 = this.panelX + this.EHA / 2.0F - var2.getStringWidth(var6) / 2.0F;
      float var8 = this.jy1.xhIe2(this.animation, this.expanded, var9.rightEdge, this.TFmJ1 + this.headerHeight + 4.0F, this.expanded ? var7 : this.panelX + 12.0F);
      CategoryPanelRenderer.renderCategoryPanel(this, var8, this.getDisplayName(), var2, categoryIcons);
   }

   public void recalculateLayout() {
      ModuleListLayout.positionModules(this.moduleComponents, this.headerHeight);
   }

   public float EWZxNc() {
      return this.getLayoutMetric(0);
   }

   public float getPanelY() {
      return this.getLayoutMetric(1);
   }

   public float WHmq() {
      return this.getLayoutMetric(2);
   }

   public float getPanelWidth() {
      return this.getLayoutMetric(3);
   }

   private float getLayoutMetric(int var1) {
      if (var1 == 0) {
         return this.panelX;
      } else if (var1 == 1) {
         return this.TFmJ1;
      } else {
         return var1 == 2 ? this.animatedPanelY : this.EHA;
      }
   }

   public void updateDrag(int var1, int var2, boolean var3) {
      NVkyouf$1 var4 = NVkyouf.computeHoverState(
         var1, var2, var3, this.dragging, this.dragOffsetX, this.dragOffsetY, this.panelX, this.TFmJ1, this.EHA, this.headerHeight, this.contentHeight, this.lastX, this.lastY
      );
      if (this.dragging) {
         this.setPanelX(var4.clampedX, false);
         this.setPanelY(var4.clampedY, false);
      }

      this.hovered = var4.hovered;
      this.Exg = var4.hoveredExpanded;
   }

   public boolean isMouseOverPanel(int var1, int var2) {
      return HoverHelper.isHovering(var1, var2, this.panelX, this.TFmJ1, this.EHA, this.headerHeight);
   }

   public boolean isOverTitle(int var1, int var2) {
      return HoverHelper.isHoveringExpanded(var1, var2, this.panelX, this.TFmJ1, this.EHA, this.headerHeight, this.contentHeight);
   }

   public boolean isOverHeader(int var1, int var2) {
      return HoverHelper.isInside(var1, var2, this.panelX, this.TFmJ1, this.EHA, this.headerHeight);
   }

   public boolean DHEXX(int var1, int var2) {
      return HoverHelper.isInsideExpanded(var1, var2, this.panelX, this.TFmJ1, this.EHA, this.jy1.getCurrentValue());
   }

   private float getTitleY() {
      if (this.jy1.TXCvi1()) {
         return this.jy1.getSecondaryValue(0.0F);
      } else {
         float var1 = this.panelX + this.EHA / 2.0F - Gui.getHeaderFont().getStringWidth(this.getDisplayName()) / 2.0F;
         return this.expanded ? var1 : this.panelX + 12.0F;
      }
   }

   private float BQOR() {
      if (this.jy1.getCurrentValue() > 0.0F) {
         return this.jy1.getCurrentValue();
      } else {
         return this.moduleComponents.isEmpty() || !this.expanded && this.animation == null ? this.TFmJ1 + this.headerHeight + 4.0F : ModuleListLayout.OvpqA(this.moduleComponents, this.TFmJ1, this.headerHeight);
      }
   }

   public void setHoverOrigins(float var1, float var2) {
      this.lastX = var1;
      this.lastY = var2;
   }

   public void feedOnc() {
      this.setPanelX(this.panelX, true);
      this.setPanelY(this.TFmJ1, true);
   }

   public void restoreState(float var1, float var2, boolean var3, boolean var4) {
      if (var4) {
         this.setPanelX(var1, true);
         this.setPanelY(var2, true);
      } else {
         float var5 = this.animatedFloat.getTargetValue() - this.TFmJ1;
         this.panelX = var1;
         this.TFmJ1 = var2;
         float var6 = var2 + var5;
         this.animatedPanelY = var6;
         this.animatedFloat.snapTo(var6);
      }

      this.expanded = var3;
      this.animation = null;
      this.jy1.clearAnimation();
      if (var3 && !this.moduleComponents.isEmpty()) {
         TooltipLayout$1 var7 = this.measureModuleLayout(true);
         this.contentHeight = var7.contentHeight;
         this.jy1.WUjb(var7.rightEdge);
      } else {
         this.contentHeight = 0.0F;
         this.jy1.WUjb(this.TFmJ1 + this.headerHeight + 4.0F);
      }

      this.animatedPanelY = this.TFmJ1;
      this.animatedFloat.snapTo(this.TFmJ1);
   }

   public void mlvAc() {
      if (this.animation != null || this.jy1.TXCvi1()) {
         float var1 = this.TFmJ1 + this.headerHeight;
         if (this.expanded && !this.moduleComponents.isEmpty()) {
            var1 = ModuleListLayout.OvpqA(this.moduleComponents, this.TFmJ1, this.headerHeight);
         } else {
            var1 += 4.0F;
         }

         this.jy1.WUjb(var1);
      }

      this.animation = null;
      this.jy1.clearAnimation();
      this.animatedPanelY = this.animatedFloat.getTargetValue();
      this.animatedFloat.snapTo(this.animatedPanelY);
   }

   private TooltipLayout$1 measureModuleLayout(boolean var1) {
      return ModuleListLayout.measureModules(this.moduleComponents, this.expanded, this.animation != null, this.TFmJ1, this.headerHeight, this.lastY, var1);
   }

   private boolean hasModuleAt(float var1, float var2) {
      return CategoryScrollHelper.isHoveringListComponent(this.moduleComponents, var1, var2);
   }
}
