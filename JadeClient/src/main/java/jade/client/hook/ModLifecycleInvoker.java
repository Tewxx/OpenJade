// Jade recovery: original class: jade.deps.eLz.rpCZKfr
package jade.client.hook;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

public class ModLifecycleInvoker {
   private static final Logger logger = Logger.getLogger("ModLifecycleInvoker");
   private static final String EVENT_HANDLER_CLASS_NAME = "net.minecraftforge.fml.common.Mod$EventHandler";
   private static final String[] LIFECYCLE_EVENT_CLASS_NAMES = new String[]{"net.minecraftforge.fml.common.event.FMLPreInitializationEvent", "net.minecraftforge.fml.common.event.FMLInitializationEvent", "net.minecraftforge.fml.common.event.FMLPostInitializationEvent", "net.minecraftforge.fml.common.event.FMLLoadCompleteEvent"};
   private static final Unsafe unsafe;

   public static void invokeLifecycleMethods(Object var0, File var1, ClassLoader var2) {
      if (unsafe == null) {
         logger.severe("[ModLifecycleInvoker] Unsafe unavailable,  aborting lifecycle.");
      } else {
         Class var3 = var0.getClass();
         Class var4 = resolveEventHandlerAnnotation(var2);
         if (var4 == null) {
            logger.warning("[ModLifecycleInvoker] Could not resolve @EventHandler; falling back to name-based matching.");
         }

         List var5 = collectLifecycleMethods(var3, var4);
         if (var5.isEmpty()) {
            logger.info("[ModLifecycleInvoker] No lifecycle methods found on " + var3.getName());
         } else {
            for (Method var7 : (java.lang.Iterable<Method>) (java.lang.Iterable<?>) (var5)) {
               Class var8 = var7.getParameterTypes()[0];
               Object var9 = createEventInstance(var8, var1);
               if (var9 == null) {
                  logger.warning(
                     "[ModLifecycleInvoker] Skipping "
                        + var7.getName()
                        + " \u2014 could not construct event "
                        + var8.getName()
                  );
               } else {
                  try {
                     var7.setAccessible(true);
                     var7.invoke(var0, var9);
                     logger.info(
                        "[ModLifecycleInvoker] Invoked "
                           + var7.getName()
                           + "("
                           + var8.getSimpleName()
                           + ") on "
                           + var3.getSimpleName()
                     );
                  } catch (Exception var12) {
                     Throwable var11 = var12.getCause() != null ? var12.getCause() : var12;
                     logger.log(
                        Level.SEVERE,
                        "[ModLifecycleInvoker] Exception in "
                           + var7.getName()
                           + "("
                           + var8.getSimpleName()
                           + "): "
                           + var11.getMessage(),
                        (Throwable)var11
                     );
                  }
               }
            }
         }
      }
   }

   private static Class<? extends Annotation> resolveEventHandlerAnnotation(ClassLoader var0) {
      try {
         return (Class<? extends Annotation>)Class.forName(
            "net.minecraftforge.fml.common.Mod$EventHandler", true, var0
         );
      } catch (ClassNotFoundException var2) {
         return null;
      }
   }

   private static List<Method> collectLifecycleMethods(Class<?> var0, Class<? extends Annotation> var1) {
      ArrayList var2 = new ArrayList();

      for (Class var3 = var0; var3 != null && var3 != Object.class; var3 = var3.getSuperclass()) {
         for (Method var7 : var3.getDeclaredMethods()) {
            if (var7.getParameterCount() == 1) {
               boolean var8 = var1 != null ? var7.isAnnotationPresent(var1) : uWe8(var7);
               if (var8 && !var2.contains(var7)) {
                  var2.add(var7);
               }
            }
         }
      }

      var2.sort(Comparator.comparingInt(ModLifecycleInvoker::getEventOrderKey));
      return var2;
   }

   private static boolean uWe8(Method var0) {
      for (Annotation var4 : var0.getAnnotations()) {
         String var5 = var4.annotationType().getSimpleName();
         if ("EventHandler".equals(var5)) {
            return true;
         }
      }

      return false;
   }

   private static int getLifecycleEventRank(String var0) {
      for (int var1 = 0; var1 < LIFECYCLE_EVENT_CLASS_NAMES.length; var1++) {
         if (LIFECYCLE_EVENT_CLASS_NAMES[var1].equals(var0)) {
            return var1;
         }
      }

      return LIFECYCLE_EVENT_CLASS_NAMES.length;
   }

   private static Object createEventInstance(Class<?> var0, File var1) {
      try {
         Object var2 = unsafe.allocateInstance(var0);
         injectConfigDirectory(var2, var0, var1);
         return var2;
      } catch (InstantiationException var3) {
         logger.log(Level.WARNING, "[ModLifecycleInvoker] allocateInstance failed for " + var0.getName(), (Throwable)var3);
         return null;
      }
   }

   private static void injectConfigDirectory(Object var0, Class<?> var1, File var2) {
      for (Class var3 = var1; var3 != null && var3 != Object.class; var3 = var3.getSuperclass()) {
         for (Field var7 : var3.getDeclaredFields()) {
            String var8 = var7.getName().toLowerCase();
            boolean var9 = var7.getType() == File.class;
            boolean var10 = var8.contains("config")
               || var8.contains("cfgdir")
               || var8.contains("moddir");
            if (var9 && var10) {
               try {
                  var7.setAccessible(true);
                  var7.set(var0, var2);
                  logger.fine(
                     "[ModLifecycleInvoker] Injected configDir into "
                        + var1.getSimpleName()
                        + "."
                        + var7.getName()
                  );
               } catch (IllegalAccessException var12) {
                  logger.log(Level.WARNING, "[ModLifecycleInvoker] Could not set field " + var7.getName(), (Throwable)var12);
               }

               return;
            }
         }
      }
   }

   private static int getEventOrderKey(Method var0) {
      return getLifecycleEventRank(var0.getParameterTypes()[0].getName());
   }

   static {
      Unsafe var0 = null;

      try {
         Field var1 = Unsafe.class.getDeclaredField("theUnsafe");
         var1.setAccessible(true);
         var0 = (Unsafe)var1.get(null);
      } catch (Exception var2) {
         logger.log(Level.SEVERE, "[ModLifecycleInvoker] Could not obtain Unsafe.  Lifecycle invocation will not work!", (Throwable)var2);
      }

      unsafe = var0;
   }
}
