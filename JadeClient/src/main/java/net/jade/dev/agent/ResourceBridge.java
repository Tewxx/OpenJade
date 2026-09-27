// Jade recovery: recovered class name: ResourceBridge
package net.jade.dev.agent;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassWriter;
import jade.deps.asm.tree.ClassNode;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;

public final class ResourceBridge {
   private static final URL RESOURCE_URL = createResourceUrl();
   private static volatile ResourceBridge.State state = ResourceBridge.State.empty();

   private ResourceBridge() {
   }

   public static URL resourceUrl() {
      return RESOURCE_URL;
   }

   public static synchronized void publishCore(Map<String, byte[]> classes, Map<String, byte[]> resources, String buildId, String sha256) {
      ResourceBridge.State previous = state;
      boolean samePayload = previous.prepared && equal(previous.Fci, buildId) && equal(previous.sha256, sha256);
      Map<String, byte[]> patched = samePayload ? previous.patchedResources : Collections.emptyMap();
      state = new ResourceBridge.State(copyClasses(classes), copyResources(resources), patched, buildId, sha256, true);
      wipe(previous.classes);
      wipe(previous.resources);
      if (!samePayload) {
         wipe(previous.patchedResources);
      }
   }

   public static synchronized void publishPatched(Map<String, byte[]> patched) {
      ResourceBridge.State previous = state;
      state = new ResourceBridge.State(previous.classes, previous.resources, copyResources(patched), previous.Fci, previous.sha256, previous.prepared);
      wipe(previous.patchedResources);
   }

   public static synchronized void publish(Map<String, byte[]> delivered) {
      ResourceBridge.State previous = state;
      state = new ResourceBridge.State(copyClasses(delivered), previous.resources, previous.patchedResources, previous.Fci, previous.sha256, true);
      wipe(previous.classes);
   }

   public static byte[] readResource(String path) {
      byte[] bytes = lookup(path, state);
      return bytes == null ? null : Arrays.copyOf(bytes, bytes.length);
   }

   public static Map<String, byte[]> copyResources() {
      return copyResources(state.resources);
   }

   public static Set<String> publishedClassNames() {
      Set<String> names = new LinkedHashSet<>();

      for (String binaryName : state.classes.keySet()) {
         names.add(binaryName.replace('.', '/'));
      }

      return Collections.unmodifiableSet(names);
   }

   public static boolean isPrepared() {
      return state.prepared;
   }

   public static String buildId() {
      return state.Fci;
   }

   public static String coreSha256() {
      return state.sha256;
   }

   public static int classCount() {
      return state.classes.size();
   }

   public static boolean isPublishedClass(String internalName) {
      return internalName == null ? false : state.classes.containsKey(toBinaryClassName(internalName));
   }

   public static synchronized void retainHierarchyOnly() {
      ResourceBridge.State previous = state;
      Map<String, byte[]> stubs = new LinkedHashMap<>();

      for (Entry<String, byte[]> entry : previous.classes.entrySet()) {
         try {
            ClassNode node = new ClassNode();
            new ClassReader(entry.getValue()).accept(node, 7);
            ClassWriter writer = new ClassWriter(0);
            writer.visit(node.version, node.access, node.name, null, node.superName, node.interfaces.toArray(new String[node.interfaces.size()]));
            writer.visitEnd();
            stubs.put(entry.getKey(), writer.toByteArray());
         } catch (Throwable var6) {
         }
      }

      state = new ResourceBridge.State(Collections.unmodifiableMap(stubs), previous.resources, previous.patchedResources, previous.Fci, previous.sha256, previous.prepared);
      wipe(previous.classes);
   }

   public static synchronized void clear() {
      ResourceBridge.State previous = state;
      state = ResourceBridge.State.empty();
      wipe(previous.classes);
      wipe(previous.resources);
      wipe(previous.patchedResources);
   }

   private static URL createResourceUrl() {
      try {
         return new URL(null, "jade-memory://core/", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL var1) {
               return new URLConnection(var1) {
                  @Override
                  public void connect() {
                     this.connected = true;
                  }

                  @Override
                  public InputStream getInputStream() throws FileNotFoundException {
                     byte[] var1x = ResourceBridge.lookup(this.url.getPath(), ResourceBridge.state);
                     if (var1x == null) {
                        throw new FileNotFoundException(this.url.getPath());
                     } else {
                        return new ByteArrayInputStream(var1x);
                     }
                  }

                  @Override
                  public int getContentLength() {
                     byte[] var1x = ResourceBridge.lookup(this.url.getPath(), ResourceBridge.state);
                     return var1x == null ? -1 : var1x.length;
                  }

                  @Override
                  public boolean getUseCaches() {
                     return false;
                  }
               };
            }
         });
      } catch (Exception var1) {
         throw new ExceptionInInitializerError(var1);
      }
   }

   private static byte[] lookup(String rawPath, ResourceBridge.State snapshot) {
      String path = normalizeResourcePath(rawPath);
      byte[] bytes = snapshot.patchedResources.get(path);
      if (bytes != null) {
         return bytes;
      } else {
         bytes = snapshot.resources.get(path);
         if (bytes != null) {
            return bytes;
         } else {
            return !path.endsWith(".class")
               ? null
               : snapshot.classes.get(toBinaryClassName(path));
         }
      }
   }

   private static Map<String, byte[]> copyClasses(Map<String, byte[]> source) {
      if (source != null && !source.isEmpty()) {
         Map<String, byte[]> copy = new LinkedHashMap<>();

         for (Entry<String, byte[]> entry : source.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
               copy.put(toBinaryClassName(entry.getKey()), Arrays.copyOf(entry.getValue(), entry.getValue().length));
            }
         }

         return Collections.unmodifiableMap(copy);
      } else {
         return Collections.emptyMap();
      }
   }

   private static Map<String, byte[]> copyResources(Map<String, byte[]> source) {
      if (source != null && !source.isEmpty()) {
         Map<String, byte[]> copy = new LinkedHashMap<>();

         for (Entry<String, byte[]> entry : source.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
               copy.put(normalizeResourcePath(entry.getKey()), Arrays.copyOf(entry.getValue(), entry.getValue().length));
            }
         }

         return Collections.unmodifiableMap(copy);
      } else {
         return Collections.emptyMap();
      }
   }

   private static String normalizeResourcePath(String rawPath) {
      if (rawPath == null) {
         return "";
      } else {
         String path = rawPath.replace('\\', '/');

         while (path.startsWith("/")) {
            path = path.substring(1);
         }

         return path;
      }
   }

   private static String toBinaryClassName(String name) {
      String normalized = normalizeResourcePath(name);
      if (normalized.endsWith(".class")) {
         normalized = normalized.substring(0, normalized.length() - 6);
      }

      return normalized.replace('/', '.');
   }

   private static void wipe(Map<String, byte[]> bytesByName) {
      for (byte[] bytes : bytesByName.values()) {
         if (bytes != null) {
            Arrays.fill(bytes, (byte)0);
         }
      }
   }

   private static boolean equal(Object left, Object right) {
      return left == right || left != null && left.equals(right);
   }

   private static final class State {
      final Map<String, byte[]> classes;
      final Map<String, byte[]> resources;
      final Map<String, byte[]> patchedResources;
      final String Fci;
      final String sha256;
      final boolean prepared;

      State(Map<String, byte[]> var1, Map<String, byte[]> var2, Map<String, byte[]> var3, String var4, String var5, boolean var6) {
         this.classes = var1;
         this.resources = var2;
         this.patchedResources = var3;
         this.Fci = var4;
         this.sha256 = var5;
         this.prepared = var6;
      }

      static ResourceBridge.State empty() {
         return new ResourceBridge.State(Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap(), null, null, false);
      }
   }


}
