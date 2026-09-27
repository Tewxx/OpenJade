package jade.deps.loader107;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.List;

public final class JvmAgentDetector {
   public static final int FLAG_JDWP_AGENTLIB = 1;
   public static final int FLAG_XDEBUG = 2;
   public static final int FLAG_OWN_JAVAAGENT = 4;
   public static final int FLAG_FOREIGN_JAVAAGENT = 8;
   public static final int FLAG_SCAN_FAILED = 16;
   public static final int FLAG_DENIED_MASK = 19;
   private static volatile int cachedFlags = -1;

   private JvmAgentDetector() {
   }

   public static int getRuntimeFlags() {
      int var0 = cachedFlags;
      if (var0 >= 0) {
         return var0;
      } else {
         synchronized (JvmAgentDetector.class) {
            if (cachedFlags >= 0) {
               return cachedFlags;
            } else {
               cachedFlags = detectRuntimeFlags();
               return cachedFlags;
            }
         }
      }
   }

   public static boolean hasDeniedRuntimeFlags() {
      byte var0 = 0;

      while (true) {
         switch (var0) {
            case 0:

               var0 = 1;
               break;
            case 1:
               var0 = 2;
               break;
            default:
               return (getRuntimeFlags() & 19) != 0;
         }
      }
   }

   public static void enforceCleanRuntime() {
      byte var0 = 0;

      while (true) {
         switch (var0) {
            case 0:

               var0 = 1;
               break;
            case 1:
               var0 = 2;
               break;
            default:
               if (hasDeniedRuntimeFlags()) {
                  throw new SecurityException("runtime_environment_denied");
               } else {
                  return;
               }
         }
      }
   }

   private static int detectRuntimeFlags() {

      try {
         List var0 = ManagementFactory.getRuntimeMXBean().getInputArguments();
         return var0 == null ? 16 : scanJvmArguments(var0, getOwnCodeSourceFile());
      } catch (Throwable var1) {
         return 16;
      }
   }

   static int scanJvmArguments(List<String> var0, File var1) {
      byte var2 = 0;

      try {
         for (String var4 : var0) {
            if (var4 != null) {
               String var5 = var4.trim();
               if (var5.startsWith("-agentlib:jdwp")) {
                  var2 |= 1;
               } else if (!var5.startsWith("-Xdebug")
                  && !var5.startsWith("-Xrunjdwp")) {
                  if (var5.startsWith("-javaagent:")) {
                     File var6 = parseAgentJarPath(var5);
                     if (var1 != null && var6 != null && isSameFile(var1, var6)) {
                        var2 |= 4;
                     } else {
                        var2 |= 8;
                     }
                  }
               } else {
                  var2 |= 2;
               }
            }
         }

         return var2;
      } catch (Throwable var7) {
         return 16;
      }
   }

   private static File getOwnCodeSourceFile() {

      try {
         if (JvmAgentDetector.class.getProtectionDomain() != null
            && JvmAgentDetector.class.getProtectionDomain().getCodeSource() != null) {
            URL var0 = JvmAgentDetector.class.getProtectionDomain().getCodeSource().getLocation();
            return var0 != null && "file".equalsIgnoreCase(var0.getProtocol())
               ? new File(var0.toURI()).getCanonicalFile()
               : null;
         } else {
            return null;
         }
      } catch (Exception var1) {
         return null;
      }
   }

   private static File parseAgentJarPath(String var0) {

      try {
         String var1 = var0.substring("-javaagent:".length());
         int var2 = var1.indexOf(61);
         if (var2 >= 0) {
            var1 = var1.substring(0, var2);
         }

         if (var1.length() >= 2 && var1.charAt(0) == '"' && var1.charAt(var1.length() - 1) == '"') {
            var1 = var1.substring(1, var1.length() - 1);
         }

         return new File(var1).getCanonicalFile();
      } catch (Exception var3) {
         return null;
      }
   }

   private static boolean isSameFile(File var0, File var1) {

      try {
         return var0.getCanonicalFile().equals(var1.getCanonicalFile());
      } catch (Exception var3) {
         return false;
      }
   }

   static {
   }
}
