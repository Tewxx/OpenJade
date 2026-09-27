// Jade recovery: original class: jade.deps.eLz.MF7Ut7L
package jade.client.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class DrawSelectionBoxEvent extends Event {
   public final EntityPlayer entityPlayer;
   public final MovingObjectPosition movingObjectPosition;
   public final float AHs;

   public DrawSelectionBoxEvent(EntityPlayer var1, MovingObjectPosition var2, float var3) {
      this.entityPlayer = var1;
      this.movingObjectPosition = var2;
      this.AHs = var3;
   }
}
