// Jade recovery: original class: jade.deps.eLz.WMwTRJ0
package jade.client.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.renderer.texture.TextureUtil;

public final class ImageDownloader {
   private ImageDownloader() {
   }

   private static HttpURLConnection openImageConnection(URL var0, String var1) throws IOException {
      HttpURLConnection var2 = (HttpURLConnection)var0.openConnection();
      var2.setRequestMethod("GET");
      var2.setUseCaches(true);
      var2.setInstanceFollowRedirects(true);
      var2.setRequestProperty("User-Agent", var1);
      var2.setRequestProperty("Accept", "text/html, image/*");
      if (var0.getHost().contains("imgur")) {
         var2.setRequestProperty("Referer", "https://imgur.com/");
      }

      return var2;
   }

   public static BufferedImage downloadImage(String var0, String var1) {
      try {
         URL var2 = new URL(var0);

         while (true) {
            HttpURLConnection var3 = openImageConnection(var2, var1);
            int var4 = var3.getResponseCode();
            if (var4 >= 300 && var4 < 400) {
               String var40 = var3.getHeaderField("Location");
               if (var40 == null || var40.length() == 0) {
                  return null;
               }

               var2 = new URL(var2, var40);
               var3.disconnect();
            } else {
               String var5 = var3.getContentType();
               if (var5 != null && var5.startsWith("image")) {
                  BufferedImage var44;
                  try (InputStream var41 = var3.getInputStream()) {
                     BufferedImage var43 = TextureUtil.readBufferedImage(var41);
                     var3.disconnect();
                     var44 = var43;
                  }

                  return var44;
               }

               Object var9;
               try (InputStream var6 = var3.getInputStream()) {
                  String var8 = HtmlImageUrlExtractor.extractImageUrl(AhmO(var6));
                  if (var8 != null && var8.length() != 0) {
                     var2 = new URL(var2, var8);
                     var3.disconnect();
                     continue;
                  }

                  var9 = null;
               }

               return (BufferedImage)var9;
            }
         }
      } catch (Exception var39) {
         var39.printStackTrace();
         return null;
      }
   }

   private static String AhmO(InputStream var0) throws IOException {
      InputStreamReader var1 = new InputStreamReader(var0, StandardCharsets.UTF_8);
      StringBuilder var2 = new StringBuilder();
      char[] var3 = new char[4096];

      int var4;
      while ((var4 = var1.read(var3)) != -1) {
         var2.append(var3, 0, var4);
      }

      return var2.toString();
   }
}
