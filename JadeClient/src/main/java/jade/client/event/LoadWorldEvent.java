// Jade recovery: original class: jade.deps.eLz.yRGNyb
package jade.client.event;

import net.minecraft.world.World;

public class LoadWorldEvent extends Event {
   public final World world;

   public LoadWorldEvent(World var1) {
      this.world = var1;
   }
}
