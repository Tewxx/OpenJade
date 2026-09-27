// Jade recovery: original class: jade.deps.eLz.p2OIkq
package jade.client.module.client.relationships;

import jade.client.Jade;
import jade.client.common.RelationManager$1;
import jade.client.setting.BooleanSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.RelationListSetting;

public final class RelationshipsSettings {
   public final GroupSetting NvNt0 = createGroup("Friends");
   public final GroupSetting enemiesGroup = createGroup("Enemies");
   public final GroupSetting middleClickGroup = createGroup("Middle click");
   public final RelationListSetting JwlHo = gvNk(this.NvNt0, RelationManager$1.FRIEND);
   public final RelationListSetting enemiesList = gvNk(this.enemiesGroup, RelationManager$1.ENEMY);
   public final BooleanSetting middleClickFriends = new BooleanSetting(
      this.middleClickGroup,
      "Middle click friends",
      ((Jade.relationManager != null && Jade.relationManager.isMiddleClickFriends()
         ? 1
         : 0) != 0)
   );

   private static GroupSetting createGroup(String var0) {
      GroupSetting var1 = new GroupSetting(var0);
      var1.setExpanded(true);
      return var1;
   }

   private static RelationListSetting gvNk(GroupSetting var0, RelationManager$1 var1) {
      return new RelationListSetting(var0, "Players", var1, "Type a username", 32);
   }
}
