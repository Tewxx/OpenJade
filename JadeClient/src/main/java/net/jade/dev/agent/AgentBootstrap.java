package net.jade.dev.agent;

import jade.client.hook.AccessorConflictPatcher;
import jade.client.hook.BakeCacheWatcher;
import jade.client.hook.ForgeEventPatcher;
import jade.client.hook.MixinProxyPatcher;
import jade.client.hook.MixinScanner;
import jade.client.hook.MixinWarmup;
import jade.client.hook.SrgRemapper;
import jade.client.hook.MixinRegistrationEntry;
import jade.deps.asm.AnnotationVisitor;
import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassVisitor;
import jade.deps.asm.ClassWriter;
import jade.deps.asm.MethodVisitor;
import jade.deps.asm.Type;
import jade.deps.asm.commons.ClassRemapper;
import jade.deps.asm.commons.Remapper;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;


import jade.deps.loader107.CoreCacheMaterial;

import jade.deps.loader107.LocalCoreLoader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import net.jade.dev.agent.cache.GenesisBakeCacheBridge;

public class AgentBootstrap {
   private static final String MODS_PROPERTY = "lunar.agent.bootstrap.mods";
   private static final String JAR_PATHS_PROPERTY = "lunar.agent.bootstrap.jar.paths";
   private static final String PwLvnS = "Lnet/minecraftforge/fml/common/Mod;";
   private static final String KuZ = "org.spongepowered.asm.mixin.transformer.MixinProxyImpl";
   private static final long BAKE_CACHE_SCAN_INTERVAL_MILLIS = 100L;
   private static final long umk = 120L;
   private static final long REGISTRATION_TIMEOUT_SECONDS = 60L;
   private static final BakeCacheWatcher bakeCacheWatcher = new BakeCacheWatcher(resolveBakeCacheDirectory(), 100L);
   private static final MixinWarmup mixinWarmup = new MixinWarmup();
   private static final Object registrationLock = new Object();
   private static volatile Thread RrQ;
   private static volatile ClassLoader classLoader;
   private static final Map<ClassLoader, Boolean> KNt = Collections.synchronizedMap(new WeakHashMap<>());
   private static volatile AgentBootstrap.MixinRegistrationContext mixinRegistrationContext;
   private static volatile Instrumentation instrumentation;
   private static final String YuU = "Lorg/spongepowered/asm/mixin/Mixin;";
   private static final String ACCESSOR_ANNOTATION_DESC = "Lorg/spongepowered/asm/mixin/gen/Accessor;";
   private static final String INVOKER_ANNOTATION_DESC = "Lorg/spongepowered/asm/mixin/gen/Invoker;";
   private static final int MAX_MIXIN_PRIORITY = 100;

   public static boolean detachResourceUrl(ClassLoader var0) {
      if (var0 == null) {
         return false;
      } else {
         URL var1 = ResourceBridge.resourceUrl();
         if (yaneyD(var0, var1, new IdentityHashMap<>(), 4)) {
            return true;
         } else {
            try {
               cmJyo("java.net");
               cmJyo("jdk.internal.loader");
               Class var2 = Class.forName("java.net.URLClassLoader");
               if (!var2.isAssignableFrom(var0.getClass())) {
                  return false;
               } else {
                  Field var3 = var2.getDeclaredField("ucp");
                  var3.setAccessible(true);
                  return yaneyD(var3.get(var0), var1, new IdentityHashMap<>(), 6);
               }
            } catch (Throwable var4) {
               return false;
            }
         }
      }
   }

   public static boolean hasCoreResourceIndex(ClassLoader var0) {
      if (var0 == null) {
         return false;
      } else {
         try {
            Enumeration var1 = var0.getResources("META-INF/jade-core-resource-index");

            while (var1.hasMoreElements()) {
               URL var2 = (URL)var1.nextElement();
               if (var2 != null
                  && "jade-memory".equalsIgnoreCase(var2.getProtocol())
                  && "core".equalsIgnoreCase(var2.getHost())) {
                  return true;
               }
            }

            return false;
         } catch (Throwable var3) {
            return true;
         }
      }
   }

   private static boolean yaneyD(Object var0, URL var1, IdentityHashMap<Object, Boolean> var2, int var3) {
      if (var0 != null && var3 >= 0 && var2.put(var0, Boolean.TRUE) == null) {
         if (var0 instanceof Collection) {
            Collection var14 = (Collection)var0;
            if (var14.remove(var1)) {
               return true;
            } else {
               Iterator var16 = var14.iterator();

               while (var16.hasNext()) {
                  Object var19 = var16.next();
                  if (containsUrlReference(var19, var1, new IdentityHashMap<>(), var3 - 1)) {
                     var16.remove();
                     return true;
                  }
               }

               for (Object var22 : new ArrayList(var14)) {
                  if (yaneyD(var22, var1, var2, var3 - 1)) {
                     return true;
                  }
               }

               return false;
            }
         } else if (var0 instanceof Map) {
            boolean var13 = false;
            Iterator var15 = ((Map)var0).entrySet().iterator();

            while (var15.hasNext()) {
               Entry var17 = (Entry)var15.next();
               if (var1.equals(var17.getKey()) || var1.equals(var17.getValue())) {
                  var15.remove();
                  var13 = true;
               }
            }

            if (var13) {
               return true;
            } else {
               for (Object var21 : new ArrayList(((Map)var0).values())) {
                  if (yaneyD(var21, var1, var2, var3 - 1)) {
                     return true;
                  }
               }

               return false;
            }
         } else {
            Class var4 = var0.getClass();
            String var5 = var4.getName();
            if (!var5.startsWith("java.")
               && !var5.startsWith("sun.")) {
               for (Class var6 = var4; var6 != null; var6 = var6.getSuperclass()) {
                  for (Field var10 : var6.getDeclaredFields()) {
                     if (!Modifier.isStatic(var10.getModifiers()) && !var10.getType().isPrimitive()) {
                        try {
                           var10.setAccessible(true);
                           if (yaneyD(var10.get(var0), var1, var2, var3 - 1)) {
                              return true;
                           }
                        } catch (Throwable var12) {
                        }
                     }
                  }
               }

               return false;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private static boolean containsUrlReference(Object var0, URL var1, IdentityHashMap<Object, Boolean> var2, int var3) {
      if (var0 == null || var3 < 0 || var2.put(var0, Boolean.TRUE) != null) {
         return false;
      } else if (var1.equals(var0)) {
         return true;
      } else {
         String var4 = var0.getClass().getName();
         if (!var4.startsWith("java.")
            && !var4.startsWith("sun.")) {
            for (Class var5 = var0.getClass(); var5 != null; var5 = var5.getSuperclass()) {
               for (Field var9 : var5.getDeclaredFields()) {
                  if (!Modifier.isStatic(var9.getModifiers()) && !var9.getType().isPrimitive()) {
                     try {
                        var9.setAccessible(true);
                        if (containsUrlReference(var9.get(var0), var1, var2, var3 - 1)) {
                           return true;
                        }
                     } catch (Throwable var11) {
                     }
                  }
               }
            }

            return false;
         } else {
            return false;
         }
      }
   }

   private static void KqkAl() throws IOException {
      bakeCacheWatcher.prepareCacheProtection();
   }

   private static void startBakeCacheWatcher() {
      bakeCacheWatcher.startWatcher();
   }

   private static void failBakeCacheBootstrap() {
      bakeCacheWatcher.cleanupAfterBootstrapFailure("failed Jade bootstrap");
   }

   public static void onGenesisBakeCacheHooksInstalled(byte[] var0) {
      bakeCacheWatcher.setGenesisHookFingerprint(var0);
   }

   public static void onGenesisBakeCacheHookFailure(String var0) {
      bakeCacheWatcher.reportEncryptedCacheUnavailable(var0);
   }

   private static Path resolveBakeCacheDirectory() {
      String var0 = System.getProperty("lunar.dataDir");
      Path var1;
      if (var0 != null && !var0.trim().isEmpty()) {
         var1 = Paths.get(var0.trim());
      } else {
         var1 = Paths.get(System.getProperty("user.home"), new String[]{".lunarclient"});
      }

      return var1.toAbsolutePath()
         .normalize()
         .resolve("offline")
         .resolve("multiver")
         .resolve("cache");
   }

   public static void premain(String var0, Instrumentation var1) throws Exception {
      instrumentation = var1;
      KqkAl();
      {
         try {
            LocalCoreLoader.prepareLocalPayload();
         } catch (Throwable var27) {
            System.err
               .println(
                  "[Jade] Payload preparation failed - agent will not inject mods. Reason: "
                     + describeThrowable("prepare_exception", var27)
               );
            failBakeCacheBootstrap();
            return;
         }

         CoreCacheMaterial var4 = null;
         byte[] var5 = null;

         try {
            var4 = LocalCoreLoader.getLocalPayload();
            if (var4 == null) {
               throw new IllegalStateException(
                  "cache material is missing"
               );
            }

            String var6 = var4.getCacheBinding();
            int var7 = var4.getCacheEpoch();
            var5 = var4.consumeCacheKey();
            bakeCacheWatcher.installCacheBinding(var5, var6, var7);
            var5 = null;
            GenesisBakeCacheBridge.install(bakeCacheWatcher, mixinWarmup, ResourceBridge.resourceUrl());
         } catch (Throwable var31) {
            if (var5 != null) {
               Arrays.fill(var5, (byte)0);
            }

            System.err
               .println(
                  "[Jade] Cache setup failed; agent will not inject mods. Reason: "
                     + describeThrowable("cache_material_exception", var31)
               );
            failBakeCacheBootstrap();
            return;
         } finally {
            if (var4 != null) {
               var4.clearSensitiveMaterial();
            }
         }

         System.out.println("[Mod-Agent] premain fired, config: " + var0);
         File var33 = new File(AgentBootstrap.class.getProtectionDomain().getCodeSource().getLocation().toURI());
         System.out.println("[Mod-Agent] Agent JAR located at: " + var33);
         String var34 = loadAgentConfig(var0, var33);
         if (var34 == null) {
            System.out.println("[Mod-Agent] No config available (no path arg, no bundled /agent-mods.json), nothing to inject.");
            failBakeCacheBootstrap();
         } else {
            List var8 = UxqxtP(var34, var33);
            if (var8.isEmpty()) {
               System.out.println("[Mod-Agent] No mods found in config.");
               failBakeCacheBootstrap();
            } else {
               startBakeCacheWatcher();

               for (MixinRegistrationEntry var10 : (java.lang.Iterable<MixinRegistrationEntry>) (java.lang.Iterable<?>) (var8)) {
                  if (!isBlank(var10.getPropertyName())) {
                     System.setProperty(
                        var10.getPropertyName(), "true"
                     );
                     System.out.println("[Mod-Agent] Set property: " + var10.getPropertyName());
                  }
               }

               String var35 = buildModListProperty(var8);
               if (!isBlank(var35)) {
                  System.setProperty("lunar.agent.bootstrap.mods", var35);
                  System.out.println("[Mod-Agent] Mod list property set: " + var35);
               }

               StringBuilder var36 = new StringBuilder();

               for (MixinRegistrationEntry var12 : (java.lang.Iterable<MixinRegistrationEntry>) (java.lang.Iterable<?>) (var8)) {
                  File var13 = new File(var12.getJarPath());
                  if (var13.exists()) {
                     if (var36.length() != 0) {
                        var36.append("::");
                     }

                     var36.append(var13.getAbsolutePath());
                  }
               }

               if (var36.length() != 0) {
                  System.setProperty(
                     "lunar.agent.bootstrap.jar.paths", var36.toString()
                  );
                  System.out.println("[Mod-Agent] JAR paths property set: " + var36);
               }

               Set var37 = PwsFy(var8);
               var37.addAll(ResourceBridge.publishedClassNames());
               System.out
                  .println(
                     "[Mod-Agent] Tracking "
                        + var37.size()
                        + " mod classes for transformation."
                  );
               HashMap var38 = new HashMap();
               HashMap var39 = new HashMap();
               loadSrgMappings(var38, var39);
               Map var14 = planAccessorRenames(var8);
               System.out.println("[Mod-Agent] Accessor renames planned: " + var14.size());
               SrgRemapper var15 = new SrgRemapper(var37, var38, var39);
               mixinRegistrationContext = new AgentBootstrap.MixinRegistrationContext(var1, var8, var33);
               var1.addTransformer(new MixinProxyPatcher(), true);
               var1.addTransformer(mixinWarmup, true);
               var1.addTransformer(var15, true);
               var1.addTransformer(new MixinScanner(var37), true);
               var1.addTransformer(new ForgeEventPatcher(var37), true);
               var1.addTransformer(new AccessorConflictPatcher(var37, var14), true);
               System.out.println("[Mod-Agent] Transformers registered.");
               Thread var16 = new Thread(() -> AgentBootstrap.prepareMixinResources(var8, var38, var39, var14, var37, var33));
               var16.setDaemon(true);
               var16.start();

               try {
                  if (!mixinWarmup.awaitPreparation(120L, TimeUnit.SECONDS)) {
                     failMixinRegistration(
                        "Mixin resource preparation did not complete: "
                           + mixinWarmup.failureReason()
                     );
                  }

                  synchronized (registrationLock) {
                     if (!mixinWarmup.isRegistrationComplete()) {
                        verifyNoMixinTargetsPreloaded(var1);
                     }
                  }

                  if (isClassLoaded(
                        var1,
                        "org.spongepowered.asm.mixin.transformer.MixinProxyImpl"
                     )
                     && !mixinWarmup.awaitRegistration(60L, TimeUnit.SECONDS)) {
                     failMixinRegistration(
                        "Ichor Mixin proxy loaded without completing the registration hook"
                     );
                  }
               } catch (InterruptedException var30) {
                  Thread.currentThread().interrupt();
                  failMixinRegistration(
                     "Interrupted while preparing Mixin resources"
                  );
               }
            }
         }
      }
   }

   public static void onIchorMixinPipelineReady(String var0, ClassLoader var1) {
      if ("MIXIN".equals(var0)) {
         try {
            if (!mixinWarmup.awaitPreparation(120L, TimeUnit.SECONDS)) {
               failMixinRegistration(
                  "Mixin resources were not ready at environment startup: "
                     + mixinWarmup.failureReason()
               );
            }

            registerMixinConfigs(var1);
         } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
            failMixinRegistration("Interrupted during synchronous Mixin registration");
         } catch (Throwable var5) {
            String var3 = describeThrowable(
               "synchronous_mixin_registration_exception", var5
            );
            mixinWarmup.markFailed(var3);
            failBakeCacheBootstrap();
            if (var5 instanceof Error) {
               throw (Error)var5;
            }

            throw new IllegalStateException(var3, var5);
         }
      }
   }

   public static void onIchorMixinPipelineRejected(String var0) {
      String var1 = var0 == null ? "Ichor Mixin pipeline hook rejected" : var0;
      mixinWarmup.markFailed(var1);
      failBakeCacheBootstrap();
   }

   private static void registerMixinConfigs(ClassLoader var0) throws Exception {
      if (var0 == null) {
         throw new IllegalArgumentException("mixinLoader");
      } else {
         synchronized (registrationLock) {
            if (classLoader != null && classLoader != var0) {
               throw new IllegalStateException(
                  "Ichor MIXIN stage loader changed during registration"
               );
            } else if (!mixinWarmup.isRegistrationComplete()) {
               if (RrQ != Thread.currentThread()) {
                  if (mixinWarmup.failureReason() != null) {
                     throw new IllegalStateException(mixinWarmup.failureReason());
                  } else {
                     AgentBootstrap.MixinRegistrationContext var2 = mixinRegistrationContext;
                     if (var2 == null) {
                        throw new IllegalStateException(
                           "Mixin registration context missing"
                        );
                     } else {
                        if (classLoader == null) {
                           classLoader = var0;
                        }

                        RrQ = Thread.currentThread();

                        try {
                           mixinWarmup.setRegistrarThread(Thread.currentThread());
                           System.out.println("[Mod-Agent] Found Mixin loader: " + var0.getClass().getName());
                           openJavaLangToLoader(AgentBootstrap.MixinRegistrationContext.getInstrumentation(var2), var0);
                           Class var3 = Class.forName(
                              "org.spongepowered.asm.mixin.Mixins",
                              true,
                              var0
                           );
                           Method var4 = var3.getMethod(
                              "addConfiguration", String.class
                           );
                           LinkedHashSet var5 = new LinkedHashSet();

                           for (MixinRegistrationEntry var7 : (java.lang.Iterable<MixinRegistrationEntry>) (java.lang.Iterable<?>) (AgentBootstrap.MixinRegistrationContext.LxRbr(var2))) {
                              File var8 = new File(var7.getJarPath());
                              if (!var8.exists()) {
                                 System.out.println("[Mod-Agent] JAR not found, skipping: " + var7.getJarPath());
                              } else if (!var8.getCanonicalFile().equals(AgentBootstrap.MixinRegistrationContext.getAgentJar(var2).getCanonicalFile())) {
                                 var5.add(var8.getCanonicalFile().toURI().toURL());
                              }
                           }

                           if (!var5.isEmpty()) {
                              Method var15 = var0.getClass()
                                 .getMethod("addURL", URL.class);

                              for (URL var19 : (java.lang.Iterable<URL>) (java.lang.Iterable<?>) (var5)) {
                                 var15.invoke(var0, var19);
                                 System.out.println("[Mod-Agent] URL added to IchorClassLoader: " + var19);
                              }

                              System.setProperty(
                                 "jade.local.ichor.urls", joinUrlsByNewline(var5)
                              );
                           }

                           var4.invoke(null, "mixins.agent.json");
                           System.out.println("[Mod-Agent] Agent bootstrap mixin registered.");

                           for (MixinRegistrationEntry var18 : (java.lang.Iterable<MixinRegistrationEntry>) (java.lang.Iterable<?>) (AgentBootstrap.MixinRegistrationContext.LxRbr(var2))) {
                              if (!isBlank(var18.getMixinConfig())) {
                                 var4.invoke(null, var18.getMixinConfig());
                                 System.out.println("[Mod-Agent] Mixin config registered: " + var18.getMixinConfig());
                              }
                           }

                           mixinWarmup.markRegistrationComplete();
                        } finally {
                           RrQ = null;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isClassLoaded(Instrumentation var0, String var1) {
      for (Class var5 : var0.getAllLoadedClasses()) {
         if (var1.equals(var5.getName())) {
            return true;
         }
      }

      return false;
   }

   private static void verifyNoMixinTargetsPreloaded(Instrumentation var0) {
      for (Class var4 : var0.getAllLoadedClasses()) {
         if (mixinWarmup.isExplicitTarget(var4.getName())) {
            failMixinRegistration(
               "Mixin target was already defined before initialized configuration registration: "
                  + var4.getName()
            );
         }
      }
   }

   private static void failMixinRegistration(String var0) {
      String var1 = var0 == null
         ? "initialized Mixin registration failed"
         : var0;
      mixinWarmup.markFailed(var1);
      failBakeCacheBootstrap();
      throw new IllegalStateException(var1);
   }

   private static void openJavaLangToLoader(Instrumentation var0, ClassLoader var1) {
      if (var0 != null && var1 != null) {
         String var2 = var1.getClass().getName();
         if (!var2.startsWith("jdk.internal.reflect.")
            && !var2.startsWith("sun.reflect.")) {
            synchronized (KNt) {
               if (KNt.containsKey(var1)) {
                  return;
               }

               KNt.put(var1, Boolean.TRUE);
            }

            try {
               Class var15 = Class.forName("java.lang.Module");
               Method var4 = Class.class.getMethod("getModule");
               Method var5 = ClassLoader.class.getMethod("getUnnamedModule");
               Object var6 = var4.invoke(Object.class);
               Object var7 = var5.invoke(var1);
               Object var8 = var4.invoke(AgentBootstrap.class);
               Method var9 = Instrumentation.class
                  .getMethod(
                     "redefineModule",
                     var15,
                     Set.class,
                     Map.class,
                     Map.class,
                     Set.class,
                     Map.class
                  );
               HashSet var10 = new HashSet();
               var10.add(var7);
               var10.add(var8);
               HashMap var11 = new HashMap();
               var11.put("java.lang", var10);
               var9.invoke(var0, var6, Collections.emptySet(), Collections.emptyMap(), var11, Collections.emptySet(), Collections.emptyMap());
               System.out.println("[Mod-Agent] Opened java.base/java.lang to loader: " + var2);
            } catch (ClassNotFoundException var12) {
            } catch (Exception var13) {
               KNt.remove(var1);
               System.out
                  .println(
                     "[Mod-Agent] Failed to open java.base/java.lang to loader "
                        + var2
                        + ": "
                        + var13
                  );
            }
         }
      }
   }

   private static void cmJyo(String var0) throws Exception {
      Instrumentation var1 = instrumentation;
      if (var1 != null) {
         Class var2 = Class.forName("java.lang.Module");
         Method var3 = Class.class.getMethod("getModule");
         Object var4 = var3.invoke(Object.class);
         Object var5 = var3.invoke(AgentBootstrap.class);
         Method var6 = Instrumentation.class
            .getMethod(
               "redefineModule",
               var2,
               Set.class,
               Map.class,
               Map.class,
               Set.class,
               Map.class
            );
         HashMap var7 = new HashMap();
         var7.put(var0, Collections.singleton(var5));
         var6.invoke(var1, var4, Collections.emptySet(), Collections.emptyMap(), var7, Collections.emptySet(), Collections.emptyMap());
      }
   }

   private static void verifyMixinTargets(byte[] var0, String var1) {
      final boolean[] var2 = new boolean[]{false};
      final int[] var3 = new int[]{0};

      try {
         ClassReader var4 = new ClassReader(var0);
         var4.accept(
            new ClassVisitor(327680) {
               @Override
               public AnnotationVisitor visitAnnotation(String var1, boolean var2x) {
                  if (!"Lorg/spongepowered/asm/mixin/Mixin;".equals(var1)) {
                     return null;
                  } else {
                     var2[0] = true;
                     return new AnnotationVisitor(327680) {
                        @Override
                        public void visit(String var1, Object var2xx) {
                           if ("value".equals(var1)
                              || "targets".equals(var1)) {
                              AgentBootstrap.recordMixinTargetBridge(var2xx, var3);
                           }
                        }

                        @Override
                        public AnnotationVisitor visitArray(String var1) {
                           return !"value".equals(var1)
                                 && !"targets".equals(var1)
                              ? null
                              : new AnnotationVisitor(327680) {
                                 @Override
                                 public void visit(String var1, Object var2x) {
                                    AgentBootstrap.recordMixinTargetBridge(var2x, var3);
                                 }
                              };
                        }
                     };
                  }
               }
            },
            7
         );
         if (!var2[0] || var3[0] == 0) {
            throw new IllegalStateException(
               "Mixin target could not be proven for " + var1
            );
         }
      } catch (RuntimeException var5) {
         throw new IllegalStateException(
            "Mixin target scan failed for " + var1, var5
         );
      }
   }

   private static void recordMixinTarget(Object var0, int[] var1) {
      String var2 = null;
      if (var0 instanceof Type) {
         Type var3 = (Type)var0;
         if (var3.getSort() == 10) {
            var2 = var3.getInternalName();
         }
      } else if (var0 instanceof String) {
         var2 = ((String)var0).replace('.', '/');
      }

      if (!isValidMixinTargetName(var2)) {
         throw new IllegalArgumentException("Invalid Mixin target: " + var2);
      } else {
         var1[0]++;
         mixinWarmup.protectTarget(var2);
      }
   }

   private static boolean isValidMixinTargetName(String var0) {
      if (var0 != null
         && !var0.isEmpty()
         && !var0.startsWith("/")
         && !var0.endsWith("/")
         && !var0.contains("//")
         && !var0.contains("\\")
         && !var0.contains("..")
         && var0.indexOf(59) < 0
         && var0.indexOf(91) < 0
         && var0.indexOf(58) < 0
         && var0.indexOf(0) < 0) {
         for (int var1 = 0; var1 < var0.length(); var1++) {
            char var2 = var0.charAt(var1);
            if (Character.isWhitespace(var2) || Character.isSpaceChar(var2)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static byte[] patchMixinClassBytes(byte[] var0, final Map<String, String> var1, final Map<String, String> var2, Map<String, String> var3) {
      if (qUsay(var0)) {
         ClassReader var4 = new ClassReader(var0);
         ClassWriter var5 = new ClassWriter(var4, 1);
         var4.accept(new ClassRemapper(var5, new Remapper() {
            @Override
            public String mapMethodName(String var1x, String var2x, String var3x) {
               String var4x = (String)var1.get(var2x);
               return var4x != null ? var4x : var2x;
            }

            @Override
            public String mapFieldName(String var1x, String var2x, String var3x) {
               String var4x = (String)var2.get(var2x);
               return var4x != null ? var4x : var2x;
            }
         }), 0);
         var0 = var5.toByteArray();
      }

      var0 = UJel(var0);
      if (!var3.isEmpty()) {
         var0 = AccessorConflictPatcher.applyAccessorRenames(var0, var3);
      }

      return TwgyM(var0);
   }

   private static byte[] UJel(byte[] var0) {
      String var1 = "Lorg/spongepowered/asm/mixin/gen/Accessor;";
      String var2 = "Lorg/spongepowered/asm/mixin/gen/Invoker;";
      if (!VezswCk(var0, "Accessor")
         && !VezswCk(var0, "Invoker")) {
         return var0;
      } else {
         ClassReader var3 = new ClassReader(var0);
         ClassWriter var4 = new ClassWriter(var3, 0);
         var3.accept(
            new ClassVisitor(327680, var4) {
               @Override
               public MethodVisitor visitMethod(int var1, final String var2x, String var3x, String var4x, String[] var5) {
                  return new MethodVisitor(327680, super.visitMethod(var1, var2x, var3x, var4x, var5)) {
                     @Override
                     public AnnotationVisitor visitAnnotation(String var1, boolean var2xx) {
                        AnnotationVisitor var3x = super.visitAnnotation(var1, var2xx);
                        if ("Lorg/spongepowered/asm/mixin/gen/Accessor;".equals(var1)) {
                           String var4x = AgentBootstrap.accessorTargetName(var2);
                           if (var4x != null) {
                              System.out
                                 .println(
                                    "[Mod-Agent] Injecting @Accessor value=\""
                                       + var4x
                                       + "\" for method: "
                                       + var2
                                 );
                              return new AgentBootstrap.ValueInjectingAnnotationVisitor(var3x, var4x);
                           }
                        } else if ("Lorg/spongepowered/asm/mixin/gen/Invoker;".equals(var1)) {
                           String var5x = AgentBootstrap.invokerTargetName(var2);
                           if (var5x != null) {
                              System.out
                                 .println(
                                    "[Mod-Agent] Injecting @Invoker value=\""
                                       + var5x
                                       + "\" for method: "
                                       + var2
                                 );
                              return new AgentBootstrap.ValueInjectingAnnotationVisitor(var3x, var5x);
                           }
                        }

                        return var3x;
                     }
                  };
               }
            },
            0
         );
         return var4.toByteArray();
      }
   }

   private static String stripAccessorPrefix(String var0) {
      for (String var4 : new String[]{"get", "set", "is"}) {
         if (var0.startsWith(var4) && var0.length() > var4.length()) {
            String var5 = var0.substring(var4.length());
            return Character.toLowerCase(var5.charAt(0)) + var5.substring(1);
         }
      }

      return null;
   }

   private static String stripInvokerPrefix(String var0) {
      for (String var4 : new String[]{"call", "invoke"}) {
         if (var0.startsWith(var4) && var0.length() > var4.length()) {
            String var5 = var0.substring(var4.length());
            return Character.toLowerCase(var5.charAt(0)) + var5.substring(1);
         }
      }

      return null;
   }

   private static byte[] TwgyM(byte[] var0) {
      boolean var1 = false;

      try {
         ClassReader var2 = new ClassReader(var0);
         final boolean[] var3 = new boolean[]{false};
         var2.accept(new ClassVisitor(327680) {
            @Override
            public AnnotationVisitor visitAnnotation(String var1, boolean var2x) {
               if ("Lorg/spongepowered/asm/mixin/Mixin;".equals(var1)) {
                  var3[0] = true;
               }

               return null;
            }
         }, 5);
         var1 = var3[0];
      } catch (Exception var5) {
         return var0;
      }

      if (!var1) {
         return var0;
      } else {
         try {
            ClassReader var7 = new ClassReader(var0);
            ClassWriter var8 = new ClassWriter(var7, 0);
            var7.accept(
               new ClassVisitor(327680, var8) {
                  @Override
                  public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
                     AnnotationVisitor var3 = super.visitAnnotation(var1, var2);
                     return !"Lorg/spongepowered/asm/mixin/Mixin;".equals(var1)
                        ? var3
                        : new AnnotationVisitor(327680, var3) {
                           boolean remapSeen = false;
                           boolean cY2 = false;

                           @Override
                           public void visit(String var1, Object var2x) {
                              if ("remap".equals(var1)) {
                                 this.remapSeen = true;
                                 super.visit(var1, false);
                              } else if ("priority".equals(var1)) {
                                 this.cY2 = true;
                                 int var3x = (Integer)var2x;
                                 if (var3x > 100) {
                                    System.out
                                       .println(
                                          "[Mod-Agent] Pre-patch: clamping mixin priority "
                                             + var3x
                                             + " \u2192 "
                                             + 100
                                       );
                                 }

                                 super.visit(var1, Math.min(var3x, 100));
                              } else {
                                 super.visit(var1, var2x);
                              }
                           }

                           @Override
                           public void visitEnd() {
                              if (!this.remapSeen) {
                                 super.visit("remap", false);
                              }

                              if (!this.cY2) {
                                 super.visit("priority", 100);
                              }

                              super.visitEnd();
                           }
                        };
                  }
               },
               0
            );
            return var8.toByteArray();
         } catch (Exception var4) {
            System.out.println("[Mod-Agent] Failed to apply mixin annotation fixes: " + var4);
            return var0;
         }
      }
   }

   private static List<String> listMixinClassPaths(String var0) {
      ArrayList var1 = new ArrayList();
      JsonObject var2 = parseMixinConfig(var0);
      String var3 = VDUA(var2, "package");
      String var4 = var3 == null ? "" : var3.replace('.', '/');

      for (String var8 : new String[]{"mixins", "client", "server"}) {
         JsonElement var9 = var2.get(var8);
         if (var9 != null) {
            if (!var9.isJsonArray()) {
               throw new IllegalArgumentException(
                  "Mixin config field is not an array: " + var8
               );
            }

            for (JsonElement var12 : var9.getAsJsonArray()) {
               if (!var12.isJsonPrimitive() || !var12.getAsJsonPrimitive().isString()) {
                  throw new IllegalArgumentException(
                     "Mixin config contains a non-string entry in " + var8
                  );
               }

               String var13 = var12.getAsString();
               if (isBlank(var13)
                  || var13.startsWith("/")
                  || var13.startsWith("\\")
                  || var13.contains("..")) {
                  throw new IllegalArgumentException(
                     "Unsafe Mixin class name: " + var13
                  );
               }

               String var14 = var13.replace('.', '/');
               var1.add(
                  (var4.isEmpty() ? "" : var4 + "/")
                     + var14
                     + ".class"
               );
            }
         }
      }

      return var1;
   }

   private static boolean hasMixinPlugin(String var0) {
      return parseMixinConfig(var0).has("plugin");
   }

   private static JsonObject parseMixinConfig(String var0) {
      if (var0 == null) {
         throw new IllegalArgumentException("Mixin config is null");
      } else {
         JsonElement var1 = new JsonParser().parse(var0);
         if (!var1.isJsonObject()) {
            throw new IllegalArgumentException(
               "Mixin config root is not an object"
            );
         } else {
            return var1.getAsJsonObject();
         }
      }
   }

   private static String VDUA(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 == null) {
         return null;
      } else if (var2.isJsonPrimitive() && var2.getAsJsonPrimitive().isString()) {
         return var2.getAsString();
      } else {
         throw new IllegalArgumentException(
            "Mixin config field is not a string: " + var1
         );
      }
   }

   private static Map<String, String> planAccessorRenames(List<MixinRegistrationEntry> var0) {
      HashMap var1 = new HashMap();

      for (MixinRegistrationEntry var3 : var0) {
         File var4 = new File(var3.getJarPath());
         String var5 = deriveModIdFromMixinConfig(var3.getMixinConfig());
         HashSet var6 = new HashSet();
         byte[] var7 = isBlank(var3.getMixinConfig()) ? null : ResourceBridge.readResource(var3.getMixinConfig());
         boolean var8 = var7 != null;
         if (var7 != null) {
            Arrays.fill(var7, (byte)0);
         }

         if (var8) {
            for (String var10 : ResourceBridge.publishedClassNames()) {
               byte[] var11 = ResourceBridge.readResource(var10 + ".class");
               if (var11 != null) {
                  kSv2(var11, var10, var5, var1);
                  Arrays.fill(var11, (byte)0);
                  var6.add(var10);
               }
            }
         }

         if (var4.exists()) {
            try (JarFile var48 = new JarFile(var4)) {
               for (JarEntry var12 : Collections.list(var48.entries())) {
                  if (var12.getName().endsWith(".class")) {
                     String var13 = var12.getName().substring(0, var12.getName().length() - 6);
                     if (!var6.contains(var13)) {
                        try (InputStream var14 = var48.getInputStream(var12)) {
                           kSv2(readStreamFully(var14), var13, var5, var1);
                        } catch (Exception var44) {
                        }
                     }
                  }
               }
            } catch (IOException var47) {
               System.out
                  .println(
                     "[Mod-Agent] Failed to scan JAR for accessors: "
                        + var3.getJarPath()
                        + " -- "
                        + var47
                  );
            }
         }
      }

      return var1;
   }

   private static void kSv2(byte[] var0, final String var1, final String var2, final Map<String, String> var3) {
      try {
         ClassReader var4 = new ClassReader(var0);
         final boolean[] var5 = new boolean[]{false};
         var4.accept(new ClassVisitor(327680) {
            @Override
            public AnnotationVisitor visitAnnotation(String var1, boolean var2x) {
               if ("Lorg/spongepowered/asm/mixin/Mixin;".equals(var1)) {
                  var5[0] = true;
               }

               return null;
            }
         }, 5);
         if (!var5[0]) {
            return;
         }

         var4.accept(
            new ClassVisitor(327680) {
               @Override
               public MethodVisitor visitMethod(int var1x, final String var2x, final String var3x, String var4x, String[] var5x) {
                  return new MethodVisitor(327680) {
                     @Override
                     public AnnotationVisitor visitAnnotation(String var1x, boolean var2xx) {
                        if (!"Lorg/spongepowered/asm/mixin/gen/Accessor;".equals(var1x) && !"Lorg/spongepowered/asm/mixin/gen/Invoker;".equals(var1x)) {
                           return null;
                        } else {
                           String var3xx = var2x + "_" + var2;
                           var3.put(
                              var1
                                 + "\n"
                                 + var2x
                                 + "\n"
                                 + var3x,
                              var3xx
                           );
                           System.out
                              .println(
                                 "[Mod-Agent] Accessor rename planned: "
                                    + var2x
                                    + " \u2192 "
                                    + var3xx
                                    + " in "
                                    + var1
                              );
                           return null;
                        }
                     }
                  };
               }
            },
            5
         );
      } catch (Exception var6) {
      }
   }

   static String deriveModIdFromMixinConfig(String var0) {
      if (isBlank(var0)) {
         return "mod";
      } else {
         String var1 = var0;
         if (var0.startsWith("mixins.")) {
            var1 = var0.substring("mixins.".length());
         }

         if (var1.endsWith(".json")) {
            var1 = var1.substring(0, var1.length() - ".json".length());
         }

         int var2 = var1.indexOf(46);
         return (var2 > 0 ? var1.substring(0, var2) : var1)
            .replaceAll("[^a-zA-Z0-9_]", "_");
      }
   }

   private static String stripRefmapEntries(String var0) {
      String var1 = var0.replaceAll(",\\s*\"refmap\"\\s*:\\s*\"[^\"]*\"", "");
      var1 = var1.replaceAll("\"refmap\"\\s*:\\s*\"[^\"]*\"\\s*,", "");
      return var1.replaceAll("\"refmap\"\\s*:\\s*\"[^\"]*\"", "");
   }

   private static String CBCaxeC(String var0) {
      String var1 = "\\\"plugin\\\"\\s*:\\s*\\\"net\\.jade\\.dev\\.agent\\.MixinBootstrapPlugin\\\"";
      String var2 = var0.replaceFirst(",\\s*" + var1, "");
      return var2.replaceFirst(var1 + "\\s*,", "");
   }

   private static void gnlehZ4(String var0) {
      List var1;
      try {
         var1 = LocalCoreLoader.listLocalMixinClassNames();
      } catch (Exception var7) {
         throw new IllegalStateException(
            "loader update required: could not read initialized core Mixin index",
            var7
         );
      }

      ArrayList var2 = new ArrayList();

      for (String var6 : new String[]{"mixins", "client", "server"}) {
         var2.addAll(extractJsonStringArray(var0, var6));
      }

      if (var2.isEmpty() || !var2.equals(var1)) {
         throw new IllegalStateException(
            "loader update required: static Mixin descriptor does not match initialized core index"
         );
      }
   }

   private static boolean qUsay(byte[] var0) {
      for (int var1 = 0; var1 < var0.length - 6; var1++) {
         if (var0[var1] == 102) {
            if (var0[var1 + 1] == 117 && var0[var1 + 2] == 110 && var0[var1 + 3] == 99 && var0[var1 + 4] == 95) {
               return true;
            }

            if (var0[var1 + 1] == 105 && var0[var1 + 2] == 101 && var0[var1 + 3] == 108 && var0[var1 + 4] == 100 && var0[var1 + 5] == 95) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean VezswCk(byte[] var0, String var1) {
      byte[] var2 = var1.getBytes(StandardCharsets.UTF_8);

      label24:
      for (int var3 = 0; var3 <= var0.length - var2.length; var3++) {
         for (int var4 = 0; var4 < var2.length; var4++) {
            if (var0[var3 + var4] != var2[var4]) {
               continue label24;
            }
         }

         return true;
      }

      return false;
   }

   public static void agentmain(String var0, Instrumentation var1) throws Exception {
      premain(var0, var1);
   }

   private static boolean isBlank(String var0) {
      return var0 == null || var0.trim().isEmpty();
   }

   private static String joinUrlsByNewline(Collection<URL> var0) {
      StringBuilder var1 = new StringBuilder();

      for (URL var3 : var0) {
         if (var1.length() != 0) {
            var1.append('\n');
         }

         var1.append(var3.toExternalForm());
      }

      return var1.toString();
   }

   private static byte[] readStreamFully(InputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream(Math.max(var0.available(), 1024));
      byte[] var2 = new byte[8192];

      int var3;
      while ((var3 = var0.read(var2)) > 0) {
         var1.write(var2, 0, var3);
      }

      return var1.toByteArray();
   }

   private static byte[] computePipelineFingerprint(File var0, Map<String, byte[]> var1, Map<String, String> var2, Map<String, String> var3, Map<String, String> var4) throws IOException {
      MessageDigest var5;
      try {
         var5 = MessageDigest.getInstance("SHA-256");
      } catch (NoSuchAlgorithmException var14) {
         throw new IOException("SHA-256 unavailable", var14);
      }

      vTiw(var5, "Jade Genesis pipeline fingerprint v1");
      vTiw(var5, ResourceBridge.buildId());
      vTiw(var5, ResourceBridge.coreSha256());
      vTiw(var5, ResourceBridge.resourceUrl().toExternalForm());
      InputStream var6 = Files.newInputStream(var0.toPath());

      try {
         byte[] var7 = new byte[65536];
         long var9 = 0L;

         int var8;
         while ((var8 = var6.read(var7)) != -1) {
            var5.update(var7, 0, var8);
            var9 += var8;
         }

         aQz7(var5, var9);
         Arrays.fill(var7, (byte)0);
      } finally {
         var6.close();
      }

      jUcjmd(var5, var2);
      jUcjmd(var5, var3);
      jUcjmd(var5, var4);
      ArrayList var16 = new ArrayList(var1.keySet());
      Collections.sort(var16);
      digestInt(var5, var16.size());

      for (String var18 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var16)) {
         vTiw(var5, var18);
         byte[] var10 = (byte[])var1.get(var18);
         digestInt(var5, var10 == null ? -1 : var10.length);
         if (var10 != null) {
            var5.update(var10);
         }
      }

      return var5.digest();
   }

   private static void jUcjmd(MessageDigest var0, Map<String, String> var1) {
      ArrayList var2 = new ArrayList(var1.keySet());
      Collections.sort(var2);
      digestInt(var0, var2.size());

      for (String var4 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var2)) {
         vTiw(var0, var4);
         vTiw(var0, (String)var1.get(var4));
      }
   }

   private static void vTiw(MessageDigest var0, String var1) {
      if (var1 == null) {
         digestInt(var0, -1);
      } else {
         byte[] var2 = var1.getBytes(StandardCharsets.UTF_8);
         digestInt(var0, var2.length);
         var0.update(var2);
         Arrays.fill(var2, (byte)0);
      }
   }

   private static void digestInt(MessageDigest var0, int var1) {
      var0.update((byte)(var1 >>> 24));
      var0.update((byte)(var1 >>> 16));
      var0.update((byte)(var1 >>> 8));
      var0.update((byte)var1);
   }

   private static void aQz7(MessageDigest var0, long var1) {
      digestInt(var0, (int)(var1 >>> 32));
      digestInt(var0, (int)var1);
   }

   private static byte[] readResourceOrJarEntry(JarFile var0, String var1) throws IOException {
      String var2 = EWRu(var1);
      byte[] var3 = ResourceBridge.readResource(var2);
      if (var3 != null) {
         return var3;
      } else if (var0 == null) {
         return null;
      } else {
         JarEntry var4 = var0.getJarEntry(var2);
         if (var4 == null) {
            return null;
         } else {
            byte[] var7;
            try (InputStream var5 = var0.getInputStream(var4)) {
               var7 = readStreamFully(var5);
            }

            return var7;
         }
      }
   }

   private static void QinGk9(File var0, Map<String, byte[]> var1) throws IOException {
      try (JarFile var2 = new JarFile(var0)) {
         byte[] var4 = readResourceOrJarEntry(var2, "mixins.agent.json");
         if (var4 == null) {
            throw new IOException("agent mixin config is missing");
         }

         String var5 = new String(var4, StandardCharsets.UTF_8);
         if (hasMixinPlugin(var5)) {
            throw new IllegalStateException(
               "Agent Mixin config uses an unsupported dynamic target plugin"
            );
         }

         HashSet var6 = new HashSet<>(listMixinClassPaths(var5));
         var1.put("mixins.agent.json", var4);
         byte[] var7 = readResourceOrJarEntry(var2, "META-INF/jade-loader-mixins");
         if (var7 == null || var7.length == 0) {
            throw new IOException("loader Mixin index is missing");
         }

         var1.put("META-INF/jade-loader-mixins", var7);
         Enumeration var8 = var2.entries();

         while (var8.hasMoreElements()) {
            JarEntry var9 = (JarEntry)var8.nextElement();
            if (!var9.isDirectory() && var9.getName().endsWith(".class")) {
               try (InputStream var10 = var2.getInputStream(var9)) {
                  byte[] var12 = readStreamFully(var10);
                  if (var6.remove(EWRu(var9.getName()))) {
                     verifyMixinTargets(var12, var9.getName());
                  }

                  var1.put(EWRu(var9.getName()), var12);
               }
            }
         }

         if (!var6.isEmpty()) {
            throw new IOException("agent Mixin classes are missing: " + var6);
         }
      }
   }

   private static String EWRu(String var0) {
      if (var0 == null) {
         return "";
      } else {
         String var1 = var0.replace('\\', '/');

         while (var1.startsWith("/")) {
            var1 = var1.substring(1);
         }

         return var1;
      }
   }

   private static String readUtf8File(Path var0) throws IOException {
      return new String(Files.readAllBytes(var0), StandardCharsets.UTF_8);
   }

   static void loadSrgMappings(Map<String, String> var0, Map<String, String> var1) {
      HIPz("/mappings/methods.csv", var0);
      HIPz("/mappings/fields.csv", var1);
      System.out
         .println(
            "[Mod-Agent] Loaded "
               + var0.size()
               + " method mappings, "
               + var1.size()
               + " field mappings."
         );
   }

   private static void HIPz(String var0, Map<String, String> var1) {
      try (InputStream var2 = AgentBootstrap.class.getResourceAsStream(var0)) {
         if (var2 != null) {
            BufferedReader var4 = new BufferedReader(new InputStreamReader(var2));
            var4.readLine();

            String var5;
            while ((var5 = var4.readLine()) != null) {
               String[] var6 = var5.split(",");
               if (var6.length >= 2) {
                  var1.put(var6[0].trim(), var6[1].trim());
               }
            }
         } else {
            System.out
               .println(
                  "[Mod-Agent] Mappings not found: "
                     + var0
                     + " \u2014 SRG\u2192MCP remapping will not work!"
               );
         }
      } catch (Exception var18) {
         System.out
            .println("[Mod-Agent] Failed to load mappings " + var0 + ": " + var18);
      }
   }

   private static String buildModListProperty(List<MixinRegistrationEntry> var0) {
      StringBuilder var1 = new StringBuilder();

      for (MixinRegistrationEntry var3 : var0) {
         File var4 = new File(var3.getJarPath());
         if (var4.exists()) {
            try (JarFile var5 = new JarFile(var4)) {
               for (JarEntry var8 : Collections.list(var5.entries())) {
                  if (var8.getName().endsWith(".class")) {
                     try (InputStream var9 = var5.getInputStream(var8)) {
                        byte[] var11 = readStreamFully(var9);
                        ClassReader var12 = new ClassReader(var11);
                        final String[] var13 = new String[]{null};
                        var12.accept(new ClassVisitor(327680) {
                           private String visitedClassName;

                           @Override
                           public void visit(int var1, int var2, String var3x, String var4x, String var5x, String[] var6) {
                              this.visitedClassName = var3x;
                           }

                           @Override
                           public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
                              if ("Lnet/minecraftforge/fml/common/Mod;".equals(var1)) {
                                 var13[0] = this.visitedClassName;
                              }

                              return null;
                           }
                        }, 5);
                        if (var13[0] != null) {
                           if (var1.length() != 0) {
                              var1.append(",");
                           }

                           var1.append(var13[0])
                              .append("|")
                              .append(var3.getPropertyName() != null ? var3.getPropertyName() : "");
                           System.out.println("[Mod-Agent] Discovered @Mod: " + var13[0]);
                        }
                     }
                  }
               }
            } catch (IOException var41) {
               System.out
                  .println(
                     "[Mod-Agent] Failed to scan JAR: "
                        + var3.getJarPath()
                        + " -- "
                        + var41
                  );
            }
         }
      }

      return var1.toString();
   }

   private static Set<String> PwsFy(List<MixinRegistrationEntry> var0) {
      HashSet var1 = new HashSet();

      for (MixinRegistrationEntry var3 : var0) {
         File var4 = new File(var3.getJarPath());
         if (var4.exists()) {
            try (JarFile var5 = new JarFile(var4)) {
               var5.stream().filter(AgentBootstrap::isClassEntry).map(AgentBootstrap::OFWX).forEach(var1::add);
            } catch (IOException var18) {
               System.out
                  .println(
                     "[Mod-Agent] Failed to index JAR: "
                        + var3.getJarPath()
                        + " -- "
                        + var18
                  );
            }
         }
      }

      return var1;
   }

   private static String describeThrowable(String var0, Throwable var1) {
      StringBuilder var2 = new StringBuilder(var0);
      var2.append(": ").append(var1.getClass().getName());
      if (var1.getMessage() != null) {
         var2.append(": ").append(var1.getMessage());
      }

      StackTraceElement[] var3 = var1.getStackTrace();
      if (var3 != null && var3.length > 0) {
         var2.append(" at ")
            .append(var3[0].getClassName())
            .append('.')
            .append(var3[0].getMethodName())
            .append(':')
            .append(var3[0].getLineNumber());
      }

      Throwable var4 = var1.getCause();
      if (var4 != null && var4 != var1) {
         var2.append("; caused_by=").append(var4.getClass().getName());
         if (var4.getMessage() != null) {
            var2.append(": ").append(var4.getMessage());
         }
      }

      return var2.toString();
   }

   private static String loadAgentConfig(String var0, File var1) {
      if (!isBlank(var0)) {
         try {
            String var18 = readUtf8File(new File(var0).toPath());
            System.out.println("[Mod-Agent] Config loaded from path: " + var0);
            return var18;
         } catch (IOException var17) {
            System.out
               .println(
                  "[Mod-Agent] Failed to read config path "
                     + var0
                     + ": "
                     + var17
                     + " - trying bundled fallback."
               );
         }
      }

      InputStream var2 = AgentBootstrap.class
         .getResourceAsStream("/agent-mods.json");
      if (var2 != null) {
         String var4;
         try {
            String var3 = new String(readStreamFully(var2), StandardCharsets.UTF_8);
            System.out.println("[Mod-Agent] Config loaded from bundled /agent-mods.json.");
            var4 = var3;
         } catch (IOException var15) {
            System.out.println("[Mod-Agent] Failed to read bundled /agent-mods.json: " + var15);
            return null;
         } finally {
            try {
               var2.close();
            } catch (IOException var14) {
            }
         }

         return var4;
      } else {
         return null;
      }
   }

   private static List<MixinRegistrationEntry> UxqxtP(String var0, File var1) {
      ArrayList var2 = new ArrayList();
      String var3 = var1.getAbsolutePath();
      String[] var4 = var0.split("\\{");

      for (int var5 = 1; var5 < var4.length; var5++) {
         String var6 = var4[var5];
         if (var6.contains("jar")) {
            String var7 = readConfigStringField(var6, "jar");
            String var8 = readConfigStringField(var6, "mixin");
            String var9 = readConfigStringField(var6, "property");
            if (var7 != null) {
               if ("$SELF".equals(var7)) {
                  var7 = var3;
               }

               var2.add(new MixinRegistrationEntry(var7, var8, var9));
            }
         }
      }

      return var2;
   }

   private static String readConfigStringField(String var0, String var1) {
      String var2 = "\""
         + var1
         + "\"";
      int var3 = var0.indexOf(var2);
      if (var3 == -1) {
         return null;
      } else {
         int var4 = var0.indexOf(":", var3);
         if (var4 == -1) {
            return null;
         } else {
            int var5 = var0.indexOf("\"", var4 + 1);
            if (var5 == -1) {
               return null;
            } else {
               int var6 = var0.indexOf("\"", var5 + 1);
               return var6 == -1 ? null : var0.substring(var5 + 1, var6);
            }
         }
      }
   }

   static String extractJsonStringValue(String var0, String var1) {
      String var2 = "\""
         + var1
         + "\"";
      int var3 = var0.indexOf(var2);
      if (var3 == -1) {
         return null;
      } else {
         int var4 = var0.indexOf(58, var3 + var2.length());
         if (var4 == -1) {
            return null;
         } else {
            int var5 = var0.indexOf(34, var4 + 1);
            if (var5 == -1) {
               return null;
            } else {
               int var6 = var0.indexOf(34, var5 + 1);
               return var6 == -1 ? null : var0.substring(var5 + 1, var6);
            }
         }
      }
   }

   static List<String> extractJsonStringArray(String var0, String var1) {
      ArrayList var2 = new ArrayList();
      String var3 = "\""
         + var1
         + "\"";
      int var4 = var0.indexOf(var3);
      if (var4 == -1) {
         return var2;
      } else {
         int var5 = var0.indexOf(58, var4 + var3.length());
         if (var5 == -1) {
            return var2;
         } else {
            int var6 = var0.indexOf(91, var5 + 1);
            if (var6 == -1) {
               return var2;
            } else {
               int var7 = var0.indexOf(93, var6 + 1);
               if (var7 == -1) {
                  return var2;
               } else {
                  for (String var11 : var0.substring(var6 + 1, var7).split(",")) {
                     var11 = var11.trim();
                     if (var11.startsWith("\"")
                        && var11.endsWith("\"")) {
                        var2.add(var11.substring(1, var11.length() - 1));
                     }
                  }

                  return var2;
               }
            }
         }
      }
   }

   private static String OFWX(JarEntry var0) {
      return var0.getName().replace(".class", "");
   }

   private static boolean isClassEntry(JarEntry var0) {
      return var0.getName().endsWith(".class");
   }

   private static void prepareMixinResources(List var0, Map var1, Map var2, Map var3, Set var4, File var5) {
      try {
         LinkedHashMap var6 = new LinkedHashMap();
         HashSet var7 = new HashSet();

         for (MixinRegistrationEntry var9 : (java.lang.Iterable<MixinRegistrationEntry>) (java.lang.Iterable<?>) (var0)) {
            if (!isBlank(var9.getMixinConfig())) {
               File var10 = new File(var9.getJarPath());
               JarFile var11 = null;

               try {
                  if (var10.exists()) {
                     var11 = new JarFile(var10);
                  }

                  byte[] var12 = readResourceOrJarEntry(var11, var9.getMixinConfig());
                  if (var12 == null) {
                     throw new IOException(
                        "Mixin JSON not found in memory or JAR: "
                           + var9.getMixinConfig()
                     );
                  }

                  String var14 = new String(var12, StandardCharsets.UTF_8);
                  Arrays.fill(var12, (byte)0);
                  if ("mixins.jade.json"
                     .equals(var9.getMixinConfig())) {
                     gnlehZ4(var14);
                  }

                  String var13 = CBCaxeC(stripRefmapEntries(var14));
                  if (hasMixinPlugin(var13)) {
                     throw new IllegalStateException(
                        "Dynamic Mixin plugins are unsupported because their targets cannot be proven: "
                           + var9.getMixinConfig()
                     );
                  }

                  var6.put(EWRu(var9.getMixinConfig()), var13.getBytes(StandardCharsets.UTF_8));
                  System.out.println("[Mod-Agent] Patched mixin config in memory: " + var9.getMixinConfig());

                  for (String var16 : listMixinClassPaths(var13)) {
                     byte[] var17 = readResourceOrJarEntry(var11, var16);
                     if (var17 == null) {
                        throw new IOException(
                           "Mixin class listed by config was not found: "
                              + var16
                        );
                     }

                     try {
                        verifyMixinTargets(var17, var16);
                        byte[] var18 = patchMixinClassBytes(var17, var1, var2, var3);
                        var6.put(EWRu(var16), var18);
                        var7.add(EWRu(var16));
                     } finally {
                        Arrays.fill(var17, (byte)0);
                     }
                  }

                  for (String var73 : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (var3.keySet())) {
                     String var74 = var73.split("\n")[0];
                     if (var4.contains(var74)) {
                        String var75 = var74 + ".class";
                        var75 = EWRu(var75);
                        if (!var7.contains(var75)) {
                           byte[] var19 = readResourceOrJarEntry(var11, var75);
                           if (var19 == null) {
                              throw new IOException(
                                 "Accessor Mixin class was not found: " + var75
                              );
                           }

                           try {
                              verifyMixinTargets(var19, var75);
                              byte[] var20 = patchMixinClassBytes(var19, var1, var2, var3);
                              var6.put(var75, var20);
                              var7.add(var75);
                           } catch (Exception var61) {
                              throw new IllegalStateException(
                                 "Failed to account for accessor Mixin "
                                    + var75,
                                 var61
                              );
                           } finally {
                              Arrays.fill(var19, (byte)0);
                           }
                        }
                     }
                  }
               } catch (IOException var64) {
                  throw new IllegalStateException(
                     "Failed to prepare required Mixin resources for "
                        + var9.getJarPath(),
                     var64
                  );
               } finally {
                  if (var11 != null) {
                     try {
                        var11.close();
                     } catch (IOException var59) {
                     }
                  }
               }
            }
         }

         QinGk9(var5, var6);
         byte[] var67 = computePipelineFingerprint(var5, var6, var1, var2, var3);

         try {
            bakeCacheWatcher.setPipelineFingerprint(var67);
         } finally {
            Arrays.fill(var67, (byte)0);
         }

         int var68 = var6.size();
         ResourceBridge.publishPatched(var6);

         for (byte[] var70 : (java.lang.Iterable<byte[]>) (java.lang.Iterable<?>) (var6.values())) {
            Arrays.fill(var70, (byte)0);
         }

         var6.clear();
         System.out
            .println(
               "[Mod-Agent] Published "
                  + var68
                  + " patched Mixin resources in memory."
            );
         mixinWarmup.markPreparationComplete();
      } catch (Throwable var66) {
         mixinWarmup.markFailed(describeThrowable("mixin_registration_exception", var66));
         System.out.println("[Mod-Agent] Error in mixin registrar: " + var66);
         var66.printStackTrace();
         failBakeCacheBootstrap();
      }
   }

   static void recordMixinTargetBridge(Object var0, int[] var1) {
      recordMixinTarget(var0, var1);
   }

   static String accessorTargetName(String var0) {
      return stripAccessorPrefix(var0);
   }

   static String invokerTargetName(String var0) {
      return stripInvokerPrefix(var0);
   }

   private static final class MixinRegistrationContext {
      private final Instrumentation agentInstrumentation;
      private final List<MixinRegistrationEntry> modEntries;
      private final File agentJar;

      private MixinRegistrationContext(Instrumentation var1, List<MixinRegistrationEntry> var2, File var3) {
         this.agentInstrumentation = var1;
         this.modEntries = Collections.unmodifiableList(new ArrayList<>(var2));
         this.agentJar = var3;
      }

      static Instrumentation getInstrumentation(AgentBootstrap.MixinRegistrationContext var0) {
         return var0.agentInstrumentation;
      }

      static List LxRbr(AgentBootstrap.MixinRegistrationContext var0) {
         return var0.modEntries;
      }

      static File getAgentJar(AgentBootstrap.MixinRegistrationContext var0) {
         return var0.agentJar;
      }
   }

   private static class ValueInjectingAnnotationVisitor extends AnnotationVisitor {
      private final String injectedValue;
      private boolean valueVisited = false;

      ValueInjectingAnnotationVisitor(AnnotationVisitor var1, String var2) {
         super(327680, var1);
         this.injectedValue = var2;
      }

      @Override
      public void visit(String var1, Object var2) {
         if ("value".equals(var1)) {
            this.valueVisited = true;
         }

         super.visit(var1, var2);
      }

      @Override
      public void visitEnd() {
         if (!this.valueVisited) {
            super.visit("value", this.injectedValue);
         }

         super.visitEnd();
      }
   }
}
