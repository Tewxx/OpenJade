// Jade recovery: original class: jade.deps.eLz.qtipGtgEk
package jade.client.common;

public final class ScreenProjector {
   private final double[] viewProjectionMatrix = new double[16];
   private final double[] AappwT = new double[4];
   private final double[] ttx = new double[4];
   private final double[] lineStart = new double[4];
   private final double[] ITjRr = new double[4];
   private int GVukz;
   private int DfB;
   private double projectionScaleY;
   public final double[] projectedPoint = new double[2];
   public final double[] projectedSegment = new double[4];
   public final double[] screenBounds = new double[4];
   private static final int[][] BOX_EDGES = new int[][]{{0, 1}, {0, 2}, {0, 4}, {1, 3}, {1, 5}, {2, 3}, {2, 6}, {3, 7}, {4, 5}, {4, 6}, {5, 7}, {6, 7}};
   private final double[][] cornerClipPositions = new double[8][4];
   private final double[][] twLkuc = new double[16][4];
   private final double[][] clipScratch = new double[16][4];
   private static final int[][] thgK = new int[][]{{0, 1, 3, 2}, {4, 6, 7, 5}, {0, 4, 5, 1}, {2, 3, 7, 6}, {0, 2, 6, 4}, {1, 5, 7, 3}};

   public void DyjK(float[] var1, float[] var2, int var3, int var4) {
      this.GVukz = var3;
      this.DfB = var4;
      this.projectionScaleY = var2[5] * var4 * 0.5;

      for (int var5 = 0; var5 < 4; var5++) {
         for (int var6 = 0; var6 < 4; var6++) {
            double var7 = 0.0;

            for (int var9 = 0; var9 < 4; var9++) {
               var7 += var2[var9 * 4 + var6] * var1[var5 * 4 + var9];
            }

            this.viewProjectionMatrix[var5 * 4 + var6] = var7;
         }
      }
   }

   private void transformPointByMatrix(double var1, double var3, double var5, double[] var7) {
      for (int var8 = 0; var8 < 4; var8++) {
         var7[var8] = this.viewProjectionMatrix[var8] * var1 + this.viewProjectionMatrix[4 + var8] * var3 + this.viewProjectionMatrix[8 + var8] * var5 + this.viewProjectionMatrix[12 + var8];
      }
   }

   public boolean projectPoint(double var1, double var3, double var5) {
      this.transformPointByMatrix(var1, var3, var5, this.AappwT);
      if (!(this.AappwT[3] < 1.0E-6) && !(this.AappwT[2] < -this.AappwT[3]) && !(this.AappwT[2] > this.AappwT[3])) {
         this.projectedPoint[0] = (this.AappwT[0] / this.AappwT[3] + 1.0) * this.GVukz * 0.5;
         this.projectedPoint[1] = (1.0 - this.AappwT[1] / this.AappwT[3]) * this.DfB * 0.5;
         return Double.isFinite(this.projectedPoint[0]) && Double.isFinite(this.projectedPoint[1]);
      } else {
         return false;
      }
   }

   public float getProjectedScale() {
      return (float)Math.abs(this.projectionScaleY / this.AappwT[3]);
   }

   public boolean Naj1(double var1, double var3, double var5, double var7, double var9, double var11) {
      this.transformPointByMatrix(var1, var3, var5, this.lineStart);
      this.transformPointByMatrix(var7, var9, var11, this.ITjRr);
      if (!this.clipSegmentToFrustum(this.lineStart, this.ITjRr)) {
         return false;
      } else {
         this.projectedSegment[0] = (this.AappwT[0] / this.AappwT[3] + 1.0) * this.GVukz * 0.5;
         this.projectedSegment[1] = (1.0 - this.AappwT[1] / this.AappwT[3]) * this.DfB * 0.5;
         this.projectedSegment[2] = (this.ttx[0] / this.ttx[3] + 1.0) * this.GVukz * 0.5;
         this.projectedSegment[3] = (1.0 - this.ttx[1] / this.ttx[3]) * this.DfB * 0.5;
         return areAllFinite(this.projectedSegment[0], this.projectedSegment[1], this.projectedSegment[2], this.projectedSegment[3]);
      }
   }

   public boolean drawProjectedLine(double var1, double var3, double var5, double var7, double var9, double var11, ExternalRenderBuffer var13, int var14, float var15) {
      if (!this.Naj1(var1, var3, var5, var7, var9, var11)) {
         return false;
      } else {
         var13.drawLine(this.projectedSegment[0], this.projectedSegment[1], this.projectedSegment[2], this.projectedSegment[3], var14, var15);
         return true;
      }
   }

   private static boolean areAllFinite(double var0, double var2, double var4, double var6) {
      return Double.isFinite(var0) && Double.isFinite(var2) && Double.isFinite(var4) && Double.isFinite(var6);
   }

   public void projectOffscreenDirection(double var1, double var3, double var5) {
      this.transformPointByMatrix(var1, var3, var5, this.AappwT);
      this.projectedPoint[0] = this.AappwT[0] * this.GVukz * 0.5;
      this.projectedPoint[1] = -this.AappwT[1] * this.DfB * 0.5;
      if (Math.hypot(this.projectedPoint[0], this.projectedPoint[1]) < 1.0E-6) {
         this.projectedPoint[1] = this.AappwT[3] < 0.0 ? 1.0 : -1.0;
      }
   }

   public void HIQRn(ExternalRenderBuffer var1, int var2) {
      for (int[] var6 : thgK) {
         double[][] var7 = this.twLkuc;
         double[][] var8 = this.clipScratch;
         int var9 = 4;

         for (int var10 = 0; var10 < 4; var10++) {
            System.arraycopy(this.cornerClipPositions[var6[var10]], 0, var7[var10], 0, 4);
         }

         for (int var22 = 0; var22 < 6 && var9 > 0; var22++) {
            int var11 = 0;

            for (int var12 = 0; var12 < var9; var12++) {
               double[] var13 = var7[var12];
               double[] var14 = var7[(var12 + 1) % var9];
               double var15 = getClipDistance(var13, var22);
               double var17 = getClipDistance(var14, var22);
               if (var15 >= 0.0) {
                  System.arraycopy(var13, 0, var8[var11++], 0, 4);
               }

               if (var15 < 0.0 != var17 < 0.0) {
                  double var19 = var15 / (var15 - var17);

                  for (int var21 = 0; var21 < 4; var21++) {
                     var8[var11][var21] = var13[var21] + (var14[var21] - var13[var21]) * var19;
                  }

                  var11++;
               }
            }

            var9 = var11;
            double[][] var24 = var7;
            var7 = var8;
            var8 = var24;
         }

         for (int var23 = 1; var23 < var9 - 1; var23++) {
            if (!(var7[0][3] < 1.0E-6) && !(var7[var23][3] < 1.0E-6) && !(var7[var23 + 1][3] < 1.0E-6)) {
               var1.VogZb(
                  (var7[0][0] / var7[0][3] + 1.0) * this.GVukz * 0.5,
                  (1.0 - var7[0][1] / var7[0][3]) * this.DfB * 0.5,
                  (var7[var23][0] / var7[var23][3] + 1.0) * this.GVukz * 0.5,
                  (1.0 - var7[var23][1] / var7[var23][3]) * this.DfB * 0.5,
                  (var7[var23 + 1][0] / var7[var23 + 1][3] + 1.0) * this.GVukz * 0.5,
                  (1.0 - var7[var23 + 1][1] / var7[var23 + 1][3]) * this.DfB * 0.5,
                  var2
               );
            }
         }
      }
   }

   private static double getClipDistance(double[] var0, int var1) {
      int var2 = var1 / 2;
      return var0[3] + (var1 % 2 == 0 ? var0[var2] : -var0[var2]);
   }

   private boolean clipSegmentToFrustum(double[] var1, double[] var2) {
      double var3 = 0.0;
      double var5 = 1.0;

      for (int var7 = 0; var7 < 6; var7++) {
         double var8 = getClipDistance(var1, var7);
         double var10 = getClipDistance(var2, var7);
         if (var8 < 0.0 && var10 < 0.0) {
            return false;
         }

         if (var8 < 0.0) {
            var3 = Math.max(var3, var8 / (var8 - var10));
         }

         if (var10 < 0.0) {
            var5 = Math.min(var5, var8 / (var8 - var10));
         }
      }

      if (var3 > var5) {
         return false;
      } else {
         for (int var12 = 0; var12 < 4; var12++) {
            this.AappwT[var12] = var1[var12] + (var2[var12] - var1[var12]) * var3;
            this.ttx[var12] = var1[var12] + (var2[var12] - var1[var12]) * var5;
         }

         return this.AappwT[3] >= 1.0E-6 && this.ttx[3] >= 1.0E-6;
      }
   }

   public boolean drawProjectedBox(double var1, double var3, double var5, double var7, double var9, double var11, ExternalRenderBuffer var13, int var14, float var15, boolean var16) {
      for (int var17 = 0; var17 < 8; var17++) {
         this.transformPointByMatrix((var17 & 1) == 0 ? var1 : var7, (var17 & 2) == 0 ? var3 : var9, (var17 & 4) == 0 ? var5 : var11, this.cornerClipPositions[var17]);
      }

      this.screenBounds[0] = this.GVukz;
      this.screenBounds[1] = this.DfB;
      this.screenBounds[2] = this.screenBounds[3] = 0.0;
      boolean var30 = false;

      for (int[] var21 : BOX_EDGES) {
         if (this.clipSegmentToFrustum(this.cornerClipPositions[var21[0]], this.cornerClipPositions[var21[1]])) {
            double var22 = (this.AappwT[0] / this.AappwT[3] + 1.0) * this.GVukz * 0.5;
            double var24 = (1.0 - this.AappwT[1] / this.AappwT[3]) * this.DfB * 0.5;
            double var26 = (this.ttx[0] / this.ttx[3] + 1.0) * this.GVukz * 0.5;
            double var28 = (1.0 - this.ttx[1] / this.ttx[3]) * this.DfB * 0.5;
            if (Double.isFinite(var22) && Double.isFinite(var24) && Double.isFinite(var26) && Double.isFinite(var28)) {
               var30 = true;
               this.screenBounds[0] = Math.min(this.screenBounds[0], Math.min(var22, var26));
               this.screenBounds[1] = Math.min(this.screenBounds[1], Math.min(var24, var28));
               this.screenBounds[2] = Math.max(this.screenBounds[2], Math.max(var22, var26));
               this.screenBounds[3] = Math.max(this.screenBounds[3], Math.max(var24, var28));
               if (var16) {
                  var13.drawLine(var22, var24, var26, var28, var14, var15);
               }
            }
         }
      }

      return var30;
   }
}
