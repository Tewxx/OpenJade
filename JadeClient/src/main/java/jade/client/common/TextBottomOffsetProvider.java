// Jade recovery: original class: jade.deps.eLz.iU8dwjuqGZ
package jade.client.common;

public interface TextBottomOffsetProvider extends FontHeightProvider {
   default int getTextBottomOffset() {
      return ComponentTextMetrics.height(this);
   }
}
