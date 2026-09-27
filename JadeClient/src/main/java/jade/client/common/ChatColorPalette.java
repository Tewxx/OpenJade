// Jade recovery: original class: jade.deps.eLz.JHmTuYl
package jade.client.common;

public enum ChatColorPalette {
   DARK_RED(189, 0, 1),
   RED(253, 63, 63),
   GOLD(215, 162, 50),
   YELLOW(254, 254, 62),
   DARK_GREEN(0, 191, 4),
   GREEN(64, 253, 62),
   AQUA(65, 255, 254),
   DARK_AQUA(0, 190, 189),
   DARK_BLUE(1, 1, 187),
   BLUE(61, 64, 255),
   LIGHT_PURPLE(254, 63, 255),
   DARK_PURPLE(190, 0, 190),
   GRAY(190, 190, 190),
   DARK_GRAY(63, 63, 63),
   BLACK(17, 17, 17);

   private final int argb;

   private ChatColorPalette(int var3, int var4, int var5) {
      this.argb = 0xFF000000 | var3 << 16 | var4 << 8 | var5;
   }

   public int argb() {
      return this.argb;
   }
}
