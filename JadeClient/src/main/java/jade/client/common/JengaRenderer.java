// Jade recovery: original class: jade.deps.eLz.i1kDS7J2h
package jade.client.common;

import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public final class JengaRenderer {
   private static final int[][] FACE_VERTEX_INDICES;
   private static final Vector3[] FACE_NORMALS;
   private static final int[][] EDGE_VERTEX_INDICES;
   private static final Vector3 LIGHT_DIRECTION = new Vector3(-0.35, 0.85, 0.4).normalize();

   public void gih9(List<JengaBlock> var1, List<JengaBlock> var2, JengaBlock var3, JengaBlock var4, float var5, double var6, double var8, double var10) {
      boolean var12 = GL11.glIsEnabled(3042);
      boolean var13 = GL11.glIsEnabled(3553);
      boolean var14 = GL11.glIsEnabled(2896);
      boolean var15 = GL11.glIsEnabled(2929);
      boolean var16 = GL11.glIsEnabled(2884);
      boolean var17 = GL11.glGetBoolean(2930);
      GlStateManager.pushMatrix();

      try {
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.disableTexture2D();
         GlStateManager.disableLighting();
         GlStateManager.enableDepth();
         GlStateManager.depthMask(true);
         GlStateManager.disableCull();
         Tessellator var18 = Tessellator.getInstance();
         WorldRenderer var19 = var18.getWorldRenderer();
         var19.begin(7, DefaultVertexFormats.POSITION_COLOR);

         for (JengaBlock var21 : var1) {
            this.emitBlockFaces(var19, var21, var5, var6, var8, var10);
         }

         for (JengaBlock var26 : var2) {
            this.emitBlockFaces(var19, var26, var5, var6, var8, var10);
         }

         var18.draw();
         if (var3 != null) {
            this.renderBlockOutline(var18, var3, var5, var6, var8, var10, var3 == var4 ? -11141240 : -7318);
         }
      } finally {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.restoreCullState(var16);
         this.restoreTexture(var13);
         this.qnrqaf(var14);
         this.restoreDepthTest(var15);
         this.restoreBlend(var12);
         GlStateManager.depthMask(var17);
         GlStateManager.popMatrix();
      }
   }

   private void restoreBlend(boolean var1) {
      if (var1) {
         GlStateManager.enableBlend();
      } else {
         GlStateManager.disableBlend();
      }
   }

   private void restoreTexture(boolean var1) {
      if (var1) {
         GlStateManager.enableTexture2D();
      } else {
         GlStateManager.disableTexture2D();
      }
   }

   private void qnrqaf(boolean var1) {
      if (var1) {
         GlStateManager.enableLighting();
      } else {
         GlStateManager.disableLighting();
      }
   }

   private void restoreDepthTest(boolean var1) {
      if (var1) {
         GlStateManager.enableDepth();
      } else {
         GlStateManager.disableDepth();
      }
   }

   private void restoreCullState(boolean var1) {
      if (var1) {
         GlStateManager.enableCull();
      } else {
         GlStateManager.disableCull();
      }
   }

   private void emitBlockFaces(WorldRenderer var1, JengaBlock var2, float var3, double var4, double var6, double var8) {
      double var10 = Math.max(0.0, Math.min(1.0, (double)var3));
      Vector3 var12 = var2.previousPosition.DAZXLG(var2.KmyP, var10);
      Quaternion var13 = var2.previousRotation.interpolateTo(var2.rotation, var10);
      Vector3[] var14 = this.computeCornerVertices(var12, var2.halfExtents, var13);
      int var15 = var2.colorArgb >>> 24 & 0xFF;
      int var16 = var2.colorArgb >> 16 & 0xFF;
      int var17 = var2.colorArgb >> 8 & 0xFF;
      int var18 = var2.colorArgb & 0xFF;

      for (int var19 = 0; var19 < FACE_VERTEX_INDICES.length; var19++) {
         Vector3 var20 = var13.rotateVector(FACE_NORMALS[var19]);
         double var21 = 0.58 + 0.42 * Math.max(0.0, var20.hgQgv(LIGHT_DIRECTION));
         float var23 = (float)(var16 * var21 / 255.0);
         float var24 = (float)(var17 * var21 / 255.0);
         float var25 = (float)(var18 * var21 / 255.0);
         float var26 = var15 / 255.0F;

         for (int var30 : FACE_VERTEX_INDICES[var19]) {
            Vector3 var31 = var14[var30];
            var1.pos(var31.x - var4, var31.y - var6, var31.z - var8).color(var23, var24, var25, var26).endVertex();
         }
      }
   }

   private void renderBlockOutline(Tessellator var1, JengaBlock var2, float var3, double var4, double var6, double var8, int var10) {
      double var11 = Math.max(0.0, Math.min(1.0, (double)var3));
      Vector3 var13 = var2.previousPosition.DAZXLG(var2.KmyP, var11);
      Quaternion var14 = var2.previousRotation.interpolateTo(var2.rotation, var11);
      Vector3 var15 = var2.halfExtents.add(new Vector3(0.006, 0.006, 0.006));
      Vector3[] var16 = this.computeCornerVertices(var13, var15, var14);
      float var17 = (var10 >> 16 & 0xFF) / 255.0F;
      float var18 = (var10 >> 8 & 0xFF) / 255.0F;
      float var19 = (var10 & 0xFF) / 255.0F;
      float var20 = (var10 >>> 24 & 0xFF) / 255.0F;
      GL11.glLineWidth(2.2F);
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      WorldRenderer var21 = var1.getWorldRenderer();
      var21.begin(1, DefaultVertexFormats.POSITION_COLOR);

      for (int[] var25 : EDGE_VERTEX_INDICES) {
         for (int var29 : var25) {
            Vector3 var30 = var16[var29];
            var21.pos(var30.x - var4, var30.y - var6, var30.z - var8).color(var17, var18, var19, var20).endVertex();
         }
      }

      var1.draw();
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
      GL11.glLineWidth(1.0F);
   }

   private Vector3[] computeCornerVertices(Vector3 var1, Vector3 var2, Quaternion var3) {
      Vector3[] var4 = new Vector3[8];

      for (byte var5 = -1; var5 <= 1; var5 += 2) {
         for (byte var6 = -1; var6 <= 1; var6 += 2) {
            for (byte var7 = -1; var7 <= 1; var7 += 2) {
               int var8;
               if (var5 < 0) {
                  var8 = var6 < 0 ? (var7 < 0 ? 0 : 1) : (var7 < 0 ? 3 : 2);
               } else {
                  var8 = var6 < 0 ? (var7 < 0 ? 4 : 5) : (var7 < 0 ? 7 : 6);
               }

               Vector3 var9 = new Vector3(var2.x * var7, var2.y * var6, var2.z * var5);
               var4[var8] = var1.add(var3.rotateVector(var9));
            }
         }
      }

      return var4;
   }

   static {
      int[][] var10000 = new int[6][];
      int[] var10003 = new int[4];
      var10003[0] = 0;
      var10003[1] = 3;
      var10003[2] = 2;
      var10003[3] = 1;
      var10000[0] = var10003;
      int var10002 = 1;
      var10003 = new int[4];
      var10003[0] = 4;
      var10003[1] = 5;
      var10003[2] = 6;
      var10003[3] = 7;
      var10000[var10002] = var10003;
      var10002 = 2;
      var10003 = new int[4];
      var10003[0] = 0;
      var10003[1] = 4;
      var10003[2] = 7;
      var10003[3] = 3;
      var10000[var10002] = var10003;
      var10002 = 3;
      var10003 = new int[4];
      var10003[0] = 1;
      var10003[1] = 2;
      var10003[2] = 6;
      var10003[3] = 5;
      var10000[var10002] = var10003;
      var10003 = new int[4];
      var10003[0] = 0;
      var10003[1] = 1;
      var10003[2] = 5;
      var10003[3] = 4;
      var10000[4] = var10003;
      var10002 = 5;
      var10003 = new int[4];
      var10003[0] = 3;
      var10003[1] = 7;
      var10003[2] = 6;
      var10003[3] = 2;
      var10000[var10002] = var10003;
      FACE_VERTEX_INDICES = var10000;
      Vector3[] var0 = new Vector3[6];
      var0[0] = new Vector3(0.0, 0.0, -1.0);
      var0[1] = new Vector3(0.0, 0.0, 1.0);
      var0[2] = new Vector3(-1.0, 0.0, 0.0);
      var0[3] = new Vector3(1.0, 0.0, 0.0);
      var0[4] = new Vector3(0.0, -1.0, 0.0);
      var0[5] = new Vector3(0.0, 1.0, 0.0);
      FACE_NORMALS = var0;
      var10000 = new int[12][];
      var10002 = 0;
      var10003 = new int[2];
      var10003[0] = 0;
      var10003[1] = 1;
      var10000[var10002] = var10003;
      var10003 = new int[2];
      var10003[0] = 1;
      var10003[1] = 2;
      var10000[1] = var10003;
      var10003 = new int[]{2, 0};
      var10003[1] = 3;
      var10000[2] = var10003;
      var10002 = 3;
      var10003 = new int[2];
      var10003[0] = 3;
      var10003[1] = 0;
      var10000[var10002] = var10003;
      var10003 = new int[2];
      var10003[0] = 4;
      var10003[1] = 5;
      var10000[4] = var10003;
      var10003 = new int[2];
      var10003[0] = 5;
      var10003[1] = 6;
      var10000[5] = var10003;
      var10002 = 6;
      var10003 = new int[2];
      var10003[0] = 6;
      var10003[1] = 7;
      var10000[var10002] = var10003;
      var10002 = 7;
      var10003 = new int[2];
      var10003[0] = 7;
      var10003[1] = 4;
      var10000[var10002] = var10003;
      var10002 = 8;
      var10003 = new int[2];
      var10003[0] = 0;
      var10003[1] = 4;
      var10000[var10002] = var10003;
      var10002 = 9;
      var10003 = new int[]{1, 0};
      var10003[1] = 5;
      var10000[var10002] = var10003;
      var10002 = 10;
      var10003 = new int[2];
      var10003[0] = 2;
      var10003[1] = 6;
      var10000[var10002] = var10003;
      var10003 = new int[2];
      var10003[0] = 3;
      var10003[1] = 7;
      var10000[11] = var10003;
      EDGE_VERTEX_INDICES = var10000;
   }
}
