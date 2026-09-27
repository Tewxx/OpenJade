// Jade recovery: original class: jade.deps.eLz.D2XKMbi
package jade.client.hook;

import jade.deps.asm.AnnotationVisitor;
import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassVisitor;
import jade.deps.asm.ClassWriter;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MixinScanner implements ClassFileTransformer {
   private static final String BHxzR = "Lorg/spongepowered/asm/mixin/Mixin;";
   private static final String JMVjiy = "Lorg/spongepowered/asm/mixin/Shadow;";
   private static final String ACCESSOR_DESC = "Lorg/spongepowered/asm/mixin/gen/Accessor;";
   private static final String Iy5 = "Lorg/spongepowered/asm/mixin/gen/Invoker;";
   private static final String INJECT_DESC = "Lorg/spongepowered/asm/mixin/injection/Inject;";
   private static final String REDIRECT_DESC = "Lorg/spongepowered/asm/mixin/injection/Redirect;";
   private static final String MODIFY_ARG_DESC = "Lorg/spongepowered/asm/mixin/injection/ModifyArg;";
   private static final String MODIFY_ARGS_DESC = "Lorg/spongepowered/asm/mixin/injection/ModifyArgs;";
   private static final String IHo = "Lorg/spongepowered/asm/mixin/injection/ModifyConstant;";
   private static final String VZYd = "Lorg/spongepowered/asm/mixin/injection/ModifyVariable;";
   private static final String OVERWRITE_DESC = "Lorg/spongepowered/asm/mixin/Overwrite;";
   private static final Set<String> SUPPORTED_ANNOTATION_DESCS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(MixinAnnotations.getMixinAnnotationDescriptors())));
   private static final int spUi = 100;
   private final Set<String> YOt;

   public MixinScanner(Set<String> var1) {
      this.YOt = var1;
   }

   @Override
   public byte[] transform(ClassLoader var1, String var2, Class<?> var3, ProtectionDomain var4, byte[] var5) {
      if (var2 != null && this.YOt.contains(var2)) {
         ClassReader var6 = new ClassReader(var5);
         if (!this.hasMixinAnnotation(var6)) {
            return null;
         } else {
            ClassWriter var7 = new ClassWriter(var6, 0);
            var6.accept(new MixinScanner$4(var7), 0);
            return var7.toByteArray();
         }
      } else {
         return null;
      }
   }

   private boolean hasMixinAnnotation(ClassReader var1) {
      final boolean[] var2 = new boolean[]{false};
      var1.accept(new ClassVisitor(327680) {
         @Override
         public AnnotationVisitor visitAnnotation(String var1, boolean var2x) {
            if ("Lorg/spongepowered/asm/mixin/Mixin;".equals(var1)) {
               var2[0] = true;
            }

            return null;
         }
      }, 5);
      return var2[0];
   }

   public static Set getSupportedMixinAnnotationDescs() {
      return SUPPORTED_ANNOTATION_DESCS;
   }
}
