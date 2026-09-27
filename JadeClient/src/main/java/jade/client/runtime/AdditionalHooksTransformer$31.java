// Jade recovery: original class: jade.deps.eLz.InnKfaj$31
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$31 extends MethodVisitor {
   private final boolean badlionJumpFix;

   public AdditionalHooksTransformer$31(MethodVisitor var1, boolean var2) {
      super(589824, var1);
      this.badlionJumpFix = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      if (this.badlionJumpFix) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(
            this, "onShouldRemoveBadlionJumpDelay", "(Ljava/lang/Object;)Z"
         );
         Label var1 = new Label();
         super.visitJumpInsn(153, var1);
         super.visitVarInsn(25, 0);
         super.visitInsn(3);
         super.visitFieldInsn(
            181,
            "net/minecraft/entity/EntityLivingBase",
            "jumpTicks",
            "I"
         );
         super.visitLabel(var1);
      }
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 182
         && "net/minecraft/entity/EntityLivingBase".equals(var2)
         && (
            "moveEntityWithHeading".equals(var3)
               || "func_70612_e".equals(var3)
         )
         && "(FF)V".equals(var4)) {
         ClientClassTransformer.emitHookCall(this, "onMoveEntityWithHeading", "(Ljava/lang/Object;FF)V");
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
