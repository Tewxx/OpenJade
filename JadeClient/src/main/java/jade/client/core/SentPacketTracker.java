// Jade recovery: original class: jade.deps.eLz.NrWvYC
package jade.client.core;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Vec3;

public final class SentPacketTracker {
   private final Set<Packet<?>> trackedPackets = Collections.newSetFromMap(Collections.synchronizedMap(new IdentityHashMap<>()));
   private volatile Vec3 vec3;

   public void track(Packet<?> var1) {
      this.trackedPackets.add(var1);
   }

   public boolean untrack(Packet<?> var1) {
      return this.trackedPackets.remove(var1);
   }

   public Vec3 KoeN() {
      return this.vec3;
   }

   public void PWbx34() {
      this.trackedPackets.clear();
      this.vec3 = null;
   }

   public void recordMovement(Packet<?> var1) {
      if (var1 instanceof C03PacketPlayer) {
         C03PacketPlayer var2 = (C03PacketPlayer)var1;
         if (var2.isMoving()) {
            this.vec3 = new Vec3(var2.getPositionX(), var2.getPositionY(), var2.getPositionZ());
         }
      }
   }
}
