// Jade recovery: original class: jade.deps.eLz.jt60g7H48z
package jade.client.common;

import com.mojang.authlib.GameProfile;
import jade.mixin.impl.accessor.IAccessorRenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public final class FakePlayerRenderer {
   public static final float DEFAULT_ALPHA = 0.4F;
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static final int FAKE_ENTITY_ID = -742001;
   private static boolean rendering;
   private static float currentAlpha = 0.4F;
   private static EntityOtherPlayerMP fakePlayer;
   private static World world;
   private static GameProfile gameProfile;

   private FakePlayerRenderer() {
   }

   public static boolean isRendering() {
      return rendering;
   }

   public static float xu60() {
      return currentAlpha;
   }

   public static void renderAtPosition(EntityPlayer var0, Vec3 var1, float var2) {
      renderFakePlayer(var0, FakePlayerRenderer$2.osdbmU(var0, var1, var2), var2, 0.4F);
   }

   public static void renderFakePlayerWithDefaultAlpha(EntityPlayer var0, FakePlayerRenderer$2 var1, float var2) {
      renderFakePlayer(var0, var1, var2, 0.4F);
   }

   public static void renderFakePlayer(EntityPlayer var0, FakePlayerRenderer$2 var1, float var2, float var3) {
      if (!rendering && var0 != null && var1 != null && mc.theWorld != null && mc.getRenderManager() != null) {
         EntityOtherPlayerMP var4 = getOrCreateFakePlayer(var0);
         if (var4 != null) {
            applyStateToFake(var0, var4, var1, var2);
            currentAlpha = clampUnit(var3);
            rendering = true;
            int var5 = 5888;
            boolean var6 = false;
            boolean var7 = false;
            boolean var8 = true;
            boolean var9 = false;

            try {
               var5 = GL11.glGetInteger(2976);
               GL11.glMatrixMode(5888);
               GlStateManager.pushMatrix();
               var6 = true;
               GL11.glPushAttrib(1048575);
               var7 = true;
               if (mc.getRenderManager() instanceof IAccessorRenderManager) {
                  IAccessorRenderManager var10 = (IAccessorRenderManager)mc.getRenderManager();
                  var8 = var10.getRenderShadow();
                  var10.setRenderShadow(false);
                  var9 = true;
               }

               GlStateManager.enableBlend();
               GlStateManager.enableAlpha();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               GlStateManager.depthMask(false);
               GlStateManager.color(1.0F, 1.0F, 1.0F, currentAlpha);
               double var70 = FakePlayerRenderer$2.getPosX(var1) - mc.getRenderManager().viewerPosX;
               double var12 = FakePlayerRenderer$2.getPosY(var1) - mc.getRenderManager().viewerPosY;
               double var14 = FakePlayerRenderer$2.getPosZ(var1) - mc.getRenderManager().viewerPosZ;
               mc.getRenderManager().renderEntityWithPosYaw(var4, var70, var12, var14, FakePlayerRenderer$2.getRotationYaw(var1), var2);
            } finally {
               try {
                  if (var9) {
                     ((IAccessorRenderManager)mc.getRenderManager()).setRenderShadow(var8);
                  }
               } finally {
                  try {
                     if (var7) {
                        GL11.glPopAttrib();
                     }

                     resetRenderState();
                     if (var6) {
                        GL11.glMatrixMode(5888);
                        GlStateManager.popMatrix();
                     }

                     GL11.glMatrixMode(var5);
                  } finally {
                     rendering = false;
                     currentAlpha = 0.4F;
                  }
               }
            }
         }
      }
   }

   private static void resetRenderState() {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableCull();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
   }

   private static EntityOtherPlayerMP getOrCreateFakePlayer(EntityPlayer var0) {
      GameProfile var1 = var0.getGameProfile();
      if (var1 == null) {
         return null;
      } else {
         if (fakePlayer == null || world != mc.theWorld || gameProfile != var1) {
            world = mc.theWorld;
            gameProfile = var1;
            fakePlayer = new EntityOtherPlayerMP(mc.theWorld, var1);
            fakePlayer.setEntityId(-742001);
         }

         return fakePlayer;
      }
   }

   private static void applyStateToFake(EntityPlayer var0, EntityOtherPlayerMP var1, FakePlayerRenderer$2 var2, float var3) {
      var1.setPositionAndRotation(
         FakePlayerRenderer$2.getPosX(var2), FakePlayerRenderer$2.getPosY(var2), FakePlayerRenderer$2.getPosZ(var2), FakePlayerRenderer$2.getRotationYaw(var2), FakePlayerRenderer$2.kiboaU(var2)
      );
      var1.prevPosX = FakePlayerRenderer$2.getPrevPosX(var2);
      var1.prevPosY = FakePlayerRenderer$2.getPrevPosY(var2);
      var1.prevPosZ = FakePlayerRenderer$2.getPrevPosZ(var2);
      var1.lastTickPosX = FakePlayerRenderer$2.getPrevPosX(var2);
      var1.lastTickPosY = FakePlayerRenderer$2.getPrevPosY(var2);
      var1.lastTickPosZ = FakePlayerRenderer$2.getPrevPosZ(var2);
      var1.rotationYaw = FakePlayerRenderer$2.getRotationYaw(var2);
      var1.prevRotationYaw = FakePlayerRenderer$2.getPrevRotationYaw(var2);
      var1.rotationPitch = FakePlayerRenderer$2.kiboaU(var2);
      var1.prevRotationPitch = FakePlayerRenderer$2.getPrevRotationPitch(var2);
      var1.rotationYawHead = FakePlayerRenderer$2.getRotationYawHead(var2);
      var1.prevRotationYawHead = FakePlayerRenderer$2.BfWxr(var2);
      var1.renderYawOffset = FakePlayerRenderer$2.getRenderYawOffset(var2);
      var1.prevRenderYawOffset = FakePlayerRenderer$2.getPrevRenderYawOffset(var2);
      var1.limbSwing = FakePlayerRenderer$2.getLimbSwing(var2) + FakePlayerRenderer$2.getLimbSwingAmount(var2) * (1.0F - var3);
      var1.limbSwingAmount = FakePlayerRenderer$2.getLimbSwingAmount(var2);
      var1.prevLimbSwingAmount = FakePlayerRenderer$2.getLimbSwingAmount(var2);
      var1.swingProgress = 0.0F;
      var1.prevSwingProgress = 0.0F;
      var1.onGround = FakePlayerRenderer$2.isOnGround(var2);
      var1.ticksExisted = var0.ticksExisted;
      var1.hurtTime = 0;
      var1.deathTime = 0;
      var1.setSneaking(var0.isSneaking());
      var1.setSprinting(var0.isSprinting());
      var1.setInvisible(false);
      var1.inventory.currentItem = var0.inventory.currentItem;

      for (int var4 = 0; var4 < var1.inventory.mainInventory.length; var4++) {
         var1.inventory.mainInventory[var4] = CQARcO(var0.inventory.mainInventory[var4]);
      }

      var1.inventory.mainInventory[var1.inventory.currentItem] = null;

      for (int var5 = 0; var5 < var1.inventory.armorInventory.length; var5++) {
         var1.inventory.armorInventory[var5] = CQARcO(var0.inventory.armorInventory[var5]);
      }
   }

   private static ItemStack CQARcO(ItemStack var0) {
      if (var0 == null) {
         return null;
      } else {
         ItemStack var1 = var0.copy();
         if (var1.hasTagCompound()) {
            NBTTagCompound var2 = (NBTTagCompound)var1.getTagCompound().copy();
            var2.removeTag("ench");
            var2.removeTag("StoredEnchantments");
            var1.setTagCompound(var2);
         }

         return var1;
      }
   }

   private static float clampUnit(float var0) {
      return var0 < 0.0F ? 0.0F : (var0 > 1.0F ? 1.0F : var0);
   }

   public static float clampAlpha(float var0) {
      return clampUnit(var0);
   }
}
