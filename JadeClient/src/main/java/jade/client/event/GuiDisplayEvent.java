// Jade recovery: original class: jade.deps.eLz.dXJqmfk9cX
package jade.client.event;

import net.minecraft.client.gui.GuiScreen;

public class GuiDisplayEvent extends Event {
   public GuiScreen guiScreen;
   public boolean posy;

   public GuiDisplayEvent(GuiScreen var1, boolean var2) {
      this.posy = var2;
      this.guiScreen = var1;
   }
}
