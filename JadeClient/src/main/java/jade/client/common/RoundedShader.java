// Jade recovery: original class: jade.deps.eLz.lhuNLhkzBU
package jade.client.common;

import jade.client.gui.ClickGui;
import jade.client.module.render.shared.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public final class RoundedShader {
   private RoundedShader() {
   }

   public static void Fzsi8(ShaderProgram var0, float var1, float var2, float var3, float var4, float var5, boolean var6, int var7) {
      beginBlendPass(true);
      var0.eBml();
      applyRectUniforms(var0, var1, var2, var3, var4, var5);
      var0.lyik("blur", var6 ? 1 : 0);
      setUniformColor(var0, "color", var7);
      yJdrG(var1, var2, var3, var4);
      endShaderPass(var0);
   }

   public static void drawRoundedGradient(ShaderProgram var0, float var1, float var2, float var3, float var4, float var5, int var6, int var7, int var8, int var9) {
      beginBlendPass(false);
      var0.eBml();
      applyRectUniforms(var0, var1, var2, var3, var4, var5);
      setUniformColor(var0, "color1", var7);
      setUniformColor(var0, "color2", var6);
      setUniformColor(var0, "color3", var9);
      setUniformColor(var0, "color4", var8);
      yJdrG(var1, var2, var3, var4);
      endShaderPass(var0);
   }

   public static void drawRoundedOutline(ShaderProgram var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7, int var8) {
      beginBlendPass(true);
      var0.eBml();
      applyRectUniforms(var0, var1, var2, var3, var4, var5);
      var0.setUniform("outlineThickness", var6 * new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor());
      setUniformColor(var0, "color", var7);
      setUniformColor(var0, "outlineColor", var8);
      float[] var9 = RectUtils.expandRectByPadding(var1, var2, var3, var4, var6);
      ShaderProgram.drawQuad(var9[0], var9[1], var9[2], var9[3]);
      endShaderPass(var0);
   }

   public static void drawRoundedTexturedRect(
      ShaderProgram var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10
   ) {
      beginBlendPass(false);
      var0.eBml();
      var0.lyik("textureIn", 0);
      applyRectUniforms(var0, var1, var2, var3, var4, var5);
      var0.setUniform("alpha", var6);
      var0.setUniform("texMin", var7, var8);
      var0.setUniform("texMax", var9, var10);
      float[] var11 = RectUtils.expandRect(var1, var2, var3, var4);
      ShaderProgram.drawTexturedQuad(var11[0], var11[1], var11[2], var11[3], var7, var8, var9, var10);
      endShaderPass(var0);
   }

   public static void drawRoundedRectWithCornerFlags(
      ShaderProgram var0,
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      int var6,
      boolean var7,
      boolean var8,
      boolean var9,
      boolean var10,
      boolean var11,
      boolean var12
   ) {
      renderRoundedRect(
         var0,
         var1,
         var2,
         var3,
         var4,
         var6,
         LYZER(var7, var8, var9, var10),
         new float[]{var7 ? var5 : 0.0F, var8 ? var5 : 0.0F, var9 ? var5 : 0.0F, var10 ? var5 : 0.0F},
         var5,
         var11,
         var12
      );
   }

   public static void drawRoundedRectPerCornerRadii(
      ShaderProgram var0, float var1, float var2, float var3, float var4, int var5, float var6, float var7, float var8, float var9, boolean var10, boolean var11
   ) {
      renderRoundedRect(
         var0, var1, var2, var3, var4, var5, LYZER(var9 > 0.0F, var8 > 0.0F, var7 > 0.0F, var6 > 0.0F), new float[]{var9, var8, var7, var6}, null, var10, var11
      );
   }

   private static void renderRoundedRect(
      ShaderProgram var0, float var1, float var2, float var3, float var4, int var5, float[] var6, float[] var7, Float var8, boolean var9, boolean var10
   ) {
      GL11.glPushMatrix();

      try {
         RenderUtils.lTbf();
         RenderUtils.setAlphaThreshold(0.0F);
         GL20.glUseProgram(var0.programId);
         var0.setUniform("u_size", var3, var4);
         if (var8 != null) {
            var0.setUniform("u_radius", var8);
         }

         setUniformColor(var0, "u_color", var5);
         var0.setUniform("u_edges", var6);
         var0.setUniform("u_corner_radii", var7);
         var0.setUniform("u_connected_edges", var9 ? 1.0F : 0.0F, var10 ? 1.0F : 0.0F);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         ShaderProgram.drawQuad(var1, var2, var3, var4);
         GlStateManager.disableBlend();
      } finally {
         GL20.glUseProgram(0);
         GlStateManager.disableBlend();
         restoreGlState();
         GL11.glPopMatrix();
      }
   }

   private static float[] LYZER(boolean var0, boolean var1, boolean var2, boolean var3) {
      return new float[]{var0 ? 1.0F : 0.0F, var1 ? 1.0F : 0.0F, var2 ? 1.0F : 0.0F, var3 ? 1.0F : 0.0F};
   }

   private static void applyRectUniforms(ShaderProgram var0, float var1, float var2, float var3, float var4, float var5) {
      Minecraft var6 = Minecraft.getMinecraft();
      float var7 = (float)(new ScaledResolution(var6).getScaleFactor() * ClickGui.getActiveRenderScale());
      float[] var8 = RectUtils.toScaledPosition(var1, var2, var4, var7, var6.displayHeight);
      var0.setUniform("location", var8[0], var8[1]);
      var0.setUniform("rectSize", var3 * var7, var4 * var7);
      var0.setUniform("radius", var5 * var7);
   }

   private static void setUniformColor(ShaderProgram var0, String var1, int var2) {
      var0.setUniform(var1, ArgbColorUtils.toRgbaArray(var2));
   }

   private static void yJdrG(float var0, float var1, float var2, float var3) {
      float[] var4 = RectUtils.expandRect(var0, var1, var2, var3);
      ShaderProgram.drawQuad(var4[0], var4[1], var4[2], var4[3]);
   }

   private static void beginBlendPass(boolean var0) {
      RenderUtils.lTbf();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      if (var0) {
         GL11.glBlendFunc(770, 771);
      }

      RenderUtils.setAlphaThreshold(0.0F);
   }

   private static void endShaderPass(ShaderProgram var0) {
      var0.unbindProgram();
      GlStateManager.disableBlend();
      restoreGlState();
   }

   private static void restoreGlState() {
      RenderUtils.setAlphaThreshold(10.0F);
      RenderUtils.lTbf();
   }
}
