// Jade recovery: original class: jade.deps.eLz.JFhiHaz$1
package jade.client.module.render.bedplates;

import java.util.Set;

public final class BedPlateTracker$1 {
   private final long CVe;
   private final Set<Long> coveredPositions;
   private final Set<Long> vTblC8;

   BedPlateTracker$1(long var1, Set<Long> var3, Set<Long> var4) {
      this.CVe = var1;
      this.coveredPositions = var3;
      this.vTblC8 = var4;
   }

   public static long getPartnerPosLong(BedPlateTracker$1 var0) {
      return var0.CVe;
   }

   public static Set getCoveredPositions(BedPlateTracker$1 var0) {
      return var0.coveredPositions;
   }

   public static Set VRzuj(BedPlateTracker$1 var0) {
      return var0.vTblC8;
   }
}
