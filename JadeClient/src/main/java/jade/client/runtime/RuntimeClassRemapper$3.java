// Jade recovery: recovered class name: RuntimeClassRemapper; original class: jade.deps.eLz.Hlsbhh$3
package jade.client.runtime;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.MethodVisitor;

public final class RuntimeClassRemapper$3 extends ClassVisitor {
   private final boolean bridgeMinecraftMembers;

   RuntimeClassRemapper$3(ClassVisitor output, boolean bridgeMinecraftMembers) {
      super(589824, output);
      this.bridgeMinecraftMembers = bridgeMinecraftMembers;
   }

   @Override
   public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
      MethodVisitor output = super.visitMethod(access, name, descriptor, signature, exceptions);
      return new MethodVisitor(589824, output) {
         @Override
         public void visitFieldInsn(int opcode, String owner, String field, String fieldDescriptor) {
            if (RuntimeClassRemapper$3.this.bridgeMinecraftMembers
               && owner.startsWith("net/minecraft/")) {
               String dynamicDescriptor;
               if (opcode == 178) {
                  dynamicDescriptor = "()" + fieldDescriptor;
               } else if (opcode == 179) {
                  dynamicDescriptor = "(" + fieldDescriptor + ")V";
               } else if (opcode == 180) {
                  dynamicDescriptor = "(L" + owner + ";)" + fieldDescriptor;
               } else {
                  if (opcode != 181) {
                     super.visitFieldInsn(opcode, owner, field, fieldDescriptor);
                     return;
                  }

                  dynamicDescriptor = "(L"
                     + owner
                     + ";"
                     + fieldDescriptor
                     + ")V";
               }

               super.visitInvokeDynamicInsn(field, dynamicDescriptor, RuntimeClassRemapper.access$300(), owner, field, fieldDescriptor, opcode);
            } else {
               super.visitFieldInsn(opcode, owner, field, fieldDescriptor);
            }
         }

         @Override
         public void visitTypeInsn(int opcode, String type) {
            if (!RuntimeClassRemapper.access$400(type) || opcode != 192) {
               if (RuntimeClassRemapper.access$400(type) && opcode == 193) {
                  super.visitInsn(87);
                  super.visitInsn(4);
               } else {
                  super.visitTypeInsn(opcode, type);
               }
            }
         }

         @Override
         public void visitMethodInsn(int opcode, String owner, String method, String methodDescriptor, boolean isInterface) {
            if (RuntimeClassRemapper$3.this.bridgeMinecraftMembers
               && owner.startsWith("net/minecraft/")
               && opcode != 183
               && !method.startsWith("<")) {
               String dynamicDescriptor = opcode == 184 ? methodDescriptor : RuntimeClassRemapper.access$500(owner, methodDescriptor);
               super.visitInvokeDynamicInsn(method, dynamicDescriptor, RuntimeClassRemapper.access$300(), owner, method, methodDescriptor, opcode);
            } else if (!RuntimeClassRemapper.access$400(owner)) {
               super.visitMethodInsn(opcode, owner, method, methodDescriptor, isInterface);
            } else {
               boolean staticCall = opcode == 184;
               String dynamicDescriptor = staticCall ? methodDescriptor : RuntimeClassRemapper.access$600(methodDescriptor);
               super.visitInvokeDynamicInsn(method, dynamicDescriptor, RuntimeClassRemapper.access$700(), owner, staticCall ? 1 : 0);
            }
         }
      };
   }

   static boolean access$200(jade.client.runtime.RuntimeClassRemapper$3 arg0) {
      return arg0.bridgeMinecraftMembers;
   }
}
