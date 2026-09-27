// Jade recovery: original class: jade.deps.eLz.OCLJCo$4
package jade.client.module.combat.autoaim;

import java.util.ArrayDeque;
import java.util.Deque;

public final class MotionPredictor$4 {
   public final Deque<MotionPredictor$3> samples = new ArrayDeque<>();
   public final double[] YQOZK;
   public double hintVelocityX;
   public double hintVelocityY;
   public double AJVB;
   public double FRWq;
   public boolean hasMovementHint;
   public int hintTickCount;
   public float vez;
   public boolean usingItem;
   public boolean sprinting;
   public boolean ZcwZ5;
   public boolean placingBlockDown;
   public boolean hasInputState;
   public int kev;
   public boolean LPNLp;
   public boolean kCp99;
   public MotionPredictor$1 cachedCollisionPredicate;
   public double AxmlyD;
   public int ZBiq3;
   public double SuoBk;
   public double cu95;
   public double LUSOr;
   public double nqY;
   public double lastSafeZ;
   public double verticalVelocity;
   public boolean MuE;
   public boolean xBlocked;
   public boolean wrhla;

   MotionPredictor$4() {
      double[] var10001 = new double[]{0.35, 0.0, 0.0, 0.0, 0.0};
      var10001[1] = 0.35;
      var10001[2] = 0.35;
      var10001[3] = 0.35;
      var10001[4] = 0.12;
      this.YQOZK = var10001;
      this.FRWq = Double.NaN;
      this.kev = -1;
      this.AxmlyD = Double.NaN;
      this.ZBiq3 = -1;
      this.cu95 = Double.MAX_VALUE;
   }
}
