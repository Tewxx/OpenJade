// Jade recovery: recovered class name: MixinEntity; mixin target: net.minecraft.entity.Entity; original class: jade.mixin.impl.entity.Ma98488cce1f8c584a75af7882ef4b69e
package jade.mixin.impl.entity;

import jade.client.Jade;
import jade.client.common.EventBus;
import jade.client.event.MoveFlyingEvent;
import jade.client.event.PlayerMoveEvent;
import jade.client.event.StepHeightEvent;
import jade.client.event.VectorForRotationEvent;
import jade.client.hook.EntityMotionMath$1;
import jade.client.hook.EntityMotionMath$2;
import jade.client.hook.EntityMotionMath;
import jade.client.module.player.Freecam;
import jade.client.module.player.Scaffold;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity {
   @Shadow
   public double field_70159_w;
   @Shadow
   public double field_70179_y;
   @Shadow
   public float field_70177_z;

   @Inject(method = "setAngles", at = @At("HEAD"), cancellable = true)
   private void jade$routeFreecamAngles(float yaw, float pitch, CallbackInfo ci) {
      if ((Object)this == Minecraft.getMinecraft().thePlayer && Freecam.applyCameraAngles(yaw, pitch)) {
         ci.cancel();
      }
   }

   @ModifyVariable(method = "moveEntity", at = @At(value = "STORE", ordinal = 0), name = "flag")
   private boolean injectSafeWalk(boolean flag) {
      Entity entity = (Entity)(Object)this;
      Minecraft mc = Minecraft.getMinecraft();
      boolean requested = false;
      if (entity != null && entity == mc.thePlayer && entity.onGround) {
         Scaffold scaffold = Jade.getModuleManager().getModule(Scaffold.class);
         requested = scaffold != null && scaffold.isSafeWalkRequested();
      }

      return EntityMotionMath.shouldEnableSafeWalk(flag, entity != null && entity == mc.thePlayer, entity != null && entity.onGround, requested);
   }

   @Overwrite
   public void func_70060_a(float strafe, float forward, float friction) {
      MoveFlyingEvent request = new MoveFlyingEvent(strafe, forward, friction, this.field_70177_z);
      if ((Object)this == Minecraft.getMinecraft().thePlayer) {
         EventBus.post(request);
      }

      EntityMotionMath$2 result = EntityMotionMath.computeMoveFlying(this.field_70159_w, this.field_70179_y, request.getStrafe(), request.getForward(), request.getFriction(), request.osuUy7());
      this.field_70159_w = result.x;
      this.field_70179_y = result.y;
   }

   @Redirect(method = "moveEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;stepHeight:F", opcode = 180, ordinal = 0))
   private float redirectStepHeight(Entity instance) {
      StepHeightEvent stepHeightEvent = new StepHeightEvent(instance, instance.stepHeight);
      EventBus.post(stepHeightEvent);
      return stepHeightEvent.stepHeight;
   }

   @Overwrite
   public final Vec3 func_174806_f(float pitch, float yaw) {
      VectorForRotationEvent request = new VectorForRotationEvent(yaw, pitch);
      EventBus.post(request);
      EntityMotionMath$1 vector = EntityMotionMath.computeVectorForRotation(request.MHWu, request.yaw);
      return new Vec3(vector.VPEUXL, vector.cHhj, vector.iam);
   }

   @Inject(method = "moveEntity", at = @At("HEAD"))
   private void injectPlayerMoveEvent(double x, double y, double z, CallbackInfo ci) {
      if ((Object)this instanceof EntityPlayerSP) {
         EventBus.post(new PlayerMoveEvent(x, y, z));
      }
   }
}
