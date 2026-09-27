// Jade recovery: original class: jade.deps.eLz.QUvBDhQCY
package jade.client.hook;

import jade.client.Jade;
import jade.client.module.render.FreeLook;

public final class FreeLookHook {
   private FreeLookHook() {
   }

   public static boolean QNLy() {
      FreeLook var0 = Jade.getModuleManager().getModule(FreeLook.class);
      return var0 != null && var0.isEnabled() && FreeLook.DnH;
   }

   public static float getFreeLookPitch() {
      return FreeLook.savedPitch;
   }

   public static float wusrdWx() {
      return FreeLook.savedYaw;
   }
}
