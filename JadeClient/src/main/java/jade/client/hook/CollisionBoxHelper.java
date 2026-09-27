// Jade recovery: original class: jade.deps.eLz.FkJw6OAQLT
package jade.client.hook;

import jade.client.common.EventBus;
import jade.client.event.CollisionBoxesEvent;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public final class CollisionBoxHelper {
   private CollisionBoxHelper() {
   }

   public static AxisAlignedBB postCollisionBoxesEvent(Block var0, BlockPos var1, AxisAlignedBB var2) {
      CollisionBoxesEvent var3 = new CollisionBoxesEvent(var1, var0, var2);
      EventBus.post(var3);
      return var3.axisAlignedBB;
   }

   public static boolean isBoxIntersecting(AxisAlignedBB var0, AxisAlignedBB var1) {
      return var0 != null && var1.intersectsWith(var0);
   }
}
