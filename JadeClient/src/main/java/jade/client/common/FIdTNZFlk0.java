// Jade recovery: original class: jade.deps.eLz.FIdTNZFlk0
package jade.client.common;

import jade.client.module.client.Settings;
import net.minecraft.client.Minecraft;

public final class FIdTNZFlk0 {
   public static float pKgo;
   public static float viewPitch;
   public static float viewYaw;
   public static float jIm;
   public static float[] SZSSn = new float[]{0.0F, 0.0F};
   public static Float[] Jt0;
   public static boolean zgG;

   private FIdTNZFlk0() {
   }

   public static void EbotM7(float var0, float var1) {
      Jt0 = new Float[]{var0, var1};
      zgG = true;
   }

   public static void applyHeadYaw(float var0) {
      niSo8(var0, jIm);
   }

   public static void niSo8(float var0, float var1) {
      Minecraft.getMinecraft().thePlayer.rotationYawHead = var0;
      if (Settings.rotateBody.isToggled() && Settings.fullBody.isToggled()) {
         Minecraft.getMinecraft().thePlayer.prevRenderYawOffset = var1;
         Minecraft.getMinecraft().thePlayer.renderYawOffset = var0;
      }
   }
}
