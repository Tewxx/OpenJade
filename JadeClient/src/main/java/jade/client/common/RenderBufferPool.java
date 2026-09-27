// Jade recovery: original class: jade.deps.eLz.db0A467N
package jade.client.common;

import java.util.ArrayDeque;

public final class RenderBufferPool {
   private final ArrayDeque<ExternalRenderBuffer> externalRenderBuffers = new ArrayDeque<>();
   private ExternalRenderBuffer externalRenderBuffer;
   private boolean thse;

   public RenderBufferPool() {
      for (int var1 = 0; var1 < 3; var1++) {
         this.externalRenderBuffers.add(new ExternalRenderBuffer());
      }
   }

   public synchronized ExternalRenderBuffer HFJBts() {
      return this.thse ? null : this.externalRenderBuffers.poll();
   }

   public synchronized void submitBuffer(ExternalRenderBuffer var1) {
      if (this.externalRenderBuffer != null) {
         this.externalRenderBuffers.add(this.externalRenderBuffer);
      }

      this.externalRenderBuffer = this.thse ? null : var1;
      if (this.thse) {
         this.externalRenderBuffers.add(var1);
      }

      this.notifyAll();
   }

   public synchronized ExternalRenderBuffer ZDRVuhT() throws InterruptedException {
      while (!this.thse && this.externalRenderBuffer == null) {
         this.wait();
      }

      ExternalRenderBuffer var1 = this.externalRenderBuffer;
      this.externalRenderBuffer = null;
      return var1;
   }

   public synchronized void recycleBuffer(ExternalRenderBuffer var1) {
      this.externalRenderBuffers.add(var1);
   }

   public synchronized void shutdown() {
      this.thse = true;
      if (this.externalRenderBuffer != null) {
         this.externalRenderBuffers.add(this.externalRenderBuffer);
      }

      this.externalRenderBuffer = null;
      this.notifyAll();
   }
}
