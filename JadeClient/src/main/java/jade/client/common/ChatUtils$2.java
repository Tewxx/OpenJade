// Jade recovery: original class: jade.deps.eLz.XzkQGEq$2
package jade.client.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ChatUtils$2 {
   private final String text;
   private final List<Integer> gradientColors;

   ChatUtils$2(String var1, List<Integer> var2) {
      this.text = var1;
      this.gradientColors = var2;
   }

   public static ChatUtils$2 createPlain(String var0) {
      return new ChatUtils$2(var0, null);
   }

   public static ChatUtils$2 createGradient(String var0, List<Integer> var1) {
      return new ChatUtils$2(var0, Collections.unmodifiableList(new ArrayList<>(var1)));
   }

   public boolean CREFU() {
      return this.gradientColors != null && this.gradientColors.size() >= 2;
   }

   public String getText() {
      return this.text;
   }

   public List<Integer> getGradientColors() {
      return this.gradientColors;
   }
}
