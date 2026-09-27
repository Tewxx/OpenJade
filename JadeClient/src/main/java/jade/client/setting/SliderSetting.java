// Jade recovery: original class: jade.deps.eLz.Zh8hs45VLt
package jade.client.setting;

import jade.client.common.EventBus;
import jade.client.event.SliderChangeEvent;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonPrimitive;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SliderSetting extends Setting {
   public boolean isMode;
   public boolean allowsDisabled;
   public GroupSetting group;
   private final String label;
   private final SliderBounds bounds;
   private String[] options;
   private String[] aliases = new String[0];
   private String suffix = "";
   private double value;

   public SliderSetting(GroupSetting var1, String var2, double var3, double var5, double var7, double var9) {
      super(var2);
      this.label = var2;
      this.group = var1;
      this.bounds = new SliderBounds(var5, var7, var9);
      this.value = var3;
   }

   public SliderSetting(String var1, double var2, double var4, double var6, double var8) {
      this((GroupSetting)null, var1, var2, var4, var6, var8);
   }

   public SliderSetting(String var1, double var2, double var4, double var6, double var8, String... var10) {
      this((GroupSetting)null, var1, var2, var4, var6, var8);
      this.setAliases(var10);
   }

   public SliderSetting(GroupSetting var1, String var2, String var3, double var4, double var6, double var8, double var10) {
      this(var1, var2, var4, var6, var8, var10);
      this.suffix = var3;
   }

   public SliderSetting(GroupSetting var1, String var2, String var3, double var4, double var6, double var8, double var10, String... var12) {
      this(var1, var2, var3, var4, var6, var8, var10);
      this.setAliases(var12);
   }

   public SliderSetting(String var1, String var2, double var3, double var5, double var7, double var9) {
      this((GroupSetting)null, var1, var2, var3, var5, var7, var9);
   }

   public SliderSetting(String var1, boolean var2, double var3, double var5, double var7, double var9) {
      this((GroupSetting)null, var1, var3, var5, var7, var9);
      this.allowsDisabled = var2;
   }

   public SliderSetting(GroupSetting var1, String var2, boolean var3, double var4, double var6, double var8, double var10) {
      this(var1, var2, var4, var6, var8, var10);
      this.allowsDisabled = var3;
   }

   public SliderSetting(String var1, String var2, boolean var3, double var4, double var6, double var8, double var10) {
      this((GroupSetting)null, var1, var2, var4, var6, var8, var10);
      this.allowsDisabled = var3;
   }

   public SliderSetting(GroupSetting var1, String var2, int var3, String[] var4) {
      this(var1, var2, (double)var3, 0.0, (double)(var4.length - 1), 1.0);
      this.options = var4;
      this.isMode = true;
   }

   public SliderSetting(String var1, int var2, String[] var3) {
      this((GroupSetting)null, var1, var2, var3);
   }

   public SliderSetting(String var1, int var2, String[] var3, String... var4) {
      this((GroupSetting)null, var1, var2, var3);
      this.setAliases(var4);
   }

   public SliderSetting(String var1, String var2, int var3, String[] var4) {
      this((GroupSetting)null, var1, var3, var4);
      this.suffix = var2;
   }

   public SliderSetting(GroupSetting var1, String var2, String var3, int var4, String[] var5) {
      this(var1, var2, var4, var5);
      this.suffix = var3;
   }

   private void setAliases(String[] var1) {
      this.aliases = var1 == null ? new String[0] : var1;
   }

   @Override
   public String getName() {
      return this.label;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public void setSuffix(String var1) {
      this.suffix = var1;
   }

   public String[] getOptions() {
      return this.options;
   }

   public double getMin() {
      return this.bounds.getMin();
   }

   public double getMax() {
      return this.bounds.getMax();
   }

   public double getStep() {
      return this.bounds.ffe0();
   }

   public double getInput() {
      return round(this.value, 4);
   }

   public double applyValueCurve(double var1) {
      return var1;
   }

   public double toFraction(double var1) {
      return this.bounds.toFraction(var1);
   }

   public double fromFraction(double var1) {
      return this.bounds.hcHs(var1);
   }

   @Override
   public String getPath() {
      return this.group == null ? this.getName() : this.group.getName() + "." + this.getName();
   }

   public double setValueClamped(double var1) {
      this.value = this.bounds.Zmuk(var1);
      return this.value;
   }

   public void setValueRaw(double var1) {
      this.value = var1;
   }

   public void setValue(double var1) {
      double var3 = this.value;
      double var5 = this.setValueClamped(var1);
      this.fireChange(var3, var5);
   }

   public void setValueUnclamped(double var1) {
      double var3 = this.value;
      this.value = var1;
      this.fireChange(var3, var1);
   }

   private void fireChange(double var1, double var3) {
      EventBus.post(new SliderChangeEvent(var1, var3));
   }

   public static double clamp(double var0, double var2, double var4) {
      return Math.min(var4, Math.max(var2, var0));
   }

   public static double round(double var0, int var2) {
      return var2 < 0 ? 0.0 : new BigDecimal(var0).setScale(var2, RoundingMode.HALF_UP).doubleValue();
   }

   @Override
   public void loadConfig(JsonObject var1) {
      JsonPrimitive var2 = JsonConfigHelper.MCzK(var1, this.getPath(), this.getName(), this.aliases);
      if (var2 != null) {
         double var3 = this.value;

         try {
            var3 = var2.getAsDouble();
         } catch (Exception var6) {
         }

         if (var3 != -1.0) {
            this.setValueClamped(var3);
         } else {
            this.setValueRaw(var3);
         }
      }
   }
}
