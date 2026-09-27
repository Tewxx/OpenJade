// Jade recovery: original class: jade.deps.eLz.FiuflJ$1
package jade.client.module.render;

import net.minecraft.entity.EntityLivingBase;

public final class ESP$1 {
   private EntityLivingBase entityLivingBase;
   private int color;
   private int YYem;
   private double leftX;
   private double topY;
   private double rightX;
   private double bottomY;
   private boolean knP;

   ESP$1() {
   }

   private void bpzU(EntityLivingBase var1, int var2, int var3) {
      this.entityLivingBase = var1;
      this.color = var2;
      this.YYem = var3;
      this.knP = false;
   }

   public static EntityLivingBase ZGuBgtI(ESP$1 var0) {
      return var0.entityLivingBase;
   }

   public static int setColor(ESP$1 var0, int var1) {
      return var0.color = var1;
   }

   public static int getMode(ESP$1 var0) {
      return var0.YYem;
   }

   public static int KGTuj(ESP$1 var0) {
      return var0.color;
   }

   public static void initializeEntry(ESP$1 var0, EntityLivingBase var1, int var2, int var3) {
      var0.bpzU(var1, var2, var3);
   }

   public static boolean JzgO42(ESP$1 var0, boolean var1) {
      return var0.knP = var1;
   }

   public static double setLeftX(ESP$1 var0, double var1) {
      return var0.leftX = var1;
   }

   public static double setTopY(ESP$1 var0, double var1) {
      return var0.topY = var1;
   }

   public static double setRightX(ESP$1 var0, double var1) {
      return var0.rightX = var1;
   }

   public static double setBottomY(ESP$1 var0, double var1) {
      return var0.bottomY = var1;
   }

   public static double getRightX(ESP$1 var0) {
      return var0.rightX;
   }

   public static double getLeftX(ESP$1 var0) {
      return var0.leftX;
   }

   public static double getBottomY(ESP$1 var0) {
      return var0.bottomY;
   }

   public static double getTopY(ESP$1 var0) {
      return var0.topY;
   }

   public static boolean isOnScreen(ESP$1 var0) {
      return var0.knP;
   }
}
