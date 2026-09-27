// Jade recovery: original class: jade.deps.eLz.StJE5bfXd
package jade.client.event;

import net.minecraft.entity.Entity;

public class StepHeightEvent extends Event {
   public Entity entity;
   public float stepHeight;

   public StepHeightEvent(Entity var1, float var2) {
      this.stepHeight = var2;
      this.entity = var1;
   }
}
