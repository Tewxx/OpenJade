// Jade recovery: original class: jade.deps.eLz.j3LdoR
package jade.client.common;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;

public class HttpUtils {
   public static String yBgdIx = "";
   public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";

   public static boolean isValidHypixelApiKey(String var0) {
      String var1 = httpRequest("https://api.hypixel.net/key?key=" + var0, false, false);
      return var1.length() != 0 && var1.indexOf("Invalid") < 0;
   }

   public static String httpRequest(String var0, boolean var1, boolean var2) {
      return httpRequestWithTimeouts(var0, var1, var2, 10000, 15000);
   }

   public static String httpRequestWithTimeouts(String var0, boolean var1, boolean var2, int var3, int var4) {
      return HttpConnectionUtils.fetchText(var0, var1, var2, var3, var4);
   }

   public static String readResponse(HttpURLConnection var0, boolean var1) {
      return HttpConnectionUtils.readResponse(var0, var1);
   }

   public static BufferedImage downloadImage(String var0) {
      return ImageDownloader.downloadImage(var0, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
   }
}
