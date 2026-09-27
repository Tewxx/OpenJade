// Jade recovery: original class: jade.deps.eLz.InnKfaj$53
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class AdditionalHooksTransformer$53 extends MethodVisitor {
   public AdditionalHooksTransformer$53(MethodVisitor var1) {
      super(589824, var1);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(23, 1);
      super.visitVarInsn(23, 2);
      ClientClassTransformer.emitHookCall(this, "onVectorForRotation", "(FF)Ljava/lang/Object;");
      super.visitTypeInsn(192, "net/minecraft/util/Vec3");
      Label var1 = new Label();
      super.visitInsn(89);
      super.visitJumpInsn(198, var1);
      super.visitInsn(176);
      super.visitLabel(var1);
      super.visitInsn(87);
   }
}
