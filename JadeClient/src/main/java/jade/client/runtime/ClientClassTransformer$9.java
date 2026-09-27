// Jade recovery: original class: jade.deps.eLz.BHs3TBy$9
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$9 extends MethodVisitor {
   public ClientClassTransformer$9(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      ClientClassTransformer.emitHookCall(this, "onCancelFreecamBlockInteraction", "()Z");
      Label var1 = new Label();
      super.visitJumpInsn(153, var1);
      super.visitInsn(3);
      super.visitInsn(172);
      super.visitLabel(var1);
   }

   @Override
   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      super.visitFieldInsn(var1, var2, var3, var4);
      if (var1 == 181
         && "net/minecraft/client/multiplayer/PlayerControllerMP".equals(var2)
         && "I".equals(var4)
         && (
            "blockHitDelay".equals(var3)
               || "field_78781_i".equals(var3)
         )) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(this, "onFastMineBlockHitDelay", "(Ljava/lang/Object;)V");
      }
   }
}
