// Jade recovery: original class: jade.deps.eLz.EjTaZDS1W$4
package jade.client.command;

import jade.deps.gson.JsonObject;

public final class StatusCommand$4 {
   private final boolean online;
   private final String gameType;
   private final String mode;
   private final String mapName;

   StatusCommand$4(boolean var1, String var2, String var3, String var4) {
      this.online = var1;
      this.gameType = var2 == null ? "" : var2;
      this.mode = var3 == null ? "" : var3;
      this.mapName = var4 == null ? "" : var4;
   }

   private static StatusCommand$4 parseFromJson(JsonObject var0) {
      JsonObject var1 = StatusCommand.getJsonObject(var0, "session");
      return var1 == null
         ? null
         : new StatusCommand$4(StatusCommand.QRFfWw(var1, "online"), StatusCommand.vLs7(var1, "gameType"), StatusCommand.vLs7(var1, "mode"), StatusCommand.vLs7(var1, "map"));
   }

   private String WOlp() {
      if (!this.online) {
         return "&cOffline";
      } else if ("LOBBY".equalsIgnoreCase(this.mode)) {
         return "&bLobby";
      } else {
         String var1 = normalizeModeName(this.mode);
         if (!this.mapName.isEmpty()) {
            return "&b" + this.mapName + " &7" + var1;
         } else {
            return "BEDWARS".equalsIgnoreCase(this.gameType) ? "&b" + var1 : "&b" + (this.mode.isEmpty() ? "Unknown" : this.mode);
         }
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof StatusCommand$4)) {
         return false;
      } else {
         StatusCommand$4 var2 = (StatusCommand$4)var1;
         return this.online == var2.online && this.gameType.equals(var2.gameType) && this.mode.equals(var2.mode) && this.mapName.equals(var2.mapName);
      }
   }

   @Override
   public int hashCode() {
      int var1 = this.online ? 1 : 0;
      var1 = 31 * var1 + this.gameType.hashCode();
      var1 = 31 * var1 + this.mode.hashCode();
      return 31 * var1 + this.mapName.hashCode();
   }

   private static String normalizeModeName(String var0) {
      if ("BEDWARS_EIGHT_TWO".equalsIgnoreCase(var0)) {
         return "2s";
      } else if ("BEDWARS_EIGHT_ONE".equalsIgnoreCase(var0)) {
         return "solos";
      } else if ("BEDWARS_FOUR_THREE".equalsIgnoreCase(var0)) {
         return "3s";
      } else if ("BEDWARS_TWO_FOUR".equalsIgnoreCase(var0)) {
         return "4v4";
      } else if ("BEDWARS_FOUR_FOUR".equalsIgnoreCase(var0)) {
         return "4s";
      } else {
         return "LOBBY".equalsIgnoreCase(var0) ? "Lobby" : "dreams";
      }
   }

   public static StatusCommand$4 tIa58(JsonObject var0) {
      return parseFromJson(var0);
   }

   public static String getStatusLine(StatusCommand$4 var0) {
      return var0.WOlp();
   }
}
