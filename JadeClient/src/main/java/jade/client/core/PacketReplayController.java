// Jade recovery: original class: jade.deps.eLz.e20LTj0Y
package jade.client.core;

import jade.client.common.PacketUtils;
import jade.mixin.impl.accessor.IAccessorMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public final class PacketReplayController {
   private static final PacketReplayController bo3 = new PacketReplayController();
   private final EntityPacketBuffer ORyxdM = new EntityPacketBuffer();
   private final TickAccumulator tickAccumulator = new TickAccumulator();
   private boolean HWhC;

   public static PacketReplayController getInstance() {
      return bo3;
   }

   public boolean YkFjupZ() {
      return this.HWhC;
   }

   public void beginBuffering() {
      if (!this.HWhC) {
         this.HWhC = true;
         this.tickAccumulator.resetAt(System.nanoTime());
      }
   }

   public void endBuffering() {
      if (this.HWhC) {
         this.HWhC = false;
         this.flushBufferedPackets();
      }
   }

   public void resetBuffer() {
      this.HWhC = false;
      this.ORyxdM.CADWz6();
      this.tickAccumulator.YLjRn();
   }

   public boolean bufferPacket(Packet<?> var1) {
      if (!this.HWhC) {
         return false;
      } else {
         this.ORyxdM.addPacket(var1);
         return true;
      }
   }

   public void replayBufferedPackets() {
      if (this.HWhC) {
         Minecraft var1 = Minecraft.getMinecraft();
         if (var1.theWorld != null && var1.thePlayer != null) {
            this.ORyxdM.SvivXg(PacketUtils::processInboundPacket);
            TickAccumulator$1 var2 = this.tickAccumulator.MDBEHL(System.nanoTime());

            for (int var3 = 0; var3 < var2.getTickCount(); var3++) {
               WorldEntityTicker.tickWorldEntities(var1);
            }

            ((IAccessorMinecraft)var1).getTimer().renderPartialTicks = var2.getPartialTicks();
            WorldEntityTicker.updateLastTickState(var1.thePlayer);
         }
      }
   }

   public void flushBufferedPackets() {
      this.ORyxdM.lPreU(PacketUtils::processInboundPacket);
      this.tickAccumulator.YLjRn();
   }
}
