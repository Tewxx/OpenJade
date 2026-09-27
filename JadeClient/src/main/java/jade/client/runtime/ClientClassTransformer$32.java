// Jade recovery: original class: jade.deps.eLz.BHs3TBy$32
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$32 extends MethodVisitor {
   public ClientClassTransformer$32(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      super.visitMethodInsn(var1, var2, var3, var4, var5);
      if (var1 == 182
         && "net/minecraft/client/renderer/EntityRenderer".equals(var2)
         && (
            "getMouseOver".equals(var3)
               || "func_78473_a".equals(var3)
         )
         && "(F)V".equals(var4)) {
         ClientClassTransformer.emitHookCall(this, "onRenderWorldMouseOver", "()V");
      }
   }
}
