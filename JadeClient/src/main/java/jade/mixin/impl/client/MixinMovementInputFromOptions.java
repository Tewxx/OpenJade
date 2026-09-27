// Jade recovery: recovered class name: MixinMovementInputFromOptions; mixin target: net.minecraft.util.MovementInputFromOptions; original class: jade.mixin.impl.client.M0c40a89cd8f4eaf393181a45a8ec812e
package jade.mixin.impl.client;

import jade.client.common.EventBus;
import jade.client.event.MoveInputEvent;
import jade.client.event.MoveStateUpdateEvent;
import jade.client.hook.MovementMathHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MovementInputFromOptions.class)
public class MixinMovementInputFromOptions extends MovementInput {
   @Shadow
   @Final
   private GameSettings field_78903_e;

   @Overwrite
   public void func_78898_a() {
      float rawForward = MovementMathHelper.rkEdkDk(this.field_78903_e.keyBindForward.isKeyDown(), this.field_78903_e.keyBindBack.isKeyDown());
      float rawStrafe = MovementMathHelper.rkEdkDk(this.field_78903_e.keyBindLeft.isKeyDown(), this.field_78903_e.keyBindRight.isKeyDown());
      boolean rawJump = this.field_78903_e.keyBindJump.isKeyDown();
      boolean rawSneak = this.field_78903_e.keyBindSneak.isKeyDown();
      MoveInputEvent input = new MoveInputEvent(rawForward, rawStrafe, rawJump, rawSneak, 0.3);
      EventBus.post(input);
      double slowdown = input.BFAN();
      float eventForward = input.getMoveForward();
      float eventStrafe = input.AfWugH();
      this.jump = input.isJumping();
      this.sneak = input.isSneaking();
      this.moveForward = MovementMathHelper.applySneakSlowdown(eventForward, this.sneak, slowdown);
      this.moveStrafe = MovementMathHelper.applySneakSlowdown(eventStrafe, this.sneak, slowdown);
   }

   @Inject(method = "updatePlayerMoveState", at = @At("RETURN"))
   private void onUpdatePlayerMoveState(CallbackInfo ci) {
      EventBus.post(new MoveStateUpdateEvent());
   }
}
