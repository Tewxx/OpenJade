// Jade recovery: original class: jade.deps.eLz.B0YYK5fy1s
package jade.client.common;

public class ConfigFolder {
   private final String id;
   private String rBdp;
   private boolean collapsed;

   public ConfigFolder(String var1, String var2) {
      this.id = var1 == null ? "" : var1;
      this.rBdp = tzuus(var2);
   }

   public String getId() {
      return this.id;
   }

   public String getTitle() {
      return this.rBdp;
   }

   public boolean isCollapsed() {
      return this.collapsed;
   }

   public void setTitle(String var1) {
      this.rBdp = tzuus(var1);
   }

   public void setCollapsed(boolean var1) {
      this.collapsed = var1;
   }

   private static String tzuus(String var0) {
      String var1 = var0 == null ? "" : var0.trim();
      return var1.length() == 0 ? "New Folder" : var1;
   }
}
