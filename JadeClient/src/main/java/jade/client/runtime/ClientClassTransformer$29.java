// Jade recovery: original class: jade.deps.eLz.BHs3TBy$29
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$29 extends MethodVisitor {
   public ClientClassTransformer$29(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(
         this, "onPlayerLivingUpdateBeforeSuper", "(Ljava/lang/Object;)Z"
      );
      Label var1 = new Label();
      super.visitJumpInsn(153, var1);
      super.visitVarInsn(25, 0);
      super.visitMethodInsn(
         183,
         "net/minecraft/client/entity/AbstractClientPlayer",
         "onLivingUpdate",
         "()V",
         false
      );
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(
         this, "onPlayerLivingUpdateAfterSuper", "(Ljava/lang/Object;)V"
      );
      super.visitInsn(177);
      super.visitLabel(var1);
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(
            this, "onPlayerLivingUpdateAfterSuper", "(Ljava/lang/Object;)V"
         );
      }

      super.visitInsn(var1);
   }
}
