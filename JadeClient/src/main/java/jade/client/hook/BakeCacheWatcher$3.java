// Jade recovery: original class: jade.deps.eLz.LICA3Nq$3
package jade.client.hook;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public final class BakeCacheWatcher$3 implements AutoCloseable {
   private final FileChannel fileChannel;
   private final FileLock fileLock;

   public BakeCacheWatcher$3(FileChannel var1, FileLock var2) {
      this.fileChannel = var1;
      this.fileLock = var2;
   }

   @Override
   public void close() throws IOException {
      try {
         this.fileLock.release();
      } finally {
         this.fileChannel.close();
      }
   }
}
