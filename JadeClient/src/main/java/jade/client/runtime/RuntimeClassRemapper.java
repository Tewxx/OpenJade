// Jade recovery: recovered class name: RuntimeClassRemapper; original class: jade.deps.eLz.Hlsbhh
package jade.client.runtime;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassVisitor;
import jade.deps.asm.ClassWriter;
import jade.deps.asm.Handle;
import jade.deps.asm.Type;
import jade.deps.asm.commons.ClassRemapper;
import jade.deps.asm.commons.Remapper;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Collections;
import java.util.Set;

public final class RuntimeClassRemapper implements ClassFileTransformer {
   private static final int ASM_API = 589824;
   private static final Handle ACCESS_BOOTSTRAP = new Handle(
      6,
      "jade/inject/RuntimeAccess",
      "bootstrap",
      "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;I)Ljava/lang/invoke/CallSite;"
   );
   private static final Handle BADLION_MEMBER_BOOTSTRAP = new Handle(
      6,
      "jade/inject/RuntimeAccess",
      "bootstrapMinecraftMember",
      "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/invoke/CallSite;"
   );
   private final RuntimeMappings mappings;
   private final Set<String> featureClasses;
   private final RuntimeFeatureHierarchy featureHierarchy;
   private final boolean bridgeMinecraftMembers;

   public RuntimeClassRemapper(RuntimeMappings mappings, Set<String> featureClasses) {
      this(mappings, featureClasses, RuntimeFeatureHierarchy.fromClassBytes(Collections.emptyMap()), false);
   }

   public RuntimeClassRemapper(RuntimeMappings mappings, Set<String> featureClasses, RuntimeFeatureHierarchy featureHierarchy) {
      this(mappings, featureClasses, featureHierarchy, false);
   }

   public RuntimeClassRemapper(RuntimeMappings mappings, Set<String> featureClasses, RuntimeFeatureHierarchy featureHierarchy, boolean bridgeMinecraftMembers) {
      this.mappings = mappings;
      this.featureClasses = featureClasses;
      this.featureHierarchy = featureHierarchy;
      this.bridgeMinecraftMembers = bridgeMinecraftMembers;
   }

   @Override
   public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
      if (className != null && classfileBuffer != null && this.featureClasses.contains(className)) {
         ClassReader reader = new ClassReader(classfileBuffer);
         ClassWriter writer = new ClassWriter(reader, 0);
         ClassVisitor output = (ClassVisitor)(this.mappings == null ? writer : new ClassRemapper(589824, writer, this.hierarchyAwareRemapper()) {});
         reader.accept(new RuntimeClassRemapper$3(output, this.bridgeMinecraftMembers), 0);
         return writer.toByteArray();
      } else {
         return null;
      }
   }

   private Remapper hierarchyAwareRemapper() {
      final Remapper delegate = this.mappings.forwardMinecraftMembers();
      return new Remapper() {
         @Override
         public String map(String internalName) {
            return delegate.map(internalName);
         }

         @Override
         public String mapFieldName(String owner, String name, String descriptor) {
            String minecraftOwner = RuntimeClassRemapper.this.featureHierarchy.minecraftFieldOwner(owner, name, descriptor);
            return delegate.mapFieldName(minecraftOwner == null ? owner : minecraftOwner, name, descriptor);
         }

         @Override
         public String mapMethodName(String owner, String name, String descriptor) {
            String minecraftOwner = RuntimeClassRemapper.this.featureHierarchy.minecraftMethodOwner(owner, name, descriptor);
            return delegate.mapMethodName(minecraftOwner == null ? owner : minecraftOwner, name, descriptor);
         }
      };
   }

   private static boolean accessor(String owner) {
      return owner != null && (owner.startsWith("jade/mixin/impl/accessor/IAccessor") || "jade/mixin/interfaces/IMixinItemRenderer".equals(owner));
   }

   private static String withReceiver(String descriptor) {
      Type method = Type.getMethodType(descriptor);
      Type[] original = method.getArgumentTypes();
      Type[] arguments = new Type[original.length + 1];
      arguments[0] = Type.getType(Object.class);
      System.arraycopy(original, 0, arguments, 1, original.length);
      return Type.getMethodDescriptor(method.getReturnType(), arguments);
   }

   private static String withTypedReceiver(String owner, String descriptor) {
      Type method = Type.getMethodType(descriptor);
      Type[] original = method.getArgumentTypes();
      Type[] arguments = new Type[original.length + 1];
      arguments[0] = Type.getObjectType(owner);
      System.arraycopy(original, 0, arguments, 1, original.length);
      return Type.getMethodDescriptor(method.getReturnType(), arguments);
   }

   static jade.client.runtime.RuntimeFeatureHierarchy access$100(jade.client.runtime.RuntimeClassRemapper arg0) {
      return arg0.featureHierarchy;
   }

   static jade.deps.asm.Handle access$300() {
      return jade.client.runtime.RuntimeClassRemapper.BADLION_MEMBER_BOOTSTRAP;
   }

   static boolean access$400(java.lang.String arg0) {
      return jade.client.runtime.RuntimeClassRemapper.accessor(arg0);
   }

   static java.lang.String access$500(java.lang.String arg0, java.lang.String arg1) {
      return jade.client.runtime.RuntimeClassRemapper.withTypedReceiver(arg0, arg1);
   }

   static java.lang.String access$600(java.lang.String arg0) {
      return jade.client.runtime.RuntimeClassRemapper.withReceiver(arg0);
   }

   static jade.deps.asm.Handle access$700() {
      return jade.client.runtime.RuntimeClassRemapper.ACCESS_BOOTSTRAP;
   }
}
