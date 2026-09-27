package jade.deps.loader107;

public final class XorShiftConstantDecoder {
   private XorShiftConstantDecoder() {
   }

   public static int xorIntConstant(int var0, int var1) {
      return var0 ^ deriveKey(var1);
   }

   public static long xorLongConstant(long var0, int var2) {
      return var0 ^ (deriveKey(var2) & -1 | (deriveKey(var2 ^ 1597334677) & -1) << 32);
   }

   private static int deriveKey(int var0) {
      int var1 = var0 ^ -1160241937  ^ -799408509;
      var1 ^= var1 << 13;
      var1 ^= var1 >>> 17;
      var1 ^= var1 << 5;
      return var1 * 73244475;
   }
}
