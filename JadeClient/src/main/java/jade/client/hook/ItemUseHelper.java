// Jade recovery: original class: jade.deps.eLz.mcbVjaqaB
package jade.client.hook;

import jade.mixin.interfaces.IMixinItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public final class ItemUseHelper {
   private static final ItemUseTrackingState useTracker = new ItemUseTrackingState();

   private ItemUseHelper() {
   }

   public static void beginRenderTracking(EntityPlayer var0) {
      if (var0 == Minecraft.getMinecraft().thePlayer) {
         useTracker.beginTracking(var0);
      }
   }

   public static void endRenderTracking(EntityPlayer var0) {
      useTracker.endTracking(var0);
   }

   public static void Ziacy() {
      useTracker.Kyuc(true);
   }

   public static void Npqilu9() {
      useTracker.Kyuc(false);
   }

   public static boolean shouldForceItemUse(EntityPlayer var0, ItemStack var1) {
      return isBlockUseItem(var1) && useTracker.isTrackingEntity(var0) ? isFirstPersonItemInUse() : false;
   }

   public static boolean Ujb4(EntityLivingBase var0, ItemStack var1) {
      if (useTracker.isInsideItemRenderPass() && var0 != null && isBlockUseItem(var1)) {
         Minecraft var2 = Minecraft.getMinecraft();
         return var0 == var2.thePlayer && isFirstPersonItemInUse();
      } else {
         return false;
      }
   }

   private static boolean isBlockUseItem(ItemStack var0) {
      return var0 != null && var0.getItemUseAction() == EnumAction.BLOCK;
   }

   private static boolean isFirstPersonItemInUse() {
      Minecraft var0 = Minecraft.getMinecraft();
      return var0.getItemRenderer() != null && ((IMixinItemRenderer)var0.getItemRenderer()).isRenderItemInUse();
   }
}
