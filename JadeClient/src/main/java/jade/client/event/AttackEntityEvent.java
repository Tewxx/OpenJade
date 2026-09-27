// Jade recovery: original class: jade.deps.eLz.cpR7oa
package jade.client.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AttackEntityEvent extends Event {
   public Entity entity;
   public EntityPlayer entityPlayer;
   public boolean isPreAttack;

   public AttackEntityEvent(Entity var1, EntityPlayer var2, boolean var3) {
      this.entityPlayer = var2;
      this.isPreAttack = var3;
      this.entity = var1;
   }
}
