// Jade recovery: original class: jade.deps.eLz.BSjs5Um96A
package jade.client.common;

import jade.client.gui.AnimatedFloat;
import jade.client.module.Category;
import jade.client.module.Module;
import java.util.IdentityHashMap;
import java.util.Map;

public final class CategoryPanel {
   public final Category category;
   public final AnimatedFloat animatedFloat = new AnimatedFloat(160L);
   public int panelX;
   public int panelY;
   public boolean tFj;
   public final SmoothedFloat hoverAnimation = new SmoothedFloat(1.0F);
   public final Map<Module, SmoothedFloat> moduleAnimations = new IdentityHashMap<>();
   public final SmoothedFloat expandAnimation = new SmoothedFloat(0.0F);
   public GuiRect MFruB = new GuiRect(0, 0, 0, 0);

   public CategoryPanel(Category var1, int var2, int var3) {
      this.category = var1;
      this.panelX = var2;
      this.panelY = var3;
   }
}
