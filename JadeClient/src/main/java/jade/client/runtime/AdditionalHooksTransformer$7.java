// Jade recovery: original class: jade.deps.eLz.InnKfaj$7
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$7 extends MethodVisitor {
   public AdditionalHooksTransformer$7(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(this, "onSuppressLivingName", "(Ljava/lang/Object;)Z");
      Label var1 = new Label();
      super.visitJumpInsn(153, var1);
      super.visitInsn(3);
      super.visitInsn(172);
      super.visitLabel(var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      super.visitMethodInsn(var1, var2, var3, var4, var5);
      if (var1 == 182
         && "net/minecraft/entity/EntityLivingBase".equals(var2)
         && (
            "isInvisibleToPlayer".equals(var3)
               || "func_98034_c".equals(var3)
         )
         && "(Lnet/minecraft/entity/player/EntityPlayer;)Z".equals(var4)) {
         super.visitVarInsn(54, 999);
         super.visitVarInsn(25, 1);
         super.visitVarInsn(21, 999);
         ClientClassTransformer.emitHookCall(
            this, "onLivingInvisibleToPlayer", "(Ljava/lang/Object;Z)Z"
         );
      }
   }
}
