// Jade recovery: recovered class name: BridgeClassLoader; original class: jade.deps.eLz.oWNNU51c
package jade.client.runtime;

import java.net.URL;
import java.net.URLClassLoader;

public final class BridgeClassLoader extends URLClassLoader {
   private static final String LOADER_ABI_PREFIX = "jade.deps.loader107.";
   private static final String[] SHARED_LIBRARY_PREFIXES = new String[]{"jade.deps.asm.", "jade.deps.eddsa.", "jade.deps.gson.", "jade.deps.slf4j.", "jade.deps.websocket."};
   private final ClassLoader bootstrapLoader = BridgeClassLoader.class.getClassLoader();

   public BridgeClassLoader(URL[] urls, ClassLoader parent) {
      super(urls, parent);
   }

   @Override
   protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
      synchronized (this.getClassLoadingLock(name)) {
         if (shouldLoadFromBootstrap(name) && this.bootstrapLoader != null) {
            return Class.forName(name, false, this.bootstrapLoader);
         }
         if (shouldLoadChildFirst(name)) {
            Class<?> loaded = this.findLoadedClass(name);
            if (loaded == null) {
               try {
                  loaded = this.findClass(name);
               } catch (ClassNotFoundException var7) {
               }
            }

            if (loaded != null) {
               if (resolve) {
                  this.resolveClass(loaded);
               }

               return loaded;
            }
         }

         Class var11;
         try {
            var11 = super.loadClass(name, resolve);
         } catch (ClassNotFoundException var9) {
            if (shouldLoadFromBootstrap(name) && this.bootstrapLoader != null && this.bootstrapLoader != this.getParent()) {
               try {
                  var11 = Class.forName(name, resolve, this.bootstrapLoader);
               } catch (ClassNotFoundException var8) {
                  throw var9;
               }

               return var11;
            }

            throw var9;
         }

         return var11;
      }
   }

   private static boolean shouldLoadFromBootstrap(String name) {
      if (name.startsWith("jade.inject.") || name.startsWith("jade.build.")
         || name.startsWith("jade.client.runtime.") || name.startsWith("org.spongepowered.")
         || (name.startsWith("net.jade.dev.agent.") && !name.startsWith("net.jade.dev.agent.transformer."))) {
         return true;
      }
      if (!name.startsWith("jade.deps.loader107.")
         && !"jade.inject.InjectionAgent".equals(name)
         && !"jade.inject.RuntimeAccess".equals(name)
         && !name.startsWith("jade.inject.RuntimeAccess$")) {
         for (String prefix : SHARED_LIBRARY_PREFIXES) {
            if (name.startsWith(prefix)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private static boolean shouldLoadChildFirst(String name) {
      return isCoreClass(name);
   }

   public static boolean isCoreClass(String name) {
      return (name.startsWith("jade.client.") && !name.startsWith("jade.client.runtime."))
         || name.startsWith("jade.mixin.") || name.startsWith("jade.local.")
         || name.startsWith("net.jade.dev.agent.transformer.");
   }
}
