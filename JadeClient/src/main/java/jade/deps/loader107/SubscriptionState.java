package jade.deps.loader107;

import java.time.Instant;

public final class SubscriptionState {
   private static final String DISCORD_USERNAME_PROPERTY = null;
   private static final String DISCORD_USER_ID_PROPERTY = null;
   private static final String STARTUP_ID_PROPERTY = null;
   private static final String CLIENT_UID_PROPERTY = null;
   private static final String EXPIRES_AT_PROPERTY = null;
   private static final long MINUTE_MILLIS = 60000L;
   private static final long HOUR_MILLIS = 3600000L;
   private static final long DAY_MILLIS = 86400000L;
   private static volatile String cachedDiscordUsername;
   private static volatile String cachedDiscordUserId;
   private static volatile long cachedStartupId = -1L;
   private static volatile long cachedClientUid = -1L;
   private static volatile String cachedExpiresAt;

   private SubscriptionState() {
   }

   public static void setSubscriptionIdentity(String var0, String var1, long var2, long var4, String var6) {
      byte var7 = 0;

      while (true) {
         switch (var7) {
            case 0:

               var7 = 1;
               break;
            case 1:
               var7 = 2;
               break;
            default:
               cachedDiscordUsername = nullIfEmpty(var0);
               cachedDiscordUserId = nullIfEmpty(var1);
               cachedStartupId = var2;
               cachedClientUid = var4 > 0L ? var4 : var2;
               cachedExpiresAt = nullIfEmpty(var6);
               if (cachedDiscordUsername != null) {
                  System.setProperty(
                     "jade.deps.a.discord.username",
                     cachedDiscordUsername
                  );
               }

               if (cachedDiscordUserId != null) {
                  System.setProperty(
                     "jade.deps.a.discord.userId",
                     cachedDiscordUserId
                  );
               }

               if (cachedStartupId > 0L) {
                  System.setProperty(
                     "jade.deps.a.startupId",
                     String.valueOf(cachedStartupId)
                  );
               }

               if (cachedClientUid > 0L) {
                  System.setProperty(
                     "jade.deps.a.clientUid",
                     String.valueOf(cachedClientUid)
                  );
               }

               if (cachedExpiresAt != null) {
                  System.setProperty(
                     "jade.deps.a.expiresAt", cachedExpiresAt
                  );
               }

               return;
         }
      }
   }

   public static String getDiscordUsername() {
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
               String var0 = cachedDiscordUsername;
               if (var0 != null) {
                  return var0;
               } else {
                  var0 = System.getProperty("jade.deps.a.discord.username");
                  if (var0 != null) {
                     cachedDiscordUsername = var0;
                  }

                  return var0;
               }
         }
      }
   }

   public static String getDiscordUserId() {
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
               String var0 = cachedDiscordUserId;
               if (var0 != null) {
                  return var0;
               } else {
                  var0 = System.getProperty("jade.deps.a.discord.userId");
                  if (var0 != null) {
                     cachedDiscordUserId = var0;
                  }

                  return var0;
               }
         }
      }
   }

   public static long getStartupId() {
      long var0 = cachedStartupId;
      if (var0 > 0L) {
         return var0;
      } else {
         String var2 = System.getProperty("jade.deps.a.startupId");
         if (var2 != null) {
            try {
               var0 = Long.parseLong(var2);
               if (var0 > 0L) {
                  cachedStartupId = var0;
               }

               return var0;
            } catch (NumberFormatException var4) {
            }
         }

         return -1L;
      }
   }

   public static long getClientUid() {
      long var0 = cachedClientUid;
      if (var0 > 0L) {
         return var0;
      } else {
         String var2 = System.getProperty("jade.deps.a.clientUid");
         if (var2 != null) {
            try {
               var0 = Long.parseLong(var2);
               if (var0 > 0L) {
                  cachedClientUid = var0;
               }

               return var0;
            } catch (NumberFormatException var4) {
            }
         }

         return getStartupId();
      }
   }

   public static void setClientUid(long var0) {
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
               if (var0 <= 0L) {
                  return;
               } else {
                  cachedClientUid = var0;
                  System.setProperty("jade.deps.a.clientUid", String.valueOf(var0));
                  return;
               }
         }
      }
   }

   public static String getExpiresAt() {
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
               String var0 = cachedExpiresAt;
               if (var0 != null) {
                  return var0;
               } else {
                  var0 = System.getProperty("jade.deps.a.expiresAt");
                  if (var0 != null) {
                     cachedExpiresAt = var0;
                  }

                  return var0;
               }
         }
      }
   }

   public static long getRemainingMillis() {
      String var0 = getExpiresAt();
      if (var0 == null) {
         return Long.MAX_VALUE;
      } else {
         try {
            String var1 = var0.contains("T") ? var0 : var0.replace(' ', 'T');
            if (!var1.endsWith("Z")
               && !var1.contains("+")) {
               var1 = var1 + "Z";
            }

            return Instant.parse(var1).toEpochMilli() - System.currentTimeMillis();
         } catch (Exception var2) {
            return Long.MAX_VALUE;
         }
      }
   }

   public static String formatRemainingTime() {
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
               long var0 = getRemainingMillis();
               if (var0 == Long.MAX_VALUE) {
                  return "Lifetime";
               } else if (var0 <= 0L) {
                  return "expired";
               } else if (var0 < 3600000L) {
                  return Math.max(1L, var0 / 60000L) + "m";
               } else {
                  return var0 < 86400000L
                     ? "today"
                     : var0 / 86400000L + "d";
               }
         }
      }
   }

   private static String nullIfEmpty(String var0) {
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
               return var0 != null && !var0.isEmpty() ? var0 : null;
         }
      }
   }

   static {
   }
}
