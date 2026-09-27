// Jade recovery: recovered class name: RuntimeMappings; original class: jade.deps.eLz.n9Wea2
package jade.client.runtime;

import jade.deps.asm.commons.Remapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;

public final class RuntimeMappings {
   private final Map<String, String> classes;
   private final Map<String, String> reverseClasses;
   private final Map<String, String> fields;
   private final Map<String, String> reverseFields;
   private final Map<String, String> methods;
   private final Map<String, String> reverseMethods;
   private final Map<String, String> fieldFallbacks;
   private final Map<String, String> reverseFieldFallbacks;
   private final Map<String, String> methodFallbacks;
   private final Map<String, String> reverseMethodFallbacks;
   private final Map<String, List<String>> parents;

   private RuntimeMappings(
      Map<String, String> classes,
      Map<String, String> reverseClasses,
      Map<String, String> fields,
      Map<String, String> reverseFields,
      Map<String, String> methods,
      Map<String, String> reverseMethods,
      Map<String, String> fieldFallbacks,
      Map<String, String> reverseFieldFallbacks,
      Map<String, String> methodFallbacks,
      Map<String, String> reverseMethodFallbacks,
      Map<String, List<String>> parents
   ) {
      this.classes = Collections.unmodifiableMap(classes);
      this.reverseClasses = Collections.unmodifiableMap(reverseClasses);
      this.fields = Collections.unmodifiableMap(fields);
      this.reverseFields = Collections.unmodifiableMap(reverseFields);
      this.methods = Collections.unmodifiableMap(methods);
      this.reverseMethods = Collections.unmodifiableMap(reverseMethods);
      this.fieldFallbacks = Collections.unmodifiableMap(fieldFallbacks);
      this.reverseFieldFallbacks = Collections.unmodifiableMap(reverseFieldFallbacks);
      this.methodFallbacks = Collections.unmodifiableMap(methodFallbacks);
      this.reverseMethodFallbacks = Collections.unmodifiableMap(reverseMethodFallbacks);
      this.parents = Collections.unmodifiableMap(parents);
   }

   public static RuntimeMappings load(String resource) {
      InputStream stream = RuntimeMappings.class.getResourceAsStream(resource);
      if (stream == null) {
         throw new IllegalStateException(
            "missing runtime mapping resource " + resource
         );
      } else {
         Map<String, String> classes = new HashMap<>();
         Map<String, String> reverseClasses = new HashMap<>();
         Map<String, String> fields = new HashMap<>();
         Map<String, String> reverseFields = new HashMap<>();
         Map<String, String> methods = new HashMap<>();
         Map<String, String> reverseMethods = new HashMap<>();

         try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

            String line;
            try {
               while ((line = reader.readLine()) != null) {
                  String[] part = line.split(" ");
                  if (line.startsWith("CL: ") && part.length >= 3) {
                     classes.put(part[1], part[2]);
                     reverseClasses.put(part[2], part[1]);
                  } else if (line.startsWith("FD: ") && part.length >= 3) {
                     int sourceSlash = part[1].lastIndexOf(47);
                     int runtimeSlash = part[2].lastIndexOf(47);
                     if (sourceSlash > 0 && runtimeSlash > 0) {
                        fields.put(part[1], part[2].substring(runtimeSlash + 1));
                        reverseFields.put(part[2], part[1].substring(sourceSlash + 1));
                     }
                  } else if (line.startsWith("MD: ") && part.length >= 5) {
                     int sourceSlash = part[1].lastIndexOf(47);
                     int runtimeSlash = part[3].lastIndexOf(47);
                     if (sourceSlash > 0 && runtimeSlash > 0) {
                        methods.put(
                           part[1] + " " + part[2],
                           part[3].substring(runtimeSlash + 1)
                        );
                        reverseMethods.put(
                           part[3] + " " + part[4], part[1].substring(sourceSlash + 1)
                        );
                     }
                  }
               }
            } finally {
               reader.close();
            }
         } catch (Exception var17) {
            throw new IllegalStateException("could not parse " + resource, var17);
         }

         return new RuntimeMappings(
            classes,
            reverseClasses,
            fields,
            reverseFields,
            methods,
            reverseMethods,
            uniqueFieldFallbacks(fields),
            uniqueFieldFallbacks(reverseFields),
            uniqueMethodFallbacks(methods),
            uniqueMethodFallbacks(reverseMethods),
            loadHierarchy()
         );
      }
   }

   public String sourceClass(String runtimeName) {
      String mapped = this.reverseClasses.get(runtimeName);
      return mapped == null ? runtimeName : mapped;
   }

   public Remapper forward() {
      return new RuntimeMappings$1(this, false, true);
   }

   public Remapper reverse() {
      return new RuntimeMappings$1(this, true, true);
   }

   public Remapper forwardExact() {
      return new RuntimeMappings$1(this, false, false);
   }

   public Remapper forwardMinecraftMembers() {
      return new RuntimeMappings$1(this, false, true, true);
   }

   public Remapper reverseExact() {
      return new RuntimeMappings$1(this, true, false);
   }

   public String runtimeClass(String sourceName) {
      String mapped = this.classes.get(sourceName);
      return mapped == null ? sourceName : mapped;
   }

   public String runtimeField(String sourceOwner, String sourceName) {
      return new RuntimeMappings$1(this, false, true).mapFieldName(sourceOwner, sourceName, "");
   }

   public String runtimeMethod(String sourceOwner, String sourceName, String descriptor) {
      return new RuntimeMappings$1(this, false, true).mapMethodName(sourceOwner, sourceName, descriptor);
   }

   public String sourceDescriptor(String runtimeDescriptor) {
      return new RuntimeMappings$1(this, true, false).mapDesc(runtimeDescriptor);
   }

   public String runtimeDescriptor(String sourceDescriptor) {
      return new RuntimeMappings$1(this, false, false).mapDesc(sourceDescriptor);
   }

   public String runtimeMethodDescriptor(String sourceDescriptor) {
      return new RuntimeMappings$1(this, false, false).mapMethodDesc(sourceDescriptor);
   }

   private String inheritedField(String owner, String name, boolean reverse) {
      for (String ancestor : this.ancestors(this.sourceOwner(owner, reverse))) {
         String keyOwner = reverse ? this.runtimeClass(ancestor) : ancestor;
         String mapped = (reverse ? this.reverseFields : this.fields)
            .get(keyOwner + "/" + name);
         if (mapped != null) {
            return mapped;
         }
      }

      return null;
   }

   private String inheritedMethod(String owner, String name, String descriptor, boolean reverse) {
      for (String ancestor : this.ancestors(this.sourceOwner(owner, reverse))) {
         String keyOwner = reverse ? this.runtimeClass(ancestor) : ancestor;
         String mapped = (reverse ? this.reverseMethods : this.methods)
            .get(
               keyOwner
                  + "/"
                  + name
                  + " "
                  + descriptor
            );
         if (mapped != null) {
            return mapped;
         }
      }

      return null;
   }

   private String sourceOwner(String owner, boolean reverse) {
      if (!reverse) {
         return owner;
      } else {
         String mapped = this.reverseClasses.get(owner);
         return mapped == null ? owner : mapped;
      }
   }

   private List<String> ancestors(String owner) {
      if (owner != null && owner.startsWith("net/minecraft/")) {
         List<String> result = new ArrayList<>();
         ArrayDeque<String> pending = new ArrayDeque<>();
         Set<String> visited = new HashSet<>();
         List<String> direct = this.parents.get(owner);
         if (direct != null) {
            pending.addAll(direct);
         }

         while (!pending.isEmpty()) {
            String current = pending.removeFirst();
            if (visited.add(current)) {
               result.add(current);
               List<String> next = this.parents.get(current);
               if (next != null) {
                  pending.addAll(next);
               }
            }
         }

         return result;
      } else {
         return Collections.emptyList();
      }
   }

   private static Map<String, List<String>> loadHierarchy() {
      InputStream stream = RuntimeMappings.class
         .getResourceAsStream("/jade/inject/mappings/minecraft-hierarchy.txt");
      if (stream == null) {
         throw new IllegalStateException("missing runtime Minecraft hierarchy");
      } else {
         Map<String, List<String>> hierarchy = new HashMap<>();

         try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

            String line;
            try {
               while ((line = reader.readLine()) != null) {
                  line = line.trim();
                  if (!line.isEmpty()) {
                     String[] part = line.split(" ");
                     hierarchy.put(
                        part[0],
                        part.length == 1 ? Collections.emptyList() : Collections.unmodifiableList(Arrays.asList(Arrays.copyOfRange(part, 1, part.length)))
                     );
                  }
               }
            } finally {
               reader.close();
            }

            return hierarchy;
         } catch (Exception var9) {
            throw new IllegalStateException(
               "could not parse runtime Minecraft hierarchy", var9
            );
         }
      }
   }

   private static Map<String, String> uniqueFieldFallbacks(Map<String, String> exact) {
      Map<String, String> values = new HashMap<>();
      Set<String> conflicts = new HashSet<>();

      for (Entry<String, String> entry : exact.entrySet()) {
         int slash = entry.getKey().lastIndexOf(47);
         if (slash >= 0) {
            String key = entry.getKey().substring(slash + 1);
            String previous = values.put(key, entry.getValue());
            if (previous != null && !previous.equals(entry.getValue())) {
               conflicts.add(key);
            }
         }
      }

      for (String conflict : conflicts) {
         values.remove(conflict);
      }

      return values;
   }

   private static Map<String, String> uniqueMethodFallbacks(Map<String, String> exact) {
      Map<String, String> values = new HashMap<>();
      Set<String> conflicts = new HashSet<>();

      for (Entry<String, String> entry : exact.entrySet()) {
         int space = entry.getKey().indexOf(32);
         int slash = entry.getKey().lastIndexOf(47, space);
         if (slash >= 0 && space >= 0) {
            String key = entry.getKey().substring(slash + 1);
            String previous = values.put(key, entry.getValue());
            if (previous != null && !previous.equals(entry.getValue())) {
               conflicts.add(key);
            }
         }
      }

      for (String conflict : conflicts) {
         values.remove(conflict);
      }

      return values;
   }

   static java.util.Map access$200(jade.client.runtime.RuntimeMappings arg0) {
      return arg0.reverseClasses;
   }

   static java.util.Map access$300(jade.client.runtime.RuntimeMappings arg0) {
      return arg0.classes;
   }

   static java.util.Map access$400(jade.client.runtime.RuntimeMappings arg0) {
      return arg0.reverseFields;
   }

   static java.util.Map access$500(jade.client.runtime.RuntimeMappings arg0) {
      return arg0.fields;
   }

   static java.util.Map access$600(jade.client.runtime.RuntimeMappings arg0) {
      return arg0.reverseFieldFallbacks;
   }

   static java.util.Map access$700(jade.client.runtime.RuntimeMappings arg0) {
      return arg0.fieldFallbacks;
   }

   static java.lang.String access$800(jade.client.runtime.RuntimeMappings arg0, java.lang.String arg1, java.lang.String arg2, boolean arg3) {
      return arg0.inheritedField(arg1, arg2, arg3);
   }

   static java.util.Map access$900(jade.client.runtime.RuntimeMappings arg0) {
      return arg0.reverseMethods;
   }

   static java.util.Map access$1000(jade.client.runtime.RuntimeMappings arg0) {
      return arg0.methods;
   }

   static java.util.Map access$1100(jade.client.runtime.RuntimeMappings arg0) {
      return arg0.reverseMethodFallbacks;
   }

   static java.util.Map access$1200(jade.client.runtime.RuntimeMappings arg0) {
      return arg0.methodFallbacks;
   }

   static java.lang.String access$1300(jade.client.runtime.RuntimeMappings arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, boolean arg4) {
      return arg0.inheritedMethod(arg1, arg2, arg3, arg4);
   }
}
