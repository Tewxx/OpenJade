// Jade recovery: module: Murder Mystery (minigames); original class: jade.deps.eLz.PArZeUMd
package jade.client.module.minigames;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.ItemMatcher;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.minigames.murdermystery.MurderMysteryUtils;
import jade.client.module.minigames.murdermystery.MurderWeapons;
import jade.client.module.other.AntiBot;
import jade.client.module.render.ESP;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ItemListSetting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

@ModuleInfo
public class MurderMystery extends Module implements ExternalRenderableModule {
   private final ItemListSetting murderWeapons;
   private final BooleanSetting alertMurderer;
   private final BooleanSetting alertBow;
   private final BooleanSetting highlightMurderer;
   private final BooleanSetting highlightBow;
   private final BooleanSetting highlightInnocent;
   private final BooleanSetting highlightDead;
   private final BooleanSetting goldEsp;
   private final List<EntityPlayer> Bzdo = new ArrayList<>();
   private final List<EntityPlayer> Ebh = new ArrayList<>();
   private final List<EntityItem> fuQceN = new ArrayList<>();
   private boolean foundTrackedPlayer;
   private boolean inMurderMystery;

   public MurderMystery() {
      super("Murder Mystery", Category.minigames);
      this.registerSetting(this.murderWeapons = new ItemListSetting("Murder weapons"));

      for (Item var4 : MurderWeapons.getDefaultWeapons()) {
         this.GKbJ9(var4);
      }

      this.registerSetting(
         this.alertMurderer = new BooleanSetting(
            "Alert murderer", true
         )
      );
      this.registerSetting(
         this.alertBow = new BooleanSetting(
            "Alert bow", true
         )
      );
      this.registerSetting(this.highlightMurderer = new BooleanSetting("Highlight murderer", true));
      this.registerSetting(
         this.highlightBow = new BooleanSetting(
            "Highlight bow", true
         )
      );
      this.registerSetting(
         this.highlightInnocent = new BooleanSetting(
            "Highlight innocent", true
         )
      );
      this.registerSetting(
         this.highlightDead = new BooleanSetting(
            "Highlight dead", true
         )
      );
      this.registerSetting(
         this.goldEsp = new BooleanSetting(
            "Gold ESP", true
         )
      );
   }

   @Override
   public void onDisable() {
      this.clearTrackedEntities();
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END && ClientUtils.isInWorld()) {
         this.inMurderMystery = this.detectMurderMysteryGame();
         if (!this.inMurderMystery) {
            this.clearTrackedEntities();
         } else {
            this.scanPlayers();
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (ClientUtils.isInWorld() && this.inMurderMystery) {
         ESP var2 = Jade.getModuleManager().getModule(ESP.class);
         if (var2 != null) {
            for (EntityPlayer var4 : mc.theWorld.playerEntities) {
               if (var4 != mc.thePlayer && !var4.isInvisible() && (!AntiBot.shouldHideEntity(var4) || this.highlightDead.isToggled())) {
                  int var5 = Color.green.getRGB();
                  if (this.Bzdo.contains(var4) && this.highlightMurderer.isToggled()) {
                     var5 = Color.red.getRGB();
                  } else if (this.Ebh.contains(var4) && this.highlightBow.isToggled()) {
                     var5 = Color.orange.getRGB();
                  } else if (!this.highlightInnocent.isToggled()) {
                     continue;
                  }

                  if (this.highlightDead.isToggled() || !(MurderMysteryUtils.BNAB(var4.getEntityBoundingBox()) <= 0.009)) {
                     if (this.VIuQ()) {
                        var2.renderExternalBox(var4, var5, 2.0F);
                     } else {
                        var2.renderLivingBox(var4, var5, 2.0F);
                     }
                  }
               }
            }

            if (this.goldEsp.isToggled()) {
               int var6 = -331703;

               for (EntityItem var8 : this.fuQceN) {
                  if (var8 != null && !var8.isDead && var8.getEntityItem() != null && var8.getEntityItem().stackSize != 0) {
                     if (this.VIuQ()) {
                        var2.renderExternalBox(var8, var6, 2.0F);
                     } else {
                        var2.renderEntityBox(var8, var6, 2.0F);
                     }
                  }
               }
            }
         }
      }
   }

   private void scanPlayers() {
      this.foundTrackedPlayer = false;
      this.fuQceN.clear();

      for (EntityPlayer var2 : mc.theWorld.playerEntities) {
         if (var2 != mc.thePlayer && !var2.isInvisible() && (!AntiBot.shouldHideEntity(var2) || this.highlightDead.isToggled())) {
            ItemStack var3 = var2.getHeldItem();
            if (var3 != null) {
               Item var4 = var3.getItem();
               if (this.murderWeapons.EMuhC6(var3)) {
                  if (!this.Bzdo.contains(var2)) {
                     this.Bzdo.add(var2);
                     if (this.alertMurderer.isToggled()) {
                        mc.thePlayer.playSound("note.pling", 1.0F, 1.0F);
                        ClientUtils.sendJadeMessage("Jade", "&b" + var2.getName() + " &7is the &cmurderer&7! (&f" + (int)mc.thePlayer.getDistanceToEntity(var2) + "m&7)");
                     }
                  }
               } else if (var4 instanceof ItemBow) {
                  if (!this.Ebh.contains(var2)) {
                     this.Ebh.add(var2);
                     if (this.alertBow.isToggled()) {
                        mc.thePlayer.playSound("note.pling", 1.0F, 1.25F);
                        ClientUtils.sendJadeMessage("Jade", "&b" + var2.getName() + " &7has a &6bow&7! (&f" + (int)mc.thePlayer.getDistanceToEntity(var2) + "m&7)");
                     }
                  }
               } else {
                  this.Ebh.remove(var2);
               }
            } else {
               this.Ebh.remove(var2);
            }

            this.foundTrackedPlayer = true;
         }
      }

      if (this.goldEsp.isToggled()) {
         for (Entity var6 : mc.theWorld.loadedEntityList) {
            if (var6 instanceof EntityItem && var6.ticksExisted >= 3) {
               EntityItem var7 = (EntityItem)var6;
               ItemStack var8 = var7.getEntityItem();
               if (var8 != null && var8.stackSize != 0 && var8.getItem() == Items.gold_ingot) {
                  this.fuQceN.add(var7);
               }
            }
         }
      }
   }

   private boolean detectMurderMysteryGame() {
      if (ClientUtils.isOnHypixel() && mc.thePlayer.getWorldScoreboard() != null && mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) != null) {
         ArrayList var1 = new ArrayList();

         for (String var3 : ClientUtils.getSidebarLines()) {
            var1.add(ClientUtils.AOAtn(var3));
         }

         return MurderMysteryUtils.isMurderMysteryGame(mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName(), var1);
      } else {
         return false;
      }
   }

   public boolean hasNoTrackedSuspects() {
      return this.Bzdo.isEmpty() && this.Ebh.isEmpty() && !this.foundTrackedPlayer;
   }

   private void clearTrackedEntities() {
      this.inMurderMystery = false;
      this.foundTrackedPlayer = false;
      this.Bzdo.clear();
      this.Ebh.clear();
      this.fuQceN.clear();
   }

   private void GKbJ9(Item var1) {
      if (var1 != null) {
         String var2 = ItemMatcher.getItemId(var1);
         if (var2 != null) {
            this.murderWeapons.addItem(ItemMatcher.hasMultipleVariants(var1) ? var2 + ":*" : var2);
         }
      }
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Alerts", "Mode", new String[]{"alert murderer", "alert bow"}, new String[]{"Murderer alert", "Bow alert"}),
         buildSettingAlias(
            "Highlights",
            "Alerts",
            new String[]{"highlight murderer", "highlight bow", "highlight innocent", "highlight dead", "gold esp"},
            new String[]{"Murderer", "Bow", "Innocent", "Dead", "Gold"}
         )
      );
   }
}
