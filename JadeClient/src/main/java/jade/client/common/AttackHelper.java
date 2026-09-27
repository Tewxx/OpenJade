// Jade recovery: original class: jade.deps.eLz.PsUd8OATua
package jade.client.common;

import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;

public final class AttackHelper {
   private static final Method method = findCanRiderInteractMethod();

   private AttackHelper() {
   }

   private static Method findCanRiderInteractMethod() {
      try {
         return Entity.class.getMethod("canRiderInteract");
      } catch (ReflectiveOperationException var1) {
         return null;
      }
   }

   public static boolean invokeCanRiderInteract(Entity var0) {
      Method var1 = method;
      if (var0 != null && var1 != null) {
         try {
            Object var2 = var1.invoke(var0);
            return var2 instanceof Boolean && (Boolean)var2;
         } catch (ReflectiveOperationException var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   public static void performAttack(Minecraft var0, Entity var1, boolean var2, boolean var3) {
      if (var2) {
         var0.thePlayer.swingItem();
      } else {
         var0.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
      }

      var0.playerController.attackEntity(var0.thePlayer, var1);
   }

   public static void forceSwing(Minecraft var0) {
      int var1 = 6;
      if (var0.thePlayer.isPotionActive(Potion.digSpeed)) {
         var1 -= var0.thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1;
      } else if (var0.thePlayer.isPotionActive(Potion.digSlowdown)) {
         var1 += (var0.thePlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier() + 1) * 2;
      }

      if (!var0.thePlayer.isSwingInProgress || var0.thePlayer.swingProgressInt >= var1 / 2 || var0.thePlayer.swingProgressInt < 0) {
         var0.thePlayer.swingProgressInt = -1;
         var0.thePlayer.isSwingInProgress = true;
      }
   }
}
