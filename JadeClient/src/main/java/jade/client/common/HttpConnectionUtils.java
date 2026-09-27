// Jade recovery: original class: jade.deps.eLz.nof9AR2q3l
package jade.client.common;

import jade.client.command.ClipboardUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class HttpConnectionUtils {
   private HttpConnectionUtils() {
   }

   public static String fetchText(String var0, boolean var1, boolean var2, int var3, int var4) {
      HttpURLConnection var5 = null;

      String var7;
      try {
         var5 = (HttpURLConnection)new URL(var0).openConnection();
         var5.setConnectTimeout(Math.max(1, var3));
         var5.setReadTimeout(Math.max(1, var4));
         if (var2) {
            var5.setRequestProperty("id", ClipboardUtils.generateRequestId(var0));
         }

         return readResponse(var5, var1);
      } catch (IOException var11) {
         var7 = "";
      } finally {
         if (var5 != null) {
            var5.disconnect();
         }
      }

      return var7;
   }

   public static String readResponse(HttpURLConnection var0, boolean var1) {
      if (var0 == null) {
         return "";
      } else {
         try (BufferedReader var2 = new BufferedReader(new InputStreamReader(var0.getInputStream()))) {
            StringBuilder var4 = new StringBuilder();

            String var5;
            while ((var5 = var2.readLine()) != null) {
               var4.append(var5);
               if (var1) {
                  var4.append('\n');
               }
            }

            String var6 = var4.toString();
            var0.disconnect();
            return var6;
         } catch (Exception var19) {
            return "";
         }
      }
   }
}
