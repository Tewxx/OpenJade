// Jade recovery: original class: jade.deps.eLz.PQ215i6wc$1
package jade.client.module.other.anticheat;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public final class AnticheatBlacklist$1 {
   private final UUID OoTg;
   private String tTik;
   private final Set<String> BEv = new LinkedHashSet<>();

   AnticheatBlacklist$1(UUID var1, String var2) {
      this.OoTg = var1;
      this.tTik = AnticheatBlacklist.sJmx(var2);
   }

   public static String setLastKnownName(AnticheatBlacklist$1 var0, String var1) {
      return var0.tTik = var1;
   }

   public static Set getFlags(AnticheatBlacklist$1 var0) {
      return var0.BEv;
   }

   public static String getLastKnownName(AnticheatBlacklist$1 var0) {
      return var0.tTik;
   }

   public static UUID getPlayerUuid(AnticheatBlacklist$1 var0) {
      return var0.OoTg;
   }
}
