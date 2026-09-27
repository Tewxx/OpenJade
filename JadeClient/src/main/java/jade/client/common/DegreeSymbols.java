// Jade recovery: original class: jade.deps.eLz.biN01XjR9C
package jade.client.common;

import jade.client.module.client.Gui;
import jade.client.module.render.Arraylist;

public final class DegreeSymbols {
   private DegreeSymbols() {
   }

   public static String getDegreeSymbol() {
      return getDegreeSymbolForFont(Arraylist.getFontName());
   }

   public static String applyDegreeSymbol(String var0) {
      return replaceDegreePlaceholder(var0, Gui.ISjhxoi());
   }

   public static String replaceDegreePlaceholder(String var0, String var1) {
      if (var0 == null) {
         return "";
      } else {
         return !"deg".equals(var0.trim()) ? var0 : var0.replace("deg", getDegreeSymbolForFont(var1));
      }
   }

   public static String getDegreeSymbolForFont(String var0) {
      return FontManager.isMinecraftFont(var0) ? "deg" : "°";
   }
}
