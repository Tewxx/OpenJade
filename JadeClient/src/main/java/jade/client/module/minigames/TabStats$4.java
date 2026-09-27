// Jade recovery: original class: jade.deps.eLz.sOoP9qm$4
package jade.client.module.minigames;

import java.util.Locale;

public enum TabStats$4 {
   SKIN("Skin", false),
   TEAM("Team", false),
   RANK("Rank", false),
   STAR("Star", false),
   NAME("Name", false),
   WS("WS", false),
   FKDR("FKDR", false),
   WLR("WLR", false),
   FINALS("Finals", false),
   WINS("Wins", false),
   MONTHLY_FKDR("M.FKDR", false),
   FINALS_PER_STAR("Finals/Star", false),
   SESSION("Session", false),
   SNIPER("SS", false),
   HP("HP", false),
   TAGS("Tags", false),
   SW_SKIN("Skin", true),
   SW_RANK("Rank", true),
   SW_STAR("Star", true),
   SW_NAME("Name", true),
   SW_KDR("KDR", true),
   SW_WLR("WLR", true),
   SW_KILLS("Kills", true),
   SW_WINS("Wins", true),
   SW_SESSION("Session", true),
   SW_SNIPER("SS", true),
   SW_HP("HP", true),
   SW_TAGS("Tags", true);

   public final String label;
   public final boolean skywars;

   TabStats$4(String var3, boolean var4) {
      this.label = var3;
      this.skywars = var4;
   }

   public static TabStats$4 from(String var0) {
      try {
         return var0 == null ? null : valueOf(var0.toUpperCase(Locale.ROOT));
      } catch (Exception var2) {
         return null;
      }
   }

   static {
      TabStats$4[] var10000 = new TabStats$4[28];
      var10000[0] = SKIN;
      var10000[1] = TEAM;
      var10000[2] = RANK;
      var10000[3] = STAR;
      var10000[4] = NAME;
      var10000[5] = WS;
      var10000[6] = FKDR;
      var10000[7] = WLR;
      var10000[8] = FINALS;
      var10000[9] = WINS;
      var10000[10] = MONTHLY_FKDR;
      var10000[11] = FINALS_PER_STAR;
      var10000[12] = SESSION;
      var10000[13] = SNIPER;
      var10000[14] = HP;
      var10000[15] = TAGS;
      var10000[16] = SW_SKIN;
      var10000[17] = SW_RANK;
      var10000[18] = SW_STAR;
      var10000[19] = SW_NAME;
      var10000[20] = SW_KDR;
      var10000[21] = SW_WLR;
      var10000[22] = SW_KILLS;
      var10000[23] = SW_WINS;
      var10000[24] = SW_SESSION;
      var10000[25] = SW_SNIPER;
      var10000[26] = SW_HP;
      var10000[27] = SW_TAGS;
   }
}
