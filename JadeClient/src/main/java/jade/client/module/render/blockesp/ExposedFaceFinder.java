// Jade recovery: original class: jade.deps.eLz.DzYI1TJq
package jade.client.module.render.blockesp;

import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class ExposedFaceFinder {
   private ExposedFaceFinder() {
   }

   public static EnumSet<EnumFacing> findExposedFaces(BlockPos var0, Predicate<BlockPos> var1) {
      EnumSet var2 = EnumSet.noneOf(EnumFacing.class);

      for (EnumFacing var6 : EnumFacing.values()) {
         if (!var1.test(var0.offset(var6))) {
            var2.add(var6);
         }
      }

      return var2;
   }
}
