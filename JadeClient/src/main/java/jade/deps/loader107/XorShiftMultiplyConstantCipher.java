package jade.deps.loader107;

public final class XorShiftMultiplyConstantCipher {
   private XorShiftMultiplyConstantCipher() {
   }

   public static int decodeInt(int var0, int var1) {
      return var0 ^ computeMix(var1);
   }

   public static long decodeLong(long var0, int var2) {
      return var0 ^ (computeMix(var2) & -1 | (computeMix(var2 ^ 1597334677) & -1) << 32);
   }

   private static int computeMix(int var0) {
      int var1 = var0 ^ 1408057134  ^ 243881605;
      var1 ^= var1 << 13;
      var1 ^= var1 >>> 17;
      var1 ^= var1 << 5;
      return var1 * 73244475;
   }
}
