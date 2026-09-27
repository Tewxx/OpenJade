// Jade recovery: original class: jade.deps.eLz.ru9CiAK
package jade.client.common;

import jade.mixin.interfaces.IMixinItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;

public final class ItemRendererStateHelper {
   private ItemRendererStateHelper() {
   }

   public static boolean setRenderItemInUse(boolean var0) {
      ItemRenderer var1 = Minecraft.getMinecraft().getItemRenderer();
      if (var1 == null) {
         return var0;
      } else {
         try {
            ((IMixinItemRenderer)var1).setRenderItemInUse(var0);
         } catch (RuntimeException var3) {
         } catch (LinkageError var4) {
         }

         return var0;
      }
   }
}
