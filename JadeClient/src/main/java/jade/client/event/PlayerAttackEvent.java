// Jade recovery: original class: jade.deps.eLz.NIHpSDXC8
package jade.client.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerAttackEvent extends Event {
   public final EntityPlayer entityPlayer;
   public final Entity entity;

   public PlayerAttackEvent(EntityPlayer var1, Entity var2) {
      this.entityPlayer = var1;
      this.entity = var2;
   }
}
