// Jade recovery: original class: jade.deps.eLz.InnKfaj$46
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$46 extends MethodVisitor {
   public AdditionalHooksTransformer$46(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 182
         && "net/minecraft/client/renderer/texture/TextureManager".equals(var2)
         && (
            "loadTexture".equals(var3)
               || "func_110579_a".equals(var3)
         )
         && "(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/renderer/texture/ITextureObject;)Z".equals(var4)) {
         ClientClassTransformer.emitHookCall(
            this,
            "onTextureReload",
            "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z"
         );
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
