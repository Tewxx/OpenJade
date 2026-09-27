// Jade recovery: original class: jade.deps.eLz.InnKfaj$16
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$16 extends MethodVisitor {
   public AdditionalHooksTransformer$16(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(
         this, "onContainerDrawSlotBegin", "(Ljava/lang/Object;Ljava/lang/Object;)V"
      );
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 == 177) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(this, "onContainerDrawSlotEnd", "(Ljava/lang/Object;)V");
      }

      super.visitInsn(var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 != 182
         || !"net/minecraft/client/renderer/entity/RenderItem".equals(var2)
         || !"renderItemAndEffectIntoGUI".equals(var3)
            && !"func_180450_b".equals(var3)) {
         if (var1 == 182
            && "net/minecraft/client/renderer/entity/RenderItem".equals(var2)
            && (
               "renderItemOverlayIntoGUI".equals(var3)
                  || "func_180453_a".equals(var3)
            )
            && "(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V".equals(var4)) {
            super.visitVarInsn(58, 999);
            super.visitVarInsn(54, 998);
            super.visitVarInsn(54, 997);
            super.visitVarInsn(58, 996);
            super.visitVarInsn(58, 995);
            super.visitVarInsn(58, 994);
            super.visitVarInsn(25, 0);
            super.visitVarInsn(25, 996);
            ClientClassTransformer.emitHookCall(
               this,
               "onContainerDisplayedStack",
               "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
            );
            super.visitVarInsn(58, 996);
            super.visitVarInsn(25, 994);
            super.visitVarInsn(25, 995);
            super.visitVarInsn(25, 996);
            super.visitTypeInsn(192, "net/minecraft/item/ItemStack");
            super.visitVarInsn(21, 997);
            super.visitVarInsn(21, 998);
            super.visitVarInsn(25, 999);
            super.visitMethodInsn(var1, var2, var3, var4, var5);
         } else {
            super.visitMethodInsn(var1, var2, var3, var4, var5);
         }
      } else {
         super.visitVarInsn(54, 999);
         super.visitVarInsn(54, 998);
         super.visitVarInsn(58, 997);
         super.visitVarInsn(58, 996);
         super.visitVarInsn(25, 0);
         super.visitVarInsn(25, 997);
         ClientClassTransformer.emitHookCall(
            this,
            "onContainerDisplayedStack",
            "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
         );
         super.visitVarInsn(58, 997);
         super.visitVarInsn(25, 996);
         super.visitVarInsn(25, 997);
         super.visitTypeInsn(192, "net/minecraft/item/ItemStack");
         super.visitVarInsn(21, 998);
         super.visitVarInsn(21, 999);
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
