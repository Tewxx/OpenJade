// Jade recovery: original class: jade.deps.eLz.HDv3ZtRA
package jade.client.common;

import net.minecraft.entity.Entity;

public final class EntityRotationOverride {
   private float originalYaw;
   private float bCl;
   private float originalPitch;
   private float XXa;

   public void applyRotation(Entity var1, float var2, float var3, boolean var4) {
      this.originalYaw = var1.rotationYaw;
      this.bCl = var1.prevRotationYaw;
      this.originalPitch = var1.rotationPitch;
      this.XXa = var1.prevRotationPitch;
      var1.rotationYaw = var2;
      var1.prevRotationYaw = var2;
      if (var4) {
         var1.rotationPitch = var3;
         var1.prevRotationPitch = var3;
      }
   }

   public void oIpxn(Entity var1) {
      var1.rotationYaw = this.originalYaw;
      var1.prevRotationYaw = this.bCl;
      var1.rotationPitch = this.originalPitch;
      var1.prevRotationPitch = this.XXa;
   }
}
