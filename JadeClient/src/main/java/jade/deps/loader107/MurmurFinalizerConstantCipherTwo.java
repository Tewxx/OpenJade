package jade.deps.loader107;

public final class MurmurFinalizerConstantCipherTwo {
   private MurmurFinalizerConstantCipherTwo() {
   }

   public static int XCWDi(int var0, int var1) {
      return var0 ^ GHXe0(var1);
   }

   public static long decodeLong(long var0, int var2) {
      return var0 ^ (GHXe0(var2) & -1 | (GHXe0(var2 ^ 1597334677) & -1) << 32);
   }

   private static int GHXe0(int var0) {
      int var1 = var0 ^ -554124038  ^ -1287078111;
      var1 ^= var1 >>> 16;
      var1 *= 2146121005;
      var1 ^= var1 >>> 15;
      var1 *= -2073254261;
      return var1 ^ var1 >>> 16;
   }
}
