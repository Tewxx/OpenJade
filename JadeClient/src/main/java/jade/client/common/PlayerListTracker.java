// Jade recovery: original class: jade.deps.eLz.Ni2frUrL
package jade.client.common;

import jade.client.event.PacketReceiveEvent;
import jade.client.event.PacketSendEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S38PacketPlayerListItem.Action;
import net.minecraft.network.play.server.S38PacketPlayerListItem.AddPlayerData;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;

public class PlayerListTracker implements IMinecraft {
   private static final Set<UUID> tabListPlayerIds = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private static volatile Object mGuwqx;
   private static volatile boolean tabListPopulated;
   private static final Object Gzul9 = new Object();
   private static final Map<String, PlayerListTracker$1> teamsByName = new HashMap<>();
   private static final Map<String, String> CfXt3 = new HashMap<>();
   private static volatile boolean teamsLoaded;
   public AtomicInteger clientHeldItemSlot = new AtomicInteger(-1);
   public AtomicInteger serverHeldItemSlot = new AtomicInteger(-1);
   private final boolean Owi = true;

   @Subscribe(priority = EventPriority.HIGH)
   public void onPacketSend(PacketSendEvent var1) {
      if (!var1.isCanceled()) {
         Packet var2 = var1.ys98();
         if (var2 instanceof C09PacketHeldItemChange) {
            C09PacketHeldItemChange var3 = (C09PacketHeldItemChange)var2;
            int var4 = var3.getSlotId();
            this.clientHeldItemSlot.set(var4);
            this.serverHeldItemSlot.set(var4);
         }
      }
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent var1) {
      Packet var2 = var1.ys98();
      if (var2 instanceof S38PacketPlayerListItem) {
         handlePlayerListPacket((S38PacketPlayerListItem)var2);
      } else if (var2 instanceof S3EPacketTeams) {
         handleTeamPacket((S3EPacketTeams)var2);
      } else if (var2 instanceof S40PacketDisconnect) {
         resetState("disconnect");
      }

      if (var2 instanceof S09PacketHeldItemChange) {
         S09PacketHeldItemChange var3 = (S09PacketHeldItemChange)var2;
         int var4 = var3.getHeldItemHotbarIndex();
         if (var4 >= 0 && var4 < InventoryPlayer.getHotbarSize()) {
            this.serverHeldItemSlot.set(var4);
         }
      } else if (var2 instanceof S0CPacketSpawnPlayer && Minecraft.getMinecraft().thePlayer != null) {
         S0CPacketSpawnPlayer var5 = (S0CPacketSpawnPlayer)var2;
         if (var5.getEntityID() != Minecraft.getMinecraft().thePlayer.getEntityId()) {
            return;
         }

         this.clientHeldItemSlot.set(-1);
      }
   }

   private static void handlePlayerListPacket(S38PacketPlayerListItem var0) {
      NetHandlerPlayClient var1 = Minecraft.getMinecraft().getNetHandler();
      if (var1 != mGuwqx) {
         resetState("net_handler_changed_on_tab_packet");
         mGuwqx = var1;
         MKyg(var1);
      }

      boolean var2 = var0.getAction() == Action.REMOVE_PLAYER;

      for (AddPlayerData var4 : var0.getEntries()) {
         if (var4 != null && var4.getProfile() != null && var4.getProfile().getId() != null) {
            UUID var5 = var4.getProfile().getId();
            if (var2) {
               tabListPlayerIds.remove(var5);
            } else {
               tabListPlayerIds.add(var5);
            }
         }
      }

      tabListPopulated = true;
   }

   private static void MKyg(Object var0) {
      if (var0 != null) {
         for (NetworkPlayerInfo var2 : Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()) {
            if (var2 != null && var2.getGameProfile() != null && var2.getGameProfile().getId() != null) {
               tabListPlayerIds.add(var2.getGameProfile().getId());
            }
         }
      }
   }

   private static void resetState(String var0) {
      tabListPlayerIds.clear();
      mGuwqx = null;
      tabListPopulated = false;
      synchronized (Gzul9) {
         teamsByName.clear();
         CfXt3.clear();
         teamsLoaded = false;
      }
   }

   public static Boolean getTabListPresence(UUID var0) {
      NetHandlerPlayClient var1 = Minecraft.getMinecraft().getNetHandler();
      if (var1 != mGuwqx) {
         resetState("net_handler_changed_on_lookup");
         mGuwqx = var1;
      }

      return var0 != null && tabListPopulated ? tabListPlayerIds.contains(var0) : null;
   }

   private static void handleTeamPacket(S3EPacketTeams var0) {
      refreshNetHandlerIfChanged("team_packet");
      ensureTeamsLoaded();
      synchronized (Gzul9) {
         int var2 = var0.getAction();
         String var3 = var0.getName();
         if (var2 == 0) {
            removeTeam(var3);
            PlayerListTracker$1 var4 = new PlayerListTracker$1(var3, var0.getPrefix(), var0.getSuffix(), var0.getColor());
            teamsByName.put(var3, var4);
            addPlayersToTeam(var4, var0.getPlayers());
         } else if (var2 == 1) {
            removeTeam(var3);
         } else if (var2 == 2) {
            PlayerListTracker$1 var7 = ETmmuX(var3);
            PlayerListTracker$1.vNfi(var7, var0.getPrefix());
            PlayerListTracker$1.wluE(var7, var0.getSuffix());
            PlayerListTracker$1.setColorIndex(var7, var0.getColor());
         } else if (var2 == 3) {
            addPlayersToTeam(ETmmuX(var3), var0.getPlayers());
         } else if (var2 == 4) {
            vqw7(var3, var0.getPlayers());
         }

         teamsLoaded = true;
      }
   }

   public static PlayerListTracker$1 getTeamForPlayer(String var0) {
      refreshNetHandlerIfChanged("team_lookup");
      ensureTeamsLoaded();
      if (var0 == null) {
         return null;
      } else {
         synchronized (Gzul9) {
            String var2 = CfXt3.get(normalizePlayerName(var0));
            return var2 == null ? null : teamsByName.get(var2);
         }
      }
   }

   private static void refreshNetHandlerIfChanged(String var0) {
      NetHandlerPlayClient var1 = Minecraft.getMinecraft().getNetHandler();
      if (var1 != mGuwqx) {
         resetState("net_handler_changed_on_" + var0);
         mGuwqx = var1;
         MKyg(var1);
      }
   }

   private static void ensureTeamsLoaded() {
      if (!teamsLoaded) {
         synchronized (Gzul9) {
            if (!teamsLoaded) {
               Scoreboard var1 = Minecraft.getMinecraft().theWorld == null ? null : Minecraft.getMinecraft().theWorld.getScoreboard();
               if (var1 != null) {
                  for (ScorePlayerTeam var3 : var1.getTeams()) {
                     if (var3 != null) {
                        EnumChatFormatting var4 = var3.getChatFormat();
                        PlayerListTracker$1 var5 = new PlayerListTracker$1(
                           var3.getRegisteredName(), var3.getColorPrefix(), var3.getColorSuffix(), var4 == null ? -1 : var4.getColorIndex()
                        );
                        teamsByName.put(PlayerListTracker$1.YTccBs(var5), var5);
                        addPlayersToTeam(var5, var3.getMembershipCollection());
                     }
                  }
               }

               teamsLoaded = true;
            }
         }
      }
   }

   private static PlayerListTracker$1 ETmmuX(String var0) {
      PlayerListTracker$1 var1 = teamsByName.get(var0);
      if (var1 == null) {
         var1 = new PlayerListTracker$1(var0, "", "", -1);
         teamsByName.put(var0, var1);
      }

      return var1;
   }

   private static void addPlayersToTeam(PlayerListTracker$1 var0, Collection<String> var1) {
      if (var1 != null) {
         for (String var3 : var1) {
            String var4 = normalizePlayerName(var3);
            String var5 = CfXt3.put(var4, PlayerListTracker$1.YTccBs(var0));
            if (var5 != null && !var5.equals(PlayerListTracker$1.YTccBs(var0))) {
               PlayerListTracker$1 var6 = teamsByName.get(var5);
               if (var6 != null) {
                  PlayerListTracker$1.UdnKnqO(var6).remove(var4);
               }
            }

            PlayerListTracker$1.UdnKnqO(var0).add(var4);
         }
      }
   }

   private static void vqw7(String var0, Collection<String> var1) {
      PlayerListTracker$1 var2 = teamsByName.get(var0);
      if (var1 != null) {
         for (String var4 : var1) {
            String var5 = normalizePlayerName(var4);
            if (var0.equals(CfXt3.get(var5))) {
               CfXt3.remove(var5);
            }

            if (var2 != null) {
               PlayerListTracker$1.UdnKnqO(var2).remove(var5);
            }
         }
      }
   }

   private static void removeTeam(String var0) {
      PlayerListTracker$1 var1 = teamsByName.remove(var0);
      if (var1 != null) {
         for (String var3 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (PlayerListTracker$1.UdnKnqO(var1))) {
            if (var0.equals(CfXt3.get(var3))) {
               CfXt3.remove(var3);
            }
         }
      }
   }

   private static String normalizePlayerName(String var0) {
      return var0 == null ? "" : var0.trim().toLowerCase(Locale.ROOT);
   }
}
