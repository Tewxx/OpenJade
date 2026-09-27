// Jade recovery: recovered class name: HookIds
package jade.inject;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class HookIds {
   private static final Map<String, Integer> IDS = new LinkedHashMap<>();

   private HookIds() {
   }

   public static synchronized int id(String name, String descriptor) {
      String key = name + descriptor;
      Integer existing = IDS.get(key);
      if (existing != null) {
         return existing;
      } else {
         int next = IDS.size();
         if (next >= 256) {
            throw new IllegalStateException(
               "too many injection hook signatures"
            );
         } else {
            IDS.put(key, next);
            return next;
         }
      }
   }

   public static synchronized Map<String, Integer> snapshot() {
      return Collections.unmodifiableMap(new LinkedHashMap<>(IDS));
   }

   public static synchronized Integer find(String name, String descriptor) {
      return IDS.get(name + descriptor);
   }

   public static synchronized void clear() {
      IDS.clear();
   }
}
