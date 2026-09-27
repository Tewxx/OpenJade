// Jade recovery: original class: jade.deps.eLz.LGHsOECU7
package jade.client.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LGHsOECU7 {
   private final Map<Object, GuiRect> componentBounds = new HashMap<>();
   private final List<GuiRect> boundsStack = new ArrayList<>();

   public void HUBGZjr() {
      this.componentBounds.clear();
      this.boundsStack.clear();
   }

   public void MeQu(GuiRect var1, boolean var2) {
      if (!var2) {
         var1 = new GuiRect(var1.x, var1.ufe, 0, 0);
      }

      if (!this.boundsStack.isEmpty()) {
         var1 = intersectBounds(var1, this.boundsStack.get(this.boundsStack.size() - 1));
      }

      this.boundsStack.add(var1);
   }

   public void s172() {
      this.boundsStack.remove(this.boundsStack.size() - 1);
   }

   public void registerComponentBounds(Object var1, GuiRect var2) {
      if (!this.boundsStack.isEmpty()) {
         GuiRect var3 = intersectBounds(var2, this.boundsStack.get(this.boundsStack.size() - 1));
         if (var3.busF > 0 && var3.HfS > 0) {
            this.componentBounds.put(var1, var2);
         }
      }
   }

   public GuiRect hOzmx(Object var1) {
      return this.componentBounds.get(var1);
   }

   private static GuiRect intersectBounds(GuiRect var0, GuiRect var1) {
      int var2 = Math.max(var0.x, var1.x);
      int var3 = Math.max(var0.ufe, var1.ufe);
      return new GuiRect(
         var2,
         var3,
         Math.max(0, Math.min(var0.x + var0.busF, var1.x + var1.busF) - var2),
         Math.max(0, Math.min(var0.ufe + var0.HfS, var1.ufe + var1.HfS) - var3)
      );
   }
}
