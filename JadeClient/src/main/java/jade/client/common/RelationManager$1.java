// Jade recovery: original class: jade.deps.eLz.CFLW0c51e$1
package jade.client.common;

public enum RelationManager$1 {
   FRIEND("friends"),
   ENEMY("enemies");

   private final String storageField;

   RelationManager$1(String var3) {
      this.storageField = var3;
   }

   public String getJsonKey() {
      return this.storageField;
   }
}
