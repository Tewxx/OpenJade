// Jade recovery: original class: jade.deps.eLz.BHs3TBy$28
package jade.client.runtime;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$28 extends ClassVisitor {
   public ClientClassTransformer$28(ClassVisitor var1) {
      super(589824, var1);
   }

   @Override
   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodVisitor var6 = super.visitMethod(var1, var2, var3, var4, var5);
      if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "sendUseItem",
         "func_78769_a",
         "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Z"
      )) {
         return new ClientClassTransformer$34(var6);
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "attackEntity",
         "func_78764_a",
         "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)V"
      )) {
         return new ClientClassTransformer$7(var6);
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "onPlayerDamageBlock",
         "func_180512_c",
         "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"
      )) {
         return new ClientClassTransformer$9(new ClientClassTransformer$13(var6));
      } else {
         return (MethodVisitor)(ClientClassTransformer.OSIs(
               var2,
               var3,
               "clickBlock",
               "func_180511_b",
               "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"
            )
            ? new ClientClassTransformer$9(var6)
            : var6);
      }
   }
}
