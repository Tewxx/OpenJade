// Jade recovery: original class: jade.deps.eLz.BHs3TBy$15
package jade.client.runtime;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$15 extends ClassVisitor {
   public ClientClassTransformer$15(ClassVisitor var1) {
      super(589824, var1);
   }

   @Override
   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodVisitor var6 = super.visitMethod(var1, var2, var3, var4, var5);
      if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "onUpdate",
         "func_70071_h_",
         "()V"
      )) {
         return new ClientClassTransformer$39(
            var6,
            "onPlayerUpdateHead",
            "onPlayerUpdateReturn"
         );
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "closeScreen",
         "func_71053_j",
         "()V"
      )) {
         return new ClientClassTransformer$37(var6, "onBeforeCloseScreen");
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "onUpdateWalkingPlayer",
         "func_175161_p",
         "()V"
      )) {
         return new ClientClassTransformer$40(var6, "onUpdateWalkingPlayer");
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "onLivingUpdate",
         "func_70636_d",
         "()V"
      )) {
         return new ClientClassTransformer$29(var6);
      } else {
         return (MethodVisitor)(ClientClassTransformer.OSIs(
               var2,
               var3,
               "updateEntityActionState",
               "func_70626_be",
               "()V"
            )
            ? new ClientClassTransformer$18(var6)
            : var6);
      }
   }
}
