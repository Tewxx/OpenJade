// Jade recovery: original class: jade.deps.eLz.qKydcbP
package jade.client.module.render.blockesp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class BlockEspCatalog {
   private final BlockItemIcons blockItemIcons;
   private List<BlockEspParser$0> allEntries;
   private Map<String, List<BlockEspParser$0>> PjcS;

   public BlockEspCatalog(BlockItemIcons var1) {
      this.blockItemIcons = var1;
   }

   public List<BlockEspParser$0> getAllEntries() {
      this.ensureCatalogLoaded();
      return this.allEntries;
   }

   public List<BlockEspParser$0> getEntriesForBlock(String var1) {
      this.ensureCatalogLoaded();
      List var2 = this.PjcS == null ? null : this.PjcS.get(var1);
      return var2 == null ? Collections.emptyList() : var2;
   }

   private void ensureCatalogLoaded() {
      if (this.allEntries == null) {
         this.allEntries = new ArrayList<>();
         HashMap var1 = new HashMap();

         for (Block var3 : Block.blockRegistry) {
            ResourceLocation var4 = (ResourceLocation)Block.blockRegistry.getNameForObject(var3);
            if (var4 != null) {
               String var5 = var4.toString();
               Item var6 = Item.getItemFromBlock(var3);
               if (var6 == null) {
                  Item var15 = this.blockItemIcons.PBYJHOb(var3);
                  if (var15 != null) {
                     String var16 = new ItemStack(var15, 1, 0).getDisplayName();
                     if (isNonEmptyName(var16)) {
                        BlockEspParser$0 var17 = new BlockEspParser$0(var3, 0, var16, var5, var15);
                        this.allEntries.add(var17);
                        var1.put(var5, Collections.singletonList(var17));
                     }
                  }
               } else {
                  ArrayList var7 = new ArrayList();
                  var3.getSubBlocks(var6, CreativeTabs.tabBlock, var7);
                  if (var7.isEmpty()) {
                     var7.add(new ItemStack(var3, 1, 0));
                  }

                  ArrayList var8 = new ArrayList();

                  for (ItemStack var10 : (java.lang.Iterable<ItemStack>) (java.lang.Iterable<?>) (var7)) {
                     String var11 = var10.getDisplayName();
                     if (isNonEmptyName(var11)) {
                        int var12 = var10.getMetadata();
                        String var13 = var12 == 0 ? var5 : var5 + ':' + var12;
                        BlockEspParser$0 var14 = new BlockEspParser$0(var3, var12, var11, var13);
                        var8.add(var14);
                        this.allEntries.add(var14);
                     }
                  }

                  if (!var8.isEmpty()) {
                     var8.sort(Comparator.comparingInt(BlockEspCatalog::entryMetadata));
                     var1.put(var5, var8);
                  }
               }
            }
         }

         this.allEntries.sort(Comparator.comparing(BlockEspCatalog::Bkvyo, String.CASE_INSENSITIVE_ORDER));
         this.PjcS = var1;
      }
   }

   private static boolean isNonEmptyName(String var0) {
      return var0 != null && !var0.isEmpty();
   }

   private static String Bkvyo(BlockEspParser$0 var0) {
      return var0.displayName;
   }

   private static int entryMetadata(BlockEspParser$0 var0) {
      return var0.metadata;
   }
}
