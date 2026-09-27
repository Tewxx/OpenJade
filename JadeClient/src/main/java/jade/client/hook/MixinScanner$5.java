// Jade recovery: original class: jade.deps.eLz.D2XKMbi$5
package jade.client.hook;

import jade.deps.asm.AnnotationVisitor;

public class MixinScanner$5 extends AnnotationVisitor {
   private boolean sawRemap = false;

   public MixinScanner$5(AnnotationVisitor var1) {
      super(327680, var1);
   }

   @Override
   public void visit(String var1, Object var2) {
      if ("remap".equals(var1)) {
         this.sawRemap = true;
         super.visit(var1, false);
      } else {
         super.visit(var1, var2);
      }
   }

   @Override
   public void visitEnd() {
      if (!this.sawRemap) {
         super.visit("remap", false);
      }

      super.visitEnd();
   }
}
