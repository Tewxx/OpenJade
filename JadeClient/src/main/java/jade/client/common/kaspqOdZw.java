// Jade recovery: original class: jade.deps.eLz.kaspqOdZw
package jade.client.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public final class kaspqOdZw {
   private kaspqOdZw() {
   }

   public static boolean pwB0(Minecraft var0, EntityPlayer var1) {
      for (NetworkPlayerInfo var3 : var0.getNetHandler().getPlayerInfoMap()) {
         if (var3.getGameProfile().equals(var1.getGameProfile())) {
            return true;
         }
      }

      return false;
   }

   public static List<NetworkPlayerInfo> getUniquePlayerInfos(Minecraft var0, boolean var1) {
      ArrayList var2 = new ArrayList(var0.getNetHandler().getPlayerInfoMap());
      HashSet var3 = new HashSet(var2);
      var2.clear();
      var2.addAll(var3);
      if (var1) {
         var2.remove(var0.getNetHandler().getPlayerInfo(var0.thePlayer.getUniqueID()));
      }

      return var2;
   }

   public static Vec3 YOOxcA0(Minecraft var0, double var1) {
      if (var0.theWorld != null && var0.thePlayer != null) {
         Vec3 var3 = null;
         double var4 = var1;

         for (EntityPlayer var7 : var0.theWorld.playerEntities) {
            if (var7 != var0.thePlayer && var0.getNetHandler() != null && var0.getNetHandler().getPlayerInfo(var7.getUniqueID()) != null) {
               double var8 = var7.posX - var0.thePlayer.posX;
               double var10 = var7.posY - var0.thePlayer.posY;
               double var12 = var7.posZ - var0.thePlayer.posZ;
               double var14 = var8 * var8 + var10 * var10 + var12 * var12;
               if (var14 < var4) {
                  var4 = var14;
                  var3 = new Vec3(var7.posX, var7.posY, var7.posZ);
               }
            }
         }

         return var3;
      } else {
         return null;
      }
   }
}
