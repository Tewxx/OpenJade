// Jade recovery: original class: jade.deps.eLz.A2c8GVQvn$1
package jade.client.module.render;

import net.minecraft.entity.player.EntityPlayer;

public final class Pointers$1 {
   private EntityPlayer entityPlayer;
   private int color;
   private float exTi;
   private float arrowScale;
   private boolean tracked;
   private boolean LgAt;
   private double FBTi;
   private double screenY;
   private double ZWrZ;

   Pointers$1() {
   }

   private void YdgV(EntityPlayer var1, int var2, boolean var3) {
      this.entityPlayer = var1;
      this.color = var2;
      this.tracked = var3;
   }

   private void setScreenPosition(double var1, double var3, double var5) {
      this.FBTi = var1;
      this.screenY = var3;
      this.ZWrZ = var5;
      this.LgAt = true;
   }

   public static EntityPlayer getTrackedPlayer(Pointers$1 var0) {
      return var0.entityPlayer;
   }

   public static float getVisibility(Pointers$1 var0) {
      return var0.exTi;
   }

   public static boolean setTracked(Pointers$1 var0, boolean var1) {
      return var0.tracked = var1;
   }

   public static void nlag(Pointers$1 var0, EntityPlayer var1, int var2, boolean var3) {
      var0.YdgV(var1, var2, var3);
   }

   public static boolean isTracked(Pointers$1 var0) {
      return var0.tracked;
   }

   public static float setVisibility(Pointers$1 var0, float var1) {
      return var0.exTi = var1;
   }

   public static float getArrowScale(Pointers$1 var0) {
      return var0.arrowScale;
   }

   public static void rKaqs(Pointers$1 var0, double var1, double var3, double var5) {
      var0.setScreenPosition(var1, var3, var5);
   }

   public static int dHqaD(Pointers$1 var0) {
      return var0.color;
   }

   public static boolean PpU5(Pointers$1 var0) {
      return var0.LgAt;
   }

   public static double getScreenX(Pointers$1 var0) {
      return var0.FBTi;
   }

   public static double getScreenY(Pointers$1 var0) {
      return var0.screenY;
   }

   public static double iBjoKj(Pointers$1 var0) {
      return var0.ZWrZ;
   }

   public static float setArrowScale(Pointers$1 var0, float var1) {
      return var0.arrowScale = var1;
   }
}
