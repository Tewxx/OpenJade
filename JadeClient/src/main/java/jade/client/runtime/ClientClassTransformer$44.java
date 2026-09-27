// Jade recovery: original class: jade.deps.eLz.BHs3TBy$44
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$44 extends MethodVisitor {
   public ClientClassTransformer$44(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 182
         && "net/minecraft/client/multiplayer/WorldClient".equals(var2)
         && (
            "addEntityToWorld".equals(var3)
               || "func_73027_a".equals(var3)
         )
         && "(ILnet/minecraft/entity/Entity;)V".equals(var4)) {
         super.visitVarInsn(58, 1012);
         super.visitVarInsn(54, 1011);
         super.visitVarInsn(58, 1010);
         super.visitVarInsn(25, 1010);
         super.visitVarInsn(21, 1011);
         super.visitVarInsn(25, 1012);
         super.visitMethodInsn(var1, var2, var3, var4, var5);
         super.visitVarInsn(25, 1010);
         super.visitVarInsn(25, 1012);
         ClientClassTransformer.emitHookCall(
            this, "onEntityJoinWorld", "(Ljava/lang/Object;Ljava/lang/Object;)V"
         );
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
