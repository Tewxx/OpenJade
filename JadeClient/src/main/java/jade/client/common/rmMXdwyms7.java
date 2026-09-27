// Jade recovery: original class: jade.deps.eLz.rmMXdwyms7
package jade.client.common;

import java.util.ArrayDeque;
import java.util.Deque;

public final class rmMXdwyms7 {
   private final Deque<ChunkKey> chunkKeys = new ArrayDeque<>();

   public void enqueue(int var1, int var2) {
      this.chunkKeys.addLast(new ChunkKey(var1, var2));
   }

   public ChunkKey xqcV() {
      return this.chunkKeys.pollFirst();
   }

   public boolean hasPending() {
      return !this.chunkKeys.isEmpty();
   }

   public void clear() {
      this.chunkKeys.clear();
   }

   public int size() {
      return this.chunkKeys.size();
   }
}
