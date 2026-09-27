// Jade recovery: original class: jade.deps.eLz.rzJOn4LgxU
package jade.client.module.combat.autoaim;

import java.util.Collections;
import java.util.List;
import net.minecraft.util.Vec3;

public final class AimResult {
   private final AimResult$0 xLca;
   private final float psT;
   private final float PFqVa;
   private final int OBNIo;
   private final double confidence;
   private final TargetPrediction prediction;
   private final List<Vec3> trajectory;
   private final Vec3 vec3;

   public AimResult(AimResult$0 var1, float var2, float var3, int var4, double var5, TargetPrediction var7, List<Vec3> var8, Vec3 var9) {
      this.xLca = var1;
      this.psT = var2;
      this.PFqVa = var3;
      this.OBNIo = var4;
      this.confidence = var5;
      this.prediction = var7;
      this.trajectory = var8 == null ? Collections.emptyList() : Collections.unmodifiableList(var8);
      this.vec3 = var9;
   }

   public AimResult$0 WYqjD() {
      return this.xLca;
   }

   public float getYaw() {
      return this.psT;
   }

   public float getPitch() {
      return this.PFqVa;
   }

   public int getTicks() {
      return this.OBNIo;
   }

   public double IXURWX() {
      return this.confidence;
   }

   public TargetPrediction getPrediction() {
      return this.prediction;
   }

   public List<Vec3> kfYfzo() {
      return this.trajectory;
   }

   public Vec3 kahN() {
      return this.vec3;
   }

   public boolean HufH9() {
      return this.xLca == AimResult$0.HIT;
   }
}
