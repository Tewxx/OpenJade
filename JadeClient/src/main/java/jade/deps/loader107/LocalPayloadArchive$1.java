package jade.deps.loader107;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

final class LocalPayloadArchive$1 extends ByteArrayOutputStream {
   LocalPayloadArchive$1() {
   }

   void wipe() {
      Arrays.fill(this.buf, (byte)0);
      this.reset();
   }
}
