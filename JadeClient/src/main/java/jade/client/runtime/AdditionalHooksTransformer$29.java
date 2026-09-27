// Jade recovery: original class: jade.deps.eLz.InnKfaj$29
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$29 extends MethodVisitor {
   public AdditionalHooksTransformer$29(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      super.visitMethodInsn(var1, var2, var3, var4, var5);
      if (var1 == 182
         && "net/minecraft/entity/EntityLivingBase".equals(var2)
         && (
            "isInvisible".equals(var3)
               || "func_82150_aj".equals(var3)
         )
         && "()Z".equals(var4)) {
         super.visitVarInsn(54, 999);
         super.visitVarInsn(25, 1);
         super.visitVarInsn(21, 999);
         ClientClassTransformer.emitHookCall(this, "onLivingInvisible", "(Ljava/lang/Object;Z)Z");
      }
   }
}
