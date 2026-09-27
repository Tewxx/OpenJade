// Jade recovery: original class: jade.deps.eLz.Eze02Gyc
package jade.client.common;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public final class HitVecUtils {
   private static final double FACE_INSET = 0.05;
   private static final int SjoF2 = 30;

   private HitVecUtils() {
   }

   public static Vec3 getAimPointOnEntity(Entity var0, EntityPlayer var1, double var2, double var4) {
      if (var0 != null && var1 != null) {
         AxisAlignedBB var6 = fejS(var0);
         double var7 = GDwcL(var6.minX, var6.maxX);
         double var9 = GDwcL(var6.minZ, var6.maxZ);
         double var11 = var0 instanceof EntityLivingBase ? var0.posY + ((EntityLivingBase)var0).getEyeHeight() : GDwcL(var6.minY, var6.maxY);
         Vec3 var13 = var1.getPositionEyes(1.0F);
         if (var6.isVecInside(var13)) {
            return new Vec3(var7, var13.yCoord, var9);
         } else {
            Vec3 var14 = clampToBox(var6, var13);
            double var15 = toUnitFraction(var2);
            double var17 = toUnitFraction(var4);
            return new Vec3(lerpValue(var7, var14.xCoord, var15), lerpValue(var11, var14.yCoord, var17), lerpValue(var9, var14.zCoord, var15));
         }
      } else {
         return null;
      }
   }

   public static Vec3 clampToBox(AxisAlignedBB var0, Vec3 var1) {
      return new Vec3(clampValue(var1.xCoord, var0.minX, var0.maxX), clampValue(var1.yCoord, var0.minY, var0.maxY), clampValue(var1.zCoord, var0.minZ, var0.maxZ));
   }

   public static double getDistanceSqToEntity(Entity var0, Vec3 var1) {
      return var0 != null && var1 != null ? var1.squareDistanceTo(clampToBox(fejS(var0), var1)) : Double.MAX_VALUE;
   }

   public static List<Vec3> getVisibleFaceSamples(Entity var0, Vec3 var1) {
      ArrayList var2 = new ArrayList();
      if (var0 != null && var1 != null) {
         AxisAlignedBB var3 = fejS(var0);
         boolean var4 = var1.xCoord > var3.maxX;
         boolean var5 = var1.xCoord < var3.minX;
         boolean var6 = var1.yCoord > var3.maxY;
         boolean var7 = var1.yCoord < var3.minY;
         boolean var8 = var1.zCoord > var3.maxZ;
         boolean var9 = var1.zCoord < var3.minZ;
         int var10 = countTrue(var4 || var5, var6 || var7, var8 || var9);
         if (var10 == 0) {
            return var2;
         } else {
            int var11 = 30 / var10;
            if (var4 || var5) {
               sBno468(
                  var2, HitVecUtils$1.X, var4 ? var3.maxX - 0.05 : var3.minX + 0.05, var3.minY + 0.05, var3.maxY - 0.05, var3.minZ + 0.05, var3.maxZ - 0.05, var11
               );
            }

            if (var6 || var7) {
               sBno468(
                  var2, HitVecUtils$1.Y, var6 ? var3.maxY - 0.05 : var3.minY + 0.05, var3.minX + 0.05, var3.maxX - 0.05, var3.minZ + 0.05, var3.maxZ - 0.05, var11
               );
            }

            if (var8 || var9) {
               sBno468(
                  var2, HitVecUtils$1.Z, var8 ? var3.maxZ - 0.05 : var3.minZ + 0.05, var3.minX + 0.05, var3.maxX - 0.05, var3.minY + 0.05, var3.maxY - 0.05, var11
               );
            }

            return var2;
         }
      } else {
         return var2;
      }
   }

   public static AxisAlignedBB fejS(Entity var0) {
      float var1 = var0.getCollisionBorderSize();
      return var0.getEntityBoundingBox().expand(var1, var1, var1);
   }

   private static void sBno468(List<Vec3> var0, HitVecUtils$1 var1, double var2, double var4, double var6, double var8, double var10, int var12) {
      double var13 = Math.abs(var6 - var4);
      double var15 = Math.abs(var10 - var8);
      if (!(var13 < 1.0E-4) && !(var15 < 1.0E-4)) {
         double var17 = var13 / var15;
         int var19 = Math.max(2, (int)Math.round(Math.sqrt(var12 * var17)));
         int var20 = Math.max(2, (int)Math.round(Math.sqrt(var12 / var17)));

         for (int var21 = 0; var21 < var19; var21++) {
            double var22 = lerpValue(var4, var6, (double)var21 / (var19 - 1));

            for (int var24 = 0; var24 < var20; var24++) {
               double var25 = lerpValue(var8, var10, (double)var24 / (var20 - 1));
               var0.add(toVec3ForAxis(var1, var2, var22, var25));
            }
         }
      } else {
         var0.add(toVec3ForAxis(var1, var2, GDwcL(var4, var6), GDwcL(var8, var10)));
      }
   }

   private static Vec3 toVec3ForAxis(HitVecUtils$1 var0, double var1, double var3, double var5) {
      switch (var0) {
         case X:
            return new Vec3(var1, var3, var5);
         case Y:
            return new Vec3(var3, var1, var5);
         default:
            return new Vec3(var3, var5, var1);
      }
   }

   private static int countTrue(boolean var0, boolean var1, boolean var2) {
      return (var0 ? 1 : 0) + (var1 ? 1 : 0) + (var2 ? 1 : 0);
   }

   private static double toUnitFraction(double var0) {
      return clampValue(var0 / 100.0, 0.0, 1.0);
   }

   private static double GDwcL(double var0, double var2) {
      return (var0 + var2) * 0.5;
   }

   private static double lerpValue(double var0, double var2, double var4) {
      return var0 + (var2 - var0) * var4;
   }

   private static double clampValue(double var0, double var2, double var4) {
      return Math.max(var2, Math.min(var4, var0));
   }
}
