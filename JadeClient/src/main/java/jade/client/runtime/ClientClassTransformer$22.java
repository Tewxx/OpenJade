// Jade recovery: original class: jade.deps.eLz.BHs3TBy$22
package jade.client.runtime;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$22 extends ClassVisitor {
   public ClientClassTransformer$22(ClassVisitor var1) {
      super(589824, var1);
   }

   @Override
   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodVisitor var6 = super.visitMethod(var1, var2, var3, var4, var5);
      return (MethodVisitor)(ClientClassTransformer.OSIs(
            var2,
            var3,
            "updatePlayerMoveState",
            "func_78898_a",
            "()V"
         )
         ? new ClientClassTransformer$43(var6, "onUpdatePlayerMoveState")
         : var6);
   }
}
