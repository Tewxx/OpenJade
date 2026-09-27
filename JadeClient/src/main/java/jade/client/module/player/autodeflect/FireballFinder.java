// Jade recovery: original class: jade.deps.eLz.Zmf4RIX0D
package jade.client.module.player.autodeflect;

import jade.client.common.ClientUtils;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;

public final class FireballFinder {
   private FireballFinder() {
   }

   public static EntityFireball findClosestFireball(Minecraft var0, Set<Entity> var1, double var2, float var4, boolean var5) {
      if (var5 && !var0.thePlayer.onGround) {
         return null;
      } else {
         double var6 = var2 * var2;

         for (Entity var9 : var0.theWorld.loadedEntityList) {
            if (var9 instanceof EntityFireball) {
               boolean var10 = var4 == 360.0F || ClientUtils.isLookingAtEntity(var4, var9);
               double var11 = var0.thePlayer.getDistanceSqToEntity(var9);
               if (DeflectConditions.isValidFireballTarget(true, var1.contains(var9), var11, var6, var10)) {
                  return (EntityFireball)var9;
               }
            }
         }

         return null;
      }
   }
}
