// Jade recovery: recovered class name: InjectionAgent
package jade.inject;

import jade.client.runtime.BadlionGradientDiagnostics;
import jade.client.runtime.BadlionHookTransformer;
import jade.client.runtime.BridgeClassLoader;
import jade.client.runtime.ClientClassTransformer;
import jade.client.runtime.RuntimeClassRemapper;
import jade.client.runtime.RuntimeFeatureHierarchy;
import jade.client.runtime.RuntimeMappings;
import jade.client.runtime.VanillaHookTransformer;
import jade.deps.asm.Type;

import jade.deps.loader107.InjectionPaths;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.security.MessageDigest;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.regex.Pattern;
import net.jade.dev.agent.AgentBootstrap;
import net.jade.dev.agent.ResourceBridge;

public final class InjectionAgent {
   private static final Object INSTALL_LOCK = new Object();
   private static final Pattern BUILD_ID = Pattern.compile("^[0-9a-f]{64}$");
   private static volatile Instrumentation instrumentation;
   private static volatile File agentJar;
   private static volatile ClassLoader minecraftLoader;
   private static volatile WeakReference<ClassLoader> detachedMinecraftLoader = new WeakReference<>(null);
   private static volatile ClassLoader hookLoader;
   private static volatile String activeRuntime = "UNKNOWN";
   private static volatile boolean ready;
   private static volatile boolean failed;
   private static volatile InjectionAgent.Lifecycle lifecycle = InjectionAgent.Lifecycle.IDLE;
   private static volatile String loaderBuildIdentity;
   private static volatile boolean loaderSearchInstalled;
   private static volatile Class<?> bootstrapBridgeClass;
   private static final List<ClassFileTransformer> installedTransformers = new ArrayList<>();
   private static final List<Class<?>> transformedTargets = new ArrayList<>();

   private InjectionAgent() {
   }

   public static void premain(String arguments, Instrumentation inst) {
      agentmain(arguments, inst);
   }

   public static void agentmain(String arguments, Instrumentation inst) {
      synchronized (INSTALL_LOCK) {
         String lifecycleFailure = lifecycleStartFailure();
         if (lifecycleFailure != null) {
            InjectionBootstrapContext.discardMemoryState();
            InjectionBootstrapContext.report(
               "FAILED",
               "Agent bootstrap failed: IllegalStateException: "
                  + lifecycleFailure
            );
         } else {
            try {
               beginBootstrap(arguments, inst);
            } catch (Throwable var17) {
               InjectionBootstrapContext.report(
                  "FAILED",
                  "Agent bootstrap failed: " + describeFailure(var17)
               );
               return;
            }

            writeStatus(
               "AGENT_ATTACHED",
               "Java instrumentation agent attached"
            );
            log("agent bootstrap ready");

            try {
               if (!isMemoryLoader() && !agentJar.isFile()) {
                  throw new IllegalStateException(
                     "agent jar does not exist: " + agentJar
                  );
               }

               if (!isMemoryLoader() && !loaderSearchInstalled) {
                  inst.appendToSystemClassLoaderSearch(new JarFile(agentJar));
                  loaderSearchInstalled = true;
               }

               minecraftLoader = findMinecraftClassLoader(inst);
               if (minecraftLoader == null) {
                  throw new IllegalStateException(
                     "Minecraft 1.8.9 classloader was not found"
                  );
               }

               installHookBridge(inst, minecraftLoader);
               String runtime = detectRuntime(inst, minecraftLoader);
               if ("UNKNOWN".equals(runtime)) {
                  throw new IllegalStateException(
                     "unsupported Minecraft namespace/classloader"
                  );
               }

               activeRuntime = runtime;
               if ("BADLION".equals(runtime)) {
                  BadlionGradientDiagnostics.reset();
                  BadlionGradientDiagnostics.record(
                     "runtime",
                     "loader=" + minecraftLoader.getClass().getName()
                  );
               }

               log(
                  "runtime: "
                     + runtime
                     + ", loader: "
                     + minecraftLoader.getClass().getName()
               );
               writeStatus(
                  "INITIALIZING",
                  "Validating Jade startup and runtime"
               );
               writeStatus(
                  "CORE_LOADING",
                  "Publishing the local Jade core into memory"
               );
               prepareLocalCore();

               writeStatus(
                  "CORE_PREPARED",
                  "Jade core is ready in memory"
               );

               writeStatus(
                  "MAPPING_RUNTIME",
                  "Preparing runtime namespace mappings"
               );
               RuntimeMappings runtimeMappings = null;
               if ("FORGE".equals(runtime)) {
                  runtimeMappings = RuntimeMappings.load("/jade/inject/mappings/mcp-srg.srg");
               } else if (usesNotchMappings(runtime)) {
                  runtimeMappings = RuntimeMappings.load(
                     "/jade/inject/mappings/mcp-notch.srg"
                  );
               }

               RuntimeAccess.configure(runtimeMappings);
               if ("BADLION".equals(runtime)) {
                  int accessContracts = RuntimeAccess.validateRequiredMembers(minecraftLoader, runtimeMappings);
                  log(
                     "validated Badlion runtime-access contracts: "
                        + accessContracts
                  );
               }

               ClassFileTransformer featureRemapper = installFeatureRemapper(
                  inst,
                  ResourceBridge.publishedClassNames(),
                  runtimeMappings,
                  "BADLION".equals(runtime)
               );
               installedTransformers.add(featureRemapper);
               writeStatus(
                  "LINKING_CORE",
                  "Linking the in-memory core to Minecraft"
               );
               URL coreUrl = ResourceBridge.resourceUrl();
               hookLoader = new BridgeClassLoader(new URL[]{coreUrl}, minecraftLoader);
               openJavaLangForCoreDefinition(inst, hookLoader);
               Class.forName(
                  "net.jade.dev.agent.transformer.JadeAgentHooks",
                  false,
                  hookLoader
               );
               writeStatus(
                  "DEFINING_CORE",
                  "Defining the core before hook activation"
               );
               defineLocalCore(hookLoader);
               if (!inst.removeTransformer(featureRemapper)) {
                  throw new IllegalStateException(
                     "runtime namespace remapper could not be removed after eager definition"
                  );
               }

               installedTransformers.remove(featureRemapper);
               ClassFileTransformer hooks;
               if ("BADLION".equals(runtime)) {
                  hooks = new BadlionHookTransformer(runtimeMappings);
               } else if (runtimeMappings != null) {
                  hooks = new VanillaHookTransformer(runtimeMappings, false, "FORGE".equals(runtime));
               } else {
                  hooks = new ClientClassTransformer();
               }

               ClientClassTransformer.resetTracking();
               inst.addTransformer(hooks, true);
               installedTransformers.add(hooks);
               writeStatus(
                  "RETRANSFORMING",
                  "Applying runtime hooks to loaded Minecraft classes"
               );
               int retransformed = retransformLoadedTargets(inst, runtime, runtimeMappings);
               if (retransformed == 0) {
                  throw new IllegalStateException(
                     "no loaded Minecraft hook targets were retransformed"
                  );
               }

               writeStatus(
                  "BINDING_HOOKS",
                  "Binding runtime hook dispatch"
               );
               bindAndActivateBootstrapHooks();
               lifecycle = InjectionAgent.Lifecycle.ACTIVE;
               int skippedTargets = ClientClassTransformer.getSkippedTargetCount();
               String skippedSummary = ClientClassTransformer.sKg03();
               int degradedTargets = ClientClassTransformer.getDegradedTargetCount();
               String degradedSummary = ClientClassTransformer.wnx69();
               writeStatus(
                  "TRANSFORMED",
                  "Installed runtime hooks into "
                     + retransformed
                     + " loaded classes"
                     + (
                        skippedTargets == 0
                           ? ""
                           : " ("
                              + skippedTargets
                              + " incompatible optional target(s): "
                              + skippedSummary
                              + ")"
                     )
                     + (
                        degradedTargets == 0
                           ? ""
                           : " ("
                              + degradedTargets
                              + " partially compatible target(s): "
                              + degradedSummary
                              + ")"
                     )
               );
               writeStatus(
                  "INITIALIZING",
                  "Handing Jade initialization to the Minecraft client thread"
               );
               scheduleInitializationOnClientThread(inst);
               log(
                  "transformer installed; initialization queued on the Minecraft client thread"
               );
            } catch (Throwable var16) {
               lifecycle = InjectionAgent.Lifecycle.RESTART_REQUIRED;
               markFailed("Agent installation failed: " + describeFailure(var16));
            }
         }
      }
   }

   private static String lifecycleStartFailure() {
      if (lifecycle == InjectionAgent.Lifecycle.ACTIVE) {
         return "Jade is already active in this Minecraft process";
      } else if (lifecycle == InjectionAgent.Lifecycle.INSTALLING) {
         return "Jade injection is already in progress";
      } else if (lifecycle == InjectionAgent.Lifecycle.DETACHING) {
         return "Jade removal is still in progress";
      } else {
         return lifecycle == InjectionAgent.Lifecycle.RESTART_REQUIRED
            ? "the previous Jade lifecycle failed; restart Minecraft"
            : null;
      }
   }

   public static void beginBootstrap(String arguments, Instrumentation inst) throws Exception {
      String nonce = arguments == null ? "" : arguments.trim();
      InjectionBootstrapContext.beginBootstrap(nonce);
      if (lifecycle == InjectionAgent.Lifecycle.ACTIVE) {
         throw new IllegalStateException(
            "Jade is already active in this Minecraft process"
         );
      } else if (lifecycle == InjectionAgent.Lifecycle.INSTALLING) {
         throw new IllegalStateException(
            "Jade injection is already in progress"
         );
      } else if (lifecycle == InjectionAgent.Lifecycle.DETACHING) {
         throw new IllegalStateException("Jade removal is still in progress");
      } else if (lifecycle == InjectionAgent.Lifecycle.RESTART_REQUIRED) {
         throw new IllegalStateException(
            "the previous Jade lifecycle failed; restart Minecraft"
         );
      } else {
         File candidateJar = isMemoryLoader() ? null : resolveAgentJar(arguments);
         String candidateIdentity = isMemoryLoader() ? null : readLoaderBuildIdentity(candidateJar);
         if (loaderBuildIdentity != null && !loaderBuildIdentity.equals(candidateIdentity)) {
            lifecycle = InjectionAgent.Lifecycle.RESTART_REQUIRED;
            throw new IllegalStateException(
               "the injector loader build changed; restart Minecraft"
            );
         } else {
            if (loaderBuildIdentity == null) {
               loaderBuildIdentity = candidateIdentity;
            }

            lifecycle = InjectionAgent.Lifecycle.INSTALLING;
            ready = false;
            failed = false;
            instrumentation = inst;
            log("runtime started");
            agentJar = candidateJar;
            minecraftLoader = null;
            detachedMinecraftLoader = new WeakReference<>(null);
            hookLoader = null;
            activeRuntime = "UNKNOWN";
            installedTransformers.clear();
            transformedTargets.clear();
            HookIds.clear();

            try {
               ResourceBridge.clear();
            } catch (NoSuchMethodError var6) {
            }

            RuntimeAccess.resetForInjection();
            InjectionPaths.configureForAgent(agentJar);
         }
      }
   }

   private static void installHookBridge(Instrumentation inst, ClassLoader targetLoader) throws Exception {
      if (bootstrapBridgeClass == null) {
         if (targetLoader == null) {
            throw new IllegalStateException("hook bridge loader is unavailable");
         } else {
            openJavaLangForCoreDefinition(inst, targetLoader);
            Map<String, byte[]> classes = readNestedBridgeClasses();
            Method findLoaded = ClassLoader.class
               .getDeclaredMethod("findLoadedClass", String.class);
            Method defineClass = ClassLoader.class
               .getDeclaredMethod(
                  "defineClass",
                  String.class,
                  byte[].class,
                  int.class,
                  int.class,
                  ProtectionDomain.class
               );
            findLoaded.setAccessible(true);
            defineClass.setAccessible(true);
            String traceName = "jade.inject.BootstrapHookBridge$Trace";
            defineBridgeClass(targetLoader, traceName, classes.get(traceName), findLoaded, defineClass);
            String bridgeName = "jade.inject.BootstrapHookBridge";
            Class<?> loaded = defineBridgeClass(targetLoader, bridgeName, classes.get(bridgeName), findLoaded, defineClass);
            if (loaded != null && loaded.getClassLoader() == targetLoader) {
               Class.forName(bridgeName, true, targetLoader);
               bootstrapBridgeClass = loaded;
            } else {
               throw new IllegalStateException(
                  "hook bridge was not defined by the Minecraft loader"
               );
            }
         }
      }
   }

   private static Map<String, byte[]> readNestedBridgeClasses() throws Exception {
      InputStream resource = InjectionAgent.class
         .getClassLoader()
         .getResourceAsStream("jade/inject/bootstrap-bridge.bin");
      if (resource == null) {
         throw new IOException("nested hook bridge is missing");
      } else {
         Map<String, byte[]> classes = new HashMap<>();
         JarInputStream jar = new JarInputStream(resource);

         try {
            byte[] buffer = new byte[4096];

            JarEntry entry;
            while ((entry = jar.getNextJarEntry()) != null) {
               String name = entry.getName();
               if (!entry.isDirectory() && name.endsWith(".class")) {
                  ByteArrayOutputStream output = new ByteArrayOutputStream();

                  int read;
                  while ((read = jar.read(buffer)) >= 0) {
                     output.write(buffer, 0, read);
                  }

                  classes.put(name.substring(0, name.length() - 6).replace('/', '.'), output.toByteArray());
               }
            }
         } finally {
            jar.close();
         }

         if (classes.containsKey("jade.inject.BootstrapHookBridge")
            && classes.containsKey("jade.inject.BootstrapHookBridge$Trace")
            )
          {
            return classes;
         } else {
            throw new IOException(
               "nested hook bridge classes are incomplete"
            );
         }
      }
   }

   private static Class<?> defineBridgeClass(ClassLoader targetLoader, String name, byte[] bytes, Method findLoaded, Method defineClass) throws Exception {
      Object existing = findLoaded.invoke(targetLoader, name);
      if (existing instanceof Class) {
         return (Class<?>)existing;
      } else if (bytes == null) {
         throw new IOException("missing hook bridge class: " + name);
      } else {
         try {
            return (Class<?>)defineClass.invoke(targetLoader, name, bytes, 0, bytes.length, InjectionAgent.class.getProtectionDomain());
         } catch (InvocationTargetException var8) {
            Throwable cause = var8.getCause();
            if (cause instanceof Error) {
               throw (Error)cause;
            } else if (cause instanceof Exception) {
               throw (Exception)cause;
            } else {
               throw var8;
            }
         }
      }
   }

   private static void bindAndActivateBootstrapHooks() throws Exception {
      Class<?> bridge = bootstrapBridgeClass;
      ClassLoader loader = hookLoader;
      if (bridge != null && loader != null) {
         Class<?> hooks = Class.forName(
            "net.jade.dev.agent.transformer.JadeAgentHooks", true, loader
         );
         Method install = bridge.getMethod(
            "install", int.class, MethodHandle.class, String.class
         );
         Map<String, Integer> ids = HookIds.snapshot();
         Set<String> installed = new HashSet<>();

         for (Method method : hooks.getMethods()) {
            if (Modifier.isStatic(method.getModifiers())) {
               String key = method.getName() + Type.getMethodDescriptor(method);
               Integer id = ids.get(key);
               if (id != null) {
                  MethodHandle target = MethodHandles.publicLookup().unreflect(method);
                  install.invoke(null, id, target, key);
                  if (key.startsWith("onFontDraw")
                     || key.startsWith("onChatDrawString")
                     || key.startsWith("onBadlionCachedChatDraw")
                     || key.startsWith("onChatRender")
                     || key.startsWith("onChatLineSet")) {
                     BadlionGradientDiagnostics.record(
                        "bind",
                        "id="
                           + id
                           + " hook="
                           + key
                     );
                  }

                  log(
                     "bound bridge hook id="
                        + id
                        + " hook="
                        + key
                        + " type="
                        + target.type()
                  );
                  installed.add(key);
               }
            }
         }

         if (installed.size() != ids.size()) {
            Set<String> missing = new HashSet<>(ids.keySet());
            missing.removeAll(installed);
            throw new IllegalStateException("unbound typed hook(s): " + missing);
         } else {
            bridge.getMethod("activate").invoke(null);
         }
      } else {
         throw new IllegalStateException("hook bridge is unavailable");
      }
   }

   private static void scheduleInitializationOnClientThread(Instrumentation inst) throws Exception {
      ClassLoader loader = hookLoader;
      if (loader == null) {
         throw new IllegalStateException("hook loader is unavailable");
      } else {
         Class<?> hooks = Class.forName(
            "net.jade.dev.agent.transformer.JadeAgentHooks", true, loader
         );
         final Method initialize = hooks.getMethod("ensureJadeStarted");
         Class<?> minecraftClass = null;

         for (Class<?> candidate : inst.getAllLoadedClasses()) {
            String name = candidate.getName();
            if ((
                  "net.minecraft.client.Minecraft".equals(name)
                     || "ave".equals(name)
               )
               && candidate.getClassLoader() == minecraftLoader) {
               minecraftClass = candidate;
               break;
            }
         }

         if (minecraftClass == null) {
            throw new IllegalStateException(
               "loaded Minecraft client class was not found"
            );
         } else {
            Object minecraft = findMinecraftInstance(minecraftClass);
            if (minecraft == null) {
               throw new IllegalStateException(
                  "Minecraft client instance was not available"
               );
            } else {
               Method scheduler = null;

               for (Method method : minecraftClass.getMethods()) {
                  Class<?>[] parameters = method.getParameterTypes();
                  if (parameters.length == 1
                     && parameters[0] == Runnable.class
                     && (
                        "addScheduledTask".equals(method.getName())
                           || "func_152344_a".equals(method.getName())
                     )) {
                     scheduler = method;
                     break;
                  }
               }

               if (scheduler == null) {
                  for (Method methodx : minecraftClass.getMethods()) {
                     Class<?>[] parameters = methodx.getParameterTypes();
                     if (parameters.length == 1 && parameters[0] == Runnable.class) {
                        scheduler = methodx;
                        break;
                     }
                  }
               }

               if (scheduler == null) {
                  throw new IllegalStateException(
                     "Minecraft client-thread scheduler was not found"
                  );
               } else {
                  scheduler.invoke(
                     minecraft,
                     new Runnable() {
                        @Override
                        public void run() {
                           try {
                              initialize.invoke(null);
                           } catch (Throwable var2) {
                              InjectionAgent.markFailed(
                                 "Jade client-thread initialization failed: "
                                    + InjectionAgent.describeFailure(var2)
                              );
                           }
                        }
                     }
                  );
               }
            }
         }
      }
   }

   private static Object findMinecraftInstance(Class<?> minecraftClass) throws Exception {
      String[] knownNames = new String[]{"getMinecraft", "func_71410_x"};

      for (String name : knownNames) {
         try {
            Method method = minecraftClass.getMethod(name);
            if (Modifier.isStatic(method.getModifiers()) && method.getParameterTypes().length == 0 && minecraftClass.isAssignableFrom(method.getReturnType())) {
               return method.invoke(null);
            }
         } catch (NoSuchMethodException var7) {
         }
      }

      for (Method method : minecraftClass.getMethods()) {
         if (Modifier.isStatic(method.getModifiers()) && method.getParameterTypes().length == 0 && minecraftClass.isAssignableFrom(method.getReturnType())) {
            Object instance = method.invoke(null);
            if (instance != null) {
               return instance;
            }
         }
      }

      return null;
   }

   public static void setHookEnabled(String hook, String descriptor, boolean enabled) {
      Class<?> bridge = bootstrapBridgeClass;
      if (bridge != null) {
         Integer id = HookIds.find(hook, descriptor);
         if (id != null) {
            try {
               bridge.getMethod("setEnabled", int.class, boolean.class)
                  .invoke(null, id, enabled);
               if ("onFontDraw".equals(hook)
                  || "onChatDrawString".equals(hook)) {
                  BadlionGradientDiagnostics.record(
                     "enable",
                     "id="
                        + id
                        + " hook="
                        + hook
                        + " enabled="
                        + enabled
                  );
               }
            } catch (Throwable var6) {
            }
         }
      }
   }

   private static void deactivateBootstrapHooks() {
      Class<?> bridge = bootstrapBridgeClass;
      if (bridge != null) {
         try {
            bridge.getMethod("deactivate").invoke(null);
         } catch (Throwable var2) {
         }
      }
   }

   public static void markReady() {
      if (!ready && !failed) {
         boolean isolated;
         try {
            AgentBootstrap.detachResourceUrl(minecraftLoader);
            isolated = !AgentBootstrap.hasCoreResourceIndex(minecraftLoader);
         } catch (Throwable var2) {
            isolated = false;
            log(
               "resource bridge isolation failed: "
                  + describeFailure(var2)
            );
         }

         System.setProperty("jade.local.ichor.detached", String.valueOf(isolated));
         if (!isolated) {
            markFailed(
               "Jade initialized, but its temporary resource bridge is still attached; restart Minecraft"
            );
         } else {
            ready = true;
            writeStatus(
               "READY",
               "Jade initialized on the Minecraft client thread"
            );
         }
      }
   }

   public static void markFailed(String message) {
      if (!failed) {
         failed = true;
         log(message);
         writeStatus("FAILED", message);
      }
   }

   public static void detach(final BiConsumer<Boolean, String> callback) {
      synchronized (INSTALL_LOCK) {
         if (lifecycle == InjectionAgent.Lifecycle.IDLE) {
            completeDetach(callback, true, "Jade is already removed");
            return;
         }

         if (lifecycle == InjectionAgent.Lifecycle.DETACHING) {
            completeDetach(
               callback, false, "Jade removal is already in progress"
            );
            return;
         }

         if (lifecycle == InjectionAgent.Lifecycle.RESTART_REQUIRED) {
            completeDetach(
               callback, false, "Jade lifecycle failed; restart Minecraft"
            );
            return;
         }

         if (lifecycle != InjectionAgent.Lifecycle.ACTIVE || instrumentation == null) {
            completeDetach(callback, false, "Jade injection is not active");
            return;
         }

         lifecycle = InjectionAgent.Lifecycle.DETACHING;
         deactivateBootstrapHooks();
      }

      Thread worker = new Thread(
         new Runnable() {
            @Override
            public void run() {
               boolean successful = false;

               String message;
               try {
                  try {
                     Thread.sleep(250L);
                  } catch (InterruptedException var6) {
                     Thread.currentThread().interrupt();
                  }

                  InjectionAgent.restoreTransformedTargets();
                  InjectionAgent.releaseInjectionResources();
                  synchronized (InjectionAgent.INSTALL_LOCK) {
                     InjectionAgent.ready = false;
                     InjectionAgent.failed = false;
                     InjectionAgent.lifecycle = InjectionAgent.Lifecycle.IDLE;
                  }

                  successful = true;
                  message = "Jade was removed from this Minecraft instance";
               } catch (Throwable var7) {
                  InjectionAgent.failed = true;
                  InjectionAgent.lifecycle = InjectionAgent.Lifecycle.RESTART_REQUIRED;
                  message = "Jade removal failed; restart Minecraft: "
                     + InjectionAgent.describeFailure(var7);
               }

               InjectionAgent.completeDetach(callback, successful, message);
            }
         },
         "jade-detach"
      );
      worker.setDaemon(true);
      worker.start();
   }

   private static void restoreTransformedTargets() throws Exception {
      Instrumentation inst = instrumentation;
      if (inst != null && inst.isRetransformClassesSupported()) {
         for (ClassFileTransformer transformer : new ArrayList<>(installedTransformers)) {
            inst.removeTransformer(transformer);
         }

         installedTransformers.clear();
         final List<Class<?>> targets;
         synchronized (transformedTargets) {
            targets = new ArrayList<>(transformedTargets);
         }

         if (targets.isEmpty()) {
            throw new IllegalStateException(
               "no transformed targets were recorded"
            );
         } else {
            final Set<String> residualHooks = Collections.newSetFromMap(new ConcurrentHashMap<>());
            ClassFileTransformer verifier = new ClassFileTransformer() {
               @Override
               public byte[] transform(
                  ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer
               ) {
                  if (classBeingRedefined != null && targets.contains(classBeingRedefined) && ClientClassTransformer.bytesContainInjectedHooks(classfileBuffer)) {
                     residualHooks.add(className == null ? classBeingRedefined.getName() : className);
                  }

                  return null;
               }
            };
            inst.addTransformer(verifier, true);

            try {
               for (Class<?> target : targets) {
                  log("restoring hook target: " + target.getName());
                  inst.retransformClasses(target);
                  log("restored hook target: " + target.getName());
               }
            } finally {
               inst.removeTransformer(verifier);
            }

            if (!residualHooks.isEmpty()) {
               throw new IllegalStateException(
                  "Jade callbacks remain in " + residualHooks
               );
            } else {
               transformedTargets.clear();
            }
         }
      } else {
         throw new IllegalStateException(
            "class retransformation is unavailable"
         );
      }
   }

   private static void releaseInjectionResources() {
      HookIds.clear();
      RuntimeAccess.clearForDetach();

       try {
          ResourceBridge.clear();
       } catch (Throwable var6) {
       }

       ClassLoader loader = hookLoader;
      hookLoader = null;
      if (loader instanceof URLClassLoader) {
         try {
            ((URLClassLoader)loader).close();
         } catch (Throwable var4) {
         }
      }

      detachedMinecraftLoader = new WeakReference<>(minecraftLoader);
      minecraftLoader = null;
      agentJar = null;
      activeRuntime = "DETACHED";
      Class<?> bridgeClass = bootstrapBridgeClass;
      if (bridgeClass != null) {
         try {
            bridgeClass.getMethod("clear").invoke(null);
         } catch (Throwable var3) {
         }
      }

      bootstrapBridgeClass = null;
   }

   private static void completeDetach(BiConsumer<Boolean, String> callback, boolean successful, String message) {
      if (callback != null) {
         try {
            callback.accept(successful, message);
         } catch (Throwable var4) {
         }
      }
   }

   public static ClassLoader minecraftClassLoader() {
      ClassLoader loader = minecraftLoader;
      return loader != null ? loader : detachedMinecraftLoader.get();
   }

   public static InjectionAgent.Lifecycle lifecycle() {
      return lifecycle;
   }

   public static boolean isBadlionRuntime() {
      return "BADLION".equals(activeRuntime);
   }

   public static boolean isLunarRuntime() {
      return "LUNAR".equals(activeRuntime);
   }

   public static boolean isForgeRuntime() {
      return "FORGE".equals(activeRuntime);
   }

   public static boolean diagnosticsEnabled() {
      return false;
   }

   public static void reportDiagnostic(String message) {
      log(message);
   }

   public static void recordBadlionGradient(String stage, String detail) {
      BadlionGradientDiagnostics.record(stage, detail);
   }

   public static void recordRuntimeSignal(String category, String name) {
   }

   public static void recordRuntimeFailure(String category, String name, Throwable failure) {
   }

   public static String bootstrapStatusState() {
      return InjectionBootstrapContext.currentState();
   }

   public static String externalVisualNonce() {
      return lifecycle == InjectionAgent.Lifecycle.ACTIVE ? InjectionBootstrapContext.visualNonce() : null;
   }

   public static String bootstrapStatusMessage() {
      return InjectionBootstrapContext.currentMessage();
   }

   private static int retransformLoadedTargets(Instrumentation inst, String runtime, RuntimeMappings mappings) {
      if (!inst.isRetransformClassesSupported()) {
         throw new IllegalStateException(
            "JVM does not support class retransformation"
         );
      } else {
         List<Class<?>> targets = new ArrayList<>();

         for (Class<?> type : inst.getAllLoadedClasses()) {
            if (type != null && isHookTarget(type.getName(), runtime, mappings)) {
               String canonical = canonicalHookName(type.getName(), runtime, mappings);
               if (!inst.isModifiableClass(type)) {
                  if (!ClientClassTransformer.isOptionalTargetClass(canonical)) {
                     throw new IllegalStateException(
                        "unmodifiable essential hook target: "
                           + type.getName()
                     );
                  }

                  ClientClassTransformer.markTargetSkipped(canonical);
                  log(
                     "skipping unmodifiable optional hook target: "
                        + canonical
                  );
               } else {
                  targets.add(type);
               }
            }
         }

         if (targets.isEmpty()) {
            return 0;
         } else {
            int transformed = 0;
            boolean batched;
            try {
               inst.retransformClasses(targets.toArray(new Class<?>[0]));
               batched = true;
            } catch (Throwable batchFailure) {
               log("batched retransform failed, retrying per class: " + describeFailure(batchFailure));
               batched = false;
            }

            for (Class<?> typex : targets) {
               String canonical = canonicalHookName(typex.getName(), runtime, mappings);

               try {
                  if (!batched) {
                     inst.retransformClasses(typex);
                  }

                  if (!ClientClassTransformer.gGlv(canonical)) {
                     throw new IllegalStateException(
                        "hook anchors did not transform: "
                           + canonical
                           + " ("
                           + ClientClassTransformer.sqi5(canonical)
                           + ")"
                     );
                  }

                  transformed++;
                  synchronized (transformedTargets) {
                     if (!transformedTargets.contains(typex)) {
                        transformedTargets.add(typex);
                     }
                  }
               } catch (Throwable var11) {
                  log(
                     "retransform failed: "
                        + typex.getName()
                        + " "
                        + describeFailure(var11)
                  );
                  if (!canQuarantineOptionalTarget(runtime, canonical, var11)) {
                     throw new IllegalStateException(
                        "hook retransformation failed for " + typex.getName(),
                        var11
                     );
                  }

                  ClientClassTransformer.markTargetSkipped(canonical);
                  log(
                     "skipping incompatible optional Forge hook target: "
                        + canonical
                  );
               }
            }

            return transformed;
         }
      }
   }

   private static String canonicalHookName(String runtimeName, String runtime, RuntimeMappings mappings) {
      String canonical = runtimeName.replace('.', '/');
      return mappings != null ? mappings.sourceClass(canonical) : canonical;
   }

   static boolean isBadlionCompatibilityFailure(Throwable failure) {
      for (Throwable current = failure; current != null; current = current.getCause()) {
         if (current instanceof LinkageError || current instanceof UnsupportedOperationException) {
            return true;
         }
      }

      return false;
   }

   static boolean canQuarantineOptionalTarget(String runtime, String canonical, Throwable failure) {
      return failure != null && ClientClassTransformer.isOptionalTargetClass(canonical);
   }

   private static boolean isHookTarget(String name, String runtime, RuntimeMappings mappings) {
      String canonical;
      if (mappings != null) {
         canonical = mappings.sourceClass(name.replace('.', '/'));
      } else {
         canonical = name.replace('.', '/');
      }

      Set<String> targets = "BADLION".equals(runtime)
         ? ClientClassTransformer.badlionTargetClasses()
         : ("FORGE".equals(runtime) ? ClientClassTransformer.EfYf() : ClientClassTransformer.vanillaTargetClasses());
      return targets.contains(canonical);
   }

   private static ClassLoader findMinecraftClassLoader(Instrumentation inst) {
      ClassLoader fallback = null;

      for (Class<?> type : inst.getAllLoadedClasses()) {
         if (type != null) {
            String name = type.getName();
            if ("net.minecraft.client.Minecraft".equals(name)
               || "ave".equals(name)) {
               return type.getClassLoader() != null ? type.getClassLoader() : ClassLoader.getSystemClassLoader();
            }

            if ((
                  name.startsWith("com.moonsworth.lunar.genesis.")
                     || name.startsWith("net.minecraft.launchwrapper.")
               )
               && type.getClassLoader() != null) {
               fallback = type.getClassLoader();
            }
         }
      }

      return fallback;
   }

   private static String detectRuntime(Instrumentation inst, ClassLoader loader) {
      Set<String> loadedClassNames = new HashSet<>();

      for (Class<?> type : inst.getAllLoadedClasses()) {
         if (type != null) {
            loadedClassNames.add(type.getName());
         }
      }

      String badlionVersion = null;

      try {
         badlionVersion = System.getProperty("badlion.version");
      } catch (Throwable var7) {
      }

      return detectRuntimeNames(loader.getClass().getName(), loadedClassNames, badlionVersion);
   }

   static String detectRuntimeNames(String loaderName, Iterable<String> loadedClassNames, String badlionVersion) {
      String normalizedLoader = loaderName == null ? "" : loaderName.toLowerCase(Locale.ROOT);
      boolean lunar = normalizedLoader.contains("ichor");
      boolean badlion = normalizedLoader.contains("badlion")
         || normalizedLoader.contains("blclient")
         || badlionVersion != null && !badlionVersion.trim().isEmpty();
      boolean forge = false;
      boolean notch = false;

      for (String name : loadedClassNames) {
         if (name != null) {
            if (name.startsWith("net.badlion.client.")) {
               badlion = true;
            }

            if (name.startsWith("com.moonsworth.")) {
               lunar = true;
            }

            if (name.startsWith("net.minecraftforge.")
               || name.startsWith("cpw.mods.fml.")) {
               forge = true;
            }

            if ("ave".equals(name)) {
               notch = true;
            }
         }
      }

      if (badlion) {
         return "BADLION";
      } else if (lunar) {
         return "LUNAR";
      } else if (!forge && !normalizedLoader.contains("launchclassloader")) {
         return notch
            ? "VANILLA"
            : "UNKNOWN";
      } else {
         return "FORGE";
      }
   }

   static boolean usesNotchMappings(String runtime) {
      return "VANILLA".equals(runtime)
         || "BADLION".equals(runtime);
   }

   private static void prepareLocalCore() throws Exception {
      File jar = agentJar;
      if (jar == null || !jar.isFile()) {
         try {
            CodeSource source = InjectionAgent.class.getProtectionDomain().getCodeSource();
            if (source != null) {
               jar = new File(source.getLocation().toURI()).getAbsoluteFile();
            }
         } catch (Throwable var3) {
         }
      }

      if (jar == null || !jar.isFile()) {
         throw new IllegalStateException("local core jar is unavailable: " + jar);
      } else {
         Map<String, byte[]> classes = new LinkedHashMap<>();
         Map<String, byte[]> resources = new LinkedHashMap<>();
         MessageDigest digest = MessageDigest.getInstance("SHA-256");

         try (JarFile file = new JarFile(jar)) {
            for (Enumeration<JarEntry> entries = file.entries(); entries.hasMoreElements();) {
               JarEntry entry = entries.nextElement();
               if (entry.isDirectory()) {
                  continue;
               }

               String path = entry.getName();
               ByteArrayOutputStream output = new ByteArrayOutputStream();

               try (InputStream stream = file.getInputStream(entry)) {
                  byte[] buffer = new byte[8192];

                  for (int read = stream.read(buffer); read >= 0; read = stream.read(buffer)) {
                     output.write(buffer, 0, read);
                  }
               }

               byte[] bytes = output.toByteArray();
               digest.update(path.getBytes(StandardCharsets.UTF_8));
               digest.update(bytes);
               if (path.endsWith(".class")) {
                  String name = path.substring(0, path.length() - 6).replace('/', '.');
                  if (BridgeClassLoader.isCoreClass(name)) {
                     classes.put(name, bytes);
                  }
               } else {
                  resources.put(path, bytes);
               }
            }
         }

         String buildId = readLoaderBuildIdentity(jar);
         String sha256 = hex(digest.digest());
         ResourceBridge.publishCore(classes, resources, buildId, sha256);
         log(
            "published local core: "
               + classes.size()
               + " classes, "
               + resources.size()
               + " resources, build="
               + buildId
         );
      }
   }

   private static void defineLocalCore(ClassLoader loader) throws Exception {
      int defined = 0;
      for (String name : ResourceBridge.publishedClassNames()) {
         String binary = name.replace('/', '.');
         try {
            Class<?> type = Class.forName(binary, false, loader);
            if (type.getClassLoader() != loader) {
               throw new IllegalStateException("core class escaped the hook loader: " + binary);
            }
            defined++;
         } catch (LinkageError | ClassNotFoundException cause) {
            throw new IllegalStateException("local core class failed to define: " + name, cause);
         }
      }

      log("defined " + defined + " local core classes through the hook loader");
   }

   private static String hex(byte[] bytes) {
      StringBuilder builder = new StringBuilder(bytes.length * 2);

      for (byte value : bytes) {
         builder.append(Character.forDigit(value >> 4 & 15, 16));
         builder.append(Character.forDigit(value & 15, 16));
      }

      return builder.toString();
   }

   private static ClassFileTransformer installFeatureRemapper(
      Instrumentation inst, Set<String> deliveredClasses, RuntimeMappings mappings, boolean bridgeMinecraftMembers
   ) throws Exception {
      Set<String> classes = new HashSet<>();
      Map<String, byte[]> classBytes = new HashMap<>();

      for (String name : deliveredClasses) {
         if (name != null
            && name.indexOf(46) < 0
            && (
               name.startsWith("jade/")
                  || name.endsWith("/JadeAgentHooks")
            )) {
            classes.add(name);
            byte[] bytes = ResourceBridge.readResource(name + ".class");
            if (bytes == null) {
               throw new IllegalStateException(
                  "prepared class is missing: " + name
               );
            }

            classBytes.put(name, bytes);
         }
      }

      RuntimeFeatureHierarchy hierarchy;
      try {
         hierarchy = RuntimeFeatureHierarchy.fromClassBytes(classBytes);
      } finally {
         for (byte[] bytes : classBytes.values()) {
            Arrays.fill(bytes, (byte)0);
         }

         classBytes.clear();
      }

      RuntimeClassRemapper var15 = new RuntimeClassRemapper(mappings, classes, hierarchy, bridgeMinecraftMembers);
      inst.addTransformer(var15, true);
      log(
         "installed runtime namespace remapper for "
            + classes.size()
            + " feature classes"
      );
      return var15;
   }

   private static File resolveAgentJar(String arguments) {
      String injectedPath = InjectionBootstrapContext.currentAgentJarPath();
      if (injectedPath != null && !injectedPath.trim().isEmpty()) {
         File candidate = new File(injectedPath.trim());
         if (candidate.isFile()) {
            return candidate.getAbsoluteFile();
         }
      }

      if (arguments != null && !arguments.trim().isEmpty()) {
         File candidate = new File(arguments.trim());
         if (candidate.isFile()) {
            return candidate.getAbsoluteFile();
         }
      }

      try {
         CodeSource source = InjectionAgent.class.getProtectionDomain().getCodeSource();
         if (source != null) {
            return new File(source.getLocation().toURI()).getAbsoluteFile();
         }
      } catch (Throwable var3) {
      }

      return new File("jade.jar").getAbsoluteFile();
   }

   private static boolean isMemoryLoader() {
      CodeSource source = InjectionAgent.class.getProtectionDomain().getCodeSource();
      return source != null
         && source.getLocation() != null
         && "jade-loader-memory".equals(source.getLocation().getProtocol());
   }

   private static String readLoaderBuildIdentity(File jar) throws Exception {
      if (jar != null && jar.isFile()) {
         JarFile input = new JarFile(jar);

         String var6;
         try {
            JarEntry entry = input.getJarEntry("META-INF/jade-build-id");
            if (entry == null) {
               throw new SecurityException("agent loader build ID is missing");
            }

            InputStream stream = input.getInputStream(entry);

            try {
               BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
               String identity = reader.readLine();
               identity = identity == null ? "" : identity.trim().toLowerCase(Locale.ROOT);
               if (!BUILD_ID.matcher(identity).matches()) {
                  throw new SecurityException(
                     "agent loader build ID is invalid"
                  );
               }

               var6 = identity;
            } finally {
               stream.close();
            }
         } finally {
            input.close();
         }

         return var6;
      } else {
         throw new IllegalStateException(
            "agent jar is unavailable for compatibility verification"
         );
      }
   }

   private static void writeStatus(String state, String message) {
      InjectionBootstrapContext.report(state, message);
   }

   private static void log(String message) {
      System.out.println("[Jade] " + message);
   }

   public static String describeFailure(Throwable failure) {
      StringBuilder description = new StringBuilder();

      for (Throwable current = failure; current != null; current = current.getCause()) {
         if (description.length() > 0) {
            description.append(" caused by ");
         }

         description.append(current.getClass().getName())
            .append(": ")
            .append(String.valueOf(current.getMessage()));
         if (current.getCause() == null || current.getCause() == current) {
            break;
         }
      }

      return description.toString();
   }

   private static void openJavaLangForCoreDefinition(Instrumentation inst, ClassLoader targetLoader) {
      if (inst != null && targetLoader != null) {
         try {
            Class<?> moduleClass = Class.forName("java.lang.Module");
            Method getModule = Class.class.getMethod("getModule");
            Method getUnnamedModule = ClassLoader.class.getMethod("getUnnamedModule");
            Object javaBaseModule = getModule.invoke(Object.class);
            Object targetModule = getUnnamedModule.invoke(targetLoader);
            Object agentModule = getModule.invoke(InjectionAgent.class);
            Method redefineModule = Instrumentation.class
               .getMethod(
                  "redefineModule",
                  moduleClass,
                  Set.class,
                  Map.class,
                  Map.class,
                  Set.class,
                  Map.class
               );
            Set<Object> recipients = new HashSet<>();
            recipients.add(targetModule);
            recipients.add(agentModule);
            Map<String, Set<Object>> extraOpens = new HashMap<>();
            extraOpens.put("java.lang", recipients);
            redefineModule.invoke(
               inst, javaBaseModule, Collections.emptySet(), Collections.emptyMap(), extraOpens, Collections.emptySet(), Collections.emptyMap()
            );
         } catch (ClassNotFoundException var11) {
         } catch (Throwable var12) {
            throw new IllegalStateException(
               "could not define memory-only core", var12
            );
         }
      } else {
         throw new IllegalStateException(
            "core definition module context is unavailable"
         );
      }
   }

   public static enum Lifecycle {
      IDLE,
      INSTALLING,
      ACTIVE,
      DETACHING,
      RESTART_REQUIRED;
   }

}
