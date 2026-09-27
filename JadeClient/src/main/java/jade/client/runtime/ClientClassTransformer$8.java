// Jade recovery: original class: jade.deps.eLz.BHs3TBy$8
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$8 extends MethodVisitor {
   public ClientClassTransformer$8(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 182
         && "io/netty/bootstrap/Bootstrap".equals(var2)
         && "handler".equals(var3)
         && "(Lio/netty/channel/ChannelHandler;)Lio/netty/bootstrap/AbstractBootstrap;".equals(var4)) {
         Label var6 = new Label();
         Label var7 = new Label();
         short var8 = 1016;
         short var9 = 1017;
         super.visitVarInsn(58, var8);
         super.visitVarInsn(58, var9);
         super.visitVarInsn(25, var8);
         ClientClassTransformer.emitHookCall(
            this,
            "onNetworkBootstrapHandler",
            "(Ljava/lang/Object;)Ljava/lang/Object;"
         );
         super.visitInsn(89);
         super.visitJumpInsn(199, var6);
         super.visitInsn(87);
         super.visitVarInsn(25, var8);
         super.visitJumpInsn(167, var7);
         super.visitLabel(var6);
         super.visitLabel(var7);
         super.visitTypeInsn(192, "io/netty/channel/ChannelHandler");
         super.visitVarInsn(58, var8);
         super.visitVarInsn(25, var9);
         super.visitVarInsn(25, var8);
      }

      super.visitMethodInsn(var1, var2, var3, var4, var5);
   }
}
