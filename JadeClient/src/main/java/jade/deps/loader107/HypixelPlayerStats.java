package jade.deps.loader107;

import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

public final class HypixelPlayerStats {
   private static volatile HypixelPlayerStats$0 currentMode = HypixelPlayerStats$0.OVERALL;
   private static final String SKY_WARS_LEVEL_BEGIN_MARKER = null;
   private static final String SKY_WARS_LEVEL_END_MARKER = null;
   private static final String SKY_WARS_KILLS_BEGIN_MARKER = null;
   private static final String SKY_WARS_KILLS_END_MARKER = null;
   private static final String SKY_WARS_WINS_BEGIN_MARKER = null;
   private static final String SKY_WARS_WINS_END_MARKER = null;
   private final String rankPrefix;
   private final String displayTag;
   private final String customTag;
   private final double bedwarsLevel;
   private final double bedwarsWinstreak;
   private final double bedwarsFkdr;
   private final double bedwarsWlr;
   private final double bedwarsFinalKills;
   private final double bedwarsWins;
   private final double bedwarsMonthlyFkdr;
   private final double skyWarsLevel;
   private final double skyWarsKdr;
   private final double skyWarsWlr;
   private final double sniperScore;
   private final double lastLogout;
   private final List<String> favorites;

   private HypixelPlayerStats(
      String var1,
      String var2,
      String var3,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      double var14,
      double var16,
      double var18,
      double var20,
      double var22,
      double var24,
      double var26,
      List<String> var28
   ) {
      this.rankPrefix = normalizeColorCodes(var1);
      this.displayTag = normalizeColorCodes(var2);
      this.customTag = normalizeColorCodes(var3);
      this.bedwarsLevel = var4;
      this.bedwarsWinstreak = var6;
      this.bedwarsFkdr = var8;
      this.bedwarsWlr = var10;
      this.bedwarsFinalKills = var12;
      this.bedwarsWins = var14;
      this.bedwarsMonthlyFkdr = var16;
      this.skyWarsLevel = var18;
      this.skyWarsKdr = var20;
      this.skyWarsWlr = var22;
      this.sniperScore = var24;
      this.lastLogout = var26;
      this.favorites = var28 == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(var28));
   }

   public static HypixelPlayerStats fromJson(JsonObject var0) {
      byte var29 = 0;

      while (true) {
         switch (var29) {
            case 0:

               var29 = 1;
               break;
            case 1:
               var29 = 2;
               break;
            default:
               JsonObject var1 = getJsonObjectOrEmpty(var0, "hypixel");
               JsonObject var2 = getJsonObjectOrEmpty(var0, "cubelify");
               JsonObject var3 = getObjectByPath(var1, "data");
               if (var3 == null) {
                  var3 = findObjectByKey(var1, "player");
               }

               if (var3 == null) {
                  var3 = var1;
               }

               JsonObject var4 = getObjectByPath(
                  var3, "stats.Bedwars"
               );
               if (var4 == null) {
                  var4 = findObjectByKey(var3, "Bedwars");
               }

               JsonObject var5 = getObjectByPath(
                  var3, "stats.SkyWars"
               );
               if (var5 == null) {
                  var5 = findObjectByKey(var3, "SkyWars");
               }

               JsonObject var6 = getObjectByPath(
                  var2, "data.minecraftUser"
               );
               if (var6 == null) {
                  var6 = findObjectByKey(var2, "minecraftUser");
               }

               JsonObject var7 = findObjectByKey(var6, "user");
               String var8 = getFirstString(var3, new String[]{"guild.tag", "guildTag", "guild_tag", "tag"});
               String var9 = getFirstString(var5, new String[]{"levelFormattedWithBrackets"});
               double var10 = getFirstDouble(var5, new String[]{"kills"});
               double var12 = getFirstDouble(var5, new String[]{"deaths"});
               double var14 = getFirstDouble(var5, new String[]{"wins"});
               double var16 = getFirstDouble(var5, new String[]{"losses"});
               var8 = injectBetweenMarkers(
                  var8,
                  "__JADE_SW_LEVEL_BEGIN__",
                  "__JADE_SW_LEVEL_END__",
                  var9,
                  128
               );
               var8 = injectStatPlaceholder(
                  var8,
                  "__JADE_SW_KILLS_BEGIN__",
                  "__JADE_SW_KILLS_END__",
                  var10
               );
               var8 = injectStatPlaceholder(
                  var8,
                  "__JADE_SW_WINS_BEGIN__",
                  "__JADE_SW_WINS_END__",
                  var14
               );
               HypixelPlayerStats$0 var18 = currentMode;
               double var19 = readModeStat(var4, var18, new String[]{"final_kills_bedwars", "finalKills", "final_kills"});
               double var21 = readModeStat(var4, var18, new String[]{"final_deaths_bedwars", "finalDeaths", "final_deaths"});
               double var23 = readModeStat(var4, var18, new String[]{"wins_bedwars", "wins"});
               double var25 = readModeStat(var4, var18, new String[]{"losses_bedwars", "losses"});
               double var27 = getFirstDouble(var3, new String[]{"achievements.bedwars_level", "bedwars_level"});
               if (!isFiniteNumber(var27)) {
                  var27 = bedwarsExpToLevel(getFirstDouble(var4, new String[]{"Experience", "experience"}));
               }

               return new HypixelPlayerStats(
                  readRankTag(var3),
                  var8,
                  readCustomTag(var7),
                  var27,
                  getFirstDouble(var4, new String[]{"winstreak", "win_streak", "winstreak_bedwars"}),
                  safeRatio(var19, var21),
                  safeRatio(var23, var25),
                  var19,
                  var23,
                  readMonthlyFkdr(var3, var4, var1, var18),
                  parseSkyWarsLevel(var5),
                  safeRatio(var10, var12),
                  safeRatio(var14, var16),
                  getFirstDouble(var6, new String[]{"totalReportsWeight", "sniperScore", "sniper_score"}),
                  getFirstDouble(var3, new String[]{"lastLogout"}),
                  readFavorites(var4, var1)
               );
         }
      }
   }

   public static HypixelPlayerStats$0 getCurrentMode() {
      byte var0 = 0;

      while (true) {
         switch (var0) {
            case 0:

               var0 = 1;
               break;
            case 1:
               var0 = 2;
               break;
            default:
               return currentMode;
         }
      }
   }

   public static void setCurrentMode(HypixelPlayerStats$0 var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               currentMode = var0 == null ? HypixelPlayerStats$0.OVERALL : var0;
               return;
         }
      }
   }

   public static HypixelPlayerStats$0 parseMode(String var0) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               if (var0 == null) {
                  return null;
               } else {
                  String var1 = var0.trim().toLowerCase();
                  if ("1s".equals(var1)
                     || "solo".equals(var1)
                     || "solos".equals(var1)) {
                     return HypixelPlayerStats$0.SOLO;
                  } else if ("2s".equals(var1)
                     || "doubles".equals(var1)) {
                     return HypixelPlayerStats$0.DOUBLES;
                  } else if ("3s".equals(var1)
                     || "threes".equals(var1)) {
                     return HypixelPlayerStats$0.THREES;
                  } else if ("4s".equals(var1)
                     || "fours".equals(var1)) {
                     return HypixelPlayerStats$0.FOURS;
                  } else if ("4v4".equals(var1)) {
                     return HypixelPlayerStats$0.FOUR_V_FOUR;
                  } else if ("overall".equals(var1)) {
                     return HypixelPlayerStats$0.OVERALL;
                  } else {
                     return "core".equals(var1)
                        ? HypixelPlayerStats$0.CORE
                        : null;
                  }
               }
         }
      }
   }

   public static HypixelPlayerStats createPlaceholderMvpPlusPlusStats() {
      byte var0 = 0;

      while (true) {
         switch (var0) {
            case 0:

               var0 = 1;
               break;
            case 1:
               var0 = 2;
               break;
            default:
               return new HypixelPlayerStats(
                  "\u00a7b[MVP\u00a7c+\u00a7b]",
                  "[JADE]__JADE_SW_LEVEL_BEGIN__§6[§62§64§6✯§6]§r __JADE_SW_LEVEL_END____JADE_SW_KILLS_BEGIN__18400__JADE_SW_KILLS_END____JADE_SW_WINS_BEGIN__2100__JADE_SW_WINS_END__",
                  "",
                  487.0,
                  38.0,
                  8.42,
                  2.36,
                  17650.0,
                  5290.0,
                  11.74,
                  24.0,
                  3.68,
                  0.42,
                  42.0,
                  System.currentTimeMillis() - 405000L,
                  Collections.emptyList()
               );
         }
      }
   }

   public static HypixelPlayerStats createPlaceholderVipPlusStats() {
      byte var0 = 0;

      while (true) {
         switch (var0) {
            case 0:

               var0 = 1;
               break;
            case 1:
               var0 = 2;
               break;
            default:
               return new HypixelPlayerStats(
                  "\u00a7a[VIP\u00a76+\u00a7a]",
                  "__JADE_SW_LEVEL_BEGIN__\u00a77[\u00a777\u00a77\u272f\u00a77]\u00a7r __JADE_SW_LEVEL_END____JADE_SW_KILLS_BEGIN__340__JADE_SW_KILLS_END____JADE_SW_WINS_BEGIN__30__JADE_SW_WINS_END__",
                  "",
                  219.0,
                  6.0,
                  3.27,
                  1.41,
                  3840.0,
                  1440.0,
                  4.18,
                  7.0,
                  0.72,
                  0.06,
                  0.0,
                  System.currentTimeMillis() - 1720000L,
                  Collections.emptyList()
               );
         }
      }
   }

   public String getRankPrefix() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.rankPrefix;
         }
      }
   }

   public String getDisplayTag() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.displayTag;
         }
      }
   }

   public String getCustomTag() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.customTag;
         }
      }
   }

   public double getBedwarsLevel() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.bedwarsLevel;
         }
      }
   }

   public double getBedwarsWinstreak() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.bedwarsWinstreak;
         }
      }
   }

   public double getBedwarsFkdr() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.bedwarsFkdr;
         }
      }
   }

   public double getBedwarsWlr() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.bedwarsWlr;
         }
      }
   }

   public double getBedwarsFinalKills() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.bedwarsFinalKills;
         }
      }
   }

   public double getBedwarsWins() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.bedwarsWins;
         }
      }
   }

   public double getBedwarsMonthlyFkdr() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.bedwarsMonthlyFkdr;
         }
      }
   }

   public double getSkyWarsLevel() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.skyWarsLevel;
         }
      }
   }

   public double getSkyWarsKdr() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.skyWarsKdr;
         }
      }
   }

   public double getSkyWarsWlr() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.skyWarsWlr;
         }
      }
   }

   public double getFinalKillsPerStar() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return isFiniteNumber(this.bedwarsFinalKills)
                     && isFiniteNumber(this.bedwarsLevel)
                     && this.bedwarsLevel > 0.0
                  ? this.bedwarsFinalKills / this.bedwarsLevel
                  : Double.NaN;
         }
      }
   }

   public double getSniperScore() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.sniperScore;
         }
      }
   }

   public double getLastLogout() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.lastLogout;
         }
      }
   }

   public List<String> getFavorites() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return this.favorites;
         }
      }
   }

   private static List<String> readFavorites(JsonObject var0, JsonObject var1) {
      byte var3 = 0;

      while (true) {
         switch (var3) {
            case 0:

               var3 = 1;
               break;
            case 1:
               var3 = 2;
               break;
            default:
               JsonElement var2 = findFirstElement(var0, new String[]{"favourites_2", "favorites_2"});
               return var2 != null
                  ? toStringList(var2)
                  : toStringList(findElementRecursively(var1, 0, new String[]{"favourites_2", "favorites_2"}));
         }
      }
   }

   private static JsonElement findFirstElement(JsonObject var0, String... var1) {
      byte var7 = 0;

      while (true) {
         switch (var7) {
            case 0:

               var7 = 1;
               break;
            case 1:
               var7 = 2;
               break;
            default:
               if (var0 == null) {
                  return null;
               } else {
                  for (String var5 : var1) {
                     JsonElement var6 = var0.get(var5);
                     if (var6 != null && !var6.isJsonNull()) {
                        return var6;
                     }
                  }

                  return null;
               }
         }
      }
   }

   private static JsonElement findElementRecursively(JsonElement var0, int var1, String... var2) {
      byte var8 = 0;

      while (true) {
         switch (var8) {
            case 0:

               var8 = 1;
               break;
            case 1:
               var8 = 2;
               break;
            default:
               if (var0 != null && !var0.isJsonNull() && var1 <= 12) {
                  if (var0.isJsonObject()) {
                     JsonObject var3 = var0.getAsJsonObject();
                     JsonElement var4 = findFirstElement(var3, var2);
                     if (var4 != null) {
                        return var4;
                     }

                     for (Entry var6 : var3.entrySet()) {
                        JsonElement var7 = findElementRecursively((JsonElement)var6.getValue(), var1 + 1, var2);
                        if (var7 != null) {
                           return var7;
                        }
                     }
                  } else if (var0.isJsonArray()) {
                     for (JsonElement var10 : var0.getAsJsonArray()) {
                        JsonElement var11 = findElementRecursively(var10, var1 + 1, var2);
                        if (var11 != null) {
                           return var11;
                        }
                     }
                  }

                  return null;
               } else {
                  return null;
               }
         }
      }
   }

   private static List<String> toStringList(JsonElement var0) {
      byte var6 = 0;

      while (true) {
         switch (var6) {
            case 0:

               var6 = 1;
               break;
            case 1:
               var6 = 2;
               break;
            default:
               if (var0 != null && !var0.isJsonNull()) {
                  ArrayList var1 = new ArrayList();
                  if (var0.isJsonArray()) {
                     for (JsonElement var3 : var0.getAsJsonArray()) {
                        if (var3 != null && var3.isJsonPrimitive()) {
                           addLowercaseEntry(var1, var3.getAsString());
                        }
                     }
                  } else if (var0.isJsonPrimitive()) {
                     for (String var5 : var0.getAsString().split(",")) {
                        addLowercaseEntry(var1, var5);
                     }
                  }

                  return var1;
               } else {
                  return Collections.emptyList();
               }
         }
      }
   }

   private static void addLowercaseEntry(List<String> var0, String var1) {
      byte var3 = 0;

      while (true) {
         switch (var3) {
            case 0:

               var3 = 1;
               break;
            case 1:
               var3 = 2;
               break;
            default:
               String var2 = var1 == null ? "" : var1.trim().toLowerCase();
               if (!var2.isEmpty()) {
                  var0.add(var2);
               }

               return;
         }
      }
   }

   public static boolean isFiniteNumber(double var0) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               return !Double.isNaN(var0) && !Double.isInfinite(var0);
         }
      }
   }

   private static String readRankTag(JsonObject var0) {
      byte var5 = 0;

      while (true) {
         switch (var5) {
            case 0:

               var5 = 1;
               break;
            case 1:
               var5 = 2;
               break;
            default:
               String var1 = getFirstString(var0, new String[]{"prefix"});
               if (!var1.isEmpty()) {
                  return var1;
               } else {
                  String var2 = getFirstString(var0, new String[]{"rank"});
                  if (!var2.isEmpty() && !"NORMAL".equalsIgnoreCase(var2)) {
                     return formatRankTag(var2, var0);
                  } else {
                     String var3 = getFirstString(var0, new String[]{"monthlyPackageRank"});
                     if ("SUPERSTAR".equalsIgnoreCase(var3)) {
                        return formatRankTag("MVP++", var0);
                     } else {
                        String var4 = getFirstString(var0, new String[]{"newPackageRank", "packageRank"});
                        if ("MVP_PLUS".equalsIgnoreCase(var4)) {
                           return formatRankTag("MVP+", var0);
                        } else if ("MVP".equalsIgnoreCase(var4)) {
                           return formatRankTag("MVP", var0);
                        } else if ("VIP_PLUS".equalsIgnoreCase(var4)) {
                           return formatRankTag("VIP+", var0);
                        } else {
                           return "VIP".equalsIgnoreCase(var4)
                              ? formatRankTag("VIP", var0)
                              : "";
                        }
                     }
                  }
               }
         }
      }
   }

   private static String formatRankTag(String var0, JsonObject var1) {
      byte var4 = 0;

      while (true) {
         switch (var4) {
            case 0:

               var4 = 1;
               break;
            case 1:
               var4 = 2;
               break;
            default:
               String var2 = colorNameToCode(
                  getFirstString(var1, new String[]{"rankPlusColor"}), "\u00a7c"
               );
               String var3 = colorNameToCode(
                  getFirstString(var1, new String[]{"monthlyRankColor"}), "\u00a76"
               );
               if ("MVP++".equalsIgnoreCase(var0)) {
                  return var3
                     + "[MVP"
                     + var2
                     + "++"
                     + var3
                     + "]";
               } else if ("MVP+".equalsIgnoreCase(var0)) {
                  return "\u00a7b[MVP"
                     + var2
                     + "+\u00a7b]";
               } else if ("MVP".equalsIgnoreCase(var0)) {
                  return "\u00a7b[MVP]";
               } else if ("VIP+".equalsIgnoreCase(var0)) {
                  return "\u00a7a[VIP\u00a76+\u00a7a]";
               } else if ("VIP".equalsIgnoreCase(var0)) {
                  return "\u00a7a[VIP]";
               } else if ("GAME_MASTER".equalsIgnoreCase(var0)
                  || "GM".equalsIgnoreCase(var0)) {
                  return "\u00a72[GM]";
               } else if ("YOUTUBER".equalsIgnoreCase(var0)
                  || "YOUTUBE".equalsIgnoreCase(var0)) {
                  return "\u00a7c[\u00a7fYOUTUBE\u00a7c]";
               } else {
                  return "PIG+++".equalsIgnoreCase(var0)
                     ? "\u00a7d[PIG\u00a7b+++\u00a7d]"
                     : "\u00a7c["
                        + var0
                        + "]";
               }
         }
      }
   }

   private static String colorNameToCode(String var0, String var1) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               if ("BLACK".equalsIgnoreCase(var0)) {
                  return "\u00a70";
               } else if ("DARK_BLUE".equalsIgnoreCase(var0)) {
                  return "\u00a71";
               } else if ("DARK_GREEN".equalsIgnoreCase(var0)) {
                  return "\u00a72";
               } else if ("DARK_AQUA".equalsIgnoreCase(var0)) {
                  return "\u00a73";
               } else if ("DARK_RED".equalsIgnoreCase(var0)) {
                  return "\u00a74";
               } else if ("DARK_PURPLE".equalsIgnoreCase(var0)) {
                  return "\u00a75";
               } else if ("GOLD".equalsIgnoreCase(var0)) {
                  return "\u00a76";
               } else if ("GRAY".equalsIgnoreCase(var0)) {
                  return "\u00a77";
               } else if ("DARK_GRAY".equalsIgnoreCase(var0)) {
                  return "\u00a78";
               } else if ("BLUE".equalsIgnoreCase(var0)) {
                  return "\u00a79";
               } else if ("GREEN".equalsIgnoreCase(var0)) {
                  return "\u00a7a";
               } else if ("AQUA".equalsIgnoreCase(var0)) {
                  return "\u00a7b";
               } else if ("RED".equalsIgnoreCase(var0)) {
                  return "\u00a7c";
               } else if ("LIGHT_PURPLE".equalsIgnoreCase(var0)) {
                  return "\u00a7d";
               } else if ("YELLOW".equalsIgnoreCase(var0)) {
                  return "\u00a7e";
               } else {
                  return "WHITE".equalsIgnoreCase(var0)
                     ? "\u00a7f"
                     : var1;
               }
         }
      }
   }

   private static String readCustomTag(JsonObject var0) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               String var1 = getFirstString(var0, new String[]{"customTagText", "custom_tag_text"});
               return !var1.isEmpty() ? var1 : getFirstString(var0, new String[]{"role"});
         }
      }
   }

   private static double readMonthlyFkdr(JsonObject var0, JsonObject var1, JsonObject var2, HypixelPlayerStats$0 var3) {
      byte var18 = 0;

      while (true) {
         switch (var18) {
            case 0:

               var18 = 1;
               break;
            case 1:
               var18 = 2;
               break;
            default:
               JsonObject var4 = getObjectByPath(var0, "fotmData");
               if (var4 == null) {
                  var4 = getObjectByPath(var2, "fotmData");
               }

               if (var4 == null) {
                  var4 = findObjectByKey(var2, "fotmData");
               }

               JsonObject var5 = getObjectByPath(
                  var4, "stats.Bedwars"
               );
               double var6 = readModeStat(var1, var3, new String[]{"final_kills_bedwars", "finalKills", "final_kills"});
               double var8 = readModeStat(var1, var3, new String[]{"final_deaths_bedwars", "finalDeaths", "final_deaths"});
               if (var4 != null && isFiniteNumber(var6) && isFiniteNumber(var8)) {
                  double var19 = finiteOrZero(readModeStat(var5, var3, new String[]{"final_kills_bedwars", "finalKills", "final_kills"}));
                  double var20 = finiteOrZero(readModeStat(var5, var3, new String[]{"final_deaths_bedwars", "finalDeaths", "final_deaths"}));
                  double var21 = var6 - var19;
                  double var16 = var8 - var20;
                  return !(var21 > 0.0) && !(var16 > 0.0) ? 0.0 : safeRatio(var21, var16);
               } else {
                  double var10 = getFirstDouble(var1, new String[]{"monthly_fkdr", "monthlyFkdr", "month_fkdr"});
                  if (isFiniteNumber(var10)) {
                     return var10;
                  } else {
                     double var12 = getFirstDouble(var1, new String[]{"monthly_final_kills_bedwars", "monthlyFinalKills", "month_final_kills_bedwars"});
                     double var14 = getFirstDouble(var1, new String[]{"monthly_final_deaths_bedwars", "monthlyFinalDeaths", "month_final_deaths_bedwars"});
                     return safeRatio(var12, var14);
                  }
               }
         }
      }
   }

   private static double readModeStat(JsonObject var0, HypixelPlayerStats$0 var1, String... var2) {
      byte var12 = 0;

      while (true) {
         switch (var12) {
            case 0:

               var12 = 1;
               break;
            case 1:
               var12 = 2;
               break;
            default:
               if (var0 == null) {
                  return Double.NaN;
               } else {
                  if (var1 == null) {
                     var1 = HypixelPlayerStats$0.OVERALL;
                  }

                  String var3 = var2.length == 0 ? "" : var2[0];
                  if (var3.endsWith("_bedwars")) {
                     double var4 = 0.0;

                     for (String var9 : HypixelPlayerStats$0.access$000(var1)) {
                        double var10 = getFirstDouble(
                           var0, var9 + "_" + var3
                        );
                        if (isFiniteNumber(var10)) {
                           var4 += var10;
                        }
                     }

                     return var4;
                  } else {
                     return getFirstDouble(var0, var2);
                  }
               }
         }
      }
   }

   private static double finiteOrZero(double var0) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               return isFiniteNumber(var0) ? var0 : 0.0;
         }
      }
   }

   private static double safeRatio(double var0, double var2) {
      byte var4 = 0;

      while (true) {
         switch (var4) {
            case 0:

               var4 = 1;
               break;
            case 1:
               var4 = 2;
               break;
            default:
               return isFiniteNumber(var0) && isFiniteNumber(var2) ? var0 / Math.max(1.0, var2) : Double.NaN;
         }
      }
   }

   private static String injectStatPlaceholder(String var0, String var1, String var2, double var3) {
      byte var5 = 0;

      while (true) {
         switch (var5) {
            case 0:

               var5 = 1;
               break;
            case 1:
               var5 = 2;
               break;
            default:
               return isFiniteNumber(var3) && !(var3 < 0.0)
                  ? injectBetweenMarkers(var0, var1, var2, Double.toString(var3), 32)
                  : var0;
         }
      }
   }

   private static String injectBetweenMarkers(String var0, String var1, String var2, String var3, int var4) {
      byte var6 = 0;

      while (true) {
         switch (var6) {
            case 0:

               var6 = 1;
               break;
            case 1:
               var6 = 2;
               break;
            default:
               String var5 = var0 == null ? "" : var0;
               return var3 != null
                     && !var3.isEmpty()
                     && var3.length() <= var4
                     && var5.indexOf(var1) < 0
                     && var5.indexOf(var2) < 0
                     && var3.indexOf(var1) < 0
                     && var3.indexOf(var2) < 0
                  ? var5 + var1 + var3 + var2
                  : var5;
         }
      }
   }

   private static double parseSkyWarsLevel(JsonObject var0) {
      String var1 = getFirstString(var0, new String[]{"levelFormatted", "levelFormattedWithBrackets"});
      if (var1.isEmpty()) {
         return Double.NaN;
      } else {
         String var2 = var1.replaceAll("(?i)§[0-9A-FK-OR]", "");
         StringBuilder var3 = new StringBuilder();
         boolean var4 = false;

         for (int var5 = 0; var5 < var2.length(); var5++) {
            char var6 = var2.charAt(var5);
            if (Character.isDigit(var6)) {
               var3.append(var6);
            } else if (var6 == '.' && !var4 && var3.length() > 0) {
               var3.append(var6);
               var4 = true;
            } else if (var3.length() > 0) {
               break;
            }
         }

         if (var3.length() == 0) {
            return Double.NaN;
         } else {
            try {
               return Double.parseDouble(var3.toString());
            } catch (NumberFormatException var7) {
               return Double.NaN;
            }
         }
      }
   }

   private static double bedwarsExpToLevel(double var0) {
      byte var6 = 0;

      while (true) {
         switch (var6) {
            case 0:

               var6 = 1;
               break;
            case 1:
               var6 = 2;
               break;
            default:
               if (!isFiniteNumber(var0)) {
                  return Double.NaN;
               } else {
                  double var2 = Math.floor(var0 / 487000.0);
                  double var4 = var0 - var2 * 487000.0;
                  if (var4 < 500.0) {
                     return var2 * 100.0 + var4 / 500.0;
                  } else if (var4 < 1500.0) {
                     return var2 * 100.0 + 1.0 + (var4 - 500.0) / 1000.0;
                  } else if (var4 < 3500.0) {
                     return var2 * 100.0 + 2.0 + (var4 - 1500.0) / 2000.0;
                  } else {
                     return var4 < 7000.0 ? var2 * 100.0 + 3.0 + (var4 - 3500.0) / 3500.0 : var2 * 100.0 + 4.0 + (var4 - 7000.0) / 5000.0;
                  }
               }
         }
      }
   }

   private static JsonObject getJsonObjectOrEmpty(JsonObject var0, String var1) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               return var0 != null && var0.has(var1) && var0.get(var1).isJsonObject() ? var0.getAsJsonObject(var1) : new JsonObject();
         }
      }
   }

   private static JsonObject findObjectByKey(JsonObject var0, String var1) {
      byte var5 = 0;

      while (true) {
         switch (var5) {
            case 0:

               var5 = 1;
               break;
            case 1:
               var5 = 2;
               break;
            default:
               if (var0 == null) {
                  return null;
               } else if (var0.has(var1) && var0.get(var1).isJsonObject()) {
                  return var0.getAsJsonObject(var1);
               } else {
                  for (Entry var3 : var0.entrySet()) {
                     JsonObject var4 = findObjectInElement((JsonElement)var3.getValue(), var1);
                     if (var4 != null) {
                        return var4;
                     }
                  }

                  return null;
               }
         }
      }
   }

   private static JsonObject findObjectInElement(JsonElement var0, String var1) {
      byte var6 = 0;

      while (true) {
         switch (var6) {
            case 0:

               var6 = 1;
               break;
            case 1:
               var6 = 2;
               break;
            default:
               if (var0 == null || var0.isJsonNull()) {
                  return null;
               } else if (var0.isJsonObject()) {
                  return findObjectByKey(var0.getAsJsonObject(), var1);
               } else {
                  if (var0.isJsonArray()) {
                     for (JsonElement var4 : var0.getAsJsonArray()) {
                        JsonObject var5 = findObjectInElement(var4, var1);
                        if (var5 != null) {
                           return var5;
                        }
                     }
                  }

                  return null;
               }
         }
      }
   }

   private static double getFirstDouble(JsonObject var0, String... var1) {

      for (String var5 : var1) {
         JsonElement var6 = getByPath(var0, var5);
         if (var6 != null && var6.isJsonPrimitive()) {
            try {
               return var6.getAsDouble();
            } catch (Exception var8) {
            }
         }
      }

      return Double.NaN;
   }

   private static String getFirstString(JsonObject var0, String... var1) {

      for (String var5 : var1) {
         JsonElement var6 = getByPath(var0, var5);
         if (var6 != null && var6.isJsonPrimitive()) {
            try {
               return var6.getAsString();
            } catch (Exception var8) {
            }
         }
      }

      return "";
   }

   private static JsonElement getByPath(JsonObject var0, String var1) {
      byte var5 = 0;

      while (true) {
         switch (var5) {
            case 0:

               var5 = 1;
               break;
            case 1:
               var5 = 2;
               break;
            default:
               if (var0 == null) {
                  return null;
               } else {
                  Object var2 = var0;
                  String[] var3 = var1.split("\\.");

                  for (int var4 = 0; var4 < var3.length; var4++) {
                     if (!((JsonElement)var2).isJsonObject() || !((JsonElement)var2).getAsJsonObject().has(var3[var4])) {
                        return null;
                     }

                     var2 = ((JsonElement)var2).getAsJsonObject().get(var3[var4]);
                  }

                  return (JsonElement)var2;
               }
         }
      }
   }

   private static JsonObject getObjectByPath(JsonObject var0, String var1) {
      byte var3 = 0;

      while (true) {
         switch (var3) {
            case 0:

               var3 = 1;
               break;
            case 1:
               var3 = 2;
               break;
            default:
               JsonElement var2 = getByPath(var0, var1);
               return var2 != null && var2.isJsonObject() ? var2.getAsJsonObject() : null;
         }
      }
   }

   private static String normalizeColorCodes(String var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return var0 == null
                  ? ""
                  : var0.trim().replaceAll("(?i)&([0-9A-FK-OR])", "\u00a7$1");
         }
      }
   }

   static {
   }
}
