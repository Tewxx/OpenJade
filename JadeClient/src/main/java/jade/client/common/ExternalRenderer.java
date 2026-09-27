// Jade recovery: original class: jade.deps.eLz.H2bEkIU
package jade.client.common;

import jade.client.Jade;
import jade.client.event.LoadWorldEvent;
import jade.client.event.RenderTickEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.event.TickEndEvent;
import jade.client.module.Module;
import jade.client.module.client.Rendering;
import jade.client.module.render.ESP;
import jade.client.module.render.Nametags;
import jade.inject.InjectionAgent;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public final class ExternalRenderer {
   private static final long PROBLEM_REPEAT_INTERVAL_NANOS = 5000000000L;
   private static ExternalRenderer externalRenderer;
   private final ScreenProjector screenProjector = new ScreenProjector();
   private final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
   private final float[] modelViewMatrix = new float[16];
   private final float[] projectionMatrix = new float[16];
   private ExternalOverlaySession externalOverlaySession;
   private long rh6;
   private Object cachedWorld;
   private String lastReportedProblem = "";
   private long QTIqB;
   private boolean Puye;
   private ExternalRenderBuffer externalRenderBuffer;
   private boolean hasProjectionMatrix;
   private boolean projectionCaptured;
   private String EMe4 = "External renderer is unavailable.";
   private String pendingProblem = "";
   private long problemSinceNanos;
   private boolean kvY;

   public ExternalRenderer() {
      externalRenderer = this;
   }

   private boolean shouldRenderExternally() {
      if (!Rendering.isExternalOutput()) {
         return false;
      } else if (ExternalChatOverlay.hasVisibleMessages()) {
         return true;
      } else {
         for (Module var2 : Jade.getModuleManager().getModules()) {
            if (var2.isEnabled() && var2 instanceof ExternalRenderableModule) {
               return true;
            }
         }

         return false;
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onRenderTick(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.START) {
         boolean var2 = this.shouldRenderExternally();
         if (this.externalRenderBuffer != null) {
            if (var2) {
               this.submitFrame();
            } else {
               this.returnPendingBuffer();
            }
         }

         this.hasProjectionMatrix = false;
         this.projectionCaptured = false;
         if (!var2) {
            this.clearExternalFrame();
         } else {
            ExternalItemTextures.refreshIfAtlasChanged();
            this.acquireFrameBuffer();
         }
      }
   }

   @Subscribe(priority = EventPriority.LOWEST)
   public void nnZbk(RenderTickEvent var1) {
      if (var1.eventPhase == EventPhase.END && this.externalRenderBuffer != null) {
         this.submitFrame();
      }
   }

   private void submitFrame() {
      if (this.externalRenderBuffer != null && this.externalOverlaySession != null) {
         ExternalChatOverlay.renderChatOverlay(this.externalRenderBuffer);
         ExternalItemTextures.uploadPendingItemTextures(this.externalRenderBuffer, this.externalOverlaySession);
         ExternalSkinTextures.uploadPendingTextures(this.externalRenderBuffer, this.externalOverlaySession);
         ExternalGlyphCache.ceFk(this.externalRenderBuffer, this.externalOverlaySession);
         this.externalRenderBuffer.seHtuu4();
         this.externalOverlaySession.renderBufferPool.submitBuffer(this.externalRenderBuffer);
         this.externalRenderBuffer = null;
         this.Puye = true;
      }
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onLoadWorld(LoadWorldEvent var1) {
      this.clearExternalFrame();
      this.cachedWorld = var1.world;
      this.hasProjectionMatrix = false;
      this.QTIqB = Rendering.isExternalOutput() ? System.nanoTime() : 0L;
      this.pendingProblem = "";
      this.problemSinceNanos = 0L;
   }

   private ExternalRenderBuffer acquireFrameBuffer() {
      Minecraft var1 = Minecraft.getMinecraft();
      if (!Display.isActive()) {
         this.returnPendingBuffer();
         return null;
      } else if (Display.isFullscreen() || var1.displayWidth <= 0 || var1.displayHeight <= 0) {
         this.clearExternalFrame();
         return null;
      } else if (this.externalRenderBuffer != null) {
         return this.externalRenderBuffer;
      } else {
         if (this.QTIqB == 0L) {
            this.QTIqB = System.nanoTime();
         }

         String var2 = null;

         try {
            var2 = InjectionAgent.externalVisualNonce();
         } catch (NoSuchMethodError var4) {
            this.EMe4 = "External renderer needs an updated loader.";
         }

         if (var2 == null) {
            this.EMe4 = "External renderer session is unavailable.";
         }

         if (this.externalOverlaySession == null && var2 != null) {
            this.externalOverlaySession = new ExternalOverlaySession(var2);
         }

         if (this.externalOverlaySession == null) {
            return null;
         } else {
            if (this.cachedWorld != var1.theWorld) {
               this.clearExternalFrame();
               this.cachedWorld = var1.theWorld;
            }

            this.externalRenderBuffer = this.externalOverlaySession.renderBufferPool.HFJBts();
            if (this.externalRenderBuffer != null) {
               this.externalRenderBuffer.TCsFvxT(++this.rh6, this.externalOverlaySession.EPk4, var1.displayWidth, var1.displayHeight);
            }

            return this.externalRenderBuffer;
         }
      }
   }

   public static ExternalRenderBuffer getActiveRenderBuffer() {
      return externalRenderer == null ? null : externalRenderer.acquireFrameBuffer();
   }

   public static ScreenProjector getActiveScreenProjector() {
      return externalRenderer != null && externalRenderer.hasProjectionMatrix ? externalRenderer.screenProjector : null;
   }

   @Subscribe(priority = EventPriority.HIGHEST)
   public void onRenderWorldLast(RenderWorldLastEvent var1) {
      Minecraft var2 = Minecraft.getMinecraft();
      ESP var3 = Jade.getModuleManager().getModule(ESP.class);
      Nametags var4 = Jade.getModuleManager().getModule(Nametags.class);
      boolean var5 = var3 != null && var3.isEnabled();
      boolean var6 = var4 != null && var4.isEnabled();
      if (!this.projectionCaptured && this.shouldRenderExternally() && var2.theWorld != null && var2.thePlayer != null) {
         ExternalRenderBuffer var7 = this.acquireFrameBuffer();
         if (var7 != null) {
            this.projectionCaptured = true;
            ((Buffer)this.floatBuffer).clear();
            GL11.glGetFloat(2982, this.floatBuffer);
            this.floatBuffer.get(this.modelViewMatrix);
            ((Buffer)this.floatBuffer).clear();
            GL11.glGetFloat(2983, this.floatBuffer);
            this.floatBuffer.get(this.projectionMatrix);
            this.screenProjector.DyjK(this.modelViewMatrix, this.projectionMatrix, var2.displayWidth, var2.displayHeight);
            this.hasProjectionMatrix = true;
            if (var5) {
               var3.KbEan(var7, this.screenProjector, var1.YDn0);
            }

            if (var6) {
               var4.renderToExternalBuffer(var7, this.screenProjector, var1.YDn0);
            }
         }
      }
   }

   @Subscribe
   public void onTickEnd(TickEndEvent var1) {
      if (var1.eventPhase == EventPhase.END) {
         Minecraft var2 = Minecraft.getMinecraft();
         ESP var3 = Jade.getModuleManager().getModule(ESP.class);
         boolean var4 = this.shouldRenderExternally();
         if (!var4 || this.cachedWorld != var2.theWorld) {
            this.clearExternalFrame();
         }

         if (!var4) {
            if (!Rendering.isExternalOutput()) {
               ExternalChatOverlay.clearMessages();
            }

            this.QTIqB = 0L;
            this.lastReportedProblem = this.pendingProblem = "";
            this.problemSinceNanos = 0L;
            this.kvY = false;
         } else {
            String var5 = "";
            long var6 = System.nanoTime();
            if (!Display.isActive()) {
               this.kvY = true;
               this.pendingProblem = "";
               this.problemSinceNanos = 0L;
            } else if (this.kvY) {
               this.kvY = false;
               this.QTIqB = var6;
               this.pendingProblem = "";
               this.problemSinceNanos = 0L;
            } else {
               if (Display.isFullscreen()) {
                  var5 = "External visuals need windowed mode.";
               } else if (this.externalOverlaySession != null && this.externalOverlaySession.ready()) {
                  this.pendingProblem = "";
                  this.problemSinceNanos = 0L;
               } else if (this.QTIqB != 0L && var6 - this.QTIqB > 2000000000L) {
                  String var8 = this.externalOverlaySession == null ? this.EMe4 : this.externalOverlaySession.problem();
                  if (!var8.isEmpty()) {
                     if (!var8.equals(this.pendingProblem)) {
                        this.pendingProblem = var8;
                        this.problemSinceNanos = var6;
                     } else if (var6 - this.problemSinceNanos >= 5000000000L) {
                        var5 = var8;
                     }
                  }
               }

               if (!var5.equals(this.lastReportedProblem) && var2.thePlayer != null) {
                  if (!var5.isEmpty()) {
                     ClientUtils.sendColoredMessage("&f[Jade] " + var5);
                  }

                  this.lastReportedProblem = var5;
               }
            }
         }
      }
   }

   private void clearExternalFrame() {
      this.returnPendingBuffer();
      this.hasProjectionMatrix = false;
      if (this.externalOverlaySession != null && this.Puye) {
         ExternalRenderBuffer var1 = this.externalOverlaySession.renderBufferPool.HFJBts();
         if (var1 != null) {
            Minecraft var2 = Minecraft.getMinecraft();
            var1.TCsFvxT(++this.rh6, this.externalOverlaySession.EPk4, Math.max(1, var2.displayWidth), Math.max(1, var2.displayHeight));
            var1.seHtuu4();
            this.externalOverlaySession.renderBufferPool.submitBuffer(var1);
            this.Puye = false;
         }
      }
   }

   private void returnPendingBuffer() {
      if (this.externalRenderBuffer != null && this.externalOverlaySession != null) {
         this.externalOverlaySession.renderBufferPool.recycleBuffer(this.externalRenderBuffer);
      }

      this.externalRenderBuffer = null;
   }

   public static void invalidateExternalFrame() {
      if (externalRenderer != null) {
         externalRenderer.clearExternalFrame();
      }
   }

   public static void shutdown() {
      ExternalRenderer var0 = externalRenderer;
      externalRenderer = null;
      if (var0 != null && var0.externalOverlaySession != null) {
         var0.returnPendingBuffer();
         var0.externalOverlaySession.close();
      }

      ExternalItemTextures.SuvwT();
      ExternalSkinTextures.clearCaches();
      ExternalChatOverlay.clearMessages();
   }
}
