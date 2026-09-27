// Jade recovery: original class: jade.deps.eLz.nE3ikJ
package jade.client.common;

import com.mojang.authlib.GameProfile;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public final class PlayerInfoIndex {
   private PlayerInfoIndex() {
   }

   public static Map<String, NetworkPlayerInfo> HXMtc() {
      HashMap var0 = new HashMap();
      Minecraft var1 = Minecraft.getMinecraft();
      if (var1.getNetHandler() == null) {
         return var0;
      } else {
         for (NetworkPlayerInfo var3 : var1.getNetHandler().getPlayerInfoMap()) {
            if (var3 != null) {
               GameProfile var4 = var3.getGameProfile();
               if (var4 != null && var4.getName() != null) {
                  var0.put(var4.getName().toLowerCase(), var3);
               }
            }
         }

         return var0;
      }
   }
}
