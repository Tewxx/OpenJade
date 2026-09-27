// Jade recovery: original class: jade.deps.eLz.moaBAd
package jade.client.hook;

import net.minecraft.entity.Entity;

public final class EntityRotationOverride {
   private Entity entity;
   private float xFxr4;
   private float savedPrevRotationYaw;
   private float savedRotationPitch;
   private float weyDo;

   public void nZugr() {
      this.entity = null;
   }

   public void applyRotation(Entity var1, float var2, float var3) {
      this.entity = var1;
      if (var1 != null) {
         this.xFxr4 = var1.rotationYaw;
         this.savedPrevRotationYaw = var1.prevRotationYaw;
         this.savedRotationPitch = var1.rotationPitch;
         this.weyDo = var1.prevRotationPitch;
         var1.rotationYaw = var1.prevRotationYaw = var2;
         var1.rotationPitch = var1.prevRotationPitch = var3;
      }
   }

   public void restoreRotation() {
      if (this.entity != null) {
         this.entity.rotationYaw = this.xFxr4;
         this.entity.prevRotationYaw = this.savedPrevRotationYaw;
         this.entity.rotationPitch = this.savedRotationPitch;
         this.entity.prevRotationPitch = this.weyDo;
         this.nZugr();
      }
   }
}
