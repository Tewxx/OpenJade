// Jade recovery: original class: jade.deps.eLz.TRfelhq15
package jade.client.module.render.bedplates;

import java.util.List;
import net.minecraft.util.BlockPos;

public final class BedPlateCache {
   private final long bedPosLong;
   private final List<BedPlateEntry> bedPlateEntrys;

   public BedPlateCache(BlockPos var1, List<BedPlateEntry> var2) {
      this.bedPosLong = var1.toLong();
      this.bedPlateEntrys = var2;
   }

   public boolean matchesBedPos(BlockPos var1) {
      return var1 != null && var1.toLong() == this.bedPosLong;
   }

   public List<BedPlateEntry> getEntries() {
      return this.bedPlateEntrys;
   }
}
