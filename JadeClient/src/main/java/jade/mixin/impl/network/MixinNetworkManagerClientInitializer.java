// Jade recovery: recovered class name: MixinNetworkManagerClientInitializer; mixin target: net.minecraft.network.NetworkManager$5; original class: jade.mixin.impl.network.M3aaaec49ff6d587a23f060712544be8c
package jade.mixin.impl.network;

import io.netty.channel.Channel;
import jade.client.common.ProxyManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.network.NetworkManager$5")
public class MixinNetworkManagerClientInitializer {
   @Inject(method = "initChannel(Lio/netty/channel/Channel;)V", at = @At("RETURN"), remap = false)
   private void jade$installProxyHandler(Channel channel, CallbackInfo ci) {
      ProxyManager.installProxyHandler(channel);
   }
}
