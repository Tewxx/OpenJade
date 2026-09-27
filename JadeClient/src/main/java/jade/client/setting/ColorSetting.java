// Jade recovery: original class: jade.deps.eLz.EzMs54
package jade.client.setting;

import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonPrimitive;

import java.awt.Color;

public class ColorSetting extends Setting {
   public GroupSetting groupSetting;
   private final boolean ho8;
   private int njq;

   public ColorSetting(String var1, int var2, int var3, int var4) {
      this(
         null,
         var1,
         var2,
         var3,
         var4,
         255,
         false
      );
   }

   public ColorSetting(String var1, int var2, int var3, int var4, int var5) {
      this(null, var1, var2, var3, var4, var5, true);
   }

   public ColorSetting(GroupSetting var1, String var2, int var3, int var4, int var5) {
      this(var1, var2, var3, var4, var5, 255, false);
   }

   public ColorSetting(GroupSetting var1, String var2, int var3, int var4, int var5, int var6) {
      this(var1, var2, var3, var4, var5, var6, true);
   }

   public ColorSetting(GroupSetting var1, String var2, int var3, int var4, int var5, int var6, boolean var7) {
      super(var2);
      this.groupSetting = var1;
      this.ho8 = var7;
      this.setChannel(16, var3);
      this.setChannel(8, var4);
      this.setChannel(0, var5);
      this.setChannel(24, var6);
   }

   private void setChannel(int var1, int var2) {
      int var3 = var2 < 0 ? 0 : Math.min(var2, 255);
      this.njq = this.njq & ~(255 << var1) | var3 << var1;
   }

   public int getRed() {
      return this.njq >>> 16 & 0xFF;
   }

   public int getGreen() {
      return this.njq >>> 8 & 0xFF;
   }

   public int getBlue() {
      return this.njq & 0xFF;
   }

   public int JIjrD() {
      return this.njq >>> 24;
   }

   public int getArgb() {
      return this.njq;
   }

   public int getRgb() {
      return this.njq & 16777215;
   }

   public boolean supportsAlpha() {
      return this.ho8;
   }

   public void setAlpha(int var1) {
      this.setChannel(24, var1);
   }

   public void setRgb(int var1, int var2, int var3) {
      this.setChannel(16, var1);
      this.setChannel(8, var2);
      this.setChannel(0, var3);
   }

   public void setRgba(int var1, int var2, int var3, int var4) {
      this.setRgb(var1, var2, var3);
      this.setChannel(24, var4);
   }

   private float[] getHsbComponents() {
      return Color.RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), null);
   }

   public float getHue() {
      return this.getHsbComponents()[0] * 360.0F;
   }

   public float getSaturation() {
      return this.getHsbComponents()[1];
   }

   public float pBf3() {
      return this.getHsbComponents()[2];
   }

   public void TAfvrw(float var1, float var2, float var3) {
      int var4 = Color.HSBtoRGB(var1 / 360.0F, Math.max(0.0F, Math.min(1.0F, var2)), Math.max(0.0F, Math.min(1.0F, var3)));
      this.njq = this.njq & 0xFF000000 | var4 & 16777215;
   }

   public void oAej(float var1) {
      float[] var2 = this.getHsbComponents();
      this.TAfvrw(var1, var2[1], var2[2]);
   }

   public void setSaturation(float var1) {
      float[] var2 = this.getHsbComponents();
      this.TAfvrw(var2[0] * 360.0F, var1, var2[2]);
   }

   public void setBrightness(float var1) {
      float[] var2 = this.getHsbComponents();
      this.TAfvrw(var2[0] * 360.0F, var2[1], var1);
   }

   @Override
   public String getPath() {
      return this.groupSetting == null ? this.getName() : this.groupSetting.getName() + "." + this.getName();
   }

   @Override
   public void loadConfig(JsonObject var1) {
      JsonPrimitive var2 = JsonConfigHelper.MCzK(var1, this.getPath(), this.getName(), new String[0]);
      if (var2 != null) {
         try {
            String[] var3 = var2.getAsString().split(",");
            if (var3.length < 3) {
               return;
            }

            int[] var4 = new int[]{16, 8, 0, 24};

            for (int var5 = 0; var5 < Math.min(4, var3.length); var5++) {
               this.setChannel(var4[var5], Integer.parseInt(var3[var5].trim()));
            }
         } catch (Exception var6) {
         }
      }
   }
}
