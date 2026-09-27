// Jade recovery: original class: jade.deps.eLz.yEgUWWiZ
package jade.client.common;

import java.util.function.DoubleSupplier;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class AngleUtils {
   private static final double RADIANS_TO_DEGREES_DOUBLE = 180.0 / Math.PI;
   private static final double RADIANS_TO_DEGREES_FLOAT = (float) (180.0 / Math.PI);
   private static final float MAX_ROTATION_STEPS = 30.0F;
   private static final float xSgk = 180.0F;

   private AngleUtils() {
   }

   public static float clampPitch(float var0) {
      return MathHelper.clamp_float(var0, -90.0F, 90.0F);
   }

   public static float[] computeRotationsPrecise(double var0, double var2, double var4, float var6, float var7, float var8) {
      return HItqhqB(var0, var2, var4, var6, var7, var8, 180.0 / Math.PI);
   }

   public static float[] computeRotations(double var0, double var2, double var4, float var6, float var7, float var8) {
      return HItqhqB(var0, var2, var4, var6, var7, var8, (float) (180.0 / Math.PI));
   }

   private static float[] HItqhqB(double var0, double var2, double var4, float var6, float var7, float var8, double var9) {
      float var11 = (float)(Math.atan2(var4, var0) * var9) - 90.0F;
      float var12 = var6 + MathHelper.wrapAngleTo180_float(var11 - var6);
      double var13 = var0 * var0 + var4 * var4;
      float var15 = (float)(-Math.atan2(var2, Math.sqrt(var13)) * var9);
      float var16 = var7 + MathHelper.wrapAngleTo180_float(var15 - var7) + var8;
      return new float[]{var12, clampPitch(var16)};
   }

   public static float[] computeRotationsSafe(double var0, double var2, double var4, float var6, float var7, float var8) {
      if (var0 * var0 + var4 * var4 >= 1.0E-12) {
         return computeRotations(var0, var2, var4, var6, var7, var8);
      } else {
         float var9 = (float)(-Math.atan2(var2, 0.0) * (float) (180.0 / Math.PI));
         float var10 = var7 + MathHelper.wrapAngleTo180_float(var9 - var7) + var8;
         return new float[]{var6, clampPitch(var10)};
      }
   }

   public static float[] getRotationsToVector(Vec3 var0, double var1, double var3, double var5) {
      double var7 = var1 - var0.xCoord;
      double var9 = var3 - var0.yCoord;
      double var11 = var5 - var0.zCoord;
      return new float[]{
         (float)Math.toDegrees(Math.atan2(var11, var7)) - 90.0F, (float)(-Math.toDegrees(Math.atan2(var9, Math.sqrt(var7 * var7 + var11 * var11))))
      };
   }

   public static Vec3 getLookVector(float var0, float var1) {
      float var2 = -var0 * (float) (Math.PI / 180.0) - (float) Math.PI;
      float var3 = -var1 * (float) (Math.PI / 180.0);
      float var4 = -MathHelper.cos(var3);
      return new Vec3(MathHelper.sin(var2) * var4, MathHelper.sin(var3), MathHelper.cos(var2) * var4);
   }

   public static float[] interpolateRotation(float var0, float var1, float var2, float var3, int var4, float var5, DoubleSupplier var6) {
      if (var4 <= 0) {
         return new float[]{var0, clampPitch(var1)};
      } else if (var4 >= 30) {
         return new float[]{var2, clampPitch(var3)};
      } else {
         float var7 = MathHelper.wrapAngleTo180_float(var2 - var0);
         float var8 = var3 - var1;
         float var9 = (float)Math.sqrt(var7 * var7 + var8 * var8);
         if (var9 < 0.001F) {
            return new float[]{var2, clampPitch(var3)};
         } else {
            float var10 = var4 / 30.0F;
            float var11 = var10 * var10 * 180.0F;
            float var12 = 0.6F * (float)(var5 / 100.0);
            if (var12 > 0.001F) {
               var11 *= 1.0F - var12 / 2.0F + (float)(var6.getAsDouble() * var12);
            }

            float var13 = Math.min(1.0F, var9 / 180.0F);
            float var14 = (float)Math.pow(var13, 0.7);
            float var15 = (float)(var5 / 100.0);
            float var16 = Math.max(0.8F, 1.0F - var15 * (1.0F - var14));
            float var17 = Math.min(var11 * var16, var9) / var9;
            return new float[]{var0 + var7 * var17, clampPitch(var1 + var8 * var17)};
         }
      }
   }

   public static float quantizeAngle(float var0, float var1, float var2) {
      float var3 = var2 * 0.6F + 0.2F;
      double var4 = var3 * var3 * var3 * 1.2;
      return var1 + (float)(Math.round((var0 - var1) / var4) * var4);
   }

   public static float alignAngleNear(float var0, float var1) {
      float var2 = var0 + 360.0F * (float)Math.floor(var1 / 360.0F);
      if (var2 < var1 - 180.0F) {
         var2 += 360.0F;
      } else if (var2 > var1 + 180.0F) {
         var2 -= 360.0F;
      }

      return var2;
   }

   public static float getYawToPoint(double var0, double var2, double var4, double var6) {
      return (float)(-Math.atan2(var4 - var0, var6 - var2) * (float) (180.0 / Math.PI));
   }

   public static float Ivsvmx(double var0, double var2) {
      return (float)(-Math.atan2(var0, var2) * (float) (180.0 / Math.PI));
   }
}
