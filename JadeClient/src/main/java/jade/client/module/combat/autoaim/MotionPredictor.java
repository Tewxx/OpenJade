// Jade recovery: original class: jade.deps.eLz.OCLJCo
package jade.client.module.combat.autoaim;

import java.util.HashMap;
import java.util.Map;

public final class MotionPredictor {
   private static final int AHr = 20;
   private static final double MAX_POSITION_JUMP_DISTANCE = 64.0;
   private static final double MAX_ACCELERATION = 0.12;
   private static final double xF0 = 0.65;
   private static final double MAX_UPWARD_VELOCITY = 0.82;
   private static final double MIN_DOWNWARD_VELOCITY = 1.5;
   private static final double VELOCITY_PACKET_WINDOW_TICKS = 3.5;
   private static final int STALE_VELOCITY_TICKS = 3;
   private static final double COLLISION_EPSILON = 0.001;
   private static final double IZn = 0.08;
   private static final double VERTICAL_DRAG = 0.98;
   private static final String[] HZn = new String[]{"velocity", "acceleration", "turn", "airborne", "stationary"};
   private final Map<Integer, MotionPredictor$4> OIaRp = new HashMap<>();
   private double currentTime = Double.NaN;

   public synchronized void setCurrentTime(double var1) {
      this.currentTime = var1;
   }

   public synchronized void resetAll() {
      this.OIaRp.clear();
   }

   public synchronized void AVgoK(int var1) {
      this.OIaRp.remove(var1);
   }

   public synchronized boolean hasSamples(int var1) {
      MotionPredictor$4 var2 = this.OIaRp.get(var1);
      return var2 != null && !var2.samples.isEmpty();
   }

   public synchronized double computeSquaredDistanceToLastSample(int var1, double var2, double var4, double var6) {
      MotionPredictor$4 var8 = this.OIaRp.get(var1);
      MotionPredictor$3 var9 = var8 == null ? null : var8.samples.peekLast();
      return var9 == null ? Double.MAX_VALUE : square(var9.posX - var2) + square(var9.MZhIl - var4) + square(var9.posZ - var6);
   }

   public synchronized double[] TjaA(int var1) {
      MotionPredictor$4 var2 = this.OIaRp.get(var1);
      if (var2 != null && var2.samples.size() >= 2) {
         MotionPredictor$3[] var3 = var2.samples.toArray(new MotionPredictor$3[var2.samples.size()]);
         return this.computeAverageVelocity(var3);
      } else {
         return new double[]{0.0, 0.0};
      }
   }

   public synchronized boolean hasRecentVelocity(int var1) {
      MotionPredictor$4 var2 = this.OIaRp.get(var1);
      return var2 != null && this.hasRecentVelocityPacket(var2);
   }

   public synchronized void SJz3(int var1, double var2, double var4, double var6, double var8, boolean var10) {
      MotionPredictor$4 var11 = this.OIaRp.get(var1);
      if (var11 == null) {
         var11 = new MotionPredictor$4();
         this.OIaRp.put(var1, var11);
      }

      boolean var12 = this.hasRecentVelocityPacket(var11);
      if (var12 && var11.hintTickCount == 0) {
         var11.samples.clear();
         this.resetCandidateWeights(var11);
      }

      MotionPredictor$3 var13 = var11.samples.peekLast();
      double var14 = var13 == null ? 0.0 : square(var4 - var13.posX) + square(var6 - var13.MZhIl) + square(var8 - var13.posZ);
      if (!var10 && !(var14 > 64.0) && (var13 == null || !(var2 <= var13.azrEg))) {
         if (var13 != null && var11.samples.size() >= 3) {
            this.updateCandidateWeights(var11, var13, var2, var4, var6, var8);
         }
      } else {
         var11.samples.clear();
         this.resetTrackedMotion(var11);
      }

      var11.samples.addLast(new MotionPredictor$3(var2, var4, var6, var8));
      this.resetPathCache(var11);
      if (var12) {
         var11.hintTickCount++;
         if (var11.hintTickCount >= 3) {
            this.clearVelocityPacketState(var11);
         }
      } else {
         this.clearVelocityPacketState(var11);
      }

      while (var11.samples.size() > 20) {
         var11.samples.removeFirst();
      }
   }

   public synchronized void applyVelocityPacket(int var1, double var2, double var4, double var6, double var8) {
      MotionPredictor$4 var10 = this.OIaRp.get(var1);
      if (var10 == null) {
         var10 = new MotionPredictor$4();
         this.OIaRp.put(var1, var10);
      }

      var10.hintVelocityX = var4;
      var10.hintVelocityY = var6;
      var10.AJVB = var8;
      var10.FRWq = var2;
      var10.hasMovementHint = true;
      var10.hintTickCount = 0;
      this.resetPathCache(var10);

      for (int var11 = 0; var11 < var10.YQOZK.length - 1; var11++) {
         var10.YQOZK[var11] = Math.max(var10.YQOZK[var11], 0.45);
      }

      var10.YQOZK[4] = Math.max(var10.YQOZK[4], 0.18);
   }

   public synchronized void updateMotionState(int var1, float var2, boolean var3, boolean var4, int var5) {
      this.NZno(var1, var2, var3, var4, false, var5);
   }

   public synchronized void NZno(int var1, float var2, boolean var3, boolean var4, boolean var5, int var6) {
      this.updateMotionStateInternal(var1, var2, var3, var4, var5, var6, false);
   }

   public synchronized void updateMotionStateInternal(int var1, float var2, boolean var3, boolean var4, boolean var5, int var6, boolean var7) {
      MotionPredictor$4 var8 = this.OIaRp.get(var1);
      if (var8 == null) {
         var8 = new MotionPredictor$4();
         this.OIaRp.put(var1, var8);
      }

      if (var8.hasInputState && var8.usingItem != var3) {
         this.resetTrackedMotion(var8);
         this.resetPathCache(var8);
      } else if (var8.hasInputState && var8.placingBlockDown != var7) {
         this.resetPathCache(var8);
      }

      var8.vez = var2;
      var8.usingItem = var3;
      var8.sprinting = var4;
      var8.ZcwZ5 = var5;
      var8.placingBlockDown = var7;
      var8.kev = var6;
      var8.hasInputState = true;
   }

   public synchronized TargetPrediction WFwr15(int var1, double var2) {
      return this.ygp3(var1, var2, null);
   }

   public synchronized TargetPrediction ygp3(int var1, double var2, MotionPredictor$1 var4) {
      MotionPredictor$4 var5 = this.OIaRp.get(var1);
      if (var5 != null && !var5.samples.isEmpty()) {
         var2 = Math.max(0.0, Math.min(80.0, var2));
         MotionPredictor$2 var6 = this.buildMotionModel(var5);
         this.updateAirborneWeights(var5, var6);
         double var7 = Math.min(80.0, var2 + var6.FiU);
         double[][] var9 = this.predictCandidatePositions(var5, var6, var7);
         int var10 = this.selectPredictionIndex(var5, var6);
         double var11 = var9[var10][0];
         double var13 = var9[var10][1];
         double var15 = var9[var10][2];
         if (var4 != null && var7 > 0.0) {
            double[] var17 = this.resolveCollisionFreePosition(var5, var6, var10, var7, var4);
            var11 = var17[0];
            var13 = var17[1];
            var15 = var17[2];
         }

         if (var6.zo8) {
            var11 = var6.lastSample.posX;
            var13 = var6.lastSample.MZhIl;
            var15 = var6.lastSample.posZ;
         }

         double var30 = Math.max(0.015, var5.YQOZK[var10]);
         double var19 = Math.min(1.0, Math.max(0.18, (var5.samples.size() - 1) / 5.0));
         if (var6.cEe) {
            var19 = Math.max(var19, Math.min(1.0, 0.58 + var6.FiU * 0.1));
         }

         double var21 = !var6.cEe && !(var6.FiU <= 2.0) ? Math.exp(-(var6.FiU - 2.0) * 0.18) : 1.0;
         double var23 = Math.exp(-var7 * (0.005 + Math.min(0.012, var30 * 0.009)));
         double var25 = clampValue(var19 * Math.exp(-var30 * 1.9) * var21 * var23, 0.02, 0.99);
         if (var6.zo8) {
            var25 = Math.min(var25, 0.25);
         } else if (var6.applyingVelocityPacket || var6.recentVelocityPacket) {
            var25 = Math.min(var25, 0.3);
         }

         double var27 = 0.08 + var30 * 1.8 + var7 * (0.018 + var30 * 0.02);
         if (var6.zo8) {
            var27 = Math.max(var27, 0.75);
         }

         return new TargetPrediction(var11, var13, var15, var27, var25, HZn[var10], this.describeMotionState(var5, var6));
      } else {
         return null;
      }
   }

   private double[] resolveCollisionFreePosition(MotionPredictor$4 var1, MotionPredictor$2 var2, int var3, double var4, MotionPredictor$1 var6) {
      if (var1.cachedCollisionPredicate != var6 || var1.ZBiq3 != var3 || Double.doubleToLongBits(var1.AxmlyD) != Double.doubleToLongBits(this.currentTime) || var4 < var1.SuoBk) {
         var1.cachedCollisionPredicate = var6;
         var1.AxmlyD = this.currentTime;
         var1.ZBiq3 = var3;
         var1.SuoBk = 0.0;
         var1.cu95 = Double.MAX_VALUE;
         var1.MuE = false;
         var1.xBlocked = false;
         var1.wrhla = false;
         double[] var7 = this.predictCandidatePositions(var1, var2, 0.0)[var3];
         var1.LUSOr = var7[0];
         var1.nqY = var7[1];
         var1.lastSafeZ = var7[2];
         var1.verticalVelocity = 0.0;
      }

      if (var4 >= var1.cu95) {
         if (var1.MuE) {
            double[] var22 = this.predictCandidatePositions(var1, var2, var4)[var3];
            return new double[]{var22[0], var1.nqY, var22[2]};
         } else {
            if (var1.xBlocked || var1.wrhla) {
               double[] var21 = this.predictCandidatePositions(var1, var2, var4)[var3];
               double var8 = var1.xBlocked ? var21[0] : var1.LUSOr;
               double var23 = var1.wrhla ? var21[2] : var1.lastSafeZ;
               if (var6.isPositionClear(var8, var21[1], var23)) {
                  return new double[]{var8, var21[1], var23};
               }

               if (var6.isPositionClear(var8, var1.nqY, var23)) {
                  return new double[]{var8, var1.nqY, var23};
               }
            }

            return new double[]{var1.LUSOr, var1.nqY, var1.lastSafeZ};
         }
      } else {
         while (var1.SuoBk < var4) {
            double var20 = Math.min(var4, var1.SuoBk + 0.5);
            double[] var9 = this.predictCandidatePositions(var1, var2, var20)[var3];
            if (!var2.falling && !var1.placingBlockDown) {
               var9[1] = this.resolveVerticalPosition(var1, var9[0], var9[2], var20 - var1.SuoBk, var6);
            }

            if (!var6.isPositionClear(var9[0], var9[1], var9[2])) {
               double var10 = var1.SuoBk;
               double var12 = var20;
               double[] var14 = new double[]{var1.LUSOr, var1.nqY, var1.lastSafeZ};

               for (int var15 = 0; var15 < 7; var15++) {
                  double var16 = (var10 + var12) * 0.5;
                  double[] var18 = this.predictCandidatePositions(var1, var2, var16)[var3];
                  if (var6.isPositionClear(var18[0], var18[1], var18[2])) {
                     var10 = var16;
                     var14 = var18;
                  } else {
                     var12 = var16;
                  }
               }

               var1.cu95 = var12;
               var1.LUSOr = var14[0];
               var1.nqY = var14[1];
               var1.lastSafeZ = var14[2];
               var1.MuE = var6.isPositionClear(var9[0], var14[1], var9[2]);
               if (var1.MuE) {
                  if (var9[1] < var14[1] && var6.isPositionClear(var9[0], var14[1] + 0.001, var9[2])) {
                     var1.nqY = var14[1] + 0.001;
                  }

                  double[] var25 = this.predictCandidatePositions(var1, var2, var4)[var3];
                  return new double[]{var25[0], var1.nqY, var25[2]};
               }

               var1.xBlocked = var6.isPositionClear(var9[0], var9[1], var14[2]);
               var1.wrhla = var6.isPositionClear(var14[0], var9[1], var9[2]);
               if (var1.xBlocked || var1.wrhla) {
                  double[] var24 = this.predictCandidatePositions(var1, var2, var4)[var3];
                  double var26 = var1.xBlocked ? var24[0] : var14[0];
                  double var27 = var1.wrhla ? var24[2] : var14[2];
                  if (var6.isPositionClear(var26, var24[1], var27)) {
                     return new double[]{var26, var24[1], var27};
                  }

                  if (var6.isPositionClear(var26, var14[1], var27)) {
                     return new double[]{var26, var14[1], var27};
                  }

                  var1.xBlocked = false;
                  var1.wrhla = false;
               }

               return var14;
            }

            var1.SuoBk = var20;
            var1.LUSOr = var9[0];
            var1.nqY = var9[1];
            var1.lastSafeZ = var9[2];
         }

         return new double[]{var1.LUSOr, var1.nqY, var1.lastSafeZ};
      }
   }

   private double resolveVerticalPosition(MotionPredictor$4 var1, double var2, double var4, double var6, MotionPredictor$1 var8) {
      double var9 = var1.nqY;
      if (!var8.isPositionClear(var2, var9, var4)) {
         return var9;
      } else if (!var8.isPositionClear(var2, var9 - 0.001, var4)) {
         var1.verticalVelocity = 0.0;
         return var9;
      } else {
         double var11 = Math.pow(0.98, var6);
         double var13 = (var1.verticalVelocity - 0.08 * var6) * var11;
         double var15 = var9 + (var1.verticalVelocity + var13) * 0.5 * var6;
         var1.verticalVelocity = var13;
         if (var8.isPositionClear(var2, var15, var4)) {
            return var15;
         } else {
            double var17 = var9;
            double var19 = var15;

            for (int var21 = 0; var21 < 8; var21++) {
               double var22 = (var17 + var19) * 0.5;
               if (var8.isPositionClear(var2, var22, var4)) {
                  var17 = var22;
               } else {
                  var19 = var22;
               }
            }

            var1.verticalVelocity = 0.0;
            return var17 + 0.001;
         }
      }
   }

   private void resetPathCache(MotionPredictor$4 var1) {
      var1.cachedCollisionPredicate = null;
      var1.ZBiq3 = -1;
      var1.SuoBk = 0.0;
      var1.cu95 = Double.MAX_VALUE;
      var1.MuE = false;
      var1.xBlocked = false;
      var1.wrhla = false;
      var1.verticalVelocity = 0.0;
   }

   private void updateCandidateWeights(MotionPredictor$4 var1, MotionPredictor$3 var2, double var3, double var5, double var7, double var9) {
      double var11 = var3 - var2.azrEg;
      if (!(var11 <= 0.0) && !(var11 > 10.0)) {
         MotionPredictor$2 var13 = this.buildMotionModel(var1);
         double[][] var14 = this.predictCandidatePositions(var1, var13, var11);

         for (int var15 = 0; var15 < var14.length; var15++) {
            double var16 = var14[var15][0] - var5;
            double var18 = var14[var15][1] - var7;
            double var20 = var14[var15][2] - var9;
            double var22 = Math.sqrt(var16 * var16 + var18 * var18 + var20 * var20);
            var1.YQOZK[var15] = var1.YQOZK[var15] * 0.78 + Math.min(4.0, var22) * 0.22;
         }
      }
   }

   private MotionPredictor$2 buildMotionModel(MotionPredictor$4 var1) {
      MotionPredictor$2 var2 = new MotionPredictor$2();
      var2.lastSample = var1.samples.peekLast();
      if (var2.lastSample != null && !Double.isNaN(this.currentTime)) {
         var2.FiU = clampValue(this.currentTime - var2.lastSample.azrEg, 0.0, 20.0);
      }

      if (var1.samples.size() < 2) {
         var2.cEe = true;
         return var2;
      } else {
         MotionPredictor$3[] var3 = var1.samples.toArray(new MotionPredictor$3[var1.samples.size()]);
         MotionPredictor$3 var4 = var3[var3.length - 2];
         MotionPredictor$3 var5 = var3[var3.length - 1];
         double[] var6 = this.computeAverageVelocity(var3);
         var2.averageVelocityX = var6[0];
         var2.GAW = var6[1];
         double var7 = DyAi(var5.azrEg - var4.azrEg);
         var2.sampleInterval = var7;
         var2.tYho = (var5.posX - var4.posX) / var7;
         var2.qnd = (var5.MZhIl - var4.MZhIl) / var7;
         var2.aimlP = this.smoothVerticalVelocity(var5.MZhIl - var4.MZhIl, var7);
         var2.Jewm = (var5.posZ - var4.posZ) / var7;
         var2.recentVelocityPacket = this.hasRecentVelocityPacket(var1);
         var2.applyingVelocityPacket = !var2.recentVelocityPacket && var1.hasMovementHint && (Double.isNaN(this.currentTime) || Double.isNaN(var1.FRWq) || this.currentTime - var1.FRWq <= 3.5);
         if (var2.applyingVelocityPacket) {
            var2.tYho = var2.tYho * 0.35 + var1.hintVelocityX * 0.65;
            var2.aimlP = var2.aimlP * 0.35 + var1.hintVelocityY * 0.65;
            var2.qnd = var2.qnd * 0.35 + var1.hintVelocityY * 0.65;
            var2.Jewm = var2.Jewm * 0.35 + var1.AJVB * 0.65;
         }

         this.clampModelVelocity(var1, var2);
         if (var3.length >= 3) {
            MotionPredictor$3 var9 = var3[var3.length - 3];
            double var10 = DyAi(var4.azrEg - var9.azrEg);
            double var12 = (var4.posX - var9.posX) / var10;
            double var14 = (var4.MZhIl - var9.MZhIl) / var10;
            double var16 = (var4.posZ - var9.posZ) / var10;
            double var18 = Math.max(0.05, (var7 + var10) * 0.5);
            var2.DCbVxw = clampValue((var2.tYho - var12) / var18, -0.12, 0.12);
            var2.DjF = clampValue((var2.aimlP - var14) / var18, -0.12, 0.12);
            var2.accelerationZ = clampValue((var2.Jewm - var16) / var18, -0.12, 0.12);
            double var20 = Math.sqrt(var12 * var12 + var16 * var16);
            double var22 = Math.sqrt(var2.tYho * var2.tYho + var2.Jewm * var2.Jewm);
            var2.speedDropped = var20 > 0.16 && var22 < var20 * 0.72;
            var2.speedRetention = this.computeSpeedRetention(var3);
            var2.turnRate = this.computeTurnRate(var3);
         }

         var2.cEe = this.isNearlyStill(var3) || var2.FiU >= 4.0 || var1.hasMovementHint && !var2.applyingVelocityPacket;
         if (var2.cEe) {
            var2.tYho = 0.0;
            var2.aimlP = 0.0;
            var2.Jewm = 0.0;
            var2.DCbVxw = 0.0;
            var2.DjF = 0.0;
            var2.accelerationZ = 0.0;
            var2.turnRate = 0.0;
         }

         var2.falling = !var2.cEe && !var1.ZcwZ5 && this.hasVerticalMotion(var3, var2);
         var2.straightLineSprint = var1.sprinting && this.isMovingStraight(var3) && (!var2.falling || var2.speedRetention > 0.85);
         if (var2.straightLineSprint) {
            var2.tYho = var2.averageVelocityX;
            var2.Jewm = var2.GAW;
            var2.speedRetention = 1.0;
         } else if (!var2.falling) {
            var2.speedRetention = 1.0;
         }

         return var2;
      }
   }

   private boolean isMovingStraight(MotionPredictor$3[] var1) {
      if (var1.length < 4) {
         return false;
      } else {
         int var2 = Math.max(1, var1.length - 8);
         double var3 = var1[var1.length - 1].posX - var1[var2 - 1].posX;
         double var5 = var1[var1.length - 1].posZ - var1[var2 - 1].posZ;
         double var7 = Math.sqrt(var3 * var3 + var5 * var5);
         if (var7 < 0.15) {
            return false;
         } else {
            double var9 = var3 / var7;
            double var11 = var5 / var7;

            for (int var13 = var2; var13 < var1.length; var13++) {
               MotionPredictor$3 var14 = var1[var13 - 1];
               MotionPredictor$3 var15 = var1[var13];
               double var16 = var15.posX - var14.posX;
               double var18 = var15.posZ - var14.posZ;
               double var20 = Math.sqrt(var16 * var16 + var18 * var18);
               if (var20 < 0.04) {
                  return false;
               }

               double var22 = (var16 * var9 + var18 * var11) / var20;
               if (var22 < 0.965) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private double[] computeAverageVelocity(MotionPredictor$3[] var1) {
      int var2 = Math.max(1, var1.length - 8);
      double var3 = 0.0;
      double var5 = 0.0;
      double var7 = 0.0;

      for (int var9 = var2; var9 < var1.length; var9++) {
         MotionPredictor$3 var10 = var1[var9 - 1];
         MotionPredictor$3 var11 = var1[var9];
         double var12 = DyAi(var11.azrEg - var10.azrEg);
         var3 += var11.posX - var10.posX;
         var5 += var11.posZ - var10.posZ;
         var7 += var12;
      }

      if (var7 <= 0.0) {
         return new double[]{0.0, 0.0};
      } else {
         double var15 = var3 / var7;
         double var16 = var5 / var7;
         double var13 = Math.sqrt(var15 * var15 + var16 * var16);
         if (var13 > 0.65) {
            var15 *= 0.65 / var13;
            var16 *= 0.65 / var13;
         }

         return new double[]{var15, var16};
      }
   }

   private void updateAirborneWeights(MotionPredictor$4 var1, MotionPredictor$2 var2) {
      if (!var1.kCp99) {
         var1.LPNLp = var2.falling;
         var1.kCp99 = true;
      } else if (var1.LPNLp != var2.falling) {
         var1.LPNLp = var2.falling;
         if (var2.falling) {
            var1.YQOZK[3] = Math.min(var1.YQOZK[3], 0.12);
         } else {
            var1.YQOZK[4] = Math.min(var1.YQOZK[4], 0.08);
         }
      }
   }

   private void clampModelVelocity(MotionPredictor$4 var1, MotionPredictor$2 var2) {
      double var3 = Math.sqrt(var2.tYho * var2.tYho + var2.Jewm * var2.Jewm);
      if (var3 > 0.65) {
         var2.zo8 = true;
      }

      double var5 = var1.hasInputState && var1.usingItem ? 0.11 : 0.65;
      if (var3 > var5 && var3 > 1.0E-6) {
         var2.tYho *= var5 / var3;
         var2.Jewm *= var5 / var3;
      }

      if (var2.aimlP > 0.82 || var2.qnd > 0.82 || var2.aimlP < -1.5 || var2.qnd < -1.5) {
         var2.zo8 = true;
      }

      var2.aimlP = clampValue(var2.aimlP, -1.5, 0.82);
      var2.qnd = clampValue(var2.qnd, -1.5, 0.82);
   }

   private String describeMotionState(MotionPredictor$4 var1, MotionPredictor$2 var2) {
      if (var2.cEe) {
         return var1.usingItem ? "blocking_still" : "still";
      } else {
         double var3 = Math.toRadians(var1.vez);
         double var5 = var2.tYho * -Math.sin(var3) + var2.Jewm * Math.cos(var3);
         double var7 = var2.tYho * Math.cos(var3) + var2.Jewm * Math.sin(var3);
         String var9;
         if (Math.abs(var5) > Math.abs(var7) * 1.35) {
            var9 = var5 >= 0.0 ? "forward" : "backward";
         } else if (Math.abs(var7) > Math.abs(var5) * 1.35) {
            var9 = var7 >= 0.0 ? "right" : "left";
         } else {
            var9 = (var5 >= 0.0 ? "forward_" : "backward_") + (var7 >= 0.0 ? "right" : "left");
         }

         String var10 = var1.usingItem ? "blocking_" + var9 : (var1.sprinting ? "sprinting_" : "walking_") + var9;
         if (var2.falling) {
            var10 = var10 + (var1.kev >= 0 ? "_jump_boost_" + (var1.kev + 1) : "_airborne");
         }

         return var10;
      }
   }

   private double computeTurnRate(MotionPredictor$3[] var1) {
      if (var1.length < 4) {
         return 0.0;
      } else {
         int var2 = Math.max(2, var1.length - 3);
         double var3 = 0.0;
         int var5 = 0;
         int var6 = 0;

         for (int var7 = var2; var7 < var1.length; var7++) {
            MotionPredictor$3 var8 = var1[var7 - 2];
            MotionPredictor$3 var9 = var1[var7 - 1];
            MotionPredictor$3 var10 = var1[var7];
            double var11 = DyAi(var9.azrEg - var8.azrEg);
            double var13 = DyAi(var10.azrEg - var9.azrEg);
            double var15 = (var9.posX - var8.posX) / var11;
            double var17 = (var9.posZ - var8.posZ) / var11;
            double var19 = (var10.posX - var9.posX) / var13;
            double var21 = (var10.posZ - var9.posZ) / var13;
            if (!(Math.sqrt(var15 * var15 + var17 * var17) < 0.05) && !(Math.sqrt(var19 * var19 + var21 * var21) < 0.05)) {
               double var23 = wrapAngleToPi(Math.atan2(var21, var19) - Math.atan2(var17, var15)) / Math.max(0.05, (var11 + var13) * 0.5);
               if (!(Math.abs(var23) < 0.015)) {
                  int var25 = var23 < 0.0 ? -1 : 1;
                  if (var6 != 0 && var6 != var25) {
                     return 0.0;
                  }

                  var6 = var25;
                  var3 += var23;
                  var5++;
               }
            }
         }

         return var5 < 2 ? 0.0 : clampValue(var3 / var5, -0.25, 0.25);
      }
   }

   private boolean isNearlyStill(MotionPredictor$3[] var1) {
      if (var1.length < 4) {
         return false;
      } else {
         int var2 = Math.max(1, var1.length - 4);

         for (int var3 = var2; var3 < var1.length; var3++) {
            MotionPredictor$3 var4 = var1[var3 - 1];
            MotionPredictor$3 var5 = var1[var3];
            double var6 = DyAi(var5.azrEg - var4.azrEg);
            double var8 = Math.sqrt(square(var5.posX - var4.posX) + square(var5.posZ - var4.posZ)) / var6;
            double var10 = Math.abs(var5.MZhIl - var4.MZhIl) / var6;
            if (var8 > 0.018 || var10 > 0.025) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean hasVerticalMotion(MotionPredictor$3[] var1, MotionPredictor$2 var2) {
      if (Math.abs(var2.qnd) > 0.035) {
         return true;
      } else if (var1.length < 3) {
         return false;
      } else {
         MotionPredictor$3 var3 = var1[var1.length - 3];
         MotionPredictor$3 var4 = var1[var1.length - 2];
         MotionPredictor$3 var5 = var1[var1.length - 1];
         double var6 = (var4.MZhIl - var3.MZhIl) / DyAi(var4.azrEg - var3.azrEg);
         double var8 = (var5.MZhIl - var4.MZhIl) / DyAi(var5.azrEg - var4.azrEg);
         return Math.abs(var6) > 0.025 && Math.abs(var8) > 0.015;
      }
   }

   private double computeSpeedRetention(MotionPredictor$3[] var1) {
      if (var1.length < 4) {
         return 1.0;
      } else {
         int var2 = Math.max(2, var1.length - 3);
         double var3 = 0.0;
         double var5 = Double.MAX_VALUE;
         double var7 = -Double.MAX_VALUE;
         int var9 = 0;
         int var10 = var2;

         while (var10 < var1.length) {
            MotionPredictor$3 var11 = var1[var10 - 2];
            MotionPredictor$3 var12 = var1[var10 - 1];
            MotionPredictor$3 var13 = var1[var10];
            double var14 = DyAi(var12.azrEg - var11.azrEg);
            double var16 = DyAi(var13.azrEg - var12.azrEg);
            double var18 = (var12.posX - var11.posX) / var14;
            double var20 = (var12.posZ - var11.posZ) / var14;
            double var22 = (var13.posX - var12.posX) / var16;
            double var24 = (var13.posZ - var12.posZ) / var16;
            double var26 = Math.sqrt(var18 * var18 + var20 * var20);
            double var28 = Math.sqrt(var22 * var22 + var24 * var24);
            if (!(var26 < 0.05) && !(var28 < 0.05)) {
               double var30 = (var18 * var22 + var20 * var24) / (var26 * var28);
               if (var30 < 0.985) {
                  return 1.0;
               }

               double var32 = var28 / var26;
               if (!(var32 < 0.72) && !(var32 > 0.975)) {
                  var5 = Math.min(var5, var32);
                  var7 = Math.max(var7, var32);
                  var3 += var32;
                  var9++;
                  var10++;
                  continue;
               }

               return 1.0;
            }

            return 1.0;
         }

         return var9 >= 2 && var7 - var5 <= 0.06 ? clampValue(var3 / var9, 0.72, 0.975) : 1.0;
      }
   }

   private double[][] predictCandidatePositions(MotionPredictor$4 var1, MotionPredictor$2 var2, double var3) {
      double[][] var5 = new double[5][3];
      MotionPredictor$3 var6 = var2.lastSample;
      if (var6 == null) {
         return var5;
      } else {
         var5[0][0] = var6.posX + var2.tYho * var3;
         var5[0][1] = var6.MZhIl;
         var5[0][2] = var6.posZ + var2.Jewm * var3;
         double var7 = Math.min(4.0, var3);
         double var9 = var3 - var7;
         double var11 = var2.tYho + var2.DCbVxw * var7;
         double var13 = var2.Jewm + var2.accelerationZ * var7;
         double var15 = Math.sqrt(var11 * var11 + var13 * var13);
         if (var15 > 0.45) {
            var11 *= 0.45 / var15;
            var13 *= 0.45 / var15;
         }

         var5[1][0] = var6.posX + var2.tYho * var7 + 0.5 * var2.DCbVxw * var7 * var7 + var11 * var9;
         var5[1][1] = var6.MZhIl;
         var5[1][2] = var6.posZ + var2.Jewm * var7 + 0.5 * var2.accelerationZ * var7 * var7 + var13 * var9;
         double var17 = Math.sqrt(var2.tYho * var2.tYho + var2.Jewm * var2.Jewm);
         if (Math.abs(var2.turnRate) > 1.0E-4 && var17 > 1.0E-4) {
            double var19 = Math.atan2(var2.Jewm, var2.tYho);
            double var21 = var17 / var2.turnRate;
            var5[2][0] = var6.posX + var21 * (Math.sin(var19 + var2.turnRate * var3) - Math.sin(var19));
            var5[2][2] = var6.posZ - var21 * (Math.cos(var19 + var2.turnRate * var3) - Math.cos(var19));
         } else {
            var5[2][0] = var5[0][0];
            var5[2][2] = var5[0][2];
         }

         var5[2][1] = var5[0][1];
         this.limitLateralOffset(var5[1], var5[0], var3);
         this.limitLateralOffset(var5[2], var5[0], var3);
         double var35 = var6.posX;
         double var36 = var6.MZhIl;
         double var23 = var6.posZ;
         double var25 = var2.tYho;
         double var27 = var2.aimlP;
         double var29 = var2.Jewm;
         int var31 = (int)Math.floor(var3);

         for (int var32 = 0; var32 < var31; var32++) {
            var35 += var25;
            var23 += var29;
            var25 *= var2.speedRetention;
            var29 *= var2.speedRetention;
            if (var2.falling) {
               var36 += var27;
               var27 = (var27 - 0.08) * 0.98;
            }
         }

         double var37 = var3 - var31;
         var5[3][0] = var35 + var25 * var37;
         var5[3][1] = var2.falling ? var36 + var27 * var37 : var6.MZhIl;
         var5[3][2] = var23 + var29 * var37;
         var5[4][0] = var6.posX;
         var5[4][1] = var6.MZhIl;
         var5[4][2] = var6.posZ;

         for (int var34 = 0; var34 < var5.length; var34++) {
            this.limitDistanceFromLastSample(var5[var34], var6, var3);
         }

         return var5;
      }
   }

   private void limitDistanceFromLastSample(double[] var1, MotionPredictor$3 var2, double var3) {
      double var5 = var1[0] - var2.posX;
      double var7 = var1[2] - var2.posZ;
      double var9 = Math.sqrt(var5 * var5 + var7 * var7);
      double var11 = 0.65 * Math.max(0.0, var3);
      if (var9 > var11 && var9 > 1.0E-6) {
         var1[0] = var2.posX + var5 * var11 / var9;
         var1[2] = var2.posZ + var7 * var11 / var9;
      }
   }

   private void limitLateralOffset(double[] var1, double[] var2, double var3) {
      double var5 = var1[0] - var2[0];
      double var7 = var1[2] - var2[2];
      double var9 = Math.sqrt(var5 * var5 + var7 * var7);
      double var11 = 0.6 + Math.min(1.2, var3 * 0.05);
      if (var9 > var11) {
         var1[0] = var2[0] + var5 * var11 / var9;
         var1[2] = var2[2] + var7 * var11 / var9;
      }
   }

   private int selectPredictionIndex(MotionPredictor$4 var1, MotionPredictor$2 var2) {
      if (var2.cEe) {
         return 4;
      } else if (var2.straightLineSprint) {
         return 3;
      } else if (var2.falling) {
         return 3;
      } else if (!var1.usingItem && !var2.speedDropped) {
         int var3 = 0;
         double var4 = Double.MAX_VALUE;

         for (int var6 = 0; var6 < var1.YQOZK.length - 1; var6++) {
            double var7 = var1.YQOZK[var6];
            if (var6 == 1 && Math.sqrt(var2.DCbVxw * var2.DCbVxw + var2.accelerationZ * var2.accelerationZ) > 0.025) {
               var7 *= 0.82;
            }

            if (var6 == 2 && Math.abs(var2.turnRate) > 0.035) {
               var7 *= 0.72;
            }

            if (var6 == 4) {
               var7 *= 1.8;
            }

            if (var7 < var4) {
               var4 = var7;
               var3 = var6;
            }
         }

         return var3;
      } else {
         return 0;
      }
   }

   private void resetTrackedMotion(MotionPredictor$4 var1) {
      this.resetCandidateWeights(var1);
      this.clearVelocityPacketState(var1);
   }

   private void resetCandidateWeights(MotionPredictor$4 var1) {
      for (int var2 = 0; var2 < var1.YQOZK.length; var2++) {
         var1.YQOZK[var2] = var2 == 4 ? 0.12 : 0.35;
      }

      var1.kCp99 = false;
   }

   private void clearVelocityPacketState(MotionPredictor$4 var1) {
      var1.hasMovementHint = false;
      var1.FRWq = Double.NaN;
      var1.hintTickCount = 3;
   }

   private boolean hasRecentVelocityPacket(MotionPredictor$4 var1) {
      return var1.hasMovementHint && var1.hintTickCount < 3 && (Double.isNaN(this.currentTime) || Double.isNaN(var1.FRWq) || this.currentTime - var1.FRWq <= 3.5);
   }

   private double smoothVerticalVelocity(double var1, double var3) {
      int var5 = Math.max(1, Math.min(10, (int)Math.round(var3)));
      double var6 = 0.0;
      double var8 = 0.0;
      double var10 = 1.0;
      double var12 = 0.0;

      for (int var14 = 0; var14 < var5; var14++) {
         var6 += var10;
         var8 += var12;
         var10 *= 0.98;
         var12 = (var12 - 0.08) * 0.98;
      }

      double var19 = (var1 - var8) / var6;
      double var16 = var19;

      for (int var18 = 0; var18 < var5; var18++) {
         var16 = (var16 - 0.08) * 0.98;
      }

      return var16;
   }

   private static double DyAi(double var0) {
      return Math.max(1.0, Math.min(10.0, Math.rint(Math.max(0.0, var0))));
   }

   private static double square(double var0) {
      return var0 * var0;
   }

   private static double clampValue(double var0, double var2, double var4) {
      return Math.max(var2, Math.min(var4, var0));
   }

   private static double wrapAngleToPi(double var0) {
      while (var0 <= -Math.PI) {
         var0 += Math.PI * 2;
      }

      while (var0 > Math.PI) {
         var0 -= Math.PI * 2;
      }

      return var0;
   }
}
