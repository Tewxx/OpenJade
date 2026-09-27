// Jade recovery: original class: jade.deps.eLz.ZpdDU8
package jade.client.module.render.blockesp;

import jade.client.common.BlockScanner;
import jade.client.common.BlockUtils;
import jade.client.common.RenderUtils;
import jade.client.setting.BlockColorListSetting;
import java.util.EnumSet;
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public final class BlockEspRenderer {
   private BlockEspRenderer() {
   }

   public static void renderNearbyBlocks(Minecraft var0, BlockScanner var1, BlockColorListSetting var2, BlockEspRenderer$0 var3) {
      BlockEspScanLimits$1 var4 = new BlockEspScanLimits$1(var0.thePlayer.posX, var0.thePlayer.posY, var0.thePlayer.posZ);
      int var5 = 0;

      for (Entry var7 : var1.getTrackedBlockEntries()) {
         if (var5 >= var3.maxBlocks) {
            return;
         }

         int var8 = 0;

         for (BlockPos var10 : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) ((Set)var7.getValue())) {
            if (var5 >= var3.maxBlocks || HSWTv3(var8, var3.maxPerGroup)) {
               break;
            }

            if (PMiS(var0, var1, var2, var3, var4, var10)) {
               var5++;
               var8++;
            }
         }
      }
   }

   private static boolean HSWTv3(int var0, int var1) {
      return var1 > 0 && var0 >= var1;
   }

   private static boolean PMiS(Minecraft var0, BlockScanner var1, BlockColorListSetting var2, BlockEspRenderer$0 var3, BlockEspScanLimits$1 var4, BlockPos var5) {
      BlockEspScanLimits$0 var6 = new BlockEspScanLimits$0(var5.getX(), var5.getY(), var5.getZ());
      if (!BlockEspScanLimits.YjzcSl(var6, var4, var3.maxDistanceSq)) {
         return false;
      } else {
         AxisAlignedBB var7 = BlockUtils.getSelectedBounds(var5);
         if (var7 != null && !RenderUtils.XRxsYw(var7)) {
            return false;
         } else {
            EnumSet var8 = ExposedFaceFinder.findExposedFaces(var5, var1::isTracked);
            if (var8.isEmpty()) {
               return false;
            } else {
               IBlockState var9 = var0.theWorld.getBlockState(var5);
               int var10 = BlockEspColorResolver.resolveColor(var9, var2);
               if (var3.useExternalRenderer) {
                  BlockBoxRenderer.QSHA(var0, var7, var10, var3.outline, var3.shade);
               } else {
                  RenderUtils.IfIae(var5, var9, var10, var3.outline, var3.shade, var8);
               }

               return true;
            }
         }
      }
   }
}
