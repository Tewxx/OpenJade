// Jade recovery: original class: jade.deps.eLz.oEmqQwelhb
package jade.client.common;

public final class oEmqQwelhb {
   private final float scaledX;
   private final float scaledY;

   private oEmqQwelhb(float var1, float var2) {
      this.scaledX = var1;
      this.scaledY = var2;
   }

   public static oEmqQwelhb computeScaledPosition(float var0, float var1, float var2) {
      return new oEmqQwelhb((var0 + 4.0F) * 2.0F, (var1 + var2 + 4.0F) * 2.0F);
   }

   public float getScaledX() {
      return this.scaledX;
   }

   public float getScaledY() {
      return this.scaledY;
   }
}
