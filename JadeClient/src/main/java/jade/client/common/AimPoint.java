// Jade recovery: original class: jade.deps.eLz.LujPrBVr
package jade.client.common;

import net.minecraft.entity.Entity;

public enum AimPoint {
   EYES {
      @Override
      public double offset(Entity var1) {
         return var1.getEyeHeight();
      }
   },
   TORSO {
      @Override
      public double offset(Entity var1) {
         return var1.height * 0.5;
      }
   },
   FEET {
      @Override
      public double offset(Entity var1) {
         return 0.0;
      }
   },
   NATURAL {
      @Override
      public double offset(Entity var1) {
         return var1.getEyeHeight();
      }
   };

   private AimPoint() {
   }

   public abstract double offset(Entity var1);
}
