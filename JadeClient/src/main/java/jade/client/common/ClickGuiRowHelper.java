// Jade recovery: original class: jade.deps.eLz.mMwWoApr
package jade.client.common;

import java.util.function.BooleanSupplier;

public final class ClickGuiRowHelper {
   private ClickGuiRowHelper() {
   }

   public static boolean isMouseOverModuleRow(int var0, int var1, float var2, float var3, float var4, float var5) {
      return var2 < var0 && var0 < var2 + var3 && var4 + var5 < var1 && var1 < var4 + 16.0F + var5;
   }

   public static boolean ETk4(boolean var0, int var1, BooleanSupplier var2, Runnable var3, Runnable var4) {
      if (!var0) {
         return false;
      } else {
         switch (var1) {
            case 0:
               if (!var2.getAsBoolean()) {
                  return false;
               }

               var3.run();
               return true;
            case 1:
               var4.run();
               return true;
            default:
               return false;
         }
      }
   }

   public static int getModuleNameColor(boolean var0, boolean var1) {
      if (var1) {
         return -9257734;
      } else {
         return var0 ? -15164673 : -4144960;
      }
   }
}
