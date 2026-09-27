// Jade recovery: module: Skywars Utils (minigames); original class: jade.deps.eLz.uGeJT3a
package jade.client.module.minigames;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPhase;
import jade.client.common.EventPriority;
import jade.client.common.SkywarsGameState;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.ChangeCurrentItemEvent;
import jade.client.event.ChatReceivedEvent;
import jade.client.event.LoadWorldEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.SetCurrentItemEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.render.ESP;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorMinecraft;
import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;

@ModuleInfo
public class SkywarsUtils extends Module implements ExternalRenderableModule {
   private static final long STRENGTH_HIGHLIGHT_WINDOW_MILLIS = 5000L;
   private static final float trL4 = 2.0F;
   private static final float QtW83 = 8.0F;
   private static final double ENEMY_PROXIMITY_RADIUS = 5.0;
   private static final String[] KILL_MESSAGE_SEPARATORS = new String[]{
         " by ",
         " to ",
         " with ",
         " of ",
         " from ",
         " knight ",
         " for ",
         " on ",
         " league "
      };
   private final BooleanSetting strengthEsp;
   private final ColorSetting strengthEspColor;
   private final BooleanSetting autoEcho;
   private final SliderSetting autoEchoHealth;
   private final Map<String, Long> strengthPlayers = new HashMap<>();
   private int echoState;
   private int clockSlot = -1;
   private int previousSlot = -1;

   public SkywarsUtils() {
      super("Skywars Utils", Category.minigames);
      this.registerSetting(
         this.strengthEsp = new BooleanSetting(
            "Strength ESP", false
         )
      );
      this.registerSetting(
         this.strengthEspColor = new ColorSetting(
            "Strength ESP Color",
            255,
            0,
            0
         )
      );
      this.registerSetting(
         this.autoEcho = new BooleanSetting(
            "Auto Echo", false
         )
      );
      this.registerSetting(
         this.autoEchoHealth = new SliderSetting(
            "Auto Echo Health", " HP", 4.0, 1.0, 20.0, 0.5
         )
      );
      this.strengthEspColor.visible = false;
      this.autoEchoHealth.visible = false;
      this.initialized = true;
   }

   @Override
   public void onEnable() {
      this.strengthPlayers.clear();
      this.vcoEz(true);
   }

   @Override
   public void onDisable() {
      this.strengthPlayers.clear();
      this.vcoEz(true);
   }

   @Override
   public void guiUpdate() {
      this.strengthEspColor.setVisible(this.strengthEsp.isToggled(), this);
      this.autoEchoHealth.setVisible(this.autoEcho.isToggled(), this);
   }

   @Override
   public void guiButtonToggled(BooleanSetting var1) {
      if (var1 == this.strengthEsp || var1 == this.autoEcho) {
         this.guiUpdate();
      }
   }

   @Subscribe
   public void onChatReceived(ChatReceivedEvent var1) {
      if (this.strengthEsp.isToggled() && SkywarsGameState.JYXs() && var1.messageType == 0 && var1.iChatComponent != null) {
         String var2 = extractKilledPlayerName(ClientUtils.AOAtn(var1.iChatComponent.getUnformattedText()));
         if (var2 != null) {
            this.strengthPlayers.put(var2, System.currentTimeMillis());
         }
      }
   }

   @Subscribe
   public void onLoadWorld(LoadWorldEvent var1) {
      this.strengthPlayers.clear();
      this.vcoEz(false);
   }

   @Subscribe
   public void onSetCurrentItem(SetCurrentItemEvent var1) {
      if (this.echoState != 0) {
         this.previousSlot = var1.slot;
         var1.setCanceled(true);
      }
   }

   @Subscribe
   public void onChangeCurrentItem(ChangeCurrentItemEvent var1) {
      if (this.echoState != 0) {
         int var2 = Integer.compare(var1.scrollDirection, 0);
         this.previousSlot = Math.floorMod(mc.thePlayer.inventory.currentItem - var2, 9);
         var1.setCanceled(true);
      }
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         if (this.echoState != 0) {
            this.updateEchoState();
         } else if (this.autoEcho.isToggled() && this.shouldUseEchoClock()) {
            this.clockSlot = this.findClockSlot();
            if (this.clockSlot != -1) {
               this.previousSlot = mc.thePlayer.inventory.currentItem;
               this.bzMc(this.clockSlot);
               this.echoState = 1;
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      if (this.strengthEsp.isToggled() && SkywarsGameState.JYXs() && ClientUtils.isInWorld() && Jade.getModuleManager().getModule(ESP.class) != null) {
         ESP var2 = Jade.getModuleManager().getModule(ESP.class);
         long var3 = System.currentTimeMillis();

         for (EntityPlayer var6 : mc.theWorld.playerEntities) {
            if (var6 != mc.thePlayer && this.pkR7(var6, var3)) {
               if (this.VIuQ()) {
                  var2.renderExternalBox(var6, this.strengthEspColor.getArgb(), 2.0F);
               } else {
                  var2.renderLivingBox(var6, this.strengthEspColor.getArgb(), 2.0F);
               }
            }
         }
      }
   }

   public boolean isStrengthHighlighted(EntityLivingBase var1) {
      return this.isEnabled() && this.strengthEsp.isToggled() && SkywarsGameState.JYXs() && var1 instanceof EntityPlayer && this.pkR7((EntityPlayer)var1, System.currentTimeMillis());
   }

   private boolean pkR7(EntityPlayer var1, long var2) {
      Long var4 = this.strengthPlayers.get(var1.getName());
      if (var4 == null) {
         return false;
      } else if (var2 - var4 > 5000L) {
         this.strengthPlayers.remove(var1.getName());
         return false;
      } else {
         return true;
      }
   }

   private boolean shouldUseEchoClock() {
      if (ClientUtils.isInWorld() && mc.currentScreen == null && mc.inGameHasFocus && !mc.thePlayer.isDead) {
         long var1 = System.currentTimeMillis();
         if (!SkywarsGameState.isTimerElapsed(var1)) {
            return false;
         } else {
            int var3 = this.findGroundY();
            if (var3 == Integer.MIN_VALUE) {
               return false;
            } else {
               boolean var4 = mc.thePlayer.getHealth() < this.autoEchoHealth.getInput() && this.isEnemyNearbyWithMoreHealth();
               return var4 || this.JWGpwHi(var3, 8.0F);
            }
         }
      } else {
         return false;
      }
   }

   private boolean isEnemyNearbyWithMoreHealth() {
      float var1 = mc.thePlayer.getHealth() + 2.0F;
      double var2 = 25.0;

      for (EntityPlayer var5 : mc.theWorld.playerEntities) {
         if (var5 != mc.thePlayer
            && !var5.isDead
            && !(var5.getHealth() <= 0.0F)
            && !mc.thePlayer.isOnSameTeam(var5)
            && mc.thePlayer.getDistanceSqToEntity(var5) <= var2
            && var5.getHealth() >= var1) {
            return true;
         }
      }

      return false;
   }

   private boolean JWGpwHi(int var1, float var2) {
      if (!mc.thePlayer.onGround && !(mc.thePlayer.motionY >= 0.0)) {
         Block var3 = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX), var1, (int)Math.floor(mc.thePlayer.posZ))).getBlock();
         if (var3 != Blocks.water
            && var3 != Blocks.flowing_water
            && var3 != Blocks.web
            && var3 != Blocks.ladder
            && var3 != Blocks.vine
            && (var3 != Blocks.slime_block || mc.thePlayer.isSneaking())) {
            double var4 = Math.max(0.0, mc.thePlayer.posY - (var1 + 1.0));
            float var6 = mc.thePlayer.fallDistance + (float)var4;
            PotionEffect var7 = mc.thePlayer.getActivePotionEffect(Potion.jump);
            float var8 = 3.0F + (var7 == null ? 0.0F : var7.getAmplifier() + 1.0F);
            float var9 = (float)Math.ceil(var6 - var8);
            if (var9 <= 0.0F) {
               return false;
            } else {
               int var10 = EnchantmentHelper.getEnchantmentModifierDamage(mc.thePlayer.inventory.armorInventory, DamageSource.fall);
               var10 = Math.min(20, Math.max(0, var10));
               var9 *= (25.0F - var10) / 25.0F;
               if (var3 == Blocks.hay_block) {
                  var9 *= 0.2F;
               }

               float var11 = mc.thePlayer.getHealth() + mc.thePlayer.getAbsorptionAmount() - var9;
               return var11 < var2;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private int findGroundY() {
      int var1 = (int)Math.floor(mc.thePlayer.posX);
      int var2 = (int)Math.floor(mc.thePlayer.posZ);
      int var3 = Math.min(255, (int)Math.floor(mc.thePlayer.getEntityBoundingBox().minY - 0.001));

      for (int var4 = var3; var4 >= 0; var4--) {
         Block var5 = mc.theWorld.getBlockState(new BlockPos(var1, var4, var2)).getBlock();
         if (var5 != null && var5 != Blocks.air) {
            return var4;
         }
      }

      return Integer.MIN_VALUE;
   }

   private int findClockSlot() {
      for (int var1 = 0; var1 < 9; var1++) {
         ItemStack var2 = mc.thePlayer.inventory.getStackInSlot(var1);
         if (var2 != null && var2.getItem() == Items.clock) {
            return var1;
         }
      }

      return -1;
   }

   private void updateEchoState() {
      if (!ClientUtils.isInWorld() || mc.currentScreen != null || mc.thePlayer.isDead) {
         this.vcoEz(true);
      } else if (this.echoState == 1) {
         ItemStack var1 = mc.thePlayer.getHeldItem();
         if (mc.thePlayer.inventory.currentItem == this.clockSlot && var1 != null && var1.getItem() == Items.clock) {
            ((IAccessorMinecraft)mc).callRightClickMouse();
            SkywarsGameState.PqEeqyh(System.currentTimeMillis());
            this.echoState = 2;
         } else {
            this.vcoEz(true);
         }
      } else {
         this.vcoEz(true);
      }
   }

   private void vcoEz(boolean var1) {
      if (var1 && ClientUtils.isInWorld() && this.previousSlot >= 0 && this.previousSlot < 9) {
         this.bzMc(this.previousSlot);
      }

      this.echoState = 0;
      this.clockSlot = -1;
      this.previousSlot = -1;
   }

   private void bzMc(int var1) {
      if (var1 >= 0 && var1 <= 8 && mc.thePlayer.inventory.currentItem != var1) {
         mc.thePlayer.inventory.currentItem = var1;
         ((IAccessorPlayerControllerMP)mc.playerController).callSyncCurrentPlayItem();
      }
   }

   private static String extractKilledPlayerName(String var0) {
      if (var0 != null && var0.endsWith(".")) {
         for (String var4 : KILL_MESSAGE_SEPARATORS) {
            int var5 = var0.lastIndexOf(var4);
            if (var5 != -1) {
               String var6 = var0.substring(var5 + var4.length(), var0.length() - 1).trim();
               return var6.matches("[A-Za-z0-9_]{1,16}") ? var6 : null;
            }
         }

         return null;
      } else {
         return null;
      }
   }
}
