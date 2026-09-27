package jade.deps.loader107;

public final class DoubleMixConstantDecoder {
   private DoubleMixConstantDecoder() {
   }

   public static int xorIntConstant(int var0, int var1) {
      return var0 ^ deriveKey(var1);
   }

   public static long xorLongConstant(long var0, int var2) {
      return var0 ^ (deriveKey(var2) & -1 | (deriveKey(var2 ^ 1597334677) & -1) << 32);
   }

   private static int deriveKey(int var0) {
      int var1 = var0 ^ -1307800715  ^ -239499333;
      var1 *= 668265261;
      var1 ^= var1 >>> 14;
      var1 *= 374761393;
      return var1 ^ var1 >>> 16;
   }
}
