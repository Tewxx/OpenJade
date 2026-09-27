// Jade recovery: recovered class name: RuntimeAccess
package jade.inject;

import jade.client.runtime.BadlionRuntimeDiagnostics;
import jade.client.runtime.RuntimeMappings;
import jade.deps.asm.Type;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class RuntimeAccess {
   private static final int FIELD_GET = 0;
   private static final int FIELD_SET = 1;
   private static final int INVOKE = 2;
   private static final int SIDECAR_GET = 3;
   private static final int SIDECAR_SET = 4;
   private static final Map<String, RuntimeAccess.Spec> SPECS = new HashMap<>();
   private static final Map<String, Field> FIELDS = new ConcurrentHashMap<>();
   private static final Map<String, Method> METHODS = new ConcurrentHashMap<>();
   private static final RuntimeAccess.WeakIdentityMap<RuntimeAccess.Sidecar> SIDECARS = new RuntimeAccess.WeakIdentityMap<>();
   private static volatile RuntimeMappings mappings;

   private RuntimeAccess() {
   }

   public static void configure(RuntimeMappings activeMappings) {
      mappings = activeMappings;
   }

   public static void clearForDetach() {
      FIELDS.clear();
      METHODS.clear();
      SIDECARS.clear();
   }

   public static void resetForInjection() {
      FIELDS.clear();
      METHODS.clear();
      SIDECARS.clear();
      mappings = null;
   }

   public static int validateRequiredMembers(ClassLoader loader, RuntimeMappings activeMappings) throws Exception {
      if (loader == null) {
         throw new IllegalArgumentException("runtime loader is unavailable");
      } else {
         configure(activeMappings);
         int verified = 0;

         for (Entry<String, RuntimeAccess.Spec> entry : SPECS.entrySet()) {
            RuntimeAccess.Spec spec = entry.getValue();
            if (spec.kind != 3 && spec.kind != 4) {
               String runtimeOwner = activeMappings == null ? spec.owner : activeMappings.runtimeClass(spec.owner);
               Class<?> owner = Class.forName(runtimeOwner.replace('/', '.'), false, loader);

               try {
                  if (spec.kind != 0 && spec.kind != 1) {
                     validateMethod(owner, spec, activeMappings);
                  } else {
                     resolveField(owner, spec);
                  }
               } catch (Exception var9) {
                  throw new IllegalStateException(
                     "unresolved runtime-access contract "
                        + entry.getKey()
                        + " -> "
                        + spec.owner
                        + "."
                        + spec.member,
                     var9
                  );
               }

               verified++;
            }
         }

         return verified;
      }
   }

   private static void validateMethod(Class<?> owner, RuntimeAccess.Spec spec, RuntimeMappings activeMappings) throws Exception {
      String mappedName = activeMappings == null ? spec.member : activeMappings.runtimeMethod(spec.owner, spec.member, spec.descriptor);
      String mappedDescriptor = activeMappings == null ? spec.descriptor : activeMappings.runtimeMethodDescriptor(spec.descriptor);

      for (Class<?> current = owner; current != null; current = current.getSuperclass()) {
         for (Method method : current.getDeclaredMethods()) {
            if ((method.getName().equals(mappedName) || method.getName().equals(spec.member)) && Type.getMethodDescriptor(method).equals(mappedDescriptor)) {
               return;
            }
         }
      }

      throw new NoSuchMethodException(
         owner.getName() + "." + mappedName + mappedDescriptor
      );
   }

   public static boolean consumeSidecarFlag(Object owner, String name) {
      RuntimeAccess.Sidecar sidecar = SIDECARS.getOrCreate(owner);
      boolean value = sidecar.get(name);
      if (value) {
         sidecar.set(name, false);
      }

      return value;
   }

   public static boolean getSidecarFlag(Object owner, String name) {
      return SIDECARS.getOrCreate(owner).get(name);
   }

   public static void setSidecarFlag(Object owner, String name, boolean value) {
      SIDECARS.getOrCreate(owner).set(name, value);
   }

   public static Field resolveMappedField(Class<?> type, String... sourceNames) {
      if (type != null && sourceNames != null && sourceNames.length != 0) {
         String cacheKey = type.getName() + "#mapped-field#" + sourceNames[0];
         Field cached = FIELDS.get(cacheKey);
         if (cached != null) {
            return cached;
         } else {
            for (Class<?> current = type; current != null; current = current.getSuperclass()) {
               String sourceOwner = sourceOwner(current);
               String mapped = mappings == null ? sourceNames[0] : mappings.runtimeField(sourceOwner, sourceNames[0]);

               for (String candidate : merge(mapped, sourceNames)) {
                  try {
                     Field field = current.getDeclaredField(candidate);
                     field.setAccessible(true);
                     FIELDS.put(cacheKey, field);
                     return field;
                  } catch (Throwable var12) {
                  }
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   public static Method resolveMappedMethod(Class<?> type, Class<?>[] parameterTypes, String... sourceNames) {
      if (type != null && sourceNames != null && sourceNames.length != 0) {
         Class<?>[] parameters = parameterTypes == null ? new Class[0] : parameterTypes;
         String cacheKey = type.getName()
            + "#mapped-method#"
            + sourceNames[0]
            + Arrays.toString((Object[])parameters);
         Method cached = METHODS.get(cacheKey);
         if (cached != null) {
            return cached;
         } else {
            for (Class<?> current = type; current != null; current = current.getSuperclass()) {
               String sourceOwner = sourceOwner(current);

               for (Method method : current.getDeclaredMethods()) {
                  if (Arrays.equals((Object[])method.getParameterTypes(), (Object[])parameters)) {
                     boolean matches = contains(sourceNames, method.getName());
                     if (!matches && mappings != null) {
                        String sourceDescriptor = mappings.sourceDescriptor(Type.getMethodDescriptor(method));
                        String mapped = mappings.runtimeMethod(sourceOwner, sourceNames[0], sourceDescriptor);
                        matches = method.getName().equals(mapped);
                     }

                     if (matches) {
                        try {
                           method.setAccessible(true);
                        } catch (Throwable var15) {
                        }

                        METHODS.put(cacheKey, method);
                        return method;
                     }
                  }
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   private static String sourceOwner(Class<?> runtimeType) {
      String runtimeName = runtimeType.getName().replace('.', '/');
      return mappings == null ? runtimeName : mappings.sourceClass(runtimeName);
   }

   private static boolean contains(String[] values, String value) {
      for (String candidate : values) {
         if (candidate.equals(value)) {
            return true;
         }
      }

      return false;
   }

   private static String[] merge(String first, String[] remainder) {
      if (contains(remainder, first)) {
         return remainder;
      } else {
         String[] result = new String[remainder.length + 1];
         result[0] = first;
         System.arraycopy(remainder, 0, result, 1, remainder.length);
         return result;
      }
   }

   public static CallSite bootstrap(Lookup lookup, String name, MethodType type, String interfaceName, int staticCall) throws Exception {
      Lookup own = MethodHandles.lookup();
      int arguments = type.parameterCount();
      MethodHandle target;
      if (staticCall != 0) {
         target = own.findStatic(
            RuntimeAccess.class,
            "invokeStatic",
            MethodType.methodType(Object.class, String.class, String.class, Object[].class)
         );
         target = MethodHandles.insertArguments(target, 0, interfaceName, name);
      } else {
         target = own.findStatic(
            RuntimeAccess.class,
            "invokeInstance",
            MethodType.methodType(Object.class, String.class, String.class, Object.class, Object[].class)
         );
         target = MethodHandles.insertArguments(target, 0, interfaceName, name);
         arguments--;
      }

      target = target.asCollector(Object[].class, arguments).asType(type);
      return new ConstantCallSite(target);
   }

   public static CallSite bootstrapMinecraftMember(
      Lookup lookup, String name, MethodType type, String sourceOwner, String sourceMember, String sourceDescriptor, int opcode
   ) throws Exception {
      Class<?> owner = targetClass(sourceOwner);
      MethodHandle target;
      if (opcode != 180 && opcode != 181 && opcode != 178 && opcode != 179) {
         Method method = resolveDirectMethod(owner, sourceOwner, sourceMember, sourceDescriptor);
         method.setAccessible(true);
         target = MethodHandles.lookup().unreflect(method);
      } else {
         Field field = resolveDirectField(owner, sourceOwner, sourceMember, sourceDescriptor);
         field.setAccessible(true);
         Lookup own = MethodHandles.lookup();
         target = opcode != 180 && opcode != 178 ? own.unreflectSetter(field) : own.unreflectGetter(field);
      }

      BadlionRuntimeDiagnostics.record(
         "access",
         opcode != 180 && opcode != 181 && opcode != 178 && opcode != 179
            ? "method_linked"
            : "field_linked"
      );
      return new ConstantCallSite(target.asType(type));
   }

   private static Field resolveDirectField(Class<?> owner, String sourceOwner, String sourceMember, String sourceDescriptor) throws Exception {
      String runtimeName = mappings == null ? sourceMember : mappings.runtimeField(sourceOwner, sourceMember);
      String runtimeDescriptor = mappings == null ? sourceDescriptor : mappings.runtimeDescriptor(sourceDescriptor);

      for (Class<?> current = owner; current != null; current = current.getSuperclass()) {
         for (Field field : current.getDeclaredFields()) {
            if ((field.getName().equals(runtimeName) || field.getName().equals(sourceMember)) && Type.getDescriptor(field.getType()).equals(runtimeDescriptor)) {
               return field;
            }
         }
      }

      throw new NoSuchFieldException(
         owner.getName()
            + "."
            + runtimeName
            + " "
            + runtimeDescriptor
      );
   }

   private static Method resolveDirectMethod(Class<?> owner, String sourceOwner, String sourceMember, String sourceDescriptor) throws Exception {
      String runtimeName = mappings == null ? sourceMember : mappings.runtimeMethod(sourceOwner, sourceMember, sourceDescriptor);
      String runtimeDescriptor = mappings == null ? sourceDescriptor : mappings.runtimeMethodDescriptor(sourceDescriptor);

      for (Class<?> current = owner; current != null; current = current.getSuperclass()) {
         for (Method method : current.getDeclaredMethods()) {
            if ((method.getName().equals(runtimeName) || method.getName().equals(sourceMember)) && Type.getMethodDescriptor(method).equals(runtimeDescriptor)) {
               return method;
            }
         }

         Method interfaceMethod = resolveDirectInterfaceMethod(current.getInterfaces(), runtimeName, sourceMember, runtimeDescriptor);
         if (interfaceMethod != null) {
            return interfaceMethod;
         }
      }

      throw new NoSuchMethodException(
         owner.getName() + "." + runtimeName + runtimeDescriptor
      );
   }

   private static Method resolveDirectInterfaceMethod(Class<?>[] interfaces, String runtimeName, String sourceName, String descriptor) {
      for (Class<?> iface : interfaces) {
         for (Method method : iface.getDeclaredMethods()) {
            if ((method.getName().equals(runtimeName) || method.getName().equals(sourceName)) && Type.getMethodDescriptor(method).equals(descriptor)) {
               return method;
            }
         }

         Method inherited = resolveDirectInterfaceMethod(iface.getInterfaces(), runtimeName, sourceName, descriptor);
         if (inherited != null) {
            return inherited;
         }
      }

      return null;
   }

   private static Object invokeInstance(String interfaceName, String methodName, Object receiver, Object[] arguments) throws Throwable {
      return invoke(interfaceName, methodName, receiver, arguments);
   }

   private static Object invokeStatic(String interfaceName, String methodName, Object[] arguments) throws Throwable {
      return invoke(interfaceName, methodName, null, arguments);
   }

   private static Object invoke(String interfaceName, String methodName, Object receiver, Object[] arguments) throws Throwable {
      RuntimeAccess.Spec spec = SPECS.get(simple(interfaceName) + "#" + methodName);
      if (spec == null) {
         throw new NoSuchMethodError(interfaceName + "." + methodName);
      } else if (spec.kind != 3 && spec.kind != 4) {
         Class<?> type = receiver == null ? targetClass(spec.owner) : receiver.getClass();
         if (spec.kind != 0 && spec.kind != 1) {
            Method method = resolveMethod(type, spec, arguments.length);
            return method.invoke(receiver, arguments);
         } else {
            Field field = resolveField(type, spec);
            if (spec.kind == 0) {
               return field.get(Modifier.isStatic(field.getModifiers()) ? null : receiver);
            } else {
               field.set(Modifier.isStatic(field.getModifiers()) ? null : receiver, arguments[0]);
               return null;
            }
         }
      } else {
         RuntimeAccess.Sidecar sidecar = SIDECARS.getOrCreate(receiver);
         if (spec.kind == 3) {
            return sidecar.get(spec.member);
         } else {
            sidecar.set(spec.member, (Boolean)arguments[0]);
            return null;
         }
      }
   }

   private static Field resolveField(Class<?> type, RuntimeAccess.Spec spec) throws Exception {
      String mapped = mappings == null ? spec.member : mappings.runtimeField(spec.owner, spec.member);
      String key = type.getName() + "#" + mapped;
      Field cached = FIELDS.get(key);
      if (cached != null) {
         return cached;
      } else {
         for (Class<?> current = type; current != null; current = current.getSuperclass()) {
            for (String candidate : candidates(mapped, spec.member)) {
               try {
                  Field field = current.getDeclaredField(candidate);
                  field.setAccessible(true);
                  FIELDS.put(key, field);
                  return field;
               } catch (NoSuchFieldException var11) {
               }
            }
         }

         throw new NoSuchFieldException(type.getName() + "." + mapped);
      }
   }

   private static Method resolveMethod(Class<?> type, RuntimeAccess.Spec spec, int arity) throws Exception {
      String mapped = mappings == null ? spec.member : mappings.runtimeMethod(spec.owner, spec.member, spec.descriptor);
      String key = type.getName()
         + "#"
         + mapped
         + "/"
         + arity;
      Method cached = METHODS.get(key);
      if (cached != null) {
         return cached;
      } else {
         for (Class<?> current = type; current != null; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
               if ((method.getName().equals(mapped) || method.getName().equals(spec.member)) && method.getParameterTypes().length == arity) {
                  method.setAccessible(true);
                  METHODS.put(key, method);
                  return method;
               }
            }
         }

         throw new NoSuchMethodException(
            type.getName()
               + "."
               + mapped
               + "/"
               + arity
         );
      }
   }

   private static Class<?> targetClass(String sourceName) throws ClassNotFoundException {
      String runtimeName = mappings == null ? sourceName : mappings.runtimeClass(sourceName);
      ClassLoader loader = InjectionAgent.minecraftClassLoader();
      if (loader == null) {
         loader = Thread.currentThread().getContextClassLoader();
      }

      if (loader == null) {
         loader = ClassLoader.getSystemClassLoader();
      }

      return Class.forName(runtimeName.replace('/', '.'), false, loader);
   }

   private static String[] candidates(String first, String second) {
      return first.equals(second) ? new String[]{first} : new String[]{first, second};
   }

   private static void field(String iface, String call, String owner, String field, int kind) {
      SPECS.put(iface + "#" + call, new RuntimeAccess.Spec(owner, field, "", kind));
   }

   private static void method(String iface, String call, String owner, String method, String descriptor) {
      SPECS.put(iface + "#" + call, new RuntimeAccess.Spec(owner, method, descriptor, 2));
   }

   private static void sidecar(String iface, String call, String field, int kind) {
      SPECS.put(iface + "#" + call, new RuntimeAccess.Spec("", field, "", kind));
   }

   private static String simple(String name) {
      int slash = name.lastIndexOf(47);
      return slash < 0 ? name : name.substring(slash + 1);
   }

   static {
      field(
         "IAccessorC0DPacketCloseWindow",
         "getWindowId",
         "net/minecraft/network/play/client/C0DPacketCloseWindow",
         "windowId",
         0
      );
      field(
         "IAccessorEntity",
         "getFire",
         "net/minecraft/entity/Entity",
         "fire",
         0
      );
      field(
         "IAccessorEntity",
         "getNextStepDistance",
         "net/minecraft/entity/Entity",
         "nextStepDistance",
         0
      );
      field(
         "IAccessorEntity",
         "getIsInWeb",
         "net/minecraft/entity/Entity",
         "isInWeb",
         0
      );
      field(
         "IAccessorEntityArrow",
         "getInGround",
         "net/minecraft/entity/projectile/EntityArrow",
         "inGround",
         0
      );
      field(
         "IAccessorEntityLivingBase",
         "setJumpTicks",
         "net/minecraft/entity/EntityLivingBase",
         "jumpTicks",
         1
      );
      field(
         "IAccessorEntityLivingBase",
         "getJumpTicks",
         "net/minecraft/entity/EntityLivingBase",
         "jumpTicks",
         0
      );
      field(
         "IAccessorEntityPlayer",
         "getItemInUseCountField",
         "net/minecraft/entity/player/EntityPlayer",
         "itemInUseCount",
         0
      );
      field(
         "IAccessorEntityPlayer",
         "setItemInUseCount",
         "net/minecraft/entity/player/EntityPlayer",
         "itemInUseCount",
         1
      );
      field(
         "IAccessorEntityPlayerSP",
         "getLastReportedPosX",
         "net/minecraft/client/entity/EntityPlayerSP",
         "lastReportedPosX",
         0
      );
      field(
         "IAccessorEntityPlayerSP",
         "setLastReportedPosX",
         "net/minecraft/client/entity/EntityPlayerSP",
         "lastReportedPosX",
         1
      );
      field(
         "IAccessorEntityPlayerSP",
         "getLastReportedPosY",
         "net/minecraft/client/entity/EntityPlayerSP",
         "lastReportedPosY",
         0
      );
      field(
         "IAccessorEntityPlayerSP",
         "setLastReportedPosY",
         "net/minecraft/client/entity/EntityPlayerSP",
         "lastReportedPosY",
         1
      );
      field(
         "IAccessorEntityPlayerSP",
         "getLastReportedPosZ",
         "net/minecraft/client/entity/EntityPlayerSP",
         "lastReportedPosZ",
         0
      );
      field(
         "IAccessorEntityPlayerSP",
         "setLastReportedPosZ",
         "net/minecraft/client/entity/EntityPlayerSP",
         "lastReportedPosZ",
         1
      );
      field(
         "IAccessorEntityPlayerSP",
         "getLastReportedYaw",
         "net/minecraft/client/entity/EntityPlayerSP",
         "lastReportedYaw",
         0
      );
      field(
         "IAccessorEntityPlayerSP",
         "setLastReportedYaw",
         "net/minecraft/client/entity/EntityPlayerSP",
         "lastReportedYaw",
         1
      );
      field(
         "IAccessorEntityPlayerSP",
         "getLastReportedPitch",
         "net/minecraft/client/entity/EntityPlayerSP",
         "lastReportedPitch",
         0
      );
      field(
         "IAccessorEntityPlayerSP",
         "setLastReportedPitch",
         "net/minecraft/client/entity/EntityPlayerSP",
         "lastReportedPitch",
         1
      );
      field(
         "IAccessorEntityPlayerSP",
         "setPositionUpdateTicks",
         "net/minecraft/client/entity/EntityPlayerSP",
         "positionUpdateTicks",
         1
      );
      method(
         "IAccessorEntityRenderer",
         "callSetupCameraTransform",
         "net/minecraft/client/renderer/EntityRenderer",
         "setupCameraTransform",
         "(FI)V"
      );
      method(
         "IAccessorEntityRenderer",
         "callLoadShader",
         "net/minecraft/client/renderer/EntityRenderer",
         "loadShader",
         "(Lnet/minecraft/util/ResourceLocation;)V"
      );
      field(
         "IAccessorEntityRenderer",
         "getShaderResourceLocations",
         "net/minecraft/client/renderer/EntityRenderer",
         "shaderResourceLocations",
         0
      );
      field(
         "IAccessorEntityRenderer",
         "getUseShader",
         "net/minecraft/client/renderer/EntityRenderer",
         "useShader",
         0
      );
      field(
         "IAccessorEntityRenderer",
         "setUseShader",
         "net/minecraft/client/renderer/EntityRenderer",
         "useShader",
         1
      );
      field(
         "IAccessorEntityRenderer",
         "getShaderIndex",
         "net/minecraft/client/renderer/EntityRenderer",
         "shaderIndex",
         0
      );
      field(
         "IAccessorEntityRenderer",
         "setShaderIndex",
         "net/minecraft/client/renderer/EntityRenderer",
         "shaderIndex",
         1
      );
      field(
         "IAccessorEntityRenderer",
         "setThirdPersonDistance",
         "net/minecraft/client/renderer/EntityRenderer",
         "thirdPersonDistance",
         1
      );
      field(
         "IAccessorEntityRenderer",
         "getPointedEntity",
         "net/minecraft/client/renderer/EntityRenderer",
         "pointedEntity",
         0
      );
      field(
         "IAccessorEntityRenderer",
         "setPointedEntity",
         "net/minecraft/client/renderer/EntityRenderer",
         "pointedEntity",
         1
      );
      field(
         "IAccessorGuiIngame",
         "getRecordPlaying",
         "net/minecraft/client/gui/GuiIngame",
         "recordPlaying",
         0
      );
      field(
         "IAccessorGuiIngame",
         "getDisplayedTitle",
         "net/minecraft/client/gui/GuiIngame",
         "displayedTitle",
         0
      );
      field(
         "IAccessorGuiIngame",
         "getDisplayedSubTitle",
         "net/minecraft/client/gui/GuiIngame",
         "displayedSubTitle",
         0
      );
      field(
         "IAccessorGuiIngame",
         "setHighlightingItemStack",
         "net/minecraft/client/gui/GuiIngame",
         "highlightingItemStack",
         1
      );
      field(
         "IAccessorGuiIngame",
         "setRemainingHighlightTicks",
         "net/minecraft/client/gui/GuiIngame",
         "remainingHighlightTicks",
         1
      );
      field(
         "IAccessorGuiNewChat",
         "getDrawnChatLines",
         "net/minecraft/client/gui/GuiNewChat",
         "drawnChatLines",
         0
      );
      field(
         "IAccessorGuiNewChat",
         "getScrollPos",
         "net/minecraft/client/gui/GuiNewChat",
         "scrollPos",
         0
      );
      field(
         "IAccessorGuiPlayerTabOverlay",
         "getHeader",
         "net/minecraft/client/gui/GuiPlayerTabOverlay",
         "header",
         0
      );
      field(
         "IAccessorGuiPlayerTabOverlay",
         "getFooter",
         "net/minecraft/client/gui/GuiPlayerTabOverlay",
         "footer",
         0
      );
      method(
         "IAccessorGuiScreen",
         "callMouseClicked",
         "net/minecraft/client/gui/GuiScreen",
         "mouseClicked",
         "(III)V"
      );
      field(
         "IAccessorGuiScreenBook",
         "getBookContents",
         "net/minecraft/client/gui/GuiScreenBook",
         "field_175386_A",
         0
      );
      field(
         "IAccessorGuiTextField",
         "getWidth",
         "net/minecraft/client/gui/GuiTextField",
         "width",
         0
      );
      field(
         "IAccessorGuiTextField",
         "getHeight",
         "net/minecraft/client/gui/GuiTextField",
         "height",
         0
      );
      field(
         "IAccessorGuiTextField",
         "isEnableBackgroundDrawing",
         "net/minecraft/client/gui/GuiTextField",
         "enableBackgroundDrawing",
         0
      );
      field(
         "IAccessorGuiTextField",
         "getLineScrollOffset",
         "net/minecraft/client/gui/GuiTextField",
         "lineScrollOffset",
         0
      );
      field(
         "IAccessorGuiTextField",
         "getCursorPosition",
         "net/minecraft/client/gui/GuiTextField",
         "cursorPosition",
         0
      );
      field(
         "IAccessorGuiTextField",
         "getSelectionEnd",
         "net/minecraft/client/gui/GuiTextField",
         "selectionEnd",
         0
      );
      field(
         "IAccessorItemFood",
         "getAlwaysEdible",
         "net/minecraft/item/ItemFood",
         "alwaysEdible",
         0
      );
      field(
         "IAccessorItemRenderer",
         "getEquippedProgress",
         "net/minecraft/client/renderer/ItemRenderer",
         "equippedProgress",
         0
      );
      field(
         "IAccessorMinecraft",
         "getTimer",
         "net/minecraft/client/Minecraft",
         "timer",
         0
      );
      field(
         "IAccessorMinecraft",
         "getMyNetworkManager",
         "net/minecraft/client/Minecraft",
         "myNetworkManager",
         0
      );
      field(
         "IAccessorMinecraft",
         "getRightClickDelayTimer",
         "net/minecraft/client/Minecraft",
         "rightClickDelayTimer",
         0
      );
      field(
         "IAccessorMinecraft",
         "setRightClickDelayTimer",
         "net/minecraft/client/Minecraft",
         "rightClickDelayTimer",
         1
      );
      field(
         "IAccessorMinecraft",
         "getLeftClickCounter",
         "net/minecraft/client/Minecraft",
         "leftClickCounter",
         0
      );
      field(
         "IAccessorMinecraft",
         "setLeftClickCounter",
         "net/minecraft/client/Minecraft",
         "leftClickCounter",
         1
      );
      method(
         "IAccessorMinecraft",
         "callRightClickMouse",
         "net/minecraft/client/Minecraft",
         "rightClickMouse",
         "()V"
      );
      method(
         "IAccessorMinecraft",
         "callClickMouse",
         "net/minecraft/client/Minecraft",
         "clickMouse",
         "()V"
      );
      method(
         "IAccessorMinecraft",
         "invokeDispatchKeypresses",
         "net/minecraft/client/Minecraft",
         "dispatchKeypresses",
         "()V"
      );
      field(
         "IAccessorMouseHelper",
         "getDeltaX",
         "net/minecraft/util/MouseHelper",
         "deltaX",
         0
      );
      field(
         "IAccessorMouseHelper",
         "getDeltaY",
         "net/minecraft/util/MouseHelper",
         "deltaY",
         0
      );
      field(
         "IAccessorNetworkManager",
         "getPacketListener",
         "net/minecraft/network/NetworkManager",
         "packetListener",
         0
      );
      field(
         "IAccessorPlayerControllerMP",
         "getCurBlockDamageMP",
         "net/minecraft/client/multiplayer/PlayerControllerMP",
         "curBlockDamageMP",
         0
      );
      field(
         "IAccessorPlayerControllerMP",
         "getCurrentBlock",
         "net/minecraft/client/multiplayer/PlayerControllerMP",
         "currentBlock",
         0
      );
      field(
         "IAccessorPlayerControllerMP",
         "setCurBlockDamageMP",
         "net/minecraft/client/multiplayer/PlayerControllerMP",
         "curBlockDamageMP",
         1
      );
      field(
         "IAccessorPlayerControllerMP",
         "getBlockHitDelay",
         "net/minecraft/client/multiplayer/PlayerControllerMP",
         "blockHitDelay",
         0
      );
      field(
         "IAccessorPlayerControllerMP",
         "setBlockHitDelay",
         "net/minecraft/client/multiplayer/PlayerControllerMP",
         "blockHitDelay",
         1
      );
      field(
         "IAccessorPlayerControllerMP",
         "getIsHittingBlock",
         "net/minecraft/client/multiplayer/PlayerControllerMP",
         "isHittingBlock",
         0
      );
      field(
         "IAccessorPlayerControllerMP",
         "setIsHittingBlock",
         "net/minecraft/client/multiplayer/PlayerControllerMP",
         "isHittingBlock",
         1
      );
      method(
         "IAccessorPlayerControllerMP",
         "callSyncCurrentPlayItem",
         "net/minecraft/client/multiplayer/PlayerControllerMP",
         "syncCurrentPlayItem",
         "()V"
      );
      field(
         "IAccessorRenderManager",
         "getRenderPosX",
         "net/minecraft/client/renderer/entity/RenderManager",
         "renderPosX",
         0
      );
      field(
         "IAccessorRenderManager",
         "getRenderPosY",
         "net/minecraft/client/renderer/entity/RenderManager",
         "renderPosY",
         0
      );
      field(
         "IAccessorRenderManager",
         "getRenderPosZ",
         "net/minecraft/client/renderer/entity/RenderManager",
         "renderPosZ",
         0
      );
      field(
         "IAccessorRenderManager",
         "getRenderShadow",
         "net/minecraft/client/renderer/entity/RenderManager",
         "renderShadow",
         0
      );
      field(
         "IAccessorRenderManager",
         "setRenderShadow",
         "net/minecraft/client/renderer/entity/RenderManager",
         "renderShadow",
         1
      );
      field(
         "IAccessorS02PacketChat",
         "getChatComponent",
         "net/minecraft/network/play/server/S02PacketChat",
         "chatComponent",
         0
      );
      field(
         "IAccessorS02PacketChat",
         "setChatComponent",
         "net/minecraft/network/play/server/S02PacketChat",
         "chatComponent",
         1
      );
      field(
         "IAccessorS14PacketEntity",
         "getEntityId",
         "net/minecraft/network/play/server/S14PacketEntity",
         "entityId",
         0
      );
      field(
         "IAccessorS30PacketWindowItems",
         "getWindowId",
         "net/minecraft/network/play/server/S30PacketWindowItems",
         "windowId",
         0
      );
      sidecar(
         "IMixinItemRenderer",
         "setCancelUpdate",
         "cancelUpdate",
         4
      );
      sidecar(
         "IMixinItemRenderer",
         "setCancelReset",
         "cancelReset",
         4
      );
      sidecar(
         "IMixinItemRenderer",
         "isRenderItemInUse",
         "renderItemInUse",
         3
      );
      sidecar(
         "IMixinItemRenderer",
         "setRenderItemInUse",
         "renderItemInUse",
         4
      );
   }

   private static final class IdentityReference extends WeakReference<Object> {
      private final int hash;

      private IdentityReference(Object referent, ReferenceQueue<Object> queue) {
         super(referent, queue);
         this.hash = System.identityHashCode(referent);
      }

      @Override
      public int hashCode() {
         return this.hash;
      }

      @Override
      public boolean equals(Object other) {
         return this == other
            ? true
            : other instanceof RuntimeAccess.IdentityReference && this.get() != null && this.get() == ((RuntimeAccess.IdentityReference)other).get();
      }
   }

   private static final class Sidecar {
      private volatile boolean cancelUpdate;
      private volatile boolean cancelReset;
      private volatile boolean renderItemInUse;

      private Sidecar() {
      }

      private boolean get(String name) {
         if ("cancelUpdate".equals(name)) {
            return this.cancelUpdate;
         } else {
            return "cancelReset".equals(name) ? this.cancelReset : this.renderItemInUse;
         }
      }

      private void set(String name, boolean value) {
         if ("cancelUpdate".equals(name)) {
            this.cancelUpdate = value;
         } else if ("cancelReset".equals(name)) {
            this.cancelReset = value;
         } else {
            this.renderItemInUse = value;
         }
      }
   }

   private static final class Spec {
      private final String owner;
      private final String member;
      private final String descriptor;
      private final int kind;

      private Spec(String owner, String member, String descriptor, int kind) {
         this.owner = owner;
         this.member = member;
         this.descriptor = descriptor;
         this.kind = kind;
      }
   }

   private static final class WeakIdentityMap<V> {
      private final ReferenceQueue<Object> queue = new ReferenceQueue<>();
      private final Map<RuntimeAccess.IdentityReference, V> values = new HashMap<>();

      private WeakIdentityMap() {
      }

      private synchronized V getOrCreate(Object key) {
         if (key == null) {
            throw new NullPointerException("sidecar owner");
         } else {
            this.drain();
            RuntimeAccess.IdentityReference probe = new RuntimeAccess.IdentityReference(key, null);
            V value = this.values.get(probe);
            if (value == null) {
               V created = (V)(new RuntimeAccess.Sidecar());
               this.values.put(new RuntimeAccess.IdentityReference(key, this.queue), created);
               value = created;
            }

            return value;
         }
      }

      private void drain() {
         RuntimeAccess.IdentityReference reference;
         while ((reference = (RuntimeAccess.IdentityReference)this.queue.poll()) != null) {
            this.values.remove(reference);
         }
      }

      private synchronized void clear() {
         this.values.clear();

         while (this.queue.poll() != null) {
         }
      }
   }
}
