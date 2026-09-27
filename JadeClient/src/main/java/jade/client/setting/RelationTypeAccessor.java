// Jade recovery: original class: jade.deps.eLz.eGRjIbI0A
package jade.client.setting;

import jade.client.Jade;
import jade.client.common.RelationManager$0;
import jade.client.common.RelationManager$1;
import jade.client.common.RelationManager;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public final class RelationTypeAccessor {
   private final RelationManager$1 relationType;

   public RelationTypeAccessor(RelationManager$1 var1) {
      this.relationType = var1;
   }

   public RelationManager$1 getRelationType() {
      return this.relationType;
   }

   private <T> T withRelationManager(Function<RelationManager, T> var1, T var2) {
      RelationManager var3 = Jade.relationManager;
      return (T)(var3 == null ? var2 : var1.apply(var3));
   }

   public boolean toggleRelation(String var1) {
      return this.withRelationManager((recoveredArg0) -> this.toggleRelationOnManager(var1, recoveredArg0), false);
   }

   public boolean removeRelation(String var1) {
      return this.withRelationManager((recoveredArg0) -> this.addRelationOnManager(var1, recoveredArg0), false);
   }

   public List<RelationManager$0> MylnR() {
      return this.withRelationManager(this::getRelationsFromManager, Collections.emptyList());
   }

   public void clearRelations() {
      this.withRelationManager(this::clearRelationsOnManager, null);
   }

   private Object clearRelationsOnManager(RelationManager var1) {
      var1.EtkKa(this.relationType);
      return null;
   }

   private List getRelationsFromManager(RelationManager var1) {
      return var1.Ayorz(this.relationType);
   }

   private Boolean addRelationOnManager(String var1, RelationManager var2) {
      return var2.removeRelation(this.relationType, var1);
   }

   private Boolean toggleRelationOnManager(String var1, RelationManager var2) {
      return var2.ZUCSul(this.relationType, var1);
   }
}
