// Jade recovery: recovered class name: MixinNetHandlerPlayClient; mixin target: net.minecraft.client.network.NetHandlerPlayClient; original class: jade.mixin.impl.network.Md32e15dacaaa57dbdf883cf783a08df5
package jade.mixin.impl.network;

import jade.client.common.ChatUtils;
import jade.client.common.EventBus;
import jade.client.event.ChatReceivedEvent;
import jade.client.event.DisconnectEvent;
import jade.client.event.ExplosionEvent;
import jade.client.event.VelocityEvent;
import jade.mixin.impl.accessor.IAccessorS02PacketChat;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
   @Inject(method = "handleChat", at = @At("HEAD"), cancellable = true)
   public void onHandleChat(S02PacketChat packet, CallbackInfo ci) {
      if (ChatUtils.hasFlowTag(packet.getChatComponent().getFormattedText())) {
         ChatUtils.registerComponentText(packet.getChatComponent());
         ((IAccessorS02PacketChat)packet).setChatComponent(ChatUtils.copyWithoutFlow(packet.getChatComponent()));
      }

      ChatReceivedEvent event = new ChatReceivedEvent(packet.getType(), packet.getChatComponent());
      EventBus.post(event);
      if (event.isCanceled()) {
         ci.cancel();
      }
   }

   @Inject(method = "handleDisconnect", at = @At("HEAD"))
   public void onHandleDisconnect(CallbackInfo ci) {
      EventBus.post(new DisconnectEvent());
   }

   @Inject(method = "handleEntityVelocity", at = @At("HEAD"), cancellable = true)
   public void handleEntityVelocityInjection(S12PacketEntityVelocity packet, CallbackInfo ci) {
      VelocityEvent preEntityVelocityEvent = new VelocityEvent(packet);
      EventBus.post(preEntityVelocityEvent);
      if (preEntityVelocityEvent.isCanceled()) {
         ci.cancel();
      }
   }

   @Inject(method = "handleExplosion", at = @At("HEAD"), cancellable = true)
   public void handleExplosionInjection(S27PacketExplosion packet, CallbackInfo ci) {
      ExplosionEvent event = new ExplosionEvent(packet);
      EventBus.post(event);
      if (event.isCanceled()) {
         ci.cancel();
      }
   }
}
