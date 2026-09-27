// Jade recovery: original class: jade.deps.eLz.BYot6Og
package jade.client.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.StringUtils;

public final class ScoreboardUtils {
   private static final int MAX_SCOREBOARD_LINES = 15;

   private ScoreboardUtils() {
   }

   public static boolean isInLobby(Minecraft var0) {
      List var1 = dj854(var0);
      if (var1.isEmpty()) {
         return false;
      } else {
         String[] var2 = IwugSbg((String)var1.get(1)).split("  ");
         return var2.length > 1 && var2[1].charAt(0) == 'L';
      }
   }

   public static boolean ATIk(Minecraft var0) {
      ScoreObjective var1 = getSidebarObjective(var0);
      if (var1 == null) {
         return false;
      } else {
         String var2 = IwugSbg(var1.getDisplayName());
         return var2.contains("BED WARS PRACTICE") || var2.contains("REPLAY");
      }
   }

   public static int getSkyWarsBoardType(Minecraft var0) {
      ScoreObjective var1 = getSidebarObjective(var0);
      if (var1 != null && koeKb(var1.getDisplayName()).contains("SKYWARS")) {
         List var2 = getLowercaseLines(var0);
         if (containsAnyLine(var2, "players left")) {
            return 2;
         } else if (SWBtiE(var2, "next event", "kills:")) {
            return 2;
         } else {
            for (String var4 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var2)) {
               if (var4.startsWith("players") && var4.contains("/")) {
                  return 1;
               }
            }

            return 0;
         }
      } else {
         return -1;
      }
   }

   public static int getBedWarsBoardType(Minecraft var0) {
      ScoreObjective var1 = getSidebarObjective(var0);
      if (var1 != null && IwugSbg(var1.getDisplayName()).contains("BED WARS")) {
         for (String var3 : dj854(var0)) {
            String var4 = IwugSbg(var3);
            String[] var5 = var4.split("  ");
            if (var5.length <= 1) {
               if (!var4.equals("Waiting...") && !var4.startsWith("Starting in")) {
                  if (!var4.startsWith("R Red:") && !var4.startsWith("B Blue:")) {
                     continue;
                  }

                  return 2;
               }

               return 1;
            } else if (var5[1].startsWith("L")) {
               return 0;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   public static List<String> dj854(Minecraft var0) {
      ScoreObjective var1 = getSidebarObjective(var0);
      if (var1 == null) {
         return new ArrayList<>();
      } else {
         Scoreboard var2 = var0.theWorld.getScoreboard();
         ArrayList var3 = new ArrayList();

         for (Score var5 : var2.getSortedScores(var1)) {
            String var6 = var5 == null ? null : var5.getPlayerName();
            if (var6 != null && !var6.startsWith("#")) {
               var3.add(var5);
            }
         }

         if (var3.size() > 15) {
            var3 = new ArrayList(var3.subList(var3.size() - 15, var3.size()));
         }

         ArrayList var8 = new ArrayList(var3.size() + 1);

         for (Score var10 : (java.lang.Iterable<Score>) (java.lang.Iterable<?>) (var3)) {
            String var7 = var10.getPlayerName();
            var8.add(ScorePlayerTeam.formatPlayerName(var2.getPlayersTeam(var7), var7));
         }

         if (!var3.isEmpty()) {
            var8.add(var1.getDisplayName());
         }

         Collections.reverse(var8);
         return var8;
      }
   }

   public static String IwugSbg(String var0) {
      String var1 = StringUtils.stripControlCodes(var0);
      StringBuilder var2 = new StringBuilder(var1.length());

      for (int var3 = 0; var3 < var1.length(); var3++) {
         char var4 = var1.charAt(var3);
         if (var4 > 20 && var4 < 127) {
            var2.append(var4);
         }
      }

      return var2.toString();
   }

   private static ScoreObjective getSidebarObjective(Minecraft var0) {
      if (var0.thePlayer != null && var0.theWorld != null) {
         Scoreboard var1 = var0.theWorld.getScoreboard();
         return var1 == null ? null : var1.getObjectiveInDisplaySlot(1);
      } else {
         return null;
      }
   }

   private static List<String> getLowercaseLines(Minecraft var0) {
      ArrayList var1 = new ArrayList();

      for (String var3 : dj854(var0)) {
         var1.add(normalizeLine(var3).toLowerCase(Locale.ROOT));
      }

      return var1;
   }

   private static boolean containsAnyLine(List<String> var0, String var1) {
      for (String var3 : var0) {
         if (var3.contains(var1)) {
            return true;
         }
      }

      return false;
   }

   private static boolean SWBtiE(List<String> var0, String var1, String var2) {
      for (String var4 : var0) {
         if (var4.startsWith(var1) || var4.startsWith(var2)) {
            return true;
         }
      }

      return false;
   }

   private static String koeKb(String var0) {
      return normalizeLine(var0).toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "");
   }

   private static String normalizeLine(String var0) {
      return var0 == null ? "" : StringUtils.stripControlCodes(var0).replace(' ', ' ').trim().replaceAll("[\\p{Cf}\\p{Cc}]", "").replaceAll("\\s+", " ");
   }
}
