// Jade recovery: original class: jade.deps.eLz.Ni2frUrL$1
package jade.client.common;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerListTracker$1 {
   private final String name;
   private volatile String prefix;
   private volatile String uyT;
   private volatile int colorIndex;
   private final Set<String> members = Collections.newSetFromMap(new ConcurrentHashMap<>());

   PlayerListTracker$1(String var1, String var2, String var3, int var4) {
      this.name = var1;
      this.prefix = var2;
      this.uyT = var3;
      this.colorIndex = var4;
   }

   public String getName() {
      return this.name;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String spQt() {
      return this.uyT;
   }

   public int getColorIndex() {
      return this.colorIndex;
   }

   public static String vNfi(PlayerListTracker$1 var0, String var1) {
      return var0.prefix = var1;
   }

   public static String wluE(PlayerListTracker$1 var0, String var1) {
      return var0.uyT = var1;
   }

   public static int setColorIndex(PlayerListTracker$1 var0, int var1) {
      return var0.colorIndex = var1;
   }

   public static String YTccBs(PlayerListTracker$1 var0) {
      return var0.name;
   }

   public static Set UdnKnqO(PlayerListTracker$1 var0) {
      return var0.members;
   }
}
