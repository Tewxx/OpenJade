// Jade recovery: original class: jade.deps.eLz.InnKfaj$11
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$11 extends MethodVisitor {
   private final boolean nHv;

   public AdditionalHooksTransformer$11(MethodVisitor var1, boolean var2) {
      super(589824, var1);
      this.nHv = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(this, "onChatRenderBegin", "(Ljava/lang/Object;)V");
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(this, "onChatRenderEnd", "(Ljava/lang/Object;)V");
      }

      super.visitInsn(var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      boolean var6 = this.nHv
         && var1 != 184
         && "net/badlion/clientcommon/util/bk".equals(var2)
         && "a".equals(var3)
         && "(Lnet/badlion/clientcommon/util/bk$b;Lorg/apache/commons/lang3/mutable/MutableInt;Lnet/badlion/a/Xm;IIIZ)V".equals(var4);
      if (var6) {
         super.visitVarInsn(54, 999);
         super.visitVarInsn(54, 998);
         super.visitVarInsn(54, 997);
         super.visitVarInsn(54, 996);
         super.visitVarInsn(58, 995);
         super.visitVarInsn(58, 994);
         super.visitVarInsn(58, 993);
         super.visitVarInsn(58, 992);
         super.visitVarInsn(25, 992);
         super.visitVarInsn(25, 993);
         super.visitVarInsn(25, 994);
         super.visitVarInsn(25, 995);
         super.visitVarInsn(21, 996);
         super.visitVarInsn(21, 997);
         super.visitVarInsn(21, 998);
         super.visitVarInsn(21, 999);
         ClientClassTransformer.emitHookCall(
            this,
            "onBadlionCachedChatDraw",
            "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;IIIZ)Z"
         );
         Label var12 = new Label();
         Label var13 = new Label();
         super.visitJumpInsn(153, var12);
         super.visitJumpInsn(167, var13);
         super.visitLabel(var12);
         super.visitVarInsn(25, 992);
         super.visitVarInsn(25, 993);
         super.visitVarInsn(25, 994);
         super.visitVarInsn(25, 995);
         super.visitVarInsn(21, 996);
         super.visitVarInsn(21, 997);
         super.visitVarInsn(21, 998);
         super.visitVarInsn(21, 999);
         super.visitMethodInsn(var1, var2, var3, var4, var5);
         super.visitLabel(var13);
      } else {
         boolean var7 = var1 == 182
            && "net/minecraft/client/gui/FontRenderer".equals(var2)
            && (
               "drawStringWithShadow".equals(var3)
                  || "func_175063_a".equals(var3)
            )
            && "(Ljava/lang/String;FFI)I".equals(var4);
         boolean var8 = this.nHv && var1 != 184 && "(Ljava/lang/String;FFI)I".equals(var4);
         boolean var9 = this.nHv && var1 == 184 && "(Ljava/lang/String;FFI)I".equals(var4);
         if (var9) {
            super.visitVarInsn(54, 999);
            super.visitVarInsn(56, 998);
            super.visitVarInsn(56, 997);
            super.visitVarInsn(58, 996);
            super.visitInsn(1);
            super.visitVarInsn(25, 996);
            super.visitVarInsn(23, 997);
            super.visitVarInsn(23, 998);
            super.visitVarInsn(21, 999);
            ClientClassTransformer.emitHookCall(
               this,
               "onChatDrawString",
               "(Ljava/lang/Object;Ljava/lang/Object;FFI)Ljava/lang/Object;"
            );
            Label var14 = new Label();
            Label var15 = new Label();
            super.visitInsn(89);
            super.visitJumpInsn(198, var14);
            super.visitTypeInsn(192, "java/lang/Number");
            super.visitMethodInsn(182, "java/lang/Number", "intValue", "()I", false);
            super.visitJumpInsn(167, var15);
            super.visitLabel(var14);
            super.visitInsn(87);
            super.visitVarInsn(25, 996);
            super.visitVarInsn(23, 997);
            super.visitVarInsn(23, 998);
            super.visitVarInsn(21, 999);
            super.visitMethodInsn(var1, var2, var3, var4, var5);
            super.visitLabel(var15);
         } else if (!var7 && !var8) {
            super.visitMethodInsn(var1, var2, var3, var4, var5);
         } else {
            super.visitVarInsn(54, 999);
            super.visitVarInsn(56, 998);
            super.visitVarInsn(56, 997);
            super.visitVarInsn(58, 996);
            super.visitVarInsn(58, 995);
            super.visitVarInsn(25, 995);
            super.visitVarInsn(25, 996);
            super.visitVarInsn(23, 997);
            super.visitVarInsn(23, 998);
            super.visitVarInsn(21, 999);
            ClientClassTransformer.emitHookCall(
               this,
               "onChatDrawString",
               "(Ljava/lang/Object;Ljava/lang/Object;FFI)Ljava/lang/Object;"
            );
            Label var10 = new Label();
            Label var11 = new Label();
            super.visitInsn(89);
            super.visitJumpInsn(198, var10);
            super.visitTypeInsn(192, "java/lang/Number");
            super.visitMethodInsn(182, "java/lang/Number", "intValue", "()I", false);
            super.visitJumpInsn(167, var11);
            super.visitLabel(var10);
            super.visitInsn(87);
            super.visitVarInsn(25, 995);
            super.visitVarInsn(25, 996);
            super.visitVarInsn(23, 997);
            super.visitVarInsn(23, 998);
            super.visitVarInsn(21, 999);
            super.visitMethodInsn(var1, var2, var3, var4, var5);
            super.visitLabel(var11);
         }
      }
   }
}
