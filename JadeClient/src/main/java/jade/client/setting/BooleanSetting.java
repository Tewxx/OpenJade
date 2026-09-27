// Jade recovery: original class: jade.deps.eLz.ZyV7ftPOFY
package jade.client.setting;

import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonPrimitive;

public class BooleanSetting extends Setting {
   public boolean isButton;
   public GroupSetting group;
   private final String label;
   private final String[] aliases;
   private boolean toggled;
   private Runnable action;
   private Runnable changeListener;
   private String buttonText = "Run";

   public BooleanSetting(String var1, boolean var2) {
      this(null, var1, var2);
   }

   public BooleanSetting(String var1, boolean var2, String... var3) {
      this(null, var1, var2, var3);
   }

   public BooleanSetting(GroupSetting var1, String var2, boolean var3) {
      this(var1, var2, var3, new String[0]);
   }

   public BooleanSetting(GroupSetting var1, String var2, boolean var3, String... var4) {
      super(var2);
      this.label = var2;
      this.group = var1;
      this.toggled = var3;
      this.aliases = var4 == null ? new String[0] : var4;
   }

   public BooleanSetting(String var1, Runnable var2) {
      this(var1, false);
      this.action = var2;
      this.isButton = true;
   }

   @Override
   public String getName() {
      return this.label;
   }

   @Override
   public String getPath() {
      return this.group == null ? this.getName() : this.group.getName() + "." + this.getName();
   }

   public boolean isToggled() {
      return this.toggled;
   }

   public void setToggled(boolean var1) {
      this.toggled = var1;
   }

   public void enable() {
      this.toggled = true;
   }

   public void disable() {
      this.toggled = false;
   }

   public void toggle() {
      this.toggled ^= true;
      this.fireChange();
   }

   public BooleanSetting onChange(Runnable var1) {
      this.changeListener = var1;
      return this;
   }

   private void fireChange() {
      if (this.changeListener != null) {
         this.changeListener.run();
      }
   }

   public void runAction() {
      if (this.action != null) {
         this.action.run();
      }
   }

   public BooleanSetting setButtonText(String var1) {
      String var2 = var1 == null ? "" : var1.trim();
      this.buttonText = var2.length() == 0 ? "Run" : var2;
      return this;
   }

   public String getButtonText() {
      return this.buttonText;
   }

   @Override
   public void loadConfig(JsonObject var1) {
      JsonPrimitive var2 = JsonConfigHelper.MCzK(var1, this.getPath(), this.getName(), this.aliases);
      if (var2 != null && !this.isButton) {
         boolean var3 = this.toggled;

         try {
            var3 = var2.getAsBoolean();
         } catch (Exception var5) {
         }

         boolean var4 = this.toggled != var3;
         this.setToggled(var3);
         if (var4) {
            this.fireChange();
         }
      }
   }
}
