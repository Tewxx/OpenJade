// Jade recovery: original class: jade.deps.eLz.InnKfaj$19
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$19 extends MethodVisitor {
   private int localStoreCount;
   private int stepHeightReadCount;

   public AdditionalHooksTransformer$19(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      super.visitVarInsn(24, 1);
      super.visitVarInsn(24, 3);
      super.visitVarInsn(24, 5);
      ClientClassTransformer.emitHookCall(this, "onEntityMove", "(Ljava/lang/Object;DDD)V");
   }

   @Override
   public void visitVarInsn(int var1, int var2) {
      if (var1 == 54 && this.localStoreCount++ == 0) {
         super.visitVarInsn(54, 999);
         super.visitVarInsn(25, 0);
         super.visitVarInsn(21, 999);
         ClientClassTransformer.emitHookCall(this, "onSafeWalk", "(Ljava/lang/Object;Z)Z");
      }

      super.visitVarInsn(var1, var2);
   }

   @Override
   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      super.visitFieldInsn(var1, var2, var3, var4);
      if (var1 == 180
         && "net/minecraft/entity/Entity".equals(var2)
         && "F".equals(var4)
         && (
            "stepHeight".equals(var3)
               || "field_70138_W".equals(var3)
         )
         && this.stepHeightReadCount++ == 0) {
         super.visitVarInsn(56, 999);
         super.visitVarInsn(25, 0);
         super.visitVarInsn(23, 999);
         ClientClassTransformer.emitHookCall(this, "onStepHeight", "(Ljava/lang/Object;F)F");
      }
   }
}
