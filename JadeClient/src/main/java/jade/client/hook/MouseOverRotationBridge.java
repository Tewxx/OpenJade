// Jade recovery: original class: jade.deps.eLz.k6zzbLeUW
package jade.client.hook;

import jade.client.Jade;
import jade.client.common.RotationHandler;
import jade.client.common.PointedObjectOverrider;
import jade.client.module.combat.Piercing;
import jade.client.module.player.BedNuker;
import jade.client.module.player.BridgeNuker;
import jade.client.module.player.GhostHand;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public final class MouseOverRotationBridge {
   private static final RotationSourceChain rotationSourceChain = new RotationSourceChain(MouseOverRotationBridge::getBedNukerModule, MouseOverRotationBridge::getBridgeNukerModule, MouseOverRotationBridge::GAYg, MouseOverRotationBridge::getPiercingModule);

   private MouseOverRotationBridge() {
   }

   public static void beginSilentRotation() {
      RotationHandler var0 = RotationHandler.getInstance();
      if (!var0.renderRotationApplied) {
         Entity var1 = Minecraft.getMinecraft().getRenderViewEntity();
         if (var1 != null && var0.abxpJn()) {
            Float var2 = var0.getTargetYaw();
            Float var3 = var0.cvZx();
            if (RotationSourceChain.areAnglesUsable(var2, var3)) {
               var0.mNwrQ(var1, var2, var3, true);
               var0.renderRotationApplied = true;
            }
         }
      }
   }

   public static void updateRotationTargets(float var0) {
      rotationSourceChain.updateActiveRotationSource(var0);
   }

   public static void endSilentRotation() {
      RotationHandler var0 = RotationHandler.getInstance();
      if (var0.renderRotationApplied) {
         Entity var1 = Minecraft.getMinecraft().getRenderViewEntity();
         if (var1 != null) {
            var0.restoreEntityRotation(var1);
         }

         var0.renderRotationApplied = false;
      }
   }

   private static PointedObjectOverrider getPiercingModule() {
      return Jade.getModuleManager().getModule(Piercing.class);
   }

   private static PointedObjectOverrider GAYg() {
      return Jade.getModuleManager().getModule(GhostHand.class);
   }

   private static PointedObjectOverrider getBridgeNukerModule() {
      return Jade.getModuleManager().getModule(BridgeNuker.class);
   }

   private static PointedObjectOverrider getBedNukerModule() {
      return Jade.getModuleManager().getModule(BedNuker.class);
   }
}
