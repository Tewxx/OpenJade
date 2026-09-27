// Jade recovery: original class: jade.deps.eLz.Bz6LtyS$10
package jade.client.module.minigames;

import jade.client.common.IFont;
import jade.deps.loader107.HypixelPlayerStats;

public final class Overlay$10 {
   final Overlay this$0;

   private final String ts1;
   private final String sBt;
   private final Overlay$18 columnType;

   Overlay$10(Overlay var1, String var2, String var3, Overlay$18 var4) {
      this.this$0 = var1;
      this.ts1 = var2;
      this.sBt = var3;
      this.columnType = var4;
   }

   private String buildHeaderText(int var1) {
      String var2 = (int)Overlay.getHeaderSetting(this.this$0).getInput() == 1 ? this.sBt : this.ts1;
      return this.columnType != Overlay$18.NAME && this.columnType != Overlay$18.SW_NAME ? var2 : var2 + " §7(" + var1 + ")";
   }

   private boolean isNonNameColumn() {
      return this.columnType != Overlay$18.NAME && this.columnType != Overlay$18.SW_NAME;
   }

   private float computeCenteredX(IFont var1, float var2, int var3, String var4) {
      return this.isNonNameColumn() ? var2 + Math.max(0, var3 - Overlay.measureTextWidth(this.this$0, var1, var4)) / 2.0F : var2;
   }

        private String buildCellText(jade.client.module.minigames.Overlay$16 var1_1) {
            if (var1_1.uuidMismatch && (this.columnType == jade.client.module.minigames.Overlay$18.RANK || this.columnType == jade.client.module.minigames.Overlay$18.SW_RANK)) {
                return jade.client.module.minigames.Overlay.zfM;
            }
            if (var1_1.DMr) {
                if (this.columnType == jade.client.module.minigames.Overlay$18.RANK || this.columnType == jade.client.module.minigames.Overlay$18.SW_RANK) {
                    return jade.client.module.minigames.Overlay.zfM;
                }
                if (this.this$0.isStatColumn(this.columnType)) {
                    return jade.client.module.minigames.Overlay.NICK_MARKER;
                }
            }
            switch (this.columnType) {
                case TEAM: {
                    return var1_1.buildTeamText();
                }
                case RANK: {
                    return var1_1.buildRankText();
                }
                case STAR: {
                    return this.this$0.WDkp(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.STAR));
                }
                case NAME: {
                    return this.this$0.qclLe(var1_1);
                }
                case WINSTREAK: {
                    return this.this$0.formatWinstreakValue(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.WINSTREAK));
                }
                case FKDR: {
                    return this.this$0.formatFkdrValue(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.FKDR));
                }
                case WLR: {
                    return this.this$0.formatWlrValue(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.WLR));
                }
                case FINALS: {
                    return this.this$0.formatFinalsValue(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.FINALS));
                }
                case WINS: {
                    return this.this$0.formatWinsValue(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.WINS));
                }
                case MONTHLY_FKDR: {
                    return this.this$0.formatFkdrValue(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.MONTHLY_FKDR));
                }
                case FINALS_PER_STAR: {
                    return this.this$0.formatFinalsPerStarValue(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.FINALS_PER_STAR));
                }
                case SW_RANK: {
                    return var1_1.buildRankText();
                }
                case SW_STAR: {
                    return this.this$0.lfQd(var1_1);
                }
                case SW_NAME: {
                    return this.this$0.qclLe(var1_1);
                }
                case SW_KDR: {
                    return this.this$0.UThNqd(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.SW_KDR));
                }
                case SW_WLR: {
                    return this.this$0.SUSe(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.SW_WLR));
                }
                case SW_KILLS: {
                    return this.this$0.formatStatNumber(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.SW_KILLS), 0);
                }
                case SW_WINS: {
                    return this.this$0.formatStatNumber(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.SW_WINS), 0);
                }
                case SW_SNIPER: {
                    return this.this$0.formatStatNumber(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.SW_SNIPER), 1);
                }
                case SESSION: {
                    return this.this$0.PCpc(var1_1);
                }
                case SNIPER: {
                    return this.this$0.formatStatNumber(var1_1.DIprcY(jade.client.module.minigames.Overlay$18.SNIPER), 1);
                }
                case TAGS: {
                    return this.this$0.tD01(var1_1) == null ? "..." : "-";
                }
                case HP: 
                case SW_HP: {
                    return var1_1.buildHealthText();
                }
            }
            return "";
        }

   private int computeCellColor(Overlay$16 var1) {
      if (!Overlay$16.hasUuidMismatch(var1) || this.columnType != Overlay$18.RANK && this.columnType != Overlay$18.SW_RANK) {
         if (Overlay$16.isDenicked(var1)) {
            if (this.columnType == Overlay$18.RANK || this.columnType == Overlay$18.SW_RANK) {
               return -43691;
            }

            if (Overlay.isStatColumnFor(this.this$0, this.columnType)) {
               return -5635926;
            }
         }

         if (this.columnType == Overlay$18.NAME || this.columnType == Overlay$18.SW_NAME) {
            return Overlay.getUsernameColorFor(this.this$0, var1);
         } else if (this.columnType == Overlay$18.TEAM) {
            return Overlay$16.getTeamColor(var1);
         } else if (this.columnType == Overlay$18.RANK || this.columnType == Overlay$18.SW_RANK) {
            return Overlay$16.getRankColor(var1);
         } else if ((this.columnType == Overlay$18.SNIPER || this.columnType == Overlay$18.SW_SNIPER)
            && HypixelPlayerStats.isFiniteNumber(Overlay$16.getStatValue(var1, this.columnType))
            && Overlay$16.getStatValue(var1, this.columnType) > 0.0) {
            return -43691;
         } else if (this.columnType == Overlay$18.TAGS && Overlay.getStatsForPlayer(this.this$0, var1) == null) {
            return -8947849;
         } else if (this.columnType != Overlay$18.HP && this.columnType != Overlay$18.SW_HP) {
            return Overlay$16.getStats(var1) == null && this.columnType != Overlay$18.SESSION ? -8947849 : Overlay.getTextColorSetting(this.this$0).getArgb();
         } else {
            return Overlay$16.NRUhJi(var1);
         }
      } else {
         return -43691;
      }
   }

   private int getDefaultCellColor() {
      return Overlay.getTextColorSetting(this.this$0).getArgb();
   }

   public static Overlay$18 getColumnType(Overlay$10 var0) {
      return var0.columnType;
   }

   public static String getColumnLabel(Overlay$10 var0) {
      return var0.ts1;
   }

   public static String getHeaderText(Overlay$10 var0, int var1) {
      return var0.buildHeaderText(var1);
   }

   public static String getCellText(Overlay$10 var0, Overlay$16 var1) {
      return var0.buildCellText(var1);
   }

   public static float centerColumnText(Overlay$10 var0, IFont var1, float var2, int var3, String var4) {
      return var0.computeCenteredX(var1, var2, var3, var4);
   }

   public static int mamN(Overlay$10 var0) {
      return var0.getDefaultCellColor();
   }

   public static int getCellColor(Overlay$10 var0, Overlay$16 var1) {
      return var0.computeCellColor(var1);
   }
}
