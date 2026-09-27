// Jade recovery: original class: jade.deps.eLz.N1vLKkf$2
package jade.client.common;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

public final class RenderUtils$2 {
   private int guiScale;
   private final FloatBuffer modelViewMatrix = BufferUtils.createFloatBuffer(16);
   private final FloatBuffer projectionMatrix = BufferUtils.createFloatBuffer(16);
   private final IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
   private final FloatBuffer projectedCoords = BufferUtils.createFloatBuffer(3);

   public static int setGuiScale(RenderUtils$2 var0, int var1) {
      return var0.guiScale = var1;
   }

   public static FloatBuffer Aqnrs(RenderUtils$2 var0) {
      return var0.modelViewMatrix;
   }

   public static FloatBuffer getProjectionMatrix(RenderUtils$2 var0) {
      return var0.projectionMatrix;
   }

   public static IntBuffer getViewportBuffer(RenderUtils$2 var0) {
      return var0.intBuffer;
   }

   public static FloatBuffer getProjectedCoords(RenderUtils$2 var0) {
      return var0.projectedCoords;
   }

   public static int getGuiScale(RenderUtils$2 var0) {
      return var0.guiScale;
   }
}
