// Jade recovery: recovered class name: IAccessorEntityRenderer; mixin target: net.minecraft.client.renderer.EntityRenderer
package jade.mixin.impl.accessor;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityRenderer.class)
public interface IAccessorEntityRenderer {
   @Accessor("shaderResourceLocations")
   static ResourceLocation[] getShaderResourceLocations() {
      throw new AssertionError();
   }

   @Accessor("pointedEntity")
   void setPointedEntity(Entity var1);

   @Accessor("pointedEntity")
   Entity getPointedEntity();

   @Accessor("thirdPersonDistance")
   void setThirdPersonDistance(float var1);

   @Accessor("shaderIndex")
   void setShaderIndex(int var1);

   @Accessor("shaderIndex")
   int getShaderIndex();

   @Accessor("useShader")
   void setUseShader(boolean var1);

   @Accessor("useShader")
   boolean getUseShader();

   @Invoker("loadShader")
   void callLoadShader(ResourceLocation var1);

   @Invoker("setupCameraTransform")
   void callSetupCameraTransform(float var1, int var2);
}
