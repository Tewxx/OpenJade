// Jade recovery: original class: jade.deps.eLz.S62zxFb
package jade.client.event;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class RenderPlayerEvent extends Event {
   public final EntityPlayer entityPlayer;
   public final double OwWh;
   public final double y;
   public final double aCw;
   public final float partialTicks;
   public final ModelBiped modelBiped;

   public RenderPlayerEvent(EntityPlayer var1, double var2, double var4, double var6, float var8) {
      this(var1, var2, var4, var6, var8, null);
   }

   public RenderPlayerEvent(EntityPlayer var1, double var2, double var4, double var6, float var8, ModelBiped var9) {
      this.entityPlayer = var1;
      this.OwWh = var2;
      this.y = var4;
      this.aCw = var6;
      this.partialTicks = var8;
      this.modelBiped = var9;
   }
}
