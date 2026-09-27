// Jade recovery: original class: jade.deps.eLz.wqeb45UxH
package jade.client.common;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ExternalOverlaySession implements AutoCloseable {
   private static final int MAX_TRACKED_TEXTURES = 4096;
   public final RenderBufferPool renderBufferPool = new RenderBufferPool();
   private final String pipePath;
   private final Thread lay;
   private volatile RandomAccessFile randomAccessFile;
   private volatile boolean closeRequested;
   public volatile int EPk4;
   public volatile int gwr;
   private volatile long lastResponseNanos;
   private volatile String problemMessage = "";
   private volatile long WeP64;
   private final Set<Integer> RcJ5 = ConcurrentHashMap.newKeySet();
   public volatile int Rct = 1;

   public boolean hasTexture(int var1) {
      return this.RcJ5.contains(var1);
   }

   public ExternalOverlaySession(String var1) {
      if (var1 != null && var1.matches("[0-9a-fA-F]{32,128}")) {
         this.pipePath = "\\\\.\\pipe\\JadeVisuals-" + var1;
         this.lay = new Thread(this::runFrameLoop, "jade-external-frames");
         this.lay.setDaemon(true);
         this.lay.start();
      } else {
         throw new IllegalArgumentException("Missing external overlay session");
      }
   }

   public boolean ready() {
      return (this.gwr & 1) != 0 && System.nanoTime() - this.lastResponseNanos < 2000000000L;
   }

   public String problem() {
      if (!this.lay.isAlive()) {
         return "External renderer stopped.";
      } else if (this.WeP64 == 0L) {
         return "";
      } else if (!this.problemMessage.isEmpty()) {
         return this.problemMessage;
      } else {
         return System.nanoTime() - this.lastResponseNanos >= 2000000000L ? "External renderer is not responding." : ExternalRendererErrors.describeStatus(this.gwr);
      }
   }

   private void runFrameLoop() {
      byte[] var1 = new byte[24];
      ByteBuffer var2 = ByteBuffer.wrap(var1).order(ByteOrder.LITTLE_ENDIAN);

      try {
         while (!this.closeRequested) {
            ExternalRenderBuffer var3 = this.renderBufferPool.ZDRVuhT();
            if (var3 != null) {
               this.WeP64 = System.nanoTime();
               boolean var4 = false;

               try {
                  if (this.randomAccessFile == null) {
                     this.randomAccessFile = new RandomAccessFile(this.pipePath, "rw");
                  }

                  var4 = true;
                  if (!this.closeRequested) {
                     this.randomAccessFile.write(var3.AkJmu, 0, var3.jfwSfx);
                     this.randomAccessFile.readFully(var1);
                     ((Buffer)var2).rewind();
                     int var5 = var2.getInt();
                     int var6 = var2.getInt();
                     if (var5 == 1480868170 && var6 == 4) {
                        int var7 = var2.getInt();
                        int var8 = this.gwr;
                        this.gwr = var7;
                        this.problemMessage = "";
                        this.EPk4 = var2.getInt();
                        this.lastResponseNanos = System.nanoTime();
                        if (shouldResetTextures(var8, var7, this.RcJ5.size())) {
                           this.RcJ5.clear();
                        }

                        if (var3.IVr5 == this.Rct) {
                           for (int var9 = 0; var9 < var3.zNc3; var9++) {
                              this.RcJ5.add(var3.uploadedTextureIds[var9]);
                           }
                        }
                        continue;
                     }

                     this.problemMessage = "External renderer version mismatch.";
                     throw new IOException("Incompatible external overlay");
                  }
               } catch (IOException var20) {
                  if (this.problemMessage.isEmpty() || !this.problemMessage.contains("version mismatch")) {
                     this.problemMessage = var4 ? "External renderer disconnected." : "External renderer is unavailable.";
                  }

                  this.gwr = 0;
                  this.resetConnection();
                  if (!this.closeRequested) {
                     Thread.sleep(500L);
                  }
                  continue;
               } finally {
                  this.renderBufferPool.recycleBuffer(var3);
               }
            }
            break;
         }
      } catch (InterruptedException var22) {
         Thread.currentThread().interrupt();
      } finally {
         this.resetConnection();
      }
   }

   public static boolean shouldResetTextures(int var0, int var1, int var2) {
      boolean var3 = (var1 & 2) != 0;
      boolean var4 = (var0 & 2) != 0;
      return var3 && !var4 || var2 > 4096;
   }

   private void resetConnection() {
      this.Rct++;
      this.RcJ5.clear();
      RandomAccessFile var1 = this.randomAccessFile;
      this.randomAccessFile = null;
      if (var1 != null) {
         try {
            var1.close();
         } catch (IOException var3) {
         }
      }
   }

   @Override
   public void close() {
      this.closeRequested = true;
      this.gwr = 0;
      this.renderBufferPool.shutdown();
      this.lay.interrupt();
      this.resetConnection();
   }
}
