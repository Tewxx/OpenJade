// Jade recovery: original class: jade.deps.eLz.tJv2Kx0n
package jade.client.module.render.bedplates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing;

public final class BedPlateShapes {
   private static final int WEtX = 5;
   private static final EnumMap<EnumFacing, List<List<BlockPos>>> enumMap = createShapeCache();

   private BedPlateShapes() {
   }

   public static List<List<BlockPos>> getLayersForDirection(BlockPos var0, BlockPos var1) {
      EnumFacing var2 = findHorizontalFacing(var0, var1);
      return var2 == null ? null : enumMap.get(var2);
   }

   public static List<BlockPos> XAwJ3(BlockPos var0, BlockPos var1) {
      List var2 = getLayersForDirection(var0, var1);
      if (var2 == null) {
         return Collections.emptyList();
      } else {
         ArrayList var3 = new ArrayList();

         for (List var5 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (var2)) {
            for (BlockPos var7 : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (var5)) {
               var3.add(var0.add(var7));
            }
         }

         return var3;
      }
   }

   private static EnumMap<EnumFacing, List<List<BlockPos>>> createShapeCache() {
      EnumMap var0 = new EnumMap(EnumFacing.class);
      EnumFacing[] var1 = new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH};

      for (EnumFacing var5 : var1) {
         var0.put(var5, buildLayersForFacing(var5));
      }

      return var0;
   }

   private static List<List<BlockPos>> buildLayersForFacing(EnumFacing var0) {
      BlockPos var1 = BlockPos.ORIGIN;
      BlockPos var2 = var1.offset(var0);
      boolean var3 = var0.getAxis() == Axis.Z;
      BlockPos var4 = var3 ? (var2.getZ() > var1.getZ() ? var2 : var1) : (var2.getX() > var1.getX() ? var2 : var1);
      BlockPos var5 = var4.equals(var1) ? var2 : var1;
      BlockPos[] var6 = new BlockPos[]{var4, var5};
      HashSet var7 = new HashSet();
      ArrayList var8 = new ArrayList(5);

      for (int var9 = 1; var9 <= 5; var9++) {
         ArrayList var10 = new ArrayList();

         for (int var11 = 0; var11 < var6.length; var11++) {
            BlockPos var12 = var6[var11];
            int var13 = var11 == 0 ? var9 : -var9;
            int var14 = var3 ? var12.getX() : var12.getX() + var13;
            int var15 = var3 ? var12.getZ() + var13 : var12.getZ();

            for (int var16 = 0; var16 <= var9; var16++) {
               int var17 = 0;

               for (int var18 = var16; var18 >= 0; var18--) {
                  int var19 = var11 == 0 ? var16 : -var16;
                  if (var3) {
                     addUniquePos(var10, var7, var14 - var18, var12.getY() + var17, var15 - var19);
                     addUniquePos(var10, var7, var14 + var18, var12.getY() + var17, var15 - var19);
                  } else {
                     addUniquePos(var10, var7, var14 - var19, var12.getY() + var17, var15 - var18);
                     addUniquePos(var10, var7, var14 - var19, var12.getY() + var17, var15 + var18);
                  }

                  if (var18 > 0) {
                     var17++;
                  }
               }
            }
         }

         var8.add(Collections.unmodifiableList(var10));
      }

      return Collections.unmodifiableList(var8);
   }

   private static void addUniquePos(List<BlockPos> var0, Set<Long> var1, int var2, int var3, int var4) {
      BlockPos var5 = new BlockPos(var2, var3, var4);
      if (var1.add(var5.toLong())) {
         var0.add(var5);
      }
   }

   private static EnumFacing findHorizontalFacing(BlockPos var0, BlockPos var1) {
      int var2 = var1.getX() - var0.getX();
      int var3 = var1.getZ() - var0.getZ();

      for (EnumFacing var7 : new EnumFacing[] {EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.EAST}) {
         if (var7.getFrontOffsetX() == var2 && var7.getFrontOffsetZ() == var3) {
            return var7;
         }
      }

      return null;
   }
}
