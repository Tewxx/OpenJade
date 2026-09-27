// Jade recovery: original class: jade.deps.eLz.vHr8rKZiH
package jade.client.module.minigames.bedwarsutils;

import jade.client.common.ClientUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

public final class BedwarsGameTracker {
   private static final Pattern pattern = Pattern.compile(
      "(diamond ii|emerald ii|diamond iii|emerald iii|bed destruction|sudden death|game end)\\s+in\\s+(\\d+):(\\d+)",
      2
   );
   private static final double SPAWNER_MATCH_DISTANCE_SQ = 1.0;
   private static final double DEDUPE_DISTANCE_SQ = 1.0;
   private long gameStartMillis = -1L;
   private boolean doublesMode;
   private final List<BedwarsGameTracker$2> spawnerPositions = new ArrayList<>();
   private ScoreObjective scoreObjective;
   private String replacementLineText;
   private String LOB;
   private int savedScorePoints;

   public void zry91() {
      this.restoreSidebarLine();
      this.gameStartMillis = -1L;
      this.doublesMode = false;
      this.spawnerPositions.clear();
   }

   public void onGameStartMessage(String var1) {
      if (var1 != null && var1.contains("Protect your bed and destroy the enemy beds.")) {
         this.gameStartMillis = System.currentTimeMillis();
      }
   }

   public void updateGameState(Minecraft var1, int var2, boolean var3) {
      this.restoreSidebarLine();
      if (var1 != null && var1.theWorld != null && var2 == 2) {
         List var4 = ClientUtils.PxSw4();
         this.doublesMode = hasPinkTeam(var4);
         long var5 = System.currentTimeMillis();
         long var7 = estimateGameStartMillis(var4, var5);
         if (var7 >= 0L) {
            if (this.gameStartMillis < 0L || Math.abs(this.gameStartMillis - var7) > 1500L) {
               this.gameStartMillis = var7;
            }
         } else if (this.gameStartMillis < 0L) {
            this.gameStartMillis = var5;
         }

         this.scanSpawnerMarkers(var1);
         if (var3) {
            this.XFayst(var1, var5);
         }
      } else {
         if (var2 == 0 || var2 == 1) {
            this.zry91();
         }
      }
   }

   private void scanSpawnerMarkers(Minecraft var1) {
      for (Entity var3 : var1.theWorld.loadedEntityList) {
         if (var3 instanceof EntityArmorStand && !var3.isDead) {
            EntityArmorStand var4 = (EntityArmorStand)var3;
            BedwarsGameTracker$3 var5 = this.DkFmxuE(var4);
            if (var5 != null) {
               this.yojbxAw(new BedwarsGameTracker$2(var4.posX, var4.posY, var4.posZ, var5));
            }
         }
      }
   }

   public String getItemSpawnEta(EntityItem var1) {
      if (var1 != null && !var1.isDead && var1.getEntityItem() != null && this.gameStartMillis >= 0L) {
         Item var2 = var1.getEntityItem().getItem();
         BedwarsGameTracker$3 var3 = var2 == Items.emerald ? BedwarsGameTracker$3.EMERALD : (var2 == Items.diamond ? BedwarsGameTracker$3.DIAMOND : null);
         if (var3 != null && this.XQji(var1, var3)) {
            long var4 = System.currentTimeMillis();
            return "(" + this.Tixps(var3, var4) + "s)";
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private boolean XQji(EntityItem var1, BedwarsGameTracker$3 var2) {
      for (BedwarsGameTracker$2 var4 : this.spawnerPositions) {
         double var5 = BedwarsGameTracker$2.VqcO(var4) - var1.posX;
         double var7 = BedwarsGameTracker$2.getPosZ(var4) - var1.posZ;
         if (BedwarsGameTracker$2.getSpawnerType(var4) == var2 && var5 * var5 + var7 * var7 <= 1.0) {
            return true;
         }
      }

      return false;
   }

   private void yojbxAw(BedwarsGameTracker$2 var1) {
      for (BedwarsGameTracker$2 var3 : this.spawnerPositions) {
         double var4 = BedwarsGameTracker$2.VqcO(var3) - BedwarsGameTracker$2.VqcO(var1);
         double var6 = BedwarsGameTracker$2.getPosY(var3) - BedwarsGameTracker$2.getPosY(var1);
         double var8 = BedwarsGameTracker$2.getPosZ(var3) - BedwarsGameTracker$2.getPosZ(var1);
         if (BedwarsGameTracker$2.getSpawnerType(var3) == BedwarsGameTracker$2.getSpawnerType(var1) && var4 * var4 + var6 * var6 + var8 * var8 <= 1.0) {
            return;
         }
      }

      this.spawnerPositions.add(var1);
   }

   private BedwarsGameTracker$3 DkFmxuE(EntityArmorStand var1) {
      ItemStack var2 = var1.getEquipmentInSlot(4);
      if (var2 == null) {
         return null;
      } else if (var2.getItem() == Item.getItemFromBlock(Blocks.emerald_block)) {
         return BedwarsGameTracker$3.EMERALD;
      } else {
         return var2.getItem() == Item.getItemFromBlock(Blocks.diamond_block) ? BedwarsGameTracker$3.DIAMOND : null;
      }
   }

   private void XFayst(Minecraft var1, long var2) {
      Scoreboard var4 = var1.theWorld.getScoreboard();
      ScoreObjective var5 = var4 == null ? null : var4.getObjectiveInDisplaySlot(1);
      if (var5 != null) {
         Score var6 = null;
         ArrayList var7 = new ArrayList();

         for (Score var9 : var4.getSortedScores(var5)) {
            if (var9 != null && var9.getPlayerName() != null && !var9.getPlayerName().startsWith("#")) {
               ScorePlayerTeam var10 = var4.getPlayersTeam(var9.getPlayerName());
               String var11 = ClientUtils.AOAtn(ScorePlayerTeam.formatPlayerName(var10, var9.getPlayerName())).trim();
               if (var11.toLowerCase(Locale.ROOT).contains("hypixel.net")) {
                  var6 = var9;
               } else if (var11.isEmpty()) {
                  var7.add(var9);
               }
            }
         }

         Score var13 = null;
         if (var6 != null) {
            int var14 = Integer.MAX_VALUE;

            for (Score var17 : (java.lang.Iterable<Score>) (java.lang.Iterable<?>) (var7)) {
               int var12 = var17.getScorePoints() - var6.getScorePoints();
               if (var12 > 0 && var12 < var14) {
                  var13 = var17;
                  var14 = var12;
               }
            }
         }

         if (var13 == null && !var7.isEmpty()) {
            var13 = (Score)var7.get(0);
         }

         if (var13 != null) {
            this.LOB = var13.getPlayerName();
            this.savedScorePoints = var13.getScorePoints();
            var4.removeObjectiveFromEntity(this.LOB, var5);
            String var15 = "§aE: §f" + this.Tixps(BedwarsGameTracker$3.EMERALD, var2) + "s §bD: §f" + this.Tixps(BedwarsGameTracker$3.DIAMOND, var2) + "s§r";
            var4.getValueFromObjective(var15, var5).setScorePoints(this.savedScorePoints);
            this.scoreObjective = var5;
            this.replacementLineText = var15;
         }
      }
   }

   private void restoreSidebarLine() {
      if (this.scoreObjective != null && this.replacementLineText != null) {
         Scoreboard var1 = this.scoreObjective.getScoreboard();
         var1.removeObjectiveFromEntity(this.replacementLineText, this.scoreObjective);
         if (this.LOB != null) {
            var1.getValueFromObjective(this.LOB, this.scoreObjective).setScorePoints(this.savedScorePoints);
         }
      }

      this.scoreObjective = null;
      this.replacementLineText = null;
      this.LOB = null;
      this.savedScorePoints = 0;
   }

   private BedwarsGameTracker$1 getEmeraldSpawnCountdown(long var1) {
      int var3 = (int)Math.max(0L, (var1 - this.gameStartMillis) / 1000L);
      byte var4 = 1;
      int var5 = -1;
      int var6 = 0;

      for (int var7 = 0; var7 <= var3; var7++) {
         int var8 = var7 + 1;
         if (var8 == 1) {
            var4 = 1;
            var5 = 30;
         } else if (var8 == 720) {
            var4 = 2;
            var5 = 0;
         } else if (var8 == 1440) {
            var4 = 3;
            var5 = 0;
         }

         if (var5 == 0) {
            var6++;
            var5 = this.getEmeraldIntervalSeconds(var4);
         }

         if (var7 < var3) {
            var5--;
         }
      }

      return new BedwarsGameTracker$1(Math.max(0, var5), var6);
   }

   private int getEmeraldIntervalSeconds(int var1) {
      if (this.doublesMode) {
         return var1 == 1 ? 65 : (var1 == 2 ? 50 : 35);
      } else {
         return var1 == 1 ? 55 : (var1 == 2 ? 40 : 27);
      }
   }

   private int Tixps(BedwarsGameTracker$3 var1, long var2) {
      if (var1 == BedwarsGameTracker$3.EMERALD) {
         return BedwarsGameTracker$1.qiZnvV(this.getEmeraldSpawnCountdown(var2));
      } else {
         int var4 = (int)Math.max(0L, (var2 - this.gameStartMillis) / 1000L);
         int var5 = var4 >= 1080 ? 3 : (var4 >= 360 ? 2 : 1);
         int var6 = var5 == 1 ? 30 : (var5 == 2 ? 24 : 12);
         int var7 = var5 == 1 ? 0 : (var5 == 2 ? 360 : 1080);
         int var8 = var4 - var7;
         int var9 = var8 % var6;
         return var9 == 0 ? var6 : var6 - var9;
      }
   }

   private static boolean hasPinkTeam(List<String> var0) {
      for (String var2 : var0) {
         String var3 = ClientUtils.AOAtn(var2).toLowerCase(Locale.ROOT);
         if (var3.contains("pink:")) {
            return true;
         }
      }

      return false;
   }

   private static long estimateGameStartMillis(List<String> var0, long var1) {
      for (String var4 : var0) {
         Matcher var5 = pattern.matcher(ClientUtils.AOAtn(var4));
         if (var5.find()) {
            int var6 = Integer.parseInt(var5.group(2)) * 60 + Integer.parseInt(var5.group(3));
            int var7 = kRzj(var5.group(1));
            if (var7 >= var6) {
               return var1 - (var7 - var6) * 1000L;
            }
         }
      }

      return -1L;
   }

   private static int kRzj(String var0) {
      String var1 = var0.toLowerCase(Locale.ROOT);
      if ("diamond ii".equals(var1)) {
         return 360;
      } else if ("emerald ii".equals(var1)) {
         return 720;
      } else if ("diamond iii".equals(var1)) {
         return 1080;
      } else if ("emerald iii".equals(var1)) {
         return 1440;
      } else if ("bed destruction".equals(var1)) {
         return 1800;
      } else if ("sudden death".equals(var1)) {
         return 2400;
      } else {
         return "game end".equals(var1) ? 3000 : -1;
      }
   }
}
