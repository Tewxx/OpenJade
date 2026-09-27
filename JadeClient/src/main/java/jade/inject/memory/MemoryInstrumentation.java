package jade.inject.memory;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public final class MemoryInstrumentation implements InvocationHandler {
   private static final List<ClassFileTransformer> transformers = new CopyOnWriteArrayList<>();
   private static volatile ClassFileTransformer[] snapshot = new ClassFileTransformer[0];
   private static final Instrumentation INSTANCE = (Instrumentation)Proxy.newProxyInstance(
      MemoryInstrumentation.class.getClassLoader(), new Class[]{Instrumentation.class}, new MemoryInstrumentation()
   );

   public static Instrumentation instance() {
      return INSTANCE;
   }

   private static native Class<?>[] loadedClasses();

   private static native boolean modifiable(Class<?> var0);

   private static native void retransform(Class<?>[] var0);

   private static native long objectSize(Object var0);

   private static native void openPackage(Object var0, String var1, Object var2);

   @Override
   public Object invoke(Object var1, Method var2, Object[] var3) throws Throwable {
      String var4 = var2.getName();
      if (var4.equals("addTransformer")) {
         if (var3.length != 2 || !Boolean.TRUE.equals(var3[1])) {
            throw new UnsupportedOperationException("only retransformable Jade transformers are supported");
         } else if (var3[0] == null) {
            throw new NullPointerException("transformer");
         } else {
            synchronized (transformers) {
               transformers.add((ClassFileTransformer)var3[0]);
               snapshot = (ClassFileTransformer[]) transformers.toArray(new ClassFileTransformer[0]);
               return null;
            }
         }
      } else if (var4.equals("removeTransformer")) {
         synchronized (transformers) {
            boolean var13 = transformers.remove(var3[0]);
            snapshot = (ClassFileTransformer[]) transformers.toArray(new ClassFileTransformer[0]);
            return var13;
         }
      } else if (var4.equals("getAllLoadedClasses")) {
         return loadedClasses();
      } else if (var4.equals("isModifiableClass")) {
         return modifiable((Class<?>)var3[0]);
      } else if (var4.equals("retransformClasses")) {
         retransform((Class<?>[])var3[0]);
         return null;
      } else if (var4.equals("isRetransformClassesSupported")) {
         return true;
      } else if (var4.equals("isRedefineClassesSupported") || var4.equals("isNativeMethodPrefixSupported")) {
         return false;
      } else if (var4.equals("getObjectSize")) {
         return objectSize(var3[0]);
      } else if (var4.equals("isModifiableModule")) {
         return true;
      } else if (!var4.equals("redefineModule")) {
         if (var4.equals("toString")) {
            return "Jade memory instrumentation";
         } else if (var4.equals("hashCode")) {
            return System.identityHashCode(var1);
         } else if (var4.equals("equals")) {
            return var1 == var3[0];
         } else {
            throw new UnsupportedOperationException("unsupported instrumentation operation: " + var4);
         }
      } else if (((Set)var3[1]).isEmpty() && ((Map)var3[2]).isEmpty() && ((Set)var3[4]).isEmpty() && ((Map)var3[5]).isEmpty()) {
         for (Entry var6 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (((Map)var3[3]).entrySet())) {
            for (Object var8 : (Set)var6.getValue()) {
               openPackage(var3[0], (String)var6.getKey(), var8);
            }
         }

         return null;
      } else {
         throw new UnsupportedOperationException("only module opens are supported");
      }
   }

   public static byte[] transform(ClassLoader var0, String var1, Class<?> var2, ProtectionDomain var3, byte[] var4) {
      if (var1 != null && var1.startsWith("jade/inject/memory/")) {
         return null;
      } else {
         byte[] var5 = var4;

         try {
            for (ClassFileTransformer var9 : snapshot) {
               byte[] var10 = var9.transform(var0, var1, var2, var3, var5);
               if (var10 != null) {
                  var5 = var10;
               }
            }

            return var5 == var4 ? null : var5;
         } catch (Throwable var11) {
            return new byte[0];
         }
      }
   }
}
