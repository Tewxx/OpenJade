// Jade recovery: module: Fast Break (player); original class: jade.deps.eLz.t2TH5i
package jade.client.module.player;

import jade.client.common.ClientUtils;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.shared.BreakDamageHelper;
import jade.client.setting.BooleanSetting;
import jade.client.setting.DescriptionSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorPlayerControllerMP;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

@ModuleInfo(aliases = {"Fast Mine", "FastMine"})
public class FastBreak extends Module {
   private SliderSetting breakDelay;
   public SliderSetting breakSpeed;
   private SliderSetting fasterBreakSpeed;
   private BooleanSetting dynamicSpeed;
   private BooleanSetting decreaseBreakDelay;
   private BooleanSetting ignoreMiningFatigue;
   private int lastBlockHitDelay;

   public FastBreak() {
      super("Fast Break", Category.player);
      this.registerSetting(new DescriptionSetting("Vanilla is 250ms delay & 1x speed."));
      this.registerSetting(
         this.breakDelay = new SliderSetting(
            "Break delay", "ms", 250.0, 0.0, 250.0, 50.0
         )
      );
      this.registerSetting(
         this.breakSpeed = new SliderSetting(
            "Break speed", "x", 1.0, 1.0, 2.0, 0.02
         )
      );
      this.registerSetting(
         this.fasterBreakSpeed = new SliderSetting(
            (GroupSetting)null,
            "Faster Break Speed",
            "x",
            1.0,
            1.0,
            3.0,
            0.02,
            new String[]{"Secondary speed"}
         )
      );
      this.registerSetting(this.dynamicSpeed = new BooleanSetting("Dynamic Speed", false, new String[]{"Secondary on RMB"}));
      this.registerSetting(
         this.decreaseBreakDelay = new BooleanSetting(
            "Decrease break delay", false
         )
      );
      this.registerSetting(
         this.ignoreMiningFatigue = new BooleanSetting(
            "Ignore mining fatigue",
            false
         )
      );
      this.breakDelay.visible = false;
      this.fasterBreakSpeed.visible = false;
      this.initialized = true;
   }

   public float getEffectiveBreakSpeed() {
      return this.lxCdn(this.getHitBlockPos());
   }

   private float lxCdn(BlockPos var1) {
      if (!this.isEnabled()) {
         return 1.0F;
      } else if (mc.thePlayer != null && mc.thePlayer.capabilities.isCreativeMode) {
         return 1.0F;
      } else {
         float var2 = (float)(this.ykhL1(var1) ? this.fasterBreakSpeed.getInput() : this.breakSpeed.getInput());
         if (this.ignoreMiningFatigue.isToggled() && mc.thePlayer != null && mc.thePlayer.isPotionActive(Potion.digSlowdown)) {
            ItemStack var3 = mc.thePlayer.getHeldItem();
            Item var4 = var3 != null ? var3.getItem() : null;
            if (var4 != Items.wooden_axe && var4 != Items.wooden_pickaxe) {
               var2 = 2.5F;
            } else {
               var2 = 1.8F;
            }
         }

         return var2 > 1.0F ? var2 : 1.0F;
      }
   }

   private BlockPos getHitBlockPos() {
      if (mc.playerController == null) {
         return null;
      } else {
         IAccessorPlayerControllerMP var1 = (IAccessorPlayerControllerMP)mc.playerController;
         return var1.getIsHittingBlock() ? var1.getCurrentBlock() : null;
      }
   }

   private boolean ykhL1(BlockPos var1) {
      if (this.dynamicSpeed.isToggled() && mc.theWorld != null && mc.thePlayer != null && var1 != null) {
         ItemStack var2 = mc.thePlayer.getHeldItem();
         Item var3 = var2 != null ? var2.getItem() : null;
         return this.YRVfb(var3, mc.theWorld.getBlockState(var1).getBlock());
      } else {
         return false;
      }
   }

   private boolean YRVfb(Item var1, Block var2) {
      if (var2 == Blocks.bed) {
         return true;
      } else {
         boolean var3 = var1 == Items.iron_pickaxe || var1 == Items.golden_pickaxe || var1 == Items.diamond_pickaxe;
         if (!var3 || var2 != Blocks.end_stone && var2 != Blocks.hardened_clay && var2 != Blocks.stained_hardened_clay && var2 != Blocks.clay) {
            if (var1 == Items.shears && var2 == Blocks.wool) {
               return true;
            } else {
               boolean var4 = var1 == Items.iron_axe || var1 == Items.diamond_axe;
               return var4 && (var2 == Blocks.planks || var2 == Blocks.log || var2 == Blocks.log2);
            }
         } else {
            return true;
         }
      }
   }

   public boolean shouldDecreaseBreakDelay() {
      return this.isEnabled() && this.decreaseBreakDelay.isToggled() && ClientUtils.isInWorld();
   }

   public void decreaseBlockHitDelay(Minecraft var1) {
      if (this.shouldDecreaseBreakDelay() && var1.playerController != null) {
         IAccessorPlayerControllerMP var2 = (IAccessorPlayerControllerMP)var1.playerController;
         int var3 = var2.getBlockHitDelay();
         if (var3 > 0 && var3 == this.lastBlockHitDelay) {
            var2.setBlockHitDelay(var3 - 1);
         }

         this.lastBlockHitDelay = var3;
      }
   }

   public void boostBlockDamageProgress(Minecraft var1) {
      if (this.isEnabled() && ClientUtils.isInWorld() && var1.inGameHasFocus && var1.playerController != null) {
         IAccessorPlayerControllerMP var2 = (IAccessorPlayerControllerMP)var1.playerController;
         BlockPos var3 = var2.getCurrentBlock();
         if (var2.getIsHittingBlock() && var3 != null) {
            float var4 = this.Urvn(var3);
            float var5 = var2.getCurBlockDamageMP();
            float var6 = BreakDamageHelper.oxOn(var5, var4);
            if (var6 > var5) {
               var2.setCurBlockDamageMP(var6);
            }
         }
      }
   }

   public float getCurrentBlockDamageSpeed() {
      return this.Urvn(this.getHitBlockPos());
   }

   private float Urvn(BlockPos var1) {
      return BreakDamageHelper.GeX06(this.lxCdn(var1));
   }

   public int getBlockHitDelayOverride() {
      if (!this.isEnabled() || !this.decreaseBreakDelay.isToggled() || !ClientUtils.isInWorld() || !mc.inGameHasFocus) {
         return -1;
      } else if (mc.thePlayer != null && mc.thePlayer.capabilities.isCreativeMode) {
         return -1;
      } else {
         int var1 = (int)(this.breakDelay.getInput() / 50.0);
         return var1 >= 5 ? -1 : var1;
      }
   }

   @Override
   public void guiUpdate() {
      this.breakDelay.setVisible(this.decreaseBreakDelay.isToggled(), this);
      this.fasterBreakSpeed.setVisible(this.dynamicSpeed.isToggled(), this);
   }

   @Override
   public String getInfo() {
      SliderSetting var1 = this.ykhL1(this.getHitBlockPos()) ? this.fasterBreakSpeed : this.breakSpeed;
      return ((int)var1.getInput() == var1.getInput() ? (int)var1.getInput() + "" : var1.getInput()) + var1.getSuffix();
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias("Conditions", "Speed", new String[]{"disable in creative"}, new String[]{"Not in creative"}),
         buildSettingAlias(
            "Action",
            "Conditions",
            new String[]{"dynamic speed", "decrease break delay", "ignore mining fatigue"},
            new String[]{"Dynamic Speed", "Decrease break delay", "Ignore mining fatigue"}
         )
      );
   }
}
