// Jade recovery: original class: jade.deps.eLz.IEdHCC$1
package jade.client.misc;

import jade.deps.asm.MethodVisitor;

public class GenesisBakeHook$1 extends MethodVisitor {
   final GenesisBakeHook$2 recoveredOuter;
   private final String wQ9;
   private final int methodAccessFlags;
   private final String methodDescriptor;
   private int zipFilePatternState;

   public GenesisBakeHook$1(GenesisBakeHook$2 var1, MethodVisitor var2, String var3, int var4, String var5) {
      super(327680, var2);
      this.recoveredOuter = var1;
      this.wQ9 = var3;
      this.methodAccessFlags = var4;
      this.methodDescriptor = var5;
   }

   @Override
   public void visitTypeInsn(int var1, String var2) {
      if (var1 == 187 && "java/util/zip/ZipFile".equals(var2)) {
         if (this.zipFilePatternState != 0) {
            GenesisBakeHook$2.YEzx(this.recoveredOuter, "nested ZipFile construction");
         }

         this.zipFilePatternState = 1;
      } else {
         super.visitTypeInsn(var1, var2);
      }
   }

   @Override
   public void visitInsn(int var1) {
      if (this.zipFilePatternState == 1) {
         if (var1 != 89) {
            GenesisBakeHook$2.YEzx(this.recoveredOuter, "ZipFile constructor lost exact DUP");
         }

         this.zipFilePatternState = 2;
      } else {
         super.visitInsn(var1);
      }
   }

   @Override
   public void visitLdcInsn(Object var1) {
      if ("Genesis/Baker".equals(var1)) {
         GenesisBakeHook$2.incrementBakerMarkerCount(this.recoveredOuter);
      }

      if ("Loading baked classes...".equals(var1)) {
         GenesisBakeHook$2.RxEq(this.recoveredOuter).add(this.wQ9);
      }

      if ("LUNARCLIENT_STATUS_SAVING_CACHE".equals(var1)) {
         GenesisBakeHook$2.getCacheWriterMethodKeys(this.recoveredOuter).add(this.wQ9);
      }

      super.visitLdcInsn(var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 183 && "java/util/zip/ZipFile".equals(var2) && "<init>".equals(var3) && "(Ljava/io/File;)V".equals(var4)) {
         if (this.zipFilePatternState != 2) {
            GenesisBakeHook$2.YEzx(
               this.recoveredOuter,
               "ZipFile constructor does not match NEW/DUP/File contract"
            );
         }

         if ((this.methodAccessFlags & 9) != 9) {
            GenesisBakeHook$2.YEzx(
               this.recoveredOuter,
               "warm cache constructor is not in the public static Baker entry"
            );
         }

         if (!GenesisBakeHook$2.isWarmCacheConstructorDescriptor(this.methodDescriptor)) {
            GenesisBakeHook$2.YEzx(
               this.recoveredOuter,
               "warm cache entry no longer supplies the Genesis loader at slot 1"
            );
         }

         this.zipFilePatternState = 0;
         GenesisBakeHook$2.Xibj3(this.recoveredOuter);
         GenesisBakeHook$2.recordWarmCacheConstructor(this.recoveredOuter, this.wQ9);
         super.visitVarInsn(25, 1);
         super.visitMethodInsn(
            184,
            GenesisBakeHook$2.FMra(this.recoveredOuter),
            "jade$openCache",
            "(Ljava/io/File;Ljava/lang/ClassLoader;)Ljava/util/zip/ZipFile;",
            false
         );
      } else if (var1 == 184
         && "(Ljava/util/TreeMap;Ljava/io/File;)V".equals(var4)
         && var2.startsWith("com/moonsworth/lunar/ichor/util/")) {
         if ((this.methodAccessFlags & 10) != 10) {
            GenesisBakeHook$2.YEzx(
               this.recoveredOuter,
               "cache writer call is not in the private async Baker method"
            );
         }

         GenesisBakeHook$2.incrementCacheWriterCount(this.recoveredOuter);
         GenesisBakeHook$2.recordCacheWriterMethod(this.recoveredOuter, this.wQ9);
         super.visitMethodInsn(
            184,
            GenesisBakeHook$2.FMra(this.recoveredOuter),
            "jade$writeCache",
            "(Ljava/util/TreeMap;Ljava/io/File;)V",
            false
         );
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }

   @Override
   public void visitEnd() {
      if (this.zipFilePatternState != 0) {
         GenesisBakeHook$2.YEzx(this.recoveredOuter, "unterminated ZipFile construction");
      }

      super.visitEnd();
   }
}
