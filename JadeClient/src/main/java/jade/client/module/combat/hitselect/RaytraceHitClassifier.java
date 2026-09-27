// Jade recovery: original class: jade.deps.eLz.dcGjcbLF
package jade.client.module.combat.hitselect;

import jade.client.module.shared.TargetFinder;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;

public final class RaytraceHitClassifier {
   private RaytraceHitClassifier() {
   }

   public static RaytraceHitClassifier$0 classifyRaytraceResult(MovingObjectPosition var0, double var1) {
      if (var0 == null) {
         return RaytraceHitClassifier$0.MISS;
      } else if (var0.typeOfHit == MovingObjectType.BLOCK) {
         return RaytraceHitClassifier$0.BLOCK;
      } else if (var0.typeOfHit != MovingObjectType.ENTITY) {
         return RaytraceHitClassifier$0.MISS;
      } else {
         Entity var3 = var0.entityHit;
         return TargetFinder.TDBVqmH(var3, var1) == null ? RaytraceHitClassifier$0.MISS : RaytraceHitClassifier$0.VALID_PLAYER;
      }
   }
}
