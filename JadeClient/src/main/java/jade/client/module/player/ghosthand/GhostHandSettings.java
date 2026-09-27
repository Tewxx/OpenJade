// Jade recovery: original class: jade.deps.eLz.l3Je67e8
package jade.client.module.player.ghosthand;

import jade.client.module.Module;
import jade.client.setting.BooleanSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.Setting;

public final class GhostHandSettings {
   public final BooleanSetting nonPlayerEntities;
   public final BooleanSetting bots;
   public final BooleanSetting friendlies;
   public final BooleanSetting enemies;
   public final BooleanSetting everything;
   public final BooleanSetting bed;
   public final BooleanSetting nextToBed;
   public final BooleanSetting sword;
   public final BooleanSetting tool;
   public final BooleanSetting fists;
   public final BooleanSetting bucket;
   public final BooleanSetting flintAndSteel;
   public final BooleanSetting cobweb;
   public final BooleanSetting other;
   public final BooleanSetting requireLeftMouse;
   public final BooleanSetting requireRightMouse;
   public final BooleanSetting notHoldingASword;

   public GhostHandSettings(Module var1) {
      GroupSetting var2 = registerSetting(var1, new GroupSetting("Interact through"));
      this.nonPlayerEntities = registerSetting(
         var1,
         new BooleanSetting(
            var2,
            "Non-player entities",
            true
         )
      );
      this.bots = registerSetting(var1, new BooleanSetting(var2, "Bots", false));
      this.friendlies = registerSetting(
         var1,
         new BooleanSetting(
            var2, "Friendlies", false
         )
      );
      this.enemies = registerSetting(
         var1,
         new BooleanSetting(
            var2, "Enemies", true
         )
      );
      GroupSetting var3 = registerSetting(var1, new GroupSetting("Preferred targets"));
      this.everything = registerSetting(
         var1,
         new BooleanSetting(
            var3, "Everything", true
         )
      );
      this.bed = registerSetting(
         var1,
         new BooleanSetting(var3, "Bed", false)
      );
      this.nextToBed = registerSetting(
         var1,
         new BooleanSetting(
            var3, "Next to bed", false
         )
      );
      GroupSetting var4 = registerSetting(var1, new GroupSetting("Allow while using"));
      this.sword = registerSetting(var1, new BooleanSetting(var4, "Sword", false));
      this.tool = registerSetting(
         var1,
         new BooleanSetting(var4, "Tool", true)
      );
      this.fists = registerSetting(
         var1,
         new BooleanSetting(var4, "Fists", true)
      );
      this.bucket = registerSetting(var1, new BooleanSetting(var4, "Bucket", true));
      this.flintAndSteel = registerSetting(
         var1,
         new BooleanSetting(
            var4,
            "Flint and steel",
            true
         )
      );
      this.cobweb = registerSetting(
         var1,
         new BooleanSetting(var4, "Cobweb", true)
      );
      this.other = registerSetting(
         var1,
         new BooleanSetting(
            var4, "Other", true
         )
      );
      GroupSetting var5 = registerSetting(var1, new GroupSetting("Conditions"));
      this.requireLeftMouse = registerSetting(
         var1,
         new BooleanSetting(
            var5,
            "Require Left mouse",
            false
         )
      );
      this.requireRightMouse = registerSetting(var1, new BooleanSetting(var5, "Require right mouse", false));
      this.notHoldingASword = registerSetting(var1, new BooleanSetting(var5, "Not holding a sword", false));
   }

   private static <S extends Setting> S registerSetting(Module var0, S var1) {
      var0.registerSetting(var1);
      return (S)var1;
   }
}
