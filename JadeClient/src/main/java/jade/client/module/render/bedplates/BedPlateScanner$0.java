// Jade recovery: original class: jade.deps.eLz.WAq402O$0
package jade.client.module.render.bedplates;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public final class BedPlateScanner$0 {
   private final String blockKey;
   private final String displayName;
   private final ItemStack itemStack;
   private final TextureAtlasSprite textureAtlasSprite;

   BedPlateScanner$0(String var1, String var2, ItemStack var3, TextureAtlasSprite var4) {
      this.blockKey = var1;
      this.displayName = var2;
      this.itemStack = var3;
      this.textureAtlasSprite = var4;
   }

   private static BedPlateScanner$0 createEntry(Minecraft var0, World var1, IBlockState var2, BlockPos var3) {
      String var4 = Lega(var2);
      ItemStack var5 = resolveItemStack(var1, var2, var3);
      String var6 = wlrliw(var2, var5);
      TextureAtlasSprite var7 = null;
      if (var5 == null || var5.getItem() == null) {
         var7 = resolveModelSprite(var0, var2);
         var5 = var7 == null ? itemStackFromBlock(var2.getBlock()) : null;
      }

      String var8 = var5 != null && var5.getItem() != null ? resolveDisplayName(var5, var4) : var4;
      return new BedPlateScanner$0(var6, var8, var5, var7);
   }

   private ItemStack copyItemStack() {
      return this.itemStack == null ? null : this.itemStack.copy();
   }

   private static ItemStack resolveItemStack(World var0, IBlockState var1, BlockPos var2) {
      try {
         Block var3 = var1.getBlock();
         Item var4 = var3.getItem(var0, var2);
         if (var4 == null) {
            return null;
         } else {
            int var5 = var4.getHasSubtypes() ? var3.getDamageValue(var0, var2) : 0;
            return new ItemStack(var4, 1, var5);
         }
      } catch (Exception var6) {
         return null;
      }
   }

   private static TextureAtlasSprite resolveModelSprite(Minecraft var0, IBlockState var1) {
      return var0 != null && var0.getBlockRendererDispatcher() != null && var0.getBlockRendererDispatcher().getBlockModelShapes() != null
         ? var0.getBlockRendererDispatcher().getBlockModelShapes().getTexture(var1)
         : null;
   }

   private static String wlrliw(IBlockState var0, ItemStack var1) {
      if (var1 != null && var1.getItem() != null) {
         return Item.getIdFromItem(var1.getItem()) + ":" + var1.getMetadata();
      } else {
         Object var2 = Block.blockRegistry.getNameForObject(var0.getBlock());
         return var2 == null ? Integer.toString(Block.getIdFromBlock(var0.getBlock())) : var2.toString();
      }
   }

   private static String Lega(IBlockState var0) {
      String var1 = var0.getBlock().getLocalizedName();
      if (var1 != null && !var1.isEmpty()) {
         return var1;
      } else {
         Object var2 = Block.blockRegistry.getNameForObject(var0.getBlock());
         if (var2 == null) {
            return "unknown";
         } else {
            int var3 = var0.getBlock().getMetaFromState(var0);
            return var3 == 0 ? var2.toString() : var2 + ":" + var3;
         }
      }
   }

   private static String resolveDisplayName(ItemStack var0, String var1) {
      try {
         String var2 = var0.getDisplayName();
         return var2 != null && !var2.isEmpty() ? var2 : var1;
      } catch (Exception var3) {
         return var1;
      }
   }

   private static ItemStack itemStackFromBlock(Block var0) {
      if (var0 == Blocks.bed) {
         return new ItemStack(Items.bed);
      } else {
         try {
            Item var1 = Item.getItemFromBlock(var0);
            if (var1 != null) {
               return new ItemStack(var1, 1, var0.getMetaFromState(var0.getDefaultState()));
            }
         } catch (Exception var2) {
         }

         return new ItemStack(Blocks.barrier);
      }
   }

   @Override
   public boolean equals(Object var1) {
      return this == var1 || var1 instanceof BedPlateScanner$0 && this.blockKey.equals(((BedPlateScanner$0)var1).blockKey);
   }

   @Override
   public int hashCode() {
      return this.blockKey.hashCode();
   }

   public static ItemStack copyEntryItemStack(BedPlateScanner$0 var0) {
      return var0.copyItemStack();
   }

   public static TextureAtlasSprite getSprite(BedPlateScanner$0 var0) {
      return var0.textureAtlasSprite;
   }

   public static String getDisplayName(BedPlateScanner$0 var0) {
      return var0.displayName;
   }

   public static BedPlateScanner$0 createFromBlockState(Minecraft var0, World var1, IBlockState var2, BlockPos var3) {
      return createEntry(var0, var1, var2, var3);
   }
}
