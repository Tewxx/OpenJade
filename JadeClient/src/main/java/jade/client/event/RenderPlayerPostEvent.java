// Jade recovery: original class: jade.deps.eLz.S62zxFb$0
package jade.client.event;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class RenderPlayerPostEvent extends RenderPlayerEvent {
   public RenderPlayerPostEvent(EntityPlayer var1, double var2, double var4, double var6, float var8) {
      super(var1, var2, var4, var6, var8);
   }

   public RenderPlayerPostEvent(EntityPlayer var1, double var2, double var4, double var6, float var8, ModelBiped var9) {
      super(var1, var2, var4, var6, var8, var9);
   }
}
