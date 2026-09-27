// Jade recovery: original class: jade.deps.eLz.mRexjB1D
package jade.client.hook;

import jade.deps.asm.ClassReader;
import jade.deps.asm.ClassVisitor;
import jade.deps.asm.ClassWriter;
import jade.deps.asm.FieldVisitor;
import jade.deps.asm.Handle;
import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.Set;
import net.jade.dev.agent.ResourceBridge;

public class SrgRemapper implements ClassFileTransformer {
   private final Map<String, String> qaJ;
   private final Map<String, String> ntrD;
   private final Set<String> targetClassNames;

   public SrgRemapper(Set<String> var1, Map<String, String> var2, Map<String, String> var3) {
      this.targetClassNames = var1;
      this.qaJ = var2;
      this.ntrD = var3;
      System.out
         .println(
            "[Mod-Agent] RuntimeRemapper ready: "
               + var2.size()
               + " method mappings, "
               + var3.size()
               + " field mappings."
         );
   }

   @Override
   public byte[] transform(ClassLoader var1, String var2, Class<?> var3, ProtectionDomain var4, byte[] var5) {
      if (var2 == null) {
         return null;
      } else {
         boolean var6 = ResourceBridge.isPublishedClass(var2);
         if (!this.targetClassNames.contains(var2) && !var6) {
            return null;
         } else if (!this.QAGPT(var5)) {
            return null;
         } else {
            ClassReader var7 = new ClassReader(var5);
            ClassWriter var8 = new ClassWriter(var7, 0);
            ClassVisitor var9 = new ClassVisitor(327680, var8) {
               @Override
               public void visitOuterClass(String var1, String var2x, String var3x) {
                  String var4x = var2x == null ? null : (String)SrgRemapper.getMethodMappings(SrgRemapper.this).get(var2x);
                  super.visitOuterClass(var1, var4x != null ? var4x : var2x, var3x);
               }

               @Override
               public FieldVisitor visitField(int var1, String var2x, String var3x, String var4x, Object var5x) {
                  String var6x = (String)SrgRemapper.getFieldMappings(SrgRemapper.this).get(var2x);
                  return super.visitField(var1, var6x != null ? var6x : var2x, var3x, var4x, var5x);
               }

               @Override
               public MethodVisitor visitMethod(int var1, String var2x, String var3x, String var4x, String[] var5x) {
                  String var6x = (String)SrgRemapper.getMethodMappings(SrgRemapper.this).get(var2x);
                  MethodVisitor var7x = super.visitMethod(var1, var6x != null ? var6x : var2x, var3x, var4x, var5x);
                  return var7x == null ? null : new MethodVisitor(327680, var7x) {
                     @Override
                     public void visitFieldInsn(int var1, String var2x, String var3x, String var4x) {
                        String var5x = (String)SrgRemapper.getFieldMappings(SrgRemapper.this).get(var3x);
                        super.visitFieldInsn(var1, var2x, var5x != null ? var5x : var3x, var4x);
                     }

                     @Override
                     public void visitMethodInsn(int var1, String var2x, String var3x, String var4x, boolean var5x) {
                        String var6x = (String)SrgRemapper.getMethodMappings(SrgRemapper.this).get(var3x);
                        super.visitMethodInsn(var1, var2x, var6x != null ? var6x : var3x, var4x, var5x);
                     }

                     @Override
                     public void visitLdcInsn(Object var1) {
                        if (var1 instanceof Handle) {
                           var1 = SrgRemapper.BljK(SrgRemapper.this, (Handle)var1);
                        }

                        super.visitLdcInsn(var1);
                     }

                     @Override
                     public void visitInvokeDynamicInsn(String var1, String var2x, Handle var3x, Object... var4x) {
                        Handle var5x = SrgRemapper.BljK(SrgRemapper.this, var3x);
                        Object[] var6x = (Object[])var4x.clone();

                        for (int var7x = 0; var7x < var6x.length; var7x++) {
                           if (var6x[var7x] instanceof Handle) {
                              var6x[var7x] = SrgRemapper.BljK(SrgRemapper.this, (Handle)var6x[var7x]);
                           }
                        }

                        super.visitInvokeDynamicInsn(var1, var2x, var5x, var6x);
                     }
                  };
               }
            };
            var7.accept(var9, 0);
            byte[] var10 = var8.toByteArray();
            String var11 = buildFrameSignature(var5);
            String var12 = buildFrameSignature(var10);
            if (!var11.equals(var12)) {
               throw new IllegalStateException(
                  "SRG remap changed stack-map frame types in " + var2
               );
            } else {
               return var10;
            }
         }
      }
   }

   private boolean QAGPT(byte[] var1) {
      for (int var2 = 0; var2 < var1.length - 6; var2++) {
         if (var1[var2] == 102) {
            if (var1[var2 + 1] == 117 && var1[var2 + 2] == 110 && var1[var2 + 3] == 99 && var1[var2 + 4] == 95) {
               return true;
            }

            if (var1[var2 + 1] == 105 && var1[var2 + 2] == 101 && var1[var2 + 3] == 108 && var1[var2 + 4] == 100 && var1[var2 + 5] == 95) {
               return true;
            }
         }
      }

      return false;
   }

   private Handle remapHandle(Handle var1) {
      int var2 = var1.getTag();
      boolean var3 = var2 == 1 || var2 == 2 || var2 == 3 || var2 == 4;
      String var4 = var3 ? this.ntrD.get(var1.getName()) : this.qaJ.get(var1.getName());
      return var4 == null ? var1 : new Handle(var2, var1.getOwner(), var4, var1.getDesc());
   }

   private static String buildFrameSignature(byte[] var0) {
      final StringBuilder var1 = new StringBuilder();
      new ClassReader(var0).accept(new ClassVisitor(327680) {
         @Override
         public MethodVisitor visitMethod(int var1x, String var2, String var3, String var4, String[] var5) {
            var1.append('M').append(var3).append(';');
            return new MethodVisitor(327680) {
               @Override
               public void visitFrame(int var1x, int var2x, Object[] var3x, int var4x, Object[] var5x) {
                  var1.append('F').append(var1x).append(':');
                  SrgRemapper.appendFrameTypesBridge(var1, var3x, var2x);
                  var1.append('/');
                  SrgRemapper.appendFrameTypesBridge(var1, var5x, var4x);
                  var1.append(';');
               }
            };
         }
      }, 10);
      return var1.toString();
   }

   private static void appendFrameTypes(StringBuilder var0, Object[] var1, int var2) {
      for (int var3 = 0; var3 < var2; var3++) {
         Object var4 = var1[var3];
         if (var4 instanceof String) {
            var0.append('T').append((String)var4);
         } else if (var4 instanceof Integer) {
            var0.append('I').append(var4);
         } else if (var4 instanceof Label) {
            var0.append('U');
         } else {
            var0.append('N');
         }

         var0.append(',');
      }
   }

   public static Map getMethodMappings(SrgRemapper var0) {
      return var0.qaJ;
   }

   public static Map getFieldMappings(SrgRemapper var0) {
      return var0.ntrD;
   }

   public static Handle BljK(SrgRemapper var0, Handle var1) {
      return var0.remapHandle(var1);
   }

   public static void appendFrameTypesBridge(StringBuilder var0, Object[] var1, int var2) {
      appendFrameTypes(var0, var1, var2);
   }
}
