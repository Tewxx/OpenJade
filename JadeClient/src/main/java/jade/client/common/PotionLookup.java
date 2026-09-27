// Jade recovery: original class: jade.deps.eLz.gO1DnYYZeM
package jade.client.common;

import jade.client.setting.NameListSetting;
import java.util.List;
import net.minecraft.item.ItemStack;

public final class PotionLookup {
   private static final PotionIndex potionIndex = new PotionIndex();
   private static final mfVOUAAl7 potionItemFactory = new mfVOUAAl7();

   private PotionLookup() {
   }

   public static List<PotionLookup$0> searchPotions(String var0, NameListSetting var1) {
      return FuzzyNameSearch.searchAndRank(potionIndex.JXmgUi(), var0, (recoveredArg0) -> PotionLookup.isExcluded(var1, (java.lang.String) recoveredArg0));
   }

   public static String getPotionDisplayName(String var0) {
      PotionLookup$0 var1 = potionIndex.findByName(var0);
      return var1 == null ? var0 : var1.displayName;
   }

   public static ItemStack GhAk(String var0) {
      PotionLookup$0 var1 = potionIndex.findByName(var0);
      return var1 == null ? null : potionItemFactory.getCachedStack(var1.YfS);
   }

   private static boolean isExcluded(NameListSetting var0, String var1) {
      return var0.hasEntry(var1);
   }
}
