// Jade recovery: recovered class name: MixinBlock; mixin target: net.minecraft.block.Block; original class: jade.mixin.impl.world.M9c67177e0eb71c4972baecc7a387ba01
package jade.mixin.impl.world;

import jade.client.hook.CollisionBoxHelper;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Block.class)
public abstract class MixinBlock {
   @Shadow(aliases = "func_180640_a")
   public abstract AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3);

   @Overwrite
   public void func_180638_a(World world, BlockPos position, IBlockState state, AxisAlignedBB query, List<AxisAlignedBB> results, Entity entity) {
      AxisAlignedBB vanillaBox = this.getCollisionBoundingBox(world, position, state);
      AxisAlignedBB selectedBox = CollisionBoxHelper.postCollisionBoxesEvent((Block)(Object)this, position, vanillaBox);
      if (CollisionBoxHelper.isBoxIntersecting(selectedBox, query)) {
         results.add(selectedBox);
      }
   }
}
