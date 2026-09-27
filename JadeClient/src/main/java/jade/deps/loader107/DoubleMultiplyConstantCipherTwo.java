package jade.deps.loader107;

public final class DoubleMultiplyConstantCipherTwo {
   private DoubleMultiplyConstantCipherTwo() {
   }

   public static int decodeInt(int var0, int var1) {
      return var0 ^ computeMix(var1);
   }

   public static long decodeLong(long var0, int var2) {
      return var0 ^ (computeMix(var2) & -1 | (computeMix(var2 ^ 1597334677) & -1) << 32);
   }

   private static int computeMix(int var0) {
      int var1 = var0 ^ 535327226  ^ 1929129293;
      var1 *= 668265261;
      var1 ^= var1 >>> 14;
      var1 *= 374761393;
      return var1 ^ var1 >>> 16;
   }
}
