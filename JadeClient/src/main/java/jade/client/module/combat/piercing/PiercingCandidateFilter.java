// Jade recovery: original class: jade.deps.eLz.JqCeBKMr9P
package jade.client.module.combat.piercing;

import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;

public final class PiercingCandidateFilter {
   public static final double MIN_PIERCING_DISTANCE = 0.1F;

   private PiercingCandidateFilter() {
   }

   public static boolean arePiercingConditionsMet(boolean var0, boolean var1, boolean var2, boolean var3, boolean var4, MovingObjectPosition var5) {
      return var0 && var1 && (!var2 || var3) ? var4 || var5 == null || var5.typeOfHit != MovingObjectType.BLOCK : false;
   }

   public static boolean isCandidateInRange(double var0, double var2, boolean var4, double var5, boolean var7, boolean var8, boolean var9) {
      if (!var4 && var0 > 3.0) {
         return false;
      } else if (var0 > var2 || var0 >= var5) {
         return false;
      } else {
         return var7 && var0 > 0.1F ? false : !var8 || !var9;
      }
   }

   public static boolean isBetterCandidate(PiercingCandidateFilter$0 var0, PiercingCandidateFilter$0 var1, int var2) {
      if (var1 == null) {
         return true;
      } else if (var0.isLiving != var1.isLiving) {
         return var0.isLiving;
      } else if (!var0.isLiving) {
         return var0.bKu < var1.bKu;
      } else {
         return var2 == 0
            ? var0.vX0 < var1.vX0 || var0.vX0 == var1.vX0 && var0.bKu < var1.bKu
            : var0.health < var1.health || var0.health == var1.health && var0.bKu < var1.bKu;
      }
   }
}
