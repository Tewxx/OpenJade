// Jade recovery: original class: jade.deps.eLz.zVBO2mMC
package jade.client.hook;

public final class ItemUseTrackingState {
   private Object trackedEntity;
   private int bOnz;
   private boolean insideItemRenderPass;

   public void beginTracking(Object var1) {
      if (this.bOnz++ == 0) {
         this.trackedEntity = var1;
      }
   }

   public void endTracking(Object var1) {
      if (var1 == this.trackedEntity && this.bOnz > 0) {
         this.bOnz--;
         if (this.bOnz == 0) {
            this.trackedEntity = null;
         }
      }
   }

   public boolean isTrackingEntity(Object var1) {
      return var1 != null && var1 == this.trackedEntity;
   }

   public void Kyuc(boolean var1) {
      this.insideItemRenderPass = var1;
   }

   public boolean isInsideItemRenderPass() {
      return this.insideItemRenderPass;
   }
}
