package jade.inject.memory;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Arrays;

public final class MemoryBootstrap {
   private static MemoryLoader loader;
   private static byte[] identity;
   private static Class<?> agent;

   public static synchronized void start(byte[] var0, String var1, String var2, String var3, String var4) throws Exception {
      byte[] var5 = MessageDigest.getInstance("SHA-256").digest(var0);

      try {
         if (identity != null && !MessageDigest.isEqual(identity, var5)) {
            throw new SecurityException("loader changed; restart Minecraft");
         }

         if (loader == null) {
            MemoryLoader var6 = new MemoryLoader(var0, MemoryBootstrap.class.getClassLoader());

            try {
               agent = Class.forName("jade.inject.InjectionAgent", false, var6);
               loader = var6;
               identity = (byte[])var5.clone();
            } catch (Throwable var11) {
               var6.destroy();
               throw var11;
            }
         }

         Class var13 = Class.forName("jade.inject.InjectionBootstrapContext", true, loader);
         Method var7 = var13.getMethod("importMemoryState", String.class, String.class, String.class, String.class);
         var7.invoke(null, var1, var2, var3, var4);
         agent.getMethod("agentmain", String.class, Instrumentation.class).invoke(null, var1, MemoryInstrumentation.instance());
      } finally {
         Arrays.fill(var0, (byte)0);
         Arrays.fill(var5, (byte)0);
      }
   }

   public static String state() throws Exception {
      return agent == null ? "" : (String)agent.getMethod("bootstrapStatusState").invoke(null);
   }

   public static String message() throws Exception {
      return agent == null ? "" : (String)agent.getMethod("bootstrapStatusMessage").invoke(null);
   }
}
