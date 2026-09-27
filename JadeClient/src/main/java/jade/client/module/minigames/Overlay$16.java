// Jade recovery: original class: jade.deps.eLz.Bz6LtyS$16
package jade.client.module.minigames;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.module.other.Denick;

import jade.deps.loader107.HypixelPlayerStats;
import java.util.Locale;
import java.util.UUID;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;

public final class Overlay$16 {
   final Overlay this$0;

   private final NetworkPlayerInfo playerInfo;
   private final UUID playerUuid;
   private final String LHt7;
   private final long lastSeenMillis;
   private final HypixelPlayerStats eX1;
   private final int tabListIndex;
   final boolean DMr;
   final boolean uuidMismatch;

   Overlay$16(Overlay var1, NetworkPlayerInfo var2, UUID var3, String var4, long var5, HypixelPlayerStats var7, int var8) {
      this.this$0 = var1;
      this.playerInfo = var2;
      this.playerUuid = var3;
      this.LHt7 = var4;
      this.lastSeenMillis = var5;
      this.eX1 = var7;
      this.tabListIndex = var8;
      this.uuidMismatch = ((var2 != null && var2.getGameProfile() != null && !var3.equals(var2.getGameProfile().getId())
         ? 1
         : 0) != 0);
      this.DMr = ((var2 != null
            && var3.equals(var2.getGameProfile().getId())
            && Jade.getModuleManager().getModule(Denick.class) != null
            && Jade.getModuleManager().getModule(Denick.class).shouldShowInTab(var2)
         ? 1
         : 0) != 0);
   }

   String buildRankText() {
      return this.eX1 == null ? "-" : Overlay.orDash(this.this$0, this.eX1.getRankPrefix());
   }

   String buildTeamText() {
      ScorePlayerTeam var1 = this.playerInfo == null ? null : this.playerInfo.getPlayerTeam();
      String var2 = Overlay.getLastColorName(this.this$0, var1 == null ? "" : var1.getColorPrefix());
      if (!var2.isEmpty()) {
         return var2;
      } else {
         String var3 = var1 == null ? "" : var1.getRegisteredName();
         if (var3 != null && !var3.isEmpty()) {
            return var3.length() > 14 ? var3.substring(0, 14) : var3;
         } else {
            return "-";
         }
      }
   }

   private int getTeamColorCode() {
      ScorePlayerTeam var1 = this.playerInfo == null ? null : this.playerInfo.getPlayerTeam();
      int var2 = Overlay.TUEd6(this.this$0, var1 == null ? "" : var1.getColorPrefix());
      return var2 == -1 ? Overlay.getTextColorSetting(this.this$0).getArgb() : var2;
   }

   private int getRankColorCode() {
      int var1 = Overlay.TUEd6(this.this$0, this.buildRankText());
      if (var1 != -1) {
         return var1;
      } else {
         String var2 = EnumChatFormatting.getTextWithoutFormattingCodes(this.buildRankText());
         if (var2 != null && !var2.trim().isEmpty() && !"-".equals(var2.trim())) {
            if (var2 != null && var2.toUpperCase(Locale.ROOT).contains("VIP")) {
               return -11141291;
            } else {
               return var2 != null && var2.toUpperCase(Locale.ROOT).contains("MVP") ? -11141121 : -15314;
            }
         } else {
            return -5592406;
         }
      }
   }

        double DIprcY(jade.client.module.minigames.Overlay$18 var1_1) {
            if (var1_1 == jade.client.module.minigames.Overlay$18.SNIPER || var1_1 == jade.client.module.minigames.Overlay$18.SW_SNIPER) {
                double d = this.eX1 == null ? Double.NaN : this.eX1.getSniperScore();
                jade.client.module.minigames.overlay.StatsFetcher$2 var4_3 = this.this$0.tD01(this);
                if (var4_3 != null && var4_3.NhWet()) {
                    if (var4_3.rhWdy5()) {
                        return var4_3.getScoreValue();
                    }
                    return (HypixelPlayerStats.isFiniteNumber(d) ? d : 0.0) + var4_3.getScoreValue();
                }
                return d;
            }
            if (this.eX1 == null) {
                return Double.NaN;
            }
            switch (var1_1) {
                case STAR: {
                    return this.eX1.getBedwarsLevel();
                }
                case WINSTREAK: {
                    return this.eX1.getBedwarsWinstreak();
                }
                case FKDR: {
                    return this.eX1.getBedwarsFkdr();
                }
                case WLR: {
                    return this.eX1.getBedwarsWlr();
                }
                case FINALS: {
                    return this.eX1.getBedwarsFinalKills();
                }
                case WINS: {
                    return this.eX1.getBedwarsWins();
                }
                case MONTHLY_FKDR: {
                    return this.eX1.getBedwarsMonthlyFkdr();
                }
                case FINALS_PER_STAR: {
                    return this.eX1.getFinalKillsPerStar();
                }
                case SW_STAR: {
                    return this.eX1.getSkyWarsLevel();
                }
                case SW_KDR: {
                    return this.eX1.getSkyWarsKdr();
                }
                case SW_WLR: {
                    return this.eX1.getSkyWarsWlr();
                }
                case SW_KILLS: {
                    return jade.client.module.minigames.tabstats.SkywarsStatParser.oZnyDae(this.eX1.getDisplayTag());
                }
                case SW_WINS: {
                    return jade.client.module.minigames.tabstats.SkywarsStatParser.parseWins(this.eX1.getDisplayTag());
                }
            }
            return Double.NaN;
        }

   private float getHealth() {
      if (!ClientUtils.isInWorld()) {
         return Float.NaN;
      } else {
         EntityPlayer var1 = Overlay.getMc().theWorld.getPlayerEntityByUUID(this.playerUuid);
         return var1 == null ? Float.NaN : var1.getHealth();
      }
   }

   String buildHealthText() {
      float var1 = this.getHealth();
      return Float.isNaN(var1) ? "-" : Integer.toString(Math.max(0, Math.round(var1)));
   }

   private int getHealthColor() {
      float var1 = this.getHealth();
      if (Float.isNaN(var1)) {
         return -8947849;
      } else if (var1 >= 20.0F) {
         return -16733696;
      } else if (var1 >= 15.0F) {
         return -11141291;
      } else if (var1 >= 10.0F) {
         return -171;
      } else {
         return var1 >= 5.0F ? -22016 : -43691;
      }
   }

   private boolean isSniperStatHigh(boolean var1) {
      if (this.DMr) {
         return false;
      } else {
         double var2 = this.DIprcY(var1 ? Overlay$18.SW_SNIPER : Overlay$18.SNIPER);
         return HypixelPlayerStats.isFiniteNumber(var2) && var2 >= 500.0;
      }
   }

   public static HypixelPlayerStats getStats(Overlay$16 var0) {
      return var0.eX1;
   }

   public static UUID RlnF2(Overlay$16 var0) {
      return var0.playerUuid;
   }

   public static String tFvn63(Overlay$16 var0) {
      return var0.LHt7;
   }

   public static NetworkPlayerInfo getPlayerInfo(Overlay$16 var0) {
      return var0.playerInfo;
   }

   public static boolean TkHi8(Overlay$16 var0, boolean var1) {
      return var0.isSniperStatHigh(var1);
   }

   public static String getTeamText(Overlay$16 var0) {
      return var0.buildTeamText();
   }

   public static String getRankText(Overlay$16 var0) {
      return var0.buildRankText();
   }

   public static int IxdltfE(Overlay$16 var0) {
      return var0.tabListIndex;
   }

   public static double getStatValue(Overlay$16 var0, Overlay$18 var1) {
      return var0.DIprcY(var1);
   }

   public static int getRankColor(Overlay$16 var0) {
      return var0.getRankColorCode();
   }

   public static int getTeamColor(Overlay$16 var0) {
      return var0.getTeamColorCode();
   }

   public static boolean hasUuidMismatch(Overlay$16 var0) {
      return var0.uuidMismatch;
   }

   public static boolean isDenicked(Overlay$16 var0) {
      return var0.DMr;
   }

   public static String getHealthText(Overlay$16 var0) {
      return var0.buildHealthText();
   }

   public static int NRUhJi(Overlay$16 var0) {
      return var0.getHealthColor();
   }
}
