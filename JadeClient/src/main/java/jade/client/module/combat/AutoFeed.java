// Jade recovery: module: Auto Feed (combat); original class: jade.deps.eLz.r7rEnm8
package jade.client.module.combat;

import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.Subscribe;
import jade.client.event.AttackEntityEvent;
import jade.client.event.ClickMouseEvent;
import jade.client.event.DisconnectEvent;
import jade.client.event.LoadWorldEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.PlayerAttackEvent;
import jade.client.event.TickStartEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.combat.autofeed.DamageCalculator;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.MultiSelectSetting;
import jade.client.setting.SliderSetting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo(aliases = "AutoFeed")
public class AutoFeed extends Module {
   private static final double jnz = 400.0;
   private static final long ATTACK_DAMAGE_WINDOW_MS = 1500L;
   private static final long DAMAGE_OBSERVATION_WINDOW_MS = 15000L;
   private static final float omrZr = 0.01F;
   private final SliderSetting minimumHealth;
   private final SliderSetting safetyMargin;
   private final MultiSelectSetting multiSelectSetting;
   private final BooleanSetting onlyInBedwars;
   private final BooleanSetting onlyWhilstTeammateNear;
   private final Map<Integer, AutoFeed$2> healthTrackers = new HashMap<>();
   private final Map<Integer, AutoFeed$1> rrGx = new HashMap<>();

   public AutoFeed() {
      super("Auto Feed", Category.combat);
      this.registerSetting(new DescriptionSetting("Stops attacks that would finish an enemy."));
      this.registerSetting(
         this.minimumHealth = new SliderSetting(
            "Minimum health", " HP", 1.0, 0.5, 10.0, 0.5
         )
      );
      this.registerSetting(
         this.safetyMargin = new SliderSetting(
            "Safety margin", " HP", 0.5, 0.0, 3.0, 0.5
         )
      );
      this.onlyInBedwars = new BooleanSetting(
         "Only in Bedwars", true
      );
      this.onlyWhilstTeammateNear = new BooleanSetting(
         "Only whilst teammate near",
         true
      );
      String var10004 = "Conditionals";
      BooleanSetting[] var10005 = new BooleanSetting[]{this.onlyInBedwars, null};
      var10005[1] = this.onlyWhilstTeammateNear;
      this.registerSetting(this.multiSelectSetting = new MultiSelectSetting(var10004, var10005));
      this.onlyInBedwars.visible = false;
      this.onlyWhilstTeammateNear.visible = false;
      this.registerSetting(this.onlyInBedwars);
      this.registerSetting(this.onlyWhilstTeammateNear);
   }

   @Override
   public String getInfo() {
      return formatTrimmedDouble(this.minimumHealth.getInput()) + " HP";
   }

   @Override
   public void onEnable() {
      this.clearTrackingData();
   }

   @Override
   public void onDisable() {
      this.clearTrackingData();
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onClickMouse(ClickMouseEvent var1) {
      Entity var2 = var1.movingObjectPosition == null ? null : var1.movingObjectPosition.entityHit;
      if (var2 instanceof EntityPlayer && this.qImi((EntityPlayer)var2)) {
         var1.setCanceled(true);
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void preventLethalAttack(AttackEntityEvent var1) {
      if (var1.entityPlayer == mc.thePlayer && var1.entity instanceof EntityPlayer && this.qImi((EntityPlayer)var1.entity)) {
         var1.setCanceled(true);
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onAttackEntity(AttackEntityEvent var1) {
      if (!var1.isCanceled() && var1.entityPlayer == mc.thePlayer && var1.entity instanceof EntityPlayer) {
         this.recordAttackDamage((EntityPlayer)var1.entity);
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onPlayerAttack(PlayerAttackEvent var1) {
      if (var1.entityPlayer == mc.thePlayer && var1.entity instanceof EntityPlayer && this.qImi((EntityPlayer)var1.entity)) {
         var1.setCanceled(true);
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void preventLethalAttackPacket(PacketSendEvent var1) {
      if (ClientUtils.isInWorld()) {
         Packet var2 = var1.ys98();
         if (var2 instanceof C02PacketUseEntity) {
            C02PacketUseEntity var3 = (C02PacketUseEntity)var2;
            if (var3.getAction() != Action.ATTACK) {
               return;
            }

            Entity var4 = var3.getEntityFromWorld(mc.theWorld);
            if (var4 instanceof EntityPlayer && this.qImi((EntityPlayer)var4)) {
               var1.setCanceled(true);
            }
         } else if (var2 instanceof C0APacketAnimation) {
            EntityPlayer var5 = this.CKAiU();
            if (var5 != null && this.qImi(var5)) {
               var1.setCanceled(true);
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onPacketSend(PacketSendEvent var1) {
      if (!var1.isCanceled() && ClientUtils.isInWorld() && var1.ys98() instanceof C02PacketUseEntity) {
         C02PacketUseEntity var2 = (C02PacketUseEntity)var1.ys98();
         if (var2.getAction() == Action.ATTACK) {
            Entity var3 = var2.getEntityFromWorld(mc.theWorld);
            if (var3 instanceof EntityPlayer) {
               this.recordAttackDamage((EntityPlayer)var3);
            }
         }
      }
   }

   @Subscribe
   public void onTickStart(TickStartEvent var1) {
      this.updateHealthTrackers();
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent var1) {
      this.clearTrackingData();
   }

   @Subscribe
   public void onDisconnect(DisconnectEvent var1) {
      this.clearTrackingData();
   }

   public boolean wouldCancelAttack(EntityPlayer var1) {
      return this.isEnabled() && this.qImi(var1);
   }

   private boolean qImi(EntityPlayer var1) {
      if (this.areConditionsMet() && this.isValidEnemy(var1)) {
         float var2 = DamageCalculator.getAttackDamage(mc.thePlayer, var1);
         float var3 = this.MFJVkwW(var1, var2);
         return DamageCalculator.isLethalDamage(var1.getHealth(), var1.getAbsorptionAmount(), var3, (float)this.minimumHealth.getInput(), (float)this.safetyMargin.getInput());
      } else {
         return false;
      }
   }

   private float MFJVkwW(EntityPlayer var1, float var2) {
      if (ClientUtils.getBedWarsBoardType() == 2) {
         AutoFeed$2 var3 = this.healthTrackers.get(var1.getEntityId());
         long var4 = System.currentTimeMillis();
         if (var3 != null && AutoFeed$2.getObservedDamageRatio(var3) > 0.0F && var4 - AutoFeed$2.getLastDamageObservationTime(var3) <= 15000L && AutoFeed$2.getSampleArmorPoints(var3) == var1.getTotalArmorValue()) {
            return DamageCalculator.applyObservedDamageRatio(var2, AutoFeed$2.getObservedDamageRatio(var3));
         }
      }

      return DamageCalculator.applyDamageReduction(var2, var1);
   }

   private boolean areConditionsMet() {
      if (ClientUtils.isInWorld() && !mc.thePlayer.isDead) {
         return this.onlyInBedwars.isToggled() && ClientUtils.getBedWarsBoardType() != 2 ? false : !this.onlyWhilstTeammateNear.isToggled() || this.isTeammateNearby();
      } else {
         return false;
      }
   }

   private boolean isTeammateNearby() {
      for (EntityPlayer var2 : mc.theWorld.playerEntities) {
         if (var2 != mc.thePlayer && !var2.isDead && var2.deathTime == 0 && ClientUtils.isTeammate(var2) && mc.thePlayer.getDistanceSqToEntity(var2) <= 400.0) {
            return true;
         }
      }

      return false;
   }

   private boolean isValidEnemy(EntityPlayer var1) {
      return var1 != null && var1 != mc.thePlayer && !var1.isDead && var1.deathTime == 0 && !ClientUtils.isTeammate(var1);
   }

   private EntityPlayer CKAiU() {
      MovingObjectPosition var1 = mc.objectMouseOver;
      return var1 != null && var1.typeOfHit == MovingObjectType.ENTITY && var1.entityHit instanceof EntityPlayer ? (EntityPlayer)var1.entityHit : null;
   }

   private void recordAttackDamage(EntityPlayer var1) {
      if (this.areConditionsMet() && this.isValidEnemy(var1)) {
         long var2 = System.currentTimeMillis();
         AutoFeed$1 var4 = this.rrGx.get(var1.getEntityId());
         if (var4 == null || var2 - AutoFeed$1.getAttackTime(var4) >= 100L) {
            float var5 = DamageCalculator.getAttackDamage(mc.thePlayer, var1);
            if (var5 > 0.0F) {
               this.rrGx.put(var1.getEntityId(), new AutoFeed$1(var5, var2));
            }
         }
      }
   }

   private void updateHealthTrackers() {
      if (!ClientUtils.isInWorld()) {
         this.clearTrackingData();
      } else {
         long var1 = System.currentTimeMillis();
         HashSet var3 = new HashSet();

         for (EntityPlayer var5 : mc.theWorld.playerEntities) {
            if (var5 != mc.thePlayer) {
               int var6 = var5.getEntityId();
               var3.add(var6);
               float var7 = var5.getHealth() + var5.getAbsorptionAmount();
               AutoFeed$2 var8 = this.healthTrackers.get(var6);
               if (var8 == null) {
                  this.healthTrackers.put(var6, new AutoFeed$2(var7));
               } else {
                  float var9 = AutoFeed$2.TBJf18(var8) - var7;
                  AutoFeed$1 var10 = this.rrGx.get(var6);
                  if (var9 > 0.01F && var10 != null && var1 - AutoFeed$1.getAttackTime(var10) <= 1500L && AutoFeed$1.getExpectedDamage(var10) > 0.0F) {
                     AutoFeed$2.setObservedDamageRatio(var8, Math.min(1.0F, var9 / AutoFeed$1.getExpectedDamage(var10)));
                     AutoFeed$2.setLastDamageObservationTime(var8, var1);
                     AutoFeed$2.kcJs(var8, var5.getTotalArmorValue());
                     this.rrGx.remove(var6);
                  }

                  AutoFeed$2.setPreviousHealthTotal(var8, var7);
               }
            }
         }

         Iterator var11 = this.healthTrackers.entrySet().iterator();

         while (var11.hasNext()) {
            if (!var3.contains(((Entry)var11.next()).getKey())) {
               var11.remove();
            }
         }

         Iterator var12 = this.rrGx.entrySet().iterator();

         while (var12.hasNext()) {
            Entry var13 = (Entry)var12.next();
            if (!var3.contains(var13.getKey()) || var1 - AutoFeed$1.getAttackTime((AutoFeed$1)var13.getValue()) > 1500L) {
               var12.remove();
            }
         }
      }
   }

   private void clearTrackingData() {
      this.healthTrackers.clear();
      this.rrGx.clear();
   }

   private static String formatTrimmedDouble(double var0) {
      return var0 == Math.rint(var0) ? Integer.toString((int)var0) : Double.toString(var0);
   }
}
