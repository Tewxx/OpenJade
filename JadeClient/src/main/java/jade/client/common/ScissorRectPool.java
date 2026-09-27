// Jade recovery: original class: jade.deps.eLz.fBwKa1Z
package jade.client.common;

import java.util.ArrayList;
import java.util.List;

public final class ScissorRectPool {
   private final List<int[]> pooledRects = new ArrayList<>();
   private int ufPul;

   public int[] NyGw() {
      if (this.ufPul == this.pooledRects.size()) {
         this.pooledRects.add(new int[5]);
      }

      return this.pooledRects.get(this.ufPul++);
   }

   public int[] Fu52() {
      if (this.ufPul == 0) {
         throw new IllegalStateException("Scissor stack underflow");
      } else {
         return this.pooledRects.get(--this.ufPul);
      }
   }
}
