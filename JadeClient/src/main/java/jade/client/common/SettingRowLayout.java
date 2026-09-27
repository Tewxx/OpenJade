// Jade recovery: original class: jade.deps.eLz.dADJY7P
package jade.client.common;

public final class SettingRowLayout {
   private final float x;
   private final float y;
   private final float width;
   private final float bcpEgh;
   private final float rightEdgeX;
   private final float secondaryRowY;
   private final float tertiaryRowY;

   private SettingRowLayout(float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.x = var1;
      this.y = var2;
      this.width = var3;
      this.bcpEgh = var4;
      this.rightEdgeX = var5;
      this.secondaryRowY = var6;
      this.tertiaryRowY = var7;
   }

   public static SettingRowLayout create(float var0, float var1, float var2, float var3, float var4, float var5) {
      return new SettingRowLayout(var0, var1, var2, var0 + 4.0F + var4 / 2.0F, var0 + var2 - 4.0F, var1 + var3 + var5, var1 + var3 + var5 * 2.0F);
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getWidth() {
      return this.width;
   }

   public float getControlCenterX() {
      return this.bcpEgh;
   }

   public float getRightEdgeX() {
      return this.rightEdgeX;
   }

   public float getSecondaryRowY() {
      return this.secondaryRowY;
   }

   public float getTertiaryRowY() {
      return this.tertiaryRowY;
   }

   public static float QYl2(float var0, float var1, float var2, float var3, float var4) {
      float var5 = Math.max(1.0F, (var4 - var3) * var2);
      return var0 + (var1 - var5) / 2.0F - var3 * var2;
   }
}
