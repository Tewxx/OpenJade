// Jade recovery: original class: jade.deps.eLz.WiGFx3PFEF
package jade.client.core;

import jade.client.common.PacketDirection;
import jade.client.common.PacketListenerRegistration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import net.minecraft.network.Packet;

public final class PacketQueue {
   private final Deque<PacketQueueEntry> OCj = new ArrayDeque<>();
   private final HandledPacketTracker OptvvS;
   private PacketListenerRegistration activeTask;

   public PacketQueue(HandledPacketTracker var1) {
      this.OptvvS = var1;
   }

   public synchronized void uuoOv(PacketListenerRegistration var1) {
      this.OCj.addLast(new TaskSwitchEntry(var1));
   }

   public synchronized void FZao() {
      this.OCj.clear();
      this.activeTask = null;
   }

   private static boolean isTaskFinished(PacketListenerRegistration var0) {
      return var0 == null || var0.getPacketHandler().isOpen();
   }

   public synchronized boolean processQueue(Packet<?> var1, PacketDirection var2) {
      if (this.OCj.isEmpty() && isTaskFinished(this.activeTask)) {
         this.activeTask = null;
         return false;
      } else {
         if (var1 != null) {
            this.OCj.addLast(new DelayedPacketEntry(var1, var2));
         }

         PacketListenerRegistration var3 = this.activeTask;

         try {
            while (isTaskFinished(var3)) {
               PacketQueueEntry var4 = this.OCj.pollFirst();
               if (var4 == null) {
                  var3 = null;
                  break;
               }

               var3 = var4.nok9(var3, this.OptvvS);
            }
         } catch (Exception var5) {
            var5.printStackTrace();
         }

         this.activeTask = var3;
         return true;
      }
   }

   public synchronized void expireTimedOutTasks(long var1) {
      long var3 = System.currentTimeMillis() - var1;
      ArrayList var5 = new ArrayList();
      Iterator var6 = this.OCj.iterator();

      while (var6.hasNext()) {
         PacketQueueEntry var7 = (PacketQueueEntry)var6.next();
         if (var7.isExpired(var3)) {
            var5.add(var7);
            var6.remove();
         }
      }

      for (PacketQueueEntry var8 : (java.lang.Iterable<PacketQueueEntry>) (java.lang.Iterable<?>) (var5)) {
         try {
            var8.nok9(null, this.OptvvS);
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }
   }
}
