// Jade recovery: module: Block ESP (render); original class: jade.deps.eLz.bvJ3ymOl
package jade.client.module.render;

import jade.client.common.BlockScanner;
import jade.client.common.ClientUtils;
import jade.client.common.Subscribe;
import jade.client.common.ExternalRenderableModule;
import jade.client.event.RenderWorldLastEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.render.blockesp.BlockEspSettings;
import jade.client.module.render.blockesp.BlockEspScanLimits;
import jade.client.module.render.blockesp.FYXBuabS7;
import jade.client.module.render.blockesp.BlockEspRenderer$0;
import jade.client.module.render.blockesp.BlockEspRenderer;

@ModuleInfo(aliases = "BlockESP")
public class BlockESP extends Module implements ExternalRenderableModule {
   private final BlockEspSettings blockEspSettings = new BlockEspSettings(this);
   private int lastEntryHash;

   public BlockESP() {
      super("Block ESP", Category.render);
   }

   @Override
   public void onEnable() {
      BlockScanner var1 = BlockScanner.getInstance();
      var1.hokXq4(this.blockEspSettings.blocks);
      this.lastEntryHash = this.blockEspSettings.blocks.getEntries().hashCode();
      if (!this.blockEspSettings.blocks.getEntries().isEmpty()) {
         var1.Ziwu();
      }
   }

   @Override
   public void onDisable() {
      BlockScanner.getInstance().vUnwA();
   }

   @Override
   public void onUpdate() {
      if (ClientUtils.isInWorld()) {
         int var1 = this.blockEspSettings.blocks.getEntries().hashCode();
         if (var1 != this.lastEntryHash) {
            this.lastEntryHash = var1;
            BlockScanner.getInstance().Ziwu();
         }
      }
   }

   public int getSearchSpeed() {
      boolean var1 = !this.blockEspSettings.gameCheck.isToggled() || FYXBuabS7.isBedWarsGame();
      return BlockEspScanLimits.resolveBlocksPerTick(this.isEnabled(), !this.blockEspSettings.blocks.getEntries().isEmpty(), var1, this.blockEspSettings.searchSpeed.getInput());
   }

   @Override
   public String getInfo() {
      int var1 = BlockScanner.getInstance().getTrackedBlockCount();
      return var1 > 0 ? String.valueOf(var1) : "";
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      BlockScanner var2 = BlockScanner.getInstance();
      if (var2.isScanningActive() && ClientUtils.isInWorld()) {
         if (!this.blockEspSettings.gameCheck.isToggled() || FYXBuabS7.isBedWarsGame()) {
            double var3 = this.blockEspSettings.range.getInput();
            BlockEspRenderer$0 var5 = new BlockEspRenderer$0(
               var3 * var3, (int)this.blockEspSettings.maxBlocksRendered.getInput(), this.getMaxPerChunk(), this.blockEspSettings.outline.isToggled(), this.blockEspSettings.shade.isToggled(), this.VIuQ()
            );
            BlockEspRenderer.renderNearbyBlocks(mc, var2, this.blockEspSettings.blocks, var5);
         }
      }
   }

   private int getMaxPerChunk() {
      return BlockEspScanLimits.resolveMaxPerChunk(this.blockEspSettings.maxPerChunk.getInput());
   }
}
