// Jade recovery: original class: jade.deps.eLz.FHCLzy6an
package jade.client.common;

import java.awt.Color;

public final class HsbColorPickerState {
   public static final int DRAG_MODE_NONE = 0;
   public static final int DRAG_MODE_SATURATION_BRIGHTNESS = 1;
   public static final int DRAG_MODE_HUE = 2;
   private String targetName;
   private int dragMode;
   private float uS7;
   private float saturation;
   private float brightnessValue;

   public void whL6(String var1, int var2) {
      this.targetName = var1;
      this.dragMode = 0;
      float[] var3 = Color.RGBtoHSB(var2 >>> 16 & 0xFF, var2 >>> 8 & 0xFF, var2 & 0xFF, null);
      this.uS7 = var3[0];
      this.saturation = var3[1];
      this.brightnessValue = var3[2];
   }

   public void kbAd() {
      this.targetName = null;
      this.dragMode = 0;
   }

   public boolean hasTarget() {
      return this.targetName != null;
   }

   public boolean matchesTargetName(String var1) {
      return var1 != null && var1.equals(this.targetName);
   }

   public String getTargetName() {
      return this.targetName;
   }

   public int getDragMode() {
      return this.dragMode;
   }

   public void setDragMode(int var1) {
      this.dragMode = var1;
   }

   public void clearDragMode() {
      this.dragMode = 0;
   }

   public void setSaturationAndBrightness(float var1, float var2) {
      this.saturation = clampUnit(var1);
      this.brightnessValue = clampUnit(var2);
   }

   public void setHue(float var1) {
      this.uS7 = clampUnit(var1);
   }

   public float getHue() {
      return this.uS7;
   }

   public float NJgI() {
      return this.saturation;
   }

   public float getBrightness() {
      return this.brightnessValue;
   }

   public int getPackedColor() {
      return 0xFF000000 | Color.HSBtoRGB(this.uS7, this.saturation, this.brightnessValue) & 16777215;
   }

   private static float clampUnit(float var0) {
      return Math.max(0.0F, Math.min(1.0F, var0));
   }
}
