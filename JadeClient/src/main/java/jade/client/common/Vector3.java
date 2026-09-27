// Jade recovery: original class: jade.deps.eLz.QUMEUR
package jade.client.common;

public final class Vector3 {
   public static final Vector3 vector3 = new Vector3(0.0, 0.0, 0.0);
   public final double x;
   public final double y;
   public final double z;

   public Vector3(double var1, double var3, double var5) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
   }

   public Vector3 add(Vector3 var1) {
      return new Vector3(this.x + var1.x, this.y + var1.y, this.z + var1.z);
   }

   public Vector3 yiK5(Vector3 var1) {
      return new Vector3(this.x - var1.x, this.y - var1.y, this.z - var1.z);
   }

   public Vector3 MMGcz7(double var1) {
      return new Vector3(this.x * var1, this.y * var1, this.z * var1);
   }

   public double hgQgv(Vector3 var1) {
      return this.x * var1.x + this.y * var1.y + this.z * var1.z;
   }

   public Vector3 cross(Vector3 var1) {
      return new Vector3(
         this.y * var1.z - this.z * var1.y,
         this.z * var1.x - this.x * var1.z,
         this.x * var1.y - this.y * var1.x
      );
   }

   public double lengthSquared() {
      return this.hgQgv(this);
   }

   public double length() {
      return Math.sqrt(this.lengthSquared());
   }

   public Vector3 normalize() {
      double var1 = this.length();
      return var1 < 1.0E-9 ? vector3 : this.MMGcz7(1.0 / var1);
   }

   public Vector3 QHQpkG() {
      return new Vector3(-this.x, -this.y, -this.z);
   }

   public Vector3 DAZXLG(Vector3 var1, double var2) {
      return new Vector3(
         this.x + (var1.x - this.x) * var2, this.y + (var1.y - this.y) * var2, this.z + (var1.z - this.z) * var2
      );
   }

   public static double clamp(double var0, double var2, double var4) {
      return Math.max(var2, Math.min(var4, var0));
   }
}
