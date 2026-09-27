// Jade recovery: recovered class name: StringDecoder
package jade.deps.loader107;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

public final class StringDecoder {
   private static final byte[] K = new byte[]{
      -23, 104, 30, -19, 121, 27, 53, -15, -97, 26, -48, -8, 27, 35, 34, 22, -100, 82, 107, 126, -75, -25, 12, -42, -92, 17, -117, 96, 126, -24, -96, 68
   };
   private static final ConcurrentHashMap<String, String> CACHE = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<String, String> CACHE_E = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<String, String> CACHE_F = new ConcurrentHashMap<>();

   private StringDecoder() {
   }

   public static String d(String b64) {
      String hit = CACHE.get(b64);
      if (hit != null) {
         return hit;
      } else {
         byte[] enc = Base64.getDecoder().decode(b64);
         byte[] out = new byte[enc.length];

         for (int i = 0; i < enc.length; i++) {
            out[i] = (byte)(enc[i] ^ K[i % K.length]);
         }

         String s = new String(out, StandardCharsets.UTF_8);
         CACHE.put(b64, s);
         return s;
      }
   }

   public static String d(String b64, int salt) {
      String key = b64 + ':' + salt;
      String hit = CACHE.get(key);
      if (hit != null) {
         return hit;
      } else {
         byte[] enc = Base64.getDecoder().decode(b64);
         byte[] out = new byte[enc.length];

         for (int i = 0; i < enc.length; i++) {
            out[i] = (byte)(enc[i] ^ kb(i, salt));
         }

         String s = new String(out, StandardCharsets.UTF_8);
         CACHE.put(key, s);
         return s;
      }
   }

   public static String e(String b64) {
      String hit = CACHE_E.get(b64);
      if (hit != null) {
         return hit;
      } else {
         byte[] enc = Base64.getDecoder().decode(b64);
         byte[] out = new byte[enc.length];

         for (int i = 0; i < enc.length; i++) {
            int y = (enc[i] ^ K[i % K.length]) & 0xFF;
            out[i] = (byte)(y - (i & 15) & 0xFF);
         }

         String s = new String(out, StandardCharsets.UTF_8);
         CACHE_E.put(b64, s);
         return s;
      }
   }

   public static String e(String b64, int salt) {
      String key = b64 + ':' + salt;
      String hit = CACHE_E.get(key);
      if (hit != null) {
         return hit;
      } else {
         byte[] enc = Base64.getDecoder().decode(b64);
         byte[] out = new byte[enc.length];

         for (int i = 0; i < enc.length; i++) {
            int y = (enc[i] ^ kb(i, salt)) & 0xFF;
            out[i] = (byte)(y - (i + salt & 15) & 0xFF);
         }

         String s = new String(out, StandardCharsets.UTF_8);
         CACHE_E.put(key, s);
         return s;
      }
   }

   public static String f(String b64) {
      String hit = CACHE_F.get(b64);
      if (hit != null) {
         return hit;
      } else {
         byte[] enc = Base64.getDecoder().decode(b64);
         int n = enc.length;
         byte[] out = new byte[n];

         for (int i = 0; i < n; i++) {
            int rev = enc[n - 1 - i] & 255;
            out[i] = (byte)(rev ^ K[i % K.length]);
         }

         String s = new String(out, StandardCharsets.UTF_8);
         CACHE_F.put(b64, s);
         return s;
      }
   }

   public static String f(String b64, int salt) {
      String key = b64 + ':' + salt;
      String hit = CACHE_F.get(key);
      if (hit != null) {
         return hit;
      } else {
         byte[] enc = Base64.getDecoder().decode(b64);
         int n = enc.length;
         byte[] out = new byte[n];

         for (int i = 0; i < n; i++) {
            int rev = enc[n - 1 - i] & 255;
            out[i] = (byte)(rev ^ kb(i, salt));
         }

         String s = new String(out, StandardCharsets.UTF_8);
         CACHE_F.put(key, s);
         return s;
      }
   }

   private static int kb(int i, int salt) {
      int x = salt ^ i * 73244475;
      x ^= x >>> 16;
      x *= 668265261;
      x ^= x >>> 15;
      return (K[i + (salt & 31) & 31] ^ x) & 0xFF;
   }
}
