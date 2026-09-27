// Jade recovery: original class: jade.deps.eLz.Lellt2mOFO$1
package jade.client.hook;

import java.util.Collections;
import java.util.Set;

public final class ClassLoaderAssetCache$1 {
   private final Set<String> OJJg;
   private final boolean indexLoaded;

   ClassLoaderAssetCache$1(Set<String> var1, boolean var2) {
      this.OJJg = var1;
      this.indexLoaded = var2;
   }

   private static ClassLoaderAssetCache$1 createEmptyIndex() {
      return new ClassLoaderAssetCache$1(Collections.emptySet(), false);
   }

   public static boolean isIndexLoaded(ClassLoaderAssetCache$1 var0) {
      return var0.indexLoaded;
   }

   public static Set getAssetEntryPaths(ClassLoaderAssetCache$1 var0) {
      return var0.OJJg;
   }

   public static ClassLoaderAssetCache$1 NjWp6() {
      return createEmptyIndex();
   }
}
