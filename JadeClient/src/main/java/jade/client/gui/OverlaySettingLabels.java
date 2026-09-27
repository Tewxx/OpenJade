// Jade recovery: original class: jade.deps.eLz.b1fFFe5n
package jade.client.gui;

public final class OverlaySettingLabels {
   private static final String[] TEXT_COLOR_NAMES = new String[]{"White", "Red", "Green", "Blue", "Yellow", "Purple", "Rainbow"};

   private OverlaySettingLabels() {
   }

   public static String getOverlayEnabledLabel(boolean var0) {
      return "Jade Overlay: " + (var0 ? "Enabled" : "Disabled");
   }

   public static String getTextColorLabel(int var0) {
      return "Text Color: " + TEXT_COLOR_NAMES[var0];
   }

   public static String getShowMouseButtonsLabel(boolean var0) {
      return "Show Mouse Buttons: " + (var0 ? "On" : "Off");
   }

   public static String getOutlineLabel(boolean var0) {
      return "Outline: " + (var0 ? "On" : "Off");
   }

   public static int getNextTextColorIndex(int var0) {
      return var0 == TEXT_COLOR_NAMES.length - 1 ? 0 : var0 + 1;
   }
}
