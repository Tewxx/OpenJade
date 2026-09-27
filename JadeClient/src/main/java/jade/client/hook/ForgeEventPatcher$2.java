// Jade recovery: original class: jade.deps.eLz.zWWk9hiXdH$2
package jade.client.hook;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.MethodVisitor;

public class ForgeEventPatcher$2 extends ClassVisitor {
   private final String xjP;
   private String pxIb;

   public ForgeEventPatcher$2(ClassVisitor var1, String var2) {
      super(327680, var1);
      this.xjP = var2;
   }

   @Override
   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.pxIb = var3;
      super.visit(var1, var2, var3, var4, var5, var6);
   }

   @Override
   public void visitEnd() {
      MethodVisitor var1 = super.visitMethod(4097, "<init>", "()V", null, null);
      var1.visitCode();
      var1.visitVarInsn(25, 0);
      var1.visitMethodInsn(183, this.xjP, "<init>", "()V", false);
      var1.visitInsn(177);
      var1.visitMaxs(1, 1);
      var1.visitEnd();
      super.visitEnd();
   }
}
