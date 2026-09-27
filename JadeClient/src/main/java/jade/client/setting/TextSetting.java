// Jade recovery: original class: jade.deps.eLz.o16e2C
package jade.client.setting;

import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonPrimitive;

public class TextSetting extends Setting {
   public GroupSetting groupSetting;
   private final String placeholder;
   private final int maxLength;
   private final Runnable changeCallback;
   private final String[] aliases;
   private String value;

   public TextSetting(String var1, String var2, String var3, int var4) {
      this(null, var1, var2, var3, var4, null);
   }

   public TextSetting(String var1, String var2, String var3, int var4, Runnable var5) {
      this(null, var1, var2, var3, var4, var5);
   }

   public TextSetting(GroupSetting var1, String var2, String var3, String var4, int var5) {
      this(var1, var2, var3, var4, var5, null);
   }

   public TextSetting(GroupSetting var1, String var2, String var3, String var4, int var5, Runnable var6) {
      this(var1, var2, var3, var4, var5, var6, new String[0]);
   }

   public TextSetting(GroupSetting var1, String var2, String var3, String var4, int var5, Runnable var6, String... var7) {
      super(var2);
      this.groupSetting = var1;
      this.placeholder = var4 == null ? "" : var4;
      this.maxLength = var5 > 0 ? var5 : 1;
      this.changeCallback = var6;
      this.aliases = var7 == null ? new String[0] : var7;
      this.setValue(var3);
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String var1) {
      if (var1 == null) {
         this.value = "";
      } else {
         this.value = var1.substring(0, Math.min(this.maxLength, var1.length()));
      }
   }

   public String getPlaceholder() {
      return this.placeholder;
   }

   public int xmB5() {
      return this.maxLength;
   }

   @Override
   public String getPath() {
      return this.groupSetting == null ? this.getName() : this.groupSetting.getName() + "." + this.getName();
   }

   public void runChangeCallback() {
      if (this.changeCallback != null) {
         this.changeCallback.run();
      }
   }

   @Override
   public void loadConfig(JsonObject var1) {
      JsonPrimitive var2 = JsonConfigHelper.MCzK(var1, this.getPath(), this.getName(), this.aliases);
      if (var2 != null) {
         try {
            this.setValue(var2.getAsString());
         } catch (Exception var4) {
         }
      }
   }
}
