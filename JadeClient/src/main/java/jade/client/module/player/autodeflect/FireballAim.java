// Jade recovery: original class: jade.deps.eLz.ou33Ik
package jade.client.module.player.autodeflect;

import jade.client.common.ClientUtils;
import jade.client.common.RotationUtils;
import jade.client.module.other.AntiBot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public final class FireballAim {
   private FireballAim() {
   }

   public static float[] computeAimAngles(Minecraft var0, EntityFireball var1, float var2, float var3) {
      Vec3 var4 = var0.thePlayer.getPositionEyes(1.0F);
      float var5 = var1.getCollisionBorderSize();
      AxisAlignedBB var6 = var1.getEntityBoundingBox().expand(var5, var5, var5);
      double var7 = var0.playerController.getBlockReachDistance();

      for (EntityPlayer var10 : getSortedPlayers(var0)) {
         float[] var11 = RotationUtils.anglesToCoordinates(var10.posX, var10.posY, var10.posZ, var2, var3);
         if (rayHitsBox(var4, var11, var6, var7)) {
            return var11;
         }
      }

      double var13 = (var6.minX + var6.maxX) * 0.5;
      double var14 = (var6.minZ + var6.maxZ) * 0.5;
      return RotationUtils.anglesToCoordinates(var13, var6.maxY, var14, var2, var3);
   }

   private static List<EntityPlayer> getSortedPlayers(final Minecraft var0) {
      ArrayList var1 = new ArrayList();

      for (EntityPlayer var3 : var0.theWorld.playerEntities) {
         if (var3 != var0.thePlayer && var3.deathTime == 0 && !ClientUtils.isFriend(var3) && !ClientUtils.isTeammate(var3) && !AntiBot.shouldHideEntity(var3)) {
            var1.add(var3);
         }
      }

      Collections.sort(var1, new Comparator<EntityPlayer>() {
         public int compare(EntityPlayer var1, EntityPlayer var2) {
            return Double.compare(var0.thePlayer.getDistanceSqToEntity(var1), var0.thePlayer.getDistanceSqToEntity(var2));
         }
      });
      return var1;
   }

   private static boolean rayHitsBox(Vec3 var0, float[] var1, AxisAlignedBB var2, double var3) {
      Vec3 var5 = ClientUtils.getLookVector(var1[0], var1[1]);
      Vec3 var6 = var0.addVector(var5.xCoord * var3, var5.yCoord * var3, var5.zCoord * var3);
      return var2.calculateIntercept(var0, var6) != null;
   }
}
