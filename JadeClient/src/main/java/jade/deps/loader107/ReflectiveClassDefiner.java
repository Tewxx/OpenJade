package jade.deps.loader107;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

final class ReflectiveClassDefiner {
   private final ClassLoader classLoader;
   private final Method defineClassMethod;
   private final Method findLoadedClassMethod;
   private final ProtectionDomain protectionDomain;
   private final boolean rejectIfAlreadyDefined;

   ReflectiveClassDefiner(ClassLoader var1, boolean var2) throws Exception {
      this.classLoader = var1;
      this.rejectIfAlreadyDefined = var2;
      this.defineClassMethod = ClassLoader.class
         .getDeclaredMethod(
            "defineClass",
            String.class,
            byte[].class,
            int.class,
            int.class,
            ProtectionDomain.class
         );
      this.defineClassMethod.setAccessible(true);
      this.findLoadedClassMethod = ClassLoader.class
         .getDeclaredMethod("findLoadedClass", String.class);
      this.findLoadedClassMethod.setAccessible(true);
      this.protectionDomain = LocalCoreLoader.class.getProtectionDomain();
   }

   void defineClass(String var1, byte[] var2) throws Exception {
      RecoveredMethodInterpreter.invokeRecovered(
         "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",
         jade.build.RecoveredHandles.resolve(ReflectiveClassDefiner.class, "dispatchClassDefinition"),
         new Object[]{this, var1, var2}
      );
   }

   private static Object dispatchClassDefinition(int var0, Object[] var1) throws Throwable {
      switch (var0) {
         case 0:
            return ((ReflectiveClassDefiner)var1[0]).findLoadedClassMethod;
         case 1:
            return ((ReflectiveClassDefiner)var1[0]).classLoader;
         case 2:
            return new Object[((Number)var1[0]).intValue()];
         case 3:
            ((Object[])var1[0])[((Number)var1[1]).intValue()] = var1[2];
            return null;
         case 4:
            return ((Method)var1[0]).invoke(var1[1], (Object[])var1[2]);
         case 5:
            return ((InvocationTargetException)var1[0]).getCause();
         case 6:
            return Integer.valueOf((var1[0] instanceof Error) ? 1 : 0);
         case 7:
            return (Error)var1[0];
         case 8:
            return Integer.valueOf((var1[0] instanceof Exception) ? 1 : 0);
         case 9:
            return (Exception)var1[0];
         case 10:
            return Integer.valueOf((((ReflectiveClassDefiner)var1[0]).rejectIfAlreadyDefined) ? 1 : 0);
         case 11:
            return RecoveredMethodInterpreter.initializeClass(SecurityException.class);
         case 12:
            return RecoveredMethodInterpreter.initializeClass(StringBuilder.class);
         case 13:
            return new StringBuilder();
         case 14:
            return "local class was already defined before fallback delivery: ";
         case 15:
            return ((StringBuilder)var1[0]).append((String)var1[1]);
         case 16:
            return ((StringBuilder)var1[0]).append((String)var1[1]);
         case 17:
            return ((StringBuilder)var1[0]).toString();
         case 18:
            return new SecurityException((String)var1[1]);
         case 19:
            return ((ReflectiveClassDefiner)var1[0]).defineClassMethod;
         case 20:
            return ((ReflectiveClassDefiner)var1[0]).classLoader;
         case 21:
            return new Object[((Number)var1[0]).intValue()];
         case 22:
            ((Object[])var1[0])[((Number)var1[1]).intValue()] = var1[2];
            return null;
         case 23:
            ((Object[])var1[0])[((Number)var1[1]).intValue()] = var1[2];
            return null;
         case 24:
            return ((Number)var1[0]).intValue();
         case 25:
            ((Object[])var1[0])[((Number)var1[1]).intValue()] = var1[2];
            return null;
         case 26:
            return Array.getLength(var1[0]);
         case 27:
            return ((Number)var1[0]).intValue();
         case 28:
            ((Object[])var1[0])[((Number)var1[1]).intValue()] = var1[2];
            return null;
         case 29:
            return ((ReflectiveClassDefiner)var1[0]).protectionDomain;
         case 30:
            ((Object[])var1[0])[((Number)var1[1]).intValue()] = var1[2];
            return null;
         case 31:
            return ((Method)var1[0]).invoke(var1[1], (Object[])var1[2]);
         case 32:
            return ((InvocationTargetException)var1[0]).getCause();
         case 33:
            return Integer.valueOf((var1[0] instanceof Error) ? 1 : 0);
         case 34:
            return (Error)var1[0];
         case 35:
            return Integer.valueOf((var1[0] instanceof Exception) ? 1 : 0);
         case 36:
            return (Exception)var1[0];
         case 37:
            return Integer.valueOf((var1[0] instanceof InvocationTargetException) ? 1 : 0);
         case 38:
            return Integer.valueOf((var1[0] instanceof InvocationTargetException) ? 1 : 0);
         default:
            throw new IllegalArgumentException();
      }
   }
}
