// Jade recovery: original class: jade.deps.eLz.IEdHCC$2
package jade.client.misc;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.FieldVisitor;
import jade.deps.asm.MethodVisitor;
import jade.deps.asm.Type;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public final class GenesisBakeHook$2 extends ClassVisitor {
   private String className;
   private boolean cacheOpenFieldPresent;
   private boolean cacheWriteFieldPresent;
   private int clinitCount;
   private int openCacheCount;
   private int LUXg;
   private int bakerMarkerCount;
   private final Set<String> TTE = new HashSet<>();
   private final Set<String> lFoi = new HashSet<>();
   private String warmCacheConstructorKey;
   private String RZf;
   private String AwvmWo;

   public GenesisBakeHook$2(ClassVisitor var1) {
      super(327680, var1);
   }

   @Override
   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.className = var3;
      super.visit(var1, var2, var3, var4, var5, var6);
   }

   @Override
   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      if ("jade$cacheOpen".equals(var2)) {
         this.cacheOpenFieldPresent = true;
      }

      if ("jade$cacheWrite".equals(var2)) {
         this.cacheWriteFieldPresent = true;
      }

      return super.visitField(var1, var2, var3, var4, var5);
   }

   @Override
   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      MethodVisitor var6 = super.visitMethod(var1, var2, var3, var4, var5);
      String var7 = var2 + var3;
      if ("<clinit>".equals(var2) && "()V".equals(var3)) {
         this.clinitCount++;
         return new GenesisBakeHook$1(this, var6, var7, var1, var3) {
            @Override
            public void visitCode() {
               super.visitCode();
               GenesisBakeHook$2.installCacheBridgeHandles(GenesisBakeHook$2.this, this.mv);
            }
         };
      } else {
         return new GenesisBakeHook$1(this, var6, var7, var1, var3);
      }
   }

   @Override
   public void visitEnd() {
      this.addBridgeHandleField("jade$cacheOpen");
      this.addBridgeHandleField("jade$cacheWrite");
      this.addOpenCacheBridgeMethod();
      this.addWriteCacheBridgeMethod();
      super.visitEnd();
   }

   public String failureReason() {
      if (this.AwvmWo != null) {
         return this.AwvmWo;
      } else if (this.cacheOpenFieldPresent || this.cacheWriteFieldPresent) {
         return "Genesis Baker synthetic bridge field collision";
      } else if (this.clinitCount != 1 || this.bakerMarkerCount != 1 || this.openCacheCount != 1 || this.LUXg != 1) {
         return "Genesis Baker hook structure changed (clinit="
            + this.clinitCount
            + ", marker="
            + this.bakerMarkerCount
            + ", open="
            + this.openCacheCount
            + ", write="
            + this.LUXg
            + ")";
      } else if (!this.TTE.contains(this.warmCacheConstructorKey)) {
         return "Genesis warm-cache constructor is outside the expected load method";
      } else {
         return !this.lFoi.contains(this.RZf)
            ? "Genesis cache writer is outside the expected async save method"
            : null;
      }
   }

   private void recordFailure(String var1) {
      if (this.AwvmWo == null) {
         this.AwvmWo = var1;
      }
   }

   private static boolean hasGenesisWarmCacheArgs(String var0) {
      Type[] var1 = Type.getArgumentTypes(var0);
      return var1.length == 5
         && var1[0].getSort() == 10
         && var1[1].getSort() == 10
         && var1[1].getInternalName().startsWith("com/moonsworth/lunar/genesis/")
         && Type.getObjectType("java/nio/file/Path").equals(var1[2])
         && Type.getObjectType("java/util/Map").equals(var1[3]);
   }

   private void addBridgeHandleField(String var1) {
      FieldVisitor var2 = super.visitField(4122, var1, "Ljava/lang/invoke/MethodHandle;", null, null);
      if (var2 != null) {
         var2.visitEnd();
      }
   }

   private void addOpenCacheBridgeMethod() {
      MethodVisitor var1 = super.visitMethod(
         4106,
         "jade$openCache",
         "(Ljava/io/File;Ljava/lang/ClassLoader;)Ljava/util/zip/ZipFile;",
         null,
         new String[]{"java/lang/Throwable"}
      );
      var1.visitCode();
      var1.visitFieldInsn(
         178,
         this.className,
         "jade$cacheOpen",
         "Ljava/lang/invoke/MethodHandle;"
      );
      var1.visitVarInsn(25, 0);
      var1.visitVarInsn(25, 1);
      var1.visitMethodInsn(
         182,
         "java/lang/invoke/MethodHandle",
         "invokeExact",
         "(Ljava/io/File;Ljava/lang/ClassLoader;)Ljava/util/zip/ZipFile;",
         false
      );
      var1.visitInsn(176);
      var1.visitMaxs(3, 2);
      var1.visitEnd();
   }

   private void addWriteCacheBridgeMethod() {
      MethodVisitor var1 = super.visitMethod(
         4106,
         "jade$writeCache",
         "(Ljava/util/TreeMap;Ljava/io/File;)V",
         null,
         new String[]{"java/lang/Throwable"}
      );
      var1.visitCode();
      var1.visitFieldInsn(
         178,
         this.className,
         "jade$cacheWrite",
         "Ljava/lang/invoke/MethodHandle;"
      );
      var1.visitVarInsn(25, 0);
      var1.visitVarInsn(25, 1);
      var1.visitMethodInsn(
         182,
         "java/lang/invoke/MethodHandle",
         "invokeExact",
         "(Ljava/util/TreeMap;Ljava/io/File;)V",
         false
      );
      var1.visitInsn(177);
      var1.visitMaxs(3, 2);
      var1.visitEnd();
   }

   private void emitCacheBridgeHandleInit(MethodVisitor var1) {
      this.qIdtR(
         var1,
         "open",
         "jade$cacheOpen",
         new Type[]{Type.getType(File.class), Type.getType(ClassLoader.class)}
      );
      this.qIdtR(
         var1,
         "write",
         "jade$cacheWrite",
         new Type[]{Type.getType(TreeMap.class), Type.getType(File.class)}
      );
   }

   private void qIdtR(MethodVisitor var1, String var2, String var3, Type[] var4) {
      var1.visitMethodInsn(
         184,
         "java/lang/invoke/MethodHandles",
         "publicLookup",
         "()Ljava/lang/invoke/MethodHandles$Lookup;",
         false
      );
      var1.visitMethodInsn(
         184,
         "java/lang/ClassLoader",
         "getSystemClassLoader",
         "()Ljava/lang/ClassLoader;",
         false
      );
      var1.visitLdcInsn("net.jade.dev.agent.cache.GenesisBakeCacheBridge");
      var1.visitMethodInsn(
         182,
         "java/lang/ClassLoader",
         "loadClass",
         "(Ljava/lang/String;)Ljava/lang/Class;",
         false
      );
      var1.visitLdcInsn(var2);
      tlkydQ(var1, var4.length);
      var1.visitTypeInsn(189, "java/lang/Class");

      for (int var5 = 0; var5 < var4.length; var5++) {
         var1.visitInsn(89);
         tlkydQ(var1, var5);
         var1.visitLdcInsn(var4[var5]);
         var1.visitInsn(83);
      }

      var1.visitMethodInsn(
         182,
         "java/lang/Class",
         "getMethod",
         "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;",
         false
      );
      var1.visitMethodInsn(
         182,
         "java/lang/invoke/MethodHandles$Lookup",
         "unreflect",
         "(Ljava/lang/reflect/Method;)Ljava/lang/invoke/MethodHandle;",
         false
      );
      var1.visitFieldInsn(179, this.className, var3, "Ljava/lang/invoke/MethodHandle;");
   }

   private static void tlkydQ(MethodVisitor var0, int var1) {
      if (var1 >= 0 && var1 <= 5) {
         var0.visitInsn(3 + var1);
      } else {
         var0.visitIntInsn(16, var1);
      }
   }

   public static void installCacheBridgeHandles(GenesisBakeHook$2 var0, MethodVisitor var1) {
      var0.emitCacheBridgeHandleInit(var1);
   }

   public static void YEzx(GenesisBakeHook$2 var0, String var1) {
      var0.recordFailure(var1);
   }

   public static int incrementBakerMarkerCount(GenesisBakeHook$2 var0) {
      return var0.bakerMarkerCount++;
   }

   public static Set RxEq(GenesisBakeHook$2 var0) {
      return var0.TTE;
   }

   public static Set getCacheWriterMethodKeys(GenesisBakeHook$2 var0) {
      return var0.lFoi;
   }

   public static boolean isWarmCacheConstructorDescriptor(String var0) {
      return hasGenesisWarmCacheArgs(var0);
   }

   public static int Xibj3(GenesisBakeHook$2 var0) {
      return var0.openCacheCount++;
   }

   public static String recordWarmCacheConstructor(GenesisBakeHook$2 var0, String var1) {
      return var0.warmCacheConstructorKey = var1;
   }

   public static String FMra(GenesisBakeHook$2 var0) {
      return var0.className;
   }

   public static int incrementCacheWriterCount(GenesisBakeHook$2 var0) {
      return var0.LUXg++;
   }

   public static String recordCacheWriterMethod(GenesisBakeHook$2 var0, String var1) {
      return var0.RZf = var1;
   }
}
