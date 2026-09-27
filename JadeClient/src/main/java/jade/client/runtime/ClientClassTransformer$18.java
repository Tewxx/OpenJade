// Jade recovery: original class: jade.deps.eLz.BHs3TBy$18
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$18 extends MethodVisitor {
   public ClientClassTransformer$18(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 182
         && "net/minecraft/client/entity/EntityPlayerSP".equals(var2)
         && (
            "isCurrentViewEntity".equals(var3)
               || "func_175160_A".equals(var3)
         )
         && "()Z".equals(var4)) {
         ClientClassTransformer.emitHookCall(this, "onFreecamCurrentView", "(Ljava/lang/Object;)Z");
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
