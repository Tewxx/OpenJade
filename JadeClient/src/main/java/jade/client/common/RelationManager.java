// Jade recovery: original class: jade.deps.eLz.CFLW0c51e
package jade.client.common;

import jade.deps.loader107.InjectionPaths;
import java.io.File;
import java.util.List;

public class RelationManager implements IMinecraft {
   private final File file;
   private final RelationStore relationStore = new RelationStore();
   private boolean byJs1 = true;
   private boolean middleClickFriends;

   public RelationManager() {
      File var1 = InjectionPaths.dataDirectory(mc.mcDataDir);
      if (!var1.exists()) {
         var1.mkdirs();
      }

      this.file = new File(var1, "players.json");
   }

   public void load() {
      this.relationStore.svviY();
      this.byJs1 = true;
      this.middleClickFriends = false;
      if (this.file.exists()) {
         try {
            RelationsSettings var1 = RelationsJson.load(this.file, this.relationStore);
            this.byJs1 = var1.isActive();
            this.middleClickFriends = var1.isMiddleClickFriends();
         } catch (Exception var2) {
            var2.printStackTrace();
         }
      }

      RelationSyncHelper.KQZFyJ(this.relationStore);
   }

   public boolean addFriend(String var1) {
      return this.ZUCSul(RelationManager$1.FRIEND, var1);
   }

   public boolean addEnemy(String var1) {
      return this.ZUCSul(RelationManager$1.ENEMY, var1);
   }

   public boolean removeFriend(String var1) {
      return this.removeRelation(RelationManager$1.FRIEND, var1);
   }

   public boolean RLDdY(String var1) {
      return this.removeRelation(RelationManager$1.ENEMY, var1);
   }

   public void clearFriends() {
      this.EtkKa(RelationManager$1.FRIEND);
   }

   public void clearEnemies() {
      this.EtkKa(RelationManager$1.ENEMY);
   }

   public boolean ZUCSul(RelationManager$1 var1, String var2) {
      RelationChangeResult var3 = this.relationStore.toggleRelationEntry(var1, var2);
      if (!var3.hasChanged()) {
         return false;
      } else {
         this.saveAndNotify();
         return var3.isNewEntry();
      }
   }

   public boolean removeRelation(RelationManager$1 var1, String var2) {
      if (!this.relationStore.removeRelation(var1, var2)) {
         return false;
      } else {
         this.saveAndNotify();
         return true;
      }
   }

   public void EtkKa(RelationManager$1 var1) {
      if (this.relationStore.clearRelations(var1)) {
         this.saveAndNotify();
      }
   }

   public boolean isFriend(String var1) {
      return this.relationStore.hasRelation(RelationManager$1.FRIEND, var1);
   }

   public boolean mHh2(String var1) {
      return this.relationStore.hasRelation(RelationManager$1.ENEMY, var1);
   }

   public int getRelationCount(RelationManager$1 var1) {
      return this.relationStore.WHpE(var1);
   }

   public List<RelationManager$0> Ayorz(RelationManager$1 var1) {
      return this.relationStore.getEntries(var1);
   }

   public boolean isMiddleClickFriends() {
      return this.middleClickFriends;
   }

   public boolean isActive() {
      return this.byJs1;
   }

   public void setActive(boolean var1) {
      if (this.byJs1 != var1) {
         this.byJs1 = var1;
         this.saveRelations();
         RelationSyncHelper.LIxB(this.byJs1, this.middleClickFriends);
      }
   }

   public void ADaZ8(boolean var1) {
      if (this.middleClickFriends != var1) {
         this.middleClickFriends = var1;
         this.saveRelations();
         RelationSyncHelper.LIxB(this.byJs1, this.middleClickFriends);
      }
   }

   public void refreshDisplayName(String var1) {
      if (this.relationStore.updateDisplayName(var1)) {
         this.saveAndNotify();
      }
   }

   private void saveAndNotify() {
      this.saveRelations();
      RelationSyncHelper.KQZFyJ(this.relationStore);
      RelationSyncHelper.LIxB(this.byJs1, this.middleClickFriends);
      RelationSyncHelper.refreshEspEntries();
      RelationSyncHelper.refreshPlayerListComponents();
   }

   private void saveRelations() {
      try {
         RelationsJson.TNiu(this.file, this.relationStore, new RelationsSettings(this.byJs1, this.middleClickFriends));
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }
}
