// Jade recovery: recovered class name: RuntimeSchemaReconciler; original class: jade.deps.eLz.JQauqG
package jade.client.runtime;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassWriter;
import jade.deps.asm.Type;
import jade.deps.asm.tree.ClassNode;
import jade.deps.asm.tree.FieldNode;
import jade.deps.asm.tree.MethodNode;

import jade.inject.InjectionAgent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class RuntimeSchemaReconciler {
   private static final int ASM_API = 589824;

   private RuntimeSchemaReconciler() {
   }

   public static byte[] reconcileSchema(byte[] input, Class<?> liveClass) {
      return reconcileSchema(input, liveClass, null);
   }

   public static byte[] reconcileSchema(byte[] input, Class<?> liveClass, int classAccess) {
      return reconcileSchema(input, liveClass, Integer.valueOf(classAccess));
   }

   private static byte[] reconcileSchema(byte[] input, Class<?> liveClass, Integer requiredClassAccess) {
      if (input != null && liveClass != null) {
         try {
            ClassNode node = new ClassNode(589824);
            new ClassReader(input).accept(node, 0);
            boolean changed = requiredClassAccess == null ? reconcileClassAccess(node, liveClass) : reconcileClassAccess(node, requiredClassAccess);
            changed |= reconcileHierarchy(node, liveClass);
            Map<String, FieldNode> encoded = new HashMap<>();

            for (FieldNode field : node.fields) {
               encoded.put(key(field.name, field.desc), field);
            }

            Field[] liveFields = liveClass.getDeclaredFields();
            List<FieldNode> reconciled = new ArrayList<>(liveFields.length);
            changed |= liveFields.length != node.fields.size();

            for (Field liveField : liveFields) {
               String descriptor = Type.getDescriptor(liveField.getType());
               FieldNode field = encoded.remove(key(liveField.getName(), descriptor));
               if (field == null) {
                  field = new FieldNode(589824, liveField.getModifiers(), liveField.getName(), descriptor, null, null);
                  changed = true;
               } else if (field.access != liveField.getModifiers()) {
                  field.access = liveField.getModifiers();
                  changed = true;
               }

               reconciled.add(field);
            }

            if (!encoded.isEmpty()) {
               changed = true;
            }

            node.fields.clear();
            node.fields.addAll(reconciled);
            changed |= reconcileMethods(node, liveClass);
            if (!changed) {
               return input;
            } else {
               ClassWriter writer = new ClassWriter(0);
               node.accept(writer);
               byte[] output = writer.toByteArray();
               InjectionAgent.reportDiagnostic(
                  "reconciled live class schema for "
                     + liveClass.getName()
                     + " ("
                     + liveFields.length
                     + " fields, "
                     + liveClass.getDeclaredConstructors().length
                     + " constructors, "
                     + liveClass.getDeclaredMethods().length
                     + " methods)"
               );
               return output;
            }
         } catch (Throwable var14) {
            InjectionAgent.reportDiagnostic(
               "could not reconcile live class schema for "
                  + liveClass.getName()
                  + ": "
                  + var14
            );
            throw new IllegalStateException(
               "live schema reconciliation failed for "
                  + liveClass.getName()
                  + ": "
                  + var14.getMessage(),
               var14
            );
         }
      } else {
         return input;
      }
   }

   private static boolean reconcileClassAccess(ClassNode node, int classAccess) {
      if (node.access == classAccess) {
         return false;
      } else {
         node.access = classAccess;
         return true;
      }
   }

   private static boolean reconcileClassAccess(ClassNode node, Class<?> liveClass) {
      int classSchemaAccess = 30225;
      int liveAccess = node.access & ~classSchemaAccess | liveClass.getModifiers() & classSchemaAccess;
      if (liveClass.isSynthetic()) {
         liveAccess |= 4096;
      }

      if (node.access == liveAccess) {
         return false;
      } else {
         node.access = liveAccess;
         return true;
      }
   }

   private static boolean reconcileHierarchy(ClassNode node, Class<?> liveClass) {
      boolean changed = false;
      Class<?> liveSuper = liveClass.getSuperclass();
      String superName = liveSuper == null ? null : Type.getInternalName(liveSuper);
      if (superName == null ? node.superName != null : !superName.equals(node.superName)) {
         node.superName = superName;
         changed = true;
      }

      Class<?>[] liveInterfaces = liveClass.getInterfaces();
      List<String> interfaces = new ArrayList<>(liveInterfaces.length);

      for (Class<?> liveInterface : liveInterfaces) {
         interfaces.add(Type.getInternalName(liveInterface));
      }

      if (!interfaces.equals(node.interfaces)) {
         node.interfaces.clear();
         node.interfaces.addAll(interfaces);
         changed = true;
      }

      return changed;
   }

   private static boolean reconcileMethods(ClassNode node, Class<?> liveClass) {
      Map<String, MethodNode> encoded = new LinkedHashMap<>();
      MethodNode classInitializer = null;

      for (MethodNode method : node.methods) {
         if ("<clinit>".equals(method.name)) {
            classInitializer = method;
         } else {
            encoded.put(key(method.name, method.desc), method);
         }
      }

      List<MethodNode> reconciled = new ArrayList<>(node.methods.size());
      if (classInitializer != null) {
         reconciled.add(classInitializer);
      }

      boolean changed = false;

      for (Constructor<?> constructor : liveClass.getDeclaredConstructors()) {
         changed |= addLiveMethod(reconciled, encoded, "<init>", Type.getConstructorDescriptor(constructor), constructor.getModifiers());
      }

      for (Method methodx : liveClass.getDeclaredMethods()) {
         changed |= addLiveMethod(reconciled, encoded, methodx.getName(), Type.getMethodDescriptor(methodx), methodx.getModifiers());
      }

      if (!encoded.isEmpty()) {
         changed = true;
      }

      if (reconciled.size() != node.methods.size()) {
         changed = true;
      }

      if (changed) {
         node.methods.clear();
         node.methods.addAll(reconciled);
      }

      return changed;
   }

   private static boolean addLiveMethod(List<MethodNode> output, Map<String, MethodNode> encoded, String name, String descriptor, int modifiers) {
      MethodNode method = encoded.remove(key(name, descriptor));
      if (method == null) {
         throw new IllegalStateException(
            "retransformation bytes omit live method "
               + name
               + descriptor
         );
      } else {
         boolean changed = method.access != modifiers;
         method.access = modifiers;
         output.add(method);
         return changed;
      }
   }

   private static String key(String name, String descriptor) {
      return name + " " + descriptor;
   }
}
