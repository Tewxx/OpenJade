// Jade recovery: module: Null Move (movement); original class: jade.deps.eLz.clvp7XQQ
package jade.client.module.movement;

import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.Subscribe;
import jade.client.event.MoveInputEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.movement.nullmove.OppositeKeyResolver;

@ModuleInfo
public class NullMove extends Module {
   private final OppositeKeyResolver forwardInputTracker = new OppositeKeyResolver();
   private final OppositeKeyResolver strafeInputTracker = new OppositeKeyResolver();

   public NullMove() {
      super("Null Move", Category.movement, 0);
   }

   @Override
   public void onDisable() {
      this.forwardInputTracker.reset();
      this.strafeInputTracker.reset();
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onMoveInput(MoveInputEvent var1) {
      if (this.isEnabled()) {
         if (ClientUtils.isInWorld() && mc.currentScreen == null) {
            boolean var2 = mc.gameSettings.keyBindForward.isKeyDown();
            boolean var3 = mc.gameSettings.keyBindBack.isKeyDown();
            boolean var4 = mc.gameSettings.keyBindLeft.isKeyDown();
            boolean var5 = mc.gameSettings.keyBindRight.isKeyDown();
            int var6 = this.forwardInputTracker.resolveDirection(var2, var3);
            int var7 = this.strafeInputTracker.resolveDirection(var4, var5);
            if (var6 != 0) {
               var1.setMoveForward(var6);
            }

            if (var7 != 0) {
               var1.setMoveStrafe(var7);
            }
         }
      }
   }
}
