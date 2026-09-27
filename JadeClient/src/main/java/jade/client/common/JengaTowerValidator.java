// Jade recovery: original class: jade.deps.eLz.p7dAAS4
package jade.client.common;

import java.util.List;

public final class JengaTowerValidator {
   private static final Vector3 vector3 = new Vector3(0.0, 1.0, 0.0);

   private JengaTowerValidator() {
   }

   public static boolean isBlockRemovable(List<JengaBlock> var0, JengaBlock var1) {
      return var1 != null && !var1.removed && var1.cLsk / 3 < zaaf9(var0);
   }

   public static boolean isTowerUnstable(List<JengaBlock> var0) {
      int var1 = zaaf9(var0);

      for (int var2 = 0; var2 < var1; var2++) {
         int var3 = 0;
         int var4 = 2;
         int var5 = 0;

         for (JengaBlock var7 : var0) {
            if (var7.cLsk / 3 == var2 && !var7.removed) {
               var3++;
               var4 = Math.min(var4, var7.cLsk % 3);
               var5 = Math.max(var5, var7.cLsk % 3);
            }
         }

         if (var3 == 0) {
            return true;
         }

         double var12 = njas(var0, var2, var1);
         double var8 = var4 - 1 - 0.46;
         double var10 = var5 - 1 + 0.46;
         if (var12 < var8 + 0.04 || var12 > var10 - 0.04) {
            return true;
         }
      }

      return false;
   }

   public static boolean isTowerToppled(List<JengaBlock> var0, double var1) {
      int var3 = 0;

      for (JengaBlock var5 : var0) {
         if (!var5.removed) {
            Vector3 var6 = var5.KmyP.yiK5(var5.spawnPosition);
            double var7 = Math.sqrt(var6.x * var6.x + var6.z * var6.z);
            double var9 = Math.min(var5.halfExtents.x, var5.halfExtents.z) * 2.0;
            double var11 = Math.abs(var5.rotation.rotateVector(vector3).hgQgv(var5.WImay.rotateVector(vector3)));
            if (var5.KmyP.y < var1 - var5.halfExtents.y || Math.abs(var6.y) > var5.halfExtents.y * 3.0 || var7 > var9 * 1.5 || var11 < 0.72) {
               if (++var3 >= 4) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private static double njas(List<JengaBlock> var0, int var1, int var2) {
      double var3 = 0.0;
      int var5 = 0;
      int var6 = var1 & 1;

      for (JengaBlock var8 : var0) {
         int var9 = var8.cLsk / 3;
         if (!var8.removed && var9 >= var1 && var9 <= var2) {
            if ((var9 & 1) == var6) {
               var3 += var8.cLsk % 3 - 1;
            }

            var5++;
         }
      }

      return var5 == 0 ? 0.0 : var3 / var5;
   }

   private static int zaaf9(List<JengaBlock> var0) {
      int var1 = -1;

      for (JengaBlock var3 : var0) {
         if (!var3.removed) {
            var1 = Math.max(var1, var3.cLsk / 3);
         }
      }

      return var1;
   }
}
