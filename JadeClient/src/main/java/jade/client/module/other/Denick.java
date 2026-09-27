// Jade recovery: module: Denick (other); original class: jade.deps.eLz.wpmQGvzwn
package jade.client.module.other;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import jade.client.common.ChatUtils;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.ExternalChatOverlay;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.Subscribe;
import jade.client.common.WhisperShortcuts;
import jade.client.event.ChatReceivedEvent;
import jade.client.event.EntityJoinWorldEvent;
import jade.client.event.GuiOpenEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.RenderTickEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.render.Arraylist;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.deps.gson.GsonBuilder;
import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;




import jade.deps.loader107.InjectionPaths;

import jade.mixin.impl.accessor.IAccessorS30PacketWindowItems;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

@ModuleInfo
public class Denick extends Module {
   private static final int GDVI = 13;
   private static final int CHAT_RESPONSE_TIMEOUT_TICKS = 60;
   private static final int BLOCK_LIST_TIMEOUT_TICKS = 60;
   private static final int i82 = 20;
   private static final long sT4 = 7200000L;
   private static final String DENICK_API_URL = "https://redacted/api/denick";
   private static final String DENICKS_FILE_NAME = "denicks.json";
   private static final String RANDOM_KILL_MESSAGE = "random_kill_message";
   private static final String RANDOM_FAVORITE_KILL_MESSAGE = "random_favorite_kill_message";
   private static final String RANDOM_COSMETIC = "random_cosmetic";
   private static final String RANDOM_FAVORITE_COSMETIC = "random_favorite_cosmetic";
   private static final Pattern UNBLOCKED_MESSAGE_PATTERN = Pattern.compile("^Unblocked ([A-Za-z0-9_]{3,16})\\.$");
   private static final Pattern BLOCKED_LIST_HEADER_PATTERN = Pattern.compile(
      "^-+\\s*Blocked Players \\(Page \\d+ of \\d+\\)\\s*-+$"
   );
   private static final Pattern BLOCKED_LIST_ENTRY_PATTERN = Pattern.compile("^\\d+\\.\\s+([A-Za-z0-9_]{3,16})$");
   private final BooleanSetting skinDenick;
   private final BooleanSetting uuidDenick;
   private final BooleanSetting showFailedDenicks;
   private final BooleanSetting bedWarsCosmeticDenick;
   private final BooleanSetting showInTab;
   private final Set<String> skinCheckedUuids = new HashSet<>();
   private final Map<UUID, GameProfile> tZpt = new HashMap<>();
   private final Set<String> Xsym5 = new HashSet<>();
   private final Set<String> pendingUuidLookups = ConcurrentHashMap.newKeySet();
   private final Map<String, Integer> Do9 = new HashMap<>();
   private final Set<String> lkv = new LinkedHashSet<>();
   private final Map<String, String> killMessagesByNick = new LinkedHashMap<>();
   private final Map<String, Denick$13> statCountersByNick = new LinkedHashMap<>();
   private final Map<String, LinkedHashSet<String>> z42 = new LinkedHashMap<>();
   private final Set<String> promptedNicks = new HashSet<>();
   private final Set<String> notifiedReadyNicks = new HashSet<>();
   private final Set<String> confirmedNicks = new HashSet<>();
   private final Set<String> CkmGeu = new HashSet<>();
   private final Map<String, Denick$15> savedDenicks = new LinkedHashMap<>();
   private final List<Denick$18> rbPko = this.buildKillMessageDefinitions();
   private final Object nkCi = new Object();
   private final Object YEspO = new Object();
   private volatile Denick$17 Ob0 = Denick$17.IDLE;
   private volatile String OIBq = "";
   private volatile List<Denick$19> candidateNicks = Collections.emptyList();
   private String cAp = "";
   private String qPi5 = "";
   private Denick$13 statFilters;
   private List<Denick$12> cosmeticFilters = Collections.emptyList();
   private String pendingNick = "";
   private String lastCheckedNick = "";
   private boolean verifyingBlockedNick;
   private String gKc = "";
   private final Set<String> failedVerificationNicks = new HashSet<>();
   private boolean awaitingUnblockResult;
   private boolean Mji;
   private boolean verifyHeaderSeen;
   private boolean verifyingDatabase;
   private int phaseWaitTicks;
   private int stateGeneration;
   private List<String> ambiguousNicks = Collections.emptyList();
   private final LinkedHashSet<String> collectedBlockedNicks = new LinkedHashSet<>();
   private boolean Tcxha;
   private boolean awaitingBlockList;
   private boolean listHeaderSeen;
   private int YgCuk;
   private int Xw3;
   private final LinkedHashSet<String> AZd8 = new LinkedHashSet<>();
   private int candidateIndex = -1;
   private int Vte = 0;
   private int actionDelayTicks = 0;
   private int trackedWindowId = -1;
   private String trackedWindowTitle = "";
   private boolean bruteMode;
   private int GFf = 0;
   private static final Denick$11[] COSMETIC_CATEGORIES;
   private static final String[] COSMETIC_KEYWORDS;
   private static final Map<String, String[]> KILL_MESSAGE_SAMPLES = new LinkedHashMap<>();
   private static final Set<String> sZ8;

   public Denick() {
      super("Denick", Category.other);
      this.registerSetting(new DescriptionSetting("Reveals nicked players."));
      this.registerSetting(
         this.skinDenick = new BooleanSetting(
            "Skin denick", true
         )
      );
      this.registerSetting(
         this.uuidDenick = new BooleanSetting(
            "UUID denick", false
         )
      );
      this.registerSetting(
         this.bedWarsCosmeticDenick = new BooleanSetting(
            "BedWars cosmetic denick",
            true
         )
      );
      this.registerSetting(this.showInTab = new BooleanSetting("Show in tab", true));
      this.registerSetting(
         this.showFailedDenicks = new BooleanSetting(
            "Show failed denicks", true
         )
      );
      this.loadSavedDenicks();
   }

   @Override
   public void onEnable() {
      this.skinCheckedUuids.clear();
      this.tZpt.clear();
      this.Xsym5.clear();
      this.pendingUuidLookups.clear();
      this.Do9.clear();
      this.lkv.clear();
      this.killMessagesByNick.clear();
      this.statCountersByNick.clear();
      this.z42.clear();
      this.promptedNicks.clear();
      this.notifiedReadyNicks.clear();
      this.confirmedNicks.clear();
      this.CkmGeu.clear();
      this.resetBlockListState();
      this.loadSavedDenicks();
      this.resetDenickState();
      this.GFf = 0;
   }

   @Override
   public void onDisable() {
      this.skinCheckedUuids.clear();
      this.tZpt.clear();
      this.Xsym5.clear();
      this.pendingUuidLookups.clear();
      this.Do9.clear();
      this.lkv.clear();
      this.killMessagesByNick.clear();
      this.statCountersByNick.clear();
      this.z42.clear();
      this.promptedNicks.clear();
      this.notifiedReadyNicks.clear();
      this.confirmedNicks.clear();
      this.CkmGeu.clear();
      this.resetBlockListState();
      this.resetDenickState();
   }

   @Subscribe
   public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
      if (var1.entity == mc.thePlayer) {
         this.skinCheckedUuids.clear();
         this.tZpt.clear();
         this.Xsym5.clear();
         this.pendingUuidLookups.clear();
         this.Do9.clear();
         this.CkmGeu.clear();
         this.resetBlockListState();
         if (this.Ob0 != Denick$17.SEARCHING && this.Ob0 != Denick$17.PAUSED) {
            this.resetDenickState();
         }
      }
   }

   @Override
   public void onUpdate() {
      if (ClientUtils.isInWorld()) {
         if (mc.getNetHandler() != null) {
            this.tickDenick();
            this.GFf++;
            if (this.GFf >= 20) {
               this.GFf = 0;
               Collection var1 = mc.getNetHandler().getPlayerInfoMap();
               if (var1 != null) {
                  for (NetworkPlayerInfo var3 : (java.lang.Iterable<NetworkPlayerInfo>) (java.lang.Iterable<?>) (var1)) {
                     if (var3.getGameProfile() != null && var3.getGameProfile().getId() != null) {
                        String var4 = var3.getGameProfile().getId().toString();
                        String var5 = var3.getGameProfile().getName();
                        if (var5 != null && !var5.isEmpty() && this.skinDenick.isToggled()) {
                           this.denickBySkin(var3, var4, var5);
                        }
                     }
                  }

                  if (this.uuidDenick.isToggled()) {
                     this.resolveUuidDenicks(var1);
                  }
               }
            }
         }
      }
   }

   @Subscribe
   public void onPacketSend(PacketSendEvent var1) {
      if (this.bedWarsCosmeticDenick.isToggled() && ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 2) {
         if (var1.ys98() instanceof C01PacketChatMessage) {
            String var2 = ((C01PacketChatMessage)var1.ys98()).getMessage();
            String var3 = var2 == null ? "" : var2.toLowerCase(Locale.ROOT);
            String var4 = var3.startsWith("/block add ") ? "/block add " : (var3.startsWith("/block ") ? "/block " : "");
            if (!var4.isEmpty()) {
               String var5 = this.cleanNick(var2.substring(var4.length()));
               if (this.isValidPlayerName(var5)) {
                  String var6 = var5.toLowerCase(Locale.ROOT);
                  this.AZd8.add(var6);
                  if (this.XJOwdq7(var5, false)) {
                     this.forgetNick(var6);
                     return;
                  }

                  this.lkv.add(var6);
                  this.killMessagesByNick.remove(var6);
                  this.statCountersByNick.remove(var6);
                  this.promptedNicks.remove(var6);
                  this.notifiedReadyNicks.remove(var6);
                  this.confirmedNicks.remove(var6);
                  this.refreshSavedDenick(var5);
               }
            }
         }
      }
   }

   @Subscribe
   public void onChatReceived(ChatReceivedEvent var1) {
      if (var1 != null && var1.iChatComponent != null) {
         String var2 = ClientUtils.zaUnpz(var1.iChatComponent.getUnformattedText());
         String var3 = var1.iChatComponent.getFormattedText();
         if (var2 != null && !var2.isEmpty()) {
            if (!this.handleUnblockedMessage(var2)) {
               if (this.bedWarsCosmeticDenick.isToggled()) {
                  if (this.handleBlockListLine(var2)) {
                     var1.setCanceled(true);
                  } else if (this.EFmaPeu(var2)) {
                     var1.setCanceled(true);
                  } else if (ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 2) {
                     for (Denick$18 var5 : this.rbPko) {
                        Matcher var6 = Denick$18.getPattern(var5).matcher(var2);
                        if (var6.matches() && !this.MDGRc(var5, var2, var3) && !this.isUnexpectedVoidMessage(var5, var2, var3)) {
                           String var7 = this.cleanNick(this.getMatcherGroup(var6, "killer"));
                           if (this.isValidPlayerName(var7)) {
                              String var8 = var7.toLowerCase(Locale.ROOT);
                              this.MsEx(var8, Denick$18.ZeKzvUe(var5));
                              String var9 = this.killMessagesByNick.get(var8);
                              if (var9 != null || this.lkv.contains(var8)) {
                                 String var10 = this.iykH(var8, var9, Denick$18.ZeKzvUe(var5));
                                 this.killMessagesByNick.put(var8, var10);
                                 Denick$13 var11 = this.parseCounterFilter(var5, var6);
                                 if (var11 != null) {
                                    Denick$13 var12 = this.statCountersByNick.get(var8);
                                    if (var12 == null) {
                                       this.statCountersByNick.put(var8, var11);
                                    } else {
                                       Denick$13.copyStats(var12, var11);
                                       var11 = var12;
                                    }
                                 }

                                 this.lkv.remove(var8);
                                 if (this.XJOwdq7(var7, false)) {
                                    this.forgetNick(var8);
                                    return;
                                 } else {
                                    this.saveDenickRecord(var7, var10, var11);
                                    if (this.notifiedReadyNicks.add(var8)) {
                                       String var13 = var11 == null ? "" : " &8(" + Denick$13.getStatsLabelFor(var11) + ")";
                                       ClientUtils.sendJadeMessage("Jade", "&7ready to denick &f" + var7 + var13 + "&7. Use &b.denick " + var7 + " &7from a BedWars lobby.");
                                    }

                                    return;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (this.bedWarsCosmeticDenick.isToggled() && var1 != null && var1.ys98() != null) {
         if (var1.ys98() instanceof S02PacketChat) {
            S02PacketChat var7 = (S02PacketChat)var1.ys98();
            IChatComponent var8 = var7.getChatComponent();
            String var4 = var8 == null ? "" : ClientUtils.zaUnpz(var8.getUnformattedText());
            if (this.handleBlockListLine(var4)) {
               var1.setCanceled(true);
            } else {
               if (this.EFmaPeu(var4)) {
                  var1.setCanceled(true);
               }
            }
         } else if (this.Ob0 == Denick$17.SEARCHING || this.Ob0 == Denick$17.PAUSED) {
            if (var1.ys98() instanceof S2DPacketOpenWindow) {
               S2DPacketOpenWindow var6 = (S2DPacketOpenWindow)var1.ys98();
               String var3 = var6.getWindowTitle() == null ? "" : ClientUtils.zaUnpz(var6.getWindowTitle().getUnformattedText());
               if (this.isDenickWindowTitle(var3)) {
                  this.trackedWindowId = var6.getWindowId();
                  this.trackedWindowTitle = var3;
                  var1.setCanceled(true);
                  this.closeContainerWindow(this.trackedWindowId);
               } else {
                  this.trackedWindowId = -1;
                  this.trackedWindowTitle = "";
               }
            } else if (var1.ys98() instanceof S30PacketWindowItems) {
               S30PacketWindowItems var5 = (S30PacketWindowItems)var1.ys98();
               if (this.trackedWindowId >= 0 && ((IAccessorS30PacketWindowItems)var5).getWindowId() == this.trackedWindowId) {
                  var1.setCanceled(true);
                  this.scanWindowItems(var5.getItemStacks());
               }
            } else {
               if (var1.ys98() instanceof S2FPacketSetSlot) {
                  S2FPacketSetSlot var2 = (S2FPacketSetSlot)var1.ys98();
                  if (this.trackedWindowId >= 0 && var2.func_149175_c() == this.trackedWindowId) {
                     var1.setCanceled(true);
                     this.scanSetSlotItem(var2.func_149174_e());
                  }
               }
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onGuiOpen(GuiOpenEvent var1) {
      if ((this.Ob0 == Denick$17.SEARCHING || this.Ob0 == Denick$17.PAUSED) && var1.guiScreen instanceof GuiChest) {
         GuiChest var2 = (GuiChest)var1.guiScreen;
         if (var2.inventorySlots instanceof ContainerChest) {
            ContainerChest var3 = (ContainerChest)var2.inventorySlots;
            if (this.isDenickContainer(var3)) {
               boolean var4 = this.hasUnblockPlayerItem(var3);
               String var5 = this.findNickInContainer(var3);
               var1.setCanceled(true);
               if (var4) {
                  if (this.isValidPlayerName(var5)) {
                     this.closeContainerWindow(var3.windowId);
                     if (this.verifyingBlockedNick) {
                        this.handleUnblockResult(var5, true);
                     } else {
                        this.startVerification(var5);
                     }
                  } else {
                     this.closeContainerWindow(var3.windowId);
                  }
               } else if (this.AFPWmj(var5)) {
                  this.closeContainerWindow(var3.windowId);
                  if (this.verifyingBlockedNick) {
                     this.handleUnblockResult(var5, false);
                     return;
                  }

                  this.lastCheckedNick = this.pendingNick;
                  this.pendingNick = "";
                  this.actionDelayTicks = 13;
               } else {
                  this.closeContainerWindow(var3.windowId);
                  if (this.verifyingBlockedNick) {
                     this.handleUnblockResult(var5, false);
                  }
               }
            }
         }
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END && this.Ob0 != Denick$17.IDLE && !mc.gameSettings.showDebugInfo) {
         this.renderStatusOverlay();
      }
   }

   public void KFSYR(String var1) {
      this.JiXv02(var1, Collections.emptyList(), false);
   }

   public void NLTSjw(String var1, List<String> var2) {
      this.JiXv02(var1, var2, false);
   }

   public void JiXv02(String var1, List<String> var2, final boolean var3) {
      if (!this.bedWarsCosmeticDenick.isToggled()) {
         ClientUtils.sendJadeMessage("Jade", "&7enable &fBedWars cosmetic denick&7 first.");
      } else {
         final String var4 = this.cleanNick(var1);
         String var5 = this.getConfirmedNick(var4);
         if (var5 != null) {
            this.aoen(var4, var5);
         } else {
            String var6 = this.killMessagesByNick.get(var4.toLowerCase(Locale.ROOT));
            final Denick$13 var7 = this.statCountersByNick.get(var4.toLowerCase(Locale.ROOT));
            Denick$16 var8 = this.parseDenickArguments(var4, var2, var6);
            if (var8 != null) {
               if (this.isValidPlayerName(var4) && Denick$16.getKillMessage(var8) != null && !Denick$16.getKillMessage(var8).isEmpty()) {
                  final String var9 = Denick$16.getKillMessage(var8);
                  final List var10 = Denick$16.getCosmeticFilters(var8);
                  synchronized (this.nkCi) {
                     if (this.Ob0 == Denick$17.FETCHING || this.Ob0 == Denick$17.SEARCHING || this.Ob0 == Denick$17.PAUSED) {
                        ClientUtils.sendJadeMessage("Jade", "&7already denicking &f" + this.cAp + "&7.");
                        return;
                     }

                     this.Ob0 = Denick$17.FETCHING;
                     this.cAp = var4;
                     this.qPi5 = var9;
                     this.statFilters = var7;
                     this.cosmeticFilters = new ArrayList<>(var10);
                     this.bruteMode = var3;
                     this.OIBq = "Denicking (loading)";
                     this.candidateNicks = Collections.emptyList();
                     this.candidateIndex = -1;
                     this.Vte = 0;
                     this.actionDelayTicks = 0;
                  }

                  Thread var14 = new Thread(new Runnable() {
                     @Override
                     public void run() {
                        Denick.startSearch(Denick.this, var4, var9, var7, var10, var3);
                     }
                  }, "Denick-Cosmetic-" + var4);
                  var14.setPriority(1);
                  var14.start();
               } else {
                  ClientUtils.sendJadeMessage("Jade", "&7not enough denick info saved for &f" + var4 + "&7 yet.");
               }
            }
         }
      }
   }

   public List<String> RjNrev() {
      ArrayList var1 = new ArrayList();
      synchronized (this.YEspO) {
         for (Entry var4 : this.killMessagesByNick.entrySet()) {
            String var5 = (String)var4.getKey();
            String var6 = (String)var4.getValue();
            if (var6 != null && !var6.isEmpty() && !this.isNickResolved(var5) && this.isWorthDenicking(var5)) {
               var1.add(var5);
            }
         }

         return var1;
      }
   }

   public String getTabMarker(NetworkPlayerInfo var1) {
      if (!this.isEnabled() || !this.showInTab.isToggled()) {
         return "";
      } else if (var1 != null && var1.getGameProfile() != null) {
         String var2 = this.cleanNick(var1.getGameProfile().getName());
         if (!this.isValidPlayerName(var2)) {
            return "";
         } else if (!this.shouldShowInTab(var1)) {
            return "";
         } else {
            String var3 = var2.toLowerCase(Locale.ROOT);
            synchronized (this.YEspO) {
               boolean var5 = this.killMessagesByNick.containsKey(var3);
               Denick$15 var6 = this.savedDenicks.get(var3);
               if (!var5 && var6 != null && Denick$15.getKillMessage(var6) != null && !Denick$15.getKillMessage(var6).isEmpty()) {
                  var5 = true;
               }

               boolean var7 = this.statCountersByNick.containsKey(var3);
               if (!var7 && var6 != null) {
                  var7 = Denick$15.getFinals(var6) >= 0 || Denick$15.getBeds(var6) >= 0;
               }

               if (var5 && var7) {
                  return " §a*§r";
               } else if (var5) {
                  return " §e*§r";
               } else {
                  return !this.vUpo70(var1) && !this.savedDenicks.containsKey(var3) && !this.lkv.contains(var3) ? "" : " §5*§r";
               }
            }
         }
      } else {
         return "";
      }
   }

   public GameProfile rczQ(NetworkPlayerInfo var1) {
      return this.isEnabled() && var1 != null && var1.getGameProfile() != null ? this.tZpt.get(var1.getGameProfile().getId()) : null;
   }

   public boolean shouldShowInTab(NetworkPlayerInfo var1) {
      if (this.isEnabled() && var1 != null && var1.getGameProfile() != null) {
         String var2 = this.cleanNick(var1.getGameProfile().getName());
         if (!this.isValidPlayerName(var2)) {
            return false;
         } else {
            String var3 = var2.toLowerCase(Locale.ROOT);
            if (!this.isWorthDenicking(var3)) {
               return false;
            } else {
               synchronized (this.YEspO) {
                  return this.vUpo70(var1)
                     || this.savedDenicks.containsKey(var3)
                     || this.lkv.contains(var3)
                     || this.killMessagesByNick.containsKey(var3)
                     || this.statCountersByNick.containsKey(var3);
               }
            }
         }
      } else {
         return false;
      }
   }

   public List<String> getCosmeticKeywords() {
      ArrayList var1 = new ArrayList();
      var1.add("kill_message");
      Collections.addAll(var1, COSMETIC_KEYWORDS);
      return var1;
   }

   public List<String> getCosmeticCompletions(String var1) {
      String var2 = this.normalizeToken(var1);
      if (var2.isEmpty()) {
         return this.getCosmeticKeywords();
      } else if (this.isKillMessageKeyword(var2)) {
         return this.getRandomCosmeticCompletions();
      } else {
         Denick$11 var3 = this.findCosmeticCategory(var2);
         if (var3 != null && Denick$11.DVb815(var3).equals(var2)) {
            ArrayList var4 = new ArrayList();

            for (String var8 : COSMETIC_KEYWORDS) {
               if (var8.startsWith(Denick$11.getValuePrefix(var3))) {
                  var4.add(var8.substring(Denick$11.getValuePrefix(var3).length()));
               }
            }

            if ("victorydance_".equals(Denick$11.getValuePrefix(var3))
               || "killeffect_".equals(Denick$11.getValuePrefix(var3))
               || "beddestroy_".equals(Denick$11.getValuePrefix(var3))) {
               var4.add("random_cosmetic");
               var4.add("random_favorite_cosmetic");
            }

            return var4;
         } else {
            return this.getCosmeticKeywords();
         }
      }
   }

   private List<String> getRandomCosmeticCompletions() {
      ArrayList var1 = new ArrayList();
      var1.add("random_cosmetic");
      var1.add("random_favorite_cosmetic");

      for (String var3 : KILL_MESSAGE_SAMPLES.keySet()) {
         var1.add(var3.startsWith("killmessages_") ? var3.substring("killmessages_".length()) : var3);
      }

      Collections.sort(var1, String.CASE_INSENSITIVE_ORDER);
      return var1;
   }

   public void togglePause() {
      synchronized (this.nkCi) {
         if (this.Ob0 == Denick$17.SEARCHING) {
            this.Ob0 = Denick$17.PAUSED;
            this.OIBq = "Denicking (paused)";
            this.pendingNick = "";
            this.actionDelayTicks = 60;
            ClientUtils.sendJadeMessage("Jade", "&7denick paused. use &b.denick pause &7to resume.");
            return;
         }

         if (this.Ob0 == Denick$17.PAUSED) {
            this.Ob0 = Denick$17.SEARCHING;
            this.updateStatusText();
            this.pendingNick = "";
            this.actionDelayTicks = 13;
            ClientUtils.sendJadeMessage("Jade", "&7denick resumed.");
            return;
         }
      }

      ClientUtils.sendJadeMessage("Jade", "&7there is no active denick to pause.");
   }

   public void stopDenick() {
      synchronized (this.nkCi) {
         if (this.Ob0 == Denick$17.IDLE) {
            ClientUtils.sendJadeMessage("Jade", "&7there is no active denick to stop.");
            return;
         }
      }

      this.resetDenickState();
      ClientUtils.sendJadeMessage("Jade", "&7denick stopped.");
   }

   private void tickDenick() {
      this.tickBlockedListRefresh();
      if (this.bedWarsCosmeticDenick.isToggled() && ClientUtils.getBedWarsBoardType() == 0) {
         for (Entry var2 : this.killMessagesByNick.entrySet()) {
            if (!this.isNickResolved((String)var2.getKey()) && this.isWorthDenicking((String)var2.getKey()) && this.promptedNicks.add((String)var2.getKey())) {
               ClientUtils.sendJadeMessage("Jade", "&7type &b.denick " + (String)var2.getKey() + " &7from a BedWars lobby.");
            }
         }
      }

      if (this.Ob0 == Denick$17.PAUSED) {
         this.handleChestScreen();
      } else if (this.Ob0 == Denick$17.SEARCHING) {
         this.Vte++;
         if (this.awaitingUnblockResult) {
            this.phaseWaitTicks++;
            if (this.phaseWaitTicks >= 20) {
               this.restartVerification();
            }
         } else if (this.Mji) {
            this.phaseWaitTicks++;
            if (this.phaseWaitTicks >= 20) {
               this.startDatabaseVerification();
            }
         } else if (!ClientUtils.isInWorld() || ClientUtils.getBedWarsBoardType() != 0) {
            this.QGPECu();
         } else if (!this.handleChestScreen()) {
            if (!this.pendingNick.isEmpty() && this.Vte > 60) {
               if (this.verifyingBlockedNick) {
                  this.uKzz(this.pendingNick.isEmpty() ? this.gKc : this.pendingNick, "verification timed out");
                  return;
               }

               this.lastCheckedNick = this.pendingNick;
               this.pendingNick = "";
               this.actionDelayTicks = 13;
            }

            if (mc.currentScreen == null) {
               if (this.actionDelayTicks > 0) {
                  this.actionDelayTicks--;
               } else if (this.pendingNick.isEmpty()) {
                  if (this.verifyingBlockedNick) {
                     this.pendingNick = this.gKc;
                     this.lastCheckedNick = this.gKc;
                     this.trackedWindowId = -1;
                     this.trackedWindowTitle = "";
                     this.Vte = 0;
                     this.updateStatusText();
                     mc.thePlayer.sendChatMessage("/profile " + this.gKc);
                     this.actionDelayTicks = 13;
                  } else {
                     do {
                        this.candidateIndex++;
                     } while (this.candidateIndex < this.candidateNicks.size() && this.isFailedNick(Denick$19.getName(this.candidateNicks.get(this.candidateIndex))));

                     if (this.candidateIndex >= this.candidateNicks.size()) {
                        this.failDenick("no blocked profile matched " + this.cAp + ".");
                     } else {
                        Denick$19 var3 = this.candidateNicks.get(this.candidateIndex);
                        this.pendingNick = Denick$19.getName(var3);
                        this.lastCheckedNick = Denick$19.getName(var3);
                        this.trackedWindowId = -1;
                        this.trackedWindowTitle = "";
                        this.Vte = 0;
                        this.updateStatusText();
                        mc.thePlayer.sendChatMessage("/profile " + Denick$19.getName(var3));
                        this.actionDelayTicks = 13;
                     }
                  }
               }
            }
         }
      }
   }

   private boolean handleChestScreen() {
      if (!(mc.currentScreen instanceof GuiChest)) {
         return false;
      } else {
         GuiChest var1 = (GuiChest)mc.currentScreen;
         if (!(var1.inventorySlots instanceof ContainerChest)) {
            mc.displayGuiScreen(null);
            return true;
         } else {
            ContainerChest var2 = (ContainerChest)var1.inventorySlots;
            if (!this.isDenickContainer(var2)) {
               return false;
            } else {
               boolean var3 = this.hasUnblockPlayerItem(var2);
               String var4 = this.findNickInContainer(var2);
               if (var3) {
                  if (this.isValidPlayerName(var4)) {
                     this.closeContainerWindow(var2.windowId);
                     mc.displayGuiScreen(null);
                     if (this.verifyingBlockedNick) {
                        this.handleUnblockResult(var4, true);
                     } else {
                        this.startVerification(var4);
                     }
                  } else {
                     this.closeContainerWindow(var2.windowId);
                     mc.displayGuiScreen(null);
                  }
               } else if (this.AFPWmj(var4)) {
                  this.closeContainerWindow(var2.windowId);
                  mc.displayGuiScreen(null);
                  if (this.verifyingBlockedNick) {
                     this.handleUnblockResult(var4, false);
                     return true;
                  }

                  this.lastCheckedNick = this.pendingNick;
                  this.pendingNick = "";
                  this.actionDelayTicks = 13;
               } else {
                  this.closeContainerWindow(var2.windowId);
                  mc.displayGuiScreen(null);
                  if (this.verifyingBlockedNick) {
                     this.handleUnblockResult(var4, false);
                  }
               }

               return true;
            }
         }
      }
   }

   private boolean EFmaPeu(String var1) {
      if (this.Ob0 != Denick$17.SEARCHING && this.Ob0 != Denick$17.PAUSED) {
         return false;
      } else if (this.handleVerifyResponse(var1)) {
         return true;
      } else {
         String var2 = var1.toLowerCase(Locale.ROOT);
         if (!var2.contains("command failed: this command is on cooldown") && !var2.contains("this command is on cooldown")) {
            if (var2.contains("this player's profile is unavailable")) {
               this.lastCheckedNick = this.pendingNick.isEmpty() ? this.lastCheckedNick : this.pendingNick;
               this.pendingNick = "";
               this.trackedWindowId = -1;
               this.trackedWindowTitle = "";
               this.Vte = 0;
               this.actionDelayTicks = 13;
               this.updateStatusText();
               return true;
            } else {
               return false;
            }
         } else {
            String var3 = this.pendingNick.isEmpty() ? this.lastCheckedNick : this.pendingNick;
            this.lastCheckedNick = var3;
            this.pendingNick = "";
            this.trackedWindowId = -1;
            this.trackedWindowTitle = "";
            this.Vte = 0;
            this.actionDelayTicks = 60;
            this.updateStatusText();
            return true;
         }
      }
   }

   private boolean handleVerifyResponse(String var1) {
      if (this.verifyingBlockedNick && var1 != null) {
         String var2 = var1.trim();
         if (this.awaitingUnblockResult) {
            Matcher var5 = UNBLOCKED_MESSAGE_PATTERN.matcher(var2);
            if (var5.matches()) {
               String var6 = this.cleanNick(var5.group(1));
               if (this.isValidPlayerName(var6) && this.cAp != null && this.cAp.equalsIgnoreCase(var6)) {
                  this.restartVerification();
                  return true;
               }
            }

            String var7 = var2.toLowerCase(Locale.ROOT);
            if (!var7.contains("not blocked") && !var7.contains("isn't blocked") && !var7.contains("is not blocked")) {
               return false;
            } else {
               this.restartVerification();
               return true;
            }
         } else if (!this.Mji) {
            return false;
         } else if (BLOCKED_LIST_HEADER_PATTERN.matcher(var2).matches()) {
            this.verifyHeaderSeen = true;
            this.phaseWaitTicks = 0;
            return true;
         } else if (!this.verifyHeaderSeen) {
            return false;
         } else {
            Matcher var3 = BLOCKED_LIST_ENTRY_PATTERN.matcher(var2);
            if (var3.matches()) {
               String var4 = this.cleanNick(var3.group(1));
               if (this.isValidPlayerName(var4)) {
                  this.collectedBlockedNicks.add(var4);
               }

               this.phaseWaitTicks = 0;
               return true;
            } else if (var2.startsWith("------")) {
               this.phaseWaitTicks = 20;
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private boolean handleBlockListLine(String var1) {
      if (this.awaitingBlockList && !this.verifyingBlockedNick && var1 != null) {
         String var2 = var1.trim();
         String var3 = var2.toLowerCase(Locale.ROOT);
         if (var3.contains("no blocked players") || var3.contains("not blocked any players") || var3.contains("you have not blocked anyone")) {
            this.AZd8.clear();
            this.finishBlockListRequest(true);
            return true;
         } else if (BLOCKED_LIST_HEADER_PATTERN.matcher(var2).matches()) {
            this.listHeaderSeen = true;
            this.YgCuk = 0;
            this.AZd8.clear();
            return true;
         } else if (!this.listHeaderSeen) {
            return false;
         } else {
            Matcher var4 = BLOCKED_LIST_ENTRY_PATTERN.matcher(var2);
            if (var4.matches()) {
               String var5 = this.cleanNick(var4.group(1));
               if (this.isValidPlayerName(var5)) {
                  this.AZd8.add(var5.toLowerCase(Locale.ROOT));
               }

               this.YgCuk = 0;
               return true;
            } else if (var2.startsWith("------")) {
               this.finishBlockListRequest(true);
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private void tickBlockedListRefresh() {
      if (this.bedWarsCosmeticDenick.isToggled() && ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 0) {
         if (this.Xw3 > 0) {
            this.Xw3--;
         }

         if (this.awaitingBlockList) {
            this.YgCuk++;
            if (this.YgCuk >= 20) {
               this.finishBlockListRequest(this.listHeaderSeen);
            }
         } else if (!this.Tcxha && this.Xw3 <= 0 && this.hasUnresolvedDenicks()) {
            this.awaitingBlockList = true;
            this.listHeaderSeen = false;
            this.YgCuk = 0;
            this.AZd8.clear();
            this.Xw3 = 100;
            mc.thePlayer.sendChatMessage("/block list");
         }
      }
   }

   private void finishBlockListRequest(boolean var1) {
      this.awaitingBlockList = false;
      this.listHeaderSeen = false;
      this.YgCuk = 0;
      this.Tcxha = var1;
      this.Xw3 = var1 ? 100 : 1200;
   }

   private void resetBlockListState() {
      this.Tcxha = false;
      this.awaitingBlockList = false;
      this.listHeaderSeen = false;
      this.YgCuk = 0;
      this.Xw3 = 0;
      this.AZd8.clear();
   }

   private boolean hasUnresolvedDenicks() {
      synchronized (this.YEspO) {
         for (Denick$15 var3 : this.savedDenicks.values()) {
            if (var3 != null && !this.isValidPlayerName(Denick$15.SlV3(var3))) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isWorthDenicking(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         synchronized (this.YEspO) {
            Denick$15 var3 = this.savedDenicks.get(var1.toLowerCase(Locale.ROOT));
            return var3 != null && !this.isValidPlayerName(Denick$15.SlV3(var3)) ? this.Tcxha && this.AZd8.contains(Denick$15.getNickKey(var3)) : true;
         }
      } else {
         return false;
      }
   }

   private boolean handleUnblockedMessage(String var1) {
      Matcher var2 = UNBLOCKED_MESSAGE_PATTERN.matcher(var1);
      if (!var2.matches()) {
         return false;
      } else {
         String var3 = this.cleanNick(var2.group(1));
         if (this.isValidPlayerName(var3) && !this.cosiI(var3)) {
            this.LFaJc9(var3);
            return true;
         } else {
            return false;
         }
      }
   }

   private void LFaJc9(String var1) {
      String var2 = this.cleanNick(var1);
      if (this.isValidPlayerName(var2)) {
         String var3 = var2.toLowerCase(Locale.ROOT);
         this.AZd8.remove(var3);
         this.promptedNicks.remove(var3);
         this.notifiedReadyNicks.remove(var3);
         this.confirmedNicks.remove(var3);
      }
   }

   private boolean cosiI(String var1) {
      return this.Ob0 != Denick$17.SEARCHING && this.Ob0 != Denick$17.PAUSED ? false : this.verifyingBlockedNick && this.cAp != null && this.cAp.equalsIgnoreCase(var1);
   }

   private boolean isDenickWindowTitle(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         String var2 = this.extractProfileName(var1);
         return this.isValidPlayerName(var2) ? this.Yzz13(var2) : this.matchesPendingNick(var1);
      } else {
         return false;
      }
   }

   private boolean isDenickContainer(ContainerChest var1) {
      if (var1 == null) {
         return false;
      } else {
         String var2 = "";

         try {
            var2 = var1.getLowerChestInventory().getDisplayName().getUnformattedText();
         } catch (Exception var4) {
         }

         return this.isDenickWindowTitle(ClientUtils.zaUnpz(var2));
      }
   }

   private boolean Yzz13(String var1) {
      if (!this.isValidPlayerName(var1)) {
         return false;
      } else if (!this.pendingNick.isEmpty() && var1.equalsIgnoreCase(this.pendingNick)) {
         return true;
      } else if (!this.lastCheckedNick.isEmpty() && var1.equalsIgnoreCase(this.lastCheckedNick)) {
         return true;
      } else {
         for (Denick$19 var3 : this.candidateNicks) {
            if (var3 != null && Denick$19.getName(var3) != null && var1.equalsIgnoreCase(Denick$19.getName(var3))) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean matchesPendingNick(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         String var2 = var1.toLowerCase(Locale.ROOT);
         if (!this.pendingNick.isEmpty() && var2.contains(this.pendingNick.toLowerCase(Locale.ROOT))) {
            return true;
         } else if (!this.lastCheckedNick.isEmpty() && var2.contains(this.lastCheckedNick.toLowerCase(Locale.ROOT))) {
            return true;
         } else {
            for (Denick$19 var4 : this.candidateNicks) {
               if (var4 != null && Denick$19.getName(var4) != null && var2.contains(Denick$19.getName(var4).toLowerCase(Locale.ROOT))) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private void scanWindowItems(ItemStack[] var1) {
      if (var1 == null) {
         this.finishWindowScan(this.matchCandidateName(this.trackedWindowTitle), false);
      } else {
         boolean var2 = false;
         StringBuilder var3 = new StringBuilder();
         var3.append(' ').append(this.trackedWindowTitle);

         for (ItemStack var7 : var1) {
            if (var7 != null) {
               this.appendItemText(var3, var7);
               if (this.isUnblockPlayerItem(var7)) {
                  var2 = true;
               }
            }
         }

         this.finishWindowScan(this.matchCandidateName(var3.toString()), var2);
      }
   }

   private void scanSetSlotItem(ItemStack var1) {
      if (var1 != null && this.isUnblockPlayerItem(var1)) {
         StringBuilder var2 = new StringBuilder();
         var2.append(' ').append(this.trackedWindowTitle);
         this.appendItemText(var2, var1);
         this.finishWindowScan(this.matchCandidateName(var2.toString()), true);
      }
   }

   private void appendItemText(StringBuilder var1, ItemStack var2) {
      if (var1 != null && var2 != null) {
         if (var2.hasDisplayName()) {
            var1.append(' ').append(ClientUtils.zaUnpz(var2.getDisplayName()));
         }

         try {
            if (var2.hasTagCompound() && var2.getTagCompound().hasKey("display", 10)) {
               NBTTagCompound var3 = var2.getTagCompound().getCompoundTag("display");
               if (var3.hasKey("Lore", 9)) {
                  NBTTagList var4 = var3.getTagList("Lore", 8);

                  for (int var5 = 0; var5 < var4.tagCount(); var5++) {
                     var1.append(' ').append(ClientUtils.zaUnpz(var4.getStringTagAt(var5)));
                  }
               }
            }
         } catch (RuntimeException var6) {
         }
      }
   }

   private boolean isUnblockPlayerItem(ItemStack var1) {
      if (var1 != null && var1.getItem() == Items.paper && var1.hasDisplayName()) {
         String var2 = var1.getDisplayName();
         String var3 = ClientUtils.zaUnpz(var2);
         return "Unblock Player".equalsIgnoreCase(var3) || var2.contains("§cUnblock Player");
      } else {
         return false;
      }
   }

   private void finishWindowScan(String var1, boolean var2) {
      int var3 = this.trackedWindowId;
      this.trackedWindowId = -1;
      this.trackedWindowTitle = "";
      if (this.verifyingBlockedNick) {
         this.handleUnblockResult(var1, var2);
         if (var3 >= 0) {
            this.closeContainerWindow(var3);
         }
      } else if (var2) {
         if (this.isValidPlayerName(var1)) {
            this.startVerification(var1);
         }
      } else {
         if (this.AFPWmj(var1)) {
            this.lastCheckedNick = this.pendingNick;
            this.pendingNick = "";
            this.actionDelayTicks = 13;
            this.updateStatusText();
         }

         if (var3 >= 0) {
            this.closeContainerWindow(var3);
         }
      }
   }

   private String findNickInContainer(ContainerChest var1) {
      StringBuilder var2 = new StringBuilder();

      try {
         var2.append(' ').append(var1.getLowerChestInventory().getDisplayName().getUnformattedText());
      } catch (Exception var6) {
      }

      for (int var3 = 0; var3 < var1.inventorySlots.size(); var3++) {
         Slot var4 = (Slot)var1.inventorySlots.get(var3);
         ItemStack var5 = var4.getStack();
         if (var5 != null && var5.hasDisplayName()) {
            var2.append(' ').append(ClientUtils.zaUnpz(var5.getDisplayName()));
         }
      }

      String var7 = var2.toString().toLowerCase(Locale.ROOT);

      for (Denick$19 var10 : this.candidateNicks) {
         if (var10 != null && Denick$19.getName(var10) != null && var7.contains(Denick$19.getName(var10).toLowerCase(Locale.ROOT))) {
            return Denick$19.getName(var10);
         }
      }

      String var9 = this.extractProfileName(var2.toString());
      return this.isValidPlayerName(var9) ? var9 : this.pendingNick;
   }

   private String matchCandidateName(String var1) {
      if (var1 == null) {
         var1 = "";
      }

      for (Denick$19 var3 : this.candidateNicks) {
         if (var3 != null && Denick$19.getName(var3) != null && var1.toLowerCase(Locale.ROOT).contains(Denick$19.getName(var3).toLowerCase(Locale.ROOT))) {
            return Denick$19.getName(var3);
         }
      }

      String var4 = this.extractProfileName(var1);
      return this.isValidPlayerName(var4) ? var4 : this.pendingNick;
   }

   private String extractProfileName(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         Matcher var2 = Pattern.compile("([A-Za-z0-9_]{3,16})'s Profile", 2).matcher(var1);
         if (var2.find()) {
            return var2.group(1);
         } else {
            Matcher var3 = Pattern.compile("Profile\\s*[:\\-]\\s*([A-Za-z0-9_]{3,16})", 2).matcher(var1);
            return var3.find() ? var3.group(1) : "";
         }
      } else {
         return "";
      }
   }

   private boolean AFPWmj(String var1) {
      return var1 == null || var1.isEmpty() || this.pendingNick.isEmpty() || var1.equalsIgnoreCase(this.pendingNick);
   }

   private void QGPECu() {
      synchronized (this.nkCi) {
         if (this.Ob0 != Denick$17.SEARCHING) {
            return;
         }

         this.Ob0 = Denick$17.PAUSED;
         this.OIBq = "Denicking (paused - lobby)";
         this.pendingNick = "";
         this.actionDelayTicks = 13;
      }

      ClientUtils.sendJadeMessage("Jade", "&7denick paused. please denick from BedWars lobby. use &b.denick pause &7to resume.");
   }

   private void updateStatusText() {
      String var1 = this.pendingNick.isEmpty() ? this.lastCheckedNick : this.pendingNick;
      String var2 = Math.max(0, this.candidateIndex + 1) + "/" + this.candidateNicks.size();
      String var3 = this.bruteMode ? "Denicking brute" : "Denicking";
      if (var1 != null && !var1.isEmpty()) {
         this.OIBq = var3 + " (" + var2 + ") " + var1;
      } else {
         this.OIBq = var3 + " (" + var2 + ")";
      }
   }

   private void denickBySkin(NetworkPlayerInfo var1, String var2, String var3) {
      if (!this.skinCheckedUuids.contains(var2)) {
         if (this.vUpo70(var1)) {
            String var4 = this.getFormattedDisplayName(var1);
            boolean var5 = !var4.contains("§");
            if (var5) {
               int var6 = this.Do9.merge(var3, 1, Integer::sum);
               if (var6 < 10) {
                  return;
               }
            }

            this.skinCheckedUuids.add(var2);
            this.Do9.remove(var3);
            GameProfile var7 = this.vxa4(var1);
            if (var7 != null) {
               this.tZpt.put(var1.getGameProfile().getId(), var7);
            }

            if (!this.XJOwdq7(var3, true)) {
               this.checkSkinDenick(var1);
            }
         }
      }
   }

   private GameProfile vxa4(NetworkPlayerInfo var1) {
      JsonObject var2 = this.decodeSkinProfile(var1);
      if (var2 == null) {
         return null;
      } else {
         String var3 = this.bojzAa(this.getJsonString(this.getJsonObject(this.getJsonObject(var2, "textures"), "SKIN"), "url"));
         String var4 = this.getJsonString(var2, "profileName");
         String var5 = this.getJsonString(var2, "profileId");
         if (var3 != null && !sZ8.contains(var3) && this.isValidPlayerName(var4) && var5 != null) {
            String var6 = var5.replace("-", "");
            if (!var6.matches("[0-9a-fA-F]{32}")) {
               return null;
            } else {
               UUID var7 = UUID.fromString(
                  var6.substring(0, 8) + "-" + var6.substring(8, 12) + "-" + var6.substring(12, 16) + "-" + var6.substring(16, 20) + "-" + var6.substring(20)
               );
               return new GameProfile(var7, var4);
            }
         } else {
            return null;
         }
      }
   }

   private void checkSkinDenick(NetworkPlayerInfo var1) {
      JsonObject var2 = this.decodeSkinProfile(var1);
      if (var2 != null) {
         String var3 = this.getJsonString(this.getJsonObject(this.getJsonObject(var2, "textures"), "SKIN"), "url");
         String var4 = this.getJsonString(var2, "profileName");
         String var5 = this.bojzAa(var3);
         if (var5 != null && var4 != null && !var4.isEmpty() && !sZ8.contains(var5)) {
            this.YADSSbm(var1, var4, "Skin");
            return;
         }
      }

      if (this.showFailedDenicks.isToggled()) {
         this.reportFailedDenick(var1);
      }
   }

   private void resolveUuidDenicks(Collection<NetworkPlayerInfo> var1) {
      HashMap var2 = new HashMap();

      for (NetworkPlayerInfo var4 : var1) {
         if (var4.getGameProfile() != null && var4.getGameProfile().getId() != null) {
            String var5 = var4.getGameProfile().getName();
            if (var5 != null && !var5.isEmpty()) {
               String var6 = this.YiM0(var5).toLowerCase(Locale.ROOT);
               if (!var6.isEmpty()) {
                  List var7 = (List)var2.get(var6);
                  if (var7 == null) {
                     var7 = new ArrayList();
                     var2.put(var6, var7);
                  }

                  var7.add(var4);
               }
            }
         }
      }

      for (List var10 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (var2.values())) {
         if (var10.size() >= 2) {
            NetworkPlayerInfo var11 = null;
            ArrayList var12 = new ArrayList();

            for (NetworkPlayerInfo var8 : (java.lang.Iterable<NetworkPlayerInfo>) (java.lang.Iterable<?>) (var10)) {
               if (this.vUpo70(var8)) {
                  if (var11 == null) {
                     var11 = var8;
                  }
               } else {
                  var12.add(var8);
               }
            }

            if (var11 != null && !var12.isEmpty() && !this.XJOwdq7(var11.getGameProfile().getName(), true)) {
               for (NetworkPlayerInfo var15 : (java.lang.Iterable<NetworkPlayerInfo>) (java.lang.Iterable<?>) (var12)) {
                  this.resolvePlayerUuid(var15, var11);
               }
            }
         }
      }
   }

   private void resolvePlayerUuid(NetworkPlayerInfo var1, NetworkPlayerInfo var2) {
      String var3 = var1.getGameProfile().getId().toString();
      if (!this.Xsym5.contains(var3)) {
         if (!this.pendingUuidLookups.contains(var3)) {
            this.Xsym5.add(var3);
            this.pendingUuidLookups.add(var3);
            String var4 = var3.replace("-", "");
            String var5 = "https://sessionserver.mojang.com/session/minecraft/profile/" + var4;
            new Thread(() -> this.fetchProfileName(var5, var3, var2), "Denick-UUID-" + var4).start();
         }
      }
   }

   private String getFormattedDisplayName(NetworkPlayerInfo var1) {
      return var1.getGameProfile().equals(mc.thePlayer.getGameProfile())
         ? mc.thePlayer.getDisplayName().getFormattedText()
         : ScorePlayerTeam.formatPlayerName(var1.getPlayerTeam(), var1.getGameProfile().getName());
   }

   private JsonObject decodeSkinProfile(NetworkPlayerInfo var1) {
      Collection var2 = var1.getGameProfile().getProperties().get("textures");
      if (var2 != null && !var2.isEmpty()) {
         Property var3 = (Property)var2.iterator().next();
         if (var3 != null && var3.getValue() != null && !var3.getValue().isEmpty()) {
            try {
               byte[] var4 = Base64.getDecoder().decode(var3.getValue());
               JsonElement var5 = new JsonParser().parse(new String(var4, StandardCharsets.UTF_8));
               return var5.isJsonObject() ? var5.getAsJsonObject() : null;
            } catch (RuntimeException var6) {
               return null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private String extractNameFromJson(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         try {
            JsonElement var2 = new JsonParser().parse(var1);
            return var2.isJsonObject() ? this.getJsonString(var2.getAsJsonObject(), "name") : null;
         } catch (RuntimeException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   private JsonObject getJsonObject(JsonObject var1, String var2) {
      if (var1 == null) {
         return null;
      } else {
         JsonElement var3 = var1.get(var2);
         return var3 != null && var3.isJsonObject() ? var3.getAsJsonObject() : null;
      }
   }

   private String getJsonString(JsonObject var1, String var2) {
      if (var1 == null) {
         return null;
      } else {
         JsonElement var3 = var1.get(var2);
         return var3 != null && var3.isJsonPrimitive() ? var3.getAsString() : null;
      }
   }

   private long getJsonLong(JsonObject var1, String var2) {
      if (var1 == null) {
         return 0L;
      } else {
         JsonElement var3 = var1.get(var2);
         if (var3 != null && var3.isJsonPrimitive()) {
            try {
               return var3.getAsLong();
            } catch (RuntimeException var5) {
               return 0L;
            }
         } else {
            return 0L;
         }
      }
   }

   private int XfDc(JsonObject var1, String var2, int var3) {
      if (var1 == null) {
         return var3;
      } else {
         JsonElement var4 = var1.get(var2);
         if (var4 != null && var4.isJsonPrimitive()) {
            try {
               return var4.getAsInt();
            } catch (RuntimeException var6) {
               return var3;
            }
         } else {
            return var3;
         }
      }
   }

   private boolean getJsonBoolean(JsonObject var1, String var2) {
      if (var1 == null) {
         return false;
      } else {
         JsonElement var3 = var1.get(var2);
         if (var3 != null && var3.isJsonPrimitive()) {
            try {
               return var3.getAsBoolean();
            } catch (RuntimeException var5) {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   private double YOgE(JsonObject var1, String var2) {
      if (var1 == null) {
         return 0.0;
      } else {
         JsonElement var3 = var1.get(var2);
         if (var3 != null && var3.isJsonPrimitive()) {
            try {
               return var3.getAsDouble();
            } catch (RuntimeException var5) {
               return 0.0;
            }
         } else {
            return 0.0;
         }
      }
   }

   private Map<String, String> parseCosmetics(JsonObject var1) {
      HashMap var2 = new HashMap();
      JsonObject var3 = this.getJsonObject(var1, "cosmetics");

      for (Denick$11 var7 : COSMETIC_CATEGORIES) {
         String var8 = this.normalizeCosmeticValue(var7, this.getJsonString(var1, Denick$11.getStatsField(var7)));
         if (var8.isEmpty() && var3 != null) {
            var8 = this.normalizeCosmeticValue(var7, this.getJsonString(var3, Denick$11.getStatsField(var7)));
         }

         if (!var8.isEmpty()) {
            var2.put(Denick$11.getStatsField(var7), var8);
         }
      }

      return var2;
   }

   private Map<String, String> parseSavedCosmetics(JsonObject var1) {
      LinkedHashMap var2 = new LinkedHashMap();
      JsonObject var3 = this.getJsonObject(var1, "cosmetics");
      if (var3 != null) {
         for (Entry var5 : var3.entrySet()) {
            if (var5.getValue() != null && ((JsonElement)var5.getValue()).isJsonPrimitive()) {
               Denick$11 var6 = this.shiL((String)var5.getKey());
               String var7 = this.normalizeCosmeticValue(var6, ((JsonElement)var5.getValue()).getAsString());
               if (var6 != null && !var7.isEmpty()) {
                  var2.put(Denick$11.getStatsField(var6), var7);
               }
            }
         }
      }

      for (Denick$11 var12 : COSMETIC_CATEGORIES) {
         String var8 = this.normalizeCosmeticValue(var12, this.getJsonString(var1, Denick$11.getStatsField(var12)));
         if (!var8.isEmpty()) {
            var2.put(Denick$11.getStatsField(var12), var8);
         }
      }

      return var2;
   }

   private void loadSavedDenicks() {
      synchronized (this.YEspO) {
         this.savedDenicks.clear();
         File var2 = this.TQhq();
         if (var2.isFile()) {
            FileReader var3 = null;

            try {
               var3 = new FileReader(var2);
               JsonElement var4 = new JsonParser().parse(var3);
               JsonArray var5 = this.parseDenicksArray(var4);
               if (var5 != null) {
                  for (JsonElement var7 : var5) {
                     if (var7 != null && var7.isJsonObject()) {
                        Denick$15 var8 = this.DCEDR(var7.getAsJsonObject());
                        if (var8 != null) {
                           boolean var9 = this.isValidPlayerName(Denick$15.SlV3(var8));
                           this.savedDenicks.put(Denick$15.getNickKey(var8), var8);
                           if (!Denick$15.JMVOQSm(var8).isEmpty()) {
                              this.z42.put(Denick$15.getNickKey(var8), new LinkedHashSet<>(Denick$15.JMVOQSm(var8)));
                           }

                           if (!var9 && Denick$15.getKillMessage(var8) != null && !Denick$15.getKillMessage(var8).isEmpty()) {
                              this.killMessagesByNick.put(Denick$15.getNickKey(var8), Denick$15.getKillMessage(var8));
                           } else if (!var9) {
                              this.lkv.add(Denick$15.getNickKey(var8));
                           }

                           Denick$13 var10 = var9 ? null : this.createStatFilter(Denick$15.getFinals(var8), Denick$15.getBeds(var8));
                           if (var10 != null) {
                              this.statCountersByNick.put(Denick$15.getNickKey(var8), var10);
                           }
                        }
                     }
                  }

                  return;
               }
            } catch (Exception var22) {
               return;
            } finally {
               if (var3 != null) {
                  try {
                     var3.close();
                  } catch (Exception var21) {
                  }
               }
            }
         }
      }
   }

   private JsonArray parseDenicksArray(JsonElement var1) {
      if (var1 == null || var1.isJsonNull()) {
         return null;
      } else if (var1.isJsonArray()) {
         return var1.getAsJsonArray();
      } else if (!var1.isJsonObject()) {
         return null;
      } else {
         JsonElement var2 = var1.getAsJsonObject().get("denicks");
         return var2 != null && var2.isJsonArray() ? var2.getAsJsonArray() : null;
      }
   }

   private JsonArray Rjv9(JsonObject var1, String var2) {
      if (var1 == null) {
         return null;
      } else {
         JsonElement var3 = var1.get(var2);
         return var3 != null && var3.isJsonArray() ? var3.getAsJsonArray() : null;
      }
   }

   private Denick$15 DCEDR(JsonObject var1) {
      String var2 = this.cleanNick(this.getJsonString(var1, "nick"));
      if (!this.isValidPlayerName(var2)) {
         return null;
      } else {
         Denick$15 var3 = new Denick$15(var2);
         Denick$15.setKillMessage(var3, this.canonicalizeCosmeticKey(this.getJsonString(var1, "killMessage")));
         JsonArray var4 = this.Rjv9(var1, "killMessages");
         if (var4 != null) {
            for (JsonElement var6 : var4) {
               String var7 = this.canonicalizeCosmeticKey(var6 != null && var6.isJsonPrimitive() ? var6.getAsString() : "");
               if (this.vIc4(var7)) {
                  Denick$15.JMVOQSm(var3).add(var7);
               }
            }
         }

         if (this.vIc4(Denick$15.getKillMessage(var3))) {
            Denick$15.JMVOQSm(var3).add(Denick$15.getKillMessage(var3));
         }

         Denick$15.setFinals(var3, this.XfDc(var1, "finals", -1));
         Denick$15.setBeds(var3, this.XfDc(var1, "beds", -1));
         Denick$15.Zgkro(var3).putAll(this.parseSavedCosmetics(var1));
         Denick$15.setResolvedAs(var3, this.cleanNick(this.getJsonString(var1, "resolvedAs")));
         if (!this.isValidPlayerName(Denick$15.SlV3(var3))) {
            Denick$15.setResolvedAs(var3, "");
         }

         Denick$15.setSource(var3, this.getJsonString(var1, "source"));
         Denick$15.Xuug(var3, this.getJsonLong(var1, "firstSeen"));
         Denick$15.setUpdatedAt(var3, this.getJsonLong(var1, "updatedAt"));
         JsonObject var10 = this.getJsonObject(var1, "teamContext");
         if (var10 != null) {
            Denick$15.setObservedAt(var3, this.getJsonLong(var10, "observedAt"));
            JsonArray var11 = this.Rjv9(var10, "teammates");
            if (var11 != null) {
               for (JsonElement var8 : var11) {
                  if (var8 != null && var8.isJsonObject()) {
                     Denick$20 var9 = this.clt7(var8.getAsJsonObject());
                     if (var9 != null) {
                        Denick$15.getNameHistory(var3).add(var9);
                     }
                  }
               }
            }
         }

         return var3;
      }
   }

   private Denick$20 clt7(JsonObject var1) {
      String var2 = this.cleanNick(this.getJsonString(var1, "name"));
      if (!this.isValidPlayerName(var2)) {
         return null;
      } else {
         String var3 = this.cleanNick(this.getJsonString(var1, "resolvedName"));
         if (!this.isValidPlayerName(var3)) {
            var3 = "";
         }

         boolean var4 = this.getJsonBoolean(var1, "nicked");
         long var5 = this.getJsonLong(var1, "observedAt");
         return new Denick$20(var2, var3, var4, var5);
      }
   }

   private void refreshSavedDenick(String var1) {
      String var2 = this.cleanNick(var1);
      if (this.isValidPlayerName(var2)) {
         synchronized (this.YEspO) {
            Denick$15 var4 = this.xFup(var2);
            this.updateTeammateContext(var4);
            String var5 = Denick$15.getNickKey(var4);
            LinkedHashSet var6 = this.z42.get(var5);
            if (var6 != null && !var6.isEmpty()) {
               Denick$15.JMVOQSm(var4).clear();
               Denick$15.JMVOQSm(var4).addAll(var6);
               Denick$15.setKillMessage(var4, this.iykH(var5, Denick$15.getKillMessage(var4), this.getMostRecentMessage(var6)));
               this.killMessagesByNick.put(var5, Denick$15.getKillMessage(var4));
            } else {
               Denick$15.setKillMessage(var4, "");
            }

            Denick$15.setFinals(var4, -1);
            Denick$15.setBeds(var4, -1);
            Denick$15.setUpdatedAt(var4, System.currentTimeMillis());
            this.WAfi();
         }
      }
   }

   private void saveDenickRecord(String var1, String var2, Denick$13 var3) {
      String var4 = this.cleanNick(var1);
      if (this.isValidPlayerName(var4)) {
         var2 = this.canonicalizeCosmeticKey(var2);
         synchronized (this.YEspO) {
            Denick$15 var6 = this.xFup(var4);
            this.updateTeammateContext(var6);
            Denick$15.setKillMessage(var6, var2);
            if (this.vIc4(var2)) {
               Denick$15.JMVOQSm(var6).add(var2);
               LinkedHashSet var7 = this.z42.get(Denick$15.getNickKey(var6));
               if (var7 != null) {
                  Denick$15.JMVOQSm(var6).addAll(var7);
               }
            }

            if (var3 != null) {
               Denick$15.setFinals(var6, Denick$13.kBqhi(var3));
               Denick$15.setBeds(var6, Denick$13.Dceyqa(var3));
            }

            Denick$15.setUpdatedAt(var6, System.currentTimeMillis());
            this.WAfi();
         }
      }
   }

   private void MsEx(String var1, String var2) {
      var2 = this.canonicalizeCosmeticKey(var2);
      if (var1 != null && !var1.isEmpty() && this.vIc4(var2)) {
         LinkedHashSet var3 = this.z42.get(var1);
         if (var3 == null) {
            var3 = new LinkedHashSet();
            this.z42.put(var1, var3);
         }

         var3.add(var2);
      }
   }

   private String iykH(String var1, String var2, String var3) {
      var2 = this.canonicalizeCosmeticKey(var2);
      var3 = this.canonicalizeCosmeticKey(var3);
      if (this.Lejx(var2)) {
         return var2;
      } else {
         LinkedHashSet var4 = this.z42.get(var1);
         if (this.hasConflictingMessages(var4)) {
            return "random_kill_message";
         } else if (this.bothUseCounters(var2, new Denick$18(var3, Denick$14.NONE, null))) {
            return var2;
         } else {
            return this.vIc4(var3) ? var3 : var2;
         }
      }
   }

   private String getMostRecentMessage(LinkedHashSet<String> var1) {
      String var2 = "";
      if (var1 == null) {
         return var2;
      } else {
         for (String var4 : var1) {
            var2 = var4;
         }

         return var2;
      }
   }

   private boolean hasConflictingMessages(LinkedHashSet<String> var1) {
      if (var1 != null && !var1.isEmpty()) {
         String var2 = "";

         for (String var4 : var1) {
            if (this.vIc4(var4) && !this.forQ(var4)) {
               String var5 = this.canonicalizeMessageVariant(var4);
               if (var2.isEmpty()) {
                  var2 = var5;
               } else if (!var2.equals(var5)) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private String canonicalizeMessageVariant(String var1) {
      if (this.isCounterKillMessage(var1)) {
         return "numbered";
      } else {
         return var1 == null ? "" : var1;
      }
   }

   private void updateTeammateContext(Denick$15 var1) {
      if (var1 != null && ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 2 && mc.getNetHandler() != null) {
         Collection var2 = mc.getNetHandler().getPlayerInfoMap();
         if (var2 != null && !var2.isEmpty()) {
            NetworkPlayerInfo var3 = null;

            for (NetworkPlayerInfo var5 : (java.lang.Iterable<NetworkPlayerInfo>) (java.lang.Iterable<?>) (var2)) {
               if (var5 != null && var5.getGameProfile() != null) {
                  String var6 = this.cleanNick(var5.getGameProfile().getName());
                  if (Denick$15.getNick(var1).equalsIgnoreCase(var6)) {
                     var3 = var5;
                     break;
                  }
               }
            }

            EntityPlayer var17 = mc.theWorld == null ? null : mc.theWorld.getPlayerEntityByName(Denick$15.getNick(var1));
            String var18 = var3 == null ? "" : this.getTeamColorCode(var3);
            if (var18.isEmpty()) {
               var18 = this.lUje(var17);
            }

            if (!var18.isEmpty()) {
               long var19 = System.currentTimeMillis();
               LinkedHashMap var8 = new LinkedHashMap();

               for (NetworkPlayerInfo var10 : (java.lang.Iterable<NetworkPlayerInfo>) (java.lang.Iterable<?>) (var2)) {
                  if (var10 != null && var10.getGameProfile() != null) {
                     String var11 = this.cleanNick(var10.getGameProfile().getName());
                     if (this.isValidPlayerName(var11) && !Denick$15.getNick(var1).equalsIgnoreCase(var11) && var18.equals(this.getTeamColorCode(var10))) {
                        String var12 = this.getResolvedNick(var11);
                        boolean var13 = this.vUpo70(var10);
                        if (!var13 && !this.isValidPlayerName(var12)) {
                           var12 = var11;
                        }

                        var8.put(var11.toLowerCase(Locale.ROOT), new Denick$20(var11, var12 == null ? "" : var12, var13, var19));
                     }
                  }
               }

               if (mc.theWorld != null && mc.theWorld.playerEntities != null) {
                  for (Object var21 : mc.theWorld.playerEntities) {
                     if (var21 instanceof EntityPlayer) {
                        EntityPlayer var22 = (EntityPlayer)var21;
                        String var23 = this.cleanNick(var22.getName());
                        if (this.isValidPlayerName(var23) && !Denick$15.getNick(var1).equalsIgnoreCase(var23) && var18.equals(this.lUje(var22))) {
                           String var24 = var23.toLowerCase(Locale.ROOT);
                           if (!var8.containsKey(var24)) {
                              NetworkPlayerInfo var14 = this.findPlayerInfoByName(var23);
                              String var15 = this.getResolvedNick(var23);
                              boolean var16 = var14 != null && this.vUpo70(var14);
                              if (!var16 && !this.isValidPlayerName(var15)) {
                                 var15 = var23;
                              }

                              var8.put(var24, new Denick$20(var23, var15 == null ? "" : var15, var16, var19));
                           }
                        }
                     }
                  }
               }

               if (!var8.isEmpty()) {
                  Denick$15.setObservedAt(var1, var19);
                  Denick$15.getNameHistory(var1).clear();
                  Denick$15.getNameHistory(var1).addAll(var8.values());
               }
            }
         }
      }
   }

   private String getTeamColorCode(NetworkPlayerInfo var1) {
      if (var1 != null && var1.getGameProfile() != null) {
         if (var1.getPlayerTeam() != null) {
            String var2 = var1.getPlayerTeam().getColorPrefix();
            String var3 = this.extractTeamColorCode(var2);
            if (!var3.isEmpty()) {
               return var3;
            }
         }

         String var4 = ScorePlayerTeam.formatPlayerName(var1.getPlayerTeam(), var1.getGameProfile().getName());
         return this.extractTeamColorCode(var4);
      } else {
         return "";
      }
   }

   private String lUje(EntityPlayer var1) {
      if (var1 == null) {
         return "";
      } else {
         if (var1.getTeam() instanceof ScorePlayerTeam) {
            String var2 = this.extractTeamColorCode(((ScorePlayerTeam)var1.getTeam()).getColorPrefix());
            if (!var2.isEmpty()) {
               return var2;
            }
         }

         String var3 = var1.getDisplayName() == null ? "" : var1.getDisplayName().getFormattedText();
         return this.extractTeamColorCode(var3);
      }
   }

   private NetworkPlayerInfo findPlayerInfoByName(String var1) {
      if (this.isValidPlayerName(var1) && mc.getNetHandler() != null) {
         Collection var2 = mc.getNetHandler().getPlayerInfoMap();
         if (var2 == null) {
            return null;
         } else {
            for (NetworkPlayerInfo var4 : (java.lang.Iterable<NetworkPlayerInfo>) (java.lang.Iterable<?>) (var2)) {
               if (var4 != null && var4.getGameProfile() != null && var1.equalsIgnoreCase(var4.getGameProfile().getName())) {
                  return var4;
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   private String extractTeamColorCode(String var1) {
      if (var1 == null) {
         return "";
      } else {
         for (int var2 = 0; var2 + 1 < var1.length(); var2++) {
            if (var1.charAt(var2) == 167) {
               char var3 = Character.toLowerCase(var1.charAt(var2 + 1));
               if (this.isTeamColorCode(var3)) {
                  return String.valueOf(var3);
               }

               var2++;
            }
         }

         return "";
      }
   }

   private boolean isTeamColorCode(char var1) {
      return var1 == 'c' || var1 == '9' || var1 == 'a' || var1 == 'e' || var1 == 'b' || var1 == 'f' || var1 == 'd' || var1 == '8';
   }

   private void saveResolvedNick(String var1, String var2, String var3) {
      String var4 = this.cleanNick(var1);
      String var5 = this.cleanNick(var2);
      if (this.isValidPlayerName(var4) && this.isValidPlayerName(var5)) {
         synchronized (this.YEspO) {
            Denick$15 var7 = this.xFup(var4);
            Denick$15.setResolvedAs(var7, var5);
            Denick$15.setSource(var7, var3 == null ? "" : var3);
            Denick$15.setUpdatedAt(var7, System.currentTimeMillis());
            this.forgetNick(Denick$15.getNickKey(var7));
            this.WAfi();
         }
      }
   }

   private void forgetNick(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         this.lkv.remove(var1);
         this.killMessagesByNick.remove(var1);
         this.statCountersByNick.remove(var1);
         this.z42.remove(var1);
         this.promptedNicks.remove(var1);
         this.notifiedReadyNicks.remove(var1);
         this.confirmedNicks.remove(var1);
      }
   }

   private String getResolvedNick(String var1) {
      String var2 = this.cleanNick(var1);
      if (!this.isValidPlayerName(var2)) {
         return null;
      } else {
         synchronized (this.YEspO) {
            Denick$15 var4 = this.savedDenicks.get(var2.toLowerCase(Locale.ROOT));
            return var4 != null && this.isValidPlayerName(Denick$15.SlV3(var4)) ? Denick$15.SlV3(var4) : null;
         }
      }
   }

   private String getConfirmedNick(String var1) {
      String var2 = this.cleanNick(var1);
      if (!this.isValidPlayerName(var2)) {
         return null;
      } else {
         synchronized (this.YEspO) {
            Denick$15 var4 = this.savedDenicks.get(var2.toLowerCase(Locale.ROOT));
            return var4 != null && "Confirmed".equals(Denick$15.getSource(var4)) && this.isValidPlayerName(Denick$15.SlV3(var4)) ? Denick$15.SlV3(var4) : null;
         }
      }
   }

   private boolean XJOwdq7(String var1, boolean var2) {
      String var3 = this.cleanNick(var1);
      String var4 = this.getConfirmedNick(var3);
      if (var4 == null) {
         return false;
      } else if (var2 && !this.CkmGeu.add(var3.toLowerCase(Locale.ROOT))) {
         return true;
      } else {
         this.aoen(var3, var4);
         return true;
      }
   }

   private void aoen(String var1, String var2) {
      ClientUtils.sendJadeMessage("Jade", "&f" + var2 + " &7is likely nicked as &b" + this.cleanNick(var1) + "&7. &8(prev confirmed)");
   }

   private long getLastSeenTime(String var1) {
      String var2 = this.cleanNick(var1);
      if (!this.isValidPlayerName(var2)) {
         return 0L;
      } else {
         synchronized (this.YEspO) {
            Denick$15 var4 = this.savedDenicks.get(var2.toLowerCase(Locale.ROOT));
            if (var4 == null) {
               return 0L;
            } else {
               return Denick$15.getFirstSeen(var4) > 0L ? Denick$15.getFirstSeen(var4) : Denick$15.getUpdatedAt(var4);
            }
         }
      }
   }

   private boolean isNickResolved(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         synchronized (this.YEspO) {
            Denick$15 var3 = this.savedDenicks.get(var1.toLowerCase(Locale.ROOT));
            return var3 != null && this.isValidPlayerName(Denick$15.SlV3(var3));
         }
      } else {
         return false;
      }
   }

   private Denick$15 xFup(String var1) {
      String var2 = this.cleanNick(var1);
      String var3 = var2.toLowerCase(Locale.ROOT);
      Denick$15 var4 = this.savedDenicks.get(var3);
      if (var4 == null) {
         var4 = new Denick$15(var2);
         this.savedDenicks.put(var3, var4);
      }

      if (Denick$15.getFirstSeen(var4) <= 0L) {
         Denick$15.Xuug(var4, System.currentTimeMillis());
      }

      return var4;
   }

   private void WAfi() {
      File var1 = this.TQhq();
      File var2 = var1.getParentFile();
      if (var2 == null || var2.exists() || var2.mkdirs()) {
         JsonObject var3 = new JsonObject();
         JsonArray var4 = new JsonArray();

         for (Denick$15 var6 : this.savedDenicks.values()) {
            JsonObject var7 = new JsonObject();
            var7.addProperty("nick", Denick$15.getNick(var6));
            if (Denick$15.getKillMessage(var6) != null && !Denick$15.getKillMessage(var6).isEmpty()) {
               var7.addProperty("killMessage", Denick$15.getKillMessage(var6));
            }

            if (!Denick$15.JMVOQSm(var6).isEmpty()) {
               JsonArray var8 = new JsonArray();

               for (String var10 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (Denick$15.JMVOQSm(var6))) {
                  var8.add(var10);
               }

               var7.add("killMessages", var8);
            }

            if (Denick$15.getFinals(var6) >= 0) {
               var7.addProperty("finals", Denick$15.getFinals(var6));
            }

            if (Denick$15.getBeds(var6) >= 0) {
               var7.addProperty("beds", Denick$15.getBeds(var6));
            }

            if (Denick$15.SlV3(var6) != null && !Denick$15.SlV3(var6).isEmpty()) {
               var7.addProperty("resolvedAs", Denick$15.SlV3(var6));
            }

            if (Denick$15.getSource(var6) != null && !Denick$15.getSource(var6).isEmpty()) {
               var7.addProperty("source", Denick$15.getSource(var6));
            }

            if (!Denick$15.Zgkro(var6).isEmpty()) {
               JsonObject var25 = new JsonObject();

               for (Entry var29 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (Denick$15.Zgkro(var6).entrySet())) {
                  var25.addProperty((String)var29.getKey(), (String)var29.getValue());
               }

               var7.add("cosmetics", var25);
            }

            if (!Denick$15.getNameHistory(var6).isEmpty() && Denick$15.getObservedAt(var6) > 0L) {
               JsonObject var26 = new JsonObject();
               var26.addProperty("observedAt", Denick$15.getObservedAt(var6));
               JsonArray var28 = new JsonArray();

               for (Denick$20 var11 : (java.lang.Iterable<Denick$20>) (java.lang.Iterable<?>) (Denick$15.getNameHistory(var6))) {
                  JsonObject var12 = new JsonObject();
                  var12.addProperty("name", Denick$20.getName(var11));
                  String var13 = this.resolveTeammateNick(var11);
                  if (this.isValidPlayerName(var13)) {
                     var12.addProperty("resolvedName", var13);
                  }

                  var12.addProperty("nicked", Denick$20.isNicked(var11));
                  var12.addProperty("observedAt", Denick$20.getObservedAt(var11));
                  var28.add(var12);
               }

               var26.add("teammates", var28);
               var7.add("teamContext", var26);
            }

            var7.addProperty("firstSeen", Denick$15.getFirstSeen(var6));
            var7.addProperty("updatedAt", Denick$15.getUpdatedAt(var6));
            var4.add(var7);
         }

         var3.add("denicks", var4);
         FileWriter var24 = null;

         try {
            var24 = new FileWriter(var1);
            new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)var3, var24);
         } catch (Exception var22) {
         } finally {
            if (var24 != null) {
               try {
                  var24.close();
               } catch (Exception var21) {
               }
            }
         }
      }
   }

   private File TQhq() {
      return new File(InjectionPaths.dataDirectory(mc.mcDataDir), "denicks.json");
   }

   private Denick$13 createStatFilter(int var1, int var2) {
      if (var1 < 0 && var2 < 0) {
         return null;
      } else {
         Denick$13 var3 = new Denick$13(Denick$14.NONE, -1);
         Denick$13.setFinals(var3, var1);
         Denick$13.setBeds(var3, var2);
         return var3;
      }
   }

   private String bojzAa(String var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = var1.lastIndexOf(47);
         return var2 >= 0 && var2 + 1 < var1.length() ? var1.substring(var2 + 1) : null;
      }
   }

   private boolean vUpo70(NetworkPlayerInfo var1) {
      return var1.getGameProfile() != null && var1.getGameProfile().getId() != null && var1.getGameProfile().getId().version() == 1;
   }

   private void YADSSbm(NetworkPlayerInfo var1, String var2, String var3) {
      String var4 = var1 != null && var1.getGameProfile() != null ? var1.getGameProfile().getName() : "";
      if (var1 != null && var1.getGameProfile() != null) {
         this.saveResolvedNick(var4, var2, var3);
      }

      this.aTtnDg(var4, "&f" + this.cleanNick(var2) + " &7is nicked as &b" + this.cleanNick(var4) + "&7. &8(" + var3 + ")");
   }

   private void reportFailedDenick(NetworkPlayerInfo var1) {
      String var2 = var1 != null && var1.getGameProfile() != null ? var1.getGameProfile().getName() : "";
      this.aTtnDg(var2, this.getFormattedDisplayName(var1) + " &cis nicked&7.");
   }

   private void aTtnDg(String var1, String var2) {
      String var3 = this.cleanNick(var1);
      if (ClientUtils.isInWorld() && this.isValidPlayerName(var3)) {
         String var4 = ClientUtils.formatGradientPrefix("Jade") + ClientUtils.translateColorCodes(" " + var2);
         if (!ExternalChatOverlay.WBGA(var4)) {
            ChatUtils.resolveFlowText(var4);
            ChatComponentText var5 = new ChatComponentText(ChatUtils.kWr1(ClientUtils.formatGradientPrefix("Jade")) + ClientUtils.translateColorCodes(" "));
            ChatComponentText var6 = new ChatComponentText(ClientUtils.translateColorCodes(var2));
            ChatStyle var7 = new ChatStyle();
            var7.setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, "/block add " + var3));
            var7.setChatHoverEvent(
               new HoverEvent(net.minecraft.event.HoverEvent.Action.SHOW_TEXT, new ChatComponentText(ClientUtils.translateColorCodes("&7Click to run &b/block add " + var3)))
            );
            var6.setChatStyle(var7);
            var5.appendSibling(var6);
            mc.thePlayer.addChatMessage(var5);
         }
      } else {
         ClientUtils.sendJadeMessage("Jade", var2);
      }
   }

   private void searchCandidates(final String var1, String var2, Denick$13 var3, List<Denick$12> var4, boolean var5) {
      try {
         List var6 = this.fetchCandidatesWithContext(var1, var2, var3, var4, var5);
         final ArrayList var7 = new ArrayList();
         int var8 = 0;

         for (Denick$19 var10 : (java.lang.Iterable<Denick$19>) (java.lang.Iterable<?>) (var6)) {
            if (this.Bfez(var10, var2, var3, var4)) {
               var7.add(var10);
            }

            if ((++var8 & 511) == 0) {
               Thread.yield();
            }
         }

         if (var7.isEmpty()) {
            this.failDenick("no MVP++ players matched " + this.DXCzlA2(var2, var3, var4) + ".");
            return;
         }

         this.sortCandidates(var7, var3, this.getLastSeenTime(var1));
         synchronized (this.nkCi) {
            if (!var1.equalsIgnoreCase(this.cAp) || this.Ob0 != Denick$17.FETCHING) {
               return;
            }

            this.applySearchResults(var1, var2, var3, var4, var7, var5);
         }

         mc.addScheduledTask(new Runnable() {
            @Override
            public void run() {
               ClientUtils.sendJadeMessage("Jade", "&7denicking &f" + var1 + " &8(" + var7.size() + " possibilities)&7. " + Denick.formatEstimate(Denick.this, var7.size()));
            }
         });
      } catch (Exception var14) {
         this.failDenick("could not load Jade denick candidates.");
      }
   }

   private List<Denick$19> fetchCandidatesWithContext(String var1, String var2, Denick$13 var3, List<Denick$12> var4, boolean var5) throws Exception {
      return this.fetchCandidates(var1, var2, var3, var4, var5, true);
   }

   private List<Denick$19> fetchCandidates(String var1, String var2, Denick$13 var3, List<Denick$12> var4, boolean var5, boolean var6) throws Exception {
      var2 = this.canonicalizeCosmeticKey(var2);
      JsonObject var7 = var6 ? this.buildTeamContextJson(var1) : null;
      long var8 = var6 ? this.getLastSeenTime(var1) : 0L;
      if ("random_kill_message".equals(var2)) {
         ArrayList var10 = new ArrayList();
         Exception var11 = null;

         try {
            this.mergeCandidateLists(var10, this.Rywg(var1, "random_favorite_kill_message", var3, var4, var5, var6, var8, var7));
         } catch (Exception var13) {
            var11 = var13;
         }

         try {
            this.mergeCandidateLists(var10, this.Rywg(var1, "random_kill_message", var3, var4, var5, var6, var8, var7));
         } catch (Exception var14) {
            if (var11 != null || var10.isEmpty()) {
               throw var14;
            }
         }

         return var10;
      } else {
         return this.Rywg(var1, var2, var3, var4, var5, var6, var8, var7);
      }
   }

   private List<Denick$19> Rywg(String var1, String var2, Denick$13 var3, List<Denick$12> var4, boolean var5, boolean var6, long var7, JsonObject var9) throws Exception {
      HttpURLConnection var10 = null;

      List var17;
      try {
         var10 = (HttpURLConnection)new URL("https://redacted/api/denick").openConnection();
         var10.setRequestMethod("POST");
         var10.setDoOutput(true);
         var10.setConnectTimeout(7000);
         var10.setReadTimeout(10000);
         var10.setRequestProperty("Content-Type", "application/json; charset=utf-8");
         var10.setRequestProperty("Accept", "application/json");
         byte[] var11 = this.buildRequestBody(var1, var2, var3, var4, var5, var6, var7, var9).toString().getBytes(StandardCharsets.UTF_8);
         var10.setFixedLengthStreamingMode(var11.length);
         OutputStream var12 = var10.getOutputStream();
         var12.write(var11);
         var12.close();
         int var13 = var10.getResponseCode();
         if (var13 != 200) {
            throw new IllegalStateException("Jade denick API returned HTTP " + var13 + ".");
         }

         BufferedReader var14 = new BufferedReader(new InputStreamReader(var10.getInputStream(), StandardCharsets.UTF_8));
         StringBuilder var15 = new StringBuilder();

         String var16;
         while ((var16 = var14.readLine()) != null) {
            var15.append(var16);
         }

         var14.close();
         var17 = this.parseCandidates(var15.toString());
      } finally {
         if (var10 != null) {
            var10.disconnect();
         }
      }

      return var17;
   }

   private void mergeCandidateLists(List<Denick$19> var1, List<Denick$19> var2) {
      if (var1 != null && var2 != null && !var2.isEmpty()) {
         HashSet var3 = new HashSet();

         for (Denick$19 var5 : var1) {
            if (var5 != null && Denick$19.getName(var5) != null) {
               var3.add(Denick$19.getName(var5).toLowerCase(Locale.ROOT));
            }
         }

         for (Denick$19 var8 : var2) {
            if (var8 != null && Denick$19.getName(var8) != null) {
               String var6 = Denick$19.getName(var8).toLowerCase(Locale.ROOT);
               if (var3.add(var6)) {
                  var1.add(var8);
               }
            }
         }
      }
   }

   private JsonObject buildRequestWithContext(String var1, String var2, Denick$13 var3, List<Denick$12> var4, boolean var5) {
      return this.buildRequestBodyWithContext(var1, var2, var3, var4, var5, true);
   }

   private JsonObject buildRequestBodyWithContext(String var1, String var2, Denick$13 var3, List<Denick$12> var4, boolean var5, boolean var6) {
      JsonObject var7 = var6 ? this.buildTeamContextJson(var1) : null;
      long var8 = var6 ? this.getLastSeenTime(var1) : 0L;
      return this.buildRequestBody(var1, var2, var3, var4, var5, var6, var8, var7);
   }

   private JsonObject buildRequestBody(String var1, String var2, Denick$13 var3, List<Denick$12> var4, boolean var5, boolean var6, long var7, JsonObject var9) {
      JsonObject var10 = new JsonObject();
      var10.addProperty("nick", var1);
      var10.addProperty("killMessage", var2);
      var10.addProperty("brute", var5);
      if (var6 && var7 > 0L) {
         var10.addProperty("seenAt", var7);
      }

      if (var9 != null) {
         var10.add("teamContext", var9);
      }

      if (var3 != null) {
         if (Denick$13.kBqhi(var3) >= 0) {
            var10.addProperty("finals", Denick$13.kBqhi(var3));
         }

         if (Denick$13.Dceyqa(var3) >= 0) {
            var10.addProperty("beds", Denick$13.Dceyqa(var3));
         }
      }

      JsonObject var11 = new JsonObject();
      if (var4 != null) {
         for (Denick$12 var13 : var4) {
            var11.addProperty(Denick$12.getCosmeticId(var13), Denick$12.GJUvu(var13));
         }
      }

      var10.add("cosmetics", var11);
      return var10;
   }

   private JsonObject buildTeamContextJson(String var1) {
      String var2 = this.cleanNick(var1);
      if (!this.isValidPlayerName(var2)) {
         return null;
      } else {
         synchronized (this.YEspO) {
            Denick$15 var3 = this.savedDenicks.get(var2.toLowerCase(Locale.ROOT));
            if (var3 != null && !Denick$15.getNameHistory(var3).isEmpty() && Denick$15.getObservedAt(var3) > 0L) {
               if (System.currentTimeMillis() - Denick$15.getObservedAt(var3) > 7200000L) {
                  return null;
               } else {
                  JsonObject var5 = new JsonObject();
                  var5.addProperty("observedAt", Denick$15.getObservedAt(var3));
                  JsonArray var6 = new JsonArray();

                  for (Denick$20 var8 : (java.lang.Iterable<Denick$20>) (java.lang.Iterable<?>) (Denick$15.getNameHistory(var3))) {
                     if (var8 != null && this.isValidPlayerName(Denick$20.getName(var8))) {
                        JsonObject var9 = new JsonObject();
                        var9.addProperty("name", Denick$20.getName(var8));
                        String var10 = this.resolveTeammateNick(var8);
                        if (this.isValidPlayerName(var10)) {
                           var9.addProperty("resolvedName", var10);
                        }

                        var9.addProperty("nicked", Denick$20.isNicked(var8));
                        var9.addProperty("observedAt", Denick$20.getObservedAt(var8));
                        var6.add(var9);
                     }
                  }

                  if (var6.size() == 0) {
                     return null;
                  } else {
                     var5.add("teammates", var6);
                     return var5;
                  }
               }
            } else {
               return null;
            }
         }
      }
   }

   private String resolveTeammateNick(Denick$20 var1) {
      if (var1 == null) {
         return "";
      } else if (this.isValidPlayerName(Denick$20.getDenickedName(var1))) {
         return Denick$20.getDenickedName(var1);
      } else {
         String var2 = this.getResolvedNick(Denick$20.getName(var1));
         return this.isValidPlayerName(var2) ? var2 : "";
      }
   }

   private void applySearchResults(String var1, String var2, Denick$13 var3, List<Denick$12> var4, List<Denick$19> var5, boolean var6) {
      this.stateGeneration++;
      this.Ob0 = Denick$17.SEARCHING;
      this.cAp = var1;
      this.qPi5 = var2;
      this.statFilters = var3;
      this.cosmeticFilters = new ArrayList<>(var4);
      this.candidateNicks = new ArrayList<>(var5);
      this.bruteMode = var6;
      this.verifyingBlockedNick = false;
      this.gKc = "";
      this.awaitingUnblockResult = false;
      this.Mji = false;
      this.verifyHeaderSeen = false;
      this.verifyingDatabase = false;
      this.ambiguousNicks = Collections.emptyList();
      this.failedVerificationNicks.clear();
      this.candidateIndex = -1;
      this.pendingNick = "";
      this.lastCheckedNick = "";
      this.trackedWindowId = -1;
      this.trackedWindowTitle = "";
      this.Vte = 0;
      this.actionDelayTicks = 13;
      this.OIBq = var6 ? "Denicking brute (0/" + this.candidateNicks.size() + ")" : "Denicking (0/" + this.candidateNicks.size() + ")";
   }

   private void sortCandidates(List<Denick$19> var1, final Denick$13 var2, final long var3) {
      final long var5 = System.currentTimeMillis();
      Collections.sort(var1, new Comparator<Denick$19>() {
         public int compare(Denick$19 var1, Denick$19 var2x) {
            double var3x = Denick.scoreCandidateInternal(Denick.this, var2x, var2, var5, var3) - Denick.scoreCandidateInternal(Denick.this, var1, var2, var5, var3);
            if (var3x > 1.0E-4) {
               return 1;
            } else {
               return var3x < -1.0E-4 ? -1 : Denick$19.getName(var1).compareToIgnoreCase(Denick$19.getName(var2x));
            }
         }
      });
   }

   private double scoreCandidate(Denick$19 var1, Denick$13 var2, long var3, long var5) {
      if (var1 == null) {
         return 0.0;
      } else {
         double var7 = Denick$19.YJeT(var1);
         if ("random_favorite_kill_message".equals(this.canonicalizeCosmeticKey(Denick$19.getKillMessage(var1)))) {
            var7 += 10000.0;
         }

         if (Denick$19.UhoHj(var1) > 0L) {
            long var9 = var5 > 0L ? Math.abs(Denick$19.UhoHj(var1) - var5) : Math.max(0L, var3 - Denick$19.UhoHj(var1));
            var7 += Math.max(0.0, 7000.0 - var9 / 60000.0);
         }

         if (var2 != null) {
            int var11 = Denick$13.computeEntryDistance(var2, var1);
            if (var11 != Integer.MAX_VALUE) {
               var7 += Math.max(0.0, 3000.0 - var11);
            }
         }

         if (Denick$19.getLastUpdated(var1) > 0L) {
            long var12 = Math.max(0L, var3 - Denick$19.getLastUpdated(var1));
            var7 += Math.max(0.0, 1000.0 - var12 / 3600000.0);
         }

         return var7;
      }
   }

   private String formatEstimateMessage(int var1) {
      return "&7estimated: &f~" + this.formatTimeRange(var1) + "&7.";
   }

   private String formatTimeRange(int var1) {
      int var2 = Math.max(0, var1);
      int var3 = var2 * Math.max(1, 13);
      int var4 = var2 * Math.max(60, 13);
      int var5 = Math.max(1, (int)Math.ceil(var3 / 20.0));
      int var6 = Math.max(var5, (int)Math.ceil(var4 / 20.0));
      return var6 <= var5 + 3 ? this.formatSeconds(var5) : this.formatSeconds(var5) + "-" + this.formatSeconds(var6);
   }

   private String formatSeconds(int var1) {
      if (var1 < 60) {
         return var1 + "s";
      } else {
         int var2 = var1 / 60;
         int var3 = var1 % 60;
         return var3 == 0 ? var2 + "m" : var2 + "m " + var3 + "s";
      }
   }

   private List<Denick$19> parseCandidates(String var1) {
      ArrayList var2 = new ArrayList();
      if (var1 != null && !var1.isEmpty()) {
         JsonElement var3 = new JsonParser().parse(var1);
         this.ZcJyr(var3, var2);
         return var2;
      } else {
         return var2;
      }
   }

   private void ZcJyr(JsonElement var1, List<Denick$19> var2) {
      if (var1 != null && !var1.isJsonNull()) {
         if (var1.isJsonArray()) {
            for (JsonElement var19 : var1.getAsJsonArray()) {
               this.ZcJyr(var19, var2);
            }
         } else if (var1.isJsonObject()) {
            JsonObject var3 = var1.getAsJsonObject();
            String var4 = this.getJsonString(var3, "name");
            String var5 = this.canonicalizeCosmeticKey(this.getJsonString(var3, "killMessage"));
            long var6 = this.getJsonLong(var3, "lastUpdated");
            int var8 = this.XfDc(var3, "finals", -1);
            int var9 = this.XfDc(var3, "beds", -1);
            long var10 = this.getJsonLong(var3, "lastSeen");
            double var12 = this.YOgE(var3, "score");
            if (this.isValidPlayerName(var4)) {
               var2.add(new Denick$19(var4, var5, var8, var9, this.parseCosmetics(var3), var6, var10, var12));
            } else {
               for (Entry var15 : var3.entrySet()) {
                  JsonElement var16 = (JsonElement)var15.getValue();
                  if (var16 != null && (var16.isJsonArray() || var16.isJsonObject())) {
                     this.ZcJyr(var16, var2);
                  }
               }
            }
         }
      }
   }

   private boolean hasUnblockPlayerItem(ContainerChest var1) {
      for (int var2 = 0; var2 < var1.inventorySlots.size(); var2++) {
         Slot var3 = (Slot)var1.inventorySlots.get(var2);
         ItemStack var4 = var3.getStack();
         if (var4 != null && var4.getItem() == Items.paper && var4.hasDisplayName()) {
            String var5 = var4.getDisplayName();
            String var6 = ClientUtils.zaUnpz(var5);
            if ("Unblock Player".equalsIgnoreCase(var6) || var5.contains("§cUnblock Player")) {
               return true;
            }
         }
      }

      return false;
   }

   private void closeContainerWindow(int var1) {
      try {
         mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(var1));
      } catch (Exception var3) {
      }
   }

   private void startVerification(String var1) {
      if (this.isValidPlayerName(var1)) {
         final String var2;
         synchronized (this.nkCi) {
            var2 = this.cAp;
            this.verifyingBlockedNick = true;
            this.gKc = var1;
            this.pendingNick = "";
            this.lastCheckedNick = var1;
            this.trackedWindowId = -1;
            this.trackedWindowTitle = "";
            this.awaitingUnblockResult = true;
            this.Mji = false;
            this.verifyHeaderSeen = false;
            this.verifyingDatabase = false;
            this.phaseWaitTicks = 0;
            this.collectedBlockedNicks.clear();
            this.ambiguousNicks = Collections.emptyList();
            this.stateGeneration++;
            this.Vte = 0;
            this.actionDelayTicks = 13;
            this.OIBq = "Denicking (verifying) " + var1;
         }

         if (!this.isValidPlayerName(var2)) {
            this.restartVerification();
         } else {
            mc.addScheduledTask(new Runnable() {
               @Override
               public void run() {
                  if (Denick.getMinecraftClient().thePlayer != null) {
                     Denick.getMinecraftInstance().thePlayer.sendChatMessage("/block remove " + var2);
                  }
               }
            });
         }
      }
   }

   private void requestBlockedList() {
      synchronized (this.nkCi) {
         if (!this.verifyingBlockedNick) {
            return;
         }

         this.awaitingUnblockResult = false;
         this.Mji = true;
         this.verifyHeaderSeen = false;
         this.phaseWaitTicks = 0;
         this.collectedBlockedNicks.clear();
         this.ambiguousNicks = Collections.emptyList();
      }

      mc.addScheduledTask(new Runnable() {
         @Override
         public void run() {
            if (Denick.getClientInstance().thePlayer != null) {
               Denick.getGameClient().thePlayer.sendChatMessage("/block list");
            }
         }
      });
   }

   private void restartVerification() {
      synchronized (this.nkCi) {
         if (this.verifyingBlockedNick) {
            this.awaitingUnblockResult = false;
            this.Mji = false;
            this.verifyHeaderSeen = false;
            this.verifyingDatabase = false;
            this.phaseWaitTicks = 0;
            this.collectedBlockedNicks.clear();
            this.ambiguousNicks = Collections.emptyList();
            this.pendingNick = "";
            this.lastCheckedNick = this.gKc;
            this.trackedWindowId = -1;
            this.trackedWindowTitle = "";
            this.Vte = 0;
            this.actionDelayTicks = 0;
            this.OIBq = "Denicking (rechecking) " + this.gKc;
         }
      }
   }

   private void handleUnblockResult(String var1, boolean var2) {
      String var3 = this.isValidPlayerName(var1) ? var1 : this.gKc;
      if (!var2) {
         this.confirmVerification();
      } else {
         this.uKzz(var3, "Unblock Player remained after unblock");
      }
   }

   private void uKzz(String var1, final String var2) {
      final String var3 = this.isValidPlayerName(var1) ? var1 : this.gKc;
      final String var4;
      synchronized (this.nkCi) {
         var4 = this.cAp;
         if (this.isValidPlayerName(var3)) {
            this.failedVerificationNicks.add(var3.toLowerCase(Locale.ROOT));
         }

         this.verifyingBlockedNick = false;
         this.stateGeneration++;
         this.awaitingUnblockResult = false;
         this.Mji = false;
         this.verifyHeaderSeen = false;
         this.verifyingDatabase = false;
         this.phaseWaitTicks = 0;
         this.collectedBlockedNicks.clear();
         this.ambiguousNicks = Collections.emptyList();
         this.gKc = "";
         this.pendingNick = "";
         this.lastCheckedNick = var3;
         this.trackedWindowId = -1;
         this.trackedWindowTitle = "";
         this.Vte = 0;
         this.actionDelayTicks = 26;
         this.updateStatusText();
      }

      mc.addScheduledTask(new Runnable() {
         @Override
         public void run() {
            if (Denick.getMinecraftHandle().thePlayer != null && Denick.isValidNickName(Denick.this, var4)) {
               Denick.getMc().thePlayer.sendChatMessage("/block " + var4);
            }

            if (Denick.isValidNickName(Denick.this, var3)) {
               ClientUtils.sendJadeMessage("Jade", "&f" + var3 + " &7failed verification &8(" + var2 + ")&7. Continuing.");
            }
         }
      });
   }

   private void confirmVerification() {
      String var1;
      String var2;
      int var3;
      synchronized (this.nkCi) {
         var1 = this.cAp;
         var2 = this.gKc;
         var3 = this.stateGeneration;
      }

      this.completeDenick(var3, var1, var2);
   }

   private void completeDenick(int var1, final String var2, final String var3) {
      final boolean var4;
      synchronized (this.nkCi) {
         if (!this.isSameVerificationAttempt(var1, var2, var3)) {
            return;
         }

         this.Ob0 = Denick$17.FOUND;
         this.OIBq = "Denicking (found)";
         this.verifyingBlockedNick = false;
         this.awaitingUnblockResult = false;
         this.verifyingDatabase = false;
         this.ambiguousNicks = Collections.emptyList();
         var4 = this.confirmedNicks.add(var2.toLowerCase(Locale.ROOT));
      }

      this.saveResolvedNick(var2, var3, "Confirmed");
      mc.addScheduledTask(new Runnable() {
         @Override
         public void run() {
            if (var4) {
               ClientUtils.sendJadeMessage("Jade", "&f" + var3 + " &7is nicked as &b" + var2 + "&7. &8(Confirmed)");
            }

            WhisperShortcuts.vQwg3(var3);
            Denick.stopDenickInternal(Denick.this);
         }
      });
   }

   private boolean isFailedNick(String var1) {
      return var1 != null && this.failedVerificationNicks.contains(var1.toLowerCase(Locale.ROOT));
   }

   private String summarizeSimilarNicks() {
      ArrayList var1 = new ArrayList();
      String var2 = this.cAp == null ? "" : this.cAp.toLowerCase(Locale.ROOT);

      for (Entry var4 : this.killMessagesByNick.entrySet()) {
         String var5 = (String)var4.getKey();
         if (var5 != null && !var5.equalsIgnoreCase(var2) && this.matchesKillMessageFilter(this.qPi5, (String)var4.getValue())) {
            var1.add(var5);
         }
      }

      Collections.sort(var1, String.CASE_INSENSITIVE_ORDER);
      return var1.size() > 8 ? this.joinNames(var1.subList(0, 8)) + " +" + (var1.size() - 8) + " more" : this.joinNames(var1);
   }

   private String joinNames(List<String> var1) {
      StringBuilder var2 = new StringBuilder();

      for (String var4 : var1) {
         if (var2.length() > 0) {
            var2.append(", ");
         }

         var2.append(var4);
      }

      return var2.toString();
   }

   private String formatNameList(List<String> var1, int var2) {
      if (var1 != null && !var1.isEmpty()) {
         int var3 = Math.max(1, var2);
         ArrayList var4 = new ArrayList(var1);
         Collections.sort(var4, String.CASE_INSENSITIVE_ORDER);
         return var4.size() > var3 ? this.joinNames(var4.subList(0, var3)) + " +" + (var4.size() - var3) + " more" : this.joinNames(var4);
      } else {
         return "";
      }
   }

   private void failDenick(final String var1) {
      synchronized (this.nkCi) {
         this.Ob0 = Denick$17.IDLE;
         this.OIBq = "";
         this.candidateNicks = Collections.emptyList();
         this.statFilters = null;
         this.cosmeticFilters = Collections.emptyList();
         this.bruteMode = false;
         this.verifyingBlockedNick = false;
         this.gKc = "";
         this.failedVerificationNicks.clear();
         this.awaitingUnblockResult = false;
         this.Mji = false;
         this.verifyHeaderSeen = false;
         this.verifyingDatabase = false;
         this.phaseWaitTicks = 0;
         this.collectedBlockedNicks.clear();
         this.ambiguousNicks = Collections.emptyList();
         this.stateGeneration++;
         this.pendingNick = "";
         this.lastCheckedNick = "";
         this.trackedWindowId = -1;
         this.trackedWindowTitle = "";
      }

      mc.addScheduledTask(new Runnable() {
         @Override
         public void run() {
            ClientUtils.sendJadeMessage("Jade", "&7" + var1);
         }
      });
   }

   private void resetDenickState() {
      synchronized (this.nkCi) {
         this.Ob0 = Denick$17.IDLE;
         this.OIBq = "";
         this.candidateNicks = Collections.emptyList();
         this.cAp = "";
         this.qPi5 = "";
         this.statFilters = null;
         this.cosmeticFilters = Collections.emptyList();
         this.bruteMode = false;
         this.verifyingBlockedNick = false;
         this.gKc = "";
         this.failedVerificationNicks.clear();
         this.awaitingUnblockResult = false;
         this.Mji = false;
         this.verifyHeaderSeen = false;
         this.verifyingDatabase = false;
         this.phaseWaitTicks = 0;
         this.collectedBlockedNicks.clear();
         this.ambiguousNicks = Collections.emptyList();
         this.stateGeneration++;
         this.pendingNick = "";
         this.lastCheckedNick = "";
         this.trackedWindowId = -1;
         this.trackedWindowTitle = "";
         this.candidateIndex = -1;
         this.Vte = 0;
         this.actionDelayTicks = 0;
      }
   }

   private void startDatabaseVerification() {
      final String var1;
      final String var2;
      final LinkedHashSet var3;
      final String var4;
      final Denick$13 var5;
      final ArrayList var6;
      final boolean var7;
      final int var8;
      synchronized (this.nkCi) {
         var1 = this.cAp;
         var2 = this.gKc;
         var3 = new LinkedHashSet<>(this.collectedBlockedNicks);
         var4 = this.qPi5;
         var5 = this.statFilters;
         var6 = new ArrayList<>(this.cosmeticFilters);
         var7 = this.bruteMode;
         var8 = this.stateGeneration;
         this.Mji = false;
         this.verifyHeaderSeen = false;
         this.verifyingDatabase = true;
         this.phaseWaitTicks = 0;
         this.collectedBlockedNicks.clear();
         this.ambiguousNicks = Collections.emptyList();
         this.OIBq = "Denicking (verifying database) " + var2;
      }

      Thread var12 = new Thread(new Runnable() {
         @Override
         public void run() {
            Denick.gcbTi(Denick.this, var8, var1, var2, var3, var4, var5, var6, var7);
         }
      }, "Denick-Verify-" + var2);
      var12.setPriority(1);
      var12.start();
   }

   private void verifyNickCandidates(int var1, String var2, String var3, LinkedHashSet<String> var4, String var5, Denick$13 var6, List<Denick$12> var7, boolean var8) {
      if (this.isValidPlayerName(var2) && this.isValidPlayerName(var3)) {
         ArrayList var9 = new ArrayList();

         for (String var11 : var4) {
            if (!this.isVerificationAttemptActive(var1, var2, var3)) {
               return;
            }

            if (this.isValidPlayerName(var11) && !var11.equalsIgnoreCase(var2) && !var11.equalsIgnoreCase(var3)) {
               try {
                  List var12 = this.fetchCandidates(var11, var5, var6, var7, var8, false);
                  if (!this.isVerificationAttemptActive(var1, var2, var3)) {
                     return;
                  }

                  if (this.listContainsNick(var12, var3)) {
                     var9.add(var11);
                  }
               } catch (Exception var13) {
               }
            }
         }

         if (var9.isEmpty()) {
            this.completeDenick(var1, var2, var3);
         } else {
            this.startAmbiguousSelection(var1, var2, var3, var9);
         }
      } else {
         this.completeDenick(var1, var2, var3);
      }
   }

   private void startAmbiguousSelection(int var1, String var2, String var3, List<String> var4) {
      synchronized (this.nkCi) {
         if (this.isSameVerificationAttempt(var1, var2, var3)) {
            this.verifyingDatabase = false;
            this.ambiguousNicks = new ArrayList<>(var4);
            this.pendingNick = "";
            this.trackedWindowId = -1;
            this.trackedWindowTitle = "";
            this.Vte = 0;
            this.actionDelayTicks = 0;
            this.updateStatusText();
         }
      }
   }

   private boolean isVerificationAttemptActive(int var1, String var2, String var3) {
      synchronized (this.nkCi) {
         return this.isSameVerificationAttempt(var1, var2, var3);
      }
   }

   private boolean isSameVerificationAttempt(int var1, String var2, String var3) {
      return (this.Ob0 == Denick$17.SEARCHING || this.Ob0 == Denick$17.PAUSED)
         && this.verifyingBlockedNick
         && this.stateGeneration == var1
         && this.cAp != null
         && this.gKc != null
         && this.cAp.equalsIgnoreCase(var2)
         && this.gKc.equalsIgnoreCase(var3);
   }

   private boolean listContainsNick(List<Denick$19> var1, String var2) {
      if (var1 != null && this.isValidPlayerName(var2)) {
         for (Denick$19 var4 : var1) {
            if (var4 != null && var2.equalsIgnoreCase(Denick$19.getName(var4))) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void warnAmbiguousNicks(int var1, final String var2, final String var3, List<String> var4) {
      synchronized (this.nkCi) {
         if (!this.isSameVerificationAttempt(var1, var2, var3)) {
            return;
         }

         if (this.isValidPlayerName(var3)) {
            this.failedVerificationNicks.add(var3.toLowerCase(Locale.ROOT));
         }

         this.verifyingBlockedNick = false;
         this.stateGeneration++;
         this.awaitingUnblockResult = false;
         this.verifyingDatabase = false;
         this.ambiguousNicks = Collections.emptyList();
         this.gKc = "";
         this.pendingNick = "";
         this.lastCheckedNick = var3;
         this.trackedWindowId = -1;
         this.trackedWindowTitle = "";
         this.Vte = 0;
         this.actionDelayTicks = 13;
         this.updateStatusText();
      }

      final String var8 = this.formatNameList(var4, 8);
      mc.addScheduledTask(
         new Runnable() {
            @Override
            public void run() {
               String var1x = Denick.getResolvedNickInternal(Denick.this, var2);
               if (var1x == null || !var1x.equalsIgnoreCase(var3)) {
                  ClientUtils.sendJadeMessage(
                     "Jade",
                     "&f"
                        + var3
                        + " &7also matched blocked nick(s) &f"
                        + var8
                        + "&7 with the same cosmetics, so it likely belongs to another blocked nick. Continuing."
                  );
               }
            }
         }
      );
   }

   private void renderStatusOverlay() {
      Denick$17 var1;
      String var2;
      String var3;
      String var4;
      int var5;
      int var6;
      synchronized (this.nkCi) {
         var1 = this.Ob0;
         var2 = this.cAp;
         var3 = this.pendingNick;
         var4 = this.lastCheckedNick;
         var5 = this.candidateIndex;
         var6 = this.candidateNicks.size();
      }

      if (var1 != Denick$17.IDLE) {
         ScaledResolution var16 = new ScaledResolution(mc);
         IFont var8 = FontManager.getHudRenderer("Modern", 1.0F);
         IFont var9 = FontManager.getHudRenderer("Bold", 1.0F);
         float var10 = var16.getScaledWidth() / 2.0F;
         float var11 = 16.0F;
         float var12 = 11.0F;
         this.OTAWus(var9, "Denicking", var10, var11, this.getThemeColor(0.0));
         var11 += var12;
         this.OTAWus(var8, this.getStatusLabel(var1), var10, var11, -1);
         var11 += var12;
         if (var2 != null && !var2.isEmpty()) {
            int var13 = var6 <= 0 ? 0 : Math.max(0, var6 - Math.max(0, var5 + 1));
            String var14 = var6 <= 0 ? "loading" : this.formatTimeRange(Math.max(1, var13));
            this.drawCenteredSegments(
               var8, var10, var11, new String[]{"Target ", var2, "  Time ", var14}, new int[]{-1644826, this.getThemeColor(22.0), -1644826, this.getThemeColor(68.0)}
            );
            var11 += var12;
         }

         if (var6 > 0) {
            int var19 = Math.max(0, Math.min(var6, var5 + 1));
            this.drawCenteredSegments(
               var8,
               var10,
               var11,
               new String[]{"Progress ", String.valueOf(var19), " / ", String.valueOf(var6)},
               new int[]{-1644826, this.getThemeColor(100.0), -1644826, this.getThemeColor(132.0)}
            );
            var11 += var12;
         }

         String var20 = var3 != null && !var3.isEmpty() ? var3 : var4;
         if (var20 != null && !var20.isEmpty()) {
            String var21 = var3 != null && !var3.isEmpty() ? "Checking " : "Last ";
            this.drawCenteredSegments(var8, var10, var11, new String[]{var21, var20}, new int[]{-1644826, this.getThemeColor(168.0)});
         }
      }
   }

   private String getStatusLabel(Denick$17 var1) {
      if (var1 == Denick$17.FETCHING) {
         return "Loading candidates";
      } else if (var1 == Denick$17.PAUSED) {
         return "Paused";
      } else if (var1 == Denick$17.FOUND) {
         return "Found";
      } else {
         return this.verifyingBlockedNick ? "Verifying" : "Searching";
      }
   }

   private void OTAWus(IFont var1, String var2, float var3, float var4, int var5) {
      if (var2 != null && !var2.isEmpty()) {
         var1.drawString(var2, var3 - var1.getStringWidth(var2) / 2.0F, var4, var5, true);
      }
   }

   private void drawCenteredSegments(IFont var1, float var2, float var3, String[] var4, int[] var5) {
      if (var4 != null && var5 != null && var4.length != 0 && var4.length == var5.length) {
         float var6 = 0.0F;

         for (String var10 : var4) {
            if (var10 != null && !var10.isEmpty()) {
               var6 += var1.getStringWidth(var10);
            }
         }

         float var11 = var2 - var6 / 2.0F;

         for (int var12 = 0; var12 < var4.length; var12++) {
            String var13 = var4[var12];
            if (var13 != null && !var13.isEmpty()) {
               var1.drawString(var13, var11, var3, var5[var12], true);
               var11 += var1.getStringWidth(var13);
            }
         }
      }
   }

   private int getThemeColor(double var1) {
      return ClientUtils.YVVZ(Arraylist.xQec0(0.0), 255);
   }

   private String cleanNick(String var1) {
      String var2 = this.YiM0(var1);
      int var3 = var2.indexOf(32);
      if (var3 >= 0) {
         var2 = var2.substring(0, var3);
      }

      return var2.trim();
   }

   private boolean isValidPlayerName(String var1) {
      if (var1 != null && var1.length() >= 3 && var1.length() <= 16) {
         for (int var2 = 0; var2 < var1.length(); var2++) {
            char var3 = var1.charAt(var2);
            if (!Character.isLetterOrDigit(var3) && var3 != '_') {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private String formatCosmeticName(String var1) {
      if (this.isDefaultKillMessage(var1)) {
         return "default";
      } else if (var1 != null && !var1.isEmpty()) {
         String var2 = var1.startsWith("killmessages_") ? var1.substring("killmessages_".length()) : var1;
         return var2.replace('_', ' ');
      } else {
         return "unknown";
      }
   }

   private boolean matchesKillMessageFilter(String var1, String var2) {
      var1 = this.canonicalizeCosmeticKey(var1);
      var2 = this.canonicalizeCosmeticKey(var2);
      if ("random_kill_message".equals(var1)) {
         return this.forQ(var2);
      } else if ("random_favorite_kill_message".equals(var1)) {
         return "random_favorite_kill_message".equals(var2);
      } else if (this.isDefaultKillMessage(var1)) {
         return var2 == null || var2.trim().isEmpty() || this.isDefaultKillMessage(var2);
      } else {
         return this.isCounterKillMessage(var1) ? this.isCounterKillMessage(var2) : var1 != null && var1.equals(var2);
      }
   }

   private boolean Bfez(Denick$19 var1, String var2, Denick$13 var3, List<Denick$12> var4) {
      if (!this.matchesKillMessageFilter(var2, Denick$19.getKillMessage(var1))) {
         return false;
      } else if (var3 != null && !Denick$13.UYRBXj(var3, var1)) {
         return false;
      } else {
         if (var4 != null) {
            for (Denick$12 var6 : var4) {
               if (!Denick$12.matchesEntry(var6, var1)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private String DXCzlA2(String var1, Denick$13 var2, List<Denick$12> var3) {
      StringBuilder var4 = new StringBuilder(this.formatCosmeticName(var1));
      if (var2 != null) {
         var4.append(" + ").append(Denick$13.getStatsLabelFor(var2));
      }

      if (var3 != null) {
         for (Denick$12 var6 : var3) {
            var4.append(" + ").append(Denick$12.getDisplayText(var6));
         }
      }

      return var4.toString();
   }

   private String buildFilterCacheKey(Denick$13 var1, List<Denick$12> var2) {
      StringBuilder var3 = new StringBuilder();
      if (var1 != null) {
         var3.append(Denick$13.getStatsKey(var1));
      }

      if (var2 != null) {
         for (Denick$12 var5 : var2) {
            var3.append('|').append(Denick$12.getCosmeticId(var5)).append('=').append(Denick$12.GJUvu(var5));
         }
      }

      return var3.toString();
   }

   private Denick$13 parseCounterFilter(Denick$18 var1, Matcher var2) {
      if (Denick$18.getStatType(var1) == Denick$14.NONE) {
         return null;
      } else {
         String var3 = this.getMatcherGroup(var2, "counter");
         if (var3 != null && !var3.isEmpty()) {
            try {
               int var4 = Integer.parseInt(var3.replace(",", ""));
               return new Denick$13(Denick$18.getStatType(var1), var4);
            } catch (NumberFormatException var5) {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   private String getMatcherGroup(Matcher var1, String var2) {
      try {
         String var3 = var1.group(var2);
         return var3 == null ? "" : var3;
      } catch (IllegalArgumentException var4) {
         return "";
      }
   }

   private Denick$16 parseDenickArguments(String var1, List<String> var2, String var3) {
      ArrayList var4 = new ArrayList();
      Map var5 = this.getSavedCosmetics(var1);

      for (Entry var7 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (var5.entrySet())) {
         var4.add(new Denick$12((String)var7.getKey(), (String)var7.getValue()));
      }

      String var13 = this.canonicalizeCosmeticKey(var3);
      if (var2 != null && !var2.isEmpty()) {
         for (int var14 = 0; var14 < var2.size(); var14++) {
            String var8 = this.normalizeToken((String)var2.get(var14));
            if (!var8.isEmpty()) {
               if (this.isKillMessageKeyword(var8)) {
                  if (var14 + 1 >= var2.size()) {
                     ClientUtils.sendJadeMessage("Jade", "&7missing kill message value after &f" + (String)var2.get(var14) + "&7.");
                     return null;
                  }

                  StringBuilder var15;
                  for (var15 = new StringBuilder(this.normalizeToken((String)var2.get(++var14))); var14 + 1 < var2.size(); var14++) {
                     String var16 = this.normalizeToken((String)var2.get(var14 + 1));
                     if (var16.isEmpty() || this.isKillMessageKeyword(var16) || this.findCosmeticCategory(var16) != null) {
                        break;
                     }

                     var15.append('_').append(var16);
                  }

                  String var17 = this.ItKhg(var15.toString());
                  if (!this.vIc4(var17)) {
                     ClientUtils.sendJadeMessage("Jade", "&7unknown kill message &f" + var15 + "&7.");
                     return null;
                  }

                  var13 = var17;
               } else {
                  Denick$11 var9 = this.findCosmeticCategory(var8);
                  String var10 = var8;
                  if (var9 != null && Denick$11.DVb815(var9).equals(var8)) {
                     if (var14 + 1 >= var2.size()) {
                        ClientUtils.sendJadeMessage("Jade", "&7missing cosmetic value after &f" + (String)var2.get(var14) + "&7.");
                        return null;
                     }

                     StringBuilder var11;
                     for (var11 = new StringBuilder(this.normalizeToken((String)var2.get(++var14))); var14 + 1 < var2.size(); var14++) {
                        String var12 = this.normalizeToken((String)var2.get(var14 + 1));
                        if (var12.isEmpty() || this.findCosmeticCategory(var12) != null || this.isRandomCosmeticKeyword(var12)) {
                           break;
                        }

                        var11.append('_').append(var12);
                     }

                     var10 = var11.toString();
                  }

                  Denick$12 var18 = this.createCosmeticFilter(var9, var10);
                  if (var18 == null) {
                     ClientUtils.sendJadeMessage("Jade", "&7unknown cosmetic filter &f" + (String)var2.get(var14) + "&7.");
                     return null;
                  }

                  var4.add(var18);
               }
            }
         }

         return new Denick$16(var13, var4);
      } else {
         return new Denick$16(var13, var4);
      }
   }

   private boolean isKillMessageKeyword(String var1) {
      return "kill_message".equals(var1) || "killmessage".equals(var1) || "killmessages".equals(var1);
   }

   private String ItKhg(String var1) {
      var1 = this.normalizeToken(var1);
      if (var1.isEmpty()) {
         return "";
      } else if ("random_cosmetic".equals(var1) || "random_kill_message".equals(var1)) {
         return "random_kill_message";
      } else if ("random_favorite_cosmetic".equals(var1) || "random_favorite_kill_message".equals(var1)) {
         return "random_favorite_kill_message";
      } else if (KILL_MESSAGE_SAMPLES.containsKey(var1)) {
         return var1;
      } else {
         String var2 = var1.startsWith("killmessages_") ? var1 : "killmessages_" + var1;
         return KILL_MESSAGE_SAMPLES.containsKey(var2) ? var2 : this.canonicalizeCosmeticKey(var1);
      }
   }

   private Map<String, String> getSavedCosmetics(String var1) {
      String var2 = this.cleanNick(var1);
      if (!this.isValidPlayerName(var2)) {
         return Collections.emptyMap();
      } else {
         synchronized (this.YEspO) {
            Denick$15 var4 = this.savedDenicks.get(var2.toLowerCase(Locale.ROOT));
            return (Map<String, String>)(var4 != null && !Denick$15.Zgkro(var4).isEmpty()
               ? new LinkedHashMap<>(Denick$15.Zgkro(var4))
               : Collections.emptyMap());
         }
      }
   }

   private Denick$12 createCosmeticFilter(Denick$11 var1, String var2) {
      Denick$11 var3 = var1 == null ? this.findCosmeticCategory(var2) : var1;
      if (var3 == null) {
         return null;
      } else {
         String var4 = this.normalizeCosmeticValue(var3, var2);
         return var4.isEmpty() ? null : new Denick$12(Denick$11.getStatsField(var3), var4);
      }
   }

   private String normalizeCosmeticValue(Denick$11 var1, String var2) {
      if (var1 == null) {
         return "";
      } else {
         String var3 = this.normalizeToken(var2);
         if (var3.isEmpty()) {
            return "";
         } else {
            if ("lightning_strike".equals(var3)) {
               var3 = "lighting_strike";
            }

            if (!this.isRandomCosmeticKeyword(var3) && !var3.startsWith(Denick$11.getValuePrefix(var1))) {
               var3 = Denick$11.getValuePrefix(var1) + var3;
            }

            return var3;
         }
      }
   }

   private Denick$11 findCosmeticCategory(String var1) {
      for (Denick$11 var5 : COSMETIC_CATEGORIES) {
         if (Denick$11.DVb815(var5).equals(var1) || var1.startsWith(Denick$11.getValuePrefix(var5))) {
            return var5;
         }
      }

      return null;
   }

   private Denick$11 shiL(String var1) {
      if (var1 == null) {
         return null;
      } else {
         for (Denick$11 var5 : COSMETIC_CATEGORIES) {
            if (Denick$11.getStatsField(var5).equals(var1)) {
               return var5;
            }
         }

         return null;
      }
   }

   private String normalizeToken(String var1) {
      return var1 == null ? "" : var1.trim().toLowerCase(Locale.ROOT).replace('-', '_').replace(' ', '_');
   }

   private boolean isRandomCosmeticKeyword(String var1) {
      return "random_cosmetic".equals(var1) || "random_favorite_cosmetic".equals(var1);
   }

   private boolean forQ(String var1) {
      var1 = this.canonicalizeCosmeticKey(var1);
      return "random_kill_message".equals(var1) || "random_favorite_kill_message".equals(var1);
   }

   private boolean Lejx(String var1) {
      return this.forQ(var1);
   }

   private boolean vIc4(String var1) {
      var1 = this.canonicalizeCosmeticKey(var1);
      return this.forQ(var1) || KILL_MESSAGE_SAMPLES.containsKey(var1);
   }

   private String canonicalizeCosmeticKey(String var1) {
      if (var1 == null) {
         return "";
      } else if ("random_cosmetic".equals(var1)) {
         return "random_kill_message";
      } else {
         return "random_favorite_cosmetic".equals(var1) ? "random_favorite_kill_message" : var1;
      }
   }

   private boolean isDefaultKillMessage(String var1) {
      return var1 == null || "killmessages_none".equals(var1) || "killmessages_default".equals(var1);
   }

   private String YiM0(String var1) {
      if (var1 == null) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder();

         for (int var3 = 0; var3 < var1.length(); var3++) {
            char var4 = var1.charAt(var3);
            if ((var4 == '&' || var4 == 167) && var3 + 1 < var1.length()) {
               var3++;
            } else {
               var2.append(var4);
            }
         }

         return var2.toString().trim();
      }
   }

   private List<Denick$18> buildKillMessageDefinitions() {
      ArrayList var1 = new ArrayList();

      for (Entry var3 : KILL_MESSAGE_SAMPLES.entrySet()) {
         for (String var7 : (String[])var3.getValue()) {
            var1.add(new Denick$18((String)var3.getKey(), this.getCounterType(var7), this.JIzu(var7)));
         }
      }

      return var1;
   }

   private Denick$14 getCounterType(String var1) {
      if (var1 == null || var1.indexOf(35) < 0) {
         return Denick$14.NONE;
      } else {
         return var1.startsWith("Blue Bed") ? Denick$14.BEDS : Denick$14.FINALS;
      }
   }

   private boolean bothUseCounters(String var1, Denick$18 var2) {
      return var1 != null && var2 != null && Denick$18.getStatType(var2) != Denick$14.NONE && this.isCounterKillMessage(var1) && this.isCounterKillMessage(Denick$18.ZeKzvUe(var2));
   }

   private boolean MDGRc(Denick$18 var1, String var2, String var3) {
      return var1 != null
         && "killmessages_default".equals(Denick$18.ZeKzvUe(var1))
         && var2 != null
         && var2.indexOf(" was knocked into the void by ") >= 0
         && this.isVoidWordHighlighted(var3);
   }

   private boolean isUnexpectedVoidMessage(Denick$18 var1, String var2, String var3) {
      return var1 != null
         && Denick$18.getStatType(var1) == Denick$14.NONE
         && this.isCounterKillMessage(Denick$18.ZeKzvUe(var1))
         && var2 != null
         && var2.indexOf(" was knocked into the void by ") >= 0
         && this.isVoidWordHighlighted(var3);
   }

   private boolean isVoidWordHighlighted(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         StringBuilder var2 = new StringBuilder();
         StringBuilder var3 = new StringBuilder();
         char var4 = 'r';

         for (int var5 = 0; var5 < var1.length(); var5++) {
            char var6 = var1.charAt(var5);
            if (var6 == 167 && var5 + 1 < var1.length()) {
               char var7 = Character.toLowerCase(var1.charAt(++var5));
               if (var7 >= '0' && var7 <= '9' || var7 >= 'a' && var7 <= 'f' || var7 == 'r') {
                  var4 = var7;
               }
            } else {
               var2.append(var6);
               var3.append(var4);
            }
         }

         String var10 = var2.toString().toLowerCase(Locale.ROOT);
         int var11 = var10.indexOf("void");

         while (var11 >= 0) {
            int var12 = var11 + "void".length();
            boolean var8 = true;

            for (int var9 = var11; var9 < var12 && var9 < var3.length(); var9++) {
               if (var3.charAt(var9) != 'e') {
                  var8 = false;
                  break;
               }
            }

            if (var8) {
               return true;
            }

            var11 = var10.indexOf("void", var12);
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean isCounterKillMessage(String var1) {
      return "killmessages_counter".equals(var1) || "killmessages_glorious".equals(var1) || "killmessages_noble".equals(var1);
   }

   private Pattern JIzu(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("(?:(?:[A-Z][A-Z ]*)\\s*>\\s*)?");
      int var3 = 0;

      while (var3 < var1.length()) {
         if (var1.startsWith("FrostyCookies", var3)) {
            var2.append(".+?");
            var3 += "FrostyCookies".length();
         } else if (var1.startsWith("Blue Bed", var3)) {
            var2.append(".+? Bed");
            var3 += "Blue Bed".length();
         } else if (var1.startsWith("uwu_x3", var3)) {
            var2.append("(?<killer>[A-Za-z0-9_]{3,16})");
            var3 += "uwu_x3".length();
         } else if (var1.charAt(var3) == '#') {
            var2.append("#(?<counter>[0-9,]+)");
            var3++;

            while (var3 < var1.length() && (Character.isDigit(var1.charAt(var3)) || var1.charAt(var3) == ',')) {
               var3++;
            }
         } else {
            var2.append(Pattern.quote(String.valueOf(var1.charAt(var3))));
            var3++;
         }
      }

      var2.append("(?: FINAL KILL!)?");
      return Pattern.compile(var2.toString());
   }

   private void fetchProfileName(String var1, String var2, final NetworkPlayerInfo var3) {
      try {
         HttpURLConnection var4 = (HttpURLConnection)new URL(var1).openConnection();
         var4.setRequestMethod("GET");
         var4.setConnectTimeout(5000);
         var4.setReadTimeout(5000);
         int var5 = var4.getResponseCode();
         if (var5 == 200) {
            BufferedReader var6 = new BufferedReader(new InputStreamReader(var4.getInputStream()));
            StringBuilder var7 = new StringBuilder();

            String var8;
            while ((var8 = var6.readLine()) != null) {
               var7.append(var8);
            }

            var6.close();
            var4.disconnect();
            final String var9 = this.extractNameFromJson(var7.toString());
            if (var9 != null && !var9.equalsIgnoreCase(var3.getGameProfile().getName())) {
               mc.addScheduledTask(new Runnable() {
                  @Override
                  public void run() {
                     Denick.ysvJq(Denick.this, var3, var9, "UUID");
                  }
               });
            }

            return;
         }

         this.pendingUuidLookups.remove(var2);
      } catch (Exception var13) {
         return;
      } finally {
         this.pendingUuidLookups.remove(var2);
      }
   }

   public static void startSearch(Denick var0, String var1, String var2, Denick$13 var3, List var4, boolean var5) {
      var0.searchCandidates(var1, var2, var3, var4, var5);
   }

   public static String formatEstimate(Denick var0, int var1) {
      return var0.formatEstimateMessage(var1);
   }

   public static double scoreCandidateInternal(Denick var0, Denick$19 var1, Denick$13 var2, long var3, long var5) {
      return var0.scoreCandidate(var1, var2, var3, var5);
   }

   public static Minecraft getMinecraftClient() {
      return mc;
   }

   public static Minecraft getMinecraftInstance() {
      return mc;
   }

   public static Minecraft getClientInstance() {
      return mc;
   }

   public static Minecraft getGameClient() {
      return mc;
   }

   public static Minecraft getMinecraftHandle() {
      return mc;
   }

   public static boolean isValidNickName(Denick var0, String var1) {
      return var0.isValidPlayerName(var1);
   }

   public static Minecraft getMc() {
      return mc;
   }

   public static void stopDenickInternal(Denick var0) {
      var0.resetDenickState();
   }

   public static void gcbTi(Denick var0, int var1, String var2, String var3, LinkedHashSet var4, String var5, Denick$13 var6, List var7, boolean var8) {
      var0.verifyNickCandidates(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public static String getResolvedNickInternal(Denick var0, String var1) {
      return var0.getResolvedNick(var1);
   }

   public static void ysvJq(Denick var0, NetworkPlayerInfo var1, String var2, String var3) {
      var0.YADSSbm(var1, var2, var3);
   }

   static {
      Denick$11[] var10000 = new Denick$11[15];
      var10000[0] = new Denick$11(
         "victory_dance",
         "victorydance_",
         "activeVictoryDance"
      );
      var10000[1] = new Denick$11(
         "victorydance",
         "victorydance_",
         "activeVictoryDance"
      );
      var10000[2] = new Denick$11(
         "kill_effect",
         "killeffect_",
         "activeKillEffect"
      );
      var10000[3] = new Denick$11(
         "killeffect",
         "killeffect_",
         "activeKillEffect"
      );
      var10000[4] = new Denick$11(
         "bed_destroy",
         "beddestroy_",
         "activeBedDestroy"
      );
      var10000[5] = new Denick$11(
         "beddestroy",
         "beddestroy_",
         "activeBedDestroy"
      );
      var10000[6] = new Denick$11(
         "spray",
         "spray_",
         "activeSprays"
      );
      var10000[7] = new Denick$11(
         "sprays",
         "spray_",
         "activeSprays"
      );
      var10000[8] = new Denick$11(
         "island_topper",
         "islandtopper_",
         "activeIslandTopper"
      );
      var10000[9] = new Denick$11(
         "npc_skin",
         "npcskin_",
         "activeNPCSkin"
      );
      var10000[10] = new Denick$11(
         "glyph",
         "glyph_",
         "activeGlyph"
      );
      var10000[11] = new Denick$11(
         "wood_type",
         "woodtype_",
         "activeWoodType"
      );
      var10000[12] = new Denick$11(
         "death_cry",
         "deathcry_",
         "activeDeathCry"
      );
      var10000[13] = new Denick$11(
         "projectile_trail",
         "projectiletrail_",
         "activeProjectileTrail"
      );
      var10000[14] = new Denick$11(
         "figurine",
         "figurine_",
         "active_figurine"
      );
      COSMETIC_CATEGORIES = var10000;
      String[] var0 = new String[152];
      var0[0] = "victory_dance";
      var0[1] = "kill_effect";
      var0[2] = "bed_destroy";
      var0[3] = "victorydance_abominable_snowman";
      var0[4] = "victorydance_anvil_ascension";
      var0[5] = "victorydance_anvil_rain";
      var0[6] = "victorydance_aura";
      var0[7] = "victorydance_cake_walk";
      var0[8] = "victorydance_chicken_apocalypse";
      var0[9] = "victorydance_chicken_rider";
      var0[10] = "victorydance_chinese_dragon";
      var0[11] = "victorydance_cold_snap";
      var0[12] = "victorydance_dragon_fire";
      var0[13] = "victorydance_dragon_rider";
      var0[14] = "victorydance_dreamscape";
      var0[15] = "victorydance_easter_bunnies";
      var0[16] = "victorydance_egg_meteors";
      var0[17] = "victorydance_elder_guardian_rider";
      var0[18] = "victorydance_exploding_bunnies";
      var0[19] = "victorydance_fanbase";
      var0[20] = "victorydance_festive_music";
      var0[21] = "victorydance_figurine_rain";
      var0[22] = "victorydance_fireworks";
      var0[23] = "victorydance_floating_lanterns";
      var0[24] = "victorydance_flower_bed";
      var0[25] = "victorydance_ghast_rider";
      var0[26] = "victorydance_graveyardrave";
      var0[27] = "victorydance_guardians";
      var0[28] = "victorydance_haunted";
      var0[29] = "victorydance_heat_wave";
      var0[30] = "victorydance_hurricanehell";
      var0[31] = "victorydance_infection";
      var0[32] = "victorydance_insomnia";
      var0[33] = "victorydance_kartaway";
      var0[34] = "victorydance_meteor_shower";
      var0[35] = "victorydance_night_shift";
      var0[36] = "victorydance_none";
      var0[37] = "victorydance_portal";
      var0[38] = "victorydance_pumpkin_bomber";
      var0[39] = "victorydance_pumpkin_laser";
      var0[40] = "victorydance_pumpkin_patch";
      var0[41] = "victorydance_puppy_party";
      var0[42] = "victorydance_rabbit_meteors";
      var0[43] = "victorydance_rainbow_dolly";
      var0[44] = "victorydance_raining_pigs";
      var0[45] = "victorydance_rooted";
      var0[46] = "victorydance_snow_bomber";
      var0[47] = "victorydance_snowed_in";
      var0[48] = "victorydance_special_fireworks";
      var0[49] = "victorydance_terror";
      var0[50] = "victorydance_to_build_a_snowman";
      var0[51] = "victorydance_toy_stick";
      var0[52] = "victorydance_twerk_apocalypse";
      var0[53] = "victorydance_winter_twister";
      var0[54] = "victorydance_wither_rider";
      var0[55] = "victorydance_woolnado";
      var0[56] = "victorydance_yeehaw";
      var0[57] = "killeffect_after_life";
      var0[58] = "killeffect_anvil_smash";
      var0[59] = "killeffect_balloons";
      var0[60] = "killeffect_batcrux";
      var0[61] = "killeffect_bee_abduction";
      var0[62] = "killeffect_beef_everywhere";
      var0[63] = "killeffect_black_mark";
      var0[64] = "killeffect_blood_bats";
      var0[65] = "killeffect_blood_explosion";
      var0[66] = "killeffect_bunny_explosion";
      var0[67] = "killeffect_burning_shoes";
      var0[68] = "killeffect_campfire";
      var0[69] = "killeffect_candle";
      var0[70] = "killeffect_chicken_tower";
      var0[71] = "killeffect_cookie_fountain";
      var0[72] = "killeffect_cow_rocket";
      var0[73] = "killeffect_crackling_ice";
      var0[74] = "killeffect_draculas_flight";
      var0[75] = "killeffect_egg_theft";
      var0[76] = "killeffect_final_smash";
      var0[77] = "killeffect_fire_breath";
      var0[78] = "killeffect_firework";
      var0[79] = "killeffect_frozen_in_time";
      var0[80] = "killeffect_gift_explosion";
      var0[81] = "killeffect_golemyeet";
      var0[82] = "killeffect_guardian_rocket";
      var0[83] = "killeffect_hatching_eggs";
      var0[84] = "killeffect_haunted";
      var0[85] = "killeffect_head_rocket";
      var0[86] = "killeffect_heart_aura";
      var0[87] = "killeffect_heartbeat";
      var0[88] = "killeffect_holiday_fireworks";
      var0[89] = "killeffect_holiday_tree";
      var0[90] = "killeffect_jack_o_twister";
      var0[91] = "killeffect_kill_counter_holo";
      var0[92] = "killeffect_lantern_spiral";
      var0[93] = "killeffect_lighting_strike";
      var0[94] = "killeffect_lightning_strike";
      var0[95] = "killeffect_lit";
      var0[96] = "killeffect_magnolia";
      var0[97] = "killeffect_none";
      var0[98] = "killeffect_pedestal";
      var0[99] = "killeffect_petal_gust";
      var0[100] = "killeffect_pigsmash";
      var0[101] = "killeffect_pi\u00f1ata";
      var0[102] = "killeffect_present_rain";
      var0[103] = "killeffect_pumpkin_popper";
      var0[104] = "killeffect_pumpkin_rocket";
      var0[105] = "killeffect_rain_on_my_parade";
      var0[106] = "killeffect_rainbow";
      var0[107] = "killeffect_raining_eggs";
      var0[108] = "killeffect_raining_gold";
      var0[109] = "killeffect_rekt";
      var0[110] = "killeffect_ring_of_fire";
      var0[111] = "killeffect_rising_dragon";
      var0[112] = "killeffect_shattered";
      var0[113] = "killeffect_shockwave";
      var0[114] = "killeffect_skeletalremains";
      var0[115] = "killeffect_smiley";
      var0[116] = "killeffect_snow_globe";
      var0[117] = "killeffect_snowplosion";
      var0[118] = "killeffect_soul_ripper";
      var0[119] = "killeffect_spirit";
      var0[120] = "killeffect_squid_missile";
      var0[121] = "killeffect_team_destroy";
      var0[122] = "killeffect_tnt";
      var0[123] = "killeffect_tornado";
      var0[124] = "killeffect_wing_gusts";
      var0[125] = "killeffect_witch_ritual";
      var0[126] = "killeffect_xp_orb";
      var0[127] = "beddestroy_bedbugs";
      var0[128] = "beddestroy_blizzard";
      var0[129] = "beddestroy_burned_up";
      var0[130] = "beddestroy_egg_popper";
      var0[131] = "beddestroy_eggsplosion";
      var0[132] = "beddestroy_firework";
      var0[133] = "beddestroy_fishy";
      var0[134] = "beddestroy_ghosts";
      var0[135] = "beddestroy_glyph";
      var0[136] = "beddestroy_ladybug";
      var0[137] = "beddestroy_lava_explosion";
      var0[138] = "beddestroy_lighting_strike";
      var0[139] = "beddestroy_none";
      var0[140] = "beddestroy_pig_missile";
      var0[141] = "beddestroy_pigsplosion";
      var0[142] = "beddestroy_present";
      var0[143] = "beddestroy_pumpkin_explosion";
      var0[144] = "beddestroy_rocket";
      var0[145] = "beddestroy_shattering_ice_bed";
      var0[146] = "beddestroy_squid_missile";
      var0[147] = "beddestroy_stormy";
      var0[148] = "beddestroy_thief";
      var0[149] = "beddestroy_tornado";
      var0[150] = "beddestroy_uncraft";
      var0[151] = "beddestroy_water_spout";
      COSMETIC_KEYWORDS = var0;
      KILL_MESSAGE_SAMPLES.put("killmessages_to_the_moon", new String[]{
         "FrostyCookies was crushed into moon dust by uwu_x3.", "FrostyCookies was sent the wrong way by uwu_x3.", "FrostyCookies was hit by an asteroid from uwu_x3.", "FrostyCookies was blasted to the moon by uwu_x3.", "FrostyCookies was blown up by uwu_x3's Dream Defender.", "Blue Bed was blasted to dust by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_pirate", new String[]{
         "FrostyCookies be sent to Davy Jones' locker by uwu_x3.", "FrostyCookies be cannonballed to death by uwu_x3.", "FrostyCookies be shot and killed by uwu_x3.", "FrostyCookies be killed with magic by uwu_x3.", "FrostyCookies be killed with metal by uwu_x3's Dream Defender.", "Blue Bed be shot with cannon by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_celebratory", new String[]{"FrostyCookies was whacked with a party balloon by uwu_x3.", "FrostyCookies was popped into the void by uwu_x3.", "FrostyCookies was shot with a roman candle by uwu_x3.", "FrostyCookies was launched like a firework by uwu_x3.", "FrostyCookies was lit up by uwu_x3's Dream Defender.", "Blue Bed exploded from a firework by uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_dramatic", new String[]{
         "FrostyCookies was tragically backstabbed by uwu_x3.", "FrostyCookies was heartlessly let go by uwu_x3.", "FrostyCookies's heart was pierced by uwu_x3.", "FrostyCookies was delivered into nothingness by uwu_x3.", "FrostyCookies was dismembered by uwu_x3's Dream Defender.", "Blue Bed was dreadfully corrupted by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_oink", new String[]{"FrostyCookies was oinked by uwu_x3.", "FrostyCookies slipped into void for uwu_x3.", "FrostyCookies got attacked by a carrot from uwu_x3.", "FrostyCookies was distracted by a piglet from uwu_x3.", "FrostyCookies was oinked by uwu_x3's Dream Defender.", "Blue Bed was gulped by uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_limbo", new String[]{"FrostyCookies was sent to limbo by uwu_x3.", "FrostyCookies was pushed into limbo by uwu_x3.", "FrostyCookies was shot into limbo by uwu_x3.", "FrostyCookies was launched into limbo by uwu_x3's Dream Defender.", "Blue Bed was sent to limbo by uwu_x3."});
      KILL_MESSAGE_SAMPLES.put("killmessages_multiverse", new String[]{"FrostyCookies was distorted by uwu_x3.", "FrostyCookies was thrown into the singularity by uwu_x3.", "FrostyCookies was shot into another dimension by uwu_x3.", "FrostyCookies was thrown into a black hole by uwu_x3.", "FrostyCookies was launched into a wormhole by uwu_x3's Dream Defender.", "Blue Bed was sucked into a black hole by uwu_x3."});
      KILL_MESSAGE_SAMPLES.put("killmessages_default", new String[]{"FrostyCookies was killed by uwu_x3.", "FrostyCookies was knocked into the void by uwu_x3.", "FrostyCookies was shot by uwu_x3.", "Blue Bed was destroyed by uwu_x3!", "FrostyCookies was killed by uwu_x3's Dream Defender.", "FrostyCookies was killed by uwu_x3's Bedbug."});
      KILL_MESSAGE_SAMPLES.put("killmessages_primal", new String[]{
         "FrostyCookies was hunted down by uwu_x3.", "FrostyCookies stumbled on a trap set by uwu_x3.", "FrostyCookies got skewered by uwu_x3.", "FrostyCookies was thrown into a volcano by uwu_x3.", "FrostyCookies was mauled by uwu_x3's Dream Defender.", "Blue Bed was sacrificed by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_old_man", new String[]{"FrostyCookies was yelled at by uwu_x3.", "FrostyCookies was thrown off the lawn by uwu_x3.", "FrostyCookies was accidentally spit on by uwu_x3.", "FrostyCookies slipped on the fake teeth of uwu_x3.", "FrostyCookies was chased away by uwu_x3's Dream Defender.", "Blue Bed was sold in a garage sale by uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_santa_workshop", new String[]{
         "FrostyCookies was wrapped into a gift by uwu_x3.", "FrostyCookies hit the hard-wood floor because of uwu_x3.", "FrostyCookies was put on the naughty list by uwu_x3.", "FrostyCookies was pushed down a slope by uwu_x3.", "FrostyCookies was turned to gingerbread by uwu_x3's Dream Defender.", "Blue Bed was traded in for milk and cookies by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_spooky", new String[]{"FrostyCookies was spooked by uwu_x3.", "FrostyCookies was spooked off the map by uwu_x3.", "FrostyCookies was remotely spooked by uwu_x3.", "FrostyCookies was totally spooked by uwu_x3.", "FrostyCookies was spooked by uwu_x3's Dream Defender.", "Blue Bed was spooked by uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_love", new String[]{
         "FrostyCookies was given the cold shoulder by uwu_x3.", "FrostyCookies was hit off by a love bomb from uwu_x3.", "FrostyCookies was struck with Cupid's arrow by uwu_x3.", "FrostyCookies was out of the league of uwu_x3.", "FrostyCookies was no match for uwu_x3's Dream Defender.", "Blue Bed was dismantled by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_squeak", new String[]{"FrostyCookies was chewed up by uwu_x3.", "FrostyCookies was scared into the void by uwu_x3.", "FrostyCookies stepped in a mouse trap placed by uwu_x3.", "FrostyCookies was distracted by a rat dragging pizza from uwu_x3.", "FrostyCookies squeaked around with uwu_x3's Dream Defender.", "Blue Bed was squeaked apart by uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_social_distancing", new String[]{"FrostyCookies was too shy to meet uwu_x3.", "FrostyCookies didn't distance themselves properly from uwu_x3.", "FrostyCookies was coughed at by uwu_x3.", "FrostyCookies tripped while trying to run away from uwu_x3.", "FrostyCookies got too close to uwu_x3's Dream Defender.", "Blue Bed was contaminated by uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_honourable", new String[]{
         "FrostyCookies died in close combat to uwu_x3.", "FrostyCookies fought to the edge with uwu_x3.", "FrostyCookies fell to the great marksmanship of uwu_x3.", "FrostyCookies stumbled off a ledge with help by uwu_x3.", "FrostyCookies tangoed with uwu_x3's Dream Defender.", "Blue Bed had to raise the white flag to uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_eggy", new String[]{"FrostyCookies was painted pretty by uwu_x3.", "FrostyCookies was deviled into the void by uwu_x3.", "FrostyCookies slipped into a pan placed by uwu_x3.", "FrostyCookies was flipped off the edge by uwu_x3.", "FrostyCookies was made sunny side up by uwu_x3's Dream Defender.", "Blue Bed was scrambled by uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_memed", new String[]{"FrostyCookies got rekt by uwu_x3.", "FrostyCookies took the L to uwu_x3.", "FrostyCookies got smacked by uwu_x3.", "FrostyCookies got roasted by uwu_x3.", "FrostyCookies got bamboozled by uwu_x3's Dream Defender.", "Blue Bed got memed by uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_oxed", new String[]{
         "FrostyCookies was trampled by uwu_x3.", "FrostyCookies was back kicked into the void by uwu_x3.", "FrostyCookies was impaled from a distance by uwu_x3.", "FrostyCookies was headbutted off a cliff by uwu_x3.", "FrostyCookies was trampled by uwu_x3's Dream Defender.", "Blue Bed was impaled by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_counter", new String[]{
         "FrostyCookies was bested by uwu_x3.", "FrostyCookies was uwu_x3's final #10,000.", "FrostyCookies was knocked into the void by uwu_x3.", "FrostyCookies was shot by uwu_x3.", "FrostyCookies was knocked off an edge by uwu_x3.", "FrostyCookies was bested by uwu_x3's Dream Defender.", "Blue Bed was bed #10,000 destroyed by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_buzz", new String[]{"FrostyCookies was buzzed to death by uwu_x3.", "FrostyCookies was bzzz'd into the void by uwu_x3.", "FrostyCookies was startled by uwu_x3.", "FrostyCookies was stung off the edge by uwu_x3.", "FrostyCookies was bee'd by uwu_x3's Dream Defender.", "Blue Bed was stung by uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_bbq", new String[]{"FrostyCookies was glazed in BBQ sauce by uwu_x3.", "FrostyCookies slipped in BBQ sauce off the edge spilled by uwu_x3.", "FrostyCookies was thrown chili powder at by uwu_x3.", "FrostyCookies was not spicy enough for uwu_x3.", "FrostyCookies was sliced up by uwu_x3's Dream Defender.", "Blue Bed was deep fried by uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_glorious", new String[]{
         "FrostyCookies was stomped by uwu_x3.",
         "FrostyCookies was uwu_x3's final #10,000.",
         "FrostyCookies was thrown down a pit by uwu_x3.",
         "FrostyCookies was shot by uwu_x3.",
         "FrostyCookies was thrown to the ground by uwu_x3.",
         "FrostyCookies was outclassed by uwu_x3's Dream Defender.",
         "Blue Bed was bed #10,000 destroyed by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_western", new String[]{
         "FrostyCookies was filled full of lead by uwu_x3.", "FrostyCookies met their end by uwu_x3.", "FrostyCookies was killed with dynamite by uwu_x3.", "FrostyCookies lost a drinking contest with uwu_x3.", "FrostyCookies lost the draw to uwu_x3's Dream Defender.", "Blue Bed was iced by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_bridging_for_dummies", new String[]{"FrostyCookies had a small brain moment while fighting uwu_x3.", "FrostyCookies was not able to block clutch against uwu_x3.", "FrostyCookies got 360 no-scoped by uwu_x3.", "FrostyCookies forgot how many blocks they had left while fighting uwu_x3.", "FrostyCookies got absolutely destroyed by uwu_x3's Dream Defender.", "Blue Bed has left the game after seeing uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_snow_storm", new String[]{
         "FrostyCookies was locked outside during a snow storm by uwu_x3.", "FrostyCookies was pushed into a snowbank by uwu_x3.", "FrostyCookies was hit with a snowball from uwu_x3.", "FrostyCookies was shoved down an icy slope by uwu_x3.", "FrostyCookies got snowed in by uwu_x3's Dream Defender.", "Blue Bed was made into a snowman by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_fire", new String[]{"FrostyCookies was struck down by uwu_x3.", "FrostyCookies was turned to dust by uwu_x3.", "FrostyCookies was melted by uwu_x3.", "FrostyCookies was turned to ash by uwu_x3.", "FrostyCookies was fried by uwu_x3's Dream Defender.", "Blue Bed was incinerated by uwu_x3!"});
      KILL_MESSAGE_SAMPLES.put("killmessages_festive", new String[]{
         "FrostyCookies was smothered in holiday cheer by uwu_x3.", "FrostyCookies was banished into the ether by uwu_x3's holiday spirit.", "FrostyCookies was sniped by a missile of festivity by uwu_x3.", "FrostyCookies was pushed by uwu_x3's holiday spirit.", "FrostyCookies was sung holiday tunes to by uwu_x3's Dream Defender.", "Blue Bed was melted by uwu_x3's holiday spirit!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_wrapped_up", new String[]{
         "FrostyCookies was wrapped up by uwu_x3.", "FrostyCookies was tied into a bow by uwu_x3.", "FrostyCookies was glued up by uwu_x3.", "FrostyCookies tripped over a present placed by uwu_x3.", "FrostyCookies was taped together by uwu_x3's Dream Defender.", "Blue Bed was stuffed with tissue paper by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_roar", new String[]{"FrostyCookies was ripped to shreds by uwu_x3.", "FrostyCookies was charged by uwu_x3.", "FrostyCookies was pounced on by uwu_x3.", "FrostyCookies was ripped and thrown by uwu_x3.", "FrostyCookies was ripped to shreds by uwu_x3's Dream Defender.", "Blue Bed was ripped to shreds by uwu_x3."});
      KILL_MESSAGE_SAMPLES.put("killmessages_noble", new String[]{
         "FrostyCookies was crushed by uwu_x3.",
         "FrostyCookies was uwu_x3's final #10,000.",
         "FrostyCookies was dominated by uwu_x3.",
         "FrostyCookies was assassinated by uwu_x3.",
         "FrostyCookies was thrown off their high horse by uwu_x3.",
         "FrostyCookies was degraded by uwu_x3's Dream Defender.",
         "Blue Bed was bed #10,000 destroyed by uwu_x3!"
      });
      KILL_MESSAGE_SAMPLES.put("killmessages_woof_woof", new String[]{"FrostyCookies was bitten by uwu_x3.", "FrostyCookies howled into the void for uwu_x3.", "FrostyCookies caught the ball thrown by uwu_x3.", "FrostyCookies was distracted by a puppy placed by uwu_x3.", "FrostyCookies played too rough with uwu_x3's Dream Defender.", "Blue Bed was ripped apart by uwu_x3!"});
      String[] var10002 = new String[227];
      var10002[0] = "4c7b0468044bfecacc43d00a3a69335a834b73937688292c20d3988cae58248d";
      var10002[1] = "3b60a1f6d562f52aaebbf1434f1de147933a3affe0e764fa49ea057536623cd3";
      var10002[2] = "19875bb4ac8e7e68c122fdf22bf99abeb4326b96c58ec21d4c5b64cc7a12a5";
      var10002[3] = "dd2f967eee43908cda7854df9eb7263637573fd10e498dcdf5d60e9ebc80a1e5";
      var10002[4] = "21c44f6b47eadd6720ddc1a14dc4502bd6ccee6542efb74e2b07adb65479cc5";
      var10002[5] = "7162726e3b3a7f9c515749a18723ee4439dadd26c0d60e07dea0f2267c6f40a7";
      var10002[6] = "10e62bc629872c7d91c2f2edb9643b7455e7238a8c9b4074f1c5312ef162ba22";
      var10002[7] = "4336ff82b3d2d7b9081fec5adec2943329531c605b657c11b35231c13a0b8571";
      var10002[8] = "173ec57a878e2b5b0922e34be6acac108372f34dace9871a894fe15ed8";
      var10002[9] = "7f73526b1a9379be41301cfb74c55270186fbaca63df6949ce3d626e79304d92";
      var10002[10] = "7d91aee3b51f3f8d92df52575e5755d97977dcdfb38e74488c613411829e32";
      var10002[11] = "8e42e588e1d09ce03c79463e94a7664304f688caf4c617dbcbca64a635bbe79";
      var10002[12] = "8f1f9b3919c879f4ec251871c19b20725bc76d657762b5ddfdf3a5ff4f82cb47";
      var10002[13] = "989bc66d66ff0513507bcb7aa27f4e7e2af24337c4e7c4786c4630839966fdf7";
      var10002[14] = "bdfc818d40b635bcd3d2e3d3b977651d0da0eea87f13847b99dc7bea3338b";
      var10002[15] = "5841684ec6a1f8ba919046857dac9175514fef18a2c9395dc3e341b9f5e778ac";
      var10002[16] = "211e121448c83125c945cd238dc4f4c5e9a320df5ee3c8c9ad3bb80c055db876";
      var10002[17] = "3cce5c4d27979e98afea0d43acca8ebddc7e74a4e62480486e62ee3512";
      var10002[18] = "68d98f9a56d4c0ab805c6805756171f4a2cdbf5fa8ce052a4bf4f86411fb080";
      var10002[19] = "e2629467cf544e79ed148d3d3842876f2d8841898b4b2e6f60dfc1e02f1179f3";
      var10002[20] = "6162abdfb5c7ace7d2caaabdc5d4fdfc32fb63f2a56db69f276167dffce41";
      var10002[21] = "af336a55d17916836ce0ed102cbdb0fa6376544971301e0f28beb3899c649ff2";
      var10002[22] = "1580729b12e1d6357e7eaa088fbf06ba2c8a2fb35b0ba4c5451937d3f134ca9";
      var10002[23] = "1f72e604cdb4c49f7106b594ac87eff3ed6a1999255437241f57d28c45d103f";
      var10002[24] = "542a699fe314b9f747eed89b9cae23fdefc27684f6c13dc4a29f5d057cc12d";
      var10002[25] = "b2a4cd136505395a876113a800aa8ec15761ca3d945b57e0d0dcdcfeafd7a6d9";
      var10002[26] = "907fcce8c3a8d337a6aff226847e3cc7c9bb6bd02f43be1b7c71b3dcd244e11";
      var10002[27] = "62a6c3e6b8cbd4fbcb5866289642bb5b5a90dd16e2c28dc054c9d248943834f9";
      var10002[28] = "173481eb7f2157a5ad79ec765d36be5736395b72ee519185e23f436ba15185";
      var10002[29] = "9ad4ffb57819e7ff633f60b5901e44a3570573ad4d453075b72ae2cbd23c8a6d";
      var10002[30] = "8c064476ed9de9ca437cf869127c61a945ea6c308e9b25e4a991bb252c6d754d";
      var10002[31] = "9ddd647a59a93c23ce49cece35f7529985ee40d0ca7ead6a1e3fe0f97b286162";
      var10002[32] = "c56ab25347aa70f406a85d221da104c5ff05d2a1866a1b57dc1ab4f5feb97";
      var10002[33] = "7dcb1d264010bfac568d19e9baee3c2a2aaa729d6cf335b9cf62d2fb2f4c813";
      var10002[34] = "fd22ca2c137a6ecf6a4366eb1f2c8a6b173220b295abc1ae13cedf93dabdbf3c";
      var10002[35] = "7245bc1d62123b6f8c954cf08be76c9c0d23e778ca9843935a24782c8b2bab";
      var10002[36] = "721d9bc16854e75bad69fc7529e3f4c82f32a4715f219697f413c67115a93";
      var10002[37] = "e1aa418bd0b4f4d37d6853b7c577eac34034d2f64b6415ff653132f4ca66cd7";
      var10002[38] = "c2159fdfe7ef9f12269e2791ebc5aca8e787506b28bfc69747ccf12671261afb";
      var10002[39] = "8362ff7077a326747210c56031dc46a141f25454b27873395eda6483d55df";
      var10002[40] = "6845756829b6bca516b5bf9251ae31c79cd6ddbc3c57f119370b0ccd8d6f5a1";
      var10002[41] = "b77b40e51c7562e523efb0c0f94a616da97c1f485fd8b4a4ac9fb37561812";
      var10002[42] = "48b34cc77a18dfa0ebb54f93c3c31779769f519f41b5153c1869aedec9965b2";
      var10002[43] = "44d65b5b742333fa051b81b8365155618d4231becd9393283ec639b2f12b7f93";
      var10002[44] = "dff36d1281f862f8841d2b84ce17c560e45cd0b3fd879c78c13d26b7c7f7cbfe";
      var10002[45] = "1cdeb260f0b31796aca16be0c79ea0169b6f6542fef743c09c4238ad2114a49f";
      var10002[46] = "1ab96446ecc368e4c685239fb6d14f7adbf337fb343da95285eec68dd79c4a";
      var10002[47] = "7e9437c77b2529da8dbb0545f2898e9a2d12e667f8e6f69f051ced32acfde";
      var10002[48] = "b5f0d648162c98c6ee9cb31c4e5cead456dd105a37f7ce8a7a09d384a47b8b2";
      var10002[49] = "7f9712869fb1ffbd4905342aca6b7a4f5e47ee9ea7dae5e752c7b9e9bbcea";
      var10002[50] = "5485ef7d262e19a65756ec94338777f93b16f64da2783189d0ee34b816b357";
      var10002[51] = "869b276cbe44c4a5918beb106e625ae36f829e7c7bcdfad8b67565f48430b199";
      var10002[52] = "243ad02c2f5bf1a4b8225a1f6243d507e707fda68237f2aa738467c956be10";
      var10002[53] = "dd80f354e47a8b66bddb43ff38d972487aa1105d1eebdb7a26844c140d888e0";
      var10002[54] = "32aba3b2955a782f0a3a37bfa9c4173919bdc4827c99bee5670169ec4e181dc";
      var10002[55] = "c9939c9d2d9e5e5fb689b12d89e9edf08c915866b7661545fe46e88ab1551";
      var10002[56] = "c61aec3d73110435b3c549f7bb70d4aa6d6e6c404590b34be63e6ab42c2946";
      var10002[57] = "c96522d14a7a21f59fc2c66ef82fbe62263c9d7d064f823b7c1a614e409099";
      var10002[58] = "5a75720a749dca3fee845d6d7e9b2234542f1a2d7d948f040c5ca3e493f5e4";
      var10002[59] = "4a213a331b92693ec8f534f627803ac8492df0916b70a762e28aff6a5d8ea2";
      var10002[60] = "c149f0fe3a696fad6fd6ad7858ff788b6d15129207b5f72b0d7d7f983e1b";
      var10002[61] = "6c175165d75062a323c5865916162ce7eca5e6ac224440c8b0536c96530d33a";
      var10002[62] = "acf665de64faf18cbfd1d13598fd4552566c878fbc3716f52587e2cbae44b9";
      var10002[63] = "ff167fcbb98ecd6377905add5c15459e3fb815a0b7c9142e8037a818945630e";
      var10002[64] = "3e8a56d37decce24f73e3e97e67812a2f5a1384a525f4c0e58cd3fdeffc38";
      var10002[65] = "4d6e309a40b631ec6cb5be74e22b883da452f43acf4e834f43c1cb25c8f82a";
      var10002[66] = "7417341979bb41714df892d4994d63347006be8bd7f2cfb65b826d2b21172";
      var10002[67] = "fe74c2edd110608e523ce6b6b31f528cb38a122941ede2d68d61ba52bd6802e";
      var10002[68] = "04426382d98452d90ef2cdb492af67853ca8542972595351acc6cbf88f51532";
      var10002[69] = "c35dd24dc529b664504bfd3315b3fe8ca9c6c9b9fe1e84ce6aea216ef7bd3";
      var10002[70] = "d2498d91a41fdb77f58c9da73073cc3f287b93ee12ccbfa6473d55019454b1d";
      var10002[71] = "3fca4000b6c9b5c7d57f29245bb9ee00af282e351d697a44031d15a1384eb3e8";
      var10002[72] = "f3e399b37b4fba7d2fd0ed14f8a6820131d6c9a355282704c59796f092677542";
      var10002[73] = "1561ee2ea67346c667b4e96d85847b7b51372b605fe34ae046c8c0d0d2973d";
      var10002[74] = "40f836d124597ab614e97ab9bae81bcfacfb9a5cb87b8ce9fc50e5ab93c53dae";
      var10002[75] = "52ff1cf6537438f4aff7c2cdcdc84cca8f42f9aa264913827981ace5876f71";
      var10002[76] = "8983c344fe3bb69d16caa51766a5bf371ec9075496a061334db9f9a44711";
      var10002[77] = "76acbc4d98a2deab2b2b8e7798d5b9ae54e1d5710c9b0e93c243461405d4519";
      var10002[78] = "b4c815e8e24ecc26ce18a35a938a5d8b6f96e9c8467841be37699d83e43d";
      var10002[79] = "16b5b6a89aa283548411eef311b2ab46216a7b0538452b249386895b917cf2";
      var10002[80] = "5ff1fd2453f6d85961de8976bce16c5d514e38ad7f846e99317ca4a3b5889";
      var10002[81] = "1b144c7532ec233ffb5c52e69e3d98be12d52481fd5113703f21e1ec850b6";
      var10002[82] = "64316ffd3ede6158b3eb421b97cbf9b82dd93e08d54b3f9f0105c75f134f89c";
      var10002[83] = "b6966498a988780cd1dcf4ea059eeec6497fe14b95c32470bc6d99b17ff1";
      var10002[84] = "b9ec71e4727fde6edef0d08f25561c4ba6eb21583fd9fae443e566c79d88e160";
      var10002[85] = "3385c509e1b649552c750dc23c344d24d158df94f6d8532377a9b726462bc05b";
      var10002[86] = "59f87bef785eddc72f2289b8482375266bc3ceed365c270c5ef7f835df39";
      var10002[87] = "27d4d629ac756da03d894556535bcca033fdaf172e69cc772262b43918ede351";
      var10002[88] = "af43feeb32559878e0561c87f8f35c9812973bf27661d874bf68bb569b333f45";
      var10002[89] = "ef1f3805eb46853b22559404b373c54b12453c4882abf3dd7673f5869be4cd";
      var10002[90] = "7265757c8a5a826f9e2b68e4631fee33e74dcbfcd9e4c744360186f4ff58fa1";
      var10002[91] = "3bf9314d6f78711c93d895519bc620a8176819551dc1d498aea840f32cf0d917";
      var10002[92] = "ae97b72b9972d5db2516ceda54c6837116c2c52e75763749de9949aaab95d0";
      var10002[93] = "6512d4661323db375b829bf2e090f7c3a277f95d3a5613ae59a06d9a9a270";
      var10002[94] = "bf948ef3d865729d4120179087c0323a4cb913119932aa620dd9accef7d528a";
      var10002[95] = "bb9688ec3a8fb8f18887377bd5be94a56fafb267d870c0532c356cc35adc";
      var10002[96] = "4097a9b1113fa753d37d613ab9e118f0d05d3f5276f965f6466bb25d313a0a9";
      var10002[97] = "40952ce63957766d68819e9e033429db2f9a472b3646d856e8b839088de699f";
      var10002[98] = "59c13c5c833d4205fb899fe6a329f136c5c67ce0dd86efb834684686ead2d";
      var10002[99] = "762b16ce467a4096e188f9c12351e66fce8ef1e18b6e9788befe4666c68876";
      var10002[100] = "80516a7b5faf2cc796650c51c167773ba8c8e73e94b10d96d3e9d827dd63c5";
      var10002[101] = "5a956dc2631e54a3cb62d31390226b5cc052432fc7b9261da4bf6420f8d7e8";
      var10002[102] = "156c183d1064d9d72e25de3945ef16483c15eb06b6c87396ab4ba1f8e9b6df";
      var10002[103] = "1d387e3e5b89925ce6519cfb4378af11abed6e4b7ae3491f93048971a2e80e7";
      var10002[104] = "8fcdcc72bfd5192d752d1a5eac7c11115c9aa43d574dfa85f99f2896c9b15";
      var10002[105] = "b6557aa1aaf5fe97745da389ab69473ef9f5ec31960be2860a5c8bd6ed37";
      var10002[106] = "a191322292ab595ff53c704b85f514f5b9f45470332ac2719eb85e92df4023";
      var10002[107] = "93e15c711b3a37d5634a1b629cb9e43f793f297dd3369c2bdd9b4bba80fa6b";
      var10002[108] = "489fd1a12c42e0fed383e9d23bacb95815fb752213849ddaa8b5893ac7eba24";
      var10002[109] = "fbd1fa49884dacaed4cc4650d23bfea4dc7a89dce8d90a2e27acfb712e8f8";
      var10002[110] = "daa35aa45c2d7092e359962e79d11842ed18ce499aacff22c5871662f7a69dd";
      var10002[111] = "447374bbeadeaa36684d3f68eb46bed5b7d145a206d5a54b9c12382d6b1f9dce";
      var10002[112] = "9c8f4d6466382820536e82842a162615c2e7d2316afc59264a9c3ede";
      var10002[113] = "997eb6a7b37bc8924ed341a4a0a356112b620bbc121b5ce27e692a535d2df81";
      var10002[114] = "adc9c2fd56f6698f5807012e4dd2e785e5efe1e6799b47cd1c3bdb1c05eda3c";
      var10002[115] = "2d13cdd15b5673a27a63c04226e3b2b3639ac27fb853d1a146a239496da1ff";
      var10002[116] = "238580ddf446509b4c84e829b39a8b2f72ab8cd649dca6886405dd2ad2dcd5";
      var10002[117] = "241c4fdecb52afd81b24993b5a7e6c715f375d94f4eafab39a60bb2b5050e9";
      var10002[118] = "d2447fe1ee25c2476525e78aa71bd2f56bdec3b5f829215650642563698d272";
      var10002[119] = "f515cc3ace7a74afe76e41c117dace9f278b63c1829d30b246bd7d73584cf2";
      var10002[120] = "9691b4eada776b725eb2a5fdda77af65b87ccfde6ccb76c75fbe4da347723";
      var10002[121] = "344f6ae0fa81aadf8a2197e656cb8e696d18f12e2a87ab42c41b64b61c688";
      var10002[122] = "6b76f913d7c02718c7cf9a8087db3dc246d9eb89febefd28abfd59226559548";
      var10002[123] = "eed8565e62768584c3a933227e1747165dd86e3942a93f52e4285ba65cd2662";
      var10002[124] = "8fee25beac53be9a196b5319e9887eaff50cb19a61ad5d88bb57fe431c8a8";
      var10002[125] = "e32379103e70b548a716a1c8d477b611c44c6d1925e978f1164362f22564b9";
      var10002[126] = "c8424766a87919285dd9f92e4c8868de558d87c739d76668b532f7c21a490";
      var10002[127] = "ad369862824c34b9354edc9cb14832364dc74cf867863210c1a83dc3ebcd22c3";
      var10002[128] = "a17d731aef41184853bafa292fa9f46c6c56912cdd1bb4cd43e53c88ba82cc6";
      var10002[129] = "ff642bf35a3e622515bb1f20356534fc3f24316b3eb170f477f336ec26edf9d6";
      var10002[130] = "dd222e15fa6d522d9bc3a674d1a9327bbee733b396246e40af1fce716bef9b";
      var10002[131] = "7fd6bf7bc58c661dbef8b8896eb2ccd31229a3e8b09e54d769c1e46242348277";
      var10002[132] = "4e5223efd125cba238eea12334f9e856718842281d7f865db3c6d577ed5e084";
      var10002[133] = "689026f3bf84461b773a3e7915da121dde4a3371598a8431cd5e8951ea549";
      var10002[134] = "80c86c1d62b71928d6251ef238434e226fa9fe3041964625d8a0e550d8f528";
      var10002[135] = "d275c5282d3ce248ecec75b82a44c2e70497c76565c993618316ca12a1efb8a";
      var10002[136] = "4c232f62387b2ac54c371beaaa0577fe7778a3c6954b34914c3e016b84f6f46";
      var10002[137] = "2ecf8d5923446aba9eeef5a24848b84b20bbc37c80ca62e9e664375c24dc7077";
      var10002[138] = "d5fab8a6fc9ec343f7ccecd86a0f5cc339d11a02c8fb0ba1ed5cb446d01ae0";
      var10002[139] = "ddeb44d8f85ee1b918dcd231eb62887e2e2df63df8c91d11461111f710c9f2ac";
      var10002[140] = "c25c40b36da47d6a982c86a319d6fe5e4916df411fbbf656451bd6049d6d179";
      var10002[141] = "5fe8b3813cf0c55cdf2c97789735d62085767323cd9af8da804669592ac45f";
      var10002[142] = "7cbe75103d02c10dd410fc5760139212fbeda8d91ee752e53e4674aecfa30";
      var10002[143] = "35873a599e65596b99080d86f5b5ecfe162a8235a136bb4990fb8e2325965b";
      var10002[144] = "f813e90a33dce8bca6a6c688683706498e1f2aaec8530c481c2a80faa82ddebc";
      var10002[145] = "99a5daa3fe44c414ca8f4324c36bced2afe6d962a580842d8a36c5d9c1b8be0";
      var10002[146] = "672d94becd9f61dec864a8232692fb1c54ff4676ade457ae6847f7d9d954a8d";
      var10002[147] = "b0a74cd03493521b451cbf256775e93809a203672e837e0eb46c590e5f0928f";
      var10002[148] = "f3a01fd5a6267170ff7c6526d3a9ee4ee4403e036955f902c9414c064d9449be";
      var10002[149] = "bfd4e3b0527bd94824e530968f2281ce8e3dd9a3f6b73fb23e6cc15b7c79d2c";
      var10002[150] = "4fda29ceab6ca457da30882493c5129287277895ebd3f244456126666a6";
      var10002[151] = "79b7861ac71a511be1b88823549fbbbbc8a300dccc0e873322ea2a7859d686cc";
      var10002[152] = "3debc1619533fe5f011783e43e526efad44bf49ace9e5ca10badd7f55e069";
      var10002[153] = "dbc1c832b4919315df977aedc7ece84d9aabef8823cc9de5ceee5129b1728";
      var10002[154] = "7a27c712e9446026aa38afd114d76cef0b96bdbfe58adbf73a9a9149684eac3";
      var10002[155] = "b78e4089143163c32291d6365e715e1b42806a40784ff93b8737947c7687e9";
      var10002[156] = "3a3d66c09223d0899b896487505fda388584941ec946534d2a9e277133fe02e";
      var10002[157] = "1ae5861b10c43426dc4345aa49585d818886a1fa4599c8ada0b2fcbf69b81";
      var10002[158] = "a7924326aca6415f34b017573996f333ad1c8db9bf46d4fa216493faafbc9fd";
      var10002[159] = "62cc348490c1d2bf32daed40eae64bf812c3ba23abda3653a1335af6f2123b";
      var10002[160] = "c9b2b946ab9aebc76084157641aceed34e83185d958fc2f225c2595c8171d6";
      var10002[161] = "81895e92fc1552fe1dcd531f6775e1266146b75812c58795a1b0a3bef922b79f";
      var10002[162] = "8569245371edff63a26913a972f2c44fbe41f75e42668a72599759cf452f3a";
      var10002[163] = "2470257f2b1ea354f4a1c5e0e1ee207d162f669a83e440a913cd87f58f52ffd9";
      var10002[164] = "1670d1f3de92402e51e780b2e6699dabbc79fb44dca1c273f5cbde64258aa";
      var10002[165] = "dc3938ebca030fe937c77b9876d456e19e9b6588f3ee8ec993bace98ccae4e";
      var10002[166] = "1ad471136cdf5e9dec7b30c841f2b9af1aab09b9f369fa24aa52a98f4b0afe1";
      var10002[167] = "c828ea469bef8c84eee8e9abf66788c7991789d8f3a21ab460789a0b14884";
      var10002[168] = "16c63d6591c8e7e3a1b8825e43bb8dc8ac561ab567a9cda57cf783031e0";
      var10002[169] = "62604b8b7df3da89a0a85ed8152073da59f31774cdcfec66951edd572a72957";
      var10002[170] = "4b28dc744eb31957b74a14bcfc2320b451f5b6859a158fd8bab28dd873f71b";
      var10002[171] = "3d4a203011d23899e4f02065324b4c9e97228f2ab2cef1eb366c8edbaad26d";
      var10002[172] = "b6ed84678adf2445348963fc343a6f44e8631a1d7fd25f377d824e9bde37e";
      var10002[173] = "34f889c53b26a7b68569a51be9a5d17d3fca371df6dc615183e2b437530";
      var10002[174] = "b7d33aa3cf94603b6bbaccdda885c33bdba3baf6f866b9c1dc64816cf47e7fa";
      var10002[175] = "bd2730d152b782f5e6f26e5d22f49d3da81ada8cb193fecd37189a72b9a7c";
      var10002[176] = "49e3bd98686d1146b9337c9652899a12f28fe59e42f123578e8981652510e5";
      var10002[177] = "4c4d84cc6798da26511812353857e7630544ecbb93a7d9a5a44e0a8bddf3";
      var10002[178] = "de915d709d818429c09838c6341ce6c4abf41e450def2dbb4c12adee5745fb";
      var10002[179] = "f1d8494d382feb5e7434daa2fe553b4d6b51269717cd12309d331399efdbbd49";
      var10002[180] = "c41043fb371ce1d6bc2b5e3c38c116177a71aba9c2e9511188f9e15955131";
      var10002[181] = "a5e77eca3a8571b6ea72a2d1d41429323c610d35477191d5191faa9745d772";
      var10002[182] = "f42d8249035be11d50ef8c2a947d1ffe3eb8d91989de5b27748190eb231295";
      var10002[183] = "2e24edaf3df936127c1be1097bc798411719489a1652139ebd6f8dd21fda71b";
      var10002[184] = "14ea486fef4c37c771f23968df9d47358111d3cac57c458c57b6a2c7dc9970";
      var10002[185] = "7dc27a418be63cd1ece8d2daf106c5b369db846c6c141d2bde4d81c83eff";
      var10002[186] = "f911f9329b391d8b6e30d55ab9f20608d620d682c9327514b5ab9d1b7ed360d3";
      var10002[187] = "c0554e6189ce7ba79de273dcac358f2985771d3e3cec7ff8b4c953bf6d5c5";
      var10002[188] = "f60648e541171fb69121794cc8894c3168ba841c897cbb457819172d9df3";
      var10002[189] = "7c5aeae0e15227404b1f1c59c93e9ffce83c7433feb5649ecc2ddc1af6e2c7";
      var10002[190] = "9960335e7981e3c82b03a59bce8aad04b893efc9928e825498199d59627079";
      var10002[191] = "579a5713ed8affbfe8bbcd432def758ec4a31647db4f7dda4df7535a3fa0f58f";
      var10002[192] = "7fb832fa27791731e34a1adaf6f59c1a95e6e5aa46d528f6c2975d668bc1";
      var10002[193] = "cae2c0eb1730e11888e3f4dc133e9c5fd1434beb19b1616b026412fafc8e87";
      var10002[194] = "7ca423e35c767d5844f8bb9a3c949710e8615847e3f6db117591ce53c30f9e1";
      var10002[195] = "cf9de3c33c4b523248fc7dc23f18c551f1d2740cfeb162674aad031852e";
      var10002[196] = "f4254838c33ea227ffca223dddaabfe0b0215f70da649e944477f44370ca6952";
      var10002[197] = "fd41e45153cb159af3d2b3d0e4210969fc4a6402a327c5ab6d1f6981ceae7929";
      var10002[198] = "6437c4b8eba97a3f79a49074d8c9c6c7ededabffe2896161256d426014f52dfc";
      var10002[199] = "8b1ce430410e2416c1c0ae1ad2b91621e128b9c8a46e32dfee9a6e777eab6cf8";
      var10002[200] = "1e1408a4152663ccd1169965eabd5815216913bf9f9374d499ed3160a562cef9";
      var10002[201] = "e814f4ac1be28dd2ee5d6d297265d2efac52c36035a60bd919961914ea226ef";
      var10002[202] = "8958ea6b50dbb139ec56c20fa3d61d4902c30d4ee234d8009180bd9c37f33708";
      var10002[203] = "a7556d263c98a1d3b12236b56f613c516d91c755d4d7555eff0788da9c134f2f";
      var10002[204] = "6e0b2d4281ad9a090037ef0985fc0667e43c4762c083d34daaa6cd05d5e53055";
      var10002[205] = "7ae4fce4ecdfcf9e27e8723061fe43236ec937004cd0e016c7fd924587a34c5d";
      var10002[206] = "adcebc4e9a583d84ee7083b5540b8de8499f9ed4e02619213219c2c6eb081821";
      var10002[207] = "c6e4f799a1019320407a035ee54350b3a507523c7d5625f13b372a7b215402c9";
      var10002[208] = "23c7a66adf72ebe7338b23a34e5bf02827433c805e8b63c2a19ed9617df26b2f";
      var10002[209] = "81280cc515d765b001c9f628a4f488b1a910effd38bc24e0b5aa64252e68068";
      var10002[210] = "2dc567c2c3ee555d22944a5d9238c3d27915f5076f5ced1687471afd0dc605d4";
      var10002[211] = "fb44f9b19f45f1356f9c5c85ff2137018068e50c84ff0b2ae7d9ce0f33629d34";
      var10002[212] = "52b273741996ce877d63a8651e94ba3c55fee196c34fd52b78c0aa06c5fd550";
      var10002[213] = "48cc9961ec54f340f0d35239562d92748e92c6e2d6aa7aac884c414ae949618e";
      var10002[214] = "a8485f852d273ab6223517c0d84c4c13f704ea5e40a7431c5012bb2e86250445";
      var10002[215] = "8fba7e9fa894246022930de1320368d013544d6953f192c62561c1423bb44dd5";
      var10002[216] = "3ef953f0f2d9bdc2d2156952d7aae3fb438216a47108cbf0a0348f26e018049b";
      var10002[217] = "2ab3f698e25c02439c7b2dbd7b6648e2ecb3ddcc34e638ed4d116b0b409aeb25";
      var10002[218] = "2585c58e856754f3d7e36f427caa95aab8ff19781a1ba645acb428e86452b5a0";
      var10002[219] = "99e3b82da73910648899e454691e68f8d15f276dc9aab1435feff8a047948a40";
      var10002[220] = "75867d2c0a93cc99e05a86d55637e7f47fef7c9f98a9e5158dc36dd1b414bd90";
      var10002[221] = "b283088987c25b6153bbc1261864bc1764985dc4dd94bd5ebe040f598f7c4d59";
      var10002[222] = "5b71b9a4ff7292f7c4955407a97acc93d2784620a53367117713bfa07d0909c2";
      var10002[223] = "b1732d69af0612111e8f15ccc105013fe2ce08c4c65b65833b6456e4f01238ef";
      var10002[224] = "519d503b6cd7565119361cf6b51ef8d294a951b4b512d2727f28b5cc9d784626";
      var10002[225] = "d2da19710b8a4171ac9b17984dd95d042d92742d72a74ba40450d7494a24321";
      var10002[226] = "1d9e8dafe7d87bb7cba7eb3d8d2d5bf58eab72ecdfdf9ecce3d1c03871c0";
      sZ8 = new HashSet<>(Arrays.asList(var10002));
   }
}
