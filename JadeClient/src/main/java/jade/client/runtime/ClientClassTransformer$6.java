// Jade recovery: original class: jade.deps.eLz.BHs3TBy$6
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$6 extends MethodVisitor {
   private final int packetVarIndex;
   private final String cancelHookName;

   public ClientClassTransformer$6(MethodVisitor var1, int var2, String var3) {
      super(589824, var1);
      this.packetVarIndex = var2;
      this.cancelHookName = var3;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, this.packetVarIndex);
      ClientClassTransformer.emitHookCall(this, this.cancelHookName, "(Ljava/lang/Object;)Z");
      ClientClassTransformer.emitReturnIfFalse(this);
   }
}
