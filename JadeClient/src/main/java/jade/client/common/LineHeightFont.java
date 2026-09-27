// Jade recovery: original class: jade.deps.eLz.IEu4NJW
package jade.client.common;

public interface LineHeightFont extends FontHeightProvider {
   default int getLineHeight() {
      return ComponentTextMetrics.height(this);
   }
}
