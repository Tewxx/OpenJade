// Jade recovery: original class: jade.deps.eLz.BHs3TBy$42
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$42 extends MethodVisitor {
   private final String entryHookName;

   public ClientClassTransformer$42(MethodVisitor var1, String var2) {
      super(589824, var1);
      this.entryHookName = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      super.visitVarInsn(25, 0);
      ClientClassTransformer.emitHookCall(this, this.entryHookName, "(Ljava/lang/Object;)V");
   }
}
