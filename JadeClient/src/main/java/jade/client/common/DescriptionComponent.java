// Jade recovery: original class: jade.deps.eLz.FKffiqG8
package jade.client.common;

import jade.client.gui.Component;
import jade.client.module.client.Gui;
import jade.client.module.render.arraylist.ColorTheme;
import jade.client.setting.DescriptionSetting;
import org.lwjgl.opengl.GL11;

public class DescriptionComponent extends Component {
   public DescriptionSetting descriptionSetting;
   private final ModuleComponent moduleComponent;
   public float brZoc;
   public float rightEdgeX;
   public float rowY;

   public DescriptionComponent(DescriptionSetting var1, ModuleComponent var2, float var3) {
      this.descriptionSetting = var1;
      this.moduleComponent = var2;
      this.rightEdgeX = var2.categoryComponent.EWZxNc() + var2.categoryComponent.getPanelWidth();
      this.rowY = var2.categoryComponent.getPanelY() + var2.HPu;
      this.brZoc = var3;
   }

   @Override
   public void renderComponent() {
      IFont var1 = Gui.getSettingFont();
      oEmqQwelhb var2 = oEmqQwelhb.computeScaledPosition(this.moduleComponent.categoryComponent.EWZxNc(), this.moduleComponent.categoryComponent.getPanelY(), this.brZoc);
      GL11.glPushMatrix();
      GL11.glScaled(0.5, 0.5, 0.5);
      var1.drawString(this.descriptionSetting.getDescriptionText(), var2.getScaledX(), var2.getScaledY(), ColorTheme.getGradient(ColorTheme.descriptor[0], ColorTheme.descriptor[1], 0.0), true);
      GL11.glPopMatrix();
   }

   @Override
   public void setY(float var1) {
      this.brZoc = var1;
   }

   @Override
   public float getWidth() {
      return this.brZoc;
   }

   @Override
   public boolean ejbAn() {
      return this.descriptionSetting.visible;
   }
}
