// Jade recovery: recovered class name: MixinEntityPlayer; mixin target: net.minecraft.entity.player.EntityPlayer; original class: jade.mixin.impl.entity.M2b2b91503c2e41111845a65118fbd873
package jade.mixin.impl.entity;

import jade.client.hook.ItemUseHelper;
import jade.client.hook.PlayerAttackHandler;
import jade.client.module.render.Cape;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {
   public MixinEntityPlayer(World p_i1594_1_) {
      super(p_i1594_1_);
   }

   @Overwrite
   public void func_71059_n(Entity targetEntity) {
      PlayerAttackHandler.attackTarget((EntityPlayer)(Object)this, targetEntity);
   }

   @Inject(method = "isBlocking", at = @At("RETURN"), cancellable = true)
   private void isBlocking(CallbackInfoReturnable<Boolean> cir) {
      EntityPlayer player = (EntityPlayer)(Object)this;
      if (!cir.getReturnValue() && ItemUseHelper.shouldForceItemUse(player, player.getHeldItem())) {
         cir.setReturnValue(true);
      }
   }

   @Inject(method = "isWearing", at = @At("RETURN"), cancellable = true)
   private void jade$overrideCapeWearState(EnumPlayerModelParts playerModelParts, CallbackInfoReturnable<Boolean> cir) {
      if (playerModelParts == EnumPlayerModelParts.CAPE && (Object)this == Minecraft.getMinecraft().thePlayer && Cape.isCapeEnabled()) {
         cir.setReturnValue(true);
      }
   }

   @Inject(method = "getItemInUseCount", at = @At("RETURN"), cancellable = true)
   private void getItemInUseCount(CallbackInfoReturnable<Integer> cir) {
      if (cir.getReturnValue() == 0 && ItemUseHelper.Ujb4((EntityPlayer)(Object)this, ((EntityPlayer)(Object)this).getHeldItem())) {
         cir.setReturnValue(1);
      }
   }

   @Inject(method = "isUsingItem", at = @At("RETURN"), cancellable = true)
   private void isUsingItem(CallbackInfoReturnable<Boolean> cir) {
      if (!cir.getReturnValue() && ItemUseHelper.Ujb4((EntityPlayer)(Object)this, ((EntityPlayer)(Object)this).getHeldItem())) {
         cir.setReturnValue(true);
      }
   }
}
