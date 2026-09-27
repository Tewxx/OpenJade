// Jade recovery: original class: jade.deps.eLz.nwokc3X3E
package jade.client.hook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public final class InMemoryBakeZip extends ZipFile {
   private static final byte[] EMPTY_ZIP_RECORD = new byte[]{80, 75, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
   private static final File EMPTY_ZIP_FILE = createEmptyZipFile();
   private final LinkedHashMap<String, byte[]> rawEntries;
   private final Set<String> openedEntryNames = new HashSet<>();
   private final Set<String> transferredEntryNames = new HashSet<>();
   private final AtomicBoolean closedFlag = new AtomicBoolean(false);
   private final String name;
   private final InMemoryBakeZip$0 closeCallback;

   public InMemoryBakeZip(File var1, LinkedHashMap<String, byte[]> var2, InMemoryBakeZip$0 var3) throws IOException {
      super(EMPTY_ZIP_FILE);
      if (var1 != null && var2 != null && var3 != null) {
         this.name = var1.getAbsolutePath();
         this.rawEntries = var2;
         this.closeCallback = var3;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public synchronized Map<String, byte[]> entriesByInternalName() throws IOException {
      this.ensureOpenForRead();
      LinkedHashMap var1 = new LinkedHashMap();

      for (Entry var3 : this.rawEntries.entrySet()) {
         String var4 = EncryptedBakeCache.resolveClassIdentity((String)var3.getKey(), (byte[])var3.getValue());
         var1.put(var4, var3.getValue());
      }

      return var1;
   }

   @Override
   public synchronized Enumeration<? extends ZipEntry> entries() {
      this.ensureOpenForListing();
      return Collections.enumeration(this.buildZipEntryList());
   }

   @Override
   public synchronized Stream<? extends ZipEntry> stream() {
      this.ensureOpenForListing();
      return this.buildZipEntryList().stream();
   }

   @Override
   public synchronized ZipEntry getEntry(String var1) {
      this.ensureOpenForListing();
      byte[] var2 = this.rawEntries.get(var1);
      return var2 == null ? null : createZipEntry(var1, var2.length);
   }

   @Override
   public synchronized InputStream getInputStream(ZipEntry var1) throws IOException {
      this.ensureOpenForRead();
      if (var1 == null) {
         throw new NullPointerException("entry");
      } else {
         String var2 = var1.getName();
         byte[] var3 = this.rawEntries.get(var2);
         if (var3 == null) {
            throw new IOException("unknown in-memory cache entry");
         } else if (!this.openedEntryNames.add(var2)) {
            throw new IOException("cache entry requested more than once");
         } else {
            return new InMemoryBakeZip$1(this, var2, var3);
         }
      }
   }

   @Override
   public synchronized int size() {
      this.ensureOpenForListing();
      return this.rawEntries.size();
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public synchronized void close() throws IOException {
      if (this.closedFlag.compareAndSet(false, true)) {
         boolean var1 = false;
         Throwable var2 = null;

         try {
            if (this.openedEntryNames.size() != this.rawEntries.size() || this.transferredEntryNames.size() != this.rawEntries.size()) {
               throw new ZipException(
                  "Genesis did not consume the complete cache"
               );
            }

            this.closeCallback.onCacheConsumed(this.collectClassEntries());
            var1 = true;
            this.rawEntries.clear();
         } catch (Throwable var15) {
            var2 = var15;

            try {
               this.closeCallback.onCacheAborted();
            } catch (Throwable var13) {
               var15.addSuppressed(var13);
            }
         } finally {
            if (!var1) {
               EncryptedBakeCache.wipeEntries(this.rawEntries);
            }

            try {
               super.close();
            } catch (IOException var14) {
               if (var2 != null) {
                  var2.addSuppressed(var14);
               }
            }
         }

         if (var2 instanceof IOException) {
            throw (IOException)var2;
         } else if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         } else if (var2 instanceof Error) {
            throw (Error)var2;
         } else if (var2 != null) {
            throw new IOException(
               "warm-cache close verification failed", var2
            );
         }
      }
   }

   private Map<String, byte[]> collectClassEntries() throws IOException {
      LinkedHashMap var1 = new LinkedHashMap();

      for (Entry var3 : this.rawEntries.entrySet()) {
         String var4 = EncryptedBakeCache.resolveClassIdentity((String)var3.getKey(), (byte[])var3.getValue());
         var1.put(var4, var3.getValue());
      }

      return var1;
   }

   private List<ZipEntry> buildZipEntryList() {
      ArrayList var1 = new ArrayList(this.rawEntries.size());

      for (Entry var3 : this.rawEntries.entrySet()) {
         var1.add(createZipEntry((String)var3.getKey(), ((byte[])var3.getValue()).length));
      }

      return var1;
   }

   private static ZipEntry createZipEntry(String var0, int var1) {
      ZipEntry var2 = new ZipEntry(var0);
      var2.setSize(var1);
      var2.setCompressedSize(var1);
      var2.setTime(0L);
      return var2;
   }

   private void ensureOpenForRead() throws IOException {
      if (this.closedFlag.get()) {
         throw new IOException("in-memory bake cache is closed");
      }
   }

   private void ensureOpenForListing() {
      if (this.closedFlag.get()) {
         throw new IllegalStateException("in-memory bake cache is closed");
      }
   }

   private static File createEmptyZipFile() {
      try {
         Path var0 = Files.createTempFile(
            "jade-empty-bake-",
            ".zip"
         );
         Files.write(var0, EMPTY_ZIP_RECORD, StandardOpenOption.TRUNCATE_EXISTING);
         File var1 = var0.toFile();
         var1.deleteOnExit();
         return var1;
      } catch (IOException var2) {
         throw new ExceptionInInitializerError(var2);
      }
   }

   public static Set getTransferredEntryNames(InMemoryBakeZip var0) {
      return var0.transferredEntryNames;
   }
}
