// Jade recovery: original class: jade.deps.eLz.JvKlvIgC
package jade.client.hook;

import jade.client.common.EventBus;
import jade.client.common.FakePlayerRenderer;
import jade.client.event.RenderPlayerPostEvent;
import jade.client.event.RenderPlayerPreEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;

public final class RenderPlayerHook {
   private RenderPlayerHook() {
   }

   public static void BiN15(EntityPlayer var0, double var1, double var3, double var5, float var7) {
      if (!FakePlayerRenderer.isRendering()) {
         ItemUseHelper.beginRenderTracking(var0);
         if (EventBus.hasListeners(RenderPlayerPreEvent.class)) {
            EventBus.post(new RenderPlayerPreEvent(var0, var1, var3, var5, var7));
         }
      }
   }

   public static void kkihzAr(RenderPlayer var0, EntityPlayer var1, double var2, double var4, double var6, float var8) {
      if (!FakePlayerRenderer.isRendering()) {
         ItemUseHelper.endRenderTracking(var1);
         if (EventBus.hasListeners(RenderPlayerPostEvent.class)) {
            ModelPlayer var9 = var0.getMainModel();
            ModelBiped var10 = var9 instanceof ModelBiped ? (ModelBiped)var9 : null;
            EventBus.post(new RenderPlayerPostEvent(var1, var2, var4, var6, var8, var10));
         }
      }
   }
}
