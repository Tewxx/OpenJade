// Jade recovery: original class: jade.deps.eLz.HqlHYPQx
package jade.client.setting;

import jade.deps.gson.JsonObject;

public class MultiSelectSetting extends Setting {
   private final BooleanSetting[] YiR23;
   private final String[] optionLabels;
   private boolean expanded;

   public MultiSelectSetting(String var1, BooleanSetting... var2) {
      this(var1, null, var2);
   }

   public MultiSelectSetting(String var1, String[] var2, BooleanSetting... var3) {
      super(var1);
      this.YiR23 = var3 == null ? new BooleanSetting[0] : var3;
      this.optionLabels = var2;
   }

   public BooleanSetting[] awwHd() {
      return this.YiR23;
   }

   public boolean isExpanded() {
      return this.expanded;
   }

   public String getLabelFor(BooleanSetting var1) {
      for (int var2 = 0; var2 < this.YiR23.length; var2++) {
         if (this.YiR23[var2] == var1) {
            return this.getOptionLabel(var2);
         }
      }

      return var1 == null ? "" : var1.getName();
   }

   public String getOptionLabel(int var1) {
      if (var1 < 0 || var1 >= this.YiR23.length) {
         return "";
      } else if (this.optionLabels != null && var1 < this.optionLabels.length && this.optionLabels[var1] != null && !this.optionLabels[var1].isEmpty()) {
         return this.optionLabels[var1];
      } else {
         BooleanSetting var2 = this.YiR23[var1];
         return var2 == null ? "" : var2.getName();
      }
   }

   public void Gfaah(boolean var1) {
      this.expanded = var1;
   }

   public String getSummaryText() {
      StringBuilder var1 = new StringBuilder();

      for (BooleanSetting var5 : this.YiR23) {
         if (var5 != null && var5.isToggled()) {
            if (var1.length() > 0) {
               var1.append(", ");
            }

            var1.append(this.getLabelFor(var5));
         }
      }

      return var1.length() == 0 ? "None" : var1.toString();
   }

   @Override
   public void loadConfig(JsonObject var1) {
   }
}
