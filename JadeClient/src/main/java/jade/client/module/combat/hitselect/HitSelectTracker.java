// Jade recovery: original class: jade.deps.eLz.H9W4kKw0
package jade.client.module.combat.hitselect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public final class HitSelectTracker {
   private final Map<Integer, PlayerHitTracker> playerStates = new HashMap<>();
   private final LocalHurtTracker localPlayerState = new LocalHurtTracker();
   private EntityPlayer entityPlayer;

   public EntityPlayer getTrackedTarget() {
      return this.entityPlayer;
   }

   public LocalHurtTracker getLocalPlayerState() {
      return this.localPlayerState;
   }

   public void updateTrackedTarget(EntityPlayer var1, int var2, boolean var3) {
      if (hF77(this.entityPlayer, var1)) {
         if (var1 != null) {
            this.entityPlayer = var1;
            this.getOrCreatePlayerState(var1, var3);
         }
      } else {
         this.entityPlayer = var1;
         this.localPlayerState.startFirstHitWait(var1 != null, var2);
         if (var1 != null) {
            this.getOrCreatePlayerState(var1, var3);
         }
      }
   }

   public PlayerHitTracker getOrCreatePlayerState(EntityPlayer var1, boolean var2) {
      int var3 = var1.getEntityId();
      PlayerHitTracker var4 = this.playerStates.get(var3);
      if (var4 == null) {
         var4 = new PlayerHitTracker(var2 ? var1.hurtTime : 0);
         this.playerStates.put(var3, var4);
      }

      return var4;
   }

   public void pruneInvalidPlayers(World var1) {
      if (var1 == null) {
         this.playerStates.clear();
      } else {
         Iterator var2 = this.playerStates.entrySet().iterator();

         while (var2.hasNext()) {
            Entity var3 = var1.getEntityByID((Integer)((Entry)var2.next()).getKey());
            if (!(var3 instanceof EntityPlayer) || var3.isDead || ((EntityPlayer)var3).deathTime != 0) {
               var2.remove();
            }
         }
      }
   }

   public void resetAll() {
      this.entityPlayer = null;
      this.playerStates.clear();
      this.localPlayerState.resetAll();
   }

   private static boolean hF77(EntityPlayer var0, EntityPlayer var1) {
      return var0 != null && var1 != null ? var0.getEntityId() == var1.getEntityId() : var0 == var1;
   }
}
