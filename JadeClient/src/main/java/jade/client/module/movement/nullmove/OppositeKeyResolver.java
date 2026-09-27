// Jade recovery: original class: jade.deps.eLz.s65p7YH
package jade.client.module.movement.nullmove;

public final class OppositeKeyResolver {
   private int previousKeyMask;
   private boolean negativePressedLast;

   public int resolveDirection(boolean var1, boolean var2) {
      int var3 = (var1 ? 1 : 0) | (var2 ? 2 : 0);
      int var4 = var3 & ~this.previousKeyMask;
      if ((var4 & 2) != 0) {
         this.negativePressedLast = true;
      } else if ((var4 & 1) != 0) {
         this.negativePressedLast = false;
      }

      this.previousKeyMask = var3;
      return var3 == 3 ? (this.negativePressedLast ? -1 : 1) : 0;
   }

   public void reset() {
      this.previousKeyMask = 0;
      this.negativePressedLast = false;
   }
}
