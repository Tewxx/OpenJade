// Jade recovery: original class: jade.deps.eLz.BHs3TBy$21
package jade.client.runtime;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$21 extends ClassVisitor {
   private final boolean badlionRenderPass;

   public ClientClassTransformer$21(ClassVisitor var1, boolean var2) {
      super(589824, var1);
      this.badlionRenderPass = var2;
   }

   @Override
   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodVisitor var6 = super.visitMethod(var1, var2, var3, var4, var5);
      if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "startGame",
         "func_71384_a",
         "()V"
      )) {
         return new ClientClassTransformer$38(var6, "onStartGame", "()V", false);
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "runTick",
         "func_71407_l",
         "()V"
      )) {
         return new ClientClassTransformer$33(var6, this.badlionRenderPass);
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "runGameLoop",
         "func_71411_J",
         "()V"
      )) {
         return new ClientClassTransformer$42(var6, "onRunGameLoopStart");
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "clickMouse",
         "func_147116_af",
         "()V"
      )) {
         return new ClientClassTransformer$41(var6, "onClickMouse");
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "rightClickMouse",
         "func_147121_ag",
         "()V"
      )) {
         return new ClientClassTransformer$36(var6, "onRightClickMouse");
      } else if (ClientClassTransformer.OSIs(
         var2,
         var3,
         "loadWorld",
         "func_71353_a",
         "(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V"
      )) {
         return var6;
      } else {
         return (MethodVisitor)(ClientClassTransformer.OSIs(
               var2,
               var3,
               "displayGuiScreen",
               "func_147108_a",
               "(Lnet/minecraft/client/gui/GuiScreen;)V"
            )
            ? new ClientClassTransformer$14(var6)
            : var6);
      }
   }
}
