package jade.deps.loader107;

public final class MurmurConstantDecoder {
   private MurmurConstantDecoder() {
   }

   public static int xorIntConstant(int var0, int var1) {
      return var0 ^ deriveKey(var1);
   }

   public static long xorLongConstant(long var0, int var2) {
      return var0 ^ (deriveKey(var2) & -1 | (deriveKey(var2 ^ 1597334677) & -1) << 32);
   }

   private static int deriveKey(int var0) {
      int var1 = var0 ^ -1966068553  ^ 282642461;
      var1 ^= var1 >>> 16;
      var1 *= 2146121005;
      var1 ^= var1 >>> 15;
      var1 *= -2073254261;
      return var1 ^ var1 >>> 16;
   }
}
