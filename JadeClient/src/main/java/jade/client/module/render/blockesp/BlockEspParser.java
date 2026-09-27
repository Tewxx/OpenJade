// Jade recovery: original class: jade.deps.eLz.wJOo2uOK
package jade.client.module.render.blockesp;

import jade.client.common.VCvk14;
import jade.client.setting.BlockListSetting;
import java.util.List;
import net.minecraft.item.ItemStack;

public final class BlockEspParser {
   private static final BlockItemIcons blockItemIcons = new BlockItemIcons();
   private static final BlockEspCatalog blockEspCatalog = new BlockEspCatalog(blockItemIcons);

   public static List<BlockEspParser$0> parseEntries(String var0, BlockListSetting var1) {
      return BlockEspSearch.searchFlat(var0, var1, blockEspCatalog);
   }

   public static List<BlockEspParser$1> parseGroupedEntries(String var0, BlockListSetting var1) {
      return parseGroupedEntriesIncludingUnlisted(var0, var1, false);
   }

   public static List<BlockEspParser$1> parseGroupedEntriesIncludingUnlisted(String var0, BlockListSetting var1, boolean var2) {
      return BlockEspSearch.searchGrouped(var0, var1, var2, blockEspCatalog);
   }

   public static List<BlockEspParser$0> NsGry(String var0) {
      return blockEspCatalog.getEntriesForBlock(var0);
   }

   public static boolean isWildcardPattern(String var0) {
      return VCvk14.hasWildcardSuffix(var0);
   }

   public static String getBaseRegistryName(String var0) {
      return VCvk14.stripWildcardSuffix(var0);
   }

   public static ItemStack getIconStack(String var0) {
      return blockItemIcons.SxiT(var0);
   }

   public static String getIconDisplayName(String var0) {
      return blockItemIcons.ublf(var0);
   }
}
