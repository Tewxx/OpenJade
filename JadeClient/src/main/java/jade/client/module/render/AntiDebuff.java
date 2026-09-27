// Jade recovery: module: Anti Debuff (render); original class: jade.deps.eLz.RNmPaDd
package jade.client.module.render;

import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import net.minecraft.potion.Potion;

@ModuleInfo
public class AntiDebuff extends Module {
   public AntiDebuff() {
      super("Anti Debuff", Category.render);
   }

   public boolean blocksBlindness(Potion var1) {
      return this.isEnabled() && var1 == Potion.blindness;
   }

   public boolean blocksConfusion(Potion var1) {
      return this.isEnabled() && var1 == Potion.confusion;
   }
}
