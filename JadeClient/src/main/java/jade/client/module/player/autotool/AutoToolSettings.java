// Jade recovery: original class: jade.deps.eLz.wlJs8nke
package jade.client.module.player.autotool;

import jade.client.module.Module;
import jade.client.setting.BlockListSetting;
import jade.client.setting.BooleanSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.ItemListSetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;

public final class AutoToolSettings {
   public final SliderSetting holdDelay;
   public final SliderSetting hoverDelay;
   public final BooleanSetting heldItemBlacklist;
   public final ItemListSetting heldItems;
   public final BooleanSetting onlyWhileCrouching;
   public final BooleanSetting requireLeftMouse;
   public final BooleanSetting switchBackWhenDone;
   public final BooleanSetting blockWhitelist;
   public final BlockListSetting whitelistedBlocks;
   public final BooleanSetting blockBlacklist;
   public final BlockListSetting blacklistedBlocks;

   public AutoToolSettings(Module var1) {
      GroupSetting var2 = TFzaUd(var1, new GroupSetting("Timing"));
      this.holdDelay = TFzaUd(
         var1,
         new SliderSetting(
            var2,
            "Hold delay",
            "ms",
            0.0,
            0.0,
            1000.0,
            25.0,
            new String[]{"Timing.Activation time", "Activation time", "Timing.Activiation Time", "Activiation Time"}
         )
      );
      this.hoverDelay = TFzaUd(
         var1,
         new SliderSetting(
            var2, "Hover delay", "ms", 0.0, 0.0, 1000.0, 25.0
         )
      );
      GroupSetting var3 = TFzaUd(var1, new GroupSetting("Conditions"));
      this.onlyWhileCrouching = TFzaUd(
         var1,
         new BooleanSetting(
            var3,
            "Only while crouching",
            false
         )
      );
      this.requireLeftMouse = TFzaUd(var1, new BooleanSetting(var3, "Require Left mouse", true, new String[]{"Require mouse down"}));
      GroupSetting var4 = TFzaUd(var1, new GroupSetting("Swap"));
      this.switchBackWhenDone = TFzaUd(
         var1,
         new BooleanSetting(
            var4,
            "Switch back when done",
            true,
            new String[]{"Swap to previous slot"}
         )
      );
      this.heldItemBlacklist = TFzaUd(
         var1,
         new BooleanSetting(
            "Held item blacklist",
            false,
            new String[]{"Ignore held items", "Restrict held items", "Allow while holding"}
         )
      );
      this.heldItems = TFzaUd(var1, new ItemListSetting("Held items", new String[]{"Items"}));
      this.blockWhitelist = TFzaUd(
         var1,
         new BooleanSetting(
            "Block whitelist",
            false,
            new String[]{"Restrict allowed blocks", "Blocks.Block whitelist"}
         )
      );
      this.whitelistedBlocks = TFzaUd(var1, new BlockListSetting("Whitelisted blocks", new String[]{"Blocks", "Blocks.Whitelisted blocks"}));
      this.blockBlacklist = TFzaUd(
         var1,
         new BooleanSetting(
            "Block blacklist",
            false,
            new String[]{"Blocks.Block blacklist"}
         )
      );
      this.blacklistedBlocks = TFzaUd(var1, new BlockListSetting("Blacklisted blocks", new String[]{"Block blacklist", "Blocks.Block blacklist", "Blocks.Blacklisted blocks"}));
   }

   private static <S extends Setting> S TFzaUd(Module var0, S var1) {
      var0.registerSetting(var1);
      return (S)var1;
   }
}
