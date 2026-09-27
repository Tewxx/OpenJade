package jade.deps.loader107;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;
import java.util.Set;

final class ResourceBridgeAccess {
   private ResourceBridgeAccess() {
   }

   static void publishCore(Map<String, byte[]> var0, Map<String, byte[]> var1, String var2, String var3) throws Exception {
      invokeBridgeMethod(
            "publishCore", Map.class, Map.class, String.class, String.class
         )
         .invoke(null, var0, var1, var2, var3);
   }

   static boolean isPrepared() throws Exception {
      return (Boolean)invokeBridgeMethod("isPrepared").invoke(null);
   }

   static String getBuildId() throws Exception {
      return (String)invokeBridgeMethod("buildId").invoke(null);
   }

   static String getCoreSha256() throws Exception {
      return (String)invokeBridgeMethod("coreSha256").invoke(null);
   }

   static int getClassCount() throws Exception {
      return ((Number)invokeBridgeMethod("classCount").invoke(null))
         .intValue();
   }

   static byte[] readResource(String var0) throws Exception {
      return (byte[])invokeBridgeMethod("readResource", String.class)
         .invoke(null, var0);
   }

   static URL getResourceUrl() throws Exception {
      return (URL)invokeBridgeMethod("resourceUrl").invoke(null);
   }

   static Map<String, byte[]> copyResources() throws Exception {
      return (Map<String, byte[]>)invokeBridgeMethod("copyResources")
         .invoke(null);
   }

   static Set<String> getPublishedClassNames() throws Exception {
      return (Set<String>)invokeBridgeMethod("publishedClassNames")
         .invoke(null);
   }

   static void retainHierarchyOnly() {
      try {
         invokeBridgeMethod("retainHierarchyOnly").invoke(null);
      } catch (Exception var1) {
      }
   }

   private static Method invokeBridgeMethod(String var0, Class<?>... var1) throws Exception {
      Class var2;
      try {
         var2 = Class.forName(
            "net.jade.dev.agent.ResourceBridge",
            true,
            ClassLoader.getSystemClassLoader()
         );
      } catch (ClassNotFoundException var4) {
         var2 = Class.forName(
            "net.jade.dev.agent.ResourceBridge",
            true,
            ResourceBridgeAccess.class.getClassLoader()
         );
      }

      return var2.getMethod(var0, var1);
   }
}
