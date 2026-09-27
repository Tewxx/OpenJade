// Jade recovery: original class: jade.deps.eLz.R0kiIxiv5
package jade.client.module.render.blockesp;

import jade.client.module.Module;
import jade.client.setting.BlockColorListSetting;
import jade.client.setting.BooleanSetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;

public final class BlockEspSettings {
   public final BlockColorListSetting blocks;
   public final BooleanSetting outline;
   public final BooleanSetting shade;
   public final SliderSetting range;
   public final SliderSetting searchSpeed;
   public final SliderSetting maxBlocksRendered;
   public final SliderSetting maxPerChunk;
   public final BooleanSetting gameCheck;

   public BlockEspSettings(Module var1) {
      this.blocks = registerSetting(var1, new BlockColorListSetting("Blocks"));
      this.outline = registerSetting(var1, new BooleanSetting("Outline", true));
      this.shade = registerSetting(
         var1,
         new BooleanSetting("Shade", false)
      );
      this.range = registerSetting(var1, new SliderSetting("Range", 64.0, 8.0, 256.0, 8.0));
      this.searchSpeed = registerSetting(var1, new SliderSetting("Search Speed", 8.0, 1.0, 64.0, 1.0));
      this.maxBlocksRendered = registerSetting(
         var1, new SliderSetting("Max Blocks Rendered", 2048.0, 128.0, 8192.0, 128.0, new String[]{"Max renders"})
      );
      this.maxPerChunk = registerSetting(var1, new SliderSetting("Max Per Chunk", 50.0, 1.0, 50.0, 1.0));
      this.gameCheck = registerSetting(
         var1,
         new BooleanSetting(
            "Game check",
            false,
            new String[]{"Only in-game"}
         )
      );
   }

   private static <S extends Setting> S registerSetting(Module var0, S var1) {
      var0.registerSetting(var1);
      return (S)var1;
   }
}
