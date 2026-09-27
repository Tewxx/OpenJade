// Jade recovery: original class: jade.deps.eLz.BDFYb0yO8$0
package jade.client.module.render.blockesp;

import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ScreenProjector;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;

public final class BlockBoxRenderer$0 {
   private final double minX;
   private final double AVrcC9;
   private final double minZ;
   private final double lhTk;
   private final double maxY;
   private final double maxZ;

   BlockBoxRenderer$0(double var1, double var3, double var5, double var7, double var9, double var11) {
      this.minX = var1;
      this.AVrcC9 = var3;
      this.minZ = var5;
      this.lhTk = var7;
      this.maxY = var9;
      this.maxZ = var11;
   }

   public static BlockBoxRenderer$0 createViewerRelative(Minecraft var0, AxisAlignedBB var1) {
      double var2 = var0.getRenderManager().viewerPosX;
      double var4 = var0.getRenderManager().viewerPosY;
      double var6 = var0.getRenderManager().viewerPosZ;
      return new BlockBoxRenderer$0(var1.minX - var2, var1.minY - var4, var1.minZ - var6, var1.maxX - var2, var1.maxY - var4, var1.maxZ - var6);
   }

   public boolean projectAndDraw(ScreenProjector var1, ExternalRenderBuffer var2, int var3, float var4, boolean var5) {
      return var1.drawProjectedBox(this.minX, this.AVrcC9, this.minZ, this.lhTk, this.maxY, this.maxZ, var2, var3, var4, var5);
   }
}
