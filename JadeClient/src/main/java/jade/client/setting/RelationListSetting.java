// Jade recovery: original class: jade.deps.eLz.YcjzCkyoQ8
package jade.client.setting;

import jade.client.common.RelationManager$0;
import jade.client.common.RelationManager$1;
import java.util.List;

public class RelationListSetting extends LabelSetting {
   public GroupSetting groupSetting;
   private final RelationTypeAccessor relationStore;
   private final String inputHint;
   private final int maxNameLength;

   public RelationListSetting(GroupSetting var1, String var2, RelationManager$1 var3, String var4, int var5) {
      super(var2);
      this.groupSetting = var1;
      this.relationStore = new RelationTypeAccessor(var3);
      this.inputHint = var4 == null ? "" : var4;
      this.maxNameLength = var5 > 0 ? var5 : 1;
   }

   public RelationManager$1 getRelationType() {
      return this.relationStore.getRelationType();
   }

   public String getInputHint() {
      return this.inputHint;
   }

   public int getMaxNameLength() {
      return this.maxNameLength;
   }

   public boolean addRelation(String var1) {
      return this.relationStore.toggleRelation(var1);
   }

   public boolean removeRelation(String var1) {
      return this.relationStore.removeRelation(var1);
   }

   public void clearRelations() {
      this.relationStore.clearRelations();
   }

   public List<RelationManager$0> getRelations() {
      return this.relationStore.MylnR();
   }

   @Override
   public String getPath() {
      return this.groupSetting == null ? this.getName() : this.groupSetting.getName() + "." + this.getName();
   }
}
