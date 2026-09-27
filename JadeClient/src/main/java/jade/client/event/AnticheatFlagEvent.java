// Jade recovery: original class: jade.deps.eLz.go65iO5V
package jade.client.event;

import net.minecraft.entity.Entity;

public class AnticheatFlagEvent extends Event {
   public String checkName;
   public Entity entity;
   public int wnsTt;

   public AnticheatFlagEvent(String var1, Entity var2) {
      this(var1, var2, 1);
   }

   public AnticheatFlagEvent(String var1, Entity var2, int var3) {
      this.checkName = var1;
      this.entity = var2;
      this.wnsTt = var3;
   }
}
