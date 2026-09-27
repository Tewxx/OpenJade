// Jade recovery: original class: jade.deps.eLz.jt60g7H48z$2
package jade.client.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public final class FakePlayerRenderer$2 {
   private final double wcOo;
   private final double posY;
   private final double posZ;
   private final double KNr;
   private final double prevPosY;
   private final double prevPosZ;
   private final float DYWP;
   private final float prevRotationYaw;
   private final float Ximj58;
   private final float gRib;
   private final float rotationYawHead;
   private final float prevRotationYawHead;
   private final float DXcz6;
   private final float VbtY;
   private final float XTWuj8;
   private final float BXty;
   private final float pyp;
   private final float JVpE;
   private final float prevSwingProgress;
   private final boolean onGround;

   FakePlayerRenderer$2(
      double var1,
      double var3,
      double var5,
      double var7,
      double var9,
      double var11,
      float var13,
      float var14,
      float var15,
      float var16,
      float var17,
      float var18,
      float var19,
      float var20,
      float var21,
      float var22,
      float var23,
      float var24,
      float var25,
      boolean var26
   ) {
      this.wcOo = var1;
      this.posY = var3;
      this.posZ = var5;
      this.KNr = var7;
      this.prevPosY = var9;
      this.prevPosZ = var11;
      this.DYWP = var13;
      this.prevRotationYaw = var14;
      this.Ximj58 = var15;
      this.gRib = var16;
      this.rotationYawHead = var17;
      this.prevRotationYawHead = var18;
      this.DXcz6 = var19;
      this.VbtY = var20;
      this.XTWuj8 = var21;
      this.BXty = var22;
      this.pyp = var23;
      this.JVpE = var24;
      this.prevSwingProgress = var25;
      this.onGround = var26;
   }

   public static FakePlayerRenderer$2 osdbmU(EntityPlayer var0, Vec3 var1, float var2) {
      float var3 = lerp(var0.prevLimbSwingAmount, var0.limbSwingAmount, var2);
      float var4 = var0.limbSwing - var0.limbSwingAmount * (1.0F - var2);
      return new FakePlayerRenderer$2(
         var1.xCoord,
         var1.yCoord,
         var1.zCoord,
         var1.xCoord,
         var1.yCoord,
         var1.zCoord,
         var0.rotationYaw,
         var0.prevRotationYaw,
         var0.rotationPitch,
         var0.prevRotationPitch,
         var0.rotationYawHead,
         var0.prevRotationYawHead,
         var0.renderYawOffset,
         var0.prevRenderYawOffset,
         var4,
         var3,
         var3,
         var0.swingProgress,
         var0.prevSwingProgress,
         var0.onGround
      );
   }

   public static FakePlayerRenderer$2 captureInterpolatedSnapshot(EntityPlayer var0, float var1) {
      double var2 = var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * var1;
      double var4 = var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * var1;
      double var6 = var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * var1;
      float var8 = lerp(var0.prevLimbSwingAmount, var0.limbSwingAmount, var1);
      float var9 = var0.limbSwing - var0.limbSwingAmount * (1.0F - var1);
      return new FakePlayerRenderer$2(
         var2,
         var4,
         var6,
         var0.lastTickPosX,
         var0.lastTickPosY,
         var0.lastTickPosZ,
         lerpAngle(var0.prevRotationYaw, var0.rotationYaw, var1),
         var0.prevRotationYaw,
         var0.prevRotationPitch + (var0.rotationPitch - var0.prevRotationPitch) * var1,
         var0.prevRotationPitch,
         lerpAngle(var0.prevRotationYawHead, var0.rotationYawHead, var1),
         var0.prevRotationYawHead,
         lerpAngle(var0.prevRenderYawOffset, var0.renderYawOffset, var1),
         var0.prevRenderYawOffset,
         var9,
         var8,
         var8,
         var0.swingProgress,
         var0.prevSwingProgress,
         var0.onGround
      );
   }

   public static FakePlayerRenderer$2 interpolateSnapshot(FakePlayerRenderer$2 var0, FakePlayerRenderer$2 var1, float var2) {
      float var3 = FakePlayerRenderer.clampAlpha(var2);
      return new FakePlayerRenderer$2(
         lerpDouble(var0.wcOo, var1.wcOo, var3),
         lerpDouble(var0.posY, var1.posY, var3),
         lerpDouble(var0.posZ, var1.posZ, var3),
         lerpDouble(var0.KNr, var1.KNr, var3),
         lerpDouble(var0.prevPosY, var1.prevPosY, var3),
         lerpDouble(var0.prevPosZ, var1.prevPosZ, var3),
         lerpAngle(var0.DYWP, var1.DYWP, var3),
         var0.DYWP,
         lerp(var0.Ximj58, var1.Ximj58, var3),
         var0.Ximj58,
         lerpAngle(var0.rotationYawHead, var1.rotationYawHead, var3),
         var0.rotationYawHead,
         lerpAngle(var0.DXcz6, var1.DXcz6, var3),
         var0.DXcz6,
         lerp(var0.XTWuj8, var1.XTWuj8, var3),
         lerp(var0.BXty, var1.BXty, var3),
         lerp(var0.pyp, var1.pyp, var3),
         lerp(var0.JVpE, var1.JVpE, var3),
         var0.JVpE,
         var1.onGround
      );
   }

   private static double lerpDouble(double var0, double var2, float var4) {
      return var0 + (var2 - var0) * var4;
   }

   private static float lerp(float var0, float var1, float var2) {
      return var0 + (var1 - var0) * var2;
   }

   private static float lerpAngle(float var0, float var1, float var2) {
      float var3 = var1 - var0;

      while (var3 < -180.0F) {
         var3 += 360.0F;
      }

      while (var3 >= 180.0F) {
         var3 -= 360.0F;
      }

      return var0 + var3 * var2;
   }

   public static double getPosX(FakePlayerRenderer$2 var0) {
      return var0.wcOo;
   }

   public static double getPosY(FakePlayerRenderer$2 var0) {
      return var0.posY;
   }

   public static double getPosZ(FakePlayerRenderer$2 var0) {
      return var0.posZ;
   }

   public static float getRotationYaw(FakePlayerRenderer$2 var0) {
      return var0.DYWP;
   }

   public static float kiboaU(FakePlayerRenderer$2 var0) {
      return var0.Ximj58;
   }

   public static double getPrevPosX(FakePlayerRenderer$2 var0) {
      return var0.KNr;
   }

   public static double getPrevPosY(FakePlayerRenderer$2 var0) {
      return var0.prevPosY;
   }

   public static double getPrevPosZ(FakePlayerRenderer$2 var0) {
      return var0.prevPosZ;
   }

   public static float getPrevRotationYaw(FakePlayerRenderer$2 var0) {
      return var0.prevRotationYaw;
   }

   public static float getPrevRotationPitch(FakePlayerRenderer$2 var0) {
      return var0.gRib;
   }

   public static float getRotationYawHead(FakePlayerRenderer$2 var0) {
      return var0.rotationYawHead;
   }

   public static float BfWxr(FakePlayerRenderer$2 var0) {
      return var0.prevRotationYawHead;
   }

   public static float getRenderYawOffset(FakePlayerRenderer$2 var0) {
      return var0.DXcz6;
   }

   public static float getPrevRenderYawOffset(FakePlayerRenderer$2 var0) {
      return var0.VbtY;
   }

   public static float getLimbSwing(FakePlayerRenderer$2 var0) {
      return var0.XTWuj8;
   }

   public static float getLimbSwingAmount(FakePlayerRenderer$2 var0) {
      return var0.BXty;
   }

   public static boolean isOnGround(FakePlayerRenderer$2 var0) {
      return var0.onGround;
   }
}
