// Jade recovery: original class: jade.deps.eLz.PlRXHC$1
package jade.client.module.combat;

import net.minecraft.entity.player.EntityPlayer;

public final class AimAssist$1 {
   private final EntityPlayer entityPlayer;
   private final double sortMetric;
   private final double distanceSquared;

   AimAssist$1(EntityPlayer var1, double var2, double var4) {
      this.entityPlayer = var1;
      this.sortMetric = var2;
      this.distanceSquared = var4;
   }

   public static EntityPlayer getPlayer(AimAssist$1 var0) {
      return var0.entityPlayer;
   }

   public static double Dxq6(AimAssist$1 var0) {
      return var0.sortMetric;
   }

   public static double getDistanceSquared(AimAssist$1 var0) {
      return var0.distanceSquared;
   }
}
