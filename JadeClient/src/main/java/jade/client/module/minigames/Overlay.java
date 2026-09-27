// Jade recovery: module: Overlay (minigames); original class: jade.deps.eLz.Bz6LtyS
package jade.client.module.minigames;

import com.mojang.authlib.GameProfile;
import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EXQhDjf8;
import jade.client.common.EventPhase;
import jade.client.common.ExternalRenderBuffer;
import jade.client.common.ExternalRenderer;
import jade.client.common.ExternalSkinTextures;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.FallbackGlyphShapes;
import jade.client.common.PlayerApi$2;
import jade.client.common.PlayerApi;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.common.SkinCache;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.ChatReceivedEvent;
import jade.client.event.EntityJoinWorldEvent;
import jade.client.event.RenderTickEvent;
import jade.client.gui.OverlayEditorScreen;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.minigames.overlay.SkywarsLevelParser;
import jade.client.module.minigames.overlay.StatsFetcher$2;
import jade.client.module.minigames.overlay.StatsFetcher$3;
import jade.client.module.minigames.overlay.StatsFetcher;
import jade.client.module.minigames.overlay.StarGlyphRenderer;
import jade.client.module.other.Denick;
import jade.client.module.shared.FormattedTextRenderer;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.KeySetting;
import jade.client.setting.SliderSetting;
import jade.client.setting.StringListSetting;
import jade.client.setting.TextSetting;

import jade.deps.loader107.HypixelPlayerStats;

import jade.deps.loader107.StatsLookupService;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D.Float;
import java.awt.image.BufferedImage;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings.GameType;
import org.lwjgl.input.Mouse;

@ModuleInfo(aliases = "Overlay Player List")
public class Overlay extends Module implements ExternalRenderableModule {
   private static final long REMOVAL_GRACE_MILLIS = 1500L;
   private static final String[] BEDWARS_SORT_OPTIONS = new String[]{
         "Tab Order",
         "Username",
         "Team",
         "Rank",
         "Star",
         "Winstreak",
         "FKDR",
         "WLR",
         "Finals",
         "Wins",
         "Monthly FKDR",
         "Finals/Star",
         "Sniper Score"
      };
   private static final String[] SKYWARS_SORT_OPTIONS = new String[]{
         "Tab Order",
         "Username",
         "Rank",
         "Star",
         "KDR",
         "WLR",
         "Sniper Score",
         "Kills",
         "Wins"
      };
   private static final String[] omuE = new String[]{"Descending", "Ascending"};
   private static final String[] USERNAME_COLOR_MODES = new String[]{"Team", "Rank", "Static"};
   private static final String[] ckn = new String[]{"Labels", "Short", "Hidden"};
   private static final int lePea = -15314;
   private static final int XAZ = -8947849;
   private static final int COLOR_LIGHT_GRAY = -5592406;
   private static final int onNiry = -43691;
   private static final int COLOR_NICKED_STAT = -5635926;
   static final String zfM = "§c§lNICK";
   static final String NICK_MARKER = "§5*";
   private static final int ICON_IMAGE_SIZE = 64;
   private static final float MAX_CORNER_RADIUS = 4.0F;
   private static final float GRpL = 0.45F;
   private static final String Ypsar = "https://redacted/cubelify?id={{id}}&name={{name}}&sources={{sources}}&key=";
   private static final Pattern BVk = Pattern.compile("^(?:\\[[^\\]]+\\]\\s*)*([A-Za-z0-9_]{2,16})\\s*:");
   private static final Pattern USERNAME_PATTERN = Pattern.compile("(?<![A-Za-z0-9_])([A-Za-z0-9_]{2,16})(?![A-Za-z0-9_])");
   private static final Overlay$18[] wfX82;
   private static final Overlay$18[] SKYWARS_COLUMNS;
   private final GroupSetting columnsGroup;
   private final BooleanSetting skinHead;
   private final BooleanSetting team;
   private final BooleanSetting hypixelRank2;
   private final BooleanSetting star2;
   private final BooleanSetting username3;
   private final BooleanSetting winstreak;
   private final BooleanSetting fkdr;
   private final BooleanSetting wlr;
   private final BooleanSetting finals;
   private final BooleanSetting wins2;
   private final BooleanSetting monthlyFkdr;
   private final BooleanSetting finalsPerStar;
   private final GroupSetting skyWarsColumnsGroup;
   private final BooleanSetting hypixelRank;
   private final BooleanSetting username2;
   private final BooleanSetting star;
   private final BooleanSetting kdr;
   private final BooleanSetting wlr2;
   private final BooleanSetting kills;
   private final BooleanSetting wins;
   private final BooleanSetting sniperScore2;
   private final BooleanSetting sessionTime;
   private final BooleanSetting sniperScore;
   private final BooleanSetting tags;
   private final BooleanSetting hp2;
   private final BooleanSetting hp;
   private final GroupSetting layoutGroup;
   private final SliderSetting x;
   private final SliderSetting y;
   private final SliderSetting skyWarsOverlayX;
   private final SliderSetting skyWarsOverlayY;
   private final FontSetting font;
   private final SliderSetting fontSize;
   private final SliderSetting rowHeight;
   private final SliderSetting xPadding;
   private final SliderSetting yPadding;
   private final SliderSetting columnGap;
   private final SliderSetting maxPlayers;
   private final SliderSetting header;
   private final SliderSetting roundingLevel;
   private final KeySetting hideKey;
   private final GroupSetting automationGroup;
   private final BooleanSetting autoShow;
   private final BooleanSetting autoHide;
   private final SliderSetting hideDelay;
   private final GroupSetting appearanceGroup;
   private final BooleanSetting background2;
   private final BooleanSetting textShadow;
   private final BooleanSetting showSelf;
   private final GroupSetting sortGroup;
   private final SliderSetting sortBy;
   private final SliderSetting direction;
   private final GroupSetting skyWarsSortGroup;
   private final SliderSetting sortBy2;
   private final SliderSetting direction2;
   private final GroupSetting customGroup;
   private final BooleanSetting custom;
   private final TextSetting key;
   private final GroupSetting customAntisniperGroup;
   private final BooleanSetting customAntisniper;
   private final TextSetting customUrl;
   private final GroupSetting colorsGroup;
   private final SliderSetting usernameColor;
   private final ColorSetting background;
   private final ColorSetting header2;
   private final ColorSetting text;
   private final ColorSetting username;
   private final StringListSetting overlayColumnOrder;
   private final StringListSetting skyWarsOverlayColumnOrder;
   private final Map<UUID, Overlay$14> trackedPlayers = new LinkedHashMap<>();
   private final Set<String> OkM0 = new HashSet<>();
   private final Set<String> manualPlayers = new HashSet<>();
   private final Set<String> pendingManualLookups = new HashSet<>();
   private final Set<String> TlZyg = new HashSet<>();
   private final Set<String> pendingGameLookups = new HashSet<>();
   private final Map<UUID, Set<String>> kFz = new LinkedHashMap<>();
   private final Map<UUID, Long> pendingRemovalSince = new LinkedHashMap<>();
   private boolean xwpuU3;
   private boolean manuallyHidden;
   private int lastSkyWarsBoardType = -1;
   private long lastInGameAtMillis;
   private float visibilityAlpha;
   private long lastAlphaUpdateMillis = System.currentTimeMillis();
   private String lastEndpoint = "";
   private long tempShowUntilMillis;
   private final Map<String, ResourceLocation> iconTextureCache = new LinkedHashMap<>();

   public Overlay() {
      super("Overlay", Category.minigames);
      this.registerSetting(new BooleanSetting("BedWars overlay editor", new Runnable() {
         @Override
         public void run() {
            Overlay.openEditorScreen(Overlay.this, false);
         }
      }).setButtonText("EDIT"));
      this.registerSetting(new BooleanSetting("SkyWars overlay editor", new Runnable() {
         @Override
         public void run() {
            Overlay.openEditorScreen(Overlay.this, true);
         }
      }).setButtonText("EDIT"));
      this.registerSetting(this.columnsGroup = new GroupSetting("Columns"));
      this.registerSetting(
         this.skinHead = new BooleanSetting(
            this.columnsGroup, "Skin Head", true
         )
      );
      this.registerSetting(this.team = new BooleanSetting(this.columnsGroup, "Team", false));
      this.registerSetting(
         this.hypixelRank2 = new BooleanSetting(
            this.columnsGroup, "Hypixel Rank", false
         )
      );
      this.registerSetting(this.star2 = new BooleanSetting(this.columnsGroup, "Star", true, new String[]{"Columns.BW Star", "BW Star"}));
      this.registerSetting(this.username3 = new BooleanSetting(this.columnsGroup, "Username", true));
      this.registerSetting(
         this.winstreak = new BooleanSetting(
            this.columnsGroup,
            "Winstreak",
            true,
            new String[]{"Columns.BW Winstreak", "BW Winstreak"}
         )
      );
      this.registerSetting(
         this.fkdr = new BooleanSetting(
            this.columnsGroup,
            "FKDR",
            true,
            new String[]{"Columns.BW FKDR", "BW FKDR"}
         )
      );
      this.registerSetting(this.wlr = new BooleanSetting(this.columnsGroup, "WLR", true, new String[]{"Columns.BW WLR", "BW WLR"}));
      this.registerSetting(
         this.finals = new BooleanSetting(
            this.columnsGroup,
            "Finals",
            true,
            new String[]{"Columns.BW Finals", "BW Finals"}
         )
      );
      this.registerSetting(
         this.wins2 = new BooleanSetting(
            this.columnsGroup,
            "Wins",
            true,
            new String[]{"Columns.BW Wins", "BW Wins"}
         )
      );
      this.registerSetting(
         this.monthlyFkdr = new BooleanSetting(
            this.columnsGroup,
            "Monthly FKDR",
            true,
            new String[]{"Columns.BW Monthly FKDR", "BW Monthly FKDR"}
         )
      );
      this.registerSetting(
         this.finalsPerStar = new BooleanSetting(
            this.columnsGroup,
            "Finals per star",
            true,
            new String[]{"Columns.BW Finals per star", "BW Finals per star"}
         )
      );
      this.registerSetting(
         this.sessionTime = new BooleanSetting(
            this.columnsGroup, "Session Time", true
         )
      );
      this.registerSetting(this.sniperScore = new BooleanSetting(this.columnsGroup, "Sniper Score", true));
      this.registerSetting(this.tags = new BooleanSetting(this.columnsGroup, "Tags", true));
      this.registerSetting(this.hp2 = new BooleanSetting(this.columnsGroup, "HP", false));
      this.registerSetting(this.layoutGroup = new GroupSetting("Layout"));
      this.registerSetting(
         this.x = new SliderSetting(
            this.layoutGroup, "X", "%", 2.0, 0.0, 100.0, 1.0
         )
      );
      this.registerSetting(
         this.y = new SliderSetting(
            this.layoutGroup, "Y", "%", 8.0, 0.0, 100.0, 1.0
         )
      );
      this.registerSetting(
         this.skyWarsOverlayX = new SliderSetting(
            "SkyWars overlay X", "%", 2.0, 0.0, 100.0, 1.0
         )
      );
      this.registerSetting(
         this.skyWarsOverlayY = new SliderSetting(
            "SkyWars overlay Y", "%", 8.0, 0.0, 100.0, 1.0
         )
      );
      this.x.visible = false;
      this.y.visible = false;
      this.skyWarsOverlayX.visible = false;
      this.skyWarsOverlayY.visible = false;
      this.registerSetting(
         this.font = new FontSetting(
            this.layoutGroup, "Font", "Modern"
         )
      );
      this.registerSetting(this.fontSize = new SliderSetting(this.layoutGroup, "Font size", 0.75, 0.5, 2.0, 0.05));
      this.registerSetting(this.rowHeight = new SliderSetting(this.layoutGroup, "Row height", 11.0, 9.0, 28.0, 1.0));
      this.registerSetting(this.xPadding = new SliderSetting(this.layoutGroup, "X padding", 6.0, 1.0, 18.0, 1.0));
      this.registerSetting(this.yPadding = new SliderSetting(this.layoutGroup, "Y padding", 2.0, 1.0, 18.0, 1.0));
      this.registerSetting(this.columnGap = new SliderSetting(this.layoutGroup, "Column gap", 5.0, 2.0, 24.0, 1.0));
      this.registerSetting(this.maxPlayers = new SliderSetting(this.layoutGroup, "Max players", 24.0, 1.0, 80.0, 1.0));
      this.registerSetting(
         this.header = new SliderSetting(
            this.layoutGroup, "Header", 1, ckn
         )
      );
      this.registerSetting(this.roundingLevel = new SliderSetting(this.layoutGroup, "Rounding level", 0.0, 0.0, 12.0, 0.5));
      this.registerSetting(
         this.hideKey = new KeySetting(
            this.layoutGroup, "Hide key", 207
         )
      );
      this.registerSetting(this.automationGroup = new GroupSetting("Automation"));
      this.registerSetting(
         this.autoShow = new BooleanSetting(
            this.automationGroup, "Auto-Show", true
         )
      );
      this.registerSetting(
         this.autoHide = new BooleanSetting(
            this.automationGroup, "Auto-Hide", true
         )
      );
      this.registerSetting(
         this.hideDelay = new SliderSetting(
            this.automationGroup, "Hide delay", "s", 15.0, 1.0, 60.0, 1.0
         )
      );
      this.registerSetting(this.appearanceGroup = new GroupSetting("Appearance"));
      this.registerSetting(
         this.background2 = new BooleanSetting(
            this.appearanceGroup, "Background", true
         )
      );
      this.registerSetting(
         this.textShadow = new BooleanSetting(
            this.appearanceGroup,
            "Text shadow",
            true
         )
      );
      this.registerSetting(
         this.showSelf = new BooleanSetting(
            this.appearanceGroup, "Show self", true
         )
      );
      this.registerSetting(this.sortGroup = new GroupSetting("Sort"));
      this.registerSetting(this.sortBy = new SliderSetting(this.sortGroup, "Sort by", 0, BEDWARS_SORT_OPTIONS));
      this.registerSetting(
         this.direction = new SliderSetting(
            this.sortGroup, "Direction", 0, omuE
         )
      );
      this.registerSetting(this.skyWarsSortGroup = new GroupSetting("SkyWars Sort"));
      this.registerSetting(this.sortBy2 = new SliderSetting(this.skyWarsSortGroup, "Sort by", 0, SKYWARS_SORT_OPTIONS));
      this.registerSetting(this.direction2 = new SliderSetting(this.skyWarsSortGroup, "Direction", 0, omuE));
      this.registerSetting(this.skyWarsColumnsGroup = new GroupSetting("SkyWars Columns"));
      this.registerSetting(
         this.star = new BooleanSetting(
            this.skyWarsColumnsGroup,
            "Star",
            true,
            new String[]{"Columns.SW Star", "SW Star"}
         )
      );
      this.registerSetting(
         this.hypixelRank = new BooleanSetting(
            this.skyWarsColumnsGroup,
            "Hypixel Rank",
            true
         )
      );
      this.registerSetting(
         this.username2 = new BooleanSetting(
            this.skyWarsColumnsGroup, "Username", true
         )
      );
      this.registerSetting(this.kdr = new BooleanSetting(this.skyWarsColumnsGroup, "KDR", true, new String[]{"Columns.SW KDR", "SW KDR"}));
      this.registerSetting(
         this.wlr2 = new BooleanSetting(
            this.skyWarsColumnsGroup,
            "WLR",
            true,
            new String[]{"Columns.SW WLR", "SW WLR"}
         )
      );
      this.registerSetting(this.kills = new BooleanSetting(this.skyWarsColumnsGroup, "Kills", true, new String[]{"Columns.SW Kills", "SW Kills"}));
      this.registerSetting(
         this.wins = new BooleanSetting(
            this.skyWarsColumnsGroup,
            "Wins",
            true,
            new String[]{"Columns.SW Wins", "SW Wins"}
         )
      );
      this.registerSetting(
         this.sniperScore2 = new BooleanSetting(
            this.skyWarsColumnsGroup,
            "Sniper Score",
            true
         )
      );
      this.registerSetting(
         this.hp = new BooleanSetting(
            this.skyWarsColumnsGroup, "HP", false
         )
      );
      this.registerSetting(this.customGroup = new GroupSetting("Custom"));
      this.registerSetting(
         this.custom = new BooleanSetting(
               this.customGroup, "Custom", false
            )
            .onChange(new Runnable() {
               @Override
               public void run() {
                  Overlay.LNWCfr5(Overlay.this);
                  Overlay.sJd0(Overlay.this);
               }
            })
      );
      this.registerSetting(
         this.key = new TextSetting(
            this.customGroup,
            "Key",
            "",
            "Custom API key...",
            256,
            new Runnable() {
               @Override
               public void run() {
                  Overlay.LNWCfr5(Overlay.this);
               }
            }
         )
      );
      this.registerSetting(this.customAntisniperGroup = new GroupSetting("Custom"));
      this.registerSetting(
         this.customAntisniper = new BooleanSetting(
               this.customAntisniperGroup,
               "Custom Antisniper",
               false
            )
            .onChange(new Runnable() {
               @Override
               public void run() {
                  Overlay.LNWCfr5(Overlay.this);
                  Overlay.sJd0(Overlay.this);
               }
            })
      );
      this.registerSetting(
         this.customUrl = new TextSetting(
            this.customAntisniperGroup,
            "Custom URL",
            "",
            "Custom URL...",
            1024,
            new Runnable() {
               @Override
               public void run() {
                  Overlay.LNWCfr5(Overlay.this);
               }
            },
            new String[]{"Custom Anti-Sniper.URL", "Anti-Sniper.URL"}
         )
      );
      this.ISyCp();
      this.registerSetting(this.colorsGroup = new GroupSetting("Colors"));
      this.registerSetting(this.usernameColor = new SliderSetting(this.colorsGroup, "Username color", 0, USERNAME_COLOR_MODES));
      this.registerSetting(
         this.background = new ColorSetting(
            this.colorsGroup,
            "Background",
            0,
            0,
            0,
            165
         )
      );
      this.registerSetting(
         this.header2 = new ColorSetting(
            this.colorsGroup,
            "Header",
            20,
            20,
            20,
            255
         )
      );
      this.registerSetting(
         this.text = new ColorSetting(
            this.colorsGroup,
            "Text",
            232,
            235,
            241
         )
      );
      this.registerSetting(
         this.username = new ColorSetting(
            this.colorsGroup,
            "Username",
            255,
            255,
            255
         )
      );
      this.registerSetting(
         this.overlayColumnOrder = new StringListSetting(
            "Overlay column order", "", 32
         )
      );
      this.registerSetting(
         this.skyWarsOverlayColumnOrder = new StringListSetting(
            "SkyWars overlay column order",
            "",
            16
         )
      );
      this.columnsGroup.visible = false;
      this.skyWarsColumnsGroup.visible = false;
      this.layoutGroup.visible = false;
      this.appearanceGroup.visible = false;
      this.sortGroup.visible = false;
      this.skyWarsSortGroup.visible = false;
      this.colorsGroup.visible = false;
      this.skinHead.visible = false;
      this.team.visible = false;
      this.hypixelRank2.visible = false;
      this.star2.visible = false;
      this.username3.visible = false;
      this.winstreak.visible = false;
      this.fkdr.visible = false;
      this.wlr.visible = false;
      this.finals.visible = false;
      this.wins2.visible = false;
      this.monthlyFkdr.visible = false;
      this.finalsPerStar.visible = false;
      this.star.visible = false;
      this.kdr.visible = false;
      this.wlr2.visible = false;
      this.kills.visible = false;
      this.wins.visible = false;
      this.hypixelRank.visible = false;
      this.username2.visible = false;
      this.sniperScore2.visible = false;
      this.sessionTime.visible = false;
      this.sniperScore.visible = false;
      this.tags.visible = false;
      this.hp2.visible = false;
      this.hp.visible = false;
      this.background2.visible = false;
      this.textShadow.visible = false;
      this.showSelf.visible = false;
      this.sortBy.visible = false;
      this.direction.visible = false;
      this.sortBy2.visible = false;
      this.direction2.visible = false;
      this.usernameColor.visible = false;
      this.background.visible = false;
      this.header2.visible = false;
      this.text.visible = false;
      this.username.visible = false;
      this.overlayColumnOrder.visible = false;
      this.skyWarsOverlayColumnOrder.visible = false;
      this.font.visible = false;
      this.fontSize.visible = false;
      this.rowHeight.visible = false;
      this.xPadding.visible = false;
      this.yPadding.visible = false;
      this.columnGap.visible = false;
      this.maxPlayers.visible = false;
      this.header.visible = false;
      this.roundingLevel.visible = false;
      this.layoutGroup.setExpanded(true);
   }

   @Override
   public void onDisable() {
      StatsLookupService.getInstance().disconnect();
      this.trackedPlayers.clear();
      this.OkM0.clear();
      this.manualPlayers.clear();
      this.pendingManualLookups.clear();
      this.TlZyg.clear();
      this.pendingGameLookups.clear();
      this.kFz.clear();
      this.pendingRemovalSince.clear();
      StatsFetcher.getInstance().clearRequestState();
      this.xwpuU3 = false;
      this.manuallyHidden = false;
      this.lastSkyWarsBoardType = -1;
      this.lastInGameAtMillis = 0L;
      this.visibilityAlpha = 0.0F;
      this.tempShowUntilMillis = 0L;
   }

   @Override
   public void guiUpdate() {
      if (this.key != null) {
         this.key.setVisible(this.custom != null && this.custom.isToggled(), this);
      }

      if (this.customUrl != null) {
         this.customUrl.setVisible(this.customAntisniper != null && this.customAntisniper.isToggled(), this);
      }

      if (this.hideDelay != null) {
         this.hideDelay.setVisible(this.autoShow != null && this.autoShow.isToggled() && this.autoHide != null && this.autoHide.isToggled(), this);
      }
   }

   @Override
   public void onUpdate() {
      boolean var1 = this.hideKey.isHeldDown();
      if (var1 && !this.xwpuU3) {
         if (this.tempShowUntilMillis > System.currentTimeMillis()) {
            this.tempShowUntilMillis = 0L;
            this.manuallyHidden = true;
         } else {
            this.manuallyHidden = !this.manuallyHidden;
         }
      }

      this.xwpuU3 = var1;
      if (ClientUtils.isInWorld()) {
         this.refreshTrackedPlayers();
      }
   }

   @Subscribe
   public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
      if (var1.entity == mc.thePlayer) {
         this.OkM0.clear();
         this.TlZyg.clear();
         this.pendingGameLookups.clear();
         this.pendingRemovalSince.clear();
         this.tempShowUntilMillis = 0L;
         this.lastSkyWarsBoardType = -1;
         this.lastInGameAtMillis = 0L;
         this.SONh();
      }
   }

   @Subscribe
   public void onChatReceived(ChatReceivedEvent var1) {
      if (var1.messageType != 2 && ClientUtils.isInWorld()) {
         String var2 = ClientUtils.AOAtn(var1.iChatComponent.getUnformattedText());
         if (var2 != null) {
            if (var2.startsWith("ONLINE: ")) {
               this.OkM0.clear();
               if (this.parseOnlineList(var2.substring("ONLINE: ".length())) > 0) {
                  this.showTemporarily();
               }

               this.refreshTrackedPlayers();
            } else {
               if (ClientUtils.getBedWarsBoardType() == 1) {
                  this.trackPlayersFromChat(var2);
               }
            }
         }
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END && ClientUtils.isInWorld() && !mc.gameSettings.showDebugInfo) {
         this.renderOverlay();
      }
   }

   private void renderOverlay() {
      List var1 = this.getTrackedPlayers();
      boolean var2 = var1.isEmpty();
      boolean var3 = this.manuallyHidden && this.tempShowUntilMillis > System.currentTimeMillis();
      int var4 = ClientUtils.getSkyWarsBoardType();
      this.updateVisibilityAlpha(this.manuallyHidden && !var3 ? 0.0F : 1.0F);
      if (!(this.visibilityAlpha <= 0.01F)) {
         if (!var2) {
            StatsLookupService.getInstance().tick();
            this.preloadPlayerSkins(var1);
            this.fetchStatsForPlayers(var1);
         }

         boolean var5 = var4 != -1;
         this.oBei(var1, var5);
         int var6 = Math.min(var1.size(), (int)this.maxPlayers.getInput());
         List var7 = this.buildColumnDescriptors(var5);
         Overlay$13 var8 = this.computeOverlayLayout(var7, var1, var6);
         ScaledResolution var9 = new ScaledResolution(mc);
         int var10 = var9.getScaledWidth();
         int var11 = var9.getScaledHeight();
         float var12 = var10 * (float)(var5 ? this.skyWarsOverlayX : this.x).getInput() / 100.0F;
         float var13 = var11 * (float)(var5 ? this.skyWarsOverlayY : this.y).getInput() / 100.0F;
         var12 = Math.max(2.0F, Math.min(var10 - Overlay$13.getWidth(var8) - 2.0F, var12));
         var13 = Math.max(2.0F, Math.min(var11 - Overlay$13.getHeight(var8) - 2.0F, var13));
         if (this.VIuQ()) {
            this.drawOverlayBuffered(var12, var13, var8, var7, var1, var6, var5);
         } else {
            this.drawOverlay(var12, var13, var8, var7, var1, var6, var5);
         }
      }
   }

   public float getOverlayPosX() {
      SliderSetting var1 = this.BZEGl() ? this.skyWarsOverlayX : this.x;
      return (float)var1.getInput() / 100.0F * new ScaledResolution(mc).getScaledWidth();
   }

   public float getOverlayPosY() {
      SliderSetting var1 = this.BZEGl() ? this.skyWarsOverlayY : this.y;
      return (float)var1.getInput() / 100.0F * new ScaledResolution(mc).getScaledHeight();
   }

   public void setOverlayPosition(float var1, float var2) {
      SliderSetting var3 = this.BZEGl() ? this.skyWarsOverlayX : this.x;
      SliderSetting var4 = this.BZEGl() ? this.skyWarsOverlayY : this.y;
      var3.setValueClamped(Math.max(0.0, Math.min(100.0, (double)(var1 * 100.0F))));
      var4.setValueClamped(Math.max(0.0, Math.min(100.0, (double)(var2 * 100.0F))));
   }

   public void resetOverlayPosition() {
      this.setOverlayPosition(0.02F, 0.08F);
   }

   public SliderSetting getFontSize() {
      return this.fontSize;
   }

   public float[] getOverlayPreviewBounds(float var1, float var2) {
      boolean var3 = this.BZEGl();
      List var4 = this.buildPlayerEntries(var3);
      int var5 = Math.min(var4.size(), Math.min(3, (int)this.maxPlayers.getInput()));
      List var6 = this.buildColumnDescriptors(var3);
      Overlay$13 var7 = this.computeOverlayLayout(var6, var4, var5);
      float var8 = this.visibilityAlpha;
      this.visibilityAlpha = 1.0F;
      this.drawOverlay(var1, var2, var7, var6, var4, var5, var3);
      this.visibilityAlpha = var8;
      return new float[]{var1, var2, var1 + Overlay$13.getWidth(var7), var2 + Overlay$13.getHeight(var7)};
   }

   public Overlay$12 wyJ5(float var1, float var2, float var3, boolean var4) {
      float var5 = Math.max(0.5F, var3);
      List var6 = this.buildPlayerEntries(var4);
      int var7 = Math.min(var6.size(), Math.min(3, (int)this.maxPlayers.getInput()));
      List var8 = this.buildColumnDescriptors(var4);
      Overlay$13 var9 = this.computeOverlayLayout(var8, var6, var7);
      float var10 = var1 / var5;
      float var11 = var2 / var5;
      float var12 = this.visibilityAlpha;
      this.visibilityAlpha = 1.0F;
      boolean var13 = false;

      try {
         GlStateManager.pushMatrix();
         var13 = true;
         GlStateManager.scale(var5, var5, 1.0F);
         this.drawOverlay(var10, var11, var9, var8, var6, var7, var4);
      } finally {
         if (var13) {
            GlStateManager.popMatrix();
         }

         this.visibilityAlpha = var12;
      }

      float var14 = (Overlay$13.getHeaderHeight(var9) > 0 ? var11 + Overlay$13.getPaddingY(var9) : var11 - 18.0F) * var5;
      int var15 = Math.round((Overlay$13.getHeaderHeight(var9) > 0 ? Math.max(12, Overlay$13.getFont(var9).getFontHeight() + 4) : 16) * var5);
      ArrayList var16 = new ArrayList();
      float var17 = var10 + Overlay$13.getPaddingX(var9);

      for (int var18 = 0; var18 < var8.size(); var18++) {
         Overlay$10 var19 = (Overlay$10)var8.get(var18);
         var16.add(
            new Overlay$11(
               Overlay$10.getColumnType(var19).name(), Overlay$10.getColumnLabel(var19), var17 * var5, var14, Math.round(Overlay$13.JxeeY(var9)[var18] * var5), var15
            )
         );
         var17 += Overlay$13.JxeeY(var9)[var18] + Overlay$13.getColumnGap(var9);
      }

      float var22 = (var11 + Overlay$13.getPaddingY(var9) + Overlay$13.getHeaderHeight(var9)) * var5;
      return new Overlay$12(
         new float[]{var1, var2, var1 + Overlay$13.getWidth(var9) * var5, var2 + Overlay$13.getHeight(var9) * var5},
         var16,
         var14,
         var15,
         var22,
         Math.round(Overlay$13.getRowHeight(var9) * var5),
         Math.round(Overlay$13.getPaddingX(var9) * var5),
         Math.round(Overlay$13.getPaddingY(var9) * var5)
      );
   }

   public SliderSetting[] EIAJ(boolean var1) {
      return new SliderSetting[]{
         this.font,
         this.rowHeight,
         this.xPadding,
         this.yPadding,
         this.columnGap,
         this.maxPlayers,
         this.header,
         this.roundingLevel,
         var1 ? this.sortBy2 : this.sortBy,
         var1 ? this.direction2 : this.direction,
         this.usernameColor
      };
   }

   public BooleanSetting[] getAppearanceToggles() {
      return new BooleanSetting[]{this.background2, this.textShadow, this.showSelf};
   }

   public ColorSetting[] RmynT() {
      return new ColorSetting[]{this.background, this.header2, this.text, this.username};
   }

   public List<String> DBxswe6(boolean var1) {
      ArrayList var2 = new ArrayList();

      for (Overlay$18 var4 : this.getVisibleColumns(var1)) {
         var2.add(var4.name());
      }

      return var2;
   }

   public List<String> getHiddenColumnNames(boolean var1) {
      ArrayList var2 = new ArrayList();

      for (Overlay$18 var4 : this.getAllColumns(var1)) {
         if (!this.getColumnToggle(var4).isToggled()) {
            var2.add(var4.name());
         }
      }

      return var2;
   }

   public String getColumnDisplayName(String var1) {
      Overlay$18 var2 = this.parseColumnName(var1);
      return var2 == null ? "" : this.getColumnTitle(var2);
   }

   public void umhzj(String var1, int var2, boolean var3) {
      Overlay$18 var4 = this.parseColumnName(var1);
      if (var4 != null) {
         this.getColumnToggle(var4).enable();
         this.reorderColumn(var4, var2, var3);
      }
   }

   public void disableColumn(String var1, boolean var2) {
      Overlay$18 var3 = this.parseColumnName(var1);
      if (var3 != null && (var3 != Overlay$18.NAME && var3 != Overlay$18.SW_NAME || this.getVisibleColumns(var2).size() > 1)) {
         if (this.getVisibleColumns(var2).size() > 1) {
            this.getColumnToggle(var3).disable();
         }
      }
   }

   public void moveColumnIfEnabled(String var1, int var2, boolean var3) {
      Overlay$18 var4 = this.parseColumnName(var1);
      if (var4 != null && this.getColumnToggle(var4).isToggled()) {
         this.reorderColumn(var4, var2, var3);
      }
   }

   public float[] HPmA(float var1, boolean var2) {
      float var3 = Math.max(0.5F, var1);
      List var4 = this.buildPlayerEntries(var2);
      int var5 = Math.min(var4.size(), Math.min(3, (int)this.maxPlayers.getInput()));
      Overlay$13 var6 = this.computeOverlayLayout(this.buildColumnDescriptors(var2), var4, var5);
      return new float[]{Overlay$13.getWidth(var6) * var3, Overlay$13.getHeight(var6) * var3};
   }

   private void reorderColumn(Overlay$18 var1, int var2, boolean var3) {
      ArrayList var4 = new ArrayList<>(this.getVisibleColumns(var3));
      var4.remove(var1);
      var4.add(Math.max(0, Math.min(var4.size(), var2)), var1);
      ArrayList var5 = new ArrayList(var4);

      for (Overlay$18 var7 : this.getAllColumns(var3)) {
         if (!var5.contains(var7)) {
            var5.add(var7);
         }
      }

      this.saveColumnOrder(var5, var3);
   }

   private boolean BZEGl() {
      return ClientUtils.isInWorld() && ClientUtils.getSkyWarsBoardType() != -1;
   }

   private void VCnge(boolean var1) {
      this.ensureDefaultColumns(var1);
      mc.displayGuiScreen(new OverlayEditorScreen(mc.currentScreen, this, var1));
   }

   private void ensureDefaultColumns(boolean var1) {
      if (this.getVisibleColumns(var1).isEmpty()) {
         Overlay$18[] var2 = var1 ? SKYWARS_COLUMNS : wfX82;
         ArrayList var3 = new ArrayList();

         for (Overlay$18 var7 : var2) {
            this.getColumnToggle(var7).enable();
            var3.add(var7);
         }

         this.saveColumnOrder(var3, var1);
      }
   }

   public void SSXt(boolean var1) {
      this.ensureDefaultColumns(var1);
   }

   private IFont getOverlayFont() {
      return FontManager.getHudRenderer(this.font.getResolvedFontName(), (float)this.fontSize.getInput());
   }

   private boolean isCustomFont() {
      return !FontManager.isMinecraftFont(this.font.getResolvedFontName());
   }

   public String getBedwarsStarDisplay(double var1) {
      return this.WDkp(var1);
   }

   public String getWinstreakDisplay(double var1) {
      return this.formatWinstreakValue(var1);
   }

   public String getFkdrDisplay(double var1) {
      return this.formatFkdrValue(var1);
   }

   public String getWlrDisplay(double var1) {
      return this.formatWlrValue(var1);
   }

   public String getFinalsDisplay(double var1) {
      return this.formatFinalsValue(var1);
   }

   public String getWinsDisplay(double var1) {
      return this.formatWinsValue(var1);
   }

   public String TcSth(double var1) {
      return this.formatFinalsPerStarValue(var1);
   }

   public String getSkywarsKdrDisplay(double var1) {
      return this.UThNqd(var1);
   }

   public String getSkywarsWlrDisplay(double var1) {
      return this.SUSe(var1);
   }

   public String UjTi8(double var1, int var3) {
      return this.formatStatNumber(var1, var3);
   }

   public String formatSkywarsStarFromApi(HypixelPlayerStats var1) {
      if (var1 == null) {
         return "-";
      } else {
         String var2 = SkywarsLevelParser.Sg45(var1.getDisplayTag());
         if (!var2.isEmpty()) {
            return var2;
         } else {
            double var3 = var1.getSkyWarsLevel();
            String var5 = this.formatStatNumber(var3, var3 == Math.rint(var3) ? 0 : 2);
            return "-".equals(var5) ? var5 : "§7[§7" + var5 + "§7✯§7]§r";
         }
      }
   }

   public int measureStringWidth(IFont var1, String var2) {
      return this.zgyrI(var1, var2);
   }

   public void xFhku(IFont var1, String var2, float var3, float var4, int var5, boolean var6) {
      int var7 = this.findIconIndex(var2);
      if (var7 >= 0) {
         String var8 = var2.substring(0, var7);
         int var9 = Character.codePointAt(var2, var7);
         int var10 = Character.charCount(var9);
         String var11 = var2.substring(var7 + var10);
         var1.drawString(var8, var3, var4, var5, var6);
         float var12 = var3 + this.zgyrI(var1, var8);
         int var13 = this.measureCodepointWidth(var1, var9);
         float var14 = var4 + Math.max(0.0F, (var1.getFontHeight() - var13) / 2.0F) - 0.35F;
         this.WagRa(var1, var8, var9, var12, var14, var5);
         var1.drawString(var11, var12 + var13, var4, var5, var6);
      } else {
         var1.drawString(var2, var3, var4, var5, var6);
      }
   }

   private void updateVisibilityAlpha(float var1) {
      long var2 = System.currentTimeMillis();
      float var4 = Math.min(0.05F, Math.max(0.0F, (float)(var2 - this.lastAlphaUpdateMillis) / 1000.0F));
      this.lastAlphaUpdateMillis = var2;
      this.visibilityAlpha = this.UOqV(this.visibilityAlpha, var1, var4, 11.0F);
   }

   private float kWeb() {
      float var1 = 1.0F - Math.max(0.0F, Math.min(1.0F, this.visibilityAlpha));
      return 1.0F - var1 * var1 * var1;
   }

   private float UOqV(float var1, float var2, float var3, float var4) {
      if (Math.abs(var1 - var2) < 0.001F) {
         return var2;
      } else {
         float var5 = 1.0F - (float)Math.pow(2.0, -var4 * var3);
         return var1 + (var2 - var1) * var5;
      }
   }

   private int applyAlphaScale(int var1, int var2) {
      int var3 = var1 >>> 24 & 0xFF;
      return ClientUtils.YVVZ(var1, Math.round(var3 * Math.max(0, Math.min(255, var2)) / 255.0F));
   }

   private void preloadPlayerSkins(List<Overlay$16> var1) {
      int var2 = 16;

      for (int var3 = 0; var3 < var1.size() && var2 > 0; var3++) {
         if (Overlay$16.getStats((Overlay$16)var1.get(var3)) == null) {
            StatsLookupService.getInstance()
               .requestStatsForGame(Overlay$16.RlnF2((Overlay$16)var1.get(var3)));
            var2--;
         }
      }
   }

   private void fetchStatsForPlayers(List<Overlay$16> var1) {
      String var2 = this.resolveStatsEndpoint();
      if (!var2.isEmpty()) {
         if (!var2.equals(this.lastEndpoint)) {
            this.MAKN();
            this.lastEndpoint = var2;
         }

         int var3 = 16;

         for (int var4 = 0; var4 < var1.size() && var3 > 0; var4++) {
            Overlay$16 var5 = (Overlay$16)var1.get(var4);
            StatsFetcher.getInstance().requestStats(Overlay$16.RlnF2(var5), Overlay$16.tFvn63(var5), this.ATgq(var5), var2);
            this.logTagReasons(var5, this.tD01(var5));
            var3--;
         }
      }
   }

   private void MAKN() {
      StatsFetcher.getInstance().clearRequestState();
      this.lastEndpoint = this.resolveStatsEndpoint();
   }

   private void ISyCp() {
      if (this.key != null) {
         this.key.visible = this.custom != null && this.custom.isToggled();
      }

      if (this.customUrl != null) {
         this.customUrl.visible = this.customAntisniper != null && this.customAntisniper.isToggled();
      }
   }

   private String resolveStatsEndpoint() {
      if (this.customAntisniper != null && this.customAntisniper.isToggled()) {
         String var1 = this.customUrl == null ? "" : this.customUrl.getValue().trim();
         if (!var1.isEmpty()) {
            return var1;
         }
      }

      if (this.custom != null && this.custom.isToggled()) {
         String var2 = this.key == null ? "" : this.key.getValue().trim();
         if (!var2.isEmpty()) {
            return "https://redacted/cubelify?id={{id}}&name={{name}}&sources={{sources}}&key=" + this.urlEncode(var2);
         }
      }

      return "";
   }

   private String urlEncode(String var1) {
      try {
         return URLEncoder.encode(var1 == null ? "" : var1, "UTF-8").replace("+", "%20");
      } catch (Exception var3) {
         return var1 == null ? "" : var1;
      }
   }

   private boolean isCustomApiEnabled() {
      return this.custom != null && this.custom.isToggled() || this.customAntisniper != null && this.customAntisniper.isToggled();
   }

   private String ATgq(Overlay$16 var1) {
      ArrayList var2 = new ArrayList();
      if (Overlay$16.getPlayerInfo(var1) != null) {
         var2.add("GAME");
      }

      if (this.manualPlayers.contains(this.normalizeName(Overlay$16.tFvn63(var1)))) {
         var2.add("MANUAL");
      }

      if (this.TlZyg.contains(this.normalizeName(Overlay$16.tFvn63(var1))) && !var2.contains("GAME")) {
         var2.add("GAME");
      }

      if (ClientUtils.isInWorld() && Overlay$16.RlnF2(var1).equals(mc.thePlayer.getUniqueID())) {
         var2.add("ME");
      }

      if (var2.isEmpty()) {
         var2.add("GAME");
      }

      StringBuilder var3 = new StringBuilder();

      for (int var4 = 0; var4 < var2.size(); var4++) {
         if (var4 > 0) {
            var3.append(',');
         }

         var3.append((String)var2.get(var4));
      }

      return var3.toString();
   }

   private void logTagReasons(Overlay$16 var1, StatsFetcher$2 var2) {
      if (var2 != null && !var2.getTags().isEmpty()) {
         if (!this.manualPlayers.contains(this.normalizeName(Overlay$16.tFvn63(var1))) || Overlay$16.getStats(var1) != null) {
            Set<String> var3 = this.kFz.get(Overlay$16.RlnF2(var1));
            if (var3 == null) {
               var3 = new HashSet();
               this.kFz.put(Overlay$16.RlnF2(var1), (Set<String>)var3);
            }

            for (StatsFetcher$3 var5 : var2.getTags()) {
               String var6 = this.extractTagReason(var5.Uv1);
               if (!var6.isEmpty() && var3.add(var6)) {
                  ClientUtils.sendJadeMessage("Jade", "&7" + this.getNearestColorCode(this.getLogNameColor(var1)) + Overlay$16.tFvn63(var1) + "&7 tag reason: &f" + var6);
               }
            }
         }
      }
   }

   private List<Overlay$16> getTrackedPlayers() {
      return this.resolveTrackedPlayers(new ArrayList<>(this.trackedPlayers.values()));
   }

   private List<Overlay$16> resolveTrackedPlayers(List<Overlay$14> var1) {
      HashMap var2 = new HashMap();
      int var3 = 0;
      Denick var4 = Jade.getModuleManager().getModule(Denick.class);

      for (Overlay$14 var6 : var1) {
         GameProfile var7 = var4 == null ? null : var4.rczQ(Overlay$14.getNetworkPlayerInfo(var6));
         if (var7 != null) {
            Overlay$16 var8 = new Overlay$16(
               this,
               Overlay$14.getNetworkPlayerInfo(var6),
               var7.getId(),
               var7.getName(),
               Overlay$14.csso(var6),
               StatsLookupService.getInstance().getCachedStats(var7.getId()),
               var3
            );
            var2.put(this.normalizeName(Overlay$14.RXus(var6)), var8);
            var2.put(this.normalizeName(var7.getName()), var8);
         }

         var3++;
      }

      LinkedHashMap var10 = new LinkedHashMap();
      var3 = 0;

      for (Overlay$14 var12 : var1) {
         Overlay$16 var13 = (Overlay$16)var2.get(this.normalizeName(Overlay$14.RXus(var12)));
         if (var13 == null) {
            var13 = new Overlay$16(
               this,
               Overlay$14.getNetworkPlayerInfo(var12),
               Overlay$14.bpdZ(var12),
               Overlay$14.RXus(var12),
               Overlay$14.csso(var12),
               StatsLookupService.getInstance().getCachedStats(Overlay$14.bpdZ(var12)),
               var3
            );
         }

         var10.put(Overlay$16.RlnF2(var13), var13);
         var3++;
      }

      return new ArrayList<>(var10.values());
   }

   private List<Overlay$16> buildPlayerEntries(boolean var1) {
      ArrayList var2 = new ArrayList();
      if (ClientUtils.isInWorld()) {
         UUID var3 = mc.thePlayer.getUniqueID();
         HypixelPlayerStats var4 = StatsLookupService.getInstance().getCachedStats(var3);
         String var5 = mc.thePlayer.getName();
         var2.add(
            new Overlay$16(
               this,
               null,
               var3,
               var5,
               System.currentTimeMillis() - 185000L,
               var4 == null ? HypixelPlayerStats.createPlaceholderMvpPlusPlusStats() : var4,
               0
            )
         );
      }

      if (!var2.isEmpty()) {
         var2.add(
            new Overlay$16(
               this,
               null,
               new UUID(0L, 2L),
               var1 ? "SkywarsPlayer" : "BedwarsQueue",
               System.currentTimeMillis() - 42000L,
               HypixelPlayerStats.createPlaceholderVipPlusStats(),
               1
            )
         );
         return var2;
      } else {
         return this.buildPreviewPlayers(var1);
      }
   }

   private List<Overlay$16> buildPreviewPlayers(boolean var1) {
      ArrayList var2 = new ArrayList();
      var2.add(
         new Overlay$16(
            this,
            null,
            new UUID(0L, 1L),
            "SelfPlayer",
            System.currentTimeMillis() - 185000L,
            HypixelPlayerStats.createPlaceholderMvpPlusPlusStats(),
            0
         )
      );
      var2.add(
         new Overlay$16(
            this,
            null,
            new UUID(0L, 2L),
            var1 ? "SkywarsPlayer" : "BedwarsQueue",
            System.currentTimeMillis() - 42000L,
            HypixelPlayerStats.createPlaceholderVipPlusStats(),
            1
         )
      );
      return var2;
   }

   private void refreshTrackedPlayers() {
      int var1 = ClientUtils.getBedWarsBoardType();
      int var2 = ClientUtils.getSkyWarsBoardType();
      long var3 = System.currentTimeMillis();
      boolean var5 = var2 == 1 || var2 == 2;
      if (var5) {
         this.lastInGameAtMillis = var3;
      }

      boolean var6 = var2 == 0 && this.lastSkyWarsBoardType == 1 && this.lastInGameAtMillis > 0L && var3 - this.lastInGameAtMillis <= 5000L;
      boolean var7 = var5 || var6;
      boolean var8 = var5 && this.lastSkyWarsBoardType != 1 && this.lastSkyWarsBoardType != 2;
      boolean var9 = var2 == 2 && this.lastSkyWarsBoardType != 2;
      if (var5) {
         this.lastSkyWarsBoardType = var2;
      } else if (!var6) {
         this.lastSkyWarsBoardType = -1;
         this.lastInGameAtMillis = 0L;
      }

      if (!var6) {
         if (var1 < 1 && !var7) {
            this.OkM0.clear();
            this.TlZyg.clear();
            this.pendingGameLookups.clear();
            this.pendingRemovalSince.clear();
            this.tempShowUntilMillis = 0L;
            if (this.manualPlayers.isEmpty()) {
               this.trackedPlayers.clear();
               return;
            }
         }

         HashSet var10 = new HashSet();

         for (NetworkPlayerInfo var12 : ClientUtils.ibtl3(false)) {
            if ((var2 != 2 || var12 == null || var12.getGameType() != GameType.SPECTATOR) && this.wbhP(var12, var7)) {
               GameProfile var13 = var12.getGameProfile();
               UUID var14 = var13.getId();
               var10.add(var14);
               this.pendingRemovalSince.remove(var14);
               Overlay$14 var15 = this.trackedPlayers.get(var14);
               if (var15 == null) {
                  this.trackedPlayers.put(var14, new Overlay$14(var12, var14, var13.getName(), System.currentTimeMillis()));
               } else {
                  Overlay$14.setNetworkPlayerInfo(var15, var12);
                  Overlay$14.setPlayerName(var15, var13.getName());
               }
            }
         }

         if (var9 && this.autoShow != null && this.autoShow.isToggled()) {
            this.tempShowUntilMillis = 0L;
            this.manuallyHidden = false;
         } else if (var8) {
            this.showTemporarily();
         }

         if (var2 == 2) {
            Iterator var16 = this.trackedPlayers.entrySet().iterator();

            while (var16.hasNext()) {
               Overlay$14 var18 = (Overlay$14)((Entry)var16.next()).getValue();
               String var20 = this.normalizeName(Overlay$14.RXus(var18));
               if (!var10.contains(Overlay$14.bpdZ(var18)) && !this.manualPlayers.contains(var20) && !this.TlZyg.contains(var20)) {
                  Long var22 = this.pendingRemovalSince.get(Overlay$14.bpdZ(var18));
                  if (var22 == null) {
                     this.pendingRemovalSince.put(Overlay$14.bpdZ(var18), var3);
                  } else if (var3 - var22 >= 1500L) {
                     this.kFz.remove(Overlay$14.bpdZ(var18));
                     this.pendingRemovalSince.remove(Overlay$14.bpdZ(var18));
                     var16.remove();
                  }
               } else {
                  this.pendingRemovalSince.remove(Overlay$14.bpdZ(var18));
               }
            }
         } else {
            this.pendingRemovalSince.clear();
            Iterator var17 = this.trackedPlayers.entrySet().iterator();

            while (var17.hasNext()) {
               Overlay$14 var19 = (Overlay$14)((Entry)var17.next()).getValue();
               String var21 = this.normalizeName(Overlay$14.RXus(var19));
               if (!var10.contains(Overlay$14.bpdZ(var19)) && !this.manualPlayers.contains(var21) && !this.TlZyg.contains(var21)) {
                  this.kFz.remove(Overlay$14.bpdZ(var19));
                  this.pendingRemovalSince.remove(Overlay$14.bpdZ(var19));
                  var17.remove();
               }
            }
         }
      }
   }

   private boolean wbhP(NetworkPlayerInfo var1, boolean var2) {
      if (var1 != null && var1.getGameProfile() != null && var1.getGameProfile().getId() != null) {
         GameProfile var3 = var1.getGameProfile();
         boolean var4 = mc.thePlayer.getUniqueID().equals(var3.getId());
         if (!this.showSelf.isToggled() && var4) {
            return false;
         } else {
            String var5 = this.normalizeName(var3.getName());
            if (var4) {
               return !var5.isEmpty() && this.isPremiumUuid(var3.getId());
            } else if (var2) {
               return !var5.isEmpty() && this.isPremiumUuid(var3.getId());
            } else {
               return !var5.isEmpty() && (this.OkM0.contains(var5) || this.manualPlayers.contains(var5) || this.TlZyg.contains(var5))
                  ? this.isPremiumUuid(var3.getId())
                  : false;
            }
         }
      } else {
         return false;
      }
   }

   private boolean isPremiumUuid(UUID var1) {
      String var2 = var1 == null ? "" : var1.toString();
      return var2.length() > 14 && (var2.charAt(14) == '4' || var2.charAt(14) == '1');
   }

   public boolean addManualPlayer(String var1) {
      String var2 = this.normalizeName(var1);
      if (var2.isEmpty()) {
         return false;
      } else {
         this.manualPlayers.add(var2);
         this.showTemporarily();
         this.refreshTrackedPlayers();
         if (!this.ZULO(var2)) {
            this.resolvePlayerByName(var1.trim(), var2, this.manualPlayers, this.pendingManualLookups, true);
         }

         return true;
      }
   }

   private boolean trackPlayerFromChat(String var1) {
      String var2 = this.normalizeName(var1);
      if (var2.isEmpty() || this.DSfRx(var2)) {
         return false;
      } else if (!this.manualPlayers.contains(var2) && !this.OkM0.contains(var2) && !this.TlZyg.contains(var2)) {
         this.TlZyg.add(var2);
         this.showTemporarily();
         this.refreshTrackedPlayers();
         if (!this.ZULO(var2)) {
            this.resolvePlayerByName(var1.trim(), var2, this.TlZyg, this.pendingGameLookups, false);
         }

         return true;
      } else {
         return false;
      }
   }

   private void showTemporarily() {
      if (this.autoShow != null && this.autoShow.isToggled()) {
         if (this.autoHide != null && this.autoHide.isToggled()) {
            this.tempShowUntilMillis = System.currentTimeMillis() + this.getHideDelayMillis();
         } else {
            this.tempShowUntilMillis = 0L;
            this.manuallyHidden = false;
         }
      }
   }

   private long getHideDelayMillis() {
      double var1 = this.hideDelay == null ? 15.0 : this.hideDelay.getInput();
      return Math.max(1L, Math.round(var1 * 1000.0));
   }

   public boolean removeTrackedPlayer(String var1) {
      String var2 = this.normalizeName(var1);
      if (var2.isEmpty()) {
         return false;
      } else {
         boolean var3 = this.manualPlayers.remove(var2) | this.OkM0.remove(var2) | this.TlZyg.remove(var2);
         this.pendingGameLookups.remove(var2);
         Iterator var4 = this.trackedPlayers.entrySet().iterator();

         while (var4.hasNext()) {
            Overlay$14 var5 = (Overlay$14)((Entry)var4.next()).getValue();
            if (var2.equals(this.normalizeName(Overlay$14.RXus(var5)))) {
               this.kFz.remove(Overlay$14.bpdZ(var5));
               var4.remove();
               var3 = true;
            }
         }

         return var3;
      }
   }

   public int clearTrackedPlayers() {
      int var1 = this.trackedPlayers.size();
      this.trackedPlayers.clear();
      this.OkM0.clear();
      this.manualPlayers.clear();
      this.pendingManualLookups.clear();
      this.TlZyg.clear();
      this.pendingGameLookups.clear();
      this.kFz.clear();
      this.pendingRemovalSince.clear();
      this.tempShowUntilMillis = 0L;
      return var1;
   }

   private boolean ZULO(String var1) {
      for (Overlay$14 var3 : this.trackedPlayers.values()) {
         if (var1.equals(this.normalizeName(Overlay$14.RXus(var3)))) {
            return true;
         }
      }

      return false;
   }

   private void SONh() {
      Iterator var1 = this.trackedPlayers.entrySet().iterator();

      while (var1.hasNext()) {
         Overlay$14 var2 = (Overlay$14)((Entry)var1.next()).getValue();
         if (!this.manualPlayers.contains(this.normalizeName(Overlay$14.RXus(var2)))) {
            this.kFz.remove(Overlay$14.bpdZ(var2));
            var1.remove();
         }
      }
   }

   private void resolvePlayerByName(final String var1, final String var2, final Set<String> var3, final Set<String> var4, final boolean var5) {
      if (var4.add(var2)) {
         Jade.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
               final PlayerApi$2 var1x = PlayerApi.lookupProfileByName(var1);
               Overlay.getMinecraftInstance().addScheduledTask(new Runnable() {
                  @Override
                  public void run() {
                     var4.remove(var2);
                     if (var3.contains(var2)) {
                        UUID var1xx = var1x == null ? null : var1x.getUuid();
                        if (var1xx == null) {
                           var3.remove(var2);
                           if (var5) {
                              ClientUtils.sendJadeMessage("Jade", "&7could not find overlay player: &f" + var1);
                           }
                        } else {
                           String var2x = var1x.getName().isEmpty() ? var1 : var1x.getName();
                           Overlay$14 var3x = (Overlay$14)Overlay.getTrackedPlayerMap(Overlay.this).get(var1xx);
                           if (var3x == null) {
                              var3x = new Overlay$14(null, var1xx, var2x, System.currentTimeMillis());
                              Overlay.getTrackedPlayerMap(Overlay.this).put(var1xx, var3x);
                           } else {
                              Overlay$14.setPlayerName(var3x, var2x);
                           }

                           SkinCache.cachePlayerId(var2x, var1xx);
                           if (var5) {
                              ClientUtils.sendJadeMessage("Jade", "&7added overlay player: &f" + var2x + " &8(" + var1xx + ")");
                           }
                        }
                     }
                  }
               });
            }
         });
      }
   }

   private void trackPlayersFromChat(String var1) {
      String var2 = this.frhD(var1);
      if (!var2.isEmpty()) {
         this.trackPlayerFromChat(var2);
      } else if (this.isPartyMessage(var1)) {
         String var3 = var1.substring(var1.indexOf(58) + 1).replaceAll("\\[[^\\]]+\\]", " ");
         Matcher var4 = USERNAME_PATTERN.matcher(var3);

         while (var4.find()) {
            this.trackPlayerFromChat(var4.group(1));
         }
      }
   }

   private String frhD(String var1) {
      Matcher var2 = BVk.matcher(var1 == null ? "" : var1.trim());
      return !var2.find() ? "" : var2.group(1);
   }

   private boolean isPartyMessage(String var1) {
      return var1.startsWith("Party Leader:") || var1.startsWith("Party Members:") || var1.startsWith("Party Moderators:");
   }

   private boolean DSfRx(String var1) {
      return ClientUtils.isInWorld() && var1.equals(this.normalizeName(mc.thePlayer.getName()));
   }

   private int parseOnlineList(String var1) {
      int var2 = 0;
      String[] var3 = var1.split(",");

      for (String var7 : var3) {
         String var8 = this.parseOnlineEntry(var7);
         if (!var8.isEmpty() && this.OkM0.add(var8)) {
            var2++;
         }
      }

      return var2;
   }

   private String parseOnlineEntry(String var1) {
      String var2 = ClientUtils.AOAtn(var1 == null ? "" : var1).trim();
      int var3 = var2.lastIndexOf(32);
      if (var3 >= 0) {
         var2 = var2.substring(var3 + 1);
      }

      return this.normalizeName(var2);
   }

   private String normalizeName(String var1) {
      String var2 = ClientUtils.AOAtn(var1 == null ? "" : var1).trim();
      if (var2.length() >= 2 && var2.length() <= 16) {
         for (int var3 = 0; var3 < var2.length(); var3++) {
            char var4 = var2.charAt(var3);
            if (!Character.isLetterOrDigit(var4) && var4 != '_') {
               return "";
            }
         }

         return var2.toLowerCase(Locale.ROOT);
      } else {
         return "";
      }
   }

   private List<Overlay$10> buildColumnDescriptors(boolean var1) {
      ArrayList var2 = new ArrayList();

      for (Overlay$18 var4 : this.getVisibleColumns(var1)) {
         var2.add(new Overlay$10(this, this.getColumnTitle(var4), this.getColumnShortLabel(var4), var4));
      }

      if (var2.isEmpty()) {
         Overlay$18 var5 = var1 ? Overlay$18.SW_NAME : Overlay$18.NAME;
         var2.add(new Overlay$10(this, "Username", "Name", var5));
      }

      return var2;
   }

   private List<Overlay$18> getVisibleColumns(boolean var1) {
      ArrayList var2 = new ArrayList();

      for (Overlay$18 var4 : this.getAllColumns(var1)) {
         if ((var4 != Overlay$18.TAGS || this.isCustomApiEnabled()) && this.getColumnToggle(var4).isToggled()) {
            var2.add(var4);
         }
      }

      return var2;
   }

   private List<Overlay$18> getAllColumns(boolean var1) {
      ArrayList var2 = new ArrayList();
      StringListSetting var3 = var1 ? this.skyWarsOverlayColumnOrder : this.overlayColumnOrder;
      Overlay$18[] var4 = var1 ? SKYWARS_COLUMNS : wfX82;

      for (String var6 : var3.getEntries()) {
         Overlay$18 var7 = this.parseColumnName(var6);
         if (var7 != null && this.OGQq7(var4, var7) && !var2.contains(var7)) {
            var2.add(var7);
         }
      }

      for (int var8 = 0; var8 < var4.length; var8++) {
         if (!var2.contains(var4[var8])) {
            var2.add(var4[var8]);
         }
      }

      return var2;
   }

   private boolean OGQq7(Overlay$18[] var1, Overlay$18 var2) {
      for (int var3 = 0; var3 < var1.length; var3++) {
         if (var1[var3] == var2) {
            return true;
         }
      }

      return false;
   }

   private void saveColumnOrder(List<Overlay$18> var1, boolean var2) {
      StringListSetting var3 = var2 ? this.skyWarsOverlayColumnOrder : this.overlayColumnOrder;
      var3.clearEntries();

      for (Overlay$18 var5 : var1) {
         var3.addEntry(var5.name());
      }
   }

   private Overlay$18 parseColumnName(String var1) {
      if (var1 == null) {
         return null;
      } else {
         try {
            return Overlay$18.valueOf(var1);
         } catch (IllegalArgumentException var3) {
            return null;
         }
      }
   }

    private BooleanSetting getColumnToggle(Overlay$18 var1_1) {
        switch (var1_1) {
            case HEAD: {
                return this.skinHead;
            }
            case TEAM: {
                return this.team;
            }
            case RANK: {
                return this.hypixelRank2;
            }
            case STAR: {
                return this.star2;
            }
            case NAME: {
                return this.username3;
            }
            case WINSTREAK: {
                return this.winstreak;
            }
            case FKDR: {
                return this.fkdr;
            }
            case WLR: {
                return this.wlr;
            }
            case FINALS: {
                return this.finals;
            }
            case WINS: {
                return this.wins2;
            }
            case MONTHLY_FKDR: {
                return this.monthlyFkdr;
            }
            case FINALS_PER_STAR: {
                return this.finalsPerStar;
            }
            case SW_RANK: {
                return this.hypixelRank;
            }
            case SW_STAR: {
                return this.username2;
            }
            case SW_NAME: {
                return this.star;
            }
            case SW_KDR: {
                return this.kdr;
            }
            case SW_WLR: {
                return this.wlr2;
            }
            case SW_KILLS: {
                return this.kills;
            }
            case SW_WINS: {
                return this.wins;
            }
            case SW_SNIPER: {
                return this.sniperScore2;
            }
            case SESSION: {
                return this.sessionTime;
            }
            case SNIPER: {
                return this.sniperScore;
            }
            case HP: {
                return this.tags;
            }
            case SW_HP: {
                return this.hp2;
            }
            case TAGS: {
                return this.hp;
            }
        }
        return this.username3;
    }

    private String getColumnTitle(Overlay$18 var1_1) {
        switch (var1_1) {
            case HEAD: {
                return "Skin";
            }
            case TEAM: {
                return "Team";
            }
            case RANK: {
                return "Hypixel Rank";
            }
            case STAR: {
                return "Star";
            }
            case NAME: {
                return "Username";
            }
            case WINSTREAK: {
                return "Winstreak";
            }
            case FKDR: {
                return "FKDR";
            }
            case WLR: {
                return "WLR";
            }
            case FINALS: {
                return "Finals";
            }
            case WINS: {
                return "Wins";
            }
            case MONTHLY_FKDR: {
                return "Monthly FKDR";
            }
            case FINALS_PER_STAR: {
                return "Finals per star";
            }
            case SW_RANK: {
                return "Hypixel Rank";
            }
            case SW_STAR: {
                return "Username";
            }
            case SW_NAME: {
                return "Star";
            }
            case SW_KDR: {
                return "KDR";
            }
            case SW_WLR: {
                return "WLR";
            }
            case SW_KILLS: {
                return "Kills";
            }
            case SW_WINS: {
                return "Wins";
            }
            case SW_SNIPER: {
                return "Sniper Score";
            }
            case SESSION: {
                return "Session Time";
            }
            case SNIPER: {
                return "Sniper Score";
            }
            case HP: {
                return "Tags";
            }
            case SW_HP: 
            case TAGS: {
                return "HP";
            }
        }
        return "";
    }

    private String getColumnShortLabel(Overlay$18 var1_1) {
        switch (var1_1) {
            case HEAD: {
                return "";
            }
            case TEAM: {
                return "Team";
            }
            case RANK: {
                return "Rank";
            }
            case STAR: {
                return "Star";
            }
            case NAME: {
                return "Name";
            }
            case WINSTREAK: {
                return "WS";
            }
            case FKDR: {
                return "FKDR";
            }
            case WLR: {
                return "WLR";
            }
            case FINALS: {
                return "F. Kills";
            }
            case WINS: {
                return "Wins";
            }
            case MONTHLY_FKDR: {
                return "M. FKDR";
            }
            case FINALS_PER_STAR: {
                return "FKs/Lvl";
            }
            case SW_RANK: {
                return "Rank";
            }
            case SW_STAR: {
                return "Name";
            }
            case SW_NAME: {
                return "Star";
            }
            case SW_KDR: {
                return "KDR";
            }
            case SW_WLR: {
                return "WLR";
            }
            case SW_KILLS: {
                return "Kills";
            }
            case SW_WINS: {
                return "Wins";
            }
            case SW_SNIPER: {
                return "SS";
            }
            case SESSION: {
                return "Session";
            }
            case SNIPER: {
                return "SS";
            }
            case HP: {
                return "Tags";
            }
            case SW_HP: 
            case TAGS: {
                return "HP";
            }
        }
        return "";
    }

   private Overlay$13 computeOverlayLayout(List<Overlay$10> var1, List<Overlay$16> var2, int var3) {
      IFont var4 = this.getOverlayFont();
      int var5 = (int)this.columnGap.getInput();
      int var6 = (int)this.xPadding.getInput();
      int var7 = (int)this.yPadding.getInput();
      int var8 = Math.max((int)this.rowHeight.getInput(), var4.getFontHeight() + 5);
      int[] var9 = new int[var1.size()];

      for (int var10 = 0; var10 < var1.size(); var10++) {
         Overlay$10 var11 = (Overlay$10)var1.get(var10);
         var9[var10] = Overlay$10.getColumnType(var11) == Overlay$18.HEAD ? var8 - 4 : this.zgyrI(var4, Overlay$10.getHeaderText(var11, var3));

         for (int var12 = 0; var12 < var3; var12++) {
            var9[var10] = Math.max(
               var9[var10],
               Overlay$10.getColumnType(var11) == Overlay$18.TAGS
                  ? this.measureTagRowWidth(var4, (Overlay$16)var2.get(var12))
                  : this.zgyrI(var4, Overlay$10.getCellText(var11, (Overlay$16)var2.get(var12)))
            );
         }
      }

      int var13 = var6 * 2;

      for (int var14 = 0; var14 < var9.length; var14++) {
         var13 += var9[var14] + (var14 == var9.length - 1 ? 0 : var5);
      }

      int var15 = (int)this.header.getInput() == 2 ? 0 : var8;
      return new Overlay$13(var4, var9, var13, var7 * 2 + var15 + var3 * var8, var15, var8, var6, var7, var5);
   }

   private void drawOverlay(float var1, float var2, Overlay$13 var3, List<Overlay$10> var4, List<Overlay$16> var5, int var6, boolean var7) {
      this.drawOverlayWithAlpha(var1, var2, var3, var4, var5, var6, var7, 1.0F);
   }

   private void drawOverlayWithAlpha(float var1, float var2, Overlay$13 var3, List<Overlay$10> var4, List<Overlay$16> var5, int var6, boolean var7, float var8) {
      int var9 = Math.max(0, Math.min(255, Math.round(255.0F * this.kWeb())));
      int[] var10 = this.TJQWgw3();
      var10[0] = (int)(var10[0] / var8);
      var10[1] = (int)(var10[1] / var8);
      Overlay$17 var11 = null;
      float var12 = (float)this.roundingLevel.getInput();
      int var13 = this.applyAlphaScale(this.background.getArgb(), var9);
      int var14 = this.applyAlphaScale(this.header2.getArgb(), var9);
      float var15 = var2 + Overlay$13.getPaddingY(var3) + Overlay$13.getHeaderHeight(var3);
      if (this.background2.isToggled()) {
         this.drawPanelRect(var1, var2, Overlay$13.getWidth(var3), Overlay$13.getHeight(var3), var12, var13);
      }

      if (Overlay$13.getHeaderHeight(var3) > 0) {
         this.drawPanelHeaderRect(var1, var2, Overlay$13.getWidth(var3), var15 - var2, var12, var14);
      }

      for (int var16 = 0; var16 < var6; var16++) {
         Overlay$16 var17 = (Overlay$16)var5.get(var16);
         float var18 = var2 + Overlay$13.getPaddingY(var3) + Overlay$13.getHeaderHeight(var3) + var16 * Overlay$13.getRowHeight(var3);
         int var19 = Overlay$16.TkHi8(var17, var7) ? this.applyAlphaScale(1353973760, var9) : (var16 % 2 == 0 ? this.applyAlphaScale(this.header2.getArgb(), Math.min(34, var9)) : 0);
         if (var19 != 0) {
            RenderUtils.jxyoE(
               var1 + Overlay$13.getPaddingX(var3) - 2.0F,
               var18 + 1.0F,
               var1 + Overlay$13.getWidth(var3) - Overlay$13.getPaddingX(var3) + 2.0F,
               var18 + Overlay$13.getRowHeight(var3) - 1.0F,
               Math.max(1.0F, var12 - 2.0F),
               var19
            );
         }
      }

      float var23 = var1 + Overlay$13.getPaddingX(var3);

      for (int var24 = 0; var24 < var4.size(); var24++) {
         Overlay$10 var25 = (Overlay$10)var4.get(var24);
         if (Overlay$13.getHeaderHeight(var3) > 0 && !Overlay$10.getHeaderText(var25, var6).isEmpty()) {
            String var26 = Overlay$10.getHeaderText(var25, var6);
            this.drawStringWithIcons(
               Overlay$13.getFont(var3),
               var26,
               Overlay$10.centerColumnText(var25, Overlay$13.getFont(var3), var23, Overlay$13.JxeeY(var3)[var24], var26),
               var2 + Overlay$13.getPaddingY(var3) + this.getTextBaselineOffset(var3, Overlay$13.getHeaderHeight(var3)),
               this.applyAlphaScale(Overlay$10.mamN(var25), var9)
            );
         }

         for (int var27 = 0; var27 < var6; var27++) {
            Overlay$16 var20 = (Overlay$16)var5.get(var27);
            float var21 = var2 + Overlay$13.getPaddingY(var3) + Overlay$13.getHeaderHeight(var3) + var27 * Overlay$13.getRowHeight(var3);
            if (Overlay$10.getColumnType(var25) == Overlay$18.HEAD) {
               this.drawSkinHead(var20, var23, var21 + 2.0F, Overlay$13.getRowHeight(var3) - 4, this.kWeb());
            } else if (Overlay$10.getColumnType(var25) == Overlay$18.TAGS) {
               Overlay$17 var22 = this.drawTagRow(
                  Overlay$13.getFont(var3), var20, var23, var21, Overlay$13.JxeeY(var3)[var24], Overlay$13.getRowHeight(var3), var9, var10[0], var10[1]
               );
               if (var22 != null) {
                  var11 = var22;
               }
            } else {
               String var28 = Overlay$10.getCellText(var25, var20);
               this.drawStringWithIcons(
                  Overlay$13.getFont(var3),
                  var28,
                  Overlay$10.centerColumnText(var25, Overlay$13.getFont(var3), var23, Overlay$13.JxeeY(var3)[var24], var28),
                  var21 + this.getTextBaselineOffset(var3, Overlay$13.getRowHeight(var3)),
                  this.applyAlphaScale(Overlay$10.getCellColor(var25, var20), var9)
               );
            }
         }

         var23 += Overlay$13.JxeeY(var3)[var24] + Overlay$13.getColumnGap(var3);
      }

      if (var11 != null) {
         this.drawTagTooltip(Overlay$17.getTooltipText(var11), Overlay$17.getMouseX(var11), Overlay$17.getMouseY(var11), var8);
      }
   }

   private void drawOverlayBuffered(float var1, float var2, Overlay$13 var3, List<Overlay$10> var4, List<Overlay$16> var5, int var6, boolean var7) {
      ExternalRenderBuffer var8 = ExternalRenderer.getActiveRenderBuffer();
      if (var8 != null) {
         float var9 = new ScaledResolution(mc).getScaleFactor();
         int var10 = Math.max(0, Math.min(255, Math.round(255.0F * this.kWeb())));
         float var11 = (float)this.roundingLevel.getInput() * var9;
         float var12 = var1 * var9;
         float var13 = var2 * var9;
         float var14 = (var1 + Overlay$13.getWidth(var3)) * var9;
         float var15 = (var2 + Overlay$13.getHeight(var3)) * var9;
         if (this.background2.isToggled()) {
            var8.fillRoundedRect(var12, var13, var14, var15, this.applyAlphaScale(this.background.getArgb(), var10), var11);
         }

         float var16 = (var2 + Overlay$13.getPaddingY(var3) + Overlay$13.getHeaderHeight(var3)) * var9;
         if (Overlay$13.getHeaderHeight(var3) > 0) {
            var8.fillPerCornerRoundedRect(var12, var13, var14, var16, this.applyAlphaScale(this.header2.getArgb(), var10), var11, var11, 0.0F, 0.0F);
         }

         for (int var17 = 0; var17 < var6; var17++) {
            Overlay$16 var18 = (Overlay$16)var5.get(var17);
            float var19 = var2 + Overlay$13.getPaddingY(var3) + Overlay$13.getHeaderHeight(var3) + var17 * Overlay$13.getRowHeight(var3);
            int var20 = Overlay$16.TkHi8(var18, var7)
               ? this.applyAlphaScale(1353973760, var10)
               : (var17 % 2 == 0 ? this.applyAlphaScale(this.header2.getArgb(), Math.min(34, var10)) : 0);
            if (var20 != 0) {
               var8.fillRoundedRect(
                  (var1 + Overlay$13.getPaddingX(var3) - 2.0F) * var9,
                  (var19 + 1.0F) * var9,
                  (var1 + Overlay$13.getWidth(var3) - Overlay$13.getPaddingX(var3) + 2.0F) * var9,
                  (var19 + Overlay$13.getRowHeight(var3) - 1.0F) * var9,
                  var20,
                  Math.max(1.0F, var11 - 2.0F * var9)
               );
            }
         }

         float var26 = var1 + Overlay$13.getPaddingX(var3);

         for (int var27 = 0; var27 < var4.size(); var27++) {
            Overlay$10 var28 = (Overlay$10)var4.get(var27);
            String var29 = Overlay$10.getHeaderText(var28, var6);
            if (Overlay$13.getHeaderHeight(var3) > 0 && !var29.isEmpty()) {
               this.drawBufferedIconString(
                  var8,
                  Overlay$13.getFont(var3),
                  var29,
                  Overlay$10.centerColumnText(var28, Overlay$13.getFont(var3), var26, Overlay$13.JxeeY(var3)[var27], var29),
                  var2 + Overlay$13.getPaddingY(var3) + this.getTextBaselineOffset(var3, Overlay$13.getHeaderHeight(var3)),
                  this.applyAlphaScale(Overlay$10.mamN(var28), var10),
                  var9
               );
            }

            for (int var21 = 0; var21 < var6; var21++) {
               Overlay$16 var22 = (Overlay$16)var5.get(var21);
               float var23 = var2 + Overlay$13.getPaddingY(var3) + Overlay$13.getHeaderHeight(var3) + var21 * Overlay$13.getRowHeight(var3);
               if (Overlay$10.getColumnType(var28) == Overlay$18.HEAD) {
                  AbstractClientPlayer var24 = this.findPlayerEntity(Overlay$16.RlnF2(var22));
                  if (var24 != null) {
                     float var25 = Overlay$13.getRowHeight(var3) - 4.0F;
                     ExternalSkinTextures.drawPlayerHead(var8, var24, var26 * var9, (var23 + 2.0F) * var9, var25 * var9, this.kWeb());
                  }
               } else {
                  String var30 = Overlay$10.getCellText(var28, var22);
                  this.drawBufferedIconString(
                     var8,
                     Overlay$13.getFont(var3),
                     var30,
                     Overlay$10.centerColumnText(var28, Overlay$13.getFont(var3), var26, Overlay$13.JxeeY(var3)[var27], var30),
                     var23 + this.getTextBaselineOffset(var3, Overlay$13.getRowHeight(var3)),
                     this.applyAlphaScale(Overlay$10.getCellColor(var28, var22), var10),
                     var9
                  );
               }
            }

            var26 += Overlay$13.JxeeY(var3)[var27] + Overlay$13.getColumnGap(var3);
         }
      }
   }

   private void drawBufferedIconString(ExternalRenderBuffer var1, IFont var2, String var3, float var4, float var5, int var6, float var7) {
      int var8 = this.findIconIndex(var3);
      if (var8 >= 0) {
         String var9 = var3.substring(0, var8);
         int var10 = Character.codePointAt(var3, var8);
         int var11 = Character.charCount(var10);
         String var12 = var3.substring(var8 + var11);
         float var13 = this.zgyrI(var2, var9);
         float var14 = this.measureCodepointWidth(var2, var10);
         float var15 = (var4 + var13) * var7;
         float var16 = (var5 + Math.max(0.0F, (var2.getFontHeight() - var14) / 2.0F) - 0.35F) * var7;
         int var17 = this.getLastColorCodeArgb(var9);
         if (var17 == -1) {
            var17 = var6;
         } else {
            var17 = var6 & 0xFF000000 | var17 & 16777215;
         }

         FormattedTextRenderer.drawTextAtHeight(var1, var2, var9, var4 * var7, var5 * var7, var2.getFontHeight() * var7, var6, this.textShadow.isToggled(), var13 * var7);
         StarGlyphRenderer.egNdi(var1, var15, var16, var14 * var7, var17, EXQhDjf8.QWFA(var10));
         FormattedTextRenderer.drawTextAtHeight(var1, var2, var12, var15 + var14 * var7, var5 * var7, var2.getFontHeight() * var7, var6, this.textShadow.isToggled(), this.zgyrI(var2, var12) * var7);
      } else {
         FormattedTextRenderer.drawTextAtHeight(var1, var2, var3, var4 * var7, var5 * var7, var2.getFontHeight() * var7, var6, this.textShadow.isToggled(), var2.getStringWidth(var3) * var7);
      }
   }

   private AbstractClientPlayer findPlayerEntity(UUID var1) {
      if (var1 != null && mc.theWorld != null) {
         for (Object var3 : mc.theWorld.playerEntities) {
            if (var3 instanceof AbstractClientPlayer && var1.equals(((AbstractClientPlayer)var3).getUniqueID())) {
               return (AbstractClientPlayer)var3;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private int[] TJQWgw3() {
      ScaledResolution var1 = new ScaledResolution(mc);
      int var2 = Mouse.getX() * var1.getScaledWidth() / Math.max(1, mc.displayWidth);
      int var3 = var1.getScaledHeight() - Mouse.getY() * var1.getScaledHeight() / Math.max(1, mc.displayHeight) - 1;
      return new int[]{var2, var3};
   }

   private int measureTagRowWidth(IFont var1, Overlay$16 var2) {
      StatsFetcher$2 var3 = this.tD01(var2);
      if (var3 != null && !var3.getTags().isEmpty()) {
         int var4 = 0;

         for (StatsFetcher$3 var6 : var3.getTags()) {
            var4 += this.measureTagChipWidth(var1, var6) + (var4 == 0 ? 0 : 2);
         }

         return Math.max(var4, 8);
      } else {
         return this.zgyrI(var1, var3 == null ? "..." : "-");
      }
   }

   private Overlay$17 drawTagRow(IFont var1, Overlay$16 var2, float var3, float var4, int var5, int var6, int var7, int var8, int var9) {
      StatsFetcher$2 var10 = this.tD01(var2);
      if (var10 != null && !var10.getTags().isEmpty()) {
         int var20 = this.measureTagRowWidth(var1, var2);
         float var12 = var3 + Math.max(0.0F, (var5 - var20) / 2.0F);
         int var13 = Math.max(8, Math.min(14, var6 - 4));
         float var14 = var4 + Math.max(1.0F, (var6 - var13) / 2.0F);
         Overlay$17 var15 = null;

         for (StatsFetcher$3 var17 : var10.getTags()) {
            int var18 = this.measureTagChipWidth(var1, var17);
            int var19 = this.applyAlphaScale(var17.backgroundColor, var7);
            RoundedRect.drawRoundedRectArgb(var12, var14, var18, var13, Math.min(4.0F, var13 / 2.0F), var19);
            this.drawTagChip(var1, var17, var12, var14, var18, var13, var7);
            if (var8 >= var12 && var8 <= var12 + var18 && var9 >= var14 && var9 <= var14 + var13 && var17.Uv1 != null && !var17.Uv1.trim().isEmpty()) {
               var15 = new Overlay$17(var17.Uv1, var8, var9);
            }

            var12 += var18 + 2.0F;
         }

         return var15;
      } else {
         String var11 = var10 == null ? "..." : "-";
         this.drawStringWithIcons(
            var1,
            var11,
            var3 + Math.max(0, var5 - this.zgyrI(var1, var11)) / 2.0F,
            var4 + this.getTextBaselineOffset(new Overlay$13(var1, new int[]{var5}, var5, var6, 0, var6, 0, 0, 0), var6),
            this.applyAlphaScale(var10 == null ? -8947849 : this.text.getArgb(), var7)
         );
         return null;
      }
   }

   private int measureTagChipWidth(IFont var1, StatsFetcher$3 var2) {
      String var3 = this.getTagShortText(var2);
      boolean var4 = this.isIconTag(var2.AGiv);
      boolean var5 = !var3.isEmpty();
      int var6 = var4 ? 12 : 0;
      int var7 = var5 ? this.zgyrI(var1, var3) : 0;
      if (var4 && var5) {
         return Math.max(16, var6 + var7 + 10);
      } else {
         return var4 ? 16 : Math.max(12, var7 + 8);
      }
   }

   private void drawTagChip(IFont var1, StatsFetcher$3 var2, float var3, float var4, int var5, int var6, int var7) {
      String var8 = this.getTagShortText(var2);
      int var9 = this.applyAlphaScale(var2.KRg, var7);
      boolean var10 = this.isIconTag(var2.AGiv);
      int var11 = Math.max(8, Math.min(12, var6 - 2));
      int var12 = var8.isEmpty() ? 0 : this.zgyrI(var1, var8);
      float var13 = (var10 ? var11 : 0) + (var10 && !var8.isEmpty() ? 3 : 0) + var12;
      float var14 = var3 + Math.max(0.0F, (var5 - var13) / 2.0F);
      if (var10) {
         this.drawTagIcon(var2.AGiv, var14, var4 + (var6 - var11) / 2.0F, var11, var9);
         var14 += var11 + (var8.isEmpty() ? 0 : 3);
      }

      if (!var8.isEmpty()) {
         float var15 = var4 + Math.max(0.0F, (var6 - var1.getFontHeight()) / 2.0F) - var1.getTextTopOffset();
         this.drawStringWithIcons(var1, var8, var14, var15, var9);
      }
   }

   private String getTagShortText(StatsFetcher$3 var1) {
      if (var1.LIxpx != null && !var1.LIxpx.isEmpty()) {
         String var2 = this.cleanTagLabel(var1.LIxpx).trim();
         return var2.length() > 8 ? var2.substring(0, 8) : var2;
      } else {
         return this.isIconTag(var1.AGiv) ? "" : "*";
      }
   }

   private String cleanTagLabel(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         StringBuilder var2 = new StringBuilder(var1.length());

         for (int var3 = 0; var3 < var1.length(); var3++) {
            char var4 = var1.charAt(var3);
            if (var4 != 8203 && var4 != 8204 && var4 != 8205 && var4 != '\ufeff') {
               if (var4 == 12644 || var4 == 160 || var4 == 10240) {
                  var2.append(' ');
               } else if (var4 == 183) {
                  var2.append('.');
               } else if (var4 == 9670) {
                  var2.append('*');
               } else {
                  var2.append(var4);
               }
            }
         }

         return var2.toString();
      } else {
         return "";
      }
   }

   private boolean isIconTag(String var1) {
      return var1 != null && var1.toLowerCase(Locale.ROOT).startsWith("mdi-");
   }

   private void drawTagIcon(String var1, float var2, float var3, int var4, int var5) {
      ResourceLocation var6 = this.getTagIconTexture(this.resolveIconKey(var1));
      if (var6 == null) {
         RoundedRect.drawRoundedRectArgb(var2 + var4 * 0.25F, var3 + var4 * 0.25F, var4 * 0.5F, var4 * 0.5F, var4 * 0.25F, var5);
      } else {
         RenderUtils.drawIconTexture(var6, var2, var3, var4, var5);
      }
   }

   private String resolveIconKey(String var1) {
      String var2 = var1 == null ? "" : var1.toLowerCase(Locale.ROOT);
      if (var2.contains("signal-cellular")) {
         return "signal";
      } else if (var2.contains("bow") || var2.contains("arrow")) {
         return "bow_arrow";
      } else if (var2.contains("timeline")) {
         return "timeline";
      } else if (var2.contains("account-check")) {
         return "account_check";
      } else if (var2.contains("account-alert")) {
         return "account_alert";
      } else if (var2.contains("account")) {
         return "account";
      } else {
         return !var2.contains("octagram") && !var2.contains("alert") ? "" : "alert";
      }
   }

   private ResourceLocation getTagIconTexture(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         String var2 = this.getIconPathData(var1);
         if (var2.isEmpty()) {
            return null;
         } else {
            ResourceLocation var3 = this.iconTextureCache.get(var1);
            if (var3 != null) {
               return var3;
            } else {
               ResourceLocation var4 = mc.getTextureManager().getDynamicTextureLocation("jade_overlay_mdi_" + var1, new DynamicTexture(this.createIconImage(var2)));
               this.iconTextureCache.put(var1, var4);
               return var4;
            }
         }
      } else {
         return null;
      }
   }

   private String getIconPathData(String var1) {
      if ("account_alert".equals(var1)) {
         return "M10 4A4 4 0 0 1 14 8A4 4 0 0 1 10 12A4 4 0 0 1 6 8A4 4 0 0 1 10 4M10 14C14.42 14 18 15.79 18 18V20H2V18C2 15.79 5.58 14 10 14M20 12V7H22V13H20M20 17V15H22V17H20Z";
      } else if ("account_check".equals(var1)) {
         return "M21.1,12.5L22.5,13.91L15.97,20.5L12.5,17L13.9,15.59L15.97,17.67L21.1,12.5M10,17L13,20H3V18C3,15.79 6.58,14 11,14L12.89,14.11L10,17M11,4A4,4 0 0,1 15,8A4,4 0 0,1 11,12A4,4 0 0,1 7,8A4,4 0 0,1 11,4Z";
      } else if ("alert".equals(var1)) {
         return "M2.2,16.06L3.88,12L2.2,7.94L6.26,6.26L7.94,2.2L12,3.88L16.06,2.2L17.74,6.26L21.8,7.94L20.12,12L21.8,16.06L17.74,17.74L16.06,21.8L12,20.12L7.94,21.8L6.26,17.74L2.2,16.06M4.81,9L6.05,12L4.81,15L7.79,16.21L9,19.19L12,17.95L15,19.19L16.21,16.21L19.19,15L17.95,12L19.19,9L16.21,7.79L15,4.81L12,6.05L9,4.81L7.79,7.79L4.81,9M11,15H13V17H11V15M11,7H13V13H11V7";
      } else if ("timeline".equals(var1)) {
         return "M4 2V8H2V2H4M2 22V16H4V22H2M5 12C5 13.11 4.11 14 3 14C1.9 14 1 13.11 1 12C1 10.9 1.9 10 3 10C4.11 10 5 10.9 5 12M24 6V18C24 19.11 23.11 20 22 20H10C8.9 20 8 19.11 8 18V14L6 12L8 10V6C8 4.89 8.9 4 10 4H22C23.11 4 24 4.89 24 6M10 6V18H22V6H10Z";
      } else if ("bow_arrow".equals(var1)) {
         return "M19.03 6.03L20 7L22 2L17 4L17.97 4.97L16.15 6.79C10.87 2.16 3.3 3.94 2.97 4L2 4.26L2.5 6.2L3.29 6L10.12 12.82L6.94 16H5L2 19L4 20L5 22L8 19V17.06L11.18 13.88L18 20.71L17.81 21.5L19.74 22L20 21.03C20.06 20.7 21.84 13.13 17.21 7.85L19.03 6.03M4.5 5.78C6.55 5.5 11.28 5.28 14.73 8.21L10.82 12.12L4.5 5.78M18.22 19.5L11.88 13.18L15.79 9.27C18.72 12.72 18.5 17.45 18.22 19.5Z";
      } else if ("signal".equals(var1)) {
         return "M19.5,5.5V18.5H17.5V5.5H19.5M21,4H16V20H21V4M14,9H9V20H14V9M7,14H2V20H7V14Z";
      } else {
         return "account".equals(var1)
            ? "M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z"
            : "";
      }
   }

   private BufferedImage createIconImage(String var1) {
      BufferedImage var2 = new BufferedImage(64, 64, 2);
      Graphics2D var3 = var2.createGraphics();
      var3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var3.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      var3.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
      var3.setColor(Color.WHITE);
      AffineTransform var4 = AffineTransform.getScaleInstance(2.6666667F, 2.6666667F);
      Shape var5 = var4.createTransformedShape(this.parseSvgPath(var1));
      var3.fill(var5);
      var3.dispose();
      return var2;
   }

   private Float parseSvgPath(String var1) {
      ArrayList var2 = new ArrayList();
      Matcher var3 = Pattern.compile("[A-Za-z]|[-+]?(?:\\d*\\.\\d+|\\d+\\.?)(?:[eE][-+]?\\d+)?").matcher(var1);

      while (var3.find()) {
         var2.add(var3.group());
      }

      Float var4 = new Float(1);
      int var5 = 0;
      char var6 = ' ';
      char var7 = ' ';
      float var8 = 0.0F;
      float var9 = 0.0F;
      float var10 = 0.0F;
      float var11 = 0.0F;
      float var12 = 0.0F;
      float var13 = 0.0F;
      float var14 = 0.0F;
      float var15 = 0.0F;

      while (var5 < var2.size()) {
         String var16 = (String)var2.get(var5);
         if (this.isPathCommand(var16)) {
            var6 = var16.charAt(0);
            var5++;
         }

         boolean var17 = Character.isLowerCase(var6);
         char var18 = Character.toUpperCase(var6);
         if (var18 == 'Z') {
            var4.closePath();
            var8 = var10;
            var9 = var11;
            var7 = var18;
         } else if (var18 == 'M') {
            boolean var53 = true;

            while (var5 + 1 < var2.size() && !this.isPathCommand((String)var2.get(var5))) {
               float var59 = this.parseSvgNumber(var2, var5++);
               float var64 = this.parseSvgNumber(var2, var5++);
               if (var17) {
                  var59 += var8;
                  var64 += var9;
               }

               if (var53) {
                  var4.moveTo(var59, var64);
                  var10 = var59;
                  var11 = var64;
                  var53 = false;
               } else {
                  var4.lineTo(var59, var64);
               }

               var8 = var59;
               var9 = var64;
            }

            var7 = var18;
         } else {
            for (; var5 < var2.size() && !this.isPathCommand((String)var2.get(var5)); var7 = var18) {
               if (var18 == 'L') {
                  float var19 = this.parseSvgNumber(var2, var5++);
                  float var20 = this.parseSvgNumber(var2, var5++);
                  if (var17) {
                     var19 += var8;
                     var20 += var9;
                  }

                  var4.lineTo(var19, var20);
                  var8 = var19;
                  var9 = var20;
               } else if (var18 == 'H') {
                  float var46 = this.parseSvgNumber(var2, var5++);
                  if (var17) {
                     var46 += var8;
                  }

                  var4.lineTo(var46, var9);
                  var8 = var46;
               } else if (var18 == 'V') {
                  float var47 = this.parseSvgNumber(var2, var5++);
                  if (var17) {
                     var47 += var9;
                  }

                  var4.lineTo(var8, var47);
                  var9 = var47;
               } else if (var18 == 'C') {
                  float var48 = this.parseSvgNumber(var2, var5++);
                  float var54 = this.parseSvgNumber(var2, var5++);
                  float var21 = this.parseSvgNumber(var2, var5++);
                  float var22 = this.parseSvgNumber(var2, var5++);
                  float var23 = this.parseSvgNumber(var2, var5++);
                  float var24 = this.parseSvgNumber(var2, var5++);
                  if (var17) {
                     var48 += var8;
                     var54 += var9;
                     var21 += var8;
                     var22 += var9;
                     var23 += var8;
                     var24 += var9;
                  }

                  var4.curveTo(var48, var54, var21, var22, var23, var24);
                  var12 = var21;
                  var13 = var22;
                  var8 = var23;
                  var9 = var24;
               } else if (var18 == 'S') {
                  float var49 = var7 != 'C' && var7 != 'S' ? var8 : var8 * 2.0F - var12;
                  float var55 = var7 != 'C' && var7 != 'S' ? var9 : var9 * 2.0F - var13;
                  float var60 = this.parseSvgNumber(var2, var5++);
                  float var65 = this.parseSvgNumber(var2, var5++);
                  float var69 = this.parseSvgNumber(var2, var5++);
                  float var71 = this.parseSvgNumber(var2, var5++);
                  if (var17) {
                     var60 += var8;
                     var65 += var9;
                     var69 += var8;
                     var71 += var9;
                  }

                  var4.curveTo(var49, var55, var60, var65, var69, var71);
                  var12 = var60;
                  var13 = var65;
                  var8 = var69;
                  var9 = var71;
               } else if (var18 == 'Q') {
                  float var50 = this.parseSvgNumber(var2, var5++);
                  float var56 = this.parseSvgNumber(var2, var5++);
                  float var61 = this.parseSvgNumber(var2, var5++);
                  float var66 = this.parseSvgNumber(var2, var5++);
                  if (var17) {
                     var50 += var8;
                     var56 += var9;
                     var61 += var8;
                     var66 += var9;
                  }

                  var4.quadTo(var50, var56, var61, var66);
                  var14 = var50;
                  var15 = var56;
                  var8 = var61;
                  var9 = var66;
               } else if (var18 == 'T') {
                  float var51 = var7 != 'Q' && var7 != 'T' ? var8 : var8 * 2.0F - var14;
                  float var57 = var7 != 'Q' && var7 != 'T' ? var9 : var9 * 2.0F - var15;
                  float var62 = this.parseSvgNumber(var2, var5++);
                  float var67 = this.parseSvgNumber(var2, var5++);
                  if (var17) {
                     var62 += var8;
                     var67 += var9;
                  }

                  var4.quadTo(var51, var57, var62, var67);
                  var14 = var51;
                  var15 = var57;
                  var8 = var62;
                  var9 = var67;
               } else {
                  if (var18 != 'A') {
                     break;
                  }

                  float var52 = this.parseSvgNumber(var2, var5++);
                  float var58 = this.parseSvgNumber(var2, var5++);
                  float var63 = this.parseSvgNumber(var2, var5++);
                  boolean var68 = this.parseSvgNumber(var2, var5++) != 0.0F;
                  boolean var70 = this.parseSvgNumber(var2, var5++) != 0.0F;
                  float var72 = this.parseSvgNumber(var2, var5++);
                  float var25 = this.parseSvgNumber(var2, var5++);
                  if (var17) {
                     var72 += var8;
                     var25 += var9;
                  }

                  this.ExjxbR(var4, var8, var9, var52, var58, var63, var68, var70, var72, var25);
                  var8 = var72;
                  var9 = var25;
               }
            }
         }
      }

      return var4;
   }

   private void ExjxbR(Float var1, float var2, float var3, float var4, float var5, float var6, boolean var7, boolean var8, float var9, float var10) {
      if (var4 != 0.0F && var5 != 0.0F && (var2 != var9 || var3 != var10)) {
         double var11 = Math.toRadians(var6 % 360.0F);
         double var13 = Math.cos(var11);
         double var15 = Math.sin(var11);
         double var17 = (var2 - var9) / 2.0;
         double var19 = (var3 - var10) / 2.0;
         double var21 = var13 * var17 + var15 * var19;
         double var23 = -var15 * var17 + var13 * var19;
         double var25 = Math.abs(var4);
         double var27 = Math.abs(var5);
         double var29 = var21 * var21 / (var25 * var25) + var23 * var23 / (var27 * var27);
         if (var29 > 1.0) {
            double var31 = Math.sqrt(var29);
            var25 *= var31;
            var27 *= var31;
         }

         double var57 = var25 * var25 * var27 * var27 - var25 * var25 * var23 * var23 - var27 * var27 * var21 * var21;
         double var33 = var25 * var25 * var23 * var23 + var27 * var27 * var21 * var21;
         double var35 = var33 == 0.0 ? 0.0 : Math.sqrt(Math.max(0.0, var57 / var33));
         if (var7 == var8) {
            var35 = -var35;
         }

         double var37 = var35 * var25 * var23 / var27;
         double var39 = var35 * -var27 * var21 / var25;
         double var41 = var13 * var37 - var15 * var39 + (var2 + var9) / 2.0;
         double var43 = var15 * var37 + var13 * var39 + (var3 + var10) / 2.0;
         double var45 = this.angleBetweenVectors(1.0, 0.0, (var21 - var37) / var25, (var23 - var39) / var27);
         double var47 = this.angleBetweenVectors((var21 - var37) / var25, (var23 - var39) / var27, (-var21 - var37) / var25, (-var23 - var39) / var27);
         if (!var8 && var47 > 0.0) {
            var47 -= Math.PI * 2;
         } else if (var8 && var47 < 0.0) {
            var47 += Math.PI * 2;
         }

         int var49 = Math.max(1, (int)Math.ceil(Math.abs(var47) / (Math.PI / 2)));
         double var50 = var47 / var49;

         for (int var52 = 0; var52 < var49; var52++) {
            double var53 = var45 + var52 * var50;
            double var55 = var53 + var50;
            this.VOxk(var1, var41, var43, var25, var27, var13, var15, var53, var55);
         }
      } else {
         var1.lineTo(var9, var10);
      }
   }

   private void VOxk(Float var1, double var2, double var4, double var6, double var8, double var10, double var12, double var14, double var16) {
      double var18 = 1.3333333333333333 * Math.tan((var16 - var14) / 4.0);
      double var20 = Math.cos(var14);
      double var22 = Math.sin(var14);
      double var24 = Math.cos(var16);
      double var26 = Math.sin(var16);
      double var28 = var20 - var18 * var22;
      double var30 = var22 + var18 * var20;
      double var32 = var24 + var18 * var26;
      double var34 = var26 - var18 * var24;
      var1.curveTo(
         this.ellipsePointX(var2, var6, var8, var10, var12, var28, var30),
         this.FOMUp6(var4, var6, var8, var10, var12, var28, var30),
         this.ellipsePointX(var2, var6, var8, var10, var12, var32, var34),
         this.FOMUp6(var4, var6, var8, var10, var12, var32, var34),
         this.ellipsePointX(var2, var6, var8, var10, var12, var24, var26),
         this.FOMUp6(var4, var6, var8, var10, var12, var24, var26)
      );
   }

   private float ellipsePointX(double var1, double var3, double var5, double var7, double var9, double var11, double var13) {
      return (float)(var1 + var3 * var7 * var11 - var5 * var9 * var13);
   }

   private float FOMUp6(double var1, double var3, double var5, double var7, double var9, double var11, double var13) {
      return (float)(var1 + var3 * var9 * var11 + var5 * var7 * var13);
   }

   private double angleBetweenVectors(double var1, double var3, double var5, double var7) {
      double var9 = var1 * var5 + var3 * var7;
      double var11 = Math.sqrt((var1 * var1 + var3 * var3) * (var5 * var5 + var7 * var7));
      double var13 = Math.acos(Math.max(-1.0, Math.min(1.0, var9 / Math.max(1.0E-6, var11))));
      return var1 * var7 - var3 * var5 < 0.0 ? -var13 : var13;
   }

   private boolean isPathCommand(String var1) {
      return var1.length() == 1 && Character.isLetter(var1.charAt(0));
   }

   private float parseSvgNumber(List<String> var1, int var2) {
      return java.lang.Float.parseFloat((String)var1.get(var2));
   }

   StatsFetcher$2 tD01(Overlay$16 var1) {
      String var2 = this.resolveStatsEndpoint();
      return this.isCustomApiEnabled() && var1 != null && !var2.isEmpty()
         ? StatsFetcher.getInstance().getCachedStats(Overlay$16.RlnF2(var1), Overlay$16.tFvn63(var1), this.ATgq(var1), var2)
         : null;
   }

   private void drawTagTooltip(String var1, int var2, int var3, float var4) {
      IFont var5 = this.getOverlayFont();
      String[] var6 = var1.replace("\r", "").split("\n");
      ArrayList var7 = new ArrayList();
      int var8 = 0;

      for (String var12 : var6) {
         String var13 = this.cleanTagLabel(var12).trim();
         if (!var13.isEmpty()) {
            var7.add(var13);
            var8 = Math.max(var8, this.zgyrI(var5, var13));
         }
      }

      if (!var7.isEmpty()) {
         ScaledResolution var15 = new ScaledResolution(mc);
         int var16 = var5.getFontHeight() + 3;
         int var17 = var7.size() * var16 + 6;
         int var18 = Math.min(var2 + 10, (int)(var15.getScaledWidth() / var4) - var8 - 10);
         int var20 = Math.min(var3 + 10, (int)(var15.getScaledHeight() / var4) - var17 - 6);
         var18 = Math.max(4, var18);
         var20 = Math.max(4, var20);
         RenderUtils.jxyoE(var18 - 4, var20 - 4, var18 + var8 + 5, var20 + var17 - 2, 3.0F, -535818224);

         for (int var14 = 0; var14 < var7.size(); var14++) {
            this.drawStringWithIcons(var5, (String)var7.get(var14), var18, var20 + var14 * var16, -1);
         }
      }
   }

   private void drawSkinHead(Overlay$16 var1, float var2, float var3, int var4, float var5) {
      ResourceLocation var6 = SkinCache.getPlayerSkin(Overlay$16.tFvn63(var1), Overlay$16.getPlayerInfo(var1));
      mc.getTextureManager().bindTexture(var6);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, var5);
      float var7 = Math.min(Math.min(var4 / 2.0F, 4.0F), Math.max(0.0F, (float)this.roundingLevel.getInput() * 0.45F));
      RoundedRect.drawRoundedTextureRegion(var2, var3, var4, var4, var7, var5, 0.125F, 0.125F, 0.25F, 0.25F);
      RoundedRect.drawRoundedTextureRegion(var2, var3, var4, var4, var7, var5, 0.625F, 0.125F, 0.75F, 0.25F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private int zgyrI(IFont var1, String var2) {
      int var3 = this.findIconIndex(var2);
      if (var3 >= 0) {
         int var7 = Character.codePointAt(var2, var3);
         int var5 = Character.charCount(var7);
         String var6 = var2.substring(0, var3) + var2.substring(var3 + var5);
         return this.zgyrI(var1, var6) + this.measureCodepointWidth(var1, var7);
      } else {
         String var4 = EnumChatFormatting.getTextWithoutFormattingCodes(var2 == null ? "" : var2);
         return var1.getStringWidth(var4 == null ? "" : var4);
      }
   }

   private int getTextBaselineOffset(Overlay$13 var1, int var2) {
      return Math.max(1, (var2 - Overlay$13.getFont(var1).getFontHeight()) / 2 - Overlay$13.getFont(var1).getTextTopOffset());
   }

   private void drawStringWithIcons(IFont var1, String var2, float var3, float var4, int var5) {
      int var6 = this.findIconIndex(var2);
      if (var6 >= 0) {
         String var7 = var2.substring(0, var6);
         int var8 = Character.codePointAt(var2, var6);
         int var9 = Character.charCount(var8);
         String var10 = var2.substring(var6 + var9);
         var1.drawString(var7, var3, var4, var5, this.textShadow.isToggled());
         float var11 = var3 + this.zgyrI(var1, var7);
         int var12 = this.measureCodepointWidth(var1, var8);
         float var13 = var4 + Math.max(0.0F, (var1.getFontHeight() - var12) / 2.0F) - 0.35F;
         this.WagRa(var1, var7, var8, var11, var13, var5);
         var1.drawString(var10, var11 + var12, var4, var5, this.textShadow.isToggled());
      } else {
         var1.drawString(var2, var3, var4, var5, this.textShadow.isToggled());
      }
   }

   private int rpJ1(int var1) {
      return this.measureCodepointWidth(this.getOverlayFont(), var1);
   }

   private int measureCodepointWidth(IFont var1, int var2) {
      if (this.isGlyphSupported(var2)) {
         return Math.max(4, Math.round(var1.getFontHeight() * 0.9F));
      } else {
         int var3 = mc.fontRendererObj.getStringWidth(new String(Character.toChars(var2)));
         return this.isCustomFont() ? Math.max(1, Math.round(var3 * 0.72F)) : var3;
      }
   }

   private void WagRa(IFont var1, String var2, int var3, float var4, float var5, int var6) {
      if (this.isGlyphSupported(var3)) {
         int var7 = this.getLastColorCodeArgb(var2);
         if (var7 == -1) {
            var7 = var6;
         } else {
            var7 = var6 & 0xFF000000 | var7 & 16777215;
         }

         int var8 = this.measureCodepointWidth(var1, var3);

         try {
            if (EXQhDjf8.QWFA(var3)) {
               FallbackGlyphShapes.mnxstly(var4, var5, var8, var7);
            } else {
               FallbackGlyphShapes.drawCircle(var4, var5, var8, var7);
            }

            return;
         } catch (RuntimeException var10) {
         }
      }

      String var12 = this.getLastFormattingCode(var2) + new String(Character.toChars(var3));
      if (!this.isCustomFont()) {
         mc.fontRendererObj.drawString(var12, var4, var5, var6, this.textShadow.isToggled());
      } else {
         float var13 = 0.72F;
         GlStateManager.pushMatrix();
         GlStateManager.translate(var4, var5, 0.0F);
         GlStateManager.scale(var13, var13, 1.0F);
         mc.fontRendererObj.drawString(var12, 0.0F, 0.0F, var6, this.textShadow.isToggled());
         GlStateManager.popMatrix();
      }
   }

   private boolean isGlyphSupported(int var1) {
      return EXQhDjf8.isSupportedGlyph(var1);
   }

   private int findIconIndex(String var1) {
      if (var1 == null) {
         return -1;
      } else {
         int var2 = 0;

         while (var2 < var1.length()) {
            int var3 = Character.codePointAt(var1, var2);
            if (EXQhDjf8.isSupportedGlyph(var3)) {
               return var2;
            }

            var2 += Character.charCount(var3);
         }

         return -1;
      }
   }

   private String getLastFormattingCode(String var1) {
      if (var1 == null) {
         return "";
      } else {
         for (int var2 = var1.length() - 2; var2 >= 0; var2--) {
            if (var1.charAt(var2) == 167) {
               return var1.substring(var2, var2 + 2);
            }
         }

         return "";
      }
   }

   private void oBei(List<Overlay$16> var1, final boolean var2) {
      SliderSetting var3 = var2 ? this.sortBy2 : this.sortBy;
      SliderSetting var4 = var2 ? this.direction2 : this.direction;
      final int var5 = (int)var3.getInput();
      if (var5 != 0) {
         final int var6 = (int)var4.getInput() == 1 ? 1 : -1;
         Collections.sort(var1, new Comparator<Overlay$16>() {
            public int compare(Overlay$16 var1, Overlay$16 var2x) {
               int var3x;
               if (var5 == 1) {
                  var3x = Overlay$16.tFvn63(var1).compareToIgnoreCase(Overlay$16.tFvn63(var2x));
               } else if (!var2 && var5 == 2) {
                  var3x = Overlay$16.getTeamText(var1).compareToIgnoreCase(Overlay$16.getTeamText(var2x));
               } else if ((var2 || var5 != 3) && (!var2 || var5 != 2)) {
                  var3x = Double.compare(Overlay.getSortValueFor(Overlay.this, var1, var5, var2), Overlay.getSortValueFor(Overlay.this, var2x, var5, var2));
               } else {
                  var3x = Overlay$16.getRankText(var1).compareToIgnoreCase(Overlay$16.getRankText(var2x));
               }

               return (var3x == 0 ? Integer.compare(Overlay$16.IxdltfE(var1), Overlay$16.IxdltfE(var2x)) : var3x) * var6;
            }
         });
      }
   }

   private double getSortValue(Overlay$16 var1, int var2, boolean var3) {
      if (Overlay$16.getStats(var1) == null) {
         return -1.0;
      } else if (var3) {
         switch (var2) {
            case 3:
               return this.PNAs(Overlay$16.getStats(var1).getSkyWarsLevel());
            case 4:
               return this.PNAs(Overlay$16.getStats(var1).getSkyWarsKdr());
            case 5:
               return this.PNAs(Overlay$16.getStats(var1).getSkyWarsWlr());
            case 6:
               return this.PNAs(Overlay$16.getStats(var1).getSniperScore());
            case 7:
               return this.PNAs(Overlay$16.getStatValue(var1, Overlay$18.SW_KILLS));
            case 8:
               return this.PNAs(Overlay$16.getStatValue(var1, Overlay$18.SW_WINS));
            default:
               return -1.0;
         }
      } else {
         switch (var2) {
            case 4:
               return this.PNAs(Overlay$16.getStats(var1).getBedwarsLevel());
            case 5:
               return this.PNAs(Overlay$16.getStats(var1).getBedwarsWinstreak());
            case 6:
               return this.PNAs(Overlay$16.getStats(var1).getBedwarsFkdr());
            case 7:
               return this.PNAs(Overlay$16.getStats(var1).getBedwarsWlr());
            case 8:
               return this.PNAs(Overlay$16.getStats(var1).getBedwarsFinalKills());
            case 9:
               return this.PNAs(Overlay$16.getStats(var1).getBedwarsWins());
            case 10:
               return this.PNAs(Overlay$16.getStats(var1).getBedwarsMonthlyFkdr());
            case 11:
               return this.PNAs(Overlay$16.getStats(var1).getFinalKillsPerStar());
            case 12:
               return this.PNAs(Overlay$16.getStats(var1).getSniperScore());
            default:
               return -1.0;
         }
      }
   }

   private double PNAs(double var1) {
      return HypixelPlayerStats.isFiniteNumber(var1) ? var1 : -1.0;
   }

    boolean isStatColumn(Overlay$18 var1_1) {
        switch (var1_1) {
            case STAR: 
            case WINSTREAK: 
            case FKDR: 
            case WLR: 
            case FINALS: 
            case WINS: 
            case MONTHLY_FKDR: 
            case FINALS_PER_STAR: 
            case SW_NAME: 
            case SW_KDR: 
            case SW_WLR: 
            case SW_KILLS: 
            case SW_WINS: 
            case SW_SNIPER: 
            case SNIPER: {
                return true;
            }
        }
        return false;
    }

   String formatStatNumber(double var1, int var3) {
      if (!HypixelPlayerStats.isFiniteNumber(var1)) {
         return "-";
      } else {
         return var3 == 0 ? String.valueOf((long)Math.floor(var1)) : String.format(Locale.ROOT, "%." + var3 + "f", var1);
      }
   }

   private void drawPanelRect(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (var5 <= 0.0F) {
         RenderUtils.XNRNki(var1, var2, var1 + var3, var2 + var4, var6);
      } else {
         RoundedRect.drawRoundedRectArgb(var1, var2, var3, var4, var5, var6);
      }
   }

   private void drawPanelHeaderRect(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (var5 <= 0.0F) {
         RenderUtils.XNRNki(var1, var2, var1 + var3, var2 + var4, var6);
      } else {
         RoundedRect.drawRoundedRectWithCornerFlags(var1, var2, var3, var4, var5, var6, false, false, true, true);
      }
   }

   String formatFkdrValue(double var1) {
      String var3 = this.formatStatNumber(var1, 2);
      if ("-".equals(var3)) {
         return var3;
      } else if (var1 >= 100.0) {
         return "§5" + var3;
      } else if (var1 >= 50.0) {
         return "§d" + var3;
      } else if (var1 >= 30.0) {
         return "§4" + var3;
      } else if (var1 >= 20.0) {
         return "§c" + var3;
      } else if (var1 >= 10.0) {
         return "§6" + var3;
      } else if (var1 >= 7.0) {
         return "§e" + var3;
      } else if (var1 >= 5.0) {
         return "§2" + var3;
      } else if (var1 >= 3.0) {
         return "§a" + var3;
      } else {
         return var1 >= 1.0 ? "§f" + var3 : "§7" + var3;
      }
   }

   String formatWinstreakValue(double var1) {
      String var3 = this.formatStatNumber(var1, 0);
      if ("-".equals(var3)) {
         return var3;
      } else if (var1 >= 500.0) {
         return "§5" + var3;
      } else if (var1 >= 250.0) {
         return "§d" + var3;
      } else if (var1 >= 100.0) {
         return "§4" + var3;
      } else if (var1 >= 75.0) {
         return "§c" + var3;
      } else if (var1 >= 50.0) {
         return "§6" + var3;
      } else if (var1 >= 40.0) {
         return "§e" + var3;
      } else if (var1 >= 25.0) {
         return "§2" + var3;
      } else if (var1 >= 15.0) {
         return "§a" + var3;
      } else {
         return var1 >= 5.0 ? "§f" + var3 : "§7" + var3;
      }
   }

   String formatWlrValue(double var1) {
      String var3 = this.formatStatNumber(var1, 2);
      if ("-".equals(var3)) {
         return var3;
      } else if (var1 >= 30.0) {
         return "§5" + var3;
      } else if (var1 >= 15.0) {
         return "§d" + var3;
      } else if (var1 >= 9.0) {
         return "§4" + var3;
      } else if (var1 >= 6.0) {
         return "§c" + var3;
      } else if (var1 >= 3.0) {
         return "§6" + var3;
      } else if (var1 >= 2.1) {
         return "§e" + var3;
      } else if (var1 >= 1.5) {
         return "§2" + var3;
      } else if (var1 >= 0.9) {
         return "§a" + var3;
      } else {
         return var1 >= 0.3 ? "§f" + var3 : "§7" + var3;
      }
   }

   String formatFinalsValue(double var1) {
      String var3 = this.formatStatNumber(var1, 0);
      if ("-".equals(var3)) {
         return var3;
      } else if (var1 >= 100000.0) {
         return "§5" + var3;
      } else if (var1 >= 50000.0) {
         return "§d" + var3;
      } else if (var1 >= 25000.0) {
         return "§4" + var3;
      } else if (var1 >= 15000.0) {
         return "§c" + var3;
      } else if (var1 >= 7500.0) {
         return "§6" + var3;
      } else if (var1 >= 5000.0) {
         return "§e" + var3;
      } else if (var1 >= 2500.0) {
         return "§2" + var3;
      } else if (var1 >= 1000.0) {
         return "§a" + var3;
      } else {
         return var1 >= 500.0 ? "§f" + var3 : "§7" + var3;
      }
   }

   String formatWinsValue(double var1) {
      String var3 = this.formatStatNumber(var1, 0);
      if ("-".equals(var3)) {
         return var3;
      } else if (var1 >= 30000.0) {
         return "§5" + var3;
      } else if (var1 >= 15000.0) {
         return "§d" + var3;
      } else if (var1 >= 7500.0) {
         return "§4" + var3;
      } else if (var1 >= 4500.0) {
         return "§c" + var3;
      } else if (var1 >= 2250.0) {
         return "§6" + var3;
      } else if (var1 >= 1500.0) {
         return "§e" + var3;
      } else if (var1 >= 450.0) {
         return "§2" + var3;
      } else if (var1 >= 300.0) {
         return "§a" + var3;
      } else {
         return var1 >= 150.0 ? "§f" + var3 : "§7" + var3;
      }
   }

   String formatFinalsPerStarValue(double var1) {
      String var3 = this.formatStatNumber(var1, 2);
      if ("-".equals(var3)) {
         return var3;
      } else if (var1 >= 80.0) {
         return "§5" + var3;
      } else if (var1 >= 70.0) {
         return "§d" + var3;
      } else if (var1 >= 60.0) {
         return "§4" + var3;
      } else if (var1 >= 50.0) {
         return "§c" + var3;
      } else if (var1 >= 40.0) {
         return "§6" + var3;
      } else if (var1 >= 30.0) {
         return "§e" + var3;
      } else if (var1 >= 25.0) {
         return "§2" + var3;
      } else if (var1 >= 20.0) {
         return "§a" + var3;
      } else {
         return var1 >= 15.0 ? "§f" + var3 : "§7" + var3;
      }
   }

   String WDkp(double var1) {
      if (!HypixelPlayerStats.isFiniteNumber(var1)) {
         return "-";
      } else {
         int var3 = (int)Math.floor(var1);
         Overlay$15 var4 = this.TURN(Math.min(5000, Math.max(0, var3 / 100 * 100)));
         return Overlay$15.getOpeningPrefix(var4)
            + "["
            + this.interleaveStarColors(String.valueOf(var3), Overlay$15.sJxn(var4))
            + Overlay$15.tkoU(var4)
            + Overlay$15.MYHsZ0(var4)
            + Overlay$15.getClosingColor(var4)
            + "]";
      }
   }

   String lfQd(Overlay$16 var1) {
      if (Overlay$16.getStats(var1) == null) {
         return "-";
      } else {
         String var2 = SkywarsLevelParser.Sg45(Overlay$16.getStats(var1).getDisplayTag());
         if (!var2.isEmpty()) {
            return var2;
         } else {
            double var3 = Overlay$16.getStatValue(var1, Overlay$18.SW_STAR);
            String var5 = this.formatStatNumber(var3, var3 == Math.rint(var3) ? 0 : 2);
            return "-".equals(var5) ? var5 : "§7[§7" + var5 + "§7✯§7]§r";
         }
      }
   }

   String UThNqd(double var1) {
      String var3 = this.formatStatNumber(var1, 2);
      if ("-".equals(var3)) {
         return var3;
      } else if (var1 >= 50.0) {
         return "§5" + var3;
      } else if (var1 >= 25.0) {
         return "§d" + var3;
      } else if (var1 >= 15.0) {
         return "§4" + var3;
      } else if (var1 >= 10.0) {
         return "§c" + var3;
      } else if (var1 >= 5.0) {
         return "§6" + var3;
      } else if (var1 >= 3.5) {
         return "§e" + var3;
      } else if (var1 >= 2.5) {
         return "§2" + var3;
      } else if (var1 >= 1.5) {
         return "§a" + var3;
      } else {
         return var1 >= 0.5 ? "§f" + var3 : "§7" + var3;
      }
   }

   String SUSe(double var1) {
      String var3 = this.formatStatNumber(var1, 2);
      if ("-".equals(var3)) {
         return var3;
      } else if (var1 >= 10.0) {
         return "§5" + var3;
      } else if (var1 >= 5.0) {
         return "§d" + var3;
      } else if (var1 >= 3.0) {
         return "§4" + var3;
      } else if (var1 >= 2.0) {
         return "§c" + var3;
      } else if (var1 >= 1.0) {
         return "§6" + var3;
      } else if (var1 >= 0.7) {
         return "§e" + var3;
      } else if (var1 >= 0.5) {
         return "§2" + var3;
      } else if (var1 >= 0.3) {
         return "§a" + var3;
      } else {
         return var1 >= 0.1 ? "§f" + var3 : "§7" + var3;
      }
   }

   private String interleaveStarColors(String var1, String var2) {
      if (var2.length() <= 2) {
         return var2 + var1;
      } else {
         StringBuilder var3 = new StringBuilder();

         for (int var4 = 0; var4 < var1.length(); var4++) {
            int var5 = Math.min(var4 * 2, var2.length() - 2);
            var3.append(var2.substring(var5, var5 + 2)).append(var1.charAt(var4));
         }

         return var3.toString();
      }
   }

   private Overlay$15 TURN(int var1) {
      switch (var1) {
         case 100:
            return this.createStarTierStyle("§f", "§f", "§f", "§f", "✫");
         case 200:
            return this.createStarTierStyle("§6", "§6", "§6", "§6", "✫");
         case 300:
            return this.createStarTierStyle("§b", "§b", "§b", "§b", "✫");
         case 400:
            return this.createStarTierStyle("§2", "§2", "§2", "§2", "✫");
         case 500:
            return this.createStarTierStyle("§3", "§3", "§3", "§3", "✫");
         case 600:
            return this.createStarTierStyle("§c", "§c", "§c", "§c", "✫");
         case 700:
            return this.createStarTierStyle("§d", "§d", "§d", "§d", "✫");
         case 800:
            return this.createStarTierStyle("§9", "§9", "§9", "§9", "✫");
         case 900:
            return this.createStarTierStyle("§5", "§5", "§5", "§5", "✫");
         case 1000:
            return this.createStarTierStyle("§c", "§5", "§6§e§a§b", "§d", "✫");
         case 1100:
            return this.createStarTierStyle("§8", "§8", "§f", "§7", "✪");
         case 1200:
            return this.createStarTierStyle("§8", "§8", "§e", "§6", "✪");
         case 1300:
            return this.createStarTierStyle("§8", "§8", "§b", "§3", "✪");
         case 1400:
            return this.createStarTierStyle("§8", "§8", "§a", "§2", "✪");
         case 1500:
            return this.createStarTierStyle("§8", "§8", "§3", "§9", "✪");
         case 1600:
            return this.createStarTierStyle("§8", "§8", "§c", "§4", "✪");
         case 1700:
            return this.createStarTierStyle("§8", "§8", "§d", "§5", "✪");
         case 1800:
            return this.createStarTierStyle("§8", "§8", "§9", "§1", "✪");
         case 1900:
            return this.createStarTierStyle("§8", "§7", "§5", "§8", "✪");
         case 2000:
            return this.createStarTierStyle("§8", "§8", "§7§f§f§7", "§7", "✪");
         case 2100:
            return this.createStarTierStyle("§7", "§6", "§7§e§e§6", "§6", "⚝");
         case 2200:
            return this.createStarTierStyle("§7", "§3", "§7§3§3§3", "§3", "⚝");
         case 2300:
            return this.createStarTierStyle("§5", "§e", "§5§d§6§e", "§e", "⚝");
         case 2400:
            return this.createStarTierStyle("§b", "§8", "§b§f§8§8", "§8", "⚝");
         case 2500:
            return this.createStarTierStyle("§7", "§2", "§7§a§2§2", "§2", "⚝");
         case 2600:
            return this.createStarTierStyle("§4", "§5", "§4§c§d§5", "§5", "⚝");
         case 2700:
            return this.createStarTierStyle("§6", "§8", "§6§7§8§8", "§8", "⚝");
         case 2800:
            return this.createStarTierStyle("§a", "§e", "§a§2§6§e", "§e", "⚝");
         case 2900:
            return this.createStarTierStyle("§b", "§1", "§b§3§9§1", "§1", "⚝");
         case 3000:
            return this.createStarTierStyle("§e", "§4", "§e§6§c§4", "§4", "⚝");
         case 3100:
            return this.createStarTierStyle("§9", "§e", "§9§3§6§e", "§6", "✥");
         case 3200:
            return this.createStarTierStyle("§c", "§c", "§4§7§7§4", "§c", "✥");
         case 3300:
            return this.createStarTierStyle("§9", "§4", "§9§d§c§c", "§4", "✥");
         case 3400:
            return this.createStarTierStyle("§2", "§2", "§a§d§d§5", "§5", "✥");
         case 3500:
            return this.createStarTierStyle("§c", "§a", "§4§4§2§a", "§a", "✥");
         case 3600:
            return this.createStarTierStyle("§a", "§1", "§a§b§9§9", "§1", "✥");
         case 3700:
            return this.createStarTierStyle("§4", "§3", "§4§c§b§3", "§3", "✥");
         case 3800:
            return this.createStarTierStyle("§1", "§1", "§9§5§5§d", "§d", "✥");
         case 3900:
            return this.createStarTierStyle("§c", "§9", "§c§a§3§9", "§9", "✥");
         case 4000:
            return this.createStarTierStyle("§5", "§e", "§5§c§6§e", "§e", "✥");
         case 4100:
            return this.createStarTierStyle("§5", "§5", "§6§c§d§d", "§d", "✥");
         case 4200:
            return this.createStarTierStyle("§1", "§7", "§9§3§b§f", "§7", "✥");
         case 4300:
            return this.createStarTierStyle("§8", "§8", "§5§7§7§5", "§5", "✥");
         case 4400:
            return this.createStarTierStyle("§2", "§8", "§a§e§6§5", "§5", "✥");
         case 4500:
            return this.createStarTierStyle("§f", "§3", "§f§b§b§3", "§3", "✥");
         case 4600:
            return this.createStarTierStyle("§3", "§5", "§b§e§6§6", "§5", "✥");
         case 4700:
            return this.createStarTierStyle("§f", "§9", "§4§c§9§9", "§1", "✥");
         case 4800:
            return this.createStarTierStyle("§5", "§3", "§c§6§e§b", "§b", "✥");
         case 4900:
            return this.createStarTierStyle("§2", "§2", "§a§f§f§a", "§a", "✥");
         case 5000:
            return this.createStarTierStyle("§4", "§1", "§5§9§9§1", "§1", "✥");
         default:
            return this.createStarTierStyle("§7", "§7", "§7", "§7", "✫");
      }
   }

   private Overlay$15 createStarTierStyle(String var1, String var2, String var3, String var4, String var5) {
      return new Overlay$15(var1, var2, var3, var4, var5);
   }

   private String defaultToDash(String var1) {
      return var1 != null && !var1.isEmpty() ? var1 : "-";
   }

   String PCpc(Overlay$16 var1) {
      return this.formatSessionTime(Overlay$16.getStats(var1));
   }

   public String formatSessionTime(HypixelPlayerStats var1) {
      if (var1 != null
         && HypixelPlayerStats.isFiniteNumber(var1.getLastLogout())
         && !(var1.getLastLogout() <= 0.0)) {
         long var2 = (long)var1.getLastLogout();
         return this.FDJVb(var2) + this.SIAjL0(var2, System.currentTimeMillis());
      } else {
         return "§cAPI";
      }
   }

   private String extractTagReason(String var1) {
      if (var1 != null && !var1.trim().isEmpty()) {
         String[] var2 = var1.replace("\r", "").split("\n");

         for (String var6 : var2) {
            String var7 = var6.trim();
            if (var7.startsWith("- ")) {
               return var7.substring(2).trim();
            }

            int var8 = var7.lastIndexOf(" - ");
            if (var8 >= 0 && var8 + 3 < var7.length()) {
               String var9 = var7.substring(var8 + 3).trim();
               var9 = var9.replaceAll("^\\d{1,2}:\\d{2}:\\d{2}\\s+", "");
               if (var9.endsWith(")")) {
                  var9 = var9.substring(0, var9.length() - 1).trim();
               }

               return var9;
            }
         }

         return "";
      } else {
         return "";
      }
   }

   private String FDJVb(long var1) {
      long var3 = System.currentTimeMillis() - var1;
      if (var3 < 600000L) {
         return "§a";
      } else if (var3 < 1800000L) {
         return "§2";
      } else if (var3 < 3600000L) {
         return "§e";
      } else if (var3 < 10800000L) {
         return "§6";
      } else {
         return var3 < 86400000L ? "§c" : "§7";
      }
   }

   private String SIAjL0(long var1, long var3) {
      long var5 = Math.max(0L, (var3 - var1) / 1000L);
      long[] var7 = new long[]{31557600L, 2629800L, 86400L, 3600L, 60L, 1L};
      String[] var8 = new String[]{"y", "mo", "d", "h", "m", "s"};
      StringBuilder var9 = new StringBuilder();
      int var10 = 0;

      for (int var11 = 0; var11 < var7.length && var10 < 2; var11++) {
         long var12 = var5 / var7[var11];
         var5 %= var7[var11];
         if (var12 > 0L || var11 == var7.length - 1 && var10 == 0) {
            var9.append(var12).append(var8[var11]);
            var10++;
         }
      }

      return var9.toString();
   }

   private int getUsernameColor(Overlay$16 var1) {
      int var2 = (int)this.usernameColor.getInput();
      if (Overlay$16.getPlayerInfo(var1) == null && var2 == 0) {
         return Overlay$16.getRankColor(var1);
      } else {
         return var2 == 2 ? this.username.getArgb() : (var2 == 1 ? Overlay$16.getRankColor(var1) : Overlay$16.getTeamColor(var1));
      }
   }

   private int getLogNameColor(Overlay$16 var1) {
      return this.manualPlayers.contains(this.normalizeName(Overlay$16.tFvn63(var1))) ? Overlay$16.getRankColor(var1) : this.getUsernameColor(var1);
   }

   private String getNearestColorCode(int var1) {
      int var2 = var1 & 16777215;
      int var3 = 7;
      int var4 = Integer.MAX_VALUE;

      for (int var5 = 0; var5 < 16; var5++) {
         int var6 = mc.fontRendererObj.getColorCode("0123456789abcdef".charAt(var5)) & 16777215;
         int var7 = (var2 >> 16 & 0xFF) - (var6 >> 16 & 0xFF);
         int var8 = (var2 >> 8 & 0xFF) - (var6 >> 8 & 0xFF);
         int var9 = (var2 & 0xFF) - (var6 & 0xFF);
         int var10 = var7 * var7 + var8 * var8 + var9 * var9;
         if (var10 < var4) {
            var4 = var10;
            var3 = var5;
         }
      }

      return "&" + "0123456789abcdef".charAt(var3);
   }

   String qclLe(Overlay$16 var1) {
      if (this.manualPlayers.contains(this.normalizeName(Overlay$16.tFvn63(var1)))) {
         return Overlay$16.tFvn63(var1);
      } else {
         int var2 = (int)this.usernameColor.getInput();
         if (var2 == 1 || Overlay$16.getPlayerInfo(var1) == null && var2 == 0) {
            String var3 = Overlay$16.getRankText(var1);
            return var3 != null && !var3.isEmpty() && !"-".equals(var3) ? var3 + " " + Overlay$16.tFvn63(var1) : Overlay$16.tFvn63(var1);
         } else {
            return Overlay$16.tFvn63(var1);
         }
      }
   }

   private int getLastColorCodeArgb(String var1) {
      if (var1 == null) {
         return -1;
      } else {
         for (int var2 = var1.length() - 2; var2 >= 0; var2--) {
            if (var1.charAt(var2) == 167) {
               char var3 = Character.toLowerCase(var1.charAt(var2 + 1));
               if ("0123456789abcdef".indexOf(var3) >= 0) {
                  return 0xFF000000 | mc.fontRendererObj.getColorCode(var3);
               }
            }
         }

         return -1;
      }
   }

   private String describeLastColorCode(String var1) {
      if (var1 == null) {
         return "";
      } else {
         for (int var2 = var1.length() - 2; var2 >= 0; var2--) {
            if (var1.charAt(var2) == 167) {
               switch (Character.toLowerCase(var1.charAt(var2 + 1))) {
                  case '0':
                     return "Black";
                  case '1':
                     return "Blue";
                  case '2':
                     return "Green";
                  case '3':
                     return "Aqua";
                  case '4':
                     return "Red";
                  case '5':
                     return "Purple";
                  case '6':
                     return "Gold";
                  case '7':
                     return "Gray";
                  case '8':
                     return "Dark Gray";
                  case '9':
                     return "Blue";
                  case ':':
                  case ';':
                  case '<':
                  case '=':
                  case '>':
                  case '?':
                  case '@':
                  case 'A':
                  case 'B':
                  case 'C':
                  case 'D':
                  case 'E':
                  case 'F':
                  case 'G':
                  case 'H':
                  case 'I':
                  case 'J':
                  case 'K':
                  case 'L':
                  case 'M':
                  case 'N':
                  case 'O':
                  case 'P':
                  case 'Q':
                  case 'R':
                  case 'S':
                  case 'T':
                  case 'U':
                  case 'V':
                  case 'W':
                  case 'X':
                  case 'Y':
                  case 'Z':
                  case '[':
                  case '\\':
                  case ']':
                  case '^':
                  case '_':
                  case '`':
                  default:
                     break;
                  case 'a':
                     return "Green";
                  case 'b':
                     return "Aqua";
                  case 'c':
                     return "Red";
                  case 'd':
                     return "Pink";
                  case 'e':
                     return "Yellow";
                  case 'f':
                     return "White";
               }
            }
         }

         return "";
      }
   }

   public static void openEditorScreen(Overlay var0, boolean var1) {
      var0.VCnge(var1);
   }

   public static void LNWCfr5(Overlay var0) {
      var0.MAKN();
   }

   public static void sJd0(Overlay var0) {
      var0.ISyCp();
   }

   public static Map getTrackedPlayerMap(Overlay var0) {
      return var0.trackedPlayers;
   }

   public static Minecraft getMinecraftInstance() {
      return mc;
   }

   public static double getSortValueFor(Overlay var0, Overlay$16 var1, int var2, boolean var3) {
      return var0.getSortValue(var1, var2, var3);
   }

   public static SliderSetting getHeaderSetting(Overlay var0) {
      return var0.header;
   }

   public static int measureTextWidth(Overlay var0, IFont var1, String var2) {
      return var0.zgyrI(var1, var2);
   }

   public static boolean isStatColumnFor(Overlay var0, Overlay$18 var1) {
      return var0.isStatColumn(var1);
   }

   public static String UruX(Overlay var0, double var1) {
      return var0.WDkp(var1);
   }

   public static String getDisplayNameText(Overlay var0, Overlay$16 var1) {
      return var0.qclLe(var1);
   }

   public static String getWinstreakText(Overlay var0, double var1) {
      return var0.formatWinstreakValue(var1);
   }

   public static String getFkdrText(Overlay var0, double var1) {
      return var0.formatFkdrValue(var1);
   }

   public static String getWlrText(Overlay var0, double var1) {
      return var0.formatWlrValue(var1);
   }

   public static String getFinalsText(Overlay var0, double var1) {
      return var0.formatFinalsValue(var1);
   }

   public static String QfLim9(Overlay var0, double var1) {
      return var0.formatWinsValue(var1);
   }

   public static String getFinalsPerStarText(Overlay var0, double var1) {
      return var0.formatFinalsPerStarValue(var1);
   }

   public static String getSkywarsStarText(Overlay var0, Overlay$16 var1) {
      return var0.lfQd(var1);
   }

   public static String getSkywarsKdrText(Overlay var0, double var1) {
      return var0.UThNqd(var1);
   }

   public static String getSkywarsWlrText(Overlay var0, double var1) {
      return var0.SUSe(var1);
   }

   public static String formatNumberText(Overlay var0, double var1, int var3) {
      return var0.formatStatNumber(var1, var3);
   }

   public static String getSessionTimeText(Overlay var0, Overlay$16 var1) {
      return var0.PCpc(var1);
   }

   public static StatsFetcher$2 getStatsForPlayer(Overlay var0, Overlay$16 var1) {
      return var0.tD01(var1);
   }

   public static int getUsernameColorFor(Overlay var0, Overlay$16 var1) {
      return var0.getUsernameColor(var1);
   }

   public static ColorSetting getTextColorSetting(Overlay var0) {
      return var0.text;
   }

   public static String orDash(Overlay var0, String var1) {
      return var0.defaultToDash(var1);
   }

   public static String getLastColorName(Overlay var0, String var1) {
      return var0.describeLastColorCode(var1);
   }

   public static int TUEd6(Overlay var0, String var1) {
      return var0.getLastColorCodeArgb(var1);
   }

   public static Minecraft getMc() {
      return mc;
   }

   static {
      Overlay$18[] var10000 = new Overlay$18[16];
      var10000[0] = Overlay$18.HEAD;
      var10000[1] = Overlay$18.TEAM;
      var10000[2] = Overlay$18.STAR;
      var10000[3] = Overlay$18.RANK;
      var10000[4] = Overlay$18.NAME;
      var10000[5] = Overlay$18.WINSTREAK;
      var10000[6] = Overlay$18.FKDR;
      var10000[7] = Overlay$18.WLR;
      var10000[8] = Overlay$18.FINALS;
      var10000[9] = Overlay$18.WINS;
      var10000[10] = Overlay$18.MONTHLY_FKDR;
      var10000[11] = Overlay$18.FINALS_PER_STAR;
      var10000[12] = Overlay$18.SESSION;
      var10000[13] = Overlay$18.SNIPER;
      var10000[14] = Overlay$18.HP;
      var10000[15] = Overlay$18.TAGS;
      wfX82 = var10000;
      var10000 = new Overlay$18[9];
      var10000[0] = Overlay$18.SW_STAR;
      var10000[1] = Overlay$18.SW_RANK;
      var10000[2] = Overlay$18.SW_NAME;
      var10000[3] = Overlay$18.SW_KDR;
      var10000[4] = Overlay$18.SW_WLR;
      var10000[5] = Overlay$18.SW_KILLS;
      var10000[6] = Overlay$18.SW_WINS;
      var10000[7] = Overlay$18.SW_SNIPER;
      var10000[8] = Overlay$18.SW_HP;
      SKYWARS_COLUMNS = var10000;
   }
}
