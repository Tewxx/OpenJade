// Jade recovery: original class: jade.deps.eLz.pnzs0cx
package jade.client.gui;

import jade.client.common.Animation;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class ClickGuiFadeAnimator {
   private Animation sharedFadeAnimation;
   private Animation delayedAnimation;
   private Animation JBg4;
   private Animation fadeAnimation;
   private ScheduledFuture<?> scheduledFuture;

   public void startFade(ScheduledExecutorService var1) {
      Animation var2 = new Animation(500.0F);
      this.sharedFadeAnimation = var2;
      this.JBg4 = var2;
      this.fadeAnimation = var2;
      var2.restart();
      this.scheduledFuture = var1.schedule(this::RkWh, 650L, TimeUnit.MILLISECONDS);
   }

   public int AGNCnZs() {
      return (int)(this.fadeAnimation.computeEasedValue(0.0F, 0.7F, 2) * 255.0F) << 24;
   }

   public void dnw7() {
      this.delayedAnimation = null;
      if (this.scheduledFuture != null) {
         this.scheduledFuture.cancel(true);
         this.scheduledFuture = null;
      }
   }

   private void RkWh() {
      this.delayedAnimation = new Animation(650.0F);
      this.delayedAnimation.restart();
   }
}
