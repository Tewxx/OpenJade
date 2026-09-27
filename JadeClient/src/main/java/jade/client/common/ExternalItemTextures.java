// Jade recovery: original class: jade.deps.eLz.WVsIvMajM
package jade.client.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class ExternalItemTextures {
   private static final Map<String, ExternalItemTextures$0> texturesByKey = new LinkedHashMap<>(256, 0.75F, true);
   private static final Map<Integer, ExternalItemTextures$0> texturesById = new HashMap<>();
   private static int nextTextureId = 1;
   private static Object wsZ11;
   private static boolean sUex;

   private ExternalItemTextures() {
   }

   public static void refreshIfAtlasChanged() {
      TextureAtlasSprite var0 = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:items/arrow");
      if (var0 != wsZ11) {
         SuvwT();
         wsZ11 = var0;
      }
   }

   public static void drawItemTexture(ExternalRenderBuffer var0, ItemStack var1, double var2, double var4, float var6, float var7, float var8) {
      if (var1 != null && var1.getItem() != null) {
         refreshIfAtlasChanged();
         String var9 = Item.getIdFromItem(var1.getItem()) + ":" + var1.getMetadata() + ":" + var1.getTagCompound();
         ExternalItemTextures$0 var10 = texturesByKey.get(var9);
         if (var10 == null) {
            var10 = new ExternalItemTextures$0(var1.copy(), null);
            putCachedTexture(var9, var10);
         }

         var0.drawTexture(var10.textureId, var2, var4, var6, var7, var8);
      }
   }

   public static void qlKvouG(ExternalRenderBuffer var0, TextureAtlasSprite var1, double var2, double var4, float var6) {
      if (var1 != null) {
         refreshIfAtlasChanged();
         String var7 = "sprite:" + var1.getIconName();
         ExternalItemTextures$0 var8 = texturesByKey.get(var7);
         if (var8 == null) {
            var8 = new ExternalItemTextures$0(null, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(var1.getIconName()));
            putCachedTexture(var7, var8);
         }

         var0.drawTexture(var8.textureId, var2, var4, var6, 1.0F, 0.0F);
      }
   }

   private static void putCachedTexture(String var0, ExternalItemTextures$0 var1) {
      if (texturesByKey.size() >= 256) {
         Iterator var2 = texturesByKey.values().iterator();
         texturesById.remove(((ExternalItemTextures$0)var2.next()).textureId);
         var2.remove();
      }

      texturesByKey.put(var0, var1);
      texturesById.put(var1.textureId, var1);
   }

   public static void uploadPendingItemTextures(ExternalRenderBuffer var0, ExternalOverlaySession var1) {
      var0.IVr5 = var1.Rct;
      int var2 = 0;
      HashSet var3 = new HashSet();

      for (int var4 = 0; var4 < var0.referencedTextureCount; var4++) {
         ExternalItemTextures$0 var5 = texturesById.get(var0.referencedTextureIds[var4]);
         if (var5 != null && var3.add(var5.textureId) && !var1.hasTexture(var5.textureId)) {
            if (var5.FBul == null && var2 < 2 && System.nanoTime() >= var5.retryAfterNanos) {
               var2++;

               try {
                  var5.FBul = ItemTextureCapture.UAlhg(var5.itemStack, var5.textureAtlasSprite);
               } catch (RuntimeException var7) {
                  if (!sUex) {
                     ClientUtils.sendColoredMessage("&eAn external item texture could not be captured. Other external visuals remain active.");
                     sUex = true;
                  }
               }

               if (var5.FBul == null) {
                  var5.retryAfterNanos = System.nanoTime() + 5000000000L;
               }
            }

            if (var5.FBul != null) {
               var0.FnkMa(var5.textureId, var5.FBul);
            }
         }
      }
   }

   public static void SuvwT() {
      texturesByKey.clear();
      texturesById.clear();
      sUex = false;
      ExternalGlyphCache.clearCache();
      VanillaGlyphs.resetCache();
   }

   public static int allocateTextureId() {
      return nextTextureId++;
   }
}
