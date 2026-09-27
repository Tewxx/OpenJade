// Jade recovery: original class: jade.deps.eLz.VOFwCP0O0
package jade.client.common;

import java.util.EnumMap;
import java.util.Map;

public final class DuelsStatKeys {
   private static final Map<PlayerApi$1, String[]> vgZ = new EnumMap<>(PlayerApi$1.class);

   private DuelsStatKeys() {
   }

   public static String[] getStatKeys(PlayerApi$1 var0) {
      return (String[])vgZ.get(var0).clone();
   }

   private static void registerStatKeys(PlayerApi$1 var0, String var1, String var2, String var3) {
      vgZ.put(var0, new String[]{var1, var2, var3});
   }

   static {
      registerStatKeys(PlayerApi$1.OVERALL, "wins", "losses", "current_winstreak");
      registerStatKeys(PlayerApi$1.BRIDGE, "bridge_duel_wins", "bridge_duel_losses", "current_winstreak_mode_bridge_duel");
      registerStatKeys(PlayerApi$1.UHC, "uhc_duel_wins", "uhc_duel_losses", "current_winstreak_mode_uhc_duel");
      registerStatKeys(PlayerApi$1.SKYWARS, "sw_duel_wins", "sw_duel_losses", "current_winstreak_mode_sw_duel");
      registerStatKeys(PlayerApi$1.CLASSIC, "classic_duel_wins", "classic_duel_losses", "current_winstreak_mode_classic_duel");
      registerStatKeys(PlayerApi$1.SUMO, "sumo_duel_wins", "sumo_duel_losses", "current_winstreak_mode_sumo_duel");
      registerStatKeys(PlayerApi$1.OP, "op_duel_wins", "op_duel_losses", "current_winstreak_mode_op_duel");
   }
}
