// Jade recovery: original class: jade.deps.eLz.oGkiVIewGW
package jade.client.module.player.bednuker;

import net.minecraft.util.BlockPos;

public final class oGkiVIewGW {
   private static final int Tosj2 = 102;
   private BlockPos blockPos;
   private float ThH = -1.0F;

   public int getOutlineColor(BlockPos var1, boolean var2, float var3) {
      float var4 = this.updateFadeProgress(var1, var2, var3);
      int var5 = lerpColorChannel(247, 155, var4);
      int var6 = lerpColorChannel(82, 255, var4);
      int var7 = lerpColorChannel(82, 130, var4);
      return 1711276032 | var5 << 16 | var6 << 8 | var7;
   }

   public float updateFadeProgress(BlockPos var1, boolean var2, float var3) {
      float var4 = clamp01(var3);
      if (var1 == null) {
         this.reset();
         return 0.0F;
      } else if (!var2) {
         if (!var1.equals(this.blockPos)) {
            this.reset();
         }

         return 0.0F;
      } else {
         if (!var1.equals(this.blockPos) || this.ThH < 0.0F || var4 + 0.01F < this.ThH) {
            this.blockPos = var1;
            this.ThH = Math.min(var4, 0.99F);
         }

         float var5 = 1.0F - this.ThH;
         return var5 <= 0.001F ? 1.0F : clamp01((var4 - this.ThH) / var5);
      }
   }

   public void reset() {
      this.blockPos = null;
      this.ThH = -1.0F;
   }

   private static int lerpColorChannel(int var0, int var1, float var2) {
      return Math.max(0, Math.min(255, Math.round(var0 + (var1 - var0) * var2)));
   }

   private static float clamp01(float var0) {
      return Math.max(0.0F, Math.min(1.0F, var0));
   }
}
