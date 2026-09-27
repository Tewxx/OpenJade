// Jade recovery: original class: jade.deps.eLz.InnKfaj$28
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$28 extends MethodVisitor {
   public AdditionalHooksTransformer$28(MethodVisitor var1) {
      super(589824, var1);
   }

   private void emitRenderLivingHook(String var1) {
      super.visitVarInsn(25, 1);
      super.visitVarInsn(24, 2);
      super.visitVarInsn(24, 4);
      super.visitVarInsn(24, 6);
      super.visitVarInsn(23, 9);
      ClientClassTransformer.emitHookCall(this, var1, "(Ljava/lang/Object;DDDF)V");
   }

   @Override
   public void visitCode() {
      super.visitCode();
      this.emitRenderLivingHook("onRenderLivingPre");
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         this.emitRenderLivingHook("onRenderLivingPost");
      }

      super.visitInsn(var1);
   }
}
