// Jade recovery: original class: jade.deps.eLz.IX3DzXa
package jade.client.common;

import jade.client.Jade;
import jade.client.core.ConfigProfile;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.client.Gui;
import jade.client.module.profiles.ProfileManager;

public final class ModuleEntryHelper {
   private ModuleEntryHelper() {
   }

   public static void Lyxy7(ModuleComponent var0, HoverHighlightAnimation var1, boolean var2) {
      float var3 = var0.categoryComponent.EWZxNc();
      float var4 = var0.categoryComponent.getPanelY() + var0.HPu;
      if (var2 && var1.isAnimating()) {
         int var5 = (int)var1.UJTRN(System.currentTimeMillis());
         RenderUtils.jxyoE(var3, var4, var3 + var0.categoryComponent.getPanelWidth(), var0.categoryComponent.getPanelY() + 16.0F + var0.HPu, 1.0F, var5 << 24);
      }

      Module var9 = var0.module;
      boolean var6 = var9.isEnabled();
      boolean var7 = var9.getCategory() == Category.profiles
         && !(var9 instanceof ProfileManager)
         && !((ConfigProfile)var9).unmodified
         && Jade.Grq != null
         && Jade.Grq.getProfile() == var9;
      IFont var8 = Gui.getHeaderFont();
      if (var2) {
         var8.drawString(var9.getName(), var3 + 5.0F, var4 + 4.0F, ClickGuiRowHelper.getModuleNameColor(var6, var7), true);
      }
   }

   public static void toggleModule(Module var0) {
      var0.toggle();
      if (var0.getCategory() != Category.profiles && Jade.Grq != null) {
         Jade.Grq.getProfile().unmodified = false;
      }
   }
}
