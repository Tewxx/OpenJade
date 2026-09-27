// Jade recovery: original class: jade.deps.eLz.vxorJZN0s1
package jade.client.common;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public final class ProxyChannelInitializer extends ChannelInitializer<Channel> {
   private final ChannelHandler Sdc;

   public ProxyChannelInitializer(ChannelHandler var1) {
      this.Sdc = var1;
   }

   protected void initChannel(Channel var1) throws Exception {
      var1.pipeline().addLast(new ChannelHandler[]{this.Sdc});
      if (var1 instanceof SocketChannel) {
         ProxyManager.installProxyHandler(var1);
      }
   }
}
