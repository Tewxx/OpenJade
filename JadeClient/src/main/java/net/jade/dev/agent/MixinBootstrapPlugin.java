package net.jade.dev.agent;


import jade.deps.loader107.LocalCoreLoader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class MixinBootstrapPlugin implements IMixinConfigPlugin {
   private static boolean initialized;
   private static List<String> mixinIndex;

   @Override
   public synchronized void onLoad(String var1) {
      if (!initialized) {
         try {
            {
               LocalCoreLoader.ensureLocalPayloadPrepared();
               List var2 = LocalCoreLoader.listLocalMixinClassNames();
               MixinBootstrapPlugin.MixinRuntime var3 = resolveMixinRuntime();
               if (var3 != null) {
                  QKYR(var3.hdyHn, "mixins.jade.refmap.json");
                  LocalCoreLoader.defineLocalMixinClasses(var3.qvV, var3.bytecodeProvider);
               } else {
                  String var4 = "jade/mixin/impl/"
                     + ((String)var2.get(0)).replace('.', '/')
                     + ".class";
                  byte[] var5 = LocalCoreLoader.readCoreResource(var4);
                  if (var5 == null) {
                     throw new IllegalStateException(
                        "Lunar Mixin resource is unavailable"
                     );
                  }

                  Arrays.fill(var5, (byte)0);
               }

               mixinIndex = var2;
               initialized = true;
               System.out.println("[Jade] Forge core received; continuing Mixin bootstrap.");
            }
         } catch (Exception var6) {
            throw new IllegalStateException(
               "Jade bootstrap failed; Forge launch stopped", var6
            );
         }
      }
   }

   @Override
   public String getRefMapperConfig() {
      return "mixins.jade.refmap.json";
   }

   @Override
   public boolean shouldApplyMixin(String var1, String var2) {
      return true;
   }

   @Override
   public void acceptTargets(Set<String> var1, Set<String> var2) {
   }

   @Override
   public List<String> getMixins() {
      if (initialized && mixinIndex != null && !mixinIndex.isEmpty()) {
         return new ArrayList<>();
      } else {
         throw new IllegalStateException(
            "Jade Mixin index was not received"
         );
      }
   }

   @Override
   public void preApply(String var1, ClassNode var2, String var3, IMixinInfo var4) {
   }

   @Override
   public void postApply(String var1, ClassNode var2, String var3, IMixinInfo var4) {
   }

   private static MixinBootstrapPlugin.MixinRuntime resolveMixinRuntime() throws Exception {
      ClassLoader var0 = MixinBootstrapPlugin.class.getClassLoader();
      Class var1 = Class.forName(
         "org.spongepowered.asm.service.MixinService", false, var0
      );
      Object var2 = var1.getMethod("getService").invoke(null);
      if (var2 == null) {
         throw new IllegalStateException("active Mixin service is unavailable");
      } else {
         Field var3 = findFieldInHierarchy(var2.getClass(), "classLoaderUtil");
         if (var3 == null) {
            if (var2.getClass().getName().contains("LaunchWrapper")) {
               throw new IllegalStateException(
                  "LaunchWrapper Mixin service has no class tracker"
               );
            } else {
               return null;
            }
         } else {
            var3.setAccessible(true);
            Object var4 = var3.get(var2);
            if (var4 == null) {
               throw new IllegalStateException(
                  "Mixin service has no class tracker"
               );
            } else {
               Field var5 = findFieldInHierarchy(var4.getClass(), "classLoader");
               if (var5 == null) {
                  throw new IllegalStateException(
                     "Mixin class tracker has no LaunchClassLoader"
                  );
               } else {
                  var5.setAccessible(true);
                  Object var6 = var5.get(var4);
                  if (!(var6 instanceof ClassLoader)) {
                     throw new IllegalStateException(
                        "Mixin class tracker loader is invalid"
                     );
                  } else {
                     ClassLoader var7 = (ClassLoader)var6;
                     ClassLoader var8 = var7;

                     try {
                        Class var9 = Class.forName(
                           "net.minecraft.launchwrapper.Launch",
                           false,
                           var2.getClass().getClassLoader()
                        );
                        Object var10 = var9.getField("classLoader").get(null);
                        if (var10 instanceof ClassLoader) {
                           var8 = (ClassLoader)var10;
                        }
                     } catch (Exception var11) {
                     }

                     Object var12 = var2.getClass()
                        .getMethod("getBytecodeProvider")
                        .invoke(var2);
                     if (var12 == null) {
                        throw new IllegalStateException(
                           "Mixin service has no bytecode provider"
                        );
                     } else {
                        return new MixinBootstrapPlugin.MixinRuntime(var8, var7, var12, var2);
                     }
                  }
               }
            }
         }
      }
   }

   private static void QKYR(Object var0, String var1) throws Exception {
      byte[] var2 = LocalCoreLoader.readCoreResource(var1);
      if (var2 != null && var2.length != 0) {
         InputStream var3 = null;
         ByteArrayOutputStream var4 = new ByteArrayOutputStream(var2.length);

         try {
            var3 = (InputStream)var0.getClass()
               .getMethod("getResourceAsStream", String.class)
               .invoke(var0, var1);
            if (var3 == null) {
               throw new IllegalStateException(
                  "Mixin service cannot read local resource: " + var1
               );
            }

            byte[] var5 = new byte[8192];

            int var6;
            while ((var6 = var3.read(var5)) != -1) {
               if (var4.size() + var6 > 4194304) {
                  throw new IllegalStateException(
                     "local resource is oversized: " + var1
                  );
               }

               var4.write(var5, 0, var6);
            }

            byte[] var7 = var4.toByteArray();

            try {
               if (!MessageDigest.isEqual(var2, var7)) {
                  throw new SecurityException(
                     "Mixin service returned the wrong local resource: "
                        + var1
                  );
               }
            } finally {
               Arrays.fill(var7, (byte)0);
            }
         } finally {
            if (var3 != null) {
               var3.close();
            }

            Arrays.fill(var2, (byte)0);
         }
      } else {
         throw new IllegalStateException("local resource is missing: " + var1);
      }
   }

   private static Field findFieldInHierarchy(Class<?> var0, String var1) {
      for (Class var2 = var0; var2 != null; var2 = var2.getSuperclass()) {
         try {
            return var2.getDeclaredField(var1);
         } catch (NoSuchFieldException var4) {
         }
      }

      return null;
   }

   private static final class MixinRuntime {
      final ClassLoader qvV;
      final ClassLoader iylaZ;
      final Object bytecodeProvider;
      final Object hdyHn;

      MixinRuntime(ClassLoader var1, ClassLoader var2, Object var3, Object var4) {
         this.qvV = var1;
         this.iylaZ = var2;
         this.bytecodeProvider = var3;
         this.hdyHn = var4;
      }
   }
}
