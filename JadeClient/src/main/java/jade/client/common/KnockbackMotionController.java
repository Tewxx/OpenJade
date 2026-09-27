// Jade recovery: original class: jade.deps.eLz.bx5bAOC
package jade.client.common;

import net.minecraft.entity.player.EntityPlayer;

public final class KnockbackMotionController {
   private final MovementTickTracker RLih = new MovementTickTracker();

   public KnockbackMotionController$1 updateKnockbackMotion(EntityPlayer var1, int var2, int var3, int var4, boolean var5, boolean var6, boolean var7) {
      MovementTickTracker$1 var8 = this.RLih.wWmd(var1.onGround, ClientUtils.uUwk(), var1.posY % 1.0 == 0.0, var2, var3, var4);
      boolean var9 = var6;
      boolean var10 = var5;
      boolean var11 = var7;
      if (var5 && !var1.onGround) {
         double var12 = 0.9 - var8.getAirborneTicks() / 10000.0 - ClientUtils.randomDouble(1.0E-5, 6.0E-5);
         if (var1.hurtTime == 0 && var8.getAirborneTicks() > 4 && !var7) {
            var1.motionX *= var12;
            var1.motionZ *= var12;
            var11 = true;
         }

         var9 = true;
      } else if (var6) {
         var10 = false;
         var9 = false;
      }

      if (var1.onGround) {
         var11 = false;
      }

      return new KnockbackMotionController$1(var8, var10, var9, var11);
   }
}
