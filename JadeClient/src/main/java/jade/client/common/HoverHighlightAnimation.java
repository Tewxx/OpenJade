// Jade recovery: original class: jade.deps.eLz.OqHD5Ixr
package jade.client.common;

public final class HoverHighlightAnimation {
   private boolean hovered;
   private boolean fadeInStarted;
   private boolean animating;
   private boolean GSSpZw;
   private long animationStartTime;

   public void JMHl(boolean var1, long var2) {
      if (var1) {
         this.hovered = true;
         if (!this.animating) {
            this.ZpEvk(var2);
            this.fadeInStarted = true;
         }
      } else {
         if (this.hovered && this.fadeInStarted) {
            this.ZpEvk(var2);
         }

         this.fadeInStarted = false;
         this.hovered = false;
      }
   }

   public boolean isAnimating() {
      return this.hovered || this.animating;
   }

   public float UJTRN(long var1) {
      float var3 = 120.0F;
      if (this.animating) {
         float var4 = (float)(var1 - this.animationStartTime) / 75.0F;
         float var5 = this.GSSpZw ? 120.0F : Math.min(120.0F, Easing.easeByType(1, var4) * 120.0F);
         if (var5 == 120.0F) {
            this.GSSpZw = true;
         }

         var3 = this.hovered ? var5 : 120.0F - var5;
      }

      if (var3 == 0.0F) {
         this.animating = false;
      }

      return var3;
   }

   public void vBj5() {
      this.animating = false;
   }

   private void ZpEvk(long var1) {
      this.animationStartTime = var1;
      this.animating = true;
      this.GSSpZw = false;
   }
}
