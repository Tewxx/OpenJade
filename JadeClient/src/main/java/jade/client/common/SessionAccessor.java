// Jade recovery: original class: jade.deps.eLz.a6KmOWC
package jade.client.common;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class SessionAccessor {
   private static Field field;

   private static Field findSessionField() {
      if (field != null) {
         return field;
      } else {
         for (Field var3 : Minecraft.class.getDeclaredFields()) {
            if (var3.getType() == Session.class) {
               var3.setAccessible(true);
               field = var3;
               return var3;
            }
         }

         return null;
      }
   }

   public static Session getSession() {
      return Minecraft.getMinecraft().getSession();
   }

   public static void setSession(Session var0) {
      try {
         Field var1 = findSessionField();
         if (var1 != null) {
            var1.set(Minecraft.getMinecraft(), var0);
         }
      } catch (Exception var2) {
      }
   }
}
