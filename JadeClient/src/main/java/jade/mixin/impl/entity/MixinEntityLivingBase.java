// Jade recovery: recovered class name: MixinEntityLivingBase; mixin target: net.minecraft.entity.EntityLivingBase; original class: jade.mixin.impl.entity.M3105ed62e3af203a350341565463177f
package jade.mixin.impl.entity;

import jade.client.hook.PlayerMotionHelper$2;
import jade.client.hook.MovementEventBridge$1;
import jade.client.hook.MovementEventBridge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {
   @Shadow
   public float field_70759_as;
   @Shadow
   public float field_70761_aq;
   @Shadow
   public float field_70733_aJ;

   public MixinEntityLivingBase(World worldIn) {
      super(worldIn);
   }

   @Inject(method = {"updateDistance", "func_110146_f"}, at = @At("HEAD"), cancellable = true)
   protected void injectUpdateDistance(float p_110146_1_, float p_110146_2_, CallbackInfoReturnable<Float> cir) {
      MovementEventBridge$1 result = MovementEventBridge.UcuO0((EntityLivingBase)(Object)this, p_110146_1_, p_110146_2_, this.field_70761_aq, this.field_70733_aJ);
      this.field_70761_aq = result.jlX;
      if (result.hasForcedYaw()) {
         this.field_70759_as = result.forcedRotationYawHead;
      }

      cir.setReturnValue(result.updateDistanceResult);
   }

   @Shadow(aliases = "func_175134_bD")
   protected float getJumpUpwardsMotion() {
      return 0.42F;
   }

   @Overwrite
   public void func_70664_aZ() {
      PlayerMotionHelper$2 motion = MovementEventBridge.jumpWithEvent((EntityLivingBase)(Object)this, this.getJumpUpwardsMotion());
      if (motion != null) {
         this.motionX = motion.motionX;
         this.motionY = motion.motionY;
         this.motionZ = motion.AvpU;
         this.isAirBorne = true;
      }
   }

   @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;moveEntityWithHeading(FF)V"))
   private void onMoveEntityWithHeadingRedirect(EntityLivingBase self, float originalStrafing, float originalForward) {
      MovementEventBridge.redirectMoveEntityWithHeading(self, originalStrafing, originalForward);
   }
}
