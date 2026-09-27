// Jade recovery: original class: jade.deps.eLz.cKF4v7iKNK
package jade.client.common;

import java.util.HashSet;
import java.util.List;

public final class JengaPhysics {
   private static final Vector3 vector3 = new Vector3(0.0, 1.0, 0.0);
   private static final double pyK5 = -9.81;
   private static final int ShA = 4;
   private static final int NDn = 5;
   private static final double PENETRATION_EPSILON = 3.0E-4;
   private static final double ScD = 0.35;

   public void dragHeldBlock(
      List<JengaBlock> var1, JengaBlock var2, Vector3 var3, double var4, double var6, double var8, Vector3 var10, Quaternion var11, double var12, double var14
   ) {
      if (var2 != null && var2.held && !(var4 <= 0.0)) {
         Vector3 var16 = var2.KmyP;
         Vector3 var17 = var3.yiK5(var16);
         double var18 = var17.length();
         if (var18 > 0.35) {
            var17 = var17.MMGcz7(0.35 / var18);
            var18 = 0.35;
         }

         if (var18 < 1.0E-8) {
            var2.velocity = Vector3.vector3;
         } else {
            double var20 = Math.min(var2.halfExtents.x, Math.min(var2.halfExtents.y, var2.halfExtents.z));
            int var22 = Math.max(1, (int)Math.ceil(var18 / Math.max(0.004, var20 * 0.3)));
            Vector3 var23 = var17.MMGcz7(1.0 / var22);
            Vector3 var24 = var17.MMGcz7(1.0 / var4);
            HashSet var25 = new HashSet();

            for (int var26 = 0; var26 < var22; var26++) {
               var2.KmyP = var2.KmyP.add(var23);
               this.constrainHeldBlockToFloor(var2, var6, var8, var10, var11, var12, var14);

               for (int var27 = 0; var27 < 3; var27++) {
                  boolean var28 = false;

                  for (JengaBlock var30 : var1) {
                     if (var30 != var2 && !var30.held && var30.removed == var2.removed) {
                        double var31 = var2.getBoundingRadius() + var30.getBoundingRadius() + 0.03;
                        if (!(var2.KmyP.yiK5(var30.KmyP).lengthSquared() > var31 * var31)) {
                           JengaPhysics$1 var33 = this.computeContact(var2, var30);
                           if (var33 != null) {
                              this.MsqUqj(var2, var30, var33, var24, var25.add(var30));
                              var28 = true;
                           }
                        }
                     }
                  }

                  if (!var28) {
                     break;
                  }
               }
            }

            var2.velocity = var2.KmyP.yiK5(var16).MMGcz7(1.0 / var4);
         }
      }
   }

   private void constrainHeldBlockToFloor(JengaBlock var1, double var2, double var4, Vector3 var6, Quaternion var7, double var8, double var10) {
      double var12 = var1.BkF3(vector3);
      double var14 = var1.KmyP.y - var12;
      double var16 = var2;
      Vector3 var18 = var7.LZAUFqA().rotateVector(var1.KmyP.yiK5(var6));
      double var19 = this.getProjectedExtent(var1, var7.rotateVector(new Vector3(1.0, 0.0, 0.0)));
      double var21 = this.getProjectedExtent(var1, var7.rotateVector(new Vector3(0.0, 0.0, 1.0)));
      if (Math.abs(var18.x) <= var8 + var19 && Math.abs(var18.z) <= var10 + var21 && var1.KmyP.y >= var4 - var12) {
         var16 = var4;
      }

      if (var14 < var16) {
         var1.KmyP = var1.KmyP.add(new Vector3(0.0, var16 - var14 + 3.0E-4, 0.0));
      }
   }

   private void MsqUqj(JengaBlock var1, JengaBlock var2, JengaPhysics$1 var3, Vector3 var4, boolean var5) {
      Vector3 var6 = var3.collisionNormal;
      double var7 = Math.max(0.0, var3.penetrationDepth) + 3.0E-4;
      var1.KmyP = var1.KmyP.yiK5(var6.MMGcz7(var7));
      if (var5) {
         double var9 = var4.yiK5(var2.rKnnW(var3.contactPoint)).hgQgv(var6);
         if (!(var9 <= 0.02)) {
            var2.EDVnA4 = false;
            var2.settledTicks = 0;
            double var11 = Math.min(0.22, var9 * 0.16);
            var2.applyImpulse(var6.MMGcz7(var11), var3.contactPoint);
         }
      }
   }

   public void Agxt(List<JengaBlock> var1, double var2, double var4, double var6, Vector3 var8, Quaternion var9, double var10, double var12) {
      double var14 = Math.min(0.05, Math.max(0.0, var2)) / 4.0;
      if (!(var14 <= 0.0)) {
         for (int var16 = 0; var16 < 4; var16++) {
            this.integrateMotion(var1, var14);

            for (int var17 = 0; var17 < 5; var17++) {
               this.resolveFloorContacts(var1, var4, var6, var8, var9, var10, var12);
               this.AYxax(var1);
            }

            this.applyVelocityDamping(var1, var14);
         }

         this.settleRestingBlocks(var1, var4, var6, var8, var9, var10, var12);
      }
   }

   private void integrateMotion(List<JengaBlock> var1, double var2) {
      for (JengaBlock var5 : var1) {
         if (var5.physicsEnabled && !var5.held && !var5.EDVnA4) {
            var5.velocity = var5.velocity.add(new Vector3(0.0, -9.81 * var2, 0.0));
            var5.KmyP = var5.KmyP.add(var5.velocity.MMGcz7(var2));
            var5.rotation = var5.rotation.rotateByAxisAngle(var5.bN0, var2);
         }
      }
   }

   private void applyVelocityDamping(List<JengaBlock> var1, double var2) {
      double var4 = Math.pow(0.985, var2 * 20.0);
      double var6 = Math.pow(0.94, var2 * 20.0);

      for (JengaBlock var9 : var1) {
         if (var9.physicsEnabled && !var9.held && !var9.EDVnA4) {
            var9.velocity = var9.velocity.MMGcz7(var4);
            var9.bN0 = var9.bN0.MMGcz7(var6);
            var9.velocity = this.clampLength(var9.velocity, 5.0);
            var9.bN0 = this.clampLength(var9.bN0, 10.0);
            if (var9.velocity.lengthSquared() < 1.0E-7) {
               var9.velocity = Vector3.vector3;
            }

            if (var9.bN0.lengthSquared() < 1.0E-7) {
               var9.bN0 = Vector3.vector3;
            }
         }
      }
   }

   private void resolveFloorContacts(List<JengaBlock> var1, double var2, double var4, Vector3 var6, Quaternion var7, double var8, double var10) {
      for (JengaBlock var13 : var1) {
         if (var13.physicsEnabled && !var13.held && !var13.EDVnA4) {
            this.resolveFloorPenetration(var13, var2, 0.85);
            Vector3 var14 = var7.LZAUFqA().rotateVector(var13.KmyP.yiK5(var6));
            double var15 = this.getProjectedExtent(var13, var7.rotateVector(new Vector3(1.0, 0.0, 0.0)));
            double var17 = this.getProjectedExtent(var13, var7.rotateVector(new Vector3(0.0, 0.0, 1.0)));
            boolean var19 = Math.abs(var14.x) <= var8 + var15 && Math.abs(var14.z) <= var10 + var17;
            double var20 = var13.KmyP.y - var13.BkF3(vector3);
            if (var19 && var13.KmyP.y > var4 && var20 >= var4 - 0.045) {
               this.resolveFloorPenetration(var13, var4, 0.72);
            }
         }
      }
   }

   private double getProjectedExtent(JengaBlock var1, Vector3 var2) {
      return var1.BkF3(var2);
   }

   private void resolveFloorPenetration(JengaBlock var1, double var2, double var4) {
      double var6 = var1.BkF3(vector3);
      double var8 = var2 - (var1.KmyP.y - var6);
      if (!(var8 <= 0.0)) {
         var1.KmyP = var1.KmyP.add(new Vector3(0.0, var8 + 3.0E-4, 0.0));
         Vector3 var10 = new Vector3(var1.KmyP.x, var2, var1.KmyP.z);
         Vector3 var11 = var1.rKnnW(var10);
         if (var11.y < 0.0) {
            double var12 = var11.y < -1.2 ? 0.04 : 0.0;
            Vector3 var14 = vector3.MMGcz7(-(1.0 + var12) * var11.y / this.computeEffectiveInertia(var1, var10, vector3));
            var1.applyImpulse(var14, var10);
         }

         Vector3 var19 = new Vector3(var11.x, 0.0, var11.z);
         double var13 = var19.length();
         if (var13 > 1.0E-7) {
            double var15 = Math.max(0.0, -var11.y) * var4 + 0.018;
            double var17 = var13 / this.computeEffectiveInertia(var1, var10, var19.normalize());
            var1.applyImpulse(var19.normalize().MMGcz7(-Math.min(var17, var15)), var10);
         }

         var1.bN0 = var1.bN0.MMGcz7(0.82);
      }
   }

   private double computeEffectiveInertia(JengaBlock var1, Vector3 var2, Vector3 var3) {
      Vector3 var4 = var2.yiK5(var1.KmyP);
      Vector3 var5 = var1.uOrpJ(var4.cross(var3)).cross(var4);
      return Math.max(1.0E-6, 1.0 + var3.hgQgv(var5));
   }

   private void AYxax(List<JengaBlock> var1) {
      int var2 = var1.size();

      for (int var3 = 0; var3 < var2; var3++) {
         JengaBlock var4 = (JengaBlock)var1.get(var3);

         for (int var5 = var3 + 1; var5 < var2; var5++) {
            JengaBlock var6 = (JengaBlock)var1.get(var5);
            if (var4.removed == var6.removed && (var4.held || var6.held || !var4.EDVnA4 || !var6.EDVnA4)) {
               double var7 = var4.getBoundingRadius() + var6.getBoundingRadius() + 0.03;
               if (!(var4.KmyP.yiK5(var6.KmyP).lengthSquared() > var7 * var7)) {
                  JengaPhysics$1 var9 = this.computeContact(var4, var6);
                  if (var9 != null) {
                     if (!var4.held && !var6.held) {
                        this.resolveBlockCollision(var4, var6, var9);
                     } else {
                        this.resolveHeldBlockCollision(var4, var6, var9);
                     }
                  }
               }
            }
         }
      }
   }

   private void resolveHeldBlockCollision(JengaBlock var1, JengaBlock var2, JengaPhysics$1 var3) {
      if (!var1.held || !var2.held) {
         JengaBlock var4 = var1.held ? var1 : var2;
         Vector3 var5 = var1.held ? var3.collisionNormal : var3.collisionNormal.QHQpkG();
         double var6 = Math.max(0.0, var3.penetrationDepth) + 3.0E-4;
         var4.KmyP = var4.KmyP.yiK5(var5.MMGcz7(var6));
         double var8 = var4.velocity.hgQgv(var5);
         if (var8 > 0.0) {
            var4.velocity = var4.velocity.yiK5(var5.MMGcz7(var8));
         }
      }
   }

   private JengaPhysics$1 computeContact(JengaBlock var1, JengaBlock var2) {
      Vector3[] var3 = var1.getRotationAxes();
      Vector3[] var4 = var2.getRotationAxes();
      Vector3 var5 = var2.KmyP.yiK5(var1.KmyP);
      Vector3 var6 = null;
      double var7 = Double.POSITIVE_INFINITY;

      for (int var9 = 0; var9 < 3; var9++) {
         JengaPhysics$0 var10 = this.testSeparatingAxis(var1, var2, var3, var4, var5, var3[var9], var7);
         if (!var10.colliding) {
            return null;
         }

         if (var10.penetrationDepth < var7) {
            var7 = var10.penetrationDepth;
            var6 = var10.collisionAxis;
         }
      }

      for (int var15 = 0; var15 < 3; var15++) {
         JengaPhysics$0 var18 = this.testSeparatingAxis(var1, var2, var3, var4, var5, var4[var15], var7);
         if (!var18.colliding) {
            return null;
         }

         if (var18.penetrationDepth < var7) {
            var7 = var18.penetrationDepth;
            var6 = var18.collisionAxis;
         }
      }

      for (int var16 = 0; var16 < 3; var16++) {
         for (int var19 = 0; var19 < 3; var19++) {
            Vector3 var11 = var3[var16].cross(var4[var19]);
            if (!(var11.lengthSquared() < 1.0E-8)) {
               JengaPhysics$0 var12 = this.testSeparatingAxis(var1, var2, var3, var4, var5, var11.normalize(), var7);
               if (!var12.colliding) {
                  return null;
               }

               if (var12.penetrationDepth < var7) {
                  var7 = var12.penetrationDepth;
                  var6 = var12.collisionAxis;
               }
            }
         }
      }

      if (var6 == null) {
         return null;
      } else {
         if (var5.hgQgv(var6) < 0.0) {
            var6 = var6.QHQpkG();
         }

         double var17 = var1.projectedRadius(var6, var3);
         double var20 = var2.projectedRadius(var6, var4);
         Vector3 var13 = var1.KmyP.add(var6.MMGcz7(var17));
         Vector3 var14 = var2.KmyP.yiK5(var6.MMGcz7(var20));
         return new JengaPhysics$1(var6, var7, var13.DAZXLG(var14, 0.5));
      }
   }

   private JengaPhysics$0 testSeparatingAxis(JengaBlock var1, JengaBlock var2, Vector3[] var3, Vector3[] var4, Vector3 var5, Vector3 var6, double var7) {
      double var9 = Math.abs(var5.hgQgv(var6));
      double var11 = var1.projectedRadius(var6, var3) + var2.projectedRadius(var6, var4) - var9;
      return var11 < -3.0E-4 ? JengaPhysics$0.ODMd : new JengaPhysics$0(true, Math.min(var11, var7), var6);
   }

   private void resolveBlockCollision(JengaBlock var1, JengaBlock var2, JengaPhysics$1 var3) {
      double var4 = !var1.held && !var1.EDVnA4 ? 1.0 : 0.0;
      double var6 = !var2.held && !var2.EDVnA4 ? 1.0 : 0.0;
      double var8 = var4 + var6;
      if (!(var8 <= 0.0)) {
         double var10 = Math.max(0.0, var3.penetrationDepth - 3.0E-4) * 0.48 / var8;
         Vector3 var12 = var3.collisionNormal.MMGcz7(var10);
         if (var4 > 0.0) {
            var1.KmyP = var1.KmyP.yiK5(var12.MMGcz7(var4));
         }

         if (var6 > 0.0) {
            var2.KmyP = var2.KmyP.add(var12.MMGcz7(var6));
         }

         Vector3 var13 = var2.rKnnW(var3.contactPoint).yiK5(var1.rKnnW(var3.contactPoint));
         double var14 = var13.hgQgv(var3.collisionNormal);
         if (!(var14 >= 0.0)) {
            if (var14 < -0.045) {
               if (var1.EDVnA4 && !var2.EDVnA4) {
                  var1.EDVnA4 = false;
                  var4 = 1.0;
               }

               if (var2.EDVnA4 && !var1.EDVnA4) {
                  var2.EDVnA4 = false;
                  var6 = 1.0;
               }

               var8 = var4 + var6;
            }

            double var16 = var4 + var6;
            if (var4 > 0.0) {
               var16 += this.computeAngularResponse(var1, var3.contactPoint, var3.collisionNormal);
            }

            if (var6 > 0.0) {
               var16 += this.computeAngularResponse(var2, var3.contactPoint, var3.collisionNormal);
            }

            double var18 = var14 < -1.2 ? 0.025 : 0.0;
            double var20 = -(1.0 + var18) * var14 / Math.max(1.0E-6, var16);
            Vector3 var22 = var3.collisionNormal.MMGcz7(var20);
            var1.applyImpulse(var22.QHQpkG(), var3.contactPoint);
            var2.applyImpulse(var22, var3.contactPoint);
            var13 = var2.rKnnW(var3.contactPoint).yiK5(var1.rKnnW(var3.contactPoint));
            Vector3 var23 = var13.yiK5(var3.collisionNormal.MMGcz7(var13.hgQgv(var3.collisionNormal)));
            if (var23.lengthSquared() > 1.0E-9) {
               var23 = var23.normalize();
               double var24 = var4 + var6;
               if (var4 > 0.0) {
                  var24 += this.computeAngularResponse(var1, var3.contactPoint, var23);
               }

               if (var6 > 0.0) {
                  var24 += this.computeAngularResponse(var2, var3.contactPoint, var23);
               }

               double var26 = -var13.hgQgv(var23) / Math.max(1.0E-6, var24);
               double var28 = var20 * 0.62;
               var26 = Vector3.clamp(var26, -var28, var28);
               Vector3 var30 = var23.MMGcz7(var26);
               var1.applyImpulse(var30.QHQpkG(), var3.contactPoint);
               var2.applyImpulse(var30, var3.contactPoint);
            }
         }
      }
   }

   private double computeAngularResponse(JengaBlock var1, Vector3 var2, Vector3 var3) {
      Vector3 var4 = var2.yiK5(var1.KmyP);
      return var3.hgQgv(var1.uOrpJ(var4.cross(var3)).cross(var4));
   }

   private void settleRestingBlocks(List<JengaBlock> var1, double var2, double var4, Vector3 var6, Quaternion var7, double var8, double var10) {
      for (JengaBlock var13 : var1) {
         if (var13.physicsEnabled && !var13.held && !var13.EDVnA4) {
            if (!this.isBlockSupported(var13, var1, var2, var4, var6, var7, var8, var10)) {
               var13.settledTicks = 0;
            } else {
               var13.velocity = new Vector3(var13.velocity.x * 0.68, var13.velocity.y, var13.velocity.z * 0.68);
               var13.bN0 = var13.bN0.MMGcz7(0.68);
               if (var13.velocity.lengthSquared() < 0.0016 && var13.bN0.lengthSquared() < 0.025) {
                  var13.settledTicks++;
                  if (var13.settledTicks >= 8) {
                     var13.velocity = Vector3.vector3;
                     var13.bN0 = Vector3.vector3;
                     var13.EDVnA4 = true;
                  }
               } else {
                  var13.settledTicks = 0;
               }
            }
         }
      }
   }

   private boolean isBlockSupported(JengaBlock var1, List<JengaBlock> var2, double var3, double var5, Vector3 var7, Quaternion var8, double var9, double var11) {
      double var13 = var1.KmyP.y - var1.BkF3(vector3);
      if (Math.abs(var13 - var3) <= 0.012) {
         return true;
      } else {
         Vector3 var15 = var8.LZAUFqA().rotateVector(var1.KmyP.yiK5(var7));
         if (Math.abs(var13 - var5) <= 0.012 && Math.abs(var15.x) <= var9 && Math.abs(var15.z) <= var11) {
            return true;
         } else {
            for (JengaBlock var17 : var2) {
               if (var17 != var1 && !var17.held) {
                  double var18 = var17.KmyP.y + var17.BkF3(vector3);
                  if (!(Math.abs(var13 - var18) > 0.012)) {
                     double var20 = var1.KmyP.yiK5(var17.KmyP).length();
                     if (var20 <= var1.getBoundingRadius() + var17.getBoundingRadius() + 0.012) {
                        return true;
                     }
                  }
               }
            }

            return false;
         }
      }
   }

   private Vector3 clampLength(Vector3 var1, double var2) {
      double var4 = var1.length();
      return var4 > var2 ? var1.MMGcz7(var2 / var4) : var1;
   }
}
