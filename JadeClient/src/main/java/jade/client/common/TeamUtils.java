// Jade recovery: original class: jade.deps.eLz.tRLzo749
package jade.client.common;

import jade.inject.InjectionAgent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;

public final class TeamUtils {
   private static final String SKwCu = "c9aebfd8";

   private TeamUtils() {
   }

   public static boolean isTeammate(Minecraft var0, Entity var1) {
      if (var1 instanceof EntityLivingBase && var0.thePlayer != null) {
         EntityLivingBase var2 = (EntityLivingBase)var1;
         return InjectionAgent.isBadlionRuntime() ? isTeammateByScoreboard(var0, var2) : isTeammateByDisplayName(var0, var2);
      } else {
         return false;
      }
   }

   private static boolean isTeammateByScoreboard(Minecraft var0, EntityLivingBase var1) {
      try {
         TeamUtils$0 var2 = xhyM7(var0, var0.thePlayer);
         TeamUtils$0 var3 = xhyM7(var0, var1);
         return !safeEquals(var2.PgL6, var3.PgL6) && !safeEquals(var2.ERhe4, var3.ERhe4)
            ? var2.hasSingleCharTag && var3.hasSingleCharTag && var2.colorCode != 0 && var2.colorCode == var3.colorCode
            : true;
      } catch (Exception var4) {
         return false;
      }
   }

   private static TeamUtils$0 xhyM7(Minecraft var0, EntityLivingBase var1) {
      NetworkPlayerInfo var2 = ALxi(var0, var1);
      PlayerListTracker$1 var3 = findPlayerListEntry(var1, var2);
      ScorePlayerTeam var4 = VYMZCvC(var0, var1, var2);
      String var5 = var3 == null ? null : var3.getName();
      String var6 = var4 == null ? null : var4.getRegisteredName();
      String var7 = var3 == null ? null : var3.getPrefix();
      if (var7 == null || var7.length() == 0) {
         var7 = var4 == null ? null : var4.getColorPrefix();
      }

      boolean var8 = hasSingleCharTag(var7);
      char var9 = findTeamColorCode(var3, var4, var2, var1);
      return new TeamUtils$0(var5, var6, var9, var8);
   }

   private static boolean isTeammateByDisplayName(Minecraft var0, EntityLivingBase var1) {
      try {
         if (var0.thePlayer.isOnSameTeam(var1)) {
            return true;
         } else {
            String var2 = var1.getDisplayName().getUnformattedText().substring(0, 2);
            String var3 = var0.thePlayer.getDisplayName().getUnformattedText();
            return var3.startsWith(var2) || getOwnFormattedName(var0).startsWith(var2);
         }
      } catch (Exception var4) {
         return false;
      }
   }

   public static String getOwnFormattedName(Minecraft var0) {
      try {
         NetworkPlayerInfo var1 = var0.getNetHandler().getPlayerInfo(var0.thePlayer.getUniqueID());
         return ScorePlayerTeam.formatPlayerName(var1.getPlayerTeam(), var1.getGameProfile().getName());
      } catch (Exception var2) {
         return "";
      }
   }

   private static NetworkPlayerInfo ALxi(Minecraft var0, EntityLivingBase var1) {
      if (var0.getNetHandler() != null && var1 != null) {
         NetworkPlayerInfo var2 = var0.getNetHandler().getPlayerInfo(var1.getUniqueID());
         if (var2 != null) {
            return var2;
         } else {
            for (NetworkPlayerInfo var4 : var0.getNetHandler().getPlayerInfoMap()) {
               if (var4 != null && var4.getGameProfile() != null && var1.getUniqueID().equals(var4.getGameProfile().getId())) {
                  return var4;
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   private static ScorePlayerTeam VYMZCvC(Minecraft var0, EntityLivingBase var1, NetworkPlayerInfo var2) {
      if (var1.getTeam() instanceof ScorePlayerTeam) {
         return (ScorePlayerTeam)var1.getTeam();
      } else if (var2 != null && var2.getPlayerTeam() != null) {
         return var2.getPlayerTeam();
      } else {
         Scoreboard var3 = var0.theWorld == null ? null : var0.theWorld.getScoreboard();
         return var3 == null ? null : var3.getPlayersTeam(var1.getName());
      }
   }

   private static PlayerListTracker$1 findPlayerListEntry(EntityLivingBase var0, NetworkPlayerInfo var1) {
      if (var1 != null && var1.getGameProfile() != null) {
         PlayerListTracker$1 var2 = PlayerListTracker.getTeamForPlayer(var1.getGameProfile().getName());
         if (var2 != null) {
            return var2;
         }
      }

      return PlayerListTracker.getTeamForPlayer(var0.getName());
   }

   private static boolean safeEquals(String var0, String var1) {
      return var0 != null && var1 != null && var0.equals(var1);
   }

   private static boolean hasSingleCharTag(String var0) {
      if (var0 != null && var0.contains("§l")) {
         String var1 = EnumChatFormatting.getTextWithoutFormattingCodes(var0);
         return var1 != null && var1.trim().length() == 1;
      } else {
         return false;
      }
   }

   private static char findTeamColorCode(PlayerListTracker$1 var0, ScorePlayerTeam var1, NetworkPlayerInfo var2, EntityLivingBase var3) {
      String[] var4 = new String[]{
         var0 == null ? null : var0.getPrefix(),
         var1 == null ? null : var1.getColorPrefix(),
         var2 != null && var2.getPlayerTeam() != null ? var2.getPlayerTeam().getColorPrefix() : null,
         var3.getDisplayName() == null ? null : var3.getDisplayName().getFormattedText()
      };

      for (String var8 : var4) {
         char var9 = extractColorCode(var8);
         if (var9 != 0) {
            return var9;
         }
      }

      return '\u0000';
   }

   private static char extractColorCode(String var0) {
      if (var0 == null) {
         return '\u0000';
      } else {
         for (int var1 = 0; var1 + 1 < var0.length(); var1++) {
            if (var0.charAt(var1) == 167) {
               char var2 = Character.toLowerCase(var0.charAt(var1 + 1));
               if ("c9aebfd8".indexOf(var2) >= 0) {
                  return var2;
               }
            }
         }

         return '\u0000';
      }
   }
}
