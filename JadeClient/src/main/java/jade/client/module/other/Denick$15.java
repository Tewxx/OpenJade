// Jade recovery: original class: jade.deps.eLz.wpmQGvzwn$15
package jade.client.module.other;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class Denick$15 {
   private final String nickKey;
   private String nick;
   private String bck2 = "";
   private final LinkedHashSet<String> xzmyek = new LinkedHashSet<>();
   private int finals = -1;
   private int beds = -1;
   private final Map<String, String> stats = new LinkedHashMap<>();
   private long Ivf;
   private final List<Denick$20> nameHistory = new ArrayList<>();
   private String drcyu = "";
   private String source = "";
   private long IudW;
   private long updatedAt;

   Denick$15(String var1) {
      this.nick = var1;
      this.nickKey = var1.toLowerCase(Locale.ROOT);
      long var2 = System.currentTimeMillis();
      this.IudW = var2;
      this.updatedAt = var2;
   }

   public static String getKillMessage(Denick$15 var0) {
      return var0.bck2;
   }

   public static int getFinals(Denick$15 var0) {
      return var0.finals;
   }

   public static int getBeds(Denick$15 var0) {
      return var0.beds;
   }

   public static String SlV3(Denick$15 var0) {
      return var0.drcyu;
   }

   public static String getNickKey(Denick$15 var0) {
      return var0.nickKey;
   }

   public static LinkedHashSet JMVOQSm(Denick$15 var0) {
      return var0.xzmyek;
   }

   public static String setKillMessage(Denick$15 var0, String var1) {
      return var0.bck2 = var1;
   }

   public static int setFinals(Denick$15 var0, int var1) {
      return var0.finals = var1;
   }

   public static int setBeds(Denick$15 var0, int var1) {
      return var0.beds = var1;
   }

   public static Map Zgkro(Denick$15 var0) {
      return var0.stats;
   }

   public static String setResolvedAs(Denick$15 var0, String var1) {
      return var0.drcyu = var1;
   }

   public static String setSource(Denick$15 var0, String var1) {
      return var0.source = var1;
   }

   public static long Xuug(Denick$15 var0, long var1) {
      return var0.IudW = var1;
   }

   public static long setUpdatedAt(Denick$15 var0, long var1) {
      return var0.updatedAt = var1;
   }

   public static long setObservedAt(Denick$15 var0, long var1) {
      return var0.Ivf = var1;
   }

   public static List getNameHistory(Denick$15 var0) {
      return var0.nameHistory;
   }

   public static String getNick(Denick$15 var0) {
      return var0.nick;
   }

   public static String getSource(Denick$15 var0) {
      return var0.source;
   }

   public static long getFirstSeen(Denick$15 var0) {
      return var0.IudW;
   }

   public static long getUpdatedAt(Denick$15 var0) {
      return var0.updatedAt;
   }

   public static long getObservedAt(Denick$15 var0) {
      return var0.Ivf;
   }
}
