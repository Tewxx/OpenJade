// Jade recovery: recovered class name: RuntimeBits
package jade.deps.loader107;

public final class RuntimeBits {
   private static final int SEED = -134634994  ^ 1245522971;

   private RuntimeBits() {
   }

   public static int i(int encoded, int salt) {
      return encoded ^ mix32(salt);
   }

   public static long l(long encoded, int salt) {
      long lo = mix32(salt) & 4294967295L;
      long hi = mix32(salt ^ 1597334677) & 4294967295L;
      return encoded ^ (lo | hi << 32);
   }

   private static int mix32(int x) {
      x ^= SEED;
      x ^= x >>> 16;
      x *= 2146121005;
      x ^= x >>> 15;
      x *= -2073254261;
      return x ^ x >>> 16;
   }
}
