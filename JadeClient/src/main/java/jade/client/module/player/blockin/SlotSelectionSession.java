// Jade recovery: original class: jade.deps.eLz.tdq6Ql75x
package jade.client.module.player.blockin;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public final class SlotSelectionSession {
   private boolean active;
   private boolean slotChanged;
   private int initialSlot = -1;
   private int selectedSlot = -1;

   public boolean isActive() {
      return this.active;
   }

   public int getSelectedSlot() {
      return this.selectedSlot;
   }

   public void setSelectedSlot(int var1) {
      this.selectedSlot = var1;
   }

   public void OvuplMq(IntSupplier var1) {
      if (!this.active) {
         this.active = true;
         this.slotChanged = false;
         this.initialSlot = var1.getAsInt();
      }
   }

   public void dkgyua(IntSupplier var1, IntConsumer var2) {
      int var3 = var1.getAsInt();
      if (this.selectedSlot != -1 && this.selectedSlot != var3) {
         var2.accept(this.selectedSlot);
         this.slotChanged = true;
      }
   }

   public boolean pKf97(IntSupplier var1, IntConsumer var2) {
      if (!this.active) {
         return false;
      } else {
         if (this.slotChanged && this.initialSlot != -1 && this.initialSlot != var1.getAsInt()) {
            var2.accept(this.initialSlot);
         }

         this.active = false;
         this.slotChanged = false;
         this.initialSlot = -1;
         this.selectedSlot = -1;
         return true;
      }
   }
}
