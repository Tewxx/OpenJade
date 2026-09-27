// Jade recovery: original class: jade.deps.eLz.Plc3qt
package jade.client.event;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityJoinWorldEvent extends Event {
   public final Entity entity;
   public final World world;

   public EntityJoinWorldEvent(Entity var1, World var2) {
      this.entity = var1;
      this.world = var2;
   }
}
