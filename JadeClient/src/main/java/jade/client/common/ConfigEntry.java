// Jade recovery: original class: jade.deps.eLz.tnbo69WaA
package jade.client.common;

import jade.client.core.ConfigProfile;

public class ConfigEntry {
   private final ConfigProfile configProfile;
   private final ConfigProfileInfo configData;

   public ConfigEntry(String var1, int var2) {
      this.configData = new ConfigProfileInfo(var1, var2);
      this.configProfile = new ConfigProfile(this, var1, var2);
      this.configProfile.skipSettingsPersistence = true;
   }

   public ConfigProfile getProfile() {
      return this.configProfile;
   }

   public int Xykpb() {
      return this.configData.getIndex();
   }

   public String getName() {
      return this.configData.getName();
   }

   public void rename(String var1) {
      this.configData.setName(var1);
      this.configProfile.VPe8(var1);
   }

   public String getThemeName() {
      return this.configData.oAagW();
   }

   public void odjy(String var1) {
      this.configData.setGroup(var1);
   }
}
