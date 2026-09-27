// Jade recovery: original class: jade.deps.eLz.BXpgB9edE
package jade.client.module.combat.autoaim;

import jade.mixin.impl.accessor.IAccessorS14PacketEntity;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.world.World;

public final class EntityPositionTracker {
   private static final double Gas3 = 5.0E7;
   private final Object stateLock = new Object();
   private final Map<Integer, EntityPositionTracker$1> stateByEntityId = new HashMap<>();
   private final Queue<EntityPositionTracker$0> enyAs = new ArrayDeque<>();
   private final Queue<Integer> removedEntityIds = new ArrayDeque<>();

   public void ELFwOy() {
      synchronized (this.stateLock) {
         this.stateByEntityId.clear();
         this.enyAs.clear();
         this.removedEntityIds.clear();
      }
   }

   public void onPacketReceived(Packet<?> var1, World var2) {
      double var3 = System.nanoTime() / 5.0E7;
      synchronized (this.stateLock) {
         if (var1 instanceof S13PacketDestroyEntities) {
            for (int var9 : ((S13PacketDestroyEntities)var1).getEntityIDs()) {
               this.stateByEntityId.remove(var9);
               this.dropQueuedUpdates(var9);
               this.removedEntityIds.add(var9);
            }
         } else if (var1 instanceof S14PacketEntity) {
            S14PacketEntity var12 = (S14PacketEntity)var1;
            int var15 = ((IAccessorS14PacketEntity)var12).getEntityId();
            EntityPositionTracker$1 var17 = this.stateByEntityId.get(var15);
            if (var17 == null) {
               Entity var19 = var2 == null ? null : var2.getEntityByID(var15);
               if (var19 == null) {
                  return;
               }

               var17 = new EntityPositionTracker$1(var19.serverPosX / 32.0, var19.serverPosY / 32.0, var19.serverPosZ / 32.0);
               this.stateByEntityId.put(var15, var17);
            }

            var17.x = var17.x + var12.func_149062_c() / 32.0;
            var17.y = var17.y + var12.func_149061_d() / 32.0;
            var17.z = var17.z + var12.func_149064_e() / 32.0;
            this.enyAs.add(new EntityPositionTracker$0(var15, var3, var17.x, var17.y, var17.z, false, false));
         } else if (var1 instanceof S18PacketEntityTeleport) {
            S18PacketEntityTeleport var13 = (S18PacketEntityTeleport)var1;
            int var16 = var13.getEntityId();
            EntityPositionTracker$1 var18 = new EntityPositionTracker$1(var13.getX() / 32.0, var13.getY() / 32.0, var13.getZ() / 32.0);
            this.stateByEntityId.put(var16, var18);
            this.enyAs.add(new EntityPositionTracker$0(var16, var3, var18.x, var18.y, var18.z, true, false));
         } else if (var1 instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity var14 = (S12PacketEntityVelocity)var1;
            this.enyAs
               .add(
                  new EntityPositionTracker$0(var14.getEntityID(), var3, var14.getMotionX() / 8000.0, var14.getMotionY() / 8000.0, var14.getMotionZ() / 8000.0, false, true)
               );
         }
      }
   }

   public void trackInitialPosition(int var1, double var2, double var4, double var6) {
      synchronized (this.stateLock) {
         if (!this.stateByEntityId.containsKey(var1)) {
            this.stateByEntityId.put(var1, new EntityPositionTracker$1(var2, var4, var6));
            this.enyAs.add(new EntityPositionTracker$0(var1, System.nanoTime() / 5.0E7, var2, var4, var6, false, false));
         }
      }
   }

   public void MVkfPjx(int var1, double var2, double var4, double var6) {
      synchronized (this.stateLock) {
         this.stateByEntityId.put(var1, new EntityPositionTracker$1(var2, var4, var6));
         this.enyAs.add(new EntityPositionTracker$0(var1, System.nanoTime() / 5.0E7, var2, var4, var6, true, false));
      }
   }

   public void removeEntity(int var1) {
      synchronized (this.stateLock) {
         this.stateByEntityId.remove(var1);
         this.dropQueuedUpdates(var1);
      }
   }

   public int[] Thdhe() {
      synchronized (this.stateLock) {
         int[] var2 = new int[this.removedEntityIds.size()];
         int var3 = 0;

         Integer var4;
         while ((var4 = this.removedEntityIds.poll()) != null) {
            var2[var3++] = var4;
         }

         return var2;
      }
   }

   public boolean hasPendingTeleport(int var1) {
      synchronized (this.stateLock) {
         for (EntityPositionTracker$0 var4 : this.enyAs) {
            if (var4.entityId == var1 && var4.vObedA) {
               return true;
            }
         }

         return false;
      }
   }

   private void dropQueuedUpdates(int var1) {
      Iterator var2 = this.enyAs.iterator();

      while (var2.hasNext()) {
         if (((EntityPositionTracker$0)var2.next()).entityId == var1) {
            var2.remove();
         }
      }
   }

   public void flushToPredictor(MotionPredictor var1) {
      synchronized (this.stateLock) {
         EntityPositionTracker$0 var3;
         while ((var3 = this.enyAs.poll()) != null) {
            if (var3.vObedA) {
               var1.applyVelocityPacket(var3.entityId, var3.abmQ, var3.WESfV, var3.y, var3.z);
            } else {
               var1.SJz3(var3.entityId, var3.abmQ, var3.WESfV, var3.y, var3.z, var3.teleported);
            }
         }
      }
   }
}
