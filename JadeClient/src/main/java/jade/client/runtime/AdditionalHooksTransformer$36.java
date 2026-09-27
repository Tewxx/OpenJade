// Jade recovery: original class: jade.deps.eLz.InnKfaj$36
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$36 extends MethodVisitor {
   public AdditionalHooksTransformer$36(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 182
         && "net/minecraft/entity/player/InventoryPlayer".equals(var2)
         && (
            "getCurrentItem".equals(var3)
               || "func_70448_g".equals(var3)
         )
         && "()Lnet/minecraft/item/ItemStack;".equals(var4)) {
         ClientClassTransformer.emitHookCall(
            this,
            "onRenderPlayerCurrentItem",
            "(Ljava/lang/Object;)Ljava/lang/Object;"
         );
         super.visitTypeInsn(192, "net/minecraft/item/ItemStack");
      } else if (var1 == 182
         && "net/minecraft/client/entity/AbstractClientPlayer".equals(var2)
         && (
            "getItemInUseCount".equals(var3)
               || "func_71052_bv".equals(var3)
         )
         && "()I".equals(var4)) {
         ClientClassTransformer.emitHookCall(this, "onRenderPlayerUseCount", "(Ljava/lang/Object;)I");
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
