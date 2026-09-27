// Jade recovery: module: Rendering (client); original class: jade.deps.eLz.Szyn3b
package jade.client.module.client;

import jade.client.Jade;
import jade.client.common.ExternalRenderer;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.SliderSetting;

@ModuleInfo
public final class Rendering extends Module {
   private final SliderSetting output;
   private final SliderSetting externalChatX;
   private final SliderSetting externalChatY;

   public Rendering() {
      super("Rendering", Category.client, 0);
      this.registerSetting(this.output = new SliderSetting("Output", 0, new String[]{"Internal", "External"}));
      this.registerSetting(
         this.externalChatX = new SliderSetting(
            "External Chat X", "%", 0.0, 0.0, 100.0, 0.1
         )
      );
      this.registerSetting(
         this.externalChatY = new SliderSetting(
            "External Chat Y", "%", 0.0, 0.0, 100.0, 0.1
         )
      );
      this.externalChatX.visible = false;
      this.externalChatY.visible = false;
      this.canBeEnabled = false;
   }

   public static boolean isExternalOutput() {
      if (Jade.getModuleManager() == null) {
         return false;
      } else {
         Rendering var0 = Jade.getModuleManager().getModule(Rendering.class);
         return var0 != null && var0.output.getInput() == 1.0;
      }
   }

   public void setExternalOutput(boolean var1) {
      this.output.setValueClamped(var1 ? 1.0 : 0.0);
      this.refreshExternalRenderer();
   }

   public float NGLeqy() {
      return (float)this.externalChatX.getInput() / 100.0F;
   }

   public float getExternalChatY() {
      return (float)this.externalChatY.getInput() / 100.0F;
   }

   public void setExternalChatPosition(float var1, float var2) {
      this.externalChatX.setValueClamped(Math.max(0.0, Math.min(100.0, var1 * 100.0)));
      this.externalChatY.setValueClamped(Math.max(0.0, Math.min(100.0, var2 * 100.0)));
   }

   public void diViwfX() {
      this.setExternalChatPosition(0.0F, 0.0F);
   }

   @Override
   public void guiSliderChanged(SliderSetting var1) {
      if (var1 == this.output) {
         this.refreshExternalRenderer();
      }
   }

   public void refreshExternalRenderer() {
      ExternalRenderer.invalidateExternalFrame();
      if (Jade.getModuleManager() != null) {
         for (Module var2 : Jade.getModuleManager().getModules()) {
            var2.guiUpdate();
         }
      }
   }
}
