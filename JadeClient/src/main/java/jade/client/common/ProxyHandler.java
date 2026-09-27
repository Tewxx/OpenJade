// Jade recovery: original class: jade.deps.eLz.X6yuBBOhU9
package jade.client.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import jade.client.gui.ProxySettings$0;
import jade.client.gui.ProxySettings;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class ProxyHandler extends ChannelDuplexHandler {
   private static final int quqOa = 1;
   private static final int STAGE_SOCKS5_GREETING = 2;
   private static final int STAGE_SOCKS5_AUTH = 3;
   private static final int STAGE_SOCKS5_CONNECT = 4;
   private final ProxySettings proxySettings;
   private final InetSocketAddress iIzt;
   private SocketAddress vsNps;
   private ChannelPromise connectPromise;
   private ByteBuf handshakeBuffer;
   private int xTjvJp;
   private boolean pendingChannelActive;
   private boolean PTrEr;

   public ProxyHandler(ProxySettings var1) {
      this.proxySettings = var1.copy();
      this.iIzt = new InetSocketAddress(this.proxySettings.getHost(), this.proxySettings.getPort());
   }

   public void connect(final ChannelHandlerContext var1, SocketAddress var2, SocketAddress var3, ChannelPromise var4) throws Exception {
      this.vsNps = var2;
      this.connectPromise = var4;
      ChannelPromise var5 = var1.newPromise();
      var5.addListener(new ChannelFutureListener() {
         public void operationComplete(ChannelFuture var1x) throws Exception {
            if (!var1x.isSuccess()) {
               ProxyHandler.handleConnectFailure(ProxyHandler.this, var1, var1x.cause());
            } else {
               ProxyHandler.VZESRN(ProxyHandler.this, var1);
            }
         }
      });
      if (var3 == null) {
         var1.connect(this.iIzt, var5);
      } else {
         var1.connect(this.iIzt, var3, var5);
      }
   }

   public void channelActive(ChannelHandlerContext var1) throws Exception {
      if (this.PTrEr) {
         super.channelActive(var1);
      } else {
         this.pendingChannelActive = true;
      }
   }

   public void channelRead(ChannelHandlerContext var1, Object var2) throws Exception {
      if (this.PTrEr) {
         super.channelRead(var1, var2);
      } else if (!(var2 instanceof ByteBuf)) {
         ReferenceCountUtil.release(var2);
         this.failConnection(var1, new IllegalStateException("Unexpected proxy response"));
      } else {
         if (this.handshakeBuffer == null) {
            this.handshakeBuffer = Unpooled.buffer();
         }

         ByteBuf var3 = (ByteBuf)var2;

         try {
            this.handshakeBuffer.writeBytes(var3);
         } finally {
            var3.release();
         }

         this.piNs(var1);
      }
   }

   public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) throws Exception {
      this.failConnection(var1, var2);
   }

   public void handlerRemoved(ChannelHandlerContext var1) throws Exception {
      if (this.handshakeBuffer != null) {
         this.handshakeBuffer.release();
         this.handshakeBuffer = null;
      }

      super.handlerRemoved(var1);
   }

   private void startHandshake(ChannelHandlerContext var1) {
      if (this.proxySettings.getProxyType() == ProxySettings$0.SOCKS4) {
         this.xTjvJp = 1;
         this.sendSocks4Connect(var1);
      } else {
         this.xTjvJp = 2;
         this.sendSocks5Greeting(var1);
      }
   }

   private void piNs(ChannelHandlerContext var1) {
      try {
         if (this.xTjvJp == 1) {
            this.readSocks4Reply(var1);
         } else if (this.xTjvJp == 2) {
            this.QKvW(var1);
         } else if (this.xTjvJp == 3) {
            this.readSocks5AuthReply(var1);
         } else if (this.xTjvJp == 4) {
            this.JltP(var1);
         }
      } catch (Exception var3) {
         this.failConnection(var1, var3);
      }
   }

   private void sendSocks4Connect(ChannelHandlerContext var1) {
      InetSocketAddress var2 = this.resolveServerAddress();
      InetAddress var3 = var2.getAddress();
      if (!(var3 instanceof Inet4Address)) {
         this.failConnection(var1, new IllegalArgumentException("SOCKS4 requires an IPv4 server address"));
      } else {
         byte[] var4 = iUzlLc(this.proxySettings.getUserId());
         ByteBuf var5 = var1.alloc().buffer(9 + var4.length);
         var5.writeByte(4);
         var5.writeByte(1);
         var5.writeShort(var2.getPort());
         var5.writeBytes(var3.getAddress());
         var5.writeBytes(var4);
         var5.writeByte(0);
         this.rZlqD0(var1, var5);
      }
   }

   private void readSocks4Reply(ChannelHandlerContext var1) {
      if (this.handshakeBuffer.readableBytes() >= 8) {
         this.handshakeBuffer.readUnsignedByte();
         short var2 = this.handshakeBuffer.readUnsignedByte();
         this.handshakeBuffer.skipBytes(6);
         if (var2 != 90) {
            this.failConnection(var1, new IllegalStateException("SOCKS4 proxy rejected connection: " + var2));
         } else {
            this.finishHandshake(var1);
         }
      }
   }

   private void sendSocks5Greeting(ChannelHandlerContext var1) {
      boolean var2 = LLAF(this.proxySettings.getUsername()) || LLAF(this.proxySettings.getPassword());
      ByteBuf var3 = var1.alloc().buffer(var2 ? 4 : 3);
      var3.writeByte(5);
      if (var2) {
         var3.writeByte(2);
         var3.writeByte(0);
         var3.writeByte(2);
      } else {
         var3.writeByte(1);
         var3.writeByte(0);
      }

      this.rZlqD0(var1, var3);
   }

   private void QKvW(ChannelHandlerContext var1) {
      if (this.handshakeBuffer.readableBytes() >= 2) {
         short var2 = this.handshakeBuffer.readUnsignedByte();
         short var3 = this.handshakeBuffer.readUnsignedByte();
         if (var2 == 5 && var3 != 255) {
            if (var3 == 2) {
               this.xTjvJp = 3;
               this.sendSocks5Auth(var1);
            } else if (var3 == 0) {
               this.xTjvJp = 4;
               this.sendSocks5ConnectRequest(var1);
            } else {
               this.failConnection(var1, new IllegalStateException("Unsupported SOCKS5 initialization method: " + var3));
            }
         } else {
            this.failConnection(var1, new IllegalStateException("SOCKS5 proxy refused initialization"));
         }
      }
   }

   private void sendSocks5Auth(ChannelHandlerContext var1) {
      byte[] var2 = iUzlLc(this.proxySettings.getUsername());
      byte[] var3 = iUzlLc(this.proxySettings.getPassword());
      if (var2.length <= 255 && var3.length <= 255) {
         ByteBuf var4 = var1.alloc().buffer(3 + var2.length + var3.length);
         var4.writeByte(1);
         var4.writeByte(var2.length);
         var4.writeBytes(var2);
         var4.writeByte(var3.length);
         var4.writeBytes(var3);
         this.rZlqD0(var1, var4);
      } else {
         this.failConnection(var1, new IllegalArgumentException("SOCKS5 username/password is too long"));
      }
   }

   private void readSocks5AuthReply(ChannelHandlerContext var1) {
      if (this.handshakeBuffer.readableBytes() >= 2) {
         this.handshakeBuffer.readUnsignedByte();
         short var2 = this.handshakeBuffer.readUnsignedByte();
         if (var2 != 0) {
            this.failConnection(var1, new IllegalStateException("SOCKS5 proxy rejected username/password"));
         } else {
            this.xTjvJp = 4;
            this.sendSocks5ConnectRequest(var1);
         }
      }
   }

   private void sendSocks5ConnectRequest(ChannelHandlerContext var1) {
      InetSocketAddress var2 = this.resolveServerAddress();
      ByteBuf var3 = var1.alloc().buffer();
      var3.writeByte(5);
      var3.writeByte(1);
      var3.writeByte(0);
      InetAddress var4 = var2.getAddress();
      if (var4 instanceof Inet4Address) {
         var3.writeByte(1);
         var3.writeBytes(var4.getAddress());
      } else if (var4 instanceof Inet6Address) {
         var3.writeByte(4);
         var3.writeBytes(var4.getAddress());
      } else {
         byte[] var5 = iUzlLc(var2.getHostString());
         if (var5.length > 255) {
            this.failConnection(var1, new IllegalArgumentException("Server host is too long for SOCKS5"));
            var3.release();
            return;
         }

         var3.writeByte(3);
         var3.writeByte(var5.length);
         var3.writeBytes(var5);
      }

      var3.writeShort(var2.getPort());
      this.rZlqD0(var1, var3);
   }

   private void JltP(ChannelHandlerContext var1) {
      if (this.handshakeBuffer.readableBytes() >= 5) {
         this.handshakeBuffer.markReaderIndex();
         short var2 = this.handshakeBuffer.readUnsignedByte();
         short var3 = this.handshakeBuffer.readUnsignedByte();
         this.handshakeBuffer.readUnsignedByte();
         short var4 = this.handshakeBuffer.readUnsignedByte();
         short var5;
         if (var4 == 1) {
            var5 = 4;
         } else if (var4 == 4) {
            var5 = 16;
         } else {
            if (var4 != 3) {
               this.failConnection(var1, new IllegalStateException("SOCKS5 proxy sent unknown address type: " + var4));
               return;
            }

            if (this.handshakeBuffer.readableBytes() < 1) {
               this.handshakeBuffer.resetReaderIndex();
               return;
            }

            var5 = this.handshakeBuffer.readUnsignedByte();
         }

         if (this.handshakeBuffer.readableBytes() < var5 + 2) {
            this.handshakeBuffer.resetReaderIndex();
         } else {
            this.handshakeBuffer.skipBytes(var5 + 2);
            if (var2 == 5 && var3 == 0) {
               this.finishHandshake(var1);
            } else {
               this.failConnection(var1, new IllegalStateException("SOCKS5 proxy rejected connection: " + var3));
            }
         }
      }
   }

   private InetSocketAddress resolveServerAddress() {
      if (!(this.vsNps instanceof InetSocketAddress)) {
         throw new IllegalArgumentException("Unsupported server address: " + this.vsNps);
      } else {
         return (InetSocketAddress)this.vsNps;
      }
   }

   private void rZlqD0(final ChannelHandlerContext var1, ByteBuf var2) {
      var1.writeAndFlush(var2).addListener(new ChannelFutureListener() {
         public void operationComplete(ChannelFuture var1x) throws Exception {
            if (!var1x.isSuccess()) {
               ProxyHandler.handleConnectFailure(ProxyHandler.this, var1, var1x.cause());
            }
         }
      });
   }

   private void finishHandshake(ChannelHandlerContext var1) {
      this.PTrEr = true;
      if (this.pendingChannelActive) {
         var1.fireChannelActive();
      }

      if (this.connectPromise != null && !this.connectPromise.isDone()) {
         this.connectPromise.setSuccess();
      }

      if (this.handshakeBuffer != null && this.handshakeBuffer.isReadable()) {
         var1.fireChannelRead(this.handshakeBuffer.readBytes(this.handshakeBuffer.readableBytes()));
      }

      var1.pipeline().remove(this);
   }

   private void failConnection(ChannelHandlerContext var1, Throwable var2) {
      if (this.connectPromise != null && !this.connectPromise.isDone()) {
         this.connectPromise.setFailure(var2);
      }

      var1.close();
   }

   private static byte[] iUzlLc(String var0) {
      return (var0 == null ? "" : var0).getBytes(CharsetUtil.UTF_8);
   }

   private static boolean LLAF(String var0) {
      return var0 != null && var0.length() > 0;
   }

   public static void handleConnectFailure(ProxyHandler var0, ChannelHandlerContext var1, Throwable var2) {
      var0.failConnection(var1, var2);
   }

   public static void VZESRN(ProxyHandler var0, ChannelHandlerContext var1) {
      var0.startHandshake(var1);
   }
}
