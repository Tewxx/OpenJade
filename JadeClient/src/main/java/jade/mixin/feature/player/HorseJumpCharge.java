// Jade recovery: recovered class name: HorseJumpCharge; original class: jade.mixin.feature.player.Mb7b016a4374cba47bc98088ae61baf7a
package jade.mixin.feature.player;

public final class HorseJumpCharge {
   private HorseJumpCharge() {
   }

   public static HorseJumpCharge$1 advance(boolean ridingHorse, boolean jumpBefore, boolean jumpNow, int counter, float power) {
      boolean sendJump = false;
      if (!ridingHorse) {
         return new HorseJumpCharge$1(counter, 0.0F, false);
      } else {
         if (counter < 0) {
            if (++counter == 0) {
               power = 0.0F;
            }
         }

         if (jumpBefore && !jumpNow) {
            counter = -10;
            sendJump = true;
         } else if (!jumpBefore && jumpNow) {
            counter = 0;
            power = 0.0F;
         } else if (jumpBefore) {
            counter++;
            power = counter < 10 ? counter * 0.1F : 0.8F + 0.2F / (counter - 9);
         }

         return new HorseJumpCharge$1(counter, power, sendJump);
      }
   }
}
