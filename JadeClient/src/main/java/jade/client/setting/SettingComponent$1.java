// Jade recovery: original class: jade.deps.eLz.acSzVn6F$1
package jade.client.setting;

import jade.client.common.SettingRowLayout;

public final class SettingComponent$1 {
   public float listTopY;
   public float contentTopY;
   public float contentRightX;
   public float contentLeftX;
   public float boundsWidth;
   public float boundsY;
   public float boundsX;

   public SettingComponent$1() {
   }

   SettingComponent$1(SettingRowLayout var1) {
      this.boundsX = var1.getX();
      this.boundsY = var1.getY();
      this.boundsWidth = var1.getWidth();
      this.contentLeftX = var1.getControlCenterX();
      this.contentRightX = var1.getRightEdgeX();
      this.contentTopY = var1.getSecondaryRowY();
      this.listTopY = var1.getTertiaryRowY();
   }
}
