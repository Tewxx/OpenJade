// Jade recovery: original class: jade.deps.eLz.rCrGG8F0
package jade.client.module.combat.sprintreset;

public final class SprintResetState {
   private boolean ipT;
   private boolean awaitingRestart;
   private int THul;

   public void beginReset() {
      this.ipT = true;
      this.THul = 1;
      this.awaitingRestart = true;
   }

   public boolean AAhHl() {
      return this.ipT;
   }

   public void clearResetInProgress() {
      this.ipT = false;
   }

   public boolean isRestartSuppressed() {
      return this.THul > 0;
   }

   public boolean DBQv() {
      return this.awaitingRestart;
   }

   public void clearAwaitingRestart() {
      this.awaitingRestart = false;
   }

   public void FzYu() {
      if (this.THul > 0) {
         this.THul--;
      }
   }

   public void resetState() {
      this.ipT = false;
      this.awaitingRestart = false;
      this.THul = 0;
   }
}
