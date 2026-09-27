// Jade recovery: recovered class name: RuntimeFeatureHierarchy; original class: jade.deps.eLz.EspmIH
package jade.client.runtime;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassVisitor;
import jade.deps.asm.FieldVisitor;
import jade.deps.asm.MethodVisitor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;

public final class RuntimeFeatureHierarchy {
   private final Map<String, List<String>> parents;
   private final Map<String, Set<String>> fields;
   private final Map<String, Set<String>> methods;

   private RuntimeFeatureHierarchy(Map<String, List<String>> parents, Map<String, Set<String>> fields, Map<String, Set<String>> methods) {
      this.parents = parents;
      this.fields = fields;
      this.methods = methods;
   }

   public static RuntimeFeatureHierarchy fromClassBytes(Map<String, byte[]> classes) {
      Map<String, List<String>> parents = new HashMap<>();
      Map<String, Set<String>> fields = new HashMap<>();
      Map<String, Set<String>> methods = new HashMap<>();

      for (Entry<String, byte[]> entry : classes.entrySet()) {
         final List<String> classParents = new ArrayList<>();
         final Set<String> classFields = new HashSet<>();
         final Set<String> classMethods = new HashSet<>();
         new ClassReader(entry.getValue()).accept(new ClassVisitor(589824) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
               if (superName != null) {
                  classParents.add(superName);
               }

               if (interfaces != null) {
                  Collections.addAll(classParents, interfaces);
               }
            }

            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
               classFields.add(name + " " + descriptor);
               return null;
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
               classMethods.add(name + " " + descriptor);
               return null;
            }
         }, 7);
         parents.put(entry.getKey(), Collections.unmodifiableList(classParents));
         fields.put(entry.getKey(), Collections.unmodifiableSet(classFields));
         methods.put(entry.getKey(), Collections.unmodifiableSet(classMethods));
      }

      return new RuntimeFeatureHierarchy(parents, fields, methods);
   }

   public String minecraftFieldOwner(String owner, String name, String descriptor) {
      return this.minecraftOwner(owner, name + " " + descriptor, this.fields);
   }

   public String minecraftMethodOwner(String owner, String name, String descriptor) {
      if (owner == null) {
         return null;
      } else if (owner.startsWith("net/minecraft/")) {
         return owner;
      } else {
         ArrayDeque<String> pending = new ArrayDeque<>();
         Set<String> visited = new HashSet<>();
         List<String> directParents = this.parents.get(owner);
         if (directParents == null) {
            return null;
         } else {
            pending.addAll(directParents);

            while (!pending.isEmpty()) {
               String current = pending.removeFirst();
               if (visited.add(current)) {
                  if (current.startsWith("net/minecraft/")) {
                     return current;
                  }

                  List<String> next = this.parents.get(current);
                  if (next != null) {
                     pending.addAll(next);
                  }
               }
            }

            return null;
         }
      }
   }

   private String minecraftOwner(String owner, String member, Map<String, Set<String>> declarations) {
      if (owner == null) {
         return null;
      } else if (owner.startsWith("net/minecraft/")) {
         return owner;
      } else if (!this.parents.containsKey(owner)) {
         return null;
      } else {
         ArrayDeque<String> pending = new ArrayDeque<>();
         Set<String> visited = new HashSet<>();
         pending.add(owner);

         while (!pending.isEmpty()) {
            String current = pending.removeFirst();
            if (visited.add(current)) {
               if (current.startsWith("net/minecraft/")) {
                  return current;
               }

               Set<String> declared = declarations.get(current);
               if (declared != null && declared.contains(member)) {
                  return null;
               }

               List<String> next = this.parents.get(current);
               if (next != null) {
                  pending.addAll(next);
               }
            }
         }

         return null;
      }
   }
}
