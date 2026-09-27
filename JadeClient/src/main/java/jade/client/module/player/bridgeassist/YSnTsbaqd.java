// Jade recovery: original class: jade.deps.eLz.YSnTsbaqd
package jade.client.module.player.bridgeassist;

public final class YSnTsbaqd {
   private int placementCount;
   private int nMe;
   private boolean sneakPulseActive;

   public void recordPlacement(int var1) {
      this.placementCount++;
      if (this.placementCount >= var1) {
         this.placementCount = 0;
         this.nMe = 1;
      }
   }

   public boolean isSneakPulseActive() {
      if (this.nMe > 0) {
         this.nMe--;
         this.sneakPulseActive = true;
      }

      return this.sneakPulseActive;
   }

   public void cancelSneakPulse() {
      this.sneakPulseActive = false;
   }

   public void DQtgL() {
      this.placementCount = 0;
      this.nMe = 0;
      this.sneakPulseActive = false;
   }
}
