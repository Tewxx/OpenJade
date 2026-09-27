// Jade recovery: original class: jade.deps.eLz.vvw1Pe0qV
package jade.client.common;

import jade.client.Jade;
import jade.client.event.Event;
import jade.client.event.RenderLivingPostEvent;
import jade.client.event.RenderLivingPreEvent;
import jade.client.event.RenderWorldLastEvent;
import jade.client.module.Module;
import jade.inject.InjectionAgent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EventBus {
   private static final Map<Class<? extends Event>, EnumMap<EventPriority, List<EventBus$0>>> Ozg = new HashMap<>();
   private static volatile Map<Class<? extends Event>, EventBus$0[]> UAL = Collections.emptyMap();
   private static final Map<String, Long> ZYJ = new ConcurrentHashMap<>();
   private static final long SLOW_LISTENER_THRESHOLD_NANOS = 4000000L;
   private static final long SLOW_LISTENER_LOG_INTERVAL_MILLIS = 1000L;

   public static synchronized void register(Object var0) {
      for (Method var4 : var0.getClass().getDeclaredMethods()) {
         if (var4.isAnnotationPresent(Subscribe.class) && var4.getParameterCount() == 1) {
            Class var5 = var4.getParameterTypes()[0];
            if (Event.class.isAssignableFrom(var5)) {
               EventPriority var7 = var4.getAnnotation(Subscribe.class).priority();
               var4.setAccessible(true);
               Ozg.computeIfAbsent(var5, EventBus::createPriorityMap).computeIfAbsent(var7, EventBus::listenersFor).add(new EventBus$0(var0, var4));
            }
         }
      }

      rebuildDispatchTable();
   }

   public static synchronized void unregister(Object var0) {
      for (EnumMap var2 : Ozg.values()) {
         for (List var4 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (var2.values())) {
            var4.removeIf((recoveredArg0) -> EventBus.isOwnedBy(var0, (jade.client.common.EventBus$0) recoveredArg0));
         }
      }

      rebuildDispatchTable();
   }

   public static synchronized void clear() {
      Ozg.clear();
      UAL = Collections.emptyMap();
      ZYJ.clear();
      sUon();
   }

   public static boolean hasListeners(Class<? extends Event> var0) {
      EventBus$0[] var1 = UAL.get(var0);
      return var1 != null && var1.length != 0;
   }

   public static <T extends Event> T post(T var0) {
      if (InjectionAgent.diagnosticsEnabled()) {
         InjectionAgent.recordRuntimeSignal("event.post", var0.getClass().getSimpleName());
      }

      EventBus$0[] var1 = UAL.get(var0.getClass());
      if (var1 == null) {
         return (T)var0;
      } else {
         for (EventBus$0 var5 : var1) {
            if (!(var5.listener instanceof Module) || ((Module)var5.listener).isEnabled()) {
               long var6 = Jade.profilingEnabled ? System.nanoTime() : 0L;

               try {
                  var5.method.invoke(var5.listener, var0);
                  if (InjectionAgent.diagnosticsEnabled()) {
                     InjectionAgent.recordRuntimeSignal(
                        "event.listener", var0.getClass().getSimpleName() + "." + var5.listener.getClass().getSimpleName() + "." + var5.method.getName()
                     );
                  }
               } catch (Exception var9) {
                  if (InjectionAgent.diagnosticsEnabled()) {
                     InjectionAgent.recordRuntimeFailure(
                        "event.listener", var0.getClass().getSimpleName() + "." + var5.listener.getClass().getSimpleName() + "." + var5.method.getName(), var9
                     );
                  }

                  var9.printStackTrace();
               }

               if (var6 != 0L) {
                  dispatch(var0, var5, System.nanoTime() - var6);
               }
            }
         }

         return (T)var0;
      }
   }

   private static void rebuildDispatchTable() {
      HashMap var0 = new HashMap();

      for (Entry var2 : Ozg.entrySet()) {
         ArrayList var3 = new ArrayList();

         for (EventPriority var7 : EventPriority.values()) {
            List var8 = (List)((EnumMap)var2.getValue()).get(var7);
            if (var8 != null) {
               var3.addAll(var8);
            }
         }

         if (!var3.isEmpty()) {
            var0.put(var2.getKey(), var3.toArray(new EventBus$0[var3.size()]));
         }
      }

      UAL = Collections.unmodifiableMap(var0);
      sUon();
   }

   private static void sUon() {
      InjectionAgent.setHookEnabled("onRenderLivingPre", "(Ljava/lang/Object;DDDF)V", hasListeners(RenderLivingPreEvent.class));
      InjectionAgent.setHookEnabled("onRenderLivingPost", "(Ljava/lang/Object;DDDF)V", hasListeners(RenderLivingPostEvent.class));
      boolean var0 = hasListeners(RenderWorldLastEvent.class);
      InjectionAgent.setHookEnabled("onRenderWorldLast", "(F)V", var0);
      InjectionAgent.setHookEnabled("onRenderWorldLast", "(FZ)V", var0);
   }

   private static void dispatch(Event var0, EventBus$0 var1, long var2) {
      if (var2 >= 4000000L) {
         String var4 = var0.getClass().getSimpleName() + " -> " + var1.listener.getClass().getSimpleName() + "." + var1.method.getName();
         long var5 = System.currentTimeMillis();
         Long var7 = ZYJ.get(var4);
         if (var7 == null || var5 - var7 >= 1000L) {
            ZYJ.put(var4, var5);
            System.out.println("[Jade] Slow event listener " + var4 + " took " + var2 / 1000000.0 + "ms");
         }
      }
   }

   private static boolean isOwnedBy(Object var0, EventBus$0 var1) {
      return var1.listener == var0;
   }

   private static List listenersFor(EventPriority var0) {
      return new CopyOnWriteArrayList<>();
   }

   private static EnumMap createPriorityMap(Class var0) {
      return new EnumMap<>(EventPriority.class);
   }
}
