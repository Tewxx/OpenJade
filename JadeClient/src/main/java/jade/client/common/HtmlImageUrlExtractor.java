// Jade recovery: original class: jade.deps.eLz.GZZV0aZXK
package jade.client.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HtmlImageUrlExtractor {
   private static final Pattern[] IMAGE_URL_PATTERNS = new Pattern[]{
      Pattern.compile("<meta property=\"(?:og:image|twitter:image)\" content=\"(.+?)\".*?/?>"), Pattern.compile("<img.*?src=\"(.+?)\".*?>")
   };

   private HtmlImageUrlExtractor() {
   }

   public static String extractImageUrl(String var0) {
      for (Pattern var4 : IMAGE_URL_PATTERNS) {
         Matcher var5 = var4.matcher(var0);
         if (var5.find()) {
            return var5.group(1);
         }
      }

      return null;
   }
}
