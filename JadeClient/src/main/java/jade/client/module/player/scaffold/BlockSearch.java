// Jade recovery: original class: jade.deps.eLz.Dzt1o6dG9
package jade.client.module.player.scaffold;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class BlockSearch {
   private static final int OKd = 4;
   private static final double MAX_TRACE_DISTANCE = 4.0;

   private BlockSearch() {
   }

   public static List<BlockPos> findSupportCandidates(BlockPos var0, Predicate<BlockPos> var1) {
      ArrayList var2 = new ArrayList(9);
      BlockPos var3 = var0.down();
      if (var1.test(var3)) {
         var2.add(var3);
      }

      for (int var4 = -1; var4 <= 1; var4++) {
         for (int var5 = -1; var5 <= 1; var5++) {
            if (var4 != 0 || var5 != 0) {
               BlockPos var6 = var0.add(var4, 0, var5);
               if (var1.test(var6)) {
                  var2.add(var6);
               }
            }
         }
      }

      return var2;
   }

   public static boolean hasSupportAlongTrace(double var0, int var2, double var3, double var5, double var7, Predicate<BlockPos> var9) {
      double var10 = Math.sqrt(var5 * var5 + var7 * var7);
      double var12 = var10 > 1.0E-6 && Double.isFinite(var10) ? (var10 + 1.0) / var10 : 1.0;

      for (BlockPos var15 : buildTracePositions(var0, var2, var3, var5 * var12 / 4.0, var7 * var12 / 4.0)) {
         if (var9.test(var15)) {
            return true;
         }
      }

      return false;
   }

   public static List<BlockPos> buildTracePositions(double var0, int var2, double var3, double var5, double var7) {
      LinkedHashSet var9 = new LinkedHashSet();
      var9.add(new BlockPos(var0, var2, var3));
      if (Double.isFinite(var5) && Double.isFinite(var7)) {
         double var10 = var5 * 4.0;
         double var12 = var7 * 4.0;
         double var14 = Math.max(Math.abs(var10), Math.abs(var12));
         if (var14 > 4.0) {
            var10 *= 4.0 / var14;
            var12 *= 4.0 / var14;
            var14 = 4.0;
         }

         int var16 = Math.max(4, (int)Math.ceil(var14 * 4.0));

         for (int var17 = 1; var17 <= var16; var17++) {
            double var18 = (double)var17 / var16;
            var9.add(new BlockPos(var0 + var10 * var18, var2, var3 + var12 * var18));
         }

         return new ArrayList<>(var9);
      } else {
         return new ArrayList<>(var9);
      }
   }

   public static <T> T findPlacement(BlockPos var0, List<BlockPos> var1, Predicate<BlockPos> var2, BiFunction<BlockPos, EnumFacing, T> var3) {
      if (!var2.test(var0)) {
         return null;
      } else {
         HashSet var4 = new HashSet(var1);
         boolean var5 = false;

         for (EnumFacing var9 : EnumFacing.values()) {
            if (var9 != EnumFacing.DOWN) {
               BlockPos var10 = var0.offset(var9.getOpposite());
               if (var4.contains(var10)) {
                  var5 = true;
                  Object var11 = var3.apply(var10, var9);
                  if (var11 != null) {
                     return (T)var11;
                  }
               }
            }
         }

         if (var5) {
            return null;
         } else {
            for (BlockPos var15 : var1) {
               if (var15.getY() == var0.getY() && Math.abs(var15.getX() - var0.getX()) == 1 && Math.abs(var15.getZ() - var0.getZ()) == 1) {
                  for (EnumFacing var19 : new EnumFacing[] {EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.EAST}) {
                     BlockPos var12 = var15.offset(var19);
                     if (var12.distanceSq(var0) == 1.0 && var2.test(var12)) {
                        Object var13 = var3.apply(var15, var19);
                        if (var13 != null) {
                           return (T)var13;
                        }
                     }
                  }
               }
            }

            return null;
         }
      }
   }
}
