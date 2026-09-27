// Jade recovery: original class: jade.deps.eLz.BHs3TBy$30
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$30 extends MethodVisitor {
   private final String CTHe;

   public ClientClassTransformer$30(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.CTHe = var2;
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 182
         && ("net/minecraft/entity/EntityLivingBase".equals(var2) || "net/minecraft/client/entity/EntityPlayerSP".equals(var2))
         && (
            "isPotionActive".equals(var3)
               || "func_70644_a".equals(var3)
         )
         && "(Lnet/minecraft/potion/Potion;)Z".equals(var4)) {
         ClientClassTransformer.emitHookCall(this, this.CTHe, "(Ljava/lang/Object;Ljava/lang/Object;)Z");
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
