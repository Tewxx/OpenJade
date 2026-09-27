// Jade recovery: recovered class name: StringCodec
package jade.deps.loader107;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

public final class StringCodec {
   private static final byte[] K = new byte[]{
      -23, 104, 30, -19, 121, 27, 53, -15, -97, 26, -48, -8, 27, 35, 34, 22, -100, 82, 107, 126, -75, -25, 12, -42, -92, 17, -117, 96, 126, -24, -96, 68
   };
   private static final ConcurrentHashMap<String, String> CACHE = new ConcurrentHashMap<>();

   private StringCodec() {
   }

   public static String a(String b64, int salt) {
      return decode(b64, salt, 0);
   }

   public static String b(String b64, int salt) {
      return decode(b64, salt, 1);
   }

   public static String c(String b64, int salt) {
      return decode(b64, salt, 2);
   }

   private static String decode(String b64, int salt, int mode) {
      String key = mode + ":" + salt + ":" + b64;
      String hit = CACHE.get(key);
      if (hit != null) {
         return hit;
      } else {
         byte[] enc = Base64.getDecoder().decode(b64);
         byte[] out = new byte[enc.length];
         int n = enc.length;

         for (int i = 0; i < n; i++) {
            int src = mode == 2 ? n - 1 - i : i;
            int y = enc[src] & 255;
            if (mode == 1) {
               y = y - (salt >>> (i & 7) & 31) & 0xFF;
            }

            out[i] = (byte)(y ^ kb(i, salt, mode));
         }

         String s = new String(out, StandardCharsets.UTF_8);
         CACHE.put(key, s);
         return s;
      }
   }

   private static int kb(int i, int salt, int mode) {
      int x = salt + -1640531527 + mode * 2135587861 + i;
      x ^= x >>> 15;
      x *= -2048144789;
      x ^= x >>> 13;
      return (K[i * 5 + salt + mode & 31] ^ x) & 0xFF;
   }
}
