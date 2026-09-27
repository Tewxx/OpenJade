// Jade recovery: recovered class name: VanillaHookTransformer; original class: jade.deps.eLz.MH0XMe4dLB$2
package jade.client.runtime;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassWriter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class VanillaHookTransformer$2 extends ClassLoader {
   private final ClassLoader runtimeLoader;
   private final RuntimeMappings mappings;
   private final boolean preferLiveHierarchy;
   private final Map<String, byte[]> hierarchyResources = new ConcurrentHashMap<>();

   public VanillaHookTransformer$2(ClassLoader runtimeLoader, RuntimeMappings mappings) {
      this(runtimeLoader, mappings, false);
   }

   public VanillaHookTransformer$2(ClassLoader runtimeLoader, RuntimeMappings mappings, boolean preferLiveHierarchy) {
      super(runtimeLoader);
      this.runtimeLoader = runtimeLoader;
      this.mappings = mappings;
      this.preferLiveHierarchy = preferLiveHierarchy;
   }

   @Override
   public InputStream getResourceAsStream(String name) {
      if (name != null && name.endsWith(".class")) {
         String sourceName = name.substring(0, name.length() - 6);
         String runtimeName = this.mappings.runtimeClass(sourceName);
         byte[] cached = this.hierarchyResources.get(sourceName);
         if (cached != null) {
            return new ByteArrayInputStream(cached);
         }

         byte[] hierarchy = this.preferLiveHierarchy
            ? this.hierarchyFromLiveClass(sourceName, runtimeName)
            : this.hierarchyFromResource(sourceName, runtimeName);
         if (hierarchy == null) {
            hierarchy = this.preferLiveHierarchy ? this.hierarchyFromResource(sourceName, runtimeName) : this.hierarchyFromLiveClass(sourceName, runtimeName);
         }

         if (hierarchy != null) {
            this.hierarchyResources.put(sourceName, hierarchy);
            return new ByteArrayInputStream(hierarchy);
         }
      }

      return this.runtimeLoader.getResourceAsStream(name);
   }

   private byte[] hierarchyFromResource(String sourceName, String runtimeName) {
      InputStream input = this.runtimeLoader.getResourceAsStream(runtimeName + ".class");
      if (input == null) {
         return null;
      } else {
         Object var5;
         try {
            ClassReader reader = new ClassReader(input);
            return this.hierarchyClass(sourceName, reader.getAccess(), reader.getSuperName(), reader.getInterfaces());
         } catch (Throwable var15) {
            var5 = null;
         } finally {
            try {
               input.close();
            } catch (Throwable var14) {
            }
         }

         return (byte[])var5;
      }
   }

   private byte[] hierarchyFromLiveClass(String sourceName, String runtimeName) {
      try {
         Class<?> type = Class.forName(runtimeName.replace('/', '.'), false, this.runtimeLoader);
         Class<?> superType = type.getSuperclass();
         Class<?>[] interfaceTypes = type.getInterfaces();
         String[] runtimeInterfaces = new String[interfaceTypes.length];

         for (int i = 0; i < interfaceTypes.length; i++) {
            runtimeInterfaces[i] = interfaceTypes[i].getName().replace('.', '/');
         }

         return this.hierarchyClass(sourceName, type.getModifiers(), superType == null ? null : superType.getName().replace('.', '/'), runtimeInterfaces);
      } catch (Throwable var8) {
         return null;
      }
   }

   private byte[] hierarchyClass(String sourceName, int access, String runtimeSuper, String[] runtimeInterfaces) {
      String[] sourceInterfaces = new String[runtimeInterfaces.length];

      for (int i = 0; i < runtimeInterfaces.length; i++) {
         sourceInterfaces[i] = this.mappings.sourceClass(runtimeInterfaces[i]);
      }

      ClassWriter writer = new ClassWriter(0);
      writer.visit(52, access, sourceName, null, runtimeSuper == null ? null : this.mappings.sourceClass(runtimeSuper), sourceInterfaces);
      writer.visitEnd();
      return writer.toByteArray();
   }
}
