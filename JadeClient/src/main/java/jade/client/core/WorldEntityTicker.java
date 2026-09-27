// Jade recovery: original class: jade.deps.eLz.VyS2pS
package jade.client.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

public final class WorldEntityTicker {
   private WorldEntityTicker() {
   }

   public static void tickWorldEntities(Minecraft var0) {
      tickWeatherEffects(var0);
      EntityPlayerSP var1 = var0.thePlayer;

      for (Entity var3 : var0.theWorld.loadedEntityList) {
         if (gjbV(var3, var1)) {
            PKXn(var3);

            try {
               var0.theWorld.updateEntityWithOptionalForce(var3, true);
            } catch (Throwable var5) {
            }
         }
      }
   }

   public static void updateLastTickState(EntityPlayerSP var0) {
      var0.lastTickPosX = var0.posX;
      var0.lastTickPosY = var0.posY;
      var0.lastTickPosZ = var0.posZ;
      var0.prevRotationYaw = var0.rotationYaw;
      var0.prevRotationPitch = var0.rotationPitch;
      var0.prevRotationYawHead = var0.rotationYawHead;
      var0.prevRenderYawOffset = var0.renderYawOffset;
      var0.prevLimbSwingAmount = var0.limbSwingAmount;
      var0.prevSwingProgress = var0.swingProgress;
      var0.prevCameraPitch = var0.cameraPitch;
   }

   private static void tickWeatherEffects(Minecraft var0) {
      int var1 = 0;

      while (var1 < var0.theWorld.weatherEffects.size()) {
         Entity var2 = (Entity)var0.theWorld.weatherEffects.get(var1);

         try {
            var2.ticksExisted++;
            var2.onUpdate();
         } catch (Throwable var4) {
         }

         if (var2.isDead) {
            var0.theWorld.weatherEffects.remove(var1);
         } else {
            var1++;
         }
      }
   }

   private static boolean gjbV(Entity var0, EntityPlayerSP var1) {
      return var0 != null && !var0.isDead && var0 != var1
         ? var0.ridingEntity == null || var0.ridingEntity.isDead || var0.ridingEntity.riddenByEntity != var0
         : false;
   }

   private static void PKXn(Entity var0) {
      if (var0.ridingEntity != null) {
         var0.ridingEntity.riddenByEntity = null;
         var0.ridingEntity = null;
      }
   }
}
