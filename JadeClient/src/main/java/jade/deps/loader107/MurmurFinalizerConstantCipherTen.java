package jade.deps.loader107;

public final class MurmurFinalizerConstantCipherTen {
   private MurmurFinalizerConstantCipherTen() {
   }

   public static int decodeInt(int var0, int var1) {
      return var0 ^ computeMix(var1);
   }

   public static long decodeLong(long var0, int var2) {
      return var0 ^ (computeMix(var2) & -1 | (computeMix(var2 ^ 1597334677) & -1) << 32);
   }

   private static int computeMix(int var0) {
      int var1 = var0 ^ -662361617  ^ -1880369195;
      var1 ^= var1 >>> 16;
      var1 *= 2146121005;
      var1 ^= var1 >>> 15;
      var1 *= -2073254261;
      return var1 ^ var1 >>> 16;
   }
}
