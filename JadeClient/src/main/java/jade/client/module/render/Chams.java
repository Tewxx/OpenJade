// Jade recovery: module: Chams (render); original class: jade.deps.eLz.LYkiobsXE
package jade.client.module.render;

import jade.client.common.Subscribe;
import jade.client.event.RenderPlayerPostEvent;
import jade.client.event.RenderPlayerPreEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.module.Category;
import jade.client.module.Module;
import jade.client.module.ModuleInfo;
import jade.client.module.client.Rendering;
import jade.client.module.other.AntiBot;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

@ModuleInfo
public class Chams extends Module {
   private boolean polygonOffsetActive;
   private boolean LAAWtE;
   private float savedPolygonOffsetFactor;
   private float IGauQ1;

   public Chams() {
      super("Chams", Category.render, 0);
   }

   private void applyPolygonOffset() {
      this.LAAWtE = GL11.glIsEnabled(32823);
      this.savedPolygonOffsetFactor = GL11.glGetFloat(32824);
      this.IGauQ1 = GL11.glGetFloat(10752);
      this.polygonOffsetActive = true;
      GL11.glEnable(32823);
      GL11.glPolygonOffset(1.0F, -4000000.0F);
   }

   private void restorePolygonOffset() {
      if (this.polygonOffsetActive) {
         GL11.glPolygonOffset(this.savedPolygonOffsetFactor, this.IGauQ1);
         if (this.LAAWtE) {
            GL11.glEnable(32823);
         } else {
            GL11.glDisable(32823);
         }

         this.polygonOffsetActive = false;
      }
   }

   @Subscribe
   public void onRenderPlayerPre(RenderPlayerPreEvent var1) {
      this.restorePolygonOffset();
      if (!Rendering.isExternalOutput()) {
         EntityPlayer var2 = var1.entityPlayer;
         if (var2 != mc.thePlayer) {
            if (!AntiBot.shouldHideEntity(var2)) {
               this.applyPolygonOffset();
            }
         }
      }
   }

   @Subscribe
   public void onRenderPlayerPost(RenderPlayerPostEvent var1) {
      this.restorePolygonOffset();
   }

   @Subscribe
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      this.restorePolygonOffset();
   }

   @Override
   public void onDisable() {
      this.restorePolygonOffset();
   }
}
