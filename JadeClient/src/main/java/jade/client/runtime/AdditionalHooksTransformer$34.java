// Jade recovery: original class: jade.deps.eLz.InnKfaj$34
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$34 extends MethodVisitor {
   private Label vanillaSlowdownLabel;
   private Label keepSprintLabel;

   public AdditionalHooksTransformer$34(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(
         this, "onPlayerAttack", "(Ljava/lang/Object;Ljava/lang/Object;)Z"
      );
      Label var1 = new Label();
      super.visitJumpInsn(153, var1);
      super.visitInsn(177);
      super.visitLabel(var1);
      super.visitFrame(3, 0, null, 0, null);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      super.visitMethodInsn(var1, var2, var3, var4, var5);
      if (this.vanillaSlowdownLabel == null
         && var1 == 182
         && (
            "addVelocity".equals(var3)
               || "func_70024_g".equals(var3)
         )
         && "(DDD)V".equals(var4)) {
         this.vanillaSlowdownLabel = new Label();
         this.keepSprintLabel = new Label();
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(
            this, "onKeepSprintAttackSlowdown", "(Ljava/lang/Object;)Z"
         );
         super.visitJumpInsn(153, this.vanillaSlowdownLabel);
         super.visitJumpInsn(167, this.keepSprintLabel);
         super.visitLabel(this.vanillaSlowdownLabel);
      } else {
         if (this.keepSprintLabel != null
            && var1 == 182
            && (
               "setSprinting".equals(var3)
                  || "func_70031_b".equals(var3)
            )
            && "(Z)V".equals(var4)) {
            super.visitLabel(this.keepSprintLabel);
            this.vanillaSlowdownLabel = null;
            this.keepSprintLabel = null;
         }
      }
   }
}
