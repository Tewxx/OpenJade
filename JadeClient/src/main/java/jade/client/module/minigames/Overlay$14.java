// Jade recovery: original class: jade.deps.eLz.Bz6LtyS$14
package jade.client.module.minigames;

import java.util.UUID;
import net.minecraft.client.network.NetworkPlayerInfo;

public final class Overlay$14 {
   private NetworkPlayerInfo networkPlayerInfo;
   private final UUID playerId;
   private String playerName;
   private final long lastSeenMillis;

   Overlay$14(NetworkPlayerInfo var1, UUID var2, String var3, long var4) {
      this.networkPlayerInfo = var1;
      this.playerId = var2;
      this.playerName = var3;
      this.lastSeenMillis = var4;
   }

   public static NetworkPlayerInfo getNetworkPlayerInfo(Overlay$14 var0) {
      return var0.networkPlayerInfo;
   }

   public static long csso(Overlay$14 var0) {
      return var0.lastSeenMillis;
   }

   public static String RXus(Overlay$14 var0) {
      return var0.playerName;
   }

   public static UUID bpdZ(Overlay$14 var0) {
      return var0.playerId;
   }

   public static NetworkPlayerInfo setNetworkPlayerInfo(Overlay$14 var0, NetworkPlayerInfo var1) {
      return var0.networkPlayerInfo = var1;
   }

   public static String setPlayerName(Overlay$14 var0, String var1) {
      return var0.playerName = var1;
   }
}
