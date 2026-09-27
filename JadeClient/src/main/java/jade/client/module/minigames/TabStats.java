// Jade recovery: module: Tab Stats (minigames); original class: jade.deps.eLz.sOoP9qm
package jade.client.module.minigames;

import com.mojang.authlib.GameProfile;
import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.common.SkinCache;
import jade.client.gui.TabStatsScreen;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.minigames.tabstats.SkywarsStatParser;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.SliderSetting;
import jade.client.setting.StringListSetting;

import jade.deps.loader107.HypixelPlayerStats;

import jade.deps.loader107.StatsLookupService;

import jade.mixin.impl.accessor.IAccessorGuiPlayerTabOverlay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

@ModuleInfo(aliases = {"TabStats", "Stats Tab"})
public class TabStats extends Module {
   private static final String[] BEDWARS_SORT_OPTIONS = new String[]{
         "Tab Order",
         "Name",
         "Star",
         "Winstreak",
         "FKDR",
         "Finals",
         "M.FKDR",
         "Sniper Score",
         "HP",
         "Team"
      };
   private static final String[] SKYWARS_SORT_OPTIONS = new String[]{
         "Tab Order",
         "Name",
         "Star",
         "KDR",
         "WLR",
         "Kills",
         "Wins",
         "Sniper Score",
         "HP"
      };
   private static final String[] bku = new String[]{"Descending", "Ascending"};
   private static final TabStats$4[] OQZ;
   private static final TabStats$4[] aIye;
   private final Map<UUID, HypixelPlayerStats> statEntryCache = new HashMap<>();
   private final GroupSetting layoutGroup;
   private final FontSetting font;
   private final SliderSetting fontSize;
   private final SliderSetting rowHeight;
   private final SliderSetting padding;
   private final SliderSetting columnGap;
   private final SliderSetting maxPlayers;
   private final SliderSetting rounding;
   private final SliderSetting bedWarsSort;
   private final SliderSetting skyWarsSort;
   private final SliderSetting direction;
   private final BooleanSetting textShadow;
   private final ColorSetting panel;
   private final ColorSetting header;
   private final ColorSetting text;
   private final StringListSetting bedWarsTabColumnOrder;
   private final StringListSetting skyWarsTabColumnOrder;

   public TabStats() {
      super("Tab Stats", Category.minigames);
      this.registerSetting(new BooleanSetting("BedWars editor", new Runnable() {
         @Override
         public void run() {
            TabStats.this.openEditorScreen(false);
         }
      }).setButtonText("EDIT"));
      this.registerSetting(new BooleanSetting("SkyWars editor", new Runnable() {
         @Override
         public void run() {
            TabStats.this.openEditorScreen(true);
         }
      }).setButtonText("EDIT"));
      this.registerSetting(this.layoutGroup = new GroupSetting("Layout"));
      this.registerSetting(
         this.font = new FontSetting(this.layoutGroup, "Font", "Modern")
      );
      this.registerSetting(this.fontSize = new SliderSetting(this.layoutGroup, "Font size", 1.0, 0.5, 2.0, 0.05));
      this.registerSetting(this.rowHeight = new SliderSetting(this.layoutGroup, "Row height", 13.0, 10.0, 24.0, 1.0));
      this.registerSetting(this.padding = new SliderSetting(this.layoutGroup, "Padding", 6.0, 2.0, 16.0, 1.0));
      this.registerSetting(this.columnGap = new SliderSetting(this.layoutGroup, "Column gap", 8.0, 2.0, 24.0, 1.0));
      this.registerSetting(this.maxPlayers = new SliderSetting(this.layoutGroup, "Max players", 24.0, 1.0, 80.0, 1.0));
      this.registerSetting(this.rounding = new SliderSetting(this.layoutGroup, "Rounding", 2.0, 0.0, 12.0, 0.5));
      this.registerSetting(
         this.bedWarsSort = new SliderSetting(
            this.layoutGroup,
            "BedWars sort",
            0,
            BEDWARS_SORT_OPTIONS
         )
      );
      this.registerSetting(
         this.skyWarsSort = new SliderSetting(
            this.layoutGroup, "SkyWars sort", 0, SKYWARS_SORT_OPTIONS
         )
      );
      this.registerSetting(
         this.direction = new SliderSetting(
            this.layoutGroup, "Direction", 0, bku
         )
      );
      this.registerSetting(
         this.textShadow = new BooleanSetting(
            this.layoutGroup, "Text shadow", true
         )
      );
      this.registerSetting(
         this.panel = new ColorSetting(
            "Panel",
            0,
            0,
            0,
            128
         )
      );
      this.registerSetting(
         this.header = new ColorSetting(
            "Header",
            0,
            0,
            0,
            179
         )
      );
      this.registerSetting(
         this.text = new ColorSetting(
            "Text",
            232,
            235,
            241
         )
      );
      this.registerSetting(
         this.bedWarsTabColumnOrder = new StringListSetting(
            "BedWars tab column order", "", 20
         )
      );
      this.registerSetting(this.skyWarsTabColumnOrder = new StringListSetting("SkyWars tab column order", "", 20));
      this.lvNk();
   }

   private void lvNk() {
      this.layoutGroup.visible = false;
      this.font.visible = false;
      this.fontSize.visible = false;
      this.rowHeight.visible = false;
      this.padding.visible = false;
      this.columnGap.visible = false;
      this.maxPlayers.visible = false;
      this.rounding.visible = false;
      this.bedWarsSort.visible = false;
      this.skyWarsSort.visible = false;
      this.direction.visible = false;
      this.textShadow.visible = false;
      this.panel.visible = false;
      this.header.visible = false;
      this.text.visible = false;
      this.bedWarsTabColumnOrder.visible = false;
      this.skyWarsTabColumnOrder.visible = false;
   }

   public boolean renderTabStats(int var1, Scoreboard var2, ScoreObjective var3) {
      if (this.isEnabled() && ClientUtils.isInWorld()) {
         int var4 = ClientUtils.getSkyWarsBoardType();
         int var5 = ClientUtils.getBedWarsBoardType();
         if (var4 != 2 && var5 != 2) {
            return false;
         } else {
            boolean var6 = var4 == 2;
            List var7 = this.buildStatRows(var2, var3);
            if (var7.isEmpty()) {
               return false;
            } else {
               StatsLookupService.getInstance().tick();
               this.requestMissingStats(var7);
               this.meoZ(var7, var6);
               List var8 = this.getConfiguredColumns(var6);
               int var9 = Math.min(var7.size(), (int)this.maxPlayers.getInput());
               this.mnUv(var1, var7, var8, var9, this.getTabFooterSummary(), 1.0F);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public void onDisable() {
      this.statEntryCache.clear();
   }

   private List<TabStats$5> buildStatRows(Scoreboard var1, ScoreObjective var2) {
      ArrayList var3 = new ArrayList();

      for (NetworkPlayerInfo var5 : mc.getNetHandler().getPlayerInfoMap()) {
         GameProfile var6 = var5 == null ? null : var5.getGameProfile();
         if (var6 != null && var6.getId() != null && var6.getName() != null && !var6.getName().trim().isEmpty()) {
            HypixelPlayerStats var7 = StatsLookupService.getInstance()
               .getCachedStats(var6.getId());
            if (var7 != null) {
               this.statEntryCache.put(var6.getId(), var7);
            }

            float var8 = this.getScoreboardHealth(var1, var2, var6.getName());
            var3.add(new TabStats$5(var5, var6.getId(), var6.getName(), this.statEntryCache.get(var6.getId()), var8));
         }
      }

      return var3;
   }

   private float getScoreboardHealth(Scoreboard var1, ScoreObjective var2, String var3) {
      if (var1 != null && var2 != null && var3 != null) {
         try {
            Score var4 = var1.getValueFromObjective(var3, var2);
            return var4 == null ? Float.NaN : var4.getScorePoints();
         } catch (Throwable var5) {
            return Float.NaN;
         }
      } else {
         return Float.NaN;
      }
   }

   private void requestMissingStats(List<TabStats$5> var1) {
      for (TabStats$5 var3 : var1) {
         if (TabStats$5.getStatEntry(var3) == null) {
            StatsLookupService.getInstance().requestStatsForGame(TabStats$5.getPlayerUuid(var3));
         }
      }
   }

   private void meoZ(List<TabStats$5> var1, final boolean var2) {
      final int var3 = (int)(var2 ? this.skyWarsSort : this.bedWarsSort).getInput();
      if (var3 != 0) {
         Collections.sort(var1, new Comparator<TabStats$5>() {
            public int compare(TabStats$5 var1, TabStats$5 var2x) {
               int var3x;
               if (var3 == 1) {
                  var3x = TabStats$5.NJzB(var1).compareToIgnoreCase(TabStats$5.NJzB(var2x));
               } else if (!var2 && var3 == 9) {
                  var3x = TabStats$5.getTeamName(var1).compareToIgnoreCase(TabStats$5.getTeamName(var2x));
               } else {
                  var3x = Double.compare(TabStats.accessSortValue(TabStats.this, var2x, var3, var2), TabStats.accessSortValue(TabStats.this, var1, var3, var2));
               }

               if ((int)TabStats.accessDirection(TabStats.this).getInput() == 1) {
                  var3x = -var3x;
               }

               return var3x == 0 ? TabStats$5.NJzB(var1).compareToIgnoreCase(TabStats$5.NJzB(var2x)) : var3x;
            }
         });
      }
   }

   private double getSortValue(TabStats$5 var1, int var2, boolean var3) {
      if (var2 == 8) {
         return TabStats$5.rLaf(var1);
      } else if (TabStats$5.getStatEntry(var1) == null) {
         return Double.NEGATIVE_INFINITY;
      } else if (var3) {
         switch (var2) {
            case 2:
               return TabStats$5.getStatEntry(var1).getSkyWarsLevel();
            case 3:
               return TabStats$5.getStatEntry(var1).getSkyWarsKdr();
            case 4:
               return TabStats$5.getStatEntry(var1).getSkyWarsWlr();
            case 5:
               return SkywarsStatParser.oZnyDae(TabStats$5.getStatEntry(var1).getDisplayTag());
            case 6:
               return SkywarsStatParser.parseWins(TabStats$5.getStatEntry(var1).getDisplayTag());
            case 7:
               return TabStats$5.getStatEntry(var1).getSniperScore();
            default:
               return 0.0;
         }
      } else {
         switch (var2) {
            case 2:
               return TabStats$5.getStatEntry(var1).getBedwarsLevel();
            case 3:
               return TabStats$5.getStatEntry(var1).getBedwarsWinstreak();
            case 4:
               return TabStats$5.getStatEntry(var1).getBedwarsFkdr();
            case 5:
               return TabStats$5.getStatEntry(var1).getBedwarsFinalKills();
            case 6:
               return TabStats$5.getStatEntry(var1).getBedwarsMonthlyFkdr();
            case 7:
               return TabStats$5.getStatEntry(var1).getSniperScore();
            default:
               return 0.0;
         }
      }
   }

   private void mnUv(int var1, List<TabStats$5> var2, List<TabStats$4> var3, int var4, String var5, float var6) {
      IFont var7 = this.ZWpf();
      int var8 = (int)this.columnGap.getInput();
      int var9 = (int)this.padding.getInput();
      int var10 = Math.max((int)this.rowHeight.getInput(), var7.getFontHeight() + 4);
      int[] var11 = new int[var3.size()];
      int var12 = var9 * 2;

      for (int var13 = 0; var13 < var3.size(); var13++) {
         var11[var13] = this.getOverlayModule().measureStringWidth(var7, ((TabStats$4)var3.get(var13)).label);

         for (int var14 = 0; var14 < var4; var14++) {
            var11[var13] = Math.max(var11[var13], this.dxD6(var7, (TabStats$4)var3.get(var13), (TabStats$5)var2.get(var14), var10));
         }

         var12 += var11[var13] + (var13 == var3.size() - 1 ? 0 : var8);
      }

      if (!var5.isEmpty()) {
         var12 = Math.max(var12, this.getOverlayModule().measureStringWidth(var7, var5) + var9 * 2);
      }

      int var26 = var5.isEmpty() ? 0 : var10 + 3;
      int var27 = var10 * (var4 + 1) + var9 * 2 + var26;
      ScaledResolution var15 = new ScaledResolution(mc);
      float var16 = Math.min(1.0F, Math.min((var1 - 8.0F) / Math.max(1, var12), (var15.getScaledHeight() - 8.0F) / Math.max(1, var27)));
      float var17 = Math.min(var6, var16);
      float var18 = (var1 / var17 - var12) / 2.0F;
      float var19 = 4.0F / var17;
      GlStateManager.pushMatrix();
      GlStateManager.scale(var17, var17, 1.0F);
      float var20 = (float)this.rounding.getInput();
      if (var20 <= 0.0F) {
         RenderUtils.XNRNki(var18, var19, var18 + var12, var19 + var27, this.panel.getArgb());
      } else {
         RoundedRect.drawRoundedRectArgb(var18, var19, var12, var27, var20, this.panel.getArgb());
      }

      if (var20 <= 0.0F) {
         RenderUtils.XNRNki(var18, var19, var18 + var12, var19 + var10 + var9, this.header.getArgb());
      } else {
         RoundedRect.drawRoundedRectArgb(var18, var19, var12, var10 + var9, var20, this.header.getArgb());
      }

      float var21 = var18 + var9;

      for (int var22 = 0; var22 < var3.size(); var22++) {
         TabStats$4 var23 = (TabStats$4)var3.get(var22);
         if (var23 != TabStats$4.SKIN && var23 != TabStats$4.SW_SKIN) {
            this.getOverlayModule().xFhku(var7, var23.label, var21, var19 + (var10 + var9 - var7.getFontHeight()) / 2.0F, this.text.getArgb(), this.textShadow.isToggled());
         }

         var21 += var11[var22] + var8;
      }

      for (int var29 = 0; var29 < var4; var29++) {
         float var31 = var19 + var10 + var9 + var29 * var10;
         TabStats$5 var24 = (TabStats$5)var2.get(var29);
         if (TabStats$5.getStatEntry(var24) != null
            && HypixelPlayerStats.isFiniteNumber(TabStats$5.getStatEntry(var24).getSniperScore())
            && TabStats$5.getStatEntry(var24).getSniperScore() >= 500.0) {
            RenderUtils.XNRNki(var18 + 2.0F, var31 + 1.0F, var18 + var12 - 2.0F, var31 + var10 - 1.0F, 1353973760);
         } else if ((var29 & 1) == 0) {
            RenderUtils.XNRNki(var18, var31, var18 + var12, var31 + var10, 369098752);
         }

         var21 = var18 + var9;

         for (int var25 = 0; var25 < var3.size(); var25++) {
            this.drawStatCell(var7, (TabStats$4)var3.get(var25), var24, var21, var31, var10);
            var21 += var11[var25] + var8;
         }
      }

      if (!var5.isEmpty()) {
         float var30 = var19 + var9 + var10 * (var4 + 1);
         int var32 = this.getOverlayModule().measureStringWidth(var7, var5);
         this.getOverlayModule().xFhku(var7, var5, var18 + (var12 - var32) / 2.0F, var30 + (var26 - var7.getFontHeight()) / 2.0F, -1, this.textShadow.isToggled());
      }

      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private int dxD6(IFont var1, TabStats$4 var2, TabStats$5 var3, int var4) {
      return var2 != TabStats$4.SKIN && var2 != TabStats$4.SW_SKIN ? this.getOverlayModule().measureStringWidth(var1, this.getCellText(var2, var3)) : Math.max(8, var4 - 4);
   }

   private void drawStatCell(IFont var1, TabStats$4 var2, TabStats$5 var3, float var4, float var5, int var6) {
      if (var2 != TabStats$4.SKIN && var2 != TabStats$4.SW_SKIN) {
         int var9 = var2 != TabStats$4.HP && var2 != TabStats$4.SW_HP ? this.text.getArgb() : this.getHealthColor(TabStats$5.rLaf(var3));
         this.getOverlayModule().xFhku(var1, this.getCellText(var2, var3), var4, var5 + (var6 - var1.getFontHeight()) / 2.0F, var9, this.textShadow.isToggled());
      } else {
         ResourceLocation var7 = SkinCache.getPlayerSkin(TabStats$5.NJzB(var3), TabStats$5.getPlayerInfo(var3));
         mc.getTextureManager().bindTexture(var7);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         int var8 = Math.max(8, var6 - 4);
         Gui.drawScaledCustomSizeModalRect((int)var4, (int)var5 + 2, 8.0F, 8.0F, 8, 8, var8, var8, 64.0F, 64.0F);
         Gui.drawScaledCustomSizeModalRect((int)var4, (int)var5 + 2, 40.0F, 8.0F, 8, 8, var8, var8, 64.0F, 64.0F);
      }
   }

    private String getCellText(jade.client.module.minigames.TabStats$4 var1_1, jade.client.module.minigames.TabStats$5 var2_2) {
        if (var1_1 == jade.client.module.minigames.TabStats$4.NAME || var1_1 == jade.client.module.minigames.TabStats$4.SW_NAME) {
            return var2_2.getColoredName();
        }
        if (var1_1 == jade.client.module.minigames.TabStats$4.TEAM) {
            return var2_2.YPlE();
        }
        if (var1_1 == jade.client.module.minigames.TabStats$4.HP || var1_1 == jade.client.module.minigames.TabStats$4.SW_HP) {
            return Float.isNaN(var2_2.statValue) ? "-" : Integer.toString(Math.max(0, Math.round(var2_2.statValue)));
        }
        if (var1_1 == jade.client.module.minigames.TabStats$4.SESSION || var1_1 == jade.client.module.minigames.TabStats$4.SW_SESSION) {
            return this.getOverlayModule().formatSessionTime(var2_2.statEntry);
        }
        if (var2_2.statEntry == null) {
            return "...";
        }
        switch (var1_1) {
            case RANK: 
            case SW_RANK: {
                return var2_2.statEntry.getRankPrefix().isEmpty() ? "-" : var2_2.statEntry.getRankPrefix();
            }
            case STAR: {
                return this.getOverlayModule().getBedwarsStarDisplay(var2_2.statEntry.getBedwarsLevel());
            }
            case WS: {
                return this.getOverlayModule().getWinstreakDisplay(var2_2.statEntry.getBedwarsWinstreak());
            }
            case FKDR: {
                return this.getOverlayModule().getFkdrDisplay(var2_2.statEntry.getBedwarsFkdr());
            }
            case WLR: {
                return this.getOverlayModule().getWlrDisplay(var2_2.statEntry.getBedwarsWlr());
            }
            case FINALS: {
                return this.getOverlayModule().getFinalsDisplay(var2_2.statEntry.getBedwarsFinalKills());
            }
            case WINS: {
                return this.getOverlayModule().getWinsDisplay(var2_2.statEntry.getBedwarsWins());
            }
            case MONTHLY_FKDR: {
                return this.getOverlayModule().getFkdrDisplay(var2_2.statEntry.getBedwarsMonthlyFkdr());
            }
            case FINALS_PER_STAR: {
                return this.getOverlayModule().TcSth(var2_2.statEntry.getFinalKillsPerStar());
            }
            case SNIPER: 
            case SW_SNIPER: {
                String string = this.getOverlayModule().UjTi8(var2_2.statEntry.getSniperScore(), 1);
                return HypixelPlayerStats.isFiniteNumber(var2_2.statEntry.getSniperScore()) && var2_2.statEntry.getSniperScore() > 0.0 ? "\u00a7c" + string : string;
            }
            case TAGS: 
            case SW_TAGS: {
                return var2_2.statEntry.getCustomTag().isEmpty() ? "-" : var2_2.statEntry.getCustomTag();
            }
            case SW_STAR: {
                return this.getOverlayModule().formatSkywarsStarFromApi(var2_2.statEntry);
            }
            case SW_KDR: {
                return this.getOverlayModule().getSkywarsKdrDisplay(var2_2.statEntry.getSkyWarsKdr());
            }
            case SW_WLR: {
                return this.getOverlayModule().getSkywarsWlrDisplay(var2_2.statEntry.getSkyWarsWlr());
            }
            case SW_KILLS: {
                return this.getOverlayModule().UjTi8(jade.client.module.minigames.tabstats.SkywarsStatParser.oZnyDae(var2_2.statEntry.getDisplayTag()), 0);
            }
            case SW_WINS: {
                return this.getOverlayModule().UjTi8(jade.client.module.minigames.tabstats.SkywarsStatParser.parseWins(var2_2.statEntry.getDisplayTag()), 0);
            }
        }
        return "-";
    }

   private int getHealthColor(float var1) {
      if (Float.isNaN(var1)) {
         return -5592406;
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

   private String getTabFooterSummary() {
      try {
         GuiPlayerTabOverlay var1 = mc.ingameGUI.getTabList();
         IChatComponent var2 = ((IAccessorGuiPlayerTabOverlay)var1).getFooter();
         if (var2 == null) {
            return "";
         }

         String var3 = var2.getFormattedText().replace("\\r", "");

         for (String var7 : var3.split("\\n")) {
            String var8 = EnumChatFormatting.getTextWithoutFormattingCodes(var7);
            if (var8 != null && var8.contains("Kills:") && var8.contains("Final Kills:") && var8.contains("Beds Broken:")) {
               Matcher var9 = Pattern.compile("(?i)Kills:\\s*([^ ]+)\\s+Final Kills:\\s*([^ ]+)\\s+Beds Broken:\\s*([^ ]+)").matcher(var8);
               if (var9.find()) {
                  return "§bKills: §e" + var9.group(1) + "  §bFinal Kills: §e" + var9.group(2) + "  §bBeds Broken: §e" + var9.group(3);
               }
            }
         }
      } catch (Throwable var10) {
      }

      return "";
   }

   private IFont ZWpf() {
      String var1 = this.font.getResolvedFontName();
      return FontManager.getHudRenderer(var1, (float)this.fontSize.getInput());
   }

   private String SHYP(double var1) {
      return HypixelPlayerStats.isFiniteNumber(var1) ? String.valueOf((long)Math.floor(var1)) : "-";
   }

   private String formatDecimalOrDash(double var1) {
      return HypixelPlayerStats.isFiniteNumber(var1) ? String.format(Locale.ROOT, "%.2f", var1) : "-";
   }

   private Overlay getOverlayModule() {
      return Jade.getModuleManager().getModule(Overlay.class);
   }

   public List<TabStats$4> getConfiguredColumns(boolean var1) {
      TabStats$4[] var2 = var1 ? aIye : OQZ;
      StringListSetting var3 = var1 ? this.skyWarsTabColumnOrder : this.bedWarsTabColumnOrder;
      ArrayList var4 = new ArrayList();
      if (var3.getEntries().isEmpty()) {
         Collections.addAll(var4, var2);
      } else {
         for (String var6 : var3.getEntries()) {
            TabStats$4 var7 = TabStats$4.from(var6);
            if (var7 != null && var7.skywars == var1) {
               var4.add(var7);
            }
         }
      }

      if (var4.isEmpty()) {
         Collections.addAll(var4, var2);
      }

      return var4;
   }

   public List<TabStats$4> getAvailableColumns(boolean var1) {
      List var2 = this.getConfiguredColumns(var1);
      ArrayList var3 = new ArrayList();

      for (TabStats$4 var7 : TabStats$4.values()) {
         if (var7.skywars == var1 && !var2.contains(var7)) {
            var3.add(var7);
         }
      }

      return var3;
   }

   public void setColumnOrder(boolean var1, List<TabStats$4> var2) {
      StringListSetting var3 = var1 ? this.skyWarsTabColumnOrder : this.bedWarsTabColumnOrder;
      var3.clearEntries();

      for (TabStats$4 var5 : var2) {
         var3.addEntry(var5.name());
      }
   }

   public FontSetting getFont() {
      return this.font;
   }

   public SliderSetting getFontSize() {
      return this.fontSize;
   }

   public SliderSetting getRowHeight() {
      return this.rowHeight;
   }

   public SliderSetting getPadding() {
      return this.padding;
   }

   public SliderSetting getColumnGap() {
      return this.columnGap;
   }

   public SliderSetting getMaxPlayers() {
      return this.maxPlayers;
   }

   public SliderSetting getRounding() {
      return this.rounding;
   }

   public SliderSetting getSortSetting(boolean var1) {
      return var1 ? this.skyWarsSort : this.bedWarsSort;
   }

   public SliderSetting getDirection() {
      return this.direction;
   }

   public BooleanSetting getTextShadow() {
      return this.textShadow;
   }

   public ColorSetting[] getColorSettings() {
      return new ColorSetting[]{this.panel, this.header, this.text};
   }

   public void RgHc(int var1, boolean var2, float var3) {
      ArrayList var4 = new ArrayList();
      var4.add(new TabStats$5(null, UUID.randomUUID(), "JadePlayer", HypixelPlayerStats.createPlaceholderMvpPlusPlusStats(), 20.0F));
      var4.add(new TabStats$5(null, UUID.randomUUID(), "ExamplePlayer", HypixelPlayerStats.createPlaceholderVipPlusStats(), 13.0F));
      var4.add(new TabStats$5(null, UUID.randomUUID(), "BedDestroyer", HypixelPlayerStats.createPlaceholderMvpPlusPlusStats(), 7.0F));
      this.mnUv(var1, var4, this.getConfiguredColumns(var2), var4.size(), var2 ? "" : "§bKills: §e4  §bFinal Kills: §e2  §bBeds Broken: §e1", var3);
   }

   public void openEditorScreen(boolean var1) {
      mc.displayGuiScreen(new TabStatsScreen(mc.currentScreen, this, var1));
   }

   public static double accessSortValue(TabStats var0, TabStats$5 var1, int var2, boolean var3) {
      return var0.getSortValue(var1, var2, var3);
   }

   public static SliderSetting accessDirection(TabStats var0) {
      return var0.direction;
   }

   static {
      TabStats$4[] var10000 = new TabStats$4[8];
      var10000[0] = TabStats$4.SKIN;
      var10000[1] = TabStats$4.STAR;
      var10000[2] = TabStats$4.NAME;
      var10000[3] = TabStats$4.WS;
      var10000[4] = TabStats$4.FKDR;
      var10000[5] = TabStats$4.FINALS;
      var10000[6] = TabStats$4.MONTHLY_FKDR;
      var10000[7] = TabStats$4.SNIPER;
      OQZ = var10000;
      var10000 = new TabStats$4[8];
      var10000[0] = TabStats$4.SW_SKIN;
      var10000[1] = TabStats$4.SW_STAR;
      var10000[2] = TabStats$4.SW_NAME;
      var10000[3] = TabStats$4.SW_KDR;
      var10000[4] = TabStats$4.SW_WLR;
      var10000[5] = TabStats$4.SW_KILLS;
      var10000[6] = TabStats$4.SW_WINS;
      var10000[7] = TabStats$4.SW_SNIPER;
      aIye = var10000;
   }
}
