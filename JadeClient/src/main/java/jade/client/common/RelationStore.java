// Jade recovery: original class: jade.deps.eLz.g1fqWq0SR
package jade.client.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Map;

public final class RelationStore {
   private final LinkedHashMap<String, String> friends = new LinkedHashMap<>();
   private final LinkedHashMap<String, String> jhiinQ = new LinkedHashMap<>();

   public RelationChangeResult toggleRelationEntry(RelationManager$1 var1, String var2) {
      String var3 = normalizeName(var2);
      if (var3.isEmpty()) {
         return new RelationChangeResult(false, false);
      } else {
         LinkedHashMap var4 = this.ihUr(var1);
         LinkedHashMap var5 = this.ihUr(HuuW(var1));
         String var6 = resolveDisplayName(var2, var3);
         boolean var7 = !var4.containsKey(var3);
         boolean var8 = var5.remove(var3) != null;
         boolean var9 = !var6.equals(var4.get(var3));
         if (!var7 && !var8 && !var9) {
            return new RelationChangeResult(false, false);
         } else {
            var4.put(var3, var6);
            return new RelationChangeResult(true, var7);
         }
      }
   }

   public boolean removeRelation(RelationManager$1 var1, String var2) {
      String var3 = normalizeName(var2);
      return !var3.isEmpty() && this.ihUr(var1).remove(var3) != null;
   }

   public boolean clearRelations(RelationManager$1 var1) {
      LinkedHashMap var2 = this.ihUr(var1);
      if (var2.isEmpty()) {
         return false;
      } else {
         var2.clear();
         return true;
      }
   }

   public void svviY() {
      this.friends.clear();
      this.jhiinQ.clear();
   }

   public boolean hasRelation(RelationManager$1 var1, String var2) {
      String var3 = normalizeName(var2);
      return !var3.isEmpty() && this.ihUr(var1).containsKey(var3);
   }

   public int WHpE(RelationManager$1 var1) {
      return this.ihUr(var1).size();
   }

   public List<RelationManager$0> getEntries(RelationManager$1 var1) {
      ArrayList var2 = new ArrayList(this.WHpE(var1));

      for (Entry var4 : this.ihUr(var1).entrySet()) {
         var2.add(new RelationManager$0((String)var4.getKey(), (String)var4.getValue()));
      }

      return var2;
   }

   public List<String> hdnH(RelationManager$1 var1) {
      return new ArrayList<>(this.ihUr(var1).keySet());
   }

   public boolean updateDisplayName(String var1) {
      String var2 = normalizeName(var1);
      if (var2.isEmpty()) {
         return false;
      } else {
         String var3 = resolveDisplayName(var1, var2);
         return this.updateEntryIfChanged(this.friends, var2, var3) | this.updateEntryIfChanged(this.jhiinQ, var2, var3);
      }
   }

   public void setEntry(RelationManager$1 var1, String var2, String var3) {
      String var4 = normalizeName(var2);
      if (!var4.isEmpty()) {
         this.ihUr(var1).put(var4, resolveDisplayName(var3, var4));
      }
   }

   public Map<String, String> getEntryMap(RelationManager$1 var1) {
      return new LinkedHashMap<>(this.ihUr(var1));
   }

   private boolean updateEntryIfChanged(LinkedHashMap<String, String> var1, String var2, String var3) {
      if (var1.containsKey(var2) && !var3.equals(var1.get(var2))) {
         var1.put(var2, var3);
         return true;
      } else {
         return false;
      }
   }

   private LinkedHashMap<String, String> ihUr(RelationManager$1 var1) {
      return var1 == RelationManager$1.FRIEND ? this.friends : this.jhiinQ;
   }

   private static RelationManager$1 HuuW(RelationManager$1 var0) {
      return var0 == RelationManager$1.FRIEND ? RelationManager$1.ENEMY : RelationManager$1.FRIEND;
   }

   public static String normalizeName(String var0) {
      return var0 == null ? "" : var0.trim().toLowerCase(Locale.ROOT);
   }

   public static String resolveDisplayName(String var0, String var1) {
      String var2 = var0 == null ? "" : var0.trim();
      return var2.isEmpty() ? var1 : var2;
   }
}
