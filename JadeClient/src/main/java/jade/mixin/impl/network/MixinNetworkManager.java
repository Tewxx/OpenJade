// Jade recovery: recovered class name: MixinNetworkManager; mixin target: net.minecraft.network.NetworkManager; original class: jade.mixin.impl.network.M0269c44eb07e8efa2da002dfba65c81b
package jade.mixin.impl.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import jade.client.hook.PacketEventDispatcher;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
   @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
   public void jade$beforeSend(Packet packet, CallbackInfo ci) {
      if (PacketEventDispatcher.onOutgoingPacket(packet)) {
         ci.cancel();
      }
   }

   @Inject(method = "dispatchPacket", at = @At("HEAD"))
   public void jade$beforeDispatch(Packet packet, GenericFutureListener<? extends Future<? super Void>>[] listeners, CallbackInfo ci) {
      PacketEventDispatcher.pvkT(packet, listeners);
   }

   @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
   public void jade$beforeReceive(ChannelHandlerContext context, Packet packet, CallbackInfo ci) {
      if (PacketEventDispatcher.onIncomingPacket(packet)) {
         ci.cancel();
      }
   }
}
