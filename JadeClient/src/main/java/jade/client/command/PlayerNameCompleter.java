// Jade recovery: original class: jade.deps.eLz.XYSZrS13x
package jade.client.command;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public final class PlayerNameCompleter {
   private PlayerNameCompleter() {
   }

   public static List<String> completePlayerNames(String var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      if (var1.getNetHandler() == null) {
         return Collections.emptyList();
      } else {
         String var2 = var0 == null ? "" : var0.trim().toLowerCase(Locale.ENGLISH);
         String var3 = var1.thePlayer == null ? "" : var1.thePlayer.getName();
         LinkedHashMap var4 = new LinkedHashMap();

         for (NetworkPlayerInfo var6 : var1.getNetHandler().getPlayerInfoMap()) {
            if (var6 != null) {
               GameProfile var7 = var6.getGameProfile();
               String var8 = var7 == null ? null : var7.getName();
               if (var8 != null && !var8.isEmpty() && !var8.equalsIgnoreCase(var3) && var8.toLowerCase(Locale.ENGLISH).startsWith(var2)) {
                  var4.put(var8.toLowerCase(Locale.ENGLISH), var8);
               }
            }
         }

         ArrayList var9 = new ArrayList(var4.values());
         Collections.sort(var9, String.CASE_INSENSITIVE_ORDER);
         return var9;
      }
   }
}
