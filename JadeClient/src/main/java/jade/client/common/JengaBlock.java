// Jade recovery: original class: jade.deps.eLz.lL5n6ZEX
package jade.client.common;

public final class JengaBlock {
   public final int cLsk;
   public final Vector3 halfExtents;
   public final int colorArgb;
   public final boolean physicsEnabled;
   public final Vector3 spawnPosition;
   public final Quaternion WImay;
   public Vector3 KmyP;
   public Vector3 previousPosition;
   public Quaternion rotation;
   public Quaternion previousRotation;
   public Vector3 velocity = Vector3.vector3;
   public Vector3 bN0 = Vector3.vector3;
   public boolean held;
   public boolean EDVnA4;
   public boolean removed;
   public int settledTicks;

   public JengaBlock(int var1, Vector3 var2, Vector3 var3, Quaternion var4, int var5, boolean var6) {
      this.cLsk = var1;
      this.KmyP = var2;
      this.previousPosition = var2;
      this.spawnPosition = var2;
      this.halfExtents = var3;
      this.rotation = var4;
      this.previousRotation = var4;
      this.WImay = var4;
      this.colorArgb = var5;
      this.physicsEnabled = var6;
      this.EDVnA4 = var6;
   }

   public void DlCxwUo() {
      this.previousPosition = this.KmyP;
      this.previousRotation = this.rotation;
   }

   public Vector3[] getRotationAxes() {
      return new Vector3[]{this.rotation.rotateVector(new Vector3(1.0, 0.0, 0.0)), this.rotation.rotateVector(new Vector3(0.0, 1.0, 0.0)), this.rotation.rotateVector(new Vector3(0.0, 0.0, 1.0))};
   }

   public double BkF3(Vector3 var1) {
      return this.projectedRadius(var1, this.getRotationAxes());
   }

   public double projectedRadius(Vector3 var1, Vector3[] var2) {
      return Math.abs(var1.hgQgv(var2[0])) * this.halfExtents.x
         + Math.abs(var1.hgQgv(var2[1])) * this.halfExtents.y
         + Math.abs(var1.hgQgv(var2[2])) * this.halfExtents.z;
   }

   public Vector3 rKnnW(Vector3 var1) {
      return this.velocity.add(this.bN0.cross(var1.yiK5(this.KmyP)));
   }

   public void applyImpulse(Vector3 var1, Vector3 var2) {
      if (this.physicsEnabled && !this.held && !this.EDVnA4) {
         this.velocity = this.velocity.add(var1);
         Vector3 var3 = var2.yiK5(this.KmyP).cross(var1);
         this.bN0 = this.bN0.add(this.uOrpJ(var3));
      }
   }

   public Vector3 uOrpJ(Vector3 var1) {
      Vector3 var2 = this.rotation.LZAUFqA().rotateVector(var1);
      double var3 = this.halfExtents.x * 2.0;
      double var5 = this.halfExtents.y * 2.0;
      double var7 = this.halfExtents.z * 2.0;
      double var9 = 12.0 / Math.max(1.0E-6, var5 * var5 + var7 * var7);
      double var11 = 12.0 / Math.max(1.0E-6, var3 * var3 + var7 * var7);
      double var13 = 12.0 / Math.max(1.0E-6, var3 * var3 + var5 * var5);
      return this.rotation.rotateVector(new Vector3(var2.x * var9, var2.y * var11, var2.z * var13));
   }

   public double getBoundingRadius() {
      return Math.sqrt(this.halfExtents.lengthSquared());
   }
}
