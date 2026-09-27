// Jade recovery: original class: jade.deps.eLz.TdrIqzww
package jade.client.module.render.indicators;

import jade.client.module.Module;
import jade.client.setting.BooleanSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.Setting;
import jade.client.setting.SliderSetting;

public final class IndicatorSettings {
   public final BooleanSetting renderArrows;
   public final BooleanSetting renderEnderPearls;
   public final BooleanSetting renderFireballs;
   public final BooleanSetting renderEggs;
   public final BooleanSetting renderSnowballs;
   public final SliderSetting radius;
   public final FontSetting font;
   public final BooleanSetting itemColors;
   public final BooleanSetting renderItem;
   public final BooleanSetting renderDistance;
   public final BooleanSetting onlyWhenApproaching;
   public final BooleanSetting renderOnlyOffscreen;

   public IndicatorSettings(Module var1) {
      GroupSetting var2 = registerSetting(var1, new GroupSetting("Items"));
      this.renderArrows = registerSetting(
         var1,
         new BooleanSetting(
            var2, "Render arrows", true
         )
      );
      this.renderEnderPearls = registerSetting(var1, new BooleanSetting(var2, "Render ender pearls", true));
      this.renderFireballs = registerSetting(
         var1,
         new BooleanSetting(
            var2, "Render fireballs", true
         )
      );
      this.renderEggs = registerSetting(
         var1,
         new BooleanSetting(
            var2, "Render eggs", false
         )
      );
      this.renderSnowballs = registerSetting(var1, new BooleanSetting(var2, "Render snowballs", false));
      this.radius = registerSetting(var1, new SliderSetting("Radius", 50.0, 30.0, 200.0, 5.0));
      this.font = registerSetting(var1, new FontSetting("Font", "Modern"));
      this.itemColors = registerSetting(
         var1,
         new BooleanSetting("Item colors", true)
      );
      this.renderItem = registerSetting(var1, new BooleanSetting("Render item", true));
      this.renderDistance = registerSetting(var1, new BooleanSetting("Render distance", true));
      this.onlyWhenApproaching = registerSetting(
         var1,
         new BooleanSetting(
            "Only when approaching", false
         )
      );
      this.renderOnlyOffscreen = registerSetting(var1, new BooleanSetting("Render only offscreen", false));
   }

   private static <S extends Setting> S registerSetting(Module var0, S var1) {
      var0.registerSetting(var1);
      return (S)var1;
   }
}
