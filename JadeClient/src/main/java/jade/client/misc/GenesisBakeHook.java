// Jade recovery: original class: jade.deps.eLz.IEdHCC
package jade.client.misc;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.ProtectionDomain;
import java.util.concurrent.atomic.AtomicBoolean;
import net.jade.dev.agent.AgentBootstrap;

public final class GenesisBakeHook implements ClassFileTransformer {
   private static final String tteS = "com/moonsworth/lunar/genesis/";
   private static final String GENESIS_BAKER_MARKER = "Genesis/Baker";
   private static final String CACHE_SAVING_STATUS_MARKER = "LUNARCLIENT_STATUS_SAVING_CACHE";
   private static final String BAKED_CLASSES_STATUS_MARKER = "Loading baked classes...";
   private static final String JjYj = "java/util/zip/ZipFile";
   private static final String Kb5 = "(Ljava/io/File;)V";
   private static final String OPEN_CACHE_DESCRIPTOR = "(Ljava/io/File;Ljava/lang/ClassLoader;)Ljava/util/zip/ZipFile;";
   private static final String WRITE_CACHE_DESCRIPTOR = "(Ljava/util/TreeMap;Ljava/io/File;)V";
   private static final String BRIDGE_CLASS_NAME = "net.jade.dev.agent.cache.GenesisBakeCacheBridge";
   private static final String htN = "jade$cacheOpen";
   private static final String CACHE_WRITE_METHOD = "jade$cacheWrite";
   private static final String OPEN_CACHE_METHOD = "jade$openCache";
   private static final String tG2 = "jade$writeCache";
   private final AtomicBoolean hooksInstalled = new AtomicBoolean(false);
   private final AtomicBoolean bakeCandidateFound = new AtomicBoolean(false);

   @Override
   public byte[] transform(ClassLoader var1, String var2, Class<?> var3, ProtectionDomain var4, byte[] var5) throws IllegalClassFormatException {
      if (var3 != null
         || var2 == null
         || var5 == null
         || !var2.startsWith("com/moonsworth/lunar/genesis/")
         || !containsUtf8Marker(var5, "Genesis/Baker")
         || !containsUtf8Marker(var5, "LUNARCLIENT_STATUS_SAVING_CACHE")) {
         return null;
      } else if (!this.bakeCandidateFound.compareAndSet(false, true)) {
         AgentBootstrap.onGenesisBakeCacheHookFailure(
            "multiple Genesis Baker cache candidates were discovered"
         );
         return null;
      } else {
         try {
            ClassReader var6 = new ClassReader(var5);
            ClassWriter var7 = new ClassWriter(var6, 1);
            GenesisBakeHook$2 var8 = new GenesisBakeHook$2(var7);
            var6.accept(var8, 0);
            String var9 = var8.failureReason();
            if (var9 != null) {
               AgentBootstrap.onGenesisBakeCacheHookFailure(var9);
               return null;
            } else if (!this.hooksInstalled.compareAndSet(false, true)) {
               AgentBootstrap.onGenesisBakeCacheHookFailure(
                  "Genesis Baker cache hook installed more than once"
               );
               return null;
            } else {
               AgentBootstrap.onGenesisBakeCacheHooksInstalled(uhbG7(var5));
               return var7.toByteArray();
            }
         } catch (RuntimeException var10) {
            AgentBootstrap.onGenesisBakeCacheHookFailure(
               "unable to inspect installed Genesis Baker: "
                  + var10.getClass().getSimpleName()
            );
            return null;
         }
      }
   }

   private static boolean containsUtf8Marker(byte[] var0, String var1) {
      byte[] var2 = var1.getBytes(StandardCharsets.UTF_8);

      label24:
      for (int var3 = 0; var3 <= var0.length - var2.length; var3++) {
         for (int var4 = 0; var4 < var2.length; var4++) {
            if (var0[var3 + var4] != var2[var4]) {
               continue label24;
            }
         }

         return true;
      }

      return false;
   }

   private static byte[] uhbG7(byte[] var0) {
      try {
         return MessageDigest.getInstance("SHA-256").digest(var0);
      } catch (NoSuchAlgorithmException var2) {
         throw new IllegalStateException("SHA-256 unavailable", var2);
      }
   }
}
