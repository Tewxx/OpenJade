// Jade recovery: original class: jade.deps.eLz.MRof25
package jade.client.module.render.bedplates;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

public final class BedPlateEntry {
   private final ItemStack itemStack;
   private final TextureAtlasSprite textureAtlasSprite;
   private final int count;
   private final String displayName;

   public BedPlateEntry(ItemStack var1, TextureAtlasSprite var2, int var3, String var4) {
      this.itemStack = var1;
      this.textureAtlasSprite = var2;
      this.count = var3;
      this.displayName = var4;
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   public TextureAtlasSprite getSprite() {
      return this.textureAtlasSprite;
   }

   public int GULE() {
      return this.count;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public boolean hasItem() {
      return this.itemStack != null && this.itemStack.getItem() != null;
   }

   public boolean hasSprite() {
      return this.textureAtlasSprite != null;
   }
}
