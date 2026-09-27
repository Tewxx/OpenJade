// Jade recovery: original class: jade.deps.eLz.Ma199Jd
package jade.client.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

public final class ScoreboardLines {
   private ScoreboardLines() {
   }

   public static <T> List<T> MLEt(Collection<T> var0, Predicate<T> var1) {
      ArrayList var2 = new ArrayList();

      for (T var4 : var0) {
         if (var1.test(var4)) {
            var2.add(var4);
         }
      }

      if (var2.size() <= 15) {
         return var2;
      } else {
         int var5 = Math.min(var2.size(), var0.size() - 15);
         return new ArrayList<>(var2.subList(var5, var2.size()));
      }
   }

   public static List<String> getSidebarLines(Minecraft var0) {
      ArrayList var1 = new ArrayList();
      if (var0.theWorld == null) {
         return var1;
      } else {
         Scoreboard var2 = var0.theWorld.getScoreboard();
         if (var2 == null) {
            return var1;
         } else {
            ScoreObjective var3 = var2.getObjectiveInDisplaySlot(1);
            if (var3 == null) {
               return var1;
            } else {
               for (Score var6 : MLEt(var2.getSortedScores(var3), ScoreboardLines::RXDwc)) {
                  String var7 = var6.getPlayerName();
                  var1.add(ScorePlayerTeam.formatPlayerName(var2.getPlayersTeam(var7), var7));
               }

               return var1;
            }
         }
      }
   }

   private static boolean RXDwc(Score var0) {
      return var0 != null && var0.getPlayerName() != null && !var0.getPlayerName().startsWith("#");
   }
}
