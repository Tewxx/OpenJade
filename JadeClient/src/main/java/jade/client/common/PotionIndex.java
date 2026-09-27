// Jade recovery: original class: jade.deps.eLz.kNXOCUi8eh
package jade.client.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.potion.Potion;
import net.minecraft.util.StatCollector;

public final class PotionIndex {
   private Map<String, PotionLookup$0> potionByName;
   private List<PotionLookup$0> ELWj;

   public List<PotionLookup$0> JXmgUi() {
      this.ensureInitialized();
      return this.ELWj;
   }

   public PotionLookup$0 findByName(String var1) {
      this.ensureInitialized();
      return this.potionByName.get(var1);
   }

   public static String resolveDisplayName(String var0, String var1) {
      return var1 != null && !var1.isEmpty() && !var1.equals(var0) ? var1 : FuzzyNameSearch.stripNamespace(var0);
   }

   private void ensureInitialized() {
      if (this.ELWj == null || this.potionByName == null) {
         LinkedHashMap var1 = new LinkedHashMap();
         Potion[] var2 = Potion.potionTypes;

         for (int var3 = 0; var3 < var2.length; var3++) {
            Potion var4 = var2[var3];
            String var5 = var4 == null ? null : var4.getName();
            if (var5 != null && !var5.isEmpty() && !var1.containsKey(var5)) {
               String var6 = resolveDisplayName(var5, StatCollector.translateToLocal(var5));
               var1.put(var5, new PotionLookup$0(var3, var5, var6));
            }
         }

         ArrayList var7 = new ArrayList(var1.values());
         var7.sort(FuzzyNameSearch.comparator);
         this.ELWj = Collections.unmodifiableList(var7);
         this.potionByName = var1;
      }
   }
}
