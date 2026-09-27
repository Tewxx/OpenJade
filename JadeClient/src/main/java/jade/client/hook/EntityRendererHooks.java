// Jade recovery: original class: jade.deps.eLz.qVkfvy
package jade.client.hook;

import jade.client.Jade;
import jade.client.module.render.AntiDebuff;
import jade.client.module.render.NoCameraClip;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;

public final class EntityRendererHooks {
   private EntityRendererHooks() {
   }

   public static double getCameraRayTraceDistance(Vec3 var0, Vec3 var1) {
      NoCameraClip var2 = Jade.getModuleManager().getModule(NoCameraClip.class);
      return var2 != null && var2.isEnabled() ? 4.0 : var0.distanceTo(var1);
   }

   public static boolean SHst(EntityLivingBase var0, Potion var1, boolean var2) {
      AntiDebuff var3 = Jade.getModuleManager().getModule(AntiDebuff.class);
      return var3 == null || (var2 ? !var3.blocksBlindness(var1) : !var3.blocksConfusion(var1)) ? var0.isPotionActive(var1) : false;
   }
}
