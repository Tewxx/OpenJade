// Jade recovery: original class: jade.deps.eLz.Qe6oHN0
package jade.client.common;

import java.util.ArrayList;
import java.util.List;

public final class SuggestionCycler {
   private List<String> QoMc = new ArrayList<>();
   private int suggestionIndex = -1;

   public String nextSuggestion(String var1, SuggestionCycler$0 var2) {
      if (!this.QoMc.isEmpty() && this.Pivn7(var1)) {
         this.suggestionIndex = (this.suggestionIndex + 1) % this.QoMc.size();
      } else {
         List var3 = var2.getSuggestions(var1);
         this.QoMc = var3 == null ? new ArrayList<>() : new ArrayList<>(var3);
         this.suggestionIndex = 0;
      }

      if (this.QoMc.isEmpty()) {
         this.suggestionIndex = -1;
         return null;
      } else {
         return this.QoMc.get(this.suggestionIndex);
      }
   }

   public void reset() {
      this.QoMc = new ArrayList<>();
      this.suggestionIndex = -1;
   }

   private boolean Pivn7(String var1) {
      for (String var3 : this.QoMc) {
         if (var3.equalsIgnoreCase(var1)) {
            return true;
         }
      }

      return false;
   }
}
