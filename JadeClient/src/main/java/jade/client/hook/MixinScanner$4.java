// Jade recovery: original class: jade.deps.eLz.D2XKMbi$4
package jade.client.hook;

import jade.deps.asm.AnnotationVisitor;
import jade.deps.asm.ClassVisitor;
import jade.deps.asm.FieldVisitor;
import jade.deps.asm.MethodVisitor;

public class MixinScanner$4 extends ClassVisitor {
   public MixinScanner$4(ClassVisitor var1) {
      super(327680, var1);
   }

   @Override
   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationVisitor var3 = super.visitAnnotation(var1, var2);
      return (AnnotationVisitor)("Lorg/spongepowered/asm/mixin/Mixin;".equals(var1) ? new MixinScanner$1(var3) : var3);
   }

   @Override
   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      return new FieldVisitor(327680, super.visitField(var1, var2, var3, var4, var5)) {
         @Override
         public AnnotationVisitor visitAnnotation(String var1, boolean var2x) {
            AnnotationVisitor var3x = super.visitAnnotation(var1, var2x);
            return (AnnotationVisitor)(MixinScanner.getSupportedMixinAnnotationDescs().contains(var1) ? new MixinScanner$5(var3x) : var3x);
         }
      };
   }

   @Override
   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      return new MethodVisitor(327680, super.visitMethod(var1, var2, var3, var4, var5)) {
         @Override
         public AnnotationVisitor visitAnnotation(String var1, boolean var2x) {
            AnnotationVisitor var3x = super.visitAnnotation(var1, var2x);
            return (AnnotationVisitor)(MixinScanner.getSupportedMixinAnnotationDescs().contains(var1) ? new MixinScanner$5(var3x) : var3x);
         }
      };
   }
}
