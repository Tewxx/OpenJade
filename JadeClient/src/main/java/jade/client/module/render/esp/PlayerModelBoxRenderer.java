// Jade recovery: original class: jade.deps.eLz.b6e0eBZ9
package jade.client.module.render.esp;

import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ScreenProjector;
import net.minecraft.entity.player.EntityPlayer;

public final class PlayerModelBoxRenderer {
   private static final double DEGREES_TO_RADIANS = Math.PI / 180.0;
   private static final int[][] offset = new int[][]{{0, 1, 3, 2}, {4, 6, 7, 5}, {0, 4, 5, 1}, {2, 3, 7, 6}, {0, 2, 6, 4}, {1, 5, 7, 3}};
   private static final int[][] CUBE_EDGE_PAIRS = new int[][]{{0, 1}, {0, 2}, {0, 4}, {1, 3}, {1, 5}, {2, 3}, {2, 6}, {3, 7}, {4, 5}, {4, 6}, {5, 7}, {6, 7}};

   private PlayerModelBoxRenderer() {
   }

   public static void drawSkeletonLines(
      ExternalRenderBuffer var0, ScreenProjector var1, EntityPlayer var2, PlayerModelBoxRenderer$2 var3, float var4, double var5, double var7, double var9, int var11, float var12
   ) {
      PlayerModelBoxRenderer$1 var13 = buildPlayerSkeleton(var2, var3, var4, var5, var7, var9);
      drawBoneLine(var0, var1, PlayerModelBoxRenderer$1.getRightHip(var13), PlayerModelBoxRenderer$1.RksJ(var13), var11, var12);
      drawBoneLine(var0, var1, PlayerModelBoxRenderer$1.JsjJ(var13), PlayerModelBoxRenderer$1.getLeftFoot(var13), var11, var12);
      drawBoneLine(var0, var1, PlayerModelBoxRenderer$1.getRightHip(var13), PlayerModelBoxRenderer$1.JsjJ(var13), var11, var12);
      drawBoneLine(var0, var1, PlayerModelBoxRenderer$1.getHipCenter(var13), PlayerModelBoxRenderer$1.getChest(var13), var11, var12);
      drawBoneLine(var0, var1, PlayerModelBoxRenderer$1.getRightShoulder(var13), PlayerModelBoxRenderer$1.getLeftShoulder(var13), var11, var12);
      drawBoneLine(var0, var1, PlayerModelBoxRenderer$1.getRightShoulder(var13), PlayerModelBoxRenderer$1.getRightHand(var13), var11, var12);
      drawBoneLine(var0, var1, PlayerModelBoxRenderer$1.getLeftShoulder(var13), PlayerModelBoxRenderer$1.getLeftHand(var13), var11, var12);
      drawBoneLine(var0, var1, PlayerModelBoxRenderer$1.getChest(var13), PlayerModelBoxRenderer$1.getHeadTop(var13), var11, var12);
   }

   public static void drawSolidLimbBoxes(ExternalRenderBuffer var0, ScreenProjector var1, EntityPlayer var2, PlayerModelBoxRenderer$2 var3, float var4, double var5, double var7, double var9, int var11) {
      PlayerModelBoxRenderer$1 var12 = buildPlayerSkeleton(var2, var3, var4, var5, var7, var9);
      double[] var13 = PlayerModelBoxRenderer$1.getBodySideAxis(var12);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.RksJ(var12), PlayerModelBoxRenderer$1.getRightHip(var12), 0.23, 0.23, var13, var11, 0.0F, true);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.getLeftFoot(var12), PlayerModelBoxRenderer$1.JsjJ(var12), 0.23, 0.23, var13, var11, 0.0F, true);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.getRightHand(var12), PlayerModelBoxRenderer$1.getRightShoulder(var12), 0.22, 0.22, var13, var11, 0.0F, true);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.getLeftHand(var12), PlayerModelBoxRenderer$1.getLeftShoulder(var12), 0.22, 0.22, var13, var11, 0.0F, true);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.getHipCenter(var12), PlayerModelBoxRenderer$1.getChest(var12), 0.5, 0.25, var13, var11, 0.0F, true);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.getChest(var12), PlayerModelBoxRenderer$1.getHeadTop(var12), 0.5, 0.5, PlayerModelBoxRenderer$1.getHeadFacingAxis(var12), var11, 0.0F, true);
   }

   public static void drawLimbBoxOutlines(
      ExternalRenderBuffer var0, ScreenProjector var1, EntityPlayer var2, PlayerModelBoxRenderer$2 var3, float var4, double var5, double var7, double var9, int var11, float var12
   ) {
      PlayerModelBoxRenderer$1 var13 = buildPlayerSkeleton(var2, var3, var4, var5, var7, var9);
      double[] var14 = PlayerModelBoxRenderer$1.getBodySideAxis(var13);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.RksJ(var13), PlayerModelBoxRenderer$1.getRightHip(var13), 0.23, 0.23, var14, var11, var12, false);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.getLeftFoot(var13), PlayerModelBoxRenderer$1.JsjJ(var13), 0.23, 0.23, var14, var11, var12, false);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.getRightHand(var13), PlayerModelBoxRenderer$1.getRightShoulder(var13), 0.22, 0.22, var14, var11, var12, false);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.getLeftHand(var13), PlayerModelBoxRenderer$1.getLeftShoulder(var13), 0.22, 0.22, var14, var11, var12, false);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.getHipCenter(var13), PlayerModelBoxRenderer$1.getChest(var13), 0.5, 0.25, var14, var11, var12, false);
      drawOrientedPrism(var0, var1, PlayerModelBoxRenderer$1.getChest(var13), PlayerModelBoxRenderer$1.getHeadTop(var13), 0.5, 0.5, PlayerModelBoxRenderer$1.getHeadFacingAxis(var13), var11, var12, false);
   }

   private static void drawBoneLine(ExternalRenderBuffer var0, ScreenProjector var1, double[] var2, double[] var3, int var4, float var5) {
      var1.drawProjectedLine(var2[0], var2[1], var2[2], var3[0], var3[1], var3[2], var0, var4, var5);
   }

   private static void drawOrientedPrism(
      ExternalRenderBuffer var0, ScreenProjector var1, double[] var2, double[] var3, double var4, double var6, double[] var8, int var9, float var10, boolean var11
   ) {
      double[] var12 = normalizeVector(subtractVectors(var3, var2));
      double[] var13 = normalizeVector(subtractVectors(var8, scaleVector(var12, dotProduct(var8, var12))));
      if (vectorLength(var13) < 1.0E-5) {
         var13 = normalizeVector(crossProduct(var12, new double[]{0.0, 0.0, 1.0}));
      }

      double[] var14 = normalizeVector(crossProduct(var12, var13));
      double[][] var15 = new double[8][3];
      double[][] var16 = new double[8][2];

      for (int var17 = 0; var17 < 8; var17++) {
         double[] var18 = (var17 & 2) == 0 ? var2 : var3;
         double var19 = (var17 & 1) == 0 ? -0.5 : 0.5;
         double var21 = (var17 & 4) == 0 ? -0.5 : 0.5;
         var15[var17] = addVectors(var18, addVectors(scaleVector(var13, var19 * var4), scaleVector(var14, var21 * var6)));
         if (!var1.projectPoint(var15[var17][0], var15[var17][1], var15[var17][2])) {
            return;
         }

         var16[var17][0] = var1.projectedPoint[0];
         var16[var17][1] = var1.projectedPoint[1];
      }

      boolean[] var25 = new boolean[offset.length];

      for (int var26 = 0; var26 < offset.length; var26++) {
         int[] var28 = offset[var26];
         double var20 = 0.0;

         for (int var22 = 0; var22 < var28.length; var22++) {
            double[] var23 = var16[var28[var22]];
            double[] var24 = var16[var28[(var22 + 1) % var28.length]];
            var20 += var23[0] * var24[1] - var24[0] * var23[1];
         }

         var25[var26] = var20 < 0.0;
         if (var11 && var25[var26]) {
            var0.VogZb(var16[var28[0]][0], var16[var28[0]][1], var16[var28[1]][0], var16[var28[1]][1], var16[var28[2]][0], var16[var28[2]][1], var9);
            var0.VogZb(var16[var28[0]][0], var16[var28[0]][1], var16[var28[2]][0], var16[var28[2]][1], var16[var28[3]][0], var16[var28[3]][1], var9);
         }
      }

      if (!var11) {
         for (int[] var31 : CUBE_EDGE_PAIRS) {
            int var32 = -1;
            int var33 = -1;

            for (int var34 = 0; var34 < offset.length; var34++) {
               if (containsIndex(offset[var34], var31[0]) && containsIndex(offset[var34], var31[1])) {
                  if (var32 >= 0) {
                     var33 = var34;
                     break;
                  }

                  var32 = var34;
               }
            }

            if (var32 >= 0 && var33 >= 0 && var25[var32] != var25[var33]) {
               var0.drawLine(var16[var31[0]][0], var16[var31[0]][1], var16[var31[1]][0], var16[var31[1]][1], var9, var10);
            }
         }
      }
   }

   private static boolean containsIndex(int[] var0, int var1) {
      for (int var5 : var0) {
         if (var5 == var1) {
            return true;
         }
      }

      return false;
   }

   private static PlayerModelBoxRenderer$1 buildPlayerSkeleton(EntityPlayer var0, PlayerModelBoxRenderer$2 var1, float var2, double var3, double var5, double var7) {
      PlayerModelBoxRenderer$2 var9 = var1;
      if (var1 == null) {
         var9 = new PlayerModelBoxRenderer$2();
         var9.applyLimbSwing(var0, var2);
      }

      boolean var10 = var0.isSneaking();
      double var11 = var10 ? 0.6 : 0.75;
      double var13 = var10 ? -0.2 : 0.0;
      double var15 = -var0.renderYawOffset * (Math.PI / 180.0);
      double var17 = var10 ? Math.PI / 9 : 0.0;
      PlayerModelBoxRenderer$1 var19 = new PlayerModelBoxRenderer$1();
      PlayerModelBoxRenderer$1.setBodySideAxis(var19, rotateY(new double[]{1.0, 0.0, 0.0}, var15));
      PlayerModelBoxRenderer$1.jGzxM(var19, offsetPoint(var3, var5, var7, var15, -0.15, var11, var13));
      PlayerModelBoxRenderer$1.setLeftHip(var19, offsetPoint(var3, var5, var7, var15, 0.15, var11, var13));
      PlayerModelBoxRenderer$1.setHipCenter(var19, offsetPoint(var3, var5, var7, var15, 0.0, var11, var13));
      PlayerModelBoxRenderer$1.setRightFoot(var19, addVectors(PlayerModelBoxRenderer$1.getRightHip(var19), rotateYaw(applyLimbRotation(0.0, -var11, 0.0, var9.rightLegRotX, var9.RGtc, var9.rightLegRotZ), var15)));
      PlayerModelBoxRenderer$1.setLeftFoot(var19, addVectors(PlayerModelBoxRenderer$1.JsjJ(var19), rotateYaw(applyLimbRotation(0.0, -var11, 0.0, var9.gKyq, var9.leftLegRotY, var9.leftLegRotZ), var15)));
      double[] var20 = rotateX(new double[]{0.0, 0.65, 0.0}, var17);
      PlayerModelBoxRenderer$1.setChest(var19, addVectors(PlayerModelBoxRenderer$1.getHipCenter(var19), rotateYaw(var20, var15)));
      double[] var21 = rotateX(new double[]{0.35, 0.0, 0.0}, var17);
      PlayerModelBoxRenderer$1.setLeftShoulder(var19, addVectors(PlayerModelBoxRenderer$1.getChest(var19), rotateYaw(var21, var15)));
      var21[0] = -0.35;
      PlayerModelBoxRenderer$1.setRightShoulder(var19, addVectors(PlayerModelBoxRenderer$1.getChest(var19), rotateYaw(var21, var15)));
      double[] var22 = rotateX(applyLimbRotation(0.0, -0.6, 0.0, var9.rightArmRotX, var9.PYK, var9.rightArmRotZ), var17);
      double[] var23 = rotateX(applyLimbRotation(0.0, -0.6, 0.0, var9.ULu, var9.leftArmRotY, var9.fwB), var17);
      PlayerModelBoxRenderer$1.setRightHand(var19, addVectors(PlayerModelBoxRenderer$1.getRightShoulder(var19), rotateYaw(var22, var15)));
      PlayerModelBoxRenderer$1.setLeftHand(var19, addVectors(PlayerModelBoxRenderer$1.getLeftShoulder(var19), rotateYaw(var23, var15)));
      double var24 = var0.prevRotationYawHead + (var0.rotationYawHead - var0.prevRotationYawHead) * var2;
      double var26 = -(var24 - var0.renderYawOffset) * (Math.PI / 180.0);
      double var28 = (var0.prevRotationPitch + (var0.rotationPitch - var0.prevRotationPitch) * var2) * (Math.PI / 180.0);
      double[] var30 = rotateY(rotateX(new double[]{0.0, 0.4, 0.0}, var28), var26);
      PlayerModelBoxRenderer$1.fidfYk(var19, addVectors(PlayerModelBoxRenderer$1.getChest(var19), rotateYaw(rotateX(var30, var17), var15)));
      PlayerModelBoxRenderer$1.qzWw(var19, rotateY(new double[]{1.0, 0.0, 0.0}, -var24 * (Math.PI / 180.0)));
      return var19;
   }

   private static double[] applyLimbRotation(double var0, double var2, double var4, float var6, float var7, float var8) {
      return rotateX(rotateY(rotateZ(new double[]{var0, var2, var4}, -var8), -var7), var6);
   }

   private static double[] rotateX(double[] var0, double var1) {
      double var3 = Math.cos(var1);
      double var5 = Math.sin(var1);
      return new double[]{var0[0], var0[1] * var3 - var0[2] * var5, var0[1] * var5 + var0[2] * var3};
   }

   private static double[] rotateY(double[] var0, double var1) {
      double var3 = Math.cos(var1);
      double var5 = Math.sin(var1);
      return new double[]{var0[0] * var3 + var0[2] * var5, var0[1], -var0[0] * var5 + var0[2] * var3};
   }

   private static double[] rotateZ(double[] var0, double var1) {
      double var3 = Math.cos(var1);
      double var5 = Math.sin(var1);
      return new double[]{var0[0] * var3 - var0[1] * var5, var0[0] * var5 + var0[1] * var3, var0[2]};
   }

   private static double[] rotateYaw(double[] var0, double var1) {
      return rotateY(var0, var1);
   }

   private static double[] offsetPoint(double var0, double var2, double var4, double var6, double var8, double var10, double var12) {
      double[] var14 = rotateY(new double[]{var8, var10, var12}, var6);
      return new double[]{var0 + var14[0], var2 + var14[1], var4 + var14[2]};
   }

   private static double[] addVectors(double[] var0, double[] var1) {
      return new double[]{var0[0] + var1[0], var0[1] + var1[1], var0[2] + var1[2]};
   }

   private static double[] subtractVectors(double[] var0, double[] var1) {
      return new double[]{var0[0] - var1[0], var0[1] - var1[1], var0[2] - var1[2]};
   }

   private static double[] scaleVector(double[] var0, double var1) {
      return new double[]{var0[0] * var1, var0[1] * var1, var0[2] * var1};
   }

   private static double dotProduct(double[] var0, double[] var1) {
      return var0[0] * var1[0] + var0[1] * var1[1] + var0[2] * var1[2];
   }

   private static double[] crossProduct(double[] var0, double[] var1) {
      return new double[]{var0[1] * var1[2] - var0[2] * var1[1], var0[2] * var1[0] - var0[0] * var1[2], var0[0] * var1[1] - var0[1] * var1[0]};
   }

   private static double vectorLength(double[] var0) {
      return Math.sqrt(dotProduct(var0, var0));
   }

   private static double[] normalizeVector(double[] var0) {
      double var1 = vectorLength(var0);
      return var1 < 1.0E-8 ? new double[]{0.0, 1.0, 0.0} : scaleVector(var0, 1.0 / var1);
   }
}
