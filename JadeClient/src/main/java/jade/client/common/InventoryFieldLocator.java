// Jade recovery: original class: jade.deps.eLz.bY0Jwn
package jade.client.common;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.inventory.IInventory;

public final class InventoryFieldLocator {
   private InventoryFieldLocator() {
   }

   public static void findInventoryField(Class<?> var0, Map<Class, Field> var1) {
      for (Field var5 : var0.getDeclaredFields()) {
         if (var5.getType() == IInventory.class) {
            var5.setAccessible(true);
            var1.put(var0, var5);
         }
      }
   }
}
