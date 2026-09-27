package jade.deps.loader107;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class ForwardImulStringCipher {
   private static final byte[] KEYSTREAM_TABLE = new byte[]{
      -23, 104, 30, -19, 121, 27, 53, -15, -97, 26, -48, -8, 27, 35, 34, 22, -100, 82, 107, 126, -75, -25, 12, -42, -92, 17, -117, 96, 126, -24, -96, 68
   };

   private ForwardImulStringCipher() {
   }

   public static String decodeString(String var0, int var1) {
      byte[] var2 = Base64.getDecoder().decode(var0);
      int var3 = var2.length;
      byte[] var4 = new byte[var3];

      for (int var5 = 0; var5 < var3; var5++) {
         int var6 = var2[var5] & 255 ^ computeKeystreamByte(var5, var1);
         var4[var5] = (byte)var6;
      }

      return new String(var4, StandardCharsets.UTF_8);
   }

   public static CallSite bootstrapStringConstant(Lookup var0, String var1, MethodType var2, String var3, int var4) {
      return new ConstantCallSite(MethodHandles.constant(String.class, decodeString(var3, var4)));
   }

   private static int computeKeystreamByte(int var0, int var1) {
      int var2 = var1 ^ var0 * 73244475;
      var2 ^= var2 >>> 16;
      var2 *= 668265261;
      var2 ^= var2 >>> 15;
      return (KEYSTREAM_TABLE[var0 + (var1 & 31) & 31] ^ var2) & 0xFF;
   }
}
