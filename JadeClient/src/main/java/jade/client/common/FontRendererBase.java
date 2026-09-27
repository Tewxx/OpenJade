// Jade recovery: original class: jade.deps.eLz.cVh5uWA
package jade.client.common;

public interface FontRendererBase extends TextRenderer {
   default int drawString(String var1, float var2, float var3, int var4) {
      return StringDrawer.plain(this, var1, var2, var3, var4);
   }
}
