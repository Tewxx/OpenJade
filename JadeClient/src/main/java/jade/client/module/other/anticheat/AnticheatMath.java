// Jade recovery: original class: jade.deps.eLz.eBPsmHo2
package jade.client.module.other.anticheat;

public final class AnticheatMath {
   private static final double ZRIpD = 0.07;
   private static final double Vp5 = 0.1;
   private static final float MIN_SCAFFOLD_PITCH = 70.0F;

   private AnticheatMath() {
   }

   public static double horizontalSpeed(double var0, double var2) {
      return Math.max(Math.abs(var0), Math.abs(var2));
   }

   public static boolean BfnuQ(double var0) {
      return var0 >= 0.07;
   }

   public static boolean hasVerticalMovement(double var0) {
      return Math.abs(var0) >= 0.1;
   }

   public static int QgxOo(int var0) {
      return var0 + 1;
   }

   public static int nextStreakCount(int var0, boolean var1) {
      return var1 ? QgxOo(var0) : 0;
   }

   public static int pFoo(int var0, float var1, boolean var2, int var3, boolean var4, boolean var5) {
      if (var1 < 70.0F || !var2) {
         return 0;
      } else if (var3 != 1) {
         return var0;
      } else {
         return !var4 && var5 ? QgxOo(var0) : 0;
      }
   }

   public static double AERFxL(int var0) {
      return var0 / 32;
   }
}
