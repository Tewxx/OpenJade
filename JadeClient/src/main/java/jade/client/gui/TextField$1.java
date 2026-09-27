// Jade recovery: original class: jade.deps.eLz.OcJ6Qju7M$1
package jade.client.gui;

import jade.client.common.IFont;

public final class TextField$1 implements ScrolledTextLayout$2 {
   private final IFont iFont;

   TextField$1(IFont var1) {
      this.iFont = var1;
   }

   @Override
   public float getTextWidth(String var1) {
      return this.iFont.getStringWidth(var1);
   }
}
