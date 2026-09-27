// Jade recovery: original class: jade.deps.eLz.FOQzp4QRM
package jade.client.common;

import jade.client.Jade;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class PacketUtils implements IMinecraft {
   private static final PacketExclusions packetExclusions = new PacketExclusions();

   private PacketUtils() {
   }

   public static boolean consumeSilentlySent(Packet<?> var0) {
      return packetExclusions.consumeOutboundPacket(var0);
   }

   public static boolean NQazztK(Packet<?> var0) {
      return packetExclusions.consumeInboundPacket(var0);
   }

   public static void sendSilently(Packet var0) {
      if (isOutboundPacket(var0)) {
         packetExclusions.excludeOutboundPacket(var0);
         Jade.mc.thePlayer.sendQueue.addToSendQueue(var0);
      }
   }

   public static void processInboundPacket(Packet var0) {
      try {
         var0.processPacket(Jade.mc.getNetHandler());
      } catch (ThreadQuickExitException var2) {
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   public static void sendReleaseUseItem() {
      mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
   }

   private static boolean isOutboundPacket(Packet var0) {
      return var0 != null && !var0.getClass().getSimpleName().startsWith("S");
   }
}
