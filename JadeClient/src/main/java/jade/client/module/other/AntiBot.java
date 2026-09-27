// Jade recovery: module: Anti Bot (other); original class: jade.deps.eLz.BhH3R9Sd
package jade.client.module.other;

import jade.client.Jade;
import jade.client.common.PlayerListTracker;
import jade.client.common.Subscribe;
import jade.client.event.LoadWorldEvent;
import jade.client.event.PacketReceiveEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.Freecam;
import jade.client.setting.BooleanSetting;

import jade.inject.InjectionAgent;
import jade.mixin.impl.accessor.IAccessorS14PacketEntity;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;

@ModuleInfo
public class AntiBot extends Module {
   private static final int ECzYhh = 10;
   private static BooleanSetting tabList;
   private static BooleanSetting moveCheck;
   private static final Set<Integer> DHoht = ConcurrentHashMap.newKeySet();

   public AntiBot() {
      super("Anti Bot", Category.other, 0);
      this.registerSetting(
         tabList = new BooleanSetting("Tab list", false)
      );
      this.registerSetting(
         moveCheck = new BooleanSetting(
            "Move Check", true
         )
      );
      this.initialized = true;
   }

   @Override
   public boolean isEnabledByDefault() {
      return true;
   }

   @Override
   public void onEnable() {
      DHoht.clear();
   }

   @Override
   public void onDisable() {
      DHoht.clear();
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent var1) {
      DHoht.clear();
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent var1) {
      Packet var2 = var1.ys98();
      if (var2 instanceof S14PacketEntity) {
         S14PacketEntity var3 = (S14PacketEntity)var2;
         if (var3.func_149062_c() != 0 || var3.func_149061_d() != 0 || var3.func_149064_e() != 0) {
            DHoht.add(((IAccessorS14PacketEntity)var3).getEntityId());
         }
      } else if (var2 instanceof S13PacketDestroyEntities) {
         for (int var6 : ((S13PacketDestroyEntities)var2).getEntityIDs()) {
            DHoht.remove(var6);
         }
      }
   }

   public static boolean shouldHideEntity(Entity var0) {
      if (!Jade.getModuleManager().getModule(AntiBot.class).isEnabled()) {
         return false;
      } else if (Freecam.cameraEntity != null && Freecam.cameraEntity == var0) {
         return flagBotByReason("freecam");
      } else if (var0 != null && var0 instanceof EntityPlayer) {
         EntityPlayer var1 = (EntityPlayer)var0;
         if (var1 == mc.thePlayer) {
            return reportBotDetection(var1, false, "local_player");
         } else if (moveCheck.isToggled() && !DHoht.contains(var1.getEntityId())) {
            return reportBotDetection(var1, true, "no_movement_packet");
         } else if (var1.isDead) {
            return reportBotDetection(var1, true, "dead");
         } else if (var1.getName().isEmpty()) {
            return reportBotDetection(var1, true, "empty_name");
         } else {
            if (InjectionAgent.isBadlionRuntime()) {
               String var2 = var1.getDisplayName() == null ? "" : var1.getDisplayName().getUnformattedText();
               if (var2.contains("[NPC]")) {
                  return reportBotDetection(var1, true, "npc_display_marker");
               }
            }

            if (tabList.isToggled() && !isListedInTab(var1)) {
               return reportBotDetection(var1, true, "not_in_tablist");
            } else if (var1.getHealth() != 20.0F && var1.getName().startsWith("§c")) {
               return reportBotDetection(var1, true, "red_name_health");
            } else {
               if (var1.maxHurtTime == 0) {
                  if (var1.getHealth() == 20.0F) {
                     String var3 = var1.getDisplayName().getUnformattedText();
                     if (var3.length() == 10 && var3.charAt(0) != 167 && var1.ticksExisted >= 10 && isMissingFromTabList(var1)) {
                        return reportBotDetection(var1, true, "ten_character_non_tab_player");
                     }

                     if (var3.length() == 12 && var1.isPlayerSleeping() && var3.charAt(0) == 167) {
                        return reportBotDetection(var1, true, "sleeping_name");
                     }

                     if (var3.length() >= 7 && var3.charAt(2) == '[' && var3.charAt(3) == 'N' && var3.charAt(6) == ']') {
                        return reportBotDetection(var1, true, "npc_marker");
                     }

                     if (var1.getName().contains(" ")) {
                        return reportBotDetection(var1, true, "space_in_name");
                     }
                  } else if (var1.isInvisible()) {
                     String var4 = var1.getDisplayName().getUnformattedText();
                     if (var4.length() >= 3 && var4.charAt(0) == 167 && var4.charAt(1) == 'c') {
                        return reportBotDetection(var1, true, "invisible_red_name");
                     }
                  }
               }

               return reportBotDetection(var1, false, "accepted_player");
            }
         }
      } else {
         return flagBotByReason("not_player");
      }
   }

   private static boolean isMissingFromTabList(EntityPlayer var0) {
      if (mc == null || mc.getNetHandler() == null || var0 == null) {
         return false;
      } else if (InjectionAgent.isBadlionRuntime()) {
         Boolean var2 = PlayerListTracker.getTabListPresence(var0.getUniqueID());
         return var2 != null ? !var2 : Boolean.FALSE.equals(QkXnc08(var0.getUniqueID()));
      } else {
         NetworkPlayerInfo var1 = mc.getNetHandler().getPlayerInfo(var0.getUniqueID());
         return var1 == null || var1.getGameProfile() == null;
      }
   }

   private static boolean flagBotByReason(String var0) {
      return true;
   }

   private static boolean reportBotDetection(EntityPlayer var0, boolean var1, String var2) {
      return var1;
   }

   private static boolean isListedInTab(EntityPlayer var0) {
      if (mc == null || mc.getNetHandler() == null || var0 == null) {
         return false;
      } else if (!InjectionAgent.isBadlionRuntime()) {
         NetworkPlayerInfo var4 = mc.getNetHandler().getPlayerInfo(var0.getUniqueID());
         return var4 != null && var4.getGameProfile() != null;
      } else {
         Boolean var1 = PlayerListTracker.getTabListPresence(var0.getUniqueID());
         Boolean var2 = QkXnc08(var0.getUniqueID());
         if (var1 != null) {
            return var1;
         } else if (var2 != null) {
            return var2;
         } else {
            NetworkPlayerInfo var3 = mc.getNetHandler().getPlayerInfo(var0.getUniqueID());
            return var3 != null && var3.getGameProfile() != null;
         }
      }
   }

   private static Boolean QkXnc08(UUID var0) {
      if (var0 == null) {
         return null;
      } else {
         for (NetworkPlayerInfo var2 : mc.getNetHandler().getPlayerInfoMap()) {
            if (var2 != null && var2.getGameProfile() != null && var0.equals(var2.getGameProfile().getId())) {
               return Boolean.TRUE;
            }
         }

         return Boolean.FALSE;
      }
   }
}
