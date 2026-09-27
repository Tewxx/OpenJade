// Jade recovery: original class: jade.deps.eLz.BHs3TBy$31
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$31 extends MethodVisitor {
   private final boolean badlionRenderPass;
   private boolean hookEmitted;
   private int clearCallCount;

   public ClientClassTransformer$31(MethodVisitor var1, boolean var2) {
      super(589824, var1);
      this.badlionRenderPass = var2;
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      boolean var6 = var1 == 184
         && "net/minecraft/client/renderer/GlStateManager".equals(var2)
         && (
            "clear".equals(var3)
               || "func_179086_m".equals(var3)
         )
         && "(I)V".equals(var4);
      if (var6 && this.clearCallCount++ == 1 && !this.hookEmitted) {
         this.hookEmitted = true;
         super.visitVarInsn(54, 995);
         super.visitVarInsn(23, 2);
         if (this.badlionRenderPass) {
            super.visitInsn(4);
            ClientClassTransformer.emitHookCall(this, "onRenderWorldLast", "(FZ)V");
         } else {
            ClientClassTransformer.emitHookCall(this, "onRenderWorldLast", "(F)V");
         }

         super.visitVarInsn(21, 995);
      }

      super.visitMethodInsn(var1, var2, var3, var4, var5);
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177 && !this.hookEmitted) {
         this.hookEmitted = true;
         super.visitVarInsn(23, 2);
         if (this.badlionRenderPass) {
            super.visitInsn(4);
            ClientClassTransformer.emitHookCall(this, "onRenderWorldLast", "(FZ)V");
         } else {
            ClientClassTransformer.emitHookCall(this, "onRenderWorldLast", "(F)V");
         }
      }

      super.visitInsn(var1);
   }
}
