// Jade recovery: original class: jade.deps.eLz.BHs3TBy$24
package jade.client.runtime;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$24 extends ClassVisitor {
   private final boolean HAd;

   public ClientClassTransformer$24(ClassVisitor var1, boolean var2) {
      super(589824, var1);
      this.HAd = var2;
   }

   @Override
   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodVisitor var6 = super.visitMethod(var1, var2, var3, var4, var5);
      if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "sendPacket",
         "func_179290_a",
         "(Lnet/minecraft/network/Packet;)V"
      )) {
         return new ClientClassTransformer$26(var6, "onNetworkSend");
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "dispatchPacket",
         "func_150732_b",
         "(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V"
      )) {
         return new ClientClassTransformer$27(var6, "onNetworkDispatch");
      } else if ("channelRead0".equals(var2)
         && "(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V".equals(var3)) {
         return new ClientClassTransformer$11(var6);
      } else {
         return (MethodVisitor)(this.HAd ? new ClientClassTransformer$8(var6) : var6);
      }
   }
}
