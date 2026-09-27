// Jade recovery: original class: jade.deps.eLz.WzuK4ei
package jade.client.hook;

import jade.deps.asm.ClassReader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class EncryptedBakeCache {
   public static final byte[] ptY = new byte[]{74, 68, 69, 66, 65, 75, 69, 49};
   private static final int GCM_IV_LENGTH = 12;
   private static final int CSde = 128;
   private static final int Jfeh1 = 1245856561;
   private static final int cyk3 = 100000;
   private static final int MAX_ENTRY_NAME_BYTES = 4096;
   private static final int MAX_ENTRY_BYTES = 33554432;
   private static final long pzfCfh = 536870912L;
   private static final long MAX_CIPHERTEXT_BYTES = 134217728L;
   private static final long MAX_CACHE_FILE_BYTES = ptY.length + 12 + 134217728L;
   private static final SecureRandom secureRandom = new SecureRandom();

   private EncryptedBakeCache() {
   }

   public static boolean hasCacheMagic(Path var0) {
      if (var0 != null && Files.isRegularFile(var0)) {
         try {
            if (Files.size(var0) < ptY.length + 12 + 16L) {
               return false;
            } else {
               byte[] var1 = new byte[ptY.length];
               DataInputStream var2 = new DataInputStream(Files.newInputStream(var0));

               boolean var3;
               try {
                  var2.readFully(var1);
                  var3 = Arrays.equals(var1, ptY);
               } finally {
                  var2.close();
                  Arrays.fill(var1, (byte)0);
               }

               return var3;
            }
         } catch (IOException var8) {
            return false;
         }
      } else {
         return false;
      }
   }

   public static LinkedHashMap<String, byte[]> readEncryptedEntries(Path var0, byte[] var1, byte[] var2) throws IOException {
      validateKey(var1);
      long var3 = Files.size(var0);
      if (var3 >= ptY.length + 12 + 16L && var3 <= MAX_CACHE_FILE_BYTES) {
         byte[] var5 = new byte[12];
         byte[] var6 = null;
         byte[] var7 = null;
         DataInputStream var8 = new DataInputStream(new BufferedInputStream(Files.newInputStream(var0), 65536));

         LinkedHashMap var13;
         try {
            byte[] var9 = new byte[ptY.length];
            var8.readFully(var9);

            for (int var10 = 0; var10 < ptY.length; var10++) {
               if (var9[var10] != ptY[var10]) {
                  throw new IOException("not a Jade encrypted bake cache");
               }
            }

            Arrays.fill(var9, (byte)0);
            var8.readFully(var5);
            int var33 = (int)(var3 - ptY.length - 12L);
            var6 = new byte[var33];
            var8.readFully(var6);
            if (var8.read() != -1) {
               throw new IOException(
                  "encrypted bake cache changed while being read"
               );
            }

            try {
               Cipher var11 = createCipher(2, var1, var5, var2);
               var7 = var11.doFinal(var6);
            } catch (GeneralSecurityException var31) {
               throw new IOException(
                  "encrypted bake cache initialization failed", var31
               );
            }

            Inflater var34 = new Inflater(false);

            try {
               DataInputStream var12 = new DataInputStream(new InflaterInputStream(new ByteArrayInputStream(var7), var34, 65536));

               try {
                  var13 = decodePayload(var12);
               } finally {
                  var12.close();
               }
            } finally {
               var34.end();
            }
         } finally {
            DYNu(var5);
            DYNu(var6);
            DYNu(var7);
            var8.close();
         }

         return var13;
      } else {
         throw new IOException(
            "encrypted bake cache has an invalid size"
         );
      }
   }

   public static void writeEncryptedEntries(Path var0, TreeMap<String, byte[]> var1, byte[] var2, byte[] var3) throws IOException {
      validateKey(var2);
      EncryptedBakeCache$0 var4 = validateEntriesForEncoding(var1);
      byte[] var5 = new byte[12];
      secureRandom.nextBytes(var5);
      OutputStream var6 = Files.newOutputStream(var0, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
      boolean var7 = false;

      try {
         BufferedOutputStream var8 = new BufferedOutputStream(var6, 65536);
         var8.write(ptY);
         var8.write(var5);
         Cipher var9 = createCipher(1, var2, var5, var3);
         CipherOutputStream var10 = new CipherOutputStream(var8, var9);
         Deflater var11 = new Deflater(1, false);
         DataOutputStream var12 = new DataOutputStream(new DeflaterOutputStream(var10, var11, 65536));

         try {
            var12.writeInt(1245856561);
            var12.writeInt(var4.treeMap.size());
            var12.writeLong(var4.woqE);

            for (Entry var14 : var4.treeMap.entrySet()) {
               byte[] var15 = ((String)var14.getKey()).getBytes(StandardCharsets.UTF_8);
               var12.writeInt(var15.length);
               var12.writeInt(((byte[])var14.getValue()).length);
               var12.write(var15);
               var12.write((byte[])var14.getValue());
               Arrays.fill(var15, (byte)0);
            }
         } finally {
            var12.close();
            var11.end();
         }

         if (Files.size(var0) > MAX_CACHE_FILE_BYTES) {
            throw new IOException(
               "encrypted Lunar bake cache exceeds the size limit"
            );
         }

         var7 = true;
      } catch (GeneralSecurityException var30) {
         throw new IOException("cannot encrypt Lunar bake cache", var30);
      } finally {
         DYNu(var5);
         if (!var7) {
            try {
               var6.close();
            } catch (IOException var28) {
            }

            Files.deleteIfExists(var0);
         }
      }
   }

   private static LinkedHashMap<String, byte[]> decodePayload(DataInputStream var0) throws IOException {
      LinkedHashMap var1 = new LinkedHashMap();
      LinkedHashSet var2 = new LinkedHashSet();
      long var3 = 0L;

      try {
         if (var0.readInt() != 1245856561) {
            throw new IOException(
               "encrypted bake cache payload version mismatch"
            );
         } else {
            int var5 = var0.readInt();
            long var6 = var0.readLong();
            if (var5 > 0 && var5 <= 100000 && var6 > 0L && var6 <= 536870912L) {
               int var8 = 0;

               while (var8 < var5) {
                  int var9 = var0.readInt();
                  int var10 = var0.readInt();
                  if (var9 > 0 && var9 <= 4096 && var10 >= 8 && var10 <= 33554432 && var3 + var10 <= 536870912L) {
                     byte[] var11 = new byte[var9];
                     var0.readFully(var11);
                     String var12 = new String(var11, StandardCharsets.UTF_8);
                     if (!Arrays.equals(var11, var12.getBytes(StandardCharsets.UTF_8))) {
                        throw new IOException(
                           "encrypted bake cache entry name is not canonical UTF-8"
                        );
                     }

                     Arrays.fill(var11, (byte)0);
                     byte[] var13 = new byte[var10];
                     var0.readFully(var13);

                     String var14;
                     try {
                        var14 = resolveClassIdentity(var12, var13);
                     } catch (IOException var16) {
                        Arrays.fill(var13, (byte)0);
                        throw var16;
                     }

                     if (var1.put(var12, var13) == null && var2.add(var14)) {
                        var3 += var10;
                        var8++;
                        continue;
                     }

                     Arrays.fill(var13, (byte)0);
                     throw new IOException(
                        "duplicate encrypted bake cache class: " + var12
                     );
                  }

                  throw new IOException(
                     "encrypted bake cache entry bounds rejected"
                  );
               }

               if (var3 == var6 && var0.read() == -1) {
                  return var1;
               } else {
                  throw new IOException(
                     "encrypted bake cache payload length mismatch"
                  );
               }
            } else {
               throw new IOException(
                  "encrypted bake cache payload bounds rejected"
               );
            }
         }
      } catch (EOFException var17) {
         wipeEntries(var1);
         throw new IOException(
            "encrypted bake cache payload is truncated", var17
         );
      } catch (IOException var18) {
         wipeEntries(var1);
         throw var18;
      } catch (RuntimeException var19) {
         wipeEntries(var1);
         throw var19;
      }
   }

   private static EncryptedBakeCache$0 validateEntriesForEncoding(TreeMap<String, byte[]> var0) throws IOException {
      if (var0 != null && !var0.isEmpty() && var0.size() <= 100000) {
         long var1 = 0L;
         LinkedHashSet var3 = new LinkedHashSet();

         for (Entry var5 : var0.entrySet()) {
            String var6 = (String)var5.getKey();
            byte[] var7 = (byte[])var5.getValue();
            if (var6 != null && var7 != null && var7.length >= 8 && var7.length <= 33554432) {
               byte[] var8 = var6.getBytes(StandardCharsets.UTF_8);
               if (var8.length != 0 && var8.length <= 4096) {
                  Arrays.fill(var8, (byte)0);
                  String var9 = resolveClassIdentity(var6, var7);
                  if (!var3.add(var9)) {
                     throw new IOException(
                        "Genesis supplied duplicate class identities"
                     );
                  }

                  var1 += var7.length;
                  if (var1 > 536870912L) {
                     throw new IOException(
                        "Genesis bake cache exceeds the initialized size limit"
                     );
                  }
                  continue;
               }

               throw new IOException(
                  "Genesis supplied an invalid bake cache entry name"
               );
            }

            throw new IOException(
               "Genesis supplied an invalid bake cache entry"
            );
         }

         return new EncryptedBakeCache$0(var0, var1);
      } else {
         throw new IOException(
            "Genesis supplied an invalid bake cache entry count"
         );
      }
   }

   public static String resolveClassIdentity(String var0, byte[] var1) throws IOException {
      if (var0 != null
         && var0.length() != 0
         && var0.indexOf(0) < 0
         && var0.indexOf(92) < 0
         && !var0.startsWith("/")
         && !var0.contains("../")
         && !var0.contains("/../")
         && !var0.endsWith("/..")) {
         if (var1 != null && var1.length >= 8 && var1[0] == -54 && var1[1] == -2 && var1[2] == -70 && var1[3] == -66) {
            String var2;
            try {
               var2 = new ClassReader(var1).getClassName();
            } catch (RuntimeException var4) {
               throw new IOException(
                  "invalid class bytes in encrypted bake cache", var4
               );
            }

            String var3 = var0;
            if (var0.endsWith(".class")) {
               var3 = var0.substring(0, var0.length() - 6);
            }

            var3 = var3.replace('.', '/');

            while (var3.startsWith("/")) {
               var3 = var3.substring(1);
            }

            if (!var2.equals(var3)) {
               throw new IOException(
                  "cache entry/class identity mismatch: " + var0
               );
            } else {
               return var2;
            }
         } else {
            throw new IOException(
               "encrypted bake cache entry is not a class: " + var0
            );
         }
      } else {
         throw new IOException("unsafe encrypted bake cache entry name");
      }
   }

   private static Cipher createCipher(int var0, byte[] var1, byte[] var2, byte[] var3) throws GeneralSecurityException {
      Cipher var4 = Cipher.getInstance("AES/GCM/NoPadding");
      var4.init(var0, new SecretKeySpec(var1, "AES"), new GCMParameterSpec(128, var2));
      var4.updateAAD(var3);
      return var4;
   }

   private static void validateKey(byte[] var0) {
      if (var0 == null || var0.length != 32) {
         throw new IllegalArgumentException(
            "AES-256 bake cache key required"
         );
      }
   }

   public static void wipeEntries(Map<String, byte[]> var0) {
      if (var0 != null) {
         for (byte[] var2 : var0.values()) {
            DYNu(var2);
         }

         var0.clear();
      }
   }

   private static void DYNu(byte[] var0) {
      if (var0 != null) {
         Arrays.fill(var0, (byte)0);
      }
   }
}
