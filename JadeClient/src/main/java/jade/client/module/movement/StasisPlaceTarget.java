// Jade recovery: original class: jade.deps.eLz.FK6uC9m$1
package jade.client.module.movement;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public final class StasisPlaceTarget {
   private final EnumFacing face;
   private final Vec3 hitVec;

   StasisPlaceTarget(EnumFacing face, Vec3 hitVec) {
      this.face = face;
      this.hitVec = hitVec;
   }

   public static EnumFacing face(StasisPlaceTarget target) {
      return target.face;
   }

   public static Vec3 hitVec(StasisPlaceTarget target) {
      return target.hitVec;
   }
}
