// Jade recovery: module: Gui (client); original class: jade.deps.eLz.PdLR2Z9TI
package jade.client.module.client;

import jade.client.Jade;
import jade.client.common.ClientUtils;
import jade.client.common.FontManager;
import jade.client.common.IFont;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.setting.BooleanSetting;
import jade.client.setting.ColorSetting;
import jade.client.setting.FontSetting;
import jade.client.setting.KeySetting;
import jade.client.setting.SliderSetting;

@ModuleInfo
public class Gui extends Module {
   public static SliderSetting guiScale;
   public static SliderSetting guiStyle;
   public static SliderSetting dropdownColor;
   public static ColorSetting dropdownAccent;
   public static SliderSetting rounding;
   public static ColorSetting accent;
   public static FontSetting clickGUIFont;
   public static KeySetting clickGUIKey;
   public static BooleanSetting darkBackground;
   public static BooleanSetting gradientTopbar;
   public static BooleanSetting capitalizeText;

   public Gui() {
      super("Gui", Category.client, 25);
      this.registerSetting(
         guiStyle = new SliderSetting(
            "GUI style", 0, new String[]{"Box", "Dropdown"}
         )
      );
      this.registerSetting(dropdownColor = new SliderSetting("Dropdown color", 0, new String[]{"Theme", "Custom"}));
      this.registerSetting(
         dropdownAccent = new ColorSetting(
            "Dropdown accent",
            97,
            127,
            148
         )
      );
      this.registerSetting(
         clickGUIKey = new KeySetting("ClickGUI key", 25) {
            @Override
            public int getKeyCode() {
               return Gui.this.getKeycode();
            }

            @Override
            public int[] getKeyCodes() {
               return new int[]{Gui.this.getKeycode()};
            }

            @Override
            public String ukYzs() {
               return KeySetting.SBJv(Gui.this.getKeycode());
            }

            @Override
            public void setKeyCode(int var1) {
               Gui.this.setKeycode(var1);
            }

            @Override
            public void CvUpqo(int[] var1) {
               Gui.this.setKeycode(var1 != null && var1.length != 0 ? var1[var1.length - 1] : 0);
            }
         }
      );
      this.registerSetting(
         guiScale = new SliderSetting("Gui scale", "x", 1.0, 0.5, 2.0, 0.01)
      );
      darkBackground = new BooleanSetting("Dark background", true);
      rounding = new SliderSetting(
         "Rounding", "%", 70.0, 0.0, 100.0, 1.0
      );
      accent = new ColorSetting(
         "Accent",
         26,
         168,
         121
      );
      this.registerSetting(clickGUIFont = new FontSetting("ClickGUI font", "Modern"));
      gradientTopbar = new BooleanSetting(
         "Gradient topbar", false
      );
      capitalizeText = new BooleanSetting(
         "Capitalize text", false
      );
   }

   @Override
   public void onEnable() {
      if (ClientUtils.isInWorld() && mc.currentScreen != Jade.clickGui) {
         mc.displayGuiScreen(Jade.clickGui);
         Jade.clickGui.initMain();
      }

      this.disable();
   }

   @Override
   public void setKeycode(int var1) {
      super.setKeycode(var1);
   }

   public static String ISjhxoi() {
      return clickGUIFont == null ? FontManager.getDefaultHudFontName() : clickGUIFont.getResolvedFontName();
   }

   public static void selectClickGuiFont(String var0) {
      if (clickGUIFont != null) {
         String[] var1 = clickGUIFont.getOptions();

         for (int var2 = 0; var2 < var1.length; var2++) {
            if (var1[var2].equals(var0)) {
               clickGUIFont.setValueClamped(var2);
               break;
            }
         }
      }
   }

   public static IFont getHeaderFont() {
      return JzraV3() ? FontManager.getPixelHeightRenderer(ISjhxoi(), 10.0F) : FontManager.getClickGuiHeaderRenderer(ISjhxoi());
   }

   public static IFont getSettingFont() {
      return JzraV3() ? FontManager.getPixelHeightRenderer(ISjhxoi(), 10.0F) : FontManager.getClickGuiSettingRenderer(ISjhxoi());
   }

   public static boolean JzraV3() {
      return guiStyle != null && guiStyle.getInput() >= 1.0;
   }

   public static boolean YPPBEUf() {
      return dropdownColor != null && dropdownColor.getInput() >= 1.0;
   }

   public static int getAccentColor() {
      ColorSetting var0 = YPPBEUf() ? dropdownAccent : accent;
      return var0 == null ? -10387564 : var0.getArgb() | 0xFF000000;
   }

   public static float getGuiScale() {
      return guiScale == null ? 1.0F : (float)Math.max(0.5, Math.min(2.0, guiScale.getInput()));
   }

   public static float getRoundingPercent() {
      return rounding == null ? 70.0F : (float)Math.max(0.0, Math.min(100.0, rounding.getInput()));
   }

   public static int getAccentHue() {
      return accent == null ? 160 : (int)accent.getHue();
   }

   public static float zFsde8() {
      return accent == null ? 0.85F : accent.getSaturation();
   }

   public static float getAccentBrightness() {
      return accent == null ? 0.66F : accent.pBf3();
   }

   public static boolean GWPe() {
      return gradientTopbar != null && gradientTopbar.isToggled();
   }

   public static boolean isCapitalizeTextEnabled() {
      return capitalizeText != null && capitalizeText.isToggled();
   }
}
