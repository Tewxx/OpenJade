// Jade recovery: recovered class name: IAccessorPlayerControllerMP; mixin target: net.minecraft.client.multiplayer.PlayerControllerMP
package jade.mixin.impl.accessor;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerControllerMP.class)
public interface IAccessorPlayerControllerMP {
   @Invoker("syncCurrentPlayItem")
   void callSyncCurrentPlayItem();

   @Accessor("isHittingBlock")
   void setIsHittingBlock(boolean var1);

   @Accessor("isHittingBlock")
   boolean getIsHittingBlock();

   @Accessor("blockHitDelay")
   void setBlockHitDelay(int var1);

   @Accessor("blockHitDelay")
   int getBlockHitDelay();

   @Accessor("curBlockDamageMP")
   void setCurBlockDamageMP(float var1);

   @Accessor("currentBlock")
   BlockPos getCurrentBlock();

   @Accessor("curBlockDamageMP")
   float getCurBlockDamageMP();
}
