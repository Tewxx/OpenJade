// Jade recovery: recovered class name: MixinDisplay; mixin target: net.minecraft.client.Minecraft; original class: jade.mixin.impl.client.M6af33f0ee8a60d87f59328eb455b2e55
package jade.mixin.impl.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinDisplay {
   @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;<init>()V", shift = At.Shift.BEFORE))
   private void onStartGame(CallbackInfo ci) {
   }
}
