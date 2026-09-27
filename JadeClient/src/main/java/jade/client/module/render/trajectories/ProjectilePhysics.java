// Jade recovery: original class: jade.deps.eLz.G6KDtX2jp
package jade.client.module.render.trajectories;

public final class ProjectilePhysics {
   public final ProjectileType projectileType;
   public final double gravity;
   public final double SjX;
   public final double waterDrag;
   public final double FbwT;
   public final double dHgx;
   public final double height;
   public final double velocityScale;
   public final boolean applyWaterPhysics;
   public final boolean ignoreBlockWithoutBoundingBox;

   public ProjectilePhysics(ProjectileType var1, double var2, double var4, double var6, double var8, double var10, double var12, double var14, boolean var16, boolean var17) {
      this.projectileType = var1;
      this.gravity = var2;
      this.SjX = var4;
      this.waterDrag = var6;
      this.FbwT = var8;
      this.dHgx = var10;
      this.height = var12;
      this.velocityScale = var14;
      this.applyWaterPhysics = var16;
      this.ignoreBlockWithoutBoundingBox = var17;
   }
}
