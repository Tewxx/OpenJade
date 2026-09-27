// Jade recovery: original class: jade.deps.eLz.h8r705KPe$8
package jade.client.common;

import java.io.File;

public final class FontManager$8 {
   private final String displayName;
   private final File file;
   private final int style;
   private final String cacheKey;

   FontManager$8(String var1, File var2, int var3) {
      this.displayName = var1;
      this.file = var2;
      this.style = var3;
      this.cacheKey = var2.getAbsolutePath() + ":" + var2.lastModified() + ":" + var2.length();
   }

   static java.lang.String access$600(jade.client.common.FontManager$8 arg0) {
      return arg0.cacheKey;
   }

   static java.io.File access$700(jade.client.common.FontManager$8 arg0) {
      return arg0.file;
   }

   static int access$900(jade.client.common.FontManager$8 arg0) {
      return arg0.style;
   }
}
