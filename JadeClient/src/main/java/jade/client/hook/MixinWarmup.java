// Jade recovery: original class: jade.deps.eLz.u5SyWfW0
package jade.client.hook;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class MixinWarmup implements ClassFileTransformer {
   private static final Object REGISTRATION_COMPLETE_MARKER = new Object();
   private final Set<String> protectedTargets = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private final CountDownLatch lRd = new CountDownLatch(1);
   private final CountDownLatch registrationLatch = new CountDownLatch(1);
   private final AtomicReference<Object> registrationOutcome = new AtomicReference<>();
   private volatile Map<String, byte[]> warmCacheDigests = Collections.emptyMap();
   private volatile Map<String, byte[]> uZcme4 = Collections.emptyMap();
   private volatile String rejectionReason;
   private volatile Thread GiU;

   public void protectTarget(String var1) {
      String var2 = NFzhe(var1);
      if (var2 != null) {
         this.protectedTargets.add(var2);
      }
   }

   public boolean protects(String var1) {
      String var2 = NFzhe(var1);
      return var2 != null && this.protectedTargets.contains(var2);
   }

   public boolean isExplicitTarget(String var1) {
      String var2 = NFzhe(var1);
      return var2 != null && this.protectedTargets.contains(var2);
   }

   public void setRegistrarThread(Thread var1) {
      this.GiU = var1;
   }

   public void markPreparationComplete() {
      this.lRd.countDown();
   }

   public void markRegistrationComplete() {
      this.lRd.countDown();
      this.registrationOutcome.compareAndSet(null, REGISTRATION_COMPLETE_MARKER);
      this.registrationLatch.countDown();
   }

   public void markFailed(String var1) {
      this.registrationOutcome
         .set(var1 == null ? "unknown Mixin registration failure" : var1);
      this.wipeActiveWarmCache();
      this.clt6();
      this.lRd.countDown();
      this.registrationLatch.countDown();
   }

   public boolean prepareWarmCache(Map<String, byte[]> var1, long var2, TimeUnit var4) throws InterruptedException {
      if (!awaitLatch(this.lRd, var2, var4)) {
         this.rejectionReason = "Mixin target discovery timed out";
         return false;
      } else if (this.failureReason() != null) {
         this.rejectionReason = this.failureReason();
         return false;
      } else if (var1 != null && !var1.isEmpty()) {
         HashMap var5 = new HashMap();

         for (String var7 : this.protectedTargets) {
            byte[] var8 = (byte[])var1.get(var7);
            if (var8 == null) {
               this.rejectionReason = "cache is missing Mixin target "
                  + var7;
               BQPh(var5);
               return false;
            }

            var5.put(var7, computeSha256(var8));
         }

         if (var5.isEmpty()) {
            this.rejectionReason = "no explicit Mixin targets were discovered";
            return false;
         } else {
            this.clt6();
            this.uZcme4 = Collections.unmodifiableMap(var5);
            this.rejectionReason = null;
            return true;
         }
      } else {
         this.rejectionReason = "cache has no classes";
         return false;
      }
   }

   public synchronized boolean activatePreparedWarmCache(Map<String, byte[]> var1) {
      Map var2 = this.uZcme4;
      if (var2.isEmpty()) {
         this.rejectionReason = "warm cache was not prepared";
         return false;
      } else {
         for (Entry var4 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (var2.entrySet())) {
            byte[] var5 = (byte[])var1.get(var4.getKey());
            if (var5 == null || !MessageDigest.isEqual((byte[])var4.getValue(), computeSha256(var5))) {
               this.rejectionReason = "target changed while Genesis consumed it: "
                  + (String)var4.getKey();
               this.clt6();
               return false;
            }
         }

         this.wipeActiveWarmCache();
         this.warmCacheDigests = var2;
         this.uZcme4 = Collections.emptyMap();
         this.rejectionReason = null;
         return true;
      }
   }

   public synchronized void abortPreparedWarmCache() {
      this.clt6();
      this.wipeActiveWarmCache();
   }

   public boolean isWarmCacheActive() {
      return !this.warmCacheDigests.isEmpty();
   }

   public String warmCacheRejectionReason() {
      return this.rejectionReason;
   }

   public boolean awaitPreparation(long var1, TimeUnit var3) throws InterruptedException {
      return awaitLatch(this.lRd, var1, var3) && this.failureReason() == null;
   }

   public boolean awaitRegistration(long var1, TimeUnit var3) throws InterruptedException {
      return awaitLatch(this.registrationLatch, var1, var3) && this.registrationOutcome.get() == REGISTRATION_COMPLETE_MARKER;
   }

   private static boolean awaitLatch(CountDownLatch var0, long var1, TimeUnit var3) throws InterruptedException {
      if (var0.getCount() == 0L) {
         return true;
      } else {
         try {
            return var0.await(var1, var3);
         } catch (InterruptedException var5) {
            if (var0.getCount() != 0L) {
               throw var5;
            } else {
               Thread.currentThread().interrupt();
               return true;
            }
         }
      }
   }

   public String failureReason() {
      Object var1 = this.registrationOutcome.get();
      return var1 instanceof String ? (String)var1 : null;
   }

   public boolean isRegistrationComplete() {
      return this.registrationOutcome.get() == REGISTRATION_COMPLETE_MARKER;
   }

   @Override
   public byte[] transform(ClassLoader var1, String var2, Class<?> var3, ProtectionDomain var4, byte[] var5) throws IllegalClassFormatException {
      if (!this.protects(var2)) {
         return null;
      } else {
         Object var6 = this.registrationOutcome.get();
         if (var6 == REGISTRATION_COMPLETE_MARKER) {
            return null;
         } else if (var6 instanceof String) {
            return neutralizeClassBytes(var5);
         } else {
            byte[] var7 = this.warmCacheDigests.get(NFzhe(var2));
            if (var7 != null && var5 != null && MessageDigest.isEqual(var7, computeSha256(var5))) {
               return null;
            } else if (var7 != null) {
               this.markFailed(
                  "Mixin target bytes differ from warm cache: "
                     + var2
               );
               return neutralizeClassBytes(var5);
            } else if (Thread.currentThread() == this.GiU) {
               var6 = this.registrationOutcome.get();
               if (var6 == REGISTRATION_COMPLETE_MARKER) {
                  return null;
               } else if (var6 instanceof String) {
                  return neutralizeClassBytes(var5);
               } else {
                  this.markFailed(
                     "Mixin registrar attempted to define target before registration completed: "
                        + var2
                  );
                  return neutralizeClassBytes(var5);
               }
            } else {
               this.markFailed(
                  "Mixin target reached definition before registration: "
                     + var2
               );
               return neutralizeClassBytes(var5);
            }
         }
      }
   }

   private static byte[] neutralizeClassBytes(byte[] var0) {
      if (var0 != null && var0.length >= 4) {
         byte[] var1 = (byte[])var0.clone();
         var1[0] = 0;
         var1[1] = 0;
         var1[2] = 0;
         var1[3] = 0;
         return var1;
      } else {
         return new byte[]{0};
      }
   }

   private static String NFzhe(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = var0.trim().replace('.', '/').replace('\\', '/');

         while (var1.startsWith("/")) {
            var1 = var1.substring(1);
         }

         if (var1.endsWith(".class")) {
            var1 = var1.substring(0, var1.length() - ".class".length());
         }

         return var1.length() == 0 ? null : var1;
      }
   }

   private void wipeActiveWarmCache() {
      Map var1 = this.warmCacheDigests;
      this.warmCacheDigests = Collections.emptyMap();
      BQPh(var1);
   }

   private void clt6() {
      Map var1 = this.uZcme4;
      this.uZcme4 = Collections.emptyMap();
      BQPh(var1);
   }

   private static void BQPh(Map<String, byte[]> var0) {
      if (var0 != null) {
         for (byte[] var2 : var0.values()) {
            if (var2 != null) {
               Arrays.fill(var2, (byte)0);
            }
         }
      }
   }

   private static byte[] computeSha256(byte[] var0) {
      try {
         return MessageDigest.getInstance("SHA-256").digest(var0);
      } catch (NoSuchAlgorithmException var2) {
         throw new IllegalStateException("SHA-256 unavailable", var2);
      }
   }
}
