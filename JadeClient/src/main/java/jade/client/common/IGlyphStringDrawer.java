// Jade recovery: original class: jade.deps.eLz.i3bMr9ISmS
package jade.client.common;

public interface IGlyphStringDrawer {
   int drawGlyphString(String var1, float var2, float var3, IFont$0 var4, boolean var5);

   default int drawGlyphString(String var1, float var2, float var3, IFont$0 var4) {
      return this.drawGlyphString(var1, var2, var3, var4, false);
   }
}
