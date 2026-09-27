// Jade recovery: original class: jade.deps.eLz.bs6fJMN
package jade.client.common;

public final class ConfigProfileInfo {
   private static final String DEFAULT_GROUP = "jade";
   private final int eTq;
   private String name;
   private String IXtQ = "jade";

   public ConfigProfileInfo(String var1, int var2) {
      this.name = var1;
      this.eTq = var2;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public int getIndex() {
      return this.eTq;
   }

   public String oAagW() {
      return this.IXtQ;
   }

   public void setGroup(String var1) {
      this.IXtQ = var1 != null && !var1.trim().isEmpty() ? var1 : "jade";
   }
}
