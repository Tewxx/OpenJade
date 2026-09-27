package jade.deps.loader107;

public final class XorShiftMultiplyConstantCipherSix {
   private XorShiftMultiplyConstantCipherSix() {
   }

   public static int decodeInt(int var0, int var1) {
      return var0 ^ computeMix(var1);
   }

   public static long decodeLong(long var0, int var2) {
      return var0 ^ (computeMix(var2) & -1 | (computeMix(var2 ^ 1597334677) & -1) << 32);
   }

   private static int computeMix(int var0) {
      int var1 = var0 ^ -1482456249  ^ 838771231;
      var1 ^= var1 << 13;
      var1 ^= var1 >>> 17;
      var1 ^= var1 << 5;
      return var1 * 73244475;
   }
}
