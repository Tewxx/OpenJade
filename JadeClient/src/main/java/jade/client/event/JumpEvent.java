// Jade recovery: original class: jade.deps.eLz.CNRVDj7GE
package jade.client.event;

import net.minecraft.entity.EntityLivingBase;

public class JumpEvent extends Event {
   private final JumpData jumpData;

   public JumpEvent(EntityLivingBase var1, float var2, float var3, boolean var4) {
      this.jumpData = new JumpData(var1, var2, var3, var4);
   }

   public EntityLivingBase getEntityLiving() {
      return this.jumpData.getEntity();
   }

   public float MCqU() {
      return this.jumpData.getMotionY();
   }

   public void setMotionY(float var1) {
      this.jumpData.setMotionY(var1);
   }

   public float glMe() {
      return this.jumpData.getYaw();
   }

   public void setYaw(float var1) {
      this.jumpData.setYaw(var1);
   }

   public boolean isSprinting() {
      return this.jumpData.isSprinting();
   }

   public void setSprinting(boolean var1) {
      this.jumpData.setSprinting(var1);
   }
}
