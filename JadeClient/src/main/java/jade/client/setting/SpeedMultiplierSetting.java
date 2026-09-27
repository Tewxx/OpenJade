// Jade recovery: original class: jade.deps.eLz.h9s2hyL2
package jade.client.setting;

public class SpeedMultiplierSetting extends SliderSetting {
   public SpeedMultiplierSetting(String var1) {
      super(var1, "x", 1.0, 0.0, 2.0, 0.01);
   }

   @Override
   public double applyValueCurve(double var1) {
      return var1 <= 1.0 ? var1 : 1.0 + (var1 - 1.0) * 9.0;
   }

   public float getSpeedMultiplier() {
      return (float)this.applyValueCurve(this.getInput());
   }
}
