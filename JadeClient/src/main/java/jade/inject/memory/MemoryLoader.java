package jade.inject.memory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.security.SecureClassLoader;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class MemoryLoader extends SecureClassLoader {
   private final Map<String, byte[]> entries = new HashMap<>();
   private final byte[] image;
   private final URL origin;

   public MemoryLoader(byte[] var1, ClassLoader var2) throws IOException {
      super(var2);
      if (var1 != null && var1.length != 0 && var1.length <= 16777216) {
         this.image = (byte[])var1.clone();
         boolean var3 = false;
         byte[] var4 = new byte[8192];

         try {
            ZipInputStream var5 = new ZipInputStream(new ByteArrayInputStream(this.image));

            try {
               HashSet var6 = new HashSet();
               long var7 = 0L;

               ZipEntry var9;
               while ((var9 = var5.getNextEntry()) != null) {
                  String var10 = var9.getName();
                  if (!var6.add(var10)
                     || var6.size() > 16384
                     || var10.startsWith("/")
                     || var10.indexOf(92) >= 0
                     || var10.indexOf(0) >= 0
                     || var10.equals("..")
                     || var10.contains("../")) {
                     throw new IOException("invalid loader archive entry");
                  }

                  if (var9.isDirectory()) {
                     if (var5.read() != -1) {
                        throw new IOException("loader directory contains data");
                     }
                  } else {
                     ByteArrayOutputStream var11 = new ByteArrayOutputStream();

                     int var12;
                     while ((var12 = var5.read(var4)) != -1) {
                        if (var12 > 33554432 - var11.size() || var12 > 134217728L - var7) {
                           throw new IOException("loader archive expansion exceeds budget");
                        }

                        var11.write(var4, 0, var12);
                        var7 += var12;
                     }

                     this.entries.put(var10, var11.toByteArray());
                  }
               }

               if (!this.entries.containsKey("jade/inject/InjectionAgent.class")
                  || !this.entries.containsKey("META-INF/jade-loader-protocol")
                  || !this.entries.containsKey("META-INF/jade-build-id")) {
                  throw new IOException("loader bootstrap entries missing");
               }

               if (!"107".equals(new String(this.entries.get("META-INF/jade-loader-protocol"), StandardCharsets.US_ASCII).trim())) {
                  throw new IOException("loader protocol mismatch");
               }

               var3 = true;
            } catch (Throwable var18) {
               try {
                  var5.close();
               } catch (Throwable var17) {
                  var18.addSuppressed(var17);
               }

               throw var18;
            }

            var5.close();
         } finally {
            Arrays.fill(var4, (byte)0);
            if (!var3) {
               this.destroy();
            }
         }

         this.origin = this.resourceUrl("", this.image);
      } else {
         throw new IOException("invalid loader image size");
      }
   }

   @Override
   protected Class<?> loadClass(String var1, boolean var2) throws ClassNotFoundException {
      synchronized (this.getClassLoadingLock(var1)) {
         Class var4 = this.findLoadedClass(var1);
         if (var4 == null
            && !var1.startsWith("java.")
            && !var1.startsWith("javax.")
            && !var1.startsWith("sun.")
            && !var1.startsWith("jdk.")
            && !var1.startsWith("jade.inject.memory.")
            && this.entries.containsKey(var1.replace('.', '/') + ".class")) {
            var4 = this.findClass(var1);
         }

         if (var4 == null) {
            var4 = super.loadClass(var1, false);
         }

         if (var2) {
            this.resolveClass(var4);
         }

         return var4;
      }
   }

   @Override
   protected Class<?> findClass(String var1) throws ClassNotFoundException {
      byte[] var2 = this.entries.get(var1.replace('.', '/') + ".class");
      if (var2 == null) {
         throw new ClassNotFoundException(var1);
      } else {
         return this.defineClass(var1, var2, 0, var2.length, new CodeSource(this.origin, (Certificate[])null));
      }
   }

   @Override
   public URL getResource(String var1) {
      URL var2 = this.findResource(var1);
      return var2 != null ? var2 : super.getResource(var1);
   }

   @Override
   public Enumeration<URL> getResources(String var1) throws IOException {
      URL var2 = this.findResource(var1);
      return var2 != null ? Collections.enumeration(Collections.singletonList(var2)) : super.getResources(var1);
   }

   @Override
   protected URL findResource(String var1) {
      byte[] var2 = this.entries.get(var1);
      if (var2 == null) {
         return null;
      } else {
         try {
            return this.resourceUrl(var1, var2);
         } catch (IOException var4) {
            throw new IllegalStateException(var4);
         }
      }
   }

   private URL resourceUrl(String var1, final byte[] var2) throws IOException {
      return new URL(null, "jade-loader-memory:/" + var1, new URLStreamHandler() {
         @Override
         protected URLConnection openConnection(URL var1) {
            return new URLConnection(var1) {
               @Override
               public void connect() {
                  this.connected = true;
               }

               @Override
               public InputStream getInputStream() {
                  return new ByteArrayInputStream(var2);
               }

               @Override
               public int getContentLength() {
                  return var2.length;
               }

               @Override
               public boolean getUseCaches() {
                  return false;
               }
            };
         }
      });
   }

   public void destroy() {
      Arrays.fill(this.image, (byte)0);

      for (byte[] var2 : this.entries.values()) {
         Arrays.fill(var2, (byte)0);
      }

      this.entries.clear();
   }
}
