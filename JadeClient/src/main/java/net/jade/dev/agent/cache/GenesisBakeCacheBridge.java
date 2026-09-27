// Jade recovery: recovered class name: GenesisBakeCacheBridge
package net.jade.dev.agent.cache;

import jade.client.hook.BakeCacheWatcher;
import jade.client.hook.InMemoryBakeZip$0;
import jade.client.hook.InMemoryBakeZip;
import jade.client.hook.MixinWarmup;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public final class GenesisBakeCacheBridge {
   private static final Set<ClassLoader> EXPOSED_LOADERS = Collections.newSetFromMap(Collections.synchronizedMap(new WeakHashMap<>()));
   private static volatile BakeCacheWatcher controller;
   private static volatile MixinWarmup registrationGate;
   private static volatile URL resourceUrl;

   private GenesisBakeCacheBridge() {
   }

   public static synchronized void install(BakeCacheWatcher installedController, MixinWarmup installedGate, URL warmCacheResourceUrl) {
      if (installedController == null || installedGate == null || warmCacheResourceUrl == null) {
         throw new IllegalArgumentException();
      } else if (controller != null && controller != installedController) {
         throw new IllegalStateException(
            "Genesis cache bridge already installed"
         );
      } else {
         controller = installedController;
         registrationGate = installedGate;
         resourceUrl = warmCacheResourceUrl;
      }
   }

   public static ZipFile open(File file, ClassLoader genesisLoader) throws IOException {
      BakeCacheWatcher active = requireController();
      final MixinWarmup gate = registrationGate;
      if (gate == null) {
         throw new IOException("Mixin registration gate unavailable");
      } else {
         ZipFile opened = active.openEncryptedBakeCache(
            file,
            new InMemoryBakeZip$0() {
               @Override
               public void onCacheConsumed(Map<String, byte[]> var1) throws IOException {
                  if (!gate.activatePreparedWarmCache(var1)) {
                     throw new ZipException(
                        "initialized warm-cache activation failed: "
                           + gate.warmCacheRejectionReason()
                     );
                  }
               }

               @Override
               public void onCacheAborted() {
                  gate.abortPreparedWarmCache();
               }
            }
         );
         if (!(opened instanceof InMemoryBakeZip)) {
            closeQuietly(opened);
            throw new ZipException("unexpected cache facade");
         } else {
            InMemoryBakeZip memory = (InMemoryBakeZip)opened;

            try {
               Map<String, byte[]> byInternalName = memory.entriesByInternalName();
               if (!gate.prepareWarmCache(byInternalName, 120L, TimeUnit.SECONDS)) {
                  throw new IOException(
                     "cache does not cover every Mixin target: "
                        + gate.warmCacheRejectionReason()
                  );
               } else {
                  exposeResourceUrl(genesisLoader);
                  System.out
                     .println(
                        "[Mod-Agent] Accepted Lunar whole-class cache ("
                           + memory.size()
                           + " classes)."
                     );
                  return memory;
               }
            } catch (Throwable var8) {
               closeQuietly(memory);
               ZipException rejected = new ZipException(
                  "Lunar whole-class cache activation failed"
               );
               rejected.initCause(var8);
               throw rejected;
            }
         }
      }
   }

   public static void write(TreeMap<String, byte[]> entries, File file) throws IOException {
      MixinWarmup gate = registrationGate;
      if (gate != null && gate.isRegistrationComplete()) {
         requireController().writeEncryptedBakeCache(entries, file);
         System.out
            .println(
               "[Mod-Agent] Stored encrypted Lunar whole-class cache ("
                  + entries.size()
                  + " classes)."
            );
      } else {
         throw new IOException(
            "cold cache write refused before Mixin registration completed"
         );
      }
   }

   private static BakeCacheWatcher requireController() throws IOException {
      BakeCacheWatcher active = controller;
      if (active != null && active.hasGenesisHookFingerprint()) {
         return active;
      } else {
         throw new IOException(
            "exact Genesis bake-cache hook is unavailable"
         );
      }
   }

   private static void exposeResourceUrl(ClassLoader loader) throws Exception {
      if (loader == null) {
         throw new IOException("Genesis context loader is unavailable");
      } else {
         synchronized (EXPOSED_LOADERS) {
            if (!EXPOSED_LOADERS.contains(loader)) {
               Method addUrl = null;

               for (Class<?> type = loader.getClass(); type != null && addUrl == null; type = type.getSuperclass()) {
                  try {
                     addUrl = type.getDeclaredMethod("addURL", URL.class);
                  } catch (NoSuchMethodException var6) {
                  }
               }

               if (addUrl == null) {
                  throw new IOException(
                     "Genesis loader cannot expose resources"
                  );
               } else {
                  addUrl.setAccessible(true);
                  addUrl.invoke(loader, resourceUrl);
                  EXPOSED_LOADERS.add(loader);
               }
            }
         }
      }
   }

   private static void closeQuietly(ZipFile file) {
      if (file != null) {
         try {
            file.close();
         } catch (IOException var2) {
         }
      }
   }
}
