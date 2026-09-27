// Jade recovery: original class: jade.deps.eLz.InnKfaj$35
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$35 extends MethodVisitor {
   public AdditionalHooksTransformer$35(MethodVisitor var1) {
      super(589824, var1);
   }

   private void emitRenderPlayerHook(String var1) {
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      super.visitVarInsn(24, 2);
      super.visitVarInsn(24, 4);
      super.visitVarInsn(24, 6);
      super.visitVarInsn(23, 9);
      ClientClassTransformer.emitHookCall(this, var1, "(Ljava/lang/Object;Ljava/lang/Object;DDDF)V");
   }

   @Override
   public void visitCode() {
      super.visitCode();
      this.emitRenderPlayerHook("onRenderPlayerPre");
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         this.emitRenderPlayerHook("onRenderPlayerPost");
      }

      super.visitInsn(var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 182
         && "net/minecraft/client/entity/AbstractClientPlayer".equals(var2)
         && (
            "isUser".equals(var3)
               || "func_175144_cb".equals(var3)
         )
         && "()Z".equals(var4)) {
         ClientClassTransformer.emitHookCall(this, "onRenderPlayerIsUser", "(Ljava/lang/Object;)Z");
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
