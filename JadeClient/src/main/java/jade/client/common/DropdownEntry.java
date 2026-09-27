// Jade recovery: original class: jade.deps.eLz.z814ZEMaGE
package jade.client.common;

import jade.client.module.Module;
import jade.client.setting.Setting;

public final class DropdownEntry {
   public final String mzi;
   public final Module module;
   public final Setting setting;

   public DropdownEntry(String var1) {
      this.mzi = var1;
      this.module = null;
      this.setting = null;
   }

   public DropdownEntry(Module var1, Setting var2) {
      this.mzi = null;
      this.module = var1;
      this.setting = var2;
   }

   public boolean HpgN() {
      return this.mzi != null;
   }
}
