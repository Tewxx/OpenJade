// Jade recovery: original class: jade.deps.eLz.SqO4CW$3
package jade.client.module.render;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.entity.item.EntityItem;

public final class ItemESP$3 {
   private final EntityItem WZZgq;
   private final Map<String, ItemESP$4> entriesByName = new LinkedHashMap<>();

   ItemESP$3(EntityItem var1) {
      this.WZZgq = var1;
   }

   private void Admt9(String var1, String var2, int var3, ItemESP$1 var4, String var5) {
      ItemESP$4 var6 = this.entriesByName.get(var1);
      if (var6 == null) {
         var6 = new ItemESP$4(var2, var4, var5);
         this.entriesByName.put(var1, var6);
      }

      ItemESP$4.setTotalCount(var6, ItemESP$4.getTotalCount(var6) + var3);
   }

   public static EntityItem getDroppedItem(ItemESP$3 var0) {
      return var0.WZZgq;
   }

   public static Map getEntriesByName(ItemESP$3 var0) {
      return var0.entriesByName;
   }

   public static void addEntry(ItemESP$3 var0, String var1, String var2, int var3, ItemESP$1 var4, String var5) {
      var0.Admt9(var1, var2, var3, var4, var5);
   }
}
