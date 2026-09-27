// Jade recovery: original class: jade.deps.eLz.BHs3TBy$33
package jade.client.runtime;

import jade.deps.asm.Label;
import jade.deps.asm.MethodVisitor;

public final class ClientClassTransformer$33 extends MethodVisitor {
   private final boolean badlionRenderPass;

   public ClientClassTransformer$33(MethodVisitor var1, boolean var2) {
      super(589824, var1);
      this.badlionRenderPass = var2;
   }

   @Override
   public void visitCode() {
      super.visitCode();
      ClientClassTransformer.emitHookCall(this, "onRunTickStart", "()V");
      if (this.badlionRenderPass) {
         ClientClassTransformer.emitHookCall(this, "onPrePlayerInteract", "()V");
      }
   }

   @Override
   public void visitInsn(int var1) {
      if (var1 >= 172 && var1 <= 177) {
         super.visitVarInsn(25, 0);
         ClientClassTransformer.emitHookCall(this, "onPassiveFastMine", "(Ljava/lang/Object;)V");
         if (this.badlionRenderPass) {
            super.visitVarInsn(25, 0);
            ClientClassTransformer.emitHookCall(
               this, "onBadlionThirdPersonTick", "(Ljava/lang/Object;)V"
            );
            super.visitVarInsn(25, 0);
            ClientClassTransformer.emitHookCall(this, "onBadlionWorldTick", "(Ljava/lang/Object;)V");
         }

         ClientClassTransformer.emitHookCall(this, "onRunTickEnd", "()V");
      }

      super.visitInsn(var1);
   }

   @Override
   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if ((var1 == 182 || var1 == 183 || var1 == 184)
         && "net/minecraft/client/Minecraft".equals(var2)
         && "(Lnet/minecraft/client/settings/GameSettings;I)V".equals(var4)) {
         Label var6 = new Label();
         Label var7 = new Label();
         super.visitVarInsn(54, 995);
         super.visitVarInsn(58, 994);
         if (var1 != 184) {
            super.visitVarInsn(58, 993);
         }

         super.visitVarInsn(25, 994);
         super.visitVarInsn(21, 995);
         super.visitInsn(4);
         ClientClassTransformer.emitHookCall(this, "onSetThirdPersonView", "(Ljava/lang/Object;IZ)Z");
         super.visitJumpInsn(153, var6);
         super.visitJumpInsn(167, var7);
         super.visitLabel(var6);
         if (var1 != 184) {
            super.visitVarInsn(25, 993);
         }

         super.visitVarInsn(25, 994);
         super.visitVarInsn(21, 995);
         super.visitMethodInsn(var1, var2, var3, var4, var5);
         super.visitLabel(var7);
      } else if (var1 == 184
         && "org/lwjgl/input/Keyboard".equals(var2)
         && "next".equals(var3)
         && "()Z".equals(var4)) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
         super.visitVarInsn(54, 997);
         super.visitVarInsn(21, 997);
         ClientClassTransformer.emitHookCall(this, "onKeyboardEventAvailable", "(Z)V");
         super.visitVarInsn(21, 997);
      } else if (var1 == 184
         && "org/lwjgl/input/Mouse".equals(var2)
         && "next".equals(var3)
         && "()Z".equals(var4)) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
         super.visitVarInsn(54, 997);
         super.visitVarInsn(21, 997);
         ClientClassTransformer.emitHookCall(this, "onMouseEventAvailable", "(Z)V");
         super.visitVarInsn(21, 997);
      } else if (var1 == 184
         && "net/minecraft/client/settings/KeyBinding".equals(var2)
         && (
            "setKeyBindState".equals(var3)
               || "func_74510_a".equals(var3)
         )
         && "(IZ)V".equals(var4)) {
         super.visitVarInsn(54, 997);
         super.visitVarInsn(54, 996);
         super.visitVarInsn(21, 996);
         super.visitVarInsn(21, 997);
         ClientClassTransformer.emitHookCall(this, "onKeyBindingState", "(IZ)V");
         super.visitVarInsn(21, 996);
         super.visitVarInsn(21, 997);
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      } else if (var1 == 182
         && "net/minecraft/client/multiplayer/PlayerControllerMP".equals(var2)
         && (
            "updateController".equals(var3)
               || "func_78765_e".equals(var3)
         )
         && "()V".equals(var4)) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
         ClientClassTransformer.emitHookCall(this, "onAfterControllerUpdate", "()V");
      } else if (var1 == 182
         && "net/minecraft/entity/player/InventoryPlayer".equals(var2)
         && (
            "changeCurrentItem".equals(var3)
               || "func_70453_c".equals(var3)
         )
         && "(I)V".equals(var4)) {
         ClientClassTransformer.emitHookCall(this, "onChangeCurrentItem", "(Ljava/lang/Object;I)V");
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      }
   }

   @Override
   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      if (var1 != 181
         || !"net/minecraft/client/settings/GameSettings".equals(var2)
         || !"I".equals(var4)
         || !"thirdPersonView".equals(var3)
            && !"field_74320_O".equals(var3)) {
         if (var1 != 181
            || !"net/minecraft/entity/player/InventoryPlayer".equals(var2)
            || !"I".equals(var4)
            || !"currentItem".equals(var3)
               && !"field_70461_c".equals(var3)) {
            super.visitFieldInsn(var1, var2, var3, var4);
         } else {
            ClientClassTransformer.emitHookCall(this, "onSetCurrentItem", "(Ljava/lang/Object;I)V");
         }
      } else {
         ClientClassTransformer.emitHookCall(this, "onSetThirdPersonView", "(Ljava/lang/Object;I)V");
      }
   }
}
