// Jade recovery: original class: jade.deps.eLz.fK2ZmQAP
package jade.client.event;

import net.minecraft.entity.EntityLivingBase;

public final class JumpData {
   private final EntityLivingBase entityLivingBase;
   private float motionY;
   private float yaw;
   private boolean sprinting;

   public JumpData(EntityLivingBase var1, float var2, float var3, boolean var4) {
      this.entityLivingBase = var1;
      this.motionY = var2;
      this.yaw = var3;
      this.sprinting = var4;
   }

   public EntityLivingBase getEntity() {
      return this.entityLivingBase;
   }

   public float getMotionY() {
      return this.motionY;
   }

   public void setMotionY(float var1) {
      this.motionY = var1;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float var1) {
      this.yaw = var1;
   }

   public boolean isSprinting() {
      return this.sprinting;
   }

   public void setSprinting(boolean var1) {
      this.sprinting = var1;
   }
}
