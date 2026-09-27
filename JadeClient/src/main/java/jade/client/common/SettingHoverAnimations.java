// Jade recovery: original class: jade.deps.eLz.eNg4CcT3D0
package jade.client.common;

import jade.client.setting.Setting;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public final class SettingHoverAnimations {
   private final Map<Setting, Map<String, SmoothedFloat>> qcuW = new IdentityHashMap<>();

   public void clearAnimations() {
      this.qcuW.clear();
   }

   public float getHoverProgress(Setting var1, String var2, boolean var3) {
      Map<String, SmoothedFloat> var4 = this.qcuW.get(var1);
      if (var4 == null) {
         var4 = new HashMap();
         this.qcuW.put(var1, (Map<String, SmoothedFloat>)var4);
      }

      SmoothedFloat var5 = (SmoothedFloat)var4.get(var2);
      if (var5 == null) {
         var5 = new SmoothedFloat(0.0F);
         var4.put(var2, var5);
      }

      return var5.smoothTowards(var3 ? 1.0F : 0.0F);
   }

   public int renderHoverRow(Setting var1, String var2, GuiRect var3, GuiRect var4, int var5, int var6) {
      float var7 = this.getHoverProgress(var1, "row:" + var2, var3.contains(var5, var6));
      GuiIcons.fillRect(var3.x, var3.ufe, var3.busF, var3.HfS, Math.round(48.0F * var7) << 24 | 16777215);
      float var8 = this.getHoverProgress(var1, "remove:" + var2, var4.contains(var5, var6));
      int var9 = -4208434;
      int var10 = -40350;
      int var11 = Math.round((var9 >> 16 & 0xFF) + ((var10 >> 16 & 0xFF) - (var9 >> 16 & 0xFF)) * var8);
      int var12 = Math.round((var9 >> 8 & 0xFF) + ((var10 >> 8 & 0xFF) - (var9 >> 8 & 0xFF)) * var8);
      int var13 = Math.round((var9 & 0xFF) + ((var10 & 0xFF) - (var9 & 0xFF)) * var8);
      return 0xFF000000 | var11 << 16 | var12 << 8 | var13;
   }
}
