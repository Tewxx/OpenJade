// Jade recovery: original class: jade.deps.eLz.oxw36UpI
package jade.client.hook;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassWriter;
import jade.deps.asm.MethodVisitor;
import jade.deps.asm.Type;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import net.jade.dev.agent.AgentBootstrap;

public final class MixinProxyPatcher implements ClassFileTransformer {
   private static final String MIXIN_PROXY_IMPL_CLASS = "org/spongepowered/asm/mixin/transformer/MixinProxyImpl";
   private static final String CALLBACK_FIELD_NAME = "jade$mixinCallback";
   private static final String AGENT_BOOTSTRAP_CLASS = "net.jade.dev.agent.AgentBootstrap";
   private static final String PIPELINE_READY_METHOD = "onIchorMixinPipelineReady";
   private final MixinProxyPatcher$1 failureReporter;

   public MixinProxyPatcher() {
      this(new MixinProxyPatcher$1() {
         @Override
         public void reportFailure(String var1) {
            AgentBootstrap.onIchorMixinPipelineRejected(var1);
         }
      });
   }

   public MixinProxyPatcher(MixinProxyPatcher$1 var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("failureHandler");
      } else {
         this.failureReporter = var1;
      }
   }

   @Override
   public byte[] transform(ClassLoader var1, String var2, Class<?> var3, ProtectionDomain var4, byte[] var5) throws IllegalClassFormatException {
      if ("org/spongepowered/asm/mixin/transformer/MixinProxyImpl".equals(var2) && var5 != null) {
         try {
            ClassReader var6 = new ClassReader(var5);
            if (!"org/spongepowered/asm/mixin/transformer/MixinProxyImpl".equals(var6.getClassName())) {
               this.failureReporter
                  .reportFailure(
                     "Ichor Mixin proxy bytes declared an unexpected class name"
                  );
               return EHZwX(var5);
            } else {
               ClassWriter var7 = new ClassWriter(var6, 1);
               MixinProxyPatcher$5 var8 = new MixinProxyPatcher$5(var7);
               var6.accept(var8, 0);
               if (MixinProxyPatcher$5.hasStageConstant(var8) && !MixinProxyPatcher$5.hasMixinCallbackField(var8) && MixinProxyPatcher$5.didInstrumentRegisterMixins(var8) && MixinProxyPatcher$5.didInstrumentTransformClassNode(var8) && MixinProxyPatcher$5.getTransformClassCallCount(var8) == 1) {
                  return var7.toByteArray();
               } else {
                  this.failureReporter
                     .reportFailure(
                        "Ichor MixinProxyImpl registration hook contract changed (stage="
                           + MixinProxyPatcher$5.hasStageConstant(var8)
                           + ", bridgeCollision="
                           + MixinProxyPatcher$5.hasMixinCallbackField(var8)
                           + ", register="
                           + MixinProxyPatcher$5.didInstrumentRegisterMixins(var8)
                           + ", transform="
                           + MixinProxyPatcher$5.didInstrumentTransformClassNode(var8)
                           + ", calls="
                           + MixinProxyPatcher$5.getTransformClassCallCount(var8)
                           + ")"
                     );
                  return EHZwX(var5);
               }
            }
         } catch (RuntimeException var9) {
            this.failureReporter
               .reportFailure(
                  "Unable to install Ichor Mixin registration hooks: "
                     + var9.getClass().getSimpleName()
               );
            return EHZwX(var5);
         }
      } else {
         return null;
      }
   }

   private static void emitCallbackInvocation(MethodVisitor var0) {
      var0.visitFieldInsn(
         178,
         "org/spongepowered/asm/mixin/transformer/MixinProxyImpl",
         "jade$mixinCallback",
         "Ljava/lang/invoke/MethodHandle;"
      );
      var0.visitVarInsn(25, 0);
      var0.visitFieldInsn(
         180,
         "org/spongepowered/asm/mixin/transformer/MixinProxyImpl",
         "stage",
         "Ljava/lang/String;"
      );
      var0.visitLdcInsn(Type.getObjectType("org/spongepowered/asm/mixin/transformer/MixinProxyImpl"));
      var0.visitMethodInsn(
         182, "java/lang/Class", "getClassLoader", "()Ljava/lang/ClassLoader;", false
      );
      var0.visitMethodInsn(
         182,
         "java/lang/invoke/MethodHandle",
         "invokeExact",
         "(Ljava/lang/String;Ljava/lang/ClassLoader;)V",
         false
      );
   }

   private static void emitCallbackHandleInstaller(MethodVisitor var0) {
      var0.visitMethodInsn(
         184,
         "java/lang/invoke/MethodHandles",
         "publicLookup",
         "()Ljava/lang/invoke/MethodHandles$Lookup;",
         false
      );
      var0.visitMethodInsn(
         184,
         "java/lang/ClassLoader",
         "getSystemClassLoader",
         "()Ljava/lang/ClassLoader;",
         false
      );
      var0.visitLdcInsn("net.jade.dev.agent.AgentBootstrap");
      var0.visitMethodInsn(
         182,
         "java/lang/ClassLoader",
         "loadClass",
         "(Ljava/lang/String;)Ljava/lang/Class;",
         false
      );
      var0.visitLdcInsn("onIchorMixinPipelineReady");
      var0.visitInsn(5);
      var0.visitTypeInsn(189, "java/lang/Class");
      var0.visitInsn(89);
      var0.visitInsn(3);
      var0.visitLdcInsn(Type.getType(String.class));
      var0.visitInsn(83);
      var0.visitInsn(89);
      var0.visitInsn(4);
      var0.visitLdcInsn(Type.getType(ClassLoader.class));
      var0.visitInsn(83);
      var0.visitMethodInsn(
         182,
         "java/lang/Class",
         "getMethod",
         "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;",
         false
      );
      var0.visitMethodInsn(
         182,
         "java/lang/invoke/MethodHandles$Lookup",
         "unreflect",
         "(Ljava/lang/reflect/Method;)Ljava/lang/invoke/MethodHandle;",
         false
      );
      var0.visitFieldInsn(
         179,
         "org/spongepowered/asm/mixin/transformer/MixinProxyImpl",
         "jade$mixinCallback",
         "Ljava/lang/invoke/MethodHandle;"
      );
   }

   private static byte[] EHZwX(byte[] var0) {
      if (var0.length < 4) {
         return new byte[]{0};
      } else {
         byte[] var1 = (byte[])var0.clone();
         var1[0] = 0;
         var1[1] = 0;
         var1[2] = 0;
         var1[3] = 0;
         return var1;
      }
   }

   public static void emitCallbackHandleSetup(MethodVisitor var0) {
      emitCallbackHandleInstaller(var0);
   }

   public static void emitCallbackCallSite(MethodVisitor var0) {
      emitCallbackInvocation(var0);
   }
}
