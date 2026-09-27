// Jade recovery: module: Anticheat (other); original class: jade.deps.eLz.XJMk36mhYU
package jade.client.module.other;

import jade.client.Jade;
import jade.client.common.ChatUtils;
import jade.client.common.ClientUtils;
import jade.client.common.EventBus;
import jade.client.common.ExternalChatOverlay;
import jade.client.common.Subscribe;
import jade.client.event.EntityJoinWorldEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.event.AnticheatFlagEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.AutoBlock;
import jade.client.module.combat.Backtrack;
import jade.client.module.combat.LagRange;
import jade.client.module.combat.Velocity;
import jade.client.module.movement.Timer;
import jade.client.module.other.anticheat.AnticheatBlacklist;
import jade.client.module.other.anticheat.AnticheatPlayerState;
import jade.client.module.player.Blink;
import jade.client.module.player.FakeLag;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.SliderSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

@ModuleInfo
public class Anticheat extends Module {
   private static final int zufhwp = 15;
   private static final double MAX_CLOSING_DISTANCE = 3.2;
   private static final double Ohuhs = 3.6;
   private static final double MIN_DISTANCE_GAIN = 0.55;
   private static final int LAG_RANGE_FLAG_WINDOW_TICKS = 8;
   private static final int tax = 4;
   private static final long FiG = 120L;
   private static final long LAG_RANGE_WINDOW_MS = 80L;
   private static final double LAG_RANGE_BASE_OFFSET = 0.75;
   private static final double LAG_RANGE_SPEED_THRESHOLD = 0.9;
   private static final int VELOCITY_IGNORE_TICKS = 12;
   private static final int sMdta = 4;
   private static final int RrLkig = 60;
   private static final float AIM_SNAP_MIN_ANGLE = 70.0F;
   private static final float AIM_SNAP_MAX_ANGLE = 35.0F;
   private static final int requireLeftMouse = 5;
   private SliderSetting flagInterval;
   private BooleanSetting addCheatersAsEnemy;
   private BooleanSetting ignoreTeammates;
   private BooleanSetting shouldPing;
   private BooleanSetting tabIcon;
   private BooleanSetting autoblock;
   private BooleanSetting scaffold;
   private BooleanSetting bridgeAssist;
   private BooleanSetting lagRange;
   private BooleanSetting kbDisplace;
   private HashMap<UUID, HashMap<BooleanSetting, Long>> lastFlagTimes = new HashMap<>();
   private HashMap<UUID, AnticheatPlayerState> playerStates = new HashMap<>();
   private final HashMap<String, Integer> flagCounts = new HashMap<>();
   private final HashMap<UUID, Integer> OGh = new HashMap<>();
   private final HashMap<UUID, Integer> lastUnsneakTick = new HashMap<>();
   private final HashMap<UUID, Integer> lastCountedSneakTick = new HashMap<>();
   private final HashMap<UUID, Boolean> sneakingStates = new HashMap<>();
   private final HashMap<UUID, Integer> lastSwingTicks = new HashMap<>();
   private final HashMap<UUID, Integer> FMuIxt = new HashMap<>();
   private final HashMap<UUID, Integer> VQhIua = new HashMap<>();
   private final HashMap<UUID, Integer> lastScaffoldPairTick = new HashMap<>();
   private final HashMap<UUID, List<Integer>> sneakDurations = new HashMap<>();
   private final HashMap<UUID, Integer> scaffoldDetectionCount = new HashMap<>();
   private final HashMap<UUID, Anticheat$2> pendingLagRangeWindows = new HashMap<>();
   private final HashMap<UUID, Anticheat$4> lagRangeSamples = new HashMap<>();
   private final HashMap<UUID, Integer> velocityIgnoreUntilTick = new HashMap<>();
   private final List<Anticheat$1> recentLagRangeFlags = new ArrayList<>();
   private final HashMap<UUID, Anticheat$5> aimSnapStates = new HashMap<>();
   private final HashMap<UUID, List<Anticheat$3>> XYIl = new HashMap<>();
   private final HashMap<UUID, Integer> Vs63 = new HashMap<>();
   private final HashMap<UUID, String> WwuL = new HashMap<>();
   private final HashMap<UUID, Integer> lastScaffoldTypeTick = new HashMap<>();
   private final List<Runnable> KaTp = new ArrayList<>();
   private final Set<UUID> pb3 = new HashSet<>();
   private long lastPingAt;
   private long lastPacketAt;
   private long suppressUntil;

   public Anticheat() {
      super("Anticheat", Category.other);
      this.registerSetting(new DescriptionSetting("Tries to detect cheaters."));
      this.registerSetting(
         this.flagInterval = new SliderSetting(
            "Flag interval",
            " second",
            20.0,
            0.0,
            60.0,
            1.0
         )
      );
      this.registerSetting(
         this.addCheatersAsEnemy = new BooleanSetting(
            "Add cheaters as enemy",
            false
         )
      );
      this.registerSetting(
         this.ignoreTeammates = new BooleanSetting(
            "Ignore teammates", false
         )
      );
      this.registerSetting(
         this.shouldPing = new BooleanSetting(
            "Should ping", true
         )
      );
      this.registerSetting(
         this.tabIcon = new BooleanSetting(
            "Tab Icon", true
         )
      );
      this.registerSetting(new DescriptionSetting("Detected cheats"));
      this.registerSetting(
         this.autoblock = new BooleanSetting(
            "Autoblock", true
         )
      );
      this.registerSetting(
         this.scaffold = new BooleanSetting(
            "Scaffold", true
         )
      );
      this.registerSetting(
         this.bridgeAssist = new BooleanSetting(
            "Bridge Assist", true
         )
      );
      this.registerSetting(this.lagRange = new BooleanSetting("Lag Range", true));
      this.registerSetting(
         this.kbDisplace = new BooleanSetting(
            "KB Displace", true
         )
      );
      this.initialized = true;
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent var1) {
      this.lastPacketAt = System.currentTimeMillis();
      if (ClientUtils.isInWorld() && !var1.isCanceled()) {
         this.handlePacket(var1.ys98());
      }
   }

   private void flagCheat(EntityPlayer var1, BooleanSetting var2) {
      this.flagCheatIfThreshold(var1, var2, 1);
   }

   private void flagCheatIfThreshold(EntityPlayer var1, BooleanSetting var2, int var3) {
      if (!this.mIlmsF()) {
         if (!ClientUtils.isFriend(var1) && (!this.ignoreTeammates.isToggled() || !ClientUtils.isTeammate(var1))) {
            if (this.addCheatersAsEnemy.isToggled()) {
               ClientUtils.addEnemy(var1.getName());
            }

            long var4 = System.currentTimeMillis();
            int var6 = this.incrementFlagCount(var1, var2);
            if (AnticheatBlacklist.recordFlag(var1.getUniqueID(), var1.getName(), var2.getName(), var6)) {
               this.pb3.add(var1.getUniqueID());
            }

            if (var6 >= var3) {
               if (this.flagInterval.getInput() > 0.0) {
                  HashMap var7 = this.lastFlagTimes.get(var1.getUniqueID());
                  if (var7 == null) {
                     var7 = new HashMap();
                  } else {
                     Long var8 = (Long)var7.get(var2);
                     if (var8 != null && ClientUtils.LvhY(var8, var4) <= this.flagInterval.getInput() * 1000.0) {
                        return;
                     }
                  }

                  var7.put(var2, var4);
                  this.lastFlagTimes.put(var1.getUniqueID(), var7);
               }

               String var10 = var1.getDisplayName().getFormattedText();
               String var11 = ClientUtils.formatGradientPrefix("Jade") + ClientUtils.translateColorCodes("&r ") + var10 + ClientUtils.translateColorCodes("&7 flagged &f" + var2.getName() + " &7&ox" + var6 + "&r");
               boolean var9 = this.shouldPing.isToggled() && ClientUtils.LvhY(this.lastPingAt, var4) >= 1500L;
               if (var9) {
                  this.lastPingAt = var4;
               }

               this.announceFlag(var1, var2.getName(), var6, var11, var9);
            }
         }
      }
   }

   private void announceFlag(final EntityPlayer var1, final String var2, final int var3, final String var4, final boolean var5) {
      synchronized (this.KaTp) {
         this.KaTp.add(new Runnable() {
            @Override
            public void run() {
               if (Anticheat.getMc().thePlayer != null) {
                  if (!ExternalChatOverlay.WBGA(var4)) {
                     Anticheat.getClientInstance().thePlayer.addChatMessage(new ChatComponentText(ChatUtils.resolveFlowText(var4)));
                  }

                  Anticheat.this.VqIc(var2, var1, var3);
                  if (var5) {
                     Anticheat.getMinecraftInstance().thePlayer.playSound("note.pling", 1.0F, 1.0F);
                  }
               }
            }
         });
      }
   }

   @Override
   public void onUpdate() {
      if (mc.thePlayer != null) {
         this.runPendingFlagTasks();
      }

      if (!mc.isSingleplayer() && mc.theWorld != null && mc.thePlayer != null) {
         for (EntityPlayer var2 : (java.lang.Iterable<EntityPlayer>) (java.lang.Iterable<?>) (new ArrayList(mc.theWorld.playerEntities))) {
            if (var2 != null && var2 != mc.thePlayer && !this.isIgnoredEntity(var2)) {
               this.announcePreviousFlags(var2);
               AnticheatPlayerState var3 = this.playerStates.get(var2.getUniqueID());
               if (var3 == null) {
                  var3 = new AnticheatPlayerState();
               }

               var3.updateFromPlayer(var2);
               this.recordPositionSample(var2, var3);
               this.runCheckDetectors(var2, var3);
               var3.updateServerPosition(var2);
               var3.recordSneakingState(var2);
               this.playerStates.put(var2.getUniqueID(), var3);
            }
         }
      }
   }

   @Subscribe
   public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
      if (var1.entity == mc.thePlayer) {
         this.playerStates.clear();
         this.lastFlagTimes.clear();
         this.flagCounts.clear();
         this.pb3.clear();
         synchronized (this.KaTp) {
            this.KaTp.clear();
         }

         this.resetTracking();
      }
   }

   @Override
   public void onDisable() {
      this.playerStates.clear();
      this.lastFlagTimes.clear();
      this.flagCounts.clear();
      this.pb3.clear();
      synchronized (this.KaTp) {
         this.KaTp.clear();
      }

      this.resetTracking();
      this.lastPingAt = 0L;
   }

   private void runPendingFlagTasks() {
      ArrayList var1;
      synchronized (this.KaTp) {
         if (this.KaTp.isEmpty()) {
            return;
         }

         var1 = new ArrayList<>(this.KaTp);
         this.KaTp.clear();
      }

      for (Runnable var3 : (java.lang.Iterable<Runnable>) (java.lang.Iterable<?>) (var1)) {
         var3.run();
      }
   }

   private int incrementFlagCount(EntityPlayer var1, BooleanSetting var2) {
      String var3 = var1.getUniqueID() + "|" + var2.getName();
      int var4 = this.flagCounts.getOrDefault(var3, 0) + 1;
      this.flagCounts.put(var3, var4);
      return var4;
   }

   private void announcePreviousFlags(EntityPlayer var1) {
      UUID var2 = var1.getUniqueID();
      if (this.tabIcon.isToggled() && !this.pb3.contains(var2) && ClientUtils.isRealPlayer(var1)) {
         List var3 = AnticheatBlacklist.getFlags(var2, var1.getName());
         if (!var3.isEmpty()) {
            this.pb3.add(var2);
            StringBuilder var4 = new StringBuilder();

            for (String var6 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var3)) {
               if (var4.length() > 0) {
                  var4.append(", ");
               }

               var4.append(var6);
            }

            String var7 = ClientUtils.formatGradientPrefix("Jade")
               + ClientUtils.translateColorCodes("&r ")
               + var1.getDisplayName().getFormattedText()
               + ClientUtils.translateColorCodes("&7 previously flagged: &f" + var4.toString());
            if (!ExternalChatOverlay.WBGA(var7)) {
               mc.thePlayer.addChatMessage(new ChatComponentText(ChatUtils.resolveFlowText(var7)));
            }
         }
      }
   }

   public boolean WfMt(UUID var1, String var2) {
      return this.isEnabled() && this.tabIcon.isToggled() && AnticheatBlacklist.isBlacklisted(var1, var2);
   }

   public boolean FnlAevK(EntityPlayer var1) {
      return var1 != null && this.WfMt(var1.getUniqueID(), var1.getName());
   }

   private void runCheckDetectors(EntityPlayer var1, AnticheatPlayerState var2) {
      if (this.autoblock.isToggled() && var2.autoblockTicks >= 10) {
         this.flagCheat(var1, this.autoblock);
      } else if (this.bridgeAssist.isToggled() && this.tLnqZ(var1, var2)) {
         this.flagCheatIfThreshold(var1, this.bridgeAssist, 3);
      } else if (this.scaffold.isToggled() && this.isScaffolding(var1, var2)) {
         this.flagCheat(var1, this.scaffold);
      }
   }

   private void recordPositionSample(EntityPlayer var1, AnticheatPlayerState var2) {
      UUID var3 = var1.getUniqueID();
      List var4 = this.XYIl.computeIfAbsent(var3, Anticheat::createPositionSampleList);
      var4.add(new Anticheat$3(var1.posX, var1.posY, var1.posZ, var2.QSdcI, var2.KIU, var2.deltaZ));

      while (var4.size() > 4) {
         var4.remove(0);
      }
   }

   private boolean isScaffolding(EntityPlayer var1, AnticheatPlayerState var2) {
      if (var1 == mc.thePlayer || var1.isRiding() || var1.hurtTime > 0) {
         return false;
      } else if (var1.isSwingInProgress && var1.getHeldItem() != null && var1.getHeldItem().getItem() instanceof ItemBlock) {
         List var3 = this.XYIl.get(var1.getUniqueID());
         if (var3 != null && var3.size() >= 4) {
            Anticheat$3 var4 = (Anticheat$3)var3.get(0);
            Anticheat$3 var5 = (Anticheat$3)var3.get(1);
            Anticheat$3 var6 = (Anticheat$3)var3.get(2);
            Anticheat$3 var7 = (Anticheat$3)var3.get(3);
            double var8 = var1.rotationPitch;
            double var10 = var2.QSdcI * 20.0;
            double var12 = var2.deltaZ * 20.0;
            double var14 = var10 * var10 + var12 * var12;
            double var16 = Math.sqrt(var14);
            double var18 = (var7.bDe - var6.bDe) * 20.0;
            double var20 = 50.0 * (var4.bDe - var5.bDe - var6.bDe + var7.bDe);
            double var22 = Math.abs(this.getMovementYawOffset(var2.QSdcI, var2.deltaZ, var1.rotationYaw));
            if (!(var8 <= 50.0) && !(var14 <= 9.0) && !(var14 >= 100.0) && !(var22 <= 165.0) && !(Math.abs(var20) < 0.001)) {
               String var24 = "";
               if (var18 >= 4.0 && var18 <= 15.0 && var20 > -25.0) {
                  var24 = "tower";
               } else if (var18 >= -1.0 && var18 <= 4.0 && Math.abs(var18) > 0.005 && var14 > 25.0) {
                  var24 = "horizontal";
               }

               if (var24.length() == 0) {
                  this.clearScaffoldStreak(var1.getUniqueID(), var1.ticksExisted);
                  return false;
               } else {
                  UUID var25 = var1.getUniqueID();
                  int var26 = var1.ticksExisted;
                  String var27 = this.WwuL.getOrDefault(var25, "");
                  int var28 = this.lastScaffoldTypeTick.getOrDefault(var25, 0);
                  int var29 = var27.equals(var24) && var26 - var28 < 40 ? this.Vs63.getOrDefault(var25, 0) + 1 : 0;
                  this.Vs63.put(var25, var29);
                  this.WwuL.put(var25, var24);
                  this.lastScaffoldTypeTick.put(var25, var26);
                  return var29 >= 1 || var24.equals("tower");
               }
            } else {
               this.clearScaffoldStreak(var1.getUniqueID(), var1.ticksExisted);
               return false;
            }
         } else {
            return false;
         }
      } else {
         this.clearScaffoldStreak(var1.getUniqueID(), var1.ticksExisted);
         return false;
      }
   }

   private void clearScaffoldStreak(UUID var1, int var2) {
      if (var2 - this.lastScaffoldTypeTick.getOrDefault(var1, 0) > 60) {
         this.Vs63.put(var1, 0);
      }
   }

   private void handlePacket(Packet<?> var1) {
      if (var1 instanceof S14PacketEntity) {
         this.ZEiqe((S14PacketEntity)var1);
      } else if (var1 instanceof S18PacketEntityTeleport) {
         this.FKzaA((S18PacketEntityTeleport)var1);
      } else if (var1 instanceof S19PacketEntityHeadLook) {
         this.gGoq((S19PacketEntityHeadLook)var1);
      } else if (var1 instanceof S0BPacketAnimation) {
         this.onAnimationPacket((S0BPacketAnimation)var1);
      } else if (var1 instanceof S19PacketEntityStatus) {
         this.LUOXsuM((S19PacketEntityStatus)var1);
      } else if (var1 instanceof S12PacketEntityVelocity) {
         this.AckQ((S12PacketEntityVelocity)var1);
      }
   }

   private void ZEiqe(S14PacketEntity var1) {
      if (mc.theWorld != null && mc.thePlayer != null) {
         Entity var2 = var1.getEntity(mc.theWorld);
         if (var2 instanceof EntityPlayer && var2 != mc.thePlayer && !this.isIgnoredEntity(var2)) {
            EntityPlayer var3 = (EntityPlayer)var2;
            double var4 = var3.posX;
            double var6 = var3.posY;
            double var8 = var3.posZ;
            double var10 = var4 + var1.func_149062_c() / 32.0;
            double var12 = var6 + var1.func_149061_d() / 32.0;
            double var14 = var8 + var1.func_149064_e() / 32.0;
            int var16 = mc.thePlayer.ticksExisted;
            if (this.lagRange.isToggled()) {
               this.checkLagRange(var3, var4, var6, var8, var10, var12, var14, var16);
            }

            if (this.kbDisplace.isToggled() && var1.func_149060_h()) {
               this.trackAimSnap(var3, this.packetYawToDegrees(var1.func_149066_f()), var16);
            }
         }
      }
   }

   private void FKzaA(S18PacketEntityTeleport var1) {
      if (mc.theWorld != null && mc.thePlayer != null) {
         Entity var2 = mc.theWorld.getEntityByID(var1.getEntityId());
         if (var2 instanceof EntityPlayer && var2 != mc.thePlayer && !this.isIgnoredEntity(var2)) {
            EntityPlayer var3 = (EntityPlayer)var2;
            double var4 = var3.posX;
            double var6 = var3.posY;
            double var8 = var3.posZ;
            double var10 = var1.getX() / 32.0;
            double var12 = var1.getY() / 32.0;
            double var14 = var1.getZ() / 32.0;
            int var16 = mc.thePlayer.ticksExisted;
            if (this.lagRange.isToggled()) {
               this.checkLagRange(var3, var4, var6, var8, var10, var12, var14, var16);
            }

            if (this.kbDisplace.isToggled()) {
               this.trackAimSnap(var3, this.packetYawToDegrees(var1.getYaw()), var16);
            }
         }
      }
   }

   private void AckQ(S12PacketEntityVelocity var1) {
      if (mc.theWorld != null && mc.thePlayer != null) {
         Entity var2 = mc.theWorld.getEntityByID(var1.getEntityID());
         if (var2 instanceof EntityPlayer && var2 != mc.thePlayer && !this.isIgnoredEntity(var2)) {
            this.markVelocityPacket((EntityPlayer)var2, mc.thePlayer.ticksExisted);
         }
      }
   }

   private void gGoq(S19PacketEntityHeadLook var1) {
      if (this.kbDisplace.isToggled() && mc.theWorld != null && mc.thePlayer != null) {
         Entity var2 = var1.getEntity(mc.theWorld);
         if (var2 instanceof EntityPlayer && var2 != mc.thePlayer && !this.isIgnoredEntity(var2)) {
            this.trackAimSnap((EntityPlayer)var2, this.packetYawToDegrees(var1.getYaw()), mc.thePlayer.ticksExisted);
         }
      }
   }

   private void onAnimationPacket(S0BPacketAnimation var1) {
      if (mc.theWorld != null && mc.thePlayer != null && var1.getAnimationType() == 0) {
         Entity var2 = mc.theWorld.getEntityByID(var1.getEntityID());
         if (var2 instanceof EntityPlayer && var2 != mc.thePlayer && !this.isIgnoredEntity(var2)) {
            EntityPlayer var3 = (EntityPlayer)var2;
            int var4 = mc.thePlayer.ticksExisted;
            this.FMuIxt.put(var3.getUniqueID(), var4);
            if (this.lagRange.isToggled() && this.AEQUGyc(var3, var4)) {
               this.flagCheat(var3, this.lagRange);
            }

            if (this.kbDisplace.isToggled() && this.isKillauraSnap(var3, var4)) {
               this.flagCheat(var3, this.kbDisplace);
            }
         }
      }
   }

   private void LUOXsuM(S19PacketEntityStatus var1) {
      if ((this.lagRange.isToggled() || this.kbDisplace.isToggled()) && mc.theWorld != null && mc.thePlayer != null && var1.getOpCode() == 2) {
         Entity var2 = var1.getEntity(mc.theWorld);
         if (var2 == mc.thePlayer) {
            int var3 = mc.thePlayer.ticksExisted;

            for (EntityPlayer var5 : (java.lang.Iterable<EntityPlayer>) (java.lang.Iterable<?>) (new ArrayList(mc.theWorld.playerEntities))) {
               if (var5 != null && var5 != mc.thePlayer && !this.isIgnoredEntity(var5)) {
                  if (this.kbDisplace.isToggled() && this.isKillauraSnap(var5, var3)) {
                     this.flagCheat(var5, this.kbDisplace);
                     return;
                  }

                  if (this.lagRange.isToggled() && this.AEQUGyc(var5, var3)) {
                     this.flagCheat(var5, this.lagRange);
                     return;
                  }
               }
            }
         }
      }
   }

   private void checkLagRange(EntityPlayer var1, double var2, double var4, double var6, double var8, double var10, double var12, int var14) {
      UUID var15 = var1.getUniqueID();
      if (this.isOtherLagModuleActive()) {
         this.pendingLagRangeWindows.remove(var15);
         this.lagRangeSamples.remove(var15);
         this.Wdzu1(var14);
      } else if (this.isWithinVelocityIgnoreWindow(var15, var14)) {
         this.pendingLagRangeWindows.remove(var15);
         this.lagRangeSamples.remove(var15);
      } else {
         long var16 = System.currentTimeMillis();
         Anticheat$4 var18 = this.lagRangeSamples.get(var15);
         if (var18 == null || var16 - var18.startedAt > 120L || var14 - var18.JrW > 4) {
            var18 = new Anticheat$4(var2, var4, var6, var16, var14);
            this.lagRangeSamples.put(var15, var18);
         }

         var18.sampleCount++;
         var18.IFCs6 = var8;
         var18.currentPosY = var10;
         var18.rfiyf = var12;
         var18.QRsI = var14;
         double var19 = Math.hypot(var8 - var2, var12 - var6);
         double var21 = Math.hypot(var8 - var18.ALa, var12 - var18.startPosZ);
         double var23 = Math.abs(var10 - var18.hfsX);
         long var25 = var16 - var18.startedAt;
         int var27 = this.getSpeedAmplifier(var1);
         int var28 = var27 > 0 ? 4 : 3;
         int var29 = var27 > 0 ? 5 : 4;
         double var30 = 0.75 + var27 * 0.15;
         boolean var32 = var19 > 0.9 + var27 * 0.15;
         boolean var33 = var18.sampleCount >= var28 && var25 <= 80L && var21 >= var30;
         boolean var34 = var18.sampleCount >= var29 && var25 <= 120L && var21 >= var30 + 0.1;
         boolean var35 = var32 || var33 || var34;
         this.recordLagRangeFlag(var15, var14, var35);
         if (var35) {
            if (this.hasMultipleLagRangeFlags(var14 - 4, var14)) {
               this.suppressUntil = var16 + 400L;
               this.pendingLagRangeWindows.clear();
            } else {
               double var36 = this.JfpXwqV(var18.ALa, var18.hfsX, var18.startPosZ, mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
               double var38 = this.JfpXwqV(var8, var10, var12, mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
               double var40 = var36 - var38;
               if (var23 <= 1.2 && (var32 || var19 <= 0.75 + var27 * 0.1) && var36 > 3.6 && var38 <= 3.2 && var40 >= 0.55) {
                  this.pendingLagRangeWindows.put(var15, new Anticheat$2(var14, var14 + 8));
                  int var42 = this.FMuIxt.getOrDefault(var15, Integer.MIN_VALUE);
                  if (var14 - var42 <= 2 && this.AEQUGyc(var1, var14)) {
                     this.flagCheat(var1, this.lagRange);
                  }
               }
            }
         }
      }
   }

   private boolean AEQUGyc(EntityPlayer var1, int var2) {
      if (this.isOtherLagModuleActive()) {
         this.pendingLagRangeWindows.remove(var1.getUniqueID());
         return false;
      } else if (this.isWithinVelocityIgnoreWindow(var1.getUniqueID(), var2)) {
         this.pendingLagRangeWindows.remove(var1.getUniqueID());
         return false;
      } else {
         Anticheat$2 var3 = this.pendingLagRangeWindows.get(var1.getUniqueID());
         if (var3 == null) {
            return false;
         } else {
            this.pendingLagRangeWindows.remove(var1.getUniqueID());
            return var2 <= var3.ijRlbV && !this.hasMultipleLagRangeFlags(var3.startTick, var2);
         }
      }
   }

   private void markVelocityPacket(EntityPlayer var1, int var2) {
      this.velocityIgnoreUntilTick.put(var1.getUniqueID(), var2 + 12);
   }

   private boolean isWithinVelocityIgnoreWindow(UUID var1, int var2) {
      return var2 <= this.velocityIgnoreUntilTick.getOrDefault(var1, Integer.MIN_VALUE);
   }

   private int getSpeedAmplifier(EntityPlayer var1) {
      if (!var1.isPotionActive(Potion.moveSpeed)) {
         return 0;
      } else {
         PotionEffect var2 = var1.getActivePotionEffect(Potion.moveSpeed);
         return var2 == null ? 1 : var2.getAmplifier() + 1;
      }
   }

   private void trackAimSnap(EntityPlayer var1, float var2, int var3) {
      UUID var4 = var1.getUniqueID();
      Anticheat$5 var5 = this.aimSnapStates.computeIfAbsent(var4, Anticheat::createAimSnapState);
      float var6 = this.getYawToPlayer(var1);
      float var7 = Math.abs(MathHelper.wrapAngleTo180_float(var2 - var6));
      float var8 = var5.hasYawSample ? var5.lastYawOffset : Math.abs(MathHelper.wrapAngleTo180_float(var1.rotationYaw - var6));
      boolean var9 = var7 <= 35.0F;
      boolean var10 = var7 >= 70.0F && var8 <= 35.0F;
      if (var10) {
         var5.snapStartTick = var3;
         var5.awaitingAlign = true;
         var5.alignDetected = false;
      } else if (var5.awaitingAlign && var9 && var3 - var5.snapStartTick <= 5) {
         var5.alignTick = var3;
         var5.awaitingAlign = false;
         var5.alignDetected = true;
      }

      if (var5.awaitingAlign && var3 - var5.snapStartTick > 5) {
         var5.awaitingAlign = false;
      }

      if (var5.alignDetected && var3 - var5.alignTick > 5) {
         var5.alignDetected = false;
      }

      var5.lastYawOffset = var7;
      var5.hasYawSample = true;
   }

   private boolean isKillauraSnap(EntityPlayer var1, int var2) {
      Anticheat$5 var3 = this.aimSnapStates.get(var1.getUniqueID());
      if (var3 != null && var3.alignDetected) {
         int var4 = this.FMuIxt.getOrDefault(var1.getUniqueID(), Integer.MIN_VALUE);
         boolean var5 = var4 >= var3.snapStartTick && var2 - var4 <= 7;
         boolean var6 = this.isPlayerWithinRadius(var1, 4.2F);
         boolean var7 = var1.getHeldItem() != null && var1.getHeldItem().getItem() instanceof ItemSword;
         boolean var8 = var2 - var3.alignTick <= 7 && var5 && var6 && var7;
         if (var8 || var2 - var3.alignTick > 5) {
            var3.alignDetected = false;
         }

         return var8;
      } else {
         return false;
      }
   }

   private boolean isPlayerWithinRadius(EntityPlayer var1, float var2) {
      if (mc.theWorld == null) {
         return false;
      } else {
         float var3 = var2 * var2;

         for (EntityPlayer var5 : (java.lang.Iterable<EntityPlayer>) (java.lang.Iterable<?>) (new ArrayList(mc.theWorld.playerEntities))) {
            if (var5 != null && var5 != var1 && !this.isIgnoredEntity(var5) && var1.getDistanceSqToEntity(var5) <= var3) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean tLnqZ(EntityPlayer var1, AnticheatPlayerState var2) {
      UUID var3 = var1.getUniqueID();
      int var4 = var1.ticksExisted;
      boolean var5 = var1.isSneaking();
      boolean var6 = this.sneakingStates.getOrDefault(var3, false);
      if (var5 && !var6) {
         this.OGh.put(var3, var4);
      }

      List var7 = this.sneakDurations.computeIfAbsent(var3, Anticheat::createSneakDurationList);
      if (!var5 && var6) {
         this.lastUnsneakTick.put(var3, var4);
         int var8 = this.OGh.getOrDefault(var3, var4 - 1);
         var7.add(0, var4 - var8);

         while (var7.size() > 10) {
            var7.remove(var7.size() - 1);
         }
      }

      this.sneakingStates.put(var3, var5);
      if (var1.isSwingInProgress && var1.prevSwingProgress != var1.swingProgress) {
         this.lastSwingTicks.put(var3, var4);
      }

      int var20 = this.OGh.getOrDefault(var3, 0);
      int var9 = this.lastUnsneakTick.getOrDefault(var3, 0);
      int var10 = this.lastSwingTicks.getOrDefault(var3, Integer.MIN_VALUE);
      int var11 = var9 - var20;
      boolean var12 = !var5 && var9 > 0 && var4 - var9 <= 3 && this.lastCountedSneakTick.getOrDefault(var3, Integer.MIN_VALUE) != var9;
      boolean var13 = var1.getHeldItem() != null && var1.getHeldItem().getItem() instanceof ItemBlock;
      boolean var14 = var1.rotationPitch >= 70.0F;
      boolean var15 = var11 >= 1 && var11 <= 2;
      boolean var16 = var10 >= var9 - 1 && var10 <= var9 + 1;
      if (var14 && var13 && var1.onGround) {
         if (var12 && var15 && var16) {
            this.lastCountedSneakTick.put(var3, var9);
            int var17 = this.lastScaffoldPairTick.getOrDefault(var3, 0);
            int var18 = this.VQhIua.getOrDefault(var3, 0);
            if (var4 - var17 > 15) {
               var18 = 0;
            }

            var18++;
            this.lastScaffoldPairTick.put(var3, var4);
            this.VQhIua.put(var3, var18);
            if (var18 >= 2) {
               int var19 = this.scaffoldDetectionCount.getOrDefault(var3, 0) + 1;
               this.scaffoldDetectionCount.put(var3, var19);
               this.VQhIua.put(var3, 0);
               return true;
            }
         } else {
            if (var12 && var10 == var4 && var9 >= var4 - 1 && var20 == var9 - 1) {
               this.lastCountedSneakTick.put(var3, var9);
               this.scaffoldDetectionCount.put(var3, this.scaffoldDetectionCount.getOrDefault(var3, 0) + 1);
               this.VQhIua.put(var3, 0);
               return true;
            }

            this.kYbxJ(var3, var4);
         }

         return false;
      } else {
         this.kYbxJ(var3, var4);
         return false;
      }
   }

   private void kYbxJ(UUID var1, int var2) {
      if (var2 - this.lastScaffoldPairTick.getOrDefault(var1, 0) > 15) {
         this.VQhIua.put(var1, 0);
         this.scaffoldDetectionCount.put(var1, 0);
      }
   }

   private boolean mIlmsF() {
      return Jade.getModuleManager().getModule(Timer.class) != null && Jade.getModuleManager().getModule(Timer.class).DZshJt();
   }

   private boolean isIgnoredEntity(Entity var1) {
      if (var1 == null) {
         return true;
      } else if (Jade.getModuleManager().getModule(AntiBot.class) != null && Jade.getModuleManager().getModule(AntiBot.class).isEnabled()) {
         try {
            return AntiBot.shouldHideEntity(var1);
         } catch (RuntimeException var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean isOtherLagModuleActive() {
      long var1 = System.currentTimeMillis();
      if (var1 >= this.suppressUntil && !this.mIlmsF()) {
         boolean var3 = false;
         if (Jade.getModuleManager().getModule(Blink.class) != null && Jade.getModuleManager().getModule(Blink.class).isEnabled()) {
            var3 = true;
         }

         if (Jade.getModuleManager().getModule(FakeLag.class) != null && Jade.getModuleManager().getModule(FakeLag.class).isEnabled()) {
            var3 = true;
         }

         if (Jade.getModuleManager().getModule(Velocity.class) != null && Jade.getModuleManager().getModule(Velocity.class).isInboundDelaying()) {
            var3 = true;
         }

         if (Jade.getModuleManager().getModule(LagRange.class) != null && Jade.getModuleManager().getModule(LagRange.class).isLagging()) {
            var3 = true;
         }

         Backtrack var4 = Jade.getModuleManager().getModule(Backtrack.class);
         if (var4 != null && var4.isLagging()) {
            var3 = true;
         }

         AutoBlock var5 = Jade.getModuleManager().getModule(AutoBlock.class);
         if (var5 != null && var5.isAutoBlockActive()) {
            var3 = true;
         }

         if (var3) {
            this.suppressUntil = var1 + 400L;
         }

         return var3;
      } else {
         return true;
      }
   }

   private void recordLagRangeFlag(UUID var1, int var2, boolean var3) {
      this.Wdzu1(var2);
      this.recentLagRangeFlags.add(new Anticheat$1(var1, var2, var3));
      if (var3) {
         HashSet var4 = new HashSet();

         for (Anticheat$1 var6 : this.recentLagRangeFlags) {
            if (var6.zV6) {
               var4.add(var6.uvs);
            }
         }

         if (var4.size() >= 2) {
            this.suppressUntil = System.currentTimeMillis() + 400L;
            this.pendingLagRangeWindows.clear();
         }
      }
   }

   private void Wdzu1(int var1) {
      for (int var2 = this.recentLagRangeFlags.size() - 1; var2 >= 0; var2--) {
         if (var1 - this.recentLagRangeFlags.get(var2).htJ > 4) {
            this.recentLagRangeFlags.remove(var2);
         }
      }
   }

   private boolean hasMultipleLagRangeFlags(int var1, int var2) {
      HashSet var3 = new HashSet();

      for (Anticheat$1 var5 : this.recentLagRangeFlags) {
         if (var5.zV6 && var5.htJ >= var1 - 4 && var5.htJ <= var2) {
            var3.add(var5.uvs);
         }
      }

      return var3.size() >= 2;
   }

   private float getYawToPlayer(EntityPlayer var1) {
      if (mc.thePlayer == null) {
         return var1.rotationYaw;
      } else {
         double var2 = mc.thePlayer.posX - var1.posX;
         double var4 = mc.thePlayer.posZ - var1.posZ;
         return (float)(Math.atan2(var4, var2) * 180.0 / Math.PI) - 90.0F;
      }
   }

   private float packetYawToDegrees(int var1) {
      return var1 * 360.0F / 256.0F;
   }

   private double getMovementYawOffset(double var1, double var3, float var5) {
      if (Math.abs(var1) < 1.0E-5 && Math.abs(var3) < 1.0E-5) {
         return 0.0;
      } else {
         double var6 = Math.toDegrees(Math.atan2(var3, var1)) - 90.0;
         double var8 = (var6 - var5) % 360.0;
         if (var8 < -180.0) {
            var8 += 360.0;
         } else if (var8 > 180.0) {
            var8 -= 360.0;
         }

         return var8;
      }
   }

   private double JfpXwqV(double var1, double var3, double var5, double var7, double var9, double var11) {
      double var13 = var1 - var7;
      double var15 = var3 - var9;
      double var17 = var5 - var11;
      return Math.sqrt(var13 * var13 + var15 * var15 + var17 * var17);
   }

   private void resetTracking() {
      this.OGh.clear();
      this.lastUnsneakTick.clear();
      this.lastCountedSneakTick.clear();
      this.sneakingStates.clear();
      this.lastSwingTicks.clear();
      this.FMuIxt.clear();
      this.VQhIua.clear();
      this.lastScaffoldPairTick.clear();
      this.sneakDurations.clear();
      this.scaffoldDetectionCount.clear();
      this.pendingLagRangeWindows.clear();
      this.lagRangeSamples.clear();
      this.recentLagRangeFlags.clear();
      this.aimSnapStates.clear();
      this.XYIl.clear();
      this.Vs63.clear();
      this.WwuL.clear();
      this.lastScaffoldTypeTick.clear();
      this.velocityIgnoreUntilTick.clear();
      this.suppressUntil = 0L;
   }

   public void reportFlag(String var1, Entity var2) {
      this.VqIc(var1, var2, 1);
   }

   public void VqIc(String var1, Entity var2, int var3) {
      EventBus.post(new AnticheatFlagEvent(var1, var2, var3));
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Actions", "Mode", new String[]{"add cheaters as enemy", "should ping", "tab icon"}, new String[]{"Add as enemy", "Ping", "Tab Icon"}),
         buildSettingAlias("Target", "Actions", new String[]{"ignore teammates"}, new String[]{"Ignore teammates"}),
         buildSettingAlias(
            "Checks",
            "Target",
            new String[]{"autoblock", "scaffold", "bridge assist", "lag range", "kb displace"},
            new String[]{"Autoblock", "Scaffold", "Bridge Assist", "Lag Range", "KB Displace"}
         )
      );
   }

   private static List createSneakDurationList(UUID var0) {
      return new ArrayList<>();
   }

   private static Anticheat$5 createAimSnapState(UUID var0) {
      return new Anticheat$5();
   }

   private static List createPositionSampleList(UUID var0) {
      return new ArrayList<>();
   }

   public static Minecraft getMc() {
      return mc;
   }

   public static Minecraft getClientInstance() {
      return mc;
   }

   public static Minecraft getMinecraftInstance() {
      return mc;
   }
}
