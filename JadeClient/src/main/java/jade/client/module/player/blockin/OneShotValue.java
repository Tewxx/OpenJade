// Jade recovery: original class: jade.deps.eLz.b9CIetwIo
package jade.client.module.player.blockin;

import java.util.function.Consumer;

public final class OneShotValue<T> {
   private T storedValue;

   public void gzgY0(T var1) {
      this.storedValue = (T)var1;
   }

   public void clear() {
      this.storedValue = null;
   }

   public void consumeIfPresent(Consumer<T> var1) {
      T var2 = this.storedValue;
      if (var2 != null) {
         this.storedValue = null;
         var1.accept(var2);
      }
   }
}
