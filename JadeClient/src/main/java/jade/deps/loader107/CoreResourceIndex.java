package jade.deps.loader107;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CoreResourceIndex {
   static final String INDEX_RESOURCE_PATH = "META-INF/jade-core-resource-index";
   private static final Map<String, byte[]> memoryResources = new HashMap<>();
   private static final Set<String> indexedPaths = new HashSet<>();
   private static boolean indexLoaded;

   private CoreResourceIndex() {
   }

   public static synchronized void registerMemoryResources(Map<String, byte[]> var0) {
      memoryResources.putAll(var0);
   }

   public static synchronized InputStream openResource(String var0) {
      String var1 = normalizeResourcePath(var0);
      byte[] var2 = memoryResources.get(var1);
      return (InputStream)(var2 != null ? new ByteArrayInputStream(var2) : CoreResourceIndex.class.getClassLoader().getResourceAsStream(var1));
   }

   public static synchronized List<String> listResources(String var0) {
      loadResourceIndex();
      String var1 = normalizeResourcePath(var0);
      HashSet var2 = new HashSet();

      for (String var4 : memoryResources.keySet()) {
         if (var4.startsWith(var1)) {
            var2.add(var4);
         }
      }

      for (String var7 : indexedPaths) {
         if (var7.startsWith(var1)) {
            var2.add(var7);
         }
      }

      ArrayList var6 = new ArrayList(var2);
      Collections.sort(var6);
      return var6;
   }

   private static void loadResourceIndex() {
      if (!indexLoaded) {
         indexLoaded = true;
         InputStream var0 = CoreResourceIndex.class.getClassLoader().getResourceAsStream("META-INF/jade-core-resource-index");
         if (var0 != null) {
            BufferedReader var1 = new BufferedReader(new InputStreamReader(var0, StandardCharsets.UTF_8));

            try {
               String var2;
               try {
                  while ((var2 = var1.readLine()) != null) {
                     String var3 = normalizeResourcePath(var2.trim());
                     if (var3.length() != 0) {
                        indexedPaths.add(var3);
                     }
                  }
               } catch (IOException var12) {
                  indexedPaths.clear();
               }
            } finally {
               try {
                  var1.close();
               } catch (IOException var11) {
               }
            }
         }
      }
   }

   private static String normalizeResourcePath(String var0) {
      return var0.startsWith("/") ? var0.substring(1) : var0;
   }
}
