// Jade recovery: original class: jade.deps.eLz.kzqzwW
package jade.client.hook;

import jade.client.common.EventBus;
import jade.client.common.RotationUtils;
import jade.client.event.JumpEvent;
import jade.client.event.MoveEntityWithHeadingEvent;
import jade.client.event.UpdateWalkingPlayerEvent;
import jade.client.module.client.Settings;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public final class MovementEventBridge {
   private MovementEventBridge() {
   }

   public static MovementEventBridge$1 UcuO0(EntityLivingBase var0, float var1, float var2, float var3, float var4) {
      float var5 = var0.rotationYaw;
      boolean var6 = Settings.fullBody != null
         && Settings.rotateBody != null
         && !Settings.fullBody.isToggled()
         && Settings.rotateBody.isToggled()
         && var0 instanceof EntityPlayerSP
         && UpdateWalkingPlayerEvent.isYawOverrideRequested();
      if (var6) {
         var5 = RotationUtils.outgoingYaw;
         if (var4 > 0.0F) {
            var1 = var5;
         }
      }

      PlayerMotionHelper$1 var7 = PlayerMotionHelper.computeBodyRotation(var3, var5, var1, var2);
      return new MovementEventBridge$1(var7.renderYawOffset, var7.cutR, var6 ? var5 : Float.NaN);
   }

   public static PlayerMotionHelper$2 jumpWithEvent(EntityLivingBase var0, float var1) {
      JumpEvent var2 = new JumpEvent(var0, var1, var0.rotationYaw, var0.isSprinting());
      EventBus.post(var2);
      if (var2.isCanceled()) {
         return null;
      } else {
         int var3 = var0.isPotionActive(Potion.jump) ? var0.getActivePotionEffect(Potion.jump).getAmplifier() : -1;
         return PlayerMotionHelper.computeJumpMotion(var0.motionX, var0.motionZ, var2.MCqU(), var3, var2.isSprinting(), var2.glMe());
      }
   }

   public static void redirectMoveEntityWithHeading(EntityLivingBase var0, float var1, float var2) {
      if (!(var0 instanceof EntityPlayerSP)) {
         var0.moveEntityWithHeading(var1, var2);
      } else {
         MoveEntityWithHeadingEvent var3 = new MoveEntityWithHeadingEvent(var2, var1);
         EventBus.post(var3);
         var0.moveEntityWithHeading(var3.vjoL8, var3.ljWi1);
      }
   }
}
