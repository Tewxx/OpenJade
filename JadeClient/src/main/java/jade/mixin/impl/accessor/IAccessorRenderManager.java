// Jade recovery: recovered class name: IAccessorRenderManager; mixin target: net.minecraft.client.renderer.entity.RenderManager
package jade.mixin.impl.accessor;

import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderManager.class)
public interface IAccessorRenderManager {
   @Accessor("renderShadow")
   void setRenderShadow(boolean var1);

   @Accessor("renderShadow")
   boolean getRenderShadow();

   @Accessor("renderPosZ")
   double getRenderPosZ();

   @Accessor("renderPosY")
   double getRenderPosY();

   @Accessor("renderPosX")
   double getRenderPosX();
}
