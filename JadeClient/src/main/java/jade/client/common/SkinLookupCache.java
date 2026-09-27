// Jade recovery: original class: jade.deps.eLz.JuozSR
package jade.client.common;

import com.mojang.authlib.GameProfile;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.util.ResourceLocation;

public final class SkinLookupCache {
   private final long entryTtlMillis;
   private final Map<String, ResourceLocation> skinLocations = new ConcurrentHashMap<>();
   private final Map<String, UUID> playerIdsByName = new ConcurrentHashMap<>();
   private final Map<String, SkinLookupCache$2> profileCache = new ConcurrentHashMap<>();
   private final Set<String> eeZg1 = newConcurrentKeySet();
   private final Set<String> failedLookupKeys = newConcurrentKeySet();
   private final Set<String> queuedLookupKeys = newConcurrentKeySet();
   private final ConcurrentLinkedQueue<SkinLookupCache$1> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

   public SkinLookupCache(long var1) {
      this.entryTtlMillis = var1;
   }

   private static Set<String> newConcurrentKeySet() {
      return Collections.newSetFromMap(new ConcurrentHashMap<>());
   }

   public static String normalizeName(String var0) {
      return var0 == null ? "" : var0.trim().toLowerCase();
   }

   public ResourceLocation getSkinLocation(String var1) {
      return this.skinLocations.get(var1);
   }

   public void putSkinLocation(String var1, ResourceLocation var2) {
      this.skinLocations.put(var1, var2);
   }

   public UUID THHIYC(String var1) {
      return this.playerIdsByName.get(var1);
   }

   public void putPlayerId(String var1, UUID var2) {
      this.playerIdsByName.put(var1, var2);
   }

   public SkinLookupCache$2 getProfileEntry(String var1) {
      return this.profileCache.get(var1);
   }

   public void putProfileEntry(String var1, GameProfile var2, long var3) {
      this.profileCache.put(var1, new SkinLookupCache$2(var2, var3 + this.entryTtlMillis));
   }

   public boolean markLookupPending(String var1) {
      return this.eeZg1.add(var1);
   }

   public void clearLookupPending(String var1) {
      this.eeZg1.remove(var1);
   }

   public boolean hasLookupFailed(String var1) {
      return this.failedLookupKeys.contains(var1);
   }

   public void markLookupFailed(String var1) {
      this.failedLookupKeys.add(var1);
   }

   public void LqrS(String var1) {
      this.failedLookupKeys.remove(var1);
   }

   public boolean CgFb(String var1, GameProfile var2) {
      if (!this.queuedLookupKeys.add(var1)) {
         return false;
      } else {
         this.concurrentLinkedQueue.add(new SkinLookupCache$1(var1, var2));
         return true;
      }
   }

   public SkinLookupCache$1 pollPendingLookup() {
      return this.concurrentLinkedQueue.poll();
   }

   public void IsRuo(String var1) {
      this.queuedLookupKeys.remove(var1);
   }

   public void invalidate(String var1) {
      this.skinLocations.remove(var1);
      this.profileCache.remove(var1);
      this.failedLookupKeys.remove(var1);
      this.eeZg1.remove(var1);
      this.queuedLookupKeys.remove(var1);
   }

   public void clearAll() {
      this.skinLocations.clear();
      this.playerIdsByName.clear();
      this.profileCache.clear();
      this.eeZg1.clear();
      this.failedLookupKeys.clear();
      this.queuedLookupKeys.clear();
      this.concurrentLinkedQueue.clear();
   }
}
