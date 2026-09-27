// Jade recovery: original class: jade.deps.eLz.JSeTqn
package jade.client.setting;

import jade.client.module.Module;
import jade.deps.gson.JsonObject;

public abstract class Setting {
   public String name;
   public boolean visible = true;

   public Setting(String var1) {
      this.name = var1;
   }

   public String getName() {
      return this.name;
   }

   public String getPath() {
      return this.name;
   }

   public abstract void loadConfig(JsonObject var1);

   public void setVisible(boolean var1, Module var2) {
      boolean var3 = this.visible != var1;
      if (var3) {
         this.visible = var1;
         MMzhau.JZYRBC(var2);
      }
   }
}
