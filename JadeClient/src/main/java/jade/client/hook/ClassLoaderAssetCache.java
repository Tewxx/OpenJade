// Jade recovery: original class: jade.deps.eLz.Lellt2mOFO
package jade.client.hook;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ClassLoaderAssetCache {
   private static final Map<ClassLoader, ClassLoaderAssetCache$1> assetCacheByLoader = new WeakHashMap<>();

   private ClassLoaderAssetCache() {
   }

   public static Boolean gZe4(ClassLoader var0, String var1) {
      if (var0 != null && var1 != null && var0 instanceof URLClassLoader) {
         ClassLoaderAssetCache$1 var2;
         synchronized (assetCacheByLoader) {
            var2 = assetCacheByLoader.get(var0);
            if (var2 == null) {
               var2 = buildAssetIndex((URLClassLoader)var0);
               assetCacheByLoader.put(var0, var2);
            }
         }

         return ClassLoaderAssetCache$1.isIndexLoaded(var2) ? ClassLoaderAssetCache$1.getAssetEntryPaths(var2).contains(var1) : null;
      } else {
         return null;
      }
   }

   private static ClassLoaderAssetCache$1 buildAssetIndex(URLClassLoader var0) {
      HashSet var1 = new HashSet();
      URL[] var2 = var0.getURLs();
      if (var2 != null && var2.length != 0) {
         try {
            for (URL var6 : var2) {
               if (var6 == null || !"file".equalsIgnoreCase(var6.getProtocol())) {
                  return ClassLoaderAssetCache$1.NjWp6();
               }

               URI var7 = var6.toURI();
               File var8 = new File(var7);
               if (var8.isDirectory()) {
                  File var9 = new File(var8, "assets");
                  if (var9.isDirectory() && !OLqoV(var8, var9, var1)) {
                     return ClassLoaderAssetCache$1.NjWp6();
                  }
               } else {
                  if (!var8.isFile()) {
                     return ClassLoaderAssetCache$1.NjWp6();
                  }

                  if (!collectJarAssetEntries(var8, var1)) {
                     return ClassLoaderAssetCache$1.NjWp6();
                  }
               }
            }

            return new ClassLoaderAssetCache$1(Collections.unmodifiableSet(var1), true);
         } catch (Throwable var10) {
            return ClassLoaderAssetCache$1.NjWp6();
         }
      } else {
         return ClassLoaderAssetCache$1.NjWp6();
      }
   }

   private static boolean collectJarAssetEntries(File var0, Set<String> var1) {
      JarFile var2 = null;

      boolean var4;
      try {
         var2 = new JarFile(var0, false);
         Enumeration var3 = var2.entries();

         while (var3.hasMoreElements()) {
            JarEntry var16 = (JarEntry)var3.nextElement();
            String var5 = var16.getName();
            if (!var16.isDirectory() && var5.startsWith("assets/")) {
               var1.add(var5);
            }
         }

         return true;
      } catch (Throwable var14) {
         var4 = false;
      } finally {
         if (var2 != null) {
            try {
               var2.close();
            } catch (Throwable var13) {
            }
         }
      }

      return var4;
   }

   private static boolean OLqoV(File var0, File var1, Set<String> var2) {
      File[] var3 = var1.listFiles();
      if (var3 == null) {
         return false;
      } else {
         for (File var7 : var3) {
            if (var7.isDirectory()) {
               if (!OLqoV(var0, var7, var2)) {
                  return false;
               }
            } else if (var7.isFile()) {
               String var8 = var0.toURI().relativize(var7.toURI()).getPath();
               if (var8.startsWith("assets/")) {
                  var2.add(var8);
               }
            }
         }

         return true;
      }
   }
}
