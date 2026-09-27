// Jade recovery: original class: jade.deps.eLz.MmkrZAveWv
package jade.client.event;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class CollisionBoxesEvent extends Event {
   public final BlockPos blockPos;
   public final Block block;
   public AxisAlignedBB axisAlignedBB;

   public CollisionBoxesEvent(BlockPos var1, Block var2, AxisAlignedBB var3) {
      this.block = var2;
      this.blockPos = var1;
      this.axisAlignedBB = var3;
   }

   public BlockPos IfUujIz() {
      return this.blockPos;
   }

   public Block getBlock() {
      return this.block;
   }

   public AxisAlignedBB getCollisionBox() {
      return this.axisAlignedBB;
   }

   public void setCollisionBox(AxisAlignedBB var1) {
      this.axisAlignedBB = var1;
   }
}
