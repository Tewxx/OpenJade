// Jade recovery: original class: jade.deps.eLz.BHs3TBy$25
package jade.client.runtime;

import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$25 extends ClientClassTransformer$35 {
   public ClientClassTransformer$25(MethodVisitor var1) {
      super(
         var1,
         "onOrientCameraPre",
         "onOrientCameraPost"
      );
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (var1 == 182
         && "net/minecraft/util/Vec3".equals(var2)
         && (
            "distanceTo".equals(var3)
               || "func_72438_d".equals(var3)
         )
         && "(Lnet/minecraft/util/Vec3;)D".equals(var4)) {
         ClientClassTransformer.emitHookCall(
            this, "onCameraClipDistance", "(Ljava/lang/Object;Ljava/lang/Object;)D"
         );
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }
}
