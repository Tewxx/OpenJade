// Jade recovery: original class: jade.deps.eLz.sOoP9qm$5
package jade.client.module.minigames;

import jade.deps.loader107.HypixelPlayerStats;
import java.util.UUID;
import net.minecraft.client.network.NetworkPlayerInfo;

public final class TabStats$5 {
   private final NetworkPlayerInfo networkPlayerInfo;
   private final UUID playerUuid;
   private final String Yhq;
   final HypixelPlayerStats statEntry;
   final float statValue;

   TabStats$5(NetworkPlayerInfo var1, UUID var2, String var3, HypixelPlayerStats var4, float var5) {
      this.networkPlayerInfo = var1;
      this.playerUuid = var2;
      this.Yhq = var3;
      this.statEntry = var4;
      this.statValue = var5;
   }

   String YPlE() {
      if (this.networkPlayerInfo != null && this.networkPlayerInfo.getPlayerTeam() != null) {
         String var1 = this.networkPlayerInfo.getPlayerTeam().getRegisteredName();
         return var1 != null && !var1.isEmpty() ? var1 : "-";
      } else {
         return "-";
      }
   }

   String getColoredName() {
      if (this.networkPlayerInfo != null && this.networkPlayerInfo.getPlayerTeam() != null) {
         String var1 = this.networkPlayerInfo.getPlayerTeam().getColorPrefix();
         return (var1 == null ? "" : var1) + this.Yhq;
      } else {
         return this.Yhq;
      }
   }

   public static HypixelPlayerStats getStatEntry(TabStats$5 var0) {
      return var0.statEntry;
   }

   public static UUID getPlayerUuid(TabStats$5 var0) {
      return var0.playerUuid;
   }

   public static String NJzB(TabStats$5 var0) {
      return var0.Yhq;
   }

   public static String getTeamName(TabStats$5 var0) {
      return var0.YPlE();
   }

   public static float rLaf(TabStats$5 var0) {
      return var0.statValue;
   }

   public static NetworkPlayerInfo getPlayerInfo(TabStats$5 var0) {
      return var0.networkPlayerInfo;
   }

   public static String LdeG2(TabStats$5 var0) {
      return var0.getColoredName();
   }
}
