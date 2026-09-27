// Jade recovery: module: Fisherman (combat); original class: jade.deps.eLz.PUS4pFDXCd
package jade.client.module.combat;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.EventPriority;
import jade.client.common.PacketDirection;
import jade.client.common.RotationUtils;
import jade.client.common.Subscribe;
import jade.client.common.PacketListenerRegistration;
import jade.client.event.MoveStateUpdateEvent;
import jade.client.event.PacketSendEvent;
import jade.client.event.RotationEvent;
import jade.client.event.TickStartEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.shared.DisabledOrNestedCondition;
import jade.client.module.shared.TargetFinder;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.ItemListSetting;
import jade.client.setting.SliderSetting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;

@ModuleInfo
public class Fisherman extends Module {
   private static final int uxUxx = 10;
   private final SliderSetting delay;
   private final BooleanSetting blink;
   private final BooleanSetting hasKnockback;
   private final BooleanSetting itemWhitelist;
   private final ItemListSetting whitelistedItems;
   private final BooleanSetting disableOnBreak;
   private boolean attackPhaseToggle = false;
   private boolean hasValidTarget = false;
   private boolean knockbackItemHeld = false;
   private boolean ULFM = false;
   private boolean blinkActive = false;
   private int YNS;
   private final Map<Integer, Integer> lastAttackTicks = new HashMap<>();
   private PacketListenerRegistration blinkPacketListener;

   public Fisherman() {
      super("Fisherman", Category.combat);
      this.registerSetting(
         this.delay = new SliderSetting("Delay", "ms", 0.0, 0.0, 500.0, 50.0)
      );
      this.registerSetting(this.blink = new BooleanSetting("Blink", false));
      this.registerSetting(new DescriptionSetting("Item conditions"));
      this.registerSetting(
         this.hasKnockback = new BooleanSetting(
            "Has knockback", false
         )
      );
      this.registerSetting(this.itemWhitelist = new BooleanSetting("Item whitelist", false));
      this.registerSetting(this.whitelistedItems = new ItemListSetting("Whitelisted items"));
      this.registerSetting(
         this.disableOnBreak = new BooleanSetting(
            "Disable on break", false
         )
      );
   }

   @Override
   public void guiUpdate() {
      this.whitelistedItems.setVisible(this.itemWhitelist.isToggled(), this);
   }

   @Override
   public String getInfo() {
      return (int)Math.round(this.delay.getInput()) + "ms";
   }

   @Override
   public void onEnable() {
      this.attackPhaseToggle = false;
      this.hasValidTarget = false;
      this.knockbackItemHeld = false;
      this.ULFM = false;
      this.blinkActive = false;
      this.YNS = 0;
      this.lastAttackTicks.clear();
      this.releaseBlinkQueue();
   }

   @Override
   public void onDisable() {
      this.hasValidTarget = false;
      this.ULFM = false;
      this.blinkActive = false;
      this.lastAttackTicks.clear();
      this.releaseBlinkQueue();
   }

   private static int DWnTbl(double var0) {
      return var0 <= 0.0 ? 0 : (int)Math.ceil(var0 / 50.0);
   }

   private boolean isMovementKeyHeld() {
      return mc.gameSettings.keyBindForward.isKeyDown()
         || mc.gameSettings.keyBindBack.isKeyDown()
         || mc.gameSettings.keyBindLeft.isKeyDown()
         || mc.gameSettings.keyBindRight.isKeyDown();
   }

   private void lUiyxcs() {
      if (mc.theWorld == null) {
         this.lastAttackTicks.clear();
      } else {
         Iterator var1 = this.lastAttackTicks.entrySet().iterator();

         while (var1.hasNext()) {
            Entry var2 = (Entry)var1.next();
            Entity var3 = mc.theWorld.getEntityByID((Integer)var2.getKey());
            if (!(var3 instanceof EntityPlayer) || var3.isDead || ((EntityPlayer)var3).deathTime != 0) {
               var1.remove();
            }
         }
      }
   }

   private boolean FubFo(EntityPlayer var1, int var2) {
      int var3 = var1.getEntityId();
      Integer var4 = this.lastAttackTicks.get(var3);
      if (var4 != null && var2 - var4 < 10) {
         int var5 = DWnTbl(this.delay.getInput());
         if (var5 <= 0) {
            return true;
         } else {
            int var6 = var2 - var4;
            return var6 >= var5;
         }
      } else {
         this.lastAttackTicks.put(var3, var2);
         return true;
      }
   }

   private void releaseBlinkQueue() {
      if (this.blinkPacketListener != null) {
         this.blinkPacketListener.getPacketHandler().forceOpen();
         this.blinkPacketListener = null;
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onTickStart(TickStartEvent var1) {
      if (this.blinkActive) {
         this.releaseBlinkQueue();
         this.blinkActive = false;
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onMoveStateUpdate(MoveStateUpdateEvent var1) {
      if (this.hasValidTarget && this.attackPhaseToggle && !this.knockbackItemHeld) {
         if (this.isMovementKeyHeld()) {
            mc.thePlayer.movementInput.moveForward = 1.0F;
         }
      }
   }

   @Subscribe(priority = EventPriority.HIGH)
   public void onPacketSend(PacketSendEvent var1) {
      if (this.blink.isToggled() && this.hasValidTarget && this.attackPhaseToggle && !this.blinkActive) {
         if (var1.ys98() instanceof C03PacketPlayer) {
            if (this.blinkPacketListener == null) {
               this.blinkPacketListener = new PacketListenerRegistration(PacketDirection.ONLY_OUTBOUND, new DisabledOrNestedCondition(this));
               Jade.nbT.JUlwlNu(this.blinkPacketListener);
               this.blinkActive = true;
            }
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void onRotation(RotationEvent var1) {
      if (!ClientUtils.isInWorld()) {
         this.hasValidTarget = false;
         this.ULFM = false;
      } else {
         if (this.disableOnBreak.isToggled() && Mouse.isButtonDown(0) && mc.objectMouseOver != null) {
            BlockPos var2 = mc.objectMouseOver.getBlockPos();
            if (var2 != null) {
               Block var3 = mc.theWorld.getBlockState(var2).getBlock();
               if (var3 != Blocks.air && !(var3 instanceof BlockLiquid)) {
                  this.hasValidTarget = false;
                  this.attackPhaseToggle = false;
                  this.ULFM = false;
                  return;
               }
            }
         }

         this.YNS++;
         this.lUiyxcs();
         if (this.hasKnockback.isToggled() || this.itemWhitelist.isToggled()) {
            boolean var5 = !this.hasKnockback.isToggled() || EnchantmentHelper.getKnockbackModifier(mc.thePlayer) > 0;
            boolean var7 = !this.itemWhitelist.isToggled() || this.whitelistedItems.EMuhC6(mc.thePlayer.getHeldItem());
            if (!var5 && !var7) {
               this.hasValidTarget = false;
               this.attackPhaseToggle = false;
               this.ULFM = false;
               return;
            }
         }

         EntityPlayer var6 = null;
         if (Mouse.isButtonDown(0)) {
            var6 = TargetFinder.findNearestTarget(9.0);
         }

         boolean var8 = EnchantmentHelper.getKnockbackModifier(mc.thePlayer) > 0;
         this.hasValidTarget = var6 != null && (var8 || this.isMovementKeyHeld());
         if (!this.hasValidTarget) {
            this.attackPhaseToggle = false;
            this.ULFM = false;
         } else {
            this.knockbackItemHeld = var8;
            this.attackPhaseToggle = !this.attackPhaseToggle;
            if (this.attackPhaseToggle && !this.FubFo(var6, this.YNS)) {
               this.attackPhaseToggle = false;
               this.ULFM = false;
            } else {
               if (!this.attackPhaseToggle && this.ULFM) {
                  int var4 = mc.gameSettings.keyBindAttack.getKeyCode();
                  if (var4 != 0) {
                     KeyBinding.onTick(var4);
                  }
               }

               this.ULFM = this.attackPhaseToggle;
               if (this.attackPhaseToggle) {
                  var1.PrQmu(RotationUtils.lastSentRotation[0] + 180.0F, 30);
               }
            }
         }
      }
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Action", "Range", new String[]{"find void", "blink"}, new String[]{"Find void", "Blink"}),
         buildSettingAlias(
            "Conditions",
            "Action",
            new String[]{"stop when breaking", "has knockback", "item whitelist", "disable on break"},
            new String[]{"Not whilst breaking", "Holding knockback item", "Whitelisted item", "Disable after break"}
         )
      );
   }
}
