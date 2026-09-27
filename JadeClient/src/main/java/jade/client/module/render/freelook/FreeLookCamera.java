// Jade recovery: original class: jade.deps.eLz.Ge1iOkriRD
package jade.client.module.render.freelook;

import jade.mixin.impl.accessor.IAccessorMouseHelper;
import net.minecraft.client.Minecraft;

public final class FreeLookCamera {
   private FreeLookCamera() {
   }

   public static FreeLookCamera$1 applyFreeLookRotation(Minecraft var0, boolean var1, float var2, float var3, boolean var4, float var5) {
      if (var1 && var0.gameSettings.thirdPersonView != 0) {
         var0.mouseHelper.mouseXYChange();
         IAccessorMouseHelper var6 = (IAccessorMouseHelper)var0.mouseHelper;
         FreeLookMath$1 var7 = FreeLookMath.computeAngles(var2, var3, var6.getDeltaX(), var6.getDeltaY(), var0.gameSettings.mouseSensitivity);
         if (var4) {
            var0.gameSettings.fovSetting = var5;
         }

         return new FreeLookCamera$1(false, true, var7.yaw, var7.pitch);
      } else {
         return new FreeLookCamera$1(true, false, var2, var3);
      }
   }
}
