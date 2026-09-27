// Jade recovery: original class: jade.deps.eLz.nwokc3X3E$1
package jade.client.hook;

import java.io.IOException;
import java.io.InputStream;

public final class InMemoryBakeZip$1 extends InputStream {
   final InMemoryBakeZip this$0;

   private final String fVlwa;
   private byte[] entryBytes;
   private int readPosition;
   private boolean ULbTgn;

   public InMemoryBakeZip$1(InMemoryBakeZip var1, String var2, byte[] var3) {
      this.this$0 = var1;
      this.fVlwa = var2;
      this.entryBytes = var3;
   }

   public synchronized byte[] readAllBytes() throws IOException {
      if (!this.ULbTgn && this.readPosition == 0 && this.entryBytes != null) {
         this.ULbTgn = true;
         this.readPosition = this.entryBytes.length;
         synchronized (this.this$0) {
            if (!InMemoryBakeZip.getTransferredEntryNames(this.this$0).add(this.fVlwa)) {
               throw new IOException(
                  "cache entry ownership transferred twice"
               );
            }
         }

         byte[] var4 = this.entryBytes;
         this.entryBytes = null;
         return var4;
      } else {
         throw new IOException(
            "cache entry cannot transfer ownership twice"
         );
      }
   }

   @Override
   public synchronized int read() {
      return this.entryBytes != null && this.readPosition < this.entryBytes.length ? this.entryBytes[this.readPosition++] & 0xFF : -1;
   }

   @Override
   public synchronized int read(byte[] var1, int var2, int var3) {
      if (var1 == null) {
         throw new NullPointerException("target");
      } else if (var2 < 0 || var3 < 0 || var3 > var1.length - var2) {
         throw new IndexOutOfBoundsException();
      } else if (this.entryBytes != null && this.readPosition < this.entryBytes.length) {
         int var4 = Math.min(var3, this.entryBytes.length - this.readPosition);
         System.arraycopy(this.entryBytes, this.readPosition, var1, var2, var4);
         this.readPosition += var4;
         return var4;
      } else {
         return -1;
      }
   }

   @Override
   public synchronized int available() {
      return this.entryBytes == null ? 0 : this.entryBytes.length - this.readPosition;
   }
}
