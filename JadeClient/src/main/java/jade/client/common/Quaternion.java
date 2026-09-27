// Jade recovery: original class: jade.deps.eLz.CRaKRNTbZa
package jade.client.common;

public final class Quaternion {
   public static final Quaternion DGw = new Quaternion(1.0, 0.0, 0.0, 0.0);
   public final double wComponent;
   public final double xComponent;
   public final double yComponent;
   public final double zComponent;

   public Quaternion(double var1, double var3, double var5, double var7) {
      this.wComponent = var1;
      this.xComponent = var3;
      this.yComponent = var5;
      this.zComponent = var7;
   }

   public static Quaternion fromAxisAngle(Vector3 var0, double var1) {
      Vector3 var3 = var0.normalize();
      double var4 = var1 * 0.5;
      double var6 = Math.sin(var4);
      return new Quaternion(Math.cos(var4), var3.x * var6, var3.y * var6, var3.z * var6);
   }

   public Quaternion SITfh(Quaternion var1) {
      return new Quaternion(
         this.wComponent * var1.wComponent - this.xComponent * var1.xComponent - this.yComponent * var1.yComponent - this.zComponent * var1.zComponent,
         this.wComponent * var1.xComponent + this.xComponent * var1.wComponent + this.yComponent * var1.zComponent - this.zComponent * var1.yComponent,
         this.wComponent * var1.yComponent - this.xComponent * var1.zComponent + this.yComponent * var1.wComponent + this.zComponent * var1.xComponent,
         this.wComponent * var1.zComponent + this.xComponent * var1.yComponent - this.yComponent * var1.xComponent + this.zComponent * var1.wComponent
      );
   }

   public Quaternion addScaled(Quaternion var1, double var2) {
      return new Quaternion(this.wComponent + var1.wComponent * var2, this.xComponent + var1.xComponent * var2, this.yComponent + var1.yComponent * var2, this.zComponent + var1.zComponent * var2);
   }

   public Quaternion normalize() {
      double var1 = Math.sqrt(this.wComponent * this.wComponent + this.xComponent * this.xComponent + this.yComponent * this.yComponent + this.zComponent * this.zComponent);
      return var1 < 1.0E-10 ? DGw : new Quaternion(this.wComponent / var1, this.xComponent / var1, this.yComponent / var1, this.zComponent / var1);
   }

   public Quaternion LZAUFqA() {
      return new Quaternion(this.wComponent, -this.xComponent, -this.yComponent, -this.zComponent);
   }

   public Vector3 rotateVector(Vector3 var1) {
      Vector3 var2 = new Vector3(this.xComponent, this.yComponent, this.zComponent);
      Vector3 var3 = var2.cross(var1).MMGcz7(2.0);
      return var1.add(var3.MMGcz7(this.wComponent)).add(var2.cross(var3));
   }

   public Quaternion rotateByAxisAngle(Vector3 var1, double var2) {
      Quaternion var4 = new Quaternion(0.0, var1.x, var1.y, var1.z);
      return this.addScaled(var4.SITfh(this), var2 * 0.5).normalize();
   }

   public Quaternion interpolateTo(Quaternion var1, double var2) {
      double var4 = this.wComponent * var1.wComponent + this.xComponent * var1.xComponent + this.yComponent * var1.yComponent + this.zComponent * var1.zComponent < 0.0 ? -1.0 : 1.0;
      return new Quaternion(
            this.wComponent + (var1.wComponent * var4 - this.wComponent) * var2,
            this.xComponent + (var1.xComponent * var4 - this.xComponent) * var2,
            this.yComponent + (var1.yComponent * var4 - this.yComponent) * var2,
            this.zComponent + (var1.zComponent * var4 - this.zComponent) * var2
         )
         .normalize();
   }
}
