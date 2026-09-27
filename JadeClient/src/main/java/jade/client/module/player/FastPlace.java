// Jade recovery: module: Fast Place (player); original class: jade.deps.eLz.gvi0qTJK
package jade.client.module.player;

import jade.client.common.ClientUtils;
import jade.client.common.Subscribe;
import jade.client.event.PacketSendEvent;
import jade.client.event.RightClickDelayEvent;
import jade.client.module.Category;
import jade.client.module.Module$2;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.player.fastplace.FastPlaceSettings;
import jade.client.module.player.fastplace.FastPlaceConditions;
import jade.client.module.player.fastplace.ElapsedTimerGate;
import jade.client.setting.BlockListSetting;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ItemListSetting;
import jade.client.setting.SliderSetting;

import jade.mixin.impl.accessor.IAccessorMinecraft;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

@ModuleInfo
public class FastPlace extends Module {
   public SliderSetting tickDelay;
   public SliderSetting FdP;
   public BooleanSetting blocksOnly;
   public BooleanSetting pgJx7;
   public BooleanSetting heldItemBlacklist;
   public ItemListSetting itemListSetting;
   public BooleanSetting blockBlacklist;
   public BlockListSetting blockListSetting;
   private final ElapsedTimerGate OjIj = new ElapsedTimerGate();

   public FastPlace() {
      super("Fast Place", Category.player, 0);
      FastPlaceSettings var1 = new FastPlaceSettings();
      this.registerSetting(this.tickDelay = var1.tickDelay);
      this.registerSetting(this.FdP = var1.activationTime);
      this.registerSetting(this.blocksOnly = var1.blocksOnly);
      this.registerSetting(this.pgJx7 = var1.pitchCheck);
      this.registerSetting(this.heldItemBlacklist = var1.heldItemBlacklist);
      this.registerSetting(this.itemListSetting = var1.heldItems);
      this.registerSetting(this.blockBlacklist = var1.blockBlacklist);
      this.registerSetting(this.blockListSetting = var1.blacklistedBlocks);
      this.initialized = true;
   }

   @Override
   public void onDisable() {
      this.OjIj.reset();
   }

   @Override
   public void guiUpdate() {
      this.itemListSetting.setVisible(this.heldItemBlacklist.isToggled(), this);
      this.blockListSetting.setVisible(this.blockBlacklist.isToggled(), this);
   }

   @Subscribe
   public void onRightClickDelay(RightClickDelayEvent var1) {
      if (ClientUtils.isInWorld() && mc.inGameHasFocus && this.KPyawZ()) {
         long var2 = System.currentTimeMillis();
         this.OjIj.beginTiming(var2);
         if (this.isFastPlaceActive(var2)) {
            IAccessorMinecraft var4 = (IAccessorMinecraft)mc;
            int var5 = FastPlaceConditions.resolveRightClickDelay((int)this.tickDelay.getInput(), var4.getRightClickDelayTimer());
            if (var5 != var4.getRightClickDelayTimer()) {
               var4.setRightClickDelayTimer(var5);
            }
         }
      } else {
         this.OjIj.reset();
      }
   }

   @Subscribe
   public void onPacketSend(PacketSendEvent var1) {
      if (ClientUtils.isInWorld() && var1.ys98() instanceof C08PacketPlayerBlockPlacement) {
         C08PacketPlayerBlockPlacement var2 = (C08PacketPlayerBlockPlacement)var1.ys98();
         ItemStack var3 = var2.getStack();
         if (var2.getPlacedBlockDirection() == 255
            && var3 != null
            && var3.getItem() instanceof ItemBlock
            && this.isFastPlaceActive(System.currentTimeMillis())
            && Math.random() < 0.7) {
            var1.setCanceled(true);
         }
      }
   }

   private boolean KPyawZ() {
      return ClientUtils.xusXfhC(mc.gameSettings.keyBindUseItem) || mc.gameSettings.keyBindUseItem.isKeyDown() || mc.thePlayer.isUsingItem();
   }

   private boolean isFastPlaceActive(long var1) {
      ItemStack var3 = mc.thePlayer.getHeldItem();
      boolean var4 = var3 != null && var3.getItem() instanceof ItemBlock;
      boolean var5 = this.heldItemBlacklist.isToggled() && this.itemListSetting.EMuhC6(var3);
      return FastPlaceConditions.meetsFastPlaceConditions(
         this.blocksOnly.isToggled(), var4, this.pgJx7.isToggled(), mc.thePlayer.rotationPitch, var5, this.wuaK(), this.OjIj.hasElapsed(var1, (long)this.FdP.getInput())
      );
   }

   private boolean wuaK() {
      if (this.blockBlacklist.isToggled() && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK) {
         BlockPos var1 = mc.objectMouseOver.getBlockPos();
         if (var1 == null) {
            return false;
         } else {
            IBlockState var2 = mc.theWorld.getBlockState(var1);
            Block var3 = var2.getBlock();
            Object var4 = var3 == null ? null : Block.blockRegistry.getNameForObject(var3);
            if (var4 == null) {
               return false;
            } else {
               String var5 = var4.toString();
               int var6 = var3.getMetaFromState(var2);
               return this.blockListSetting.containsEntry(var6 == 0 ? var5 : var5 + ":" + var6) || this.blockListSetting.containsEntry(var5);
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public List<Module$2> getSettingAliases() {
      return BIqm(
         buildSettingAlias(
            "Target",
            "Delay",
            new String[]{"blocks only", "block blacklist", "held item blacklist"},
            new String[]{"Blocks only", "Block blacklist", "Held item blacklist"}
         ),
         buildSettingAlias("Conditions", "Target", new String[]{"pitch check"}, new String[]{"Pitch check"})
      );
   }
}
