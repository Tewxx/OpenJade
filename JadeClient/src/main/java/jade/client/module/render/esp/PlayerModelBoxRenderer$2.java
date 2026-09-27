// Jade recovery: original class: jade.deps.eLz.b6e0eBZ9$2
package jade.client.module.render.esp;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public final class PlayerModelBoxRenderer$2 {
   public float rightLegRotX;
   public float RGtc;
   public float rightLegRotZ;
   public float gKyq;
   public float leftLegRotY;
   public float leftLegRotZ;
   public float rightArmRotX;
   public float PYK;
   public float rightArmRotZ;
   public float ULu;
   public float leftArmRotY;
   public float fwB;

   public void captureModelRotations(ModelBiped var1) {
      this.rightLegRotX = var1.bipedRightLeg.rotateAngleX;
      this.RGtc = var1.bipedRightLeg.rotateAngleY;
      this.rightLegRotZ = var1.bipedRightLeg.rotateAngleZ;
      this.gKyq = var1.bipedLeftLeg.rotateAngleX;
      this.leftLegRotY = var1.bipedLeftLeg.rotateAngleY;
      this.leftLegRotZ = var1.bipedLeftLeg.rotateAngleZ;
      this.rightArmRotX = var1.bipedRightArm.rotateAngleX;
      this.PYK = var1.bipedRightArm.rotateAngleY;
      this.rightArmRotZ = var1.bipedRightArm.rotateAngleZ;
      this.ULu = var1.bipedLeftArm.rotateAngleX;
      this.leftArmRotY = var1.bipedLeftArm.rotateAngleY;
      this.fwB = var1.bipedLeftArm.rotateAngleZ;
   }

   public void applyLimbSwing(EntityPlayer var1, float var2) {
      float var3 = var1.prevLimbSwingAmount + (var1.limbSwingAmount - var1.prevLimbSwingAmount) * var2;
      float var4 = var1.limbSwing - var1.limbSwingAmount * (1.0F - var2);
      this.rightLegRotX = MathHelper.cos(var4 * 0.6662F) * 1.4F * var3;
      this.gKyq = MathHelper.cos(var4 * 0.6662F + (float) Math.PI) * 1.4F * var3;
      this.rightArmRotX = this.gKyq;
      this.ULu = this.rightLegRotX;
      this.RGtc = this.rightLegRotZ = this.leftLegRotY = this.leftLegRotZ = 0.0F;
      this.PYK = this.rightArmRotZ = this.leftArmRotY = this.fwB = 0.0F;
   }
}
