// Jade recovery: original class: jade.deps.eLz.KwyyrlIG0
package jade.client.module.minigames.murdermystery;

import net.minecraft.util.AxisAlignedBB;

public final class MurderMysteryUtils {
   private MurderMysteryUtils() {
   }

   public static boolean isMurderMysteryGame(String var0, Iterable<String> var1) {
      if (var0 != null && (var0.contains("MURDER") || var0.contains("MYSTERY"))) {
         if (var1 == null) {
            return false;
         } else {
            for (String var3 : var1) {
               if (var3 != null && (var3.contains("Role:") || var3.contains("Innocents Left:"))) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public static double BNAB(AxisAlignedBB var0) {
      return var0 == null ? 0.0 : (var0.maxX - var0.minX) * (var0.maxZ - var0.minZ) * (var0.maxY - var0.minY);
   }
}
