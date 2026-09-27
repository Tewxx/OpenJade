// Jade recovery: original class: jade.deps.eLz.BHs3TBy$23
package jade.client.runtime;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$23 extends ClassVisitor {
   private final boolean badlionRenderPass;

   public ClientClassTransformer$23(ClassVisitor var1, boolean var2) {
      super(589824, var1);
      this.badlionRenderPass = var2;
   }

   @Override
   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      Object var6 = super.visitMethod(var1, var2, var3, var4, var5);
      if (this.badlionRenderPass) {
         var6 = new ClientClassTransformer$44((MethodVisitor)var6);
      }

      if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "handleChat",
         "func_147251_a",
         "(Lnet/minecraft/network/play/server/S02PacketChat;)V"
      )) {
         return new ClientClassTransformer$6((MethodVisitor)var6, 1, "onHandleChat");
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "handleDisconnect",
         "func_147253_a",
         "(Lnet/minecraft/network/play/server/S40PacketDisconnect;)V"
      )) {
         return new ClientClassTransformer$37((MethodVisitor)var6, "onHandleDisconnect");
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "handleEntityVelocity",
         "func_147244_a",
         "(Lnet/minecraft/network/play/server/S12PacketEntityVelocity;)V"
      )) {
         return new ClientClassTransformer$6((MethodVisitor)var6, 1, "onHandleEntityVelocity");
      } else {
         return (MethodVisitor)(ClientClassTransformer.OSIs(
               var2,
               var3,
               "handleExplosion",
               "func_147283_a",
               "(Lnet/minecraft/network/play/server/S27PacketExplosion;)V"
            )
            ? new ClientClassTransformer$6((MethodVisitor)var6, 1, "onHandleExplosion")
            : var6);
      }
   }
}
