// Jade recovery: original class: jade.deps.eLz.WAq402O
package jade.client.module.render.bedplates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos.MutableBlockPos;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public final class BedPlateScanner {
   private static final float uGhyc = 0.2F;
   private static final float KqaGz = 0.2F;

   private BedPlateScanner() {
   }

   public static BedPlateCache scanBedArea(Minecraft var0, World var1, BlockPos var2, BlockPos var3) {
      List var4 = BedPlateShapes.getLayersForDirection(var2, var3);
      if (var4 == null) {
         return new BedPlateCache(new BlockPos(var3), Collections.emptyList());
      } else {
         HashMap var5 = new HashMap();
         HashMap var6 = new HashMap();
         int var7 = 0;
         MutableBlockPos var8 = new MutableBlockPos();

         for (List var10 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (var4)) {
            HashMap var11 = new HashMap();
            int var12 = 0;

            for (BlockPos var14 : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (var10)) {
               var8.set(var2.getX() + var14.getX(), var2.getY() + var14.getY(), var2.getZ() + var14.getZ());
               if (accumulateBlock(var0, var1, var8, var11, var6)) {
                  var12++;
               }
            }

            int var22 = var10.size();
            if (var22 != 0 && !((float)var12 / var22 > 0.2F)) {
               var7 = 0;

               for (Entry var15 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (var11.entrySet())) {
                  int var16 = (Integer)var15.getValue();
                  if ((float)var16 / var22 >= 0.2F) {
                     Integer var17 = (Integer)var5.get(var15.getKey());
                     var5.put(var15.getKey(), (var17 == null ? 0 : var17) + var16);
                  }
               }
            } else if (++var7 == 2) {
               break;
            }
         }

         ArrayList var18 = new ArrayList();

         for (Entry var20 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (var5.entrySet())) {
            BedPlateScanner$0 var21 = (BedPlateScanner$0)var20.getKey();
            var18.add(new BedPlateEntry(BedPlateScanner$0.copyEntryItemStack(var21), BedPlateScanner$0.getSprite(var21), (Integer)var20.getValue(), BedPlateScanner$0.getDisplayName(var21)));
         }

         Collections.sort(var18, BedPlateScanner::compareEntriesByCount);
         return new BedPlateCache(new BlockPos(var3), Collections.unmodifiableList(var18));
      }
   }

   private static boolean accumulateBlock(Minecraft var0, World var1, BlockPos var2, Map<BedPlateScanner$0, Integer> var3, Map<Integer, BedPlateScanner$0> var4) {
      IBlockState var5 = var1.getBlockState(var2);
      if (var5 != null && var5.getBlock() != Blocks.air) {
         IBlockState var6 = gvIigI(var5);
         int var7 = Block.getStateId(var6);
         BedPlateScanner$0 var8 = (BedPlateScanner$0)var4.get(var7);
         if (var8 == null) {
            var8 = BedPlateScanner$0.createFromBlockState(var0, var1, var6, var2);
            var4.put(var7, var8);
         }

         Integer var9 = (Integer)var3.get(var8);
         var3.put(var8, var9 == null ? 1 : var9 + 1);
         return false;
      } else {
         return true;
      }
   }

   private static IBlockState gvIigI(IBlockState var0) {
      Block var1 = var0.getBlock();
      if (var1 == Blocks.water || var1 == Blocks.flowing_water) {
         return Blocks.water.getDefaultState();
      } else if (var1 != Blocks.lava && var1 != Blocks.flowing_lava) {
         return var1 == Blocks.fire ? Blocks.fire.getDefaultState() : var0;
      } else {
         return Blocks.lava.getDefaultState();
      }
   }

   private static int compareEntriesByCount(BedPlateEntry var0, BedPlateEntry var1) {
      int var2 = Integer.compare(var1.GULE(), var0.GULE());
      return var2 != 0 ? var2 : var0.getDisplayName().compareToIgnoreCase(var1.getDisplayName());
   }
}
