// Jade recovery: recovered class name: VanillaHookTransformer; original class: jade.deps.eLz.MH0XMe4dLB
package jade.client.runtime;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassVisitor;
import jade.deps.asm.ClassWriter;
import jade.deps.asm.Handle;
import jade.deps.asm.MethodVisitor;
import jade.deps.asm.commons.Remapper;

import jade.inject.InjectionAgent;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public final class VanillaHookTransformer implements ClassFileTransformer {
   private final RuntimeMappings mappings;
   private final ClientClassTransformer delegate;
   private final boolean preferLiveHierarchy;
   private final boolean preserveClassAccess;

   public VanillaHookTransformer(RuntimeMappings mappings) {
      this(mappings, false);
   }

   public VanillaHookTransformer(RuntimeMappings mappings, boolean badlionRenderPass) {
      this(mappings, badlionRenderPass, false);
   }

   public VanillaHookTransformer(RuntimeMappings mappings, boolean badlionRenderPass, boolean forgeHudPass) {
      this.mappings = mappings;
      this.preferLiveHierarchy = badlionRenderPass;
      this.preserveClassAccess = forgeHudPass || badlionRenderPass;
      this.delegate = new ClientClassTransformer(badlionRenderPass, forgeHudPass);
   }

   public boolean isTargetClassName(String runtimeName) {
      return this.delegate.isConfiguredTarget(this.mappings.sourceClass(runtimeName.replace('.', '/')));
   }

   @Override
   public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
      if (className != null && classfileBuffer != null) {
         String sourceName = this.mappings.sourceClass(className);
         if (!this.delegate.isConfiguredTarget(sourceName)) {
            return null;
         } else {
            int originalClassAccess = new ClassReader(classfileBuffer).getAccess();
            byte[] schemaCompatible = this.preserveClassAccess
               ? RuntimeSchemaReconciler.reconcileSchema(classfileBuffer, classBeingRedefined, originalClassAccess)
               : RuntimeSchemaReconciler.reconcileSchema(classfileBuffer, classBeingRedefined);
            ClassReader rawReader = new ClassReader(schemaCompatible);
            ClassWriter namedWriter = new ClassWriter(rawReader, 0);
            Remapper reverseExact = this.mappings.reverseExact();
            rawReader.accept(new SchemaSafeClassRemapper(589824, namedWriter, reverseExact, reverseExact), 0);
            if (this.preferLiveHierarchy && "net/minecraft/client/gui/GuiNewChat".equals(sourceName)) {
               byte[] liveNamedChat = namedWriter.toByteArray();
               traceBadlionChatCalls(liveNamedChat);
            }

            ClassLoader runtimeLoader = loader == null ? ClassLoader.getSystemClassLoader() : loader;
            byte[] hooked = this.delegate
               .transformCanonical(sourceName, namedWriter.toByteArray(), new VanillaHookTransformer$2(runtimeLoader, this.mappings, this.preferLiveHierarchy));
            if (hooked == null) {
               return null;
            } else {
               ClassReader hookedReader = new ClassReader(hooked);
               ClassWriter runtimeWriter = new ClassWriter(hookedReader, 0);
               Remapper forwardExact = this.mappings.forwardExact();
               hookedReader.accept(new SchemaSafeClassRemapper(589824, runtimeWriter, forwardExact, forwardExact), 0);
               return this.preserveClassAccess
                  ? RuntimeSchemaReconciler.reconcileSchema(runtimeWriter.toByteArray(), classBeingRedefined, originalClassAccess)
                  : RuntimeSchemaReconciler.reconcileSchema(runtimeWriter.toByteArray(), classBeingRedefined);
            }
         }
      } else {
         return null;
      }
   }

   private static void traceBadlionChatCalls(byte[] namedBytes) {
      try {
         new ClassReader(namedBytes)
            .accept(
               new ClassVisitor(589824) {
                  @Override
                  public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                     if ((
                           "drawChat".equals(name)
                              || "func_146230_a".equals(name)
                        )
                        && "(I)V".equals(descriptor)) {
                        InjectionAgent.recordBadlionGradient(
                           "bytecode.method", name + descriptor
                        );
                        return new MethodVisitor(589824) {
                           @Override
                           public void visitMethodInsn(int opcode, String owner, String call, String callDescriptor, boolean isInterface) {
                              if (callDescriptor.indexOf("Ljava/lang/String;") >= 0) {
                                 InjectionAgent.recordBadlionGradient(
                                    "bytecode.call",
                                    "opcode="
                                       + opcode
                                       + " owner="
                                       + owner
                                       + " name="
                                       + call
                                       + " desc="
                                       + callDescriptor
                                 );
                              }
                           }

                           @Override
                           public void visitInvokeDynamicInsn(String call, String callDescriptor, Handle bootstrap, Object... arguments) {
                              if (callDescriptor.indexOf("Ljava/lang/String;") >= 0) {
                                 InjectionAgent.recordBadlionGradient(
                                    "bytecode.indy",
                                    "name="
                                       + call
                                       + " desc="
                                       + callDescriptor
                                 );
                              }
                           }
                        };
                     } else {
                        return null;
                     }
                  }
               },
               6
            );
      } catch (Throwable var2) {
         InjectionAgent.recordBadlionGradient(
            "bytecode.failure", var2.getClass().getName()
         );
      }
   }
}
