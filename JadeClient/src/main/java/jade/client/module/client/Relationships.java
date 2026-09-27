// Jade recovery: module: Relationships (client); original class: jade.deps.eLz.CgZtbc
package jade.client.module.client;

import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.client.relationships.RelationshipsBridge;
import jade.client.module.client.relationships.RelationshipsSettings;
import jade.client.setting.BooleanSetting;
import jade.client.setting.GroupSetting;
import jade.client.setting.RelationListSetting;

@ModuleInfo
public class Relationships extends Module {
   public final GroupSetting FEqfXq;
   public final GroupSetting enemiesGroup;
   public final GroupSetting middleClickGroup;
   public final RelationListSetting friendsList;
   public final RelationListSetting DBaQq9;
   public final BooleanSetting booleanSetting;

   public Relationships() {
      super(
         "Relationships",
         Category.client,
         0
      );
      RelationshipsSettings var1 = new RelationshipsSettings();
      this.FEqfXq = var1.NvNt0;
      this.enemiesGroup = var1.enemiesGroup;
      this.middleClickGroup = var1.middleClickGroup;
      this.friendsList = var1.JwlHo;
      this.DBaQq9 = var1.enemiesList;
      this.booleanSetting = var1.middleClickFriends;
      this.registerSetting(this.FEqfXq);
      this.registerSetting(this.friendsList);
      this.registerSetting(this.enemiesGroup);
      this.registerSetting(this.DBaQq9);
      this.registerSetting(this.middleClickGroup);
      this.registerSetting(this.booleanSetting);
      this.skipSettingsPersistence = true;
      this.hidden = true;
   }

   @Override
   public boolean isEnabledByDefault() {
      return RelationshipsBridge.isRelationshipsEnabled();
   }

   @Override
   public void guiButtonToggled(BooleanSetting var1) {
      if (var1 == this.booleanSetting) {
         RelationshipsBridge.setMiddleClickFriends(var1.isToggled());
      }
   }

   @Override
   public void onEnable() {
      RelationshipsBridge.PnqK(true);
   }

   @Override
   public void onDisable() {
      RelationshipsBridge.PnqK(false);
   }
}
