// Jade recovery: original class: jade.deps.eLz.InnKfaj$42
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$42 extends MethodVisitor {
   public AdditionalHooksTransformer$42(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(25, 1);
      ClientClassTransformer.emitHookCall(
         this, "onSoundReloadHead", "(Ljava/lang/Object;Ljava/lang/Object;)V"
      );
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 182
         && "net/minecraft/client/audio/SoundManager".equals(var2)
         && (
            "reloadSoundSystem".equals(var3)
               || "func_148596_a".equals(var3)
         )
         && "()V".equals(var4)) {
         ClientClassTransformer.emitHookCall(this, "onSoundManagerReload", "(Ljava/lang/Object;)V");
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
