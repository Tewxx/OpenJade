// Jade recovery: original class: jade.deps.eLz.InnKfaj$27
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$27 extends MethodVisitor {
   private final boolean badlionRenderPass;

   public AdditionalHooksTransformer$27(MethodVisitor var1, boolean var2) {
      super(589824, var1);
      this.badlionRenderPass = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(this, "onItemRenderPre", "(Ljava/lang/Object;)V");
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(this, "onItemRenderPost", "(Ljava/lang/Object;)V");
      }

      super.visitInsn(var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.badlionRenderPass
         && var1 == 182
         && "net/minecraft/client/entity/AbstractClientPlayer".equals(var2)
         && (
            "getItemInUseCount".equals(var3)
               || "func_71052_bv".equals(var3)
         )
         && "()I".equals(var4)) {
         ClientClassTransformer.emitHookCall(
            this, "onBadlionFirstPersonUseCount", "(Ljava/lang/Object;)I"
         );
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
