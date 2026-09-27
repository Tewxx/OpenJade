package jade.inject;

import java.io.File;
import java.io.FileWriter;
import java.lang.invoke.MethodHandle;

public final class BootstrapHookBridge {
   private static final MethodHandle[] TARGETS = new MethodHandle[256];
   private static final boolean[] ENABLED = new boolean[256];
   private static final String[] NAMES = new String[256];
   private static volatile boolean active;
   private static final long[] GRADIENT_TRACE_AT = new long[256];

   private BootstrapHookBridge() {
   }

   public static void install(int id, MethodHandle target) {
      installInternal(id, target, "hook-" + id, false);
   }

   public static void install(int id, MethodHandle target, String name) {
      installInternal(id, target, name, true);
   }

   private static void installInternal(int id, MethodHandle target, String name, boolean trace) {
      if (id >= 0 && id < TARGETS.length && target != null) {
         TARGETS[id] = target;
         ENABLED[id] = true;
         NAMES[id] = name == null ? "hook-" + id : name;
      } else {
         throw new IllegalArgumentException("invalid hook target");
      }
   }

   public static void setEnabled(int id, boolean enabled) {
      if (id >= 0 && id < ENABLED.length) {
         ENABLED[id] = enabled;
      }
   }

   public static void activate() {
      active = true;
   }

   public static void deactivate() {
      active = false;
   }

   public static void clear() {
      active = false;

      for (int i = 0; i < TARGETS.length; i++) {
         TARGETS[i] = null;
         ENABLED[i] = false;
         NAMES[i] = null;
      }
   }

   private static MethodHandle target(int id) {
      boolean valid = id >= 0 && id < TARGETS.length;
      MethodHandle result = active && valid && ENABLED[id] ? TARGETS[id] : null;
      if (valid && isGradientHook(id)) {
         gradientTrace(id, "dispatch active=" + active + " enabled=" + ENABLED[id] + " bound=" + (result != null));
      }

      return result;
   }

   private static boolean isGradientHook(int id) {
      String name = NAMES[id];
      return name != null
         && (
            name.startsWith("onFontDraw")
               || name.startsWith("onChatDrawString")
               || name.startsWith("onBadlionCachedChatDraw")
               || name.startsWith("onChatRender")
               || name.startsWith("onChatLineSet")
         );
   }

   private static void gradientTrace(int id, String detail) {
      long now = System.currentTimeMillis();
      if (now - GRADIENT_TRACE_AT[id] >= 1500L) {
         GRADIENT_TRACE_AT[id] = now;
         FileWriter writer = null;

         try {
            File directory = new File(System.getenv("APPDATA"), ".jade");
            directory.mkdirs();
            File file = new File(directory, "badlion-gradient-debug.log");
            if (!file.isFile() || file.length() < 524288L) {
               writer = new FileWriter(file, true);
               writer.write("[" + now + "] bridge " + NAMES[id] + " " + detail + System.lineSeparator());
               return;
            }
         } catch (Throwable var17) {
            return;
         } finally {
            if (writer != null) {
               try {
                  writer.close();
               } catch (Throwable var16) {
               }
            }
         }
      }
   }

   public static void invoke(int id) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact();
         } catch (Throwable var3) {
         }
      }
   }

   public static boolean invokeBoolean(int id) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact();
         } catch (Throwable var3) {
         }
      }

      return false;
   }

   public static void invoke(int id, Object a) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a);
         } catch (Throwable var4) {
         }
      }
   }

   public static boolean invokeBoolean(int id, Object a) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((Object)a);
         } catch (Throwable var4) {
         }
      }

      return false;
   }

   public static int invokeInt(int id, Object a) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (int)h.invokeExact((Object)a);
         } catch (Throwable var4) {
         }
      }

      return 0;
   }

   public static int invokeInt(int id, Object a, int b) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (int)h.invokeExact((Object)a, (int)b);
         } catch (Throwable var5) {
         }
      }

      return b;
   }

   public static Object invokeObject(int id, Object a) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (Object)h.invokeExact((Object)a);
         } catch (Throwable var4) {
         }
      }

      return null;
   }

   public static void invoke(int id, boolean a) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((boolean)a);
         } catch (Throwable var4) {
         }
      }
   }

   public static void invoke(int id, int a, boolean b) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((int)a, (boolean)b);
         } catch (Throwable var5) {
         }
      }
   }

   public static void invoke(int id, float a) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((float)a);
         } catch (Throwable var4) {
         }
      }
   }

   public static void invoke(int id, float a, boolean b) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((float)a, (boolean)b);
         } catch (Throwable var5) {
         }
      }
   }

   public static Object invokeObject(int id, float a) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (Object)h.invokeExact((float)a);
         } catch (Throwable var4) {
         }
      }

      return null;
   }

   public static Object invokeObject(int id, float a, float b) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (Object)h.invokeExact((float)a, (float)b);
         } catch (Throwable var5) {
         }
      }

      return null;
   }

   public static void invoke(int id, Object a, int b) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a, (int)b);
         } catch (Throwable var5) {
         }
      }
   }

   public static void invoke(int id, Object a, int b, int c) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a, (int)b, (int)c);
         } catch (Throwable var6) {
         }
      }
   }

   public static void invoke(int id, Object a, float b, float c) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a, (float)b, (float)c);
         } catch (Throwable var6) {
         }
      }
   }

   public static void invoke(int id, Object a, float b, float c, float d) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a, (float)b, (float)c, (float)d);
         } catch (Throwable var7) {
         }
      }
   }

   public static float invokeFloat(int id, Object a, float b) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (float)h.invokeExact((Object)a, (float)b);
         } catch (Throwable var5) {
         }
      }

      return b;
   }

   public static float invokeFloat(int id, Object a, float b, float c) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (float)h.invokeExact((Object)a, (float)b, (float)c);
         } catch (Throwable var6) {
         }
      }

      return c;
   }

   public static boolean invokeBoolean(int id, Object a, boolean b) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((Object)a, (boolean)b);
         } catch (Throwable var5) {
         }
      }

      return b;
   }

   public static boolean invokeBoolean(int id, Object a, float b, float c) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((Object)a, (float)b, (float)c);
         } catch (Throwable var6) {
         }
      }

      return false;
   }

   public static void invoke(int id, Object a, double b, double c, double d) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a, (double)b, (double)c, (double)d);
         } catch (Throwable var10) {
         }
      }
   }

   public static double invokeDouble(int id, Object a, Object b) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (double)h.invokeExact((Object)a, (Object)b);
         } catch (Throwable var5) {
         }
      }

      return 0.0;
   }

   public static void invoke(int id, Object a, Object b) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a, (Object)b);
         } catch (Throwable var5) {
         }
      }
   }

   public static boolean invokeBoolean(int id, Object a, Object b) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((Object)a, (Object)b);
         } catch (Throwable var5) {
         }
      }

      return false;
   }

   public static Object invokeObject(int id, Object a, Object b) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (Object)h.invokeExact((Object)a, (Object)b);
         } catch (Throwable var5) {
         }
      }

      return null;
   }

   public static boolean invokeBoolean(int id, Object a, Object b, boolean c) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((Object)a, (Object)b, (boolean)c);
         } catch (Throwable var6) {
         }
      }

      return c;
   }

   public static void invoke(int id, Object a, Object b, int c, int d, boolean e) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a, (Object)b, (int)c, (int)d, (boolean)e);
         } catch (Throwable var8) {
         }
      }
   }

   public static boolean invokeBoolean(int id, Object a, Object b, int c, int d, int e) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((Object)a, (Object)b, (int)c, (int)d, (int)e);
         } catch (Throwable var8) {
         }
      }

      return false;
   }

   public static Object invokeObject(int id, Object a, Object b, Object c) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (Object)h.invokeExact((Object)a, (Object)b, (Object)c);
         } catch (Throwable var6) {
         }
      }

      return null;
   }

   public static boolean invokeBoolean(int id, Object a, Object b, Object c) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((Object)a, (Object)b, (Object)c);
         } catch (Throwable var6) {
         }
      }

      return false;
   }

   public static boolean invokeBoolean(int id, int a, Object b, Object c) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((int)a, (Object)b, (Object)c);
         } catch (Throwable var6) {
         }
      }

      return false;
   }

   public static boolean invokeBoolean(int id, Object a, Object b, Object c, Object d, int e, int f, int g, boolean h0) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((Object)a, (Object)b, (Object)c, (Object)d, (int)e, (int)f, (int)g, (boolean)h0);
         } catch (Throwable var11) {
         }
      }

      return false;
   }

   public static void invoke(int id, Object a, Object b, Object c, Object d, Object e, Object f, Object g) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a, (Object)b, (Object)c, (Object)d, (Object)e, (Object)f, (Object)g);
         } catch (Throwable var10) {
         }
      }
   }

   public static boolean invokeBoolean(int id, Object a, Object b, Object c, Object d, Object e, Object f, Object g) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((Object)a, (Object)b, (Object)c, (Object)d, (Object)e, (Object)f, (Object)g);
         } catch (Throwable var10) {
         }
      }

      return false;
   }

   public static void invoke(int id, Object a, double b, double c, double d, float e) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a, (double)b, (double)c, (double)d, (float)e);
         } catch (Throwable var11) {
         }
      }
   }

   public static void invoke(int id, Object a, Object b, double c, double d, double e, float f) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a, (Object)b, (double)c, (double)d, (double)e, (float)f);
         } catch (Throwable var12) {
         }
      }
   }

   public static Object invokeObject(int id, Object a, Object b, float c, float d, int e) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (Object)h.invokeExact((Object)a, (Object)b, (float)c, (float)d, (int)e);
         } catch (Throwable var8) {
            if (isGradientHook(id)) {
               gradientTrace(id, "failure=" + var8.getClass().getName());
            }
         }
      }

      return null;
   }

   public static Object invokeObject(int id, Object a, Object b, float c, float d, int e, boolean f) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (Object)h.invokeExact((Object)a, (Object)b, (float)c, (float)d, (int)e, (boolean)f);
         } catch (Throwable var9) {
            if (isGradientHook(id)) {
               gradientTrace(id, "failure=" + var9.getClass().getName());
            }
         }
      }

      return null;
   }

   public static boolean invokeBoolean(int id, Object a, Object b, float c) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((Object)a, (Object)b, (float)c);
         } catch (Throwable var6) {
         }
      }

      return false;
   }

   public static void invoke(int id, Object a, Object b, float c) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            h.invokeExact((Object)a, (Object)b, (float)c);
         } catch (Throwable var6) {
         }
      }
   }

   public static boolean invokeBoolean(int id, Object a, int b, boolean c) {
      MethodHandle h = target(id);
      if (h != null) {
         try {
            return (boolean)h.invokeExact((Object)a, (int)b, (boolean)c);
         } catch (Throwable var6) {
         }
      }

      return false;
   }

   private static final class Trace implements Runnable {
      static void record(String event) {
      }

      @Override
      public void run() {
      }
   }
}
