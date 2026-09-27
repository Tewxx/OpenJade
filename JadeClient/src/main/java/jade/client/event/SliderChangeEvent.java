// Jade recovery: original class: jade.deps.eLz.lIakBsY6z8
package jade.client.event;

public class SliderChangeEvent extends Event {
   public double oldValue;
   public double newValue;

   public SliderChangeEvent(double var1, double var3) {
      this.newValue = var3;
      this.oldValue = var1;
   }
}
