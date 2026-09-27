// Jade recovery: module: Bedwars Utils (minigames); original class: jade.deps.eLz.t1ZZKD
package jade.client.module.minigames;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.common.RenderUtils;
import jade.client.common.RoundedRect;
import jade.client.common.Subscribe;
import jade.client.event.ChatReceivedEvent;
import jade.client.event.EntityJoinWorldEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.RenderTickEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.minigames.bedwarsutils.BedwarsGameTracker;
import jade.client.module.minigames.bedwarsutils.BuildLimits$0;
import jade.client.module.minigames.bedwarsutils.BuildLimits;
import jade.client.module.minigames.bedwarsutils.BuildLimitRenderer;
import jade.client.module.minigames.bedwarsutils.LowHealthAlert;
import jade.client.module.minigames.bedwarsutils.QuickMathsSolver;
import jade.client.module.render.ItemESP;
import jade.client.module.shared.ModuleToggleListener;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.SliderSetting;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;

import java.awt.Color;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@ModuleInfo(aliases = {"Bed Wars", "BedWars"})
public class BedwarsUtils extends Module implements ModuleToggleListener {
   private static final Pattern pattern = Pattern.compile(
      "(?:§[0-9a-fk-or]\\s*)*FINAL\\s+(?:§[0-9a-fk-or]\\s*)*KILL!", 2
   );
   private static final String[] ZMwa = new String[]{
         "c",
         "9",
         "a",
         "e",
         "b",
         "f",
         "d",
         "8"
      };
   private static final float XSCYL = 6.0F;
   private static final float FINAL_KILL_HUD_DEFAULT_Y = 52.0F;
   private static final float DRAGON_HUD_DEFAULT_X = 0.5F;
   private static final float DRAGON_HUD_DEFAULT_Y = 6.0F;
   private static final float BUILD_LIMIT_HUD_DEFAULT_X = 6.0F;
   private static final float aBx = 92.0F;
   private static final int THhKd = 20;
   private static final long LOCRAW_COOLDOWN_MILLIS = 5000L;
   private static final double DRAGON_DISTANCE_RANGE = 80.0;
   private static final int DRAGON_ESP_ALPHA = 150;
   private static final Set<String> quickShopCategoryNames = new HashSet<>();
   private final BooleanSetting alerts;
   private final BooleanSetting ping;
   private final BooleanSetting showDistance;
   private final BooleanSetting ignoreFriends;
   private final BooleanSetting ignoreTeammates;
   private final SliderSetting alertDelay;
   private final BooleanSetting quickShop;
   private final BooleanSetting autoQuickMathsChallenge;
   private final BooleanSetting bowUtils;
   private final BooleanSetting resourceTimers;
   private final BooleanSetting itemNametagTimers;
   private final BooleanSetting buildLimit;
   private final BooleanSetting buildLimitEffect;
   private final BooleanSetting barrierPlane;
   private final BooleanSetting buildLimitHud;
   private final FontSetting buildLimitFont;
   private final SliderSetting buildLimitHudScale;
   private final BooleanSetting chainmailArmor;
   private final BooleanSetting ironArmor;
   private final BooleanSetting diamondArmor;
   private final BooleanSetting ironSword;
   private final BooleanSetting diamondSword;
   private final BooleanSetting diamondPickaxe;
   private final BooleanSetting enderPearl;
   private final BooleanSetting bridgeEgg;
   private final BooleanSetting milkBucket;
   private final BooleanSetting fireball;
   private final BooleanSetting bow;
   private final BooleanSetting obsidian;
   private final BooleanSetting tnt;
   private final BooleanSetting rotationals;
   private final BooleanSetting sharpness;
   private final BooleanSetting protection;
   private final BooleanSetting finalKillHud;
   private final BooleanSetting teammatesOnly;
   private final BooleanSetting finalKillTextShadow;
   private final BooleanSetting finalKillBackground;
   private final FontSetting finalKillFont;
   private final SliderSetting finalKillHudScale;
   private final SliderSetting finalKillRounding;
   private final BooleanSetting dragons;
   private final BooleanSetting dragonHudText;
   private final SliderSetting dragonHudScale;
   private final List<BedwarsUtils$5> alertItems = new ArrayList<>();
   private final Map<String, BedwarsUtils$6> playerEquipmentStates = new HashMap<>();
   private final Map<String, String> teamColorCache = new HashMap<>();
   private final Map<String, Integer> fdm = new LinkedHashMap<>();
   private final Set<String> upgradeAlertKeys = new HashSet<>();
   private final LowHealthAlert lowHealthAlert = new LowHealthAlert();
   private final BedwarsGameTracker bedwarsGameTracker = new BedwarsGameTracker();
   private String selfTeamColorCode = "";
   private String lastFinalKillMessage = "";
   private long lastFinalKillTime;
   private float VLaHy = 6.0F;
   private float WDGbV = 52.0F;
   private float WzS = Float.NaN;
   private float finalKillHudYRatio = Float.NaN;
   private float dragonHudX = 0.5F;
   private float dragonHudY = 6.0F;
   private float dragonHudXRatio = Float.NaN;
   private float dragonHudYRatio = Float.NaN;
   private float buildLimitHudX = 6.0F;
   private float CRde = 92.0F;
   private float buildLimitHudXRatio = Float.NaN;
   private float buildLimitHudYRatio = Float.NaN;
   private String GQj = "";
   private BuildLimits$0 HLpZ;
   private int locrawDelayTicks;
   private boolean locrawPending;
   private boolean BRxu;
   private long lastLocrawTime;
   private String pendingQuickMathsAnswer;
   private long LxTzp;
   private String UTVX = "";
   private long lastQuickMathsTime;

   public BedwarsUtils() {
      super("Bedwars Utils", Category.minigames);
      this.registerSetting(new DescriptionSetting("BedWars alerts and utilities."));
      this.registerSetting(
         this.quickShop = new BooleanSetting(
            "Quick Shop", false
         )
      );
      this.registerSetting(this.autoQuickMathsChallenge = new BooleanSetting("Auto Quick Maths Challenge", false));
      this.registerSetting(
         this.bowUtils = new BooleanSetting(
            "Bow Utils", false
         )
      );
      this.registerSetting(
         this.resourceTimers = new BooleanSetting(
            "Resource Timers", false
         )
      );
      this.registerSetting(
         this.itemNametagTimers = new BooleanSetting(
            "Item nametag timers",
            false
         )
      );
      this.itemNametagTimers.visible = false;
      this.registerSetting(
         this.buildLimit = new BooleanSetting(
            "Build Limit", false
         )
      );
      this.registerSetting(
         this.buildLimitEffect = new BooleanSetting(
            "Build limit effect", true
         )
      );
      this.registerSetting(this.barrierPlane = new BooleanSetting("Barrier plane", true));
      this.registerSetting(
         this.buildLimitHud = new BooleanSetting(
            "Build limit HUD", true
         )
      );
      this.registerSetting(
         this.buildLimitFont = new FontSetting(
            "Build limit font", "Modern"
         )
      );
      this.registerSetting(
         this.buildLimitHudScale = new SliderSetting(
            "Build limit HUD scale", "x", 1.0, 0.5, 2.0, 0.05
         )
      );
      this.buildLimitHudScale.visible = false;
      this.registerSetting(
         this.alerts = new BooleanSetting(
            "Alerts",
            true,
            new String[]{"Chat alerts"}
         )
      );
      this.registerSetting(
         this.ping = new BooleanSetting(
            "Ping",
            true,
            new String[]{"Should ping"}
         )
      );
      this.registerSetting(
         this.showDistance = new BooleanSetting(
            "Show distance", true
         )
      );
      this.registerSetting(
         this.ignoreFriends = new BooleanSetting(
            "Ignore friends",
            true,
            new String[]{"Ignore"}
         )
      );
      this.registerSetting(
         this.ignoreTeammates = new BooleanSetting(
            "Ignore teammates", true
         )
      );
      this.registerSetting(
         this.alertDelay = new SliderSetting(
            "Alert delay", " second", 15.0, 0.0, 60.0, 1.0
         )
      );
      this.registerSetting(
         this.chainmailArmor = new BooleanSetting(
            "Chainmail Armor", true
         )
      );
      this.registerSetting(
         this.ironArmor = new BooleanSetting(
            "Iron Armor", true
         )
      );
      this.registerSetting(this.diamondArmor = new BooleanSetting("Diamond Armor", true, new String[]{"Diamond armor"}));
      this.registerSetting(this.ironSword = new BooleanSetting("Iron Sword", true));
      this.registerSetting(this.diamondSword = new BooleanSetting("Diamond Sword", true));
      this.registerSetting(
         this.diamondPickaxe = new BooleanSetting(
            "Diamond Pickaxe", true
         )
      );
      this.registerSetting(this.enderPearl = new BooleanSetting("Ender Pearl", true, new String[]{"Ender pearl"}));
      this.registerSetting(this.bridgeEgg = new BooleanSetting("Bridge Egg", true));
      this.registerSetting(
         this.milkBucket = new BooleanSetting(
            "Milk Bucket", true
         )
      );
      this.registerSetting(this.fireball = new BooleanSetting("Fireball", true));
      this.registerSetting(
         this.bow = new BooleanSetting("Bow", true)
      );
      this.registerSetting(this.obsidian = new BooleanSetting("Obsidian", true));
      this.registerSetting(
         this.tnt = new BooleanSetting("TNT", true)
      );
      this.registerSetting(
         this.rotationals = new BooleanSetting(
            "Rotationals", true
         )
      );
      this.registerSetting(
         this.sharpness = new BooleanSetting(
            "Sharpness", true
         )
      );
      this.registerSetting(
         this.protection = new BooleanSetting(
            "Protection", true
         )
      );
      this.registerSetting(
         this.finalKillHud = new BooleanSetting(
            "Final kill HUD", true
         )
      );
      this.registerSetting(
         this.teammatesOnly = new BooleanSetting(
            "Teammates only", false
         )
      );
      this.registerSetting(
         this.finalKillTextShadow = new BooleanSetting(
            "Final kill text shadow",
            true
         )
      );
      this.registerSetting(this.finalKillBackground = new BooleanSetting("Final kill background", true));
      this.registerSetting(
         this.finalKillFont = new FontSetting(
            "Final kill font", "Modern"
         )
      );
      this.registerSetting(
         this.finalKillHudScale = new SliderSetting(
            "Final kill HUD scale", "x", 1.0, 0.5, 2.0, 0.05
         )
      );
      this.finalKillHudScale.visible = false;
      this.registerSetting(this.finalKillRounding = new SliderSetting("Final kill rounding", 4.0, 0.0, 12.0, 0.5));
      this.registerSetting(
         this.dragons = new BooleanSetting(
            "Dragons", false
         )
      );
      this.registerSetting(
         this.dragonHudText = new BooleanSetting(
            "Dragon HUD text", true
         )
      );
      this.registerSetting(
         this.dragonHudScale = new SliderSetting(
            "Dragon HUD scale", "x", 1.0, 0.5, 2.0, 0.05
         )
      );
      this.dragonHudScale.visible = false;
      this.zkVv7();
      this.initialized = true;
   }

   @Override
   public void guiUpdate() {
      this.itemNametagTimers.setVisible(this.resourceTimers.isToggled() && this.isItemEspNametagsActive(), this);
      boolean var1 = this.buildLimit.isToggled();
      this.buildLimitEffect.setVisible(var1, this);
      this.barrierPlane.setVisible(var1 && this.buildLimitEffect.isToggled(), this);
      this.buildLimitHud.setVisible(var1, this);
      this.buildLimitFont.setVisible(var1 && this.buildLimitHud.isToggled(), this);
      this.buildLimitHudScale.visible = false;
      boolean var2 = this.alerts.isToggled();
      this.ping.setVisible(var2, this);
      this.showDistance.setVisible(var2, this);
      this.ignoreFriends.setVisible(var2, this);
      this.ignoreTeammates.setVisible(var2, this);
      this.alertDelay.setVisible(var2, this);
      this.chainmailArmor.setVisible(var2, this);
      this.ironArmor.setVisible(var2, this);
      this.diamondArmor.setVisible(var2, this);
      this.ironSword.setVisible(var2, this);
      this.diamondSword.setVisible(var2, this);
      this.diamondPickaxe.setVisible(var2, this);
      this.enderPearl.setVisible(var2, this);
      this.bridgeEgg.setVisible(var2, this);
      this.milkBucket.setVisible(var2, this);
      this.fireball.setVisible(var2, this);
      this.bow.setVisible(var2, this);
      this.obsidian.setVisible(var2, this);
      this.tnt.setVisible(var2, this);
      this.rotationals.setVisible(var2, this);
      this.sharpness.setVisible(var2, this);
      this.protection.setVisible(var2, this);
      boolean var3 = this.finalKillHud.isToggled();
      this.teammatesOnly.setVisible(var3, this);
      this.finalKillTextShadow.setVisible(var3, this);
      this.finalKillBackground.setVisible(var3, this);
      this.finalKillFont.setVisible(var3, this);
      this.finalKillHudScale.visible = false;
      this.finalKillRounding.setVisible(var3, this);
      this.dragonHudText.setVisible(this.dragons.isToggled(), this);
      this.dragonHudScale.visible = false;
   }

   @Override
   public void onEnable() {
      this.guiUpdate();
      this.resetTrackedState();
      this.resetBuildLimitState();
      this.bedwarsGameTracker.zry91();
   }

   @Override
   public void guiButtonToggled(BooleanSetting var1) {
      if (var1 == this.buildLimit || var1 == this.buildLimitHud || var1 == this.alerts || var1 == this.resourceTimers || var1 == this.itemNametagTimers || var1 == this.finalKillHud || var1 == this.dragons) {
         this.guiUpdate();
      }

      if (var1 == this.resourceTimers && !this.resourceTimers.isToggled()) {
         this.bedwarsGameTracker.zry91();
      }

      if (var1 == this.buildLimit && this.buildLimit.isToggled()) {
         this.resetBuildLimitState();
      }

      if (var1 == this.bowUtils && !this.bowUtils.isToggled()) {
         this.lowHealthAlert.SQxv();
      }
   }

   @Override
   public void onDisable() {
      this.clearPendingQuickMathsAnswer();
      this.resetTrackedState();
      this.resetBuildLimitState();
      this.bedwarsGameTracker.zry91();
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onPacketReceive(PacketReceiveEvent var1) {
      if (ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 2 && var1.ys98() instanceof S04PacketEntityEquipment) {
         S04PacketEntityEquipment var2 = (S04PacketEntityEquipment)var1.ys98();
         Entity var3 = mc.theWorld.getEntityByID(var2.getEntityID());
         if (var3 instanceof EntityPlayer) {
            this.handlePlayerEquipment((EntityPlayer)var3, var2.getItemStack(), var2.getEquipmentSlot());
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPacketSend(PacketSendEvent var1) {
      if (!var1.isCancelled() && this.bowUtils.isToggled() && ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 2) {
         this.lowHealthAlert.recordBowRelease(var1.ys98(), mc);
      }
   }

   @Subscribe
   public void handleChatMessage(ChatReceivedEvent var1) {
      if (var1 != null && var1.messageType != 2 && var1.iChatComponent != null) {
         String var2 = ClientUtils.AOAtn(var1.iChatComponent.getUnformattedText());
         if (this.resourceTimers.isToggled()) {
            this.bedwarsGameTracker.onGameStartMessage(var2);
         }

         this.trySolveQuickMaths(var2);
         if (this.buildLimit.isToggled() && this.isLocrawResponse(var2)) {
            var1.setCanceled(true);
            this.locrawPending = false;
            this.parseLocrawResponse(var2);
         } else if (ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 2) {
            String var3 = var1.iChatComponent.getFormattedText();
            Matcher var4 = pattern.matcher(var3);
            if (var4.find()) {
               if (!this.isDuplicateFinalKill(var3)) {
                  BedwarsUtils$3 var5 = parseFinalKillMessage(var3.substring(0, var4.start()).trim());
                  if (var5 != null) {
                     if (!"void".equalsIgnoreCase(BedwarsUtils$3.jzp9(var5))) {
                        if (!this.teammatesOnly.isToggled() || this.isFinalKillTeammate(var5)) {
                           this.HQnc(BedwarsUtils$3.getColorPrefix(var5) + BedwarsUtils$3.jzp9(var5));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onChatReceived(ChatReceivedEvent var1) {
      if (var1 != null && !var1.isCancelled() && var1.messageType != 2 && var1.iChatComponent != null && this.bowUtils.isToggled() && ClientUtils.isInWorld() && ClientUtils.getBedWarsBoardType() == 2) {
         this.lowHealthAlert.appendShooterDistance(var1.iChatComponent, ClientUtils.AOAtn(var1.iChatComponent.getUnformattedText()), mc);
      }
   }

   @Subscribe
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END && ClientUtils.isInWorld() && mc.currentScreen == null && !mc.gameSettings.showDebugInfo) {
         if (this.finalKillHud.isToggled() && ClientUtils.getBedWarsBoardType() == 2 && !this.fdm.isEmpty()) {
            this.refreshFinalKillHudPosition();
            this.drawFinalKillHud(this.VLaHy, this.WDGbV, false);
         }

         if (this.dragons.isToggled() && this.dragonHudText.isToggled()) {
            EntityDragon var2 = this.DJVRwPh();
            if (var2 != null) {
               this.refreshDragonHudPosition();
               this.drawDragonHud(this.dragonHudX, this.dragonHudY, var2, false);
            }
         }

         if (this.buildLimit.isToggled() && this.buildLimitHud.isToggled() && ClientUtils.getBedWarsBoardType() == 2 && this.HLpZ != null) {
            this.refreshBuildLimitHudPosition();
            this.drawBuildLimitHud(this.buildLimitHudX, this.CRde, this.HLpZ.minY, this.HLpZ.maxY, false);
         }
      }
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (ClientUtils.isInWorld() && mc.theWorld != null) {
         if (this.dragons.isToggled()) {
            for (Entity var3 : mc.theWorld.loadedEntityList) {
               if (var3 instanceof EntityDragon && !var3.isDead && RenderUtils.isEntityInView(var3)) {
                  this.renderDragonBox((EntityDragon)var3, var1.YDn0);
               }
            }
         }

         if (this.buildLimit.isToggled() && this.buildLimitEffect.isToggled() && ClientUtils.getBedWarsBoardType() == 2 && this.HLpZ != null) {
            BuildLimitRenderer.QWUZbXw(mc, var1.YDn0, this.HLpZ.minY, this.HLpZ.maxY, this.barrierPlane.isToggled());
         }
      }
   }

   @Subscribe
   public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
      if (var1.entity == mc.thePlayer) {
         this.resetTrackedState();
         this.resetBuildLimitState();
         this.bedwarsGameTracker.zry91();
      }
   }

   @Override
   public void onUpdate() {
      if (!ClientUtils.isInWorld()) {
         this.clearPendingQuickMathsAnswer();
      } else {
         this.sendPendingQuickMathsAnswer();
         int var1 = ClientUtils.getBedWarsBoardType();
         if (this.resourceTimers.isToggled()) {
            this.bedwarsGameTracker.updateGameState(mc, var1, true);
         }

         if (this.buildLimit.isToggled() && (var1 == 1 || var1 == 2) && !this.BRxu) {
            if (this.locrawDelayTicks > 0) {
               this.locrawDelayTicks--;
            } else {
               this.requestLocraw();
            }
         }

         if (var1 == 2) {
            if (mc.thePlayer.ticksExisted % 100 == 0) {
               this.selfTeamColorCode = this.resolveSelfTeamColor();
               this.scanPlayerEquipment();
            }
         } else {
            if (!this.playerEquipmentStates.isEmpty() || !this.fdm.isEmpty()) {
               this.resetTrackedState();
            }
         }
      }
   }

   public String getItemNametagText(EntityItem var1) {
      return this.isEnabled() && this.resourceTimers.isToggled() && this.PJMzZo() && ClientUtils.getBedWarsBoardType() == 2 ? this.bedwarsGameTracker.getItemSpawnEta(var1) : null;
   }

   private boolean PJMzZo() {
      return this.itemNametagTimers.isToggled() && this.isItemEspNametagsActive();
   }

   private boolean isItemEspNametagsActive() {
      ItemESP var1 = Jade.getModuleManager().getModule(ItemESP.class);
      return var1 != null && var1.isEnabled() && var1.isNametagMode();
   }

   @Override
   public void onModuleToggled(Module var1, boolean var2) {
      if (var1 instanceof ItemESP) {
         this.guiUpdate();
      }
   }

   private void trySolveQuickMaths(String var1) {
      if (this.autoQuickMathsChallenge.isToggled()) {
         String var2 = QuickMathsSolver.solveQuickMaths(var1);
         if (var2 != null) {
            long var3 = System.currentTimeMillis();
            String var5 = var1.trim();
            if (!var5.equals(this.UTVX) || var3 - this.lastQuickMathsTime >= 5000L) {
               this.UTVX = var5;
               this.lastQuickMathsTime = var3;
               this.pendingQuickMathsAnswer = var2;
               this.LxTzp = var3 + ThreadLocalRandom.current().nextInt(500, 2001);
            }
         }
      }
   }

   private void sendPendingQuickMathsAnswer() {
      if (this.pendingQuickMathsAnswer != null) {
         if (!this.autoQuickMathsChallenge.isToggled()) {
            this.clearPendingQuickMathsAnswer();
         } else if (System.currentTimeMillis() >= this.LxTzp) {
            String var1 = this.pendingQuickMathsAnswer;
            this.clearPendingQuickMathsAnswer();
            mc.thePlayer.sendChatMessage("/quickmaths " + var1);
         }
      }
   }

   private void clearPendingQuickMathsAnswer() {
      this.pendingQuickMathsAnswer = null;
      this.LxTzp = 0L;
   }

   private void scanPlayerEquipment() {
      for (EntityPlayer var2 : mc.theWorld.playerEntities) {
         if (var2 != null && var2 != mc.thePlayer) {
            this.handlePlayerEquipment(var2, var2.getHeldItem(), 0);
            if (var2.inventory != null && var2.inventory.armorInventory != null && var2.inventory.armorInventory.length > 1) {
               this.handlePlayerEquipment(var2, var2.inventory.armorInventory[1], 2);
            }
         }
      }
   }

   private void handlePlayerEquipment(EntityPlayer var1, ItemStack var2, int var3) {
      if (var1 != mc.thePlayer && !var1.isDead && !this.isIgnoredPlayer(var1)) {
         String var4 = var1.getUniqueID().toString();
         BedwarsUtils$6 var5 = this.playerEquipmentStates.get(var4);
         if (var5 == null) {
            var5 = new BedwarsUtils$6();
            this.playerEquipmentStates.put(var4, var5);
         }

         if (var2 != null && var2.getItem() != null) {
            this.checkEnchantmentUpgrades(var1, var2, var3);
            if (var3 == 0) {
               this.handleHeldItemChange(var1, var5, var2);
            } else if (var3 == 2) {
               this.handleArmorChange(var1, var5, var2);
            }
         }
      }
   }

   private void handleHeldItemChange(EntityPlayer var1, BedwarsUtils$6 var2, ItemStack var3) {
      BedwarsUtils$5 var4 = this.Ht19(var3);
      if (var4 != null) {
         BedwarsUtils$4 var5 = this.NHPiYuh(var4, var3);
         long var6 = System.currentTimeMillis();
         Long var8 = (Long)BedwarsUtils$6.getItemTimers(var2).get(BedwarsUtils$4.getTimerKey(var5));
         if (var8 == null || var6 > var8) {
            BedwarsUtils$6.getItemTimers(var2).put(BedwarsUtils$4.getTimerKey(var5), var6 + this.getAlertDelayMillis());
            this.announceHeldItem(var1, BedwarsUtils$4.getDisplayName(var5));
         }
      }
   }

   private BedwarsUtils$4 NHPiYuh(BedwarsUtils$5 var1, ItemStack var2) {
      if (BedwarsUtils$5.uiTdt(var1) == this.bow && var2.getItem() == Items.bow) {
         int var3 = var2.getEnchantmentTagList() == null ? 0 : var2.getEnchantmentTagList().tagCount();
         if (var3 >= 2) {
            return new BedwarsUtils$4(BedwarsUtils$5.getTimerKey(var1) + ":punch", "§6Punch Bow");
         } else {
            return var3 == 1
               ? new BedwarsUtils$4(BedwarsUtils$5.getTimerKey(var1) + ":power", "§dPower Bow")
               : new BedwarsUtils$4(BedwarsUtils$5.getTimerKey(var1) + ":normal", BedwarsUtils$5.getDisplayName(var1));
         }
      } else {
         return new BedwarsUtils$4(BedwarsUtils$5.getTimerKey(var1), BedwarsUtils$5.getDisplayName(var1));
      }
   }

   public boolean handleQuickShopSlotClick(Slot var1, int var2, int var3, int var4) {
      if (this.isEnabled()
         && this.quickShop.isToggled()
         && ClientUtils.isInWorld()
         && ClientUtils.getBedWarsBoardType() == 2
         && var1 != null
         && var2 >= 0
         && var4 == 0
         && (var3 == 0 || var3 == 1)
         && mc.thePlayer.openContainer instanceof ContainerChest) {
         ContainerChest var5 = (ContainerChest)mc.thePlayer.openContainer;
         IInventory var6 = var5.getLowerChestInventory();
         if (var6 != null && var6.getDisplayName() != null) {
            String var7 = ClientUtils.AOAtn(var6.getDisplayName().getUnformattedText());
            if (var7 != null && quickShopCategoryNames.contains(var7.trim().toLowerCase(Locale.ROOT))) {
               mc.playerController.windowClick(var5.windowId, var2, 2, 3, mc.thePlayer);
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void handleArmorChange(EntityPlayer var1, BedwarsUtils$6 var2, ItemStack var3) {
      BedwarsUtils$5 var4 = this.findArmorAlert(var3);
      if (var4 != null && !BedwarsUtils$5.getTimerKey(var4).equals(BedwarsUtils$6.getLastAlertKey(var2))) {
         BedwarsUtils$6.setLastAlertKey(var2, BedwarsUtils$5.getTimerKey(var4));
         this.hXiaC(var1, BedwarsUtils$5.getDisplayName(var4));
      }
   }

   private void checkEnchantmentUpgrades(EntityPlayer var1, ItemStack var2, int var3) {
      if (!EnchantmentHelper.getEnchantments(var2).isEmpty()) {
         String var4 = this.getOrCacheTeamColor(var1);
         if (var4.length() != 0) {
            if (var3 != 2 || !this.protection.isToggled() || !this.upgradeAlertKeys.contains("protection:" + var4)) {
               if (var3 != 0 || !isSwordItem(var2) || !this.sharpness.isToggled() || !this.upgradeAlertKeys.contains("sharpness:" + var4)) {
                  if (var3 == 2 && this.protection.isToggled()) {
                     this.upgradeAlertKeys.add("protection:" + var4);
                     this.qgsP(var1, "§bReinforced Armor");
                  } else if (var3 == 0 && isSwordItem(var2) && this.sharpness.isToggled()) {
                     this.upgradeAlertKeys.add("sharpness:" + var4);
                     this.qgsP(var1, "§bSharpened Swords");
                  }
               }
            }
         }
      }
   }

   private void announceHeldItem(EntityPlayer var1, String var2) {
      String var3 = this.EXjVmn(var1) + " §7is holding §r" + var2 + this.HZECi(var1);
      this.TRwZ(var3);
   }

   private void hXiaC(EntityPlayer var1, String var2) {
      String var3 = this.EXjVmn(var1) + " §7purchased §r" + var2 + this.HZECi(var1);
      this.TRwZ(var3);
   }

   private void qgsP(EntityPlayer var1, String var2) {
      String var3 = this.getTeamDisplayName(var1) + " §7purchased " + var2;
      this.TRwZ(var3);
   }

   private void TRwZ(String var1) {
      if (this.alerts.isToggled()) {
         ClientUtils.sendJadeMessage("Jade", var1);
         this.playAlertSound();
      }
   }

   private void playAlertSound() {
      if (this.ping.isToggled()) {
         mc.thePlayer.playSound("note.pling", 1.0F, 1.0F);
      }
   }

   private boolean isIgnoredPlayer(EntityPlayer var1) {
      return this.ignoreFriends.isToggled() && ClientUtils.isFriend(var1) || this.ignoreTeammates.isToggled() && ClientUtils.isTeammate(var1);
   }

   private String EXjVmn(EntityPlayer var1) {
      String var2 = getPlayerTeamColor(var1);
      return var2.length() == 0 ? "§r" + var1.getDisplayName().getFormattedText() : "§r§" + var2 + var1.getName();
   }

   private String HZECi(EntityPlayer var1) {
      return !this.showDistance.isToggled() ? "" : " §7(§f" + Math.round(mc.thePlayer.getDistanceToEntity(var1)) + "m§7)";
   }

   private long getAlertDelayMillis() {
      return (long)(this.alertDelay.getInput() * 1000.0);
   }

   private BedwarsUtils$5 Ht19(ItemStack var1) {
      BedwarsUtils$5 var2 = null;

      for (BedwarsUtils$5 var4 : this.alertItems) {
         if (BedwarsUtils$5.getCategory(var4) == BedwarsUtils$1.HELD && BedwarsUtils$5.isDisplayNameMatch(var4)) {
            if (BedwarsUtils$5.uiTdt(var4) == this.bow) {
               var2 = var4;
            } else if (BedwarsUtils$5.matchesItemStack(var4, var1)) {
               return BedwarsUtils$5.uiTdt(var4).isToggled() ? var4 : null;
            }
         }
      }

      if (var2 != null && BedwarsUtils$5.uiTdt(var2).isToggled() && var1.getItem() == Items.bow) {
         return var2;
      } else {
         for (BedwarsUtils$5 var6 : this.alertItems) {
            if (BedwarsUtils$5.getCategory(var6) == BedwarsUtils$1.HELD && !BedwarsUtils$5.isDisplayNameMatch(var6) && BedwarsUtils$5.uiTdt(var6).isToggled() && BedwarsUtils$5.matchesItemStack(var6, var1)) {
               return var6;
            }
         }

         return null;
      }
   }

   private BedwarsUtils$5 findArmorAlert(ItemStack var1) {
      for (BedwarsUtils$5 var3 : this.alertItems) {
         if (BedwarsUtils$5.getCategory(var3) == BedwarsUtils$1.ARMOR && BedwarsUtils$5.uiTdt(var3).isToggled() && BedwarsUtils$5.matchesItemStack(var3, var1)) {
            return var3;
         }
      }

      return null;
   }

   private void zkVv7() {
      this.registerArmorAlert("chainmail_leggings", "§fChainmail Armor", this.chainmailArmor);
      this.registerArmorAlert("iron_leggings", "§fIron Armor", this.ironArmor);
      this.registerArmorAlert("diamond_leggings", "§bDiamond Armor", this.diamondArmor);
      this.registerHeldItemAlert("iron_sword", "§fIron Sword", this.ironSword);
      this.registerHeldItemAlert("diamond_sword", "§bDiamond Sword", this.diamondSword);
      this.registerHeldItemAlert("diamond_pickaxe", "§bDiamond Pickaxe", this.diamondPickaxe);
      this.registerHeldItemAlert("ender_pearl", "§3Ender Pearl", this.enderPearl);
      this.registerHeldItemAlert("egg", "§eBridge Egg", this.bridgeEgg);
      this.registerHeldItemAlert("milk_bucket", "§fMilk Bucket", this.milkBucket);
      this.registerHeldItemAlert("fire_charge", "§6Fireball", this.fireball);
      this.registerHeldItemAlert("obsidian", "§5Obsidian", this.obsidian);
      this.registerHeldItemAlert("tnt", "§cT§fN§cT", this.tnt);
      this.uSwnu("Bow", "§2Bow", this.bow);
      this.registerHeldItemAlert("prismarine_shard", "§3Block Zapper", this.rotationals);
      this.uSwnu("Speed II Potion (45 seconds)", "§bSpeed Potion", this.rotationals);
      this.uSwnu("Jump V Potion (45 seconds)", "§aJump Boost Potion", this.rotationals);
      this.uSwnu("Invisibility Potion (30 seconds)", "§fInvisibility Potion", this.rotationals);
      this.uSwnu("§cDream Defender", "§fIron Golem", this.rotationals);
      this.uSwnu("Machine Gun Bow", "§4Machine Gun Bow", this.rotationals);
      this.uSwnu("Charlie the Unicorn", "§dCharlie the Unicorn", this.rotationals);
      this.uSwnu("Ice Bridge", "§bIce Bridge", this.rotationals);
      this.uSwnu("Sleeping Dust", "§cSleeping Dust", this.rotationals);
      this.uSwnu("Unstable Teleportation Device", "§eUnstable Teleportation Device", this.rotationals);
      this.uSwnu("Devastator Bow", "§2Devastator Bow", this.rotationals);
      this.uSwnu("Miracle of the Stars", "§eMiracle of the Stars", this.rotationals);
      this.uSwnu("Mystic Mirror", "§dMystic Mirror", this.rotationals);
   }

   private void registerHeldItemAlert(String var1, String var2, BooleanSetting var3) {
      this.alertItems.add(BedwarsUtils$5.qnPb(BedwarsUtils$1.HELD, var1, var2, var3));
   }

   private void uSwnu(String var1, String var2, BooleanSetting var3) {
      this.alertItems.add(BedwarsUtils$5.saFbc(BedwarsUtils$1.HELD, var1, var2, var3));
   }

   private void registerArmorAlert(String var1, String var2, BooleanSetting var3) {
      this.alertItems.add(BedwarsUtils$5.qnPb(BedwarsUtils$1.ARMOR, var1, var2, var3));
   }

   private void HQnc(String var1) {
      Integer var2 = this.fdm.get(var1);
      this.fdm.put(var1, var2 == null ? 1 : var2 + 1);
   }

   private void requestLocraw() {
      long var1 = System.currentTimeMillis();
      if (this.locrawPending && var1 - this.lastLocrawTime > 5000L) {
         this.locrawPending = false;
      }

      if (ClientUtils.isInWorld() && !this.locrawPending && var1 - this.lastLocrawTime >= 1500L) {
         this.locrawPending = true;
         this.lastLocrawTime = var1;
         mc.thePlayer.sendChatMessage("/locraw");
      }
   }

   private boolean isLocrawResponse(String var1) {
      if (var1 == null) {
         return false;
      } else {
         String var2 = var1.trim();
         return var2.startsWith("{") && var2.endsWith("}") && var2.contains("\"server\"") && var2.contains("\"gametype\"");
      }
   }

   private void parseLocrawResponse(String var1) {
      this.GQj = "";
      this.HLpZ = null;

      try {
         JsonObject var2 = new JsonParser().parse(var1).getAsJsonObject();
         String var3 = var2.has("gametype") ? var2.get("gametype").getAsString() : "";
         String var4 = var2.has("map") ? var2.get("map").getAsString() : "";
         if ("BEDWARS".equalsIgnoreCase(var3) && var4.trim().length() > 0) {
            this.GQj = BuildLimits.normalizeMapName(var4);
            this.HLpZ = BuildLimits.getForMapName(var4);
         }
      } catch (Exception var8) {
      } finally {
         this.BRxu = true;
      }
   }

   private void resetBuildLimitState() {
      this.GQj = "";
      this.HLpZ = null;
      this.locrawDelayTicks = 20;
      this.locrawPending = false;
      this.BRxu = false;
      this.lastLocrawTime = 0L;
   }

   public boolean isBuildLimitHudActive() {
      return this.buildLimit.isToggled() && this.buildLimitHud.isToggled();
   }

   public float getBuildLimitHudX() {
      this.refreshBuildLimitHudPosition();
      return this.buildLimitHudX;
   }

   public float getBuildLimitHudY() {
      this.refreshBuildLimitHudPosition();
      return this.CRde;
   }

   public float faOoa() {
      this.refreshBuildLimitHudPosition();
      return this.buildLimitHudXRatio;
   }

   public float uwJfi() {
      this.refreshBuildLimitHudPosition();
      return this.buildLimitHudYRatio;
   }

   public void applyBuildLimitHudDrag(float var1, float var2) {
      this.buildLimitHudXRatio = Math.max(0.0F, Math.min(1.0F, var1));
      this.buildLimitHudYRatio = Math.max(0.0F, Math.min(1.0F, var2));
      this.refreshBuildLimitHudPosition();
   }

   public void setBuildLimitHudPosition(float var1, float var2) {
      ScaledResolution var3 = new ScaledResolution(mc);
      this.buildLimitHudX = var1;
      this.CRde = var2;
      this.buildLimitHudXRatio = var1 / Math.max(1.0F, (float)var3.getScaledWidth());
      this.buildLimitHudYRatio = var2 / Math.max(1.0F, (float)var3.getScaledHeight());
   }

   public void resetBuildLimitHudPosition() {
      ScaledResolution var1 = new ScaledResolution(mc);
      this.buildLimitHudXRatio = 6.0F / Math.max(1.0F, (float)var1.getScaledWidth());
      this.buildLimitHudYRatio = 92.0F / Math.max(1.0F, (float)var1.getScaledHeight());
      this.updateBuildLimitHudPosition(var1);
   }

   public float[] renderBuildLimitHud(float var1, float var2) {
      return this.drawBuildLimitHud(var1, var2, 60, 100, true);
   }

   public SliderSetting getBuildLimitHudScale() {
      return this.buildLimitHudScale;
   }

   public float getFinalKillHudX() {
      this.refreshFinalKillHudPosition();
      return this.VLaHy;
   }

   public float iskppX4() {
      this.refreshFinalKillHudPosition();
      return this.WDGbV;
   }

   public float getFinalKillHudXRatio() {
      this.refreshFinalKillHudPosition();
      return this.WzS;
   }

   public float UFKv() {
      this.refreshFinalKillHudPosition();
      return this.finalKillHudYRatio;
   }

   public void applyFinalKillHudDrag(float var1, float var2) {
      this.WzS = Math.max(0.0F, Math.min(1.0F, var1));
      this.finalKillHudYRatio = Math.max(0.0F, Math.min(1.0F, var2));
      this.refreshFinalKillHudPosition();
   }

   public void setFinalKillHudPosition(float var1, float var2) {
      ScaledResolution var3 = new ScaledResolution(mc);
      this.VLaHy = var1;
      this.WDGbV = var2;
      this.WzS = var1 / Math.max(1.0F, (float)var3.getScaledWidth());
      this.finalKillHudYRatio = var2 / Math.max(1.0F, (float)var3.getScaledHeight());
   }

   public void resetFinalKillHudPosition() {
      ScaledResolution var1 = new ScaledResolution(mc);
      this.WzS = 6.0F / Math.max(1.0F, (float)var1.getScaledWidth());
      this.finalKillHudYRatio = 52.0F / Math.max(1.0F, (float)var1.getScaledHeight());
      this.updateFinalKillHudPosition(var1);
   }

   public float[] renderFinalKillHud(float var1, float var2) {
      return this.drawFinalKillHud(var1, var2, true);
   }

   public SliderSetting getFinalKillHudScale() {
      return this.finalKillHudScale;
   }

   public boolean isFinalKillHudActive() {
      return this.finalKillHud.isToggled();
   }

   public boolean woFa() {
      return this.dragons.isToggled() && this.dragonHudText.isToggled();
   }

   public float getDragonHudX() {
      this.refreshDragonHudPosition();
      return this.dragonHudX;
   }

   public float getDragonHudY() {
      this.refreshDragonHudPosition();
      return this.dragonHudY;
   }

   public float getDragonHudXRatio() {
      this.refreshDragonHudPosition();
      return this.dragonHudXRatio;
   }

   public float getDragonHudYRatio() {
      this.refreshDragonHudPosition();
      return this.dragonHudYRatio;
   }

   public void applyDragonHudDrag(float var1, float var2) {
      this.dragonHudXRatio = Math.max(0.0F, Math.min(1.0F, var1));
      this.dragonHudYRatio = Math.max(0.0F, Math.min(1.0F, var2));
      this.refreshDragonHudPosition();
   }

   public void setDragonHudPosition(float var1, float var2) {
      ScaledResolution var3 = new ScaledResolution(mc);
      this.dragonHudX = var1;
      this.dragonHudY = var2;
      this.dragonHudXRatio = var1 / Math.max(1.0F, (float)var3.getScaledWidth());
      this.dragonHudYRatio = var2 / Math.max(1.0F, (float)var3.getScaledHeight());
   }

   public void resetDragonHudPosition() {
      ScaledResolution var1 = new ScaledResolution(mc);
      this.dragonHudXRatio = this.getDragonHudCenteredX(var1) / Math.max(1.0F, (float)var1.getScaledWidth());
      this.dragonHudYRatio = 6.0F / Math.max(1.0F, (float)var1.getScaledHeight());
      this.Olyd(var1);
   }

   public float[] renderDragonHud(float var1, float var2) {
      return this.drawDragonHud(var1, var2, null, true);
   }

   public SliderSetting getDragonHudScale() {
      return this.dragonHudScale;
   }

   private float[] drawFinalKillHud(float var1, float var2, boolean var3) {
      IFont var4 = this.AThW();
      ArrayList var5 = new ArrayList<>(this.fdm.entrySet());
      if (var3 && var5.isEmpty()) {
         var5.add(new SimpleEntry<>("Example", 2));
      }

      Collections.sort(var5, new Comparator<Entry<String, Integer>>() {
         public int compare(Entry<String, Integer> var1, Entry<String, Integer> var2x) {
            return ((Integer)var2x.getValue()).compareTo((Integer)var1.getValue());
         }
      });
      float var6 = var1;
      float var7 = var2;
      int var8 = var4.getFontHeight();
      byte var9 = 2;
      int var10 = var8 + var9;
      float var11 = 1.75F;
      byte var12 = 6;
      byte var13 = 6;
      byte var14 = 5;
      int var15 = 0;

      for (Entry var17 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (var5)) {
         var15 = Math.max(var15, var4.getStringWidth(this.vym5(var17)));
      }

      byte var29 = 5;
      int var30 = var5.size() * var8 + Math.max(0, var5.size() - 1) * var9;
      int var18 = var12 + Math.round(var11 * 2.0F) + var14 + var15 + var13;
      int var19 = var29 * 2 + var30;
      float var20 = (float)this.finalKillRounding.getInput();
      GlStateManager.pushMatrix();

      try {
         if (this.finalKillBackground.isToggled()) {
            RoundedRect.drawRoundedRectArgb(var6, var7, var18, var19, var20, ClientUtils.YVVZ(Color.black.getRGB(), 122));
         }

         float var21 = var7 + (var19 - var30) * 0.5F;

         for (Entry var23 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (var5)) {
            float var24 = var21 + (var4.getTextTopOffset() + var4.getTextBottomOffset()) * 0.5F;
            float var25 = var6 + var12 + var11;
            drawFinalKillDot(var25, var24, var11);
            var4.drawString(this.vym5(var23), var6 + var12 + var11 * 2.0F + var14, var21, -1, this.finalKillTextShadow.isToggled());
            var21 += var10;
         }
      } finally {
         GlStateManager.popMatrix();
         GlStateManager.enableTexture2D();
         GlStateManager.enableBlend();
         GL11.glBlendFunc(770, 771);
         RenderUtils.setAlphaThreshold(10.0F);
         RenderUtils.lTbf();
      }

      return new float[]{var6, var2, var6 + var18, var2 + var19};
   }

   private static void drawFinalKillDot(float var0, float var1, float var2) {
      RoundedRect.drawRoundedRectWithCornerFlags(var0 - var2, var1 - var2, var2 * 2.0F, var2 * 2.0F, var2, -1, true, true, true, true);
   }

   private float[] drawDragonHud(float var1, float var2, EntityDragon var3, boolean var4) {
      IFont var5 = this.getDragonHudFont();
      double var6 = !var4 && var3 != null ? mc.thePlayer.getDistanceToEntity(var3) : 50.0;
      String var8 = "Dragon: " + Math.round(var6) + " blocks";
      String var9 = "Dragon: ";
      String var10 = Math.round(var6) + " blocks";
      int var11 = var5.getStringWidth(var8);
      int var12 = var5.getFontHeight();
      int var14 = this.iwOl6(var6);
      GlStateManager.pushMatrix();

      try {
         var5.drawString(var9, var1, var2, -1, true);
         var5.drawString(var10, var1 + var5.getStringWidth(var9), var2, var14, true);
      } finally {
         GlStateManager.popMatrix();
         GlStateManager.enableTexture2D();
         GlStateManager.enableBlend();
         GL11.glBlendFunc(770, 771);
         RenderUtils.setAlphaThreshold(10.0F);
         RenderUtils.lTbf();
      }

      return new float[]{var1, var2, var1 + var11, var2 + var12};
   }

   private float[] drawBuildLimitHud(float var1, float var2, int var3, int var4, boolean var5) {
      IFont var6 = this.getBuildLimitHudFont();
      int var7 = var5 ? var4 - 10 : MathHelper.floor_double(mc.thePlayer.posY);
      int var8 = Math.abs(var4 - var7);
      int var9 = Math.abs(var7 - var3);
      boolean var10 = var8 <= var9;
      int var11 = var10 ? var8 : var9;
      String var12 = "(";
      String var13 = Integer.toString(var7);
      String var14 = ") ";
      String var15 = (var10 ? "+" : "-") + var11;
      int var16 = this.getBuildLimitHeightColor(var11);
      int var17 = new Color(190, 190, 190).getRGB();
      float var18 = var6.getStringWidth(var12);
      float var19 = var6.getStringWidth(var13);
      float var20 = var6.getStringWidth(var14);
      float var21 = var18 + var19;
      float var22 = var21 + var20;
      String var23 = var12 + var13 + var14 + var15;
      float var24 = var6.getStringWidth(var23);
      var6.drawGlyphString(var23, var1, var2, (recoveredArg0, recoveredArg1, recoveredArg2, recoveredArg3) -> BedwarsUtils.colorizeBuildLimitGlyph(var18, var21, var22, var17, var16, recoveredArg0, recoveredArg1, recoveredArg2, (java.lang.Integer) recoveredArg3), true);
      return new float[]{var1, var2, var1 + var24, var2 + var6.getFontHeight()};
   }

   private void renderDragonBox(EntityDragon var1, float var2) {
      AxisAlignedBB var3 = this.getInterpolatedBoundingBox(var1, var2).expand(0.25, 0.25, 0.25);
      int var4 = ClientUtils.YVVZ(this.iwOl6(mc.thePlayer.getDistanceToEntity(var1)), 150);
      float var5 = (var4 >> 24 & 0xFF) / 255.0F;
      float var6 = (var4 >> 16 & 0xFF) / 255.0F;
      float var7 = (var4 >> 8 & 0xFF) / 255.0F;
      float var8 = (var4 & 0xFF) / 255.0F;
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);

      try {
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         GL11.glEnable(2848);
         GL11.glLineWidth(2.5F);
         GL11.glColor4f(var6, var7, var8, var5);
         RenderUtils.drawFilledAabb(var3, var6, var7, var8, 0.18F);
         RenderUtils.drawBoxOutline(var3);
      } finally {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glLineWidth(1.0F);
         GL11.glPopAttrib();
         GL11.glPopMatrix();
      }
   }

   private AxisAlignedBB getInterpolatedBoundingBox(Entity var1, float var2) {
      RenderManager var3 = mc.getRenderManager();
      double var4 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var2 - var3.viewerPosX;
      double var6 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var2 - var3.viewerPosY;
      double var8 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var2 - var3.viewerPosZ;
      AxisAlignedBB var10 = var1.getEntityBoundingBox();
      return new AxisAlignedBB(
         var10.minX - var1.posX + var4,
         var10.minY - var1.posY + var6,
         var10.minZ - var1.posZ + var8,
         var10.maxX - var1.posX + var4,
         var10.maxY - var1.posY + var6,
         var10.maxZ - var1.posZ + var8
      );
   }

   private EntityDragon DJVRwPh() {
      EntityDragon var1 = null;
      double var2 = Double.MAX_VALUE;

      for (Entity var5 : mc.theWorld.loadedEntityList) {
         if (var5 instanceof EntityDragon && !var5.isDead) {
            double var6 = mc.thePlayer.getDistanceSqToEntity(var5);
            if (var6 < var2) {
               var1 = (EntityDragon)var5;
               var2 = var6;
            }
         }
      }

      return var1;
   }

   private int iwOl6(double var1) {
      double var3 = Math.max(0.0, Math.min(1.0, var1 / 80.0));
      if (var3 < 0.5) {
         double var9 = var3 / 0.5;
         int var10 = (int)Math.round(165.0 * var9);
         return new Color(255, var10, 0).getRGB();
      } else {
         double var5 = (var3 - 0.5) / 0.5;
         int var7 = (int)Math.round(255.0 * (1.0 - var5));
         int var8 = (int)Math.round(165.0 + 90.0 * var5);
         return new Color(var7, var8, 0).getRGB();
      }
   }

   private int getBuildLimitHeightColor(int var1) {
      if (var1 <= 0) {
         return new Color(255, 76, 76).getRGB();
      } else if (var1 == 1) {
         return new Color(255, 146, 47).getRGB();
      } else if (var1 == 2) {
         return new Color(255, 222, 63).getRGB();
      } else {
         double var2 = Math.min(1.0, (var1 - 2) / 6.0);
         int var4 = (int)Math.round(255.0 - 150.0 * var2);
         int var5 = (int)Math.round(222.0 + 33.0 * var2);
         int var6 = (int)Math.round(63.0 + 58.0 * var2);
         return new Color(var4, var5, var6).getRGB();
      }
   }

   private IFont AThW() {
      return FontManager.getHudRenderer(this.finalKillFont.getResolvedFontName(), (float)this.finalKillHudScale.getInput());
   }

   private IFont getDragonHudFont() {
      return FontManager.getHudRenderer("Modern", (float)this.dragonHudScale.getInput());
   }

   private IFont getBuildLimitHudFont() {
      float var1 = (float)Math.max(0.5, Math.min(2.0, this.buildLimitHudScale.getInput()));
      return FontManager.getHudRenderer(this.buildLimitFont.getResolvedFontName(), var1);
   }

   private String vym5(Entry<String, Integer> var1) {
      return "§r" + (String)var1.getKey() + " §8- §f" + var1.getValue();
   }

   private void resetTrackedState() {
      this.lowHealthAlert.SQxv();
      this.playerEquipmentStates.clear();
      this.teamColorCache.clear();
      this.fdm.clear();
      this.upgradeAlertKeys.clear();
      this.selfTeamColorCode = "";
      this.lastFinalKillMessage = "";
      this.lastFinalKillTime = 0L;
   }

   private boolean isDuplicateFinalKill(String var1) {
      long var2 = System.currentTimeMillis();
      boolean var4 = var1.equals(this.lastFinalKillMessage) && var2 - this.lastFinalKillTime <= 750L;
      this.lastFinalKillMessage = var1;
      this.lastFinalKillTime = var2;
      return var4;
   }

   private void refreshFinalKillHudPosition() {
      this.updateFinalKillHudPosition(new ScaledResolution(mc));
   }

   private void updateFinalKillHudPosition(ScaledResolution var1) {
      if (Float.isNaN(this.WzS) || Float.isNaN(this.finalKillHudYRatio)) {
         this.WzS = 6.0F / Math.max(1.0F, (float)var1.getScaledWidth());
         this.finalKillHudYRatio = 52.0F / Math.max(1.0F, (float)var1.getScaledHeight());
      }

      this.VLaHy = this.WzS * var1.getScaledWidth();
      this.WDGbV = this.finalKillHudYRatio * var1.getScaledHeight();
   }

   private void refreshDragonHudPosition() {
      this.Olyd(new ScaledResolution(mc));
   }

   private void Olyd(ScaledResolution var1) {
      if (Float.isNaN(this.dragonHudXRatio) || Float.isNaN(this.dragonHudYRatio)) {
         this.dragonHudXRatio = this.getDragonHudCenteredX(var1) / Math.max(1.0F, (float)var1.getScaledWidth());
         this.dragonHudYRatio = 6.0F / Math.max(1.0F, (float)var1.getScaledHeight());
      }

      this.dragonHudX = this.dragonHudXRatio * var1.getScaledWidth();
      this.dragonHudY = this.dragonHudYRatio * var1.getScaledHeight();
   }

   private float getDragonHudCenteredX(ScaledResolution var1) {
      int var2 = this.getDragonHudFont().getStringWidth("Dragon: 50 blocks");
      return (var1.getScaledWidth() - var2) * 0.5F;
   }

   private void refreshBuildLimitHudPosition() {
      this.updateBuildLimitHudPosition(new ScaledResolution(mc));
   }

   private void updateBuildLimitHudPosition(ScaledResolution var1) {
      if (Float.isNaN(this.buildLimitHudXRatio) || Float.isNaN(this.buildLimitHudYRatio)) {
         this.buildLimitHudXRatio = 6.0F / Math.max(1.0F, (float)var1.getScaledWidth());
         this.buildLimitHudYRatio = 92.0F / Math.max(1.0F, (float)var1.getScaledHeight());
      }

      this.buildLimitHudX = this.buildLimitHudXRatio * var1.getScaledWidth();
      this.CRde = this.buildLimitHudYRatio * var1.getScaledHeight();
   }

   private String getTeamDisplayName(EntityPlayer var1) {
      String var2 = getPlayerTeamColor(var1);
      if ("c".equals(var2)) {
         return "§cRed Team§r";
      } else if ("9".equals(var2)) {
         return "§9Blue Team§r";
      } else if ("a".equals(var2)) {
         return "§aGreen Team§r";
      } else if ("e".equals(var2)) {
         return "§eYellow Team§r";
      } else if ("b".equals(var2)) {
         return "§bAqua Team§r";
      } else if ("f".equals(var2)) {
         return "§fWhite Team§r";
      } else if ("d".equals(var2)) {
         return "§dPink Team§r";
      } else {
         return "8".equals(var2) ? "§8Gray Team§r" : this.EXjVmn(var1);
      }
   }

   private String getSelfTeamColorPrefix() {
      if (this.selfTeamColorCode.length() == 0) {
         this.selfTeamColorCode = this.resolveSelfTeamColor();
      }

      return this.selfTeamColorCode.length() == 0 ? "" : "§" + this.selfTeamColorCode;
   }

   private boolean isFinalKillTeammate(BedwarsUtils$3 var1) {
      EntityPlayer var2 = mc.theWorld.getPlayerEntityByName(BedwarsUtils$3.jzp9(var1));
      return var2 == null ? BedwarsUtils$3.getColorPrefix(var1).equals(this.getSelfTeamColorPrefix()) : var2 == mc.thePlayer || ClientUtils.isTeammate(var2);
   }

   private String resolveSelfTeamColor() {
      String var1 = findColorCodeBeforeName(ClientUtils.RcNd(), mc.thePlayer.getName());
      if (var1.length() > 0) {
         return var1;
      } else {
         String var2 = mc.thePlayer.getDisplayName() == null ? "" : mc.thePlayer.getDisplayName().getFormattedText();
         String var3 = findColorCodeBeforeName(var2, mc.thePlayer.getName());
         return var3.length() > 0 ? var3 : getPlayerTeamColor(mc.thePlayer);
      }
   }

   private static String findColorCodeBeforeName(String var0, String var1) {
      if (var0 != null && var1 != null) {
         int var2 = var0.lastIndexOf(var1);
         if (var2 < 0) {
            return "";
         } else {
            for (int var3 = var2 - 2; var3 >= 0; var3--) {
               if (var0.charAt(var3) == 167 && var3 + 1 < var0.length()) {
                  String var4 = String.valueOf(Character.toLowerCase(var0.charAt(var3 + 1)));
                  if (isTeamColorCode(var4)) {
                     return var4;
                  }
               }
            }

            return "";
         }
      } else {
         return "";
      }
   }

   private static String getPlayerTeamColor(EntityPlayer var0) {
      if (var0 == null) {
         return "";
      } else {
         if (var0.getTeam() instanceof ScorePlayerTeam) {
            String var1 = ((ScorePlayerTeam)var0.getTeam()).getColorPrefix();
            String var2 = extractColorCode(var1);
            if (var2.length() > 0) {
               return var2;
            }
         }

         String var3 = var0.getDisplayName() == null ? "" : var0.getDisplayName().getFormattedText();
         return extractColorCode(var3);
      }
   }

   private String getOrCacheTeamColor(EntityPlayer var1) {
      String var2 = var1.getUniqueID().toString();
      String var3 = getPlayerTeamColor(var1);
      if (var3.length() > 0) {
         this.teamColorCache.put(var2, var3);
         return var3;
      } else {
         String var4 = this.teamColorCache.get(var2);
         return var4 == null ? "" : var4;
      }
   }

   private static String extractColorCode(String var0) {
      if (var0 == null) {
         return "";
      } else {
         for (int var1 = 0; var1 + 1 < var0.length(); var1++) {
            if (var0.charAt(var1) == 167) {
               String var2 = String.valueOf(Character.toLowerCase(var0.charAt(var1 + 1)));
               if (isTeamColorCode(var2)) {
                  return var2;
               }

               var1++;
            }
         }

         return "";
      }
   }

   private static String getItemRegistryName(ItemStack var0) {
      if (var0 != null && var0.getItem() != null && Item.itemRegistry.getNameForObject(var0.getItem()) != null) {
         String var1 = ((ResourceLocation)Item.itemRegistry.getNameForObject(var0.getItem())).toString();
         return var1.startsWith("minecraft:") ? var1.substring("minecraft:".length()) : var1;
      } else {
         return "";
      }
   }

   private static boolean isSwordItem(ItemStack var0) {
      String var1 = getItemRegistryName(var0);
      return var1.endsWith("_sword");
   }

   private static BedwarsUtils$3 parseFinalKillMessage(String var0) {
      String var1 = null;
      BedwarsUtils$3 var2 = null;
      int var3 = 0;

      while (var3 < var0.length()) {
         char var4 = var0.charAt(var3);
         if (isColorCodeAt(var0, var3)) {
            var1 = DKy3(var1, var0.charAt(var3 + 1));
            var3 += 2;
         } else if (!isUsernameChar(var4)) {
            var3++;
         } else {
            String var5 = var1;
            StringBuilder var6 = new StringBuilder();

            while (true) {
               if (var3 < var0.length()) {
                  if (isColorCodeAt(var0, var3)) {
                     var1 = DKy3(var1, var0.charAt(var3 + 1));
                     var3 += 2;
                     continue;
                  }

                  char var7 = var0.charAt(var3);
                  if (isUsernameChar(var7)) {
                     var6.append(var7);
                     var3++;
                     continue;
                  }
               }

               if (ARHcd(var5, var6.toString())) {
                  var2 = new BedwarsUtils$3(var5, var6.toString());
               }
               break;
            }
         }
      }

      return var2;
   }

   private static boolean isColorCodeAt(String var0, int var1) {
      return var0.charAt(var1) == 167 && var1 + 1 < var0.length();
   }

   private static String DKy3(String var0, char var1) {
      char var2 = Character.toLowerCase(var1);
      if ((var2 < '0' || var2 > '9') && (var2 < 'a' || var2 > 'f')) {
         return var2 == 'r' ? null : var0;
      } else {
         return "§" + var2;
      }
   }

   private static boolean isUsernameChar(char var0) {
      return var0 >= 'a' && var0 <= 'z' || var0 >= 'A' && var0 <= 'Z' || var0 >= '0' && var0 <= '9' || var0 == '_';
   }

   private static boolean ARHcd(String var0, String var1) {
      if (var0 != null && var1.length() >= 1 && var1.length() <= 16 && isKnownPlayerName(var1)) {
         String var2 = var0.substring(1);
         return isTeamColorCode(var2);
      } else {
         return false;
      }
   }

   private static boolean isKnownPlayerName(String var0) {
      if (mc != null && mc.getNetHandler() != null && var0 != null) {
         for (NetworkPlayerInfo var2 : mc.getNetHandler().getPlayerInfoMap()) {
            if (var2 != null && var2.getGameProfile() != null && var0.equalsIgnoreCase(var2.getGameProfile().getName())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean isTeamColorCode(String var0) {
      for (String var4 : ZMwa) {
         if (var4.equals(var0)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Alert Options",
            "Alerts",
            new String[]{"ping", "show distance", "ignore friends", "ignore teammates"},
            new String[]{"Ping", "Distance", "Ignore friends", "Ignore teammates"}
         ),
         buildSettingAlias(
            "Items",
            "Alerts",
            new String[]{
               "chainmail armor",
               "iron armor",
               "diamond armor",
               "iron sword",
               "diamond sword",
               "diamond pickaxe",
               "ender pearl",
               "bridge egg",
               "milk bucket",
               "fireball",
               "bow",
               "obsidian",
               "tnt",
               "rotationals",
               "sharpness",
               "protection"
            },
            new String[]{
               "Chainmail Armor",
               "Iron Armor",
               "Diamond Armor",
               "Iron Sword",
               "Diamond Sword",
               "Diamond Pickaxe",
               "Ender Pearl",
               "Bridge Egg",
               "Milk Bucket",
               "Fireball",
               "Bow",
               "Obsidian",
               "TNT",
               "Rotationals",
               "Sharpness",
               "Protection"
            }
         ),
         buildSettingAlias(
            "Final Kills",
            "Final kill HUD",
            new String[]{"teammates only", "final kill text shadow", "final kill background"},
            new String[]{"Teammates only", "Text shadow", "Background"}
         ),
         buildSettingAlias("Resource Timers", "Resource Timers", new String[]{"item nametag timers"}, new String[]{"Item Nametags"})
      );
   }

   private static int colorizeBuildLimitGlyph(float var0, float var1, float var2, int var3, int var4, char var5, float var6, float var7, Integer var8) {
      if (!(var6 < var0) && (!(var6 >= var1) || !(var6 < var2))) {
         return var6 < var1 ? -1 : var4;
      } else {
         return var3;
      }
   }

   public static String getItemRegistryKey(ItemStack var0) {
      return getItemRegistryName(var0);
   }

   static {
      Collections.addAll(quickShopCategoryNames, new String[]{
         "quick buy",
         "blocks",
         "melee",
         "armor",
         "tools",
         "ranged",
         "potions",
         "utility",
         "rotating items",
         "upgrades & traps"
      });
   }
}
