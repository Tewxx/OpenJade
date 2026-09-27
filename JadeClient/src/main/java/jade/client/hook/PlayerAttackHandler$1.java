// Jade recovery: original class: jade.deps.eLz.YqbZpQlmg$1
package jade.client.hook;

import net.minecraft.entity.Entity;

public final class PlayerAttackHandler$1 {
   public final double savedMotionX;
   public final double uWj;
   public final double savedMotionZ;

   public PlayerAttackHandler$1(Entity var1) {
      this.savedMotionX = var1.motionX;
      this.uWj = var1.motionY;
      this.savedMotionZ = var1.motionZ;
   }

   public void restoreMotion(Entity var1) {
      var1.motionX = this.savedMotionX;
      var1.motionY = this.uWj;
      var1.motionZ = this.savedMotionZ;
   }
}
