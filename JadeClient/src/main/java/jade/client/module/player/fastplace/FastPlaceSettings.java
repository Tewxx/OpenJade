// Jade recovery: original class: jade.deps.eLz.c1Toxf4
package jade.client.module.player.fastplace;

import jade.client.setting.BlockListSetting;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ItemListSetting;
import jade.client.setting.SliderSetting;

public final class FastPlaceSettings {
   public final SliderSetting tickDelay = new SliderSetting(
      "Tick delay", 1, new String[]{"1 tick", "2 ticks", "3 ticks", "4 ticks"}
   );
   public final SliderSetting activationTime = new SliderSetting(
      "Activation time", "ms", 0.0, 0.0, 100.0, 5.0
   );
   public final BooleanSetting blocksOnly = new BooleanSetting(
      "Blocks only", true
   );
   public final BooleanSetting pitchCheck = new BooleanSetting("Pitch check", false);
   public final BooleanSetting heldItemBlacklist = new BooleanSetting("Held item blacklist", false, new String[]{"Ignore held items", "Restrict held items", "Allow while holding"});
   public final ItemListSetting heldItems = new ItemListSetting("Held items", new String[]{"Items"});
   public final BooleanSetting blockBlacklist = new BooleanSetting(
      "Block blacklist",
      false,
      new String[]{"Blocks.Block blacklist"}
   );
   public final BlockListSetting blacklistedBlocks = new BlockListSetting("Blacklisted blocks", new String[]{"Block blacklist", "Blocks.Block blacklist", "Blocks.Blacklisted blocks"});
}
