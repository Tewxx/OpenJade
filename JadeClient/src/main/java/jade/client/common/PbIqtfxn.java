// Jade recovery: original class: jade.deps.eLz.PbIqtfxn
package jade.client.common;

public interface PbIqtfxn extends TextRenderer {
   default int drawStringWithShadow(String var1, float var2, float var3, int var4) {
      return StringDrawer.shadow(this, var1, var2, var3, var4);
   }
}
