package jade.deps.loader107;

public final class AdditiveMixConstantCipherNine {
   private AdditiveMixConstantCipherNine() {
   }

   public static int decodeInt(int var0, int var1) {
      return var0 ^ computeMix(var1);
   }

   public static long decodeLong(long var0, int var2) {
      return var0 ^ (computeMix(var2) & -1 | (computeMix(var2 ^ 1597334677) & -1) << 32);
   }

   private static int computeMix(int var0) {
      int var1 = var0 ^ -186368436  ^ 307503087;
      var1 += -1640531527;
      var1 ^= var1 >>> 15;
      var1 *= -2048144789;
      return var1 ^ var1 >>> 13;
   }
}
