// Jade recovery: original class: jade.deps.eLz.D2XKMbi$1
package jade.client.hook;

import jade.deps.asm.AnnotationVisitor;

public class MixinScanner$1 extends AnnotationVisitor {
   private boolean sawRemap = false;
   private boolean sawPriority = false;

   public MixinScanner$1(AnnotationVisitor var1) {
      super(327680, var1);
   }

   @Override
   public void visit(String var1, Object var2) {
      if ("remap".equals(var1)) {
         this.sawRemap = true;
         super.visit(var1, false);
      } else if ("priority".equals(var1)) {
         this.sawPriority = true;
         int var3 = (Integer)var2;
         if (var3 > 100) {
            System.out
               .println(
                  "[Mod-Agent] Clamping mixin priority from " + var3 + " to " + 100
               );
         }

         super.visit(var1, Math.min(var3, 100));
      } else {
         super.visit(var1, var2);
      }
   }

   @Override
   public void visitEnd() {
      if (!this.sawRemap) {
         super.visit("remap", false);
      }

      if (!this.sawPriority) {
         super.visit("priority", 100);
      }

      super.visitEnd();
   }
}
