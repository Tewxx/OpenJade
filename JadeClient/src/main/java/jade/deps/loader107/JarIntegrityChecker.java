package jade.deps.loader107;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

public final class JarIntegrityChecker {
   private static volatile String cachedJarHash;
   private static volatile boolean hashComputed;

   private JarIntegrityChecker() {
   }

   public static String getJarHash() {
      if (hashComputed) {
         return cachedJarHash;
      } else {
         synchronized (JarIntegrityChecker.class) {
            if (hashComputed) {
               return cachedJarHash;
            } else {
               cachedJarHash = computeJarHash();
               hashComputed = true;
               return cachedJarHash;
            }
         }
      }
   }

   public static boolean isJarAvailable() {

      try {
         return locateJarUrl() != null;
      } catch (Exception var1) {
         return false;
      }
   }

   private static String computeJarHash() {

      try {
         URL var0 = locateJarUrl();
         if (var0 == null) {
            return null;
         } else {
            URLConnection var1 = var0.openConnection();
            var1.setUseCaches(false);
            InputStream var2 = var1.getInputStream();

            String var19;
            try {
               MessageDigest var3 = MessageDigest.getInstance("SHA-256");
               byte[] var4 = new byte[16384];

               int var5;
               while ((var5 = var2.read(var4)) > 0) {
                  var3.update(var4, 0, var5);
               }

               byte[] var6 = var3.digest();
               StringBuilder var7 = new StringBuilder(var6.length * 2);

               for (int var8 = 0; var8 < var6.length; var8++) {
                  var7.append(String.format("%02x", var6[var8] & 255));
               }

               var19 = var7.toString();
            } finally {
               try {
                  var2.close();
               } catch (Exception var16) {
               }
            }

            return var19;
         }
      } catch (Exception var18) {
         return null;
      }
   }

   private static URL locateJarUrl() throws Exception {

      try {
         URL var0 = JarIntegrityChecker.class.getProtectionDomain().getCodeSource().getLocation();
         if (isLoadableJarUrl(var0)) {
            return var0;
         }
      } catch (Exception var6) {
      }

      URL var8 = JarIntegrityChecker.class
         .getResource("JarIntegrityChecker.class");
      if (var8 != null && "jar".equals(var8.getProtocol())) {
         URLConnection var1 = var8.openConnection();
         if (var1 instanceof JarURLConnection) {
            JarURLConnection var2 = (JarURLConnection)var1;
            var2.setUseCaches(false);
            URL var3 = var2.getJarFileURL();
            if (isLoadableJarUrl(var3)) {
               return var3;
            }
         }
      }

      for (ClassLoader var9 = JarIntegrityChecker.class.getClassLoader(); var9 != null; var9 = var9.getParent()) {
         try {
            Method var10 = var9.getClass().getMethod("getSources");
            Object var11 = var10.invoke(var9);
            if (var11 instanceof Iterable) {
               for (Object var5 : (Iterable)var11) {
                  if (var5 instanceof URL && isJadeJarUrl((URL)var5)) {
                     return (URL)var5;
                  }
               }
            }
         } catch (Exception var7) {
         }
      }

      return null;
   }

   private static boolean isLoadableJarUrl(URL var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return var0 != null
                  && (
                     "jade-loader-memory".equals(var0.getProtocol())
                        || "file".equals(var0.getProtocol())
                           && var0.getPath() != null
                           && var0.getPath().endsWith(".jar")
                  );
         }
      }
   }

   private static boolean isJadeJarUrl(URL var0) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               if (!isLoadableJarUrl(var0)) {
                  return false;
               } else {
                  String var1 = var0.getPath().replace('\\', '/');
                  return var1.endsWith("/jade.jar")
                     || "jade.jar".equals(var1);
               }
         }
      }
   }
}
