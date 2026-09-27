// Jade recovery: recovered class name: PlayerSprintUpdate; original class: jade.mixin.feature.player.M91ae0504dd563c9087120f5ce82c99dc
package jade.mixin.feature.player;

import jade.client.Jade;
import jade.client.common.PlayerPacketStateTracker;
import jade.client.module.combat.SprintReset;
import jade.client.module.movement.NoSlow;
import jade.client.module.movement.Sprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public final class PlayerSprintUpdate {
   private static final float FORWARD_THRESHOLD = 0.8F;

   private PlayerSprintUpdate() {
   }

   public static PlayerSprintUpdate$1 begin(EntityPlayerSP player) {
      MovementInput movement = player.movementInput;
      boolean jumpBefore = movement.jump;
      boolean sneakBefore = movement.sneak;
      boolean forwardBefore = movement.moveForward >= 0.8F;
      movement.updatePlayerMoveState();
      boolean allowsItemSprint = NoSlow.CjyFd(movement.moveForward >= 0.8F);
      boolean bypassesSlowdown = NoSlow.isVanillaModeActive();
      boolean itemMovement = player.isUsingItem() && !bypassesSlowdown;
      if (itemMovement && !player.isRiding()) {
         movement.moveStrafe *= 0.2F;
         movement.moveForward *= 0.2F;
      } else if (bypassesSlowdown && !player.isRiding()) {
         float multiplier = NoSlow.getMovementSpeedMultiplier();
         movement.moveStrafe *= multiplier;
         movement.moveForward *= multiplier;
      }

      return new PlayerSprintUpdate$1(jumpBefore, sneakBefore, forwardBefore, allowsItemSprint, itemMovement);
   }

   public static int apply(EntityPlayerSP player, Minecraft minecraft, PlayerSprintUpdate$1 input, int toggleTimer) {
      boolean itemMustStopSprint = !PlayerSprintUpdate$1.access$100(input);
      if (PlayerSprintUpdate$1.access$200(input) && !player.isRiding() && itemMustStopSprint) {
         toggleTimer = 0;
      }

      boolean fed = player.getFoodStats().getFoodLevel() > 6 || player.capabilities.allowFlying;
      Sprint sprint = Jade.getModuleManager().getModule(Sprint.class);
      boolean airSuppressed = sprint != null && sprint.shouldSuppressSprint();
      if (airSuppressed && player.isSprinting()) {
         player.setSprinting(false);
      }

      MovementInput movement = player.movementInput;
      boolean sprintKey = (minecraft.gameSettings.keyBindSprint.isKeyDown() || PlayerSprintUpdate$1.access$100(input)) && !airSuppressed;
      boolean itemBlocksSprint = (PlayerSprintUpdate$1.access$200(input) || minecraft.thePlayer.isBlocking())
         && !PlayerSprintUpdate$1.access$100(input);
      SprintReset wTap = Jade.getModuleManager().getModule(SprintReset.class);
      boolean wTapActive = wTap != null && wTap.isEnabled();
      boolean awaitingRestart = wTapActive && SprintReset.isAwaitingRestart();
      boolean legalRestart = movement.moveForward >= 0.8F
         && !input.sneakBefore
         && fed
         && !player.isPotionActive(Potion.blindness)
         && (!PlayerSprintUpdate$1.access$200(input) || !itemMustStopSprint || PlayerSprintUpdate$1.access$100(input))
         && !player.isCollidedHorizontally
         && !PlayerPacketStateTracker.airFrictionApplied;
      boolean suppressStart = airSuppressed || wTapActive && (SprintReset.isRestartSuppressed() || awaitingRestart && !legalRestart);
      boolean forwardEnough = movement.moveForward >= 0.8F || PlayerSprintUpdate$1.access$100(input);
      if (!suppressStart
         && player.onGround
         && !input.sneakBefore
         && !PlayerSprintUpdate$1.access$300(input)
         && forwardEnough
         && !player.isSprinting()
         && fed
         && (!PlayerSprintUpdate$1.access$200(input) || !itemMustStopSprint || PlayerSprintUpdate$1.access$100(input))
         && !player.isPotionActive(Potion.blindness)) {
         if (toggleTimer <= 0 && !sprintKey) {
            toggleTimer = 7;
         } else {
            player.setSprinting(true);
         }
      }

      boolean hasMovement = movement.moveForward != 0.0F || movement.moveStrafe != 0.0F;
      if (!suppressStart
         && !player.isSprinting()
         && sprintKey
         && hasMovement
         && forwardEnough
         && fed
         && (!itemBlocksSprint || !itemMustStopSprint)
         && !player.isPotionActive(Potion.blindness)) {
         player.setSprinting(true);
      }

      boolean resetRequested = wTapActive && (SprintReset.resetRequested || SprintReset.isResetInProgress());
      boolean insufficientForward = movement.moveForward < 0.8F && !PlayerSprintUpdate$1.access$100(input);
      boolean fullyStopped = movement.moveForward == 0.0F && movement.moveStrafe == 0.0F;
      boolean mustStop = insufficientForward
         || !fed
         || player.isCollidedHorizontally
         || PlayerPacketStateTracker.airFrictionApplied
         || itemBlocksSprint
         || fullyStopped
         || minecraft.gameSettings.keyBindSneak.isKeyDown()
         || resetRequested;
      if (player.isSprinting() && mustStop) {
         player.setSprinting(false);
         SprintReset.resetRequested = false;
         SprintReset.clearResetInProgress();
      } else if (resetRequested) {
         SprintReset.clearResetInProgress();
      }

      if (awaitingRestart && player.isSprinting()) {
         SprintReset.clearAwaitingRestart();
      }

      SprintReset.decrementRestartSuppression();
      return toggleTimer;
   }
}
