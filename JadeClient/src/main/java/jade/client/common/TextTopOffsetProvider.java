// Jade recovery: original class: jade.deps.eLz.KrlqpJ
package jade.client.common;

public interface TextTopOffsetProvider {
   default int getTextTopOffset() {
      return ComponentTextMetrics.top();
   }
}
