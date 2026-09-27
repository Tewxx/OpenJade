// Jade recovery: original class: jade.deps.eLz.InnKfaj$5
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$5 extends MethodVisitor {
   public AdditionalHooksTransformer$5(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 184
         && "net/minecraft/client/renderer/GlStateManager".equals(var2)
         && "color".equals(var3)
         && "(FFFF)V".equals(var4)) {
         super.visitVarInsn(56, 999);
         super.visitVarInsn(56, 998);
         super.visitVarInsn(56, 997);
         super.visitVarInsn(56, 996);
         super.visitVarInsn(23, 999);
         ClientClassTransformer.emitHookCall(this, "onArmorAlpha", "(F)Ljava/lang/Object;");
         Label var6 = new Label();
         Label var7 = new Label();
         super.visitInsn(89);
         super.visitJumpInsn(198, var6);
         super.visitTypeInsn(192, "java/lang/Number");
         super.visitMethodInsn(182, "java/lang/Number", "floatValue", "()F", false);
         super.visitJumpInsn(167, var7);
         super.visitLabel(var6);
         super.visitInsn(87);
         super.visitVarInsn(23, 999);
         super.visitLabel(var7);
         super.visitVarInsn(56, 995);
         super.visitVarInsn(23, 996);
         super.visitVarInsn(23, 997);
         super.visitVarInsn(23, 998);
         super.visitVarInsn(23, 995);
      }

      super.visitMethodInsn(var1, var2, var3, var4, var5);
   }
}
