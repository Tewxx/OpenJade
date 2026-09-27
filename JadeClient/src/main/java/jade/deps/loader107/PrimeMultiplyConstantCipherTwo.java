package jade.deps.loader107;

public final class PrimeMultiplyConstantCipherTwo {
   private PrimeMultiplyConstantCipherTwo() {
   }

   public static int decodeInt(int var0, int var1) {
      return var0 ^ LyluLnqW(var1);
   }

   public static long decodeLong(long var0, int var2) {
      return var0 ^ (LyluLnqW(var2) & -1 | (LyluLnqW(var2 ^ 1597334677) & -1) << 32);
   }

   private static int LyluLnqW(int var0) {
      int var1 = var0 ^ -1784682553  ^ -335941723;
      var1 *= 668265261;
      var1 ^= var1 >>> 14;
      var1 *= 374761393;
      return var1 ^ var1 >>> 16;
   }
}
