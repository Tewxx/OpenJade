// Jade recovery: original class: jade.deps.eLz.oxw36UpI$5
package jade.client.hook;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.FieldVisitor;
import jade.deps.asm.MethodVisitor;
import jade.deps.asm.Type;

public final class MixinProxyPatcher$5 extends ClassVisitor {
   private boolean foundStageConstant;
   private boolean foundMixinCallbackField;
   private boolean foundStaticInitializer;
   private boolean instrumentedRegisterMixins;
   private boolean instrumentedTransformClassNode;
   private int transformClassCallCount;

   MixinProxyPatcher$5(ClassVisitor var1) {
      super(327680, var1);
   }

   @Override
   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      super.visit(var1, var2, var3, var4, var5, var6);
      FieldVisitor var7 = super.visitField(
         4122,
         "jade$mixinCallback",
         "Ljava/lang/invoke/MethodHandle;",
         null,
         null
      );
      if (var7 != null) {
         var7.visitEnd();
      }
   }

   @Override
   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      if ("stage".equals(var2)
         && "Ljava/lang/String;".equals(var3)
         && (var1 & 18) == 18
         && (var1 & 8) == 0) {
         this.foundStageConstant = true;
      }

      if ("jade$mixinCallback".equals(var2)) {
         this.foundMixinCallbackField = true;
      }

      return super.visitField(var1, var2, var3, var4, var5);
   }

   @Override
   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodVisitor var6 = super.visitMethod(var1, var2, var3, var4, var5);
      if ((var1 & 8) != 0 && "<clinit>".equals(var2) && "()V".equals(var3)) {
         this.foundStaticInitializer = true;
         return new MethodVisitor(327680, var6) {
            @Override
            public void visitCode() {
               super.visitCode();
               MixinProxyPatcher.emitCallbackHandleSetup(this.mv);
            }
         };
      } else {
         boolean var7 = (var1 & 1) != 0 && (var1 & 8) == 0;
         if (var7
            && "registerMixins".equals(var2)
            && "(Ljava/util/List;)V".equals(var3)) {
            this.instrumentedRegisterMixins = true;
            return new MethodVisitor(327680, var6) {
               @Override
               public void visitInsn(int var1) {
                  if (var1 == 177) {
                     MixinProxyPatcher.emitCallbackCallSite(this.mv);
                  }

                  super.visitInsn(var1);
               }
            };
         } else if (var7 && "transformClassNode".equals(var2) && isTransformClassNodeDescriptor(var3)) {
            this.instrumentedTransformClassNode = true;
            return new MethodVisitor(327680, var6) {
               @Override
               public void visitCode() {
                  super.visitCode();
                  MixinProxyPatcher.emitCallbackCallSite(this.mv);
               }

               @Override
               public void visitMethodInsn(int var1, String var2x, String var3x, String var4x, boolean var5x) {
                  if ("org/spongepowered/asm/mixin/transformer/MixinTransformer".equals(var2x)
                     && "transformClass".equals(var3x)) {
                     MixinProxyPatcher$5.incrementTransformClassCallCount(MixinProxyPatcher$5.this);
                  }

                  super.visitMethodInsn(var1, var2x, var3x, var4x, var5x);
               }
            };
         } else {
            return var6;
         }
      }
   }

   @Override
   public void visitEnd() {
      if (!this.foundStaticInitializer) {
         MethodVisitor var1 = super.visitMethod(8, "<clinit>", "()V", null, null);
         var1.visitCode();
         MixinProxyPatcher.emitCallbackHandleSetup(var1);
         var1.visitInsn(177);
         var1.visitMaxs(0, 0);
         var1.visitEnd();
      }

      super.visitEnd();
   }

   private static boolean isTransformClassNodeDescriptor(String var0) {
      Type var1 = Type.getMethodType(var0);
      Type[] var2 = var1.getArgumentTypes();
      Type var3 = Type.getObjectType(classNodeInternalName());
      return var2.length == 4
         && var2[0].getSort() == 10
         && Type.getType(String.class).equals(var2[1])
         && Type.getType(String.class).equals(var2[2])
         && var3.equals(var2[3])
         && var3.equals(var1.getReturnType());
   }

   private static String classNodeInternalName() {
      return "org"
         + "/objectweb/asm/tree/"
         + "ClassNode";
   }

   public static boolean hasStageConstant(MixinProxyPatcher$5 var0) {
      return var0.foundStageConstant;
   }

   public static boolean hasMixinCallbackField(MixinProxyPatcher$5 var0) {
      return var0.foundMixinCallbackField;
   }

   public static boolean didInstrumentRegisterMixins(MixinProxyPatcher$5 var0) {
      return var0.instrumentedRegisterMixins;
   }

   public static boolean didInstrumentTransformClassNode(MixinProxyPatcher$5 var0) {
      return var0.instrumentedTransformClassNode;
   }

   public static int getTransformClassCallCount(MixinProxyPatcher$5 var0) {
      return var0.transformClassCallCount;
   }

   public static int incrementTransformClassCallCount(MixinProxyPatcher$5 var0) {
      return var0.transformClassCallCount++;
   }
}
