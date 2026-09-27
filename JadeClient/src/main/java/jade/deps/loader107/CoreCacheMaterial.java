package jade.deps.loader107;

import java.util.Arrays;

public final class CoreCacheMaterial {
   private byte[] coreBytes;
   private byte[] cacheKey;
   private final String cacheBinding;
   private final int cacheEpoch;

   CoreCacheMaterial(byte[] var1, byte[] var2, String var3, int var4) {
      if (var1 != null && var1.length != 0) {
         if (var2 == null || var2.length != 32) {
            throw new IllegalArgumentException("invalid cache key");
         } else if (var3 == null || !var3.matches("[0-9a-f]{64}")) {
            throw new IllegalArgumentException("invalid cache binding");
         } else if (var4 <= 0) {
            throw new IllegalArgumentException("invalid cache epoch");
         } else {
            this.coreBytes = var1;
            this.cacheKey = var2;
            this.cacheBinding = var3;
            this.cacheEpoch = var4;
         }
      } else {
         throw new IllegalArgumentException("missing core");
      }
   }

   public synchronized byte[] consumeCoreBytes() {
      return (byte[])RecoveredMethodInterpreter.invokeRecovered(
         "SlZNAeJmg4sAAAACAAAAAwAAABAAAAAAAAAAAuJmg5IAAAAAAAAABeJmgosAAAAAAAAAAQAAAAEAAAAAAAAAAuJmg0wAAAAIAAAABeJmgosAAAABAAAAAAAAAAEAAAAAAAAAAeJmg9IAAAAF4maCiwAAAAIAAAAAAAAAAQAAAAAAAAAF4maCiwAAAAMAAAACAAAAAQAAAAEAAAAB4maDNAAAAALiZoOSAAAAAAAAAAXiZoKLAAAABAAAAAEAAAABAAAAAAAAAALiZoOxAAAAAQAAAALiZoOSAAAAAAAAAAHiZoOKAAAABeJmgosAAAAFAAAAAgAAAAAAAAAAAAAAAuJmg5IAAAABAAAAAeJmgzs=",
         jade.build.RecoveredHandles.resolve(CoreCacheMaterial.class, "recoveredConsumeCoreBytes"),
         new Object[]{this}
      );
   }

   public synchronized byte[] consumeCacheKey() {
      return (byte[])RecoveredMethodInterpreter.invokeRecovered(
         "SlZNAaEdRrsAAAACAAAAAwAAABAAAAAAAAAAAqEdRqIAAAAAAAAABaEdR7sAAAAAAAAAAQAAAAEAAAAAAAAAAqEdRnwAAAAIAAAABaEdR7sAAAABAAAAAAAAAAEAAAAAAAAAAaEdRuIAAAAFoR1HuwAAAAIAAAAAAAAAAQAAAAAAAAAFoR1HuwAAAAMAAAACAAAAAQAAAAEAAAABoR1GBAAAAAKhHUaiAAAAAAAAAAWhHUe7AAAABAAAAAEAAAABAAAAAAAAAAKhHUaBAAAAAQAAAAKhHUaiAAAAAAAAAAGhHUa6AAAABaEdR7sAAAAFAAAAAgAAAAAAAAAAAAAAAqEdRqIAAAABAAAAAaEdRgs=",
         jade.build.RecoveredHandles.resolve(CoreCacheMaterial.class, "recoveredConsumeCacheKey"),
         new Object[]{this}
      );
   }

   public String getCacheBinding() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return (String)RecoveredMethodInterpreter.invokeRecovered(
                  "SlZNAZ61FvgAAAABAAAAAQAAAAMAAAAAAAAAAp61FuEAAAAAAAAABZ61F/gAAAAAAAAAAQAAAAEAAAAAAAAAAZ61Fkg=",
                  jade.build.RecoveredHandles.resolve(CoreCacheMaterial.class, "recoveredGetCacheBinding"),
                  new Object[]{this}
               );
         }
      }
   }

   public int getCacheEpoch() {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return ((Number)RecoveredMethodInterpreter.invokeRecovered(
                     "SlZNAb46llYAAAABAAAAAQAAAAMAAAAAAAAAAr46lk8AAAAAAAAABb46l1YAAAAAAAAAAQAAAAEAAAAAAAAAAb46lvo=",
                     jade.build.RecoveredHandles.resolve(CoreCacheMaterial.class, "recoveredGetCacheEpoch"),
                     new Object[]{this}
                  ))
                  .intValue();
         }
      }
   }

   public synchronized void clearSensitiveMaterial() {
      RecoveredMethodInterpreter.invokeRecovered(
         "SlZNAYD8ygwAAAABAAAAAgAAAA0AAAAAAAAAAoD8yhUAAAAAAAAABYD8ywwAAAAAAAAAAQAAAAEAAAAAAAAABYD8ywwAAAABAAAAAQAAAAAAAAAAAAAAAoD8yhUAAAAAAAAABYD8ywwAAAACAAAAAQAAAAEAAAAAAAAABYD8ywwAAAADAAAAAQAAAAAAAAAAAAAAAoD8yhUAAAAAAAAAAYD8yg0AAAAFgPzLDAAAAAQAAAACAAAAAAAAAAAAAAACgPzKFQAAAAAAAAABgPzKDQAAAAWA/MsMAAAABQAAAAIAAAAAAAAAAAAAAAGA/Mq9",
         jade.build.RecoveredHandles.resolve(CoreCacheMaterial.class, "recoveredClearSensitiveMaterial"),
         new Object[]{this}
      );
   }

   private static void wipeBytes(byte[] var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               RecoveredMethodInterpreter.invokeRecovered(
                  "SlZNAejmY8MAAAABAAAAAgAAAAYAAAAAAAAAAujmY9oAAAAAAAAAAujmYwUAAAAFAAAAAujmY9oAAAAAAAAAAejmY8AAAAAF6OZiwwAAAAAAAAACAAAAAAAAAAAAAAAB6OZjcg==",
                  jade.build.RecoveredHandles.resolve(CoreCacheMaterial.class, "recoveredWipeBytes"),
                  new Object[]{var0}
               );
               return;
         }
      }
   }

   private static Object recoveredConsumeCoreBytes(int var0, Object[] var1) throws Throwable {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               switch (var0) {
                  case 0:
                     return ((CoreCacheMaterial)var1[0]).coreBytes;
                  case 1:
                     return RecoveredMethodInterpreter.initializeClass(IllegalStateException.class);
                  case 2:
                     return "local core material was already consumed";
                  case 3:
                     return new IllegalStateException((String)var1[1]);
                  case 4:
                     return ((CoreCacheMaterial)var1[0]).coreBytes;
                  case 5:
                     ((CoreCacheMaterial)var1[0]).coreBytes = (byte[])var1[1];
                     return null;
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object recoveredConsumeCacheKey(int var0, Object[] var1) throws Throwable {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               switch (var0) {
                  case 0:
                     return ((CoreCacheMaterial)var1[0]).cacheKey;
                  case 1:
                     return RecoveredMethodInterpreter.initializeClass(IllegalStateException.class);
                  case 2:
                     return "local cache key was already consumed";
                  case 3:
                     return new IllegalStateException((String)var1[1]);
                  case 4:
                     return ((CoreCacheMaterial)var1[0]).cacheKey;
                  case 5:
                     ((CoreCacheMaterial)var1[0]).cacheKey = (byte[])var1[1];
                     return null;
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object recoveredGetCacheBinding(int var0, Object[] var1) throws Throwable {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               switch (var0) {
                  case 0:
                     return ((CoreCacheMaterial)var1[0]).cacheBinding;
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object recoveredGetCacheEpoch(int var0, Object[] var1) throws Throwable {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               switch (var0) {
                  case 0:
                     return ((CoreCacheMaterial)var1[0]).cacheEpoch;
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object recoveredClearSensitiveMaterial(int var0, Object[] var1) throws Throwable {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               switch (var0) {
                  case 0:
                     return ((CoreCacheMaterial)var1[0]).coreBytes;
                  case 1:
                     wipeBytes((byte[])var1[0]);
                     return null;
                  case 2:
                     return ((CoreCacheMaterial)var1[0]).cacheKey;
                  case 3:
                     wipeBytes((byte[])var1[0]);
                     return null;
                  case 4:
                     ((CoreCacheMaterial)var1[0]).coreBytes = (byte[])var1[1];
                     return null;
                  case 5:
                     ((CoreCacheMaterial)var1[0]).cacheKey = (byte[])var1[1];
                     return null;
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object recoveredWipeBytes(int var0, Object[] var1) throws Throwable {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               switch (var0) {
                  case 0:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }
}
