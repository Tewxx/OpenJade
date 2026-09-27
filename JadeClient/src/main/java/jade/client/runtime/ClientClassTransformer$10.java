// Jade recovery: original class: jade.deps.eLz.BHs3TBy$10
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$10 extends MethodVisitor {
   public ClientClassTransformer$10(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      if (var1 != 180
         || !"net/minecraft/client/Minecraft".equals(var2)
         || !"Z".equals(var4)
         || !"inGameHasFocus".equals(var3)
            && !"field_71415_G".equals(var3)) {
         super.visitFieldInsn(var1, var2, var3, var4);
      } else {
         super.visitInsn(89);
         super.visitFieldInsn(var1, var2, var3, var4);
         ClientClassTransformer.emitHookCall(this, "onFreelookMouseFocus", "(Ljava/lang/Object;Z)Z");
      }
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 182
         && ("net/minecraft/client/entity/EntityPlayerSP".equals(var2) || "net/minecraft/entity/Entity".equals(var2))
         && (
            "setAngles".equals(var3)
               || "func_70082_c".equals(var3)
         )
         && "(FF)V".equals(var4)) {
         ClientClassTransformer.emitHookCall(this, "onFreecamSetAngles", "(Ljava/lang/Object;FF)V");
      } else if ((var1 == 182 || var1 == 183)
         && "net/minecraft/client/renderer/EntityRenderer".equals(var2)
         && "(Lnet/minecraft/client/Minecraft;)Z".equals(var4)) {
         super.visitVarInsn(58, 992);
         super.visitVarInsn(58, 991);
         super.visitVarInsn(25, 991);
         super.visitVarInsn(25, 992);
         super.visitMethodInsn(var1, var2, var3, var4, var5);
         super.visitVarInsn(25, 992);
         super.visitInsn(95);
         ClientClassTransformer.emitHookCall(this, "onFreelookMouseFocus", "(Ljava/lang/Object;Z)Z");
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
