// Jade recovery: original class: jade.deps.eLz.UW89UQ5E
package jade.client.common;

import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class ScissorStack {
   private final IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
   private final Deque<ScissorStack$1> ihNn = new ArrayDeque<>();

   public void qweq(int var1, int var2, int var3, int var4) {
      ScissorStack$1 var5 = this.captureCurrentClip();
      this.ihNn.push(var5);
      ScissorStack$0 var6 = new ScissorStack$0(var1, var2, var3, var4);
      ScissorStack$0 var7 = var5.josK ? var6.intersect(var5.Umzp) : var6;
      if (!var5.josK) {
         GL11.glEnable(3089);
      }

      GL11.glScissor(var7.x, var7.Eatd7, var7.pfi, var7.KYt);
   }

   public void popClip() {
      if (this.ihNn.isEmpty()) {
         throw new IllegalStateException("Unmatched GUI clip pop");
      } else {
         ScissorStack$1 var1 = this.ihNn.pop();
         if (var1.josK) {
            ScissorStack$0 var2 = var1.Umzp;
            GL11.glScissor(var2.x, var2.Eatd7, var2.pfi, var2.KYt);
         } else {
            GL11.glDisable(3089);
         }
      }
   }

   private ScissorStack$1 captureCurrentClip() {
      if (!GL11.glIsEnabled(3089)) {
         return new ScissorStack$1(false, null);
      } else {
         ((Buffer)this.intBuffer).clear();
         GL11.glGetInteger(3088, this.intBuffer);
         return new ScissorStack$1(true, new ScissorStack$0(this.intBuffer.get(0), this.intBuffer.get(1), this.intBuffer.get(2), this.intBuffer.get(3)));
      }
   }
}
