// Jade recovery: original class: jade.deps.eLz.QzwYiJgen
package jade.client.common;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public final class ByteBufferFieldWriter {
   private ByteBufferFieldWriter() {
   }

   public static void writeByte(Field var0, int var1, boolean var2) {
      if (var0 != null) {
         try {
            var0.setAccessible(true);
            ByteBuffer var3 = (ByteBuffer)var0.get(null);
            var0.setAccessible(false);
            var3.put(var1, (byte)(var2 ? 1 : 0));
         } catch (IllegalAccessException var4) {
         }
      }
   }
}
