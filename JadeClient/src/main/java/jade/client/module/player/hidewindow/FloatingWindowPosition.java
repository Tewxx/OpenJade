// Jade recovery: original class: jade.deps.eLz.BsFfPt9Y
package jade.client.module.player.hidewindow;

public final class FloatingWindowPosition {
   private final float defaultFractionX;
   private final float BxiNg;
   private float SDfeh = Float.NaN;
   private float pixelY = Float.NaN;
   private float uKakP = Float.NaN;
   private float XeC = Float.NaN;

   public FloatingWindowPosition(float var1, float var2) {
      this.defaultFractionX = var1;
      this.BxiNg = var2;
   }

   public void updateForScreenSize(int var1, int var2) {
      int var3 = Math.max(1, var1);
      int var4 = Math.max(1, var2);
      if (Float.isNaN(this.uKakP) || Float.isNaN(this.XeC)) {
         if (!Float.isNaN(this.SDfeh) && !Float.isNaN(this.pixelY)) {
            this.uKakP = this.SDfeh / var3;
            this.XeC = this.pixelY / var4;
         } else {
            this.uKakP = this.defaultFractionX;
            this.XeC = this.BxiNg;
         }
      }

      this.SDfeh = this.uKakP * var3;
      this.pixelY = this.XeC * var4;
   }

   public void setFractionPosition(float var1, float var2, int var3, int var4) {
      this.uKakP = var1;
      this.XeC = var2;
      this.updateForScreenSize(var3, var4);
   }

   public void setPixelPosition(float var1, float var2, int var3, int var4) {
      this.SDfeh = var1;
      this.pixelY = var2;
      this.uKakP = var1 / Math.max(1, var3);
      this.XeC = var2 / Math.max(1, var4);
   }

   public void aTls(int var1, int var2) {
      this.setFractionPosition(this.defaultFractionX, this.BxiNg, var1, var2);
   }

   public FloatingWindowPosition$1 createSnapshot() {
      return new FloatingWindowPosition$1(this.SDfeh, this.pixelY, this.uKakP, this.XeC);
   }

   public void LDUKrN(FloatingWindowPosition$1 var1) {
      this.SDfeh = FloatingWindowPosition$1.getPixelX(var1);
      this.pixelY = FloatingWindowPosition$1.QlcE(var1);
      this.uKakP = FloatingWindowPosition$1.getFractionX(var1);
      this.XeC = FloatingWindowPosition$1.NWd3(var1);
   }

   public float getPixelX() {
      return this.SDfeh;
   }

   public float getPixelY() {
      return this.pixelY;
   }

   public float getFractionX() {
      return this.uKakP;
   }

   public float getFractionY() {
      return this.XeC;
   }
}
