// Jade recovery: original class: jade.deps.eLz.LICA3Nq
package jade.client.hook;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public final class BakeCacheWatcher {
   private static final long POLL_INTERVAL_MILLIS = 1000L;
   private static final long RESCAN_INTERVAL_MILLIS = 1000L;
   private static final String uo06 = ".jade-core-cache-id";
   private static final String Oroa = ".jade-core-cache-id.tmp";
   private static final String jTlo = "bake.zip";
   private static final String LOCK_FILE_NAME = ".jade-bake-cache.lock";
   private static final String frqUa = ".jade-bake-cache-";
   private static final String prew = ".tmp";
   private static final String wxB = "[0-9a-f]{64}";
   private static final String SHORT_HEX_PATTERN = "[0-9a-f]{1,8}";
   private final Path path;
   private final long scanIntervalMillis;
   private final AtomicBoolean watcherStarted = new AtomicBoolean(false);
   private final AtomicBoolean stopRequested = new AtomicBoolean(false);
   private final AtomicBoolean shutdownHookRegistered = new AtomicBoolean(false);
   private final Object zdR1 = new Object();
   private final Set<Path> paths = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private volatile WatchService watchService;
   private volatile Thread watcherThread;
   private volatile byte[] SAxwA;
   private volatile String cacheBindingSha256;
   private volatile int cacheEpoch;
   private volatile byte[] pipelineFingerprint;
   private volatile byte[] genesisHookFingerprint;
   private volatile boolean genesisHookFingerprintSet;

   public BakeCacheWatcher(Path var1, long var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("cacheDirectory");
      } else if (var2 <= 0L) {
         throw new IllegalArgumentException("scanIntervalMillis");
      } else {
         this.path = var1.toAbsolutePath().normalize();
         this.scanIntervalMillis = var2;
      }
   }

   public void prepareCacheProtection() throws IOException {
      this.WGfpC();
      this.xkQ7();
      int var1 = this.purgeUnsafeCacheArtifacts();
      if (var1 != 0) {
         System.out
            .println(
               "[Mod-Agent] Removed "
                  + var1
                  + " unsafe Lunar bake cache artifact(s) before bootstrap."
            );
      }

      this.startWatcher();
   }

   public void installCacheBinding(byte[] var1, String var2, int var3) {
      if (var1 == null || var1.length != 32) {
         throw new IllegalArgumentException("cacheKey");
      } else if (var2 == null || !var2.matches("[0-9a-f]{64}")) {
         throw new IllegalArgumentException("cacheBindingSha256");
      } else if (var3 <= 0) {
         throw new IllegalArgumentException("cacheEpoch");
      } else {
         synchronized (this.zdR1) {
            if (this.SAxwA != null) {
               throw new IllegalStateException("cache key already installed");
            } else {
               this.SAxwA = var1;
               this.cacheBindingSha256 = var2;
               this.cacheEpoch = var3;
            }
         }
      }
   }

   public void setPipelineFingerprint(byte[] var1) {
      if (var1 != null && var1.length == 32) {
         synchronized (this.zdR1) {
            wipeBytes(this.pipelineFingerprint);
            this.pipelineFingerprint = Arrays.copyOf(var1, var1.length);
         }
      } else {
         throw new IllegalArgumentException("pipelineFingerprint");
      }
   }

   public void setGenesisHookFingerprint(byte[] var1) {
      if (var1 != null && var1.length == 32) {
         synchronized (this.zdR1) {
            wipeBytes(this.genesisHookFingerprint);
            this.genesisHookFingerprint = Arrays.copyOf(var1, var1.length);
            this.genesisHookFingerprintSet = true;
         }
      } else {
         throw new IllegalArgumentException("genesisHookFingerprint");
      }
   }

   public void reportEncryptedCacheUnavailable(String var1) {
      this.FVowbQo();
      if (var1 != null && var1.length() != 0) {
         System.out
            .println(
               "[Mod-Agent] Encrypted Genesis cache unavailable: "
                  + var1
                  + "; using deletion-only cache protection."
            );
      }
   }

   public boolean hasGenesisHookFingerprint() {
      return this.genesisHookFingerprintSet;
   }

   public ZipFile openEncryptedBakeCache(File var1, final InMemoryBakeZip$0 var2) throws IOException {
      final Path var3;
      try {
         var3 = this.resolveCacheFilePath(var1, true);
      } catch (IOException var46) {
         throw RDcCynN("unsafe Lunar bake cache path", var46);
      }

      byte[] var4 = null;
      byte[] var5 = null;

      InMemoryBakeZip var47;
      try {
         var4 = this.getCacheKeyCopy();
         var5 = this.computeBindingDigest(var3);

         final BakeCacheWatcher$3 var6;
         try {
            var6 = this.acquireLockOrThrow(var3);
         } catch (IOException var43) {
            throw RDcCynN("initialized Lunar bake cache is busy", var43);
         }

         LinkedHashMap var7;
         try {
            var7 = EncryptedBakeCache.readEncryptedEntries(var3, var4, var5);
         } catch (IOException var42) {
            IOException var8 = var42;

            try {
               Files.deleteIfExists(var3);
            } catch (IOException var39) {
               var8.addSuppressed(var39);
            } finally {
               try {
                  var6.close();
               } catch (IOException var38) {
                  var42.addSuppressed(var38);
               }
            }

            throw RDcCynN("initialized Lunar bake cache rejected", var42);
         }

         try {
            var47 = new InMemoryBakeZip(var1, var7, new InMemoryBakeZip$0() {
               @Override
               public void onCacheConsumed(Map<String, byte[]> var1) throws IOException {
                  var2.onCacheConsumed(var1);

                  try {
                     var6.close();
                  } finally {
                     BakeCacheWatcher.clearBindingSecrets(BakeCacheWatcher.this);
                  }
               }

               @Override
               public void onCacheAborted() {
                  try {
                     var2.onCacheAborted();
                  } finally {
                     try {
                        Files.deleteIfExists(var3);
                     } catch (IOException var36) {
                     } finally {
                        try {
                           var6.close();
                        } catch (IOException var35) {
                        }
                     }
                  }
               }
            });
         } catch (Throwable var41) {
            EncryptedBakeCache.wipeEntries(var7);

            try {
               var6.close();
            } catch (IOException var37) {
               var41.addSuppressed(var37);
            }

            if (var41 instanceof Error) {
               throw (Error)var41;
            }

            throw RDcCynN(
               "initialized Lunar cache facade unavailable", var41
            );
         }
      } catch (RuntimeException var44) {
         throw RDcCynN(
            "initialized Lunar bake cache is not ready", var44
         );
      } finally {
         wipeBytes(var4);
         wipeBytes(var5);
      }

      return var47;
   }

   public void writeEncryptedBakeCache(TreeMap<String, byte[]> var1, File var2) throws IOException {
      byte[] var3 = null;
      byte[] var4 = null;
      Path var5 = null;

      try {
         Path var6 = this.resolveCacheFilePath(var2, false);
         var3 = this.getCacheKeyCopy();
         var4 = this.computeBindingDigest(var6);

         try (BakeCacheWatcher$3 var7 = this.acquireLockOrThrow(var6)) {
            var5 = var6.resolveSibling(
               ".jade-bake-cache-"
                  + UUID.randomUUID().toString()
                  + ".tmp"
            );
            this.requireInsideCacheRoot(var5);
            this.paths.add(normalizeAbsolute(var5));
            EncryptedBakeCache.writeEncryptedEntries(var5, var1, var3, var4);
            requireSafeCachePath(var5);

            try (FileChannel var9 = FileChannel.open(var5, StandardOpenOption.WRITE)) {
               var9.force(true);
            }

            try {
               Files.move(var5, var6, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException var78) {
               throw new IOException(
                  "atomic encrypted cache replacement is unavailable",
                  var78
               );
            }

            this.paths.remove(normalizeAbsolute(var5));
            var5 = null;
         }
      } finally {
         wipeBytes(var3);
         wipeBytes(var4);

         try {
            if (var5 != null) {
               this.paths.remove(normalizeAbsolute(var5));
               Files.deleteIfExists(var5);
            }
         } finally {
            this.FVowbQo();
         }
      }
   }

   public void startWatcher() {
      if (this.watcherStarted.compareAndSet(false, true)) {
         Thread var1 = new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                  BakeCacheWatcher.runWatcherLoop(BakeCacheWatcher.this);
               } finally {
                  if (BakeCacheWatcher.getWatcherThread(BakeCacheWatcher.this) == Thread.currentThread()) {
                     BakeCacheWatcher.setWatcherThread(BakeCacheWatcher.this, null);
                  }
               }
            }
         }, "Jade-LunarBakeProtection");
         var1.setDaemon(true);
         this.watcherThread = var1;
         var1.start();
      }
   }

   public void stopWatcher() {
      this.stopRequested.set(true);
      WatchService var1 = this.watchService;
      if (var1 != null) {
         try {
            var1.close();
         } catch (IOException var5) {
         }
      }

      Thread var2 = this.watcherThread;
      if (var2 != null && var2 != Thread.currentThread()) {
         var2.interrupt();

         try {
            var2.join(2000L);
         } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
         }
      }
   }

   public void cleanupAfterBootstrapFailure(String var1) {
      try {
         this.purgeUnsafeArtifactsAfterFailure(var1 == null ? "failed Jade bootstrap" : var1);
      } finally {
         this.FVowbQo();
      }
   }

   public Path getCacheRoot() {
      return this.path;
   }

   private byte[] getCacheKeyCopy() {
      synchronized (this.zdR1) {
         if (this.genesisHookFingerprintSet && this.genesisHookFingerprint != null && this.pipelineFingerprint != null && this.SAxwA != null && this.cacheBindingSha256 != null && this.cacheEpoch > 0) {
            return Arrays.copyOf(this.SAxwA, this.SAxwA.length);
         } else {
            throw new IllegalStateException(
               "encrypted cache launch binding is incomplete"
            );
         }
      }
   }

   private byte[] computeBindingDigest(Path var1) throws IOException {
      synchronized (this.zdR1) {
         if (this.pipelineFingerprint != null && this.genesisHookFingerprint != null && this.cacheBindingSha256 != null && this.cacheEpoch > 0) {
            ByteArrayOutputStream var3 = new ByteArrayOutputStream(256);
            DataOutputStream var4 = new DataOutputStream(var3);
            writeBindingString(var4, "Jade Genesis whole-cache AEAD v1");
            var4.writeInt(this.cacheEpoch);
            writeBindingString(var4, this.cacheBindingSha256);
            var4.writeInt(this.pipelineFingerprint.length);
            var4.write(this.pipelineFingerprint);
            var4.writeInt(this.genesisHookFingerprint.length);
            var4.write(this.genesisHookFingerprint);
            writeBindingString(var4, this.pAw22(var1));
            var4.close();
            return sha256AndWipe(var3.toByteArray());
         } else {
            throw new IOException("encrypted cache binding is incomplete");
         }
      }
   }

   private static void writeBindingString(DataOutputStream var0, String var1) throws IOException {
      byte[] var2 = var1.getBytes(StandardCharsets.UTF_8);
      var0.writeInt(var2.length);
      var0.write(var2);
      Arrays.fill(var2, (byte)0);
   }

   private String pAw22(Path var1) throws IOException {
      this.requireInsideCacheRoot(var1);
      return this.path.relativize(var1).toString().replace('\\', '/');
   }

   private Path resolveCacheFilePath(File var1, boolean var2) throws IOException {
      if (var1 == null) {
         throw new IOException("cache file is missing");
      } else {
         Path var3 = var1.toPath().toAbsolutePath().normalize();
         this.requireInsideCacheRoot(var3);
         Path var4 = this.path.relativize(var3);
         if (var4.getNameCount() == 3
            && "bake.zip".equals(var3.getFileName().toString())) {
            String var5 = var4.getName(0).toString();
            String var6 = var4.getName(1).toString();
            if (var5.matches("[0-9a-f]{1,8}") && var6.matches("[0-9a-f]{1,8}")) {
               this.xkQ7();
               Path var7 = var3.getParent();
               if (!Files.exists(var7, LinkOption.NOFOLLOW_LINKS)) {
                  throw new IOException(
                     "Lunar bake cache parent does not exist"
                  );
               } else {
                  requireSafeCachePath(var7);
                  if (var2) {
                     requireSafeCachePath(var3);
                  } else if (Files.exists(var3, LinkOption.NOFOLLOW_LINKS)) {
                     requireSafeCachePath(var3);
                  }

                  return var3;
               }
            } else {
               throw new IOException(
                  "unexpected Lunar bake cache hash path: " + var3
               );
            }
         } else {
            throw new IOException(
               "unexpected Lunar bake cache layout: " + var3
            );
         }
      }
   }

   private BakeCacheWatcher$3 acquireLockOrThrow(Path var1) throws IOException {
      BakeCacheWatcher$3 var2 = this.xkwW(var1);
      if (var2 == null) {
         throw new IOException("Lunar bake cache is locked by another process");
      } else {
         return var2;
      }
   }

   private BakeCacheWatcher$3 xkwW(Path var1) throws IOException {
      Path var2 = var1.resolveSibling(".jade-bake-cache.lock");
      this.requireInsideCacheRoot(var2);
      if (Files.exists(var2, LinkOption.NOFOLLOW_LINKS)) {
         requireSafeCachePath(var2);
      }

      FileChannel var3 = FileChannel.open(var2, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

      try {
         requireSafeCachePath(var2);

         FileLock var4;
         try {
            var4 = var3.tryLock();
         } catch (OverlappingFileLockException var7) {
            var4 = null;
         }

         if (var4 == null) {
            var3.close();
            return null;
         } else {
            return new BakeCacheWatcher$3(var3, var4);
         }
      } catch (IOException var8) {
         try {
            var3.close();
         } catch (IOException var6) {
            var8.addSuppressed(var6);
         }

         throw var8;
      }
   }

   private void ENiN17() {
      try {
         this.JEBqSj();
         if (!this.stopRequested.get() && Thread.currentThread().isInterrupted()) {
            Thread.interrupted();
            this.runPollingFallback("Lunar cache watcher interrupted unexpectedly");
         }
      } catch (ClosedWatchServiceException var2) {
      } catch (InterruptedException var3) {
         if (this.stopRequested.get()) {
            Thread.currentThread().interrupt();
         } else {
            this.runPollingFallback("Lunar cache watcher interrupted unexpectedly");
         }
      } catch (IOException var4) {
         this.runPollingFallback("Lunar cache file watcher unavailable");
      } catch (RuntimeException var5) {
         this.runPollingFallback("Lunar cache file watcher failed");
      }
   }

   private void runPollingFallback(String var1) {
      System.out
         .println(
            "[Mod-Agent] "
               + var1
               + "; continuing fail-closed plaintext cache deletion by polling."
         );

      while (!this.stopRequested.get() && !Thread.currentThread().isInterrupted()) {
         try {
            this.recoverCacheRootDirectory();
            this.purgeUnsafeCacheArtifacts();
         } catch (Exception var3) {
         }

         try {
            Thread.sleep(Math.max(this.scanIntervalMillis, 1000L));
         } catch (InterruptedException var4) {
            if (this.stopRequested.get()) {
               Thread.currentThread().interrupt();
            }
         }
      }
   }

   private void JEBqSj() throws IOException, InterruptedException {
      WatchService var1 = this.path.getFileSystem().newWatchService();
      this.watchService = var1;
      HashMap var2 = new HashMap();
      long var3 = 0L;

      try {
         while (!this.stopRequested.get() && !Thread.currentThread().isInterrupted()) {
            if (this.recoverCacheRootDirectory()) {
               clearWatchRegistrations(var2);
            }

            if (var2.isEmpty()) {
               this.xkQ7();
               JqOl(this.path, var1, var2);
               this.purgeUnsafeCacheArtifacts();
               var3 = System.currentTimeMillis() + 1000L;
            }

            if (System.currentTimeMillis() >= var3) {
               this.purgeUnsafeCacheArtifacts();
               var3 = System.currentTimeMillis() + 1000L;
            }

            for (WatchKey var5 = var1.poll(this.scanIntervalMillis, TimeUnit.MILLISECONDS); var5 != null; var5 = var1.poll()) {
               this.handleWatchKeyEvents(var5, var1, var2);
            }
         }
      } finally {
         if (this.watchService == var1) {
            this.watchService = null;
         }

         var1.close();
      }
   }

   private void handleWatchKeyEvents(WatchKey var1, WatchService var2, Map<WatchKey, Path> var3) throws IOException {
      Path var4 = (Path)var3.get(var1);
      if (var4 == null) {
         var1.reset();
      } else {
         boolean var5 = false;

         for (WatchEvent var7 : var1.pollEvents()) {
            if (var7.kind() == StandardWatchEventKinds.OVERFLOW) {
               var5 = true;
            } else {
               Object var8 = var7.context();
               if (!(var8 instanceof Path)) {
                  var5 = true;
               } else {
                  Path var9 = var4.resolve((Path)var8).toAbsolutePath().normalize();
                  if (var7.kind() != StandardWatchEventKinds.ENTRY_DELETE) {
                     if (Files.exists(var9, LinkOption.NOFOLLOW_LINKS)) {
                        try {
                           if (!isSafeCachePath(var9)) {
                              Files.deleteIfExists(var9);
                              var5 = true;
                              continue;
                           }
                        } catch (NoSuchFileException var11) {
                           continue;
                        }
                     }

                     if (var7.kind() == StandardWatchEventKinds.ENTRY_CREATE && Files.isDirectory(var9, LinkOption.NOFOLLOW_LINKS)) {
                        JqOl(var9, var2, var3);
                        var5 = true;
                     } else if (this.kvcyJ11(var9)) {
                        Files.deleteIfExists(var9);
                     }
                  }
               }
            }
         }

         if (!var1.reset()) {
            var3.remove(var1);
         }

         if (var5) {
            this.purgeUnsafeCacheArtifacts();
         }
      }
   }

   private static void JqOl(Path var0, WatchService var1, Map<WatchKey, Path> var2) throws IOException {
      try (Stream var3 = Files.walk(var0)) {
         Iterator var5 = var3.iterator();

         while (var5.hasNext()) {
            Path var6 = (Path)var5.next();
            if (Files.isDirectory(var6, LinkOption.NOFOLLOW_LINKS)) {
               WatchKey var7 = var6.register(
                  var1, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE
               );
               var2.put(var7, normalizeAbsolute(var6));
            }
         }
      }
   }

   private static void clearWatchRegistrations(Map<WatchKey, Path> var0) {
      for (WatchKey var2 : var0.keySet()) {
         var2.cancel();
      }

      var0.clear();
   }

   private int purgeUnsafeCacheArtifacts() throws IOException {
      IOException var1 = null;
      int var2 = 0;

      for (Path var4 : this.collectUnsafeCacheArtifacts()) {
         try {
            if (Files.deleteIfExists(var4)) {
               var2++;
            }
         } catch (IOException var6) {
            if (var1 == null) {
               var1 = var6;
            } else {
               var1.addSuppressed(var6);
            }
         }
      }

      if (var1 != null) {
         throw var1;
      } else {
         return var2;
      }
   }

   private void purgeUnsafeArtifactsAfterFailure(String var1) {
      int var2 = 0;

      try {
         for (Path var4 : this.collectUnsafeCacheArtifacts()) {
            try {
               if (Files.deleteIfExists(var4)) {
                  var2++;
               }
            } catch (IOException var6) {
            }
         }
      } catch (Exception var7) {
      }

      if (var2 != 0) {
         System.out
            .println(
               "[Mod-Agent] Removed "
                  + var2
                  + " unsafe Lunar bake cache artifact(s) after "
                  + var1
                  + "."
            );
      }
   }

   private List<Path> collectUnsafeCacheArtifacts() throws IOException {
      if (!Files.exists(this.path, LinkOption.NOFOLLOW_LINKS)) {
         return Collections.emptyList();
      } else {
         BasicFileAttributes var1 = Files.readAttributes(this.path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
         if (!Files.isSymbolicLink(this.path) && !var1.isOther() && var1.isDirectory()) {
            ArrayList var2 = new ArrayList();

            try (Stream var3 = Files.walk(this.path)) {
               Iterator var5 = var3.iterator();

               while (true) {
                  Path var6;
                  while (true) {
                     if (!var5.hasNext()) {
                        return var2;
                     }

                     var6 = (Path)var5.next();

                     try {
                        if (normalizeAbsolute(var6).equals(this.path) || isSafeCachePath(var6)) {
                           requireSafeCachePath(var6);
                           break;
                        }

                        var2.add(normalizeAbsolute(var6));
                     } catch (NoSuchFileException var16) {
                     }
                  }

                  if (!Files.isDirectory(var6, LinkOption.NOFOLLOW_LINKS) && this.kvcyJ11(var6)) {
                     var2.add(normalizeAbsolute(var6));
                  }
               }
            }
         } else {
            return Collections.singletonList(this.path);
         }
      }
   }

   private boolean kvcyJ11(Path var1) throws IOException {
      String var2 = BrtX(var1).toLowerCase(Locale.ROOT);
      Path var3 = var1.getParent();
      if (var3 == null
         || !normalizeAbsolute(var3).equals(this.path)
         || !".jade-core-cache-id".equals(var2)
            && !".jade-core-cache-id.tmp".equals(var2)) {
         if (var2.startsWith(".jade-bake-cache-")
            && var2.endsWith(".tmp")) {
            if (this.paths.contains(normalizeAbsolute(var1))) {
               return false;
            } else {
               BakeCacheWatcher$3 var4 = this.xkwW(var1);
               if (var4 == null) {
                  return false;
               } else {
                  var4.close();
                  return true;
               }
            }
         } else {
            return !var2.startsWith("bake.")
               ? false
               : !"bake.zip".equals(var2) || !EncryptedBakeCache.hasCacheMagic(var1);
         }
      } else {
         return true;
      }
   }

   private void WGfpC() {
      if (this.shutdownHookRegistered.compareAndSet(false, true)) {
         Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
               BakeCacheWatcher.this.stopWatcher();
               BakeCacheWatcher.purgeUnsafeArtifacts(BakeCacheWatcher.this, "shutdown cleanup");
               BakeCacheWatcher.clearBindingSecrets(BakeCacheWatcher.this);
            }
         }, "Jade-LunarBakeShutdownCleanup"));
      }
   }

   private void FVowbQo() {
      synchronized (this.zdR1) {
         wipeBytes(this.SAxwA);
         wipeBytes(this.pipelineFingerprint);
         wipeBytes(this.genesisHookFingerprint);
         this.SAxwA = null;
         this.pipelineFingerprint = null;
         this.genesisHookFingerprint = null;
         this.cacheBindingSha256 = null;
         this.cacheEpoch = 0;
         this.genesisHookFingerprintSet = false;
      }
   }

   private void xkQ7() throws IOException {
      if (Files.exists(this.path, LinkOption.NOFOLLOW_LINKS)) {
         BasicFileAttributes var1 = Files.readAttributes(this.path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
         if (var1.isDirectory() && !Files.isSymbolicLink(this.path) && !var1.isOther()) {
            try (Stream var2 = Files.walk(this.path)) {
               Iterator var4 = var2.iterator();

               while (var4.hasNext()) {
                  try {
                     requireSafeCachePath((Path)var4.next());
                  } catch (NoSuchFileException var14) {
                  }
               }
            }
         } else {
            throw new IOException(
               "Lunar cache root must be a plain directory: " + this.path
            );
         }
      }
   }

   private boolean recoverCacheRootDirectory() throws IOException {
      boolean var1 = false;
      if (Files.exists(this.path, LinkOption.NOFOLLOW_LINKS)) {
         BasicFileAttributes var2 = Files.readAttributes(this.path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
         if (var2.isDirectory() && !Files.isSymbolicLink(this.path) && !var2.isOther()) {
            return false;
         }

         Files.delete(this.path);
         var1 = true;
      }

      Files.createDirectories(this.path);
      BasicFileAttributes var3 = Files.readAttributes(this.path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
      if (var3.isDirectory() && !Files.isSymbolicLink(this.path) && !var3.isOther()) {
         if (var1) {
            System.out.println("[Mod-Agent] Recovered the Lunar cache root as a plain directory.");
         }

         return true;
      } else {
         throw new IOException(
            "could not recover a plain Lunar cache root: " + this.path
         );
      }
   }

   private static void requireSafeCachePath(Path var0) throws IOException {
      if (!isSafeCachePath(var0)) {
         throw new IOException(
            "linked or special Lunar cache path is not allowed: " + var0
         );
      }
   }

   private static boolean isSafeCachePath(Path var0) throws IOException {
      BasicFileAttributes var1 = Files.readAttributes(var0, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
      return !Files.isSymbolicLink(var0) && !var1.isOther() && (var1.isDirectory() || var1.isRegularFile());
   }

   private void requireInsideCacheRoot(Path var1) throws IOException {
      Path var2 = normalizeAbsolute(var1);
      if (!var2.startsWith(this.path) || var2.equals(this.path)) {
         throw new IOException(
            "cache path escapes Lunar cache root: " + var2
         );
      }
   }

   private static ZipException RDcCynN(String var0, Throwable var1) {
      ZipException var2 = new ZipException(var0);
      var2.initCause(var1);
      return var2;
   }

   private static byte[] sha256AndWipe(byte[] var0) throws IOException {
      byte[] var1;
      try {
         var1 = MessageDigest.getInstance("SHA-256").digest(var0);
      } catch (NoSuchAlgorithmException var5) {
         throw new IOException("SHA-256 unavailable", var5);
      } finally {
         wipeBytes(var0);
      }

      return var1;
   }

   private static String BrtX(Path var0) {
      Path var1 = var0.getFileName();
      return var1 == null ? "" : var1.toString();
   }

   private static Path normalizeAbsolute(Path var0) {
      return var0.toAbsolutePath().normalize();
   }

   private static void wipeBytes(byte[] var0) {
      if (var0 != null) {
         Arrays.fill(var0, (byte)0);
      }
   }

   public static void clearBindingSecrets(BakeCacheWatcher var0) {
      var0.FVowbQo();
   }

   public static void runWatcherLoop(BakeCacheWatcher var0) {
      var0.ENiN17();
   }

   public static Thread getWatcherThread(BakeCacheWatcher var0) {
      return var0.watcherThread;
   }

   public static Thread setWatcherThread(BakeCacheWatcher var0, Thread var1) {
      return var0.watcherThread = var1;
   }

   public static void purgeUnsafeArtifacts(BakeCacheWatcher var0, String var1) {
      var0.purgeUnsafeArtifactsAfterFailure(var1);
   }
}
