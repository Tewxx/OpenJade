package jade.deps.loader107;

public final class FmixConstantDecoderThree {
   private FmixConstantDecoderThree() {
   }

   public static int xorIntConstant(int var0, int var1) {
      return var0 ^ deriveKey(var1);
   }

   public static long xorLongConstant(long var0, int var2) {
      return var0 ^ (deriveKey(var2) & -1 | (deriveKey(var2 ^ 1597334677) & -1) << 32);
   }

   private static int deriveKey(int var0) {
      int var1 = var0 ^ -1499852930  ^ 1328106005;
      var1 += -1640531527;
      var1 ^= var1 >>> 15;
      var1 *= -2048144789;
      return var1 ^ var1 >>> 13;
   }
}
